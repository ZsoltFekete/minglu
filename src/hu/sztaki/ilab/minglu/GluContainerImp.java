package hu.sztaki.ilab.minglu;

import java.util.Map;
import java.util.HashSet;
import java.util.Set;

class GluContainerImpl implements GluContainer {
  private Map<String, String> rules;
  private Map<String, Object> objects;
  private String myId;

  private Set<String> usedRules = new HashSet<String>();

  GluContainerImpl(Map<String, String> rules, Map<String, Object> objects,
      String myId) {
    this.rules = rules;
    this.objects = objects;
    this.myId = myId;
  }

  public Object get(String name) {
    String id = null;
    if (rules.containsKey(name)) {
      id = rules.get(name);
      usedRules.add(name);
    } else {
      throw new NotFoundRuleException("ERROR in GluContainer get call for" +
          " object with id \"" + myId+ "\":" + "there is no rule for name \"" +
          name + "\"");
    }

    if (objects.containsKey(id)) {
      return objects.get(id);
    } else {
      throw new IllegalStateException("ERROR in GluContainer get call for" +
          " object with id \"" + myId+
          "\": no instances with id \"(" + id + "\"");
    }
  }

  void checkForUnusedRules() {
    for (String ruleKey : rules.keySet()) {
      if (!usedRules.contains(ruleKey)) {
        throw new UnusedRuleException("Unused rule in \"" + myId + "\": \""
            + ruleKey + "<-" + rules.get(ruleKey) + "\"");
      }
    }
  }
}

