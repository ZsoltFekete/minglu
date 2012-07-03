package hu.sztaki.ilab.minglu;

import java.util.HashMap;
import java.util.Map;

public class SimpleGlu {

  public static class Assignment {
    public String name;
    public Object object;
    public Assignment(String name, Object object) {
      this.name = name;
      this.object = object;
    }
  }

  public static void inject(Object object, Assignment... assignments) {
    Map<String, String> actualRules = new HashMap<String, String>();
    Map<String, Object> assignmentMap = new HashMap<String, Object>();

    for (Assignment assignment : assignments) {
      actualRules.put(assignment.name, assignment.name);
      assignmentMap.put(assignment.name, assignment.object);
    }

    boolean isAutomaticRules = false;
    OneObjectDependencySetter oneObjectDependencySetter =
      new OneObjectDependencySetter(object, actualRules, assignmentMap,
          object.getClass().toString(), isAutomaticRules);
    oneObjectDependencySetter.run();
  }

  public static Assignment inj(String name, Object object) {
    return new Assignment(name, object);
  }
}

