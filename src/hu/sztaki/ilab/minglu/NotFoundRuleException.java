package hu.sztaki.ilab.minglu;

public class NotFoundRuleException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  public NotFoundRuleException(String msg) {
    super(msg);
  }
}
