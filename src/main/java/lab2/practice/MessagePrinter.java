package lab2.practice;

import lab2.example.DeprecatedClass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@DeprecatedClass(UpdatedMessagePrinter.class)
@Component
@Setter
@Getter
public class MessagePrinter implements Printer {

  private String message;

  @Override
  public void print() {
    System.out.println(message);
  }
}
