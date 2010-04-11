package data.master;
import noc.annotation.DisplayName;
import noc.lang.Attr;
import noc.lang.Code;
import noc.lang.Day;
import noc.lang.Desc;
import noc.lang.Literal;
import noc.lang.Percent;
import noc.lang.Status;

@DisplayName("付款规则")
public class PaymentRule {
	Code 代码;
	Literal 名称;
	Attr 起算日类型;
	Day 起算偏移天数;
	Desc 备注;
	Day 付款天数;
	Percent 付款百分比;
	Desc 描述;
	Status 状态;	
}
