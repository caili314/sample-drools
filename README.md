名词定义和代码结构
----------------

- 一类规则。例如会员等级类规则。表达为一个包，有两个子包model和build。例如

	rules.mbrgrade.model
	rules.mbrgrade.build
	
- 一种规则。例如会员升级规则，会员降级规则。例如
	
	rules.mbrgrade.model.MbrGradeRule.java 会员升级规则的存储模型
	rules.mbrgrade.build.MbrGradeRule.drl.tpl drl代码模板，src/main/resources/rules/mbrgrade/build/MbrGradeRule.drl.tpl
	rules.mbrgrade.build.MbrGradeRuleBuilder.java 规则翻译器，将规则存储模型+规则drl代码模板转换成drl表达。

	rules.mbrgrade.model.MbrGradeDownRule.java 会员降级规则的存储模型
	rules.mbrgrade.build.MbrGradeDownRule.drl.tpl drl代码模板，作为resources
	rules.mbrgrade.build.MbrGradeDownRuleBuilder.java 规则翻译器，将规则存储模型+规则drl代码模板转换成drl表达。
	
- 一条规则。例如从1级升级到2级的规则，从2级升级到3级的规则。表达为一个对象实例。例如

	rule = new MbrGraderule();
	rule2 = new MbrGraderule();
	
- 数据对象模型。drl中用到的数据对象。代码存放在model包中。例如

	rules.mbrgrade.model.Mbr
	rules.mbrgrade.model.MbrBehavior


编制drl规则和测试
----------------

1. 创建数据对象模型
1. 在src/test/resources/META-INF中创建文件kmodule.xml
1. 在src/test/resources/test.drl/中创建MbrGrade.drl
1. 在src/test/java中创建test.drl.TestMbrGradeDrl.java
1. 执行测试TestMbrGradDrl

编制规则翻译器，模板和测试
----------------

1. 在src/main/java中创建规则存储模型MbrGradeRule.java
1. 在src/main/resources中，根据上一步的drl文件创建drl模板 MbrGradeRule.drl.tpl
1. 在src/main/java中创建规则翻译器MbrGradeRuleBuilder.java
1. 在src/test/java中创建test.builder.TestMbrGradeBuilder.java
1. 在src/test/resources/test.builder/目录中创建测试用例数据testcase1/Mbr.yml, MbrBehavior.yml, MbrGradeRule.yml
1. 执行测试TestMbrGradeBuilder
