package test.drl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.TestName;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.extern.slf4j.Slf4j;
import rules.mbrgrade.model.Mbr;
import rules.mbrgrade.model.MbrBehavior;
import test.help.TestHelper;

@Slf4j
public class TestMbrGradeDrl {

  static KieContainer kc;
  KieSession ks;

  @org.junit.Rule
  public TestName name = new TestName();

  @BeforeClass
  public static void beforeClass() {
    System.setProperty("drools.dateformat", "yyyy-MM-dd");
    kc = KieServices.Factory.get().newKieClasspathContainer();
  }

  @Before
  public void before() {
    System.out.println(">>> " + getClass().getName() + ":" + name.getMethodName());
    ks = kc.newKieSession();
  }

  @After
  public void after() {
    ks.dispose();
  }

  @Test
  public void testcase1() throws JsonParseException, JsonMappingException, IOException {
    String path = "/test.drl/testcase1";
    List<Mbr> mbrs = TestHelper.readValues(path, "Mbrs.yml", Mbr.class);
    List<MbrBehavior> behaviors = TestHelper.readValues(path, "MbrBehaviors.yml", MbrBehavior.class);
    for (Mbr mbr : mbrs)
      ks.insert(mbr);
    for (MbrBehavior behavior : behaviors)
      ks.insert(behavior);
    log.info("Before:\n\t{}",
        new ArrayList<Mbr>(mbrs).stream().map(e -> e.toString()).collect(Collectors.joining("\n\t")));
    ks.fireAllRules();
    log.info("Before:\n\t{}",
        new ArrayList<Mbr>(mbrs).stream().map(e -> e.toString()).collect(Collectors.joining("\n\t")));
  }

}