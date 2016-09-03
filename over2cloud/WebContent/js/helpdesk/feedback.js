	
		
var intvrl;

 
function refreshActivityBoard()
{
	  intvrl=setInterval("HDMActivityRefresh()", (20*1000));
} 
function HDMActivityRefresh()
{
	onloadData();
} 
function clearInt()
{
	clearInterval(intvrl);
}
function StopRefresh()
{
////alert("Stop");
	clearInterval(intvrl);
}
function StartRefresh()
{
	////alert("Start");
	refreshActivityBoard();
}
function onloadData(val)
{
 	var category;
	if(typeof val=='undefined')
	 {
		 category='-1';
	 }
	 
	else
	{
		 category=val;
	}
	var searchField = null;
	var taskType = $("#taskType").val();
 	var Dept = $("#dept_id").val();
 	var searchValue = $("#searchValue").val();
 	 var wildSearch= $("#wildSearch").val();
	 if(searchValue!=null && searchValue!='')
	{
 		searchField="feed_brief";
	}
	if(ticketValue!=null && ticketValue!='' && ticketValue!='undefined' && ticketValue!='0')
	{
	 	searchValue=ticketValue;
		searchField="ticket_no";
	}
	 if(wildSearch!=null && wildSearch!='')
	{
		searchValue=wildSearch;
		searchField="All";
	} 
	var toDept = null;
	//var lodgeMode = $("#lodgeMode").val();
	var feedStatus = $("#feedStatus").val();
	var minDateValue = $("#minDateValue").val();
	var maxDateValue = $("#maxDateValue").val();
	var fromTime = $("#fromTime").val();
	var toTime = $("#toTime").val();
	  
	var feedRegUser = $("#feedRegUser").val();

	//var closeMode = $("#closeMode").val();
	var escLevel = $("#escLevel").val();
	var escTAT = $("#escTAT").val();
	//var severityLevel = $("#severityLevel").val();
	var feedId = $("#feedidssss").val();
	
	var advncsearch = document.getElementById("advncSearch").checked;
 	//searchValue= $("#searchValue").val();
	if(feedStatus=='Pending' || feedStatus=='Snooze' || feedStatus=='High Priority' ||feedStatus=='Resolved')
	{
		$(".actionClass").show();
	}
	else
	{
		$(".actionClass").hide();
	}
	    $.ajax
	({
		type : "post",
		url  : "view/Over2Cloud/HelpDeskOver2Cloud/Activity_Board/viewActivityBoardColumn?",
		data : "taskType="+taskType+"&fromDepart=all&toDepart="+toDept+"&lodgeMode=All&feedStatus="+feedStatus+"&minDateValue="+minDateValue+"& maxDateValue="+maxDateValue+"&closeMode=All&escLevel="+escLevel+"&escTAT="+escTAT+"&searchField="+searchField+"&searchString="+searchValue+"&severityLevel=All&fromTime="+fromTime+"&toTime="+toTime+"&regUser="+feedRegUser+"&dept="+Dept+"&category="+category+"&complianId="+feedId+"&advncSearch="+advncsearch,
		success : function(data)
		{
		 	/*$.ajax
			({
				type : "post",
				url  : "view/Over2Cloud/HelpDeskOver2Cloud/Activity_Board/activityBoardCount?",
				data : "taskType="+taskType+"&fromDepart=all&toDepart="+toDept+"&lodgeMode=All&feedStatus="+feedStatus+"&minDateValue="+minDateValue+"& maxDateValue="+maxDateValue+"&closeMode=All&escLevel="+escLevel+"&escTAT="+escTAT+"&searchField="+searchField+"&searchString="+searchValue+"&severityLevel=All&fromTime="+fromTime+"&toTime="+toTime+"&regUser="+feedRegUser+"&dept="+Dept+"&category="+category+"&complianId="+feedId,
				success : function(testdata)
				{
					$("#pendingDiv").val(testdata.Pending);
					$("#snoozeDiv").val(testdata.Snooze);
					$("#HighPriorityDiv").val(testdata.HighPriority);
					$("#ignoreDiv").val(testdata.Ignore);
					$("#resolveDiv").val(testdata.Resolved);
					$("#holdDiv").val(testdata.Hold);
					$("#transferDiv").val(testdata.transfer);
					$("#reopenDiv").val(testdata.reopen);
					
					$("#totalDiv").val(testdata.total);
				 		if(testdata.//alert=='Ok')
						{
				 		  	$("#//alertPopup").show('slow');
				 		  	$("#ticketLabel1").text(testdata.TicketNO);
				 			$("#location1").text(testdata.Location);
				 			$("#requestFor1").text(testdata.RequestFor);
				 			$("#TAT").text(testdata.TAT);
				 		 	
							
						}
				 		
				 		if(testdata.Resolve//alert=='Ok')
						{
				 		 
						$("#resolve//alertPopup").show('slow');
						$("#ticketLabel2").text(testdata.TicketNO2);
			 			$("#location2").text(testdata.Location2);
			 			$("#requestFor2").text(testdata.RequestFor2);
			 			$("#actionTaken").text(testdata.ActionTaken);
			 			
						}
					
				},
				error : function()
				{
					//alert("Error on data fetch");
				} 
			});*/
		 	 	$("#viewDataDiv").html(data);
			 	
			 	var grid = jQuery("#gridedittable");
			grid.trigger("reloadGrid",[{current:true}]);
		},
		error : function()
		{
			////alert("Error on data fetch");
		} 
	});
}
function Reset(id)
{
	var mystring=$(id).text();
    var fieldtype = mystring.split(",");
      for(var i=0; i<fieldtype.length; i++)
    {
       
        var fieldsvalues = fieldtype[i].split("#")[0];
        
        $("#"+fieldsvalues).css("background-color","");
    }

}
      function getDetail(uniqueid,columnName)
      {
          
      	var uniqueid = document.getElementById(uniqueid).value;
      	if(uniqueid!=null && uniqueid!="")
      	   

      	     $.getJSON("/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/ContactDetailViaAjax.action?uid="+uniqueid+"&pageFor=SD&columnName="+columnName,
          	 
          		   function(data){
                     if(data.empName == null){$("#feed_by").val("NA");}
                     else {$("#feed_by").val(data.empName);}
                     
                     if(data.mobOne == null){$("#feed_by_mobno").val("NA");}
                     else {$("#feed_by_mobno").val(data.mobOne); }
                     
                     if(data.emailIdOne == null){$("#feed_by_emailid").val("NA");}
                     else {$("#feed_by_emailid").val(data.emailIdOne);}
                     
                     if(data.empId == null){$("#uniqueid").val("NA");}
                     else {$("#uniqueid").val(data.empId);}
                       
                     if (data.deptName ==null || data.deptName =='' || data.deptName =='NA') {
                  	   
                  	  //  document.getElementById('fielddiv').style.display="none";
              		 //  document.getElementById('selectdiv').style.display="block";
              		   $("#deptname").val("NA");
                  	   $("#bydept_subdept").val("NA");
      			   }
                     else {
                  	//  document.getElementById('fielddiv').style.display="block";
              		  // document.getElementById('selectdiv').style.display="none"; 
              		    $("#deptname").val(data.deptName);
                  	   $("#bydept_subdept").val(data.other2);
      			   }
             });
      }
$.subscribe('validateCompletionTip', function(event,data)
		{
			var mystring=$(".pIds").text();
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
			            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
			            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
			            $("#"+fieldsvalues).focus();
			            $("#"+fieldsvalues).css("background-color","#ff701a");
			            event.originalEvent.options.submit = false;
			            break;   
		              }
		            }
		            else if(colType =="T"){
			            if(validationType=="n")
			            {
				            var numeric= /^[0-9]+$/;
				            if(!(numeric.test($("#"+fieldsvalues).val())))
				             {
					            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Numeric Values of "+fieldsnames+"</div>";
					            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					            $("#"+fieldsvalues).focus();
					            $("#"+fieldsvalues).css("background-color","#ff701a");
					            event.originalEvent.options.submit = false;
					            break;
				              }   
			             }
			            else if(validationType=="an")
			             {
				            var allphanumeric = /^[A-Za-z0-9 ]{1,80}$/;
				            if(!(allphanumeric.test($("#"+fieldsvalues).val())))
				             {
					            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
					            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					            $("#"+fieldsvalues).focus();
					            $("#"+fieldsvalues).css("background-color","#ff701a");
					            event.originalEvent.options.submit = false;
					            break;
				              }
			             }
			            else if(validationType=="a")
			             {
				            if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val())))
				            {
					            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
					            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					            $("#"+fieldsvalues).focus();
					            $("#"+fieldsvalues).css("background-color","#ff701a");
					            event.originalEvent.options.submit = false;
					            break;    
				              }
			             }
			            else if(validationType=="m")
			             {
					            if($("#"+fieldsvalues).val().length > 10 || $("#"+fieldsvalues).val().length < 10)
					             {
					                errZone.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
					                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
					                $("#"+fieldsvalues).focus();
					                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					                event.originalEvent.options.submit = false;
					                break;
					             }
					            else if (!pattern.test($("#"+fieldsvalues).val())) 
					             {
					                errZone.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
					                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
					                $("#"+fieldsvalues).focus();
					                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					                event.originalEvent.options.submit = false;
					                break;
					              }
					             else if(($("#"+fieldsvalues).val().charAt(0)!="9") && ($("#"+fieldsvalues).val().charAt(0)!="8") && ($("#"+fieldsvalues).val().charAt(0)!="7") && ($("#"+fieldsvalues).val().charAt(0)!="6"))
					              {
					                errZone.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
					                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
					                $("#"+fieldsvalues).focus();
					                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					                event.originalEvent.options.submit = false;
					                break;
					              }
			               }
			             else if(validationType =="e")
			               {
						             if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val())){
						             }
						             else{
							            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
							            $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
							            $("#"+fieldsvalues).focus();
							            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
							            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
							            event.originalEvent.options.submit = false;
							            break;
						               }
			             }
			             else if(validationType =="w"){
			             }
			             else if(validationType =="sc"){
			            	  if($("#"+fieldsvalues).val().length < 1)
					             {
					                errZone.innerHTML="<div class='user_form_inputError2'>Enter "+fieldsnames+" !!! </div>";
					                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
					                $("#"+fieldsvalues).focus();
					                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					                event.originalEvent.options.submit = false;
					                break;
					             }
			             }
		            }
		            else if(colType=="TextArea")
		             {
				            if(validationType=="an")
				             {
					            var allphanumeric = /^[A-Za-z0-9 ]{3,50}$/;
					            if(!(allphanumeric.test($("#"+fieldsvalues).val())))
					             {
						            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
						            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
						            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
						            $("#"+fieldsvalues).focus();
						            $("#"+fieldsvalues).css("background-color","#ff701a");
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
				            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
				            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
				            $("#"+fieldsvalues).focus();
				            $("#"+fieldsvalues).css("background-color","#ff701a");
				            event.originalEvent.options.submit = false;
				            break;   
			              }   
		            }
		            else if(colType=="Date"){
			            if($("#"+fieldsvalues).val()=="")
			            {
			            errZone.innerHTML="<div class='user_form_inputError2'>Please Select Date "+fieldsnames+" Value  </div>";
			            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
			            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
			            $("#"+fieldsvalues).focus();
			            $("#"+fieldsvalues).css("background-color","#ff701a");
			            event.originalEvent.options.submit = false;
			            break;   
			              }
		             } 
		        }
		    }     
		  
		});

function getUserByDept(conditionVar_Value,targetid,frontVal) {
	////alert("module  "+conditionVar_Value2);
	 	$.ajax
	 (
	   {
		type :"post",
		url :"/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/getUserByDept.action?conditionVar_Value="+conditionVar_Value,
		success : function(empData){
		$('#'+targetid+' option').remove();
		$('#'+targetid).append($("<option></option>").attr("value",-1).text(frontVal));
    	$(empData).each(function(index)
		{
		   $('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.name));
		});
	    },
	    error : function () {
			//alert("Somthing is wrong to get Data");
		}
	   }
	 ); 
  }




/*function getFeedBreifViaAjax(subCatg)
{
	 // var subCatg = document.getElementById(subCatgId).value;
    $("#scatgid").val(subCatg);
    $("#test").val(subCatg);
     $.getJSON("/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Feedback_Draft/SubCatgDetail.action?subcatg="+subCatg,
  	 function(data){
      	   $("#subCatgId").val(data.id);
      	   $("#tosubdept").val(data.feedtype_id);
      	   $("#feed_brief").val(data.feed_msg);
      	   $("#subDept").val(data.subdept);
      	   $("#fbType").val(data.feedtype);
      	   $("#category").val(data.category);
             if(data.feed_msg == null){
          	   $("#feed_brief").val("NA");
             }
             else {
          	   $("#feed_brief").val(data.feed_msg);
			   }
             
             if(data.addressing_time == null){
          	   $("#resolutionTime").val("00:30");
             }
             else {
          	   $("#resolutionTime").val(data.resolution_time);
			   }
             
             if(data.escalation_time == null){
          	   $("#escalationTime").val("02:00");
             }
             else {
          	   $("#escalationTime").val(data.resolution_time);
			   }
     });
}*/


function viewForm()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/beforeFeedAction.action?feedStatus=Pending&moduleName=HDM",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            //alert("error");
        }
	 });
}


function backOnViewActivityBoard()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Activity_Board/viewActivityBoardHeader.action",
	    success : function(feeddraft) {
       $("#"+"data_part").html(feeddraft);
	},
	   error: function() {
            //alert("error");
        }
	 });
}

function callForm()
{

	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/beforeFeedViaCall.action?empModuleFalgForDeptSubDept=1&lodgeFeedback=1&feedStatus=call&dataFor=HDM",
	    success : function(feeddraft) {
       $("#"+"data_part").html(feeddraft);
	},
	   error: function() {
            //alert("error");
        }
	 });
}

function onlineForm()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/beforeFeedViaOnline.action?empModuleFalgForDeptSubDept=1&lodgeFeedback=1&feedStatus=online&dataFor=HDM",
	    success : function(feeddraft) {
       $("#"+"data_part").html(feeddraft);
	},
	   error: function() {
            //alert("error");
        }
	 });
}


$.subscribe('validateOnlineHDM', function(event,data)
		{
			var mystring=$(".pIds").text();
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
			            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
			            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
			            $("#"+fieldsvalues).focus();
			            $("#"+fieldsvalues).css("background-color","#ff701a");
			            event.originalEvent.options.submit = false;
			            break;   
		              }
		            }
		            else if(colType =="T"){
			            if(validationType=="n")
			            {
				            var numeric= /^[0-9]+$/;
				            if(!(numeric.test($("#"+fieldsvalues).val())))
				             {
					            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Numeric Values of "+fieldsnames+"</div>";
					            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					            $("#"+fieldsvalues).focus();
					            $("#"+fieldsvalues).css("background-color","#ff701a");
					            event.originalEvent.options.submit = false;
					            break;
				              }   
			             }
			            else if(validationType=="an")
			             {
				            var allphanumeric = /^[A-Za-z0-9 ]{1,80}$/;
				            if(!(allphanumeric.test($("#"+fieldsvalues).val())))
				             {
					            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
					            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					            $("#"+fieldsvalues).focus();
					            $("#"+fieldsvalues).css("background-color","#ff701a");
					            event.originalEvent.options.submit = false;
					            break;
				              }
			             }
			            else if(validationType=="a")
			             {
				            if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val())))
				            {
					            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
					            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					            $("#"+fieldsvalues).focus();
					            $("#"+fieldsvalues).css("background-color","#ff701a");
					            event.originalEvent.options.submit = false;
					            break;    
				              }
			             }
			            else if(validationType=="m")
			             {
					            if($("#"+fieldsvalues).val().length > 10 || $("#"+fieldsvalues).val().length < 10)
					             {
					                errZone.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
					                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
					                $("#"+fieldsvalues).focus();
					                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					                event.originalEvent.options.submit = false;
					                break;
					             }
					            else if (!pattern.test($("#"+fieldsvalues).val())) 
					             {
					                errZone.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
					                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
					                $("#"+fieldsvalues).focus();
					                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					                event.originalEvent.options.submit = false;
					                break;
					              }
					             else if(($("#"+fieldsvalues).val().charAt(0)!="9") && ($("#"+fieldsvalues).val().charAt(0)!="8") && ($("#"+fieldsvalues).val().charAt(0)!="7") && ($("#"+fieldsvalues).val().charAt(0)!="6"))
					              {
					                errZone.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
					                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
					                $("#"+fieldsvalues).focus();
					                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					                event.originalEvent.options.submit = false;
					                break;
					              }
			               }
			             else if(validationType =="e")
			               {
						             if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val())){
						             }
						             else{
							            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
							            $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
							            $("#"+fieldsvalues).focus();
							            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
							            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
							            event.originalEvent.options.submit = false;
							            break;
						               }
			             }
			             else if(validationType =="w"){
			             }
			             else if(validationType =="sc"){
			            	  if($("#"+fieldsvalues).val().length < 1)
					             {
					                errZone.innerHTML="<div class='user_form_inputError2'>Enter "+fieldsnames+" !!! </div>";
					                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
					                $("#"+fieldsvalues).focus();
					                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					                event.originalEvent.options.submit = false;
					                break;
					             }
			             }
		            }
		            else if(colType=="TextArea")
		             {
				            if(validationType=="an")
				             {
					            var allphanumeric = /^[A-Za-z0-9 ]{3,50}$/;
					            if(!(allphanumeric.test($("#"+fieldsvalues).val())))
					             {
						            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
						            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
						            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
						            $("#"+fieldsvalues).focus();
						            $("#"+fieldsvalues).css("background-color","#ff701a");
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
				            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
				            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
				            $("#"+fieldsvalues).focus();
				            $("#"+fieldsvalues).css("background-color","#ff701a");
				            event.originalEvent.options.submit = false;
				            break;   
			              }   
		            }
		            else if(colType=="Date"){
			            if($("#"+fieldsvalues).val()=="")
			            {
			            errZone.innerHTML="<div class='user_form_inputError2'>Please Select Date "+fieldsnames+" Value  </div>";
			            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
			            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
			            $("#"+fieldsvalues).focus();
			            $("#"+fieldsvalues).css("background-color","#ff701a");
			            event.originalEvent.options.submit = false;
			            break;   
			              }
		             } 
		        }
		    }     
		    if(event.originalEvent.options.submit != false)
		    {
		    	
		    	//$("#ButtonDiv").hide();
		    	//$("#confirmEscalationDialog").dialog({title:'Action Status'});
		    	$('#confirmEscalationDialog').dialog('open');
		    	$("#confirmEscalationDialog").dialog({title:'Success Message',width:700,height:140});
		    	$("#confirmEscalationDialog").html("<center><img src=images/ajax-loaderNew.gif></center>");
		    	//setTimeout(function(){ $("#confirmEscalationDialog").dialog('close'); }, 10000);
		    }
		   
		});


$.subscribe('validateCall', function(event,data)
		{
			var mystring=null;
			if($("#deptname1").val()=='' || $("#deptname1").val()=='NA')
			{
				mystring=$(".ddPids").text();
			}
			else
			{
				mystring=$(".pIds").text();
			}
		    var fieldtype = mystring.split(",");
		    var pattern = /^\d{10}$/;
		    for(var i=0; i<fieldtype.length; i++)
		    {
		        var fieldsvalues = fieldtype[i].split("#")[0];
		        var fieldsnames = fieldtype[i].split("#")[1];
		        var colType = fieldtype[i].split("#")[2];   
		        var validationType = fieldtype[i].split("#")[3];
		        $("#"+fieldsvalues).css("background-color","");
		        errZone.innerHTML="";
		        if(fieldsvalues!= "" )
		        {
		            if(colType=="D"){
		            if($("#"+fieldsvalues).val()==null || $("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
		            {
		            errZone.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
		            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;   
		              }
		            }
		            else if(colType =="T"){
		            if(validationType=="n"){
		            var numeric= /^[0-9]+$/;
		            if(!(numeric.test($("#"+fieldsvalues).val()))){
		            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Numeric Values of "+fieldsnames+"</div>";
		            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;
		              }   
		             }
		            else if(validationType=="an"){
		             var allphanumeric = /^[A-Za-z0-9 ]{1,80}$/;
		            if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
		            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
		            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;
		              }
		            }
		            else if(validationType=="a"){
		            if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val())) || $("#"+fieldsvalues).val()=='NA'){
		            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
		            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;    
		              }
		             }
		            else if(validationType=="m"){
		           if($("#"+fieldsvalues).val().length > 10 || $("#"+fieldsvalues).val().length < 10)
		            {
		                errZone.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
		                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		                $("#"+fieldsvalues).focus();
		                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		                event.originalEvent.options.submit = false;
		                break;
		            }
		            else if (!pattern.test($("#"+fieldsvalues).val())) {
		                errZone.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
		                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		                $("#"+fieldsvalues).focus();
		                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		                event.originalEvent.options.submit = false;
		                break;
		             }
		                else if(($("#"+fieldsvalues).val().charAt(0)!="9") && ($("#"+fieldsvalues).val().charAt(0)!="8") && ($("#"+fieldsvalues).val().charAt(0)!="7") && ($("#"+fieldsvalues).val().charAt(0)!="6"))
		             {
		                errZone.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
		                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		                $("#"+fieldsvalues).focus();
		                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		                event.originalEvent.options.submit = false;
		                break;
		               }
		             }
		             else if(validationType =="e"){
		             if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val())){
		             }else{
		            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
		            $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		            $("#"+fieldsvalues).focus();
		            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		            event.originalEvent.options.submit = false;
		            break;
		               }
		             }
		             else if(validationType =="w"){
		             }
		             else if(validationType =="sc"){
		            	  if($("#"+fieldsvalues).val().length < 1)
				             {
				                errZone.innerHTML="<div class='user_form_inputError2'>Please Feel Form Using Search Option !!! </div>";
				                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
				                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
				                event.originalEvent.options.submit = false;
				                break;
				             }
		             }
		           }
		          
		            else if(colType=="TextArea"){
		            if(validationType=="an"){
		            var allphanumeric = /^[A-Za-z0-9 ]{3,80}$/;
		            if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
		            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
		            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
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
		            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;   
		              }   
		            }
		            else if(colType=="Date"){
		            if($("#"+fieldsvalues).val()=="")
		            {
		            errZone.innerHTML="<div class='user_form_inputError2'>Please Select Date "+fieldsnames+" Value  </div>";
		            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;   
		              }
		             }
		            else if(colType=="C")
		            {
		            	if(document.formone.recvSMS.checked==true && document.formone.recvEmail.checked==true)
		            	{
		 		            if($("#feed_by_mobno").val().length > 10 || $("#feed_by_mobno").val().length < 10)
		 		            {
		 		                errZone.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
		 		                $("#feed_by_mobno").focus();
					            $("#feed_by_mobno").css("background-color","#ff701a");
		 		                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		 		                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		 		                event.originalEvent.options.submit = false;
		 		                break;
		 		            }
		 		            else if (!pattern.test($("#feed_by_mobno").val())) {
		 		                errZone.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
		 		                $("#feed_by_mobno").focus();
					            $("#feed_by_mobno").css("background-color","#ff701a");
		 		                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		 		                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		 		                event.originalEvent.options.submit = false;
		 		                break;
		 		             }
		 		             else if(($("#feed_by_mobno").val().charAt(0)!="9") && ($("#feed_by_mobno").val().charAt(0)!="8") && ($("#feed_by_mobno").val().charAt(0)!="7") && ($("#feed_by_mobno").val().charAt(0)!="6"))
		 		             {
		 		                errZone.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
		 		                $("#feed_by_mobno").focus();
					            $("#feed_by_mobno").css("background-color","#ff701a");
		 		                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		 		                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		 		                event.originalEvent.options.submit = false;
		 		                break;
		 		             }
		 		             else if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#feed_by_emailid").val())){
				             }else{
						            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
						            $("#feed_by_emailid").css("background-color","#ff701a");  //255;165;79
						            $("#feed_by_emailid").focus();
						            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
						            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
						            event.originalEvent.options.submit = false;
						            break;
						     }
		            	}
		            	else if(document.formone.recvSMS.checked==true)
		            	{
		            		if($("#feed_by_mobno").val().length > 10 || $("#feed_by_mobno").val().length < 10)
		 		            {
		 		                errZone.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
		 		                $("#feed_by_mobno").focus();
					            $("#feed_by_mobno").css("background-color","#ff701a");
		 		                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		 		                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		 		                event.originalEvent.options.submit = false;
		 		                break;
		 		            }
		 		            else if (!pattern.test($("#feed_by_mobno").val())) {
		 		                errZone.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
		 		                $("#feed_by_mobno").focus();
					            $("#feed_by_mobno").css("background-color","#ff701a");
		 		                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		 		                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		 		                event.originalEvent.options.submit = false;
		 		                break;
		 		             }
		 		             else if(($("#feed_by_mobno").val().charAt(0)!="9") && ($("#feed_by_mobno").val().charAt(0)!="8") && ($("#feed_by_mobno").val().charAt(0)!="7") && ($("#feed_by_mobno").val().charAt(0)!="6"))
		 		             {
		 		                errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Mobile Number Start with 9,8,7,6.  </div>";
		 		                $("#feed_by_mobno").focus();
					            $("#feed_by_mobno").css("background-color","#ff701a");
		 		                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		 		                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		 		                event.originalEvent.options.submit = false;
		 		                break;
		 		             }
		            	}
		            	else if(document.formone.recvEmail.checked==true)
		            	{
		            		if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#feed_by_emailid").val())){
				             }else{
						            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
						            $("#feed_by_emailid").css("background-color","#ff701a");  //255;165;79
						            $("#feed_by_emailid").focus();
						            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
						            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
						            event.originalEvent.options.submit = false;
						            break;
						     }
		            	}
		            }
		        }
		    }
		    if(event.originalEvent.options.submit != false)
		    {
		    	$("#ButtonDiv").hide();
		    	$("#printSelect").dialog('open');
		    	$("#confirmingEscalation").html("<br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		    }
		});

function getSubFloorDetail(subFloorId)
{
	$.ajax
	({
		type:'post',
		url:"/over2cloud/view/Over2Cloud/FloorSettingOver2Cloud/subRoomDetail.action?id="+subFloorId,
		success: function(data)
		{
			$("#interCom").val(data.other2);
		},
		error: function()
		{
			//alert("There may some error in data fetch");
		}
	});
}
function fetchAllotedTo(subdept,subCategory,targetid,flr,ab)
{
	var subDept=$('#'+subdept).val();
	////alert("ABBb ::: "+ab);
	if(ab=='1')
	{
		subCategory = $('#subCatg').val();
	}
	////alert("subCategory ::: "+subCategory);
	//var subCategory =$('#'+subCat).val(); 
	//var subCategory=$('#'+subCat).val();
 	//var flor=$('#floorIDNew').val();
 	var flor=$('#room').val();
 	////alert("subCategory:::"+subCategory);
 	////alert("subDept:::"+subDept);
   // //alert(flor);
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/fetchAllotTo.action?tosubdept="+subDept+"&subCategory="+subCategory+"&floor="+flor,
		async:false,
		success : function(empData)
		{
			//console.log(empData);
			if(empData.length != 0)
			{
				$("#allotto1").val(empData[0].id);
				$("#allotto3").val(empData[0].name);
			}
			else
			{
				$("#allotto1").val("");
				$("#allotto3").val("");
			}
	    },
	    error : function () {
			//alert("Somthing is wrong to get Data");
		}
	});
}

function manualReassignPage(caseManual)
{
	var subcat=$("#subCatg").val();
	var flor=$('#room').val();
	var subDept=$('#tosubdept').val();
if(subcat!=null && subcat!='' && flor!='' && subDept!='')
	{
	$("#reassign").dialog('open');
	$("#reassign").dialog({title:'Manual Reassign',width:300,height:300});
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/fetchAllotToManual.action?tosubdept="+subDept+"&subCategory="+subcat+"&floor="+flor+"&caseManual="+caseManual,
		success : function(empData)
		{
		
	    	$("#reassign").html(empData);
	    },
	    error : function () {
			//alert("Somthing is wrong to get Data");
		}
	});
	}
else
	{
		//alert("Please Select dropdown value");
	}
	
}
var ab;
$.subscribe('changeReassign', function(event,data)
{
	ab=event.originalEvent.ui.item.value;
	////alert(ab);
});

function setAllotTo()
{
	var allotVal= $("#allottoMan").val();
	/*$("#allotDiv2").show();
	$("#allotDiv1").hide();*/
	//$("#allotto").attr('title',ab);
	var a=ab;
	////alert(a);
	$("#allotto1").val(allotVal);
	$("#allotto3").val(a);	
	$("#allotto").attr('name','');
	$("#reassign").dialog('close');
}
function getSubFloorOtherDetail(subFloorId)
{
	$.ajax
	({
		type:'post',
		url:"/over2cloud/view/Over2Cloud/FloorSettingOver2Cloud/subRoomFloorDetail.action?id="+subFloorId,
		async:false,
		success: function(data)
		{
			$("#interCom").val(data.other2);
			$("#floorid").val(data.other3);
			$("#roomNo").val(data.other4);
		//	$("#floorIDNew").val(data.other5);
			
		},
		error: function()
		{
			//alert("There may some error in data fetch");
		}
	});
}
/*function getSubCategory(value,targetid)
{
	
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/getSubCategoryOnline.action?bydept_subdept="+value,
		success : function(empData){
		$('#'+targetid+' option').remove();
		$('#'+targetid).append($("<option></option>").attr("value",-1).text("Select Sub Category"));
    	$(empData).each(function(index)
		{
		   $('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.name));
		});
	    },
	    error : function () {
			//alert("Somthing is wrong to get Data");
		}
	});
}*/
var chkDeptFlag=0;
$.subscribe('getcategory', function(event, data)
{
	////alert("ID ::: "+data.value);
	chkDeptFlag=1;
	$.ajax({
	type :"post",
	url :"/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/getcategoryOnline.action?bydept_subdept="+data.value,
	success : function(empData)
	{
		$('#cate_widget option').remove();
		$(empData).each(function(index)
		{
		   $('#cate_widget').append($("<option></option>").attr("value",this.id).text(this.name));
		});
    },
    error : function () {
		//alert("Somthing is wrong to get Data");
	}
	});
});

$.subscribe('roomTicketDetail', function(event, data)
{
	
	$.ajax
	({
	type : "post",
	url  : "view/Over2Cloud/HelpDeskOver2Cloud/Activity_Board/getRoomDetail.action?roomNo="+data.value,
	async:false,
	success : function(data)
	{
		if(data.length<1)
		 $("#roomDiv").hide();
		else
			$("#roomDiv").show();
	//console.log(data);
	$("#TableRoomWise").empty();
	var temp='';
	$(data).each(function(index)
	{
	temp +='<tr><td class="contents" id="room'+data[index].id+'" align="center">'+data[index].ticketNo+'</td><td class="contents" id="room'+data[index].id+'" align="center">'+data[index].subCatg+'</td></tr>';
	});
	$("#TableRoomWise").append(temp);
	},
	error : function()
	{
	//alert("Error on data fetch");
	} 
	});
});
$.subscribe('getSubCategory', function(event, data)
{
	chkDeptFlag=1;
	$.ajax({
	type :"post",
	url :"/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/getSubCategoryOnline.action?bydept_subdept="+data.value,
	success : function(empData){
	$('#subCatg_widget option').remove();
	$(empData).each(function(index)
	{
	   $('#subCatg_widget').append($("<option></option>").attr("value",this.id).text(this.name));
	});
    },
    error : function () {
		//alert("Somthing is wrong to get Data");
	}
	});
});


$.subscribe('getFeedBreifViaAjax', function(event, data) 
{
	 // var subCatg = document.getElementById(subCatgId).value;
   // $("#scatgid").val(subCatg);
    //$("#test").val(subCatg);
	 // //alert("Check Flag=== "+chkDeptFlag);
     $.ajax({
    	 
     type:"post",
     url:"/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Feedback_Draft/SubCatgDetail.action?subcatg="+data.value,
     async:false,
     success :  function(data){
      	   $("#subCatgId").val(data.id);
      	   $("#tosubdept").val(data.feedtype_id);
      	   //$("#feed_brief").val(data.feed_msg);
      	 //  //alert(data.subdept);
      	   $("#subDept").val(data.subdept);
      	   $("#fbType").val(data.feedtype);
	      	 if( chkDeptFlag==0)
	         {
	      	   $("#category12").val(data.subdept);
	      	   $("#catId1").hide();
	      	   $("#catId").show();
	         }
	      	 else
	      	 {
	      		$("#catId").hide();
	      		$("#catId1").show();
	      	 }
             if(data.feed_msg == null){
          	   $("#feed_brief").val("NA");
             }
             else {
          	   //$("#feed_brief").val(data.feed_msg);
			   }
             
             if(data.addressing_time == null){
          	   $("#resolutionTime").val("00:30");
             }
             else {
          	   $("#resolutionTime").val(data.resolution_time);
			   }
             
             if(data.escalation_time == null){
          	   $("#escalationTime").val("02:00");
             }
             else {
          	   $("#escalationTime").val(data.resolution_time);
			   }
          
             if( chkDeptFlag==0)
             {
          	   $("#textDepartment").css('display','block');  
            	 $("#textDepartmentval").val(data.deptName); 
            	$("#deptName").val(data.deptId);
            	 $("#textDepartmentauto").hide();
            	 $("#textDepartmentauto").attr('name','').css('display','none');
            
              }
            // getSubFloorOtherDetailForDept(data.value);
             $('#subCatg_widget').append($("<option></option>").attr("value",this.id).text(this.name));
	    },
	    error : function () {
			//alert("Somthing is wrong to get Data");
		}
     });
});
function getSubFloorOtherDetailForDept(subFloorId)
{
	 	$.ajax
	({
		type:'post',
		url:"/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Feedback_Draft/SubCatgDetailDept.action?subcatg="+subFloorId,
		async:false,
		success: function(data)
		{
			console.log(data);
			$("#depDiv").html(data);
		},
		error: function()
		{
			//alert("There may some error in data fetch");
		}
	});
}
