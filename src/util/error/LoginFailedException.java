package util.error;

public class LoginFailedException extends RuntimeException {
  public LoginFailedException(String message) {
    super(message);  // 메시지는 일부러 단순하게
  }
}