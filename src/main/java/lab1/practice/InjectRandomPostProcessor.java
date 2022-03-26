package lab1.practice;

import java.lang.reflect.Field;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class InjectRandomPostProcessor implements BeanPostProcessor {

  @Override
  public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
    Class<?> clazz = o.getClass();
    for (Field field : clazz.getDeclaredFields()) {
      InjectRandom annotation = field.getDeclaredAnnotation(InjectRandom.class);
      if (annotation != null) {
        Object newValue = RandomNumberServiceLocator.get(field.getType(), annotation);
        field.setAccessible(true);
        ReflectionUtils.setField(field, o, newValue);
      }
    }
    return o;
  }

  @Override
  public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
    return o;
  }
}
