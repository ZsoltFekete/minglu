package hu.sztaki.ilab.minglu;

import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Method;

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
      } catch (Exception e) {
        throw new IllegalStateException("ERROR in GluManager: init " + actualName);
      }
    }
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


