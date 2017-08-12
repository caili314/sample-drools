package rules.mbrgrade.model;

import lombok.Data;
import rules.common.RuleModel;

/**
 * 对于当前等级为oldGrade的mbr， 如果在过去的conditionPeriod时间段内，
 * 消费额或消费次数(conditionName)大于等于conditionValue， 则升级为newGrade
 * 
 * @author caili
 *
 */
@Data
public class MbrGradeRule implements RuleModel {
  private int salience;
  
  private String name;
  private String oldGrade;
  private String newGrade;
  
  private String conditionPeriod; // 取值30,60,90,180日
  public static final String DAYS_30 = "30";
  public static final String DAYS_60="60";
  public static final String DAYS_90="90";
  public static final String DAYS_180="190";
  
  private String conditionName; // 取值消费额, 消费次数
  public static final String BASE_TOTAL = "消费额";
  public static final String BASE_COUNT = "消费次数";
  
  private double conditionValue;
}
