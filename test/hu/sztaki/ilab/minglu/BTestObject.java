package hu.sztaki.ilab.minglu;

import java.util.List;

class BTestObject {
  private List<String> flow;
  public ATestObject a1;
  public ATestObject a2;

  public BTestObject(List<String> flow) {
    this.flow = flow;
  }

  public void setDependencies(GluContainer glu) {
    flow.add("b_set_dep");
    a1 = (ATestObject)glu.get("A1");
    a2 = (ATestObject)glu.get("A2");
  }

  public void init() {
    flow.add("b_init");
  }
}
