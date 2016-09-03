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

$(document).ready(function() 
{
	
     $(".tabs1-menu a").click(function(event)
    {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".tab1-content").not(tab).css("display", "none");
       // $(".tab1-content").not(tab).css( "  position"," fixed");
     
        $(tab).fadeIn();
    });
});


var jsonForPieTwoData;
function showPieChartTab2() 
{
	 maximizeDivBlock=$("#maximizeDivBlock").val();
	 fromdate=$("#fromdatetab2").val();
	 todate=$("#todatetab2").val();
	 id=$("#id").val();
	 dataFor="Pie";
	 $.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/getJsonDataForChart.action?moduleName="+moduleName+"&maximizeDivBlock="+maximizeDivBlock+"&fromdate="+fromdate+"&todate="+todate+"&id="+id+"&dataFor="+dataFor,
  	    success : function(data) 
  	    {
  	    	jsonForPieTwoData=data;
  	    	var total=0;
  	    	var heading;
  	  	    for (var int = 0; int < data.length; int++) 
  	  	    {
  				total=total+parseFloat(data[int].Score);
  			}
  	  	    if(total==0)
  	  	    {
  	  	    	$('#pieChart1').html("<center><img src='images/noPie2.png' width='400' height='300' style='opacity:.5'  /><br/><font style='opacity:.5' size='4'>No Data Available</font></center>");
  	  	    }else
  	  	    {
  	    		var DivId;
	    		var title;
	    		var total=0;
  	    	for(var int=0; int<data.length; int ++)
  	    		{
  	    		sampleData = data[int];
  	    		if(int==0)
  	    			{
  	    			for (var i = 0; i < sampleData.length; i++) {
    	    	  		  total=total+parseFloat(sampleData[i].Score);
    	    	  		
    	    	  	  }
  	    			heading="Total Patient: "+total ;
  	    			 DivId='pieChart'+int;
  		    		 title="Patient";
  		    		var chart = AmCharts.makeChart( ""+DivId+"", {
  	  	  	    	  "type": "pie",
  	  	  	    	  "theme": "light",
  	  	  	    	  "path": "amchart",
  	  	  	    
  	  	  	    	"legend": {
  	  	  	    	    "markerType": "circle",
  	  	  	    	    "position": "right",
  	  	  	    	    "verticalGap":1,
  	  	  	    	    "marginRight": 80,
  	  	  	    	    "autoMargins": false,
  	  	  	    	    "markerSize":15
  	  	  	   
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
  	  	  	   		 "labelText": "[[title]] : [[Per]] %",
  	  	  	    	"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> </span>",
  	  	  	    	  "angle": 30,
  	  	  	    	  "export": {
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
  	  	  		chart.addListener("clickSlice", handlePie);
  	    			}
  	    		else if(int==1)
  	    			{
  	    			heading="Total Location: "+total  ;
  	    			 DivId='pieChart'+int;
  		    		 title="Location";
  		    		var chart = AmCharts.makeChart( ""+DivId+"", {
  	  	  	    	  "type": "pie",
  	  	  	    	  "theme": "light",
  	  	  	    	  "path": "amchart",
  	  	  	    
  	  	  	    	"legend": {
  	  	  	    	    "markerType": "circle",
  	  	  	    	    "position": "right",
  	  	  	    	    "marginRight": 80,
  	  	  	      "marginRight":200,
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
  	  	  	    	"labelText": "[[title]] : [[Per]] %",
  	  	  	    	"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> </span>",
  	  	  	    	  "angle": 30,
  	  	  	    	  "export": {
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
  	    		}
  	    	
  	  	    }},
   	   error: function() {
   	        alert("Wait !! Previous request is being responded by the server");
   	    }
   	 });
    		
    		/* } */
}
showPieChartTab2();
//Froe Grid Call By Services
function handlePie(event)
{
	var indexFor = event.dataItem;
	var dataContx = indexFor.dataContext;
	show(dataContx.corpId,dataContx.Patient,'Patient Name : '+dataContx.Patient);
}
function show(corpId,patient,title)
{
	  var fromDate=$("#fromdate1").val();
		var  toDate=$("#todate1").val();
		 $('#gridDialog').dialog({title:title,width:1240,height:505});
		 $("#gridDialog").dialog('open');
		 $("#gridDataDiv").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/CorporatePatientServices/cpservices/viewAccountManagerPage.action",
			    data: " corpId="+corpId+"&fromDate="+fromDate+"&toDate="+toDate+"&patient="+patient,//&dataFor="+status+"&reviewDate="+implementOn+"&action="+action+"&mode=action&otherPlant="+plantId,
			    success : function(data) 
			    {
			        $("#gridDataDiv").html(data);
			    },
			    error: function() 
			    {
		            //alert("error");
		        }
			 });
}
//Grid call By Patient
function handlePieClickpie(event)
{
	var indexFor = event.dataItem;
	var dataContx = indexFor.dataContext;
	showCall(dataContx.corpId,dataContx.locId,'Location Name : '+dataContx.Location);
}
function showCall(corpId,location,title)
{
	var fromDate=$("#fromdate1").val();
	var  toDate=$("#todate1").val();
		 $('#gridDialog').dialog({title:title,width:1240,height:505});
		 $("#gridDialog").dialog('open');
		 $("#gridDataDiv").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/CorporatePatientServices/cpservices/viewAccountManagerPage.action",
			    data: " corpId="+corpId+"&fromDate="+fromDate+"&toDate="+toDate+"&location="+location,//&dataFor="+status+"&reviewDate="+implementOn+"&action="+action+"&mode=action&otherPlant="+plantId,
			    success : function(data) 
			    {
			        $("#gridDataDiv").html(data);
			    },
			    error: function() 
			    {
		            //alert("error");
		        }
			 });
}
</script>
</head>
<body>
<s:property value="%{fromdate}"/>
<s:property value="%{todate}"/>
<s:hidden id="id" value="%{id}"/>
<s:hidden id="maximizeDivBlock" value="%{maximizeDivBlock}"/>
<div id="tabas-container">
    <ul class="tabs1-menu">
        <li class="current"><a href="#tab1-1">Patient Wise Status </a></li>
        <li><a href="#tab1-2" > Patient Location Wise Status </a></li>
    </ul>

    <div class="tab1">
        <div id="tab1-1" class="tab1-content">
         <div id='pieChart0'  class=""  style="width: 82%; height: 450px; margin-top: 0%; overflow: hidden; text-align: left;margin-left: -332px;"></div>
        </div>
        <div id="tab1-2" class="tab1-content">
   		   <div id='pieChart1'  style="width: 90%; height: 450px; margin-top: 0%; overflow: hidden; text-align: left; margin-left: -225px;"></div>
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