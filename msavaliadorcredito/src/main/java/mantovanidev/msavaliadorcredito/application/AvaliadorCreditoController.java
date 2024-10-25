package mantovanidev.msavaliadorcredito.application;

import lombok.RequiredArgsConstructor;
import mantovanidev.msavaliadorcredito.application.ex.DadosClienteNotFoundExpetion;
import mantovanidev.msavaliadorcredito.application.ex.ErroComunicacaoMicroserviceExpetion;
import mantovanidev.msavaliadorcredito.application.ex.ErroSolicitacaoCartaoException;
import mantovanidev.msavaliadorcredito.domain.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/avaliacoes-credito")
@RequiredArgsConstructor
public class AvaliadorCreditoController {

    private final AvaliadorCreditoService avaliadorCreditoService;

    @GetMapping
    public String ok(){
        return "ok";
    }
    @GetMapping(value= "situacao-cliente", params = "cpf")
    public ResponseEntity consultarSituacaoCliente(@RequestParam("cpf") String cpf){

        try {
            SituacaoCliente situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
            return ResponseEntity.ok(situacaoCliente);
        } catch (DadosClienteNotFoundExpetion e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMicroserviceExpetion e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }
    @PostMapping
    public ResponseEntity realizarAvalicao( @RequestBody DadosAvaliacao dados) {

        try {
           RetornoAvaliacaoCliente retornoAvaliacaoCliente = avaliadorCreditoService.realizarAvaliacao(dados.getCpf(), dados.getRenda());
            return ResponseEntity.ok(retornoAvaliacaoCliente);
        } catch (DadosClienteNotFoundExpetion e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMicroserviceExpetion e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }

    @PostMapping("solicitacoes-cartao")
    public ResponseEntity solicitarCarta(@RequestBody DadosSolicitacaoEmissaoCartao dados) {
       try {
           ProtocoloSolicitacaoCartao protocoloSolicitacaoCartao = avaliadorCreditoService.solicitarEmissaoCartao(dados);
           return ResponseEntity.ok(protocoloSolicitacaoCartao);
       }catch (ErroSolicitacaoCartaoException e){
           return ResponseEntity.internalServerError().body(e.getMessage());
       }
    }
}
