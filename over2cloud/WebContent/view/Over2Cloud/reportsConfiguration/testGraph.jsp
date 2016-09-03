<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/struts-tags"  prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/js.js"/>"></script>



<script type="text/javascript" src="https://www.google.com/jsapi"></script>

<script type="text/javascript">

  // Load the Visualization API and the piechart package.
  google.load('visualization', '1.0', {'packages':['corechart']});

  // Set a callback to run when the Google Visualization API is loaded.
  google.setOnLoadCallback(drawChart);

  // Callback that creates and populates a data table,
  // instantiates the pie chart, passes in the data and
  // draws it.
  function drawChart()
  {

    // Create the data table.
   
    var data1 = new google.visualization.DataTable();
    data1.addColumn('string', 'Department');
    data1.addColumn('number', 'Ticket');

    data1.addRows([["Pending",'10']]);
    data1.addRows([["High Priority",'20']]);
    data1.addRows([["Snooze",'30']]);
    data1.addRows([["Resolved",'40']]);
    data1.addRows([["Ignore",'50']]);

    var options1 = {'title':'Total tickets status',
            'width':600,
            'height':500};

    
    var chart1 = new google.visualization.PieChart(document.getElementById('chart_div1'));
    chart1.draw(data1, options1);

    
  }
	  
</script>

<script type="text/javascript" src="<s:url value="/js/jquery-1.3.1.min.js"/>" > </script> 
</head>
<body>
		<center>
			
				<h3>A Pie Chart</h3>
    <sjc:chart
    	id="chartPie"
    	cssStyle="width: 600px; height: 400px;"
    	pie="true"
    	pieLabel="true"
    >
    	<sjc:chartData
    		id="pieSerie1"
    		label="Serie 1"
    		data="10"
    	/>
    	<sjc:chartData
    		id="pieSerie2"
    		label="Serie 2"
    		data="3"
    	/>
    	<sjc:chartData
    		id="pieSerie3"
    		label="Serie 3"
    		data="17"
    	/>
    	<sjc:chartData
    		id="pieSerie4"
    		label="Serie 4"
    		data="37"
    	/>
    </sjc:chart>
				
		
				
		</center>
</body>
</html>
