<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<body>
<div class="page_title"><h1>Purchased application details</h1></div>
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<div class="form_inner" id="form_reg">
<div class="page_form">
<div class="group_content" align="center">
							<table width="100%" cellpadding="5" cellspacing="0" border="1" bordercolor="#bebdbc" style=" border-collapse:collapse;"><tbody>
								<tr class="group_head">
									<td width="45%"><b>Application Name</b></td>
									<td width="45%"><b>Application User</b></td>
								</tr>
								
								<s:iterator  value="#session['listofproduct']" status="counter">  
									  <s:set var="applicationCode" value="app_code" />
                                      <s:set var="applicationName" value="app_name" />
										<s:if test="#counter.count%2 != 0">
										<tr bgcolor="#e1e3e3" id="row<s:property value="%{id}"/>">
											<td align="left" valign="middle"><s:property value="applicationName" /></td>
											<td align="left" valign="middle"><s:property value="applicationCode" /> <span>users</span></td>
										</tr>
										</s:if>
										<s:else>
										<tr id="row<s:property value="%{id}"/>">
											<td align="left" valign="middle"><s:property value="applicationName" /></td>
											<td align="left" valign="middle"><s:property value="applicationCode" /> <span>users</span></td>
										</tr>
										</s:else>
								</s:iterator>
								         <tr bgcolor="#e1e3e3" id="row<s:property value="%{id}"/>">
											<td align="left" valign="middle"><b>Total</b></td>
											<td align="left" valign="middle"><b><s:property value="#session['totaluser']" /><span> users</span></b></td>
										</tr>
							</tbody></table>
</div>
</div>
</div>
</div>
</div>
</body>
</html>