package mantovanidev.mscartoes.application.dto;

import lombok.Data;
import mantovanidev.mscartoes.domain.BandeiraCartao;
import mantovanidev.mscartoes.domain.Cartao;

import java.math.BigDecimal;

@Data
public class CartaoSaveRequest {
    private String nome;
    private BandeiraCartao bandeira;
    private BigDecimal renda;
    private BigDecimal limite;

    public Cartao toModel(){
        return new Cartao(nome,bandeira,renda,limite);
    }
}
