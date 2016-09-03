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


$.subscribe('editRow', function(event,data)
{
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	else
	{
		var id   = jQuery("#gridedittableContact").jqGrid('getGridParam', 'selrow');
		var levelName = jQuery("#gridedittableContact").jqGrid('getCell',id,'level');
		var test=levelName.split("-");
		var currentLevel=parseInt(test[1]);
		var targetid='level';
		$('#'+targetid+' option').remove();
		for (var i=currentLevel; i<=5; i++)
		{
			$('#'+targetid).append($("<option></option>").attr("value",i).text("Level "+i));
		}
		jQuery("#gridedittableContact").jqGrid('editGridRow', row ,{height:200, width:300,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:100,modal:true});
	}
	
});

function deleteRow()
{
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	else
	{
		$("#gridedittableContact").jqGrid
		('delGridRow',row, 
			{
				reloadAfterSubmit:true,left:200,
				afterShowForm: function($form)
				{
					var dialog = $form.closest('div.ui-jqdialog'),
            		selRowId = jQuery("#gridedittableContact").jqGrid('getGridParam', 'selrow'),
            		selRowCoordinates = $('#'+selRowId).offset();
        			dialog.offset(selRowCoordinates);
				}
			}
		);
	}
}
function searchRow()
{
	 $("#gridedittableContact").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function reloadRow()
{
	var grid = $("#gridedittableContact");
	grid.trigger("reloadGrid",[{current:true}]);
}
</script>
</head>
<body>
<s:hidden id="dataFor" value="%{moduleName}"/>
<s:url id="veiwContact" action="viewComplContacts" escapeAmp="false">
	<s:param name="moduleName" value="%{moduleName}"></s:param>
	<s:param name="subType" value="%{subType}"></s:param>
	<s:param name="forSubDept_id" value="%{forSubDept_id}"></s:param>
	<s:param name="location" value="%{location}"></s:param>
	<s:param name="levelName" value="%{levelName}"></s:param>
</s:url>
<s:url id="modifyContactLevelURL" action="deleteComplContact" />
<sjg:grid 
		  id="gridedittableContact"
          href="%{veiwContact}"
          gridModel="masterViewList"
          groupField="['%{groupField}']"
          groupColumnShow="[true]"
          groupCollapse="true"
          groupText="['<b>{0} Total Headcount: {1}</b>']"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="false"
          editinline="false"
          rowList="100,200,500"
          rowNum="500"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          rownumbers="-1"
          pagerInput="false"   
          multiselect="false"
          navigatorSearchOptions="{sopt:['eq','cn']}"  
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400}"
          editurl="%{modifyContactLevelURL}"
          navigatorEditOptions="{closeAfterEdit:true,reloadAfterSubmit:true}"
          shrinkToFit="true"
          autowidth="true"
          loadonce="true"
          onSelectRowTopics="rowselect"
          onEditInlineSuccessTopics="oneditsuccess"
          >
		<s:iterator value="masterViewProp" id="masterViewProp" >  
			<s:if test="colomnName=='level'">
				<sjg:gridColumn 
				name="%{colomnName}"
				index="%{colomnName}"
				title="%{headingName}"
				width="%{width}"
				align="%{align}"
				editable="%{editable}"
				search="%{search}"
				hidden="false"
				edittype="select"
				editoptions="%{levelName}"
				/>
			</s:if>
			
			<s:else>  
				<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="%{headingName}"
					width="%{width}"
					align="%{align}"
					editable="false"
					formatter="%{formatter}"
					search="%{search}"
					hidden="%{hideOrShow}"
				/>
			</s:else>
		</s:iterator>  
</sjg:grid>
</body>
</html>