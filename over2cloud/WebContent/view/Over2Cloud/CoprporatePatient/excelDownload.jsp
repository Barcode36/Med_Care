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
element.style {
    width: 161px;
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
			 $('#downloadExcel').dialog('close');
	 	}, 2000);
		 setTimeout(function()
		 { 
			 $('#downloadExcel').dialog('close');
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
             cssStyle="float: right;margin-top: 6px;"
             >Download
</sj:a>
<s:form action="excelDownload" id="download" theme="css_xhtml">
	<s:hidden name="tableAlis" value="%{tableAlis}" />
	<s:hidden name="maxDateValue" value="%{maxDateValue}" />
	<s:hidden name="minDateValue" value="%{minDateValue}" />
	<s:hidden name="feed_status" value="%{feed_status}" />
	<s:hidden name="corp_type" value="%{corp_type}" />
	<s:hidden name="corp_name" value="%{corp_name}" />
	<s:hidden name="services" value="%{services}" />
	<s:hidden name="ac_manager" value="%{ac_manager}" />
	<s:hidden name="med_location" value="%{med_location}" />
	<s:hidden name="patienceSearch" value="%{patienceSearch}" />
	<s:hidden  name="wildSearch" value="%{wildSearch}"/> 
	<s:hidden  name="downloadID" value="%{downloadID}"/> 
	
	
		<sj:div id="sortable" cssStyle="width: 161px;">
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
          height="296"
          draggable="true"
    	  resizable="true"
          >
          <div id="divSendMail"></div>
</sj:dialog>
</body>
</html>