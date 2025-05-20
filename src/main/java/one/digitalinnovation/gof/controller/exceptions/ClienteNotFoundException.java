package one.digitalinnovation.gof.controller.exceptions;

public class ClienteNotFoundException extends RuntimeException {
    public ClienteNotFoundException(Long id) {
        super("Cliente não encontrado com o ID: " + id);
    }

}
