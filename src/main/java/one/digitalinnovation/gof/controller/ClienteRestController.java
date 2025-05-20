package one.digitalinnovation.gof.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import one.digitalinnovation.gof.controller.exceptions.ErrorResponse;
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

	@ApiResponse(responseCode = "200", description = "Busca clientes", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Cliente.class))))
	@Operation(summary = "Listar todos os clientes", description = "Retorna uma lista de todos os clientes cadastrados.")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public ResponseEntity<Iterable<Cliente>> buscarTodos() {
		return ResponseEntity.ok(clienteService.buscarTodos());
	}

	@ApiResponse(responseCode = "200", description = "Busca realizada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class)))
	@ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@Operation(summary = "Buscar cliente por ID", description = "Retorna os dados de um cliente específico pelo seu ID.")
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> buscarPorId(
			@Parameter(description = "ID do cliente", example = "1") @PathVariable Long id) {
		Cliente cliente = clienteService.buscarPorId(id);
		return ResponseEntity.ok(cliente);
	}

	@Operation(summary = "Inserir novo cliente", description = "Adiciona um novo cliente ao sistema.")
	@ApiResponse(responseCode = "201", description = "Cliente inserido com sucesso", content = @Content(mediaType = "application/json"))
	@ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@PostMapping
	public ResponseEntity<Cliente> inserir(@Valid @RequestBody Cliente cliente) {
		Cliente novoCliente = clienteService.inserir(cliente);
		return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
	}

	@Operation(summary = "Atualizar cliente existente", description = "Atualiza os dados de um cliente já cadastrado.")
	@ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso", content = @Content(mediaType = "application/json"))
	@ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@PutMapping("/{id}")
	public ResponseEntity<Cliente> atualizar(
			@Parameter(description = "ID do cliente a ser atualizado", example = "1") @PathVariable Long id,
			@RequestBody @Valid Cliente cliente) {

		clienteService.atualizar(id, cliente);

		return ResponseEntity.ok(cliente);
	}

	@Operation(summary = "Deletar cliente", description = "Remove um cliente do sistema pelo seu ID.")
	@ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso", content = @Content(mediaType = "application/json"))
	@ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(
			@Parameter(description = "ID do cliente a ser deletado", example = "1") @PathVariable Long id) {

		clienteService.deletar(id);

		return ResponseEntity.ok().build();
	}
}
