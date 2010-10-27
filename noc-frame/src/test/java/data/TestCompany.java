package data;

import noc.annotation.DisplayName;
import noc.lang.List;
import noc.lang.Name;
import noc.lang.Status;

@DisplayName("公司")
public class TestCompany {
	public Name 名称;
	public Name 简称;
	public Status 状态;
	public List<TestCompany> 子公司;	
}
