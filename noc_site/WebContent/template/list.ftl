[#ftl/]
[#macro typeinfo tp]
	[#if tp.name?matches("noc.lang[.].*")]
   	&lt; ${tp.name?replace("noc.lang.", "")}
	[#else]
		&lt; <a href="${contextPath}${r"${urlPath!}"}/${tp.name?replace(".", "/")}/">${tp.name}<a>
	[/#if]
[/#macro]


[#macro body title]
<html><head>
<link rel='stylesheet' href='/noc/css/form.css' type='text/css'/>
<title>${title}</title>
</head>
<body>
<h1>${title}</h1>
[#nested/]
</body></html>
[/#macro]

[@body title=type.displayName]
		
<a style="float:right;" href="/noc/noc/lang/reflect/Type/${type.name}">Type</a>

<table class="list"> 
	<thead>	
		<tr>
			[#list type.fields as inf ][#t]
			[#if inf.array][#t]
			[#elseif inf.type.scala][#t]
			<th scope="col"> ${inf.displayName} </th>
			[#elseif inf.inline][#t]
			[#elseif inf.refer][#if inf.type.keyField??][#t]
			<th scope="col"> ${inf.displayName}.${inf.type.keyField.displayName} </th>
			[/#if][/#if][#t]
			[/#list][#t]
		</tr>
	</thead>
	
	
	<tbody>
		<#list list?sort_by("${type.keyField.name}") as data>${r"<#" + "t>"}
		<tr>
		[#list type.fields as inf ][#t]
			[#if inf.array][#t]
			[#elseif inf.type.scala][#t]
			<td> [#rt]
				[#if inf.name == type.keyField.name][#t]
				<a href="${r"${data."+ inf.name + "}"}">${r"${data."+ inf.name + "}"}</a>[#t]
				[#else][#t]
					[#if inf.type.name == "noc.lang.Bool"][#t]
			<#if data.${inf.name}>True<#else>false</#if>[#t]
					[#else][#t]
				${r"${data."+ inf.name + "}"}[#t]
					[/#if][#t]
				[/#if][#t]
			</td>[#lt]
			[#elseif inf.inline][#t]
			[#elseif inf.refer][#t]
			<td> [#rt]
				[#if inf.type.keyField??][#t]  
					[#assign  fieldName = inf.name + "_" + inf.type.keyField.name /][#t]			
					[#if inf.type.keyField.name == "noc.lang.Bool"][#t]
				<#if data.${fieldName}>True<#else>false</#if>[#t]
					[#else][#t]
				${r"${data."+ fieldName + "}"}[#t]
					[/#if][#t]
				[/#if][#t]
			</td>[#lt]
			[/#if][#t]
		[/#list][#t]
		</tr>   
	</#list>	
	</tbody>
</table>

<br/>

<div class="action">
<a href="${contextPath}/${type.name?replace(".","/")}/?new">Add New<a>
</div>
[/@body]