<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}" />
<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>

<title>Insert title here</title>
<script type="text/javascript">


$(document).ready(function()
	    {
	    $("#mailIds").multiselect({
	           show: ["", 200],
	           hide: ["", 1000]
	        });
	    });



</script>
<script type="text/javascript">
function test(data)
{
	alert(" data are "+data);
}

</script>
</head>
<body>
<div>
			<s:select
			id="mailIds"
			name="mailIdTest"
			list="empAll"
			headerKey	="-1"
            cssClass="textField"
		 	multiple="true"
		   	cssStyle="width:28%"
		    >
		    </s:select>
		    </div>
		    
		    
		   
		    
		
</body>
</html>