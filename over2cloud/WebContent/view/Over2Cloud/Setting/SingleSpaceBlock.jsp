<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/js.js"/>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/master.js"></script>

<script type="text/javascript">
$.subscribe('complete', function(event,data)
    {
        setTimeout(function(){ $("#foldeffect").fadeIn(); }, 10);
        setTimeout(function(){ $("#foldeffect").fadeOut(); }, 4000);
    });

var a=0;
function selectAll()
{
	
	if(a==0)
		{
	for(var i=0; i < document.formtwo.spaceAddress.length; i++){
		if(!document.formtwo.spaceAddress[i].checked)
			document.formtwo.spaceAddress[i].checked=true;
		}
	a=1;
		}
	else
		{
		for(i=0; i < document.formtwo.spaceAddress.length; i++){
				document.formtwo.spaceAddress[i].checked=false;
			}
		a=0;
		}
}
</script>
</head>
<body>
<s:form  id="BlockAllSingleSpace_Url" action="blockAllSingleSpace"  theme="css_xhtml" name="formtwo">
	 <center>
     <table width="100%" cellspacing="0" cellpadding="3">
     <tr><td bgcolor="#0e0e0e" class="tabular_head" valign="middle" colspan="7">Single Space Operation (Block/ Activate)</td></tr>
	<tr><td bgcolor="#252525" style=" border-bottom:1px solid #e7e9e9; color:#ffffff;" valign="top" class="tabular_cont">
			<table cellspacing="0" cellpadding="0" width="100%"><tbody>
				<tr>
					<td valign="middle" width="2.5%">&nbsp;</td>
					<td valign="middle" width="2.5%"><s:checkbox  name="selectall" id="selectall" onclick="selectAll();"  theme="simple"/></td>
					<td valign="middle" width="20%">Organization</td>
					<td valign="middle" width="20%">Account Id</td>
					
				</tr>
			</tbody></table>
		</td>
	</tr>
	
<s:iterator value="blockGridmodel" id="blockAccountId" status="counter">  
<s:if test="#counter.count%2 != 0">
	<tr><td bgcolor="#ffffff" style=" border-bottom:1px solid #e7e9e9; color:#181818;" valign="top" class="tabular_cont">
		<table cellspacing="0" cellpadding="0" width="100%"><tbody>
			<tr>
				<td valign="middle" width="2.5%" bgcolor="#252525" style=" color:#ffffff; text-align:center;"><s:property value="#counter.count" /></td>
				<td valign="middle" width="2.5%"><s:set name="id" value="%{combinekey}" /><input type="checkbox" checked="checked" style="width:20px;"  value="${id}" id="spaceAddress" name="spaceAddress"></td>
				<td valign="middle" width="20%"><s:property value="org_name" /></td>
				<td valign="middle" width="20%"><s:property value="accountid" /></td>
			</tr></tbody>
		</table>
	</td></tr>
</s:if><s:else>
<tr><td bgcolor="#e2e4e4" style=" border-bottom:1px solid #e7e9e9; color:#181818;" valign="top" class="tabular_cont">
		<table cellspacing="0" cellpadding="0" width="100%"><tbody>
			<tr>
				<td valign="middle" width="2.5%" bgcolor="#252525" style=" color:#ffffff; text-align:center;"><s:property value="#counter.count" /></td>
				<td valign="middle" width="2.5%"><s:set name="id" value="%{combinekey}" /><input type="checkbox" checked="checked" style="width:20px;"  value="${id}" id="spaceAddress" name="spaceAddress"></td>
				<td valign="middle" width="20%"><s:property value="org_name" /></td>
				<td valign="middle" width="20%"><s:property value="accountid" /></td>
			</tr></tbody>
		</table>
	</td></tr>
</s:else>
</s:iterator> 		
	
      </table>
      </center>
      <center> 
      <img id="indicator" src="<s:url value="/images/ajax-loader.gif"/>" alt="Loading..."   style="display:none"/>
             <div class="type-button">
             <sj:submit 
                        targets="Result" 
                        clearForm="true"
                        value="Take Action" 
                        href="%{AddAllSingleSpace_Url}"
                        effect="highlight"
                        effectOptions="{ color :'#222222'}"
                        effectDuration="5000"
                        button="true"
                        onCompleteTopics="complete"
                        indicator="indicator"
                        />
                        <sj:div id="foldeffect"  effect="fold">
                     <div id="Result"></div>
          			</sj:div>
              </div>         
           </center>
     </s:form>
</body>
</html>