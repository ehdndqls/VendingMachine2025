package error;

public class NotEnoughChangeException extends RuntimeException {
    public NotEnoughChangeException(String message) {
        super(message);
    }
}
