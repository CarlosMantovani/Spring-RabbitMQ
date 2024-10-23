package mantovanidev.mscartoes.application;

import lombok.RequiredArgsConstructor;
import mantovanidev.mscartoes.domain.Cartao;
import mantovanidev.mscartoes.infra.repository.CartaoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartaoService {
    private final CartaoRepository cartaoRepository;

    public Cartao save(Cartao cartao){
        return cartaoRepository.save(cartao);
    }

    public List<Cartao> getCartoesRendaMenorIgual(Long renda){
        var rendaBigDecimal = BigDecimal.valueOf(renda);
        return cartaoRepository.findByRendaLessThanEqual(rendaBigDecimal);
    }
}
