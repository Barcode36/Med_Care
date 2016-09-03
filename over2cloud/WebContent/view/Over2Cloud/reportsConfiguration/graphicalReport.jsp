<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DAR</title>
<link href="<s:url value="/css/style.css"/>" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/jquery.js"/>"></script>
<s:url id="contextz" value="/template/theme" />
<s:url var="chartDataUrl" action="json-chart-data"/>
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript">

</script>
</head>
<body topmargin='0'>
<div id="graphDiv" >
   <h3><s:property value="%{graphHeader}"  /></h3>
    <sjc:chart
    	id="chartPie2"
    	cssStyle="width: 600px; height: 400px;"
    	legendShow="false"
    	pie="true"
    	pieLabel="true"
    >
    	<s:iterator value="%{pieDataMap}">
	    	<sjc:chartData
	    		label="%{key}"
	    		data="%{value}"
	    	/>
    	</s:iterator>
    </sjc:chart>

    </div>
</body>
</html>
