var title='';
var title1='';
var desc ='';
var statusJsonData=[];
var levelJsonData=[];
var categJsonData=[];
function getChartTitle()
{
	 if($.trim($("#sdate").val())!=""){
		 desc='Data From '+$("#sdate").val()+' TO '+$("#edate").val();
	 }else {
		 desc='Data From '+$("#hfromDate").val()+' TO '+$("#htoDate").val(); 
	 }
	 var dept=$("#deptname").val();
	 if(dept=="-1")
	 {
		 title='All Department ';
		 
	 }else if(dept!="-1" )
	 {
		 title=$("#deptname").find(":selected").text();
		
	 }
	 return title;
}

function getChartTitle2()
{
	
	 if($.trim($("#sdate").val())!=""){
		 desc='Data From '+$("#sdate").val()+' TO '+$("#edate").val();
	 }else {
		 desc='Data From '+$("#hfromDate").val()+' TO '+$("#htoDate").val(); 
	 }
	
	 var dept=$("#deptname1").val();
	 if(dept=="-1")
	 {
		 title1='All Department ';
		 
	 }else if(dept!="-1" )
	 {
		 title1=$("#deptname").find(":selected").text();
		
	 }
	 return title1;
}
	
	//category pie
 
	function StatckedChartStatus(divId,filterFlag,filterDeptId,status,title,color) {
	
		status1=status;
		title1=title;
		color1=color;
		if(status==''){
			//alert(status);
			status='Resolved';
			maxDivId1='1stStackedBar';
			var  sampleData =null;
			var url1='';
			var deptId=$("#deptname").val();
			if(deptId=='-1')
			{
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages_Mgt/jsonChartData4DeptCounters.action?filterFlag="+filterFlag+"&filterDeptId="+filterDeptId+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
				title="All Tickets For All Department: ";
			}else
			{
				var str=$("#deptname").find('option:selected').text();
				title=str+" Total ";
				title="All Tickets For "+str+" Department: ";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages_Mgt/jsonChartData4DeptCounters.action?filterFlag=H&filterDeptId="+deptId+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
				
			}
			$.ajax({
			    type : "post",
			    url : url1,
			    async:false,
			    type : "json",
			    success : function(data) {
			    	statusJsonData=data;
			    	var total=0;
			    	//alert(data.length);
			//console.log(data);
			    	for (var i=0;i<=data.length-1;i++){
			    	//	alert(data[i].Pending);
			    		var temp=0;
			    		total=total+data[i].Pending+data[i].Snooze+data[i].Ignore+data[i].Resolved+data[i].Reassigned+data[i].Reopened;
			    		temp=temp+data[i].Pending+data[i].Snooze+data[i].Ignore+data[i].Resolved+data[i].Reassigned+data[i].Reopened;
			    		data[i].Total = temp;
			    		temp=0;
			    	}
			    	
			    	for(var x in data)
			    	{
			    		 data[x].TotalPer = Math.round(data[x].Total / total * 100);
			    		
			    	}
			    	//alert(total);
			    	if(total==0||isNaN(total)){
				  		
		    	  	    $('#'+divId).html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
		    	  	    }else{
		    	  	    	
			    	sampleData = data;
			    	drawStatusChart(divId,sampleData,status,title,color,"dept",total);
			    	
			}},
			   error: function() {
			        alert("error");
			    }
			 });
		}
			else
		{
			drawStatusChart(divId,statusJsonData,status,title,color,"dept");	
		}
		
	//	alert('from : '+ $('#sdate').val()+' to: '+$('#edate').val());
	
	
	}//end here
	
	function drawStatusChart(divId,data,status,title,color,categ,total){
	
	
	console.log(data);
		var chart = AmCharts.makeChart( divId, {
  		  "type": "serial",
  		"titles" : [ {
			"text" : title+" "+total,
			"size" : 15
		} ],
  		  "theme": "light",
  		"legend": {
  			"align": "center",
  		    "useGraphSettings": true,
  		  "equalWidths": false,
  		"markerLabelGap": 4,
		"markerSize": 9,
		"spacing": -53,
		"autoMargins": false
  		  },
  		  "dataProvider": data,
  		  "valueAxes": [ {
  			  "stackType":"regular",
  			    "gridColor": "#FFFFFF",
  			    "gridAlpha": 0,
  			    "dashLength": 0,
  			    "totalText":"[[TotalPer]]%",
  			    "totalTextOffset":10  			    
  			  } ],
  		  "gridAboveGraphs": true,
  		  "startDuration": 1,
  		  "graphs": [ {
  		    "balloonText": "[[category]]: <b>[[value]]</b>",
  		    "fillAlphas": 1,
  		    "lineAlpha": 0,
  		    "type": "column",
  		    "valueField": "Pending",
  		  
  		    "title":"Pending",
  		    "fixedColumnWidth":40
  		    	
  		  },
  		{
    		    "balloonText": "[[category]]: <b>[[value]]</b>",
    		    "fillAlphas": 1,
    		    "lineAlpha": 0,
    		    "type": "column",
    		    "valueField": "Resolved",
    		  
    		    "title":"Resolve",
    		    "fixedColumnWidth":40
    		    	
    		  },{
    	  		    "balloonText": "[[category]]: <b>[[value]]</b>",
    	  		    "fillAlphas": 1,
    	  		    "lineAlpha": 0,
    	  		    "type": "column",
    	  		    "valueField": "Snooze",
    	  		  
    	  		    "title":"Park",
    	  		    "fixedColumnWidth":40
    	  		    	
    	  		  },{
    	    		    "balloonText": "[[category]]: <b>[[value]]</b>",
    	      		    "fillAlphas": 1,
    	      		    "lineAlpha": 0,
    	      		    "type": "column",
    	      		    "valueField": "Reassigned",
    	      		 
    	      		    "title":"Re-Assign",
    	      		    "fixedColumnWidth":40
    	      		    	
    	      		  },{
    	        		    "balloonText": "[[category]]: <b>[[value]]</b>",
    	          		    "fillAlphas": 1,
    	          		    "lineAlpha": 0,
    	          		    "type": "column",
    	          		    "valueField": "Reopened",
    	          		 
    	          		    "title":"Re-Open",
    	          		    "fixedColumnWidth":40
    	          		    	
    	          		  },
    	          		{
      	        		    "balloonText": "[[category]]: <b>[[value]]</b>",
      	          		    "fillAlphas": 1,
      	          		    "lineAlpha": 0,
      	          		    "type": "column",
      	          		    "valueField": "Ignore",
      	          		  
      	          		    "title":"Ignore",
      	          		    "fixedColumnWidth":40
      	          		    	
      	          		  }
  		  
  		  ],
  		"depth3D": 20,
  		"angle": 30,
  		  
  		 "chartCursorSettings": {
		  		"valueBalloonsEnabled": false,
		          "fullWidth":true,
		          "cursorAlpha":0.1
		  	},
  		  "categoryField": ""+categ,
  		  "categoryAxis": {
  		    "gridPosition": "start",
  		    "gridAlpha": 0,
  		    "tickPosition": "start",
  		    "tickLength": 10,
  		  "autoWrap":true
  		  },
  		  "export": {
  		    "enabled": true
  		  }

  		} );
  	chart.write(divId);
  	
  		chart.addListener("clickGraphItem", handleClick);
  	
  		
  	
  	
	}
	
	function drawLevelChart(divId,data,status,title,color,categ,total){
		
		//console.log(data);
			var chart = AmCharts.makeChart( divId, {
	  		  "type": "serial",
	  		"titles" : [ {
				"text" : title+" "+total,
				"size" : 15
	  			} ],
	  		  "theme": "light",
	  		"legend": {
	  			"align": "center",
	  		    "useGraphSettings": true,
	  		  "equalWidths": false,
	  		"markerLabelGap": 4,
			"markerSize": 9,
			"spacing": -53,
			"autoMargins": false
	  		  },
	  		  "dataProvider": data,
	  		  "valueAxes": [ {
	  			  "stackType":"regular",
	  			    "gridColor": "#FFFFFF",
	  			    "gridAlpha": 0,
	  			    "dashLength": 0,
	  			  "totalText":"[[TotalPer]]%",
	  			    "totalTextOffset":10  	
	  			    
	  			  } ],
	  		  "gridAboveGraphs": true,
	  		  "startDuration": 1,
	  		  "graphs": [ {
	  		    "balloonText": "[[category]]: <b>[[value]]</b>",
	  		    "fillAlphas": 1,
	  		    "lineAlpha": 0,
	  		    "type": "column",
	  		    "valueField": "Level1",
	  		    "title":"Level1",
	  		    "fixedColumnWidth":40
	  		    	
	  		  },
	  		{
	    		    "balloonText": "[[category]]: <b>[[value]]</b>",
	    		    "fillAlphas": 1,
	    		    "lineAlpha": 0,
	    		    "type": "column",
	    		    "valueField": "Level2",
	    		    "title":"Level2",
	    		    "fixedColumnWidth":40
	    		    	
	    		  },{
	    	  		    "balloonText": "[[category]]: <b>[[value]]</b>",
	    	  		    "fillAlphas": 1,
	    	  		    "lineAlpha": 0,
	    	  		    "type": "column",
	    	  		    "valueField": "Level3",
	    	  		    "title":"Level3",
	    	  		    "fixedColumnWidth":40
	    	  		    	
	    	  		  },{
	    	    		    "balloonText": "[[category]]: <b>[[value]]</b>",
	    	      		    "fillAlphas": 1,
	    	      		    "lineAlpha": 0,
	    	      		    "type": "column",
	    	      		    "valueField": "Level4",
	    	      		    "title":"Level4",
	    	      		    "fixedColumnWidth":40
	    	      		    	
	    	      		  },{
	    	        		    "balloonText": "[[category]]: <b>[[value]]</b>",
	    	          		    "fillAlphas": 1,
	    	          		    "lineAlpha": 0,
	    	          		    "type": "column",
	    	          		    "valueField": "Level5",
	    	          		    "title":"Level5",
	    	          		    "fixedColumnWidth":40
	    	          		    	
	    	          		  },
	    	          		{
	      	        		    "balloonText": "[[category]]: <b>[[value]]</b>",
	      	          		    "fillAlphas": 1,
	      	          		    "lineAlpha": 0,
	      	          		    "type": "column",
	      	          		    "valueField": "Level6",
	      	          		    "title":"Level6",
	      	          		    "fixedColumnWidth":40
	      	          		    	
	      	          		  }
	  		  
	  		  ],
	  		"depth3D": 20,
	  		"angle": 30,
	  		  
	  		 "chartCursorSettings": {
			  		"valueBalloonsEnabled": true,
			          "fullWidth":true,
			          "cursorAlpha":0.1
			  	},
	  		  "categoryField": "dept",
	  		  "categoryAxis": {
	  		    "gridPosition": "start",
	  		    "gridAlpha": 0,
	  		    "tickPosition": "start",
	  		    "tickLength": 10,
	  		  "autoWrap":true
	  		  },
	  		  "export": {
	  		    "enabled": true
	  		  }

	  		} );
	  	chart.write(divId);
	  	chart.addListener("clickGraphItem", handleClick1);
	  	
		}

	//handler for level
	function handleClick1(event)
	{
		var indexFor=event.index;
		var graph=event.graph;
		var data=graph.data;
		var dataContx=data[indexFor].dataContext;
		var dataFor=graph.title;
	    getData(dataFor,'Pending','L','dataFor',dataContx.id);
	}
	
	//handler for Reopen dept
	function handleClick3(event){
	 	var indexFor=event.index;
		var graph=event.graph;
		var data=graph.data;
		var dataContx=data[indexFor].dataContext;
		var dataFor=graph.title;
	     if(dataFor=='Re-Open'){
	    	dataFor='Re-opened';
	    }
	    getData(dataContx.deptId,dataFor,'Re','dataFor','Level1');
	}
	
	//handler for dept
	function handleClick(event){
		var indexFor=event.index;
		var graph=event.graph;
		var data=graph.data;
		var dataContx=data[indexFor].dataContext;
		var dataFor=graph.title;
	    if(dataFor=='Resolve'){
	    	dataFor='Resolved';
	    }
	    else if(dataFor=='Park'){
	    	dataFor='Snooze';
	    }
	    else if(dataFor=='Re-Assign'){
	    	dataFor='Re-assigned';
	    }
	    else if(dataFor=='Re-Open'){
	    	dataFor='Re-opened';
	    }
	    getData(dataContx.deptId,dataFor,'T','dataFor','Level1');
	}

	
	function showLeveStackedBar(divId,filterFlag,filterDeptId,status,title,color){
		
		status2=status;
		title2=title;
		color2=color;
		//alert('from : '+ $('#sdate').val()+' to: '+$('#edate').val());
	maxDivId2='2ndStackedBar';
	var deptId=$("#deptname1").val();
	var url1='';

		status='Pending';
	if(deptId=='-1')
	{
		title="Pending Tickets For All Department: ";
		url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages_Mgt/jsonChartData4LevelCounters.action?filterFlag="+filterFlag+"&filterDeptId="+filterDeptId+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
		
	}else
	{
		var str=$("#deptname1").find('option:selected').text();
		title="Pending Tickets For "+str+" Department: ";
		url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages_Mgt/jsonChartData4LevelCounters.action?filterFlag=H&filterDeptId="+deptId+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
		
	}
	$.ajax({
	    type : "post",
	    url : url1,
	    async:false,
	    type : "json",
	    success : function(data) {
	    	levelJsonData=data;
	    	var total=0;
	    	//alert(data.length);
	    	//console.log(data);
	    	for (var i=0;i<=data.length-1;i++){
	    		var temp=0;
	    		total=total+data[i].Level1+data[i].Level2+data[i].Level3+data[i].Level4+data[i].Level5+data[i].Level6;
	    		temp=temp+data[i].Level1+data[i].Level2+data[i].Level3+data[i].Level4+data[i].Level5+data[i].Level6;
	    		data[i].Total = temp;
	    		temp=0;
	    	}
	    	
	    	for(var x in data)
	    	{
	    		 data[x].TotalPer = Math.round(data[x].Total / total * 100);
	    		
	    	}
	    	if(total==0||isNaN(total)){
		  		
	    		  $('#'+divId).html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
    	  	    }else{
	    	sampleData = data;
	    	drawLevelChart(divId,data,status,title,color,"dept",total);
	    
	    	
	}},
	   error: function() {
	        alert("error");
	    }
	 });
	
	
	
	}
	



//dashboard 3rd
//category Pie
function showPieCateg(divId){
	//alert("caled");
	maxDivId3="PieCateg";
	 var dept=$("#deptnameCateg").val();
	 var dashFor='M';
	 if(dept!=-1){
		 dashFor='H';
	 }
	 
	 var url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages_Mgt/jsonChartData4CategCounters.action?filterFlag="+dashFor+"&filterDeptId="+dept+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
		//console.log(url1);
	$.ajax({
	    type : "post",
	    url : url1,
	    type : "json",
	    success : function(data) {if (data.length == '0')
		{
			$('#' + divId).html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
		} else
		{
			//console.log(data);
			var chart = AmCharts.makeChart(divId, {
				"type": "pie",
				
				"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>",
				"labelText": "[[percents]]%",
				"titleField": "Category",
				"valueField": "Counter",
				"fontSize": 12,
				"pathToImages": "amcharts/images",
				"theme": "light",
				"radius": 90,
				"innerRadius": 10,
				"labelRadius": 1,
				"allLabels": [],
				"balloon": {},
				"legend": {
					"position": "right",
					"valueAlign": "left",
					"verticalGap": 1,
					"useMarkerColorForLabels": true,
					"useMarkerColorForValues": true,
					"markerType": "circle",
					"markerSize": 15
					
				},
				"titles": [],
				"dataProvider":data,
				  "export": {
				    "enabled": true,
				    "position": "top-left"
				  }
			});
			chart.write(divId);
			chart.addListener("clickSlice", handlePieClick);
		}},
	   error: function() {
	        alert("error from jsonArray data ");
	    }
	 });
	
}

//For Pie slice click
function handlePieClick(event)
{
	var indexFor = event.dataItem;
	var dataContx = indexFor.dataContext;
	getData(dataContx.Id, '', 'C', 'dataFor', '');
}


function StatckedChartReOpen(divId,filterFlag,filterDeptId,status,title,color) {
	
	status1=status;
	title1=title;
	color1=color;
	var url1;
	maxDivId4='1stStackedBar';
	var deptId=$("#deptnameReopen").val();
	if(deptId=='-1')
	{
		title="Re-Opened Tickets For All Department: ";
		url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages_Mgt/jsonChartData4ReopenCounters.action?filterFlag="+filterFlag+"&filterDeptId="+filterDeptId+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
		
	}else
	{
		var str=$("#deptnameReopen").find('option:selected').text();
		title="Re-Opened Tickets For "+str+" Department: ";
		url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages_Mgt/jsonChartData4ReopenCounters.action?filterFlag=H&filterDeptId="+deptId+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
		
	}
	 	 	var  sampleData =null;
	 	 	$.ajax({
		    type : "post",
		    url : url1,
			async:false,
		    type : "json",
		    success : function(data) {
		    	statusJsonData=data;
		    	var total=0;
	 		    	for (var i=0;i<=data.length-1;i++){
		     		var temp=0;
		    		total=total+data[i].Reopened;
		    		temp=temp+data[i].Reopened;
		    		data[i].Total = temp;
		    		temp=0;
		    	}
		    	
		    	for(var x in data)
		    	{
		    		 data[x].TotalPer = Math.round(data[x].Total / total * 100);
		    		
		    	}
		    	//alert(total);
		    	if(total==0||isNaN(total)){
			  		
	    	  	    $('#'+divId).html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
	    	  	    }else{
	    	  	    	
		    	sampleData = data;
		    	drawStatusChartForReopen(divId,sampleData,status,title,color,"dept",total);
		    	
		}},
		   error: function() {
		        alert("error");
		    }
		 });
  
}//end here

function drawStatusChartForReopen(divId,data,status,title,color,categ,total){
 	var chart = AmCharts.makeChart( divId, {
		  "type": "serial",
		  "titles" : [ {
				"text" : title + " "+total,
				"size" : 15
	  			} ],
 		  "theme": "light",
		"legend": {
			"align": "center",
		    "useGraphSettings": true,
		  "equalWidths": false,
		"markerLabelGap": 4,
	"markerSize": 9,
	"spacing": -53,
	"autoMargins": false
		  },
		  "dataProvider": data,
		  "valueAxes": [ {
			  "stackType":"regular",
			    "gridColor": "#FFFFFF",
			    "gridAlpha": 0,
			    "dashLength": 0,
			    "totalText":"[[TotalPer]]%",
			    "totalTextOffset":10
			  } ],
		  "gridAboveGraphs": true,
		  "startDuration": 1,
		  "graphs": [ {
		    "balloonText": "[[category]]: <b>[[value]]</b>",
		    "fillAlphas": 1,
		    "lineAlpha": 0,
		    "type": "column",
		    "valueField": "Reopened",
		  
		    "title":"Re-Open",
		    "fixedColumnWidth":30
		    	
		  }
		 
		  ],
		"depth3D": 20,
		"angle": 30,
		  
		 "chartCursorSettings": {
	  		"valueBalloonsEnabled": false,
	          "fullWidth":true,
	          "cursorAlpha":0.1
	  	},
		  "categoryField": ""+categ,
		  "categoryAxis": {
		    "gridPosition": "start",
		    "gridAlpha": 0,
		    "tickPosition": "start",
		    "tickLength": 10,
		  "autoWrap":true
		  },
		  "export": {
		    "enabled": true
		  }

		} );
	chart.write(divId);
	
		chart.addListener("clickGraphItem", handleClick3);
 
}