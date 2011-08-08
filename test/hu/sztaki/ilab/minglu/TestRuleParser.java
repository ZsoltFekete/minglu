package hu.sztaki.ilab.minglu;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Map;

public class TestRuleParser extends TestCase {

  @Override
  protected void setUp() {
  }

  @Override
  protected void tearDown() {
  }

  public void testEmpty() {
    String ruleString = "";
    RuleParser ruleParser = new RuleParser(ruleString);
    ruleParser.run();
    Map<String, String> rules = ruleParser.getRules();
    assertTrue(0 == rules.size());
  }

  public void testOneRule1() {
    String ruleString = "aaa<-bb";
    RuleParser ruleParser = new RuleParser(ruleString);
    ruleParser.run();
    Map<String, String> rules = ruleParser.getRules();
    assertTrue(1 == rules.size());
    assertTrue(rules.get("aaa").equals("bb"));
  }

  public void testOneRule2() {
    String ruleString = " aaa  <- bb ";
    RuleParser ruleParser = new RuleParser(ruleString);
    ruleParser.run();
    Map<String, String> rules = ruleParser.getRules();
    assertTrue(1 == rules.size());
    assertTrue(rules.get("aaa").equals("bb"));
  }

  public void testTwoRules() {
    String ruleString = " aaa  <- bb , qqq <- xx";
    RuleParser ruleParser = new RuleParser(ruleString);
    ruleParser.run();
    Map<String, String> rules = ruleParser.getRules();
    assertTrue(2 == rules.size());
    assertTrue(rules.get("aaa").equals("bb"));
    assertTrue(rules.get("qqq").equals("xx"));
  }

  public void testThreeRules() {
    String ruleString = " aaa  <- bb , qqq <- xx,zz<- v";
    RuleParser ruleParser = new RuleParser(ruleString);
    ruleParser.run();
    Map<String, String> rules = ruleParser.getRules();
    assertTrue(3 == rules.size());
    assertTrue(rules.get("aaa").equals("bb"));
    assertTrue(rules.get("qqq").equals("xx"));
    assertTrue(rules.get("zz").equals("v"));
  }

  public void testBadRule1() {
    String ruleString = " aaa  <- bb qqq <- xx,zz<- v";
    RuleParser ruleParser = new RuleParser(ruleString);
    try {
      ruleParser.run();
      fail("Allows bad rule");
    } catch (Exception e) {}
  }

  public void testBadRule2() {
    String ruleString = " aaa  < - bb";
    RuleParser ruleParser = new RuleParser(ruleString);
    try {
      ruleParser.run();
      fail("Allows bad rule");
    } catch (Exception e) {}
  }

  public void testMultipleRules() {
    String ruleString = " aaa  <- bb, aaa <- qwe";
    RuleParser ruleParser = new RuleParser(ruleString);
    try {
      ruleParser.run();
      fail("Allows multiple rules");
    } catch (MultipleRulesException e) {}
  }


  public static Test suite() {
    return new TestSuite(TestRuleParser.class);
  }

  public static void main (String[] args) {
    junit.textui.TestRunner.run (suite());
  }

}
