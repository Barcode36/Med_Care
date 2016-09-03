var serviceId1='';
var serviceName1='';
var corporateID='';
var corporateID1='';
var corporateName='';
var corporateName2='';
var speciality='';
var speciality1='';
var locationNameForService1='';
var locationNameForService='';
var accountManagerName1='';
var corporateName1='';
var locationName1='';
var recordStatus1='';
var dashboard1='';
var feelColor1='';
var dataFor1='';

var serviceId2='';
var serviceName2='';
var locationNameForService2='';
var accountManagerName2='';
var corporateName2='';
var locationName2='';
var recordStatus2='';
var dashboard2='';
var feelColor2='';
var dataFor2='';

 

 



  
function showTimeDiv(status)
{
	 
	 if(status==true)
	 {
	  	$(".statusTime").show();
	  	$("#fromTime").val("");
	  	$("#toTime").val("");
	  	
	 }
	 else if(status==false)
	 {
	 	$(".statusTime").hide();
	 	$("#fromTime").val("");
	  	$("#toTime").val("");
	  	getDataForAllBoard();
	 }
	  
}
 
 

function getDataForAllBoard()
{
	if(dataFor1=='Graph')
	{
 	showStatusStackedBar('statusChart', dashboard1, '', 'All', 'All', '#09A1A4');
 	 
	}
	else if(dataFor1=='Table')
	{
	showData('statusChart','',dashboard1);
	 
	}
	if(dataFor2=='Graph')
	{
 	 
 	showProductivityStackedBar('productivityChart', dashboard2, '', 'All', 'All', '#09A1A4');
	}
	else if(dataFor2=='Table')
	{
	 
	showData('productivityChart','',dashboard2);
	}
	
   
}

function getDataForStatusCounter()
{

	if(dataFor1=='Graph')
 	showStatusStackedBar('statusChart', dashboard1, '', 'All', 'All', '#09A1A4');
	else if(dataFor1=='Table')
	showData('statusChart','',dashboard1);
	
  
}
function getDataForProductivityCounter()
{

	if(dataFor2=='Graph')
	showProductivityStackedBar('productivityChart', dashboard2, '', 'All', 'All', '#09A1A4');
	else if(dataFor2=='Table')
	showData('productivityChart','',dashboard2);
	
  
}

//Service to DOC
function locationWiseChartForAllServicesDOC(event)
{
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	serviceId1=dataContx.Id;
	serviceName1=dataContx.serviceDOC;
	showStatusStackedBar('statusChart', 'locationForSpeciality', '', recordStatus1, recordStatus1, graph.fillColors);
}

function locationWiseChartForAllSpeciality(event)
{
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	 
	locationNameForService1=dataContx.Id;
	showStatusStackedBar('statusChart', 'locationForSpecialitys', '', recordStatus1, recordStatus1, graph.fillColors);
}

function locationWiseChartForAllSpecialitytoDOC(event)
{
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	//serviceName1=dataContx.serviceDOC;
	locationNameForService1=dataContx.Id;
	speciality=dataContx.Id;
	//alert(serviceName1);
	showStatusStackedBar('statusChart', 'pecialitysToDoc', '', recordStatus1, recordStatus1, graph.fillColors);
}

function corporateWiseService(event)
{
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	corporateID=dataContx.Id;
	corporateName=dataContx.corporate;
	showStatusStackedBar('statusChart', 'corporateService', '', recordStatus1, recordStatus1, graph.fillColors);
}
function corporateWiseLocateion(event)
{
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	serviceId1=dataContx.Id;
	serviceName1=dataContx.corporateService;
	showStatusStackedBar('statusChart', 'corporateLocation', '', recordStatus1, recordStatus1, graph.fillColors);
}
function corporateWiseServiceSpeciality(event)
{
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	locationNameForService1=dataContx.Id;
	showStatusStackedBar('statusChart', 'corporateServiceSpeciality', '', recordStatus1, recordStatus1, graph.fillColors);
}
function corporateWiseServiceDoctor(event)
{
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	speciality=dataContx.Id;
	showStatusStackedBar('statusChart', 'corporateServiceDoctor', '', recordStatus1, recordStatus1, graph.fillColors);
}
//speciality
function locationWiseChartForAllServices(event)
{
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	serviceId1=dataContx.Id;
	serviceName1=dataContx.service;
	showStatusStackedBar('statusChart', 'locationForService', '', recordStatus1, recordStatus1, graph.fillColors);
}
function serviceManagerWiseChartForAllLocation(event)
{
 	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	locationNameForService1=dataContx.Id;
	showStatusStackedBar('statusChart', 'serviceManagerForService', '', recordStatus1, recordStatus1, graph.fillColors);
}
function serviceWiseChartForLocation(event)
{
 	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	locationNameForService1=dataContx.Id;
	showStatusStackedBar('statusChart', 'serviceForLocation', '', recordStatus1, recordStatus1, graph.fillColors);
}


function corporateWiseChartForAccountManager(event)
{
 	 var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
 	accountManagerName1=dataContx.Id;
 	showStatusStackedBar('statusChart', 'corporateForAcntMng', '', recordStatus1, recordStatus1, graph.fillColors);
}
function serviceWiseChartForCorporate(event)
{
 	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	corporateName1=dataContx.Id;
 	 showStatusStackedBar('statusChart', 'serviceForAcntMng', '', recordStatus1, recordStatus1, graph.fillColors);
}
function locationWiseChartForService(event)
{
 	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	serviceId1=dataContx.Id;
	serviceName1=dataContx.serviceForAcntMng;
	showStatusStackedBar('statusChart', 'locationForAcntMng', '', recordStatus1, recordStatus1, graph.fillColors );
}
function serviceManagerWiseChartForLocation(event)
{
 	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	locationNameForService1=dataContx.Id;
	showStatusStackedBar('statusChart', 'serviceManagerForAcntMng', '', recordStatus1, recordStatus1, graph.fillColors);
}

function locationWiseProductivityChartForAllServices(event)
{
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	serviceId2=dataContx.Id;
	serviceName2=dataContx.service;
	showProductivityStackedBar('productivityChart', 'locationForServiceProductivity', '', recordStatus2, recordStatus2, graph.fillColors);
}
function locationWiseChartForAllServicesProductivity(event)
{
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	serviceId2=dataContx.Id;
	serviceName2=dataContx.serviceDOCProductivity;
	showProductivityStackedBar('productivityChart', 'locationForSpecialityProductivity', '', recordStatus2, recordStatus2, graph.fillColors);
}
function locationWiseAllSpeciality(event)
{
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	locationNameForService2=dataContx.Id;
	showProductivityStackedBar('productivityChart', 'specialitysProductivity', '', recordStatus2, recordStatus2, graph.fillColors);
}
function specialityWiseProductivityChartForAllDoctor(event)
{
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	//serviceName2=dataContx.serviceDOCProductivity;
	locationNameForService2=dataContx.Id;
	speciality1=dataContx.specialitysProductivity;
	alert(serviceName2);
	showProductivityStackedBar('productivityChart', 'specialitysToDocProductivity', '', recordStatus2, recordStatus2, graph.fillColors);
}

function serviceManagerWiseProductivityChartForAllLocation(event)
{
 	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	locationNameForService2=dataContx.Id;
	showProductivityStackedBar('productivityChart', 'serviceManagerForServiceProductivity', '', recordStatus2, recordStatus2, graph.fillColors);
}
function serviceWiseProductivityChartForLocation(event)
{
 	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	locationNameForService2=dataContx.Id;
	showProductivityStackedBar('productivityChart', 'serviceForLocationProductivity', '', recordStatus2, recordStatus2, graph.fillColors);
}


function corporateWiseProductivityChartForAccountManager(event)
{
 	 var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
 	accountManagerName2=dataContx.Id;
 	showProductivityStackedBar('productivityChart', 'corporateForAcntMngProductivity', '', recordStatus2, recordStatus2, graph.fillColors);
}
function serviceWiseProductivityChartForCorporate(event)
{
 	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	corporateName2=dataContx.Id;
	showProductivityStackedBar('productivityChart', 'serviceForAcntMngProductivity', '', recordStatus2, recordStatus2, graph.fillColors);
}
function locationWiseProductivityChartForService(event)
{
 	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	serviceId2=dataContx.Id;
	serviceName2=dataContx.serviceForAcntMng;
	showProductivityStackedBar('productivityChart', 'locationForAcntMngProductivity', '', recordStatus2, recordStatus2, graph.fillColors );
}
function serviceManagerWiseProductivityChartForLocation(event)
{
 	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	locationNameForService2=dataContx.Id;
	showProductivityStackedBar('productivityChart', 'serviceManagerForAcntMngProductivity', '', recordStatus2, recordStatus2, graph.fillColors);
}


function corporateWiseServiceProductivity(event)
{
	
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	corporateID1=dataContx.Id;
	corporateName2=dataContx.corporateProductivity;
	showProductivityStackedBar('productivityChart', 'corporateServiceProductivity', '', recordStatus2, recordStatus2, graph.fillColors);
}

function corporateWiseLocateionProductivity(event)
{
	
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	serviceId2=dataContx.Id;
	serviceName2=dataContx.corporateServiceProductivity;
	showProductivityStackedBar('productivityChart', 'corporateLocationProductivity', '', recordStatus2, recordStatus2, graph.fillColors);
}
function corporateWiseServiceSpecialityProductivity(event)
{
	
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	locationNameForService2=dataContx.Id;
	showProductivityStackedBar('productivityChart', 'corporateServiceSpecialityProductivity', '', recordStatus2, recordStatus2, graph.fillColors);
}
function corporateWiseProductivityChartForAllDoctor(event)
{
	
	var indexFor = event.index;
	var graph = event.graph;
	var data = graph.data;
	var dataContx = data[indexFor].dataContext;
	speciality1=dataContx.Id;
	showProductivityStackedBar('productivityChart', 'corporateSpecialityDoctorProductivity', '', recordStatus2, recordStatus2, graph.fillColors);
}

function showStatusStackedBar(divId, filterFlag, filterDeptId, status, title, color)
{//alert(filterFlag);
 	recordStatus1=status;
	feelColor1=color;
	var statusFor=$("#statusFor").val();
 	var total = 0;
   	var url1='';
   	$(".allServicePanel").hide();
	$(".allLocationPanelForService").hide();
	$(".allServiceManagerPanelForService").hide();
	$(".LocationPanel").hide();
	$(".ServicePanelForLocation").hide();
	$(".accountManagerPanel").hide();
	$(".corporatePanelForAcntMng").hide();
	$(".servicePanelForAcntMng").hide();
	$(".locationPanelForAcntMng").hide();
	$(".serviceManagerPanelForAcntMng").hide();
	$(".serviceManagerPanel").hide();
	$(".serviceSpecialityPanel").hide();
	$(".locationSpecialityPanel").hide();
	$(".specialityPanel").hide();
	$(".specialitytoDocPanel").hide();
	$(".corporatePanel").hide();
	$(".corporateServicePanel").hide();
	$(".corporateLocationPanel").hide();
	$(".corporateServiceSpecialityPanel").hide();
	$(".corporateServiceSpecialityDoctorPanel").hide();
	
	//alert("Check "+filterFlag);
	if(filterFlag=='service')
	{
	$("#backbttn1").attr('onclick','').css('display','none');
	$(".allServicePanel").show();
	dashboard1=filterFlag;
	dataFor1='Graph';
	title=title+" Tickets:";
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();

	}
	else if(filterFlag=='locationForService')
	{
	 	$("#backbttn1").attr('onclick','showStatusStackedBar("statusChart","service" ,"" ,recordStatus1 , recordStatus1, feelColor1)').css('display','block');
	 	$(".allLocationPanelForService").show();
	dashboard1=filterFlag;
	dataFor1='Graph';
	title=title+" Tickets For "+serviceName1+" Service:";
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+serviceId1;
	}
	else if(filterFlag=='serviceManagerForService')
	{
	$("#backbttn1").attr('onclick','showStatusStackedBar("statusChart","locationForService" ,"" ,recordStatus1 , recordStatus1, feelColor1)').css('display','block');
	  	$(".allServiceManagerPanelForService").show();
	dashboard1=filterFlag;
	dataFor1='Graph';
	title=title+" Tickets For "+serviceName1+" Service & "+locationNameForService1+" Location:";
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+serviceId1+"&locationName="+locationNameForService1;

	}
	else if(filterFlag=='location')
	{
	$("#backbttn1").attr('onclick','').css('display','none'); 
	$(".LocationPanel").show();
	dashboard1=filterFlag;
	dataFor1='Graph';
	title=title+" Tickets:";
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();

	}
	else if(filterFlag=='serviceForLocation')
	{
	$("#backbttn1").attr('onclick','showStatusStackedBar("statusChart","location" ,"" ,recordStatus1 , recordStatus1, feelColor1)').css('display','block');
	  	$(".ServicePanelForLocation").show();
	dashboard1=filterFlag;
	dataFor1='Graph';
	title=title+" Tickets For "+locationNameForService1+" Location:";
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&locationName="+locationNameForService1;

	}
	else if(filterFlag=='accountManager')
	{
	$("#backbttn1").attr('onclick','').css('display','none'); 
	$(".accountManagerPanel").show();
	dashboard1=filterFlag;
	dataFor1='Graph';
	title=title+" Tickets:";
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();

	}
	else if(filterFlag=='corporateForAcntMng')
	{
	$("#backbttn1").attr('onclick','showStatusStackedBar("statusChart","accountManager" ,"" ,recordStatus1 , recordStatus1, feelColor1)').css('display','block');
	 	$(".corporatePanelForAcntMng").show();
	dashboard1=filterFlag;
	dataFor1='Graph';
	title=title+" Tickets For "+accountManagerName1+" Account Manager:";
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&accountManagerName="+accountManagerName1;

	}
	else if(filterFlag=='serviceForAcntMng')
	{
	$("#backbttn1").attr('onclick','showStatusStackedBar("statusChart","corporateForAcntMng" ,"" ,recordStatus1 , recordStatus1, feelColor1)').css('display','block');
	 	$(".servicePanelForAcntMng").show();
	dashboard1=filterFlag;
	dataFor1='Graph';
	title=title+" Tickets For "+accountManagerName1+" Account Manager & "+corporateName1+" Corporate:";
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&accountManagerName="+accountManagerName1+"&corporateName="+corporateName1;

	}
	else if(filterFlag=='locationForAcntMng')
	{
	$("#backbttn1").attr('onclick','showStatusStackedBar("statusChart","serviceForAcntMng" ,"" ,recordStatus1 , recordStatus1, feelColor1 )').css('display','block');
	 	$(".locationPanelForAcntMng").show();
	dashboard1=filterFlag;
	dataFor1='Graph';
	title=title+" Tickets For "+accountManagerName1+" Account Manager & "+corporateName1+" Corporate & "+serviceName1+" Service:"
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&accountManagerName="+accountManagerName1+"&corporateName="+corporateName1+"&serviceName="+serviceId1;

	}
	else if(filterFlag=='serviceManagerForAcntMng')
	{
	$("#backbttn1").attr('onclick','showStatusStackedBar("statusChart","locationForAcntMng" ,"" ,recordStatus1 , recordStatus1, feelColor1 )').css('display','block');
	 	$(".serviceManagerPanelForAcntMng").show();
	dashboard1=filterFlag;
	dataFor1='Graph';
	title=title+" Tickets For "+accountManagerName1+" Account Manager & "+corporateName1+" Corporate & "+serviceName1+" Service & "+locationNameForService1+" Location:";
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&accountManagerName="+accountManagerName1+"&corporateName="+corporateName1+"&serviceName="+serviceId1+"&locationName="+locationNameForService1;

	}
 
	else if(filterFlag=='serviceManager')
	{
	$("#backbttn1").attr('onclick','').css('display','none'); 
	$(".serviceManagerPanel").show();
	dashboard1=filterFlag;
	dataFor1='Graph';
	title=title+" Tickets:";
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();

	}
	//Speciality Service Wise
	else if(filterFlag=='serviceDOC')
	{
	$("#backbttn1").attr('onclick','').css('display','none');
	$(".serviceSpecialityPanel").show();
	dashboard1=filterFlag;
	dataFor1='Graph';
	title=title+" Tickets:";
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();

	}
	else if(filterFlag=='locationForSpeciality')
	{
	if(serviceName1=='Radiology' || serviceName1=='Laboratory' ||serviceName1=='Diagnostics' || serviceName1=='New Information Request')
	{ 
	$("#backbttn1").attr('onclick','').css('display','none');
	$(".locationSpecialityPanel").show();
	dashboard1=filterFlag;
	dataFor1='Graph';
	title=title+" Tickets For "+serviceName1+" Service:";
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+serviceId1;
	}
	else
	{
	$("#backbttn1").attr('onclick','showStatusStackedBar("statusChart","service" ,"" ,recordStatus1 , recordStatus1, feelColor1)').css('display','block');
	 	$(".locationSpecialityPanel").show();
	dashboard1=filterFlag;
	dataFor1='Graph';
	title=title+" Tickets For "+serviceName1+" Service:";
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+serviceId1;
	}
	 	
	}
	else if(filterFlag=='locationForSpecialitys')
	{
	if(serviceName1=='Radiology' || serviceName1=='Laboratory' ||serviceName1=='Diagnostics' || serviceName1=='New Information Request')
	{
	$("#backbttn1").attr('onclick','showStatusStackedBar("statusChart","service" ,"" ,recordStatus1 , recordStatus1, feelColor1)').css('display','block');
	 	$(".specialityPanel").show();
	dashboard1=filterFlag;
	dataFor1='Graph';
	title=title+" Tickets For "+serviceName1+" Service & Location "+locationNameForService1+":";
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+serviceId1+"&locationName="+locationNameForService1;
	}
	else
	{
	$("#backbttn1").attr('onclick','showStatusStackedBar("statusChart","service" ,"" ,recordStatus1 , recordStatus1, feelColor1)').css('display','block');
	 	$(".specialityPanel").show();
	dashboard1=filterFlag;
	dataFor1='Graph';
	title=title+" Tickets For "+serviceName1+" Service & Location "+locationNameForService1+":";
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+serviceId1+"&locationName="+locationNameForService1;
	}
	 	
	
	}
	
	else if(filterFlag=='pecialitysToDoc')
	{
	$("#backbttn1").attr('onclick','showStatusStackedBar("statusChart","service" ,"" ,recordStatus1 , recordStatus1, feelColor1)').css('display','block');
	 	$(".specialitytoDocPanel").show();
	dashboard1=filterFlag;
	dataFor1='Graph';
	alert(serviceName1);
	if(serviceName1=='EHC')
	{
		title=title+" Tickets For "+locationNameForService1+" Package:";
	}
	else
	{
		title=title+" Tickets For "+locationNameForService1+" Speciality:";
	}
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+serviceId1+"&locationName="+locationNameForService1;
	
	}
	//Corporate Wise Grapg
	else if(filterFlag=='corporate')
	{
	$("#backbttn1").attr('onclick','').css('display','none');
	$(".corporatePanel").show();
	dashboard1=filterFlag;
	dataFor1='Graph';
	title=title+" Tickets:";
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();

	}
	else if(filterFlag=='corporateLocation')
	{
	$("#backbttn1").attr('onclick','showStatusStackedBar("statusChart","service" ,"" ,recordStatus1 , recordStatus1, feelColor1)').css('display','block');
	 	$(".corporateLocationPanel").show();
	dashboard1=filterFlag;
	dataFor1='Graph';
	title=title+" Tickets For "+corporateName+" Corporate & Service "+serviceName1+":";
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&corporateName="+corporateID+"&serviceName="+serviceId1;
	}
	
	else if(filterFlag=='corporateService')
	{
	$("#backbttn1").attr('onclick','').css('display','none');
	$(".corporateServicePanel").show();
	dashboard1=filterFlag;
	dataFor1='Graph';
	title=title+" Tickets For "+corporateName+" Corporate:";
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+corporateID;

	}
	else if(filterFlag=='corporateServiceSpeciality')
	{
	if(serviceName1=='Radiology' || serviceName1=='Laboratory' ||serviceName1=='Diagnostics' || serviceName1=='New Information Request')
	{
	$("#backbttn1").attr('onclick','showStatusStackedBar("statusChart","service" ,"" ,recordStatus1 , recordStatus1, feelColor1)').css('display','block');
	 	$(".corporateServiceSpecialityPanel").show();
	dashboard1=filterFlag;
	dataFor1='Graph';
	title=title+" Tickets For "+serviceName1+" Service:";
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+serviceId1+"&locationName="+locationNameForService1+"&corporateName="+corporateID;
	}
	else
	{
	$("#backbttn1").attr('onclick','showStatusStackedBar("statusChart","service" ,"" ,recordStatus1 , recordStatus1, feelColor1)').css('display','block');
	 	$(".corporateServiceSpecialityPanel").show();
	dashboard1=filterFlag;
	dataFor1='Graph';
	title=title+" Tickets For "+locationNameForService1+" Location:";
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+serviceId1+"&locationName="+locationNameForService1+"&corporateName="+corporateID;
	}
	}
	else if(filterFlag=='corporateServiceDoctor')
	{
	$("#backbttn1").attr('onclick','showStatusStackedBar("statusChart","service" ,"" ,recordStatus1 , recordStatus1, feelColor1)').css('display','block');
	 	$(".corporateServiceSpecialityDoctorPanel").show();
	dashboard1=filterFlag;
	dataFor1='Graph';
	if(serviceName1=='EHC')
	{
		title=title+" Tickets For "+speciality+" Package:";
	}
	else
	{
		title=title+" Tickets For "+speciality+" Speciality:";
	}
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+serviceId1+"&locationName="+speciality;
	
	}
   	$('#'+divId).html("<center><br/><br/><br/><br/><br/><br/><br/><img src=images/lightbox-ico-loading.gif></center>");
  	$.ajax({
	type : "post",
	url	:	url1,
	async : false,
	type : "json",
	success : function(data)
	{
	for (var i = 0; i <= data.length - 1; i++)
	{
	total = total + data[i].Counter;
	data[i].total=data[i].Counter;
 	}
	 for (var x in data) 
	{
	 data[x].totalPer = Math.round(data[x].total / total * 100);
	}
	 if (total == 0 || isNaN(total))
	{
	 	$('#' + divId).html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
	} 
 	else
	{
 	//alert(filterFlag);
  	drawChartForStatus(divId, data, status, title, color, filterFlag,total,serviceName1);
 	}
	},
	error : function()
	{
	alert("error");
	}
	});
 
 
}// end here

  
 

function showProductivityStackedBar(divId, filterFlag, filterDeptId, status, title, color)
{
 	recordStatus2=status;
	feelColor2=color;
	var statusFor=$("#statusForProductivity").val();
 	var total = 0;
   	var url1='';
   	$(".allServiceProductivityPanel").hide();
	$(".allLocationProductivityPanelForService").hide();
	$(".allServiceManagerProductivityPanelForService").hide();
	$(".LocationProductivityPanel").hide();
	$(".ServiceProductivityPanelForLocation").hide();
	$(".accountManagerProductivityPanel").hide();
	$(".corporateProductivityPanelForAcntMng").hide();
	$(".serviceProductivityPanelForAcntMng").hide();
	$(".locationProductivityPanelForAcntMng").hide();
	$(".serviceManagerProductivityPanelForAcntMng").hide();
	$(".serviceManagerProductivityPanel").hide();
	
	$(".serviceSpecialityProductivityPanel").hide();
	$(".locationSpecialityProductivityPanel").hide();
	$(".specialityProductivityPanel").hide();
	$(".specialitytoDocProductivityPanel").hide();
	
	$(".corporateProductivityPanel").hide();
	$(".corporateServiceProductivityPanel").hide();
	$(".corporateLocationProductivityPanel").hide();
	$(".corporateServiceSpecialityProductivityPanel").hide();
	$(".corporateServiceDoctorProductivityPanel").hide();
	
	if(filterFlag=='corporateProductivity')
	{
	$("#backbttn2").attr('onclick','').css('display','none');
	$(".corporateProductivityPanel").show();
	dashboard2=filterFlag;
	dataFor2='Graph';
	title=title+" Tickets:";
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();
	}
	if(filterFlag=='corporateServiceProductivity')
	{
	$("#backbttn2").attr('onclick','').css('display','none');
	$(".corporateServiceProductivityPanel").show();
	dashboard2=filterFlag;
	dataFor2='Graph';
	title=title+" Tickets:";
	title=title+" Tickets For "+corporateName2+" Corporate:";
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+corporateID1;
	}
	
	if(filterFlag=='corporateLocationProductivity')
	{
	$("#backbttn2").attr('onclick','').css('display','none');
	$(".corporateLocationProductivityPanel").show();
	dashboard2=filterFlag;
	dataFor2='Graph';
	title=title+" Tickets "+corporateName2+" Corporate & Service "+serviceName2+":";
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&corporateName="+corporateID1+"&serviceName="+serviceId2;
	}
	if(filterFlag=='corporateServiceSpecialityProductivity')
	{
	if(serviceName2=='Radiology' || serviceName2=='Laboratory' ||serviceName2=='Diagnostics' || serviceName2=='New Information Request')
	{
	$("#backbttn2").attr('onclick','').css('display','none');
	$(".corporateServiceSpecialityProductivityPanel").show();
	dashboard2=filterFlag;
	dataFor2='Graph';
	title=title+" Tickets:";
	title=title+" Tickets "+locationNameForService2+" Location :";
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+serviceId2+"&locationName="+locationNameForService2+"&corporateName="+corporateID1;
	}
	else
	{
	$("#backbttn2").attr('onclick','').css('display','none');
	$(".corporateServiceSpecialityProductivityPanel").show();
	dashboard2=filterFlag;
	dataFor2='Graph';
	title=title+" Tickets "+locationNameForService2+" Location :";
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+serviceId2+"&locationName="+locationNameForService2+"&corporateName="+corporateID1;
	}
	
	}
	if(filterFlag=='corporateSpecialityDoctorProductivity')
	{
	$("#backbttn2").attr('onclick','').css('display','none');
	$(".corporateServiceDoctorProductivityPanel").show();
	dashboard2=filterFlag;
	dataFor2='Graph';
	title=title+" Tickets:";
	if(serviceName2=='EHC')
	{
		title=title+" Tickets For "+speciality1+" Package:";
	}
	else
	{
		title=title+" Tickets "+speciality1+" Speciality :";
	}
	
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+serviceId2+"&locationName="+speciality1;
	}
	if(filterFlag=='serviceDOCProductivity')
	{
	$("#backbttn2").attr('onclick','').css('display','none');
	$(".serviceSpecialityProductivityPanel").show();
	dashboard2=filterFlag;
	dataFor2='Graph';
	title=title+" Tickets:";
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();
	}
	else if(filterFlag=='locationForSpecialityProductivity')
	{
	$("#backbttn2").attr('onclick','').css('display','none');
	$(".locationSpecialityProductivityPanel").show();
	dashboard2=filterFlag;
	dataFor2='Graph';
	title=title+" Tickets For "+serviceName2+" Service:";
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+serviceId2;
	}
	else if(filterFlag=='specialitysProductivity')
	{

	if(serviceName2=='Radiology' || serviceName2=='Laboratory' ||serviceName2=='Diagnostics' || serviceName2=='New Information Request')
	{
	$("#backbttn2").attr('onclick','').css('display','none');
	 	$(".specialityProductivityPanel").show();
	dashboard2=filterFlag;
	dataFor2='Graph';
	title=title+" Tickets For "+serviceName2+" Service & Location "+locationNameForService2+":";
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+serviceId2+"&locationName="+locationNameForService2;
	}
	else
	{
	$("#backbttn2").attr('onclick','').css('display','none');
	 	$(".specialityProductivityPanel").show();
	dashboard2=filterFlag;
	dataFor2='Graph';
	title=title+" Tickets For "+serviceName2+" Service & Location "+locationNameForService2+":";
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+serviceId2+"&locationName="+locationNameForService2;
	}

	}
	else if(filterFlag=='specialitysToDocProductivity')
	{
	$("#backbttn1").attr('onclick','showStatusStackedBar("statusChart","service" ,"" ,recordStatus1 , recordStatus1, feelColor1)').css('display','block');
	 	$(".specialitytoDocProductivityPanel").show();
	dashboard1=filterFlag;
	dataFor2='Graph';
	if(serviceName2=='EHC')
	{
		title=title+" Tickets For "+speciality1+" Package:";
	}
	else
	{
		title=title+" Tickets For "+speciality1+" Speciality:";
	}
	
 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+serviceId2+"&locationName="+locationNameForService2;
	
	}
	
	else if(filterFlag=='serviceProductivity')
	{
	$("#backbttn2").attr('onclick','').css('display','none');
	$(".allServiceProductivityPanel").show();
	dashboard2=filterFlag;
	dataFor2='Graph';
	title=title+" Tickets:";
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();

	}
	else if(filterFlag=='locationForServiceProductivity')
	{
	 	$("#backbttn2").attr('onclick','showProductivityStackedBar("productivityChart","serviceProductivity" ,"" ,recordStatus2 , recordStatus2, feelColor2)').css('display','block');
	 	$(".allLocationProductivityPanelForService").show();
	 	dashboard2=filterFlag;
	dataFor2='Graph';
	title=title+" Tickets For "+serviceName2+" Service:";
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+serviceId2;

	
	}
	else if(filterFlag=='serviceManagerForServiceProductivity')
	{
	$("#backbttn2").attr('onclick','showProductivityStackedBar("productivityChart","locationForServiceProductivity" ,"" ,recordStatus2 , recordStatus2, feelColor2)').css('display','block');
	  	$(".allServiceManagerProductivityPanelForService").show();
	  	dashboard2=filterFlag;
	dataFor2='Graph';
	title=title+" Tickets For "+serviceName2+" Service & "+locationNameForService2+" Location:";
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+serviceId2+"&locationName="+locationNameForService2;

	}
	 	else if(filterFlag=='locationProductivity')
	{
	$("#backbttn2").attr('onclick','').css('display','none'); 
	$(".LocationProductivityPanel").show();
	dashboard2=filterFlag;
	dataFor2='Graph';
	title=title+" Tickets:";
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();

	}
	else if(filterFlag=='serviceForLocationProductivity')
	{
	$("#backbttn2").attr('onclick','showProductivityStackedBar("productivityChart","locationProductivity" ,"" ,recordStatus2 , recordStatus2, feelColor2)').css('display','block');
	  	$(".ServiceProductivityPanelForLocation").show();
	  	dashboard2=filterFlag;
	dataFor2='Graph';
	title=title+" Tickets For "+locationNameForService2+" Location:";
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&locationName="+locationNameForService2;

	}
 	else if(filterFlag=='accountManagerProductivity')
	{
	$("#backbttn2").attr('onclick','').css('display','none'); 
	$(".accountManagerProductivityPanel").show();
	dashboard2=filterFlag;
	dataFor2='Graph';
	title=title+" Tickets:";
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();

	}
	else if(filterFlag=='corporateForAcntMngProductivity')
	{
	$("#backbttn2").attr('onclick','showProductivityStackedBar("productivityChart","accountManagerProductivity" ,"" ,recordStatus2 , recordStatus2, feelColor2)').css('display','block');
	 	$(".corporateProductivityPanelForAcntMng").show();
	 	dashboard2=filterFlag;
	dataFor2='Graph';
	title=title+" Tickets For "+accountManagerName2+" Account Manager:";
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&accountManagerName="+accountManagerName2;

	}
	else if(filterFlag=='serviceForAcntMngProductivity')
	{
	$("#backbttn2").attr('onclick','showProductivityStackedBar("productivityChart","corporateForAcntMngProductivity" ,"" ,recordStatus2 , recordStatus2, feelColor2)').css('display','block');
	 	$(".serviceProductivityPanelForAcntMng").show();
	 	dashboard2=filterFlag;
	dataFor2='Graph';
	title=title+" Tickets For "+accountManagerName2+" Account Manager & "+corporateName2+" Corporate:";
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&accountManagerName="+accountManagerName2+"&corporateName="+corporateName2;

	}
	else if(filterFlag=='locationForAcntMngProductivity')
	{
	$("#backbttn2").attr('onclick','showProductivityStackedBar("productivityChart","serviceForAcntMngProductivity" ,"" ,recordStatus2 , recordStatus2, feelColor2 )').css('display','block');
	 	$(".locationProductivityPanelForAcntMng").show();
	 	dashboard2=filterFlag;
	dataFor2='Graph';
	title=title+" Tickets For "+accountManagerName2+" Account Manager & "+corporateName2+" Corporate & "+serviceName2+" Service:"
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&accountManagerName="+accountManagerName2+"&corporateName="+corporateName2+"&serviceName="+serviceId2;

	}
	else if(filterFlag=='serviceManagerForAcntMngProductivity')
	{
	$("#backbtt2").attr('onclick','showProductivityStackedBar("productivityChart","locationForAcntMngProductivity" ,"" ,recordStatus2 , recordStatus2, feelColor2 )').css('display','block');
	 	$(".serviceManagerProductivityPanelForAcntMng").show();
	 	dashboard2=filterFlag;
	dataFor2='Graph';
	title=title+" Tickets For "+accountManagerName2+" Account Manager & "+corporateName2+" Corporate & "+serviceName2+" Service & "+locationNameForService2+" Location:";
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&accountManagerName="+accountManagerName2+"&corporateName="+corporateName2+"&serviceName="+serviceId2+"&locationName="+locationNameForService2;

	}
 	else if(filterFlag=='serviceManagerProductivity')
	{
	$("#backbttn2").attr('onclick','').css('display','none'); 
	$(".serviceManagerProductivityPanel").show();
	dashboard2=filterFlag;
	dataFor2='Graph';
	title=title+" Tickets:";
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/getCounters.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+filterFlag+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();

	}
  	$('#'+divId).html("<center><br/><br/><br/><br/><br/><br/><br/><img src=images/lightbox-ico-loading.gif></center>");
  	$.ajax({
	type : "post",
	url	:	url1,
	async : false,
	type : "json",
	success : function(data)
	{
	for (var i = 0; i <= data.length - 1; i++)
	{
	total = total + data[i].Counter;
	data[i].total=data[i].Counter;
 	}
	 for (var x in data) 
	{
	 data[x].totalPer = Math.round(data[x].total / total * 100);
	}
	 if (total == 0 || isNaN(total))
	{
	 	$('#' + divId).html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
	} 
 	else
	{
  	drawChartForProductivity(divId, data, status, title, color, filterFlag,total,serviceName2);
 	}
	},
	error : function()
	{
	alert("error");
	}
	});
 
 
}// end here
    
 
function drawChartForStatus(divId, data, status, title, color, Category,totalData,ServiceName1)
{
	
	//alert(divId+" Data "+ data+" Status "+ status+" Title "+ title+" Color "+color+" Category "+Category+" Total Data"+totalData);
	  
	  var chart='';
	 	  if(data.length>0)
	  {
	  chart = AmCharts.makeChart(divId, {
	"type" : "serial",
	"titles" : [ {
	"text" :  title+" "+totalData,
	"size" : 15
	} ],
	"theme" : "light",
	"dataProvider" : data,
	"valueAxes" : [ {
	"gridColor" : "#FFFFFF",
	"gridAlpha" : 0,
	"dashLength" : 0

	} ],
	"gridAboveGraphs" : true,
	"startDuration" : 1,
	"graphs" : [ {
	"balloonText" : "[[category]]: <b>[[value]]</b>",
	"fillAlphas" : 1,
	"lineAlpha" : 0,
	"type" : "column",
	"valueField" : "Counter",
	 	"labelOffset":10,
	 	"labelText" : "[[totalPer]]%",
	
	"title" : title,
	"fillColors" : "" + color,
	"fixedColumnWidth" : 10

	}

	],
	"depth3D" : 7,
	"angle" : 15,
	"chartCursor" : {
	"categoryBalloonEnabled" : false,
	"cursorAlpha" : 0,
	"zoomable" : true
	},

	"categoryField" : "" + Category,
	"categoryAxis" : {
  	    "gridPosition": "start",
  	    "gridAlpha": 0,
  	    "tickPosition": "start",
  	    "tickLength": 10,
  	 	"autoRotateAngle": 20,
  	"autoRotateCount": 0,
  	"autoWrap":false
  	  },
	"export" : {
	"enabled" : true
	}

	});
	  }	  
	  if(chart!='')  
	 	chart.write(divId);	
	  else
	  $('#' + divId).html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
	  
	  	AmCharts.clickTimeout = 0; // this will hold setTimeout reference
	AmCharts.lastClick = 0; // last click timestamp
	AmCharts.doubleClickDuration = 300; // distance between clicks in ms - if
	   
	
	if(Category=='service')
	  {
	  AmCharts.doDoubleClick1 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick1 = function(event)
	{
	  locationWiseChartForAllServices(event);
	};
	  }
	
	if(Category=='serviceDOC')
	  {
	  AmCharts.doDoubleClick1 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick1 = function(event)
	{
	  locationWiseChartForAllServicesDOC(event);
	};
	  }
	
	if(Category=='locationForSpeciality')
	  {
	  AmCharts.doDoubleClick1 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick1 = function(event)
	{
	  locationWiseChartForAllSpeciality(event);
	};
	  }

	if(Category=='locationForSpecialitys')
	  {
	if(serviceName1=='Radiology' || serviceName1=='Laboratory' ||serviceName1=='Diagnostics' || serviceName1=='New Information Request')
	{
	AmCharts.doDoubleClick1 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick1 = function(event)
	{
	  alert("Sorry! No any data on single click");
	};
	}
	else
	{
	AmCharts.doDoubleClick1 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick1 = function(event)
	{
	  locationWiseChartForAllSpecialitytoDOC(event);
	};
	}
	  }
	if(Category=='pecialitysToDoc')
	  {
	  AmCharts.doDoubleClick1 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick1 = function(event)
	{
	  alert("Sorry! No any data on single click");
	};
	  }
	//CorporateService
	if(Category=='corporate')
	  {
	  AmCharts.doDoubleClick1 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick1 = function(event)
	{
	  corporateWiseService(event);
	};
	  }
	//CorporateService
	if(Category=='corporateService')
	  {
	  AmCharts.doDoubleClick1 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick1 = function(event)
	{
	  corporateWiseLocateion(event);
	};
	  }
	if(Category=='corporateLocation')
	  {
	  AmCharts.doDoubleClick1 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick1 = function(event)
	{
	  corporateWiseServiceSpeciality(event);
	};
	  }
	//alert("Category "+Category)
	if(Category=='corporateServiceSpeciality')
	  {
	//alert("serviceName1 "+serviceName1);
	if(serviceName1=='Radiology' || serviceName1=='Laboratory' ||serviceName1=='Diagnostics' || serviceName1=='New Information Request')
	{
	AmCharts.doDoubleClick1 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick1 = function(event)
	{
	  alert("Sorry! No any data on single click");
	};
	}
	else
	{
	AmCharts.doDoubleClick1 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick1 = function(event)
	{
	  corporateWiseServiceDoctor(event);
	};
	}
	  }
	
	if(Category=='corporateServiceDoctor')
	  {
	  AmCharts.doDoubleClick1 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick1 = function(event)
	{
	  alert("Sorry! No any data on single click");
	};
	  }
	if(Category=='locationForService')
	  {
	  AmCharts.doDoubleClick1 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick1 = function(event)
	{
	  serviceManagerWiseChartForAllLocation(event);
	};
	  }
	  
	  

	if(Category=='serviceManagerForService')
	  {
	  AmCharts.doDoubleClick1 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick1 = function(event)
	{
	  alert("Sorry! No any data on single click");
	};
	  }
	  
	   
	   
	
	
	
	
	if(Category=='location')
	  {
	  AmCharts.doDoubleClick1 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick1 = function(event)
	{
	  serviceWiseChartForLocation(event);
	};
	  }
	   
	   if(Category=='serviceForLocation')
	  {
	  AmCharts.doDoubleClick1 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick1 = function(event)
	{
	  alert("Sorry! No any data on single click");
	};
	  }
	   
	   
	   
	   
	   
	   if(Category=='accountManager')
	  {
	  AmCharts.doDoubleClick1 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick1 = function(event)
	{
	  corporateWiseChartForAccountManager(event);
	};
	  }
	   if(Category=='corporateForAcntMng')
	  {
	  AmCharts.doDoubleClick1 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick1 = function(event)
	{
	  serviceWiseChartForCorporate(event);
	};
	  }
	   if(Category=='serviceForAcntMng')
	  {
	  AmCharts.doDoubleClick1 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick1 = function(event)
	{
	  locationWiseChartForService(event);
	};
	  }
	   if(Category=='locationForAcntMng')
	  {
	  AmCharts.doDoubleClick1 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick1 = function(event)
	{
	  serviceManagerWiseChartForLocation(event);
	  
	};
	  }
	   
	   if(Category=='serviceManagerForAcntMng')
	  {
	  AmCharts.doDoubleClick1 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick1 = function(event)
	{
	  alert("Sorry! No any data on single click");
	};
	  }
	   
	   
	   
	   
	  
	   if(Category=='serviceManager')
	  {
	  AmCharts.doDoubleClick1 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick1 = function(event)
	{
	  alert("Sorry! No any data on single click");
	};
	  }   
	 
	    
	   
	 
	  
	   
	  
	 
	  
	  
	  
	   
	  
	  
	  AmCharts.myClickHandler1 = function(event)
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
	AmCharts.doDoubleClick1(event);
	} else
	{
	// single click!
	// let's delay it to see if a second click will come through
	AmCharts.clickTimeout = setTimeout(function()
	{
	AmCharts.doSingleClick1(event);
	}, AmCharts.doubleClickDuration);
	}
	AmCharts.lastClick = ts;
	};
	// add handler to the chart
	chart.addListener("clickGraphItem", AmCharts.myClickHandler1);
}
 


function drawChartForProductivity(divId, data, status, title, color, Category,totalData,serviceName2)
{
	
	 
	  
	  var chart='';
	 	  if(data.length>0)
	  {
	  chart = AmCharts.makeChart(divId, {
	"type" : "serial",
	"titles" : [ {
	"text" :  title+" "+totalData,
	"size" : 15
	} ],
	"theme" : "light",
	"dataProvider" : data,
	"valueAxes" : [ {
	"gridColor" : "#FFFFFF",
	"gridAlpha" : 0,
	"dashLength" : 0

	} ],
	"gridAboveGraphs" : true,
	"startDuration" : 1,
	"graphs" : [ {
	"balloonText" : "[[category]]: <b>[[value]]</b>",
	"fillAlphas" : 1,
	"lineAlpha" : 0,
	"type" : "column",
	"valueField" : "Counter",
	 	"labelOffset":10,
	 	"labelText" : "[[totalPer]]%",
	
	"title" : title,
	"fillColors" : "" + color,
	"fixedColumnWidth" : 10

	}

	],
	"depth3D" : 7,
	"angle" : 15,
	"chartCursor" : {
	"categoryBalloonEnabled" : false,
	"cursorAlpha" : 0,
	"zoomable" : true
	},

	"categoryField" : "" + Category,
	"categoryAxis" : {
  	    "gridPosition": "start",
  	    "gridAlpha": 0,
  	    "tickPosition": "start",
  	    "tickLength": 10,
  	 	"autoRotateAngle": 20,
  	"autoRotateCount": 0,
  	"autoWrap":false
  	  },
	"export" : {
	"enabled" : true
	}

	});
	  }	  
	  if(chart!='')  
	 	chart.write(divId);	
	  else
	  $('#' + divId).html("<center><br/><br/><br/><br/><br/><br/><font style='opacity:.9;color:#0F4C97;' size='3'>No Data Available</font></center>");
	  
	  	AmCharts.clickTimeout = 0; // this will hold setTimeout reference
	AmCharts.lastClick = 0; // last click timestamp
	AmCharts.doubleClickDuration = 300; // distance between clicks in ms - if
	
	if(Category=='corporateProductivity')
	  {
	  AmCharts.doDoubleClick2 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick2 = function(event)
	{
	  corporateWiseServiceProductivity(event);
	};
	  }
	if(Category=='corporateServiceProductivity')
	  {
	  AmCharts.doDoubleClick2 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick2 = function(event)
	{
	  corporateWiseLocateionProductivity(event);
	};
	  }
	if(Category=='corporateLocationProductivity')
	  {
	  AmCharts.doDoubleClick2 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick2 = function(event)
	{
	  corporateWiseServiceSpecialityProductivity(event);
	};
	  }
	
	if(Category=='corporateServiceSpecialityProductivity')
	  {
	if(serviceName2=='Radiology' || serviceName2=='Laboratory' ||serviceName2=='Diagnostics' || serviceName2=='New Information Request')
	{
	AmCharts.doDoubleClick2 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick2 = function(event)
	{
	  alert("Sorry! No any data on single click");
	};
	}
	else
	{
	AmCharts.doDoubleClick2 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick2 = function(event)
	{
	  corporateWiseProductivityChartForAllDoctor(event);
	};
	}
	  }
	
	if(Category=='corporateSpecialityDoctorProductivity')
	  {
	  AmCharts.doDoubleClick2 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick2 = function(event)
	{
	  alert("Sorry! No any data on single click");
	};
	  }
	if(Category=='serviceDOCProductivity')
	  {
	  AmCharts.doDoubleClick2 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick2 = function(event)
	{
	  locationWiseChartForAllServicesProductivity(event);
	};
	  }
	if(Category=='locationForSpecialityProductivity')
	  {
	  AmCharts.doDoubleClick2 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick2 = function(event)
	{
	  locationWiseAllSpeciality(event);
	};
	  }
	if(Category=='specialitysProductivity')
	  {
	if(serviceName2=='Radiology' || serviceName2=='Laboratory' ||serviceName2=='Diagnostics' || serviceName2=='New Information Request')
	{
	AmCharts.doDoubleClick2 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick2 = function(event)
	{
	  alert("Sorry! No any data on single click");
	};
	}
	else
	{
	AmCharts.doDoubleClick2 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick2 = function(event)
	{
	  specialityWiseProductivityChartForAllDoctor(event);
	};
	}
	  }
	
	if(Category=='specialitysToDocProductivity')
	  {
	  AmCharts.doDoubleClick2 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick2 = function(event)
	{
	  alert("Sorry! No any data on single click");
	};
	  }
	 
	if(Category=='serviceProductivity')
	{
	AmCharts.doDoubleClick2 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick2 = function(event)
	{
	  locationWiseProductivityChartForAllServices(event);
	};
	}
	if(Category=='locationForServiceProductivity')
	  {
	  AmCharts.doDoubleClick2 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick2 = function(event)
	{
	  serviceManagerWiseProductivityChartForAllLocation(event);
	};
	  }
	  
	  

	if(Category=='serviceManagerForServiceProductivity')
	  {
	  AmCharts.doDoubleClick2 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick2 = function(event)
	{
	  alert("Sorry! No any data on single click");
	};
	  }
	  
	   
	   
	
	
	
	
	if(Category=='locationProductivity')
	  {
	  AmCharts.doDoubleClick2 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick2 = function(event)
	{
	  serviceWiseProductivityChartForLocation(event);
	};
	  }
	   
	   if(Category=='serviceForLocationProductivity')
	  {
	  AmCharts.doDoubleClick2 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick2 = function(event)
	{
	  alert("Sorry! No any data on single click");
	};
	  }
	   
	   
	   
	   
	   
	   if(Category=='accountManagerProductivity')
	  {
	  AmCharts.doDoubleClick2 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick2 = function(event)
	{
	  corporateWiseProductivityChartForAccountManager(event);
	};
	  }
	   if(Category=='corporateForAcntMngProductivity')
	  {
	  AmCharts.doDoubleClick1 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick2 = function(event)
	{
	  serviceWiseProductivityChartForCorporate(event);
	};
	  }
	   if(Category=='serviceForAcntMngProductivity')
	  {
	  AmCharts.doDoubleClick2 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick2 = function(event)
	{
	  locationWiseProductivityChartForService(event);
	};
	  }
	   if(Category=='locationForAcntMngProductivity')
	  {
	  AmCharts.doDoubleClick2 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick2 = function(event)
	{
	  serviceManagerWiseProductivityChartForLocation(event);
	  
	};
	  }
	   
	   if(Category=='serviceManagerForAcntMngProductivity')
	  {
	  AmCharts.doDoubleClick2 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick2 = function(event)
	{
	  alert("Sorry! No any data on single click");
	};
	  }
	   
	   
	   
	   
	  
	   if(Category=='serviceManagerProductivity')
	  {
	  AmCharts.doDoubleClick2 = function(event)
	  {
	//  handleClickStatusLocation(event);
	  };
	  AmCharts.doSingleClick2 = function(event)
	{
	  alert("Sorry! No any data on single click");
	};
	  }   
	 
	    
	   
	 
	  
	   
	  
	 
	  
	  
	  
	   
	  
	  
	  AmCharts.myClickHandler2 = function(event)
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
	};
	// add handler to the chart
	chart.addListener("clickGraphItem", AmCharts.myClickHandler2);
}
 


function getData(Id, status, filterFlag, flag1,flag2)
{
 
	var fromDate='';
	var toDate='';
	var fromTime='';
	var toTime='';
	var statusFor='';
	var productivityFor='';
	var productivityLimit='';
	if(filterFlag=='specialtyProductivity' || filterFlag=='doctorProductivity'  || filterFlag=='testTypeProductivity'  || filterFlag=='testNameProductivity' || filterFlag=='locationProductivity' || filterFlag=='nursingUnitProductivity')
	{
	fromDate=$('#sdate').val();
	toDate=$('#edate').val();
	fromTime=$("#fromTime").val();
	toTime=$("#toTime").val();
	statusFor=$("#statusForProductivity").val();
	productivityFor=$("#productivityFor").val();
	productivityLimit=$("#productivityLimit").val();
	}
	if(filterFlag=='specialty' || filterFlag=='doctor'  || filterFlag=='testType'  || filterFlag=='testName' || filterFlag=='location' || filterFlag=='nursingUnit')
	{
	fromTime=$("#fromTime").val();
	toTime=$("#toTime").val();
	fromDate=$('#sdate').val();
	toDate=$('#edate').val();
	 statusFor=$("#statusFor").val();
	}
	if(filterFlag=='crcReport')
	{
	fromDate=$('#fromDateSearch').val();
	toDate=$('#toDateSearch').val();
	fromTime=$("#fTime").val();
	toTime=$("tTime").val();
	statusFor=$("#serviceID").val()
	}
	 
	 $("#confirmEscalationDialog").dialog({
	width : 1150,
	height : 500
	});
	 var url1='';
	$("#confirmEscalationDialog").dialog('open');
	$("#confirmingEscalation").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	url1="view/Over2Cloud/CriticalPatient/Dashboard_Pages/beforeFeedAction.action?fromDate="+fromDate+"&toDate="+toDate+"&status="+status+"&tableFor="+filterFlag+"&fromTime="+fromTime+"&toTime="+toTime+"&statusFor="+statusFor+"&id="+Id+"&productivityFor="+productivityFor+"&productivityLimit="+productivityLimit;
 	$.ajax({
	type : "post",
	url : url1,
	  	success : function(feedbackdata)
	{
	$("#confirmingEscalation").html(feedbackdata);
	},
	error : function()
	{
	alert("error");
	}
	});
}
 


 
 
 
 function showData(id,flag,val)
 {
	 	var url1='';
	 	var status='All';
	 	if(val=='serviceDOC' || val=='service' || val=='locationForSpeciality' || val=='locationForSpecialitys' || val=='pecialitysToDoc' || val=='locationForService'||val=='serviceManagerForService' || val=='location'||val=='serviceForLocation' || val=='accountManager' || val=='corporateForAcntMng' || val=='serviceForAcntMng' || val=='locationForAcntMng' || val=='serviceManagerForAcntMng' || val=='serviceManager')
	 	{
	$(".allServicePanel").hide();
	$(".allLocationPanelForService").hide();
	$(".allServiceManagerPanelForService").hide();
	$(".LocationPanel").hide();
	$(".ServicePanelForLocation").hide();
	$(".accountManagerPanel").hide();
	$(".corporatePanelForAcntMng").hide();
	$(".servicePanelForAcntMng").hide();
	$(".locationPanelForAcntMng").hide();
	$(".serviceManagerPanelForAcntMng").hide();
	$(".serviceManagerPanel").hide();
	
	$(".serviceSpecialityPanel").hide();
	$(".locationSpecialityPanel").hide();
	$(".specialityPanel").hide();
	$(".specialitytoDocPanel").hide();
	
	$(".corporatePanel").hide();
	$(".corporateServicePanel").hide();
	$(".corporateLocationPanel").hide();
	$(".corporateServiceSpecialityPanel").hide();
	$(".corporateServiceSpecialityDoctorPanel").hide();
	
	
	 	}
 	 	if(val=='serviceProductivity' || val=='locationForServiceProductivity' ||val=='serviceManagerForServiceProductivity' 
 	 	|| val=='locationProductivity' || val=='serviceForLocationProductivity'  || val=='accountManagerProductivity'  
 	 	|| val=='corporateForAcntMngProductivity' || val=='serviceForAcntMngProductivity'  || val=='locationForAcntMngProductivity' 
 	 	|| val=='serviceManagerForAcntMngProductivity' || val=='serviceManagerProductivity'
 	 	|| val=='serviceDOCProductivity' || val=='specialitysToDocProductivity')
 	 	{
	 	$(".allServiceProductivityPanel").hide();
	$(".allLocationProductivityPanelForService").hide();
	$(".allServiceManagerProductivityPanelForService").hide();
	$(".LocationProductivityPanel").hide();
	$(".ServiceProductivityPanelForLocation").hide();
	$(".accountManagerProductivityPanel").hide();
	$(".corporateProductivityPanelForAcntMng").hide();
	$(".serviceProductivityPanelForAcntMng").hide();
	
	$(".locationProductivityPanelForAcntMng").hide();
	$(".serviceManagerProductivityPanelForAcntMng").hide();
	$(".serviceManagerProductivityPanel").hide();
	

	$(".serviceSpecialityProductivityPanel").hide();
	$(".locationSpecialityProductivityPanel").hide();
	$(".specialityProductivityPanel").hide();
	$(".specialitytoDocProductivityPanel").hide();
	
	$(".corporateProductivityPanel").hide();
	$(".corporateServiceProductivityPanel").hide();
	$(".corporateLocationProductivityPanel").hide();
	$(".corporateServiceSpecialityProductivityPanel").hide();
	$(".corporateServiceDoctorProductivityPanel").hide();
	 }
 	$("#"+id).html("<center><br/><br/><br/><br/><br/><br/><br/><img src=images/lightbox-ico-loading.gif></center>");
 	if(val=='service')
	{
 	var statusFor=$("#statusFor").val();
	 	$(".allServicePanel").show();
	dashboard1=val;
	dataFor1='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();

	}
 	
	else if(val=='pecialitysToDoc')
	{

	var statusFor=$("#statusFor").val();
	 	$(".specialitytoDocPanel").show();
	  	dashboard1=val;
	dataFor1='Table';
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+serviceId1+"&locationName="+locationNameForService1;
	}
	else if(val=='serviceManagerForService')
	{
	var statusFor=$("#statusFor").val();
	   	$(".allServiceManagerPanelForService").show();
	   	dashboard1=val;
	dataFor1='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+serviceId1+"&locationName="+locationNameForService1;
	}
	
	
	
	
	else if(val=='location')
	{
	var statusFor=$("#statusFor").val();
	 	$(".LocationPanel").show();
	 	dashboard1=val;
	dataFor1='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();

	}
	else if(val=='serviceForLocation')
	{
	var statusFor=$("#statusFor").val();
	   	$(".ServicePanelForLocation").show();
	   	dashboard1=val;
	dataFor1='Table';
 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&locationName="+locationNameForService1;

	}
	
	
	
	
	
	else if(val=='accountManager')
	{
	var statusFor=$("#statusFor").val();
	 	$(".accountManagerPanel").show();
	 	dashboard1=val;
	dataFor1='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();

	}
	else if(val=='corporateForAcntMng')
	{
	var statusFor=$("#statusFor").val();
	  	$(".corporatePanelForAcntMng").show();
	  	dashboard1=val;
	dataFor1='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&accountManagerName="+accountManagerName1;

	}
	else if(val=='serviceForAcntMng')
	{
	var statusFor=$("#statusFor").val();
	  	$(".servicePanelForAcntMng").show();
	  	dashboard1=val;
	dataFor1='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&accountManagerName="+accountManagerName1+"&corporateName="+corporateName1;

	}
	else if(val=='locationForAcntMng')
	{
	var statusFor=$("#statusFor").val();
	  	$(".locationPanelForAcntMng").show();
	  	dashboard1=val;
	dataFor1='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&accountManagerName="+accountManagerName1+"&corporateName="+corporateName1+"&serviceName="+serviceId1;

	}
	else if(val=='serviceManagerForAcntMng')
	{
	var statusFor=$("#statusFor").val();
	  	$(".serviceManagerPanelForAcntMng").show();
	  	dashboard1=val;
	dataFor1='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&accountManagerName="+accountManagerName1+"&corporateName="+corporateName1+"&serviceName="+serviceId1+"&locationName="+locationNameForService1;

	}
	else if(val=='serviceManager')
	{
	var statusFor=$("#statusFor").val();
	 	$(".serviceManagerPanel").show();
	 	dashboard1=val;
	dataFor1='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();

	}
 	
	else if(val=='serviceDOC')
	{
 	var statusFor=$("#statusFor").val();
	 	$(".serviceSpecialityPanel").show();
	dashboard1=val;
	dataFor1='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();

	}
	else if(val=='locationForService')
	{
	var statusFor=$("#statusFor").val();
	  	$(".allLocationPanelForService").show();
	  	dashboard1=val;
	dataFor1='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+serviceId1;

	
	}
	else if(val=='locationForSpeciality')
	{
	var statusFor=$("#statusFor").val();
	  	$(".locationSpecialityPanel").show();
	  	dashboard1=val;
	dataFor1='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+serviceId1;
	}
	else if(val=='locationForSpecialitys')
	{

	var statusFor=$("#statusFor").val();
	 	$(".specialityPanel").show();
	  	dashboard1=val;
	dataFor1='Table';
	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+serviceId1+"&locationName="+locationNameForService1;
	}
 	
	else if(val=='corporate')
	{
	var statusFor=$("#statusFor").val();
	  	$(".corporatePanel").show();
	  	dashboard1=val;
	dataFor1='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();

	}
 	else if(val=='corporateService')
	{
 	var statusFor=$("#statusFor").val();
	 	$(".corporateServicePanel").show();
	dashboard1=val;
	dataFor1='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+corporateID;

	}
 	else if(val=='corporateLocation')
	{
	var statusFor=$("#statusFor").val();
	  	$(".corporateLocationPanel").show();
	  	dashboard1=val;
	dataFor1='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&corporateName="+corporateID+"&serviceName="+serviceId1;
	
	}
 	else if(val=='corporateServiceSpeciality')
	{
	var statusFor=$("#statusFor").val();
	  	$(".corporateServiceSpecialityPanel").show();
	  	dashboard1=val;
	dataFor1='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+serviceId1+"&locationName="+locationNameForService1+"&corporateName="+corporateID;
	
	}
 	else if(val=='corporateServiceDoctor')
	{
	var statusFor=$("#statusFor").val();
	  	$(".corporateServiceSpecialityDoctorPanel").show();
	  	dashboard1=val;
	dataFor1='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+serviceId1+"&locationName="+speciality;
	}
 	
 	
 	
 	
 	
	else if(val=='serviceProductivity')
	{
 	var statusFor=$("#statusForProductivity").val();
	 	$(".allServiceProductivityPanel").show();
	dashboard2=val;
	dataFor2='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();

	}
	else if(val=='locationForServiceProductivity')
	{
	var statusFor=$("#statusForProductivity").val();
	  	$(".allLocationProductivityPanelForService").show();
	  	dashboard2=val;
	dataFor2='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+serviceId2;

	
	}
	else if(val=='serviceManagerForServiceProductivity')
	{
	var statusFor=$("#statusForProductivity").val();
	   	$(".allServiceManagerProductivityPanelForService").show();
	   	dashboard2=val;
	dataFor2='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+serviceId2+"&locationName="+locationNameForService2;

	}
	
	
	
	
	else if(val=='locationProductivity')
	{
	var statusFor=$("#statusForProductivity").val();
	 	$(".LocationProductivityPanel").show();
	 	dashboard2=val;
	dataFor2='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();

	}
	else if(val=='serviceForLocationProductivity')
	{
	var statusFor=$("#statusForProductivity").val();
	   	$(".ServiceProductivityPanelForLocation").show();
	   	dashboard2=val;
	dataFor2='Table';
 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&locationName="+locationNameForService2;

	}
	
	
	
	
	
	else if(val=='accountManagerProductivity')
	{
	var statusFor=$("#statusForProductivity").val();
	 	$(".accountManagerProductivityPanel").show();
	 	dashboard2=val;
	dataFor2='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();

	}
	else if(val=='corporateForAcntMngProductivity')
	{
	var statusFor=$("#statusForProductivity").val();
	  	$(".corporateProductivityPanelForAcntMng").show();
	  	dashboard2=val;
	dataFor2='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&accountManagerName="+accountManagerName2;

	}
	else if(val=='serviceForAcntMngProductivity')
	{
	var statusFor=$("#statusForProductivity").val();
	  	$(".serviceProductivityPanelForAcntMng").show();
	  	dashboard2=val;
	dataFor2='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&accountManagerName="+accountManagerName2+"&corporateName="+corporateName2;

	}
	else if(val=='locationForAcntMngProductivity')
	{
	var statusFor=$("#statusForProductivity").val();
	  	$(".locationProductivityPanelForAcntMng").show();
	  	dashboard2=val;
	dataFor2='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&accountManagerName="+accountManagerName2+"&corporateName="+corporateName2+"&serviceName="+serviceId2;

	}
	else if(val=='serviceManagerForAcntMngProductivity')
	{
	var statusFor=$("#statusForProductivity").val();
	  	$(".serviceManagerProductivityPanelForAcntMng").show();
	  	dashboard2=val;
	dataFor2='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&accountManagerName="+accountManagerName2+"&corporateName="+corporateName2+"&serviceName="+serviceId2+"&locationName="+locationNameForService2;

	}
	
	
	
	
	
	else if(val=='serviceManagerProductivity')
	{
	var statusFor=$("#statusForProductivity").val();
	 	$(".serviceManagerProductivityPanel").show();
	 	dashboard2=val;
	dataFor2='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();

	}

	else if(val=='serviceDOCProductivity')
	{
 	var statusFor=$("#statusForProductivity").val();
	 	$(".serviceSpecialityProductivityPanel").show();
	dashboard2=val;
	dataFor2='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();

	}
	else if(val=='locationForSpecialityProductivity')
	{
 	var statusFor=$("#statusForProductivity").val();
	 	$(".locationSpecialityProductivityPanel").show();
	dashboard2=val;
	dataFor2='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+serviceId2;

	}
	else if(val=='specialitysProductivity')
	{
 	var statusFor=$("#statusForProductivity").val();
	 	$(".specialityProductivityPanel").show();
	dashboard2=val;
	dataFor2='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+serviceId2+"&locationName="+locationNameForService2;

	}
	else if(val=='corporateProductivity')
	{
	var statusFor=$("#statusFor").val();
	  	$(".corporateProductivityPanel").show();
	  	dashboard2=val;
	dataFor2='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val();

	}
 	else if(val=='corporateServiceProductivity')
	{
 	var statusFor=$("#statusFor").val();
	 	$(".corporateServiceProductivityPanel").show();
	dashboard2=val;
	dataFor2='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+corporateID;

	}
 	else if(val=='corporateLocationProductivity')
	{
	var statusFor=$("#statusFor").val();
	  	$(".corporateLocationProductivityPanel").show();
	  	dashboard2=val;
	dataFor2='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&corporateName="+corporateID1+"&serviceName="+serviceId2;
	
	}
 	else if(val=='corporateServiceSpecialityProductivity')
	{
	var statusFor=$("#statusFor").val();
	  	$(".corporateServiceSpecialityProductivityPanel").show();
	  	dashboard2=val;
	dataFor2='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+serviceId2+"&locationName="+locationNameForService2+"&corporateName="+corporateID1;
	
	}
 	else if(val=='corporateSpecialityDoctorProductivity')
	{
	var statusFor=$("#statusFor").val();
	  	$(".corporateServiceDoctorProductivityPanel").show();
	  	dashboard2=val;
	dataFor2='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+serviceId2+"&locationName="+speciality1;
	}
 	else if(val=='specialitysToDocProductivity')
 	{
 	var statusFor=$("#statusFor").val();
	  	$(".specialitytoDocProductivityPanel").show();
	  	dashboard2=val;
	dataFor2='Table';
	 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/dataCounterStatus.action?fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&status="+status+"&tableFor="+val+"&statusFor="+statusFor+"&fromTime="+$("#fromTime").val()+"&toTime="+$("#toTime").val()+"&serviceName="+serviceId2+"&locationName="+locationNameForService2;
 	}
	
    $.ajax( {
 	type :"post",
 	url : url1,
 	success : function(statusdata) 
 	{    
 	$("#"+id).html(statusdata);
 	 
 	},
 	error : function() {
 	alert("error");
 	}
 	});
    
 }
 
 function getData(Id, status, filterFlag, flag1,flag2)
 {
 	var fromDate='';
 	var toDate='';
 	var fromTime='';
 	var toTime='';
 	var statusFor='';
 	var location='';
 	var productivityLimit='';
 	var service_name='';
 	var url11='';
 	if(status=='On Time' || status=='Off Time')
 	{
 	corporateID=corporateID1;
 	locationNameForService1=locationNameForService2;
 	accountManagerName1=accountManagerName2;
 	serviceId1=serviceId2;
 	speciality=speciality1;
 	
 	}
 	if(filterFlag=='service')
 	{
 	fromDate=$('#sdate').val();
 	toDate=$('#edate').val();
 	fromTime=$("#fromTime").val();
 	toTime=$("#toTime").val();
 	statusFor=$("#statusFor").val();
 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/beforeCPSView.action?minDateValue="+fromDate+"&maxDateValue="+toDate+"&services="+Id+"&fromTime="+fromTime+"&toTime="+toTime+"&status_type="+status+"&corp_name="+corporateID+"&med_location="+locationNameForService1+"&ac_manager="+accountManagerName1;
 	}
 	else if(filterFlag=='location')
 	{
 	fromDate=$('#sdate').val();
 	toDate=$('#edate').val();
 	fromTime=$("#fromTime").val();
 	toTime=$("#toTime").val();
 	statusFor=$("#statusFor").val();
 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/beforeCPSView.action?minDateValue="+fromDate+"&maxDateValue="+toDate+"&services="+serviceId1+"&fromTime="+fromTime+"&toTime="+toTime+"&status_type="+status+"&med_location="+Id+"&corp_name="+corporateID+"&ac_manager="+accountManagerName1;
 	}
 	else if(filterFlag=='speciality')
 	{
 	fromDate=$('#sdate').val();
 	toDate=$('#edate').val();
 	fromTime=$("#fromTime").val();
 	toTime=$("#toTime").val();
 	statusFor=$("#statusFor").val();
 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/beforeCPSView.action?minDateValue="+fromDate+"&maxDateValue="+toDate+"&services="+serviceId1+"&fromTime="+fromTime+"&toTime="+toTime+"&status_type="+status+"&med_location="+locationNameForService1+"&spec="+Id+"&corp_name="+corporateID;
 	}
 	else if(filterFlag=='doctor')
 	{
 	
 	fromDate=$('#sdate').val();
 	toDate=$('#edate').val();
 	fromTime=$("#fromTime").val();
 	toTime=$("#toTime").val();
 	statusFor=$("#statusFor").val();
 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/beforeCPSView.action?minDateValue="+fromDate+"&maxDateValue="+toDate+"&services="+serviceId1+"&fromTime="+fromTime+"&toTime="+toTime+"&status_type="+status+"&spec="+speciality+"&pack="+Id;
 	}
 	else if(filterFlag=='corporate')
 	{
 	fromDate=$('#sdate').val();
 	toDate=$('#edate').val();
 	fromTime=$("#fromTime").val();
 	toTime=$("#toTime").val();
 	statusFor=$("#statusFor").val();
 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/beforeCPSView.action?minDateValue="+fromDate+"&maxDateValue="+toDate+"&corp_name="+Id+"&fromTime="+fromTime+"&toTime="+toTime+"&status_type="+status+"&ac_manager="+accountManagerName1+"&med_location="+locationNameForService1;
 	}
 	else if(filterFlag=='ser_manager')
 	{
 	fromDate=$('#sdate').val();
 	toDate=$('#edate').val();
 	fromTime=$("#fromTime").val();
 	toTime=$("#toTime").val();
 	statusFor=$("#statusFor").val();
 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/beforeCPSView.action?minDateValue="+fromDate+"&maxDateValue="+toDate+"&service_manager="+Id+"&fromTime="+fromTime+"&toTime="+toTime+"&status_type="+status+"&services="+serviceId1+"&ac_manager="+accountManagerName1+"&med_location="+locationNameForService1;
 	}
 	else if(filterFlag=='ac_manager')
 	{
 	fromDate=$('#sdate').val();
 	toDate=$('#edate').val();
 	fromTime=$("#fromTime").val();
 	toTime=$("#toTime").val();
 	statusFor=$("#statusFor").val();
 	url1="view/Over2Cloud/CoprporatePatient/Dashboard_Pages/beforeCPSView.action?minDateValue="+fromDate+"&maxDateValue="+toDate+"&ac_manager="+Id+"&fromTime="+fromTime+"&toTime="+toTime+"&status_type="+status+"&services="+serviceId1;
 	}
 	corporateID=null;
 	serviceId1=null;
 	speciality=null;
 	locationNameForService1=null;
 	accountManagerName1=null;
 	 $("#confirmEscalationDialog").dialog({
 	width : 1150,
 	height : 500
 	});
 	$("#confirmEscalationDialog").dialog('open');
 	$("#confirmingEscalation").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
  	$.ajax({
 	type : "post",
 	url : url1,
 	  	success : function(feedbackdata)
 	{
 	$("#confirmingEscalation").html(feedbackdata);
 	},
 	error : function()
 	{
 	alert("error");
 	}
 	});
  	
 }