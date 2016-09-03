<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
    <%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<title>Insert title here</title>
<script type="text/javascript">
$(document).ready(function()
	    {
	    $("#admittung_search").multiselect({
	           show: ["", 200],
	           hide: ["explode", 1000]
	        });
	    });
 
</script>
 
</head>
<body>
<div>
<b>Admitting Doctor:</b>
			<s:select
			id="admittung_search"
			name="admittung_search"
			list="admittungMap"
		    cssClass="textField"
		 	multiple="true"
		   	cssStyle="width:245px"
		   	onchange="getGridView();getStatusCounter();"
		    >
		    </s:select>
		    </div>
		    
	 
</body>
</html>