<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="<s:url value="/js/dashboard/feedbackDash.js"/>"></script>
<link href="<s:url value="/css/style.css"/>" rel="stylesheet" type="text/css"/>
<style>
	.tableData
	{
		font-size: 9px;
		font-family: Tahoma,Arail,Times New Roman;
		color: #000000;
	}
	.tableHeading
	{
		font-size: 9px;
		font-size: 1.0em;
		font-family: Tahoma,Arail,Times New Roman;
		color: #FFFFFF;
	}
	
	.tableHeadingGreen
	{
		font-size: 9px;
		font-size: 1.0em;
		font-family: Tahoma,Arail,Times New Roman;
		color: #00FF00;
	}
	
	.tableHeadingRAS
	{
		font-size: 9px;
		font-size: 1.0em;
		font-family: Tahoma,Arail,Times New Roman;
		color: #FF00FF;
	}
	
	.tableHeadingIG
	{
		font-size: 9px;
		font-size: 1.0em;
		font-family: Tahoma,Arail,Times New Roman;
		color: #4A21EB;
	}
	
	.tableHeadingHP
	{
		font-size: 9px;
		font-size: 1.0em;
		font-family: Tahoma,Arail,Times New Roman;
		color: #F00000;
	}
	
	.tableHeadingSZ
	{
		font-size: 9px;
		font-size: 1.0em;
		font-family: Tahoma,Arail,Times New Roman;
		color: #B80000;
	}

	.tableHeadingTotal
	{
		font-size: 9px;
		font-size: 1.0em;
		font-family: Times New Roman;
		color: #FF0000;
	}
	
	.tableHeadingEscalation
	{
		font-size: 9px;
		font-size: 1.0em;
		font-family: Tahoma,Arail,Times New Roman;
		color: #FFFFFF;
	}
	
	.tableHeadingPending
	{
		font-size: 9px;
		font-size: 1.0em;
		font-family: Tahoma,Arail,Times New Roman;
		color: #FFFFFF;
	}
	
</style>
</head>
<body>
<div class="middle-content">
<div class="contentdiv-small" style="overflow: top;background-color:#F2F2F2; margin-top: 61px ;height:350px">
	<div class="headding" align="center" style="margin-left: 104px;"><CENTER><h3>News And Alerts For <s:property value="%{mainHeaderName}"/></h3></CENTER></div>	
</div>
<div style="overflow: top;margin-top:-29px;margin-left: 1 px ">
<marquee onmouseover="this.stop()" onmouseout="this.start()" scrollamount="1" scrolldelay="2" direction="up" width="90%" height="97%" >
											<table height="100%" align="center" border="0">
												<s:iterator value="newsAlertsList" id="parentTakeaction" status="counter">
													<s:if test="%{parentTakeaction.severity=='High-Red'}">
														<tr align="center">
															<td  align="center">
															<b><s:property value="#parentTakeaction.severity"/></b>
																<div align="justify" >
																	<font color="red">
																		<b><s:property value="#parentTakeaction.desc"/></b>
																	</font>
																</div>
															</td>
														</tr>
													</s:if>
													<p />
													<s:elseif test="%{severity=='Normal-Blue'}" >
														<tr align="center">
															<td  align="center">
																<div align="justify" >
																	<font color="blue">
																		<b><s:property value="#parentTakeaction.desc"/></b>
																	</font>
																</div>
															</td>
														</tr>
													</s:elseif>
													<p />
													<s:elseif test="%{severity=='Wlow-Green'}">
														<tr align="center">
															<td  align="center">
																<div align="justify" >
																	<font color="green">
																		<b><s:property value="#parentTakeaction.desc"/></b>
																	</font>
																</div>
															</td>
														</tr>
													</s:elseif>	
													<p />		
												</s:iterator>
											</table>
		 								</marquee>
</div>
</div>
</body>
</html>