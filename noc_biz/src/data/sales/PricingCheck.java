package data.sales;

import noc.annotation.DisplayName;
import noc.lang.Attr;
import noc.lang.Desc;
import noc.lang.ID;
import noc.lang.List;
import noc.lang.Status;
import data.master.product.ProductForm;

/**
 * 检查过程
 * 
 * @author wangshilian
 * 
 */
@DisplayName("检查过程")
public class PricingCheck {
	ID 检查过程编号;
	Desc 描述;
	Attr 检查点;
	Status 状态;

	List<Item> 卷平价差;

	class Item {
		ProductForm 检查类别;
		Attr 是否检查;
		Attr 检查元素;
		Attr 动作;
		Attr 参数设置;
	}
}
