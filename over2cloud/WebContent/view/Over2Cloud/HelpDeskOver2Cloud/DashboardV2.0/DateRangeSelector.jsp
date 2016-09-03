<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="<s:url value="/js/dashboard/dashboardR&D.js"/>"></script>
</head>
<body>
<s:form id="formone" name="formone" action="" theme="css_xhtml"  method="post"
						enctype="multipart/form-data"  >

						
						<div class="newColumn">
							
							<div class="rightColumn1">
								<sj:datepicker id="sdate" name="sdate"  displayFormat="dd-mm-yy"  
									required="true"  cssClass="textField" cssStyle="margin:0px 0px 10px 0px;" 
									changeMonth="true" yearRange="2013:2050" changeYear="true" readonly="true" Placeholder="Select From Date"></sj:datepicker>

							</div>
						</div>
						
						<div class="newColumn">
							
							<div class="rightColumn1">
								<sj:datepicker id="edate" name="edate"  displayFormat="dd-mm-yy"  
									required="true"  cssClass="textField" cssStyle="margin:0px 0px 10px 0px;" 
									changeMonth="true" yearRange="2013:2050" changeYear="true" readonly="true" Placeholder="Select To Date"></sj:datepicker>

							</div>
						</div>
						
	
						
						
						<!-- Buttons -->
						<div class="clear"></div>

						
							<div style="margin-left: 200px">
										<sj:a
											value="Ok" 
											effect="highlight"
											effectOptions="{ color : '#222222'}" 
											effectDuration="5000"
											button="true" 
											cssClass="submit"
											style="float: left;width:100px;" onclick="beforegetDataByDate()" >Done</sj:a>
								</div>	
						
					</s:form>

</body></html>