package rules.common;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class RulesApplied {
  private List<String> rulesApplied;

  public void addRuleApplied(String name) {
    if (rulesApplied == null)
      rulesApplied = new ArrayList<>();
    rulesApplied.add(name);
  }
}
