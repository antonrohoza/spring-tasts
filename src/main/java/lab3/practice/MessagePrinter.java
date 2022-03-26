package lab3.practice;

import lombok.Getter;
import lombok.Setter;

@Transactional
@Logger
@Setter
@Getter
public class MessagePrinter implements Printer {

  private String message;

  public void print() {
    try {
      System.out.println(message);
      Thread.sleep((long) (Math.random() * 100));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
