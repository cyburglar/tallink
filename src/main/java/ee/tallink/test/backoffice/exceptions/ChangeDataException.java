package ee.tallink.test.backoffice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ChangeDataException extends RuntimeException{

  public ChangeDataException(String message) {
    super(message);
  }
  
}
