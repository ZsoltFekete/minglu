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

  public static Object inject(Object object, Assignment... assignments) {
    Map<String, String> actualRules = new HashMap<String, String>();

    Map<String, Object> assignmentMap = new HashMap<String, Object>();

    for (Assignment assignment : assignments) {
      assignmentMap.put(assignment.name, assignment.object);
      actualRules.put(assignment.name, assignment.name);
    }

    OneObjectDependencySetter oneObjectDependencySetter =
      new OneObjectDependencySetter(object, actualRules, assignmentMap,
          object.getClass().toString());
    oneObjectDependencySetter.run();

    return object;
  }

  private static String createRuleString(Assignment[] assignments) {
    String ruleString = "";
    for (int i = 0; i < assignments.length; ++i) {
      ruleString += "@" + assignments[i].name;
      if (i < assignments.length -1) {
        ruleString += ",";
      }
    }
    return ruleString; 
  }

  public static Assignment inj(String name, Object object) {
    return new Assignment(name, object);
  }
}

