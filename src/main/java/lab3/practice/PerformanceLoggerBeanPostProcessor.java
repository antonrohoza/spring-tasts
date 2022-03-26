package lab3.practice;


import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

public class PerformanceLoggerBeanPostProcessor implements BeanPostProcessor {

  Map<String, Class<?>> map = new HashMap<>();

  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
    Class<?> beanClass = bean.getClass();
    Logger annotationLogger = beanClass.getAnnotation(Logger.class);
    Transactional annotationTransactional = beanClass.getAnnotation(Transactional.class);
    if (annotationLogger != null && annotationTransactional != null) {
      map.put(beanName, beanClass);
    }
    return bean;
  }

  public Object postProcessAfterInitialization(final Object bean, String beanName)
      throws BeansException {
    Class<?> clazz = map.get(beanName);
    if (clazz == null) {
      return bean;
    }
    Object proxyLoggerObject = getLoggerProxy(clazz, bean);
    return getTransactionalProxy(clazz, proxyLoggerObject);
  }

  private Object getLoggerProxy(Class<?> clazz, Object bean) {
    return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(),
                                  (proxy, method, args) -> {
                                    long start = System.currentTimeMillis();
                                    Object value = ReflectionUtils.invokeMethod(method, bean, args);
                                    System.out.println("Execution took = " + (System.currentTimeMillis() - start));
                                    return value;
                                  });
  }

  private Object getTransactionalProxy(Class<?> clazz, Object bean) {
    return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(),
                                  (proxy, method, args) -> {
                                    System.out.println("Transation STARTED");
                                    Object value = ReflectionUtils.invokeMethod(method, bean, args);
                                    System.out.println("Transaction FINISHED");
                                    return value;
                                  });
  }
}
