package one.digitalinnovation.gof.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import one.digitalinnovation.gof.model.Cliente;
import one.digitalinnovation.gof.service.ClienteService;

@Tag(name = "Clientes", description = "API para gerenciamento de clientes, incluindo operações de CRUD e integração com ViaCEP.")
@RestController
@RequestMapping("clientes")
public class ClienteRestController {

	private final ClienteService clienteService;

	public ClienteRestController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@Operation(summary = "Listar todos os clientes", description = "Retorna uma lista de todos os clientes cadastrados.")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public ResponseEntity<Iterable<Cliente>> buscarTodos() {
		return ResponseEntity.ok(clienteService.buscarTodos());
	}

	@ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
	@ApiResponse(responseCode = "404", description = "Cliente não encontrado")
	@Operation(summary = "Buscar cliente por ID", description = "Retorna os dados de um cliente específico pelo seu ID.")
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> buscarPorId(
			@Parameter(description = "ID do cliente", example = "1") @PathVariable Long id) {
		return ResponseEntity.ok(clienteService.buscarPorId(id));
	}

	@Operation(summary = "Inserir novo cliente", description = "Adiciona um novo cliente ao sistema.")
	@ApiResponse(responseCode = "200", description = "Cliente inserido com sucesso")
	@ApiResponse(responseCode = "400", description = "Dados inválidos")
	@ApiResponse(responseCode = "404", description = "Cliente não encontrado")
	@PostMapping
	public ResponseEntity<Cliente> inserir(
			@RequestBody @Valid Cliente cliente, BindingResult result) {

		if (result.hasErrors()) {
			String errorMessage = result.getFieldErrors().get(0).getField() + ' '
					+ result.getAllErrors().get(0).getDefaultMessage();
			throw new IllegalArgumentException(errorMessage);
		}

		clienteService.inserir(cliente);

		return ResponseEntity.ok(cliente);
	}

	@Operation(summary = "Atualizar cliente existente", description = "Atualiza os dados de um cliente já cadastrado.")
	@ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso")
	@ApiResponse(responseCode = "400", description = "Dados inválidos")
	@ApiResponse(responseCode = "404", description = "Cliente não encontrado")
	@PutMapping("/{id}")
	public ResponseEntity<Cliente> atualizar(
			@Parameter(description = "ID do cliente a ser atualizado", example = "1") @PathVariable Long id,
			@RequestBody @Valid Cliente cliente, BindingResult result) {

		if (result.hasErrors()) {
			String errorMessage = result.getFieldErrors().get(0).getField() + ' '
					+ result.getAllErrors().get(0).getDefaultMessage();
			throw new IllegalArgumentException(errorMessage);
		}

		clienteService.atualizar(id, cliente);

		return ResponseEntity.ok(cliente);
	}

	@Operation(summary = "Deletar cliente", description = "Remove um cliente do sistema pelo seu ID.")
	@ApiResponse(responseCode = "200", description = "Cliente deletado com sucesso")
	@ApiResponse(responseCode = "404", description = "Cliente não encontrado")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(
			@Parameter(description = "ID do cliente a ser deletado", example = "1") @PathVariable Long id) {

		clienteService.deletar(id);

		return ResponseEntity.ok().build();
	}
}
