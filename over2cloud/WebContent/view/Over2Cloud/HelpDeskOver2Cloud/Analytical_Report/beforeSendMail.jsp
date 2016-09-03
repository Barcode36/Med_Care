<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="<s:url value="/js/helpdesk/reportconfig.js"/>"></script>
<script type="text/javascript">
$.subscribe('resetD', function(event,data)
{
	 $('select').find('option:first').attr('selected', 'selected');
});
</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="clear"></div>
<div style="width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
  <s:form id="sendmail" name="sendmail" action="sendMailConfirmAction" namespace="/view/Over2Cloud/HelpDeskOver2Cloud/Analytical_Report" theme="simple"  method="post"enctype="multipart/form-data" >
    <center>
	  	<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 50%; height: 10%; font-size: small; border: 0px solid red; border-radius: 6px;">
			<div id="errZone" style="float:center; margin-left: 7px"></div>
	  	</div>
	</center>
    <s:hidden name="dataFor" value="%{dataFor}"/>
	<s:hidden name="deptId" value="%{deptId}"/>
	<s:hidden name="subdeptId" value="%{subdeptId}"/>
	<s:hidden name="fromDate" value="%{fromDate}"/>
	<s:hidden name="toDate" value="%{toDate}"/>
	<s:hidden name="byDeptId" value="%{byDeptId}"/>
	<s:hidden name="moduleName" value="%{moduleName}"/>
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
							 onchange   = "getEmployeeData(this.value,'emp')"
                             >
                   </s:select>
                </div>
                </div>
         <span id="HFSpan" class="pIds" style="display: none; ">emp#Employee#D#,</span>
          	   <div class="newColumn">
			   <div class="leftColumn">Employee:</div>
	           <div class="rightColumn">
	            <span class="needed"></span>
                <s:select  
                             id		    ="emp"
                             name		="emp"
                             list		="{'No Data'}"
                             headerKey	="-1"
                             headerValue="Select Employee" 
                             multiple   = "true"
                             cssClass="select"
                             cssStyle="height: 75px;"
                             >
                </s:select>
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
         				onBeforeTopics  =   "validate"
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
          title="Productivity"
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