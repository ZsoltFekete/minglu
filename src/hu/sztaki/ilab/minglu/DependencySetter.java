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

public class DependencySetter {

  private List<ObjectDescriptor> objects;

  private Map<String, Object> nameToObject;

  private boolean isAutomaticRules;

  public DependencySetter(List<ObjectDescriptor> objects,
      Map<String, Object> nameToObject, boolean isAutomaticRules) {
    this.objects = objects;
    this.nameToObject = nameToObject;
    this.isAutomaticRules = isAutomaticRules;
  }

  public void run() {
    for (ObjectDescriptor descriptor : objects) {
      if (null == descriptor) {
        throw new IllegalStateException("Internal incosistency: descriptor is null.");
      }
      Object actualObject = descriptor.getObject();
      Map<String, String> actualRules = descriptor.getRules();
      String actualName = descriptor.getName();

      Map<String, Object> assignmentMap = new HashMap<String, Object>();

      OneObjectDependencySetter oneObjectDependencySetter =
        new OneObjectDependencySetter(actualObject, actualRules, nameToObject,
            actualName, isAutomaticRules);
      oneObjectDependencySetter.run();
    }
  }
}


