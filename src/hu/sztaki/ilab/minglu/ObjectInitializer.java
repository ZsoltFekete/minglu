package hu.sztaki.ilab.minglu;

import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

public class ObjectInitializer {

  private List<ObjectDescriptor> objects;

  private String actualName;

  private Object actualObject;

  ObjectInitializer(List<ObjectDescriptor> objects) {
    this.objects = objects;
  }

  public void run() {
    for (ObjectDescriptor descriptor : objects) {
      actualObject = descriptor.getObject();
      actualName = descriptor.getName();
      if (null != actualObject) {
        runInitMethod();
      }
    }
  }

  private void runInitMethod() {
    Method initMethod = getInitMethod();
    if (null != initMethod) {
      try {
        initMethod.invoke(actualObject, new Object[]{});
      } catch (IllegalAccessException e) {
        throw new IllegalStateException("IllegalAccessException occured:" +
            e.toString());
      } catch (InvocationTargetException e) {
        handleExceptions(e);
      }
    }
  }

  private void handleExceptions(java.lang.reflect.InvocationTargetException e) {
    Throwable throwable = e.getTargetException();
    if (throwable instanceof NotFoundRuleException) {
      throw (NotFoundRuleException)throwable;
    }
    throwable.printStackTrace();
    throw new RuntimeException("Exception occured during invoking init(): " +
        throwable.toString());
  }

  private Method getInitMethod() {
    Class<?> cls = actualObject.getClass();
    Method method = null;
    try {
      method = cls.getDeclaredMethod("init", new Class[]{});
    } catch(Exception e) {
      return null;
    }
    return method;
  } 
}


