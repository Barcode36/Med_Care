<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}" />
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/compliance/compliance.js"/>"></script>
<SCRIPT type="text/javascript">
$.subscribe('makeEffect', function(event,data)
 {
 	setTimeout(function(){ $("#data_msg").fadeIn(); }, 10);
 	setTimeout(function(){ $("#data_msg").fadeOut(); }, 400);
 });
function getDept()
{
	var sel_id;
	sel_id=$("#gridTransferComp1").jqGrid('getGridParam','selarrrow');
	if(sel_id=="")
	{
	     alert("Please select atleast one check box...");        
	     return false;
	}
	else
	{
		var deptName = jQuery("#gridTransferComp1").jqGrid('getCell',sel_id,'dept.deptName');
		var empId=$("#emp_id1").val();
		var dept=$("#fromDept_id1").val();
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/getDept.action?selectedId="+sel_id+"&empName="+empId+"&deptName="+deptName+"&forDept="+dept,
		    success : function(data) 
		    {
		 		$("#transferActionGrid").dialog('open');
				$("#deptDiv").html(data);
			},
		    error: function()
		    {
		        alert("error");
		    }
		 });
	}
}
function getCompParticularEmployee(empName)
{
	document.getElementById("port").style.display="block";
	 $("#getEmployeeParticular").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Compliance/compliance_pages/complTransferAction.action?modifyFlag=1&empName="+empName,
		    success : function(subdeptdata) {
	       $("#"+"getEmployeeParticular").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
}
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});
function searchRow()
{
	 $("#gridTransferComp1").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function reloadRow()
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compliance_pages/complTransferAction.action?modifyFlag=0",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
	var grid = $("#gridTransferComp1");
	grid.trigger("reloadGrid",[{current:true}]);
}
function portData(deptId1,deptId2,empId1,replaceByEmp_id2)
{
	var sel_id;
	sel_id=$("#gridTransferComp1").jqGrid('getGridParam','selarrrow');
	if (sel_id=="") 
	{
		alert("Please select atleast one row.");	
	}
	else
	{
		var replaceToEmpName=$("#"+empId1).val();
		var replaceByEmpId=$("#"+replaceByEmp_id2).val();
		var forDept=$("#"+deptId1).val();
		var fromDept=$("#"+deptId2).val();
		if(fromDept=="-1")
		{
			alert("Please Select From Department.");
		}
		else if(replaceByEmpId=="-1")
		{
			alert("Please Select Employee.");
		}
		else
		{
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/Compliance/compliance_pages/updateReminderToPort.action?selectedId="+sel_id+"&replaceToEmpName="+replaceToEmpName+"&forDept_id="+forDept+"&fromDept_id="+fromDept+"&replaceByEmpId="+replaceByEmpId,
			    success : function(subdeptdata) {
		       $("#"+"data_msg").html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
		setTimeout(function(){ $("#data_msg").fadeIn(); }, 10);
	 	setTimeout(function(){ $("#data_msg").fadeOut(); }, 800);
	}
}

</SCRIPT>
<SCRIPT type="text/javascript">
 function getCompAllotedEmp(deptId,divId)
 {
	 $.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/getEmpData.action",
		data: "deptId="+deptId, 
		success : function(data) 
		{   
		$('#'+divId+' option').remove();
		$('#'+divId).append($("<option></option>").attr("value",-1).text('Select Employee'));
		$(data).each(function(index)
		{
		   $('#'+divId).append($("<option></option>").attr("value",this.ID).text(this.NAME));
		});
		},
		   error: function() {
	            alert("error");
	        }
		 });
 }
 function escalationY(cellvalue, options, rowObject) 
 {
 	if(rowObject.actionStatus=='Done')
 	{
 		return "Done";
 	}
 	else
 	{
 		return "<a href='#' onClick='escalationYAction("+rowObject.id+")'>"+cellvalue+"</a>";
 	}
 }
 function dueDateY(cellvalue, options, rowObject) 
 {
 	if(rowObject.actionStatus=='Done')
 	{
 		return "Done";
 	}
 	else
 	{
 		return "<a href='#' onClick='dueDateYAction("+rowObject.id+")'>"+cellvalue+"</a>";
 	}
 }
 function escalationYAction(valuepassed) 
 {
  	   var escValue = jQuery("#gridTransferComp1").jqGrid('getCell',valuepassed,'comp.escalation');
  	   var a = escValue.split(">");
  	   var b = a[1].split("<");
  	   if (b[0]=='Yes') 
  	   {
  			$("#transferActionGrid").dialog({title: 'Escalation Level',height:'350' , width:'700'});
  			$("#transferActionGrid").dialog('open');
  			$("#datadivesc").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
  			$.ajax({
  			    type : "post",
  			    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/beforeEscalationData.action?complId="+valuepassed,
  			    success : function(data) 
  			    {
  			    	$("#deptDiv").html(data);
  			    },
  			    error: function() 
  			    {
  			       alert("error");
  			    }
  			 });
 	   }
 }
 function dueDateYAction(valuepassed) 
 { 
 	$("#transferActionGrid").dialog({title: 'Reminder',height:'350' , width:'700'});
 	$("#transferActionGrid").dialog('open');
 	$("#datadivesc").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
 	$.ajax({
 	    type : "post",
 	    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/beforeReminder.action?complId="+valuepassed+"&dataFor=port",
 	    success : function(data) 
 	    {
 	    	$("#deptDiv").html(data);
 	    },
 	    error: function() 
 	    {
 	       alert("error");
 	    }
 	 });
 }
</SCRIPT>
</head>
<body>
<sj:dialog
          id="transferActionGrid"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Port Operation Task"
          modal="true"
          closeTopics="closeEffectDialog"
          width="950"
          height="300"
          >
          <div id="deptDiv"></div>
</sj:dialog>

<div class="clear"></div>
<div class="middle-content">
<s:if test="%{flag}">
<div class="list-icon">
	 <div class="head">
	 Operation Task</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Port</div> 
</div>
<div class="clear"></div>
<div class="clear"></div>
</s:if>
<center> <div id="data_msg"></div></center>
<s:if test="%{flag}">
	<div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
</s:if>

	<s:if test="%{flag}">
	<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie">
		<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody><tr></tr><tr><td></td></tr><tr><td> 
			<table class="floatL" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
		<tr>
		<td class="pL10">
          <sj:a id="searchButton"  title="Search" buttonIcon="ui-icon-search" cssStyle="height:28px; width:32px"  cssClass="button" button="true"  onclick="searchRow()"></sj:a>
		  <sj:a id="refButton"  title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:28px; width:32px"  cssClass="button" button="true"  onclick="reloadRow()"></sj:a>
          
          <s:iterator value="complTransferColMap" status="status" begin="0" end="1">
	            
	             <s:if test="#status.odd">
	                    <s:select  
		                              	id					=		"emp_id1"
		                              	name				=		"empId"
		                              	list				=		"empMap"
		                              	headerKey			=		"-1"
		                              	headerValue			=		"Select Employee" 
										cssClass			=		"button"
		                              	cssStyle			=		"margin-left: 4px; position:relative;"
		                              	theme				=		"simple"
		                              	onchange            =		"getCompParticularEmployee(this.value);"
		                              >
		                   </s:select> 
	           </s:if>
          </s:iterator>
          </td>
     	    <td>
          			<div id="port" style="display: none;margin-left: -112px;">
	          				 To:
			             <s:select  
		                              	id					=		"fromDept_id2"
		                              	name				=		"fromDept_id"
		                              	list				=		"deptMap"
		                              	headerKey			=		"-1"
		                              	headerValue			=		"Select Depart" 
		                              	cssClass			=		"button"
		                              	cssStyle			=		"width: 22%;position:relative;"
		                              	theme				=		"simple"
		                              	onchange			=		"getCompAllotedEmp(this.value,'emp_id2');"
		                              >
		                </s:select>
		              
	               	    <s:select  
	                              	id					=		"emp_id2"
	                              	name				=		"empId"
	                              	cssClass			=		"button"
		                            list				=		"#{'-1':'Select Employee'}" 
		                            cssStyle			=		"width: 22%;position:relative;"
	                              	theme				=		"simple"
	                              >
	                  </s:select>
	                  <sj:a id="port"  title="Port" cssClass="button"  href="#" onclick="portData('userDeptID','fromDept_id2','emp_id1','emp_id2')" onCompleteTopics="makeEffect">Port</sj:a>
          			</div>
		  </td>
		  <td>
		 
		  </td>
		</tr>
		</tbody>
		</table>
		</td>
		 <td class="alignright printTitle">
		 <div id="port" style="display: none;">
			 <sj:a id="geDept" buttonIcon="" cssClass="button" onclick="getDept();" button="true">Port</sj:a>	
	    </div>
	    </td>  
		</tr>
		</tbody>
		</table>
	</div>
	</div>
	  </s:if>
	<s:if test="%{flag}"> 
		<div style="overflow: scroll; height: 430px;">
	</s:if> 
	<div id="getEmployeeParticular">
	<s:url id="viewAllotedComp" action="getAllotedComp" escapeAmp="false">
	    <s:param name="empName"  value="%{empName}" />
	    <s:param name="userDeptID"  value="%{userDeptID}" />
	    <s:param name="modifyFlag"  value="%{modifyFlag}" />
	</s:url>
	<s:hidden name="empName"  value="%{empName}" />
	<s:hidden name="userDeptID" value="%{userDeptID}" />
	<s:hidden name="modifyFlag" value="%{modifyFlag}" />
	    <sjg:grid 
		  id="gridTransferComp1"
		  href="%{viewAllotedComp}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorEdit="false"
          navigatorDelete="false"
          navigatorSearch="true"
          rowList="15,20,25,30,35"
          rowNum="15"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','bw']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          autowidth="true"
          loadonce="true"
          onSelectRowTopics="rowselect"
          >
          
		<s:iterator value="masterViewProp" id="masterViewProp" >  
		<s:if test="colomnName=='id'">
		<sjg:gridColumn 
							name="%{colomnName}"
							index="%{colomnName}"
							title="%{headingName}"
							width="%{width}"
							align="%{align}"
							editable="%{editable}"
							formatter="%{formatter}"
							search="%{search}"
							hidden="true"
							key="true"
		/>
		</s:if>
		<s:else>
		<sjg:gridColumn 
							name="%{colomnName}"
							index="%{colomnName}"
							title="%{headingName}"
							width="%{width}"
							align="%{align}"
							editable="%{editable}"
							formatter="%{formatter}"
							search="%{search}"
							hidden="%{hideOrShow}"
		/>
		</s:else>
		</s:iterator>  
</sjg:grid>
</div>
</div>
</div>
<s:if test="%{flag }">
</div>
</s:if>
<br>
</body>
</html>
