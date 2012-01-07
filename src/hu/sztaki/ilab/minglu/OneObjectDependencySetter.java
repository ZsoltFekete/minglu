package hu.sztaki.ilab.minglu;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Field;

public class OneObjectDependencySetter {

  private final Class[] setDependenciesParamTypes
    = new Class[] {GluContainer.class};

  private Map<String, Object> nameToObject;

  private Object actualObject;
  private Map<String, String> actualRules;
  private String actualName;
  private Method setDepMethod;
  private GluContainerImpl gluContainer;

  public OneObjectDependencySetter(Object actualObject,
      Map<String, String> actualRules, Map<String, Object> nameToObject,
      String actualName) {
    this.actualObject = actualObject;
    this.actualRules = actualRules;
    this.actualName = actualName;
    this.nameToObject = nameToObject;
  }

  public void run() {
    if (null != actualObject) {
      runSetDependenciesMethodCheckExcpetions();
    }
  }
  
  private Set<String> usedRules = new HashSet<String>();

  private void runSetDependenciesMethodCheckExcpetions() {
    setDependenciesByAnnotation();
    setDepMethod = getSetDependenciesMethod();
    if (null != setDepMethod) {
      setDependenciesByMethod();
    }
    checkForUnusedRules();
  }

  private void setDependenciesByAnnotation() {
    Class cls = actualObject.getClass();
    Field fieldlist[] = cls.getDeclaredFields();
    for (Field field : fieldlist) {
      Inject annotation = field.getAnnotation(Inject.class);
      if (null != annotation) {
        String propertyName = annotation.value();
        setDependenciesByAnnotationForField(field, propertyName);
      }
    }
  }

  private void setDependenciesByAnnotationForField(Field field,
      String propertyName) {
    checkExistsRuleForProperty(propertyName);
    String nameOfObject = actualRules.get(propertyName);
    checkExistsObjectWithName(nameOfObject);
    Object objectToInject = nameToObject.get(nameOfObject);
    setField(field, objectToInject);
    usedRules.add(propertyName);
  }

  private void checkExistsRuleForProperty(String propertyName) {
    if (!actualRules.containsKey(propertyName)) {
      throw new NotFoundRuleException("ERROR in GluContainer get call for" +
        " object with id \"" + actualName + "\":" + "there is no rule for name \"" +
        propertyName + "\"");
    }
  }

  private void checkExistsObjectWithName(String nameOfObject) {
    if (!nameToObject.containsKey(nameOfObject)) {
      throw new IllegalStateException("ERROR in GluContainer get call for" +
        " object with id \"" + actualName +
        "\": no instances with id \"" + nameOfObject + "\"");
    }
  }

  private void setField(Field field, Object objectToInject) {
    try {
      field.setAccessible(true);
    } catch (Exception e) {}
    try {
      field.set(actualObject, objectToInject);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  private void setDependenciesByMethod() {
    try {
      runSetDependenciesMethod();
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("IllegalAccessException occured:" +
          e.toString());
    } catch (InvocationTargetException e) {
      handleExceptions(e);
    }
  }

  private void runSetDependenciesMethod() throws InvocationTargetException,
          IllegalAccessException{
    gluContainer = new GluContainerImpl(actualRules, nameToObject,
        actualName, usedRules);
    Object[] setDependenciesParams = new Object[] {gluContainer};
    setDepMethod.invoke(actualObject, setDependenciesParams);
  }

  private Method getSetDependenciesMethod() {
    Class<?> cls = actualObject.getClass();
    try {
      return cls.getDeclaredMethod("setDependencies",
        setDependenciesParamTypes);
    } catch(Exception e) {
      return null;
    }
  } 

  private void handleExceptions(java.lang.reflect.InvocationTargetException e) {
    Throwable throwable = e.getTargetException();
    if (throwable instanceof NotFoundRuleException) {
      throw (NotFoundRuleException)throwable;
    }
    throwable.printStackTrace();
    throw new RuntimeException("Exception occured during invoking setDependencies: " +
      throwable.toString());
  }

  void checkForUnusedRules() {
    for (String ruleKey : actualRules.keySet()) {
      if (!usedRules.contains(ruleKey)) {
        throw new UnusedRuleException("Unused rule in \"" + actualName + "\": \""
            + ruleKey + "<-" + actualRules.get(ruleKey) + "\"");
      }
    }
  }
}


