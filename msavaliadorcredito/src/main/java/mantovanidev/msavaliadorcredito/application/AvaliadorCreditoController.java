package mantovanidev.msavaliadorcredito.application;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import mantovanidev.msavaliadorcredito.domain.domain.SituacaoCliente;
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
    public ResponseEntity<SituacaoCliente> consultaSituacaoCliente(@RequestParam("cpf") String cpf){

        SituacaoCliente situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
    }
}
