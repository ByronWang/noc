package data.master.product;
import noc.annotation.DisplayName;
import noc.lang.Code;
import noc.lang.Literal;

/**
 * 品种
 * @author wangshilian
 *
 */
@DisplayName("品种")
public class ProductType {
	Code 代码;
	Literal 名称;
	ProductType 上级;
}
