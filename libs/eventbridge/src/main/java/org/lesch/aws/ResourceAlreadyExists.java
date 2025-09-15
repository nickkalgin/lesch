package org.lesch.aws;

public class ResourceAlreadyExists extends Exception {

  public ResourceAlreadyExists(String message, Throwable cause) {
    super(message, cause);
  }

  public ResourceAlreadyExists(Throwable cause) {
    this(cause.getMessage(), cause);
  }
}
