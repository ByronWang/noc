package data.master;
import noc.annotation.DisplayName;
import noc.annotation.Inline;
import noc.lang.Attr;
import noc.lang.Code;
import noc.lang.Comment;
import noc.lang.Currency;
import noc.lang.Date;
import noc.lang.Day;
import noc.lang.Desc;
import noc.lang.List;
import noc.lang.Literal;
import noc.lang.Status;
import noc.lang.Width;

@DisplayName("业务伙伴")
public class BusinessPartner {
	Code 代码;
	Attr 供应链角色;
	
	Literal 名称;
	Literal 简称;
	
	Company 默认关系公司;
	Attr 类型;
	Literal 国家;
	Attr 网址;
	Attr 公司电话;
	Attr 传真;
	Attr 所用货币;
	Attr 语言;
	Attr 运输方式;
	Attr 付款方式;
	Attr 付款条件;
	Attr 贸易条款;
	Attr 公司略称编号;

	Status 状态;
	
	Desc 备注;
	Attr EMail;
	
	BusinessRegistrationInformation 工商注册信息;
	
	Region 地区;
	
	@Inline
	List<Address> 地址;
	List<ContactPerson> 联系人;
	
	List<BankInfo> 银行帐号;
	List<RelateInfoPerson> 关联信息个人;
	List<RelateInfoCompany> 关联信息公司;
	
	//Attr 有无银行帐号 = 银行帐号.count().gt(0);
	
	

	class Region{
		Literal 省份;
		Literal 城市;
		Literal 区县;
	}
	
    class BusinessRegistrationInformation{
		Date 成立日期;
		Attr 法人代表;
		Attr 公司类型;
		Attr 总经理;
		Attr 营业范围;
		Currency 注册资金;
	}
    
    public static class Address{
    	Literal 名称;
    	Attr 类别;
    	Literal 国家;
    	Attr 邮政编码;
    	Attr 省;
    	Attr 城市;
    	Attr 区县;
    	Attr 地址;
    	
    	Width 路宽;
    	Attr 叉车;
    	Attr 是否有月台;
    	Attr 可用于收货;
    	Attr 状态;
    	Comment 备注;    	
    }
    
    public static class ContactPerson{
    	Attr 类型;
    	Literal 名称;
    	Attr 性别;
    	Attr 电话;
    	Attr 传真;
    	Attr EMail;
    	Attr 手机号码;
    	
    	Attr 状态;
    }
    

    class TaxNumber{
    	Literal 登记机关;
    	Code 税务登记号;
    	Date 登记日期;
    }
    
    class BankInfo{
    	Literal 开户行;
    	Code 开户行代码;
    	Code 帐号;
    	Attr 币种;
    	Date 开户日期;
    	Attr 是否基本账户;
    	Status 有效;
    }
    
    
    class RelateInfoPerson{
    	Literal 关系公司名称;
    	Literal 业务伙伴名称;
    	Attr 类型;
    	BusinessArea 业务区域;
    	Attr 业务员;
    	Attr 所属分组;
    	Currency 信用额度;
    	Date 额度生效日期;
    	Date 额度到期日期;
    	Attr 常用币别;
    	Attr 行业;
    	Currency 应收余额;
    	Currency 预收款余额;
    	Day 最长付款延迟天数;
    	Attr 汇率类型;
    	TaxType 税率分组;
    	Status 状态;
    }
    
    class RelateInfoCompany{
    	Literal 关系公司名称;
    	Literal 业务伙伴名称;
    	Attr 类型;
    	BusinessArea 业务区域;
    	Attr 采购员;
    	Attr 所属分组;
    	TaxType 税率分组;
    	Attr 常用币别;
    	Attr 汇率类型;
    	Status 状态;
    }
}
