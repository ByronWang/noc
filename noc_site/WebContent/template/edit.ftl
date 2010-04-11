[#ftl]
[#macro input name value="" type="text" valuelist="*"]
[#if valuelist! = "" || valuelist! = "*"]
<input name="${name}" value="${value}" type="${type}"/>
[#elseif valuelist!?substring(0, 1) = "[" ]
<select name="${name}" value="${value}" type="${type}"/>
 [#list valuelist?eval as v ]
   <option name="${v}" <#if ${name}! = "${v}">selected</#if> >${v}</option>
 [/#list]
</select>
[/#if]
[/#macro]

[#macro submit name value=""]
<input type="submit" name="${name}" value="${value}"/>
[/#macro]

<H1>${type.name}-${r"${name!}"}</H1>

<form action="save.jsp" method="get">
[@input "_template" "edit" "hidden"/]
[@input "_type" "${type.name}" "hidden"/]
[@input "_mode" r"${_mode!}" "hidden"/]
<table>
[#list type.fields as field]
<tr>
  <th>${field.name}</th>
  <td>
	[#switch field.type!"text"]
	  [#case "text"]
            [#if field.primarykey! == "true"]
                 <#if _mode! == "addnew">
                    [@input name=field.name value="" valuelist="${field.valuelist!}"/]
                 <#else>
                    ${r"${" + field.name + "!}"}
                    [@input name=field.name value=r"${" + field.name + "!}"  type="hidden"/]
                 </#if>
            [#else]
                [@input name=field.name value=r"${" + field.name + "!}"  valuelist="${field.valuelist!}"/]
            [/#if]
	    [#break]
	  [#case "list"]
	  	<table>
		  <tr>
		  	[#list types[field.itemtype].fields as sfield]
		  		<th>${sfield.name}</th>
		  	[/#list]
		  </tr>	                  
		  <#if ${field.name}??><#list ${field.name} as item>
	  		<tr>
            [@input name="${field.name}.list._type"          value="${field.itemtype}" type="hidden"/]
	  		[#list types[field.itemtype].fields as sfield]
	  		<td>
	  			[#if sfield.primarykey! == "true"]
	  			    [@input name="${field.name}.list." + sfield.name value=r"${item."+ sfield.name + "!}" type="hidden"/]
	  				${r"${item."+ sfield.name + "!}"}
	  			[#else]
	  				[@input name="${field.name}.list." + sfield.name value=r"${item."+ sfield.name + "!}" valuelist="${sfield.valuelist!}"/]
	  			[/#if]
	  		</td>
	  		[/#list]
	  		</tr>
	  	</#list></#if> 
        <tr>
        [@input name="${field.name}.list._type"          value="${field.itemtype}" type="hidden"/]
                
        [#list types[field.itemtype].fields as sfield]
        <td>
                [@input name="${field.name}.list." + sfield.name value=""/]             
        </td>
        [/#list]
        </tr>
            
	  	</table>
	    [#break]
	  [#default]
        <table>
            <tr>
                [#list types[field.type].fields as sfield]
                    <th>${sfield.name}</th>
                [/#list]
            </tr>   
            <tr>
            [#list types[field.type].fields as sfield]
            <td>
                <#if ${field.name}?? >
                    [@input name="${field.name}." + sfield.name value=r"${"+ field.name + "." + sfield.name + "!}" valuelist="${field.valuelist!}"/]
                <#else>
                    [@input name="${field.name}." + sfield.name value="" valuelist=field.valuelist!/]
                </#if>             
            </td>
            [/#list]
            </tr> 
        </table>
	[/#switch]
</tr>
[/#list>
</table>

[@submit name="commit" value="commit"/]

<br/>
</form>
