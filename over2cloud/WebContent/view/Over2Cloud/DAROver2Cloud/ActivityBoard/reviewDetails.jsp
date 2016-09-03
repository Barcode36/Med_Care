<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<SCRIPT type="text/javascript">

$.subscribe('complete', function(event,data)
 {   setTimeout(function(){ $("#foldeffect").fadeIn(); }, 10);
	 setTimeout(function(){ $("#foldeffect").fadeOut(); }, 4000);
 });
function fetchProjectDetails(value,div)
{
	 $("#"+div).load("<%=request.getContextPath()%>/view/Over2Cloud/DAROver2Cloud/projectDetails?taskId="+value);
}
</SCRIPT>
<title></title>
</head>
<body>
<div class="list-icon">
	 <div class="head">
	 <s:property value="%{dataFor}"/></div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Review</div> 
</div>
 <div style=" float:left; padding:5px 0%; width:100%;">
	<div class="border" style="overflow-x:hidden;">
    <s:form id="takeActionForm" action="reviewStatustask" method="post"  name="reviewStatustask" enctype="multipart/form-data" theme="simple">
    <center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 50%; height: 10%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone" style="float:center; margin-left: 7px"></div></div></center>
	   
	   <s:hidden id="dataFor" name="dataFor" value="%{dataFor}"/>
	    <div class="newColumn">
        <div class="leftColumn">Project Name:</div>
		<div class="rightColumn"><span class="needed"></span>
	    <s:select 
                    cssClass="select"
					cssStyle="width:82%"
                    id="projectname"
                    name="projectname" 
                    list="projectList" 
                    headerKey="-1"
                    headerValue="Select Project"
                    onchange="fetchProjectDetails(this.value,'result');"
                    >
        </s:select>
        </div>
        </div>
        <div id="result"></div>
         <div class="newColumn">
        <div class="leftColumn">Status:</div>
		<div class="rightColumn"><span class="needed"></span>
	    <s:select 
                    cssClass="select"
					cssStyle="width:82%"
                    id="projectStatus"
                    name="projectStatus" 
                    list="{'Pending','Partially Pending','Done','Hold'}"
                    headerKey="-1"
                    headerValue="Select Status"
                    onchange=""
                    >
        </s:select>
        </div>
        </div>
          <div class="newColumn">
        <div class="leftColumn">Rating:</div>
		<div class="rightColumn"><span class="needed"></span>
	    <s:select 
                    cssClass="select"
					cssStyle="width:82%"
                    id="projectRating"
                    name="projectRating" 
                    list="{'1','2','3','4','5'}"
                    headerKey="-1"
                    headerValue="Select Status"
                    onchange=""
                    >
        </s:select>
        </div>
        </div>
        <div class="newColumn">
        <div class="leftColumn">Reason:</div>
        <div class="rightColumn"><span class="needed"></span><s:textfield placeholder="Enter Reasons" cssClass="textcell" id="comments" name="comments" cssClass="textField" theme="simple"></s:textfield>
        </div>
        </div>
 	    
 	      <div class="newColumn">
	                  <div class="leftColumn">Review Document:</div>
	                  <div class="rightColumn">
 	    			  <s:file name="takeActionDoc" id="takeActionDoc"  cssClass="textField"/></div>
 	     </div>
 	  
	   
	   
	   <div class="clear"></div>
	   <div class="clear"></div>
	    <br>
	     <center>
	         <div id="test">
	          <sj:submit 
                        targets			   	=		"finalResult" 
                        clearForm			=		"true"
                        value				=		"Save" 
                        effect				=		"highlight"
                        effectOptions		=		"{color:'#222222'}"
                        effectDuration		=		"5000"
						button				=		"true"
                        onCompleteTopics	=		"complete"
                        onBeforeTopics		=		"validateOnTakeAction"
                        />
                        <sj:a 
						button="true" href="#"
						onclick="resetTakeAction('takeActionForm');"
						>
						Reset
					</sj:a>
					 <sj:a 
						button="true" href="#"
						onclick="backTakeAction();"
						>
						Back
					</sj:a>
					
					</div>
		
		  </center>
		 <br>
		 <br>
		 	 <center> 
		 	   <sj:div id="foldeffect"  effect="fold">
                      <div id="finalResult"></div>
               </sj:div>
           </center>
		
  </s:form>
</div>
</div>
</body>
<script type="text/javascript">
function backTakeAction()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/DAROver2Cloud/beforeMainActivity.action",
	    success : function(data) {
       $("#"+"data_part").html(data);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function resetTakeAction(formId)
{
	$('#'+formId).trigger("reset");
	$('#result').hide();
	
}
</script>
</html>