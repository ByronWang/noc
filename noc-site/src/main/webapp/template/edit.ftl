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
									[#list f.type.fields as rF][#if rF.importance == "PrimaryKey"]${r"${item." + f.name + "." + rF.name + "!}"}[/#if][/#list]
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
					[#if f.importance == "PrimaryKey"]<#if data.${f.name}?? && data.${f.name}?length gt 0>[@readonlyinput field=f /]<#else>[@input field=f /]</#if>[#else][@input field=f /][/#if]
					[#break]
				[#case "Inline"]
					[@label field=f /]
					[@object type=f.type parent="data." + f.name /]
					[#break]
				[#case "Reference"]
				[#case "Cascade"]	<#compress>
					[#list f.type.fields as rF]
						[#if rF.importance == "PrimaryKey"]
							[#assign valueField=rF/]
						[/#if]
						[#if rF.name == "name" || rF.name == "名称"]
							[#assign showField=rF/]
						[/#if]
						
					[/#list]										
					[@label field=f /]
					[@refInput field=f valueField=valueField  showField=showField/]					
					</#compress>
					[#break]			
			[/#switch]	
		[/#if]
	</li>[/#list][#lt]
</ol>
[/#macro]

[#macro refInput field valueField showField parent="data"]<#compress>
		<#if ${parent}.${field.name}??>
			<#assign value=${parent}.${field.name}.${valueField.name}/>
		<#elseif ${parent}.${field.name}_${valueField.name}??>
			<#assign value=${parent}.${field.name}_${valueField.name}/>	
		<#else>
			<#assign value=""/>	
		</#if>
		<#if ${parent}.${field.name}??>
			<#assign show=${parent}.${field.name}.${showField.name}/>
		<#elseif ${parent}.${field.name}_${showField.name}??>
			<#assign show=${parent}.${field.name}_${showField.name}/>	
		<#else>
			<#assign show=""/>	
		</#if>
		
		[#if valueField.name == "code" || valueField.name == "代码"]
		[/#if]
		<input name="${field.name}_${valueField.name}" type="hidden" id="${field.name}_${valueField.name}" value="${r"${value!}"}"     title="${parent}.${field.name}_${valueField.name}"/>
		<input name="${field.name}_${showField.name}" id="${field.name}_${showField.name}" value="${r"${show!}"}"     title="${parent}.${field.name}_${showField.name}"/>
		<span class="refType" onclick='selectItem(this,"${field.name}","${valueField.name}","${showField.name}","${contextPath}/basic/${field.type.name?replace(".", "/")}/?popup");'>::</span>
		[@typeinfo tp=field.type /]
</#compress>[/#macro]

[@body title=type.displayName]
<form name="form1" method="POST" action="${contextPath}/basic/${type.name?replace(".", "/")}/${r"${data.indentify!}"}" title="Hello Title">
	[@object type=type parent="data" /]	
	[@submit/]
</form>

<div class="action">
[@backToList type=type/]
</div >
[/@body]
