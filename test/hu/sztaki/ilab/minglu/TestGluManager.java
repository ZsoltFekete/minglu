package hu.sztaki.ilab.minglu;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.List;
import java.util.ArrayList;

public class TestGluManager extends TestCase {

  @Override
  protected void setUp() {
  }

  @Override
  protected void tearDown() {
  }

  public void testEmpty() {
    int a = 1;
    assertTrue(1 == a);
  }


  public void testSimple() {
    final List<String> flow = new ArrayList<String>();
    GluManager minGlu = new GluManager();
    ATestObject a1 = new ATestObject(flow);
    ATestObject a2 = new ATestObject(flow);
    BTestObject b = new BTestObject(flow);
    minGlu.add("a1", a1, "B <- b");
    minGlu.add("a2", a2, "B<- b");
    minGlu.add("b", b, "A1 <- a1, A2 <- a2");
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

  public void testSimpleWithAtRule() {
    final List<String> flow = new ArrayList<String>();
    GluManager minGlu = new GluManager();
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

  public void testIncompleteRule() {
    final List<String> flow = new ArrayList<String>();
    GluManager minGlu = new GluManager();
    ATestObject a1 = new ATestObject(flow);
    ATestObject a2 = new ATestObject(flow);
    BTestObject b = new BTestObject(flow);
    minGlu.add("a1", a1, "B <- b");
    minGlu.add("a2", a2, "B<- b");
    minGlu.add("b", b, "A1 <- a1");
    try {
      minGlu.setDependencies();
      fail("Allows incomplete rule");
    } catch (NotFoundRuleException e) {
      System.out.println(e.toString());
    }
  }

  public void testTooManyRule() {
    final List<String> flow = new ArrayList<String>();
    GluManager minGlu = new GluManager();
    ATestObject a1 = new ATestObject(flow);
    ATestObject a2 = new ATestObject(flow);
    BTestObject b = new BTestObject(flow);
    minGlu.add("a1", a1, "B <- b");
    minGlu.add("a2", a2, "B<- b");
    minGlu.add("b", b, "A1 <- a1, A2<- a2, A3 <-a1");
    try {
      minGlu.setDependencies();
      fail("Allows too many rule");
    } catch (UnusedRuleException e) {
      System.out.println(e.toString());
    }
  }

  public void testMultipleRules() {
    final List<String> flow = new ArrayList<String>();
    GluManager minGlu = new GluManager();
    ATestObject a1 = new ATestObject(flow);
    ATestObject a2 = new ATestObject(flow);
    BTestObject b = new BTestObject(flow);
    minGlu.add("a1", a1, "B <- b");
    minGlu.add("a2", a2, "B<- b");
    try {
      minGlu.add("b", b, "A1 <- a1, A2<- a2, A1 <-a1");
      fail("Allows multiple rules");
    } catch (MultipleRulesException e) {
      System.out.println(e.toString());
    }
  }

  public void testMultipleRegistrations() {
    final List<String> flow = new ArrayList<String>();
    GluManager minGlu = new GluManager();
    ATestObject a1 = new ATestObject(flow);
    minGlu.add("a1", a1, "B <- b");
    try {
      minGlu.add("a1", a1, "B <- b");
      fail("Allows multiple registrations.");
    } catch (MultipleRegistrationException e) {}
  }

  public void testInjectByAnnotation() {
    final List<String> flow = new ArrayList<String>();
    GluManager minGlu = new GluManager();
    CTestObject c1 = new CTestObject(flow);
    CTestObject c2 = new CTestObject(flow);
    DTestObject d = new DTestObject(flow);
    minGlu.add("c1", c1, "D <- d");
    minGlu.add("c2", c2, "D<- d");
    minGlu.add("d", d, "C1 <- c1, C2 <- c2");
    minGlu.setDependencies();
    minGlu.init();
    assertTrue(6 == flow.size());
    assertTrue(flow.get(0).equals("c_set_dep"));
    assertTrue(flow.get(1).equals("c_set_dep"));
    assertTrue(flow.get(2).equals("d_set_dep"));
    assertTrue(flow.get(3).equals("c_init"));
    assertTrue(flow.get(4).equals("c_init"));
    assertTrue(flow.get(5).equals("d_init"));
    assertTrue(d.c1 == c1);
    assertTrue(d.c2 == c2);
  }


  public static Test suite() {
    return new TestSuite(TestGluManager.class);
  }

  public static void main (String[] args) {
    junit.textui.TestRunner.run (suite());
  }

}
