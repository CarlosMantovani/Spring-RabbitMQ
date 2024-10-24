package mantovanidev.msavaliadorcredito.application.ex;

public class DadosClienteNotFoundExpetion extends Exception{
    public DadosClienteNotFoundExpetion() {
        super("Dados do cliente não encotrado para o cpf informado.");
    }
}
