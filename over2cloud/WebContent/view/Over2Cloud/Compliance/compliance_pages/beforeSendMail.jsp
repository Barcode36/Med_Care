<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript" src="<s:url value="/js/compliance/compliance.js"/>"></script>
<%-- <link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script> --%>
<script type="text/javascript">
$.subscribe('resetD', function(event,data)
{
	 $('select').find('option:first').attr('selected', 'selected');
});
/* $(document).ready(function()
	{
		$("#emp").multiselect({
		   show: ["", 200],
		   hide: ["explode", 1000]
		});
	}); */
</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="clear"></div>
<div style="width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
  <s:form id="sendmailData" name="sendmailData" action="%{actionName}" namespace="/view/Over2Cloud/Compliance/compliance_pages" theme="simple"  method="post"enctype="multipart/form-data" >
    <center>
	  	<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 50%; height: 10%; font-size: small; border: 0px solid red; border-radius: 6px;">
			<div id="errZoneMail" style="float:center; margin-left: 7px"></div>
	  	</div>
	</center>
	
	<s:hidden name="selectedId" value="%{selectedId}"/>
	<s:hidden  name="mainHeaderName" value="%{mainHeaderName}"/> 
    <span id="HFSpan" class="pIds" style="display: none; ">dept#Department#D#,</span>
           	    <div class="newColumn">
  	            <div class="leftColumn">Department:</div>
                <div class="rightColumn">
                <span class="needed"></span>
                <s:select  
                             id		    ="dept"
                             name		="dept"
                             list		="deptList"
                             headerKey	="-1"
                             headerValue="Select Department" 
                             cssClass   ="select"
							 cssStyle   ="width:82%"
							 onchange   ="getEmployeeData(this.value,'employee')"
                             >
                   </s:select>
                </div>
                </div>
          	   <span id="HFSpan" class="pIds" style="display: none; ">employee#Employee#D#,</span>
          	   <div class="newColumn">
			   <div class="leftColumn">Employee:</div>
	           <div class="rightColumn"><span class="needed"></span>
	           <div id="empDivData">
                <s:select  
                             id		    ="emp"
                             name		="employee"
                             list		="{'No Data'}"
                             headerKey	="-1"
                             headerValue="Select Employee" 
                             multiple   = "true"
                             cssClass="select"
                             cssStyle="height: 75px;"
                             >
                </s:select></div>
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
         				targets			=	"sendResult" 
         				clearForm		=	"true"
         				value			=	" Send " 
         				button			=	"true"
         				cssClass		=	"submit"
         				onBeforeTopics  =   "validateMail"
         				onCompleteTopics=   "resetD"
     		  	  />
	        </div>
	        </li>
		 </ul>
		 </center>
		 <br>
		 <br>
 <sj:dialog
          id="sendMailDialog"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title="Send Mail"
          modal="true"
          width="350"
          height="600"
          draggable="true"
    	  resizable="true"
          >
          <div id="sendResult"></div>
</sj:dialog>
		 </div>

   </s:form>
</div>
</div>
</body>
</html>