[#ftl/]
[#include "./tags.ftl" /]

[#macro list field parent="data" readonly=false]
	<table> 
		<thead >
			<tr>
				<th>++</th>
			[#list field.type.fields as inf ]
				<th>${inf.displayName}</th>
			[/#list]
			</tr>
		</thead >
		<tbody>
		<#if ${parent}.${field.name}?? && ${parent}.${field.name}?size &gt; 0>
			<#list ${parent}.${field.name} as item>
			<tr>
				<td>${r"${item_index+1}"}</td>
				[#list field.type.fields as f ][#t]
				<td><#compress>
					[#if !f.array]
						[#switch f.refer]
							[#case "Scala"]
								[@input field=f parent="item" /]
								[#break]
							[#case "Inline"]
								[#break]
							[#case "Reference"]
							[#case "Cascade"]
								<#if item.${f.name}??>
									[#list f.type.fields as rF][#if rF.key]${r"${item." + f.name + "." + rF.name + "}"}[/#if][/#list]
								<#else>
									Null
								</#if>
								[#break]
						[/#switch]
					[#else]
						Array
					[/#if]
					</#compress></td>
				[/#list]
			</tr>
			</#list> 
		<#else>
			<tr><td colspan="${field.type.fields?size + 1}">No Record</td></tr>[#t]
		</#if>
		</tbody>
	</table>
[/#macro]


[#macro object type parent="data"]
<ol>[#list type.fields as f]
	<li>[#if f.array]
			[#switch f.refer]
				[#case "Scala"]			
					[@label field=f /] @TODO List ${f.name} : ${f.type.displayName}		
					[#break]
				[#case "Inline"]
					[@label field=f/]		
					[@list field=f /]
					[#break]
				[#case "Reference"]
				[#case "Cascade"]
		 			[@label field=f /]
					[@list field=f /]
					[#break]
			[/#switch]
		[#else]
			[#switch f.refer]
				[#case "Scala"]
					[@label field=f /]
					[#if f.key][@readonlyinput field=f /][#else][@input field=f /][/#if]
					[#break]
				[#case "Inline"]
					[@label field=f /]
					[@object type=f.type parent="data." + f.name /]
					[#break]
				[#case "Reference"]
				[#case "Cascade"]	<#compress>
					[#list f.type.fields as rF]
						[#if rF.key]
							[#assign subField=rF/]
						[/#if]
					[/#list]
										
					[@label field=f /]
					[@input field=subField parent="data.${f.name}"/]
					
					<span class="refType" onclick='selectItem(this,"${f.name}_${subField.name}","${contextPath}/${f.type.name?replace(".", "/")}/?popup");'>::</span>
					[@typeinfo tp=f.type /]
					</fieldset>
					</#compress>
					[#break]			
			[/#switch]	
		[/#if]
	</li>[/#list][#lt]
</ol>
[/#macro]


[@body title=type.displayName]
<form name="form1" method="POST" action="${contextPath}${r"${urlPath!}"}/${type.name?replace(".", "/")}/${r"${data.indentify!}"}" title="Hello Title">
	[@object type=type parent="data" /]	
	[@submit/]
</form>

<div class="action">
[@backToList type=type/]
</div >
[/@body]
