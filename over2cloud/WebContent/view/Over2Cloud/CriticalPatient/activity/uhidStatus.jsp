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

function viewTest(cellvalue, options, rowObject) 
{
	return "<a href='#' title='View Data' onClick='displayFullDetail("+rowObject.patient_id+")'><font color='blue'>"+cellvalue+"</font></a>";
}






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

				//alert("abc");
				var temp ="";
				temp += jQuery("#gridedittable").jqGrid('getCell',id,'caller_emp_id')+","+jQuery("#gridedittable").jqGrid('getCell',id,'caller_emp_name')+","+jQuery("#gridedittable").jqGrid('getCell',id,'caller_emp_mobile')+",";
				temp += jQuery("#gridedittable").jqGrid('getCell',id,'lab_doc')+","+jQuery("#gridedittable").jqGrid('getCell',id,'lab_doc_mob')+","+jQuery("#gridedittable").jqGrid('getCell',id,'uhid')+",";
				temp += jQuery("#gridedittable").jqGrid('getCell',id,'patient_name')+","+jQuery("#gridedittable").jqGrid('getCell',id,'patient_mob')+","+jQuery("#gridedittable").jqGrid('getCell',id,'patient_status')+",";
				temp += jQuery("#gridedittable").jqGrid('getCell',id,'adm_doc')+","+jQuery("#gridedittable").jqGrid('getCell',id,'adm_doc_mob')+",";
				temp += jQuery("#gridedittable").jqGrid('getCell',id,'nursing_unit')+","+jQuery("#gridedittable").jqGrid('getCell',id,'spec')+",";
				temp += jQuery("#gridedittable").jqGrid('getCell',id,'open_date')+","+jQuery("#gridedittable").jqGrid('getCell',id,'level')+",";
				temp += jQuery("#gridedittable").jqGrid('getCell',id,'test_type')+","+jQuery("#gridedittable").jqGrid('getCell',id,'patient_id')+","+jQuery("#gridedittable").jqGrid('getCell',id,'bed_no');
				
				var ticketNo = jQuery("#gridedittable").jqGrid('getCell',id,'ticket_no');
				ticketNo=$(ticketNo).text();
				var openDate = jQuery("#gridedittable").jqGrid('getCell',id,'open_date');
				var status = jQuery("#gridedittable").jqGrid('getCell',id,'status');
				var level = jQuery("#gridedittable").jqGrid('getCell',id,'level');
				level=$(level).text();
				var uhid = jQuery("#gridedittable").jqGrid('getCell',id,'uhid'); 
				var pid = jQuery("#gridedittable").jqGrid('getCell',id,'patient_id'); 
				$("#takeActionGrid").dialog({title: 'History of '+ticketNo,width: 1000,height: 650});
				$("#takeActionGrid").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
				$("#takeActionGrid").dialog('open');
				$.ajax({	
					type : "post",
					url : "view/Over2Cloud/Critical/viewTicketHistory.action?id="+id+"&status="+status+"&ticketNo="+ticketNo
					+"&openDate="+openDate+"&pid="+pid+"&level="+level+"&uhid="+uhid+"&viewData="+temp,
					success : function(data) 
					{
					    $("#takeActionGridUHID").html(data);
					},
					error: function() {
					    alert("error");
					}
				});
				


				//	url  : "/over2cloud/view/Over2Cloud/Referral/beforeViewActivityHistoryData?id="+id+"&status="+status+"&refBy="+refBy+"&refTo="+refTo+"&nursingUnit="+nu+"&refBySpec="+refBySpec+"&refToSpec="+refToSpec+"&bed="+bed+"&openDate="+openDate+"&priority="+priority+"&uhid="+uhid+"&patientName="+pName+"&subSpec="+subSpec,
					

				
							
			}

			function displayFullDetail()
			{
				var id=$("#patient_id").val();
			
			 $.ajax({
				    type : "post",
				 	url : "view/Over2Cloud/Critical/testDetails.action?pid="+id,
				 	success : function(data) 
				    {
			 			$("#viewTest").html(data);
					},
				   error: function() {
				        alert("error");
				    }
				 });
				
				
			}
			displayFullDetail();     
		   
</script>
</head>
<body>
<s:hidden name="patient_id" id="patient_id" value="%{dataFor}"></s:hidden>
<sj:dialog
          id="takeActionGridUHID"
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
<div style="width:100%; height:150px;overflow: scroll;">
<s:url id="viewUHIDGridData" action="viewUHIDGridData" escapeAmp="false" namespace="/view/Over2Cloud/Critical">
<s:param name="uhidStatus" value="%{uhidStatus}"></s:param>
<s:param name="dataFor" value="%{dataFor}"></s:param>
</s:url>

<sjg:grid 
		  id="uhidGrid"
          href="%{viewUHIDGridData}"
          gridModel="viewCritical"
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
          navigatorEditOptions="{height:450,width:400}"
          shrinkToFit="true"
          sortable="true"
          >
         <s:iterator value="viewPageUHIDColumns" id="viewPage">  
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

<sj:dialog
          id="viewTestDetails"
          showEffect="slide" 
          hideEffect="explode" 
          autoOpen="false"
           width="1000"
          height="350"
          draggable="true"
          resizable="true"
           modal="true"
         onCloseTopics="cancelButtonDialog"
          position="center"
          >
</sj:dialog>
<div id="viewTest"></div>
</body>
</html>