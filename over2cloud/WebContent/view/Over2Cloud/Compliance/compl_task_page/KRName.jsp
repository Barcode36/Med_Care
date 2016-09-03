<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
$(document).ready(function()
	{
	$("#kr_name").multiselect({
		   show: ["", 200],
		   hide: ["explode", 1000]
		});
	});
</script>
<title>Insert title here</title>
</head>
<body>
	<s:select  
                        id		    ="kr_name"
                        name		="kr_name"
                        list		="krName"
                        headerKey	="-1"
                        headerValue="Select KR Name" 
                        cssClass="textField"
		 				cssStyle="width:5%"
		 				multiple="true"
                        >
            </s:select>
</body>
</html>