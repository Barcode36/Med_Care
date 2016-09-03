<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<script type="text/javascript" src="<s:url value="/js/order/feedback.js"/>"></script>
<style type="text/css">
.datagrid table { border-collapse: collapse; text-align: left; width: 100%; } .datagrid {font: normal 12px/150% Arial, Helvetica, sans-serif; background: #fff; overflow: hidden; border: 2px solid #006699; -webkit-border-radius: 20px; -moz-border-radius: 20px; border-radius: 20px; }.datagrid table td, .datagrid table th { padding: 2px 6px; }.datagrid table thead th {background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #006699), color-stop(1, #00557F) );background:-moz-linear-gradient( center top, #006699 5%, #00557F 100% );filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#006699', endColorstr='#00557F');background-color:#006699; color:#FFFFFF; font-size: 15px; font-weight: bold; border-left: 4px solid #0070A8; } .datagrid table thead th:first-child { border: none; }.datagrid table tbody td { color: #00557F; border-left: 5px solid #E1EEF4;font-size: 13px;font-weight: bold; }.datagrid table tbody .alt td { background: #E1EEf4; color: #00557F; }.datagrid table tbody td:first-child { border-left: none; }.datagrid table tbody tr:last-child td { border-bottom: none; }
</style>

<STYLE type="text/css">
.CSSTableGenerator {
	margin:0px;padding:0px;
	width:100%;
 
	border:1px solid #0d0f0d;
	
	-moz-border-radius-bottomleft:0px;
	-webkit-border-bottom-left-radius:0px;
	border-bottom-left-radius:0px;
	
	-moz-border-radius-bottomright:0px;
	-webkit-border-bottom-right-radius:0px;
	border-bottom-right-radius:0px;
	
	-moz-border-radius-topright:0px;
	-webkit-border-top-right-radius:0px;
	border-top-right-radius:0px;
	
	-moz-border-radius-topleft:0px;
	-webkit-border-top-left-radius:0px;
	border-top-left-radius:0px;
}.CSSTableGenerator table{
    border-collapse: collapse;
        border-spacing: 0;
	width:100%;
	height:100%;
	margin:0px;padding:0px;
}.CSSTableGenerator tr:last-child td:last-child {
	-moz-border-radius-bottomright:0px;
	-webkit-border-bottom-right-radius:0px;
	border-bottom-right-radius:0px;
}
.CSSTableGenerator table tr:first-child td:first-child {
	-moz-border-radius-topleft:0px;
	-webkit-border-top-left-radius:0px;
	border-top-left-radius:0px;
}
.CSSTableGenerator table tr:first-child td:last-child {
	-moz-border-radius-topright:0px;
	-webkit-border-top-right-radius:0px;
	border-top-right-radius:0px;
}.CSSTableGenerator tr:last-child td:first-child{
	-moz-border-radius-bottomleft:0px;
	-webkit-border-bottom-left-radius:0px;
	border-bottom-left-radius:0px;
}.CSSTableGenerator tr:hover td{
	background-color:#ffdbdb;
}
.CSSTableGenerator td{
	vertical-align:middle;
	background-color:#ffffff;
	border:1px solid #0d0f0d;
	border-width:0px 1px 1px 0px;
	text-align:center;
	padding:4px;
	font-size:10px;
	font-family:Arial;
	font-weight:bold;
	color:#000000;
}.CSSTableGenerator tr:last-child td{
	border-width:0px 1px 0px 0px;
}.CSSTableGenerator tr td:last-child{
	border-width:0px 0px 1px 0px;
}.CSSTableGenerator tr:last-child td:last-child{
	border-width:0px 0px 0px 0px;
}
.CSSTableGenerator tr:first-child td{
 
		background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, rgba(27, 82, 239, 0.25)), color-stop(1, rgb(9, 60, 80)) );
	background:-moz-linear-gradient( center top, #d62a2a 5%, #ffaaaa 100% );
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr="#d62a2a", endColorstr="#ffaaaa");	background: -o-linear-gradient(top,#d62a2a,ffaaaa);

	background-color:rgba(10, 38, 88, 0.07);
	border:0px solid #0d0f0d;
	text-align:center;
	border-width:0px 0px 1px 1px;
	font-size:14px;
	font-family:Arial;
	font-weight:bold;
	color:#edf7f2;
}
.CSSTableGenerator tr:first-child:hover td{
	background:-o-linear-gradient(bottom, #d62a2a 5%, #ffaaaa 100%);	background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #d62a2a), color-stop(1, #ffaaaa) );
	background:-moz-linear-gradient( center top, #d62a2a 5%, #ffaaaa 100% );
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr="#d62a2a", endColorstr="#ffaaaa");	background: -o-linear-gradient(top,#d62a2a,ffaaaa);

	background-color:#d62a2a;
}
.CSSTableGenerator tr:first-child td:first-child{
	border-width:0px 0px 1px 0px;
}
.CSSTableGenerator tr:first-child td:last-child{
	border-width:0px 0px 1px 1px;
}
</STYLE>
<SCRIPT type="text/javascript"> 
	function checkValidData(receivedValue,id) 
	{
	   	$("#rec_"+id).val(receivedValue);
	  	var orderedValue=$("#ordId_"+id).val();
		var dispatchValue=$("#disId_"+id).val();
 		if(orderedValue < receivedValue ||  receivedValue > dispatchValue )
		{
 		 $("#recId_"+id).focus();
         $("#recId_"+id).css("background-color","#ff701a");
 		}
 		else if( receivedValue < dispatchValue )
		{
 			$("#actionStatus").val("Dispatch Error"); 
 			$("#recId_"+id).css("background-color","white");
 		}
 	 
 		else
 		{
 			$("#recId_"+id).css("background-color","white");
 	 	}
		
	}
  
function getDetailsData(value,divId,divId1,errDiv,sourceDiv) {
	value=value.toUpperCase();
  	if(value=='NA')
	{
		$("#"+divId).attr("readonly", false);
		$("#"+divId).focus();
	 
	}	
	else
	{
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/CorporatePatientServices/Pharmacy/fetchEmpNameById.action?uhid=" + value,
			success : function(data) {
		  			if(data[0].fName==undefined)
					{
						$("#"+errDiv).html("Please Enter Valid Emp ID!!!");
						$("#" + sourceDiv).css("background-color", "#ff701a");
						setTimeout(function() {$("#"+errDiv).fadeIn();}, 10);
						setTimeout(function() {$("#"+errDiv).fadeOut();}, 4000);
					}	
					else
					{
						$("#" + divId).val(data[0].fName+ " " + data[0].lName);
						/*$("#" + divId1).val(data[0].mobile);*/
					}	
				 
			},
			error : function() {
				alert("error");
			}
		});
	}	
}




$.subscribe('reload', function(event, data) {

	setTimeout(function(){$("#result1").fadeIn();}, 10);
	setTimeout(function(){$("#result1").fadeOut();ORDERActivityRefresh();$("#takeActionGrid").dialog('close');}, 2000); 
    
});
  
 

 </script>
</head>
<body onload="StopRefresh();">
 <div class="clear"></div>
<div class="middle-content">
 <div class="clear"></div>
<div style="overflow:auto; height:auto; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
 
	 		<s:form id="formone1" name="formone" action="actionOnPharmacyOrderLodge"  theme="css_xhtml"  method="post" enctype="multipart/form-data">
	  	    <s:hidden name="complianId" id="complianId" value="%{complianId}"></s:hidden>
	  	      <s:hidden name="nursingCode" id="nursingCode" value="%{nursingCode}"></s:hidden>
	  	    <s:hidden name="encounterId" id="encounterId" value="%{encounterId}"></s:hidden>
		    <s:hidden id="feedStatus" name="feedStatus" value="%{feedStatus}"></s:hidden>
	 	     <s:hidden id="uhid" name="uhid" value="%{uhid}"></s:hidden>
	 	 	 <center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone1" style="float:center; margin-left: 7px"></div></div></center>
	 		  <table border="1" width="100%" style="border-collapse: collapse;">
	    			<tr  style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='UHID' || key=='Ticket No'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr bgcolor="#D8D8D8" style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='Bed No' || key=='Patient Name'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr  style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='Order At' || key=='Nursing Unit'">
							<td  width="10%"><b><s:property value="%{key}"/>: <b></td>
			 				<td width="10%"><s:property value="%{value}"/></td>
		 		 		</s:if>
						 
					</s:iterator>
				</tr>
				<tr bgcolor="#D8D8D8" style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='Status' || key=='Document No'">
							<td  width="10%"><b><s:property value="%{key}"/>: <b></td>
			 				<td width="10%"><s:property value="%{value}"/></td>
			 		 	</s:if>
						 
					</s:iterator>
				</tr>
				
				<s:if test="feedStatus=='Close' || feedStatus=='Close-P'">
				<tr bgcolor="#D8D8D8" style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='Action At' || key=='Action By'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				 
				</s:if>
 		</table>
 		
 	 
 		
 		 	
	<s:if test="feedStatus!='Close' && feedStatus!='Close-P' && feedStatus=='Ordered' ">	    
	      	<div style="height:14px; margin-bottom:-14px;" id='1stBlock'></div>
          	 <table class="CSSTableGenerator">
	 	 	 	<tr><td width="100%"><font face="Arial, Helvetica, sans-serif" color="white" size="2"><B> Ordered Medicine Status</B></font> </td></tr>
	   	   		<s:iterator value="medicineList" var="listVal" status="status">
	   	   		<tr>
	   	 		<s:if test="#listVal[2]=='Dispatch'">
			 		<td width="100%"><s:checkbox theme="simple" disabled="true"  name="check_id" id="%{#listVal[0]}" value="1"  fieldValue="%{#listVal[0]}"/><s:property value="%{#listVal[1]}"/></td>
 	 	  		</s:if>
	 	  		<s:if test="#listVal[2]=='Pending'">
			 		<td width="100%"><s:checkbox theme="simple" disabled="true"  name="check_id" id="%{#listVal[0]}"  fieldValue="%{#listVal[0]}"/><s:property value="%{#listVal[1]}"/></td> 
 	 	  		</s:if>
	 	  		</tr>
	 	    	</s:iterator>
	   		</table>
     </s:if>
    
     <s:if test="feedStatus!='Close' && feedStatus!='Close-P' && feedStatus!='Ordered' ">	    
	      	<div style="height:14px; margin-bottom:-14px;" id='1stBlock'></div>
        	<div style="height:20%; margin-bottom:2px;" align="center"><font face="Arial, Helvetica, sans-serif" color="blue" size="2"><B> Ordered Medicine Status</B></font> </div>
	 	 	 <table class="CSSTableGenerator">
	   	 	 
	   	 		<tr >
	   	 		<td width="40%">Medicine</td>
	   	 		<td width="20%">Ordered Qty</td>
	   	 		<td width="20%">Dispatch Qty</td>
	   	 		<td width="20%">Received Qty</td>
	   	 		</tr>
	   	 	 
	   	 	 
	   	 		<s:iterator value="medicineList" var="listVal" status="status">
	   	   		<tr>
	   	 		<s:if test="#listVal[2]=='Dispatch'">
	   	 		<s:hidden name="medicine_order_id" id="medicine_order_id_%{#status.count}" value="%{#listVal[0]}" ></s:hidden>
	   	 			<s:hidden name="disId" id="dis_%{#status.count}" value="%{#listVal[4]}" ></s:hidden>
	   	 			<s:hidden name="recId" id="rec_%{#status.count}" value="%{#listVal[4]}" ></s:hidden>
			 		<td width="40%"><s:checkbox theme="simple" disabled="true"  name="check_id_%{#status.count}" id="%{#listVal[0]}" value="1"  fieldValue="%{#listVal[0]}"/><s:property value="%{#listVal[1]}"/></td>
			 		<td width="20%" align="center"><s:textfield id="ordId_%{#status.count}"    value="%{#listVal[3]}" cssStyle="color: blue;width:60px;text-align: center;" disabled="true"  cssClass="textField" theme="simple"   /></td>
				 	<td width="20%" align="center"><s:textfield id="disId_%{#status.count}"   value="%{#listVal[4]}" cssStyle="color: blue;width:60px;text-align: center;" disabled="true"   cssClass="textField" theme="simple"   /></td>
				 	<td width="20%" align="center"><s:textfield id="recId_%{#status.count}"    value="%{#listVal[4]}" cssStyle="color: blue;width:60px;text-align: center;"   autoFocus="true" cssClass="textField" onblur="checkValidData(this.value,'%{#status.count}');" theme="simple" /></td>
 	 	  		</s:if>
	 	  	
	 	  		<s:if test="#listVal[2]=='Pending'">
	 	  			<s:hidden name="medicine_order_id" id="medicine_order_id_%{#status.count}" value="%{#listVal[0]}" ></s:hidden>
	 	  			<s:hidden name="disId" id="dis_%{#status.count}" value="%{#listVal[4]}" ></s:hidden>
	 	  			<s:hidden name="recId" id="rec_%{#status.count}" value="%{#listVal[4]}" ></s:hidden>
			 		<td width="40%"><s:checkbox theme="simple" disabled="true"  name="check_id_%{#status.count}" id="%{#listVal[0]}"  fieldValue="%{#listVal[0]}"/><s:property value="%{#listVal[1]}"/></td> 
	 	  			<td width="20%" align="center"><s:textfield id="ordId_%{#status.count}"    value="%{#listVal[3]}"  disabled="true" cssStyle="color: blue;width:60px;text-align: center;"  cssClass="textField" theme="simple"   /></td>
				 	<td width="20%" align="center"><s:textfield id="disId_%{#status.count}"     value="%{#listVal[4]}"  disabled="true" cssStyle="color: blue;width:60px;text-align: center;"   cssClass="textField" theme="simple"  /></td>
				 	<td width="20%" align="center"><s:textfield id="recId_%{#status.count}"    disabled="true" value="NA"   cssStyle="color: blue;width:60px;text-align: center;" autoFocus="true" cssClass="textField" theme="simple" /></td>
	  	  		</s:if>
	 	  		</tr>
	 	    	</s:iterator>
	 	  	 
	 	</table>
    
    </s:if>
    <s:if test="feedStatus=='Dispatch' || feedStatus=='Dispatch-P' ">
    
     
	         <s:if test="feedStatus=='Dispatch'"> 
	       	 	<s:hidden id="actionStatus" name="actionStatus" value="Close"></s:hidden>
	        </s:if>
	        
	        <s:if test="feedStatus=='Dispatch-P'"> 
	       	 	<s:hidden id="actionStatus" name="actionStatus" value="Close-P"></s:hidden>
	        </s:if>
	        
	   			<span class="pIds1" style="display: none; ">closeById#Close By Id#T#sc,</span>
				 <div class="newColumn"  >
				 <div class="leftColumn">Close By Id:</div>
			 		<div class="rightColumn"  >
						<span class="needed"></span>
					      <s:textfield
								id="closeById" name="closeById" autoFocus="true" cssClass="textField" theme="simple" placeHolder="Caller Emp ID"   onblur="getDetailsData(this.value,'closeByName','errZone','closeById');resetColor('.pIds');"/>
					</div>
				 </div>
			 		 
			 		 <div class="newColumn"  >
					 <div class="leftColumn">Close By Name:</div>
				 		<div class="rightColumn" >
						      <s:textfield id="closeByName" name="closeByName" cssClass="textField"  placeHolder="Caller Emp Name" readonly="true" theme="simple"/>
						</div>
					 </div>
			 
			 
                 <div class="newColumn">
		       <div class="leftColumn">Comment:</div>
		            <div class="rightColumn">
		                 
			                 <s:textfield name="comment" id="comment" placeholder="Enter Comment"  cssClass="textField" />
		            </div>
               </div>
   		</s:if>
    
    <s:if test="feedStatus=='Ordered'">
	  <s:hidden id="actionStatus" name="actionStatus" value="Parked"></s:hidden>
	         <span class="pIds1" style="display: none; ">snoozeTime#Parked Time#Time#</span>
	         <div class="newColumn">
		       <div class="leftColumn">Parked Time:</div>
		            <div class="rightColumn">
		                  	 <span class="needed"></span>
			                 <sj:datepicker id="snoozeTime" name="snoozeTime" readonly="false" placeholder="Enter Parked Time"  showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerMinuteText="true" timepickerGridMinute="10" timepickerStepMinute="5" cssClass="textField"/>
		            </div>
               </div>
               <span class="pIds1" style="display: none; ">comment#Reason#T#sc,</span>
                 <div class="newColumn">
		       <div class="leftColumn">Reason:</div>
		            <div class="rightColumn">
		                	<span class="needed"></span>
			                 <s:textfield name="comment" id="comment" placeholder="Enter Reason"  cssClass="textField" />
		            </div>
               </div>
   		</s:if>
    
    
      <div class="clear"></div>
		<div class="clear"></div>
		    
		   
    <div class="fields" align="center">
   <center><img id="indicator1" src="<s:url value="/images/ajax-loader_small.gif"/>" alt="Loading..." style="display:none"/></center>
   <ul><li class="submit">
   <div class="type-button">
   <div id="bt" style="display: block;">
   
  	      
   		<sj:submit 
   				id ="actionId1"
	           targets="result1" 
	           clearForm="true"
	           value="Save" 
	           effect="highlight"
	           effectOptions="{ color : '#222222'}"
	           effectDuration="5000"
	           button="true"
	           onCompleteTopics="reload"
	           onBeforeTopics="validateActionForPharma"
	          	cssStyle=" margin-top: 12px;"
			   />
	   
   </div>
   </div>
   </li>
   </ul>
   </div>
   <div id="result1"></div>
</s:form>
</div>
</div>
</body>
</html>