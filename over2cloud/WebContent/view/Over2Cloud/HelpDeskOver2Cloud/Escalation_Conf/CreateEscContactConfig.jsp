<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/feedback.js"/>"></script>
<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<script type="text/javascript">
$.subscribe('makeEffect', function(event,data)
		  {
			 setTimeout(function(){ $("#resultTarget").fadeIn(); }, 10);
			 setTimeout(function(){ $("#resultTarget").fadeOut(); }, 400);
		  });

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

function getEmployee(value,targetid)
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Escalation_Conf/getEmployee.action?deptId="+value,
	    success : function(data) {
	    	$('#'+targetid+' option').remove();
			$('#'+targetid).append($("<option></option>").attr("value",-1).text("Select Employee"));
	    	$(data).each(function(index)
			{
			   $('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.name));
			});
		},
	    error: function() {
            alert("error");
        }
	 });
	
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Escalation_Conf/checkEscLevel.action?deptId="+value,
	    success : function(data)
	    {
	    	$("#escLevel").val(data.escLevelFlag);
	    	
	    	if(data.escLevelFlag=="wings")
	    		$("#block23").show();
	    	else
	    		$("#block23").hide();
		},
	    error: function() {
            alert("error");
        }
	 });
}
function getEmpLevel()
{
	var value = $("#empName option:selected").text();
	var lenth = value.length;
	var level =  value.substring(lenth-1,lenth);
	$("#level").val(level);
}
function getViewEscContact()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Escalation_Conf/ViewEscContact.jsp",
	    success : function(data) {
       		$("#data_part").html(data);
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
	<div class="head">Configure Contact For Escalation</div><img alt="" src="images/forward.png" style="margin-top:10px; float: left;"><div class="head"> Add</div><sj:a button="true" cssClass="button" buttonIcon="ui-icon-plus"  style="margin-top:4px; float: right;"  onclick="getOnlinePage()">Online Mode</sj:a> 
	  
</div>
<div class="clear"></div>
<div style="overflow:auto; height:450px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">

<s:form id="formone" name="formone" action="addEscContactConfig" theme="simple"  method="post" enctype="multipart/form-data" >

<center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone" style="float:center; margin-left: 7px"></div></div></center>  
				<s:hidden name="level" id="level"/>
				<s:hidden name="escLevel" id="escLevel"/>
				
				<div class="newColumn">
        			   <div class="leftColumn" >Mapped Department:&nbsp;</div>
					        <div class="rightColumn">
				                  <s:select  id="escDept"
				                              name="escDept"
				                              list="serviceDeptMap"
				                              headerKey="-1"
				                              headerValue="Select Department" 
				                              cssClass="select"
					                          cssStyle="width: 80%"
					                          onchange="getEmployee(this.value,'empName')"
				                              >
				                  </s:select>
                           </div>
                   </div>
                   
                   <div class="newColumn">
        			   <div class="leftColumn" >Employee:&nbsp;</div>
					        <div class="rightColumn">
				                  <s:select  id="empName"
				                              name="empName"
				                              list="{'No Data'}"
				                              cssClass="select"
					                          cssStyle="width: 80%"
					                          onchange="getEmpLevel();"
				                              >
				                  </s:select>
                           </div>
                   </div>
    			  
    			  <div class="newColumn">
        			   <div class="leftColumn" >Floor Name:&nbsp;</div>
					        <div class="rightColumn">
				                  <s:select  id="floorname"
				                              name="floorname"
				                              list="floorMap"
				                              headerKey="-1"
				                              headerValue="Select Floor"
				                              cssClass="select"
					                          cssStyle="width: 80%"
					                          onchange="commonSelectAjaxCall2('wings_detail','id','wingsname','floorname',this.value,'','','wingsname','ASC','wingsname','Select Wings');Reset('.pIds');">
				         			</s:select>
                           </div>
                   </div>
                   
                  
                    <div id="block23" style="display: none">  
                    <div class="newColumn">
        			   <div class="leftColumn" >Wings Name:&nbsp;</div>
					        <div class="rightColumn">
				                  <s:select  id="wingsname"
				                              name="wingsname"
				                              list="{'No Data'}"
				                              headerKey="-1"
				                              headerValue="Select Wings"
				                              cssClass="select"
					                          cssStyle="width: 80%"
				                              >
				         			</s:select>
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
                        onBeforeTopics  =   "validate"
                        
     		  	  />
     		  	  <sj:a 
						button="true" href="#"
						onclick="resetForm('formone');"
						>
						Reset
				  </sj:a>
     		  	  <sj:a 
						button="true" href="#"
						onclick="getViewEscContact();"
						>
						Back
					</sj:a>
	        </div>
	        </li>
		 </ul>
		 <sj:div id="resultTarget"  effect="fold">
   	     </sj:div>
		 </div>
</s:form>
</div>
</div>
</body>
</html>