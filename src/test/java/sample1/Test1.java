package sample1;

import java.io.IOException;
import java.io.InputStream;

import org.drools.core.io.impl.ByteArrayResource;
import org.drools.core.util.IoUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TestName;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.builder.ResultSeverity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test1 {
  @org.junit.Rule
  public TestName name = new TestName();

  @Before
  public void before() {
    System.out.println(">>> " + getClass().getName() + ":" + name.getMethodName());
  }

  private KieSession createKieSession(String... classPathResourceNames) throws IOException {
    KnowledgeBuilder kb = KnowledgeBuilderFactory.newKnowledgeBuilder();
    for (String resourceName : classPathResourceNames) {
      InputStream ins = getClass().getResourceAsStream(resourceName);
      byte[] bytes = IoUtils.readBytesFromInputStream(ins, true);
      ByteArrayResource resource = new ByteArrayResource(bytes, "UTF-8");
      kb.add(resource, ResourceType.DRL);
    }
    if (kb.hasErrors()) {
      log.error("{}", kb.getResults(ResultSeverity.ERROR));
      Assert.fail("See logs for error");
    }
    return kb.newKieBase().newKieSession();
  }

  @Test
  public void testcase1() throws IOException {
    KieSession ks = createKieSession("/sample1/Test1.drl");
    ks.setGlobal("log", log);
    OrderLine[] lines = new OrderLine[]{
        OrderLine.builder()
        .lineNo(1)
        .productId("A")
        .qty(10)
        .build(),
        OrderLine.builder()
        .lineNo(2)
        .productId("B")
        .qty(12)
        .build()
    };
    for (OrderLine line: lines) ks.insert(line);
    
    log.info("Before:");  for (OrderLine line: lines) log.info("\t{}", line);
    ks.fireAllRules();
    log.info("After:");  for (OrderLine line: lines) log.info("\t{}", line);
    
    ks.dispose();
  }
}
