<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<%String validApp = session.getAttribute("validApp") == null ? "" : session.getAttribute("validApp").toString();%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/js.js"/>"></script>
<title>Insert title here</title>
<SCRIPT type="text/javascript">

function onloadData()
{
	var approvalStatus = $("#approvalStatus").val();
	var actionBy = $("#actionBy").val();
	var moduleName = $("#moduleName").val();
	var minDateValue = $("#minDateValue").val();
	var maxDateValue = $("#maxDateValue").val();
	var reqBy = $("#requestBy").val();
	var reqTo = $("#requestTo").val();

	if(actionBy=="selfApprove")
	{
		$('#requestByTD').show();
		$('#requestToTD').hide();
	}
	else
	{
		$('#requestToTD').show();
		$('#requestByTD').hide();
	}
	
	if(approvalStatus=="Pending" && actionBy=="selfApprove")
	{
		$('#actionID').show();
	}
	else
	{
		$('#actionID').hide();
	}

	
	$.ajax
	({
		type : "post",
		url  : "/over2cloud/view/Over2Cloud/SeekApproval/beforeViewSeekApprovalInGrid.action?moduleName="+moduleName+"&approvalStatus="+approvalStatus+"&actionBy="+actionBy+"&minDateValue="+minDateValue+"&maxDateValue="+maxDateValue+"&reqBy="+reqBy+"&reqTo="+reqTo,
		success : function(data)
		{
			$.ajax
			({
				type : "post",
				url  : "/over2cloud/view/Over2Cloud/SeekApproval/gridDataCount.action?moduleName="+moduleName+"&approvalStatus="+approvalStatus+"&actionBy="+actionBy+"&minDateValue="+minDateValue+"&maxDateValue="+maxDateValue+"&reqBy="+reqBy+"&reqTo="+reqTo,
				success : function(testdata)
				{
					$("#totalDiv").val(testdata.total);
					$("#pendingDiv").val(testdata.Pending);
					$("#doneDiv").val(testdata.Approved);
					$("#dissDiv").val(testdata.DisApproved);
					
				},
				error : function()
				{
					alert("Error on data fetch");
				} 
			});
			$("#viewDataDiv").html(data);
		},
		error : function()
		{
			alert("Error on data fetch");
		} 
	});
}

function downloadApprovedDoc(cellvalue, options, rowObject) 
{
	if(rowObject.approveddoc=='NA')
	{
		return "<a href='#' title='No Doc'>"+cellvalue+"</a>";
	}
	else
	{
		return "<a href='#' title='Download' onClick='approvedDocDownloadAction("+rowObject.id+")'>"+cellvalue+"</a>";
	}
}

function downloadApprovaldoc(cellvalue, options, rowObject) 
{
	if(rowObject.approvaldoc=='NA')
	{
		return "<a href='#' title='No Doc'>"+cellvalue+"</a>";
	}
	else
	{
		return "<a href='#' title='Download' onClick='approvalDocDownloadAction("+rowObject.id+")'>"+cellvalue+"</a>";
	}
}

function approvedDocDownloadAction(seekId) 
{
	$.ajax
	({
		type : "post",
		url  : "/over2cloud/view/Over2Cloud/SeekApproval/getDocPath.action?pkId="+seekId+"&columnName=doc_after_approval&tableName=seek_approval_detail",
		success : function(data)
		{
			$("#docPath").val(data.path);
			$("#formone").submit();
		},
		error : function()
		{
			alert("Error on data fetch");
		} 
	});
}

function approvalDocDownloadAction(seekId) 
{
	$.ajax
	({
		type : "post",
		url  : "/over2cloud/view/Over2Cloud/SeekApproval/getDocPath.action?pkId="+seekId+"&columnName=doc_for_approval&tableName=seek_approval_detail",
		success : function(data)
		{
			$("#docPath").val(data.path);
			$("#formone").submit();
		},
		error : function()
		{
			alert("Error on data fetch");
		} 
	});
}

function viewActivityBoard()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Activity_Board/viewActivityBoardHeader.action",
	    success : function(feeddraft) {
       $("#"+"data_part").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function getFormData()
{
	 $("#takeActionGrid").html("<br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	var moduleName = $("#moduleName").val();
	var id;
	id = $("#gridedittable").jqGrid('getGridParam','selrow');
	var tickeNo = jQuery("#gridedittable").jqGrid('getCell',id,'ticketno');
	$("#takeActionGrid").dialog({title: 'Seek Approval for '+tickeNo});
	
	if(id=="")
    {
      alert("Please select atleast one check box...");        
      return false;
    }
	else
	{
		$("#takeActionGrid").dialog('open');
		$.ajax
		({
			type : "post",
			url  : "/over2cloud/view/Over2Cloud/SeekApproval/beforeTakeActionSeekApprovalRequest.action?moduleName="+moduleName+"&seekId="+id,
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
}


onloadData();
</SCRIPT>
</head>
<body>
<sj:dialog
          id="takeActionGrid"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Seek Approval Action"
          modal="true"
          closeTopics="closeEffectDialog"
          width="1000"
          height="400"
          >
</sj:dialog>
<sj:dialog id="printSelect" title="Print Ticket" autoOpen="false"  modal="true" height="370" width="700" showEffect="drop">
<sj:dialog id="feed_status" modal="true" effect="slide" autoOpen="false"  width="1100" hideEffect="explode" position="['center','top']"></sj:dialog>
<div id="ticketsInfo"></div>
</sj:dialog>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Seek Approval</div>
	<img alt="" src="images/forward.png" style="margin-top:10px; float: left;">
	<div class="head"> Activity</div> 
	<div class="head">
		Pending: <s:textfield id="pendingDiv" cssClass="textfieldbgcolr" cssStyle="width: 21px;" disabled="true"/>
		Approved: <s:textfield id="doneDiv" cssClass="textfieldbgcolr" cssStyle="width: 21px;" disabled="true"/>
		Disapproved: <s:textfield id="dissDiv" cssClass="textfieldbgcolr" cssStyle="width: 21px;" disabled="true"/>
		Total:<s:textfield id="totalDiv" cssClass="textfieldbgcolr" cssStyle="width: 70px;" disabled="true"/>
	</div>	
	<sj:a style="margin-top: 4px;float: right;" button="true" cssClass="button" onclick="viewActivityBoard();">Activity Board</sj:a>
</div>
<div class="clear"></div>
<div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<!-- Code For Header Strip -->
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
<s:hidden id="moduleName" value="%{moduleName}"/>
<s:form id="formone" name="formone" action="downloadDoc"  theme="css_xhtml"  method="post" enctype="multipart/form-data">
	<s:hidden id="docPath" name="fileName"/>
</s:form>
 <table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
	<tbody>
	   <tr>
		   <!-- Block for insert Left Hand Side Button -->
		   <td>
			  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
			    <tbody>
				    <tr>
				    
				    <td class="pL10">
				    		 <sj:datepicker cssStyle="height: 16px; width: 60px;" cssClass="button" id="minDateValue" name="minDateValue" size="20" maxDate="0" value="%{minDateValue}" readonly="true"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select From Date"/>
		     				 <sj:datepicker cssStyle="height: 16px; width: 60px;" cssClass="button" id="maxDateValue" name="maxDateValue" size="20" value="%{maxDateValue}"   readonly="true" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select To Date"/>
				            <s:if test="outletList.size()>0">
				             <s:select  
						    		id					=		"outletid"
						    		name				=		"outletid"
						    		list				=		"outletList"
						      		theme				=		"simple"
						      		cssClass			=		"button"
						      		cssStyle			=		"height: 28px;margin-top: 3px;margin-left: 3px;width: 120px;"
						      		onchange			=		"onloadData()"	
						      		>
					      	 </s:select> 
				             </s:if>
							 <s:select  
						    		id					=		"approvalStatus"
						    		name				=		"approvalStatus"
						    		list				=		"#{'Pending':'Pending','Approved':'Approved','Disapproved':'Disapproved','all':'All Status'}"
						      		theme				=		"simple"
						      		cssClass			=		"button"
						      		cssStyle			=		"height: 28px;margin-top: 3px;margin-left: 3px;width: 135px;"
						      		onchange			=		"onloadData()"	
						      		>
					      	 </s:select> 
					      	 <s:select  
						    		id					=		"actionBy"
						    		name				=		"actionBy"
						    		list				=		"#{'selfApprove':'Request For Me','selfRequest':'Request By Me'}"
						      		theme				=		"simple"
						      		cssClass			=		"button"
						      		cssStyle			=		"height: 28px;margin-top: 3px;margin-left: 3px;width: 135px;"
						      		onchange			=		"onloadData()"	
						      		>
					      	 </s:select> 
					      	 </td>
					      	 <td id="requestByTD">
					      	  Requested By: 
					      	  <s:select  
						    		id					=		"requestBy"
						    		name				=		"requestBy"
						    		list				=		"requestByMap"
						      		theme				=		"simple"
						      		cssClass			=		"button"
						      		cssStyle			=		"height: 28px;margin-top: 3px;margin-left: 3px;width: 120px;"
						      		onchange			=		"onloadData()"	
						      		>
					      	 </s:select> 
					      	 </td>
					      	 <td id="requestToTD">
					      	 Requested To: 
					      	  <s:select  
						    		id					=		"requestTo"
						    		name				=		"requestTo"
						    		list				=		"requestToMap"
						      		theme				=		"simple"
						      		cssClass			=		"button"
						      		cssStyle			=		"height: 28px;margin-top: 3px;margin-left: 3px;width: 120px;"
						      		onchange			=		"onloadData()"	
						      		>
					      	 </s:select> 
					      	 </td> 
					      	 
					      	 
			       		
				    </tr>
				</tbody>
			 </table>
		   </td>
				  
		  <!-- Block for insert Right Hand Side Button -->
		  <td class="alignright printTitle" id="actionID">
		     <sj:a  button="true" cssClass="button" cssStyle="height: 28px;margin-top: 3px;margin-left: 3px;width: 87px;"  onclick="getFormData();">Action</sj:a>
		  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<!-- Code End for Header Strip -->
<div id="viewDataDiv"></div>
</div>
</div>
</body>
</html>
