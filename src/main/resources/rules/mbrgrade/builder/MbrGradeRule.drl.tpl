package mbrgrade
import rules.mbrgrade.model.*;
rule "${name}" 
salience ${salience}
when 
	$mbr: Mbr(grade=="${oldGrade}", $mbrId:id) 
	MbrBehavior(mbrId==$mbrId, ${conditionName}["${conditionPeriod}"] >= ${conditionValue}) 
then
	$mbr.setGrade("${newGrade}");
	$mbr.addRuleApplied(drools.getRule().getName());
end
