package hu.sztaki.ilab.minglu;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

public class DependencySetter {

  private final Class[] setDependenciesParamTypes
    = new Class[] {GluContainer.class};

  private List<ObjectDescriptor> objects;

  private Map<String, Object> nameToObject;

  private Object actualObject;
  private Map<String, String> actualRules;
  private String actualName;
  private Method setDepMethod;
  private GluContainerImpl gluContainer;

  public DependencySetter(List<ObjectDescriptor> objects,
      Map<String, Object> nameToObject) {
    this.objects = objects;
    this.nameToObject = nameToObject;
  }

  public void run() {
    for (ObjectDescriptor descriptor : objects) {
      if (null == descriptor) {
        throw new IllegalStateException("Internal incosistency: descriptor is null.");
      }
      actualObject = descriptor.getObject();
      actualRules = descriptor.getRules();
      actualName = descriptor.getName();
      if (null != actualObject) {
        runSetDependenciesMethodCheckExcpetions();
      }
    }
  }

  private void runSetDependenciesMethodCheckExcpetions() {
    setDepMethod = getSetDependenciesMethod();
    if (null == setDepMethod) return;
    try {
      runSetDependenciesMethod();
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("IllegalAccessException occured:" +
          e.toString());
    } catch (InvocationTargetException e) {
      handleExceptions(e);
    }
    gluContainer.checkForUnusedRules();
  }

  private void runSetDependenciesMethod() throws InvocationTargetException,
          IllegalAccessException{
    gluContainer = new GluContainerImpl(actualRules, nameToObject,
        actualName);
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

}


