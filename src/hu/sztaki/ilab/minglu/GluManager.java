package hu.sztaki.ilab.minglu;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class GluManager {

  private final Class[] setDependenciesParamTypes
    = new Class[] {GluContainer.class};

  private List<ObjectDescriptor> objects = new ArrayList<ObjectDescriptor>();

  private Map<String, Object> nameToObject = new HashMap<String, Object>();

  public void add(String name, Object obj, String ruleString) {
    checkForMultipleRegistrations(name);
    ObjectDescriptor descriptor = new ObjectDescriptor(name, obj,
        parseRules(ruleString));
    nameToObject.put(name, obj);
    objects.add(descriptor);
  }

  private void checkForMultipleRegistrations(String name) {
    if (nameToObject.containsKey(name)) {
      throw new MultipleRegistrationException(
          "This id has been already registered in GluManager:\""
          + name + "\"");
    }
  }

  private Map<String, String> parseRules(String ruleString) {
    RuleParser ruleParser = new RuleParser(ruleString);
    ruleParser.run();
    return ruleParser.getRules();
  }

  public void setDependencies() {
    new DependencySetter(objects, nameToObject).run();
  }

  public void init() {
    new ObjectInitializer(objects).run();
  }

  public Object get(String id) {
    if (nameToObject.containsKey(id)) {
      return nameToObject.get(id);
    } else {
      return null;
    }
  }

  public boolean containsKey(String id) {
    return nameToObject.containsKey(id);
  }
}
