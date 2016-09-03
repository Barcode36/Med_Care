<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<sjc:chart
        id="chartPiePro"
        cssStyle="width: 700px; height: 350px;"
        pie="true"
        pieLabel="true"
        onClickTopics="getChatInfo"
        onHoverTopics="hover"
        legendShow="false"
        
    >
    <s:iterator value="%{dataMap}">
	    <s:if test="key=='On Time'">
		    <sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="#AC58FA"
		    		/>	
		    		
		 </s:if>
		 <s:elseif test="key=='Off Time'">
		 	<sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="green"/>
		 </s:elseif>
		 <s:elseif test="key=='Snooze'">
		 	<sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="Red"/>
		 </s:elseif>
		 <s:elseif test="key=='Missed'">
		 	<sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="blue"/>
		 </s:elseif>
    	</s:iterator>
    </sjc:chart>
</body>
</html>