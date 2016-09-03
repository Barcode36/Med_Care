<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
  		<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
		<script type="text/javascript" src="<s:url value="/js/js.js"/>"></script>
		<script type="text/javascript" src="<s:url value="/js/communication/instantMessage/instantCommunication.js"/>"></script>
</head>

<body>
			<s:select 
 				id			=		"depart"
 				name		=		"depart" 
 				list		=		"deptList"
 				headerKey	=		"-1"
 				headerValue	=		"--Select Data--" 
 				cssClass	=		"form_menu_inputselect"
        	>
        	
  			</s:select>  
</body>
</html>
