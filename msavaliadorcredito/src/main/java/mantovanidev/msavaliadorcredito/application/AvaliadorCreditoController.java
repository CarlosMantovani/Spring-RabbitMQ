package mantovanidev.msavaliadorcredito.application;

import lombok.RequiredArgsConstructor;
import mantovanidev.msavaliadorcredito.application.ex.DadosClienteNotFoundExpetion;
import mantovanidev.msavaliadorcredito.application.ex.ErroComunicacaoMicroserviceExpetion;
import mantovanidev.msavaliadorcredito.domain.model.SituacaoCliente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity consultaSituacaoCliente(@RequestParam("cpf") String cpf){


        try {
            SituacaoCliente situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
            return ResponseEntity.ok(situacaoCliente);
        } catch (DadosClienteNotFoundExpetion e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMicroserviceExpetion e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }

    }
}
