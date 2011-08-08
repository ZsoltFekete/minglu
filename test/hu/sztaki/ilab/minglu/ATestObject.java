package hu.sztaki.ilab.minglu;

import java.util.List;

class ATestObject {
  private List<String> flow;
  private BTestObject b;

  public ATestObject(List<String> flow) {
    this.flow = flow;
  }

  public void setDependencies(GluContainer glu) {
    flow.add("a_set_dep");
    b = (BTestObject)glu.get("B");
  }
  public void init() {
    flow.add("a_init");
  }
}
