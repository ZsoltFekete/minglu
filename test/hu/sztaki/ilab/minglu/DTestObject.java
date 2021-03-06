package hu.sztaki.ilab.minglu;

import java.util.List;

class DTestObject {
  private List<String> flow;

  @Inject("C1")
  public CTestObject c1;

  public CTestObject c2;

  public DTestObject(List<String> flow) {
    this.flow = flow;
  }

  public void setDependencies(GluContainer glu) {
    flow.add("d_set_dep");
    c2 = (CTestObject)glu.get("C2");
  }

  public void init() {
    flow.add("d_init");
  }
}
