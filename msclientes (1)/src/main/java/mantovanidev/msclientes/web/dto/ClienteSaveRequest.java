package mantovanidev.msclientes.web.dto;

import lombok.Data;
import mantovanidev.msclientes.entities.Cliente;
@Data
public class ClienteSaveRequest {
    private String cpf;
    private String nome;
    private Integer idade;

    public Cliente toModel(){
        return new Cliente(cpf,nome,idade);
    }

}
