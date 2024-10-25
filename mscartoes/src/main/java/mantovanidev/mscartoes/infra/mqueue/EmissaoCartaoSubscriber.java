package mantovanidev.mscartoes.infra.mqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mantovanidev.mscartoes.domain.Cartao;
import mantovanidev.mscartoes.domain.ClienteCartao;
import mantovanidev.mscartoes.domain.DadosSolicitacaoEmissaoCartao;
import mantovanidev.mscartoes.infra.repository.CartaoRepository;
import mantovanidev.mscartoes.infra.repository.ClienteCartaoRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmissaoCartaoSubscriber {

    private final CartaoRepository cartaoRepository;
    private final ClienteCartaoRepository clienteCartaoRepository;
    @RabbitListener(queues = "${mq.queue.emissao-cartoes}")
    public void receberSolicitacaiEmissao(@Payload String payload){
        try {
            var mapper = new ObjectMapper();
            DadosSolicitacaoEmissaoCartao dados =  mapper.readValue(payload, DadosSolicitacaoEmissaoCartao.class);
            Cartao carta = cartaoRepository.findById(dados.getIdCartao()).orElseThrow();
            ClienteCartao clienteCartao = new ClienteCartao();
            clienteCartao.setCartao(carta);
            clienteCartao.setCpf(dados.getCpf());
            clienteCartao.setLimite(dados.getLimiteLiberado());

            clienteCartaoRepository.save(clienteCartao);

        } catch (Exception e) {
            log.error("Erro ao receber solicitacao de emissao de cartao: {} ", e.getMessage());
           e.printStackTrace();
        }
    }
}
