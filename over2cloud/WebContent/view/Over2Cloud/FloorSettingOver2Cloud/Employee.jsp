<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<title>Insert title here</title>
<SCRIPT type="text/javascript">

	$(document).ready(function()
		 {
		 	$("#empId").multiselect({
		 		   show: ["", 200],
		 		   hide: ["explode", 1000]
		 		});
		 }); 
		
</script>
</head>
<body>
<s:select  
					                              	id					=		"empId"
					                              	name				=		"empId"
					                              	list				=		"empMap"
					                              	headerKey			=		"-1"
					                              	headerValue			=		"Employee"
					                              	cssClass			=		"button"
					                              	cssStyle			=		"margin-left: 3px;width: 143px;height: 80px;"
					                              	theme 				=		"simple"
					                              	multiple			=		"true"
					                              	onchange			=		"appendEmpId();"
					                              >
							            </s:select>
</body>
</html>