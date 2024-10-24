package mantovanidev.msavaliadorcredito.application;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import mantovanidev.msavaliadorcredito.application.ex.DadosClienteNotFoundExpetion;
import mantovanidev.msavaliadorcredito.application.ex.ErroComunicacaoMicroserviceExpetion;
import mantovanidev.msavaliadorcredito.domain.model.CartaoCliente;
import mantovanidev.msavaliadorcredito.domain.model.DadosCliente;
import mantovanidev.msavaliadorcredito.domain.model.SituacaoCliente;
import mantovanidev.msavaliadorcredito.infra.clients.CartaoResourceClient;
import mantovanidev.msavaliadorcredito.infra.clients.ClienteResourceClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clienteResourceClient;
    private final CartaoResourceClient cartaoResourceClient;
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

}
