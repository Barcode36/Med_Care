<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<title>Insert title here</title>
<style>
	.headHeading
	{
		font-size: 9px;
		font-size: 2.0em;
		font-family: Tahoma,Arail,Times New Roman;
		color: #CCCCCC;
	}
</style>
</head>
<body>
<div class="clear"></div>
	<div class="list-icon">
	<s:if test="dataFor=='HDM'">
		<div class="head">Announcements For <s:property value="%{mainHeaderName}"/>  </div>
		</s:if>
		<s:if test="dataFor=='CRC'">
		<div class="head">Announcements </div>
		</s:if>
	</div>
<div class="clear"></div>
<table width="100%" cellspacing="0" cellpadding="3" align="center" border="0">
	
	<tr>
		<td height="10px">
		</td>
	</tr>
	<tr>
		<td width="100%" height="100%">
			<div>
				<table height="100%" align="left" border="0">
					<s:iterator value="newsAlertsList" id="parentTakeaction" status="counter">
					
					<s:if test="dataFor=='HDM'">
					<s:if test="%{module_name=='HDM'}">
						<tr>
							<td valign="top" height="100%">
								<s:if test="%{severity=='High-Red'}">
									<font color="red">
										<s:property value="%{desc}" />
									</font>
								</s:if>
								<s:elseif test="%{severity=='Normal-Blue'}" >
									<font color="blue">
										<s:property value="%{desc}" />
									</font>
								</s:elseif>
								<s:elseif test="%{severity=='Wlow-Green'}">
									<font color="green">
										<s:property value="%{desc}" />
									</font>
								</s:elseif>
							</td>
						</tr>
						</s:if>
						</s:if>
						
						<s:if test="dataFor=='CRC'">
						<s:if test="%{module_name=='CRC'}">
						<tr>
						
							<td valign="top" height="100%">
							<br>
								<s:if test="%{severity=='High-Red'}">
									<font color="red">
									<h3>
									<s:property value="%{name}" />: </h3>
										<s:property value="%{desc}" />
									</font>
								</s:if>
								<s:elseif test="%{severity=='Normal-Blue'}" >
									<font color="blue">
									<h3>
									<s:property value="%{name}" />: </h3>
										<s:property value="%{desc}" />
									</font>
								</s:elseif>
								<s:elseif test="%{severity=='Wlow-Green'}">
									<font color="green">
									<h3>
									<s:property value="%{name}" />: </h3>
										<s:property value="%{desc}" />
									</font>
								</s:elseif>
							</td>
						</tr>
						</s:if>
						</s:if>
						<s:if test="dataFor=='All'">
						<tr>
							<td valign="top" height="100%">
								<s:if test="%{severity=='High-Red'}">
									<font color="red">
										<s:property value="%{desc}" />
									</font>
								</s:if>
								<s:elseif test="%{severity=='Normal-Blue'}" >
									<font color="blue">
										<s:property value="%{desc}" />
									</font>
								</s:elseif>
								<s:elseif test="%{severity=='Wlow-Green'}">
									<font color="green">
										<s:property value="%{desc}" />
									</font>
								</s:elseif>
							</td>
						</tr>
						</s:if>
					</s:iterator>
				</table>
			</div>
		</td>
	</tr>
</table>
</body>
</html>