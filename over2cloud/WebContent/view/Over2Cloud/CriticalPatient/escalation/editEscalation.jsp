<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true"  jquerytheme="mytheme" customBasepath="%{contextz}"/>
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>

<title>Insert title here</title>
<script type="text/javascript">

$.subscribe('makeEffect', function(event,data)
		  {
			 setTimeout(function(){ $("#complTarget").fadeIn(); }, 10);
			 setTimeout(function(){ $("#complTarget").fadeOut(); }, 400);
		  });
function selectAllotTo()
{
		var l1PreSelectedId = $("#selectEscCont1").val().split(",");
		$("#empL1").val(l1PreSelectedId);
		var l2PreSelectedId = $("#selectEscCont2").val().split(",");
		$("#empL2").val(l2PreSelectedId);
		var l3PreSelectedId = $("#selectEscCont3").val().split(",");
		$("#empL3").val(l3PreSelectedId);
		var l4PreSelectedId = $("#selectEscCont4").val().split(",");
		$("#empL4").val(l4PreSelectedId);
}	

/* function hideEsc()
{
	var escValue =  $("#escalation").val();
	if(escValue=="Y")
	{
		$("#showEscDiv").show();
	}
	else
	{
		$("#showEscDiv").hide();
	}
} */

function dselectBelowEsc(esc2,esc3,esc4,esc5)
{
	var selectedContactId = 0;
	var subDept=$("#subDept").val();
	if(esc2=='empL1')
	{
		$("#empL2 option:selected").removeAttr("selected");
		$("#empL3 option:selected").removeAttr("selected");
		$("#empL4 option:selected").removeAttr("selected");
		$("#l3EscDuration" ).datepicker( 'setDate', null);
		$("#l4EscDuration" ).datepicker( 'setDate', null);
		$("#l5EscDuration" ).datepicker( 'setDate', null);
		
		selectedContactId = $("#empL1").val();
		$.ajax({
			type :"post",
			url : "/over2cloud/view/Over2Cloud/referral/escalation_config/getNextEscMap4Edit.action?id="+selectedContactId+"&subDept="+subDept,
			success : function(empData){
			$('#empL2 option').remove();
			$('#empL3 option').remove();
			$('#empL4 option').remove();
			
	    	 $(empData).each(function(index)
			{
			   $('#empL2').append($("<option></option>").attr("value",this.ID).text(this.NAME));
			   $('#empL3').append($("<option></option>").attr("value",this.ID).text(this.NAME));
			   $('#empL4').append($("<option></option>").attr("value",this.ID).text(this.NAME));
			});
		    },
		    error : function () {
				alert("Somthing is wrong to get Data");
			}
		});
	}
	else if(esc3=='empL2')
	{
		$("#empL3 option:selected").removeAttr("selected");
		$("#empL4 option:selected").removeAttr("selected");
		$("#l4EscDuration" ).datepicker( 'setDate', null);
		$("#l5EscDuration" ).datepicker( 'setDate', null);
		selectedContactId = $("#empL1").val();
		selectedContactId = selectedContactId+","+$("#empL2").val();
		$.ajax({
			type :"post",
			url : "/over2cloud/view/Over2Cloud/referral/escalation_config/getNextEscMap4Edit.action?id="+selectedContactId+"&subDept="+subDept,
			success : function(empData){
			$('#empL3 option').remove();
			$('#empL4 option').remove();
			
	    	$(empData).each(function(index)
			{
			   $('#empL3').append($("<option></option>").attr("value",this.ID).text(this.NAME));
			   $('#empL4').append($("<option></option>").attr("value",this.ID).text(this.NAME));
			});
		    },
		    error : function () {
				alert("Somthing is wrong to get Data");
			}
		});
		
	}
	else if(esc4=='empL3')
	{
		$("#empL4 option:selected").removeAttr("selected");
		$("#l5EscDuration" ).datepicker( 'setDate', null);
		selectedContactId = $("#empL1").val();
		selectedContactId = selectedContactId+","+$("#empL2").val()+","+$("#empL3").val();
		$.ajax({
			type :"post",
			url : "/over2cloud/view/Over2Cloud/referral/escalation_config/getNextEscMap4Edit.action?id="+selectedContactId+"&subDept="+subDept,
			success : function(empData){
			$('#empL4 option').remove();
			
	    	$(empData).each(function(index)
			{
			   $('#empL4').append($("<option></option>").attr("value",this.ID).text(this.NAME));
			});
		    },
		    error : function () {
				alert("Somthing is wrong to get Data");
			}
		});
	}
	
}

function backView()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax(
	{
		type : "post",
		url : "/over2cloud/view/Over2Cloud/Referral/escalation/escalationViewGrid.jsp",
		success : function(data)
		{
			$("#" + "data_part").html(data);
		},
		error : function()
		{
			alert("error");
		}
	});
}


selectAllotTo();
</script>
<style type="text/css">
	
</style>
</head>
<body>

<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Referral Escalation</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Edit</div> 
</div>
<div class="clear"></div>

</div>
<div style="overflow-x:hidden; height:450px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">

<s:form id="EditEscalation" name="EditEscalation" action="EditEscalationAction" namespace="/view/Over2Cloud/referral/escalation_config" theme="simple"  method="post" enctype="multipart/form-data" >
	<s:hidden name="id" value="%{id}"/>
	<s:hidden id="subDept" value="%{subDept}"/>
    <s:hidden id="selectEscCont1" value="%{selectedEsc1Cont}"/> 
    <s:hidden id="selectEscCont2" value="%{selectedEsc2Cont}"/> 
    <s:hidden id="selectEscCont3" value="%{selectedEsc3Cont}"/> 
    <s:hidden id="selectEscCont4" value="%{selectedEsc4Cont}"/> 
    
    <div class="clear"></div>
    <div class="newColumn">
       <div class="leftColumn">L2:&nbsp;</div>
       <div class="rightColumn">
       		<s:select  
                   	id			="empL1"
                   	name		="l2"
                   	list		="escContMap1"
                   	multiple	="true"
                   	cssClass	="select"
					cssStyle	="width:82%;height:40%"
					onchange	="dselectBelowEsc('empL1','','','')"
             />
       </div>
    </div>
    
    <div class="newColumn">
       <div class="leftColumn">L 2 TAT:&nbsp;</div>
       <div class="rightColumn">
       		<sj:datepicker id="l2EscDuration" name="tat2" value="%{tat2}" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="1" cssClass="textField"/>
       </div>
    </div>
    
    <div class="clear"></div>
    
    				<div class="newColumn">
        			   <div class="leftColumn" >From Time:&nbsp;</div>
					        <div class="rightColumn">
				                   <sj:datepicker placeholder="From Time" cssStyle="margin:0px 0px 10px 0px"  value="%{fromTime}" cssClass="textField" timepickerOnly="true" timepicker="true" timepickerAmPm="false" id="fromTime" name="fromTime" size="20"   readonly="false"   showOn="focus"/>
                           </div>
                   </div>
                   
                    <div class="newColumn">
        			   <div class="leftColumn" >To Time:&nbsp;</div>
					        <div class="rightColumn">
				                   <sj:datepicker placeholder="To Time" cssStyle="margin:0px 0px 10px 0px"  value="%{toTime}" cssClass="textField" timepickerOnly="true" timepicker="true" timepickerAmPm="false" id="toTime" name="toTime" size="20"   readonly="false"   showOn="focus"/>
                           </div>
                   </div>
                   
                    <div class="newColumn">
        			   <div class="leftColumn" >First Escalation Time:&nbsp;</div>
					        <div class="rightColumn">
				                   <sj:datepicker placeholder="First Escalation Time" cssStyle="margin:0px 0px 10px 0px"  value="%{firstEsc}" cssClass="textField" timepickerOnly="true" timepicker="true" timepickerAmPm="false" id="firstEsc" name="firstEsc" size="20"   readonly="false"   showOn="focus"/>
                           </div>
                   </div>
                   
                   <div class="newColumn">
        			   <div class="leftColumn" >Escalation Time Flag:&nbsp;</div>
					        <div class="rightColumn">
				                  <s:select  id="firstEscFlag"
				                              name="firstEscFlag"
				                              list="#{'1':'Active','0':'Deactive'}"
				                              cssClass="select"
					                          cssStyle="width: 80%"
					                             
				                              >
				         			</s:select>
                           </div>
                   </div>
                    <div class="clear"></div>
   
    
    <div class="newColumn">
       <div class="leftColumn">L3:&nbsp;</div>
       <div class="rightColumn">
       		<s:select  
                   	id			="empL2"
                   	name		="l3"
                   	list		="escContMap2"
                   	multiple	="true"
                   	cssClass	="select"
					cssStyle	="width:82%;height:40%"
					onchange	="dselectBelowEsc('','empL2','','')"
             />
       </div>
    </div>
    
    <div class="newColumn">
       <div class="leftColumn">L 3 TAT:&nbsp;</div>
       <div class="rightColumn">
       		<sj:datepicker id="l3EscDuration" name="tat3" value="%{tat3}" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="1" cssClass="textField"/>
       </div>
    </div>
    
    <div class="clear"></div>
    <div class="newColumn">
       <div class="leftColumn">L4:&nbsp;</div>
       <div class="rightColumn">
       		<s:select  
                   	id			="empL3"
                   	name		="l4"
                   	list		="escContMap3"
                   	multiple	="true"
                   	cssClass	="select"
					cssStyle	="width:82%;height:40%"
					onchange	="dselectBelowEsc('','','empL3','')"
             />
       </div>
    </div>
    
    <div class="newColumn">
       <div class="leftColumn">L 4 TAT:&nbsp;</div>
       <div class="rightColumn">
       		<sj:datepicker id="l4EscDuration" name="tat4" value="%{tat4}" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="1" cssClass="textField"/>
       </div>
    </div>
    
    <div class="clear"></div>
    <div class="newColumn">
       <div class="leftColumn">L5:&nbsp;</div>
       <div class="rightColumn">
       		<s:select  
                   	id			="empL4"
                   	name		="l5"
                   	list		="escContMap4"
                   	multiple	="true"
                   	cssClass	="select"
					cssStyle	="width:82%;height:40%"
             />
       </div>
    </div>
    
    <div class="newColumn">
       <div class="leftColumn">L 5 TAT:&nbsp;</div>
       <div class="rightColumn">
       		<sj:datepicker id="l5EscDuration" name="tat5" value="%{tat5}" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="1" cssClass="textField"/>
       </div>
    </div>
    
     <div class="clear"></div>
         <div class="clear"></div>
           <br>
         <div class="fields">
         <center>
		 <ul>
		 <li class="submit" style="background:none;">
		 <div class="type-button">
	     <sj:submit 
         				targets			=	"complTarget" 
         				clearForm		=	"true"
         				value			=	" Save " 
         				button			=	"true"
         				cssClass		=	"submit"
                 		effect			=	"highlight"
                 		effectOptions	=	"{ color : '#222222'}"
                 		effectDuration	=	"5000"
                 		onCompleteTopics=	"makeEffect"
     		  	  />
     		  	  <sj:a name="Cancel" href="#" cssClass="submit"
							indicator="indicator" button="true"
							onclick="backView()" >
					  	Back
					</sj:a>
					
	      </div>
	      </li>
		  </ul></center>
		  <sj:div id="complTarget"  effect="fold"> </sj:div>
		  </div>
          <center>
	 	  <img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/>
	      </center> 
    
</s:form>
</div>
</div>
</body>
</html>