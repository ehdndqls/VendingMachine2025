package util.error;

public class MoneyExceededException extends RuntimeException {
    public MoneyExceededException(String message) {
        super(message);
    }
}