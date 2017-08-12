package rules.mbrgrade.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import rules.common.RulesApplied;

@Data
@EqualsAndHashCode(callSuper=false)
@ToString(callSuper=true)
public class Mbr extends RulesApplied {
  private String id;
  private String grade;
}
