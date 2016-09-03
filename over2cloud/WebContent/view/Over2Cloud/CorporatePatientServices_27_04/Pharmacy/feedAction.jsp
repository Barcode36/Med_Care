<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<script type="text/javascript" src="<s:url value="/js/order/feedback.js"/>"></script>
<SCRIPT type="text/javascript">

$.subscribe('reload', function(event, data) {
    
    ORDERActivityRefresh();
    
});
  
function ShowDivForSnoozeOrder()
{ 	 
	$("#snoozeDialogBox").dialog('open');
 	$("#snoozeDialogBox").dialog({title: 'Order Parked Detail ',width: 500,height: 300});
	 
	 
}
   
 
function closeback(){
	$("#takeActionGrid").dialog('close');
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/CorporatePatientServices/Activity_Board/viewActivityBoardHeaderCPS.action",
	    success : function(feeddraft) {
       $("#"+"data_part").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}

</script>
</head>
<body onload="StopRefresh();">
<sj:dialog
          id="snoozeDialogBox"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Parked Detail"
          modal="true"
          closeTopics="closeEffectDialog"
          width="500"
          height="350"
          >
                   <div class="newColumn">
		       <div class="leftColumn">Parked Time:</div>
		            <div class="rightColumn">
		                  	 <span class="needed"></span>
			                 <sj:datepicker id="snoozeTime" name="snoozeTime" readonly="false" placeholder="Enter Parked Time"  showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="5" cssClass="textField"/>
		            </div>
               </div>
                 <div class="newColumn">
		       <div class="leftColumn">Reason:</div>
		            <div class="rightColumn">
		                	<span class="needed"></span>
			                 <s:textfield name="snoozecomment" id="snoozecomment" placeholder="Enter Reason"  cssClass="textField" />
		            </div>
               </div>
          <div class="clear"></div>
          <div class="clear"></div>
          		
          		
          		<sj:submit 
   				id ="actionId2"
	           clearForm="true"
	           value="Done" 
	           effect="highlight"
	           effectOptions="{ color : '#222222'}"
	           effectDuration="5000"
	           button="true"
	           onCompleteTopics="reload"
	            cssStyle="margin-left: 218px;"
			   />
</sj:dialog>
<div class="clear"></div>
<div class="middle-content">
 <div class="clear"></div>
<div style="overflow:auto; height:416px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
 
	 		<s:form id="formone1" name="formone" action="actionOnOrderLodge"  theme="css_xhtml"  method="post" enctype="multipart/form-data">
			<s:hidden name="old_status" id="old_status" value="%{#parameters.feedStatus}"></s:hidden>
		    <s:hidden name="ticketno" id="ticketno" value="%{#parameters.ticketNo}"></s:hidden>
		    <s:hidden name="feedid" id="feedid" value="%{#parameters.feedId}"></s:hidden>
		    <s:hidden id="feedStatus" name="feedStatus" value="%{#parameters.feedStatus}"></s:hidden>
	 	 	 <center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone1" style="float:center; margin-left: 7px"></div></div></center>
	 		  <table border="1" width="100%" style="border-collapse: collapse;">
	   
				<tr bgcolor="#D8D8D8" style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='Order Id' || key=='Order Name'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr  style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='UHID' || key=='Patient Name'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr bgcolor="#D8D8D8" style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='Nursing Unit' || key=='Room No'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr  style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='Order At'">
							<td  width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td    width="10%"><s:property value="%{value}"/></td>
							<td></td>
							<td></td>
						</s:if>
						 
					</s:iterator>
				</tr>
 		</table>
 		 	
	 	    <div class="clear"></div>
		    <div class="clear"></div>
    
    <div class="fields" align="center">
   <center><img id="indicator1" src="<s:url value="/images/ajax-loader_small.gif"/>" alt="Loading..." style="display:none"/></center>
   <ul><li class="submit">
   <div class="type-button">
   <div id="bt" style="display: block;">
   
  <s:if test="feedStatus=='Ordered'">
   		<sj:submit 
   				id ="actionId"
	           targets="target1" 
	           clearForm="true"
	           value="  Dispatch  " 
	           effect="highlight"
	           effectOptions="{ color : '#222222'}"
	           effectDuration="5000"
	           button="true"
	           onCompleteTopics="reload"
	           onBeforeTopics="validateAction"
	           cssStyle="margin-left: -108px;margin-top: 256px;"
			   />
	         <sj:a cssStyle="margin-left: 71px;margin-top: -25px;" button="true" href="#" onclick="ShowDivForSnoozeOrder();">Parked</sj:a>
	  </s:if>
	  <s:if test="feedStatus=='Dispatch'">
   		<sj:submit 
   				id ="actionId1"
	           targets="target1" 
	           clearForm="true"
	           value="  Close  " 
	           effect="highlight"
	           effectOptions="{ color : '#222222'}"
	           effectDuration="5000"
	           button="true"
	           onCompleteTopics="reload"
	          cssStyle="margin-left: -108px;margin-top: 256px;"
			   />
	  </s:if>
   </div>
   </div>
   </li>
   </ul>
   </div>
   <div id="target1"></div>
</s:form>
</div>
</div>
</body>
</html>