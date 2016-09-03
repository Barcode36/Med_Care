<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%
String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();
%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/js.js"/>"></script>

<script type="text/javascript">
$.subscribe('getStatusForm', function(event,data)
  {
	  var connection=document.getElementById("conString").value;
	  var feedStatus = document.getElementById("feedStatusValue").value;
	  var feedid   = jQuery("#gridedittable").jqGrid('getGridParam', 'selrow');
	  var ticketNo = jQuery("#gridedittable").jqGrid('getCell',feedid,'ticket_no');
	  var feedby   = jQuery("#gridedittable").jqGrid('getCell',feedid,'feed_by');
	  var feedmob  = jQuery("#gridedittable").jqGrid('getCell',feedid,'feedback_by_mobno');
	  var feedemail= jQuery("#gridedittable").jqGrid('getCell',feedid,'feedback_by_emailid');
	  var subcatg  = jQuery("#gridedittable").jqGrid('getCell',feedid,'feedback_subcatg');
	  var feedbrief= jQuery("#gridedittable").jqGrid('getCell',feedid,'feed_brief');
	  var opendate = jQuery("#gridedittable").jqGrid('getCell',feedid,'open_date');
	  var opentime = jQuery("#gridedittable").jqGrid('getCell',feedid,'open_time');
	  var allotto = jQuery("#gridedittable").jqGrid('getCell',feedid,'feedback_allot_to');


	  if (feedid==null || feedStatus=='null' || ticketNo=="") 
	  {
		alert("Please Select a Row !!!");
		}
	  else
	  {
		  $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		    $.ajax({
		        type : "post",
		        url : connection+"/view/Over2Cloud/Feedback_Over2Cloud/Lodge_Feedback/feedAction.action?feedStatus="+feedStatus.split(" ").join("%20")+"&feedId="+feedid.split(" ").join("%20")+"&ticketNo="+ticketNo.split(" ").join("%20")+"&feedbackBy="+feedby.split(" ").join("%20")+"&mobileno="+feedmob.split(" ").join("%20")+"&emailId="+feedemail.split(" ").join("%20")+"&feedBreif="+feedbrief.split(" ").join("%20")+"&openDate="+opendate.split(" ").join("%20")+"&openTime="+opentime.split(" ").join("%20")+"&subCatg="+subcatg.split(" ").join("%20")+"&allotto="+allotto.split(" ").join("%20"),
		        success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
		    },
		       error: function() {
		            alert("error");
		        }
		     });
	  }



	  
  });

$.subscribe('getResolvedAckPage',function (event,data)
		{
			var feedid   = jQuery("#gridedittable").jqGrid('getGridParam', 'selrow');
	  		var ticketNo = jQuery("#gridedittable").jqGrid('getCell',feedid,'ticket_no');
	  		var feedby   = jQuery("#gridedittable").jqGrid('getCell',feedid,'feed_by');
	  		var feedbrief= jQuery("#gridedittable").jqGrid('getCell',feedid,'feed_brief');
	  		var opendate = jQuery("#gridedittable").jqGrid('getCell',feedid,'open_date');
	  		var capa=jQuery("#gridedittable").jqGrid('getCell',feedid,'resolve_remark');
	  		var rca=jQuery("#gridedittable").jqGrid('getCell',feedid,'spare_used');
	  		var mode=jQuery("#gridedittable").jqGrid('getCell',feedid,'via_from');
			if (feedid==null || ticketNo=="") 
			  {
				alert("Please Select a Row !!!");
				}
			  else
			  {
				  $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
				    $.ajax({
				        type : "post",
				        url : "view/Over2Cloud/feedback/report/beforePatAck.action?feedId="+feedid.split(" ").join("%20")+"&ticketNo="+ticketNo.split(" ").join("%20")+"&feedbackBy="+feedby.split(" ").join("%20")+"&brief="+feedbrief.split(" ").join("%20")+"&openDate="+opendate.split(" ").join("%20")+"&capa="+capa.split(" ").join("%20")+"&mode="+mode.split(" ").join("%20")+"&rca="+rca.split(" ").join("%20"),
				        success : function(subdeptdata) {
				       $("#"+"data_part").html(subdeptdata);
				    },
				       error: function() {
				            alert("error");
				        }
				     });
			  }




			
		});

$.subscribe('getStatusFormAction', function(event,data)
		  {
			  var connection=document.getElementById("conString").value;
			  var feedStatus = document.getElementById("feedStatusValue").value;
			  var feedid   = jQuery("#gridedittable").jqGrid('getGridParam', 'selrow');
			  var ticketNo = jQuery("#gridedittable").jqGrid('getCell',feedid,'ticket_no');


			  if (feedid==null || feedStatus=='null' || ticketNo=="") {
					alert("Please Select a Row !!!");
				}

				 if (feedStatus=='Pending') {
				  $("#actionDataDiv").load(connection+"/view/Over2Cloud/Feedback_Over2Cloud/Lodge_Feedback/getTicketsActionDetails.action?ticketNo="+ticketNo.split(" ").join("%20")+"&status="+feedStatus.split(" ").join("%20"));
			  }
			  $("#feed_Action_status").dialog('open');
			  $("#feed_Action_status").dialog({title:'Action Details For Ticket No '+ticketNo});
		  });

$.subscribe('getFormData', function(event,data)
		  {
			  var connection=document.getElementById("conString").value;
			  var feedStatus = document.getElementById("feedStatusValue").value;
			  var feedid   = jQuery("#gridedittable").jqGrid('getGridParam', 'selrow');
			  var ticketNo = jQuery("#gridedittable").jqGrid('getCell',feedid,'ticket_no');
			  var opendate = jQuery("#gridedittable").jqGrid('getCell',feedid,'open_date');
			  var opentime = jQuery("#gridedittable").jqGrid('getCell',feedid,'open_time');
			  var todept = jQuery("#gridedittable").jqGrid('getCell',feedid,'feedback_to_dept');
			  var feedtosubdept = jQuery("#gridedittable").jqGrid('getCell',feedid,'feedback_to_subdept');
			  var location  = jQuery("#gridedittable").jqGrid('getCell',feedid,'location');
			  var catg  = jQuery("#gridedittable").jqGrid('getCell',feedid,'feedback_catg');
			  var subcatg  = jQuery("#gridedittable").jqGrid('getCell',feedid,'feedback_subcatg');
			  var feedbrief= jQuery("#gridedittable").jqGrid('getCell',feedid,'feed_brief');
			  var feedby   = jQuery("#gridedittable").jqGrid('getCell',feedid,'feed_by');
			  var feedmob  = jQuery("#gridedittable").jqGrid('getCell',feedid,'feedback_by_mobno');
			  var addrdate  = jQuery("#gridedittable").jqGrid('getCell',feedid,'feedaddressing_date');
			  var addrtime  = jQuery("#gridedittable").jqGrid('getCell',feedid,'feedaddressing_time');
			  if (feedStatus=='Pending' || feedStatus=='Snooze' || feedStatus=='High Priority' || feedStatus=='Ignore') {
				  var allotto = jQuery("#gridedittable").jqGrid('getCell',feedid,'feedback_allot_to');
				  $("#printSelect").load(connection+"/view/Over2Cloud/Feedback_Over2Cloud/Lodge_Feedback/printTicketInfo.action?feedStatus="+feedStatus+"&ticket_no="+ticketNo+"&feedbackBy="+feedby.split(" ").join("%20")+"&mobileno="+feedmob.split(" ").join("%20")+"&open_date="+opendate.split(" ").join("%20")+"&open_time="+opentime.split(" ").join("%20")+"&catg="+catg.split(" ").join("%20")+"&subCatg="+subcatg.split(" ").join("%20")+"&feed_brief="+feedbrief.split(" ").join("%20")+"&allotto="+allotto.split(" ").join("%20")+"&todept="+todept.split(" ").join("%20")+"&tosubdept="+feedtosubdept.split(" ").join("%20")+"&location="+location.split(" ").join("%20")+"&addrDate="+addrdate+"&addrTime="+addrtime);
			  }
			  else if(feedStatus=='Resolved') {
				  var allotto1  = jQuery("#gridedittable").jqGrid('getCell',feedid,'resolve_by');
				  var resolveon = jQuery("#gridedittable").jqGrid('getCell',feedid,'resolve_date');
				  var resolveat = jQuery("#gridedittable").jqGrid('getCell',feedid,'resolve_time');
				  var actiontaken = jQuery("#gridedittable").jqGrid('getCell',feedid,'resolve_remark');
				  var spareused = jQuery("#gridedittable").jqGrid('getCell',feedid,'spare_used');
				  $("#printSelect").load(connection+"/view/Over2Cloud/Feedback_Over2Cloud/Lodge_Feedback/printTicketInfo.action?feedStatus="+feedStatus+"&ticket_no="+ticketNo+"&feedbackBy="+feedby.split(" ").join("%20")+"&mobileno="+feedmob.split(" ").join("%20")+"&open_date="+opendate.split(" ").join("%20")+"&open_time="+opentime.split(" ").join("%20")+"&catg="+catg.split(" ").join("%20")+"&subCatg="+subcatg.split(" ").join("%20")+"&feed_brief="+feedbrief.split(" ").join("%20")+"&allotto="+allotto1.split(" ").join("%20")+"&todept="+todept.split(" ").join("%20")+"&tosubdept="+feedtosubdept.split(" ").join("%20")+"&location="+location.split(" ").join("%20")+"&addrDate="+addrdate+"&addrTime="+addrtime+"&resolveon="+resolveon+"&resolveat="+resolveat+"&spareused="+spareused.split(" ").join("%20")+"&actiontaken="+actiontaken.split(" ").join("%20"));
				  }
			  $("#printSelect").dialog('open');
		  });

$.subscribe('getMoreData', function(event,data)
		  {
			  var from_Date=document.getElementById("fromDate").value;
			  var to_Date=document.getElementById("toDate").value;
			  var feedStatus = document.getElementById("feedStatusValue").value;
			  $("#gridedittable").jqGrid('setGridParam', {
					url:"view/Over2Cloud/Feedback_Over2Cloud/Lodge_Feedback/viewFeedbackDetail.action?feedStatus="+feedStatus+"&fromDate="+from_Date+"&toDate="+to_Date+"&dataFlag=M"
					,datatype:'JSON'
					}).trigger("reloadGrid");
		  });


function reloadPending(rowid, result)
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Feedback_Over2Cloud/Lodge_Feedback/beforeFeedAction.action?feedStatus=Pending",
	    success : function(subdeptdata) {
$("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
   alert("error");
}
	 }); 
	}


function reloadResolved(rowid, result)
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Feedback_Over2Cloud/Lodge_Feedback/beforeFeedAction.action?feedStatus=Resolved",
	    success : function(subdeptdata) {
$("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
   alert("error");
}
	 }); 
}


function searchData()
{
	jQuery("#gridedittable").jqGrid('searchGrid',{sopt:['eq','cn']}, {multipleSearch:false,reloadAfterSubmit:true,top:0,left:350,modal:true} );
}

function reloadPage()
{
	$.ajax({
 		type :"post",
 		url : "/over2cloud/view/Over2Cloud/Feedback_Over2Cloud/Lodge_Feedback/beforeFeedAction.action?feedStatus=Pending",
 		success : function(data) 
	    {
			$("#feedTicket").html(data);
		},
	    error: function()
	    {
	        alert("error");
	    }
 	});
}

function getSearchData()
{
	var fromDate=$("#fromDate").val();
	var toDate=$("#toDate").val();
	var status=$("#status").val();
	var mode=$("#mode").val();
	var feedType=$("#feedType").val();
	var feedCat=$("#feedCat").val();
	var ticket_no=$("#ticket_no").val();
	var feedBy=$("#feedBy").val();
	if(status=='Resolved')
	{
		document.getElementById("sendResponse").style.display="block";
	}
	else
	{
		document.getElementById("sendResponse").style.display="none";
	}

	$.ajax({
 		type :"post",
 		url : "view/Over2Cloud/Feedback_Over2Cloud/Lodge_Feedback/viewSearchedFeedForAction.action?feedStatus="+status.split(" ").join("%20")+"&fromDate="+fromDate+"&toDate="+toDate+"&mode="+mode.split(" ").join("%20")+"&feedType="+feedType.split(" ").join("%20")+"&feedCat="+feedCat.split(" ").join("%20")+"&ticket_no="+ticket_no.split(" ").join("%20")+"&feedBy="+feedBy.split(" ").join("%20"),
 		success : function(data) 
	    {
			$("#feedTicket").html(data);
		},
	    error: function()
	    {
	        alert("error");
	    }
 	});
}
getSearchData();

</script>
</head>
<body>
<sj:dialog id="printSelect" title="Print Ticket" autoOpen="false"  modal="true" height="370" width="700" showEffect="drop">
<sj:dialog id="feed_Action_status" modal="true" effect="slide" autoOpen="false"  width="1000" hideEffect="explode" position="['center','middle']">
<div id="actionDataDiv" align="center"></div></sj:dialog>
<sj:dialog id="feed_status" modal="true" effect="slide" autoOpen="false"  width="1000" hideEffect="explode" position="['center','middle']">
<div id="dataDiv" align="center"></div></sj:dialog>
<div id="ticketsInfo"></div>
</sj:dialog>
<s:hidden id="feedStatusValue" value="%{feedStatus}"></s:hidden>
<input type="hidden" id="conString"  value="<%=request.getContextPath()%>"/>
<div class="list-icon">
	 <div class="head">
	 <%
	if(userRights.contains("nefn"))
	{
		%>
		<s:if test="%{feedStatus=='Pending' || feedStatus=='Snooze' || feedStatus=='High Priority'}">
			 Re-Opened  
	 	</s:if>
	 	<s:else>
	 		Resolved 
	 	</s:else>
		<%
		
	}
	else
	{
		%>
		<s:if test="%{feedStatus=='Pending' || feedStatus=='Snooze' || feedStatus=='High Priority'}">
			 Pending
	 	</s:if>
	 	<s:else>
	 		Resolved 
	 	</s:else>
		<%
	}
	%>
	 </div>
	 
	 <img src="images/forward.png" style="margin-top: 8px; float: left;"><div class="head">Total: <s:property value="%{totalTickets}"/><sj:a id="refButtonPage1" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px;margin-left: 742px;" cssClass="button" button="true" onclick="reloadPage();"></sj:a></div>
</div>
<div class="clear"></div>
<div style="align:center; padding-top: 10px;padding-left: 10px;padding-right: 10px;">
<div class="listviewBorder">
<!-- Code For Header Strip -->
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <!-- Block for insert Left Hand Side Button -->
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">
							<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="searchData()"></sj:a>
							<s:if test="%{feedStatus=='Pending' || feedStatus=='Snooze' || feedStatus=='High Priority'}">
	 							<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="reloadPending()"></sj:a>
	 						</s:if>
	 						<s:else>
	 							<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="reloadResolved()"></sj:a>
	 						</s:else>
					        <sj:datepicker onchange="getSearchData();" cssStyle="height: 16px; width: 60px;" cssClass="button" id="fromDate" name="fromDate" size="20" maxDate="0"  value="%{fromDate}" readonly="true"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="From Date"/>
		     				<sj:datepicker onchange="getSearchData();"  cssStyle="height: 16px; width: 60px;" cssClass="button" id="toDate" name="toDate" size="20" value="%{toDate}" minDate="%{minCount}"  readonly="true" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="To Date"/>
					        <s:select 
		                      id="status" 
					          name="status" 
					          list="{'High Priority','Snooze','Resolved','Re-Assign','Ignore','Noted'}"
					          headerKey="Pending"
					          headerValue="Status"
					          cssClass="button"
					          cssStyle = "margin-left: 3px;margin-top:-31px;height:27px"
					          theme="simple"
					          onchange="getSearchData();"
		         			 />
		         			 <s:select 
                      id="mode" 
			          name="mode" 
			          list="{'Paper','SMS','Mob Tab'}"
			          headerKey="-1"
			          headerValue="All Mode"
			          cssClass="button"
			          cssStyle = "margin-left: 3px;margin-top:-31px;height:27px"
			          theme="simple"
			          onchange="getSearchData();"
         			 />
         			 <s:select 
                      id="feedType" 
			          name="feedType" 
			          list="feedTypeMap"
			          headerKey="-1"
			          headerValue="All Type"
			          cssClass="button"
			          cssStyle = "margin-left: 3px;margin-top:-31px;height:27px"
			          theme="simple"
			          onchange="getSearchData();"
         			 />
         			 <s:select 
                      id="feedCat" 
			          name="feedCat" 
			          list="feedCatMap"
			          headerKey="-1"
			          headerValue="Category"
			          cssClass="button"
			          cssStyle = "width: 100px;margin-left: 3px;margin-top:-31px;height:27px"
			          theme="simple"
			          onchange="getSearchData();"
         			 />
         			 <s:select 
                      id="ticket_no" 
			          name="ticket_no" 
			          list="ticketNoMap"
			          headerKey="-1"
			          headerValue="Ticket No"
			          cssClass="button"
			          cssStyle = "width: 120px;margin-left: 3px;margin-top:-31px;height:27px"
			          theme="simple"
			          onchange="getSearchData();"
         			 />
         			 <s:select 
                      id="feedBy" 
			          name="feedBy" 
			          list="feedByMap"
			          headerKey="-1"
			          headerValue="Patient Name"
			          cssClass="button"
			          cssStyle = "width: 120px;margin-left: 3px;margin-top:-31px;height:27px"
			          theme="simple"
			          onchange="getSearchData();"
         			 />
					         </td></tr></tbody>
					  </table>
				  </td>
				  
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				  <div id="sendResponse" style="display: none">
				  	<%if(true)
				      {
					%><sj:submit cssStyle="height:27px;" value="Communicate" button="true" onClickTopics="getResolvedAckPage" cssClass="button"/>
					<%	}%>
				  </div>
				      
				       </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<div style="overflow: scroll; height: 430px;">
<div id="feedTicket">
</div>
</div>
</div>
</div>
<div id="ab"></div>
</body>
</html>