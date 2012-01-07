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


  public static Test suite() {
    return new TestSuite(TestSimpleGlu.class);
  }

  public static void main (String[] args) {
    junit.textui.TestRunner.run(suite());
  }

}
