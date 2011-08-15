package hu.sztaki.ilab.minglu;

public class MultipleRegistrationException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  public MultipleRegistrationException(String msg) {
    super(msg);
  }
}
