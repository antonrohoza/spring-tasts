package lab1.practice;

public class RandomNumberServiceLocator {

  public static Object get(Class<?> fieldType, InjectRandom injectRandomAnnotation){
    return fieldType.equals(String.class) ? injectRandomAnnotation.message()
                                          : Math.floor(Math.random() * (injectRandomAnnotation.max() - injectRandomAnnotation.min() + 1)
                                                       + injectRandomAnnotation.min());

  }

}
