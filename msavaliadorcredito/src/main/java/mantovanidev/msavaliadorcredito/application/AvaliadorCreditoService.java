package mantovanidev.msavaliadorcredito.application;

import lombok.RequiredArgsConstructor;
import mantovanidev.msavaliadorcredito.domain.model.DadosCliente;
import mantovanidev.msavaliadorcredito.domain.model.SituacaoCliente;
import mantovanidev.msavaliadorcredito.infra.clients.ClienteResourceClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clienteResourceClient;
    public SituacaoCliente obterSituacaoCliente(String cpf){
       ResponseEntity<DadosCliente> dadosClienteResponse =  clienteResourceClient.dadosCliente(cpf);
       return SituacaoCliente.builder().cliente(dadosClienteResponse.getBody()).build();
    }
}
