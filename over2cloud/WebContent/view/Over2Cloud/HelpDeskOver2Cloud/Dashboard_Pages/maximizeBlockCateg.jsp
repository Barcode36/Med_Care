<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
//category pie
$.ajax({
    type : "post",
    url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/jsonChartData4CategCounters.action",
    type : "json",
    success : function(data) {
    	
    	
    	sampleData = data;
    	 var total=0;
	  	    for (var int = 0; int < data.length; int++) {
				total
				=total+parseFloat(data[int].Counter);
			}
	  	  if(total==0||isNaN(total)){
		  		
  	  	    $('#jqxPieChartCateg').html("<center><img src='images/noPie2.png' width='600' height='300' style='opacity:.5'  /><br/><font style='opacity:.9;color:#0F4C97;' size='5'>No Data Available</font></center>");
  	  	    }else{
    	  var settings = {
                  title: "",
                  description: "For All Departments",
                  enableAnimations: true,
	              showLegend: true,
	              padding: { left: 5, top: 5, right: 5, bottom: 5 },
                  titlePadding: { left: 0, top: 0, right: 0, bottom: 10 },
                  source: sampleData,
                  colorScheme: 'scheme06',
                  seriesGroups:
                      [
                          {
                              type: 'pie',
                              showLabels: true,
                              series:
                                  [ 
                                    { 
                                    	dataField: 'Counter',
                                    	displayText: 'Category',
                                    	labelRadius: 150,
                                    	initialAngle: 15,
                                    	radius: 95,
                                    	centerOffset: 0,
										formatFunction: function (value) {
                                        	
                                            if (isNaN(value))
                                                return value;
                                            return parseFloat((value/total)*100).toFixed(1)+'%' ;
                                        }
                                        
                                    }
                                    
                                    
                                  ]
                          }
                      ]
              };
    	// setup the chart
    	  $('#jqxPieChartCateg').jqxChart({ enableAnimations: true });
        $('#jqxPieChartCateg').jqxChart(settings);
        $("#FlipValueAxis").jqxCheckBox({ width: 140,  checked: false });
        $("#FlipCategoryAxis").jqxCheckBox({ width: 140,  checked: false });
        var refreshChart = function () {
            $('#jqxPieChartCateg').jqxChart({ enableAnimations: false });
            $('#jqxPieChartCateg').jqxChart('refresh');
        }
        // update greyScale values.
        $("#FlipValueAxis").on('change', function (event) {
            var groups = $('#jqxPieChartCateg').jqxChart('seriesGroups');
            groups[0].valueAxis.flip = event.args.checked;
            refreshChart();
        });
        $("#FlipCategoryAxis").on('change', function (event) {
            var categoryAxis = $('#CategChart').jqxChart('categoryAxis');
            categoryAxis.flip = event.args.checked;
            refreshChart();
        });
    	
}},
   error: function() {
        alert("error from jsonArray data ");
    }
 });
// pie end here

//Doughnut chart

$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/jsonChartData4CategCounters.action",
	    type : "json",
	    success : function(data) {
	    	
	    	 if(total==0||isNaN(total)){
			  		
	   	  	    $('#jqxDoughnutChartCateg').html("<center><img src='images/noDoughnut.png' width='500' height='370' style='opacity:.5'  /><br/><font style='opacity:.9;color:#0F4C97;' size='5'>No Data Available</font></center>");
	   	  	    }else{	
	    	sampleData = data;
	    	 var total=0;
		  	    for (var int = 0; int < data.length; int++) {
					total=total+parseFloat(data[int].Counter);
				}
	
	    	 var settings = {
	                 title: "",
	                 description: "",
	                 enableAnimations: true,
	                 showLegend: true,
	                 showBorderLine: true,
	                 padding: { left: 5, top: 5, right: 5, bottom: 5 },
	                 titlePadding: { left: 0, top: 0, right: 0, bottom: 10 },
	                 source: sampleData,
	                 colorScheme: 'scheme06',
	                 seriesGroups:
	                     [
	                         {
	                             type: 'donut',
	                             showLabels: true,
	                             series:
	                                 [
	                                     {
	                                         dataField: 'Counter',
	                                         displayText: 'Category',
	                                         labelRadius: 100,
	                                         initialAngle: 15,
	                                         radius: 160,
	                                         innerRadius: 70,
	                                         centerOffset: 0,
	                                         formatFunction: function (value) {
	                                         	
	                                             if (isNaN(value))
	                                                 return value;
	                                             return parseFloat((value/total)*100).toFixed(1)+'%' ;
	                                         }
	                                         
	                                     }
	                                 ]
	                         }
	                     ]
	             };
	
	    	  $('#jqxDoughnutChartCateg').jqxChart(settings);
}},
error: function() {
  alert("error from jsonArray data ");
}
});
//chart end here

</script>
<title>Insert title here</title>
</head>
<body>
<center>
<s:if test="maximizeDivBlock=='catg_graph'">
<div id='jqxPieChartCateg' style="width: 100%; height: 400px" align="center"></div>
</s:if>
<s:elseif test="maximizeDivBlock=='categ_doughnutChart'">
<div id='jqxDoughnutChartCateg' style="width: 100%; height: 400px" align="center"></div>
</s:elseif>
<s:elseif test="maximizeDivBlock=='catg_table'">
	<table border="1" width="100%" align="center">
    <tr>
		<td align="center" width="70%"   bgcolor="#F64D54" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp; Category</b></td>
		<td align="center" width="30%" bgcolor="#F64D54" style=" color:#ffffff; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b>&nbsp;Counter</b></td>
	</tr>
</table>
<s:iterator id="rsCompl1dfr4245"  status="status" value="%{catgCountList}" >
<table border="1" width="100%" align="center">
	<tr>
	    <td align="left" width="70%"   bgcolor="#EDDBDA" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;"><b><s:property value="%{feedback_catg}"/></b></td>
	<td align="center" width="30%" bgcolor="#EDDBDA" style=" color:#125EA9; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;" ><a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="%{counter}"/></b></a></td>
	</tr>
	</table>
</s:iterator>
</s:elseif>




</center>
</body>
</html>