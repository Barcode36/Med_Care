<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
</head>
<body>

<div class="clear"></div>


<div class="middle-content">
<div class="clear"></div>
<div style="overflow:auto; height:auto; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">

   <s:iterator value="servicePageFieldsColumns" var="value" status="status">
	      <div class="newColumn">
	         <div class="leftColumn1"><s:property value="%{value}"/>:&nbsp;</div>
		     <div class="rightColumn1">
	               <s:if test="%{colType == \"D\"}">
	                  <s:select id="%{key}" name="%{key}" list="%{ddList}" theme="simple" cssClass="textField" placeholder="Enter Data"></s:select>
	               </s:if>
	               <s:elseif test="%{colType == \"T\"}">
	                 <s:textfield id="%{key}"  name="%{key}" cssClass="textField" placeholder="Enter Data"></s:textfield>
	               </s:elseif>
	               <s:elseif test="%{colType == \"datetime\"}">
	                 <sj:datepicker  cssClass="button"  name="%{key}"  id="%{key}" size="20" maxDate="0"  readonly="true"  changeMonth="true" changeYear="true" timepicker="true" yearRange="1913:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Date & Time" />
			         
			      </s:elseif>
	               
	         </div>
	      </div>  
	        
	</s:iterator>

</div>
</div>


</body>
</html>