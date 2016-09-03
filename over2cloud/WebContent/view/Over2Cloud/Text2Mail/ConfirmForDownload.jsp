<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
.sortable {
height:15px;
margin:5px;
padding:10px;
text-align: left;
}
</style>
<script type="text/javascript">
checked = false;
function checkedAll()
{
	if (checked == false)
	{
		checked = true;
	}
	else
	{
		checked = false;
	}
	for (var i = 0; i < document.getElementById('download').elements.length; i++)
	{
		document.getElementById('download').elements[i].checked = checked;
	}
}


function abc()
{
	var checkStatus=false;
	for (var i = 0; i < document.getElementById('download').elements.length; i++)
	{
		if(document.getElementById('download').elements[i].checked==true)
		{
			checkStatus=true;
		}
	} 
	if(checkStatus==false)
	{
		checkedAll();
	}
	else
	{
		$('#download').submit();
	}
	setTimeout(function()
	{ 
		 $('#takeActionGrid').dialog('close');
 	}, 2000);
}
</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div id="sendMailDiv"></div>
<sj:a 
             buttonIcon="ui-icon-check"
             href="%{downloadDetails}"
             button="true"
             formIds="download"
             onclick="abc();"
             targets="%{divName}"
             >Ok
    </sj:a>
<s:form action="downloadAction" id="download" theme="css_xhtml">
	<s:hidden name="tableName" value="%{tableName}" />
	<s:hidden name="excelName" value="%{excelName}" />
	<s:hidden name="selectedId" value="%{selectedId}" />
	<s:hidden name="tableAlis" value="%{tableAlis}" />
	<s:hidden name="fromDate122" value="%{fromDate122}" />
	<s:hidden name="toDate122" value="%{toDate122}" />
	<s:hidden name="location" value="%{location}" />
	<s:hidden name="cons" value="%{cons}" />
	<s:hidden name="kws" value="%{kws}" />
	
	
	
	

		<sj:div id="sortable" sortable="true" sortableOpacity="0.1" sortablePlaceholder="ui-state-highlight" sortableForcePlaceholderSize="true" cssStyle="width: 200px;">
	 		<div id=""  class="sortable ui-widget-content ui-corner-all">
	 			<s:checkbox labelposition="right" name="checkall" label="Select All"  onclick='checkedAll();'/>
	 		</div>
	 		<s:iterator value="columnMap" status="rowstatus">
		 		<div id="#rowstatus.index"  class="sortable ui-widget-content ui-corner-all">
		 			<s:checkbox cssClass="aa" labelposition="right" label="%{value}" name="columnNames" fieldValue="%{key}"/>
		 		</div>
			</s:iterator>
    </sj:div>
</s:form>
</body>
</html>