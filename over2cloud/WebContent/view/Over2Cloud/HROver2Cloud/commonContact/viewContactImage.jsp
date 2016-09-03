<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
function viewGroup() {
	var conP = "<%=request.getContextPath()%>";
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : conP+"/view/Over2Cloud/hr/beforeCommonContactView.action",
        success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
}
</script>
</head>
<body><br/><br/>
<div style="text-align: center;">
	<s:if test="%{empLogoFileName == null || empLogoFileName == ''}">
			No Image Uploaded
	</s:if>
	<s:else>
		<img src="<s:property value="empLogoFileName"/>" width="300px" height="400px"/>
	</s:else>
</div>
<br/>
<div style="text-align: center;">
	 <sj:a  button="true" href="#" value="View" onclick="viewGroup();" >Back</sj:a>
</div>
</body>
</html>