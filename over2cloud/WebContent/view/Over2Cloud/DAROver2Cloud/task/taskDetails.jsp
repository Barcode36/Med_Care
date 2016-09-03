<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

   <table width="100%" border="1" style="border-collapse: collapse;">
        
        <tr bgcolor="#D8D8D8" style="height: 25px">
          <s:iterator value="%{taskDetails}" status="status" >
            <s:if test="%{key=='Task Name' || key=='Specific Task' || key=='Task Type' || key=='Task Priority'}">
                    <td align="left" width="10%" ><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="left" width="10%"> <s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
        </tr>
        <tr style="height: 25px">
         <s:iterator value="%{taskDetails}" status="status" >
            <s:if test="%{key=='For' || key=='Name' || key=='Offering' || key=='Alloted By'}">
                    <td align="left" width="10%"><strong><s:property value="%{key}"/>:</strong></td>
                   <td align="left" width="10%" > <s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
       </tr>
          
</table> 
</body>
</html>