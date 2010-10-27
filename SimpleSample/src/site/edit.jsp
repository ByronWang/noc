<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>

<%@page import="java.io.FileWriter"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<style type="text/css">
th {
	background: #a5e3c4;
	padding: 3px;
}

td th {
	background: #6495ed;
	padding: 2px;
}

td {
	background: #f0f0f0;
	padding: 3px;
}

td td {
	background: #e0e8e0;
	padding: 2px;
}

caption {
	background: yellow;
}
</style>

</head>
<body>
<!-- 
	Map<String,O> types = (Map<String,O>)application.getAttribute("types");
	Configuration cfg = (Configuration)application.getAttribute("cfg");
	Configuration typecfg = (Configuration)application.getAttribute("typecfg");
		
	String typename = (String)request.getParameter("_typename");
    String mode = (String)request.getParameter("_mode");
    String name = (String)request.getParameter("_name");
	
	O type = types.get(typename);
	DataStore datastore = (DataStore)application.getAttribute("datastore");

	Template typetemp = typecfg.getTemplate((String)request.getParameter("_template") + ".ftl");

    FileWriter fw = new FileWriter("D:\\JavaDev\\AutoPage\\WebContent\\template" + "\\" + typename + ".ftl");

    Map<String,Object> root = new HashMap<String,Object>();
    root.put("type",types.get(typename));
    root.put("types",application.getAttribute("types"));
    typetemp.process(root, fw);
    fw.close();
    

    Template temp = cfg.getTemplate(typename + ".ftl");
    if("edit".equals(mode)){
        root.putAll((ImpO)datastore.get(typename).get(name));  
        root.put("_mode",mode);       	
    }else if("addnew".equals(mode)) {
        root.put("_mode",mode);    	
    }
    
    temp.process(root, out);
    out.flush();

   %> -->

</body>
</html>
