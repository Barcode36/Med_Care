<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head></head>
<body>
<div align="center">
<sjc:chart
        id="chartPie1"
        cssStyle="width: 750px; height: 550px;"
        legendShow="false"
        pie="true"
        pieLabel="true"
        onHoverTopics="true"
    >
        <s:iterator value="%{graphMap}">
            <sjc:chartData
                label="%{key}"
                data="%{value}"
            />
        </s:iterator>
    </sjc:chart>
    </div>
</body>
</html>