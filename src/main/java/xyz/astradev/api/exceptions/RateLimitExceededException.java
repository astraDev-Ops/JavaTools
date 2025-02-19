package xyz.astradev.api.exceptions;

public class RateLimitExceededException extends Throwable {
  public RateLimitExceededException(String message) {
    super(message);
  }
}
