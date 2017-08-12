package rules.mbrgrade.model;

import java.util.Map;

import lombok.Data;

@Data
public class MbrBehavior {
  private String mbrId;
  private Map<String, Double> consumeTotal; // 消费金额 key=MbrGradeRule.conditionPeriod
  private Map<String, Integer> consumeCount; // 消费次数 key=MbrGradeRule.conditionPeriod
}
