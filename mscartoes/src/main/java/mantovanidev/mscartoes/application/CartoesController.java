package mantovanidev.mscartoes.application;

import lombok.RequiredArgsConstructor;
import mantovanidev.mscartoes.application.dto.CartaoSaveRequest;
import mantovanidev.mscartoes.application.dto.CartoesPorClienteResponse;
import mantovanidev.mscartoes.domain.Cartao;
import mantovanidev.mscartoes.domain.ClienteCartao;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cartoes")
@RequiredArgsConstructor

public class CartoesController {

    private final CartaoService cartaoService;
    private final ClienteCartaoService clienteCartaoService;
   @GetMapping
    public String status(){
        return "ok";
    }
    @PostMapping
    public ResponseEntity cadastra(@RequestBody CartaoSaveRequest request){
        Cartao cartao = request.toModel();
        cartaoService.save(cartao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam("renda") Long renda){
       List<Cartao> list = cartaoService.getCartoesRendaMenorIgual(renda);
        return ResponseEntity.ok(list);
    }

    @GetMapping(params = "cpf" )
    public ResponseEntity<List <CartoesPorClienteResponse>> getCartoesBycCliente(@RequestParam("cpf") String cpf){
    List<ClienteCartao> lista = clienteCartaoService.listarCartaoByCpf(cpf);
    List<CartoesPorClienteResponse> resultList = lista.stream().map(CartoesPorClienteResponse::fromModel).collect(Collectors.toList());
    return  ResponseEntity.ok(resultList);
    }
}
