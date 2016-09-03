<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<title>Insert title here</title>
<script type="text/javascript">
$(document).ready(function()
		{
			$("#subdeptname").multiselect({
				   show: ["", 200],
				   hide: ["explode", 1000]
				});
			$("#shifts").multiselect({
				   show: ["", 200],
				   hide: ["explode", 1000]
				});
			$("#wing").multiselect({
				   show: ["", 200],
				   hide: ["explode", 1000]
				});
			$("#roomNo").multiselect({
				   show: ["", 200],
				   hide: ["explode", 1000]
				});
		});
</script>
</head>
<body>
<s:if test="%{dataFor=='subdept'}">
			<s:select
			id="subdeptname"
			name="subdeptname"	
			list="commonMap"
			headerKey	="-1"
            cssClass="textField"
		 	multiple="true"
		   	cssStyle="width:5%"
		   	 onchange="getServiceSubDept('shiftDiv');"
            >
		    </s:select>
</s:if>
<s:elseif test="%{dataFor=='shift'}">
			<s:select
			id="shifts"
			name="shifts"	
			list="commonMap"
			headerKey	="-1"
            cssClass="textField"
		 	multiple="true"
		   	cssStyle="width:5%"
            >
		    </s:select>
</s:elseif>
<s:elseif test="%{dataFor=='wing'}">
			<s:select
			id="wing"
			name="roomNo"	
			list="commonMap"
			headerKey	="-1"
            cssClass="textField"
		 	multiple="true"
		   	cssStyle="width:5%"
		   	onchange="getServiceSubDept('roomDiv');"
            >
		    </s:select>
</s:elseif>
<s:elseif test="%{dataFor=='room'}">
			<s:select
			id="roomNo"
			name="roomNo"	
			list="commonMap"
			headerKey	="-1"
            cssClass="textField"
		 	multiple="true"
		   	cssStyle="width:5%"
            >
		    </s:select>
</s:elseif>
</body>
</html>