package rules.common;

import java.io.IOException;

public interface DrlBuilder {
  DrlFile buildDrlFile(RuleModel ruleModel) throws IOException;
}
