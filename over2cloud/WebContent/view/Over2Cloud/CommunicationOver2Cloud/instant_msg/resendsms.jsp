<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<script type="text/javascript">
$.subscribe('result', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#resulresendsms").fadeIn(); }, 10);
	         setTimeout(function(){ $("#resulresendsms").fadeOut(); }, 4000);
	       });
	       
</script>

</body>
 <s:form id="formtwo" name="formtwo" namespace="/view/Over2Cloud/CommunicationOver2Cloud/Comm" action="insertConfirmationMsgurl" theme="simple"  method="post" >
 <s:hidden name="root" value="%{#parameters.root}"></s:hidden>
 <div class="newColumn">
		 	 <div class="leftColumn">Mobile : </div> 
          	 <div class="rightColumn">
          		   <s:textfield name="mobileNosss" id="mobileNosss" value="%{#parameters.mobileno}"   maxlength="100" placeholder="Enter Password"/>
          	 </div>
          	 </div>
          	 <div class="clear"></div>
          	 <div class="newColumn">
		 	 <div class="leftColumn">Message : </div> 
          	 <div class="rightColumn">
          		  <s:textarea name="msgText" id="msgText" cols="95" rows="20" value="%{#parameters.message}"   readonly="true"/>
          	 </div>
          	 </div>
          	 <div class="clear"></div>
          	 	<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
			<div class="type-button" style="margin-left: -200px;">
			<center>
          	 			<sj:submit 
          			   targets="resendsms" 
                       clearForm="true"
                       value="Resend" 
                       effect="highlight"
                       onCompleteTopics="result"
                       effectOptions="{ color : '#222222'}"
                       effectDuration="5000"
                       button="true"
                       cssClass="submit"
                       cssStyle="margin-right: 2px;margin-bottom: 29px;"
                       indicator="indicator2"
         />
          	          <sj:div id="resulresendsms"  effect="fold">
                    <div id="resendsms"></div>
               </sj:div>
          	 </center>
          	 </s:form>
</html>