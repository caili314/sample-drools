package rules.mbrgrade.builder;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.text.StrSubstitutor;
import org.drools.core.util.IoUtils;

import rules.common.DrlBuilder;
import rules.common.DrlFile;
import rules.common.RuleModel;
import rules.mbrgrade.model.MbrGradeRule;

public class MbrGradeDrlBuilder implements DrlBuilder {
  /**
   * 将规则的业务表达翻译成drl文件内容
   * 
   * @param bo
   * @return
   * @throws IOException
   */
  public DrlFile buildDrlFile(RuleModel ruleModel) throws IOException {
    if (!(ruleModel instanceof MbrGradeRule))
      throw new IOException("No builder for " + ruleModel.getClass().getName());

    MbrGradeRule rule = (MbrGradeRule) ruleModel;

    String res = "/" + this.getClass().getPackage().getName().replaceAll("\\.", "/")
        + "/" + MbrGradeRule.class.getSimpleName()
        + ".drl.tpl";
    InputStream ins = getClass().getResourceAsStream(res);
    String tpl = new String(IoUtils.readBytesFromInputStream(ins), "UTF-8");
    ins.close();

    String name = rule.getName();

    Map<String, String> replacements = new HashMap<>();
    replacements.put("salience", Integer.toString(rule.getSalience()));
    replacements.put("name", name);
    replacements.put("oldGrade", rule.getOldGrade());
    replacements.put("newGrade", rule.getNewGrade());
    if (MbrGradeRule.BASE_TOTAL.equals(rule.getConditionName())) {
      replacements.put("conditionName", "consumeTotal");
    }
    if (MbrGradeRule.BASE_COUNT.equals(rule.getConditionName())) {
      replacements.put("conditionName", "consumeCount");
    }
    replacements.put("conditionPeriod", rule.getConditionPeriod());
    replacements.put("conditionValue", Double.toString(rule.getConditionValue()));
    String content = new StrSubstitutor(replacements).replace(tpl);
    if (content.contains("${"))
      throw new IOException("存在未替换的变量(${})");

    DrlFile drlFile = new DrlFile();
    drlFile.setName(name);
    drlFile.setContent(content);
    return drlFile;
  }
}
