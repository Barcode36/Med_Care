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
		$("#serv_loc").multiselect({
		   show: ["bounce", 200],
		   hide: ["explode", 1000]
		});
	});

</script>
<script type="text/javascript">

</script>
</head>
<body>

        
       <div class="newColumn">
	   <div class="leftColumn">Location:</div>
	        <div class="rightColumn">
	        <span class="needed"></span>
	        <s:select 
                    id="serv_loc"
                    name="serv_loc" 
                    list="location"
                    cssClass="select"
                    multiple="true"
                    cssStyle="width:24%"
                    >
             </s:select>
	          </div>
	       </div>
                                     
  </body>
</html>