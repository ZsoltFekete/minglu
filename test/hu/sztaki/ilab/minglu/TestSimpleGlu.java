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
    ETestObject other_e = inject(e, inj("F1", new FTestObject()), inj("F2", new FTestObject()),
        inj("G", new GTestObject()));
  }


  public static Test suite() {
    return new TestSuite(TestSimpleGlu.class);
  }

  public static void main (String[] args) {
    junit.textui.TestRunner.run(suite());
  }

}
