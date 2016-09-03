 var escalated=false; 
 var reopen=false;
 var title='';
 var desc ='';
 var faFlag=false;
 var indexId;
 var indexValue;
 var xaxis1='Location';
 var xaxis2='Location';
 var xaxis3='time';
 var xaxis4='Location';
 var waFlag=false;
 var windexId;
 var windexValue;
 var taFlag=false;
 var saFlag=false;
//StatckedChartStatus('jqxChart','column') ;
 //StatckedChartTime('levelChart','column');
function StatckedChartStatusLoc12Before(){
	if(taFlag){
		timeAnalysis(measure1);
	}else{
		StatckedChartStatusLoc12(measure1);
	}
}
function faReset()
{
	faFlag=false;
	indexId='';
	indexValue='';
	xaxis1='Location';
}
function waReset()
{
	waFlag=false;
	windexId='';
	windexValue='';
}
 function getEscalated(val){
	 
	 if(val){
		 console.log("1");
					 reopen=false;
					 escalated=true;
					 if(faFlag)
					 {
						 console.log("2");
						 if((xaxis1=="Location" || xaxis1=="Time" ||xaxis1=="Staff") && faFlag)
						 {
							 console.log("3");
						
							 StatckedFloorAnalysis('mytest',xaxis1);
						 }
						 
						 
					 }else if(waFlag)
					 {
						 console.log("4");
						 if((xaxis2=="Location" || xaxis2=="Time" ||xaxis2=="Staff") && !faFlag)
						 {
							 console.log("5");
							 StatckedWingAnalysis('mytest',xaxis2);
						 }
						 
					 }
					 else if(taFlag)
					 {
						 console.log("6");
						 if((xaxis3=="time" || xaxis3=="staff") && !faFlag && !waFlag)
						 {
							 console.log("7");
							 timeAnalysis(measure1);
						 }
						 
					 }else if(saFlag )
					 {
						 console.log("6.1");
						 console.log(xaxis4);
						 console.log(!faFlag);
						 console.log(!waFlag);
						 console.log(!taFlag);
						 if((xaxis4=="time" || xaxis4=="staff") && !faFlag && !waFlag && !taFlag)
						 {
							 console.log("7");
							 staffAnalysis(measure1);
						 }
						 
					 }
					 
					 else{
						 console.log("8");
						 getDeptFloorAnalysis();
					 }
		 
	 }
	 else
	 {
		 console.log("9");
			 escalated=false;
			 reopen=false;
			 if(faFlag )
			 {
				 console.log("10");
					 if((xaxis1=="Location" || xaxis1=="Time" ||xaxis1=="Staff") && faFlag)
					 {
						 console.log("11");
						 StatckedFloorAnalysis('mytest',xaxis1);
					 }
					
			 }else if(waFlag)
			 {
				 console.log("12");
				 if((xaxis2=="Location" || xaxis2=="Time" ||xaxis2=="Staff")&& !faFlag)
				 {
					 console.log("13");
					 StatckedWingAnalysis('mytest',xaxis2);
				 }
				
			 } else if(taFlag)
			 {
				 console.log("14");
				 if((xaxis3=="time" || xaxis3=="staff") && !faFlag && !waFlag)
				 {
					 console.log("15");
					 timeAnalysis(measure1);
				 }
				 
			 }
			 else if(saFlag )
			 {
				 console.log("6.2");
				 if((xaxis4=="time" || xaxis4=="staff") && !faFlag && !waFlag && !taFlag)
				 {
					 console.log("7");
					 staffAnalysis(measure1);
				 }
				 
			 }
			 
			 else{
				 console.log("16");
						 getDeptFloorAnalysis(); 
					 
				 }		 
				 
		
	 }
 }
 
function getReopen(val){
	 
	 if(val){
					 reopen=true;
					 escalated=false;
					 if(faFlag)
					 {
						 if((xaxis1=="Location" || xaxis1=="Time" ||xaxis1=="Staff") && faFlag)
						 {
						
							 StatckedFloorAnalysis('mytest',xaxis1);
						 }
						
						 
					 }else if(waFlag)
					 {
						 if((xaxis2=="Location" || xaxis2=="Time" ||xaxis2=="Staff") && !faFlag)
						 {
						
							 StatckedWingAnalysis('mytest',xaxis2);
						 }
					
					 }
					 else if(taFlag)
					 {
						 if((xaxis3=="time" || xaxis3=="staff") && !faFlag && !waFlag)
						 {
						
							 timeAnalysis(measure1);
						 }
						 
					 }
					 else if(saFlag )
					 {
						 console.log("6.3");
						 if((xaxis4=="time" || xaxis4=="staff") && !faFlag && !waFlag && !taFlag)
						 {
							 console.log("7");
							 staffAnalysis(measure1);
						 }
						 
					 }
					 else{
						
						 getDeptFloorAnalysis();
					 }
		 
	 		}
	 else
	 {
				 escalated=false;
				 reopen=false;
				 if(faFlag)
				 {
						 if((xaxis1=="Location" || xaxis1=="Time" ||xaxis1=="Staff") && faFlag)
						 {
						
							 StatckedFloorAnalysis('mytest',xaxis1);
						 }
						
				 }else if(waFlag)
				 {
					 if((xaxis2=="Location" || xaxis2=="Time" ||xaxis2=="Staff") && !faFlag)
					 {
					
						 StatckedWingAnalysis('mytest',xaxis2);
					 }
					
				 }else if(taFlag)
				 {
					 if((xaxis3=="time" || xaxis3=="staff") && !faFlag && !waFlag)
					 {
					
						 timeAnalysis(measure1);
					 }
					
					 
				 }else if(saFlag )
				 {
					 console.log("6.4");
					 if((xaxis4=="time" || xaxis4=="staff") && !faFlag && !waFlag && !taFlag)
					 {
						 console.log("7");
						 staffAnalysis(measure1);
					 }
					 
				 }
				 
				 else{
							 getDeptFloorAnalysis(); 
						 
					 }		 
				 
		
	 }
 }
 

 function getFloorChartTitle(val)
 {	var temp=indexValue;
	 if(val=='floor'){
		 temp=indexValue;
	 }else{
		 temp=windexValue;
	 }
	 if($.trim($("#sdate").val())!=""){
		 desc='Data From '+$("#sdate").val()+' TO '+$("#edate").val();
	 }else {
		 desc='Data From '+$("#hfromDate").val()+' TO '+$("#htoDate").val(); 
	 }
	 var dept=$("#deptnameLoc").val();
	 if(dept=="-1" && escalated==false)
	 {
		 title='All Department '+categoryField+' '+temp+' Tickets';
		 
	 }else if(dept!="-1" && escalated==false)
	 {
		 var dataFor=$("#dataFor").val();
		 if(dataFor=='H')
		 {
			 title=$("#dept").val()+' '+categoryField+' '+temp+' Tickets';
		 }else
		 {
			 title=$("#deptnameLoc").find(":selected").text()+' '+categoryField+' '+temp+' Tickets'; 
		 }
		
		
	 }
	 else if(escalated && dept!="-1")
	 {
			var dataFor=$("#dataFor").val();
			 if(dataFor=='H')
			 {
				 title=$("#dept").val()+' '+categoryField+' '+temp+' Tickets'+" "+"Escalated";
			 }else
			 {
				 title=$("#deptnameLoc").find(":selected").text()+' '+categoryField+' '+temp+' Tickets'+" "+"Escalated";
			 }
				 
	 }
	 else if(reopen && dept!="-1")
	 {
			var dataFor=$("#dataFor").val();
			 if(dataFor=='H')
			 {
				 title=$("#dept").val()+' '+categoryField+' '+temp+' Tickets'+" "+"Reopen";
			 }else
			 {
				 title=$("#deptnameLoc").find(":selected").text()+' '+categoryField+' '+temp+' Tickets'+" "+"Reopen";
			 }
				 
	 }
	 else if(escalated && dept=="-1")
	 {
		 title='All Department Escalated';
	 }
	 return title;
 }
 
 function getDeptFloorAnalysis(){
	 faReset();
	 waReset();
	 StatckedChartStatusLoc12(measure1);
 }
 

function StatckedFloorAnalysis(divId,xaxis) {
	$("#backbttn").attr('onclick','StatckedChartStatusLoc12(measure1)').css('display','block');
	//alert("call");
	 var dept=$("#deptnameLoc").val();
	 var mydataField;
	//getFloorChartTitle();
		//maxDivIdLoc2=type1;
	 xaxis1=xaxis;
		var url1='';
	//	alert("Depart:: "+dept+"xaxis::"+xaxis+" Escalated::"+escalated);
		 if(dept!="-1" && xaxis=='Location' && !escalated && !reopen)
			{
			 mydataField="wing";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=floor&dashFor="+indexValue+"&dataFlag="+indexId+"&xaxis=location&filterDeptId="+dept+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
		 else if(dept!="-1" && xaxis=='Time' && !escalated && !reopen)
			{
			 mydataField="time";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=floor&dashFor="+indexValue+"&dataFlag="+indexId+"&xaxis=time&filterDeptId="+dept+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
		 else if(dept!="-1" && xaxis=='Staff' && !escalated && !reopen)
			{
			 mydataField="staff";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=floor&dashFor="+indexValue+"&dataFlag="+indexId+"&xaxis=staff&filterDeptId="+dept+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
		 else if(dept=="-1" && xaxis=='Location' && !escalated && !reopen)
			{
			 mydataField="wing";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=floor&dashFor="+indexValue+"&dataFlag="+indexId+"&xaxis=location&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
		 else if(dept=="-1" && xaxis=='Time' && !escalated)
			{
			 mydataField="time";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=floor&dashFor="+indexValue+"&dataFlag="+indexId+"&xaxis=time&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
		 else if(dept=="-1" && xaxis=='Staff' && !escalated && !reopen)
			{
			 mydataField="staff";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=floor&dashFor="+indexValue+"&dataFlag="+indexId+"&xaxis=staff&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
		 
		 
		 
		 //escalated Location Time Staff
		 if(dept!="-1" && xaxis=='Location' && escalated && !reopen)
			{
			 mydataField="wing";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=floor&filterFlag=escalated&dashFor="+indexValue+"&dataFlag="+indexId+"&xaxis=location&filterDeptId="+dept+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
		 else if(dept!="-1" && xaxis=='Time' && escalated && !reopen)
			{
			 mydataField="time";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=floor&filterFlag=escalated&dashFor="+indexValue+"&dataFlag="+indexId+"&xaxis=time&filterDeptId="+dept+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
		 else if(dept!="-1" && xaxis=='Staff' && escalated && !reopen)
			{
			 mydataField="staff";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=floor&filterFlag=escalated&dashFor="+indexValue+"&dataFlag="+indexId+"&xaxis=staff&filterDeptId="+dept+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
		 else if(dept=="-1" && xaxis=='Location' && escalated && !reopen)
			{
			 mydataField="wing";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=floor&filterFlag=escalated&dashFor="+indexValue+"&dataFlag="+indexId+"&xaxis=location&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
		 else if(dept=="-1" && xaxis=='Time' && escalated && !reopen)
			{
			 mydataField="time";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=floor&filterFlag=escalated&dashFor="+indexValue+"&dataFlag="+indexId+"&xaxis=time&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
		 else if(dept=="-1" && xaxis=='Staff' && escalated && !reopen)
			{
			 mydataField="staff";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=floor&filterFlag=escalated&dashFor="+indexValue+"&dataFlag="+indexId+"&xaxis=staff&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
			
		 
		 //Reopen Location Time Staff
		 if(dept!="-1" && xaxis=='Location' && !escalated && reopen)
			{
			 mydataField="wing";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=floor&filterFlag=reopen&dashFor="+indexValue+"&dataFlag="+indexId+"&xaxis=location&filterDeptId="+dept+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
		 else if(dept!="-1" && xaxis=='Time' && !escalated && reopen)
			{
			 mydataField="time";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=floor&filterFlag=reopen&dashFor="+indexValue+"&dataFlag="+indexId+"&xaxis=time&filterDeptId="+dept+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
		 else if(dept!="-1" && xaxis=='Staff' && !escalated && reopen)
			{
			 mydataField="staff";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=floor&filterFlag=reopen&dashFor="+indexValue+"&dataFlag="+indexId+"&xaxis=staff&filterDeptId="+dept+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
		 else if(dept=="-1" && xaxis=='Location' && !escalated && reopen)
			{
			 mydataField="wing";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=floor&filterFlag=reopen&dashFor="+indexValue+"&dataFlag="+indexId+"&xaxis=location&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
		 else if(dept=="-1" && xaxis=='Time' && !escalated && reopen)
			{
			 mydataField="time";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=floor&filterFlag=reopen&dashFor="+indexValue+"&dataFlag="+indexId+"&xaxis=time&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
		 else if(dept=="-1" && xaxis=='Staff' && !escalated && reopen)
			{
			 mydataField="staff";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=floor&filterFlag=reopen&dashFor="+indexValue+"&dataFlag="+indexId+"&xaxis=staff&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
				
		 
		 $("#"+divId).html("<center><br/><br/><br/><br/><br/><br/><br/><img src=images/lightbox-ico-loading.gif></center>");
			$.ajax({
			    type : "post",
			    url : url1,
			    type : "json",
			    success : function(data) {
			    	var total=0;
			    	if(indexValue=='Resolved'){
			    		for (var i=0;i<=data.length-1;i++){
					    		total=total+data[i].Resolved;
					    	}
			    	}else if (indexValue=='Pending'){
			    		for (var i=0;i<=data.length-1;i++){
					    		total=total+data[i].Pending;
					    	}
			    	}
			    	for (var x in data)
					{
						 
						data[x].ResolvedPer = Math.round(data[x].Resolved / total * 100);
						//total=total+data[x].Resolved;
					}
			    
			    
			     	var chart = AmCharts.makeChart(""+divId, {
				  		  "type": "serial",
				  		  "titles": [{
	  			  				"text":  getFloorChartTitle('floor')+": "+total,
	  	  	  	  				"size": 15
	  	  	  	  			}
	  	  	  	  		],
				  		  "addClassNames": true,
				  		  "theme": "light",
				  		  "path": "amcharts",
				  		  "autoMargins": false,
				  		  "marginLeft": 30,
				  		  "marginRight": 8,
				  		  "marginTop": 10,
				  		  "marginBottom": 26,
				  		  "balloon": {
				  		    "adjustBorderColor": false,
				  		    "horizontalPadding": 10,
				  		    "verticalPadding": 8,
				  		    "color": "#ffffff"
				  		  },

				  		  "dataProvider": data,
				  		 "valueAxes": [{
				  	        "position": "left",
				  	        "gridAlpha":0
				  	    }],
				  		  "startDuration": 1,
				  		  "graphs": [{
				  		    "alphaField": "alpha",
				  		    "balloonText": "<span style='font-size:12px;'>[[title]] in [[category]]:<br><span style='font-size:20px;'>[[value]]</span> [[additional]]</span>",
				  		    "fillAlphas": 1,
				  		    "title": "Resolve",
				  		    "type": "column",
				  		    "valueField": "Resolved",
				  		  "labelText" : "[[ResolvedPer]]%",
				  		 "labelOffset":10,
				  		    "dashLengthField": "dashLengthColumn",
				  		    "fixedColumnWidth":30
				  		  }, {
				  		    "id": "graph2",
				  		    "balloonText": "<span style='font-size:12px;'>[[title]] in [[category]]:<br><span style='font-size:20px;'>[[value]]</span> [[additional]]</span>",
				  		    "bullet": "round",
				  		    "lineThickness": 3,
				  		    "bulletSize": 7,
				  		    "bulletBorderAlpha": 1,
				  		    "bulletColor": "#FFFFFF",
				  		    "useLineColorForBulletBorder": true,
				  		    "bulletBorderThickness": 3,
				  		    "fillAlphas": 0,
				  		    "lineAlpha": 1,
				  		    "title": "Pending",
				  		    "valueField": "Pending"
				  		  }],
				  		"depth3D": 20,
				  		"angle": 30,
				  		  "categoryField": ""+mydataField,
				  		  "categoryAxis": {
				  		    "gridPosition": "start",
				  		    "axisAlpha": 1,
				  		    "axisColor":"#B2B2B2",
				  		    "tickPosition": "start",
				  		    "tickLength": 10,
				  		    "gridAlpha":0,
				  		  
				  		   "autoWrap":true
				  		  },
				  		"legend": {
				  		    "useGraphSettings": true,
				  		    "position":"absolute"
				  		  },

				  		  "export": {
				  		    "enabled": true
				  		  }
				  		});
				  	chart.write(""+divId);
					//singleDoubleClick("1",AmCharts);
					AmCharts.clickTimeout = 0; // this will hold setTimeout reference
					AmCharts.lastClick = 0; // last click timestamp
					AmCharts.doubleClickDuration = 300; // distance between clicks in ms - if
														// it's less than that - it's a
														// double click

					AmCharts.doSingleClick2 = function(event)
					{
						//alert("Single2");
						if(xaxis=="Location")
						{
							getwingAnalysis(event);
						}
						
						
					}

					AmCharts.doDoubleClick2 = function(event)
					{
						console.log(event);
						var chart = event.chart;
						var categoryField = chart.categoryField;
						var indexFor = event.index;
						var graph = event.graph;
						var data = graph.data;
						var dataContx = data[indexFor].dataContext;
						var dataFor = graph.title;
						// //console.log(event);
						// //console.log(graph.title);
						// //console.log(dataContx.floorId);
						var temp1='Location';
						if(escalated)
						{
							temp1='escalated';
						}
						else if(reopen)
						{
							temp1='reopen';
						}
						var temp='';
						
						if (dataFor == 'Resolve')
						{
							dataFor = 'Resolved';
						} else if (dataFor == 'Park')
						{
							dataFor = 'Snooze';
						} else if (dataFor == 'Reassigned')
						{
							dataFor = 'Re-assigned';
						} else if (dataFor == 'Reopened')
						{
							dataFor = 'Re-opened';
						}
						if (categoryField == 'wing')
						{
							
								temp="WAL";
							
							getDataLoc('', dataFor, temp, 'dataFor', dataContx.wingId+"KK"+indexId,temp1);
						}
						else if (categoryField == 'time')
						{
							
								temp="WAT";
							
							getDataLoc('', dataFor, temp, 'dataFor', dataContx.timeId+"KK"+indexId,temp1);
						}
						else if (categoryField == 'staff')
						{
						
								temp="WAS";
							
							getDataLoc('', dataFor, temp, 'dataFor', dataContx.staffId+"KK"+indexId,temp1);
						}
					}

					// create click handler
					AmCharts.myClickHandler = function(event)
					{
						var ts = (new Date()).getTime();
						if ((ts - AmCharts.lastClick) < AmCharts.doubleClickDuration)
						{
							if (AmCharts.clickTimeout)
							{
								clearTimeout(AmCharts.clickTimeout);
							}
							// reset last click
							AmCharts.lastClick = 0;
							// now let's do whatever we want to do on double-click
							AmCharts.doDoubleClick2(event);
						} else
						{
							// single click!
							// let's delay it to see if a second click will come through
							AmCharts.clickTimeout = setTimeout(function()
							{
								AmCharts.doSingleClick2(event);
							}, AmCharts.doubleClickDuration);
						}
						AmCharts.lastClick = ts;
					}
					// add handler to the chart
					chart.addListener("clickGraphItem", AmCharts.myClickHandler);
				  	//chart.addListener("clickGraphItem", getwingAnalysis);
			           
			    	
			},
			   error: function() {
			        alert("error");
			    }
			 });
			
		
	
}//end here

function getwingAnalysis(event){
	
	
	faFlag=false;
	var indexFor=event.index;
	var graph=event.graph;
	var data=graph.data;
	var dataContx=data[indexFor].dataContext;
	categoryField=data[indexFor].category;
	var wingId=dataContx.wingId;
	var dataFor=graph.title;
	
	waFlag=true;
	windexId=wingId;
	windexValue=graph.title;
	if(windexValue=='Resolve'){windexValue='Resolved';}
	StatckedWingAnalysis("mytest","Location");
	$("#locationbttn").attr('onclick','StatckedWingAnalysis("mytest","Location")');
	$("#timebttn").attr('onclick','StatckedWingAnalysis("mytest","Time")');
	$("#staffbttn").attr('onclick','StatckedWingAnalysis("mytest","Staff")');
}


function StatckedWingAnalysis(divId,xaxis) {
	$("#backbttn").attr('onclick','StatckedFloorAnalysis("mytest","Location")').css('display','block');
	 var dept=$("#deptnameLoc").val();
	 var mydataField;
	 xaxis2=xaxis;
		var url1='';
		 if(dept!="-1" && xaxis=='Location' && !escalated && !reopen)
			{
			 mydataField="wing";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=wing&dashFor="+windexValue+"&dataFlag="+windexId+"&xaxis=location&filterDeptId="+dept+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
		 else if(dept!="-1" && xaxis=='Time' && !escalated && !reopen)
			{
			 mydataField="time";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=wing&dashFor="+windexValue+"&dataFlag="+windexId+"&xaxis=time&filterDeptId="+dept+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
		 else if(dept!="-1" && xaxis=='Staff' && !escalated && !reopen)
			{
			 mydataField="staff";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=wing&dashFor="+windexValue+"&dataFlag="+windexId+"&xaxis=staff&filterDeptId="+dept+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
		 else if(dept=="-1" && xaxis=='Location' && !escalated && !reopen)
			{
			 mydataField="wing";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=wing&dashFor="+windexValue+"&dataFlag="+windexId+"&xaxis=location&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
		 else if(dept=="-1" && xaxis=='Time' && !escalated && !reopen)
			{
			 mydataField="time";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=wing&dashFor="+windexValue+"&dataFlag="+windexId+"&xaxis=time&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
		 else if(dept=="-1" && xaxis=='Staff' && !escalated && !reopen)
			{
			 mydataField="staff";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=wing&dashFor="+windexValue+"&dataFlag="+windexId+"&xaxis=staff&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
		 
		 
		 
		 //escalated
		 if(dept!="-1" && xaxis=='Location' && escalated && !reopen)
			{
			 mydataField="wing";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=wing&filterFlag=escalated&dashFor="+windexValue+"&dataFlag="+windexId+"&xaxis=location&filterDeptId="+dept+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
		 else if(dept!="-1" && xaxis=='Time' && escalated && !reopen)
			{
			 mydataField="time";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=wing&filterFlag=escalated&dashFor="+windexValue+"&dataFlag="+windexId+"&xaxis=time&filterDeptId="+dept+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
		 else if(dept!="-1" && xaxis=='Staff' && escalated && !reopen)
			{
			 mydataField="staff";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=wing&filterFlag=escalated&dashFor="+windexValue+"&dataFlag="+windexId+"&xaxis=staff&filterDeptId="+dept+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
		 else if(dept=="-1" && xaxis=='Location' && escalated && !reopen)
			{
			 mydataField="wing";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=wing&filterFlag=escalated&dashFor="+windexValue+"&dataFlag="+windexId+"&xaxis=location&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
		 else if(dept=="-1" && xaxis=='Time' && escalated && !reopen)
			{
			 mydataField="time";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=wing&filterFlag=escalated&dashFor="+windexValue+"&dataFlag="+windexId+"&xaxis=time&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
		 else if(dept=="-1" && xaxis=='Staff' && escalated && !reopen)
			{
			 mydataField="staff";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=wing&filterFlag=escalated&dashFor="+windexValue+"&dataFlag="+windexId+"&xaxis=staff&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
		 
		 
		 //reopen
		 if(dept!="-1" && xaxis=='Location' && !escalated && reopen)
			{
			 mydataField="wing";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=wing&filterFlag=reopen&dashFor="+windexValue+"&dataFlag="+windexId+"&xaxis=location&filterDeptId="+dept+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
		 else if(dept!="-1" && xaxis=='Time' && !escalated && reopen)
			{
			 mydataField="time";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=wing&filterFlag=reopen&dashFor="+windexValue+"&dataFlag="+windexId+"&xaxis=time&filterDeptId="+dept+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
		 else if(dept!="-1" && xaxis=='Staff' && !escalated && reopen)
			{
			 mydataField="staff";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=wing&filterFlag=reopen&dashFor="+windexValue+"&dataFlag="+windexId+"&xaxis=staff&filterDeptId="+dept+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
		 else if(dept=="-1" && xaxis=='Location' && !escalated && reopen)
			{
			 mydataField="wing";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=wing&filterFlag=reopen&dashFor="+windexValue+"&dataFlag="+windexId+"&xaxis=location&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
		 else if(dept=="-1" && xaxis=='Time' && !escalated && reopen)
			{
			 mydataField="time";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=wing&filterFlag=reopen&dashFor="+windexValue+"&dataFlag="+windexId+"&xaxis=time&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
		 else if(dept=="-1" && xaxis=='Staff' && !escalated && reopen)
			{
			 mydataField="staff";
				url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/getFloorWingAnalysis.action?flag=wing&filterFlag=reopen&dashFor="+windexValue+"&dataFlag="+windexId+"&xaxis=staff&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			}
				
		

		 $("#"+divId).html("<center><br/><br/><br/><br/><br/><br/><br/><img src=images/lightbox-ico-loading.gif></center>");
			$.ajax({
			    type : "post",
			    url : url1,
			    type : "json",
			    success : function(data) {

			    	var total=0;
			    	if(windexValue=='Resolved'){
			    		for (var i=0;i<=data.length-1;i++){
					    		total=total+data[i].Resolved;
					    	}
			    	}else if (windexValue=='Pending'){
			    		for (var i=0;i<=data.length-1;i++){
					    		total=total+data[i].Pending;
					    	}
			    	}
			    	for (var x in data)
					{
						 
						data[x].ResolvedPer = Math.round(data[x].Resolved / total * 100);
						//total=total+data[x].Resolved;
					}
			    	var chart = AmCharts.makeChart(""+divId, {
				  		  "type": "serial",
				  		  "titles": [{
	  			  				"text":  getFloorChartTitle('wing')+": "+total,
	  	  	  	  				"size": 15
	  	  	  	  			}
	  	  	  	  		],
				  		  "addClassNames": true,
				  		  "theme": "light",
				  		  "path": "amcharts",
				  		  "autoMargins": false,
				  		  "marginLeft": 30,
				  		  "marginRight": 8,
				  		  "marginTop": 10,
				  		  "marginBottom": 26,
				  		  "balloon": {
				  		    "adjustBorderColor": false,
				  		    "horizontalPadding": 10,
				  		    "verticalPadding": 8,
				  		    "color": "#ffffff"
				  		  },

				  		  "dataProvider": data,
				  		 "valueAxes": [{
				  	        "position": "left",
				  	        "gridAlpha":0
				  	    }],
				  		  "startDuration": 1,
				  		  "graphs": [{
				  		    "alphaField": "alpha",
				  		    "balloonText": "<span style='font-size:12px;'>[[title]] in [[category]]:<br><span style='font-size:20px;'>[[value]]</span> [[additional]]</span>",
				  		    "fillAlphas": 1,
				  		    "title": "Resolve",
				  		    "type": "column",
				  		    "valueField": "Resolved",
				  		  "labelText" : "[[ResolvedPer]]%",
				  		 "labelOffset":10,
				  		    "dashLengthField": "dashLengthColumn",
				  		    "fixedColumnWidth":30
				  		  }, {
				  		    "id": "graph2",
				  		    "balloonText": "<span style='font-size:12px;'>[[title]] in [[category]]:<br><span style='font-size:20px;'>[[value]]</span> [[additional]]</span>",
				  		    "bullet": "round",
				  		    "lineThickness": 3,
				  		    "bulletSize": 7,
				  		    "bulletBorderAlpha": 1,
				  		    "bulletColor": "#FFFFFF",
				  		    "useLineColorForBulletBorder": true,
				  		    "bulletBorderThickness": 3,
				  		    "fillAlphas": 0,
				  		    "lineAlpha": 1,
				  		    "title": "Pending",
				  		    "valueField": "Pending"
				  		  }],
				  		"depth3D": 20,
				  		"angle": 30,
				  		  "categoryField": ""+mydataField,
				  		  "categoryAxis": {
				  		    "gridPosition": "start",
				  		    "axisAlpha": 1,
				  		    "axisColor":"#B2B2B2",
				  		    "tickPosition": "start",
				  		    "tickLength": 10,
				  		    "gridAlpha":0,
				  		  
				  		   "autoWrap":true
				  		  },
				  		"legend": {
				  		    "useGraphSettings": true,
				  		    "position":"absolute"
				  		  },

				  		  "export": {
				  		    "enabled": true
				  		  }
				  		});
				  	chart.write(""+divId);
				  //singleDoubleClick("1",AmCharts);
					AmCharts.clickTimeout = 0; // this will hold setTimeout reference
					AmCharts.lastClick = 0; // last click timestamp
					AmCharts.doubleClickDuration = 300; // distance between clicks in ms - if
														// it's less than that - it's a
														// double click

					AmCharts.doSingleClick2 = function(event)
					{
						
					}

					AmCharts.doDoubleClick2 = function(event)
					{
						console.log(event);
						var chart = event.chart;
						var categoryField = chart.categoryField;
						var indexFor = event.index;
						var graph = event.graph;
						var data = graph.data;
						var dataContx = data[indexFor].dataContext;
						var dataFor = graph.title;
						// //console.log(event);
						// //console.log(graph.title);
						// //console.log(dataContx.floorId);
						var temp1='Location';
						if(escalated)
						{
							temp1='escalated';
						}
						else if(reopen)
						{
							temp1='reopen';
						}
						var temp='';
						
						if (dataFor == 'Resolve')
						{
							dataFor = 'Resolved';
						} else if (dataFor == 'Park')
						{
							dataFor = 'Snooze';
						} else if (dataFor == 'Reassigned')
						{
							dataFor = 'Re-assigned';
						} else if (dataFor == 'Reopened')
						{
							dataFor = 'Re-opened';
						}
						if (categoryField == 'wing')
						{
							
								temp="RAL";
							
							getDataLoc('', dataFor, temp, 'dataFor', dataContx.wingId+"KK"+windexId,temp1);
						}
						else if (categoryField == 'time')
						{
							
								temp="RAT";
							
							getDataLoc('', dataFor, temp, 'dataFor', dataContx.timeId+"KK"+windexId,temp1);
						}
						else if (categoryField == 'staff')
						{
						
								temp="RAS";
							
							getDataLoc('', dataFor, temp, 'dataFor', dataContx.staffId+"KK"+windexId,temp1);
						}
					}

					// create click handler
					AmCharts.myClickHandler = function(event)
					{
						var ts = (new Date()).getTime();
						if ((ts - AmCharts.lastClick) < AmCharts.doubleClickDuration)
						{
							if (AmCharts.clickTimeout)
							{
								clearTimeout(AmCharts.clickTimeout);
							}
							// reset last click
							AmCharts.lastClick = 0;
							// now let's do whatever we want to do on double-click
							AmCharts.doDoubleClick2(event);
						} else
						{
							// single click!
							// let's delay it to see if a second click will come through
							AmCharts.clickTimeout = setTimeout(function()
							{
								AmCharts.doSingleClick2(event);
							}, AmCharts.doubleClickDuration);
						}
						AmCharts.lastClick = ts;
					}
					// add handler to the chart
					chart.addListener("clickGraphItem", AmCharts.myClickHandler);
				  	//chart.addListener("clickGraphItem", getwingAnalysis);
			           
			    	
			},
			   error: function() {
			        alert("error");
			    }
			 });
			
		
	
}//end here