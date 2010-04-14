[#ftl/]



[#macro link typename field parent="data"]
<a href="${contextPath}${r"${urlPath!}"}/${typename?replace(".", "/")}/${r"${" + parent + "."+ field.name + "!}"}">${r"${" + parent + "."+ field.name + "!}"}<a>
[/#macro]


[#macro typeinfo tp][#if tp.scala]<!--$ {tp.name?replace("noc.lang.", "")}-->[#else]<a href="${contextPath}${r"${urlPath!}"}/${tp.name?replace(".", "/")}/">${tp.name}<a>[/#if][/#macro]

[#macro input         field parent="data"][#if field.type.name == "noc.lang.Bool"]<#if ${parent}.${field.name}><input type="checkbox" name="${field.name}" checked         /><#else><input type="checkbox" name="${field.name}" title="${field.type.name}"/> </#if>[#else]<input name="${field.name}" value="${r"${" + parent + "."+ field.name + "!}"}"          title="${field.type.name}"/>[/#if][/#macro]
[#macro readonlyinput field parent="data"][#if field.type.name == "noc.lang.Bool"]<#if ${parent}.${field.name}><input type="checkbox" name="${field.name}" checked         /><#else><input type="checkbox" name="${field.name}" title="${field.type.name}"/> </#if>[#else]<input name="${field.name}" value="${r"${" + parent + "."+ field.name + "!}"}"          title="${field.type.name}"/>[/#if][/#macro]
[#macro view          field parent="data"][#if field.type.name == "noc.lang.Bool"]<#if ${parent}.${field.name}>True<#else>False</#if>[#else]${r"${" + parent + "."+ field.name + "!}"}[/#if][/#macro]


[#macro label field]<label>${field.name}</label>[/#macro]
[#macro submit]<input type="submit" class="submit" name="Submit" value="submit"/>[/#macro]
[#macro backToList type]<a href="${contextPath}${r"${urlPath!}"}/${type.name?replace(".", "/")}/">Return to list</a>[/#macro]

[#macro list field parent="data" readonly=false]
    <table>   
        <thead>
            <tr>
            	<th>++</th>
            [#list field.type.fields as inf ]
            	<th>${inf.displayName}</th>
            [/#list]
            </tr>
        </thead>
        <tbody>
        <#if ${parent}.${field.name}?? && ${parent}.${field.name}?size &gt; 0>
        	<#list ${parent}.${field.name} as item>
            <tr>
            	<td>${r"${item_index+1}"}</td>
            	[#list field.type.fields as f ][#t]
                	[#if f.array && f.inline][#t]
                	[#elseif f.array][#t]
                	[#elseif f.type.scala][#t]
				<td>[@input field=f parent="item" /]</td>
					[#elseif f.inline][#t]
       				[#elseif f.refer][#t]
                    	<#if item.${f.name}??>[#t]
				<td>[#if f.type.primaryKeyField?? ]${r"${item." + f.name + "." + f.type.primaryKeyField.name + "}"}[#else]ERROR[NO KeyField][/#if] <!--[link typename=f.type.name field=f.type.keyField parent="item."+f.name /]--></td>
                    	<#else>[#t]
				<td>refer</td>
						</#if>[#t]
			     	[#else][#t]
				<td>ERROR</td>
		          	[/#if][#t]
            	[/#list][#t]
            </tr></#list>   
        <#else>
            <tr><td colspan="${field.type.fields?size + 1}">No Record</td></tr>[#t]
        </#if>
        </tbody>  
    </table>
[/#macro]


[#macro object type parent="data"]
<ol>[#list type.fields as f]
	<li>[#if f.array]
			[#if f.inline]   
				[@label field=f/]		
				[@list  field=f /]
			[#elseif f.array]
		 		[@label field=f /]
				[@list  field=f /]
			[#elseif f.type.scala]
				[@label field=f /] @TODO  List ${f.name} : ${f.type.displayName}
			[#else]ERROR[#t]			
			[/#if]
		[#else]
			[#if f.type.scala]
				[@label field=f /][#if f.primaryKey][@readonlyinput field=f /][#else][@input field=f /][/#if]
			[#elseif f.inline ]
				[@label field=f /]
				[@object type=f.type parent="data." + f.name /]
			[#elseif f.refer]
				[@label field=f /] [#t]
					[#if f.type.keyField??][#t]
					[#assign  fieldName = f.name + "_" + f.type.keyField.name /][#t]
					[#else][#t]
					[#assign  fieldName = f.name + "_" + "key" /][#t]
					[/#if][#t]			
					<#if data.${fieldName}??>[#t]
						<input name="${fieldName}" id="${fieldName}" value="${r"${data." + fieldName + "!}"}"/>[#t]
					<#else>[#t]
						<input name="${fieldName}" id="${fieldName}"/>[#t]
					</#if> [#t]
					<span class="refType" onclick='selectItem(this,"${fieldName}","${contextPath}/${f.type.name?replace(".", "/")}/?popup");'>::</span>[@typeinfo tp=f.type /][#t]
			[#else]ERROR[#t]			
			[/#if]
		[/#if]
	</li>[/#list][#lt]
</ol>
[/#macro]


[@body title=type.displayName]
<a style="float:right;position:absolute;right:0px;top:0px;" href="/noc/noc/lang/reflect/Type/${type.name}">Type</a>

<form name="form1" method="${r"${_method!}"}" action="${r"${_action!}"}" title="Hello Title">
	[@object type=type parent="data" /]	
	[@submit/]
</form>

<div class="action">
[@backToList type=type/]
</div >
[/@body]



[#macro body title]
<html><head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<link rel='stylesheet' href='/noc/css/form.css' type='text/css'/>
	<link rel='stylesheet' href='/noc/css/form.css' type='text/css'/>
	<script type="text/javascript"  src="/noc/js/prototype.js"/>
	<script type="text/javascript"  src="/noc/js/popup.js" />
	
	<title>${title}</title>
</head>
<body>
<h1>${title}</h1>
[#nested/]
</body></html>
[/#macro]