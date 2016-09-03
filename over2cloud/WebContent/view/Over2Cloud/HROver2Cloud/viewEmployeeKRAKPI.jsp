<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<center>
<table width="100%" cellspacing="0" cellpadding="3">
<tr><td bgcolor="#0e0e0e" class="tabular_head" valign="middle" colspan="7">Mapped KPI with employee</td></tr>
	<tr><td bgcolor="#252525" style=" border-bottom:1px solid #e7e9e9; color:#ffffff;" valign="top" class="tabular_cont">
		<table cellspacing="0" cellpadding="0" width="100%"><tbody>
			<tr>
				<td valign="middle" width="2.5%">SR</td>
				<td valign="middle" width="20%">KRA</td>
				<td valign="middle" width="20%">KPI</td>
			</tr>
		</tbody></table>
	</td>
	</tr>
<s:iterator value="empKpiLiist" id="empKpiLiist" status="counter">  
<s:if test="#counter.count%2 != 0">
	<tr><td bgcolor="#ffffff" style=" border-bottom:1px solid #e7e9e9; color:#181818;" valign="top" class="tabular_cont">
		<table cellspacing="0" cellpadding="0" width="100%"><tbody>
			<tr>
				<td valign="middle" width="2.5%" bgcolor="#252525" style=" color:#ffffff; text-align:center;"><s:property value="#counter.count" /></td>
				<td valign="middle" width="20%"><s:property value="%{key}"/></td>
				<td valign="middle" width="20%"><s:property value="%{value}"/></td>
			</tr></tbody>
		</table>
	</td></tr>
</s:if><s:else>
<tr><td bgcolor="#e2e4e4" style=" border-bottom:1px solid #e7e9e9; color:#181818;" valign="top" class="tabular_cont">
		<table cellspacing="0" cellpadding="0" width="100%"><tbody>
			<tr>
				<td valign="middle" width="2.5%" bgcolor="#252525" style=" color:#ffffff; text-align:center;"><s:property value="#counter.count" /></td>
				<td valign="middle" width="20%"><s:property value="%{key}"/></td>
				<td valign="middle" width="20%"><s:property value="%{value}"/></td>
			</tr></tbody>
		</table>
	</td></tr>
</s:else>
</s:iterator> 
</table>
</center>
</body>
</html>