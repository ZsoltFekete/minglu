package hu.sztaki.ilab.minglu;

import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

class RuleParser {
  private String ruleString;
  private Map<String, String> rules = new HashMap<String, String>();
  private Set<String> definedRuleKeys = new HashSet<String>();

  public RuleParser(String ruleString) {
    this.ruleString = ruleString;
  }
  
  public void run() {
    if (null == ruleString || ruleString.trim().equals("")) {
      return;
    }
    String[] ruleArray = ruleString.split(",");
    for (String oneRule : ruleArray) {
      processOneRule(oneRule);
    }
  }
    
  public Map<String, String> getRules() {
    return rules;
  }

  private void processOneRule(String oneRule) {
    String[] oneRuleArray = oneRule.split("<-");
    if (2 == oneRuleArray.length) {
      String ruleKey = oneRuleArray[0].trim();
      rules.put(ruleKey, oneRuleArray[1].trim());
      if (definedRuleKeys.contains(ruleKey)) {
        throw new MultipleRulesException("Rule-key \"" + ruleKey +"\" is multiple defined.");
      }
      definedRuleKeys.add(ruleKey);
    } else {
      throw new IllegalArgumentException("ERROR in GluManager.add: incorrect rule: \"" +
        oneRule + "\"");
    }
  }
}
