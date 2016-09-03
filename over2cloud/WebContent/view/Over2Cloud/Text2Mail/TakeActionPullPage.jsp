<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

<html>
<head>

<script type="text/javascript" src="<s:url value="/js/leaveManagement/validation.js"/>"></script>
<STYLE type="text/css">
	td.tdAlign
	{
		padding: 5px;
}
tr.color
{
	background-color: #E6E6E6;
}
</STYLE>
<SCRIPT type="text/javascript">
$.subscribe('level', function(event,data)
{
	 //alert("jhsvghdvfdhv");
	 setTimeout(function(){ $("#result").fadeIn(); }, 10);
	 setTimeout(function(){ $("#result").fadeOut();
	 cancelButton();
	 },1000);
	 resetTaskType('taskTypeForm');
});
function cancelButton()
{
	 $('#takeActionGrid12').dialog('close');
	 $('#completionResult').dialog('close');
	 
	
	 viewMainPage();
}
 function viewMainPage()
	{
	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	 $.ajax({
			type:"post",
			url :"view/Over2Cloud/Text2Mail/beforePullReportViewHeaderManual.action",
			success : function(data) 
			    {
					$("#"+"data_part").html(data);
			    },
			    error: function() 
			    {
		            alert("error");
		        }
			 });
	} 
function resetTaskType(formId) 
{
	 $('#'+formId).trigger("reset");
}

function chngDiv(data){
	if(data=="inform"){
		$("#inf").show();
		$("#seen").hide();
	}
	if(data=="Seen"){
		$("#seen").show();
		$("#inf").hide();
	}
}


</script>
</head>
 <div style=" float:left; padding:5px 0%; width:100%;">

 <div class="border">
 
  <sj:dialog
          id="completionResult"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title="Confirmation Message"
          cssStyle="overflow:hidden;text-align:center;margin-top:10px;"
          modal="true"
          width="400"
          height="150"
          draggable="true"
    	  resizable="true"
    	   buttons="{ 
    		'Close':function() { cancelButton(); } 
    		}" 
          >
          <div id="result"></div>
</sj:dialog>
		<s:form id="formone" name="formone" namespace="/view/Over2Cloud/Text2Mail" action="TakeActionPullFinal" theme="simple"  method="post" enctype="multipart/form-data" >
		     	 <s:hidden name="id" id="id" value="%{id}" ></s:hidden>
		     	 <s:hidden name="name" id="name" value="%{name}" ></s:hidden>
		     	 <s:hidden name="mobileNo" id="mobileNo" value="%{mobileNo}" ></s:hidden>
		     	 <s:hidden name="speciality" id="speciality" value="%{speciality}" ></s:hidden>
		     	  <s:hidden name="excecutive" id="excecutive" value="%{excecutive}" ></s:hidden>
		     	   <s:hidden name="date" id="date" value="%{date}" ></s:hidden>
		     	    <s:hidden name="location" id="location" value="%{location}" ></s:hidden>
		     	     <s:hidden name="autoAck" id="autoAck" value="%{autoAck}" ></s:hidden>
                  
          <table border='1'  cellpadding="10px" rules="rows" width="80%" align="center">
			<tr class="color">
				<td class="tdAlign">
					<b>Ticket ID:</b>
				</td>
				<td class="tdAlign">
					<s:property value="44143" />
				</td>
				<td class="tdAlign">
					<b>Status:</b>
				</td>
				<td class="tdAlign">
					<s:property value="%{mobileNo}" />
				</td>
			</tr>
			<tr >
				<td class="tdAlign">
					<b>Referred By:</b>
				</td>
				<td class="tdAlign">
				
					Dr. Abhay kumar Verma
				</td>
				<td class="tdAlign">
					<b>Speciality:</b>
				</td>
				<td class="tdAlign">
					Ortho
				</td>
				
			</tr>
			
			<tr class="color">
				<td class="tdAlign">
					<b>Referred To:</b>
				</td>
				<td class="tdAlign">
					Dr. Nagendar Shing
				</td>
				<td class="tdAlign">
					<b>Speciality:</b>
				</td>
				<td class="tdAlign">
					Ortho
				</td>
			</tr>
			<tr >
				<td class="tdAlign">
					<b>Date:</b>
				</td>
				<td class="tdAlign">
					<s:property value="%{date}" />
				</td>
				<td class="tdAlign">
					<b>Location:</b>
				</td>
				<td class="tdAlign">
					B-44143
				</td>
			</tr>
			
			<tr class="color">
				<td class="tdAlign">
					<b>Reason:</b>
				</td>
				<td class="tdAlign">
					<s:property value="%{autoAck}" />
				</td>
				<td class="tdAlign">
					<b>Action:</b>
				</td>
				<td class="tdAlign">
					<s:select id="actionStatus" name="actionStatus" list="#{'-1':'Select Action','inform':'Inform','Seen':'Seen'}"  
			onchange="chngDiv(this.value)" theme="simple"	cssClass="button"  />
				</td>
			</tr>
		</table>
		
		
		<div class="clear"></div> 
		<div class="clear"></div>
		<div id="inf" style="display: none">
		<h3 style="margin-left: 11%;" > Inform Details:</h3>
		<table border='1'  cellpadding="10px" rules="rows" width="80%" align="center">
			<tr class="color" > 
				<td class="tdAlign">
					<b>Informed To:</b>
				</td>
				<td class="tdAlign">
				
					<s:select id="priority" name="priority" list="#{'Yes':'Yes','No':'No'}"  
			theme="simple"	cssClass="button"  />
				</td>
				<td class="tdAlign">
					<b>Informed On:</b>
				</td>
				<td class="tdAlign">
					<sj:datepicker timepicker="true" timepickerOnly="true" readonly="true" name="infOn" id="infOn"  showOn="focus" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10"   cssClass="textField" placeholder="Select From Time"/>
				</td>
				
			</tr>
			
			<tr class="color" > 
				<td class="tdAlign">
					<b>Informed Emp ID:</b>
				</td>
				<td class="tdAlign">
				<sj:autocompleter list="#{'123456789':'123456789','536987':'536987','987456':'987456'}"></sj:autocompleter>
					
				</td>
				<td class="tdAlign">
					<b>Inform To Name:</b>
				</td>
				<td class="tdAlign">
					<s:textfield id="InformToName" name="InformToName" cssStyle="height: 128%;width: 74%;"/>
			
				</td>
				
			</tr>
			
			<tr class="color">
				<td class="tdAlign">
					<b>Remark:</b>
				</td>
				<td class="tdAlign">
					<s:textfield id="remark" name="remark" cssStyle="  height: 125%;width: 222%;"/>
				</td>
				<td class="tdAlign">
					<b></b>
				</td>
				<td class="tdAlign">
					
				</td>
			</tr>
			
			
			
		</table>
		
		</div>
		
		<div id="seen" style="display: none">
		<h3 style="margin-left: 11%;" > Seen Details:</h3>
		<table border='1'  cellpadding="10px" rules="rows" width="80%" align="center">
			<tr class="color" > 
				<td class="tdAlign">
					<b>Seen By EmpID:</b>
				</td>
				<td class="tdAlign">
				
					<s:textfield id="seenEmpId" name="seenEmpId" cssStyle="height: 128%;width: 74%;"/>
				</td>
				<td class="tdAlign">
					<b>Seen By:</b>
				</td>
				<td class="tdAlign">
					<s:textfield id="seenName" name="seenName" cssStyle="height: 128%;width: 74%;"/>
				</td>
				
			</tr>
			
			<tr class="color" > 
				<td class="tdAlign">
					<b>Designation:</b>
				</td>
				<td class="tdAlign">
				
					<s:select id="desig" name="desig" list="#{'-1':'Select','Chairman':'Chairman','Director':'Director','Associate Director':'Associate Director','Consultant':'Consultant','Associate Consultant':'Associate Consultant','Attending Consultant':'Attending Consultant','Resident':'Resident','Medical Doctor':'Medical Doctor','Not Known':'Not Known'}"  theme="simple"	cssClass="button"  />
				</td>
				<td class="tdAlign">
					<b>Referral close By:</b>
				</td>
				<td class="tdAlign">
					<s:select id="desig" name="desig" list="#{'-1':'Select','Doctor':'Doctor','Nurshing':'Nurshing', 'Self':'Self'}"  theme="simple"	cssClass="button"  />
			
				</td>
				
			</tr>
			
			<tr class="color" > 
				<td class="tdAlign">
					<b>Remarks:</b>
				</td>
				<td class="tdAlign">
				<s:textfield id="remarks" name="remarks" cssStyle="  width: 73%;height: 71%;" />
				</td>
				<td class="tdAlign">
					<b>Status:</b>
				</td>
				<td class="tdAlign">
					<s:select id="desig" name="desig" list="#{'-1':'Select','Closed':'Closed','Not Closed':'Not Closed'}"  theme="simple"	cssClass="button"  />
			
				</td>
				
			</tr>
			
			
			
		</table>
		
		</div>
             	
				<center><img id="indicator23" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				 <div class="clear"></div> 
				<center>
					<div class="type-button">
	                <sj:submit 
                            targets			=	"result" 
                            clearForm="true"
                            value="OK " 
                            effect="highlight"
                            effectOptions="{ color : '#FFFFFF'}"
                            effectDuration="100"
                            button="true"
                            onCompleteTopics="level"
                            cssClass="submit"
                            indicator="indicator23"
                            openDialog="completionResult"
                            
                          
                           
                            
                            />
                            
                            
<%--  <sj:submit 
	                        targets="orglevel1Div" 
	                        clearForm="true"
	                        value=" OK " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="level3"
	                        cssClass="submit"
	                        indicator="indicator2"
	                        /> --%>
	                  
	               </div>
	            <%--    <sj:div id="orglevel1"  effect="fold">
                    <div id="orglevem1Div"></div>
               </sj:div> --%>
			</center>
				
	</s:form>	
</div>
</div>
</html>


