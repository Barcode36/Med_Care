
showDetailsPie1('prevPie1','nextPie1','jqxChart2');
showPieFreq('YearlyTotal','Yearly Total Task Status','For All Department');
showChart('1stColumn2Axes','jqxChart1');
showChart('3rdBubbleGraph','jqxChart3');
showChart('7thLine','jqxChart7');
function showDetailsPie1(graphBlockDiv1,graphBlockDiv2,middleDiv)
{
	$("#"+middleDiv).css({"width":"90%","float":"left"});
	$("#"+graphBlockDiv1).show();
	$("#"+graphBlockDiv2).show();
}

function restoreBoard(graphBlockDiv1,graphBlockDiv2,middleDiv)
{
	$("#"+middleDiv).css({"width":"100%"});
	$("#"+graphBlockDiv1).hide();
	$("#"+graphBlockDiv2).hide();
	if(middleDiv=='jqxChart1'){
	 countnext=0;
	 countprev=0;
	}else if(middleDiv=='jqxChart2'){
		countnext1=0;
		 countprev1=0;
	}else if(middleDiv=='jqxChart3'){
		countnex2=0;
		 countprev2=0;
	}else if(middleDiv=='jqxChart4'){
		countnex3=0;
		 countprev3=0;
	}else if(middleDiv=='jqxChart7'){
		countnex4=0;
		 countprev4=0;
	}
}
//next prev button for dash 1
var countnext=0;
var countprev=0;
function next() {
	if(countnext==0){
		showPieStauts('Snooze','Snooze Ticket Status','For All Departments');
		
		countnext++;
	}else if(countnext==1){
		showPieStauts('Missed','Missed Ticket Status','For All Departments');
		countnext++;
	}else if(countnext==2){
		showPieStauts('Done','Done Ticket Status','For All Departments');
		countnext++;
	}
}

function prev() {
	if(countnext==1){
		showPieStauts('Pending','Pending Ticket Status','For All Departments');
		countnext--;
	}else if(countnext==2){
		showPieStauts('Snooze','Snooze Priority Ticket Status','For All Departments');
		
		countnext--;
	}else if(countnext==3){
		showPieStauts('Missed','Missed Ticket Status','For All Departments');
		countnext--;
	}
}

//next prev button for dash 2
var countnext1=0;
var countprev1=0;
function next1() {

	if(countnext1==0){
		showPieFreq('YearlyMissed','Yearly Missed Ticket Status','For All Department');
		
		countnext1++;
	}else if(countnext1==1){
		showPieFreq('MonthlyTotal','Monthly Total Ticket Status','For All Department');
		countnext1++;
	}else if(countnext1==2){
		showPieFreq('MonthlyMissed','Monthly Missed Ticket Status','For All Department');
		countnext1++;
	}else if(countnext1==3){
		showPieFreq('WeeklyTotal','Weekly Total Ticket Status','For All Department');
		countnext1++;
	}else if(countnext1==4){
		showPieFreq('WeeklyMissed','Weekly Missed Ticket Status','For All Department');
		countnext1++;
	}
}

function prev1() {
	
	if(countnext1==1){
		showPieFreq('YearlyTotal','Yearly Total Ticket Status','For All Department');
		countnext1--;
	}else if(countnext1==2){
		showPieFreq('YearlyMissed','Yearly Missed Ticket Status','For All Department');
		
		countnext1--;
	}else if(countnext1==3){
		showPieFreq('MonthlyTotal','Monthly Total Ticket Status','For All Department');
		countnext1--;
	}else if(countnext1==4){
		showPieFreq('MonthlyMissed','Monthly Missed Ticket Status','For All Department');
		countnext1--;
	}else if(countnext1==5){
		showPieFreq('WeeklyTotal','Weekly Total Ticket Status','For All Department');
		countnext1--;
	}
}

//next prev button for dash 3
var countnext2=0;
var countprev2=0;
function next2() {
	if(countnext2==0){
		showPieAgieng('ThisMonth','This Month Agieng Details','For All Department');
		
		countnext2++;
	}else if(countnext2==1){
		showPieAgieng('ThisWeek','This Week Agieng Details','For All Department');
		countnext2++;
	}
	
}

function prev2() {
	if(countnext2==1){
		showPieAgieng('ThisYear','This Year Agieng Details','For All Department');
		countnext2--;
	}else if(countnext2==2){
		showPieAgieng('ThisMonth','This Month Agieng Details','For All Department');
		
		countnext2--;
	}
}

//next prev button for dash 4
var countnext3=0;
var countprev3=0;
function next3() {
	if(countnext3==0){
		showPieStatusPrevMonth('Done','Previous Month Done Ticket Status','For All Department');
		
		countnext3++;
	}
	
}

function prev3() {
	if(countnext3==1){
		showPieStatusPrevMonth('Pending','Previous Month Pending Ticket Status','For All Department');
		
		countnext3--;
	}
	
}

//next prev button for dash 7
var countnext4=0;
var countprev4=0;
function next4() {

	if(countnext4==0){
		showPieTodays('TeamDue','Team Due Todays Ticket Details','For All Department');
		
		countnext4++;
	}else if(countnext4==1){
		showPieTodays('SelfReminder1','Self Reminder 1 Todays Ticket Details','For All Department');
		countnext4++;
	}else if(countnext4==2){
		showPieTodays('TeamReminder1','Team Reminder 1 Todays Ticket Details','For All Department');
		countnext4++;
	}else if(countnext4==3){
		showPieTodays('SelfReminder2','Self Reminder 2 Todays Ticket Details','For All Department');
		
		countnext4++;
	}else if(countnext4==4){
		showPieTodays('TeamReminder2','Team Reminder 2 Todays Ticket Details','For All Department');
		countnext4++;
	}else if(countnext4==5){
		showPieTodays('SelfReminder3','Self Reminder 3 Todays Ticket Details','For All Department');
		countnext4++;
	}else if(countnext4==6){
		showPieTodays('TeamReminder3','Team Reminder 3 Todays Ticket Details','For All Department');
		countnext4++;
	}
}

function prev4() {
	
	if(countnext4==1){
		showPieTodays('SelfDue','Self Due Todays Ticket Details','For All Department');
		countnext4--;
	}else if(countnext4==2){
		showPieTodays('TeamDue','Team Due Todays Ticket Details','For All Department');
		
		countnext4--;
	}else if(countnext4==3){
		showPieTodays('SelfReminder1','Self Reminder 1 Todays Ticket Details','For All Department');
		
		countnext4--;
	}else if(countnext4==4){
		showPieTodays('TeamReminder1','Team Reminder 1 Todays Ticket Details','For All Department');
		countnext4--;
	}else if(countnext4==5){
		showPieTodays('SelfReminder2','Self Reminder 2 Todays Ticket Details','For All Department');
		countnext4--;
	}else if(countnext4==6){
		showPieTodays('TeamReminder2','Team Reminder 2 Todays Ticket Details','For All Department');
		
		countnext4--;
	}else if(countnext4==7){
		showPieTodays('SelfReminder3','Self Reminder 3 Todays Ticket Details','For All Department');
		countnext4--;
	}
}





//Pie For Board 1
function showPieStauts(dataField,title,description) {
	maxDivId1="1stPieGraph";
	showDetailsPie1('prevPie','nextPie','jqxChart1');
	var  sampleData =null;
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataStatus.action",
	    type : "json",
	    success : function(data) {
	    	console.log("Pie Data");
	    	console.log(data);
	    	 var total=0;
	    	 if(dataField=='Pending'){
		  	    
	    		 for (var int = 0; int < data.length; int++) {
					total=total+parseFloat(data[int].Pending);
				}
		  	    
	    	 }else if(dataField=='Snooze'){
		  	    
	    		 for (var int = 0; int < data.length; int++) {
					total=total+parseFloat(data[int].Snooze);
				}
		  	    
	    	 }else if(dataField=='Missed'){
		  	    
	    		 for (var int = 0; int < data.length; int++) {
					total=total+parseFloat(data[int].Missed);
				}
		  	    
	    	 }else if(dataField=='Done'){
		  	    
	    		 for (var int = 0; int < data.length; int++) {
					total=total+parseFloat(data[int].Done);
				}
		  	    
	    	 }
		  	    
	    	
	    	sampleData = data;
	    	  var settings = {
	                  title: ""+title,
	                  description: ""+description ,
	                  enableAnimations: true,
		              showLegend: true,
		              padding: { left: 5, top: 5, right: 5, bottom: 5 },
	                  titlePadding: { left: 0, top: 0, right: 0, bottom: 10 },
	                  source: sampleData,
	                  colorScheme: 'scheme11',
	                  seriesGroups:
	                      [
	                          {
	                              type: 'pie',
	                              showLabels: true,
	                              series:
	                                  [ 
	                                    { 
	                                    	dataField: dataField,
	                                    	displayText: 'dept',
	                                    	labelRadius: 100,
	                                    	initialAngle: 15,
	                                    	radius: 55,
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
	    	  $('#jqxChart1').jqxChart({ enableAnimations: true });
	        $('#jqxChart1').jqxChart(settings);
	        $("#FlipValueAxis").jqxCheckBox({ width: 140,  checked: false });
	        $("#FlipCategoryAxis").jqxCheckBox({ width: 140,  checked: false });
	        var refreshChart = function () {
	            $('#levelChart').jqxChart({ enableAnimations: false });
	            $('#levelChart').jqxChart('refresh');
	        }
	        // update greyScale values.
	        $("#FlipValueAxis").on('change', function (event) {
	            var groups = $('#levelChart').jqxChart('seriesGroups');
	            groups[0].valueAxis.flip = event.args.checked;
	            refreshChart();
	        });
	        $("#FlipCategoryAxis").on('change', function (event) {
	            var categoryAxis = $('#levelChart').jqxChart('categoryAxis');
	            categoryAxis.flip = event.args.checked;
	            refreshChart();
	        });
	    	
	},
	   error: function() {
	        alert("error from jsonArray data ");
	    }
	 });
}

//pie for board 2
function showPieFreq(dataField,title,description) {
	maxDivId2="2ndPieGraph";
	showDetailsPie1('prevPie1','nextPie1','jqxChart2');
	var  sampleData =null;
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDatafrequency.action",
	    type : "json",
	    success : function(data) {
	    	console.log("Pie Data");
	    	console.log(data);
	    	 var total=0;
	    	 if(dataField=='YearlyTotal'){
		  	    
	    		 for (var int = 0; int < data.length; int++) {
				total=total+parseFloat(data[int].YearlyTotal);      
				}
		  	    
	    	 }else if(dataField=='YearlyMissed'){
		  	    
	    		 for (var int = 0; int < data.length; int++) {
					total=total+parseFloat(data[int].YearlyMissed);
				}
		  	    
	    	 }else if(dataField=='MonthlyTotal'){
		  	    
	    		 for (var int = 0; int < data.length; int++) {
					total=total+parseFloat(data[int].MonthlyTotal);
				}
		  	    
	    	 }else if(dataField=='MonthlyMissed'){
		  	    
	    		 for (var int = 0; int < data.length; int++) {
					total=total+parseFloat(data[int].MonthlyMissed);
				}
		  	    
	    	 }else if(dataField=='WeeklyTotal'){
		  	    
	    		 for (var int = 0; int < data.length; int++) {
					total=total+parseFloat(data[int].WeeklyTotal);
				}
		  	    
	    	 }else if(dataField=='WeeklyMissed'){
		  	    
	    		 for (var int = 0; int < data.length; int++) {
					total=total+parseFloat(data[int].WeeklyMissed);
				}
		  	    
	    	 }
		  	    
	    	
	    	sampleData = data;
	    	  var settings = {
	                  title: ""+title,
	                  description: ""+description ,
	                  enableAnimations: true,
		              showLegend: true,
		              padding: { left: 5, top: 5, right: 5, bottom: 5 },
	                  titlePadding: { left: 0, top: 0, right: 0, bottom: 10 },
	                  source: sampleData,
	                  colorScheme: 'scheme11',
	                  seriesGroups:
	                      [
	                          {
	                              type: 'pie',
	                              showLabels: true,
	                              series:
	                                  [ 
	                                    { 
	                                    	dataField: dataField,
	                                    	displayText: 'dept',
	                                    	labelRadius: 100,
	                                    	initialAngle: 15,
	                                    	radius: 55,
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
	    	  $('#jqxChart2').jqxChart({ enableAnimations: true });
	        $('#jqxChart2').jqxChart(settings);
	        $("#FlipValueAxis").jqxCheckBox({ width: 140,  checked: false });
	        $("#FlipCategoryAxis").jqxCheckBox({ width: 140,  checked: false });
	        var refreshChart = function () {
	            $('#levelChart').jqxChart({ enableAnimations: false });
	            $('#levelChart').jqxChart('refresh');
	        }
	        // update greyScale values.
	        $("#FlipValueAxis").on('change', function (event) {
	            var groups = $('#levelChart').jqxChart('seriesGroups');
	            groups[0].valueAxis.flip = event.args.checked;
	            refreshChart();
	        });
	        $("#FlipCategoryAxis").on('change', function (event) {
	            var categoryAxis = $('#levelChart').jqxChart('categoryAxis');
	            categoryAxis.flip = event.args.checked;
	            refreshChart();
	        });
	    	
	},
	   error: function() {
	        alert("error from jsonArray data ");
	    }
	 });
}

//pie for board 3
function showPieAgieng(dataField,title,description) {
	maxDivId3="3rdPieGraph";
	showDetailsPie1('prevPie2','nextPie2','jqxChart3');
	var  sampleData =null;
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataAgieng.action",
	    type : "json",
	    success : function(data) {
	    	console.log("Pie Data");
	    	console.log(data);
	    	 var total=0;
	    	 if(dataField=='ThisYear'){
		  	    
	    		 for (var int = 0; int < data.length; int++) {
				total=total+parseFloat(data[int].ThisYear);      
				}
		  	    
	    	 }else if(dataField=='ThisMonth'){
		  	    
	    		 for (var int = 0; int < data.length; int++) {
					total=total+parseFloat(data[int].ThisMonth);
				}
		  	    
	    	 }else if(dataField=='ThisWeek'){
		  	    
	    		 for (var int = 0; int < data.length; int++) {
					total=total+parseFloat(data[int].ThisWeek);
				}
		  	    
	    	 }
		  	    
	    	
	    	sampleData = data;
	    	  var settings = {
	                  title: ""+title,
	                  description: ""+description ,
	                  enableAnimations: true,
		              showLegend: true,
		              padding: { left: 5, top: 5, right: 5, bottom: 5 },
	                  titlePadding: { left: 0, top: 0, right: 0, bottom: 10 },
	                  source: sampleData,
	                  colorScheme: 'scheme11',
	                  seriesGroups:
	                      [
	                          {
	                              type: 'pie',
	                              showLabels: true,
	                              series:
	                                  [ 
	                                    { 
	                                    	dataField: dataField,
	                                    	displayText: 'dept',
	                                    	labelRadius: 100,
	                                    	initialAngle: 15,
	                                    	radius: 55,
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
	    	  $('#jqxChart3').jqxChart({ enableAnimations: true });
	        $('#jqxChart3').jqxChart(settings);
	        $("#FlipValueAxis").jqxCheckBox({ width: 140,  checked: false });
	        $("#FlipCategoryAxis").jqxCheckBox({ width: 140,  checked: false });
	        var refreshChart = function () {
	            $('#levelChart').jqxChart({ enableAnimations: false });
	            $('#levelChart').jqxChart('refresh');
	        }
	        // update greyScale values.
	        $("#FlipValueAxis").on('change', function (event) {
	            var groups = $('#levelChart').jqxChart('seriesGroups');
	            groups[0].valueAxis.flip = event.args.checked;
	            refreshChart();
	        });
	        $("#FlipCategoryAxis").on('change', function (event) {
	            var categoryAxis = $('#levelChart').jqxChart('categoryAxis');
	            categoryAxis.flip = event.args.checked;
	            refreshChart();
	        });
	    	
	},
	   error: function() {
	        alert("error from jsonArray data ");
	    }
	 });
}

//pie for board 4
function showPieStatusPrevMonth(dataField,title,description) {
	maxDivId4="4thPieGraph";
	showDetailsPie1('prevPie3','nextPie3','jqxChart4');
	var  sampleData =null;
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataPrevMonthCompl.action",
	    type : "json",
	    success : function(data) {
	    	console.log("Pie Data");
	    	console.log(data);
	    	 var total=0;
	    	 if(dataField=='Pending'){
		  	    
	    		 for (var int = 0; int < data.length; int++) {
				total=total+parseFloat(data[int].Pending);      
				}
		  	    
	    	 }else if(dataField=='Done'){
		  	    
	    		 for (var int = 0; int < data.length; int++) {
					total=total+parseFloat(data[int].Done);
				}
		  	    
	    	 }
		  	    
	    	
	    	sampleData = data;
	    	  var settings = {
	                  title: ""+title,
	                  description: ""+description ,
	                  enableAnimations: true,
		              showLegend: true,
		              padding: { left: 5, top: 5, right: 5, bottom: 5 },
	                  titlePadding: { left: 0, top: 0, right: 0, bottom: 10 },
	                  source: sampleData,
	                  colorScheme: 'scheme11',
	                  seriesGroups:
	                      [
	                          {
	                              type: 'pie',
	                              showLabels: true,
	                              series:
	                                  [ 
	                                    { 
	                                    	dataField: dataField,
	                                    	displayText: 'dept',
	                                    	labelRadius: 100,
	                                    	initialAngle: 15,
	                                    	radius: 45,
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
	    	  $('#jqxChart4').jqxChart({ enableAnimations: true });
	        $('#jqxChart4').jqxChart(settings);
	        $("#FlipValueAxis").jqxCheckBox({ width: 140,  checked: false });
	        $("#FlipCategoryAxis").jqxCheckBox({ width: 140,  checked: false });
	        var refreshChart = function () {
	            $('#levelChart').jqxChart({ enableAnimations: false });
	            $('#levelChart').jqxChart('refresh');
	        }
	        // update greyScale values.
	        $("#FlipValueAxis").on('change', function (event) {
	            var groups = $('#levelChart').jqxChart('seriesGroups');
	            groups[0].valueAxis.flip = event.args.checked;
	            refreshChart();
	        });
	        $("#FlipCategoryAxis").on('change', function (event) {
	            var categoryAxis = $('#levelChart').jqxChart('categoryAxis');
	            categoryAxis.flip = event.args.checked;
	            refreshChart();
	        });
	    	
	},
	   error: function() {
	        alert("error from jsonArray data ");
	    }
	 });
}


//pie for board 7
function showPieTodays(dataField,title,description) {
	maxDivId5="7thPieGraph";
	showDetailsPie1('prevPie4','nextPie4','jqxChart7');
	var  sampleData =null;
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataTodaysRem.action",
	    type : "json",
	    success : function(data) {
	    	console.log("Pie Data");
	    	console.log(data);
	    	 var total=0;
	    	 if(dataField=='SelfDue'){
		  	    
	    		 for (var int = 0; int < data.length; int++) {
				total=total+parseFloat(data[int].SelfDue);      
				}
		  	    
	    	 }else if(dataField=='TeamDue'){
		  	    
	    		 for (var int = 0; int < data.length; int++) {
					total=total+parseFloat(data[int].TeamDue);
				}
		  	    
	    	 }else if(dataField=='SelfReminder1'){
		  	    
	    		 for (var int = 0; int < data.length; int++) {
					total=total+parseFloat(data[int].SelfReminder1);
				}
		  	    
	    	 }else if(dataField=='TeamReminder1'){
		  	    
	    		 for (var int = 0; int < data.length; int++) {
					total=total+parseFloat(data[int].TeamReminder1);
				}
		  	    
	    	 }else if(dataField=='SelfReminder2'){
		  	    
	    		 for (var int = 0; int < data.length; int++) {
					total=total+parseFloat(data[int].SelfReminder2);
				}
		  	    
	    	 }else if(dataField=='TeamReminder2'){
		  	    
	    		 for (var int = 0; int < data.length; int++) {
					total=total+parseFloat(data[int].TeamReminder2);
				}
		  	    
	    	 }else if(dataField=='SelfReminder3'){
		  	    
	    		 for (var int = 0; int < data.length; int++) {
					total=total+parseFloat(data[int].SelfReminder3);
				}
		  	    
	    	 }else if(dataField=='TeamReminder3'){
		  	    
	    		 for (var int = 0; int < data.length; int++) {
					total=total+parseFloat(data[int].TeamReminder3);
				}
		  	    
	    	 }
		  	    
	    	
	    	sampleData = data;
	    	  var settings = {
	                  title: ""+title,
	                  description: ""+description ,
	                  enableAnimations: true,
		              showLegend: true,
		              padding: { left: 5, top: 5, right: 5, bottom: 5 },
	                  titlePadding: { left: 0, top: 0, right: 0, bottom: 10 },
	                  source: sampleData,
	                  colorScheme: 'scheme11',
	                  seriesGroups:
	                      [
	                          {
	                              type: 'pie',
	                              showLabels: true,
	                              series:
	                                  [ 
	                                    { 
	                                    	dataField: dataField,
	                                    	displayText: 'dept',
	                                    	labelRadius: 100,
	                                    	initialAngle: 15,
	                                    	radius: 55,
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
	    	  $('#jqxChart7').jqxChart({ enableAnimations: true });
	        $('#jqxChart7').jqxChart(settings);
	        $("#FlipValueAxis").jqxCheckBox({ width: 140,  checked: false });
	        $("#FlipCategoryAxis").jqxCheckBox({ width: 140,  checked: false });
	        var refreshChart = function () {
	            $('#levelChart').jqxChart({ enableAnimations: false });
	            $('#levelChart').jqxChart('refresh');
	        }
	        // update greyScale values.
	        $("#FlipValueAxis").on('change', function (event) {
	            var groups = $('#levelChart').jqxChart('seriesGroups');
	            groups[0].valueAxis.flip = event.args.checked;
	            refreshChart();
	        });
	        $("#FlipCategoryAxis").on('change', function (event) {
	            var categoryAxis = $('#levelChart').jqxChart('categoryAxis');
	            categoryAxis.flip = event.args.checked;
	            refreshChart();
	        });
	    	
	},
	   error: function() {
	        alert("error from jsonArray data ");
	    }
	 });
}

























var maxDivId1=null;
var maxDivId2=null;
var maxDivId3=null;
var maxDivId4=null;
var maxDivId5=null;

/*//1st counter Table
$.ajax( {
	type :"post",
	url : "view/Over2Cloud/Compliance/compliance_pages/beforeCounterData.action?maximizeDivBlock=1stTableDta",
	success : function(statusdata) 
	{
		$("#jqxChart1").html(statusdata);
	},
	error : function() {
		alert("error");
	}
});*/

/*//2nd counter Table
$.ajax( {
	type :"post",
	url : "view/Over2Cloud/Compliance/compliance_pages/beforeCounterData.action?maximizeDivBlock=2ndDataBlockDiv",
	success : function(statusdata) 
	{
		$("#jqxChart2").html(statusdata);
	},
	error : function() {
		alert("error");
	}
});
*/
/*//3rd counter Table
$.ajax( {
	type :"post",
	url : "view/Over2Cloud/Compliance/compliance_pages/beforeCounterData.action?maximizeDivBlock=3rdDataBlockDiv",
	success : function(statusdata) 
	{
		$("#jqxChart3").html(statusdata);
	},
	error : function() {
		alert("error");
	}
});*/

//4th counter Table
$.ajax( {
	type :"post",
	url : "view/Over2Cloud/Compliance/compliance_pages/beforeCounterData.action?maximizeDivBlock=4thDataBlockDiv",
	success : function(statusdata) 
	{
		$("#jqxChart4").html(statusdata);
	},
	error : function() {
		alert("error");
	}
});

//6th counter Table
/*$.ajax( {
	type :"post",
	url : "view/Over2Cloud/Compliance/compliance_pages/beforeCounterData.action?maximizeDivBlock=7thDataBlockDiv",
	success : function(statusdata) 
	{
		$("#jqxChart7").html(statusdata);
	},
	error : function() {
		alert("error");
	}
});*/

function showData(id) {

$("#"+id).html("<center><br><br><br><br><img src=images/ajax-loaderNew.gif></center>");
	
	if(id=='jqxChart1')
	{
		restoreBoard('prevPie','nextPie','jqxChart1');
		maxDivId1='1stTable';
	$.ajax( {
		type :"post",
		url : "view/Over2Cloud/Compliance/compliance_pages/beforeCounterData.action?maximizeDivBlock=1stTableDta",
		success : function(statusdata) 
		{
			$("#"+id).html(statusdata);
		},
		error : function() {
			alert("error");
		}
	});
 }else if(id=='jqxChart2')
	{
	 restoreBoard('prevPie1','nextPie1','jqxChart2');
	 maxDivId2='2ndDataDiv';
		$.ajax( {
			type :"post",
			url : "view/Over2Cloud/Compliance/compliance_pages/beforeCounterData.action?maximizeDivBlock=2ndDataBlockDiv",
			success : function(statusdata) 
			{
				$("#"+id).html(statusdata);
			},
			error : function() {
				alert("error");
			}
		});
	 }else if(id=='jqxChart3')
		{
		 restoreBoard('prevPie2','nextPie2','jqxChart3');
		 maxDivId3='3rdDataDiv';
			$.ajax( {
				type :"post",
				url : "view/Over2Cloud/Compliance/compliance_pages/beforeCounterData.action?maximizeDivBlock=3rdDataBlockDiv",
				success : function(statusdata) 
				{
					$("#"+id).html(statusdata);
				},
				error : function() {
					alert("error");
				}
			});
		 }else if(id=='jqxChart4')
			{
			 restoreBoard('prevPie3','nextPie3','jqxChart4');
			 maxDivId4='4thDataDiv';
				$.ajax( {
					type :"post",
					url : "view/Over2Cloud/Compliance/compliance_pages/beforeCounterData.action?maximizeDivBlock=4thDataBlockDiv",
					success : function(statusdata) 
					{
						$("#"+id).html(statusdata);
					},
					error : function() {
						alert("error");
					}
				});
			 }else if(id=='jqxChart7')
				{
					restoreBoard('prevPie4','nextPie4','jqxChart7');
				 maxDivId5='7thDataDiv';
					$.ajax( {
						type :"post",
						url : "view/Over2Cloud/Compliance/compliance_pages/beforeCounterData.action?maximizeDivBlock=7thDataBlockDiv",
						success : function(statusdata) 
						{
							$("#"+id).html(statusdata);
						},
						error : function() {
							alert("error");
						}
					});
				 }
	
}//show data end 


function showChart(selid,id)
{
	$("#"+id).html("<center><br><br><br><br><img src=images/ajax-loaderNew.gif></center>");
	
	if(selid=='1stPie'&&id=='jqxChart1')
	{
		 maxDivId1='1stPie';
	$.ajax( {
		type :"post",
		url : "view/Over2Cloud/Compliance/compliance_pages/PieChartForCompDash.action?divName="+selid,
		success : function(piedata) 
		{
			$("#"+id).html(piedata);
		},
		error : function() {
			alert("error");
		}
	});
	    
	}else if(selid=='1stBarGraph'&&id=='jqxChart1'){
		restoreBoard('prevPie','nextPie','jqxChart1');
		
		var  sampleData =null;
		 maxDivId1='1stBarGraph';

		$.ajax({
		    type : "post",
		   
		    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataStatus.action",
		    type : "json",
		    success : function(data) {
		    	console.log("new data array for status");
		    	console.log(data);
		    	sampleData = data;
		    	var settings = {
		    	    	
		    			title: '              ',
		                description: '        ',
		                enableAnimations: true,
		                showLegend: true,
		                padding: { left: 5, top: 5, right: 5, bottom: 5 },
		                titlePadding: { left: 90, top: 10, right: 0, bottom: 10 },
		                source: sampleData,
		                categoryAxis:
		                    {
		                        text: 'Category Axis',
		                        textRotationAngle: 0,
		                        dataField: 'dept',
		                        showTickMarks: true,
		                        tickMarksInterval: 1,
		                        tickMarksColor: '#888888',
		                        unitInterval: 1,
		                        showGridLines: true,
		                        gridLinesInterval: 1,
		                        gridLinesColor: '#888888',
		                        axisSize: 'auto'
		                    },
		                colorScheme: 'scheme06',
		                seriesGroups:
		                    [
		                        {
		                            type: 'stackedcolumn',
		                            columnsGapPercent: 100,
		                            seriesGapPercent: 5,
		                            valueAxis:
		                            {
		                                minValue: 0,
		                                displayValueAxis: true,
		                                description: 'Ticket Counters',
		                                axisSize: 'auto',
		                                tickMarksColor: '#888888',
		                                gridLinesColor: '#777777'
		                            },
		                            series: 
		                        	[
		                                { dataField: 'Pending', displayText: 'Pending', color:'#FF0033'},
		                                { dataField: 'Snooze', displayText: 'Snooze' , color:'#A0A0A0' },
		                                { dataField: 'Missed', displayText: 'Missed',color:'#C87900' },
		                                { dataField: 'Done', displayText: 'Done', color:'#009933' }
		                            ]   
		                        }
		                    ]
		            };
		    	    
		    	// setup the chart
		        $('#jqxChart1').jqxChart(settings);
		        $("#FlipValueAxis").jqxCheckBox({ width: 140,  checked: false });
		        $("#FlipCategoryAxis").jqxCheckBox({ width: 140,  checked: false });
		        var refreshChart = function () {
		            $('#jqxChart').jqxChart({ enableAnimations: false });
		            $('#jqxChart').jqxChart('refresh');
		        }
		        // update greyScale values.
		        $("#FlipValueAxis").on('change', function (event) {
		            var groups = $('#jqxChart').jqxChart('seriesGroups');
		            groups[0].valueAxis.flip = event.args.checked;
		            refreshChart();
		        });
		        $("#FlipCategoryAxis").on('change', function (event) {
		            var categoryAxis = $('#jqxChart').jqxChart('categoryAxis');
		            categoryAxis.flip = event.args.checked;
		            refreshChart();
		        });
		    	
		},
		   error: function() {
		        alert("error from jsonArray data ");
		    }
		 });
		
	}else if(selid=='1stColumn2Axes'&&id=='jqxChart1'){
		restoreBoard('prevPie','nextPie','jqxChart1');
		 maxDivId1='1stColumn2AxesBar';
		 var  sampleData =null;
			
			$.ajax({
			    type : "post",
			   
			    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataStatus.action",
			    type : "json",
			    success : function(data) {
			    	
			    	console.log(data);
			    	sampleData = data;
			    	var settings = {
			    	    	
			    			title: '     ',
			                description: '      ',
			                enableAnimations: true,
			                showLegend: true,
			                padding: { left: 5, top: 5, right: 5, bottom: 5 },
			                titlePadding: { left: 90, top: 10, right: 0, bottom: 10 },
			                source: sampleData,
			                categoryAxis:
			                    {
			                        text: 'Category Axis',
			                        textRotationAngle: 0,
			                        dataField: 'dept',
			                        showTickMarks: true,
			                        tickMarksInterval: 1,
			                        tickMarksColor: '#888888',
			                        unitInterval: 1,
			                        showGridLines: true,
			                        gridLinesInterval: 1,
			                        gridLinesColor: '#888888',
			                        axisSize: 'auto'
			                    },
			                colorScheme: 'scheme06',
			                seriesGroups:
			                    [
			                        {
			                            type: 'column',
			                            columnsGapPercent: 100,
			                            seriesGapPercent: 5,
			                            valueAxis:
			                            {
			                                minValue: 0,
			                                displayValueAxis: true,
			                                description: 'Ticket Counters',
			                                axisSize: 'auto',
			                                tickMarksColor: '#888888',
			                                gridLinesColor: '#777777'
			                            },
			                            series: 
			                        	[
			                                  { dataField: 'Pending', displayText: 'Pending', color:'#FF0033'},
		                                { dataField: 'Snooze', displayText: 'Snooze' , color:'#A0A0A0' },
		                                { dataField: 'Missed', displayText: 'Missed',color:'#C87900' },
		                                { dataField: 'Done', displayText: 'Done', color:'#009933' }			                            ]
			                        }
			                    ]
			            };
			    	    
			    	// setup the chart
			        $('#jqxChart1').jqxChart(settings);
			        $("#FlipValueAxis").jqxCheckBox({ width: 140,  checked: false });
			        $("#FlipCategoryAxis").jqxCheckBox({ width: 140,  checked: false });
			        var refreshChart = function () {
			            $('#jqxChart').jqxChart({ enableAnimations: false });
			            $('#jqxChart').jqxChart('refresh');
			        }
			        // update greyScale values.
			        $("#FlipValueAxis").on('change', function (event) {
			            var groups = $('#jqxChart').jqxChart('seriesGroups');
			            groups[0].valueAxis.flip = event.args.checked;
			            refreshChart();
			        });
			        $("#FlipCategoryAxis").on('change', function (event) {
			            var categoryAxis = $('#jqxChart').jqxChart('categoryAxis');
			            categoryAxis.flip = event.args.checked;
			            refreshChart();
			        });
			    	
			},
			   error: function() {
			        alert("error from jsonArray data ");
			    }
			 });
		
	}else if(selid=='1stBubbleGraph'&&id=='jqxChart1'){
		restoreBoard('prevPie','nextPie','jqxChart1');
		 maxDivId1='1stBubbleGraph';
		 var  sampleData =null;
			$.ajax({
			    type : "post",
			   
			    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataStatus.action",
			    type : "json",
			    success : function(data) {
			    	
			    	console.log(data);
			    	sampleData = data;
			    	 var settings = {
			                 title: '  ',
			                 description: '  ',
			                 enableAnimations: true,
			                 showLegend: true,
			                 padding: { left: 5, top: 5, right: 5, bottom: 5 },
			                 titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
			                 source: sampleData,
			                 xAxis:
			                     {
			                         dataField: 'dept',
			                         valuesOnTicks: false
			                     },
			                 colorScheme: 'scheme06',
			                 seriesGroups:
			                     [
			                         {
			                             type: 'bubble',
			                             valueAxis:
			                             {
			                                 unitInterval: 10,
			                                 minValue: 0,
			                                 maxValue: 50,
			                                 description: 'Ticket Counter'
			                                 
			                             },
			                             series: [
			                                     { dataField: 'Pending', radiusDataField: 1.5, minRadius: 10, maxRadius: 30, displayText: 'Pending', color:'#FF0033' },
			                                     { dataField: 'Snooze', radiusDataField: 1.25, minRadius: 10, maxRadius: 30, displayText: 'Snooze' , color:'#A0A0A0'},
			                                     { dataField: 'Missed', radiusDataField: 0.85, minRadius: 10, maxRadius: 30, displayText: 'Missed' ,color:'#C87900'},
			                                     { dataField: 'Done', radiusDataField: 0.85, minRadius: 10, maxRadius: 30, displayText: 'Done' ,color:'#009933'},
			                                     ]
			                          }  
			                     ]
			             };
			    	    
			    	// setup the chart
			    	 $('#jqxChart1').jqxChart(settings);
			        $("#FlipValueAxis").jqxCheckBox({ width: 140,  checked: false });
			        $("#FlipCategoryAxis").jqxCheckBox({ width: 140,  checked: false });
			        var refreshChart = function () {
			            $('#jqxChart').jqxChart({ enableAnimations: false });
			            $('#jqxChart').jqxChart('refresh');
			        }
			        // update greyScale values.
			        $("#FlipValueAxis").on('change', function (event) {
			            var groups = $('#jqxChart').jqxChart('seriesGroups');
			            groups[0].valueAxis.flip = event.args.checked;
			            refreshChart();
			        });
			        $("#FlipCategoryAxis").on('change', function (event) {
			            var categoryAxis = $('#jqxChart').jqxChart('categoryAxis');
			            categoryAxis.flip = event.args.checked;
			            refreshChart();
			        });
			    	
			},
			   error: function() {
			        alert("error from jsonArray data ");
			    }
			 });
	}else if(selid=='2ndStackBarGraph'&&id=='jqxChart2'){
		restoreBoard('prevPie1','nextPie1','jqxChart2');
		 maxDivId2='2ndStackBarGraph';
		 var  sampleData =null;
			$.ajax({
			    type : "post",
			   
			    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDatafrequency.action",
			    type : "json",
			    success : function(data) {
			    	console.log("new data array for Freq");
			    	console.log(data);
			    	sampleData = data;
			    	var settings = {
			    	    	
			    			title: '  ',
			                description: '  ',
			                enableAnimations: true,
			                showLegend: true,
			                padding: { left: 5, top: 5, right: 5, bottom: 5 },
			                titlePadding: { left: 90, top: 10, right: 0, bottom: 10 },
			                source: sampleData,
			                categoryAxis:
			                    {
			                        text: 'Category Axis',
			                        textRotationAngle: 0,
			                        dataField: 'dept',
			                        showTickMarks: true,
			                        tickMarksInterval: 1,
			                        tickMarksColor: '#888888',
			                        unitInterval: 1,
			                        showGridLines: true,
			                        gridLinesInterval: 1,
			                        gridLinesColor: '#888888',
			                        axisSize: 'auto'
			                    },
			                colorScheme: 'scheme08',
			                seriesGroups:
			                    [
			                        {
			                            type: 'stackedcolumn',
			                            columnsGapPercent: 100,
			                            seriesGapPercent: 5,
			                            valueAxis:
			                            {
			                                minValue: 0,
			                                displayValueAxis: true,
			                                description: 'Ticket Counters',
			                                axisSize: 'auto',
			                                tickMarksColor: '#888888',
			                                gridLinesColor: '#777777'
			                            },
			                            series: 
			                        	[
			                                { dataField: 'YearlyTotal', displayText: 'Yearly Total',color:'#FF0000' },
			                                { dataField: 'YearlyMissed', displayText: 'Yearly Missed',color:'#779900' },
			                                { dataField: 'MonthlyTotal', displayText: 'Monthly Total',color:'#009900' },
			                                { dataField: 'MonthlyMissed', displayText: 'Monthly Missed',color:'#C87900' },
			                                { dataField: 'WeeklyTotal', displayText: 'Weekly Total',color:'#F76F90'},
			                                { dataField: 'WeeklyMissed', displayText: 'Weekly Missed',color:'#A778809' }
			                            ]
			                            
			                        }
			                    ]
			            };
			    	// setup the chart
			    	
			        $('#jqxChart2').jqxChart(settings);
			        $("#FlipValueAxis").jqxCheckBox({ width: 140,  checked: false });
			        $("#FlipCategoryAxis").jqxCheckBox({ width: 140,  checked: false });
			        var refreshChart = function () {
			            $('#jqxChart').jqxChart({ enableAnimations: false });
			            $('#jqxChart').jqxChart('refresh');
			        }
			        // update greyScale values.
			        $("#FlipValueAxis").on('change', function (event) {
			            var groups = $('#jqxChart').jqxChart('seriesGroups');
			            groups[0].valueAxis.flip = event.args.checked;
			            refreshChart();
			        });
			        $("#FlipCategoryAxis").on('change', function (event) {
			            var categoryAxis = $('#jqxChart').jqxChart('categoryAxis');
			            categoryAxis.flip = event.args.checked;
			            refreshChart();
			        });
			    	
			},
			   error: function() {
			        alert("error from jsonArray data ");
			    }
			 });
		
	}else if(selid=='1stLine'&&id=='jqxChart1'){
		
		restoreBoard('prevPie','nextPie','jqxChart1');
		 maxDivId1='1stLine';
		 var  sampleData =null;
			$.ajax({
	  	  	    type : "post",
	  	  	    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataStatus.action",
	  	  	    success : function(statusdata) {
	  	  	    	console.log("Status Data");
	  	  	    	console.log(statusdata);
	  	  	    	sampleData=statusdata;
	  	  	    	
	  	  	    var toolTipCustomFormatFn = function (key,value) {
	              var data=statusdata[value];
	                return                              '<DIV style="text-align:left"><b>Department: </b>' + data.dept+
	                									'<br /><b>Pending: </b>' +data.Pending+
	                									'<br /><b>Snooze: </b>' +data.Snooze+
	                									'<br /><b>Missed: </b>' +data.Missed+
	                									'<br /><b>Done: </b>' +data.Done+
	                									'</DIV>';
	            };

	  	  	    var settings = {
	  	                title: " ",
	  	                description: " ",
	  	                padding: { left: 5, top: 5, right: 5, bottom: 5 },
	  	                titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
	  	                source: sampleData,
	  	              enableAnimations: true,
	  	                
	  	                categoryAxis:
	  	                    {	
	  	                		textRotationAngle:0,
	  	                        dataField: 'dept',
	  	                       
	  	                        showGridLines: false,
	  	                    },
	  	                colorScheme: 'scheme04',
	  	                seriesGroups:
	  	                    [                    
	  	                        {
	  	                            type: 'line',
	  	                          showLabels: true,
	  	                           /*  formatSettings:
	  	                            {
	  	                                prefix: 'Time ',
	  	                                decimalPlaces: 0,
	  	                                sufix: ' min'
	  	                            } */
	  	                          toolTipFormatFunction: toolTipCustomFormatFn,

	  	                            valueAxis:
	  	                            {
	  	                            	minValue: 0,
		                                displayValueAxis: true,
		                                description: 'Ticket Counter',
		                                axisSize: 'auto',
	  	                            },
	  	                            series: [
	  	                                   
	  	                                   { dataField: 'Pending', displayText: 'Pending'},
			                                { dataField: 'Snooze', displayText: 'Snooze' },
			                                { dataField: 'Missed', displayText: 'Missed' },
			                                { dataField: 'Done', displayText: 'Done' }		
												  	                        
	  	                                ]
	  	                        }
	  	                    ]
	  	            };
	  	            
	  	            $('#jqxChart1').jqxChart(settings);
	  	  	    	
	  	  	},
	  	  	   error: function() {
	  	              alert("error");
	  	          }
	  	  	 });
	
	}else if(selid=='2ndColumn2AxesGraph'&&id=='jqxChart2'){
		restoreBoard('prevPie1','nextPie1','jqxChart2');
		 maxDivId2='2ndColumn2AxesGraph';
		 var  sampleData =null;
			$.ajax({
			    type : "post",
			   
			    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDatafrequency.action",
			    type : "json",
			    success : function(data) {
			    	
			    	console.log(data);
			    	sampleData = data;
			    	var settings = {
			    	    	
			    			title: '  ',
			                description: '  ',
			                enableAnimations: true,
			                showLegend: true,
			                padding: { left: 5, top: 5, right: 5, bottom: 5 },
			                titlePadding: { left: 90, top: 10, right: 0, bottom: 10 },
			                source: sampleData,
			                categoryAxis:
			                    {
			                        text: 'Category Axis',
			                        textRotationAngle: 0,
			                        dataField: 'dept',
			                        showTickMarks: true,
			                        tickMarksInterval: 1,
			                        tickMarksColor: '#888888',
			                        unitInterval: 1,
			                        showGridLines: true,
			                        gridLinesInterval: 1,
			                        gridLinesColor: '#888888',
			                        axisSize: 'auto'
			                    },
			                colorScheme: 'scheme11',
			                seriesGroups:
			                    [
			                        {
			                            type: 'column',
			                            columnsGapPercent: 100,
			                            seriesGapPercent: 5,
			                            valueAxis:
			                            {
			                                minValue: 0,
			                                displayValueAxis: true,
			                                description: 'Ticket Counters',
			                                axisSize: 'auto',
			                                tickMarksColor: '#888888',
			                                gridLinesColor: '#777777'
			                            },
			                            series: 
			                        	[
			                                { dataField: 'YearlyTotal', displayText: 'Yearly Total',color:'#FF0000' },
			                                { dataField: 'YearlyMissed', displayText: 'Yearly Missed',color:'#779900' },
			                                { dataField: 'MonthlyTotal', displayText: 'Monthly Total',color:'#009900' },
			                                { dataField: 'MonthlyMissed', displayText: 'Monthly Missed' ,color:'#C87900'},
			                                { dataField: 'WeeklyTotal', displayText: 'Weekly Total',color:'#F76F90'},
			                                { dataField: 'WeeklyMissed', displayText: 'Weekly Missed',color:'#CC9095'}
			                            ]
			                        }
			                    ]
			            };
			    	// setup the chart
			        $('#jqxChart2').jqxChart(settings);
			        $("#FlipValueAxis").jqxCheckBox({ width: 140,  checked: false });
			        $("#FlipCategoryAxis").jqxCheckBox({ width: 140,  checked: false });
			        var refreshChart = function () {
			            $('#jqxChart').jqxChart({ enableAnimations: false });
			            $('#jqxChart').jqxChart('refresh');
			        }
			        // update greyScale values.
			        $("#FlipValueAxis").on('change', function (event) {
			            var groups = $('#jqxChart').jqxChart('seriesGroups');
			            groups[0].valueAxis.flip = event.args.checked;
			            refreshChart();
			        });
			        $("#FlipCategoryAxis").on('change', function (event) {
			            var categoryAxis = $('#jqxChart').jqxChart('categoryAxis');
			            categoryAxis.flip = event.args.checked;
			            refreshChart();
			        });
			    	
			},
			   error: function() {
			        alert("error from jsonArray data ");
			    }
			 });
	
	}else if(selid=='2ndPie'&&id=='jqxChart2'){
		 maxDivId2='2ndPie';
		$.ajax( {
			type :"post",
			url : "view/Over2Cloud/Compliance/compliance_pages/PieChartForCompDash.action?divName="+selid,
			success : function(ticketdata) 
			{
				$("#"+id).html(ticketdata);
			},
			error : function() {
				alert("error");
			}
		});
	
	}else if(selid=='2ndLine'&&id=='jqxChart2'){
		restoreBoard('prevPie1','nextPie1','jqxChart2');
		 maxDivId2='2ndLine';
		 var  sampleData =null;
			$.ajax({
	  	  	    type : "post",
	  	  	    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDatafrequency.action",
	  	  	    success : function(statusdata) {
	  	  	    	console.log("Status Data");
	  	  	    	console.log(statusdata);
	  	  	    	sampleData=statusdata;
	  	  	    	
	  	  	    var toolTipCustomFormatFn = function (key,value) {
	              var data=statusdata[value];
	                return                              '<DIV style="text-align:left"><b>Department: </b>' + data.dept+
	                									'<br /><b>Yearly Total: </b>' +data.YearlyTotal+
	                									'<br /><b>Yearly Missed: </b>' +data.YearlyMissed+
	                									'<br /><b>Monthly Total: </b>' +data.MonthlyTotal+
	                									'<br /><b>Monthly Missed: </b>' +data.MonthlyMissed+
	                									'<br /><b>Weekly Total: </b>' +data.WeeklyTotal+
	                									'<br /><b>Weekly Missed: </b>' +data.WeeklyMissed+
	                									'<br /><b>Weekly Missed: </b>' +data.WeeklyMissed+
	                									'</DIV>';
	            };

	  	  	    var settings = {
	  	                title: " ",
	  	                description: " ",
	  	                padding: { left: 5, top: 5, right: 5, bottom: 5 },
	  	                titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
	  	                source: sampleData,
	  	              enableAnimations: true,
	  	                
	  	                categoryAxis:
	  	                    {	
	  	                		textRotationAngle:0,
	  	                        dataField: 'dept',
	  	                       
	  	                        showGridLines: false,
	  	                    },
	  	                colorScheme: 'scheme04',
	  	                seriesGroups:
	  	                    [                    
	  	                        {
	  	                            type: 'line',
	  	                          showLabels: true,
	  	                          
	  	                           /*  formatSettings:
	  	                            {
	  	                                prefix: 'Time ',
	  	                                decimalPlaces: 0,
	  	                                sufix: ' min'
	  	                            } */
	  	                          toolTipFormatFunction: toolTipCustomFormatFn,

	  	                            valueAxis:
	  	                            {
	  	                            	minValue: 0,
		                                displayValueAxis: true,
		                                description: 'Ticket Counter',
		                                axisSize: 'auto',
	  	                            },
	  	                            series: [
	  	                                   
												{ dataField: 'YearlyTotal', displayText: 'Yearly Total'},
												{ dataField: 'YearlyMissed', displayText: 'Yearly Missed' },
												{ dataField: 'MonthlyTotal', displayText: 'Monthly Total' },
												{ dataField: 'MonthlyMissed', displayText: 'Monthly Missed'},
												{ dataField: 'WeeklyTotal', displayText: 'Weekly Total'},
												{ dataField: 'WeeklyMissed', displayText: 'Weekly Missed',color:'#000000'}	
												  	                        
	  	                                ]
	  	                        }
	  	                    ]
	  	            };
	  	            
	  	            $('#jqxChart2').jqxChart(settings);
	  	  	    	
	  	  	},
	  	  	   error: function() {
	  	              alert("error");
	  	          }
	  	  	 });
	
	
	}else if(selid=='2ndBubbleGraph'&&id=='jqxChart2'){
		restoreBoard('prevPie1','nextPie1','jqxChart2');
		 maxDivId2='2ndBubbleGraph';
		 var  sampleData =null;
			$.ajax({
			    type : "post",
			   
			    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDatafrequency.action",
			    type : "json",
			    success : function(data) {
			    	
			    	console.log(data);
			    	sampleData = data;
			    	 var settings = {
			                 title: ' ',
			                 description: '  ',
			                 enableAnimations: true,
			                 showLegend: true,
			                 padding: { left: 5, top: 5, right: 5, bottom: 5 },
			                 titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
			                 source: sampleData,
			                 xAxis:
			                     {
			                         dataField: 'dept',
			                         valuesOnTicks: false
			                     },
			                 colorScheme: 'scheme06',
			                 seriesGroups:
			                     [
			                         {
			                             type: 'bubble',
			                             valueAxis:
			                             {
			                                 unitInterval: 10,
			                                 minValue: 0,
			                                 maxValue: 50,
			                                 description: 'Ticket Counter'
			                                 
			                             },
			                             series: [
			                                      { dataField: 'YearlyTotal', radiusDataField: 1.5, minRadius: 10, maxRadius: 30, displayText: 'Yearly Total' ,color:'#FF0000'},
				                                     { dataField: 'YearlyMissed', radiusDataField: 1.25, minRadius: 10, maxRadius: 30, displayText: 'Yearly Missed',color:'#779900' },
				                                     { dataField: 'MonthlyTotal', radiusDataField: 0.85, minRadius: 10, maxRadius: 30, displayText: 'Monthly Total' ,color:'#009900'},
				                                     { dataField: 'MonthlyMissed', radiusDataField: 0.95, minRadius: 10, maxRadius: 30, displayText: 'Monthly Missed',color:'#C87900'},
				                                     { dataField: 'WeeklyTotal', radiusDataField: 0.95, minRadius: 10, maxRadius: 30, displayText: 'Weekly Total',color:'#F76F90'},
				                                     { dataField: 'WeeklyMissed', radiusDataField: 0.95, minRadius: 10, maxRadius: 30, displayText: 'Weekly Missed',color:'#CC9095'}
			                                     ]
			                          }
			                     ]
			             };
			    	    
			    	// setup the chart
			    	 $('#jqxChart2').jqxChart(settings);
			        $("#FlipValueAxis").jqxCheckBox({ width: 140,  checked: false });
			        $("#FlipCategoryAxis").jqxCheckBox({ width: 140,  checked: false });
			        var refreshChart = function () {
			            $('#jqxChart').jqxChart({ enableAnimations: false });
			            $('#jqxChart').jqxChart('refresh');
			        }
			        // update greyScale values.
			        $("#FlipValueAxis").on('change', function (event) {
			            var groups = $('#jqxChart').jqxChart('seriesGroups');
			            groups[0].valueAxis.flip = event.args.checked;
			            refreshChart();
			        });
			        $("#FlipCategoryAxis").on('change', function (event) {
			            var categoryAxis = $('#jqxChart').jqxChart('categoryAxis');
			            categoryAxis.flip = event.args.checked;
			            refreshChart();
			        });
			    	
			},
			   error: function() {
			        alert("error from jsonArray data ");
			    }
			 });
	
	}else if(selid=='3rdStackBarGraph'&&id=='jqxChart3'){
		restoreBoard('prevPie2','nextPie2','jqxChart3');
		 maxDivId3='3rdStackBarGraph';
		 var  sampleData =null;
			$.ajax({
			    type : "post",
			   
			    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataAgieng.action",
			    type : "json",
			    success : function(data) {
			    	
			    	console.log(data);
			    	sampleData = data;
			    	var settings = {
			    	    	
			    			title: ' ',
			                description: ' ',
			                enableAnimations: true,
			                showLegend: true,
			                padding: { left: 5, top: 5, right: 5, bottom: 5 },
			                titlePadding: { left: 90, top: 10, right: 0, bottom: 10 },
			                source: sampleData,
			                categoryAxis:
			                    {
			                        text: 'Category Axis',
			                        textRotationAngle: 0,
			                        dataField: 'dept',
			                        showTickMarks: true,
			                        tickMarksInterval: 1,
			                        tickMarksColor: '#888888',
			                        unitInterval: 1,
			                        showGridLines: true,
			                        gridLinesInterval: 1,
			                        gridLinesColor: '#888888',
			                        axisSize: 'auto'
			                    },
			                colorScheme: 'scheme01',
			                seriesGroups:
			                    [
			                        {
			                            type: 'stackedcolumn',
			                            columnsGapPercent: 100,
			                            seriesGapPercent: 5,
			                            valueAxis:
			                            {
			                                minValue: 0,
			                                displayValueAxis: true,
			                                description: 'Ticket Counters',
			                                axisSize: 'auto',
			                                tickMarksColor: '#888888',
			                                gridLinesColor: '#777777'
			                            },
			                            series: 
			                        	[
			                                { dataField: 'ThisYear', displayText: 'This Year' },
			                                { dataField: 'ThisMonth', displayText: 'This Month' },
			                                { dataField: 'ThisWeek', displayText: 'This Week'  },
			                              
			                            ]
			                        }
			                    ]
			            };
			    	// setup the chart
			        $('#jqxChart3').jqxChart(settings);
			        $("#FlipValueAxis").jqxCheckBox({ width: 140,  checked: false });
			        $("#FlipCategoryAxis").jqxCheckBox({ width: 140,  checked: false });
			        var refreshChart = function () {
			            $('#jqxChart').jqxChart({ enableAnimations: false });
			            $('#jqxChart').jqxChart('refresh');
			        }
			        // update greyScale values.
			        $("#FlipValueAxis").on('change', function (event) {
			            var groups = $('#jqxChart').jqxChart('seriesGroups');
			            groups[0].valueAxis.flip = event.args.checked;
			            refreshChart();
			        });
			        $("#FlipCategoryAxis").on('change', function (event) {
			            var categoryAxis = $('#jqxChart').jqxChart('categoryAxis');
			            categoryAxis.flip = event.args.checked;
			            refreshChart();
			        });
			    	
			},
			   error: function() {
			        alert("error from jsonArray data ");
			    }
			 });
	
	}else if(selid=='3rdColumn2AxesGraph'&&id=='jqxChart3'){
		restoreBoard('prevPie2','nextPie2','jqxChart3');
		 maxDivId3='3rdColumn2AxesGraph';
		 var  sampleData =null;
			$.ajax({
			    type : "post",
			   
			    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataAgieng.action",
			    type : "json",
			    success : function(data) {
			    	
			    	console.log(data);
			    	sampleData = data;
			    	var settings = {
			    	    	
			    			title: ' ',
			                description: ' ',
			                enableAnimations: true,
			                showLegend: true,
			                padding: { left: 5, top: 5, right: 5, bottom: 5 },
			                titlePadding: { left: 90, top: 10, right: 0, bottom: 10 },
			                source: sampleData,
			                categoryAxis:
			                    {
			                        text: 'Category Axis',
			                        textRotationAngle: 0,
			                        dataField: 'dept',
			                        showTickMarks: true,
			                        tickMarksInterval: 1,
			                        tickMarksColor: '#888888',
			                        unitInterval: 1,
			                        showGridLines: true,
			                        gridLinesInterval: 1,
			                        gridLinesColor: '#888888',
			                        axisSize: 'auto'
			                    },
			                colorScheme: 'scheme01',
			                seriesGroups:
			                    [
			                        {
			                            type: 'column',
			                            columnsGapPercent: 100,
			                            seriesGapPercent: 5,
			                            valueAxis:
			                            {
			                                minValue: 0,
			                                displayValueAxis: true,
			                                description: 'Ticket Counters',
			                                axisSize: 'auto',
			                                tickMarksColor: '#888888',
			                                gridLinesColor: '#777777'
			                            },
			                            series: 
			                        	[
			                                { dataField: 'ThisYear', displayText: 'This Year' },
			                                { dataField: 'ThisMonth', displayText: 'This Month' },
			                                { dataField: 'ThisWeek', displayText: 'This Week'  },
			                              
			                            ]
			                        }
			                    ]
			            };
			    	// setup the chart
			        $('#jqxChart3').jqxChart(settings);
			        $("#FlipValueAxis").jqxCheckBox({ width: 140,  checked: false });
			        $("#FlipCategoryAxis").jqxCheckBox({ width: 140,  checked: false });
			        var refreshChart = function () {
			            $('#jqxChart').jqxChart({ enableAnimations: false });
			            $('#jqxChart').jqxChart('refresh');
			        }
			        // update greyScale values.
			        $("#FlipValueAxis").on('change', function (event) {
			            var groups = $('#jqxChart').jqxChart('seriesGroups');
			            groups[0].valueAxis.flip = event.args.checked;
			            refreshChart();
			        });
			        $("#FlipCategoryAxis").on('change', function (event) {
			            var categoryAxis = $('#jqxChart').jqxChart('categoryAxis');
			            categoryAxis.flip = event.args.checked;
			            refreshChart();
			        });
			    	
			},
			   error: function() {
			        alert("error from jsonArray data ");
			    }
			 });
	
	}else if(selid=='3rdPie'&&id=='jqxChart3'){
		 maxDivId3='3rdPie';
		$.ajax( {
			type :"post",
			url : "view/Over2Cloud/Compliance/compliance_pages/PieChartForCompDash.action?divName="+selid,
			success : function(ticketdata) 
			{
				$("#"+id).html(ticketdata);
			},
			error : function() {
				alert("error");
			}
		});
	
	}else if(selid=='3rdLine'&&id=='jqxChart3'){
		restoreBoard('prevPie2','nextPie2','jqxChart3');
		 maxDivId3='3rdLine';
		 var  sampleData =null;
			$.ajax({
	  	  	    type : "post",
	  	  	    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataAgieng.action",
	  	  	    success : function(statusdata) {
	  	  	    	console.log("Status Data");
	  	  	    	console.log(statusdata);
	  	  	    	sampleData=statusdata;
	  	  	    	
	  	  	    var toolTipCustomFormatFn = function (key,value) {
	              var data=statusdata[value];
	                return                            		 '<DIV style="text-align:left"><b>Department: </b>' + data.dept+
															'<br /><b>This Year: </b>' +data.ThisYear+
															'<br /><b>This Month: </b>' +data.ThisMonth+
															'<br /><b>This Week: </b>' +data.ThisWeek+
															'</DIV>';
	            };

	  	  	    var settings = {
	  	                title: " ",
	  	                description: " ",
	  	                padding: { left: 5, top: 5, right: 5, bottom: 5 },
	  	                titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
	  	                source: sampleData,
	  	              enableAnimations: true,
	  	                
	  	                categoryAxis:
	  	                    {	
	  	                		textRotationAngle:0,
	  	                        dataField: 'dept',
	  	                       
	  	                        showGridLines: false,
	  	                    },
	  	                colorScheme: 'scheme04',
	  	                seriesGroups:
	  	                    [                    
	  	                        {
	  	                            type: 'line',
	  	                          showLabels: true,
	  	                          
	  	                           /*  formatSettings:
	  	                            {
	  	                                prefix: 'Time ',
	  	                                decimalPlaces: 0,
	  	                                sufix: ' min'
	  	                            } */
	  	                          toolTipFormatFunction: toolTipCustomFormatFn,

	  	                            valueAxis:
	  	                            {
	  	                            	minValue: 0,
		                                displayValueAxis: true,
		                                description: 'Ticket Counter',
		                                axisSize: 'auto',
	  	                            },
	  	                            series: [
	  	                                   
	  	                                   { dataField: 'ThisYear', displayText: 'This Year' },
			                                { dataField: 'ThisMonth', displayText: 'This Month' },
			                                { dataField: 'ThisWeek', displayText: 'This Week'  },
												  	                        
	  	                                ]
	  	                        }
	  	                    ]
	  	            };
	  	            
	  	            $('#jqxChart3').jqxChart(settings);
	  	  	    	
	  	  	},
	  	  	   error: function() {
	  	              alert("error");
	  	          }
	  	  	 });	
		
	
	}else if(selid=='3rdBubbleGraph'&&id=='jqxChart3'){
		restoreBoard('prevPie2','nextPie2','jqxChart3');
		 maxDivId3='3rdBubbleGraph';
		 var  sampleData =null;
			$.ajax({
			    type : "post",
			   
			    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataAgieng.action",
			    type : "json",
			    success : function(data) {
			    	
			    	console.log(data);
			    	sampleData = data;
			    	 var settings = {
			                 title: ' ',
			                 description: ' ',
			                 enableAnimations: true,
			                 showLegend: true,
			                 padding: { left: 5, top: 5, right: 5, bottom: 5 },
			                 titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
			                 source: sampleData,
			                 xAxis:
			                     {
			                         dataField: 'dept',
			                         valuesOnTicks: false
			                     },
			                 colorScheme: 'scheme01',
			                 seriesGroups:
			                     [
			                         {
			                             type: 'bubble',
			                             valueAxis:
			                             {
			                                 unitInterval: 10,
			                                 minValue: 0,
			                                 maxValue: 50,
			                                 description: 'Ticket Counter'
			                                 
			                             },
			                             series: [
			                                     { dataField: 'ThisYear', radiusDataField: 1.5, minRadius: 10, maxRadius: 30, displayText: 'This Year' },
			                                     { dataField: 'ThisMonth', radiusDataField: 1.25, minRadius: 10, maxRadius: 30, displayText: 'This Month' },
			                                     { dataField: 'ThisWeek', radiusDataField: 0.85, minRadius: 10, maxRadius: 30, displayText: 'This Week' }
			                                     
			                                     ]
							           }              
			                     ]
			             };
			    	    
			    	// setup the chart
			    	 $('#jqxChart3').jqxChart(settings);
			        $("#FlipValueAxis").jqxCheckBox({ width: 140,  checked: false });
			        $("#FlipCategoryAxis").jqxCheckBox({ width: 140,  checked: false });
			        var refreshChart = function () {
			            $('#jqxChart').jqxChart({ enableAnimations: false });
			            $('#jqxChart').jqxChart('refresh');
			        }
			        // update greyScale values.
			        $("#FlipValueAxis").on('change', function (event) {
			            var groups = $('#jqxChart').jqxChart('seriesGroups');
			            groups[0].valueAxis.flip = event.args.checked;
			            refreshChart();
			        });
			        $("#FlipCategoryAxis").on('change', function (event) {
			            var categoryAxis = $('#jqxChart').jqxChart('categoryAxis');
			            categoryAxis.flip = event.args.checked;
			            refreshChart();
			        });
			    	
			},
			   error: function() {
			        alert("error from jsonArray data ");
			    }
			 });
	
	}else if(selid=='7thStackBarGraph'&&id=='jqxChart7'){
		restoreBoard('prevPie4','nextPie4','jqxChart7');
		 maxDivId5='7thStackBarGraph';
		 var  sampleData =null;
			$.ajax({
			    type : "post",
			   
			    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataTodaysRem.action",
			    type : "json",
			    success : function(data) {
			    	console.log("Data For Todays Pending Compl Status");
			    	console.log(data);
			    	sampleData = data;
			    	var settings = {
			    	    	
			    			title: ' ',
			                description: ' ',
			                enableAnimations: true,
			                showLegend: true,
			                padding: { left: 5, top: 5, right: 5, bottom: 5 },
			                titlePadding: { left: 90, top: 10, right: 0, bottom: 10 },
			                source: sampleData,
			                categoryAxis:
			                    {
			                        text: 'Category Axis',
			                        textRotationAngle: 0,
			                        dataField: 'dept',
			                        showTickMarks: true,
			                        tickMarksInterval: 1,
			                        tickMarksColor: '#888888',
			                        unitInterval: 1,
			                        showGridLines: true,
			                        gridLinesInterval: 1,
			                        gridLinesColor: '#888888',
			                        axisSize: 'auto'
			                    },
			                colorScheme: 'scheme01',
			                seriesGroups:
			                    [
			                        {
			                            type: 'stackedcolumn',
			                            columnsGapPercent: 100,
			                            seriesGapPercent: 5,
			                            valueAxis:
			                            {
			                                minValue: 0,
			                                displayValueAxis: true,
			                                description: 'Ticket Counters',
			                                axisSize: 'auto',
			                                tickMarksColor: '#888888',
			                                gridLinesColor: '#777777'
			                            },
			                            series: 
			                        	[
			                                { dataField: 'SelfDue', displayText: 'Self Due' },
			                                { dataField: 'TeamDue', displayText: 'Team Due' },
			                                { dataField: 'SelfReminder1', displayText: 'Self Reminder 1'  },
			                                { dataField: 'TeamReminder1', displayText: 'Team Reminder 1'  },
			                                { dataField: 'SelfReminder2', displayText: 'Self Reminder 2'  },
			                                { dataField: 'TeamReminder2', displayText: 'Team Reminder 2'  },
			                                { dataField: 'SelfReminder3', displayText: 'Self Reminder 3'  },
			                                { dataField: 'TeamReminder3', displayText: 'Team Reminder 3'  }
			                                
			                               
			                               
			                            ]
			                        }
			                    ]
			            };
			    	// setup the chart
			        $('#jqxChart7').jqxChart(settings);
			        $("#FlipValueAxis").jqxCheckBox({ width: 140,  checked: false });
			        $("#FlipCategoryAxis").jqxCheckBox({ width: 140,  checked: false });
			        var refreshChart = function () {
			            $('#jqxChart').jqxChart({ enableAnimations: false });
			            $('#jqxChart').jqxChart('refresh');
			        }
			        // update greyScale values.
			        $("#FlipValueAxis").on('change', function (event) {
			            var groups = $('#jqxChart').jqxChart('seriesGroups');
			            groups[0].valueAxis.flip = event.args.checked;
			            refreshChart();
			        });
			        $("#FlipCategoryAxis").on('change', function (event) {
			            var categoryAxis = $('#jqxChart').jqxChart('categoryAxis');
			            categoryAxis.flip = event.args.checked;
			            refreshChart();
			        });
			    	
			},
			   error: function() {
			        alert("error from jsonArray data ");
			    }
			 });
	
	}else if(selid=='7thColumn2AxesGraph'&&id=='jqxChart7'){
		restoreBoard('prevPie4','nextPie4','jqxChart7');
		 maxDivId5='7thColumn2AxesGraph';
		 var  sampleData =null;
			$.ajax({
			    type : "post",
			   
			    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataTodaysRem.action",
			    type : "json",
			    success : function(data) {
			    	
			    	console.log(data);
			    	sampleData = data;
			    	var settings = {
			    	    	
			    			title: ' ',
			                description: ' ',
			                enableAnimations: true,
			                showLegend: true,
			                padding: { left: 5, top: 5, right: 5, bottom: 5 },
			                titlePadding: { left: 90, top: 10, right: 0, bottom: 10 },
			                source: sampleData,
			                categoryAxis:
			                    {
			                        text: 'Category Axis',
			                        textRotationAngle: 0,
			                        dataField: 'dept',
			                        showTickMarks: true,
			                        tickMarksInterval: 1,
			                        tickMarksColor: '#888888',
			                        unitInterval: 1,
			                        showGridLines: true,
			                        gridLinesInterval: 1,
			                        gridLinesColor: '#888888',
			                        axisSize: 'auto'
			                    },
			                colorScheme: 'scheme01',
			                seriesGroups:
			                    [
			                        {
			                            type: 'column',
			                            columnsGapPercent: 100,
			                            seriesGapPercent: 5,
			                            valueAxis:
			                            {
			                                minValue: 0,
			                                displayValueAxis: true,
			                                description: 'Ticket Counters',
			                                axisSize: 'auto',
			                                tickMarksColor: '#888888',
			                                gridLinesColor: '#777777'
			                            },
			                            series: 
			                        	[
			                                { dataField: 'SelfDue', displayText: 'Self Due' },
			                                { dataField: 'TeamDue', displayText: 'Team Due' },
			                                { dataField: 'SelfReminder1', displayText: 'Self Reminder 1'  },
			                                { dataField: 'TeamReminder1', displayText: 'Team Reminder 1'  },
			                                { dataField: 'SelReminder2', displayText: 'Self Reminder 2'  },
			                                { dataField: 'TeamReminder2', displayText: 'Team Reminder 2'  },
			                                { dataField: 'SelfReminder3', displayText: 'Self Reminder 3'  },
			                                { dataField: 'TeamReminder3', displayText: 'Team Reminder 3'  }
			                                
			                               
			                               
			                            ]
			                        }
			                    ]
			            };
			    	// setup the chart
			        $('#jqxChart7').jqxChart(settings);
			        $("#FlipValueAxis").jqxCheckBox({ width: 140,  checked: false });
			        $("#FlipCategoryAxis").jqxCheckBox({ width: 140,  checked: false });
			        var refreshChart = function () {
			            $('#jqxChart').jqxChart({ enableAnimations: false });
			            $('#jqxChart').jqxChart('refresh');
			        }
			        // update greyScale values.
			        $("#FlipValueAxis").on('change', function (event) {
			            var groups = $('#jqxChart').jqxChart('seriesGroups');
			            groups[0].valueAxis.flip = event.args.checked;
			            refreshChart();
			        });
			        $("#FlipCategoryAxis").on('change', function (event) {
			            var categoryAxis = $('#jqxChart').jqxChart('categoryAxis');
			            categoryAxis.flip = event.args.checked;
			            refreshChart();
			        });
			    	
			},
			   error: function() {
			        alert("error from jsonArray data ");
			    }
			 });
	
	}else if(selid=='7thPie'&&id=='jqxChart7'){
		 maxDivId5='7thPie';
		$.ajax( {
			type :"post",
			url : "view/Over2Cloud/Compliance/compliance_pages/PieChartForCompDash.action?divName="+selid,
			success : function(ticketdata) 
			{
				$("#"+id).html(ticketdata);
			},
			error : function() {
				alert("error");
			}
		});
	
	}else if(selid=='7thLine'&&id=='jqxChart7'){
		restoreBoard('prevPie4','nextPie4','jqxChart7');
		 maxDivId5='7thLine';
		 var  sampleData =null;
			$.ajax({
	  	  	    type : "post",
	  	      url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataTodaysRem.action",
	  	  	    success : function(statusdata) {
	  	  	    	console.log("Status Data");
	  	  	    	console.log(statusdata);
	  	  	    	sampleData=statusdata;
	  	  	    	
	  	  	    var toolTipCustomFormatFn = function (key,value) {
	              var data=statusdata[value];
	                return                            		 '<DIV style="text-align:left"><b>Department: </b>' + data.dept+
															'<br /><b>Self Due: </b>' +data.SelfDue+
															'<br /><b>Team Due: </b>' +data.TeamDue+
															'<br /><b>Self Reminder 1: </b>' +data.SelfReminder1+
															'<br /><b>Team Reminder 1: </b>' +data.TeamReminder2+
															'<br /><b>Sel Reminder 2: </b>' +data.SelReminder2+
															'<br /><b>Team Reminder 2: </b>' +data.TeamReminder2+
															'<br /><b>Sel Reminder 3: </b>' +data.SelReminder3+
															'<br /><b>Team Reminder 3: </b>' +data.TeamReminder3+
															'</DIV>';
	            };
	          
	  	  	    var settings = {
	  	                title: " ",
	  	                description: " ",
	  	                padding: { left: 5, top: 5, right: 5, bottom: 5 },
	  	                titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
	  	                source: sampleData,
	  	              enableAnimations: true,
	  	                
	  	                categoryAxis:
	  	                    {	
	  	                		textRotationAngle:0,
	  	                        dataField: 'dept',
	  	                       
	  	                        showGridLines: false,
	  	                    },
	  	                colorScheme: 'scheme04',
	  	                seriesGroups:
	  	                    [                    
	  	                        {
	  	                            type: 'line',
	  	                          showLabels: true,
	  	                          
	  	                           /*  formatSettings:
	  	                            {
	  	                                prefix: 'Time ',
	  	                                decimalPlaces: 0,
	  	                                sufix: ' min'
	  	                            } */
	  	                          toolTipFormatFunction: toolTipCustomFormatFn,

	  	                            valueAxis:
	  	                            {
	  	                            	minValue: 0,
		                                displayValueAxis: true,
		                                description: 'Ticket Counter',
		                                axisSize: 'auto',
	  	                            },
	  	                            series: [
	  	                                   
												{ dataField: 'SelfDue', displayText: 'Self Due' },
												{ dataField: 'TeamDue', displayText: 'Team Due' },
												{ dataField: 'SelfReminder1', displayText: 'Self Reminder 1'  },
												{ dataField: 'TeamReminder1', displayText: 'Team Reminder 1'  },
												{ dataField: 'SelReminder2', displayText: 'Self Reminder 2'  },
												{ dataField: 'TeamReminder2', displayText: 'Team Reminder 2'  },
												{ dataField: 'SelfReminder3', displayText: 'Self Reminder 3'  },
												{ dataField: 'TeamReminder3', displayText: 'Team Reminder 3'  }
												  	                        
	  	                                ]
	  	                        }
	  	                    ]
	  	            };
	  	            
	  	            $('#jqxChart7').jqxChart(settings);
	  	  	    	
	  	  	},
	  	  	   error: function() {
	  	              alert("error");
	  	          }
	  	  	 });	
	
	}else if(selid=='7thBubbleGraph'&&id=='jqxChart7'){
		restoreBoard('prevPie4','nextPie4','jqxChart7');
		 maxDivId5='7thBubbleGraph';
		 var  sampleData =null;
			$.ajax({
			    type : "post",
			   
			    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataTodaysRem.action",
			    type : "json",
			    success : function(data) {
			    	
			    	console.log(data);
			    	sampleData = data;
			    	 var settings = {
			                 title: ' ',
			                 description: '  ',
			                 enableAnimations: true,
			                 showLegend: true,
			                 padding: { left: 5, top: 5, right: 5, bottom: 5 },
			                 titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
			                 source: sampleData,
			                 xAxis:
			                     {
			                         dataField: 'dept',
			                         valuesOnTicks: false
			                     },
			                 colorScheme: 'scheme01',
			                 seriesGroups:
			                     [
			                         {
			                             type: 'bubble',
			                             valueAxis:
			                             {
			                                 unitInterval: 10,
			                                 minValue: 0,
			                                 maxValue: 50,
			                                 description: 'Ticket Counter'
			                                 
			                             },
			                             series: [
			                                     { dataField: 'SelfDue', radiusDataField: 1.5, minRadius: 10, maxRadius: 30, displayText: 'Self Due' },
			                                     { dataField: 'TeamDue', radiusDataField: 1.25, minRadius: 10, maxRadius: 30, displayText: 'Team Due' },
			                                     { dataField: 'SelfReminder1', radiusDataField: 0.85, minRadius: 10, maxRadius: 30, displayText: 'Self Reminder 1' },
			                                     { dataField: 'TeamReminder1', radiusDataField: 0.95, minRadius: 10, maxRadius: 30, displayText: 'Team Reminder 1'},
			                                     { dataField: 'SelfReminder2', radiusDataField: 0.95, minRadius: 10, maxRadius: 30, displayText: 'Self Reminder 2'},
			                                     { dataField: 'TeamReminder2', radiusDataField: 0.95, minRadius: 10, maxRadius: 30, displayText: 'Team Reminder 2'},
			                                     { dataField: 'SelfReminder3', radiusDataField: 0.95, minRadius: 10, maxRadius: 30, displayText: 'Self Reminder 3'},
			                                     { dataField: 'TeamReminder3', radiusDataField: 0.95, minRadius: 10, maxRadius: 30, displayText: 'Team Reminder 3'}
			                                     ]
			                          }
			                     ]  
		                         
			             };
			    	    
			    	// setup the chart
			    	 $('#jqxChart7').jqxChart(settings);
			        $("#FlipValueAxis").jqxCheckBox({ width: 140,  checked: false });
			        $("#FlipCategoryAxis").jqxCheckBox({ width: 140,  checked: false });
			        var refreshChart = function () {
			            $('#jqxChart').jqxChart({ enableAnimations: false });
			            $('#jqxChart').jqxChart('refresh');
			        }
			        // update greyScale values.
			        $("#FlipValueAxis").on('change', function (event) {
			            var groups = $('#jqxChart').jqxChart('seriesGroups');
			            groups[0].valueAxis.flip = event.args.checked;
			            refreshChart();
			        });
			        $("#FlipCategoryAxis").on('change', function (event) {
			            var categoryAxis = $('#jqxChart').jqxChart('categoryAxis');
			            categoryAxis.flip = event.args.checked;
			            refreshChart();
			        });
			    	
			},
			   error: function() {
			        alert("error from jsonArray data ");
			    }
			 });
	
	}else if(selid=='4thStackBarGraph'&&id=='jqxChart4'){
		restoreBoard('prevPie3','nextPie3','jqxChart4');
		 maxDivId4='4thStackBarGraph';
		 var  sampleData =null;


			$.ajax({
			    type : "post",
			   
			    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataPrevMonthCompl.action",
			    type : "json",
			    success : function(data) {
			    	
			    	console.log(data);
			    	sampleData = data;
			    	var settings = {
			    	    	
			    			title: '              ',
			                description: '        ',
			                enableAnimations: true,
			                showLegend: true,
			                padding: { left: 5, top: 5, right: 5, bottom: 5 },
			                titlePadding: { left: 90, top: 10, right: 0, bottom: 10 },
			                source: sampleData,
			                categoryAxis:
			                    {
			                        text: 'Category Axis',
			                        textRotationAngle: 0,
			                        dataField: 'dept',
			                        showTickMarks: true,
			                        tickMarksInterval: 1,
			                        tickMarksColor: '#888888',
			                        unitInterval: 1,
			                        showGridLines: true,
			                        gridLinesInterval: 1,
			                        gridLinesColor: '#888888',
			                        axisSize: 'auto'
			                    },
			                colorScheme: 'scheme06',
			                seriesGroups:
			                    [
			                        {
			                            type: 'stackedcolumn',
			                            columnsGapPercent: 100,
			                            seriesGapPercent: 5,
			                            valueAxis:
			                            {
			                                minValue: 0,
			                                displayValueAxis: true,
			                                description: 'Ticket Counters',
			                                axisSize: 'auto',
			                                tickMarksColor: '#888888',
			                                gridLinesColor: '#777777'
			                            },
			                            series: 
			                        	[
			                                 { dataField: 'Pending', displayText: 'Pending' , color:'#FF0033' },
			                                { dataField: 'Done', displayText: 'Resolved', color:'#009933' }
			                            ]
			                        }
			                    ]
			            };
			    	    
			    	// setup the chart
			        $('#jqxChart4').jqxChart(settings);
			        $("#FlipValueAxis").jqxCheckBox({ width: 140,  checked: false });
			        $("#FlipCategoryAxis").jqxCheckBox({ width: 140,  checked: false });
			        var refreshChart = function () {
			            $('#jqxChart').jqxChart({ enableAnimations: false });
			            $('#jqxChart').jqxChart('refresh');
			        }
			        // update greyScale values.
			        $("#FlipValueAxis").on('change', function (event) {
			            var groups = $('#jqxChart').jqxChart('seriesGroups');
			            groups[0].valueAxis.flip = event.args.checked;
			            refreshChart();
			        });
			        $("#FlipCategoryAxis").on('change', function (event) {
			            var categoryAxis = $('#jqxChart').jqxChart('categoryAxis');
			            categoryAxis.flip = event.args.checked;
			            refreshChart();
			        });
			    	
			},
			   error: function() {
			        alert("error from jsonArray data ");
			    }
			 });
	
	}else if(selid=='4thColumn2AxesGraph'&&id=='jqxChart4'){
		restoreBoard('prevPie3','nextPie3','jqxChart4');
		 maxDivId4='4thColumn2AxesGraph';
		 var  sampleData =null;


			$.ajax({
			    type : "post",
			   
			    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataPrevMonthCompl.action",
			    type : "json",
			    success : function(data) {
			    	
			    	console.log(data);
			    	sampleData = data;
			    	var settings = {
			    	    	
			    			title: '              ',
			                description: '        ',
			                enableAnimations: true,
			                showLegend: true,
			                padding: { left: 5, top: 5, right: 5, bottom: 5 },
			                titlePadding: { left: 90, top: 10, right: 0, bottom: 10 },
			                source: sampleData,
			                categoryAxis:
			                    {
			                        text: 'Category Axis',
			                        textRotationAngle: 0,
			                        dataField: 'dept',
			                        showTickMarks: true,
			                        tickMarksInterval: 1,
			                        tickMarksColor: '#888888',
			                        unitInterval: 1,
			                        showGridLines: true,
			                        gridLinesInterval: 1,
			                        gridLinesColor: '#888888',
			                        axisSize: 'auto'
			                    },
			                colorScheme: 'scheme06',
			                seriesGroups:
			                    [
			                        {
			                            type: 'column',
			                            columnsGapPercent: 100,
			                            seriesGapPercent: 5,
			                            valueAxis:
			                            {
			                                minValue: 0,
			                                displayValueAxis: true,
			                                description: 'Ticket Counters',
			                                axisSize: 'auto',
			                                tickMarksColor: '#888888',
			                                gridLinesColor: '#777777'
			                            },
			                            series: 
			                        	[
			                                { dataField: 'Pending', displayText: 'Pending' , color:'#FF0033' },
			                                { dataField: 'Done', displayText: 'Resolved', color:'#009933' }
			                            ]
			                        }
			                    ]
			            };
			    	    
			    	// setup the chart
			        $('#jqxChart4').jqxChart(settings);
			        $("#FlipValueAxis").jqxCheckBox({ width: 140,  checked: false });
			        $("#FlipCategoryAxis").jqxCheckBox({ width: 140,  checked: false });
			        var refreshChart = function () {
			            $('#jqxChart').jqxChart({ enableAnimations: false });
			            $('#jqxChart').jqxChart('refresh');
			        }
			        // update greyScale values.
			        $("#FlipValueAxis").on('change', function (event) {
			            var groups = $('#jqxChart').jqxChart('seriesGroups');
			            groups[0].valueAxis.flip = event.args.checked;
			            refreshChart();
			        });
			        $("#FlipCategoryAxis").on('change', function (event) {
			            var categoryAxis = $('#jqxChart').jqxChart('categoryAxis');
			            categoryAxis.flip = event.args.checked;
			            refreshChart();
			        });
			    	
			},
			   error: function() {
			        alert("error from jsonArray data ");
			    }
			 });
	
	}else if(selid=='4thPie'&&id=='jqxChart4'){
		 maxDivId4='4thPie';
		$.ajax( {
			type :"post",
			url : "view/Over2Cloud/Compliance/compliance_pages/PieChartForCompDash.action?divName="+selid,
			success : function(ticketdata) 
			{
				$("#"+id).html(ticketdata);
			},
			error : function() {
				alert("error");
			}
		});
	
	}else if(selid=='4thLine'&&id=='jqxChart4'){
		restoreBoard('prevPie3','nextPie3','jqxChart4');
		 maxDivId4='4thLine';
		 var  sampleData =null;
			$.ajax({
	  	  	    type : "post",
	  	  	    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataPrevMonthCompl.action",
	  	  	    success : function(statusdata) {
	  	  	    	sampleData=statusdata;
	  	  	    	
	  	  	    var toolTipCustomFormatFn = function (key,value) {
	              var data=statusdata[value];
	                return                            		 '<DIV style="text-align:left"><b>Department: </b>' + data.dept+
															'<br /><b>Pending: </b>' +data.Pending+
															'<br /><b>Done: </b>' +data.Done+
															'</DIV>';
	            };

	  	  	    var settings = {
	  	                title: " ",
	  	                description: " ",
	  	                padding: { left: 5, top: 5, right: 5, bottom: 5 },
	  	                titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
	  	                source: sampleData,
	  	              enableAnimations: true,
	  	                
	  	                categoryAxis:
	  	                    {	
	  	                		textRotationAngle:0,
	  	                        dataField: 'dept',
	  	                       
	  	                        showGridLines: false,
	  	                    },
	  	                colorScheme: 'scheme04',
	  	                seriesGroups:
	  	                    [                    
	  	                        {
	  	                            type: 'line',
	  	                          showLabels: true,
	  	                          
	  	                           /*  formatSettings:
	  	                            {
	  	                                prefix: 'Time ',
	  	                                decimalPlaces: 0,
	  	                                sufix: ' min'
	  	                            } */
	  	                          toolTipFormatFunction: toolTipCustomFormatFn,

	  	                            valueAxis:
	  	                            {
	  	                            	minValue: 0,
		                                displayValueAxis: true,
		                                description: 'Ticket Counter',
		                                axisSize: 'auto',
	  	                            },
	  	                            series: [
	  	                                   
	  	                                    { dataField: 'Pending', displayText: 'Pending'  },
			                                { dataField: 'Done', displayText: 'Resolved' }
												  	                        
	  	                                ]
	  	                        }
	  	                    ]
	  	            };
	  	            
	  	            $('#jqxChart4').jqxChart(settings);
	  	  	    	
	  	  	},
	  	  	   error: function() {
	  	              alert("error");
	  	          }
	  	  	 });	

	
	}else if(selid=='4thBubbleGraph'&&id=='jqxChart4'){
		restoreBoard('prevPie3','nextPie3','jqxChart4');
		 maxDivId4='4thBubbleGraph';
			var  sampleData =null;
			$.ajax({
			    type : "post",
			   
			    url : "view/Over2Cloud/Compliance/compliance_pages/jsonChartDataPrevMonthCompl.action",
			    type : "json",
			    success : function(data) {
			    	
			    	console.log(data);
			    	sampleData = data;
			    	 var settings = {
			                 title: '  ',
			                 description: '  ',
			                 enableAnimations: true,
			                 showLegend: true,
			                 padding: { left: 5, top: 5, right: 5, bottom: 5 },
			                 titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
			                 source: sampleData,
			                 xAxis:
			                     {
			                         dataField: 'dept',
			                         valuesOnTicks: false
			                     },
			                 colorScheme: 'scheme06',
			                 seriesGroups:
			                     [
			                         {
			                             type: 'bubble',
			                             valueAxis:
			                             {
			                                 unitInterval: 10,
			                                 minValue: 0,
			                                 maxValue: 50,
			                                 description: 'Ticket Counter'
			                                 
			                             },
			                             series: [
			                                     { dataField: 'Pending', radiusDataField: 1.5, minRadius: 10, maxRadius: 30, displayText: 'Pending', color:'#FF0033'  },
			                                     { dataField: 'Done', radiusDataField: 0.95, minRadius: 10, maxRadius: 30, displayText: 'Resolved',color:'#009933' }
			                                     ]
			                          }
			                     ]
			             };
			    	    
			    	// setup the chart
			    	 $('#jqxChart4').jqxChart(settings);
			        $("#FlipValueAxis").jqxCheckBox({ width: 140,  checked: false });
			        $("#FlipCategoryAxis").jqxCheckBox({ width: 140,  checked: false });
			        var refreshChart = function () {
			            $('#jqxChart').jqxChart({ enableAnimations: false });
			            $('#jqxChart').jqxChart('refresh');
			        }
			        // update greyScale values.
			        $("#FlipValueAxis").on('change', function (event) {
			            var groups = $('#jqxChart').jqxChart('seriesGroups');
			            groups[0].valueAxis.flip = event.args.checked;
			            refreshChart();
			        });
			        $("#FlipCategoryAxis").on('change', function (event) {
			            var categoryAxis = $('#jqxChart').jqxChart('categoryAxis');
			            categoryAxis.flip = event.args.checked;
			            refreshChart();
			        });
			    	
			},
			   error: function() {
			        alert("error from jsonArray data ");
			    }
			 });
	
	}
	
}//end here 


//Maximise for 1st dashbaord
function beforeMaximise(divBlock,height,width,blockHeading){
	if(maxDivId1=='1stTable'){
		maximizeDiv4Analytics('1stTableDta',270,width,blockHeading);
	}else if(maxDivId1=='1stBarGraph'){
			maximizeDiv4Analytics('1stBarGraph',height,width,blockHeading);
		
	}else if(maxDivId1=='1stColumn2AxesBar'){
			maximizeDiv4Analytics('1stColumn2AxesBar',height,width,blockHeading);
		
	}else if(maxDivId1=='1stLine'){
		maximizeDiv4Analytics('1stLine',height,width,blockHeading);
	
    }else if(maxDivId1=='1stBubbleGraph'){
		maximizeDiv4Analytics('1stBubbleGraph',height,width,blockHeading);
	
    }else if(maxDivId1=='1stPieGraph'){
		maximizeDiv4Analytics('1stPieGraph',height,width,blockHeading);
	
    }
	
	else {
			maximizeDiv4Analytics(divBlock,height,width,blockHeading);
		}
	
}

//Maximise for 2nd dashbaord
function beforeMaximise1(divBlock,height,width,blockHeading){
	
	if(maxDivId2=='2ndDataDiv'){
		maximizeDiv4Analytics('2ndDataBlockDiv',270,width,blockHeading);
	}else if(maxDivId2=='2ndStackBarGraph'){
			maximizeDiv4Analytics('2ndStackBarGraph',height,width,blockHeading);
		
	}else if(maxDivId2=='2ndColumn2AxesGraph'){
			maximizeDiv4Analytics('2ndColumn2AxesGraph',height,width,blockHeading);
		
	}else if(maxDivId2=='2ndBubbleGraph'){
		maximizeDiv4Analytics('2ndBubbleGraph',height,width,blockHeading);
	
    }else if(maxDivId2=='2ndPieGraph'){
		maximizeDiv4Analytics('2ndPieGraph',height,width,blockHeading);
	
    }else if(maxDivId2=='2ndLine'){
		maximizeDiv4Analytics('2ndLine',height,width,blockHeading);
	
    }
	
	else {
			maximizeDiv4Analytics(divBlock,height,width,blockHeading);
		}
	
}

//Maximise for 3rdd dashbaord
function beforeMaximise2(divBlock,height,width,blockHeading){
	
	if(maxDivId3=='3rdDataDiv'){
		maximizeDiv4Analytics('3rdDataBlockDiv',270,width,blockHeading);
	}else if(maxDivId3=='3rdStackBarGraph'){
			maximizeDiv4Analytics('3rdStackBarGraph',height,width,blockHeading);
		
	}else if(maxDivId3=='3rdColumn2AxesGraph'){
			maximizeDiv4Analytics('3rdColumn2AxesGraph',height,width,blockHeading);
		
	}else if(maxDivId3=='3rdBubbleGraph'){
		maximizeDiv4Analytics('3rdBubbleGraph',height,width,blockHeading);
	
    }else if(maxDivId3=='3rdPieGraph'){
		maximizeDiv4Analytics('3rdPieGraph',height,width,blockHeading);
	
    }else if(maxDivId3=='3rdLine'){
		maximizeDiv4Analytics('3rdLine',height,width,blockHeading);
	
    }
	
	else {
			maximizeDiv4Analytics(divBlock,height,width,blockHeading);
		}
	
}

//Maximise for 4rth dashbaord
function beforeMaximise3(divBlock,height,width,blockHeading){
	
	if(maxDivId4=='4thDataDiv'){
		maximizeDiv4Analytics(divBlock,height,width,blockHeading);
	}else if(maxDivId4=='4thStackBarGraph'){
			maximizeDiv4Analytics('4thStackBarGraph',400,width,blockHeading);
		
	}else if(maxDivId4=='4thColumn2AxesGraph'){
			maximizeDiv4Analytics('4thColumn2AxesGraph',400,width,blockHeading);
		
	}else if(maxDivId4=='4thBubbleGraph'){
		maximizeDiv4Analytics('4thBubbleGraph',400,width,blockHeading);
	
    }else if(maxDivId4=='4thPieGraph'){
		maximizeDiv4Analytics('4thPieGraph',400,width,blockHeading);
	
    }else if(maxDivId4=='4thLine'){
		maximizeDiv4Analytics('4thLine',400,width,blockHeading);
	
    }
	
	else {
			maximizeDiv4Analytics(divBlock,height,width,blockHeading);
		}
	
}

//Maximise for 7th dashbaord
function beforeMaximise4(divBlock,height,width,blockHeading){
	
	if(maxDivId5=='7thDataDiv'){
		maximizeDiv4Analytics('7thDataBlockDiv',270,width,blockHeading);
	}else if(maxDivId5=='7thStackBarGraph'){
			maximizeDiv4Analytics('7thStackBarGraph',height,width,blockHeading);
		
	}else if(maxDivId5=='7thColumn2AxesGraph'){
			maximizeDiv4Analytics('7thColumn2AxesGraph',height,width,blockHeading);
		
	}else if(maxDivId5=='7thBubbleGraph'){
		maximizeDiv4Analytics('7thBubbleGraph',height,width,blockHeading);
	
    }else if(maxDivId5=='7thPieGraph'){
		maximizeDiv4Analytics('7thPieGraph',height,width,blockHeading);
	
    }else if(maxDivId5=='7thLine'){
		maximizeDiv4Analytics('7thLine',height,width,blockHeading);
	
    }
	
	else {
			maximizeDiv4Analytics(divBlock,height,width,blockHeading);
		}
	
}


function maximizeDiv4Analytics(divBlock,height,width,blockHeading)
{
	$("#maxmizeViewDialog").dialog({title:blockHeading,height:height,width:width,dialogClass:'transparent'});
	$("#maxmizeViewDialog").dialog('open');
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compliance_pages/getDashboardData.action?maximizeDivBlock="+divBlock,
	    success : function(data) 
	    {
			$("#maximizeDataDiv").html(data);
	 },
	   error: function() {
	        alert("error in getting dash 2 data");
	    }
	 });
}



