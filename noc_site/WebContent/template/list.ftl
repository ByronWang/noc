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
<a style="float:right;" href="/noc/noc/lang/reflect/Type/${type.name}">Type</a>

<h1>${title}</h1>
[#nested/]
</body></html>
[/#macro]

[@body title=type.displayName]
		

<table class="list"> 
	<thead>	
		<tr>
			[#list type.fields as f ]
				[#switch f.refer]
					[#case "Scala"]
			<th scope="col">${f.displayName}</th>
						[#break]
					[#case "Inline"]
						[#break]
					[#case "Reference"]
					[#case "Cascade"]
			<th scope="col">${f.displayName}</th>
						[#break]
				[/#switch]
			[/#list][#t]
		</tr>
	</thead>
	
	
	<tbody>
		<#list data as item><#lt>
		<tr>
		[#list type.fields as f ]
			<td>[#compress]
			[#if f.array]
			[#else]			
				[#switch f.refer]
					[#case "Scala"]		
						[#if f.key]
							<a href="${r"${item.indentify}"}">${r"${item."+ f.name + "}"}</a>
						[#else]
							[#if f.type.name == "noc.lang.Bool"]
								<#if item.${f.name}>True<#else>false</#if>
							[#else]
								${r"${item."+ f.name + "}"}
							[/#if]
						[/#if]			
						[#break]
					[#case "Inline"]
						[#break]
					[#case "Reference"]
					[#case "Cascade"]
						[#list f.type.fields as rF]
							[#if rF.key]
								[#assign subField=rF/]
							[/#if]
						[/#list]		
						
					[@refview field=f keyField=subField parent="item"/]		
					
						[#break]
				[/#switch]			
			[/#if]					
			[/#compress]</td>
		[/#list]
		</tr>   
	</#list>	
	</tbody>
</table>

<br/>

[#macro refview field keyField parent="data"]<#compress>
		<#if ${parent}.${field.name}_${keyField.name}??>
			<#assign value=${parent}.${field.name}_${keyField.name}/>
		<#elseif ${parent}.${field.name}??>
			<#assign value=${parent}.${field.name}.${keyField.name}/>	
		<#else>
			ERROR			
		</#if>		
		${r"${value!}"}
</#compress>[/#macro]

<div class="action">
<a href="${contextPath}/${type.name?replace(".","/")}/?new">Add New<a>
</div>
[/@body]