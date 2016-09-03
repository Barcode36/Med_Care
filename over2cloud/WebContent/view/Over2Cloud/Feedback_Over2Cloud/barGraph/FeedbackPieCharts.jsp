<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<html>
<head>
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div align="center">
<sjc:chart
        id="%{block}"
        cssStyle="width: 220px; height: 210px;"
        legendShow="false"
        pie="true"
        pieLabel="true"
        onHoverTopics="true"
    >
        <s:iterator value="%{actionMapGraph}">
	        <s:if test="%{key=='Complaint'}">
	         <sjc:chartData
                label="%{key}"
                data="%{value}"
                color="red"
            />
	        </s:if>
	        <s:elseif test="%{key=='Appreciation'}">
	          <sjc:chartData
                label="%{key}"
                data="%{value}"
                color="green"
            />
	        </s:elseif>
	        <s:elseif test="%{key=='Suggestion'}">
	          <sjc:chartData
                label="%{key}"
                data="%{value}"
                color="blue"
            />
	        </s:elseif>
	        <s:else>
            <sjc:chartData
                label="%{key}"
                data="%{value}"
            />
            </s:else>
        </s:iterator>
    </sjc:chart>
</div>
</body>
</html>