package mantovanidev.mscartoes.application;

import lombok.RequiredArgsConstructor;
import mantovanidev.mscartoes.domain.ClienteCartao;
import mantovanidev.mscartoes.infra.repository.ClienteCartaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {
    private final ClienteCartaoRepository repository;

    public List<ClienteCartao> listarCartaoByCpf(String cpf){
        return repository.findByCpf(cpf);
    }
}
