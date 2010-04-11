package data.master.product;
import noc.annotation.DisplayName;
import noc.lang.Attr;
import noc.lang.Code;
import noc.lang.Literal;
import noc.lang.Status;

/**
 * 商品形态
 * @author wangshilian
 *
 */
@DisplayName("商品形态")
public class ProductForm {
	Code 代码;
	Literal 名称;
	Attr 类型;
	Attr 基本单位;
	
	Status 状态;
}
