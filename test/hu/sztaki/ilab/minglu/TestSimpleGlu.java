package hu.sztaki.ilab.minglu;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.List;
import java.util.ArrayList;

import static hu.sztaki.ilab.minglu.SimpleGlu.*;

public class TestSimpleGlu extends TestCase {

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


  public void testSimpleGlu() {
    ETestObject e = new ETestObject();
    inject(e, inj("F1", new FTestObject()), inj("F2", new FTestObject()),
        inj("G", new GTestObject()));
  }

  public void testSimpleGlu2() {
    ETestObject e = new ETestObject();
    FTestObject f1 = new FTestObject();
    FTestObject f2 = new FTestObject();
    GTestObject g = new GTestObject();
    inject(e, inj("F1", f1), inj("F2", f2), inj("G", g));
    assertSame(f1, e.f1);
    assertSame(f2, e.f2);
    assertSame(g, e.g);
  }

  public void testSimpleGluTooMayRules() {
    ETestObject e = new ETestObject();
    FTestObject f1 = new FTestObject();
    FTestObject f2 = new FTestObject();
    GTestObject g = new GTestObject();
    try {
      inject(e, inj("F1", f1), inj("F2", f2), inj("G", g), inj("G2", g));
      fail("Expected exception.");
    } catch (Exception exception) {}
  }

  public void testSimpleGluMissingRule() {
    ETestObject e = new ETestObject();
    FTestObject f1 = new FTestObject();
    FTestObject f2 = new FTestObject();
    GTestObject g = new GTestObject();
    try {
      inject(e, inj("F1", f1), inj("F2", f2));
      fail("Expected exception.");
    } catch (Exception exception) {}
  }

  public void testSimpleGluComplex() {
    final List<String> flow = new ArrayList<String>();
    CTestObject c1 = new CTestObject(flow);
    CTestObject c2 = new CTestObject(flow);
    DTestObject d = new DTestObject(flow);
    inject(c1, inj("D", d));
    inject(c2, inj("D", d));
    inject(d, inj("C1", c1), inj("C2", c2));
    c1.init();
    c2.init();
    d.init();
    assertEquals(4, flow.size());
    assertEquals("d_set_dep", flow.get(0));
    assertEquals("c_init", flow.get(1));
    assertEquals("c_init", flow.get(2));
    assertEquals("d_init", flow.get(3));
    assertSame(c1, d.c1);
    assertSame(c2, d.c2);
    assertSame(d, c1.d);
    assertSame(d, c2.d);
  }


  public static Test suite() {
    return new TestSuite(TestSimpleGlu.class);
  }

  public static void main (String[] args) {
    junit.textui.TestRunner.run(suite());
  }

}
