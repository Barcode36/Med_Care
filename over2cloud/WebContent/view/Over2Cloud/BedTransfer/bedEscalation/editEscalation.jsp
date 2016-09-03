<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script  type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>

<title>Insert title here</title>
<style type="text/css">
.ui-multiselect-hasfilter ul { position:relative; top:2px }
.ui-multiselect-filter { float:left; margin-right:10px; font-size:11px }
.ui-multiselect-filter input { width:100px; font-size:10px; margin-left:5px; height:15px; padding:2px; border:1px solid #292929; -webkit-appearance:textfield; -webkit-box-sizing:content-box; }



.ui-multiselect{
width: 315px !important;
}

</style>
<script type="text/javascript">

$.subscribe('makeEffect', function(event,data)
		  {
			 setTimeout(function(){ $("#complTarget").fadeIn(); }, 10);
			 setTimeout(function(){ $("#complTarget").fadeOut(); }, 400);
		  });



//selectAllotTo();

//  scripted by Aarif



//***********************************************************


$(document).ready(function()
   {

	//alert("hello............"+$("#L1temp").val());
	
   
    
     //for Employee Level1
     $("#empL1").multiselect({
         show: ["explode", 200],
         hide: ["explode", 500]
      });

     var tempL1=  $("#L1temp").val();
     //alert(tempL1);
     var valArrL1 = new Array();
     valArrL1 = tempL1.split(",");
   
      
      /* Below Code Matches current object's (i.e. option) value with the array values */
       /* Returns -1 if match not found */
       $("#empL1").multiselect("widget").find(":checkbox").each(function(){
          if(jQuery.inArray(this.value, valArrL1) !=-1)
                  this.click();

         });

      

       //for Employee Level2
      $("#empL2").multiselect({
         show: ["explode", 200],
         hide: ["explode", 500]
      });
     var tempL2=  $("#L2temp").val();
    // alert(tempL2);
     var valArrL2 = new Array();
     valArrL2 = tempL2.split(",");
    
      
      /* Below Code Matches current object's (i.e. option) value with the array values */
       /* Returns -1 if match not found */
       $("#empL2").multiselect("widget").find(":checkbox").each(function(){
          if(jQuery.inArray(this.value, valArrL2) !=-1)
                  this.click();

         });

      
       //for Employee Leve3
       
      $("#empL3").multiselect({
         show: ["explode", 200],
         hide: ["explode", 500]
      });
     var tempL3=  $("#L3temp").val();
     var valArrL3 = new Array();
     valArrL3 = tempL3.split(",");
    // alert(tempL3);
      
      /* Below Code Matches current object's (i.e. option) value with the array values */
       /* Returns -1 if match not found */
       $("#empL3").multiselect("widget").find(":checkbox").each(function(){
          if(jQuery.inArray(this.value, valArrL3) !=-1)
                  this.click();

         });

      
       //for Employee Level4

     $("#empL4").multiselect({
         show: ["explode", 200],
         hide: ["explode", 500]
      });

     var tempL4=  $("#L4temp").val();
     var valArrL4 = new Array();
     valArrL4 = tempL4.split(",");
    //alert(tempL4);
      
      /* Below Code Matches current object's (i.e. option) value with the array values */
       /* Returns -1 if match not found */
       $("#empL4").multiselect("widget").find(":checkbox").each(function(){
          if(jQuery.inArray(this.value, valArrL4) !=-1)
                  this.click();

         });

      
     
   });


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
<div style="overflow-x:hidden; height:500px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">

<s:form id="EditEscalation" name="EditEscalation" action="EditEscalationAction" namespace="/view/Over2Cloud/bedTransfer" theme="simple"  method="post" enctype="multipart/form-data" >
	<s:hidden name="id" value="%{id}"/>
	<s:hidden id="subDept" value="%{subDept}"/>
    <s:hidden id="selectEscCont1" value="%{selectedEsc1Cont}"/> 
    <s:hidden id="selectEscCont2" value="%{selectedEsc2Cont}"/> 
    <s:hidden id="selectEscCont3" value="%{selectedEsc3Cont}"/> 
    <s:hidden id="selectEscCont4" value="%{selectedEsc4Cont}"/> 
    
  
    <s:hidden id="L1temp" value="%{l2}"/>
    <s:hidden id="L2temp" value="%{l3}"/>
    <s:hidden id="L3temp" value="%{l4}"/>
    <s:hidden id="L4temp" value="%{l5}"/>
    <s:hidden id="escTemp" value="%{escalation}"/>
    <s:hidden id="emp" value="%{escL1Emp}"/>

    
    <div class="clear"></div>
    <div class="newColumn">
       <div class="leftColumn">L2:&nbsp;</div>
       <div class="rightColumn">
       		<s:select  
                   	id			="empL1"
                   	name		="l2"
                   	list		="escL1Emp"
                   	multiple	="true"
                   	cssClass	="select"
					cssStyle	="width:32%;height:40%"
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
                   	list		="escL1Emp"
                   	multiple	="true"
                   	cssClass	="select"
					cssStyle	="width:32%;height:40%"
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
                   	list		="escL1Emp"
                   	multiple	="true"
                   	cssClass	="select"
					cssStyle	="width:32%;height:40%"
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
                   	list		="escL1Emp"
                   	multiple	="true"
                   	cssClass	="select"
					cssStyle	="width:32%;height:40%"
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
							onclick="bedTrackingEscalation()" >
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