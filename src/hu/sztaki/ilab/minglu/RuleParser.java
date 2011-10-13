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
    for (String rule : ruleArray) {
      processOneRule(rule);
    }
  }
    
  public Map<String, String> getRules() {
    return rules;
  }

  private void processOneRule(String rule) {
    boolean containsArrow = contains(rule, "<-");
    boolean containsAt = contains(rule, "@");
    if ((!containsArrow && !containsAt) || (containsArrow && containsAt)) {
      throwIncorrectRuleError(rule);
    }
    if (containsArrow) {
      processArrowRule(rule);
    }
    if (containsAt) {
      processAtRule(rule);
    }
  }

  private void processArrowRule(String rule) {
    String[] ruleArray = rule.split("<-");
    if (2 == ruleArray.length) {
      String ruleKey = ruleArray[0].trim();
      String ruleValue = ruleArray[1].trim();
      addRule(ruleKey, ruleValue);
    } else {
      throwIncorrectRuleError(rule);
    }
  }

  private void processAtRule(String rule) {
    String[] ruleArray = rule.split("@");
    if (2 != ruleArray.length) {
      throwIncorrectRuleError(rule);
    }
    String beforeAt = ruleArray[0].trim();
    String afterAt = ruleArray[1].trim();
    if (!beforeAt.equals("")) {
      throwIncorrectRuleError(rule);
    }
    addRule(afterAt, afterAt);
  }

  private void addRule(String ruleKey, String ruleValue) {
    rules.put(ruleKey, ruleValue);
    if (definedRuleKeys.contains(ruleKey)) {
      throw new MultipleRulesException("Rule-key \"" + ruleKey +
          "\" is multiple defined.");
    }
    definedRuleKeys.add(ruleKey);
  }

  private void throwIncorrectRuleError(String rule) {
    throw new IllegalArgumentException("ERROR in GluManager.add: incorrect" +
        " rule: \"" + rule + "\"");
  }

  private boolean contains(String str, String pattern) {
    return -1 != str.indexOf(pattern);
  }
}
