package lab1.practice;

import java.util.stream.IntStream;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component(value = "printer")
@Setter
public class MessagePrinter implements Printer {

  @InjectRandom(message = "message")
  private String message;

  @InjectRandom(min = 1, max = 2)
  private double count;

  public void print() {
    IntStream.range(0, (int) count).forEach(element -> System.out.println(message));
  }
}
