package hu.sztaki.ilab.minglu;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Map;

public class TestAll {
  public static void main (String[] args) {
    Class[] testClasses = new Class[] {
      TestRuleParser.class,
      TestGluManager.class
    };
    for (int i = 0; i < testClasses.length; ++i) {
      System.err.println("=====" + testClasses[i].getName() + "=======");
      TestSuite suite = new TestSuite(testClasses[i]);
      junit.textui.TestRunner.run (suite);
    }
  }
}


