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
			  if (feedStatus=='Pending') {
				  $("#feed_status").load(connection+"/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/feedAction.action?feedStatus="+feedStatus+"&feedId="+feedid+"&ticketNo="+ticketNo+"&feedbackBy="+feedby.split(" ").join("%20")+"&mobileno="+feedmob.split(" ").join("%20")+"&emailId="+feedemail.split(" ").join("%20")+"&feedBreif="+feedbrief.split(" ").join("%20")+"&openDate="+opendate.split(" ").join("%20")+"&openTime="+opentime.split(" ").join("%20")+"&subCatg="+subcatg.split(" ").join("%20")+"&allotto="+allotto.split(" ").join("%20"));
			  }
			  else if(feedStatus=='Snooze') {
				  $("#feed_status").load(connection+"/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/feedAction.action?feedStatus="+feedStatus+"&feedId="+feedid+"&ticketNo="+ticketNo+"&feedbackBy="+feedby.split(" ").join("%20")+"&mobileno="+feedmob.split(" ").join("%20")+"&emailId="+feedemail.split(" ").join("%20")+"&feedBreif="+feedbrief.split(" ").join("%20")+"&openDate="+opendate.split(" ").join("%20")+"&openTime="+opentime.split(" ").join("%20")+"&subCatg="+subcatg.split(" ").join("%20")+"&allotto="+allotto.split(" ").join("%20"));
			  }
			  else if(feedStatus=='High Priority') {
				  $("#feed_status").load(connection+"/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/feedAction.action?feedStatus="+feedStatus+"&feedId="+feedid+"&ticketNo="+ticketNo+"&feedbackBy="+feedby.split(" ").join("%20")+"&mobileno="+feedmob.split(" ").join("%20")+"&emailId="+feedemail.split(" ").join("%20")+"&feedBreif="+feedbrief.split(" ").join("%20")+"&openDate="+opendate.split(" ").join("%20")+"&openTime="+opentime.split(" ").join("%20")+"&subCatg="+subcatg.split(" ").join("%20")+"&allotto="+allotto.split(" ").join("%20"));
			  }
			  $("#feed_status").dialog('open');
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
				  $("#printSelect").load(connection+"/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/printTicketInfo.action?feedStatus="+feedStatus+"&ticket_no="+ticketNo+"&feedbackBy="+feedby.split(" ").join("%20")+"&mobileno="+feedmob.split(" ").join("%20")+"&open_date="+opendate.split(" ").join("%20")+"&open_time="+opentime.split(" ").join("%20")+"&catg="+catg.split(" ").join("%20")+"&subCatg="+subcatg.split(" ").join("%20")+"&feed_brief="+feedbrief.split(" ").join("%20")+"&allotto="+allotto.split(" ").join("%20")+"&todept="+todept.split(" ").join("%20")+"&tosubdept="+feedtosubdept.split(" ").join("%20")+"&location="+location.split(" ").join("%20")+"&addrDate="+addrdate+"&addrTime="+addrtime);
			  }
			  else if(feedStatus=='Resolved') {
				  var allotto1 = jQuery("#gridedittable").jqGrid('getCell',feedid,'resolve_by');
				  var resolveon = jQuery("#gridedittable").jqGrid('getCell',feedid,'resolve_date');
				  var resolveat = jQuery("#gridedittable").jqGrid('getCell',feedid,'resolve_time');
				  var actiontaken = jQuery("#gridedittable").jqGrid('getCell',feedid,'resolve_remark');
				  var spareused = jQuery("#gridedittable").jqGrid('getCell',feedid,'spare_used');
				  $("#printSelect").load(connection+"/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/printTicketInfo.action?feedStatus="+feedStatus+"&ticket_no="+ticketNo+"&feedbackBy="+feedby.split(" ").join("%20")+"&mobileno="+feedmob.split(" ").join("%20")+"&open_date="+opendate.split(" ").join("%20")+"&open_time="+opentime.split(" ").join("%20")+"&catg="+catg.split(" ").join("%20")+"&subCatg="+subcatg.split(" ").join("%20")+"&feed_brief="+feedbrief.split(" ").join("%20")+"&allotto="+allotto1.split(" ").join("%20")+"&todept="+todept.split(" ").join("%20")+"&tosubdept="+feedtosubdept.split(" ").join("%20")+"&location="+location.split(" ").join("%20")+"&addrDate="+addrdate+"&addrTime="+addrtime+"&resolveon="+resolveon+"&resolveat="+resolveat+"&spareused="+spareused+"&actiontaken="+actiontaken);
			  }
			  $("#printSelect").dialog('open');
		  });



$.subscribe('getMoreData', function(event,data)
		  {
			  var from_Date=document.getElementById("fromDate").value;
			  var to_Date=document.getElementById("toDate").value;
			  var feedStatus = document.getElementById("feedStatusValue").value;
			  $("#gridedittable").jqGrid('setGridParam', {
					url:"/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/viewFeedbackDetail.action?feedStatus="+feedStatus+"&fromDate="+from_Date+"&toDate="+to_Date+"&dataFlag=M"
					,datatype:'JSON'
					}).trigger("reloadGrid");
		  });

</script>
</head>
<body>
<sj:dialog id="printSelect" title="Reprint Ticket" autoOpen="false"  modal="true" height="370" width="700" showEffect="drop">
<sj:dialog id="feed_status" title="Take Action On Feedback" modal="true" effect="slide" autoOpen="false" width="1000" hideEffect="explode" position="['center','top']"></sj:dialog>
<div id="ticketsInfo"></div>
</sj:dialog>
<s:hidden id="feedStatusValue" value="%{feedStatus}"></s:hidden>
<input type="hidden" id="conString"  value="<%=request.getContextPath()%>"/>
<div class="clear"></div>
<div class="middle-content">
<!-- //////////////////////////////////////////// -->
<div class="clear"></div>
<div class="border" style="overflow:auto; height:250px;">
<s:if test="%{feedStatus=='Resolved'}">
<div class="form_menubox">
     <div class="user_form_text">From Date:</div>
     <div class="user_form_input"><sj:datepicker name="fromDate" id="fromDate" value="%{fromDate}" changeMonth="true" changeYear="true" yearRange="2010:2015" cssStyle="margin:0px 0px 10px 0px" showOn="focus" displayFormat="dd-mm-yy" cssClass="form_menu_inputtext" placeholder="Select From Date"/></div>
     <div class="user_form_text1">To Date:</div>
     <div class="user_form_input"><sj:datepicker name="toDate" id="toDate" value="today" changeMonth="true" changeYear="true" yearRange="2010:2015" cssStyle="margin:0px 0px 10px 0px" showOn="focus" displayFormat="dd-mm-yy" cssClass="form_menu_inputtext" placeholder="Select To Date"/></div>
</div>
</s:if>
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
<tbody>
<tr><td> 
<table class="floatL" border="0" cellpadding="0" cellspacing="0">
<tbody>
<tr><td class="pL10"> 
<s:if test="%{feedStatus=='Pending' || feedStatus=='Snooze' || feedStatus=='High Priority'}">
<sj:submit value=" Take Action " button="true" onClickTopics="getStatusForm"/>
</s:if>
<s:elseif test="%{feedStatus=='Resolved'}">
<sj:submit value=" Get Data " button="true" onClickTopics="getMoreData"/>
</s:elseif>
</td></tr>
</tbody>
</table>
<td class="alignright printTitle"><sj:a  button="true" cssClass="button" buttonIcon="ui-icon-print" onClickTopics="getFormData">Print</sj:a>
</td></tr>
</tbody>
</table>
</div>
</div>
 <div class="clear"></div>
<!-- //////////////////////////////////////////////// -->
<s:url id="href_URL" action="dashFeedbackDetail" escapeAmp="false">
<s:param name="feedStatus"   value="%{feedStatus}"></s:param>
<s:param name="fromDate"     value="%{fromDate}"></s:param>
<s:param name="toDate"       value="%{toDate}"></s:param>
<s:param name="dashFor"      value="%{dashFor}"></s:param>
<s:param name="d_subdept_id" value="%{d_subdept_id}"></s:param>
</s:url>
<sjg:grid 
		  id="gridedittable"
          href="%{href_URL}"
          gridModel="feedbackList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="true"
          rowList="10,25,50"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="false"  
          loadonce="%{loadonce}"
          loadingText="Requesting Data..."  
          rowNum="10"
          width="830"
          navigatorEditOptions="{height:230,width:500}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
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