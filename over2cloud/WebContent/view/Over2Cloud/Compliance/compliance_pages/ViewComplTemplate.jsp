<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%
	String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/compliance/ComplianceMenuCtrl.js"/>"></script>
<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	
function editRow()
{
	jQuery("#gridedittable").jqGrid('editGridRow', row ,{reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true});
}

function deleteRow()
{
	$("#gridedittable").jqGrid('delGridRow',row, {reloadAfterSubmit:true});
}
</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
<div class="head">Operation Task >> Template</div>
</div>
<div class="clear"></div>
	<%if(userRights.contains("cote_ADD")) {%>
		<div class="clear"></div>
			<div style="float: right; margin: 10px 12px 10px 0px;">
		<sj:a id="addButton"  button="true"  buttonIcon="ui-icon-arrowreturnthick-1-n" cssClass="button" onclick="addComplTemplate();">New Template</sj:a>
		<span class="normalDropDown"> 
		</span>
		</div>
	<%} %>
	<div style="overflow:auto; height:370px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
	<div class="clear"></div>
	<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie">
		<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody><tr></tr><tr><td></td></tr><tr><td> 
			<table class="floatL" border="0" cellpadding="0" cellspacing="0">
		<tbody>
		<tr><td class="pL10"> 
			<%if(userRights.contains("cote_MODIFY")) {%>
				<sj:a id="editButton" buttonIcon="ui-icon-pencil" cssClass="button" button="true" onclick="editRow()"> Edit </sj:a>
			<%} %>
			<%if(userRights.contains("cote_DELETE")) {%>
				<sj:a id="delButton" buttonIcon="ui-icon-trash" cssClass="button" button="true"  onclick="deleteRow()">Delete</sj:a>
			<%} %>	
		</td></tr>
		</tbody>
		</table>
		</td>   
		</tr>
		</tbody>
		</table>
	</div>
	</div>
<center>
<s:url id="viewCompTemplate" action="viewCompTemplate" />
<s:url id="modifyCompTemplate" action="modifyCompTemplate" />
<sjg:grid 
		  id="gridedittable"
          href="%{viewCompTemplate}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorSearch="true"
          navigatorDelete="false"
          navigatorEdit="false"
          rowList="15,50,100"
          rowNum="15"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          rownumbers="true"
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400}"
          navigatorViewOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','cn','bw','ew']}"
          editurl="%{modifyCompTemplate}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="true"
          autowidth="true"
          loadonce="true"
          multiselect="true"
          onSelectRowTopics="rowselect"
          >
		<s:iterator value="masterViewProp" id="masterViewProp" >  
		<sjg:gridColumn 
						name="%{colomnName}"
						index="%{colomnName}"
						title="%{headingName}"
						align="%{align}"
						width="244"
						editable="%{editable}"
						formatter="%{formatter}"
						search="%{search}"
						hidden="%{hideOrShow}"
						/>
		</s:iterator>  
</sjg:grid>
</center>
</div>
</div>
</body>
</html>