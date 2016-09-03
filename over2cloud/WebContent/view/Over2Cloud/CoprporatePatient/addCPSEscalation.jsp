<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/cpservice/servicesAction.js"/>"></script>
<script type="text/javascript">
$.subscribe('makeEffect', function(event,data)
		  {
			 setTimeout(function(){ $("#resultTarget").fadeIn(); }, 10);
			 setTimeout(function(){ $("#resultTarget").fadeOut();cancelButton();}, 1000);
			 $('#completionResult').dialog('open');
		  });
function cancelButton()
{
	 $('#completionResult').dialog('close');
	 
	 viewEscConfig();
}
function hideBlock(value,divId)
{
	if(value=="Customised")
	{
		$("#"+divId).show();
	}
	else
	{
		$("#"+divId).hide();
	}
}

function viewEscConfig()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/CoprporatePatient/CPSManageEscalation.jsp",
	    success : function(data) {
       		$("#data_part").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
}

function getSubDepartment(deptId){
	var DDid;
	if(deptId=='19')
	{
		DDid='All'
		$("#serviceName").show();
		//$('#'+'escDeptService').html("escsubDept#Sub Department#D#,");
		$('#'+'serviceValidation').html("service_name#Select Service#D#,");
	}
	else
	{
		DDid='All';
		$("#serviceName").hide();
		//$('#'+'escDeptService').html("");
		$('#'+'serviceValidation').html("");
	}
	  $.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Escalation_Conf/depatmentsforlist.action?deptFlag=subdept&deptId="+deptId,
	    success : function(data) {
	    	console.log(data);
       		$('#escsubDept option').remove();
      		$('#escsubDept').append($("<option></option>").attr("value",DDid).text("All Sub Department"));
          	$(data).each(function(index)
      		{
          	//alert(this.id);	
      		   $('#escsubDept').append($("<option></option>").attr("value",this.id).text(this.name));
      		});
       		
       		},
	    error: function() {
            alert("error");
        }
	 });
}

</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Corporate Patient Service Escalation</div><img alt="" src="images/forward.png" style="margin-top:10px; float: left;"><div class="head"> Add</div>
	<%-- <sj:a button="true" cssClass="button" buttonIcon="ui-icon-plus"  style="margin-top:4px; float: right;"  onclick="getOnlinePage()">Online Mode</sj:a> --%> 
	  
</div>
<div class="clear"></div>
<div style="overflow:auto; height:450px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">

<s:form id="formone" name="formone" action="addEscConfig" namespace="/view/Over2Cloud/CorporatePatientServices/cpservices" theme="simple"  method="post" enctype="multipart/form-data" >

<center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone" style="float:center; margin-left: 7px"></div></div></center>  

				<div class="newColumn">
        			   <div class="leftColumn" >Department:&nbsp;</div>
					        <div class="rightColumn">
					          <span  class="pIdsService" style="display: none; ">escDept#Department#D#,</span>
					           <span class="needed"></span>
				                  <s:select  id="escDept"
				                              name="escDept"
				                              list="serviceDeptMap"
				                              headerKey="-1"
				                              headerValue="Select Department" 
				                              cssClass="select"
					                          cssStyle="margin-left: 3px;width: 206px;height: 30px;"
					                          onchange="getSubDepartment(this.value)"
					                         
				                              >
				                  </s:select>
                           </div>
                   </div>
                   <div id="serviceName" style="display: none">
                   <div class="newColumn">
        			   <div class="leftColumn" >Select Service:</div>
					        <div class="rightColumn">
					         <span id="serviceValidation" class="pIdsService" style="display: none; "></span>
					           <span class="needed"></span>
				                  <s:select  id="service_name"
				                              name="service_name"
				                              list="#{'Service':'Service','Mark Arrival':'Mark Arrival'}"
				                               headerKey="-1"
				                              headerValue="Select Service" 
				                              cssClass="select"
					                          cssStyle="margin-left: 3px;width: 206px;height: 30px;"
					                       >
				                  </s:select>
                           </div>
                   </div>
                   </div>
                   	<div class="newColumn">
        			   <div class="leftColumn" >Sub Department:&nbsp;</div>
					        <div class="rightColumn">
				                  <s:select  id="escsubDept"
				                              name="escSubDept"
				                              list="{'No Data'}"
				                              headerKey="-1"
				                              headerValue="All Sub Department" 
				                              cssClass="select"
					                          cssStyle="margin-left: 3px;width: 206px;height: 30px;"
					                         
				                              >
				                  </s:select>
                           </div>
                   </div>
    			   <div class="clear"> </div>
    			    <div class="newColumn">
        			   <div class="leftColumn" >L1 To L2 Time:&nbsp;</div>
					        <div class="rightColumn">
				                  <s:select  id="escLevelL1L2"
				                              name="escLevelL1L2"
				                              list="#{'Multiplicative':'Multiplicative','Customised':'Customised'}"
				                              cssClass="select"
					                          cssStyle="width: 80%"
					                            onchange="hideBlock(this.value,'block12')"
				                              >
				         			</s:select>
                           </div>
                   </div>
                   
                   <div id="block12" style="display: none">  
                   <div class="newColumn">
        			   <div class="leftColumn" >Time:&nbsp;</div>
					        <div class="rightColumn">
				                   <sj:datepicker placeholder="Enter L1 To L2 Time" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" timepickerOnly="true" timepicker="true" timepickerAmPm="false" id="toTime11" name="l1Tol2" size="20"   readonly="true"   showOn="focus"/>
                           </div>
                   </div>
                   </div>
                   <div class="clear"> </div>
    			  <div class="newColumn">
        			   <div class="leftColumn" >L2 To L3 Time:&nbsp;</div>
					        <div class="rightColumn">
				                  <s:select  id="escLevelL2L3"
				                              name="escLevelL2L3"
				                              list="#{'Multiplicative':'Multiplicative','Customised':'Customised'}"
				                              cssClass="select"
					                          cssStyle="width: 80%"
					                            onchange="hideBlock(this.value,'block23')"
				                              >
				         			</s:select>
                           </div>
                   </div>
                   
                  
                    <div id="block23" style="display: none">  
                   <div class="newColumn">
        			   <div class="leftColumn" >Time:&nbsp;</div>
					        <div class="rightColumn">
				                   <sj:datepicker placeholder="Enter L2 To L3 Time" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" timepickerOnly="true" timepicker="true" timepickerAmPm="false" id="toTime21" name="l2Tol3" size="20"   readonly="true"   showOn="focus"/>
                           </div>
                   </div>
                   </div>
                    <div class="clear"> </div>   
                   <div class="newColumn">
        			   <div class="leftColumn" >L3 To L4 Time:&nbsp;</div>
					        <div class="rightColumn">
				                  <s:select  id="escLevelL3L4"
				                              name="escLevelL3L4"
				                              list="#{'Multiplicative':'Multiplicative','Customised':'Customised'}"
				                              cssClass="select"
					                          cssStyle="width: 80%"
					                          onchange="hideBlock(this.value,'block34')"
				                              >
				         			</s:select>
                           </div>
                   </div>
                   <div id="block34" style="display: none">
                   <div class="newColumn">
        			   <div class="leftColumn" >Time:&nbsp;</div>
					        <div class="rightColumn">
				                   <sj:datepicker placeholder="Enter L3 To L4 Time" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" timepickerOnly="true" timepicker="true" timepickerAmPm="false" id="toTime1" name="l3Tol4" size="20"   readonly="true"   showOn="focus"/>
                           </div>
                   </div>
                   </div>
                    <div class="clear"> </div>
                   <div class="newColumn">
        			   <div class="leftColumn" >L4 To L5 Time:&nbsp;</div>
					        <div class="rightColumn">
				                  <s:select  id="escLevelL4L5"
				                              name="escLevelL4L5"
				                              list="#{'Multiplicative':'Multiplicative','Customised':'Customised'}"
				                              cssClass="select"
					                          cssStyle="width: 80%"
					                          onchange="hideBlock(this.value,'block45')"
				                              >
				         			</s:select>
                           </div>
                   </div>
                   <div id="block45" style="display: none">
                   <div class="newColumn">
        			   <div class="leftColumn" >Time:&nbsp;</div>
					        <div class="rightColumn">
				                   <sj:datepicker placeholder="Enter L4 To L5 Time" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" timepickerOnly="true" timepicker="true" timepickerAmPm="false" id="toTime2" name="l4Tol5" size="20"   readonly="true"   showOn="focus"/>
                           </div>
                   </div>
                   </div>
                    <div class="clear"> </div>
                    <div class="newColumn">
        			   <div class="leftColumn" >L5 To L6 Time:&nbsp;</div>
					        <div class="rightColumn">
				                  <s:select  id="escLevelL5L6"
				                              name="escLevelL5L6"
				                              list="#{'Multiplicative':'Multiplicative','Customised':'Customised'}"
				                              cssClass="select"
					                          cssStyle="width: 80%"
					                          onchange="hideBlock(this.value,'block46')"
				                              >
				         			</s:select>
                           </div>
                   </div>
                   <div id="block46" style="display: none">
                   <div class="newColumn">
        			   <div class="leftColumn" >Time:&nbsp;</div>
					        <div class="rightColumn">
				                   <sj:datepicker placeholder="Enter L5 To L6 Time" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" timepickerOnly="true" timepicker="true" timepickerAmPm="false" id="toTime6" name="l5Tol6" size="20"   readonly="true"   showOn="focus"/>
                           </div>
                   </div>
                   </div>
    			  
          <div class="clear"> </div>   
		 <div class="fields" align="center">
		 <ul>
			<li class="submit" style="background: none;">
				<div class="type-button">
	        	<sj:submit 
         				targets			=	"resultTarget" 
         				clearForm		=	"true"
         				value			=	" Save " 
         				button			=	"true"
         				cssClass		=	"submit"
                 		indicator		=	"indicator2"
                 		effect			=	"highlight"
	                    effectOptions	=	"{ color : '#222222'}"
                 		effectDuration	=	"5000"
                 		onCompleteTopics=	"makeEffect"
                        indicator		=	"indicator2"
                        onBeforeTopics  =   "validateService"
                        
     		  	  />
     		  	  <sj:a 
						button="true" href="#"
						onclick="resetForm('formone');"
						>
						Reset
				  </sj:a>
     		  	  <sj:a 
						button="true" href="#"
						onclick="viewEscConfig();"
						>
						Back
					</sj:a>
	        </div>
	        </li>
		 </ul><!--
		 <sj:div id="resultTarget"  effect="fold">
   	     </sj:div>
		 --></div>
</s:form>
</div>
</div>
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
          <div id="resultTarget"></div>
</sj:dialog>
</body>
</html>