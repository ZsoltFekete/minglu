package hu.sztaki.ilab.minglu;

import java.util.List;

class CTestObject {
  private List<String> flow;
  private DTestObject d;

  public CTestObject(List<String> flow) {
    this.flow = flow;
  }

  public void setDependencies(GluContainer glu) {
    flow.add("c_set_dep");
    d = (DTestObject)glu.get("D");
  }
  public void init() {
    flow.add("c_init");
  }
}
