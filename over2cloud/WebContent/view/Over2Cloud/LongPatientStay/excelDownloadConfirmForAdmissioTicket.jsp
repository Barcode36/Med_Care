<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
			 $('#downloadExcelActivity').dialog('close');
	 	}, 2000);
		 setTimeout(function()
		 { 
			 $('#downloadExcelActivity').dialog('close');
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
             >OK
</sj:a>
<s:form action="downloadExcelTicket" id="download" theme="css_xhtml" >
		<s:hidden name="toDays" value="%{toDays}"/>
		<s:hidden name="fromDays" value="%{fromDays}"/>
	 <s:hidden name="location" value="%{location}"/>
	 <s:hidden name="nursing" value="%{nursing}"/>
	  <s:hidden name="doctor" value="%{doctor}"/>
	 <s:hidden name="speciality" value="%{speciality}"/>
	<s:hidden name="patDays" value="%{patDays}"></s:hidden>
	<s:hidden name="range" value="%{range}"></s:hidden>
	<s:hidden name="sdate" value="%{#parameters.sdate}"></s:hidden>
	 <s:hidden name="edate" value="%{#parameters.edate}"></s:hidden>
	   <s:hidden name="patDaysForDate" value="%{#parameters.patDaysForDate}"></s:hidden>
	 <s:hidden name="rangeForDate" value="%{#parameters.rangeForDate}"></s:hidden>
	   <s:hidden name="fromDaysForDate" value="%{#parameters.fromDaysForDate}"></s:hidden>
	 <s:hidden name="toDaysForDate" value="%{#parameters.toDaysForDate}"></s:hidden>
	<s:hidden name="excel_name" value="LongTimePatientStay"></s:hidden>

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
<sj:dialog
          id="sendmail"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title="Operation Task Mail"
          modal="true"
          width="1000"
          height="350"
          draggable="true"
    	  resizable="true"
          >
          <div id="divSendMail"></div>
</sj:dialog>
</body>
</html>