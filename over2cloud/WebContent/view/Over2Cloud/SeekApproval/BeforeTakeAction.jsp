<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="clear"></div>
<div style="overflow:auto; height:300px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
	<s:form id="formone111" name="formone111" action="actionOnSeekApproval"  theme="css_xhtml"  method="post" enctype="multipart/form-data">
		    <s:hidden id="seekId" name="seekId"  value="%{seekId}"/>
		    <s:hidden id="moduleName" name="moduleName"  value="%{moduleName}"/>
		    <s:if test="%{moduleName =='HDM'}">
		    <table border="1" width="100%" style="border-collapse: collapse;">
				<tr bgcolor="#D8D8D8" style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='From Department' || key=='Feedback By' || key=='Mobile No.'">
							<td width="10%"><b><s:property value="%{key}"/> :<b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr  style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='To Department' || key=='Allotted To' || key=='Mobile No'">
							<td width="10%"><b><s:property value="%{key}"/> : <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr bgcolor="#D8D8D8" style="height: 23px">
					<s:iterator value="dataMap">
						<s:if test="key=='Category' || key=='Sub-Category' || key=='Brief'">
							<td width="10%"><b><s:property value="%{key}"/> : <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key == 'Feedback Type' || key=='Current Status' || key == 'Current Level'">
							<td width="10%"><b><s:property value="%{key}"/> : <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr bgcolor="#D8D8D8" style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key == 'Lapse Time' || key=='Next Escalation On' || key=='Next Escalation To'">
							<td width="10%"><b><s:property value="%{key}"/> : <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				<tr style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key == 'Requested By' || key=='Requested On' || key == 'Reason'">
							<td width="10%"><b><s:property value="%{key}"/> : <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
		</table>
		    </s:if>
		      <div class="newColumn">
		       <div class="leftColumn">Seek Approval:</div>
		            <div class="rightColumn">
		               <span class="needed"></span>
		                <s:select 
		                             id="status"
		                             name="status" 
		                             list="#{'Approved':'Approve','Disapproved':'Disapproved'}" 
		                             headerKey="-1"
		                             headerValue="Select Status"
		                             cssClass="select"
		                             cssStyle="width:82%"
		                              >
		                              
                  		</s:select>    
		              </div>
		      </div> 
		      
		      <div class="newColumn">
		       <div class="leftColumn">Reason:</div>
		            <div class="rightColumn">
		               <span class="needed"></span>
		                <s:textfield name="comments" id="comments" maxlength="50"  placeholder="Enter Data"   cssClass="textField"/>   
		              </div>
		      </div> 
		      <div class="newColumn">
		       <div class="leftColumn">Supporting Document:</div>
		            <div class="rightColumn">
		                <s:file name="approvedDoc" id="approvedDoc"  cssClass="textField"/> 
		              </div>
		      </div> 
		     <br><br><br><br><br><br><br>   
		     <center>
		    <sj:submit 
	           targets="target1" 
	           clearForm="true"
	           value="  Save  " 
	           effect="highlight"
	           effectOptions="{ color : '#222222'}"
	           effectDuration="5000"
	           onCompleteTopics="successfullSubmission"
	           button="true"
	           cssStyle="margin-left: -30px;"
			   />
			   <center>
			   <div id="target1"></div>
	</s:form>

</div>
</div>
</body>
</html>