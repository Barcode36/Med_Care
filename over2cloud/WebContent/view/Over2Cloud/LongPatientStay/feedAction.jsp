<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
 
<script type="text/javascript" src="<s:url value="js/LongPatientStay/longPatientStay.js"/>"></script>
<script type="text/javascript">
function resetForm(formId)
{
	$('#'+formId).trigger("reset");
}
$.subscribe('reload', function(event, data) {

	setTimeout(function(){$("#result1").fadeIn();}, 10);
	setTimeout(function(){$("#result1").fadeOut();getGridViewForTicket();$("#takeActionGrid").dialog('close');}, 4000); 
    
});
function changeDiv(data)
{
	if(data=="Snooze")
	{
		$("#resolveCT").hide();
		$("#snooze").show();
	}
	else
	{
		$("#resolveCT").show();
		$("#snooze").hide();
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
			url : "view/Over2Cloud/LongPatientStay/fetchEmpNameById.action?uhid=" + value,
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


 </script>
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
 
		background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, rgb(211, 211, 211)), color-stop(1, rgb(9, 51, 80)) );
	background:-moz-linear-gradient( center top, #d62a2a 5%, #ffaaaa 100% );
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr="#d62a2a", endColorstr="#ffaaaa");	background: -o-linear-gradient(top,#d62a2a,ffaaaa);

	background-color:#d62a2a;
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
</head>
<body>
<s:form id="formone12" name="formone12" action="actionOnTicket"    method="post" enctype="multipart/form-data">
 <s:hidden name="id" id="id" value="%{#parameters.id}"></s:hidden>
 <s:hidden name="level" id="level" value="%{#parameters.level}"></s:hidden>
 <center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone1" style="float:center; margin-left: 7px"></div></div></center>
 <div style="width: 100%; center; padding-top: 10px;">
       <div class="border" style="height: 50%" id="1">
              <table width="100%" border="1">
	    		<tr  bgcolor="lightgrey" style="height: 25px">
	    			<td align="left" ><strong>UHID:</strong></td>
					<td align="left" ><s:property value="#parameters.uhid"/></td>
					<td align="left" ><strong>Patient Name:</strong></td>
					<td align="left" ><s:property value="#parameters.patName"/></td>
				 	<td align="left" ><strong>Location:</strong></td>
					<td align="left" ><s:property value="%{location}"/></td>
					<td align="left" ><strong>Nursing Unit:</strong></td>
					 <td align="left" ><s:property value="#parameters.nursing"/></td>
			    </tr>
			    <tr  bgcolor="white" style="height: 25px">
			          
			           
			          <td align="left" ><strong>Bed No:</strong></td>
					  <td align="left" ><s:property value="#parameters.bedNo"/></td>
					  <td align="left" ><strong>Adm Doctor:</strong></td>
					  <td align="left" ><s:property value="#parameters.admDoc"/></td>
					  <td align="left" ><strong>Speciality:</strong></td>
					  <td align="left" ><s:property value="#parameters.admSpec"/></td>
					  <td align="left" ><strong>Admission At:</strong></td>
					  <td align="left" ><s:property value="#parameters.admAt"/></td>
			   </tr>
			    
			 </table>
 
<br>

<div class="newColumn"> 									
			       <div class="leftColumn">Status:</div>
			            <div class="rightColumn">
			             <span class="needed"></span> 
			               	<s:select  
					    	id	=	"actionStatus"
					    	name	=	"actionStatus"
					    	list	=	"#{'Close':'Close','Snooze':'Parked'}"
					      	theme	=	"simple"
					      	cssClass	=	"button"
					      	cssStyle	=	"height: 28px;margin-top: 0px;margin-left: -2px;width: 130px;"
					      	onchange	=	"changeDiv(this.value)"
	      					>
						</s:select>
			            </div>
	            </div>
    			<div id="resolveCT"  >
		           	<span class="repIds" style="display: none; ">closeById#Close By Id#T#n,</span>
				 <div class="newColumn"  >
				 <div class="leftColumn">Close By Id:</div>
			 		<div class="rightColumn"  >
						<span class="needed"></span>
					      <s:textfield id="closeById" name="closeById" autoFocus="true" cssClass="textField" theme="simple" placeHolder="Caller Emp ID"   onblur="getDetailsData(this.value,'closeByName','errZone','closeById');resetColor('.pIds');"/>
					</div>
				 </div>
			 		 
			 		 <div class="newColumn"  >
					 <div class="leftColumn">Close By Name:</div>
				 		<div class="rightColumn" >
						      <s:textfield id="closeByName" name="closeByName" cssClass="textField"  placeHolder="Caller Emp Name" readonly="true" theme="simple"/>
						</div>
					 </div>
	            </div>
    			 
	            
	             <div id="snooze" style="display: none;">
	             <span id="parkedTill1" class="sZpIds" style="display: none; ">parkedTill#Parked Till#Date#</span>
	           	  <div class="newColumn"> 
			       <div class="leftColumn">Parked Till:</div>
			            <div class="rightColumn">
			          
			          <sj:datepicker name="parkedTill" id="parkedTill"    timepickerOnly="true"  timepicker="true" timepickerAmPm="false" cssClass="button"   size="15" displayFormat="dd-mm-yy"  readonly="true"   showOn="focus"   Placeholder="Select Time"/>
			            </div>
	            </div>
	            </div>
				
				 	 
				<div class="newColumn"> 		
					<span class="pIds" style="display: none; ">comment#Comment#T#an,</span>							
			       <div class="leftColumn">Comment:</div>
			            <div class="rightColumn">
				                 <s:textfield name="comment" id="comment" placeholder="Enter Comment" cssClass="textField" />
			        </div>
				</div>
   <!-- Buttons -->
   <div class="clear"></div>
   <div class="clear"></div>
   <div class="fields" align="center">
   <center><img id="indicator1" src="<s:url value="/images/ajax-loader_small.gif"/>" alt="Loading..." style="display:none"/></center>
   <ul><li class="submit">
   <div class="type-button">
   <div id="bt" style="display: block;">
   <sj:submit 
   				id ="actionId"
	           targets="result1" 
	           clearForm="true"
	           value="  Save  " 
	           effect="highlight"
	           effectOptions="{ color : '#222222'}"
	           effectDuration="5000"
	           button="true"
	           onBeforeTopics="validateActionForLongPat"
	           onCompleteTopics="reload"
			   />
			          <sj:a cssStyle="margin-left: 203px;margin-top: -26px;" button="true" href="#" onclick="resetForm('formone12');">Reset</sj:a>
			   
   </div>
   </div>
   </li>
   </ul>
   </div>				    
</div>
</div>
<div id="result1"></div>
</s:form>
</body>
</html>