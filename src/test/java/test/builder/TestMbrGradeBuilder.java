package test.builder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TestName;
import org.kie.api.runtime.KieSession;

import lombok.extern.slf4j.Slf4j;
import rules.mbrgrade.builder.MbrGradeDrlBuilder;
import rules.mbrgrade.model.Mbr;
import rules.mbrgrade.model.MbrBehavior;
import rules.mbrgrade.model.MbrGradeRule;
import test.help.TestHelper;

@Slf4j
public class TestMbrGradeBuilder {

  @org.junit.Rule
  public TestName name = new TestName();

  @Before
  public void before() {
    System.out.println(">>> " + getClass().getName() + ":" + name.getMethodName());
  }

  @Test
  public void testcase1() throws IOException {
    String path = "/test.builder/testcase1";
    List<MbrGradeRule> rules = TestHelper.readValues(path, "MbrGradeRules.yml", MbrGradeRule.class);
    List<Mbr> mbrs = TestHelper.readValues(path, "Mbrs.yml", Mbr.class);
    List<MbrBehavior> mbrBehaviors = TestHelper.readValues(path, "MbrBehaviors.yml", MbrBehavior.class);

    KieSession ks = TestHelper.buildSession(new MbrGradeDrlBuilder(), rules.toArray(new MbrGradeRule[0]));
    // ks.addEventListener(new DebugAgendaEventListener());

    for (Mbr mbr : mbrs)
      ks.insert(mbr);

    for (MbrBehavior mbrBehavior : mbrBehaviors)
      ks.insert(mbrBehavior);

    log.info("Before:\n\t{}",
        new ArrayList<Mbr>(mbrs).stream().map(e -> e.toString()).collect(Collectors.joining("\n\t")));
    ks.fireAllRules();
    log.info("Before:\n\t{}",
        new ArrayList<Mbr>(mbrs).stream().map(e -> e.toString()).collect(Collectors.joining("\n\t")));

    Assert.assertEquals("rate4", mbrs.get(0).getGrade());
  }

}
