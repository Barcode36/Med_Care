<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<html>
<%int i=0; %>
<%int j=0; %>
<%int k=0; %>
<%int l=0; %>
<head>
<script type="text/javascript">

$.subscribe('result', function(event,data)
{
  setTimeout(function(){ $("#smsconfirmpage").fadeIn(); }, 10);
  setTimeout(function(){ $("#smsconfirmpage").fadeOut(); }, 4000);
});
	
</script>

</head>
<body >

<div class="container_block">
<div style=" float:left; padding:10px 1%; width:98%;">
<s:form id="formone" name="formone" action="insertConfirmationMsgurl" namespace="/view/Over2Cloud/CommunicationOver2Cloud/Comm" theme="simple"  method="post">
	<s:hidden name="mobileNosss" value="%{mobileNosss}" />
     <s:hidden name="msgText" value="%{writeMessage}" />
      <s:hidden name="contactId" value="%{contactId}" />
       <s:hidden name="groupId" value="%{groupId}" />
          <s:hidden name="frequencys" value="%{frequencys}" />
        <s:hidden name="date" value="%{date}" />
        <s:hidden name="hours" value="%{hours}" />
        <s:hidden name="day" value="%{day}" />
        <s:hidden name="remLen" value="%{remLen}" />
        <s:hidden name="root" value="%{root}" />
        <Table border="0" width="600" cellspacing="10">
				<tr><th style="width: 200px;">Valid Data>> <s:property value ="set.size()"/></th><th style="width: 200px;">Duplicate Data >><s:property value ="dudataList.size()"/></th><th style="width: 200px;">Invalid Data>> <s:property value ="invdataList.size()"/></th><th style="width: 200px;">BlackList Data>> <s:property value ="bdataList.size()"/></th></tr>
				</Table>
				
        
      <sj:accordion id="accordion" autoHeight="false">
      <sj:accordionItem title="Valid Data " id="oneId">  
	<div class="menubox">
		<div class="clear"></div>
		<center>
			<Table border="0" width="600" cellspacing="10">
				<tr><th>Sr. No.</th><th>Name</th><th>MobileNo</th><th>Message</th></tr>
			
				<s:iterator  value="set" >
				<tr>
					<td width="100" align="center" valign="top"><%= ++i%></td>
					
					<td width="100" align="center" valign="top"><s:property value="name" /></td>
					<td width="150" align="center" valign="top"><s:property value="mobileNo" /></td>
					<td width="400" align="center" valign="top"><s:property value="writeMessage" /></td>
				</tr>
				</s:iterator>
		  </Table>
	</center>
	</div>
	
</sj:accordionItem>


	<sj:accordionItem title="Duplicate Data" id="twoId" >
	<div class="menubox">
            
	<div class="clear"></div>
	<center><Table border="0" width="600">
<tr><th>Sr. No.</th><th>Name</th><th>MobileNo</th><th>Message</th></tr>
				<s:iterator  value="dudataList" >
				<tr>
				<td width="100" align="center" valign="top"><%= ++j%></td>
					
					<td width="100" align="center" valign="top"><s:property value="name" /></td>
					<td width="150" align="center" valign="top"><s:property value="mobileNo" /></td>
					<td width="400" align="center" valign="top"><s:property value="writeMessage" /></td>
				</tr>
				</s:iterator>
  </Table>
  </center>
  </div>
  </sj:accordionItem>


	<sj:accordionItem title="Invalid Data" id="threeId">
	<div class="menubox">
	<center><Table border="0" width="600">
	<tr><th>Sr. No.</th><th>Name</th><th>MobileNo</th><th>Message</th></tr>
			<s:iterator  value="invdataList" >
				<tr>
				<td width="100" align="center" valign="top"><%= ++k%></td>
				
					<td width="100" align="center" valign="top"><s:property value="name" /></td>
					<td width="150" align="center" valign="top"><s:property value="mobileNo" /></td>
					<td width="400" align="center" valign="top"><s:property value="writeMessage" /></td>
				</tr>
				</s:iterator>
</Table></center>
	
	
	
	
	</div>
	
	</sj:accordionItem>
	<sj:accordionItem title="BlackList Data" id="fourId">
	<div class="menubox">
	<center><Table border="0" width="600">
		<tr><th>Sr. No.</th><th>Name</th><th>MobileNo</th><th>Message</th></tr>
			<s:iterator  value="bdataList" >
				<tr>
				<td width="100" align="center" valign="top"><%= ++l%></td>
					<td width="100" align="center" valign="top"><s:property value="name" /></td>
					<td width="150" align="center" valign="top"><s:property value="mobileNo" /></td>
					<td width="400" align="center" valign="top"><s:property value="writeMessage" /></td>
				</tr>
				</s:iterator>
</Table>
</center>
	
	</div>
	  
	</sj:accordionItem>


</sj:accordion>
<div class="clear"></div>
<br>
<sj:div id="confirmsms"  effect="fold">
                    <div id="smsconfirmpage"></div>
               </sj:div>
<center><img id="indicator3" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
<center>
          <sj:submit 
          			targets="confirmsms"
                     clearForm="true"
                       value="Send" 
                       effect="highlight"
                       effectOptions="{ color : '#222222'}"
                       effectDuration="5000"
                       button="true"
                       onCompleteTopics="result"
                       cssClass="submit"
                       cssStyle="margin-right: 65px;margin-bottom: -9px;"
                       indicator="indicator3"
                       onBeforeTopics="validate"
                     />
     </center>
    
     <center>
				

     
     </center>
</s:form>
<br>
<br>


</div>
</div>

</body>
</html>