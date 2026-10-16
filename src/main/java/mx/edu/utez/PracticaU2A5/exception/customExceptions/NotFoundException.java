package mx.edu.utez.PracticaU2A5.exception.customExceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
