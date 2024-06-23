package by.kovalski.alexsystem.exception;

public class ImpossibleRemoveException extends RuntimeException{
  public ImpossibleRemoveException() {
  }

  public ImpossibleRemoveException(String message) {
    super(message);
  }

  public ImpossibleRemoveException(String message, Throwable cause) {
    super(message, cause);
  }

  public ImpossibleRemoveException(Throwable cause) {
    super(cause);
  }
}
