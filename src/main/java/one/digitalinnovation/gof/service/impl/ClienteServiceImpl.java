package one.digitalinnovation.gof.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import one.digitalinnovation.gof.controller.exceptions.ClienteNotFoundException;
import one.digitalinnovation.gof.model.Cliente;
import one.digitalinnovation.gof.model.ClienteRepository;
import one.digitalinnovation.gof.model.Endereco;
import one.digitalinnovation.gof.model.EnderecoRepository;
import one.digitalinnovation.gof.service.ClienteService;
import one.digitalinnovation.gof.service.ViaCepService;

/**
 * Implementação da <b>Strategy</b> {@link ClienteService}, a qual pode ser
 * injetada pelo Spring (via {@link Autowired}). Com isso, como essa classe é um
 * {@link Service}, ela será tratada como um <b>Singleton</b>.
 * 
 * @author falvojr
 */
@Service
public class ClienteServiceImpl implements ClienteService {

	private final ClienteRepository clienteRepository;
	private final EnderecoRepository enderecoRepository;
	private final ViaCepService viaCepService;

	public ClienteServiceImpl(
			ClienteRepository clienteRepository,
			EnderecoRepository enderecoRepository,
			ViaCepService viaCepService) {
		this.clienteRepository = clienteRepository;
		this.enderecoRepository = enderecoRepository;
		this.viaCepService = viaCepService;
	}

	@Override
	public Iterable<Cliente> buscarTodos() {
		return clienteRepository.findAll();
	}

	@Override
	public Cliente buscarPorId(Long id) {
		return verificaSeClienteExiste(id);
	}

	@Override
	public Cliente inserir(Cliente cliente) {
		return salvarClienteComCep(cliente);
	}

	@Override
	public Cliente atualizar(Long id, Cliente cliente) {
		Cliente clienteDb = verificaSeClienteExiste(id);

		clienteDb.setNome(cliente.getNome());
		clienteDb.setEndereco(cliente.getEndereco());
		cliente.setId(clienteDb.getId());

		return clienteRepository.save(clienteDb);
	}

	@Override
	public void deletar(Long id) {
		verificaSeClienteExiste(id);

		clienteRepository.deleteById(id);
	}

	private Cliente salvarClienteComCep(Cliente cliente) {
		String cep = cliente.getEndereco().getCep();
		Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
			Endereco novoEndereco = viaCepService.consultarCep(cep);
			enderecoRepository.save(novoEndereco);
			return novoEndereco;
		});

		cliente.setEndereco(endereco);

		return clienteRepository.save(cliente);
	}

	private Cliente verificaSeClienteExiste(Long id) throws ClienteNotFoundException {
		Optional<Cliente> clienteExistente = clienteRepository.findById(id);
		if (!clienteExistente.isPresent()) {
			throw new ClienteNotFoundException(id);
		}

		return clienteExistente.get();
	}
}
