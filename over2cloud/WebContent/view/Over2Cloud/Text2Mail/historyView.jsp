<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">

function abc(){
	var id = $("#valueId").val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Text2Mail/historyGrid.action?idv="+id,
	    success : function(feeddraft) {
	    	console.log("mydata");
	    	console.log(feeddraft);
       $("#datapart1").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}
abc();


</script>
</head>
<body>
<div>
<table border="1" width="100%" style="border-collapse: collapse;">
				<s:iterator value="oneH" status="status">
				<s:if test="%{key=='id'}">
				<s:hidden id="valueId" value="%{oneH.id}"></s:hidden>
				</s:if>
					<s:if test="#status.odd">
					
					<s:if test="%{key!='id'}">
						<tr bgcolor="#D8D8D8" style="height: 25px">
							<td width="10%"><b><s:property value="%{key}"/>:</b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</tr>
						</s:if>
					</s:if>
					<s:elseif test="#status.even">
						<tr style="height: 23px">
							<td width="10%"><b><s:property value="%{key}"/>:</b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</tr>
					</s:elseif>
				</s:iterator>
			</table>
			</div>
	
	<div id="datapart1"></div>		
</body>
</html>