<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript">
function openDiag(id,ticket_no,feedby,feedmob,feedemail,feedbrief,opendate,opentime,subcatg,location,allotto,status)
{
	 var connection=document.getElementById("conString").value;
     if (status=='Pending') {
    	 $("#feed_status").load(connection+"/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/feedAction.action?feedStatus="+status+"&feedId="+id+"&ticketNo="+ticket_no+"&feedbackBy="+feedby.split(" ").join("%20")+"&mobileno="+feedmob.split(" ").join("%20")+"&emailId="+feedemail.split(" ").join("%20")+"&feedBreif="+feedbrief.split(" ").join("%20")+"&openDate="+opendate.split(" ").join("%20")+"&openTime="+opentime.split(" ").join("%20")+"&subCatg="+subcatg.split(" ").join("%20")+"&allotto="+allotto.split(" ").join("%20"));
    		 }
	 $("#feed_status").dialog('open');
}


</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Ticket Detail Via Notification</div>
</div>
<div class="clear"></div>
<div style="overflow:auto; height:410px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
<input type="hidden" id="conString"  value="<%=request.getContextPath()%>"/>
<div class="container">
		  <s:if test="%{notificationData.size>0}">
		  <div class="secHead sent_day"><s:property value="%{status}"/> Tickets Detail</div>
			<div class="sub_container_block">
				<ul>
					 <s:iterator value="notificationData" status="counter">
						<s:if test="#counter.count%2 != 0">
							<li class="notify"><sj:a id="myanchorid" onclick="openDiag('%{id}','%{ticket_no}','%{feed_by}','%{feed_by_mobno}','%{feed_by_emailid}','%{feed_brief}','%{open_date}','%{open_time}','%{subcatg}','%{location}','%{allot_to}','%{status}');"><s:property value="taskBrief"/></sj:a></li>
						</s:if>
						<s:else>
							<li class="notify"><sj:a id="myanchorid1" onclick="openDiag('%{id}','%{ticket_no}','%{feed_by}','%{feed_by_mobno}','%{feed_by_emailid}','%{feed_brief}','%{open_date}','%{open_time}','%{subcatg}','%{location}','%{allot_to}','%{status}');"><s:property value="taskBrief"/></sj:a></li>
						</s:else>
					 </s:iterator>
				</ul>
		    </div>
	  </s:if>
</div>
<sj:dialog id="feed_status"  modal="true" effect="slide" autoOpen="false" width="1100" hideEffect="explode" title="Take Action On Feedback" position="['center','top']"></sj:dialog>
</div>
</div>
</body>
</html>