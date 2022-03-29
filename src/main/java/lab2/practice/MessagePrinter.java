package lab2.practice;

import lab2.example.DeprecatedClass;
import lombok.Getter;
import lombok.Setter;

@DeprecatedClass(UpdatedMessagePrinter.class)
@Setter
@Getter
public class MessagePrinter implements Printer {

  private String message;

  @Override
  public void print() {
    System.out.println(message);
  }
}
