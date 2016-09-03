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
	 
	
	 //viewMainPage();
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
function resetForm(formId) 
{
	 $('#'+formId).trigger("reset");
}

</script>
</head>
 <div style=" 
  margin-left: 37%;
  width: 86%;
  margin-top: -65px;">

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
                  
          <table border='1' cellpadding="10px" rules="rows" width="100%" align="center">
          
			<tr class="color"><h3 style="margin-left: 0%;" > Caller Details</h3>
			
				<td class="tdAlign">
					<b>Emp ID:</b>
				</td>
				
				<td class="tdAlign">
					<s:textfield id="callerId" name="callerId" cssStyle="  height: 75%;width: 74%;"/>
				</td>
				<td class="tdAlign">
					<b>Name:</b>
				</td>
				<td class="tdAlign">
					<s:textfield id="callerName" name="callerName" cssStyle="  height: 75%;width: 74%;"/>
				</td>
				
			</tr>
			</table>
			
			<div class="clear"></div> 
			<div class="clear"></div> 
			<div class="clear"></div> 
			<div class="clear"></div> 
		<table border='1'  cellpadding="10px" rules="rows" width="100%" align="center">
			<tr class="color"><h3 style="margin-left: 0%; margin-top: 5%" > Patient Details</h3>
				<td class="tdAlign">
					<b>UHID:</b>
				</td>
				<td class="tdAlign">
				
					<s:textfield id="UHID" name="UHID" cssStyle="  height: 25px !important;width: 74%;" />
				</td>
				<td class="tdAlign">
					<b>Patient Name:</b>
				</td>
				<td class="tdAlign">
					<s:textfield id="PName" name="PName" cssStyle="  height: 75%;width: 74%;"/>
				</td>
				
			</tr>
			
			<tr class="color" > 
				<td class="tdAlign">
					<b>Bed No.:</b>
				</td>
				<td class="tdAlign">
				
					<s:textfield id="Bed" name="Bed" cssStyle="  height: 75%;width: 74%;"/>
				</td>
				<td class="tdAlign">
					<b>Nursing Unit:</b>
				</td>
				<td class="tdAlign">
					<s:textfield id="NursUnit" name="NursUnit" cssStyle="  height: 75%;width: 74%;" />
				</td>
				
			</tr>
			
			
			<tr class="color">
				<td class="tdAlign">
					<b>Adm. Doc.</b>
				</td>
				<td class="tdAlign">
					<s:textfield id="AdmDoc" name="AdmDoc" cssStyle="  height: 75%;width: 74%;"/>
				</td>
				<td class="tdAlign">
					<b>Adm. Spec.:</b>
				</td>
				<td class="tdAlign">
					<s:textfield id="AdmScec" name="AdmSpec" cssStyle="  height: 75%;width: 74%;"/>
				</td>
			</tr>
		</table>
			
			<div class="clear"></div> 
			<div class="clear"></div> 
			<div class="clear"></div> 
			<div class="clear"></div>
			<table border='1' cellpadding="10px" rules="rows" width="100%" align="center">
          
			<tr class="color"> <h3 style="margin-left: 0%; margin-top: 5%" >Referral Doctor</h3>
			
				<td class="tdAlign">
					<b>Ref. Doctor:</b>
				</td>
				
				<td class="tdAlign">
					<s:textfield id="refDoc" name="refDoc" cssStyle="  height: 75%;width: 74%;"/>
				</td>
				<td class="tdAlign">
					<b>Ref. Spec:</b>
				</td>
				<td class="tdAlign">
					<s:textfield id="refSpec" name="refSpec" cssStyle="  height: 75%;width: 74%;"/>
				</td>
				
			</tr>
		</table>
			
			<div class="clear"></div> 
			<div class="clear"></div> 
			<div class="clear"></div> 
			<div class="clear"></div> 
		<table border='1'  cellpadding="10px" rules="rows" width="100%" align="center">
			<tr class="color" > <h3 style="margin-left: 0%; margin-top: 5%" > Reffered Doctor</h3>
				<td class="tdAlign">
					<b>Ref. Doctor:</b>
				</td>
				<td class="tdAlign">
				
					<s:textfield id="refferedDoc" name="refferedDoc" cssStyle="  height: 75%;width: 74%;" />
				</td>
				<td class="tdAlign">
					<b>Ref. Spec:</b>
				</td>
				<td class="tdAlign">
					<s:textfield id="refferedSpec" name="refferedSpec" cssStyle="  height: 75%;width: 74%;"/>
				</td>
				
			</tr>
			
			<tr class="color" > 
				<td class="tdAlign">
					<b>Ref Sub Spec:</b>
				</td>
				<td class="tdAlign">
				
					<s:textfield id="refferedSubSpec" name="refferedSubSpec" cssStyle="  height: 75%;width: 74%;"/>
				</td>
				<td class="tdAlign">
					<b>Priority:</b>
				</td>
				<td class="tdAlign">
					<s:select id="priority" name="priority" list="#{'Routine':'Routine','Urgent':'Urgent','Stat':'Stat'}"  
			theme="simple"	cssClass="button"  />
				</td>
				
			</tr>
			
			
			<tr class="color">
				<td class="tdAlign">
					<b>Reason:</b>
				</td>
				<td class="tdAlign">
					<s:textfield id="reason" name="reason" cssStyle="  height: 125%;width: 222%;"/>
				</td>
				<td class="tdAlign">
					<b></b>
				</td>
				<td class="tdAlign">
					
				</td>
			</tr>
		</table>
		
             	
				<center><img id="indicator23" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				 <div class="clear"></div> 
				<center>
					<div class="type-button">
	                <sj:submit 
                            targets	="result" 
                            clearForm="true"
                            value="ADD " 
                            effect="highlight"
                            effectOptions="{ color : '#FFFFFF'}"
                            effectDuration="100"
                            button="true"
                            onCompleteTopics="level"
                            cssClass="submit"
                            indicator="indicator23"
                            openDialog="completionResult"
                            />
                            
                            &nbsp;&nbsp;
                        <sj:submit 
                             value="Reset" 
                             button="true"
                             cssStyle="margin-left: -2px;margin-top: 0px;"
                             onclick="resetForm('formone');resetColor('.pIds');"
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


