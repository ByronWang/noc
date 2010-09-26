[#ftl/]
[#macro typeinfo tp]
    [#if tp.name?matches("noc.lang[.].*")]
       &lt; ${tp.name?replace("noc.lang.", "")}
    [#else]
        &lt; <a href="${contextPath}/basic/${tp.name?replace(".", "/")}/">${tp.name}<a>
    [/#if]
[/#macro]

<html><head>
</head><body class="nav">
<div class="navbox"> 
<ul class="nav">    
	[#list type.fields as rF]
		[#switch rF.importance]
			[#case "PrimaryKey"]
				[#assign primaryKeyField=rF]
				[#break]
		[/#switch]
	[/#list]	
	

    <#list data as item><li>
        <a href="${r"${item.indentify}"}">${r"${item."+ primaryKeyField.name + "}"}</a>
       </li>
    </#list> 
</ul>   
</div>
</body></html>