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
$.subscribe('mailresult', function(event,data)
	       {
	         setTimeout(function(){ $("#confirmmail").fadeIn(); }, 10);
	         setTimeout(function(){ $("#confirmmail").fadeOut(); }, 4000);
	       });
	
</script>
</head>
<body >
<div class="container_block">
<s:form id="formoneaaaa" name="formoneaaaa" action="insertConfirmationmailData" namespace="/view/Over2Cloud/CommunicationOver2Cloud/mail" theme="simple"  method="post" >
       <s:hidden name="docfilename" value="%{docfilename}" />
       <s:hidden name="email_id" value="%{email_id}" />
       <s:hidden name="from_email" value="%{from_email}" />
       <s:hidden name="writemail" value="%{writemail}" />
       <s:hidden name="subject" value="%{subject}" />
       <s:hidden name="contactId" value="%{contactId}" />
       <s:hidden name="groupId" value="%{groupId}" />
        <s:hidden name="frequencys" value="%{frequencys}" />
        <s:hidden name="date" value="%{date}" />
        <s:hidden name="hours" value="%{hours}" />
        <s:hidden name="day" value="%{day}" />
        
      <sj:accordion id="accordion" autoHeight="false">
        <Table border="0" width="600" cellspacing="10">
				<tr><th style="width: 200px;">Valid Data>> <s:property value ="set.size()"/></th><th style="width: 200px;">Duplicate Data >><s:property value ="dudataList.size()"/></th><th style="width: 200px;">Invalid Data>> <s:property value ="invdataList.size()"/></th><th style="width: 200px;">BlackList Data>> <s:property value ="bdataList.size()"/></th></tr>
				</Table>
      <sj:accordionItem title="Valid Data" id="oneId">  
	<div class="menubox">
		<div class="clear"></div>
		<center>
			<Table border="0" width="600" cellspacing="10">
				<tr><th>Sr. No.</th><th>Name</th><th>Email Id</th><th>Email CONTAINTS</th></tr>
				<s:iterator  value="set" >
				<tr>
					<td width="100" align="center" valign="top"><%= ++i%></td>
					
					<td width="100" align="center" valign="top"><s:property value="name" /></td>
					<td width="150" align="center" valign="top"><s:property value="email_id" /></td>
					<td width="400" align="center" valign="top"><s:property value="writemail" /></td>
				</tr>
				</s:iterator>
		  </Table>
	</center>
	</div>
	
</sj:accordionItem>


	<sj:accordionItem title="Duplicate Data" id="twoId">
	<div class="menubox">
	<div class="clear"></div>
	<center><Table border="0" width="600">
<tr><th>Sr. No.</th><th>Name</th><th>Email Id</th><th>Email CONTAINTS</th></tr>
				<s:iterator  value="dudataList" >
				<tr>
				<td width="100" align="center" valign="top"><%= ++j%></td>
					
					<td width="100" align="center" valign="top"><s:property value="name" /></td>
					<td width="150" align="center" valign="top"><s:property value="email_id" /></td>
					<td width="400" align="center" valign="top"><s:property value="writemail" /></td>
				</tr>
				</s:iterator>
  </Table>
  </center>
  </div>
  </sj:accordionItem>
	<sj:accordionItem title="Invalid Data" id="threeId">
	<div class="menubox">
	<center><Table border="0" width="600">
	<tr><th>Sr. No.</th><th>Name</th><th>Email Id</th><th>Email CONTAINTS</th></tr>
			<s:iterator  value="invdataList" >
				<tr>
				<td width="100" align="center" valign="top"><%= ++k%></td>
				
					<td width="100" align="center" valign="top"><s:property value="name" /></td>
					<td width="150" align="center" valign="top"><s:property value="email_id" /></td>
					<td width="400" align="center" valign="top"><s:property value="writemail" /></td>
				</tr>
				</s:iterator>
</Table></center>
	
	
	
	
	</div>
	
	</sj:accordionItem>
	<sj:accordionItem title="BlackList Data" id="fourId">
	<div class="menubox">
	<center><Table border="0" width="600">
		<tr><th>Sr. No.</th><th>Name</th><th>Email Id</th><th>Email CONTAINTS</th></tr>
			<s:iterator  value="bdataList" >
				<tr>
				<td width="100" align="center" valign="top"><%= ++l%></td>
					<td width="100" align="center" valign="top"><s:property value="name" /></td>
					<td width="150" align="center" valign="top"><s:property value="email_id" /></td>
					<td width="400" align="center" valign="top"><s:property value="writemail" /></td>
				</tr>
				</s:iterator>
</Table>
</center>
	</div>
	</sj:accordionItem>
</sj:accordion>
    
     <center>
				<sj:div id="mailcinfirm"  effect="fold">
                    <div id="confirmmail"></div>
               </sj:div>

     
     </center>
<div class="clear"></div>
<center><img id="indicator3" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
<br>
<center>
          <sj:submit 
          			targets="mailcinfirm"
                     clearForm="true"
                       value="Send" 
                       effect="highlight"
                       effectOptions="{ color : '#222222'}"
                       effectDuration="5000"
                       button="true"
                       onCompleteTopics="mailresult"
                       cssClass="submit"
                       cssStyle="margin-right: 65px;margin-bottom: -9px;"
                       indicator="indicator3"
                     />
     </center>

</s:form>
<br>
<br>

</div>

</body>
</html>