package hu.sztaki.ilab.minglu;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import static org.junit.Assert.*;

public class TestAutomaticRules extends TestCase {

  public void testSimpleWithAtRuleAndNoAmutomaticRule() {
    final List<String> flow = new ArrayList<String>();
    GluManager minGlu = new GluManager();
    minGlu.setAutomaticRules(true);
    ATestObject a1 = new ATestObject(flow);
    ATestObject a2 = new ATestObject(flow);
    BTestObject b = new BTestObject(flow);
    minGlu.add("a1", a1, "@B");
    minGlu.add("a2", a2, "B<- B");
    minGlu.add("B", b, "A1 <- a1, A2 <- a2");
    minGlu.setDependencies();
    minGlu.init();
    assertTrue(6 == flow.size());
    assertTrue(flow.get(0).equals("a_set_dep"));
    assertTrue(flow.get(1).equals("a_set_dep"));
    assertTrue(flow.get(2).equals("b_set_dep"));
    assertTrue(flow.get(3).equals("a_init"));
    assertTrue(flow.get(4).equals("a_init"));
    assertTrue(flow.get(5).equals("b_init"));
    assertTrue(b.a1 == a1);
    assertTrue(b.a2 == a2);
  }
  
  public void testSimpleWithAtRuleAndWihtAutoamticRule() {
    final List<String> flow = new ArrayList<String>();
    GluManager minGlu = new GluManager();
    minGlu.setAutomaticRules(true);
    ATestObject a1 = new ATestObject(flow);
    ATestObject a2 = new ATestObject(flow);
    BTestObject b = new BTestObject(flow);
    minGlu.add("A1", a1, "");
    minGlu.add("a2", a2);
    minGlu.add("B", b, "A2 <- a2");
    minGlu.setDependencies();
    minGlu.init();
    assertTrue(6 == flow.size());
    assertTrue(flow.get(0).equals("a_set_dep"));
    assertTrue(flow.get(1).equals("a_set_dep"));
    assertTrue(flow.get(2).equals("b_set_dep"));
    assertTrue(flow.get(3).equals("a_init"));
    assertTrue(flow.get(4).equals("a_init"));
    assertTrue(flow.get(5).equals("b_init"));
    assertTrue(b.a1 == a1);
    assertTrue(b.a2 == a2);
  }

  public void testInjectByAnnotation() {
    final List<String> flow = new ArrayList<String>();
    GluManager minGlu = new GluManager();
    minGlu.setAutomaticRules(true);
    CTestObject c1 = new CTestObject(flow);
    CTestObject c2 = new CTestObject(flow);
    DTestObject d = new DTestObject(flow);
    minGlu.add("C1", c1, "");
    minGlu.add("C2", c2, "@D");
    minGlu.add("D", d, "");
    minGlu.setDependencies();
    minGlu.init();
    assertEquals(4, flow.size());
    assertTrue(flow.get(0).equals("d_set_dep"));
    assertTrue(flow.get(1).equals("c_init"));
    assertTrue(flow.get(2).equals("c_init"));
    assertTrue(flow.get(3).equals("d_init"));
    assertTrue(d.c1 == c1);
    assertTrue(d.c2 == c2);
    assertTrue(c1.d == d);
    assertTrue(c2.d == d);
  }

  public static Test suite() {
    return new TestSuite(TestAutomaticRules.class);
  }

  public static void main (String[] args) {
    junit.textui.TestRunner.run(suite());
  }
}
