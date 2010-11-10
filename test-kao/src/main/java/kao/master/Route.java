package kao.master;

import noc.annotation.DisplayName;
import noc.lang.Attr;
import noc.lang.Code;
import noc.lang.Name;
import noc.lang.NumberValue;

@DisplayName("路线")
public class Route {
    Code 代码;
    Name 名称;
    Attr 省;
    Attr 市;
    NumberValue 延迟天数;
}
