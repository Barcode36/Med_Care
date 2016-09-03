function getDetailsData(value, dataFor, divId,errDiv,sourceDiv) {
	value=value.toUpperCase();
	if(value=='NA')
	{
		$("#"+divId).attr("readonly", false);
		$("#"+divId).focus();
	}	
	else
	{
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/Referral/fetchDetails.action?uhid=" + value + "&dataFor=" + dataFor + "&patType=" +$("#patient_type").val(),
			success : function(data) {

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
						$("#" + divId).val(data[0].fName + " " + data[0].lName);
					}	
				} 
				else 
				{
					if(data[0].pName==undefined)
					{
						$("#"+errDiv).html("Please Enter Valid UHID!!!");
						$("#" + sourceDiv).css("background-color", "#ff701a");
						setTimeout(function() {$("#"+errDiv).fadeIn();}, 10);
						setTimeout(function() {$("#"+errDiv).fadeOut();}, 4000);
					}	
					else
					{
						if($("#patient_type").val()=='IPD')
						{
							$("#patient_name").val(data[0].pName);
							$("#bed").val(data[0].bedNo);
							$("#nursing_unit").val(data[0].nursingUnit);
							$("#adm_doc").val(data[0].admDoc);
							$("#adm_spec").val(data[0].admSpec);
						}
						else
						{
							$("#patient_name").val(data[0].pName);
							$("#bed").val(data[0].bedNo);
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

	function fetchPatientType(value)
	{
		if(value=='Emergency')
		{
			$("#nursing_unit").val("Emergency");
			$("#patient_name_div_Emergency").show();
			$("#patient_name_div_IPD").hide();
			$("#adm_doc").val("Emergency Team");
			$("#adm_spec").val("Emergency and Trauma Services");
			$("#priority").val("Stat");
			$("#patient_name").val("");
			$("#bed").val("");
			$("#uhid").val("MM");
		}
		else
		{
			$("#patient_name_div_IPD").show();
			$("#patient_name_div_Emergency").hide();
			$("#priority").val("-1");
			$("#nursing_unit").val("");
			$("#adm_doc").val("");
			$("#adm_spec").val("");
		}	
	}

function resetColor(id)
{    
    var mystring = $(id).text();
    var fieldtype = mystring.split(",");
    for(var i=0; i<fieldtype.length; i++)
    {
        var fieldsvalues = fieldtype[i].split("#")[0];
        $("#"+fieldsvalues).css("background-color","");
    }
}

$.subscribe('validate', function(event, data) {
	// alert("hh");
	//console.log(data);
	var mystring = $(".pIds").text();
	var fieldtype = mystring.split(",");
	for (var i = 0; i < fieldtype.length; i++) {

		var fieldsvalues = fieldtype[i].split("#")[0];
		var fieldsnames = fieldtype[i].split("#")[1];
		var colType = fieldtype[i].split("#")[2]; // fieldsType[i]=first_name#t
		var validationType = fieldtype[i].split("#")[3];

		$("#" + fieldsvalues).css("background-color", "");
		errZone.innerHTML = "";
		if (fieldsvalues != "") {
			if (colType == "D") {

				if ($("#" + fieldsvalues).val() == "" || $("#" + fieldsvalues).val() == "-1") {
					errZone.innerHTML = "<div class='user_form_inputError2'>Please Select " + fieldsnames + " Value from Drop Down </div>";
					setTimeout(function() {
						$("#errZone").fadeIn();
					}, 10);
					setTimeout(function() {
						$("#errZone").fadeOut();
					}, 2000);
					$("#" + fieldsvalues).focus();
					$("#" + fieldsvalues).css("background-color", "#ff701a");
					event.originalEvent.options.submit = false;
					break;
				}
			} else if (colType == "T") {
				if (validationType == "n") {
					var numeric = /^[0-9]+$/;
					if (!(numeric.test($("#" + fieldsvalues).val()))) {
						errZone.innerHTML = "<div class='user_form_inputError2'>Please Enter Numeric Values of " + fieldsnames + "</div>";
						setTimeout(function() {
							$("#errZone").fadeIn();
						}, 10);
						setTimeout(function() {
							$("#errZone").fadeOut();
						}, 2000);
						$("#" + fieldsvalues).focus();
						$("#" + fieldsvalues).css("background-color", "#ff701a");
						event.originalEvent.options.submit = false;
						break;
					}
				}
				if (validationType == "t") {
					var numeric = /^[0-9:]+$/;
					if (!(numeric.test($("#" + fieldsvalues).val()))) {
						errZone.innerHTML = "<div class='user_form_inputError2'>Please Enter Correct Time Format [HH:MM] of " + fieldsnames + " Field</div>";
						setTimeout(function() {
							$("#errZone").fadeIn();
						}, 10);
						setTimeout(function() {
							$("#errZone").fadeOut();
						}, 2000);
						$("#" + fieldsvalues).focus();
						$("#" + fieldsvalues).css("background-color", "#ff701a");
						event.originalEvent.options.submit = false;
						break;
					}
				}

				else if (validationType == "an") {
					var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
					if (!(allphanumeric.test($("#" + fieldsvalues).val()))) {
						errZone.innerHTML = "<div class='user_form_inputError2'>Please Enter Alpha Numeric of " + fieldsnames + "</div>";
						setTimeout(function() {
							$("#errZone").fadeIn();
						}, 10);
						setTimeout(function() {
							$("#errZone").fadeOut();
						}, 2000);
						$("#" + fieldsvalues).focus();
						$("#" + fieldsvalues).css("background-color", "#ff701a");
						event.originalEvent.options.submit = false;
						break;
					}
				} else if (validationType == "a") {
					if (!(/^[a-zA-Z ]+$/.test($("#" + fieldsvalues).val()))) {
						errZone.innerHTML = "<div class='user_form_inputError2'>Please Enter Alphabate Values of " + fieldsnames + "</div>";
						setTimeout(function() {
							$("#errZone").fadeIn();
						}, 10);
						setTimeout(function() {
							$("#errZone").fadeOut();
						}, 2000);
						$("#" + fieldsvalues).focus();
						$("#" + fieldsvalues).css("background-color", "#ff701a");
						event.originalEvent.options.submit = false;
						break;
					}
				}

				else if (validationType == "sc") {
					if ($("#" + fieldsvalues).val().length < 1) {
						errZone.innerHTML = "<div class='user_form_inputError2'>Enter " + fieldsnames + " !!! </div>";
						$("#" + fieldsvalues).css("background-color", "#ff701a"); // 255;165;79
						$("#" + fieldsvalues).focus();
						setTimeout(function() {
							$("#errZone").fadeIn();
						}, 10);
						setTimeout(function() {
							$("#errZone").fadeOut();
						}, 2000);
						event.originalEvent.options.submit = false;
						break;
					}
				}
			}

			else if (colType == "TextArea") {
				if (validationType == "sc") {
					if ($("#" + fieldsvalues).val().length < 1) {
						errZone.innerHTML = "<div class='user_form_inputError2'>Enter " + fieldsnames + " !!! </div>";
						$("#" + fieldsvalues).css("background-color", "#ff701a"); // 255;165;79
						$("#" + fieldsvalues).focus();
						setTimeout(function() {
							$("#errZone").fadeIn();
						}, 10);
						setTimeout(function() {
							$("#errZone").fadeOut();
						}, 2000);
						event.originalEvent.options.submit = false;
						break;
					}
				}
			}  
		}
	}
});

$.subscribe('validate1', function(event, data) {
	//console.log(data);
	// var value11 = $("#assign_to_emp_id").val();
	// alert('The Value  '+value11);
	
	 var value123 = $("#assignIdInform1:checked").val();
		//alert('The Value in textbox>>>>>>>   '+value123);
	var value1234 = $("#assignIdInform2:checked").val();
		//alert('The Value in textbox value1234 >>>>>>  '+value1234);
		
		var value1235 = $("#assignIdSeen1:checked").val();
		//alert('The Value in textbox>>>>>>>   '+value123);
	var value1236 = $("#assignIdSeen2:checked").val();
		//alert('The Value in textbox value1234 >>>>>>  '+value1234);
		
		
	var mystring="";
	if($("#status1").val()!='' && $("#status1").val()=='Informed' && value123=='1')
	{
		//alert("hhh");
		if($("#assign_to_id_inf").val()!='')
		{
		mystring=$(".inpIds").text();
		}
		else if($("#assign_to_emp_id").val()=='')
		{
		mystring=$(".dnpIds").text();
		}
		
		
	}
	else if($("#status1").val()!='' && $("#status1").val()=='Informed' && value1234=='2')
	{
		//alert("jjjjj");
		mystring=$(".AnpIds").text();
	}
	
	else if($("#status1").val()!='' && $("#status1").val()=='Seen' &&  value1235=='1')
	{
		if($("#assign_to_id_inf").val()!='')
		{
		mystring=$(".spIds").text();
		}
		else if($("#assign_to_emp_id").val()=='')
		{
		mystring=$(".kpIds").text();
		}
		else
		{
			mystring=$(".spIds").text();
		}
		
	}
	
	else if($("#status1").val()!='' && $("#status1").val()=='Seen' && value1236=='2')
	{
		//alert("ppp");
		mystring=$(".jpIds").text();
		if($("#assign_desig").val()=='-1')
		{
		mystring=$(".spIds").text();
		}
	}
	
	else if($("#status1").val()!='' && $("#status1").val()=='Seen' && $("#formone1_assignId2").val()=='2' )
	{
		//alert("rrr");
			mystring=$(".jpIds").text();
	}
	
	
	
	
	
	else if($("#status1").val()!='' && ($("#status1").val()=='Snooze' || $("#status1").val()=='Snooze-I'))
	{
		
		mystring=$(".snpIds").text();
	}
	else if($("#status1").val()!='' && ($("#status1").val()=='Ignore' || $("#status1").val()=='Ignore-I'))
	{
		
		mystring=$(".igpIds").text();
	}
	else
	{
		mystring=$(".nopIds").text();
	}
	//alert(mystring+","+$("#status").val());
	var fieldtype = mystring.split(",");
	for (var i = 0; i < fieldtype.length; i++) {

		var fieldsvalues = fieldtype[i].split("#")[0];
		var fieldsnames = fieldtype[i].split("#")[1];
		var colType = fieldtype[i].split("#")[2]; // fieldsType[i]=first_name#t
		var validationType = fieldtype[i].split("#")[3];
		 //alert(fieldsvalues+"  "+fieldsnames+" "+colType+"  "+$("#"+fieldsvalues).val());
		$("#" + fieldsvalues).css("background-color", "");
		errZone1.innerHTML = "";
		if (fieldsvalues != "") {
			
			if (colType == "D") {

				if(($("#"+fieldsvalues).val()==null || $("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1"))
	            {
	            	//alert("sdkfhkldshh");
		            errZone1.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
		            setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;   
	            }
			} else if (colType == "T") {
				if (validationType == "n") {
					var numeric = /^[0-9]+$/;
					if (!(numeric.test($("#" + fieldsvalues).val()))) {
						errZone1.innerHTML = "<div class='user_form_inputError2'>Please Enter Numeric Values of " + fieldsnames + "</div>";
						setTimeout(function() {
							$("#errZone1").fadeIn();
						}, 10);
						setTimeout(function() {
							$("#errZone1").fadeOut();
						}, 2000);
						$("#" + fieldsvalues).focus();
						$("#" + fieldsvalues).css("background-color", "#ff701a");
						event.originalEvent.options.submit = false;
						break;
					}
				}
				if (validationType == "t") {
					var numeric = /^[0-9:]+$/;
					if (!(numeric.test($("#" + fieldsvalues).val()))) {
						errZone1.innerHTML = "<div class='user_form_inputError2'>Please Enter Correct Time Format [HH:MM] of " + fieldsnames + " Field</div>";
						setTimeout(function() {
							$("#errZone1").fadeIn();
						}, 10);
						setTimeout(function() {
							$("#errZone1").fadeOut();
						}, 2000);
						$("#" + fieldsvalues).focus();
						$("#" + fieldsvalues).css("background-color", "#ff701a");
						event.originalEvent.options.submit = false;
						break;
					}
				}

				else if (validationType == "an") {
					var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
					if (!(allphanumeric.test($("#" + fieldsvalues).val()))) {
						errZone1.innerHTML = "<div class='user_form_inputError2'>Please Enter Alpha Numeric of " + fieldsnames + "</div>";
						setTimeout(function() {
							$("#errZone1").fadeIn();
						}, 10);
						setTimeout(function() {
							$("#errZone1").fadeOut();
						}, 2000);
						$("#" + fieldsvalues).focus();
						$("#" + fieldsvalues).css("background-color", "#ff701a");
						event.originalEvent.options.submit = false;
						break;
					}
				} 
				
				else if(validationType=="m"){
			           if($("#"+fieldsvalues).val().length > 10 || $("#"+fieldsvalues).val().length < 10)
			            {
			                errZone1.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
			                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			                $("#"+fieldsvalues).focus();
			                setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
			                setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
			                event.originalEvent.options.submit = false;
			                break;
			            }
			            else if (!pattern.test($("#"+fieldsvalues).val())) {
			                errZone1.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
			                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			                $("#"+fieldsvalues).focus();
			                setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
			                setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
			                event.originalEvent.options.submit = false;
			                break;
			             }
			                else if(($("#"+fieldsvalues).val().charAt(0)!="9") && ($("#"+fieldsvalues).val().charAt(0)!="8") && ($("#"+fieldsvalues).val().charAt(0)!="7") && ($("#"+fieldsvalues).val().charAt(0)!="6"))
			             {
			                errZone1.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Should Start with 9,8,7,6.  </div>";
			                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			                $("#"+fieldsvalues).focus();
			                setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
			                setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
			                event.originalEvent.options.submit = false;
			                break;
			               }
			             }
				else if (validationType == "a") {
					if (!(/^[a-zA-Z ]+$/.test($("#" + fieldsvalues).val()))) {
						errZone1.innerHTML = "<div class='user_form_inputError2'>Please Enter Alphabate Values of " + fieldsnames + "</div>";
						setTimeout(function() {
							$("#errZone1").fadeIn();
						}, 10);
						setTimeout(function() {
							$("#errZone1").fadeOut();
						}, 2000);
						$("#" + fieldsvalues).focus();
						$("#" + fieldsvalues).css("background-color", "#ff701a");
						event.originalEvent.options.submit = false;
						break;
					}
				}

				else if (validationType == "sc") {
					if ($("#" + fieldsvalues).val().length < 1) {
						errZone1.innerHTML = "<div class='user_form_inputError2'>Enter " + fieldsnames + " !!! </div>";
						$("#" + fieldsvalues).css("background-color", "#ff701a"); // 255;165;79
						$("#" + fieldsvalues).focus();
						setTimeout(function() {
							$("#errZone1").fadeIn();
						}, 10);
						setTimeout(function() {
							$("#errZone1").fadeOut();
						}, 2000);
						event.originalEvent.options.submit = false;
						break;
					}
				}
			}

			else if (colType == "TextArea") {
				if (validationType == "sc") {
					if ($("#" + fieldsvalues).val().length < 1) {
						errZone1.innerHTML = "<div class='user_form_inputError2'>Enter " + fieldsnames + " !!! </div>";
						$("#" + fieldsvalues).css("background-color", "#ff701a"); // 255;165;79
						$("#" + fieldsvalues).focus();
						setTimeout(function() {
							$("#errZone1").fadeIn();
						}, 10);
						setTimeout(function() {
							$("#errZone1").fadeOut();
						}, 2000);
						event.originalEvent.options.submit = false;
						break;
					}
					else if($("#" + fieldsvalues).val().length < 20)
					{
						errZone1.innerHTML = "<div class='user_form_inputError2'>Enter Atleast 20 characters for " + fieldsnames + " !!! </div>";
						$("#" + fieldsvalues).css("background-color", "#ff701a"); // 255;165;79
						$("#" + fieldsvalues).focus();
						setTimeout(function() {
							$("#errZone1").fadeIn();
						}, 10);
						setTimeout(function() {
							$("#errZone1").fadeOut();
						}, 2000);
						event.originalEvent.options.submit = false;
						break;
					}	
						
				}
			} else if (colType == "Time") {
				if ($("#" + fieldsvalues).val() == "") {
					errZone1.innerHTML = "<div class='user_form_inputError2'>Please Select " + fieldsnames + " Value </div>";
					setTimeout(function() {
						$("#errZone1").fadeIn();
					}, 10);
					setTimeout(function() {
						$("#errZone1").fadeOut();
					}, 2000);
					$("#" + fieldsvalues).focus();
					$("#" + fieldsvalues).css("background-color", "#ff701a");
					event.originalEvent.options.submit = false;
					break;
				}
			} else if (colType == "Date") {
				if ($("#" + fieldsvalues).val() == "") {
					errZone1.innerHTML = "<div class='user_form_inputError2'>Please Select " + fieldsnames + " Value  </div>";
					setTimeout(function() {
						$("#errZone1").fadeIn();
					}, 10);
					setTimeout(function() {
						$("#errZone1").fadeOut();
					}, 2000);
					$("#" + fieldsvalues).focus();
					$("#" + fieldsvalues).css("background-color", "#ff701a");
					event.originalEvent.options.submit = false;
					break;
				}
			}
		}
	}
});

$.subscribe('validateEsc', function(event, data) {
	//console.log(data);
	var mystring="";
	if($( "input:radio[name=actionVia]:checked" ).val()!='' && $( "input:radio[name=actionVia]:checked" ).val()=='2')
	{
		mystring=$(".escIds").text();
	}
	else
	{
		mystring=$(".escIds").text();
		mystring=mystring.split(",")[0];
	}	
	var pattern = /^\d{10}$/;
	var fieldtype = mystring.split(",");
	for (var i = 0; i < fieldtype.length; i++) {

		var fieldsvalues = fieldtype[i].split("#")[0];
		var fieldsnames = fieldtype[i].split("#")[1];
		var colType = fieldtype[i].split("#")[2]; // fieldsType[i]=first_name#t
		var validationType = fieldtype[i].split("#")[3];

		$("#" + fieldsvalues).css("background-color", "");
		errZone12.innerHTML = "";
		if (fieldsvalues != "") {
			 if (colType == "T") {
				if (validationType == "sc") {
					if ($("#" + fieldsvalues).val().length < 1) {
						errZone12.innerHTML = "<div class='user_form_inputError2'>Enter " + fieldsnames + " !!! </div>";
						$("#" + fieldsvalues).css("background-color", "#ff701a"); // 255;165;79
						$("#" + fieldsvalues).focus();
						setTimeout(function() {
							$("#errZone12").fadeIn();
						}, 10);
						setTimeout(function() {
							$("#errZone12").fadeOut();
						}, 2000);
						event.originalEvent.options.submit = false;
						break;
					}
				}
				else if(validationType=="m"){
			           if($("#"+fieldsvalues).val().length > 10 || $("#"+fieldsvalues).val().length < 10)
			            {
			                errZone12.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
			                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			                $("#"+fieldsvalues).focus();
			                setTimeout(function(){ $("#errZone12").fadeIn(); }, 10);
			                setTimeout(function(){ $("#errZone12").fadeOut(); }, 2000);
			                event.originalEvent.options.submit = false;
			                break;
			            }
			            else if (!pattern.test($("#"+fieldsvalues).val())) {
			                errZone12.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
			                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			                $("#"+fieldsvalues).focus();
			                setTimeout(function(){ $("#errZone12").fadeIn(); }, 10);
			                setTimeout(function(){ $("#errZone12").fadeOut(); }, 2000);
			                event.originalEvent.options.submit = false;
			                break;
			             }
			                else if(($("#"+fieldsvalues).val().charAt(0)!="9") && ($("#"+fieldsvalues).val().charAt(0)!="8") && ($("#"+fieldsvalues).val().charAt(0)!="7") && ($("#"+fieldsvalues).val().charAt(0)!="6"))
			             {
			                errZone12.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Should Start with 9,8,7,6.  </div>";
			                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			                $("#"+fieldsvalues).focus();
			                setTimeout(function(){ $("#errZone12").fadeIn(); }, 10);
			                setTimeout(function(){ $("#errZone12").fadeOut(); }, 2000);
			                event.originalEvent.options.submit = false;
			                break;
			               }
			             }
			}
		}
	}
});
$.subscribe('validateBulk', function(event, data) {
	
	//alert("validateBulk");
	var spanClass="";
	
	if($("#status1 option:selected").val()=='Seen')
	{
		spanClass="Seen";
	}
	else if($("#status1 option:selected").val() == 'Snooze-I')
	{
		spanClass="Snooze";
	} 
	else if($("#status1 option:selected").val() == 'Ignore-I')
	{
		spanClass="Ignore";
	}
	else if($("#status1 option:selected").val() == 'Informed')
	{
		spanClass="NotInformed";
	}

	
	//alert("spanClass:  "+spanClass);
	
	var mystring=$(".pIds"+spanClass).text();

	var fieldtype = mystring.split(",");
    var pattern = /^\d{10}$/;
	for(var i=0; i<fieldtype.length; i++)
	{
		
		var fieldsvalues = fieldtype[i].split("#")[0];
		var fieldsnames = fieldtype[i].split("#")[1];
		var colType = fieldtype[i].split("#")[2];   //fieldsType[i]=first_name#t
		var validationType = fieldtype[i].split("#")[3];
	      
		$("#"+fieldsvalues).css("background-color","");
		errZone1.innerHTML="";
		if(fieldsvalues!= "" )
		{
		    if(colType=="D"){
		    	
		    if($("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
		    {
		    	
		      	if(spanClass == "Seen"){
		    		var seenBy = $("input[name=assignId]:checked").val();
		    		if(fieldsvalues == "assign_to_emp_id"){
		    			if(seenBy == "2"){
				    		continue;
				    	}else{
				    		if($("#assign_ref_to").val().trim()==""){
				    			errZone1.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
					 	        setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
					 		    setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
					 	        $("#"+fieldsvalues).focus();
					 	        $("#"+fieldsvalues).css("background-color","#ff701a");
					 	        event.originalEvent.options.submit = false;
					 			break;
				    		}
				    	}
		    			continue;
		    		}
		      	}
		      	
		    	if(spanClass == "NotInformed"){
		    		var informedTo = $("input[name=assignId]:checked").val();
		    		if(fieldsvalues == "assign_to_emp_id"){
		    			if(informedTo == "2"){
				    		continue;
				    	}else{
				    		if($("#assign_ref_to").val().trim()==""){
				    			errZone1.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
					 	        setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
					 		    setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
					 	        $("#"+fieldsvalues).focus();
					 	        $("#"+fieldsvalues).css("background-color","#ff701a");
					 	        event.originalEvent.options.submit = false;
					 			break;
				    		}
				    	}
		    			continue;
		    		}
		      	}
		    		
		    errZone1.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
	        setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
		    setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
	        $("#"+fieldsvalues).focus();
	        $("#"+fieldsvalues).css("background-color","#ff701a");
	        event.originalEvent.options.submit = false;
			break;	
			  }
			}
			else if(colType =="T"){
			if(validationType=="n"){
			var numeric= /^[0-9]+$/;
			if(!(numeric.test($("#"+fieldsvalues).val().trim()))){
			errZone1.innerHTML="<div class='user_form_inputError2'>Please Enter Numeric Values of "+fieldsnames+"</div>";
	        setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
		    setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
	        $("#"+fieldsvalues).focus();
	        $("#"+fieldsvalues).css("background-color","#ff701a");
	        event.originalEvent.options.submit = false;
	        break;
	          }	
		     }
			else if(validationType=="an"){
		     var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
			if(!(allphanumeric.test($("#"+fieldsvalues).val().trim()))){
			errZone1.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
	        setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
		    setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
	        $("#"+fieldsvalues).focus();
	        $("#"+fieldsvalues).css("background-color","#ff701a");
	        event.originalEvent.options.submit = false;
	        break;
	          }
			}
			else if(validationType=="sc"){
				
				if($("#"+fieldsvalues).val().trim()=="")
			    {
					if(spanClass == "Seen"){
			    		var seenBy = $("input[name=assignId]:checked").val();
			    		if(fieldsvalues == "assign_to_id"){
			    			if(seenBy == "1"){
					    		continue;
					    	}
			    		}
			    	}
					
					if(spanClass == "NotInformed"){
			    		var informedTo = $("input[name=assignId]:checked").val();
			    		if(fieldsvalues == "assign_to_id"){
			    			if(informedTo == "1"){
					    		continue;
					    	}
			    		}
			      	}
					
			    errZone1.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value </div>";
		        setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
			    setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
		        $("#"+fieldsvalues).focus();
		        $("#"+fieldsvalues).css("background-color","#ff701a");
		        event.originalEvent.options.submit = false;
				break;	
				  }	
				}
			else if(validationType=="a"){
			if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val().trim()))){
		    errZone1.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
	        setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
		    setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
	        $("#"+fieldsvalues).focus();
	        $("#"+fieldsvalues).css("background-color","#ff701a");
	        event.originalEvent.options.submit = false;
	        break;     
	          }
			 }
			else if(validationType=="m"){
		   if($("#"+fieldsvalues).val().trim().length > 10 || $("#"+fieldsvalues).val().trim().length < 10)
			{
				errZone1.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
				$("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
				$("#"+fieldsvalues).focus();
				setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
		        setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
		        event.originalEvent.options.submit = false;
				break;
			}
		    else if (!pattern.test($("#"+fieldsvalues).val().trim())) {
			    errZone1.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
				$("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
				$("#"+fieldsvalues).focus();
				setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
		        setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
		        event.originalEvent.options.submit = false;
				break;
			 }
				else if(($("#"+fieldsvalues).val().trim().charAt(0)!="9") && ($("#"+fieldsvalues).val().trim().charAt(0)!="8") && ($("#"+fieldsvalues).val().trim().charAt(0)!="7") && ($("#"+fieldsvalues).val().trim().charAt(0)!="6"))
			 {
				errZone1.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
				$("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
				$("#"+fieldsvalues).focus();
				setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
		        setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
		        event.originalEvent.options.submit = false;
				break;
			   }
		     } 
			 else if(validationType =="e"){
		     if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val().trim())){
		     }else{
		    errZone1.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
		    $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			$("#"+fieldsvalues).focus();
			setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
		    setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
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
			if(!(allphanumeric.test($("#"+fieldsvalues).val().trim()))){
			errZone1.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
	        setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
		    setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
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
		    errZone1.innerHTML="<div class='user_form_inputError2'>Please Select Time "+fieldsnames+" Value </div>";
	        setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
		    setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
	        $("#"+fieldsvalues).focus();
	        $("#"+fieldsvalues).css("background-color","#ff701a");
	        event.originalEvent.options.submit = false;
			break;	
			  }	
			}
			else if(colType=="Date"){
			if($("#"+fieldsvalues).val().trim()=="")
		    {
		    errZone1.innerHTML="<div class='user_form_inputError2'>Please Select Date "+fieldsnames+" Value  </div>";
	        setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
		    setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
	        $("#"+fieldsvalues).focus();
	        $("#"+fieldsvalues).css("background-color","#ff701a");
	        event.originalEvent.options.submit = false;
			break;	
			  }
			 }  
		}
	}		
});