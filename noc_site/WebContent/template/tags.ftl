[#ftl/]

[#macro link typename field parent="data"]
<a href="${contextPath}${r"${urlPath!}"}/type/${typename?replace(".", "/")}/${r"${" + parent + "."+ field.name + "!}"}">${r"${" + parent + "."+ field.name + "!}"}<a>
[/#macro]
[#macro typeinfo tp][#if tp.master == "Scala"]<!--$ {tp.name?replace("noc.lang.", "")}-->[#else]<a href="${contextPath}${r"${urlPath!}"}/basic/${tp.name?replace(".", "/")}/">${tp.displayName}</a>[/#if][/#macro]



[#macro label field]<label>${field.name}</label>[/#macro]

[#macro input field parent="data"]<#compress>
		<#if ${parent}??>
			<#assign value=${parent}.${field.name}/>
		<#else>
			<#assign value=""/>			
		</#if>
	[#if field.type.name == "noc.lang.Bool"]
		<#if value>
			<input type="checkbox" name="${field.name}" checked         />
		<#else>
			<input type="checkbox" name="${field.name}" title="${field.type.name}"/> 
		</#if>
	[#else]
		<input name="${field.name}" value="${r"${value!}"}"     title="${field.type.name}"/>
	[/#if]
	</#compress>
[/#macro]
[#macro readonlyinput field parent="data"]<#compress>
	[#if field.type.name == "noc.lang.Bool"]
		<#if ${parent}.${field.name}>
			<input type="checkbox" name="${field.name}" checked />
		<#else>
			<input type="checkbox" name="${field.name}" title="${field.type.name}"/>
		</#if>
	[#else]
		<input name="${field.name}" value="${r"${" + parent + "."+ field.name + "!}"}" readonly         title="${field.type.name}"/>
	[/#if]
	</#compress>
[/#macro]
[#macro view field parent="data"]<#compress>
	[#if field.type.name == "noc.lang.Bool"]
		<#if ${parent}.${field.name}>True<#else>False</#if>
	[#else]
		${r"${" + parent + "."+ field.name + "!}"}
	[/#if]
	</#compress>
[/#macro]




[#macro submit]<input type="submit" class="submit" name="Submit" value="submit"/>[/#macro]
[#macro backToList type]
	<a href="${contextPath}${r"${urlPath!}"}/${type.name?replace(".", "/")}/">Return to list</a>
[/#macro]


[#macro body title]
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<link rel='stylesheet' href='/noc/css/form.css' type='text/css'/>
	<script type="text/javascript"  src="/noc/js/prototype.js"></script>
	<script type="text/javascript"  src="/noc/js/popup.js"></script>	
	<title>${title}</title>
</head>
<body>
<a style="float:right;position:absolute;right:0px;top:0px;" href="/noc/type/noc/lang/reflect/Type/${type.name}">Type</a>
<a style="float:right;position:absolute;right:0px;top:20px;" href="${contextPath}${r"${urlPath!}"}/debug/${type.name?replace(".", "/")}/${r"${data.indentify!}"}">Template</a>

<h1>${title}</h1>


[#nested/]


</body>
</html>
[/#macro]