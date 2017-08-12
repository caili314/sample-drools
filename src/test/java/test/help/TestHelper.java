package test.help;

import java.io.IOException;
import java.util.List;

import org.drools.compiler.kie.builder.impl.MemoryKieModule;
import org.junit.Assert;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Results;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import lombok.extern.slf4j.Slf4j;
import rules.common.DrlBuilder;
import rules.common.DrlFile;
import rules.common.RuleModel;

@Slf4j
public class TestHelper {
  public static KieContainer buildContainer(DrlBuilder drlBuilder, RuleModel... ruleModels) throws IOException {
    KieServices ks = KieServices.Factory.get();

    KieFileSystem kfs = ks.newKieFileSystem();
    int i = 0;
    for (RuleModel ruleModel : ruleModels) {
      DrlFile drlFile = drlBuilder.buildDrlFile(ruleModel);
      String path = "src/main/resources/" + (i++) + ".drl";
      kfs.write(path, drlFile.getContent().getBytes("UTF-8"));
      log.info("DrlFile added: {}\n{}", path, drlFile.getContent());
    }

    KieBuilder kb = ks.newKieBuilder(kfs).buildAll();
    Results results = kb.getResults();
    Assert.assertEquals(0, results.getMessages().size());
    log.info("FileNames: {}", ((MemoryKieModule) kb.getKieModule()).getFileNames());

    KieContainer kc = ks.newKieContainer(kb.getKieModule().getReleaseId());
    log.info("KieBaseNames = {}", kc.getKieBaseNames());
    log.info("KiePackages = {}", kc.getKieBase().getKiePackages());
    return kc;
  }

  public static KieSession buildSession(DrlBuilder drlBuilder, RuleModel... ruleModels) throws IOException {
    KieContainer kc = buildContainer(drlBuilder, ruleModels);
    KieSession ks = kc.newKieSession();
    return ks;
  }

  public static <T> List<T> readValues(String path, String name, Class<T> clazz) throws IOException {
    MappingIterator<T> iter = new ObjectMapper(new YAMLFactory())
        .readerFor(clazz)
        .readValues(clazz.getResourceAsStream(path + "/" + name));
    return iter.readAll();
  }

}
