<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<% 
String userTpe=(String)session.getAttribute("userTpe");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
getChartForTrend($("#trendFor").html());
</script>
<style type="text/css">
.rangeSelector {
top:298px;
}
</style>
<title>Insert title here</title>
</head>
<body>
<div style="overflow: auto;">


	<div id='jqxTabs'>
      <% if(userTpe.equalsIgnoreCase("M"))
	{%>
        <ul>
            <li style="margin-left: 15px;">
                <div style="height: 10px; margin-top: 1px;">
                    <div style="margin-left: 4px; vertical-align: middle; text-align: center; float: left;"><b>Barber</b></div>
                </div>
               
            </li>
            <li>
                <div style="height: 10px; margin-top: 1px;">
                    <div  style="margin-left: 4px; vertical-align: middle; text-align: center; float: left;"><b>Biomedical</b></div>
                </div>
            </li>
             <li>
                <div style="height: 10px; margin-top: 1px;">
                    <div style="margin-left: 4px; vertical-align: middle; text-align: center; float: left;"><b>Dietician</b></div>
                </div>
            </li>
             <li>
                <div style="height: 10px; margin-top: 1px;">
                    <div style="margin-left: 4px; vertical-align: middle; text-align: center; float: left;"><b>Engineering</b></div>
                </div>
            </li>
             <li>
                <div style="height: 10px; margin-top: 1px;">
                    <div style="margin-left: 4px; vertical-align: middle; text-align: center; float: left;"><b>Food & Beverages</b></div>
                </div>
            </li>
             <li>
                <div style="height: 10px; margin-top: 1px;">
                    <div style="margin-left: 4px; vertical-align: middle; text-align: center; float: left;"><b>Housekeeping</b></div>
                </div>
            </li>
        </ul>
         
        
        <div style="overflow: hidden;">
            <div id='chart0' style="width: 100%; height: 100%">
            </div>
        </div>
        <div style="overflow: hidden;">
            <div id='chart1' style="width: 100%; height: 100%">
            </div>
        </div>
        <div style="overflow: hidden;">
            <div id='chart2' style="width: 100%; height: 100%">
            </div>
        </div>
        <div style="overflow: hidden;">
            <div id='chart3' style="width: 100%; height: 100%">
            </div>
        </div>
        <div style="overflow: hidden;">
            <div id='chart4' style="width: 100%; height: 100%">
            </div>
        </div>
        <div style="overflow: hidden;">
            <div id='chart5' style="width: 100%; height: 100%">
            </div>
        </div>
         <% } else { %> 
         <ul>
            <li style="margin-left: 15px;">
                <div style="height: 10px; margin-top: 1px;">
                    <div style="margin-left: 4px; vertical-align: middle; text-align: center; float: left;"><b>Barber</b></div>
                </div>
            </li>
            
           
        </ul>
          
        <div style="overflow: hidden;">
            <div id='chart0' style="width: 100%; height: 100%">
            </div>
        </div>
         <%} %>
    </div>
     
</div>
</body>
</html>