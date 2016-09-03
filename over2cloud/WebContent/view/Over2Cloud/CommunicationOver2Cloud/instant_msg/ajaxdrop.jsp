<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>


<title>Insert title here</title>
</head>
<script>
$(document).ready(function(){
$("#check_list").multiselect();
});
</script>
<body>
<div class="newColumn">
							<div class="leftColumn1">Contact Person:</div>
							<div class="rightColumn">
							<s:select 
							id="check_list" 
							name="check_list" 
							list='mapobject'
							headerKey="-1"
							headerValue="---Select Contact Person---"
							multiple="true"
						
							>
							</s:select>
							 </div>
							
							</div>
</body>
</html>