<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
			<div class="newColumn">
		       <div class="leftColumn">Approval From:</div>
		            <div class="rightColumn">
		               <span class="needed"></span>
		                <s:select 
		                             id="approvedBy"
		                             name="approvedBy" 
		                             list="upperLevelContact" 
		                             headerKey="-1"
		                             headerValue="Select Employee"
		                             cssClass="select"
		                             cssStyle="width:82%"
		                              >
		                              
                  		</s:select>
                  		
                  		<%-- <s:textfield  id="approvedBy" value="%{approvedBy}" maxlength="50"  placeholder="Enter Data" disabled="true"  cssClass="textField"/>  
                  		<s:hidden  id="approvedById" name="approvedBy" value="%{approvedById}" maxlength="50"  placeholder="Enter Data"   cssClass="textField"/> --%>       
		              </div>
		      </div> 
		      
		      <div class="newColumn">
		       <div class="leftColumn">Reason:</div>
		            <div class="rightColumn">
		               <span class="needed"></span>
		                <s:textfield name="reason" id="reason" maxlength="50"  placeholder="Enter Reason For Seek Approval"   cssClass="textField"/>   
		              </div>
		      </div> 
		      <div class="newColumn">
		       <div class="leftColumn">Document:</div>
		            <div class="rightColumn">
		                <s:file name="approvalDoc" id="approvalDoc"  cssClass="textField"/> 
		              </div>
		      </div> 
</body>
</html>