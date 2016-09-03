

var jsonData;

	var jsonArr = [];
	var row=0;
 	var intvrl;

 	

 	
 	
function getDetailsData(value, dataFor, divId,divId1,errDiv,sourceDiv) {
	value=value.toUpperCase();
 
	if(value=='NA')
	{
		$("#"+divId).attr("readonly", false);
		$("#"+divId).focus();
	/*	$("#"+divId1).attr("readonly", false);
		$("#"+divId1).focus();
		*/
	}	
	else
	{
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/Critical/fetchDetails.action?uhid=" + value + "&dataFor=" + dataFor + "&patType=" +$("#patient_status").val(),
			success : function(data) {
			$("#uhid").show();
				if (dataFor == 'emp') {
					if(data[0].fName==undefined)
					{
						$("#"+errDiv).html("Please Enter Valid Emp ID!!!");
						$("#" + sourceDiv).css("background-color", "#ff701a");
						setTimeout(function() {$("#"+errDiv).fadeIn();}, 10);
						setTimeout(function() {$("#"+errDiv).fadeOut();}, 4000);
					}	
					else
					{
						$("#" + divId).val(data[0].fName+ " " + data[0].lName);
						/*$("#" + divId1).val(data[0].mobile);*/
					}	
				} 
				else 
				{
					if(data[0].pName==undefined)
					{
						
						$("#" + sourceDiv).show();
						$("#"+errDiv).html("Please Enter Valid UHID!!!");
						$("#" + sourceDiv).css("background-color", "#ff701a");
						setTimeout(function() {$("#"+errDiv).fadeIn();}, 10);
						setTimeout(function() {$("#"+errDiv).fadeOut();}, 4000);
					}	
					else
					{
						if(data[0].patientSts == 'IPD' || data[0].patientSts == 'EM' )
						{
							// $('#'+'tt').html("");
						
							$("#patient_name").val(data[0].pName);
						/*	$("#bed").val(data[0].bedNo);*/
							$("#nursing_unit").val(data[0].nursingUnit);
							$("#addmision_doc_name").val(data[0].admDoc);
							$("#specialty").val(data[0].admSpec);
							$("#patient_mobile").val(data[0].pat_mob);
							$("#patient_status").val(data[0].patientSts);
							$("#uhid1").val(data[0].uhidRe);
							$("#bed_no").val(data[0].bedNo);
							$("#test_valuec").val(data[0].test_value);
							$("#test_unit").val(data[0].TEST_UNITS);
							
							
							
							
							//$("#test_name_div_").html("");
							
							divAppendChar = '';
							$("#test_name_div_").hide();
							  $('#'+'tt1').html("");
							$("#test_name_div_txt_").show();
							$("#test_name_txt_fd_").val(data[0].testName);
							
							$('#test_type'+divAppendChar+' option').remove();
							$('#test_type'+divAppendChar).append($("<option></option>").attr("value",data[0].testFor).text(data[0].testFor));
							
							
						}
						if(data[0].patientSts =='OPD')
						{
							// $('#'+'tt').html("");
						
							$("#patient_name").val(data[0].pName);
						/*	$("#bed").val(data[0].bedNo);*/
							$("#nursing_unit").val(data[0].nursingUnit);
							$("#addmision_doc_name").val(data[0].admDoc);
							$("#specialty").val(data[0].admSpec);
							$("#patient_mobile").val(data[0].pat_mob);
							$("#patient_status").val(data[0].patientSts);
							$("#uhid1").val(data[0].uhidRe);
							$("#bed_no").val(data[0].bedNo);
							$("#test_valuec").val(data[0].test_value);
							$("#test_unit").val(data[0].TEST_UNITS);
							
							
							
							
							//$("#test_name_div_").html("");
							
							divAppendChar = '';
							$("#test_name_div_").hide();
							  $('#'+'tt1').html("");
							$("#test_name_div_txt_").show();
							$("#test_name_txt_fd_").val(data[0].testName);
							
							$('#test_type'+divAppendChar+' option').remove();
							$('#test_type'+divAppendChar).append($("<option></option>").attr("value",data[0].testFor).text(data[0].testFor));
							
							
						}
						else
						{
							$("#patient_name").val(data[0].pName);
						}	
						
					}	
				}
			},
			error : function() {
				alert("error");
			}
		});
	}	
}

function checkUHID(val){

	//setDocDataADD();
		if(val.trim().length > 2){

					$.ajax({
					    type : "post",
					    url : "view/Over2Cloud/Referral/UHIDCheck.action?searchString="+val,
					    success : function(data) {
					   var status=data.uhidStatus;
					  	   $("#"+"uhidResult1").html(status);
						    
					},
					   error: function() {
				            alert("error");
				        }
					 });
			}
	}

$.subscribe('validate', function(event,data)
{
	  var password=$("#password").val();
	    var repass=$("#repassword").val();
	var mystring = $(".pIds").text();
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
		if(password!=repass){
			 errZone.innerHTML="<div class='user_form_inputError2'>Password and Repassword Does't Match ... </div>";
	            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
	            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
	            $("#repassword").focus();
	            $("#repassword").css("background-color","#ff701a");
			event.originalEvent.options.submit = false;
            break;
		}
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
				            if(!(numeric.test($("#"+fieldsvalues).val().trim())))
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
				            if(!(allphanumeric.test($("#"+fieldsvalues).val().trim())))
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
			            else if(validationType=="ans")
			             {
				            var allphanumeric =  /^[0-9a-zA-Z\s\r\n@!#\$\^%&*()+=\-\[\]\\\';,\.\/\{\}\|\":<>\?]+$/;
				            if(!(allphanumeric.test($("#"+fieldsvalues).val().trim())))
				             {
					            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric and Special Characters of "+fieldsnames+"</div>";
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
				            if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val().trim())))
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
					            if($("#"+fieldsvalues).val().trim().length > 10 || $("#"+fieldsvalues).val().trim().length < 10)
					             {
					                errZone.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
					                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
					                $("#"+fieldsvalues).focus();
					                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					                event.originalEvent.options.submit = false;
					                break;
					             }
					            else if (!pattern.test($("#"+fieldsvalues).val().trim())) 
					             {
					                errZone.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
					                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
					                $("#"+fieldsvalues).focus();
					                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					                event.originalEvent.options.submit = false;
					                break;
					              }
					             else if(($("#"+fieldsvalues).val().trim().charAt(0)!="9") && ($("#"+fieldsvalues).val().trim().charAt(0)!="8") && ($("#"+fieldsvalues).val().trim().charAt(0)!="7") && ($("#"+fieldsvalues).val().trim().charAt(0)!="6"))
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
						             if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val().trim())){
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
			            /* else if(validationType =="sc"){
			            	 alert("fieldsvalues>>  "+fieldsvalues);
			            	  if($("#"+fieldsvalues).val().trim().length < 1)
					             {
					                errZone.innerHTML="<div class='user_form_inputError2'>Enter "+fieldsnames+" !!! </div>";
					                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
					                $("#"+fieldsvalues).focus();
					                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					                event.originalEvent.options.submit = false;
					                break;
					             }
			             }*/
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
		   }
		   
			else if(colType=="TextArea"){
			if(validationType=="an"){
		    var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
			if(!(allphanumeric.test($("#"+fieldsvalues).val().trim()))){
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
			if($("#"+fieldsvalues).val().trim()=="")
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
			if($("#"+fieldsvalues).val().trim()=="")
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
			else if(colType=="R"){
				var radio = document.getElementsByName("gender");
				if(!radio[0].checked && !radio[1].checked )
			    {
			    errZone.innerHTML="<div class='user_form_inputError2'>Please Select  "+fieldsnames+" Value  </div>";
		        setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
			    setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		        $("#"+fieldsvalues).focus();
		        $("#"+fieldsvalues).css("background-color","#ff701a");
		        event.originalEvent.options.submit = false;
				break;	
				  }
				 }  
			 
			 
		}else{
        	
        	$("#completionResult").dialog('open');
        	}
		
		
	}		
});




//script added by arif to reduce responce time


function historyView(cellvalue, options, rowObject) 
{
	return "<a href='#' title='View Data' onClick='viewHistoryOnClick("+rowObject.id+")'><font color='blue'>"+cellvalue+"</font></a>";
}

function viewHistoryOnClick(id) 
{
	//alert("abc");
	var temp ="";
	temp += jQuery("#gridedittable").jqGrid('getCell',id,'caller_emp_id')+","+jQuery("#gridedittable").jqGrid('getCell',id,'caller_emp_name')+","+jQuery("#gridedittable").jqGrid('getCell',id,'caller_emp_mobile')+",";
	temp += jQuery("#gridedittable").jqGrid('getCell',id,'lab_doc')+","+jQuery("#gridedittable").jqGrid('getCell',id,'lab_doc_mob')+","+jQuery("#gridedittable").jqGrid('getCell',id,'uhid')+",";
	temp += jQuery("#gridedittable").jqGrid('getCell',id,'patient_name')+","+jQuery("#gridedittable").jqGrid('getCell',id,'patient_mob')+","+jQuery("#gridedittable").jqGrid('getCell',id,'patient_status')+",";
	temp += jQuery("#gridedittable").jqGrid('getCell',id,'adm_doc')+","+jQuery("#gridedittable").jqGrid('getCell',id,'adm_doc_mob')+",";
	temp += jQuery("#gridedittable").jqGrid('getCell',id,'nursing_unit')+","+jQuery("#gridedittable").jqGrid('getCell',id,'spec')+",";
	temp += jQuery("#gridedittable").jqGrid('getCell',id,'open_date')+","+jQuery("#gridedittable").jqGrid('getCell',id,'level')+",";
	temp += jQuery("#gridedittable").jqGrid('getCell',id,'test_type')+","+jQuery("#gridedittable").jqGrid('getCell',id,'patient_id')
	+","+jQuery("#gridedittable").jqGrid('getCell',id,'bed_no')+","+jQuery("#gridedittable").jqGrid('getCell',id,'his_added_at');
	
	var ticketNo = jQuery("#gridedittable").jqGrid('getCell',id,'ticket_no');
	ticketNo=$(ticketNo).text();
	var openDate = jQuery("#gridedittable").jqGrid('getCell',id,'open_date');
	var status = jQuery("#gridedittable").jqGrid('getCell',id,'status');
	var level = jQuery("#gridedittable").jqGrid('getCell',id,'level');
	level=$(level).text();
	var uhid = jQuery("#gridedittable").jqGrid('getCell',id,'uhid'); 
	var pid = jQuery("#gridedittable").jqGrid('getCell',id,'patient_id'); 
	$("#takeActionGrid").dialog({title: 'History of '+ticketNo,width: 1000,height: 650});
	$("#takeActionGrid").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#takeActionGrid").dialog('open');
	$.ajax({	
		type : "post",
		url : "view/Over2Cloud/Critical/viewTicketHistory.action?id="+id+"&status="+status+"&ticketNo="+ticketNo
		+"&openDate="+openDate+"&pid="+pid+"&level="+level+"&uhid="+uhid+"&viewData="+temp,
		success : function(data) 
		{
		    $("#takeActionGrid").html(data);
		},
		error: function() {
		    alert("error");
		}
	});
	


	//	url  : "/over2cloud/view/Over2Cloud/Referral/beforeViewActivityHistoryData?id="+id+"&status="+status+"&refBy="+refBy+"&refTo="+refTo+"&nursingUnit="+nu+"&refBySpec="+refBySpec+"&refToSpec="+refToSpec+"&bed="+bed+"&openDate="+openDate+"&priority="+priority+"&uhid="+uhid+"&patientName="+pName+"&subSpec="+subSpec,
		

	
}

function displayFullDetail(id)
{
	
 	$("#viewTest").dialog({title: 'Test Details',width:800,height: 400});
	$("#viewTest").dialog('open');
	$("#viewTest").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	 	url : "view/Over2Cloud/Critical/testDetails.action?pid="+id,
	 	success : function(data) 
	    {
 			$("#viewTest").html(data);
		},
	   error: function() {
	        alert("error");
	    }
	 });
	
	
}


function resetTestType(){
	//alert("resetTestType");
	//console.log(">>>>>>>>>>   "+jsonData);
	$('#test_type option').remove();
	$('#test_type').append($("<option></option>").attr("value",-1).text("Select Test Type"));
	$('#test_typea option').remove();
	$('#test_typea').append($("<option></option>").attr("value",-1).text("Select Test Type"));
	$('#test_typeb option').remove();
	$('#test_typeb').append($("<option></option>").attr("value",-1).text("Select Test Type"));
	$('#test_typec option').remove();
	$('#test_typec').append($("<option></option>").attr("value",-1).text("Select Test Type"));
	$('#test_typed option').remove();
	$('#test_typed').append($("<option></option>").attr("value",-1).text("Select Test Type"));

	$('#test_typee option').remove();
	$('#test_typee').append($("<option></option>").attr("value",-1).text("Select Test Type"));
	$('#test_typef option').remove();
	$('#test_typef').append($("<option></option>").attr("value",-1).text("Select Test Type"));
	$('#test_typeg option').remove();
	$('#test_typeg').append($("<option></option>").attr("value",-1).text("Select Test Type"));
	$('#test_typeh option').remove();
	$('#test_typeh').append($("<option></option>").attr("value",-1).text("Select Test Type"));
	$('#test_typei option').remove();
	$('#test_typei').append($("<option></option>").attr("value",-1).text("Select Test Type"));
	$('#test_typej option').remove();
	$('#test_typej').append($("<option></option>").attr("value",-1).text("Select Test Type"));
	
	var test=jsonData[0];
	$(test).each(function(index)
	{
		
	   $('#test_type').append($("<option></option>").attr("value",this.id).text(this.name));
	   $('#test_typea').append($("<option></option>").attr("value",this.id).text(this.name));
	   $('#test_typeb').append($("<option></option>").attr("value",this.id).text(this.name));
	   $('#test_typec').append($("<option></option>").attr("value",this.id).text(this.name));
	   $('#test_typed').append($("<option></option>").attr("value",this.id).text(this.name));

	   $('#test_typee').append($("<option></option>").attr("value",this.id).text(this.name));
	   $('#test_typef').append($("<option></option>").attr("value",this.id).text(this.name));
	   $('#test_typeg').append($("<option></option>").attr("value",this.id).text(this.name));
	   $('#test_typeh').append($("<option></option>").attr("value",this.id).text(this.name));
	   $('#test_typei').append($("<option></option>").attr("value",this.id).text(this.name));
	   $('#test_typej').append($("<option></option>").attr("value",this.id).text(this.name));
	});
	
	//to reset autocompleter on reset button click

	$('#test_name_widget option').remove();
	$('#test_name_widget').append($("<option></option>").attr("value",-1).text("Select"));
	$('#test_namea_widget option').remove();
	$('#test_namea_widget').append($("<option></option>").attr("value",-1).text("Select"));
	$('#test_nameb_widget option').remove();
	$('#test_nameb_widget').append($("<option></option>").attr("value",-1).text("Select"));
	$('#test_namec_widget option').remove();
	$('#test_namec_widget').append($("<option></option>").attr("value",-1).text("Select"));
	$('#test_named_widget option').remove();
	$('#test_named_widget').append($("<option></option>").attr("value",-1).text("Select"));
	$('#test_namee_widget option').remove();
	$('#test_namee_widget').append($("<option></option>").attr("value",-1).text("Select"));
	$('#test_namef_widget option').remove();
	$('#test_namef_widget').append($("<option></option>").attr("value",-1).text("Select"));
	$('#test_nameg_widget option').remove();
	$('#test_nameg_widget').append($("<option></option>").attr("value",-1).text("Select"));
	$('#test_nameh_widget option').remove();
	$('#test_nameh_widget').append($("<option></option>").attr("value",-1).text("Select"));
	$('#test_namei_widget option').remove();
	$('#test_namei_widget').append($("<option></option>").attr("value",-1).text("Select"));
	$('#test_namej_widget option').remove();
	$('#test_namej_widget').append($("<option></option>").attr("value",-1).text("Select"));
	
/* 	$('#test_namea_widget').append($("<option></option>").attr("value",-1).text("All Test Name"));
	$('#test_nameb_widget').append($("<option></option>").attr("value",-1).text("All Test Name"));
	$('#test_namec_widget').append($("<option></option>").attr("value",-1).text("All Test Name"));
	$('#test_named_widget').append($("<option></option>").attr("value",-1).text("All Test Name")); */
	
	$(jsonData[1]).each(function(index)
	{
	   $('#test_name_widget').append($("<option></option>").attr("value",this.id).text(this.name));
	   $('#test_namea_widget').append($("<option></option>").attr("value",this.id).text(this.name));
	   $('#test_nameb_widget').append($("<option></option>").attr("value",this.id).text(this.name));
	   $('#test_namec_widget').append($("<option></option>").attr("value",this.id).text(this.name));
	   $('#test_named_widget').append($("<option></option>").attr("value",this.id).text(this.name));

	   $('#test_namee_widget').append($("<option></option>").attr("value",this.id).text(this.name));
	   $('#test_namef_widget').append($("<option></option>").attr("value",this.id).text(this.name));
	   $('#test_nameg_widget').append($("<option></option>").attr("value",this.id).text(this.name));
	   $('#test_nameh_widget').append($("<option></option>").attr("value",this.id).text(this.name));
	   $('#test_namei_widget').append($("<option></option>").attr("value",this.id).text(this.name));
	   $('#test_namej_widget').append($("<option></option>").attr("value",this.id).text(this.name));
	});
	
}


function viewUnitabc()
{
alert("onchange Called");	

}



function resetFields()
{
	
	$('#patient_statusA').prop('selectedIndex',0);
	$('#status').prop('selectedIndex',0);
	$('#level').prop('selectedIndex',0);
	$('#fromDate').prop('selectedIndex',0);
	$('#toDate').prop('selectedIndex',0);
	$('#searchStr').val('');
	

	}

function viewDetails(cellvalue, options, rowObject) 
{
	if(cellvalue=='Stat')
	{
		return "<h><b><font color='red'>"+cellvalue+"</font></b></h>";
	}	
	else if(cellvalue=='Urgent')
	{
		return "<h><b><font color='#CC00CC'>"+cellvalue+"</font></b></h>";
	}	
	else
	{
		return "<h><font>"+cellvalue+"</font></h>";
	}	
}

function viewLevel(cellvalue, options, rowObject) 
{
	if(cellvalue!='Level1')
	{
		return "<font color='red'>"+cellvalue+"</font>";
	}	
	else
	{
		return "<font>"+cellvalue+"</font>";
	}	
}

function onSearchData()
{

	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Critical/beforeCriticalView.action?fromDate="+$('#fromDate').val()+"&toDate="+$('#toDate').val()+"&status="+$('#status').val()+"&priority="+$('#priority1').val()+"&searchString="+$('#searchStr').val()+"&nursingUnit="+$('#nursingUnit').val()+"&doc_name1="+$('#doc_name1').val()+"&level="+$('#level').val()+"&refDocTo="+$('#reffto').val()+"&patient_status="+$('#patient_statusA').val()+"&patient_speciality="+$("#patient_speciality").val(),
	    success : function(data) {
       $("#datapart").html(data);
	},
	   error: function() {
            alert("error");
        }
	 });
}


function setDropDownVal(divAppendChar,minMaxIndex){
			
	$('#test_type'+divAppendChar+' option').remove();
	$('#test_type'+divAppendChar).append($("<option></option>").attr("value",-1).text("Select Test Type"));
	
	
	$(jsonData[0]).each(function(index)
			{
	    		
			   $('#test_type'+divAppendChar).append($("<option></option>").attr("value",this.id).text(this.name));
			  
			});
	
			
	$('#test_name'+divAppendChar+'_widget option').remove();
	$('#test_name'+divAppendChar+'_widget').append($("<option></option>").attr("value",-1).text("Select"));
	
	
	$(jsonData[1]).each(function(index)
	    	{
		//alert(this.id+"........ name.... "+this.name);
	    	   $('#test_name'+divAppendChar+'_widget').append($("<option></option>").attr("value",this.id).text(this.name));
	    	   
	    	});
	$("#min"+minMaxIndex).hide();
	$("#max"+minMaxIndex).hide();
	
	
}

function pullDDValueAfterLodge(){

	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Critical/criticalLodgeDDValue.action",
	    success : function(data) {
	    	jsonData=data;
	    	console.log("Drop down value");
	    	console.log(data);
	    	$('#test_type option').remove();
	    	$('#test_type').append($("<option></option>").attr("value",-1).text("Select Test Type"));
	    	
	    	$('#test_typea option').remove();
	    	$('#test_typea').append($("<option></option>").attr("value",-1).text("Select Test Type"));
	    	$('#test_typeb option').remove();
	    	$('#test_typeb').append($("<option></option>").attr("value",-1).text("Select Test Type"));
	    	$('#test_typec option').remove();
	    	$('#test_typec').append($("<option></option>").attr("value",-1).text("Select Test Type"));
	    	$('#test_typed option').remove();
	    	$('#test_typed').append($("<option></option>").attr("value",-1).text("Select Test Type"));

	    	$('#test_typee option').remove();
	    	$('#test_typee').append($("<option></option>").attr("value",-1).text("Select Test Type"));
	    	$('#test_typef option').remove();
	    	$('#test_typef').append($("<option></option>").attr("value",-1).text("Select Test Type"));
	    	$('#test_typeg option').remove();
	    	$('#test_typeg').append($("<option></option>").attr("value",-1).text("Select Test Type"));
	    	$('#test_typeh option').remove();
	    	$('#test_typeh').append($("<option></option>").attr("value",-1).text("Select Test Type"));
	    	$('#test_typei option').remove();
	    	$('#test_typei').append($("<option></option>").attr("value",-1).text("Select Test Type"));
	    	$('#test_typej option').remove();
	    	$('#test_typej').append($("<option></option>").attr("value",-1).text("Select Test Type"));
	    	
	    	$(jsonData[0]).each(function(index)
	    			{
	    	    		
	    			   $('#test_type').append($("<option></option>").attr("value",this.id).text(this.name));
	    			   $('#test_typea').append($("<option></option>").attr("value",this.id).text(this.name));
	    			   $('#test_typeb').append($("<option></option>").attr("value",this.id).text(this.name));
	    			   $('#test_typec').append($("<option></option>").attr("value",this.id).text(this.name));
	    			   $('#test_typed').append($("<option></option>").attr("value",this.id).text(this.name));

	    			   $('#test_typee').append($("<option></option>").attr("value",this.id).text(this.name));
	    			   $('#test_typef').append($("<option></option>").attr("value",this.id).text(this.name));
	    			   $('#test_typeg').append($("<option></option>").attr("value",this.id).text(this.name));
	    			   $('#test_typeh').append($("<option></option>").attr("value",this.id).text(this.name));
	    			   $('#test_typei').append($("<option></option>").attr("value",this.id).text(this.name));
	    			   $('#test_typej').append($("<option></option>").attr("value",this.id).text(this.name));
	    			});
	    	
	    			
	    	$('#test_name_widget option').remove();
	    	$('#test_name_widget').append($("<option></option>").attr("value",-1).text("Select"));
	    	$('#test_namea_widget option').remove();
	    	$('#test_namea_widget').append($("<option></option>").attr("value",-1).text("Select"));
	    	$('#test_nameb_widget option').remove();
	    	$('#test_nameb_widget').append($("<option></option>").attr("value",-1).text("Select"));
	    	$('#test_namec_widget option').remove();
	    	$('#test_namec_widget').append($("<option></option>").attr("value",-1).text("Select"));
	    	$('#test_named_widget option').remove();
	    	$('#test_named_widget').append($("<option></option>").attr("value",-1).text("Select"));
	    	$('#test_namee_widget option').remove();
	    	$('#test_namee_widget').append($("<option></option>").attr("value",-1).text("Select"));
	    	$('#test_namef_widget option').remove();
	    	$('#test_namef_widget').append($("<option></option>").attr("value",-1).text("Select"));
	    	$('#test_nameg_widget option').remove();
	    	$('#test_nameg_widget').append($("<option></option>").attr("value",-1).text("Select"));
	    	$('#test_nameh_widget option').remove();
	    	$('#test_nameh_widget').append($("<option></option>").attr("value",-1).text("Select"));
	    	$('#test_namei_widget option').remove();
	    	$('#test_namei_widget').append($("<option></option>").attr("value",-1).text("Select"));
	    	$('#test_namej_widget option').remove();
	    	$('#test_namej_widget').append($("<option></option>").attr("value",-1).text("Select"));
	    	
	    	$(jsonData[1]).each(function(index)
	    	    	{
	    	    	   $('#test_name_widget').append($("<option></option>").attr("value",this.id).text(this.name));
	    	    	   $('#test_namea_widget').append($("<option></option>").attr("value",this.id).text(this.name));
	    	    	   $('#test_nameb_widget').append($("<option></option>").attr("value",this.id).text(this.name));
	    	    	   $('#test_namec_widget').append($("<option></option>").attr("value",this.id).text(this.name));
	    	    	   $('#test_named_widget').append($("<option></option>").attr("value",this.id).text(this.name));

	    	    	   $('#test_namee_widget').append($("<option></option>").attr("value",this.id).text(this.name));
	    	    	   $('#test_namef_widget').append($("<option></option>").attr("value",this.id).text(this.name));
	    	    	   $('#test_nameg_widget').append($("<option></option>").attr("value",this.id).text(this.name));
	    	    	   $('#test_nameh_widget').append($("<option></option>").attr("value",this.id).text(this.name));
	    	    	   $('#test_namei_widget').append($("<option></option>").attr("value",this.id).text(this.name));
	    	    	   $('#test_namej_widget').append($("<option></option>").attr("value",this.id).text(this.name));
	    	    	});
			
       
	},
	   error: function() {
            alert("error");
        }
	 });
	
$("#TicketDiv").html(feeddraft);


	
}


function criticalTicketLodge( )
{

	$("#TicketDiv").html("<br><center><img src=images/ajax-loaderNew.gif></center>");
	
	
	 	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Critical/beforeCriticalLodge.action",
	    success : function(feeddraft) {
	    	$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/Critical/criticalLodgeDDValue.action",
			    success : function(data) {
			    	jsonData=data;
			    	console.log("Drop down value");
			    	console.log(data);
			    	$('#test_type option').remove();
			    	$('#test_type').append($("<option></option>").attr("value",-1).text("Select Test Type"));
			    	
			    	$('#test_typea option').remove();
			    	$('#test_typea').append($("<option></option>").attr("value",-1).text("Select Test Type"));
			    	$('#test_typeb option').remove();
			    	$('#test_typeb').append($("<option></option>").attr("value",-1).text("Select Test Type"));
			    	$('#test_typec option').remove();
			    	$('#test_typec').append($("<option></option>").attr("value",-1).text("Select Test Type"));
			    	$('#test_typed option').remove();
			    	$('#test_typed').append($("<option></option>").attr("value",-1).text("Select Test Type"));

			    	$('#test_typee option').remove();
			    	$('#test_typee').append($("<option></option>").attr("value",-1).text("Select Test Type"));
			    	$('#test_typef option').remove();
			    	$('#test_typef').append($("<option></option>").attr("value",-1).text("Select Test Type"));
			    	$('#test_typeg option').remove();
			    	$('#test_typeg').append($("<option></option>").attr("value",-1).text("Select Test Type"));
			    	$('#test_typeh option').remove();
			    	$('#test_typeh').append($("<option></option>").attr("value",-1).text("Select Test Type"));
			    	$('#test_typei option').remove();
			    	$('#test_typei').append($("<option></option>").attr("value",-1).text("Select Test Type"));
			    	$('#test_typej option').remove();
			    	$('#test_typej').append($("<option></option>").attr("value",-1).text("Select Test Type"));
			    	
			    	$(jsonData[0]).each(function(index)
			    			{
			    	    		
			    			   $('#test_type').append($("<option></option>").attr("value",this.id).text(this.name));
			    			   $('#test_typea').append($("<option></option>").attr("value",this.id).text(this.name));
			    			   $('#test_typeb').append($("<option></option>").attr("value",this.id).text(this.name));
			    			   $('#test_typec').append($("<option></option>").attr("value",this.id).text(this.name));
			    			   $('#test_typed').append($("<option></option>").attr("value",this.id).text(this.name));

			    			   $('#test_typee').append($("<option></option>").attr("value",this.id).text(this.name));
			    			   $('#test_typef').append($("<option></option>").attr("value",this.id).text(this.name));
			    			   $('#test_typeg').append($("<option></option>").attr("value",this.id).text(this.name));
			    			   $('#test_typeh').append($("<option></option>").attr("value",this.id).text(this.name));
			    			   $('#test_typei').append($("<option></option>").attr("value",this.id).text(this.name));
			    			   $('#test_typej').append($("<option></option>").attr("value",this.id).text(this.name));
			    			});
			    	
			    			
			    	$('#test_name_widget option').remove();
			    	$('#test_name_widget').append($("<option></option>").attr("value",-1).text("Select"));
			    	$('#test_namea_widget option').remove();
			    	$('#test_namea_widget').append($("<option></option>").attr("value",-1).text("Select"));
			    	$('#test_nameb_widget option').remove();
			    	$('#test_nameb_widget').append($("<option></option>").attr("value",-1).text("Select"));
			    	$('#test_namec_widget option').remove();
			    	$('#test_namec_widget').append($("<option></option>").attr("value",-1).text("Select"));
			    	$('#test_named_widget option').remove();
			    	$('#test_named_widget').append($("<option></option>").attr("value",-1).text("Select"));
			    	$('#test_namee_widget option').remove();
			    	$('#test_namee_widget').append($("<option></option>").attr("value",-1).text("Select"));
			    	$('#test_namef_widget option').remove();
			    	$('#test_namef_widget').append($("<option></option>").attr("value",-1).text("Select"));
			    	$('#test_nameg_widget option').remove();
			    	$('#test_nameg_widget').append($("<option></option>").attr("value",-1).text("Select"));
			    	$('#test_nameh_widget option').remove();
			    	$('#test_nameh_widget').append($("<option></option>").attr("value",-1).text("Select"));
			    	$('#test_namei_widget option').remove();
			    	$('#test_namei_widget').append($("<option></option>").attr("value",-1).text("Select"));
			    	$('#test_namej_widget option').remove();
			    	$('#test_namej_widget').append($("<option></option>").attr("value",-1).text("Select"));
			    	
			    	$(jsonData[1]).each(function(index)
			    	    	{
			    	    	   $('#test_name_widget').append($("<option></option>").attr("value",this.id).text(this.name));
			    	    	   $('#test_namea_widget').append($("<option></option>").attr("value",this.id).text(this.name));
			    	    	   $('#test_nameb_widget').append($("<option></option>").attr("value",this.id).text(this.name));
			    	    	   $('#test_namec_widget').append($("<option></option>").attr("value",this.id).text(this.name));
			    	    	   $('#test_named_widget').append($("<option></option>").attr("value",this.id).text(this.name));

			    	    	   $('#test_namee_widget').append($("<option></option>").attr("value",this.id).text(this.name));
			    	    	   $('#test_namef_widget').append($("<option></option>").attr("value",this.id).text(this.name));
			    	    	   $('#test_nameg_widget').append($("<option></option>").attr("value",this.id).text(this.name));
			    	    	   $('#test_nameh_widget').append($("<option></option>").attr("value",this.id).text(this.name));
			    	    	   $('#test_namei_widget').append($("<option></option>").attr("value",this.id).text(this.name));
			    	    	   $('#test_namej_widget').append($("<option></option>").attr("value",this.id).text(this.name));
			    	    	});
					
		       
			},
			   error: function() {
		            alert("error");
		        }
			 });
	    	
       $("#TicketDiv").html(feeddraft);
       
	},
	   error: function() {
            alert("error");
        }
	 });
}

function testName(id,divAppend)
{
	
	jsonArr.length = 0;
	var test=jsonData[1];
	
	for(var i = 0; i < test.length; i++)
	{
	  if(test[i].test_id == id)
	  {
		  item = {}
		    item["id"] = test[i].id;
		    item["name"] = test[i].name;
		    item["test_unit"] = test[i].test_unit;
		    item["min"] = test[i].min;
		    item["max"] = test[i].max;
		    jsonArr.push(item);
	  }
	}
	$('#test_name'+divAppend+'_widget option').remove();
	
/* 	$('#test_namea_widget').append($("<option></option>").attr("value",-1).text("All Test Name"));
	$('#test_nameb_widget').append($("<option></option>").attr("value",-1).text("All Test Name"));
	$('#test_namec_widget').append($("<option></option>").attr("value",-1).text("All Test Name"));
	$('#test_named_widget').append($("<option></option>").attr("value",-1).text("All Test Name")); */
	 $('#test_name'+divAppend+'_widget').append($("<option></option>").attr("value",-1).text("Select"));
	$(jsonArr).each(function(index)
	{
	   $('#test_name'+divAppend+'_widget').append($("<option></option>").attr("value",this.id).text(this.name));
	 
	});
}



function downloadFeedStatus()
{
	$("#takeActionGrid").dialog({title: 'Check Column',width: 350,height: 600});
	$("#takeActionGrid").dialog('open');
	$("#takeActionGrid").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");

	$.ajax({
	    type : "post",
	 	url : "/over2cloud/view/Over2Cloud/Critical/beforeCriticalDownload.action?fromDate="+$("#fromDate").val()+"&toDate="+$("#toDate").val()+"&status="+$("#status").val()+"&level="+$("#level").val()+"&searchString="+$("#searchStr").val()+"&nursingUnit="+$("#nursingUnit").val()+"&patient_status="+$("#patient_statusA").val()+"&doc_name1="+$("#doc_name1").val(),
	 	success : function(data) 
	    {
 			$("#takeActionGrid").html(data);
		},
	   error: function() {
	        alert("error");
	    }
	 });
}



function testUnit(id,div,div1,div2)
{
	
	var status="Not";
	for (var i = 0; i < jsonArr.length; i++){
		  // look for the entry with a matching `code` value
		  if (jsonArr[i].id == id){
			  $("#"+div).val(jsonArr[i].test_unit);
			  $("#"+div1).show();
			  $("#"+div2).show();
			  $("#"+div1).html("Min Value :"+jsonArr[i].min);
			  $("#"+div2).html("Max Value :"+jsonArr[i].max);
			  status="Ok";
		  }
	}
	if(status=="Not")
	  {
		  $("#"+div).html("");
		  $("#"+div1).hide();
		  $("#"+div2).hide();
		  $("#"+div1).html("Min Value : NA");
		  $("#"+div2).html("Max Value : NA");
	}	
	//var unitData = jsonArr.find(function(element, index, array) {
	//	  return element.id === id;
	//	});
	
}
	
function escalateAction(cellvalue, options, rowObject) 
{
	if((rowObject.status=="Informed") && (rowObject.escalate_date=="00:00" || rowObject.escalate_date=="0:0" ))
	{
		
		var context_path = '<%=request.getContextPath()%>';
		return "<a href='#'><img title='Escalate' src='/over2cloud/images/escalate.jpg' height='20' width='20' onClick='escalateOnClick("+rowObject.id+")'> </a>";
	}
	else
	{
		return "NA";
	}	
}


function escalateOnClick(id)
{

	var ticketNo = jQuery("#gridedittable").jqGrid('getCell',id,'ticket_no');
	ticketNo=$(ticketNo).text();
	var level = jQuery("#gridedittable").jqGrid('getCell',id,'level')

	var rid=jQuery("#gridedittable").jqGrid('getCell',id,'patient_id');
	var uhid=jQuery("#gridedittable").jqGrid('getCell',id,'uhid');

 	$("#takeActionGrid").dialog({title: 'Escalate Action for '+ticketNo,width: 600,height: 150});
	$("#takeActionGrid").html("<br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#takeActionGrid").dialog('open');
	$.ajax({	
		type : "post",
		url : "view/Over2Cloud/Critical/beforeEscalateAction.action?id="+rid+"&level="+level+"&uhid="+uhid,
		success : function(data) 
		{
		    $("#takeActionGrid").html(data);
		},
		error: function() {
		    alert("error");
		}
	});
}


function takeAction(cellvalue, options, rowObject) 
{
	var context_path = '<%=request.getContextPath()%>';
//	return "<a href='#'><img title='Take Action' src='"+ context_path +"/images/action.jpg' height='20' width='20' onClick='takeActionOnClick("+rowObject.id+")'> </a>";
	return "<a href='#'><img title='Take Action' src='/over2cloud/images/action.jpg' height='20' width='20' onClick='takeActionOnClick("+rowObject.id+")'></a>";

}






function takeActionOnClick(id) 
{
	var status = jQuery("#gridedittable").jqGrid('getCell',id,'status');
	if(id.length==0 )
	{
 		//alert("Please select atleast one check box...");
 		$("#takeActionGrid").dialog({title:"Alert!!!",height:100,width:400,dialogClass:'transparent'});
		$("#takeActionGrid").html('<div class="alert alert-danger" role="alert"><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span><span class="sr-only"></span>Please select atleast one check box...</div>');
		$("#takeActionGrid").dialog('open'); 
	}
	else if(status=='Close' || status=='OCC Ignore' || status=='Dept Ignore'|| status=='No Further Action Required')
	{
		//alert("Can't Take Action on "+status+" Referral.");
		$("#takeActionGrid").dialog({title:"Alert!!!",height:100,width:400,dialogClass:'transparent'});
		$("#takeActionGrid").html('<div class="alert alert-danger" role="alert"><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span><span class="sr-only"></span>Can not Take Action on '+status+' Critical.</div>');
		$("#takeActionGrid").dialog('open'); 
	}	
	else
	{
		var temp ="";
		temp += jQuery("#gridedittable").jqGrid('getCell',id,'caller_emp_id')+","+jQuery("#gridedittable").jqGrid('getCell',id,'caller_emp_name')+","+jQuery("#gridedittable").jqGrid('getCell',id,'caller_emp_mobile')+",";
		temp += jQuery("#gridedittable").jqGrid('getCell',id,'lab_doc')+","+jQuery("#gridedittable").jqGrid('getCell',id,'lab_doc_mob')+","+jQuery("#gridedittable").jqGrid('getCell',id,'uhid')+",";
		temp += jQuery("#gridedittable").jqGrid('getCell',id,'patient_name')+","+jQuery("#gridedittable").jqGrid('getCell',id,'patient_mob')+","+jQuery("#gridedittable").jqGrid('getCell',id,'patient_status')+",";
		temp += jQuery("#gridedittable").jqGrid('getCell',id,'adm_doc')+","+jQuery("#gridedittable").jqGrid('getCell',id,'adm_doc_mob')+",";
		temp += jQuery("#gridedittable").jqGrid('getCell',id,'nursing_unit')+","+jQuery("#gridedittable").jqGrid('getCell',id,'spec')+",";
		temp += jQuery("#gridedittable").jqGrid('getCell',id,'open_date')+","+jQuery("#gridedittable").jqGrid('getCell',id,'level')+",";
		temp += jQuery("#gridedittable").jqGrid('getCell',id,'test_type')+","+jQuery("#gridedittable").jqGrid('getCell',id,'patient_id')+","+jQuery("#gridedittable").jqGrid('getCell',id,'bed_no')+","
		+jQuery("#gridedittable").jqGrid('getCell',id,'specimen_no')+","+jQuery("#gridedittable").jqGrid('getCell',id,'his_added_at');
		
		var ticketNo = jQuery("#gridedittable").jqGrid('getCell',id,'ticket_no');
		ticketNo=$(ticketNo).text();
		var openDate = jQuery("#gridedittable").jqGrid('getCell',id,'open_date');
		var status = jQuery("#gridedittable").jqGrid('getCell',id,'status');
		var level = jQuery("#gridedittable").jqGrid('getCell',id,'level');
		level=$(level).text();
		var uhid = jQuery("#gridedittable").jqGrid('getCell',id,'uhid'); 
		var pid = jQuery("#gridedittable").jqGrid('getCell',id,'patient_id'); 
		$("#takeActionGrid").dialog({title: 'Take Action for '+ticketNo,width: 1200,height: 470});
		$("#takeActionGrid").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$("#takeActionGrid").dialog('open');
		$.ajax({	
			type : "post",
			url : "view/Over2Cloud/Critical/beforeTakeActionCritical.action?id="+id+"&status="+status+"&ticketNo="+ticketNo
			+"&openDate="+openDate+"&pid="+pid+"&level="+level+"&uhid="+uhid+"&viewData="+temp,
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

function refreshActivityBoard()
{
	  intvrl=setInterval("ReferralActivityRefresh()", (180*1000));
} 
function ReferralActivityRefresh()
{
	onSearchData();
	getStatusCounter();
} 
function clearInt()
{
	clearInterval(intvrl);
}
function StopRefresh()
{
		clearInterval(intvrl);
}
function StartRefresh()
{
 	refreshActivityBoard();
}

function fetchDropDownData(){
		$.ajax({	
		type : "post",
		 url : "view/Over2Cloud/Critical/fetchDropDownData.action?fromDate="+$("#fromDate").val()+"&toDate="+$("#toDate").val()+"&status="+$("#status").val()+"&priority="+$("#priority1").val()+"&searchString="+$("#searchStr").val()+"&nursingUnit="+$("#nursingUnit").val()+"&specialty="+$("#spec").val()+"&level="+$("#level").val()+"&refDocTo="+$("#reffto").val()+"&patient_status="+$("#patient_statusA").val(),
		success : function(data) 
		{
	
		console.log(data);
			//$('#spec option').remove();
			//$('#spec').append($("<option></option>").attr("value",-1).text("All Speciality"));
			var empData=data[0];
	    	$('#nursingUnit option').remove();
			$('#nursingUnit').append($("<option></option>").attr("value",-1).text("Nursing"));
			empData=data[0];
	    	$(empData).each(function(index)
			{
			  $('#nursingUnit').append($("<option></option>").attr("value",this.name).text(this.name));
			});

	    	$('#doc_name1 option').remove();
			$('#doc_name1').append($("<option></option>").attr("value",-1).text("Doctor"));
			empData=data[2];
	    	$(empData).each(function(index)
			{
				
			  $('#doc_name1').append($("<option></option>").attr("value",this.name).text(this.name));
			});
	    	$('#patient_speciality option').remove();
			$('#patient_speciality').append($("<option></option>").attr("value",-1).text("Specialty"));
	    	empData=data[1];
	    	$(empData).each(function(index)
	    			{
	    				
	    			  $('#patient_speciality').append($("<option></option>").attr("value",this.name).text(this.name));
	    			});

		    
		},
		error: function() {
		    alert("error");
		}
	});
}



function fetchDropDownData_NU_DocName()
{

document.getElementById("doc_name1").value = "-1";
document.getElementById("nursingUnit").value = "-1";
//alert("NU DOC Name...."+$("#doc_name1").val()+".......nursuing.."+$("#nursingUnit").val());

	$.ajax({	
		type : "post",
		 async: "false",
		 url : "view/Over2Cloud/Critical/fetchDropDownData.action?fromDate="+$("#fromDate").val()+"&toDate="+$("#toDate").val()+"&status="+$("#status").val()+"&priority="+$("#priority1").val()+"&searchString="+$("#searchStr").val()+"&nursingUnit="+$("#nursingUnit").val()+"&specialty="+$("#spec").val()+"&level="+$("#level").val()+"&refDocTo="+$("#reffto").val()+"&patient_status="+$("#patient_statusA").val()+"&patient_speciality="+$("#patient_speciality").val(),
		success : function(data) 
		{
		var empData=data[0];
    	$('#nursingUnit option').remove();
		$('#nursingUnit').append($("<option></option>").attr("value",-1).text("Select Nursing Unit"));
		empData=data[0];
    	$(empData).each(function(index)
		{
		  $('#nursingUnit').append($("<option></option>").attr("value",this.name).text(this.name));
		});

    	$('#doc_name1 option').remove();
		$('#doc_name1').append($("<option></option>").attr("value",-1).text("Select Doctor"));
		empData=data[2];
    	$(empData).each(function(index)
		{
			
		  $('#doc_name1').append($("<option></option>").attr("value",this.name).text(this.name));
		});
		},
		error: function() {
		    alert("error");
		}
	});
	
}


function fetchDropDownData_Doc_name()
{
	
	
	document.getElementById("doc_name1").value = "-1";
//	alert("only DOC Name"+$("#doc_name1").val());
	
	$.ajax({	
		type : "post",
		 async: "false",
		 url : "view/Over2Cloud/Critical/fetchDropDownData.action?fromDate="+$("#fromDate").val()+"&toDate="+$("#toDate").val()+"&status="+$("#status").val()+"&priority="+$("#priority1").val()+"&searchString="+$("#searchStr").val()+"&nursingUnit="+$("#nursingUnit").val()+"&specialty="+$("#spec").val()+"&level="+$("#level").val()+"&refDocTo="+$("#reffto").val()+"&patient_status="+$("#patient_statusA").val()+"&patient_speciality="+$("#patient_speciality").val(),
		success : function(data) 
		{
		
    	$('#doc_name1 option').remove();
		$('#doc_name1').append($("<option></option>").attr("value",-1).text("Select Doctor"));
		var empData=data[2];
    	$(empData).each(function(index)
		{
			
		  $('#doc_name1').append($("<option></option>").attr("value",this.name).text(this.name));
		});
		},
		error: function() {
		    alert("error");
		}
	});
	



}

/*function downloadFeedStatus()
{
	
 	$("#takeActionGrid").dialog({title: 'Check Column',width: 350,height: 600});
	$("#takeActionGrid").dialog('open');
	$("#takeActionGrid").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");

	$.ajax({
	    type : "post",
	 	url : "/over2cloud/view/Over2Cloud/Referral/beforeReferralDownload.action?fromDate="+$("#fromDate").val()+"&toDate="+$("#toDate").val()+"&status="+$("#status").val()+"&priority="+$("#priority1").val()+"&searchString="+$("#searchStr").val()+"&nursingUnit="+$("#nursingUnit").val()+"&specialty="+$("#spec").val()+"&level="+$("#level").val(),
	 	success : function(data) 
	    {
 			$("#takeActionGrid").html(data);
		},
	   error: function() {
	        alert("error");
	    }
	 });
}*/
function maximizeWindow(id)
	{
	//alert(id);
	if(id=='both')
		{
		$("#both").show();
		$("#grid").hide();
		$("#lodge").hide();
 		$("#TicketDiv").show();
		$("#listPart").show();
 		}
	else if(id=='grid')
		{
		$("#grid").show();
		$("#both").hide();
		$("#lodge").hide();
		$("#listPart").show();
 		$("#TicketDiv").hide();

		}
	else if(id=='lodge')
	{
		$("#lodge").show();
		$("#grid").hide();
		$("#both").hide();
			$("#TicketDiv").show();
			$("#listPart").hide();
			
	}
}
 
function getStatusCounter()
{
	//alert("hii");
	  $.ajax
	({
	type : "post",
	url  : "view/Over2Cloud/Critical/getCounterStatus.action?fromDate="+$("#fromDate").val()+"&toDate="+$("#toDate").val(),
	async:false,
	success : function(data)
	{
		
		console.log("Counter 000 "+data[0]);
		console.log("Counter 111 "+data[1]);
		if(data.length>0){
			var total=0;
			$("#statusCounter").empty();
			var temp="";
			if(data[0].NotInformed>0)
			{
			temp+='<div class="TitleBorder"><h1>Not Informed</h1><div class="circle">'+data[0].NotInformed+'</div></div>';
			total+=parseInt(data[0].NotInformed);
			}
			
			if(data[0].InformedP>0)
			{
			temp+='<div class="TitleBorder"><h1>Partial Informed</h1><div class="circle">'+data[0].InformedP+'</div></div>';
			total+=parseInt(data[0].InformedP);
			}
			
			if(data[0].Informed>0)
			{
			temp+='<div class="TitleBorder"><h1>Informed</h1><div class="circle">'+data[0].Informed+'</div></div>';
			total+=parseInt(data[0].Informed);
			}
			
			
			if(data[0].Snooze>0)
			{
			temp+='<div class="TitleBorder"><h1>Park</h1><div class="circle">'+data[0].Snooze+'</div></div>';
			total+=parseInt(data[0].Snooze);
			}
			
			if(data[0].Ignore>0)
			{
			temp+='<div class="TitleBorder"><h1>Ignore</h1><div class="circle">'+data[0].Ignore+'</div></div>';
			total+=parseInt(data[0].Ignore);
			}
			if(data[0].Close>0)
			{
			temp+='<div class="TitleBorder"><h1>Close</h1><div class="circle">'+data[0].Close+'</div></div>';
			total+=parseInt(data[0].Close);
			}
			console.log("temp "+temp);
			$("#statusCounter").append(temp);
		}
	
	},
	error : function()
	{
	alert("Error on data fetch");
	} 
	}); 
	
}

function setTestType(abc,appendCharOnDiv){
	
var test=jsonData[1];
var testT=jsonData[0];

//alert(abc);

	for(var i = 0; i < test.length; i++)
	{
		
		 
	  if(test[i].id == abc)
	  {
		  item = {}
		    item["id"] = test[i].id;
		    item["name"] = test[i].name;
		    item["test_unit"] = test[i].test_unit;
		    item["min"] = test[i].min;
		    item["max"] = test[i].max;
	
		    jsonArr.push(item);
		  
		    
		/*    console.log("sddddddddddddddd........."+testT[i].name+".....hjfgjkfdhgjfdhgkjdfhg..."+testT[i].id);*/
		  
		    
		    if(appendCharOnDiv!=''){
		    	  $('#test_type'+appendCharOnDiv+' option').remove();
				    $('#test_type'+appendCharOnDiv).append($("<option></option>").attr("value",test[i].LabID).text(test[i].LabName));
		    }else{
		    	 $('#test_type option').remove();
				    $('#test_type').append($("<option></option>").attr("value",test[i].LabID).text(test[i].LabName));
		    }
		  
		  
	  }
	}

	
}


function isNull_testName_txt(txt_val,divAppendChar)
{
	//alert("value in txt field is "+txt_val);
	if(txt_val!='' || txt_val!="")
		{
		//alert("value is empty.."+txt_val);
		
		}
	else{
		 
		//test_name\
		$("#test_name_div_"+divAppendChar).show();
		$("#test_name_div_txt_"+divAppendChar).hide();
		 $("#test_name_txt_fd_"+divAppendChar).attr("name", "test_name_txt");
		 $("#test_name"+divAppendChar).attr("name", "test_name");
		// $("#test_name_div_"+divAppendChar).children(".ui-autocomplete-input").focus());
		 
		// document.getElementById("test_name"+divAppendChar).focus();
	
	//	 $(".ui-autocomplete-input").val("");
		 
		//$("#test_name_div_"+divAppendChar).children("ui-autocomplete-input").val("");
		
		$("#test_name_div_"+divAppendChar).children(".ui-autocomplete-input").val("");
		 
		 $("#test_unit"+divAppendChar).val("");
	}
	
}


function makeChangesInDD(asd,divChar,minMax){
	
//	alert("value"+asd);
	if(asd!='0')
		{
	
	//	alert(asd+"inside if");
				if(asd=='-1'){
					setDropDownVal(divChar,minMax);
					$("#test_unit"+divChar).val("");
					$("#test_name_div_"+divChar).children(".ui-autocomplete-input").val("");
					
				}else{
					
				setTestType(asd,divChar);
					testUnit(asd,"test_unit"+divChar,"min"+minMax,"max"+minMax);
				}
		}else{
			//alert("inside else");
		
			$("#test_name_div_"+divChar).hide();
			$("#test_name_div_txt_"+divChar).show();
			 $("#test_name"+divChar).attr("name", "test_name_change");
			 $("#test_name_txt_fd_"+divChar).attr("name", "test_name");
			 document.getElementById("test_name_txt_fd_"+divChar).focus();
			 $("#test_name"+divChar).val("-2");
			 $("#test_unit"+divChar).val("");
			}

}


$.subscribe('viewUnit', function(event,data)
	{
	//var values=$("#test_name").val();
	var values=$("#test_name_widget option:selected").val();
		makeChangesInDD(values,'','1');
	});

$.subscribe('viewUnit1', function(event,data)
		{
	var values=$("#test_namea_widget option:selected").val();
	makeChangesInDD(values,'a','2');
	/*
		}
	if($("#test_namea").val()!='0')
	{
	
	if($("#test_namea").val()=='-1'){
		setDropDownVal('a','2');
		$("#test_unita").val("");
		// $(".ui-autocomplete-input").val("");
	}else{
//	alert("bbb  "+data.value);
setTestType(data.value,'a');
			testUnit(data.value,"test_unita","min2","max2");
			
	}
	}else{
		$("#test_name_div_a").hide();
		$("#test_name_div_txt_a").show();
		 $("#test_namea").attr("name", "test_name_change");
		 $("#test_name_txt_fd_a").attr("name", "test_name");
		 document.getElementById("test_name_txt_fd_a").focus();
		 $("#test_namea").val("-2");
		 $("#test_unita").val("");
		}
	
		*/});

$.subscribe('viewUnit2', function(event,data)
		{
	var values=$("#test_nameb_widget option:selected").val();
	makeChangesInDD(values,'b','3');
	
	/*
	
	
	if($("#test_nameb").val()!='0')
	{
	
	if($("#test_nameb").val()=='-1'){
		setDropDownVal('b','3');
		$("#test_unitb").val("");
	//	 $(".ui-autocomplete-input").val("");
	}else{
	setTestType(data.value,'b');		
	testUnit(data.value,"test_unitb","min3","max3");
	}
	}else{
		$("#test_name_div_b").hide();
		$("#test_name_div_txt_b").show();
		 $("#test_nameb").attr("name", "test_name_change");
		 $("#test_name_txt_fd_b").attr("name", "test_name");
		 document.getElementById("test_name_txt_fd_b").focus();
		 $("#test_nameb").val("-2");
		 $("#test_unitb").val("");
		}
	*/});

$.subscribe('viewUnit3', function(event,data)
		{
			
	var values=$("#test_namec_widget option:selected").val();
	makeChangesInDD(values,'c','4');
	
	/*
	
	if($("#test_namec").val()!='0')
	{
	
	if($("#test_namec").val()=='-1'){
		setDropDownVal('c','4');
		$("#test_unitc").val("");
	//	 $(".ui-autocomplete-input").val("");
	}else{
		
	setTestType(data.value,'c');	
	testUnit(data.value,"test_unitc","min4","max4");
	}
	}else{
		$("#test_name_div_c").hide();
		$("#test_name_div_txt_c").show();
		 $("#test_namec").attr("name", "test_name_change");
		 $("#test_name_txt_fd_c").attr("name", "test_name");
		 document.getElementById("test_name_txt_fd_c").focus();
		 $("#test_namec").val("-2");
		 $("#test_unitc").val("");
		}
		*/});



$.subscribe('viewUnit4', function(event,data)
		{
	var values=$("#test_named_widget option:selected").val();
	makeChangesInDD(values,'d','5');
	
	/*
		
	if($("#test_named").val()!='0')
	{

	if($("#test_named").val()=='-1'){
		setDropDownVal('d','5');
		$("#test_unitd").val("");
		// $(".ui-autocomplete-input").val("");
	}else{
	setTestType(data.value,'d');
	testUnit(data.value,"test_unitd","min5","max5");
		}
	}else{
		$("#test_name_div_d").hide();
		$("#test_name_div_txt_d").show();
		 $("#test_named").attr("name", "test_name_change");
		 $("#test_name_txt_fd_d").attr("name", "test_name");
		 document.getElementById("test_name_txt_fd_d").focus();
		 $("#test_named").val("-2");
		 $("#test_unitd").val("");
		}
	*/});

$.subscribe('viewUnit5', function(event,data)
		{
	
	var values=$("#test_namee_widget option:selected").val();
	makeChangesInDD(values,'e','6');
	/*
		
	if($("#test_namee").val()!='0')
	{

	if($("#test_namee").val()=='-1'){
		setDropDownVal('e','6');
		$("#test_unite").val("");
		// $(".ui-autocomplete-input").val("");
	}else{
	setTestType(data.value,'e');	
	testUnit(data.value,"test_unite","min6","max6");
		
		}
	}else{
		$("#test_name_div_e").hide();
		$("#test_name_div_txt_e").show();
		 $("#test_namee").attr("name", "test_name_change");
		 $("#test_name_txt_fd_e").attr("name", "test_name");
		 document.getElementById("test_name_txt_fd_e").focus();
		 $("#test_namee").val("-2");
		 $("#test_unite").val("");
		}
	*/});

$.subscribe('viewUnit6', function(event,data)
		{
	var values=$("#test_namef_widget option:selected").val();
	makeChangesInDD(values,'f','7');
	
	/*
		
	if($("#test_namef").val()!='0')
	{
	
	if($("#test_namef").val()=='-1'){
		setDropDownVal('f','7');
		$("#test_unitf").val("");
	//	 $(".ui-autocomplete-input").val("");
	}else{
	setTestType(data.value,'f');	
	testUnit(data.value,"test_unitf","min7","max7");
		}
	}else{
		$("#test_name_div_f").hide();
		$("#test_name_div_txt_f").show();
		 $("#test_namef").attr("name", "test_name_change");
		 $("#test_name_txt_fd_f").attr("name", "test_name");
		 document.getElementById("test_name_txt_fd_f").focus();
		 $("#test_namef").val("-2");
		 $("#test_unitf").val("");
		}
		*/});

$.subscribe('viewUnit7', function(event,data)
		{
	
	var values=$("#test_nameg_widget option:selected").val();
	makeChangesInDD(values,'g','8');
	/*
		
	if($("#test_nameg").val()!='0')
	{
	if($("#test_nameg").val()=='-1'){
		setDropDownVal('g','8');
		$("#test_unitg").val("");
		// $(".ui-autocomplete-input").val("");
	}else{
	setTestType(data.value,'g');	
	testUnit(data.value,"test_unitg","min8","max8");
		}
	}else{
		$("#test_name_div_g").hide();
		$("#test_name_div_txt_g").show();
		 $("#test_nameg").attr("name", "test_name_change");
		 $("#test_name_txt_fd_g").attr("name", "test_name");
		 document.getElementById("test_name_txt_fd_g").focus();
		 $("#test_nameg").val("-2");
		 $("#test_unitg").val("");
		}
	*/});

$.subscribe('viewUnit8', function(event,data)
		{
	
	var values=$("#test_namei_widget option:selected").val();
	makeChangesInDD(values,'i','9');
	/*
	
	if($("#test_nameh").val()!='0')
	{
	if($("#test_nameh").val()=='-1'){
		setDropDownVal('h','9');
		$("#test_unith").val("");
		// $(".ui-autocomplete-input").val("");
	}else{
	setTestType(data.value,'h');
	testUnit(data.value,"test_unith","min9","max9");
		}
	}else{
		$("#test_name_div_h").hide();
		$("#test_name_div_txt_h").show();
		 $("#test_nameh").attr("name", "test_name_change");
		 $("#test_name_txt_fd_h").attr("name", "test_name");
		 document.getElementById("test_name_txt_fd_h").focus();
		 $("#test_nameh").val("-2");
		 $("#test_unith").val("");
		}
	*/});

$.subscribe('viewUnit9', function(event,data)
		{
	var values=$("#test_namej_widget option:selected").val();
	makeChangesInDD(values,'j','10');
	/*
		
	if($("#test_namei").val()!='0')
	{
	if($("#test_namei").val()=='-1'){
		setDropDownVal('i','10');
		$("#test_uniti").val("");
		// $(".ui-autocomplete-input").val("");
	}else{
	setTestType(data.value,'i');	
	testUnit(data.value,"test_uniti","min10","max10");
	}
	}else{
		$("#test_name_div_i").hide();
		$("#test_name_div_txt_i").show();
		 $("#test_namei").attr("name", "test_name_change");
		 $("#test_name_txt_fd_i").attr("name", "test_name");
		 document.getElementById("test_name_txt_fd_i").focus();
		 $("#test_namei").val("-2");
		 $("#test_uniti").val("");
		}
		*/});


$.subscribe('fetchDoctorName', function(event,data)
	{
testUnit(id,div);
	});