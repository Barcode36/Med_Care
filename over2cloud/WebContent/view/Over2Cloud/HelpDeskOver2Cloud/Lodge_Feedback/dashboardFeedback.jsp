
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
		  /* $.subscribe('getStatusForm', function(event,data)
		  {
			  	var feedStatus = document.getElementById("feedStatusValue").value;
				 var feedid    = jQuery("#gridedittable").jqGrid('getGridParam', 'selrow');
				 if(feedid.length==0 )
				{
		     		alert("Please select atleast one check box...");        
		     		 
				}
				else
				{
			  	  $("#feed_status").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
				  $("#feed_status").load("/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/feedAction.action?feedStatus="+feedStatus.split(" ").join("%20")+"&feedId="+feedid.split(" ").join("%20"));
				  $("#feed_status").dialog('open');
			    }
		  });
 */

		  $.subscribe('getFormData', function(event,data)
		  {
			  var feedid   = jQuery("#gridedittable").jqGrid('getGridParam', 'selrow');
			  if(feedid.length==0 )
				{
		     		alert("Please select atleast one row...");        
				}
			  else
			  {
				  $("#printSelect").dialog({title: 'Print Ticket',width: 800,height: 400});
				  $("#printSelect").dialog('open');
				  $("#printSelect").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
				  $("#printSelect").load("/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/printTicketInfo.action?feedId="+feedid );
			  }	  
		  });
		  
		  function downloadFeedStatus()
		  {
			
		 	var status=$("#feedStatus").val();
		  	$("#printSelect").dialog({title: 'Check Column',width: 350,height: 600});
		  	$("#printSelect").dialog('open');
		  	$.ajax({
		  	    type : "post",
		  	    url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/feedbackDownload.action?feedStatus="+status+"&dashFor="+$("#dashFor").val()+"&d_subdept_id="+$("#d_subdept_id").val()+"&dataFlag="+$("#dataFlag").val()+"&level="+$("#level").val()+"&fromDate="+$('#fromDate').val()+"&toDate="+$('#toDate').val()+"&reopen="+$("#reopen").val(),
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
<input type="hidden" id="conString"  value="<%=request.getContextPath()%>"/>
<s:hidden id="feedStatusValue" value="%{feedStatus}"></s:hidden>
<div class="clear"></div>
<div class="middle-content">
<!-- //////////////////////////////////////////// -->
<div class="clear"></div>
<div class="border" style="padding: 0% !important;">

<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
<tbody>
<tr><td> 
<table class="floatL" border="0" cellpadding="0" cellspacing="0">
<tbody>
<tr><td class="pL10"> 
<%-- <s:if test="%{feedStatus=='Pending' || feedStatus=='Snooze' || feedStatus=='High Priority'}">
<sj:submit value=" Take Action " button="true" onClickTopics="getStatusForm"/>
</s:if> --%>

</td></tr>
</tbody>
</table>
<td class="alignright printTitle"><sj:a  button="true" cssClass="button" buttonIcon="ui-icon-print" onClickTopics="getFormData">Print</sj:a>
<sj:a  button="true" cssClass="button" cssStyle="height:24px; width:31px;"  title="Download"   onClick="downloadFeedStatus();" buttonIcon="ui-icon-arrowstop-1-s"></sj:a>
</td></tr>
</tbody>
</table>
</div>
</div>

 <div class="clear"></div>
<s:hidden name="feedStatus" value="%{feedStatus}"></s:hidden>
<s:hidden name="fromDate"     value="%{fromDate}"></s:hidden>
<s:hidden name="toDate"       value="%{toDate}"></s:hidden>
<s:hidden name="dashFor"      value="%{dashFor}"></s:hidden>
<s:hidden name="d_subdept_id" value="%{d_subdept_id}"></s:hidden>
<s:hidden name="dataFlag"     value="%{dataFlag}"></s:hidden>
<s:hidden name="level"     value="%{location}"></s:hidden>
<s:hidden name="reopen"     value="%{reopen}"></s:hidden>
<s:hidden name="checkmeBefore" value="%{dataCheck}"></s:hidden>

<s:url id="href_URLDashboardmgt" action="dashFeedbackDetail" escapeAmp="false" namespace="/view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages">
<s:param name="feedStatus"   value="%{feedStatus}"></s:param>
<s:param name="fromDate"     value="%{fromDate}"></s:param>
<s:param name="toDate"       value="%{toDate}"></s:param>
<s:param name="dashFor"      value="%{dashFor}"></s:param>
<s:param name="d_subdept_id" value="%{d_subdept_id}"></s:param>
<s:param name="dataFlag"     value="%{dataFlag}"></s:param>
<s:param name="level"     value="%{location}"></s:param>
<s:param name="reopen"     value="%{reopen}"></s:param>
<s:param name="checkmeBefore" value="%{dataCheck}"></s:param>
<s:param name="data4" value="%{data4}"></s:param>
<s:param name="dylevel" value="%{dylevel}"></s:param>
</s:url>
<sjg:grid 
		  id="gridedittable"
          href="%{href_URLDashboardmgt}"
          gridModel="dataList"
          dataType="json"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="true"
          rowList="12,25,50"
          rownumbers="12"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="false"  
          loadonce="true"
          loadingText="Requesting Data..."  
          rowNum="12"
          autowidth="true"
          navigatorEditOptions="{height:230,width:500}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="true"
          >

		  <s:iterator value="feedbackColumnNames" id="feedbackColumnNames" >  
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
		      			   frozen="%{frozenValue}"
		      			   
		 				   />
		   </s:iterator>  
</sjg:grid>
</div>
</div>
</body>
</html>


