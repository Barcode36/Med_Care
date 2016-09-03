<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript">
$.subscribe('completeData', function(event,data)
{
  setTimeout(function(){ $("#foldeffect1").fadeIn(); }, 10);
  setTimeout(function(){ $("#foldeffect1").fadeOut(); }, 4000);
 
});

 
</script>
</head>
<body>
	<div class="list-icon" style="margin-top: -100px;">
	 <div class="head">Notification</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div> 
</div>
   <div style=" float:left; padding: 23px 0%;margin-top: -88px;width:100%;">
    <div class="border">
    
<s:form id="formtaskType" name="formtaskType" namespace="/view/Over2Cloud/HelpDeskOver2Cloud/Daily_Report" action="mapNotification" theme="css_xhtml"  method="post" enctype="multipart/form-data" >
		<div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="errZone" style="float:left; margin-left: 7px"></div> 
        </div>
             
            <div style="margin-left: -908px;"><b>Complainant</b></div>
	                  <div class="newColumn">
								<div class="leftColumn1" style="margin-top: -6px;">Mail:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:checkbox name="allotedByMail"  id="allotedByMail" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
					            </div>
					  </div>          
					   <div class="newColumn">
								<div class="leftColumn1" style="margin-top: -6px;">SMS:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
					            <s:checkbox name="allotedBySMS"  id="allotedBySMS" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px; "/>
					            </div>
					    </div>    
					    
		   <div style="margin-left: -908px;"><b>Resolver</b></div>
		               <div class="newColumn">
								<div class="leftColumn1" style="margin-top: -6px;">Mail:</div>
								<div class="rightColumn1">
		                        <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                        <s:checkbox name="allotedToMail"  id="allotedToMail" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
					            </div>
					  </div>          
					  <div class="newColumn">
								<div class="leftColumn1" style="margin-top: -6px;">SMS:</div>
								<div class="rightColumn1">
		                        <s:if test="%{mandatory}"><span class="needed"></span></s:if>
					            <s:checkbox name="allotedToSMS"  id="allotedToSMS"  placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px; "/>
					            </div>
					  </div>      
				
	    <div align="center" style="margin-left: -179px;">
		  <ul>
			 <li class="submit" style="background:none;">
			 <div class="type-button">
	         <sj:submit 
	                targets="target1" 
	                id="buttonid"
	                clearForm="true"
	                value="  Save  " 
	                effect="highlight"
	                effectOptions="{ color : '#FFFFFF'}"
	                effectDuration="5000"
	                button="true"
	                onCompleteTopics="completeData"
	                onBeforeTopics="validate"
	                cssClass="submit"
	                indicator="indicator2"
	                cssStyle="margin-left: -40px;"
              />
				<sj:a cssStyle="margin-left: 181px;margin-top: -45px;" button="true" href="#" onclick="resetForm('formtaskType');">Reset</sj:a>
				<sj:a cssStyle="margin-left: 0px;margin-top: -45px;" button="true" href="#" onclick="sms_mail_conf();">Back</sj:a>  
	        </div>
	        </li>
		  </ul>
	      </div>
	      <center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
		  <sj:div id="foldeffect1" cssClass="sub_class1"  effect="fold"><div id="target1"></div></sj:div>
	 
	 </s:form>
	  </div>
	  </div>
</body>
</html>

