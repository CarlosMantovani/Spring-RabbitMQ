package mantovanidev.msclientes.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mantovanidev.msclientes.entities.Cliente;
import mantovanidev.msclientes.services.ClienteService;
import mantovanidev.msclientes.web.dto.ClienteSaveRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
@Slf4j
public class ClienteController {

    private final ClienteService clienteService;
    @GetMapping
    public String status(){
        log.info("obtento o status");
        return "ok";
    }
    @PostMapping
    public ResponseEntity save(@RequestBody ClienteSaveRequest clienteSaveRequest){
        Cliente cliente =  clienteSaveRequest.toModel();
        clienteService.save(cliente);
        URI headerLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .query("cpf={cpf}")
                .buildAndExpand(cliente.getCpf())
                .toUri();
        return ResponseEntity.created(headerLocation).build();
    }

     @GetMapping(params = "cpf")
    public ResponseEntity dadosCliente(@RequestParam("cpf") String cpf){
        var cliente = clienteService.getByCPF(cpf);
        if (cliente.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
     }
}
