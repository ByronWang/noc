package data;

import noc.annotation.DisplayName;
import noc.lang.List;
import noc.lang.Name;
import noc.lang.Status;

@DisplayName("公司")
public class TestCompany {
	Name 名称;
	Name 简称;
	Status 状态;
	List<TestCompany> 子公司;	
}
