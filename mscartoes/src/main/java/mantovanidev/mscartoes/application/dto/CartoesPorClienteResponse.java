package mantovanidev.mscartoes.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mantovanidev.mscartoes.domain.ClienteCartao;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartoesPorClienteResponse {
        private String nome;
        private String bandeira;
        private BigDecimal limiteLiberado;

        public static CartoesPorClienteResponse fromModel(ClienteCartao model){
            return new CartoesPorClienteResponse(
                    model.getCartao().getNome(),
                    model.getCartao().getBandeira().toString(),
                    model.getLimite()
            );
        }
}
