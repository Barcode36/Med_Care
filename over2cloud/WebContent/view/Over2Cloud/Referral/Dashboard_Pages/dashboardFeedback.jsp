<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/js.js"/>"></script>

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
				$("#takeActionGrid").dialog({title: 'History of '+ticketNo,width: 1000,height: 650});
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
				
		     
		    
		  function downloadFeedStatus()
		  {
			
		    	 	 
		  	$("#printSelect").dialog({title: 'Check Column',width: 350,height: 600});
		  	$("#printSelect").dialog('open');
		  	$.ajax({
		  	    type : "post",
		  	     url : "view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/feedbackStatusDownloadORD.action?feedStatus="+$('#feedStatus').val()+"&fromDate="+$('#fromDate').val()+"&toDate="+$('#toDate').val()+"&dashFor="+$("#dashFor").val()+"&dataFlag="+$("#dataFlag").val()+"&machineId="+$("#machineId").val()+"&location="+$("#location").val()+"&feedId="+$("#feedId").val(),
				
		  	    		success : function(data) 
		  	    {
		   			$("#printSelect").html(data);
		  		},
		  	   error: function() {
		  	        alert("error in");
		  	    }
		  	 }); 
		  }

</script>
</head>
<body>
<sj:dialog id="printSelect" title="Print Ticket" autoOpen="false"  modal="true" height="400" width="800" showEffect="drop">
<sj:dialog id="feed_status" title="Take Action On Feedback" modal="true" effect="slide" autoOpen="false" width="1000" hideEffect="explode" position="['center','top']"></sj:dialog>
<div id="ticketsInfo"></div>
</sj:dialog>
<sj:dialog
          id="takeActionGrid"
          showEffect="slide" 
          hideEffect="explode" 
          autoOpen="false"
           width="1000"
          height="380"
          draggable="true"
          resizable="true"
           modal="true"
         onCloseTopics="cancelButtonDialog"
          position="center"
          >
</sj:dialog>
<input type="hidden" id="conString"  value="<%=request.getContextPath()%>"/>
<s:hidden id="feedStatusValue" value="%{feedStatus}"></s:hidden>
<div class="clear"></div>
<div class="middle-content">
<!-- //////////////////////////////////////////// -->
<div class="clear"></div>
<div class="border" >
 

 <div class="clear"></div>
<s:hidden name="flag" value="%{flag}"></s:hidden>
<s:hidden name="feedStatus" value="%{feedStatus}"></s:hidden>
  <s:hidden name="filterFlag" value="%{filterFlag}"></s:hidden>
   <s:hidden name="fromTime" value="%{fromTime}"></s:hidden>
   <s:hidden name="toTime" value="%{toTime}"></s:hidden>
   <s:hidden name="specialty" value="%{specialty}"></s:hidden>
    <s:hidden name="graphDate" value="%{graphDate}"></s:hidden>
    <s:hidden name="fromDate1" value="%{fromDate1}"></s:hidden>
   <s:hidden name="toDate1" value="%{toDate1}"></s:hidden>
    <s:hidden name="emergency" value="%{emergency}"></s:hidden>
     <s:hidden name="attendedBy" value="%{attendedBy}"></s:hidden>
     <s:hidden name="refDoc" value="%{refDoc}"></s:hidden>
      <s:hidden name="locationWise" value="%{locationWise}"></s:hidden>
 <s:url id="viewBoardData" action="statusGridData" escapeAmp="false" namespace="/view/Over2Cloud/Referral/Dashboard_Pages">
<s:param name="flag" value="%{flag}"></s:param>
<s:param name="feedStatus" value="%{feedStatus}"></s:param>
  <s:param name="filterFlag" value="%{filterFlag}"></s:param>
   <s:param name="fromTime" value="%{fromTime}"></s:param>
   <s:param name="toTime" value="%{toTime}"></s:param>
   <s:param name="specialty" value="%{specialty}"></s:param>
    <s:param name="graphDate" value="%{graphDate}"></s:param>
     <s:param name="fromDate1" value="%{fromDate1}"></s:param>
   <s:param name="toDate1" value="%{toDate1}"></s:param>
    <s:param name="emergency" value="%{emergency}"></s:param>
    <s:param name="attendedBy" value="%{attendedBy}"></s:param>
     <s:param name="refDoc" value="%{refDoc}"></s:param>
     <s:param name="locationWise" value="%{locationWise}"></s:param>
  </s:url>

<sjg:grid 
		  id="gridedittable"
          href="%{viewBoardData}"
          gridModel="viewDashReferralData"
          navigator="false"
          navigatorAdd="false"
          navigatorView="false"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="false"
          rowList="500,1000,1500,2000,5000"
          editinline="true"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400,closeAfterEdit:true,reloadAfterSubmit:true}"
		  navigatorDeleteOptions="{caption:'Inactive',msg: 'Inactive selected record(s)?',bSubmit: 'Inactive',closeAfterEdit:true,reloadAfterSubmit:true}"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          rowNum="500"
          autowidth="true"
          editurl="%{editDeptDataGrid}"
          shrinkToFit="false"
          onSelectRowTopics="rowselect"
          navigatorCloneToTop="true"
          >
          
		<s:iterator value="viewPageColumns" id="viewPageColumns" > 
		
					<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="%{headingName}"
					width="%{width}"
					align="%{align}"
					editable="%{editable}"
					search="%{search}"
					hidden="%{hideOrShow}"
					formatter="%{formatter}"
					/>
			
		</s:iterator>  
</sjg:grid>
</div>
</div>
</body>


</html>