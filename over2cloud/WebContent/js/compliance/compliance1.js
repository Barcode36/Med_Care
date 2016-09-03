function hideAssetDiv()
{
	var v = $("input[type='radio']");
	for(var i=0; i<v.length; i++)
	{
	       if(v[i].value=="simpleCompl")
	       {
	    	   document.getElementById("assetComliance").style.display='none';
	       }
	}
}

function showAssetCompl()
{
	var v = $("input[type='radio']");
	for(var i=0; i<v.length; i++)
	{
	       if(v[i].value=="assetCompl")
	       {
	    	document.getElementById("assetComliance").style.display='block';
	       }
	}
}

function showDiv(divName)
{
	document.getElementById(divName).style.display='block';
}

/////////////////////////////////////////////////////////
function findHiddenDiv(complValue)
{
	if(complValue=="Configure")
 	{
	    document.getElementById("txtAreaHiddenFromDB").style.display="none";
	 	document.getElementById("txtAreaHidden").style.display="block";
	 	document.getElementById("taskTypeDIV").style.display="block";
 	}
 	else if(complValue=="remToSelf")
 	{
 	 	document.getElementById("remToOther").style.display="none";
 	 	document.getElementById("remToBoth").style.display="none";
 	 	document.getElementById("other").style.display="none";
 	 	document.getElementById("self").style.display="block";
 	    $('input:radio[name=action_type]')[0].checked = true;
 	 	document.getElementById("ownerShip").style.display="none";
 	}
 	else if(complValue=="remToOther")
 	{
 	
 	 	document.getElementById("remToBoth").style.display="none";
 	    document.getElementById("remToOther").style.display="block";
 	    document.getElementById("self").style.display="block";
 	    document.getElementById("other").style.display="block";
 	    document.getElementById("ownerShip").style.display="block";
 	    $('input:radio[name=action_type]')[1].checked = true;
 	}
 	else if(complValue=="remToBoth")
 	{
 		document.getElementById("remToOther").style.display="none";
 		document.getElementById("remToBoth").style.display="none";
 		document.getElementById("remToBoth").style.display="block";
 		document.getElementById("self").style.display="block";
 		document.getElementById("other").style.display="block";
 		document.getElementById("ownerShip").style.display="block";
 		$('input:radio[name=action_type]')[1].checked = true;
 	} 	
 	else if(complValue=="Y")
 	{
 		document.getElementById("escalationDIV").style.display="block";
 	}
 	else if(complValue=="N")
 	{
 		document.getElementById("escalationDIV").style.display="none";
 	}
 	else
 	{
 	//document.getElementById("txtAreaHiddenFromDB").style.display="none";
 	//document.getElementById("txtAreaHidden").style.display="none";
 	//document.getElementById("taskTypeDIV").style.display="block";
 	//document.getElementById("txtAreaHidden").style.display="none";
 	//document.getElementById("escalationDIV").style.display="none";
 	}
}

function validateClick(rad_val, form)
{
     if(rad_val=="daily")
      {
    	 form.date.disabled=false;
    	 form.dayName.disabled=true;
    	 form.hours.disabled=false;
    	 form.month.disabled=true;
      }
      if(rad_val=="weekly")
      {
    	  form.date.disabled=false;
    	  form.month.disabled=true;
    	  form.dayName.disabled=true;
    	  form.hours.disabled=false;
      }
      if(rad_val=="monthly")
      {
    	  form.date.disabled=false;
    	  form.month.disabled=true;
    	  form.dayName.disabled=true;
    	  form.minutes.disabled=false;
    	  form.hours.disabled=true;
      }
      if(rad_val=="Quarterly")
      {
    	  form.date.disabled=false;
    	  form.month.disabled=true;
    	  form.dayName.disabled=true;
    	  form.minutes.disabled=false;
    	  form.hours.disabled=false;
      }
      if(rad_val=="HalfEarly")
      {
    	  form.date.disabled=true;
    	  form.month.disabled=true;
    	  form.dayName.disabled=false;
    	  form.minutes.disabled=false;
    	  form.hours.disabled=false;
      }
      if(rad_val=="yearly")
      {
    	  form.date.disabled=false;
    	  form.dayName.disabled=true;
    	  form.month.disabled=true;
    	  form.hours.disabled=false;
      }
}

function adddiv(divID)
{
  document.getElementById(divID).style.display="block";
}

function adddeletediv(divID,divID1)
{
  document.getElementById(divID).style.display="block";
  document.getElementById(divID1).style.display="none";
}


function plusdiv(divID,buttonId)
{
  document.getElementById(divID).style.display="block";
}


function deletediv(divID)
{
	document.getElementById(divID).style.display="none";
}



function showhidedivblock(divID,divID1)
{
  document.getElementById(divID).style.display="block";
  document.getElementById(divID1).style.display="none";
}


function deletedivboth(divID,divID1)
{
  document.getElementById(divID).style.display="none";
  document.getElementById(divID1).style.display="block";
}



function showhidedivblock2(divID,divID1,divID2)
{
  document.getElementById(divID).style.display="block";
  document.getElementById(divID1).style.display="none";
  document.getElementById(divID2).style.display="none";
}

function hideBlock(divId)
{
	document.getElementById(divId).style.display='none';
}

function showBlock(divId)
{
	document.getElementById(divId).style.display='block';
}

function hideDiv(divName)
{
	document.getElementById(divName).style.display='none';
}

function getFeedbackStatus(statusId)
{
	var statusType=$("#"+statusId).val();
    if(statusType=='Re-Schedule')
	{
    	document.getElementById("reSchedule").style.display="block";
	}
    else
    {
    	document.getElementById("reSchedule").style.display="none"; 
    }
}

//////////////IMPORTED FROM HELP DESK 
var feedTypeFlag;
var feedCatgFlag;
var feedSCatgFlag;
var empFlag;

function getSubDept(deptId,destAjaxDiv,deptLevel,subdeptType,ftf,fcf,fscf,ef) 
{
	feedTypeFlag=ftf;
	feedCatgFlag=fcf;
	feedSCatgFlag=fscf;
	empFlag=ef;
	var dept=$("#"+deptId).val();
	if (deptLevel=='2') 
	{
	    if (subdeptType=='1') 
	{
	$.ajax({
	type :"post",
	url :"/over2cloud/view/Over2Cloud/Compliance/compliance_pages/subDeptViaAjax.action?destination="+ destAjaxDiv+"&dept="+deptId,
	success : function(subDeptData)
	{
	$("#" + destAjaxDiv).html(subDeptData);
	    },
	    error : function () {
	alert("Somthing is wrong to get Sub Department");
	}
	});
	}
	    else if (subdeptType=='2') 
	    {
	    	$.ajax({
	    	type :"post",
	    	url :"/over2cloud/view/Over2Cloud/Compliance/compliance_pages/BySubDeptViaAjax.action?destination="+ destAjaxDiv+"&dept="+deptId,
	    	success : function(subDeptData){
	    	$("#" + destAjaxDiv).html(subDeptData);
	    	    },
	    	    error : function () {
	    	alert("Somthing is wrong to get Sub Department");
	    	}
	    	});
	}
	    else if (subdeptType=='3') 
	    {
	$.ajax({
	type :"post",
	url :"/over2cloud/view/Over2Cloud/Compliance/compliance_pages/ToSubDeptViaAjax.action?destination="+ destAjaxDiv+"&dept="+deptId,
	success : function(subDeptData)
	{
	$("#" + destAjaxDiv).html(subDeptData);
	    },
	    error : function () 
	    {
	alert("Somthing is wrong to get Sub Department");
	}
	});
	    }
	}
	else if(deptLevel=='1')
	{
	$.ajax({
	type :"post",
	url :"/over2cloud/view/Over2Cloud/Compliance/compliance_pages/EmpData.action?destination="+ destAjaxDiv+"&deptOrSubDeptId="+deptId,
	success : function(empData){
	$("#" + destAjaxDiv).html(empData);
	    },
	    error : function () {
	alert("Somthing is wrong to get Employee Data");
	}
	});
	}
}

/*function getEmpData(fromDept,destAjaxDiv,forDeptTextId) {
	var forDeptId=$("#" + forDeptTextId).val();
	$.ajax({
	type :"post",
	url :"/over2cloud/view/Over2Cloud/Compliance/compl_contacts_pages/EmpData.action?destination="+ destAjaxDiv+"&fromDept="+fromDept+"&forDeptId="+forDeptId,
	success : function(empData)
	{
	$("#" + destAjaxDiv).html(empData);
	    },
	    error : function () 
	    {
	alert("Somthing is wrong to get Employee Data");
	}
	});
}*/
function complianceExcel()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compliance_pages/beforeComplExcelupload.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function complianceConfig()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    	type : "post",
	    url : "view/Over2Cloud/Compliance/compliance_pages/createConfigurationComplView.action",
	    success : function(data) {
       $("#"+"data_part").html(data);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function getEmpData(fromDept,destAjaxDiv,forDeptTextId,dataForId) 
{
	var forDeptId=$("#" + forDeptTextId).val();
	var data4=$("#" + dataForId).val();
	$("#viewempDiv").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	type :"post",
	url :"/over2cloud/view/Over2Cloud/Compliance/compl_contacts_pages/beforeEmpData.action?destination="+ destAjaxDiv+"&fromDept="+fromDept+"&forDeptId="+forDeptId+"&moduleName="+data4,
	success : function(empData)
	{
		$("#viewempDiv").html(empData);
	    },
	    error : function () 
	    {
	alert("Somthing is wrong to get Employee Data");
	}
	});
}

function getEmpMob(empId,destAjaxDiv) {
	$.ajax({
	type :"post",
	url :"/over2cloud/view/Over2Cloud/Compliance/compliance_pages/EmpMobData.action?destination="+ destAjaxDiv+"&empId="+empId,
	success : function(empData){
	$("#" + destAjaxDiv).val(empData.mobOne);
	    },
	    error : function () {
	alert("Somthing is wrong to get Employee Mobile Data");
	}
	});
	}
function getCurrentColumn(downloadType,moduleName,dialogId,dataDiv)
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/currentColumn.action?downloadType="+downloadType+"&download4="+moduleName,
	    success : function(data) {
	$("#"+dataDiv).html(data);
	$("#"+dialogId).dialog('open');
	},
	   error: function() {
	        alert("error");
	    }
	 });
}

//NEW JS METHODSSSSSSSSSS
function getTaskNameDetails(taskTypeId,divId,div)
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/getTaskNameAction.action?taskType="+taskTypeId+"&divId="+divId,
	    success : function(data) {
	    	$('#'+div+' option').remove();
			$('#'+div).append($("<option></option>").attr("value",-1).text('Select Task Name'));
			$(data).each(function(index)
			{
			   $('#'+div).append($("<option></option>").attr("value",this.ID).text(this.NAME));
			});
	},
	   error: function() {
	        alert("error");
	    }
	 });
}
function getNextEscMap(escL1,escL2,escL3,div)
{
	var escL1SelectValue = $("#"+escL1).val() || [];
	var escL2SelectValue = $("#"+escL2).val() || [];
	var escL3SelectValue = $("#"+escL3).val() || [];
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/Compliance/compliance_pages/getNextEscMap.action?escL1SelectValue="+escL1SelectValue+"&escL2SelectValue="+escL2SelectValue+"&escL3SelectValue="+escL3SelectValue,
		success : function(data)
		{
			$('#'+div+' option').remove();
			$(data).each(function(index)
			{
			   $('#'+div).append($("<option></option>").attr("value",this.ID).text(this.NAME));
			});
	    },
	    error : function () {
			alert("Somthing is wrong to get get Next excalation Level");
		}
	});
}
function getDocu(divName) 
{
	document.getElementById(divName).style.display="block";
}

function getTaskTypeDetails(uniqueid)
{
	if(uniqueid=="Configure New")
	{
	 	document.getElementById("taskNameDIV").style.display="none";
	 	document.getElementById("txtAreaHidden").style.display="block";
	}
	else
	{
	 	document.getElementById("taskNameDIV").style.display="block";
	 	
	 	$.getJSON("/over2cloud/view/Over2Cloud/Compliance/compl_task_page/getTaskDetails.action?uniqueId="+uniqueid,
	 	 function(data)
	    	 {
	    	   //Task Name Value.
	           //Message
	           if(data.msg == null)
	           {
	        	   $("#msg").val("NA");
	           }
	           else
	           {
	        	   $("#msg").val(data.msg); 
	           }
	           //Task type brief
	           if(data.taskBrief == null)
	           {
	        	   $("#taskBrief").val("NA");
	           }
	           else
	           {
	        	   $("#taskBrief").val(data.taskBrief);
	           }
	           //Task type brief
	           if(data.taskType == null)
	           {
	        	   $("#taskType").val("NA");
	           }
	           else
	           {
	        	   $("#taskType").val(data.taskType);
	           }
	           if(data.completion == null)
	           {
	        	   $("#completion").val("NA");
	           }
	           else
	           {
	        	   $("#completion").val(data.completion);
	           }
	       });
	}
       
 }
//freezeReminder
function disableReminder(value)
{
	if(value =="D")
	{
		document.configCompliance.reminder1.disabled="true";
	}
	else
	{
		document.configCompliance.reminder1.disabled=false;
	}	
}

$.subscribe('clearDatePicker', function(event,data)
		{
		$('#frequency').find('option:first').attr('selected', 'selected');
		$("#reminder1" ).datepicker( "option", "maxDate", 0);
		$("#reminder1" ).datepicker( "option", "minDate", 0);
		$("#reminder2" ).datepicker( "option", "maxDate", 0);
		$("#reminder2" ).datepicker( "option", "minDate", 0);
		$("#reminder3" ).datepicker( "option", "maxDate", 0);
		$("#reminder3" ).datepicker( "option", "minDate", 0);
		$("#dateDiff").val("0");
		});


function validateReminder(dueDate,freq,dateDiv)
{

	//document.getElementById("reminder1").value="";
	$("#dateDiff").val(0);
	$("#reminder1" ).datepicker( "option", "maxDate", 0);
	$("#reminder1" ).datepicker( "option", "minDate", 0);
	var remindDate= $("#"+dueDate).val();
	var frequency= $("#"+freq).val();
	var minDateValue= $("#dateDiff").val();
	
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/getReminder.action?dueDate="+remindDate+"&frequency="+frequency+"&minDateValue="+minDateValue,
	    success : function(data) {
		$("#dateDiff").val(data.minDateValue);
		$("#reminder1" ).datepicker( "option", "maxDate", data.maxDate);
		$("#reminder1" ).datepicker( "option", "minDate", data.minDate);
	    	
	},
	   error: function() {
	        alert("error");
	    }
	 });
}

$.subscribe('getSecondReminder', function(event,data)
	{
	$("#reminder2" ).datepicker( "option", "maxDate", 0);
	$("#reminder2" ).datepicker( "option", "minDate", 0);
	var remindDate= $("#reminder1").val();
	var frequency= $("#frequency").val();
	var minDateValue= $("#dateDiff").val();
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/getReminder.action?dueDate="+remindDate+"&frequency="+frequency+"&minDateValue="+minDateValue,
	    success : function(data) {
    		$("#reminder2" ).datepicker( "option", "maxDate", data.maxDate);
			$("#reminder2" ).datepicker( "option", "minDate", data.minDate);
	    	
	    },
	   error: function() {
	        alert("error");
	    }
	 });
	});

$.subscribe('getThirdReminder', function(event,data)
		{
	$("#reminder3" ).datepicker( "option", "maxDate", 0);
	$("#reminder3" ).datepicker( "option", "minDate", 0);
		var remindDate= $("#reminder2").val();
		var frequency= $("#frequency").val();
		var minDateValue= $("#dateDiff").val();
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/getReminder.action?dueDate="+remindDate+"&frequency="+frequency+"&minDateValue="+minDateValue,
		    success : function(data) {
	    		$("#reminder3" ).datepicker( "option", "maxDate", data.maxDate);
				$("#reminder3" ).datepicker( "option", "minDate", data.minDate);
		    	
		},
		   error: function() {
		        alert("error");
		    }
		 });
		});

function employeeProductivity(divId,empId,fdate,tdate) 
{
	
	var fromDate= $("#"+fdate).val();
	var toDate= $("#"+tdate).val();
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/employeeProductivityCounter.action?empId="+empId+"&fromDate="+fromDate+"&toDate="+toDate,
	    success : function(data) {
	$("#"+divId).html(data);
	},
	   error: function() {
	        alert("error");
	    }
	 });
}
function getProductivity(fdate,tdate,empN,divId)
{
	var fromDate = $("#"+fdate).val();
	var toDate = $("#"+tdate).val();
	var empName = $("#"+empN).val();
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/employeeProductivityCounter.action?empName="+empName+"&fromDate="+fromDate+"&toDate="+toDate,
	    success : function(data) 
	    {
    	  $("#"+divId).html(data);
	    },
	    error: function() 
	    {
	       alert("error");
	    }
	 });
}
function showAllComplProductivityDetails(dataFor,complId)
{
	
	$("#complianceDialog").dialog({title: 'Total Productivity',height:'550' , width:'1130'});
	$("#complianceDialog").dialog('open');
	$("#datadiv").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/productivityGridCol.action?dataFor="+dataFor+"&complId="+complId,
	    success : function(data) 
	    {
	    	$("#datadiv").html(data);
	    },
	    error: function() 
	    {
	       alert("error");
	    }
	 });
}
function showDetailsPieOfProduct(div,graphData)
{
	$("#complianceDialog").dialog({title: ' Productivity Graph' ,width: '740',position:'center',height:'450' });
	$("#complianceDialog").dialog('open');
	$("#datadiv").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/productivityGraph1111.action?dataFor="+graphData,
	    success : function(data) 
	    {
	    	$("#datadiv").html(data);
	    },
	    error: function() 
	    {
	       alert("error");
	    }
	 });
}
function freezeReminder(value,form,dueDivId,rem1,rem2,rem3)
{
	
	var dueDate=$("#"+dueDivId).val();
	if(value=='D')
	{
		document.form.rem1.disabled="true";
		mytextfield.disabled="true";
		document.configCompliance.reminder1.disabled="true";
		document.configCompliance.reminder3.disabled="true";
	}
	else
	{
		/*$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/getRemindDate.action?dueDate="+dueDate+"&frequency="+value+"&reminder1="+rem1+"&reminder2="+rem2+"&reminder3="+rem3,
		    success : function(data) 
		    {
		    	$("#"+rem1).datepicker("option","maxDate", maxDate);
		    	$("#"+rem1).datepicker("option","minDate", minDate);
		    },
		    error: function() 
		    {
	            alert("error");
	        }
		 });*/
		$( "#reminder1").prop("disabled", false);
	}
 }
$.subscribe('validExcelAsset', function(event,data)
		{
			var excelData = $("#excel").val();
			if (excelData=="") 
			{
				alert("Please Select Excel ...");
			} 
			else 
			{
				$("#AssetBasicDialog").dialog('open');
			}
		});
function checkMailId(sendMailFrom)
{
	var mystring=null;
	var flag=false;
	mystring=$(".mailPIds").text();
	var fieldtype = mystring.split(",");
	    for(var i=0; i<fieldtype.length; i++)
	    {
	        var fieldsvalues   = fieldtype[i].split("#")[0];
	        var colType        = fieldtype[i].split("#")[2];   
	        var validationType = fieldtype[i].split("#")[3];
	        $("#"+fieldsvalues).css("background-color","");
	        mailErrZone.innerHTML="";
	        if(fieldsvalues!= "" )
	        {
	            
	           if(colType =="T")
	            {
	            	if(validationType =="e")
	            	{
		            	if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val()))
		            	{
		            		flag=true;
		            	}
		            	else
		            	{
		            		mailErrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
			            	$("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			            	$("#"+fieldsvalues).focus();
			            	setTimeout(function(){ $("#mailErrZone").fadeIn(); }, 10);
			            	setTimeout(function(){ $("#mailErrZone").fadeOut(); }, 2000);
			            	break;
		            	}
	            	}
	            }
	        }
	      }
	    if(flag!=false)
	    {
	    	if(sendMailFrom=='Report')
	    	{
	    		var sel_id;
		    	sel_id = $("#gridCompReport").jqGrid('getGridParam','selarrrow');
		    	if(sel_id=="")
		        {
		          alert("Please select atleast one check box...");        
		          return false;
		        }
		    	else
		    	{
		    		$("#takeActionGrid").dialog({title: 'Check Column',width: 350,height: 600});
		    		$("#takeActionGrid").dialog('open');
		    	    var emailIds	= $("#mailId").val();
		    	 	$.ajax({
		    		    type : "post",
		    		    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/sendReportMailConfirmAction.action?selectedId="+sel_id+"&emailIds="+emailIds,
		    		    success : function(data) 
		    		    {
		    	 			$("#takeActionGrid").html(data);
		    			},
		    		   error: function() {
		    		        alert("error");
		    		    }
		    		 });
		    	}
	    	}
	    	else if(sendMailFrom=='Action')
	    	{

		    	var sel_id;
		    	sel_id = $("#gridedittable").jqGrid('getGridParam','selarrrow');
		    	if(sel_id=="")
		    	{
		    	     alert("Please select atleast one check box...");        			    
		    	     return false;
		    	}
		    	else
		    	{
		    		$("#takeActionGrid").dialog({title: 'Check Column ',width: 350,height: 600});
		    		$("#takeActionGrid").dialog('open');
		    		var emailIds=$("#mailId").val();
		    	 	$.ajax({
		    		    type : "post",
		    		    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/sendMailConfirmAction.action?selectedId="+sel_id+"&emailIds="+emailIds,
		    		    success : function(data) 
		    		    {
		    	 			$("#takeActionGrid").html(data);
		    			},
		    		   error: function() {
		    		        alert("error");
		    		    }
		    		 });
		    	}
		    
	    	}
	    	else if (sendMailFrom=='History') 
	    	{
	    		var sel_id;
	    		sel_id = $("#gridCompHistory").jqGrid('getGridParam','selarrrow');
	    		if(sel_id=="")
	    	   {
	    	     alert("Please select atleast one check box...");        
	    	     return false;
	    	   }
	    		else
	    		{
	    			$("#takeActionGrid1111").dialog({title: 'Check Column',width: 350,height: 600});
	    			$("#takeActionGrid1111").dialog('open');
	    			var emailIds	= $("#mailId").val();
	    		 	$.ajax({
	    			    type : "post",
	    			    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/sendReportMailConfirmAction.action?mainHeaderName=taskHistory&selectedId="+sel_id+"&emailIds="+emailIds,
	    			    success : function(data) 
	    			    {
	    		 			
	    		 			$("#dataPart").html(data);
	    				},
	    			   error: function() {
	    			        alert("error");
	    			    }
	    			 });
	    		}
			}
	    	else if(sendMailFrom=='Dashboard1')
	    	{
	    		var sel_id;
	    		sel_id = $("#gridedittable").jqGrid('getGridParam','selarrrow');
	    		if(sel_id=="")
	    		{
	    		     alert("Please select atleast one check box...");        
	    		     return false;
	    	    }
	    		else
	    		{
	    			var emailIds	= $("#mailId").val();
	    			$("#takeActionGrid").dialog({title: 'Check Column For Send Mail',width: 350,height: 600});
	    		    $("#takeActionGrid").dialog('open');
	    		 	$.ajax({
	    			    type : "post",
	    			    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/sendMailConfirmAction.action?selectedId="+sel_id+"&emailIds="+emailIds,
	    			    success : function(data) 
	    			    {
	    		 			$("#takeActionGrid").html(data);
	    				},
	    			   error: function() {
	    			        alert("error");
	    			    }
	    			 });
	    		}
	    	}
	    	else if(sendMailFrom=='DashboardLastMonth')
    		{
	    		var sel_id;
	    		sel_id = $("#gridCompReport").jqGrid('getGridParam','selarrrow');
	    		if(sel_id=="")
	    		{
	    		     alert("Please select atleast one check box...");        
	    		     return false;
	    		}
	    		else
	    		{
	    			var emailIds	= $("#mailId").val();
	    			$("#takeActionGrid").dialog({title: 'Check Column For Send Mail',width: 350,height: 600});
	    			$("#takeActionGrid").dialog('open');
	    			$.ajax({
	    			    type : "post",
	    			    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/sendReportMailConfirmAction.action?selectedId="+sel_id+"&emailIds="+emailIds,
	    			    success : function(data) 
	    			    {
	    		 			$("#takeActionGrid").html(data);
	    				},
	    			   error: function() {
	    			        alert("error");
	    			    }
	    			 });
	    		}	
    		}
	    }
}
$.subscribe('validateTaskHistory', function(event,data)
		{
			var mystring=null;
			mystring=$(".pIds").text();
			var fieldtype = mystring.split(",");
		    for(var i=0; i<fieldtype.length; i++)
		    {
		        var fieldsvalues   = fieldtype[i].split("#")[0];
		        var fieldsnames    = fieldtype[i].split("#")[1];
		        var colType        = fieldtype[i].split("#")[2];   
		        $("#"+fieldsvalues).css("background-color","");
		        errZone.innerHTML="";
		        if(fieldsvalues!= "" )
		        {
		            if(colType=="D")
		            {
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
		        }
		    }
		    if(event.originalEvent.options.submit != false)
		    	{
		    		$("#taskHistoryDialog").dialog('open');
		    	}
		 });



$.subscribe('validateTaskType', function(event,data)
{
	var mystring=null;
	mystring=$(".pIds").text();
	var fieldtype = mystring.split(",");
    var pattern = /^\d{10}$/;
    for(var i=0; i<fieldtype.length; i++)
    {
        var fieldsvalues   = fieldtype[i].split("#")[0];
        var fieldsnames    = fieldtype[i].split("#")[1];
        var colType        = fieldtype[i].split("#")[2];   
        var validationType = fieldtype[i].split("#")[3];
        $("#"+fieldsvalues).css("background-color","");
        errZone.innerHTML="";
        if(fieldsvalues!= "" )
        {
            if(colType=="D")
            {
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
            else if(colType =="T")
            {
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
            	var allphanumeric = /^[A-Za-z0-9 ]{1,50}$/;
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
            	if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val())) || $("#"+fieldsvalues).val()=='NA')
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
            	if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val()))
            	{
            	}
            	else
            	{
            	errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
            	$("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
            	$("#"+fieldsvalues).focus();
            	setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            	setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
            	event.originalEvent.options.submit = false;
            	break;
            	}
            	}
            	else if(validationType =="w")
            	{
            
            
            
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
            else if(colType=="TextArea"){
            if(validationType=="an"){
            var allphanumeric = /^[A-Za-z0-9 ]{1,100}$/;
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
 	                errZone.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
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
 });

$.subscribe('validateTransfer', function(event,data)
	{
	var mystring=null;
	mystring=$(".pIdssss").text();
	var fieldtype = mystring.split(",");
	    var pattern = /^\d{10}$/;
	    for(var i=0; i<fieldtype.length; i++)
	    {
	        var fieldsvalues   = fieldtype[i].split("#")[0];
	        var fieldsnames    = fieldtype[i].split("#")[1];
	        var colType        = fieldtype[i].split("#")[2];   
	        var validationType = fieldtype[i].split("#")[3];
	        $("#"+fieldsvalues).css("background-color","");
	        errZone.innerHTML="";
	        if(fieldsvalues!= "" )
	        {
	            if(colType=="D")
	            {
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
	            else if(colType =="T")
	            {
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
	            	var allphanumeric = /^[A-Za-z0-9 ]{1,50}$/;
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
	            	if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val())) || $("#"+fieldsvalues).val()=='NA')
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
	            	if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val()))
	            	{
	            	}
	            	else
	            	{
	            	errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
	            	$("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
	            	$("#"+fieldsvalues).focus();
	            	setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
	            	setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
	            	event.originalEvent.options.submit = false;
	            	break;
	            	}
	            	}
	            	else if(validationType =="w")
	            	{
	            
	            
	            
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
	            else if(colType=="TextArea"){
	            if(validationType=="an"){
	            var allphanumeric = /^[A-Za-z0-9 ]{1,100}$/;
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
	 	                errZone.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
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
	 });




//Method for configuration
$.subscribe('configValidate', function(event,data)
	  {
	var mystring=null;
	if($("#taskName1").val()!='' && $("#taskName1").val()=='Configure New' && $('#reminder_forremToOther').is(':checked'))
	{
	mystring=$(".rem2othernewpIds").text();
	}
	else if($("#taskName1").val()!='' && $("#taskName1").val()=='Configure New' && $('#reminder_forremToBoth').is(':checked'))
	{
	mystring=$(".rem2bothnewpIds").text();
	}
	else if($("#taskName1").val()!='' && $("#taskName1").val()=='Configure New' && $('#reminder_forremToSelf').is(':checked'))
	{
	mystring=$(".newpIds").text();
	}
	else if($("#taskName1").val()!='' && $("#taskName1").val()!='Configure New' && $('#reminder_forremToOther').is(':checked'))
	{
	mystring=$(".rem2otherpIds").text();
	}
	else if($("#taskName1").val()!='' && $("#taskName1").val()!='Configure New' && $('#reminder_forremToBoth').is(':checked'))
	{
	mystring=$(".rem2bothpIds").text();
	}
	else if($("#taskName1").val()!='' && $("#taskName1").val()!='Configure New' && $('#reminder_forremToSelf').is(':checked'))
	{
	mystring=$(".pIds").text();
	}
	var fieldtype = mystring.split(","); 
	    var pattern = /^\d{10}$/;
	    for(var i=0; i<fieldtype.length; i++)
	    {
	        var fieldsvalues   = fieldtype[i].split("#")[0];
	        var fieldsnames    = fieldtype[i].split("#")[1];
	        var colType        = fieldtype[i].split("#")[2];   
	        var validationType = fieldtype[i].split("#")[3];
	        $("#"+fieldsvalues).css("background-color","");
	        errZone.innerHTML="";
	        if(fieldsvalues!= "" )
	        {
	            if(colType=="D")
	            {
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
	            else if(colType=="date")
	            {
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
	            else if(colType=="R")
	            {
	            	if($('#reminder_forremToSelf').is(':checked') || $('#reminder_forremToOther').is(':checked') || $('#reminder_forremToBoth').is(':checked'))
	        	    { 
	            	    
	        	    }
	        	 	else
	        	 	{
	        	 	errZone.innerHTML="<div class='user_form_inputError2'>Please Select Date "+"Remind To"+" Value  </div>";
	         	setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
	         	setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
	         	$("#"+fieldsvalues).focus();
	         	$("#"+fieldsvalues).css("background-color","#ff701a");
	         	event.originalEvent.options.submit = false;
	         	break;   
	        	 	}
	            	if($('#remindModeSMS').is(':checked') || $('#remindModeMail').is(':checked') || $('#remindModeBoth').is(':checked') || $('#remindModeNone').is(':checked'))
	        	    { 
	            	    
	        	    }
	        	 	else
	        	 	{
	        	 	errZone.innerHTML="<div class='user_form_inputError2'>Please Select Date "+"Mode"+" Value  </div>";
	         	setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
	         	setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
	         	$("#"+fieldsvalues).focus();
	         	$("#"+fieldsvalues).css("background-color","#ff701a");
	         	event.originalEvent.options.submit = false;
	         	break;   
	        	 	}
	             }
	            else if(colType =="T")
	            {
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
	            	var allphanumeric = /^[A-Za-z0-9 ]{1,50}$/;
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
	            	if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val())) || $("#"+fieldsvalues).val()=='NA')
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
	            	if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val()))
	            	{
	                	
	            	}
	            	else
	            	{
	            	errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
	            	$("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
	            	$("#"+fieldsvalues).focus();
	            	setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
	            	setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
	            	event.originalEvent.options.submit = false;
	            	break;
	            	}
	            	}
	            	else if(validationType =="w")
	            	{
	            
	            
	            
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
	            else if(colType=="TA")
	            {
	            if(validationType=="an"){
	            var allphanumeric = /^[A-Za-z0-9 ]{1,100}$/;
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
	            else if(colType=="time"){
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
	            else if(colType=="date")
	            {
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
	 	                errZone.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
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
	            	if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#feed_by_emailid").val()))
	                	{
	            }
	            else
	        {
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
	    	if($("#escalation").val()!='' && $("#escalation").val()=='Y')
	{
	    	if($("#escalation_level_1").val()==null || $("#escalation_level_1").val()=="" || $("#escalation_level_1").val()=="-1")
	    	{
	    	errZone.innerHTML="<div class='user_form_inputError2'>Please Select Escalation L1 Value from Drop Down </div>";
	            	setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
	            	setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
	            	$("#escalation_level_1").focus();
	            	$("#escalation_level_1").css("background-color","#ff701a");
	            	event.originalEvent.options.submit = false;
	    	}
	    	else if($("#l1EscDuration").val()==null || $("#l1EscDuration").val()=="")
	    	{
	    	errZone.innerHTML="<div class='user_form_inputError2'>Please L1 Esc Duration </div>";
	            	setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
	            	setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
	            	$("#l1EscDuration").focus();
	            	$("#l1EscDuration").css("background-color","#ff701a");
	            	event.originalEvent.options.submit = false;
	    	}
	}
	    }
	 
	  });


$.subscribe('excelConfigValidate1', function(event,data)
	  {
	var mystring=null;
	if($("#taskName").val()!='' && $("#taskName").val()!='Configure New' && $('#reminder_forremToOther').is(':checked'))
	{
	mystring=$(".rem2otherpIds").text();
	}
	else if($("#taskName").val()!='' && $("#taskName").val()!='Configure New' && $('#reminder_forremToBoth').is(':checked'))
	{
	mystring=$(".rem2bothpIds").text();
	}
	else if($("#taskName").val()!='' && $("#taskName").val()!='Configure New' && $('#reminder_forremToSelf').is(':checked'))
	{
	mystring=$(".pIds").text();
	}
	var fieldtype = mystring.split(","); 
	    var pattern = /^\d{10}$/;
	    for(var i=0; i<fieldtype.length; i++)
	    {
	        var fieldsvalues   = fieldtype[i].split("#")[0];
	        var fieldsnames    = fieldtype[i].split("#")[1];
	        var colType        = fieldtype[i].split("#")[2];   
	        var validationType = fieldtype[i].split("#")[3];
	        $("#"+fieldsvalues).css("background-color","");
	        errZone.innerHTML="";
	        if(fieldsvalues!= "" )
	        {
	            if(colType=="D")
	            {
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
	            else if(colType=="date")
	            {
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
	            else if(colType=="R")
	            {
	            	if($('#reminder_forremToSelf').is(':checked') || $('#reminder_forremToOther').is(':checked') || $('#reminder_forremToBoth').is(':checked'))
	        	    { 
	            	    
	        	    }
	        	 	else
	        	 	{
	        	 	errZone.innerHTML="<div class='user_form_inputError2'>Please Select Date "+"Remind To"+" Value  </div>";
	         	setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
	         	setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
	         	$("#"+fieldsvalues).focus();
	         	$("#"+fieldsvalues).css("background-color","#ff701a");
	         	event.originalEvent.options.submit = false;
	         	break;   
	        	 	}
	            	if($('#remindModeSMS').is(':checked') || $('#remindModeMail').is(':checked') || $('#remindModeBoth').is(':checked') || $('#remindModeNone').is(':checked'))
	        	    { 
	            	    
	        	    }
	        	 	else
	        	 	{
	        	 	errZone.innerHTML="<div class='user_form_inputError2'>Please Select Date "+"Mode"+" Value  </div>";
	         	setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
	         	setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
	         	$("#"+fieldsvalues).focus();
	         	$("#"+fieldsvalues).css("background-color","#ff701a");
	         	event.originalEvent.options.submit = false;
	         	break;   
	        	 	}
	             }
	            else if(colType =="T")
	            {
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
	            	var allphanumeric = /^[A-Za-z0-9 ]{1,50}$/;
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
	            	if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val())) || $("#"+fieldsvalues).val()=='NA')
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
	            	if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val()))
	            	{
	                	
	            	}
	            	else
	            	{
	            	errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
	            	$("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
	            	$("#"+fieldsvalues).focus();
	            	setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
	            	setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
	            	event.originalEvent.options.submit = false;
	            	break;
	            	}
	            	}
	            	else if(validationType =="w")
	            	{
	            
	            
	            
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
	            else if(colType=="TA")
	            {
	            if(validationType=="an"){
	            var allphanumeric = /^[A-Za-z0-9 ]{1,100}$/;
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
	            else if(colType=="time"){
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
	            else if(colType=="date")
	            {
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
	 	                errZone.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
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
	            	if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#feed_by_emailid").val()))
	                	{
	            }
	            else
	        {
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
	    	if($("#escalation").val()!='' && $("#escalation").val()=='Y')
	{
	    	if($("#escalation_level_1").val()==null || $("#escalation_level_1").val()=="" || $("#escalation_level_1").val()=="-1")
	    	{
	    	errZone.innerHTML="<div class='user_form_inputError2'>Please Select Escalation L1 Value from Drop Down </div>";
	            	setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
	            	setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
	            	$("#escalation_level_1").focus();
	            	$("#escalation_level_1").css("background-color","#ff701a");
	            	event.originalEvent.options.submit = false;
	    	}
	    	else if($("#l1EscDuration").val()==null || $("#l1EscDuration").val()=="")
	    	{
	    	errZone.innerHTML="<div class='user_form_inputError2'>Please L1 Esc Duration </div>";
	            	setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
	            	setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
	            	$("#l1EscDuration").focus();
	            	$("#l1EscDuration").css("background-color","#ff701a");
	            	event.originalEvent.options.submit = false;
	    	}
	}
	    }
	 
	  });

//Method for configuration
$.subscribe('validate', function(event,data)
	  {
	var mystring=null;
	if($("#actionTaken").val()!='' && $("#actionTaken").val()=='Snooze')
	{
	mystring=$(".snzpIds").text();
	}
	else if($("#actionTaken").val()!='' && $("#actionTaken").val()=='Reschedule')
	{
	mystring=$(".reschdpIds").text();
	}
	else
	{
	mystring=$(".pIds").text();
	}
	var fieldtype = mystring.split(",");
	    var pattern = /^\d{10}$/;
	    for(var i=0; i<fieldtype.length; i++)
	    {
	        var fieldsvalues   = fieldtype[i].split("#")[0];
	        var fieldsnames    = fieldtype[i].split("#")[1];
	        var colType        = fieldtype[i].split("#")[2];   
	        var validationType = fieldtype[i].split("#")[3];
	        $("#"+fieldsvalues).css("background-color","");
	        errZone.innerHTML="";
	        if(fieldsvalues!= "" )
	        {
	            if(colType=="D")
	            {
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
	            else if(colType=="date")
	            {
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
	            else if(colType=="R")
	            {
	            	if($('#reminder_forremToSelf').is(':checked') || $('#reminder_forremToOther').is(':checked') || $('#reminder_forremToBoth').is(':checked'))
	        	    { 
	            	    
	        	    }
	        	 	else
	        	 	{
	        	 	errZone.innerHTML="<div class='user_form_inputError2'>Please Select Date "+"Remind To"+" Value  </div>";
	         	setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
	         	setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
	         	$("#"+fieldsvalues).focus();
	         	$("#"+fieldsvalues).css("background-color","#ff701a");
	         	event.originalEvent.options.submit = false;
	         	break;   
	        	 	}
	            	if($('#remindModeSMS').is(':checked') || $('#remindModeMail').is(':checked') || $('#remindModeBoth').is(':checked') || $('#remindModeNone').is(':checked'))
	        	    { 
	            	    
	        	    }
	        	 	else
	        	 	{
	        	 	errZone.innerHTML="<div class='user_form_inputError2'>Please Select Date "+"Mode"+" Value  </div>";
	         	setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
	         	setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
	         	$("#"+fieldsvalues).focus();
	         	$("#"+fieldsvalues).css("background-color","#ff701a");
	         	event.originalEvent.options.submit = false;
	         	break;   
	        	 	}
	             }
	            else if(colType =="T")
	            {
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
	            	var allphanumeric = /^[A-Za-z0-9 ]{1,50}$/;
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
	            	if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val())) || $("#"+fieldsvalues).val()=='NA')
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
	            	if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val()))
	            	{
	                	
	            	}
	            	else
	            	{
	            	errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
	            	$("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
	            	$("#"+fieldsvalues).focus();
	            	setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
	            	setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
	            	event.originalEvent.options.submit = false;
	            	break;
	            	}
	            	}
	            	else if(validationType =="w")
	            	{
	            
	            
	            
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
	            else if(colType=="TA")
	            {
	            if(validationType=="an"){
	            var allphanumeric = /^[A-Za-z0-9 ]{1,100}$/;
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
	            else if(colType=="time"){
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
	            else if(colType=="date")
	            {
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
	 	                errZone.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
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
	            	if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#feed_by_emailid").val()))
	                	{
	            }
	            else
	        {
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
	 
	  });

$.subscribe('validateOnTakeAction', function(event,data)
		  {
		var mystring=null;
		if($("#actionTaken").val()!='' && $("#actionTaken").val()=='Snooze')
		{
		mystring=$(".snzpIds").text();
		}
		else if($("#actionTaken").val()!='' && $("#actionTaken").val()=='Reschedule')
		{
		mystring=$(".reschdpIds").text();
		}
		else
		{
		mystring=$(".pIds").text();
		}
		var fieldtype = mystring.split(",");
		    var pattern = /^\d{10}$/;
		    for(var i=0; i<fieldtype.length; i++)
		    {
		        var fieldsvalues   = fieldtype[i].split("#")[0];
		        var fieldsnames    = fieldtype[i].split("#")[1];
		        var colType        = fieldtype[i].split("#")[2];   
		        var validationType = fieldtype[i].split("#")[3];
		        $("#"+fieldsvalues).css("background-color","");
		        errZone.innerHTML="";
		        if(fieldsvalues!= "" )
		        {
		            if(colType=="D")
		            {
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
		            else if(colType=="date")
		            {
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
		            else if(colType=="R")
		            {
		            	if($('#reminder_forremToSelf').is(':checked') || $('#reminder_forremToOther').is(':checked') || $('#reminder_forremToBoth').is(':checked'))
		        	    { 
		            	    
		        	    }
		        	 	else
		        	 	{
		        	 	errZone.innerHTML="<div class='user_form_inputError2'>Please Select Date "+"Remind To"+" Value  </div>";
		         	setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		         	setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		         	$("#"+fieldsvalues).focus();
		         	$("#"+fieldsvalues).css("background-color","#ff701a");
		         	event.originalEvent.options.submit = false;
		         	break;   
		        	 	}
		            	if($('#remindModeSMS').is(':checked') || $('#remindModeMail').is(':checked') || $('#remindModeBoth').is(':checked') || $('#remindModeNone').is(':checked'))
		        	    { 
		            	    
		        	    }
		        	 	else
		        	 	{
		        	 	errZone.innerHTML="<div class='user_form_inputError2'>Please Select Date "+"Mode"+" Value  </div>";
		         	setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		         	setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		         	$("#"+fieldsvalues).focus();
		         	$("#"+fieldsvalues).css("background-color","#ff701a");
		         	event.originalEvent.options.submit = false;
		         	break;   
		        	 	}
		             }
		            else if(colType =="T")
		            {
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
		            	var allphanumeric = /^[A-Za-z0-9 ]{1,50}$/;
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
		            	if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val())) || $("#"+fieldsvalues).val()=='NA')
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
		            	if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val()))
		            	{
		                	
		            	}
		            	else
		            	{
		            	errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
		            	$("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		            	$("#"+fieldsvalues).focus();
		            	setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		            	setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		            	event.originalEvent.options.submit = false;
		            	break;
		            	}
		            	}
		            	else if(validationType =="w")
		            	{
		            
		            
		            
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
		            else if(colType=="TA")
		            {
		            if(validationType=="an"){
		            var allphanumeric = /^[A-Za-z0-9 ]{1,100}$/;
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
		            else if(colType=="time"){
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
		            else if(colType=="date")
		            {
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
		 	                errZone.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
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
		            	if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#feed_by_emailid").val()))
		                	{
		            }
		            else
		        {
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
		    /*if(event.originalEvent.options.submit!= false)
		    {
		    	var retVal 
		    	var actionValue=$("#actionTaken").val();
		    	if(actionValue=='Done')
		    	{
		    		retVal= confirm("Do you want to stop Compliance?");
		    		if( retVal == true )
			    	{
		    			event.originalEvent.options.submit=true;
			    	}
		    	    else
		    	    {
		    		    event.originalEvent.options.submit=false;
		    	    }
		    	}
		    	else if(actionValue=='Snooze')
		    	{
		    		var snoozeDate=$("#snooze_date").val();
		    		var snoozeTime=$("#snooze_time").val();
		    		retVal= confirm("Do you want to snooze Compliance till "+snoozeDate+" "+snoozeTime+" ?");
		    		if( retVal == true )
			    	{
		    			event.originalEvent.options.submit=true;
			    	}
		    	    else
		    	    {
		    		    event.originalEvent.options.submit=false;
		    	    }
		    	}
		    	else if(actionValue=='Recurring')
		    	{
		    		var nextDueDate=$("#nextDueDate").val();
		    		retVal= confirm("Do you want to Recurring Compliance, next due date will "+nextDueDate+"?");
		    		if( retVal == true )
			    	{
		    			event.originalEvent.options.submit=true;
			    	}
		    	    else
		    	    {
		    		    event.originalEvent.options.submit=false;
		    	    }
		    	}
		    	else
		    	{
		    		event.originalEvent.options.submit=true;
		    	}
		    }*/
		 
		  });

function getEmpData4Edit(access,column) 
{
	
	if (access == "Y") 
	{
		var id=$("#id").val();
		document.getElementById("withh").style.display="block";
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Compliance/compl_contacts_pages/beforeComplContactEditEmployee.action?id="+id,
		    success : function(data) 
		    {
	 			$("#column").html(data);
			},
		   error: function() {
		        alert("error");
		    }
		 });
		
	}
	else
	{
		document.getElementById("withh").style.display="none";
	}
}
function viewCompl()
{
	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Compliance/compliance_pages/logedUserComplDashboard.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	
}
