package mantovanidev.msclientes.services;

import lombok.RequiredArgsConstructor;
import mantovanidev.msclientes.entities.Cliente;
import mantovanidev.msclientes.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Transactional
    public Cliente save (Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> getByCPF(String cpf) {
        return clienteRepository.findByCpf(cpf);
    }
}
