package hu.sztaki.ilab.minglu;

import java.util.Map;

class ObjectDescriptor {
  private String name;
  private Object object;
  private Map<String, String> rules;

  public ObjectDescriptor(String name, Object object, Map<String, String> rules) {
    this. name = name;
    this.object = object;
    this.rules = rules;  
  }

  public String getName() {
    return name;
  }

  public Object getObject() {
    return object;
  }

  public Map<String, String> getRules() {
    return rules;
  }
}
