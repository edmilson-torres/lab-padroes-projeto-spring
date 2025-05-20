package one.digitalinnovation.gof.controller.exceptions;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Error", description = "Erro de resposta")
public class ErrorResponse {

    private String error;
    private int status;
    private String timestamp;
    private List<String> errors;

    public ErrorResponse(String error, int status, String timestamp, List<String> errors) {
        this.error = error;
        this.status = status;
        this.timestamp = timestamp;
        this.errors = errors;
    }

    public String getError() {
        return error;
    }

    public int getStatus() {
        return status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public List<String> getErrors() {
        return errors;
    }
}
