package lab2.practice;

import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class PropertyPlaceholder implements BeanFactoryPostProcessor {

  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
      throws BeansException {
    Properties properties = getProperties();
    Arrays.stream(beanFactory.getBeanDefinitionNames())
          .map(beanFactory::getBeanDefinition)
          .map(BeanDefinition::getPropertyValues)
          .forEach(mutablePropertyValues -> redefineValues(0, mutablePropertyValues, properties));
  }

  private void redefineValues(int counter, MutablePropertyValues propertyValues, Properties properties) {
    if (counter < propertyValues.size()) {
      PropertyValue propertyValue = propertyValues.getPropertyValueList().get(counter);
      String originalPropertyValue = Objects.requireNonNull(propertyValue.getValue()).toString();
      String propertyName = parsePropertyName(originalPropertyValue);
      String realPropertyValue = properties.getProperty(propertyName);
      propertyValues.add(propertyValue.getName(), realPropertyValue);
      redefineValues(++counter, propertyValues, properties);
    }
  }

  private String parsePropertyName(String propertyValueName) {
    return Arrays.stream(propertyValueName.split("\\$\\{"))
                 .filter(element -> element.contains("}"))
                 .map(element -> element.split("}")[0])
                 .findFirst()
                 .orElseThrow(RuntimeException::new);
  }

  @SneakyThrows
  private Properties getProperties() {
    Properties properties = new Properties();
    properties.load(
        PropertyPlaceholder.class.getClassLoader().getResourceAsStream("application.properties"));
    return properties;
  }
}
