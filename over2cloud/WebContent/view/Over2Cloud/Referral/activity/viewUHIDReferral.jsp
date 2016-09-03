<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%
	String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();
%>
<html>
<head>

<script type="text/javascript">
function viewDetails(cellvalue, options, rowObject) 
{
	if(cellvalue=='Stat')
	{
		return "<h><b><font color='red'>"+cellvalue+"</font></b></h>";
	}	
	else if(cellvalue=='Urgent')
	{
		return "<h><b><font color='#CC00CC'>"+cellvalue+"</font></b></h>";
	}	
	else
	{
		return "<h><font>"+cellvalue+"</font></h>";
	}	
}

function viewLevel(cellvalue, options, rowObject) 
{
	if(cellvalue!='Level1')
	{
		return "<font color='red'>"+cellvalue+"</font>";
	}	
	else
	{
		return "<font>"+cellvalue+"</font>";
	}	
}
		    
		  function historyView(cellvalue, options, rowObject) 
			{
				return "<a href='#' title='View Data' onClick='viewHistoryOnClick("+rowObject.id+")'><font color='blue'>"+cellvalue+"</font></a>";
			}

			function viewHistoryOnClick(id) 
			{

				var ticketNo = jQuery("#gridedittable").jqGrid('getCell',id,'ticket_no');
				var refBy = jQuery("#gridedittable").jqGrid('getCell',id,'ref_by');
				var refBySpec = jQuery("#gridedittable").jqGrid('getCell',id,'spec');
				var refTo = jQuery("#gridedittable").jqGrid('getCell',id,'ref_to');
				var refToSpec = jQuery("#gridedittable").jqGrid('getCell',id,'ref_spec');
				var bed = jQuery("#gridedittable").jqGrid('getCell',id,'bed');
				var nu = jQuery("#gridedittable").jqGrid('getCell',id,'nursing_unit');
				var priority = jQuery("#gridedittable").jqGrid('getCell',id,'priority');
				priority=$(priority).text();
				var openDate = jQuery("#gridedittable").jqGrid('getCell',id,'open_date');
				var status = jQuery("#gridedittable").jqGrid('getCell',id,'status');
				var uhid = jQuery("#gridedittable").jqGrid('getCell',id,'uhid');
				var pName = jQuery("#gridedittable").jqGrid('getCell',id,'patient_name');
				var subSpec = jQuery("#gridedittable").jqGrid('getCell',id,'ref_to_sub_spec');
				var empId = jQuery("#gridedittable").jqGrid('getCell',id,'caller_emp_id');
				var empName = jQuery("#gridedittable").jqGrid('getCell',id,'caller_emp_name');
				$("#takeActionGrid").dialog('open');
				$("#takeActionGrid").dialog({title: 'History of '+ticketNo,width: 900,height: 600});
				$.ajax
				({
					type : "post",
					url  : "/over2cloud/view/Over2Cloud/Referral/beforeViewActivityHistoryData?id="+id+"&status="+status+"&refBy="+refBy+"&refTo="+refTo+"&nursingUnit="+nu+"&refBySpec="+refBySpec+"&refToSpec="+refToSpec+"&bed="+bed+"&openDate="+openDate+"&priority="+priority+"&uhid="+uhid+"&patientName="+pName+"&subSpec="+subSpec+"&empId="+empId+"&empName="+empName,
					success : function(data)
					{
						$("#takeActionGrid").html(data);
					},
					error : function()
					{
						alert("Error on data fetch");
					} 
				});
			}
				
		     
		   
</script>
</head>
<body>
<sj:dialog
          id="takeActionGrid"
          showEffect="slide" 
          hideEffect="explode" 
          autoOpen="false"
           width="1100"
          height="400"
          draggable="true"
          resizable="true"
           modal="true"
         onCloseTopics="cancelButtonDialog"
          position="center"
          >
</sj:dialog>
<div style="width:100%; height:450px;overflow: scroll;">
<s:url id="viewUHIDGridData" action="viewUHIDGridData" escapeAmp="false" namespace="/view/Over2Cloud/Referral">
<s:param name="uhidStatus" value="%{uhidStatus}"></s:param>
</s:url>

<sjg:grid 
		  id="gridUHIDedittable"
          href="%{viewUHIDGridData}"
          gridModel="viewReferralData"
          navigator="false"
          navigatorAdd="false"
          navigatorView="false"
          navigatorDelete="true"
          navigatorEdit="true"
          navigatorSearch="true"
          rowList="100,250,500,1000"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="100"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          editurl="%{modifyGrid}"
          navigatorEditOptions="{height:400,width:400}"
          shrinkToFit="true"
          sortable="true"
          >
         <s:iterator value="viewPageUHIDColumns" id="viewPageUHIDColumns">  
		<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="%{headingName}"
					width="%{width}"
					align="center"
					editable="%{editable}"
					formatter="%{formatter}"
					search="%{search}"
					hidden="%{hideOrShow}"
				/>
		</s:iterator> 
</sjg:grid>
</div>
</body>
</html>