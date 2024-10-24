package mantovanidev.mscartoes.infra.mqueue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmissaoCartaoSubscriber {
    @RabbitListener(queues = "${mq.queue.emissao-cartoes}")
    public void receberSolicitacaiEmissao(@Payload String payload){
        System.out.println(payload);
    }
}
