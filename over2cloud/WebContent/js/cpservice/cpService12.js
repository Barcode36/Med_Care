//Print
function printData()
{
		
	var ids = $("#gridedittableeee").jqGrid('getGridParam','selarrrow');
	
	if(ids.length==0)	
	{
		alert("Please Select Atleast One Row.");
	}
	else
	{
		$("#printSelect").dialog('open');
		$("#printSelect").dialog({title: 'Print Data',width: 700,height: 500});
		$("#printSelect").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$("#printSelect").load("/over2cloud/view/Over2Cloud/CorporatePatientServices/cpservices/printCartPage.action?complaintid="+ids+"&dashFor=gridPrint" );
	}
	
}
//DownLoad Excel
function downloadFeedStatusORD(){

		
	 	$("#downloadExcel").dialog({title: 'Check Column',width: 350,height: 600});
		$("#downloadExcel").dialog('open');
		$("#downloadExcel").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");//maxDateValue,minDateValue,feed_status,corp_type,corp_name,services,ac_manager,med_location,patienceSearch,wildSearch

		$.ajax({
		    type : "post",
		    url  : "view/Over2Cloud/CorporatePatientServices/cpservices/beforeReExcelDownload.action?minDateValue="+$("#minDateValue").val()+"&maxDateValue="+$("#maxDateValue").val()+"&feed_status="+$("#feed_status").val()+"&corp_type="+$("#corp_typee").val()+"&patienceSearch="+$("#patienceSearch").val()+"&corp_name="+$("#corp_namee").val()+"&services="+$("#servicese").val()+"&ac_manager="+$("#ac_managerr").val()+"&med_location="+$("#med_locationn").val()+"&wildSearch="+$("#wildSearch").val(),
		    success : function(data) 
		    {
	 	$("#downloadExcel").html(data);
		},
		   error: function() {
		        alert("error");
		    }
		 });
		
	 }


corporatePatientView("load");
getStatusCounter();

function getStatusCounter()
{
	  $.ajax
	({
	type : "post",
	url  : "view/Over2Cloud/CorporatePatientServices/cpservices/getCounterStatus.action?minDateValue="+$("#minDateValue").val()+"&maxDateValue="+$("#maxDateValue").val(),
	async:false,
	success : function(data)
	{
		console.log(data);
		if(data.length>0){
			var total=0;
			$("#statusCounter").empty();
			var temp="";
			if(data[0].Stage1>0)
			{
			temp+='<div class="TitleBorder"><h1>Stage-1</h1><div class="circle">'+data[0].Stage1+'</div></div>';
			total+=parseInt(data[0].Stage1);
			}
			if(data[0].Stage2>0)
			{
			temp+='<div class="TitleBorder"><h1>Stage-2</h1><div class="circle">'+data[0].Stage2+'</div></div>';
			total+=parseInt(data[0].Stage2);
			}
			if(data[0].Resolved>0)
			{
			temp+='<div class="TitleBorder"><h1>Resolved</h1><div class="circle">'+data[0].Resolved+'</div></div>';
			total+=parseInt(data[0].Resolved);
			}
			if(data[1].EHC>0)
			{
			temp+='<div class="TitleBorderSer"><h1>EHC</h1><div class="circleSer">'+data[1].EHC+'</div></div>';
			total+=parseInt(data[1].EHC);
			}
			if(data[1].OPD>0)
			{
			temp+='<div class="TitleBorderSer"><h1>OPD</h1><div class="circleSer">'+data[1].OPD+'</div></div>';
			total+=parseInt(data[1].OPD);
			}
			if(data[1].Laboratory>0)
			{
			temp+='<div class="TitleBorderSer"><h1>Laboratory</h1><div class="circleSer">'+data[1].Laboratory+'</div></div>';
			total+=parseInt(data[1].Laboratory);
			}
			if(data[1].Radiology>0)
			{
			temp+='<div class="TitleBorderSer"><h1>Radiology</h1><div class="circleSer">'+data[1].Radiology+'</div></div>';
			total+=parseInt(data[1].Radiology);
			}
			if(data[1].DayCare>0)
			{
			temp+='<div class="TitleBorderSer"><h1>DayCare</h1><div class="circleSer">'+data[1].DayCare+'</div></div>';
			total+=parseInt(data[1].DayCare);
			}
			if(data[1].Emergency>0)
			{
			temp+='<div class="TitleBorderSer"><h1>Emergency</h1><div class="circleSer">'+data[1].Emergency+'</div></div>';
			total+=parseInt(data[1].Emergency);
			}
			if(data[1].IPD>0)
			{
			temp+='<div class="TitleBorderSer"><h1>IPD</h1><div class="circleSer">'+data[1].IPD+'</div></div>';
			total+=parseInt(data[1].IPD);
			}
			
			
			
			$("#statusCounter").append(temp);
		}
	
	},
	error : function()
	{
	alert("Error on data fetch");
	} 
	}); 
	
}




function corporatePatientView(flag)
{    
	 	$.ajax({
	    type : "post",
	    async:false,
	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/beforeCPSView.action?minDateValue="+$("#minDateValue").val()+"&maxDateValue="+$("#maxDateValue").val(),
	    success : function(feeddraft) {
	 		if(flag=='load')
	 		{
	 		$("#gridDiv").css('width','100%');
	 		//$("#CPSAddID").hide();
	 		}
	 		
            $("#"+"viewCPS").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
	 	
}
newEntryCPS();
function newEntryCPS()
{   var addID = $("#addID").val();
	if(addID=='o')
	{
	$.ajax({
	    type : "post",
	    async:false,
	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/berforeCPSAdd.action",
	    success : function(feeddraft) {
		//$("#gridDiv").css('width','65%');
		//$("#gridDiv").css('height','100%');
		$("#CPSAdd").show().css('overflow');
        $("#"+"CPSAdd").html(feeddraft);
        $("#addID").val("c");
	},
	   error: function() 
	   {
            alert("error");
        }
	 });
	}
	else
	{
		$("#gridDiv").css('width','100%');
		$("#CPSAdd").hide();
		$("#addID").val("o");
	}
}
function corporatePatientAdd()
{    
	 	$.ajax({
	    type : "post",
	    async:false,
	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/berforeCPSAdd.action",
	    success : function(feeddraft) {
       $("#"+"CPSAdd").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function fillValue(id, value)
{
	if(value == 'D')
	{
	document.getElementById(id).style.display ="block";          
	}
	else
	{
		document.getElementById(id).style.display ="none";  
	}
}
function reset(formId)
{
	$('#'+formId).trigger("reset");

    for(var i=110; i<=118; i++)
    {
    	document.getElementById(i).style.display ="none";
        document.getElementById("fillcoltype").style.display ="none";
        document.getElementById("fillcoltype"+i).style.display ="none";
    }
    
	
	document.getElementById(id).style.display ="block";  
}

/*function resetForm(formId)
{
	$('#'+formId).trigger("reset");
	//$("#"+"addServicefields").html("");

}*/

//Faisal 03-09-2015
function getCity(state,div){
	
	$.ajax({
	   type : "post",
	   url : "view/Over2Cloud/CorporatePatientServices/cpservices/getStateList.action?stateId="+state,
	   success : function(data) {
	   	$('#'+div+' option').remove();
	$('#'+div).append($("<option></option>").attr("value",-1).text('Select State'));
	$(data).each(function(index)
	{
	  $('#'+div).append($("<option></option>").attr("value",this.NAME).text(this.NAME));
	});
	},
	  error: function() {
	       alert("error");
	   }
	});
	}
	function getCity2(state,div){
	$.ajax({
	   type : "post",
	   url : "view/Over2Cloud/CorporatePatientServices/cpservices/getCityList.action?stateId="+state,
	   success : function(data) {
	   	$('#'+div+' option').remove();
	$('#'+div).append($("<option></option>").attr("value",-1).text('Select City'));
	$(data).each(function(index)
	{
	  $('#'+div).append($("<option></option>").text("value",this.NAME).text(this.NAME));
	});
	},
	  error: function() {
	       alert("error");
	   }
	});
	}
//end
function getSettingPage()
{  
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/CorporatePatientServices/cpservices/getSettingPageView.action",
	    success : function(feeddraft) {
	    	$("#serviceConf").dialog({title: 'Configure Fields',width: 1200,height: 600});
	    	$("#serviceConf").dialog('open');
	    	$("#serviceConf").html(feeddraft);
	},
	   error: function() {
         alert("error");
     }
	 });
}
function resetForm(formId)
{
	$('#'+formId).trigger("reset");
	
}
function getMyServiceDet(val){
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/CorporatePatientServices/cpservices/getSettingPageView.action?headingName="+val,
	    success : function(feeddraft) {
	    	
	    	$("#serviceConf").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function addConfigurePage()
{
    $.ajax({

            type: "post",
            url : "view/Over2Cloud/CorporatePatientServices/cpservices/getSettingPageAdd.action",
            success :function(data)
            {
              $("#serviceConf").html(data);
            },
            error : function()
            {
                  alert("error in add");
            }
	
           });

}

var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	
function editRow()
{
	var rowid = $("#gridSetting").jqGrid('getGridParam','selarrrow');
    if(rowid.length==0)
    {  
     alert("please select atleast one row....");
     return false;
    }
    else if(rowid.length > 1)
    {
       alert("please select only one row....");
       return false;
    }
	else
		{
		jQuery("#gridSetting").jqGrid('editGridRow', row ,{height:180, width:425,reloadAfterSubmit:true,closeAfterEdit:true,top:0,left:350,modal:false});
		}
    
}

function deleteRow()
{
	var rowid = $("#gridSetting").jqGrid('getGridParam','selarrrow');
    if(rowid.length==0)
    {  
     alert("please select atleast one row....");
     return false;
    }
    else if(rowid.length > 1)
    {
       alert("please select only one row....");
       return false;
    }
	else
	{
	$("#gridSetting").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,top:0,left:350,modal:true});
	}
}
function searchRow()
{
	 $("#gridSetting").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function reloadRow()
{
	
	$("#gridSetting").trigger("reloadGrid");
	//var grid = $("#gridedittable");
	//grid.trigger("reloadGrid",[{current:true}]);
}
function getmorefields(id, checked)
{
		
		if(id=='EHC' && checked)
		{
			addDiv("#EHCDiv");
		}
		else if(id=='EHC' && !checked)
		{
			removeDiv("#EHCDiv");
		}
		else if(id=='Radiology' && checked)
		{
			addDiv("#RadiologyDiv");
		}
		else if(id=='Radiology' && !checked)
		{
			removeDiv("#RadiologyDiv");
		}
		else if(id=='OPD' && checked)
		{
			addDiv("#OPDDiv");
		}
		else if(id=='OPD' && !checked)
		{
			removeDiv("#OPDDiv");
		}
		else if(id=='IPD' && checked)
		{
			addDiv("#IPDDiv");
		}
		else if(id=='IPD' && !checked)
		{
			removeDiv("#IPDDiv");
		}
		else if(id=='DayCare' && checked)
		{
			addDiv("#DayCareDiv");
		}
		else if(id=='DayCare' && !checked)
		{
			removeDiv("#DayCareDiv");
		}
		
		else if(id=='Laboratory' && !checked)
		{
			removeDiv("#LaboratoryDiv");
		}
		else if(id=='Emergency' && checked)
		{
			addDiv("#EmergencyDiv");
		}
		else if(id=='Emergency' && !checked)
		{
			removeDiv("#EmergencyDiv");
		}
	}

	
function removeDiv(divId)
{
	$('#addmorefields').find(divId).remove().hide('slow');
}
function addDiv(divId)
{
	
	$(divId ).clone().appendTo( "#addmorefields").show('slow');
	$(divId).css('display','block');
	
}




function getSettingPage()
{  
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/CorporatePatientServices/cpservices/getSettingPageView.action",
	    success : function(feeddraft) {
	    	$("#serviceConf").dialog({title: 'Configure Fields',width: 1200,height: 600});
	    	$("#serviceConf").dialog('open');
	    	$("#serviceConf").html(feeddraft);
	},
	   error: function() {
         alert("error");
     }
	 });
}
function fillServiceField(serviceName)
{   
 var email_id = $("#email_id").val();
 var serviceName = $("#services").val();
 if(serviceName != null && serviceName != '-1')
 {
   $.ajax({
           type:"post",
           url:"view/Over2Cloud/CorporatePatientServices/cpservices/servicesFieldPage.action",
           data: {"serviceName":serviceName},    
           success: function(data)
           {    
         	
	              if(data.length>0)
	              {
                    $("#"+"addServicefields").html(data);
                    //$("#corporatePatienceADD").dialog({title : 'CPS Setting', width:1200, height: 600 });
                   // $("#corporatePatienceADD").dialog('open');
                    var printContents = document.getElementById("CPSAdd").innerHTML;
                    //$("#addServicefields").empty();
                    $("#corporatePatienceADD").html(printContents);
                    //$("#addServicefields").empty();
                    //$("#corporatePatienceADD").html(data);
                    
	              }
                else
             	   $("#"+"addServicefields").html("");

           },
           error:function()
           {

             alert("error in data");
            }

  	    });
 }
 else
 {
 	$("#"+"addServicefields").html("");
 }
}
	

function viewActivityBoardCPS()
{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
			  type : "post",
			    url : "/over2cloud/view/Over2Cloud/CoprporatePatient/BeforeViewActivityBoardCPS.jsp",
			    success : function(data) {
		       		$("#data_part").html(data);
				},
			    error: function() {
		            alert("error");
		        }
		
		 });
}
/******************************************Validation******************************************/

$.subscribe('validate1', function(event,data)
		{ 
			validate(event,data,"pIds");
		});
		$.subscribe('validate2', function(event,data)
		{
			validate(event,data,"qIds");
		});
		$.subscribe('validate3', function(event,data)
		{
			validate(event,data,"rIds");
		});

		function validate(event,data, spanClass)
			{	
			var mystring=$("."+spanClass).text();
			    var fieldtype = mystring.split(",");
			    var pattern = /^\d{10}$/;
			    for(var i=0; i<fieldtype.length; i++)
			    {
			        var fieldsvalues = fieldtype[i].split("#")[0];
			        var fieldsnames = fieldtype[i].split("#")[1];
			        var colType = fieldtype[i].split("#")[2];   //fieldsType[i]=first_name#t
			        var validationType = fieldtype[i].split("#")[3];
			        $("#"+fieldsvalues).css("background-color","");
			        errZone.innerHTML="";
			        if(fieldsvalues!= "" )
			        {
			            if(colType=="D"){
			            if($("#"+fieldsvalues).val()==null || $("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
			            {
			            errZone.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone").fadeIn(); }, 10);
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone").fadeOut(); }, 2000);
			           
			            $("#"+fieldsvalues).focus();
			            //$("#"+fieldsvalues).css("background-color","#ff701a");
			            event.originalEvent.options.submit = false;
			            break;   
			              }
			            }
			            else if(colType =="T"){
			            if(validationType=="n"){
			            var numeric= /^[0-9]+$/;
			            if(!(numeric.test($("#"+fieldsvalues).val()))){
			            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Numeric Values of "+fieldsnames+"</div>";
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone").fadeIn(); }, 10);
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone").fadeOut(); }, 2000);
			           
			            $("#"+fieldsvalues).focus();

			            event.originalEvent.options.submit = false;
			            break;
			              }   
			             }
			            else if(validationType=="an"){
			             var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
			            if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
			            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone").fadeIn(); }, 10);
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone").fadeOut(); }, 2000);
			           
			            $("#"+fieldsvalues).focus();
			           
			            event.originalEvent.options.submit = false;
			            break;
			              }
			            }
			            else if(validationType=="ans"){
			             var allphanumeric = /^[ A-Za-z0-9_@./#&+-]*$/;
			            if(!(allphanumeric.test($("#"+fieldsvalues).val())) || $("#"+fieldsvalues).val()==""){
			            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone").fadeIn(); }, 10);
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone").fadeOut(); }, 2000);
			            $("#"+fieldsvalues).focus();
			         
			            event.originalEvent.options.submit = false;
			            break;
			              }
			            }
			            else if(validationType=="sc"){
				             var allphanumeric = /^[ A-Za-z0-9_@./#&+-]*$/;
				            if($("#"+fieldsvalues).val()==""){
				            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
				            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone").fadeIn(); }, 10);
				            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone").fadeOut(); }, 2000);
				            $("#"+fieldsvalues).focus();
				            $("#"+fieldsvalues).css("background-color","#ff701a");
				            event.originalEvent.options.submit = false;
				            break;
				              }
				            }
			            else if(validationType=="a"){
			            if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val()))){
			            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone").fadeIn(); }, 10);
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone").fadeOut(); }, 2000);
			           
			            $("#"+fieldsvalues).focus();
			            //$("#"+fieldsvalues).css("background-color","#ff701a");
			            event.originalEvent.options.submit = false;
			            break;    
			              }
			             }
			            else if(validationType=="m"){
			           if($("#"+fieldsvalues).val().length > 10 || $("#"+fieldsvalues).val().length < 10)
			            {
			                errZone.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
			               // $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			                $("#"+fieldsvalues).focus();
			                setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone").fadeIn(); }, 10);
				            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone").fadeOut(); }, 2000);
				           
			                event.originalEvent.options.submit = false;
			                break;
			            }
			            else if (!pattern.test($("#"+fieldsvalues).val())) {
			                errZone.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
			                ///$("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			                $("#"+fieldsvalues).focus();
			                setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone").fadeIn(); }, 10);
				            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone").fadeOut(); }, 2000);
				           
			                event.originalEvent.options.submit = false;
			                break;
			             }
			                else if(($("#"+fieldsvalues).val().charAt(0)!="9") && ($("#"+fieldsvalues).val().charAt(0)!="8") && ($("#"+fieldsvalues).val().charAt(0)!="7") && ($("#"+fieldsvalues).val().charAt(0)!="6"))
			             {
			                errZone.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
			               // $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			                $("#"+fieldsvalues).focus();
			                setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone").fadeIn(); }, 10);
				            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone").fadeOut(); }, 2000);
				           
			                event.originalEvent.options.submit = false;
			                break;
			               }
			             }
			             else if(validationType =="e"){
			             if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val())){
			             }else{
			            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
			           // $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			            $("#"+fieldsvalues).focus();
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone").fadeIn(); }, 10);
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone").fadeOut(); }, 2000);
			           
			            event.originalEvent.options.submit = false;
			            break;
			               }
			             }
			             else if(validationType =="w"){
			            
			            
			            
			             }
			           }
			          
			            else if(colType=="TextArea"){
			            if(validationType=="an"){
			            var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
			            if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
			            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone").fadeIn(); }, 10);
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone").fadeOut(); }, 2000);
			           
			            $("#"+fieldsvalues).focus();
			            //$("#"+fieldsvalues).css("background-color","#ff701a");
			            event.originalEvent.options.submit = false;
			            break;
			               }
			             }
			            else if(validationType=="mg"){
			             
			             
			             }   
			            }
			            else if(colType=="Time"){
			            if($("#"+fieldsvalues).val()=="")
			            {
			            errZone.innerHTML="<div class='user_form_inputError2'>Please Select Time "+fieldsnames+" Value </div>";
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone").fadeIn(); }, 10);
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone").fadeOut(); }, 2000);
			           
			            $("#"+fieldsvalues).focus();
			            //$("#"+fieldsvalues).css("background-color","#ff701a");
			            event.originalEvent.options.submit = false;
			            break;   
			              }   
			            }
			            else if(colType=="Date"){
			            if($("#"+fieldsvalues).val()=="")
			            {
			            errZone.innerHTML="<div class='user_form_inputError2'>Please Select Date "+fieldsnames+" Value  </div>";
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ff701a"), $("#errZone").fadeIn(); }, 10);
			            setTimeout(function(){ $("#"+fieldsvalues).css("background-color","#ffffff"), $("#errZone").fadeOut(); }, 2000);
			           
			            $("#"+fieldsvalues).focus();
			            //$("#"+fieldsvalues).css("background-color","#ff701a");
			            event.originalEvent.options.submit = false;
			            break;   
			              }
			             } 
			        }else{$("#completionResult").dialog('open');}
			        	
			    }       
			}



	

/************************************************************************************************/

/*! jQuery UI - v1.9.2 - 2012-11-23
* http://jqueryui.com
* Includes: jquery.ui.resizable.js
* Copyright 2012 jQuery Foundation and other contributors; Licensed MIT */
//(function(e,t){e.widget("ui.resizable",e.ui.mouse,{version:"1.9.2",widgetEventPrefix:"resize",options:{alsoResize:!1,animate:!1,animateDuration:"slow",animateEasing:"swing",aspectRatio:!1,autoHide:!1,containment:!1,ghost:!1,grid:!1,handles:"e,s,se",helper:!1,maxHeight:null,maxWidth:null,minHeight:10,minWidth:10,zIndex:1e3},_create:function(){var t=this,n=this.options;this.element.addClass("ui-resizable"),e.extend(this,{_aspectRatio:!!n.aspectRatio,aspectRatio:n.aspectRatio,originalElement:this.element,_proportionallyResizeElements:[],_helper:n.helper||n.ghost||n.animate?n.helper||"ui-resizable-helper":null}),this.element[0].nodeName.match(/canvas|textarea|input|select|button|img/i)&&(this.element.wrap(e('<div class="ui-wrapper" style="overflow: hidden;"></div>').css({position:this.element.css("position"),width:this.element.outerWidth(),height:this.element.outerHeight(),top:this.element.css("top"),left:this.element.css("left")})),this.element=this.element.parent().data("resizable",this.element.data("resizable")),this.elementIsWrapper=!0,this.element.css({marginLeft:this.originalElement.css("marginLeft"),marginTop:this.originalElement.css("marginTop"),marginRight:this.originalElement.css("marginRight"),marginBottom:this.originalElement.css("marginBottom")}),this.originalElement.css({marginLeft:0,marginTop:0,marginRight:0,marginBottom:0}),this.originalResizeStyle=this.originalElement.css("resize"),this.originalElement.css("resize","none"),this._proportionallyResizeElements.push(this.originalElement.css({position:"static",zoom:1,display:"block"})),this.originalElement.css({margin:this.originalElement.css("margin")}),this._proportionallyResize()),this.handles=n.handles||(e(".ui-resizable-handle",this.element).length?{n:".ui-resizable-n",e:".ui-resizable-e",s:".ui-resizable-s",w:".ui-resizable-w",se:".ui-resizable-se",sw:".ui-resizable-sw",ne:".ui-resizable-ne",nw:".ui-resizable-nw"}:"e,s,se");if(this.handles.constructor==String){this.handles=="all"&&(this.handles="n,e,s,w,se,sw,ne,nw");var r=this.handles.split(",");this.handles={};for(var i=0;i<r.length;i++){var s=e.trim(r[i]),o="ui-resizable-"+s,u=e('<div class="ui-resizable-handle '+o+'"></div>');u.css({zIndex:n.zIndex}),"se"==s&&u.addClass("ui-icon ui-icon-gripsmall-diagonal-se"),this.handles[s]=".ui-resizable-"+s,this.element.append(u)}}this._renderAxis=function(t){t=t||this.element;for(var n in this.handles){this.handles[n].constructor==String&&(this.handles[n]=e(this.handles[n],this.element).show());if(this.elementIsWrapper&&this.originalElement[0].nodeName.match(/textarea|input|select|button/i)){var r=e(this.handles[n],this.element),i=0;i=/sw|ne|nw|se|n|s/.test(n)?r.outerHeight():r.outerWidth();var s=["padding",/ne|nw|n/.test(n)?"Top":/se|sw|s/.test(n)?"Bottom":/^e$/.test(n)?"Right":"Left"].join("");t.css(s,i),this._proportionallyResize()}if(!e(this.handles[n]).length)continue}},this._renderAxis(this.element),this._handles=e(".ui-resizable-handle",this.element).disableSelection(),this._handles.mouseover(function(){if(!t.resizing){if(this.className)var e=this.className.match(/ui-resizable-(se|sw|ne|nw|n|e|s|w)/i);t.axis=e&&e[1]?e[1]:"se"}}),n.autoHide&&(this._handles.hide(),e(this.element).addClass("ui-resizable-autohide").mouseenter(function(){if(n.disabled)return;e(this).removeClass("ui-resizable-autohide"),t._handles.show()}).mouseleave(function(){if(n.disabled)return;t.resizing||(e(this).addClass("ui-resizable-autohide"),t._handles.hide())})),this._mouseInit()},_destroy:function(){this._mouseDestroy();var t=function(t){e(t).removeClass("ui-resizable ui-resizable-disabled ui-resizable-resizing").removeData("resizable").removeData("ui-resizable").unbind(".resizable").find(".ui-resizable-handle").remove()};if(this.elementIsWrapper){t(this.element);var n=this.element;this.originalElement.css({position:n.css("position"),width:n.outerWidth(),height:n.outerHeight(),top:n.css("top"),left:n.css("left")}).insertAfter(n),n.remove()}return this.originalElement.css("resize",this.originalResizeStyle),t(this.originalElement),this},_mouseCapture:function(t){var n=!1;for(var r in this.handles)e(this.handles[r])[0]==t.target&&(n=!0);return!this.options.disabled&&n},_mouseStart:function(t){var r=this.options,i=this.element.position(),s=this.element;this.resizing=!0,this.documentScroll={top:e(document).scrollTop(),left:e(document).scrollLeft()},(s.is(".ui-draggable")||/absolute/.test(s.css("position")))&&s.css({position:"absolute",top:i.top,left:i.left}),this._renderProxy();var o=n(this.helper.css("left")),u=n(this.helper.css("top"));r.containment&&(o+=e(r.containment).scrollLeft()||0,u+=e(r.containment).scrollTop()||0),this.offset=this.helper.offset(),this.position={left:o,top:u},this.size=this._helper?{width:s.outerWidth(),height:s.outerHeight()}:{width:s.width(),height:s.height()},this.originalSize=this._helper?{width:s.outerWidth(),height:s.outerHeight()}:{width:s.width(),height:s.height()},this.originalPosition={left:o,top:u},this.sizeDiff={width:s.outerWidth()-s.width(),height:s.outerHeight()-s.height()},this.originalMousePosition={left:t.pageX,top:t.pageY},this.aspectRatio=typeof r.aspectRatio=="number"?r.aspectRatio:this.originalSize.width/this.originalSize.height||1;var a=e(".ui-resizable-"+this.axis).css("cursor");return e("body").css("cursor",a=="auto"?this.axis+"-resize":a),s.addClass("ui-resizable-resizing"),this._propagate("start",t),!0},_mouseDrag:function(e){var t=this.helper,n=this.options,r={},i=this,s=this.originalMousePosition,o=this.axis,u=e.pageX-s.left||0,a=e.pageY-s.top||0,f=this._change[o];if(!f)return!1;var l=f.apply(this,[e,u,a]);this._updateVirtualBoundaries(e.shiftKey);if(this._aspectRatio||e.shiftKey)l=this._updateRatio(l,e);return l=this._respectSize(l,e),this._propagate("resize",e),t.css({top:this.position.top+"px",left:this.position.left+"px",width:this.size.width+"px",height:this.size.height+"px"}),!this._helper&&this._proportionallyResizeElements.length&&this._proportionallyResize(),this._updateCache(l),this._trigger("resize",e,this.ui()),!1},_mouseStop:function(t){this.resizing=!1;var n=this.options,r=this;if(this._helper){var i=this._proportionallyResizeElements,s=i.length&&/textarea/i.test(i[0].nodeName),o=s&&e.ui.hasScroll(i[0],"left")?0:r.sizeDiff.height,u=s?0:r.sizeDiff.width,a={width:r.helper.width()-u,height:r.helper.height()-o},f=parseInt(r.element.css("left"),10)+(r.position.left-r.originalPosition.left)||null,l=parseInt(r.element.css("top"),10)+(r.position.top-r.originalPosition.top)||null;n.animate||this.element.css(e.extend(a,{top:l,left:f})),r.helper.height(r.size.height),r.helper.width(r.size.width),this._helper&&!n.animate&&this._proportionallyResize()}return e("body").css("cursor","auto"),this.element.removeClass("ui-resizable-resizing"),this._propagate("stop",t),this._helper&&this.helper.remove(),!1},_updateVirtualBoundaries:function(e){var t=this.options,n,i,s,o,u;u={minWidth:r(t.minWidth)?t.minWidth:0,maxWidth:r(t.maxWidth)?t.maxWidth:Infinity,minHeight:r(t.minHeight)?t.minHeight:0,maxHeight:r(t.maxHeight)?t.maxHeight:Infinity};if(this._aspectRatio||e)n=u.minHeight*this.aspectRatio,s=u.minWidth/this.aspectRatio,i=u.maxHeight*this.aspectRatio,o=u.maxWidth/this.aspectRatio,n>u.minWidth&&(u.minWidth=n),s>u.minHeight&&(u.minHeight=s),i<u.maxWidth&&(u.maxWidth=i),o<u.maxHeight&&(u.maxHeight=o);this._vBoundaries=u},_updateCache:function(e){var t=this.options;this.offset=this.helper.offset(),r(e.left)&&(this.position.left=e.left),r(e.top)&&(this.position.top=e.top),r(e.height)&&(this.size.height=e.height),r(e.width)&&(this.size.width=e.width)},_updateRatio:function(e,t){var n=this.options,i=this.position,s=this.size,o=this.axis;return r(e.height)?e.width=e.height*this.aspectRatio:r(e.width)&&(e.height=e.width/this.aspectRatio),o=="sw"&&(e.left=i.left+(s.width-e.width),e.top=null),o=="nw"&&(e.top=i.top+(s.height-e.height),e.left=i.left+(s.width-e.width)),e},_respectSize:function(e,t){var n=this.helper,i=this._vBoundaries,s=this._aspectRatio||t.shiftKey,o=this.axis,u=r(e.width)&&i.maxWidth&&i.maxWidth<e.width,a=r(e.height)&&i.maxHeight&&i.maxHeight<e.height,f=r(e.width)&&i.minWidth&&i.minWidth>e.width,l=r(e.height)&&i.minHeight&&i.minHeight>e.height;f&&(e.width=i.minWidth),l&&(e.height=i.minHeight),u&&(e.width=i.maxWidth),a&&(e.height=i.maxHeight);var c=this.originalPosition.left+this.originalSize.width,h=this.position.top+this.size.height,p=/sw|nw|w/.test(o),d=/nw|ne|n/.test(o);f&&p&&(e.left=c-i.minWidth),u&&p&&(e.left=c-i.maxWidth),l&&d&&(e.top=h-i.minHeight),a&&d&&(e.top=h-i.maxHeight);var v=!e.width&&!e.height;return v&&!e.left&&e.top?e.top=null:v&&!e.top&&e.left&&(e.left=null),e},_proportionallyResize:function(){var t=this.options;if(!this._proportionallyResizeElements.length)return;var n=this.helper||this.element;for(var r=0;r<this._proportionallyResizeElements.length;r++){var i=this._proportionallyResizeElements[r];if(!this.borderDif){var s=[i.css("borderTopWidth"),i.css("borderRightWidth"),i.css("borderBottomWidth"),i.css("borderLeftWidth")],o=[i.css("paddingTop"),i.css("paddingRight"),i.css("paddingBottom"),i.css("paddingLeft")];this.borderDif=e.map(s,function(e,t){var n=parseInt(e,10)||0,r=parseInt(o[t],10)||0;return n+r})}i.css({height:n.height()-this.borderDif[0]-this.borderDif[2]||0,width:n.width()-this.borderDif[1]-this.borderDif[3]||0})}},_renderProxy:function(){var t=this.element,n=this.options;this.elementOffset=t.offset();if(this._helper){this.helper=this.helper||e('<div style="overflow:hidden;"></div>');var r=e.ui.ie6?1:0,i=e.ui.ie6?2:-1;this.helper.addClass(this._helper).css({width:this.element.outerWidth()+i,height:this.element.outerHeight()+i,position:"absolute",left:this.elementOffset.left-r+"px",top:this.elementOffset.top-r+"px",zIndex:++n.zIndex}),this.helper.appendTo("body").disableSelection()}else this.helper=this.element},_change:{e:function(e,t,n){return{width:this.originalSize.width+t}},w:function(e,t,n){var r=this.options,i=this.originalSize,s=this.originalPosition;return{left:s.left+t,width:i.width-t}},n:function(e,t,n){var r=this.options,i=this.originalSize,s=this.originalPosition;return{top:s.top+n,height:i.height-n}},s:function(e,t,n){return{height:this.originalSize.height+n}},se:function(t,n,r){return e.extend(this._change.s.apply(this,arguments),this._change.e.apply(this,[t,n,r]))},sw:function(t,n,r){return e.extend(this._change.s.apply(this,arguments),this._change.w.apply(this,[t,n,r]))},ne:function(t,n,r){return e.extend(this._change.n.apply(this,arguments),this._change.e.apply(this,[t,n,r]))},nw:function(t,n,r){return e.extend(this._change.n.apply(this,arguments),this._change.w.apply(this,[t,n,r]))}},_propagate:function(t,n){e.ui.plugin.call(this,t,[n,this.ui()]),t!="resize"&&this._trigger(t,n,this.ui())},plugins:{},ui:function(){return{originalElement:this.originalElement,element:this.element,helper:this.helper,position:this.position,size:this.size,originalSize:this.originalSize,originalPosition:this.originalPosition}}}),e.ui.plugin.add("resizable","alsoResize",{start:function(t,n){var r=e(this).data("resizable"),i=r.options,s=function(t){e(t).each(function(){var t=e(this);t.data("resizable-alsoresize",{width:parseInt(t.width(),10),height:parseInt(t.height(),10),left:parseInt(t.css("left"),10),top:parseInt(t.css("top"),10)})})};typeof i.alsoResize=="object"&&!i.alsoResize.parentNode?i.alsoResize.length?(i.alsoResize=i.alsoResize[0],s(i.alsoResize)):e.each(i.alsoResize,function(e){s(e)}):s(i.alsoResize)},resize:function(t,n){var r=e(this).data("resizable"),i=r.options,s=r.originalSize,o=r.originalPosition,u={height:r.size.height-s.height||0,width:r.size.width-s.width||0,top:r.position.top-o.top||0,left:r.position.left-o.left||0},a=function(t,r){e(t).each(function(){var t=e(this),i=e(this).data("resizable-alsoresize"),s={},o=r&&r.length?r:t.parents(n.originalElement[0]).length?["width","height"]:["width","height","top","left"];e.each(o,function(e,t){var n=(i[t]||0)+(u[t]||0);n&&n>=0&&(s[t]=n||null)}),t.css(s)})};typeof i.alsoResize=="object"&&!i.alsoResize.nodeType?e.each(i.alsoResize,function(e,t){a(e,t)}):a(i.alsoResize)},stop:function(t,n){e(this).removeData("resizable-alsoresize")}}),e.ui.plugin.add("resizable","animate",{stop:function(t,n){var r=e(this).data("resizable"),i=r.options,s=r._proportionallyResizeElements,o=s.length&&/textarea/i.test(s[0].nodeName),u=o&&e.ui.hasScroll(s[0],"left")?0:r.sizeDiff.height,a=o?0:r.sizeDiff.width,f={width:r.size.width-a,height:r.size.height-u},l=parseInt(r.element.css("left"),10)+(r.position.left-r.originalPosition.left)||null,c=parseInt(r.element.css("top"),10)+(r.position.top-r.originalPosition.top)||null;r.element.animate(e.extend(f,c&&l?{top:c,left:l}:{}),{duration:i.animateDuration,easing:i.animateEasing,step:function(){var n={width:parseInt(r.element.css("width"),10),height:parseInt(r.element.css("height"),10),top:parseInt(r.element.css("top"),10),left:parseInt(r.element.css("left"),10)};s&&s.length&&e(s[0]).css({width:n.width,height:n.height}),r._updateCache(n),r._propagate("resize",t)}})}}),e.ui.plugin.add("resizable","containment",{start:function(t,r){var i=e(this).data("resizable"),s=i.options,o=i.element,u=s.containment,a=u instanceof e?u.get(0):/parent/.test(u)?o.parent().get(0):u;if(!a)return;i.containerElement=e(a);if(/document/.test(u)||u==document)i.containerOffset={left:0,top:0},i.containerPosition={left:0,top:0},i.parentData={element:e(document),left:0,top:0,width:e(document).width(),height:e(document).height()||document.body.parentNode.scrollHeight};else{var f=e(a),l=[];e(["Top","Right","Left","Bottom"]).each(function(e,t){l[e]=n(f.css("padding"+t))}),i.containerOffset=f.offset(),i.containerPosition=f.position(),i.containerSize={height:f.innerHeight()-l[3],width:f.innerWidth()-l[1]};var c=i.containerOffset,h=i.containerSize.height,p=i.containerSize.width,d=e.ui.hasScroll(a,"left")?a.scrollWidth:p,v=e.ui.hasScroll(a)?a.scrollHeight:h;i.parentData={element:a,left:c.left,top:c.top,width:d,height:v}}},resize:function(t,n){var r=e(this).data("resizable"),i=r.options,s=r.containerSize,o=r.containerOffset,u=r.size,a=r.position,f=r._aspectRatio||t.shiftKey,l={top:0,left:0},c=r.containerElement;c[0]!=document&&/static/.test(c.css("position"))&&(l=o),a.left<(r._helper?o.left:0)&&(r.size.width=r.size.width+(r._helper?r.position.left-o.left:r.position.left-l.left),f&&(r.size.height=r.size.width/r.aspectRatio),r.position.left=i.helper?o.left:0),a.top<(r._helper?o.top:0)&&(r.size.height=r.size.height+(r._helper?r.position.top-o.top:r.position.top),f&&(r.size.width=r.size.height*r.aspectRatio),r.position.top=r._helper?o.top:0),r.offset.left=r.parentData.left+r.position.left,r.offset.top=r.parentData.top+r.position.top;var h=Math.abs((r._helper?r.offset.left-l.left:r.offset.left-l.left)+r.sizeDiff.width),p=Math.abs((r._helper?r.offset.top-l.top:r.offset.top-o.top)+r.sizeDiff.height),d=r.containerElement.get(0)==r.element.parent().get(0),v=/relative|absolute/.test(r.containerElement.css("position"));d&&v&&(h-=r.parentData.left),h+r.size.width>=r.parentData.width&&(r.size.width=r.parentData.width-h,f&&(r.size.height=r.size.width/r.aspectRatio)),p+r.size.height>=r.parentData.height&&(r.size.height=r.parentData.height-p,f&&(r.size.width=r.size.height*r.aspectRatio))},stop:function(t,n){var r=e(this).data("resizable"),i=r.options,s=r.position,o=r.containerOffset,u=r.containerPosition,a=r.containerElement,f=e(r.helper),l=f.offset(),c=f.outerWidth()-r.sizeDiff.width,h=f.outerHeight()-r.sizeDiff.height;r._helper&&!i.animate&&/relative/.test(a.css("position"))&&e(this).css({left:l.left-u.left-o.left,width:c,height:h}),r._helper&&!i.animate&&/static/.test(a.css("position"))&&e(this).css({left:l.left-u.left-o.left,width:c,height:h})}}),e.ui.plugin.add("resizable","ghost",{start:function(t,n){var r=e(this).data("resizable"),i=r.options,s=r.size;r.ghost=r.originalElement.clone(),r.ghost.css({opacity:.25,display:"block",position:"relative",height:s.height,width:s.width,margin:0,left:0,top:0}).addClass("ui-resizable-ghost").addClass(typeof i.ghost=="string"?i.ghost:""),r.ghost.appendTo(r.helper)},resize:function(t,n){var r=e(this).data("resizable"),i=r.options;r.ghost&&r.ghost.css({position:"relative",height:r.size.height,width:r.size.width})},stop:function(t,n){var r=e(this).data("resizable"),i=r.options;r.ghost&&r.helper&&r.helper.get(0).removeChild(r.ghost.get(0))}}),e.ui.plugin.add("resizable","grid",{resize:function(t,n){var r=e(this).data("resizable"),i=r.options,s=r.size,o=r.originalSize,u=r.originalPosition,a=r.axis,f=i._aspectRatio||t.shiftKey;i.grid=typeof i.grid=="number"?[i.grid,i.grid]:i.grid;var l=Math.round((s.width-o.width)/(i.grid[0]||1))*(i.grid[0]||1),c=Math.round((s.height-o.height)/(i.grid[1]||1))*(i.grid[1]||1);/^(se|s|e)$/.test(a)?(r.size.width=o.width+l,r.size.height=o.height+c):/^(ne)$/.test(a)?(r.size.width=o.width+l,r.size.height=o.height+c,r.position.top=u.top-c):/^(sw)$/.test(a)?(r.size.width=o.width+l,r.size.height=o.height+c,r.position.left=u.left-l):(r.size.width=o.width+l,r.size.height=o.height+c,r.position.top=u.top-c,r.position.left=u.left-l)}});var n=function(e){return parseInt(e,10)||0},r=function(e){return!isNaN(parseInt(e,10))}})(jQuery);















