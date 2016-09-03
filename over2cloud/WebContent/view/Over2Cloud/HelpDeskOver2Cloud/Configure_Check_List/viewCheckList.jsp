<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
 
<s:if test="FormatFor=='comTip'">
<s:if test="checkListDetails.size()==0">
 <table border="1" width="100%" align="center">
		<tbody>
		<tr bgcolor="#D8D8D8">
			<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#00000" size="2"><b>Sorry there is no completion Tip for this Sub Category!!!!!</b></font></td>
			 	</tr>
	 	 </tbody>
	 	 </table>
 </s:if>
	  <s:else >
	 	<table border="1" width="100%" align="center">
		<tbody>
		<tr bgcolor="#D8D8D8">
			<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#00000" size="2"><b>S.No</b></font></td>
			<td  align="center" width="25%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#00000" size="2"><b>Completion Name</b></font></td>
	 	</tr>
 <s:iterator value="%{checkListDetails}" status="status" >
 <s:if test="#status.odd">
            <tr bgcolor="#FFFFFF" style="height: 25px">
            <td align="center" width="10%"><font face="Arial, Helvetica, sans-serif" color="#00000" size="2">
				<s:property value="%{key}"/>.</font></td>
            <td align="center" width="10%"><font face="Arial, Helvetica, sans-serif" color="#00000" size="2">
				<s:property value="%{value}"/></font></td>
            </tr>
  </s:if>
  <s:else>
   <tr bgcolor="#D8D8D8" style="height: 25px">
            <td align="center" width="10%"><font face="Arial, Helvetica, sans-serif" color="#00000" size="2">
				<s:property value="%{key}"/>.</font></td>
           <td align="center" width="10%"><font face="Arial, Helvetica, sans-serif" color="#00000" size="2">
				<s:property value="%{value}"/></font></td>
            </tr>
  
  </s:else>
        </s:iterator>
        </tbody>
</table>
</s:else>
 </s:if>
  
 <s:if test="FormatFor=='krList'">  
 <s:if test="%{dataList.size()==0}">
	 	 <table border="1" width="100%" align="center">
		<tbody>
		<tr bgcolor="#D8D8D8">
			<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#00000" size="2"><b>Sorry there is no KR uploaded for this Sub Category!!!!!</b></font></td>
			 	</tr>
	 	 </tbody>
	 	 </table>
	 	 </s:if>
	  <s:else>
	 	<table border="1" width="100%" align="center">
		<tbody>
		<tr bgcolor="#D8D8D8">
			<td  align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#00000" size="2"><b>S.No</b></font></td>
			<td  align="center" width="25%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#00000" size="2"><b>KR Name</b></font></td>
			<td  align="center" width="25%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#00000" size="2"><b>Doc Name</b></font></td>
		</tr>
		<s:iterator id="totalKRId"  status="status" value="%{dataList}" >
			 <s:if test="%{#status.odd}">
	 	
			<tr>
			 </s:if>
			 <s:else>
			 
			<tr bgcolor="#D8D8D8">
			 </s:else>
				<td align="center" width="10%"><font face="Arial, Helvetica, sans-serif" color="#00000" size="1">
					<s:property value="%{#status.count}"/>
				</font></td>
				<td align="center" width="25%"><font face="Arial, Helvetica, sans-serif" color="#00000" size="1">
					<s:property value="#totalKRId.name"/>
				</font></td>
				<td align="center" width="25%"><font face="Arial, Helvetica, sans-serif" color="#00000" size="1">
					<a href="<%=request.getContextPath()%>/view/Over2Cloud/Compliance/compliance_pages/confirmDownload.action?fileName=<s:property value="#totalKRId.orginalDocPath"/>"><B><s:property value="#totalKRId.docPath"/></B></a>
				</font></td>
			</tr>
		</s:iterator>
		</tbody>
		</table>
		 </s:else>
  </s:if>
</body>
</html>