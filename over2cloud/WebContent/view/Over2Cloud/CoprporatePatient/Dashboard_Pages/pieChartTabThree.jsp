<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
 <link rel="stylesheet" href="css/Kaizen/style.css" type="text/css" />
 <script type="text/javascript" src="<s:url value="/js/productivityEvaluation/dashboardProductivity.js"/>"></script>
 <script src="amcharts/themes/Pielight.js"></script>
<style type="text/css">
.amcharts-export-menu .export-main
{

margin-left:-1141%
	}
#DivId {
    width       : 100%;
    height      : 500px;
    font-size   : 11px;
}
.slice1 .amcharts-pie-slice {
  fill: #f00;
}
</style>
<script type="text/javascript">

var moduleName;
var maximizeDivBlock;
var fromdate;
var todate;
var PlantId;
var dataFor;


$(document).ready(function() {
	
	
	 moduleName=$("#moduleName").val();
	 maximizeDivBlock=$("#maximizeDivBlock").val();
	fromdate=$("#fromdate").val();
	 todate=$("#todate").val();
	 PlantId=$("#plantId").val();
	 dataFor=$("#dataFor").val();
	
	
    $(".tabs1-menu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".tab1-content").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
});

var jsonForPieThreeData;
function showPie() {
	
	 maximizeDivBlock=$("#maximizeDivBlock").val();
	fromdate=$("#fromdatetab3").val();
	 todate=$("#todatetab3").val();
	 id=$("#id").val();
	 dataFor="Pie";
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/getJsonDataForChart.action?moduleName="+moduleName+"&maximizeDivBlock="+maximizeDivBlock+"&fromdate="+fromdate+"&todate="+todate+"&id="+id+"&dataFor="+dataFor,
	    success : function(pieData) {
	  	
	    	var total=0;
	
	    	var DivId;
    		var title;
    		var heading;
    		var sampleData;
	    	for(var int=0; int<pieData.length; int ++)
	    		{
	    		sampleData = pieData[int];
	    		if(int==0)
	    			{
	    			for (var i = 0; i < sampleData.length; i++) {
  	    	  		  total=total+parseFloat(sampleData[i].Score);
  	    	  		heading="Total Account Manager : "+total;
  	    	  	  }
  	    	  	  
	    			 DivId='pieChart'+int;
		    		 title="Services";
		    		var chart = AmCharts.makeChart( ""+DivId+"", {
	  	  	    	  "type": "pie",
	  	  	    	  "theme": "dark",
	  	  	    	  "path": "amchart",
	  	  	    	/* "legend": {
	  	  	    	    "markerType": "circle",
	  	  	    	    "position": "right",
	  	  	    	    "marginRight": -30,
	  	  	    	    "autoMargins": false
	  	  	    	  }, */
	  	  	    	"legend": {
	  	  	    	    "markerType": "circle",
	  	  	    	    "position": "right",
	  	  	    	 "marginRight":500,
	  	  	    	    "autoMargins": false,
	  	  	    	 "markerSize":15,
	  	  	    	"verticalGap":1
	  	  	    	  },
	  	  	    	 "titles": [{
	 	  				"text":heading,
	 	  				"size": 15
	 	  			}
	 	  		],
	 	  		
	  	  	    	  "dataProvider":sampleData ,
	  	  	    	  "valueField": "Score",
	  	  	    	  "titleField": title,
	  	  	    	  "outlineAlpha": 0.4,
	  	  	    	  "depth3D": 15,
	  	  	    	 "labelText": "[[title]] : [[value]] %",
	  	  	    	"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> </span>",
	  	  	    	  "angle": 30,
	  	  	    	  "export": {
	  	  	    	 "marginRight":500,
	  	  	    	    "enabled": true
	  	  	    	  }
	  	  	    	} );
	  	  	    	jQuery( '.chart-input' ).off().on( 'input change', function() {
	  	  	    	  var property = jQuery( this ).data( 'property' );
	  	  	    	  var target = chart;
	  	  	    	  var value = Number( this.value );
	  	  	    	  chart.startDuration = 0;

	  	  	    	  if ( property == 'innerRadius' ) {
	  	  	    	    value += "%";
	  	  	    	  }

	  	  	    	  target[ property ] = value;
	  	  	    	  chart.validateNow();
	  	  	    	} );
	  	  	  	chart.write(""+DivId+"");
	    			}
	    		else if(int==1)
	    			{
	    			heading="Total Patient: "+total;
	    			
	    		/* 	heading="Plant Wise Kaizen Shared To OG's"; */
	    			 DivId='pieChart'+int;
		    		 title="Patient";
		    		 
		    		var chart = AmCharts.makeChart( ""+DivId+"", {
	  	  	    	  "type": "pie",
	  	  	    	  "theme": "dark",
	  	  	    	  "path": "amchart",
	  	  	    	/* "legend": {
	  	  	    	    "markerType": "circle",
	  	  	    	    "position": "right",
	  	  	    	    "marginRight": -30,
	  	  	    	    "autoMargins": false
	  	  	    	  }, */
	  	  	    	"legend": {
	  	  	    	    "markerType": "circle",
	  	  	    	    "position": "right",
	  	  	    	 "marginRight":500,
	  	  	    	    "autoMargins": false,
	  	  	    	 "markerSize":15,
	  	  	    	"verticalGap":1
	  	  	    	  },
	  	  	    	 "titles": [{
	 	  				"text":heading,
	 	  				"size": 15
	 	  			}
	 	  		],
	  	  	    	  "dataProvider":sampleData ,
	  	  	    	  "valueField": "Score",
	  	  	    	  "titleField": title,
	  	  	    	  "outlineAlpha": 0.4,
	  	  	    	  "depth3D": 15,
	  	  	    	 "labelText": "[[title]] : [[value]] %",
	  	  	    	"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> </span>",
	  	  	    	  "angle": 30,
	  	  	    	  "export": {
	  	  	    	 "marginRight":1000,
	  	  	    	    "enabled": true
	  	  	    	  }
	  	  	    	} );
	  	  	    	jQuery( '.chart-input' ).off().on( 'input change', function() {
	  	  	    	  var property = jQuery( this ).data( 'property' );
	  	  	    	  var target = chart;
	  	  	    	  var value = Number( this.value );
	  	  	    	  chart.startDuration = 0;

	  	  	    	  if ( property == 'innerRadius' ) {
	  	  	    	    value += "%";
	  	  	    	  }

	  	  	    	  target[ property ] = value;
	  	  	    	  chart.validateNow();
	  	  	    	} );
	  	  	  	chart.write(""+DivId+"");
	  	  	chart.addListener("clickSlice", handlePieClickpie);
	    			}
	    		else if(int==2)
	    			{
	    			heading="Account Manager Status";
	    			 DivId='pieChart'+int;
		    		 title="dept";
		    		 
		    		var chart = AmCharts.makeChart( ""+DivId+"", {
	  	  	    	  "type": "pie",
	  	  	    	  "theme": "light",
	  	  	    	  "path": "amchart",
	  	  	    	/* "legend": {
	  	  	    	    "markerType": "circle",
	  	  	    	    "position": "right",
	  	  	    	    "marginRight": -30,
	  	  	    	    "autoMargins": false
	  	  	    	  }, */
	  	  	    	"legend": {
	  	  	    	    "markerType": "circle",
	  	  	    	    "position": "right",
	  	  	    	    "marginRight":500,
	  	  	    	    "autoMargins": false,
	  	  	    	 "markerSize":15,
	  	  	    	"verticalGap":1
	  	  	    	  },
	  	  	    	 "titles": [{
	 	  				"text":heading,
	 	  				"size": 15
	 	  			}
	 	  		],
	  	  	    	  "dataProvider":sampleData ,
	  	  	    	  "valueField": "Score",
	  	  	    	  "titleField": title,
	  	  	    	  "outlineAlpha": 0.4,
	  	  	    	  "depth3D": 15,
	  	  	    	 "labelText": "[[title]] : [[value]]",
	  	  	    	"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> </span>",
	  	  	    	  "angle": 30,
	  	  	    	  "export": {
	  	  	    	 "marginRight":1000,
	  	  	    	    "enabled": true
	  	  	    	  }
	  	  	    	} );
	  	  	    	jQuery( '.chart-input' ).off().on( 'input change', function() {
	  	  	    	  var property = jQuery( this ).data( 'property' );
	  	  	    	  var target = chart;
	  	  	    	  var value = Number( this.value );
	  	  	    	  chart.startDuration = 0;

	  	  	    	  if ( property == 'innerRadius' ) {
	  	  	    	    value += "%";
	  	  	    	  }

	  	  	    	  target[ property ] = value;
	  	  	    	  chart.validateNow();
	  	  	    	} );
	  	  	  	chart.write(""+DivId+"");
	    			}
	    			
	    	
	    	
	    		}
	    
	  	    },
	   error: function() {
	        alert("Wait !! Previous request is being responded by the server");
	    }
	 });
		/* } */
  
      
  } 




showPie();


</script>
</head>
<body>
<s:hidden id="id" value="%{id}"/>
<s:hidden id="maximizeDivBlock" value="%{maximizeDivBlock}"/>
<s:hidden id="fromdatetab3" value="%{fromdate}"/>
<s:hidden id="todatetab3" value="%{todate}"/>
<div id="tabas-container">
    <ul class="tabs1-menu">
        <li class="current"><a href="#tab1-1">Account Manager Wise Status </a></li>
        <li><a href="#tab1-2" > Patient Wise Status</a></li>
    </ul>

    <div class="tab1">
        <div id="tab1-1" class="tab1-content">
         <div id='pieChart0'  class=""  style="width: 110%; height: 450px; margin-top: 0%; overflow: hidden; text-align: left;"></div>
        </div>
        <div id="tab1-2" class="tab1-content">
   		   <div id='pieChart1'  style="width: 100%; height: 450px; margin-top: 0%; overflow: hidden; text-align: left;"></div>
 		 </div>
    </div>
</div>
<sj:dialog 
	id="piechartTable" 
	modal="true" effect="slide" 
	autoOpen="false"  width="800" 
	height="370"
	hideEffect="explode" 
	   cssStyle="overflow: hidden;"
	position="['center','middle']"
	
>
<div id='pieChart4'  style="width: 90%; height: 400px; margin-top: 0%; overflow: hidden; text-align: left;"></div>
</sj:dialog> 

</body>
</html>