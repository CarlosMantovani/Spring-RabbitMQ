package mantovanidev.msavaliadorcredito.application;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import mantovanidev.msavaliadorcredito.application.ex.DadosClienteNotFoundExpetion;
import mantovanidev.msavaliadorcredito.application.ex.ErroComunicacaoMicroserviceExpetion;
import mantovanidev.msavaliadorcredito.application.ex.ErroSolicitacaoCartaoException;
import mantovanidev.msavaliadorcredito.domain.model.*;
import mantovanidev.msavaliadorcredito.infra.clients.CartaoResourceClient;
import mantovanidev.msavaliadorcredito.infra.clients.ClienteResourceClient;
import mantovanidev.msavaliadorcredito.infra.mqueue.SolicitacaoEmissaoCartaoPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clienteResourceClient;
    private final CartaoResourceClient cartaoResourceClient;
    private final SolicitacaoEmissaoCartaoPublisher solicitacaoEmissaoCartaoPublisher;
    public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundExpetion, ErroComunicacaoMicroserviceExpetion{
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceClient.dadosCliente(cpf);
            ResponseEntity<List<CartaoCliente>> cartaoResponse = cartaoResourceClient.getCartoesBycCliente(cpf);
            return SituacaoCliente.builder()
                    .cliente(dadosClienteResponse.getBody())
                    .cartoes(cartaoResponse.getBody())
                    .build();

        }catch (FeignException.FeignClientException e){
           int status =  e.status();
           if(HttpStatus.NOT_FOUND.value() == status){
                throw new DadosClienteNotFoundExpetion();
           }
           throw new ErroComunicacaoMicroserviceExpetion(e.getMessage(), status);
        }
    }
    public RetornoAvaliacaoCliente realizarAvaliacao(String cpf,Long renda)
            throws DadosClienteNotFoundExpetion, ErroComunicacaoMicroserviceExpetion{
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceClient.dadosCliente(cpf);
            ResponseEntity<List<Cartao>> cartoesResponse = cartaoResourceClient.getCartoesRendaAte(renda);

            List<Cartao> cartoes = cartoesResponse.getBody();

            var listaCartoesAprovados =  cartoes.stream().map(cartao -> {
                DadosCliente dadosCliente = dadosClienteResponse.getBody();

                BigDecimal limiteBasico = cartao.getLimiteBasico();
                BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());
                var fator =  idadeBD.divide(BigDecimal.valueOf(10));
                BigDecimal limiteAprovado = fator.multiply(limiteBasico);

                CartaoAprovado aprovado = new CartaoAprovado();
                aprovado.setCartao(cartao.getNome());
                aprovado.setBandeira(cartao.getBandeira());
                aprovado.setLimiteAprovado(limiteAprovado);

                return aprovado;
            }).collect(Collectors.toList());

            return new RetornoAvaliacaoCliente(listaCartoesAprovados);

        }catch (FeignException.FeignClientException e){
            int status =  e.status();
            if(HttpStatus.NOT_FOUND.value() == status){
                throw new DadosClienteNotFoundExpetion();
            }
            throw new ErroComunicacaoMicroserviceExpetion(e.getMessage(), status);
        }
    }

    @PostMapping("solicitacoes-cartao")
    public ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadosSolicitacaoEmissaoCartao dados){
        try {
            solicitacaoEmissaoCartaoPublisher.solicitarCartao(dados);
            var protocolo = UUID.randomUUID().toString();
            return new ProtocoloSolicitacaoCartao(protocolo);
        }catch (Exception e){
            throw new ErroSolicitacaoCartaoException(e.getMessage());
        }
    }

}
