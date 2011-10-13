package hu.sztaki.ilab.minglu;

import java.util.List;

class CTestObject {
  private List<String> flow;

  @Inject("D")
  public DTestObject d;

  public CTestObject(List<String> flow) {
    this.flow = flow;
  }

  public void init() {
    flow.add("c_init");
  }
}
