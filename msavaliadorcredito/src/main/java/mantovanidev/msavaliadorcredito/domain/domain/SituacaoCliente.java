package mantovanidev.msavaliadorcredito.domain.domain;

import lombok.Data;

import java.util.List;

@Data
public class SituacaoCliente {
    private DadosCliente cliente;
    private List<CartaoCliente> cartoes;
}
