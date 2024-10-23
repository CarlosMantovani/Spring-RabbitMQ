package mantovanidev.mscartoes.application;

import lombok.RequiredArgsConstructor;
import mantovanidev.mscartoes.application.dto.CartaoSaveRequest;
import mantovanidev.mscartoes.domain.Cartao;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cartoes")
@RequiredArgsConstructor
public class CartoesController {
    private final CartaoService service;
    @GetMapping
    public String status(){
        return "ok";
    }
    @PostMapping
    public ResponseEntity cadastra(@RequestBody CartaoSaveRequest request){
        Cartao cartao = request.toModel();
        service.save(cartao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
