package mantovanidev.msavaliadorcredito.application.ex;

import lombok.Getter;

public class ErroComunicacaoMicroserviceExpetion extends Exception {
    @Getter
    private Integer status;
    public ErroComunicacaoMicroserviceExpetion(String msg, Integer status) {
        super(msg);
        this.status= status;

    }
}
