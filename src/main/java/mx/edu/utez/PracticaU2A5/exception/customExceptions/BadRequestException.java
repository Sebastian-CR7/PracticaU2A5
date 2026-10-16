package mx.edu.utez.PracticaU2A5.exception.customExceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {

        super(message);
    }

}
