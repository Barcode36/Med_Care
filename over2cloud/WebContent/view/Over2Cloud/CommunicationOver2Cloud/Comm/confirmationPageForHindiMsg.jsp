<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<style type="text/css">

.user_form_input{
    margin-bottom:10px;
}

</style>
<html>
<%int i=0; %>
<%int j=0; %>
<%int k=0; %>
<%int l=0; %>
<head>


<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/associate/associate.js"/>"></script>


<script type="text/javascript">
	function viewClient()
	{
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/client/beforeClientView.action?modifyFlag=0&deleteFlag=0&isExistingClient=0&lostFlag=0&convertToClient=0",
	    success : function(subdeptdata) {
	   $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
	        alert("error");
	    }
	 });
	}
</script>
<link href="css/style3.css" rel="stylesheet" type="text/css" >
</head>
<body  topmargin=0>
	

<div class="container_block">
<div style=" float:left; padding:10px 1%; width:98%;">
 <s:hidden name="subDeptId" value="%{subDeptId}" />
  <s:hidden name="contactId" value="%{contactId}" />
   <s:hidden name="groupId" value="%{groupId}" />
    <s:hidden name="mobNo" value="%{mobNo}" />
     <s:hidden name="msgText" value="%{msgText}" />


<s:form id="formone" name="formone" namespace="/view/Over2Cloud/CommunicationOver2Cloud/Comm" action="insertConfirmationHindiMsgData" theme="simple"  method="post"enctype="multipart/form-data" >
	<s:hidden name="mobileNosss" value="%{mobileNosss}" />
     <s:hidden name="msgText" value="%{msgText}" />
<sj:accordion id="accordion" autoHeight="false">
<sj:accordionItem title="Valid Data" id="oneId">  
 
	<div class="menubox">
		<div class="clear"></div>
		<center>
			<Table border="0" width="600" cellspacing="10">
				<tr><th>Sr. No.</th><th>Name</th><th>MobileNo</th><th>Message</th></tr>
				<s:iterator  value="vdataList" >
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


	<sj:accordionItem title="Duplicate Data" id="twoId">
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

<br>
<br>
<center>
 
          <sj:submit 
                    targets="orglevel1"
                     clearForm="true"
                     value="  Send  " 
                     timeout="5000"
                     effect="highlight"
                     effectOptions="{ color : '#222222'}"
                     effectDuration="5000"
                     button="true"
                     onCompleteTopics="complete1"
                     
                     />
          
 </center>
    <div class="clear"></div>
     <center>
				<sj:div id="orglevel1"  effect="fold">
                    <div id="orglevel1Div"></div>
               </sj:div>

     
     </center>
</s:form>


</div>
</div>

</body>
</html>