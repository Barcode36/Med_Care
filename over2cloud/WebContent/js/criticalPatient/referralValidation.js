function lab(value)
{
	if(value=="Lab")
		{
		$("#lab").show();
		$("#lab1").show();
		$("#radio").hide();
		$("#radio1").hide();
		$("#radiotest").hide();
		
		}
	if(value=="Radiology")
		{
		$("#lab").hide();
		$("#lab1").hide();
		$("#radio").show();
		$("#radio1").show();
		$("#radiotest").show();
		}
	
}



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
			url : "view/Over2Cloud/Critical/fetchDetails.action?uhid=" + value + "&dataFor=" + dataFor + "&patType=" +$("#patient_type").val(),
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
			$("#adm_doc").val("Emergency Team");
			$("#adm_spec").val("Emergency and Trauma Services");
			$("#priority").val("Stat");
			$("#patient_name").val("");
			$("#bed").val("");
			$("#uhid").val("MM");
		}
		else
		{
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
	//alert("hello");
	var mystring="pIds";
	mystring=$(".pIds").text();
	if($("#status1").val()!='' && $("#status1").val()=='Informed')
	{
		mystring=$(".inpIds").text();
	}
	else if($("#status1").val()!='' && $("#status1").val()=='Close')
	{
		mystring=$(".dnpIds").text();
	}
	else if($("#status1").val()!='' && $("#status1").val()=='Seen')
	{
		mystring=$(".spIds").text();
	}
	else if($("#status1").val()!='' && $("#status1").val()=='No Further Action Required' )
	{
		mystring=$(".dnpIds").text();
	}
	else if($("#status1").val()!='' && ($("#status1").val()=='Snooze' || $("#status1").val()=='Snooze-I'))
	{
		mystring=$(".snpIds").text();
	}
	else if($("#status1").val()!='' && ($("#status1").val()=='Ignore' || $("#status1").val()=='Ignore-I'))
	{
		mystring=$(".igpIds").text();
	}
	//alert(mystring);
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
					errZone1.innerHTML = "<div class='user_form_inputError2'>Please Select " + fieldsnames + " Value from Drop Down </div>";
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
				if(validationType=="m"){
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
				} else if (validationType == "a") {
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


$.subscribe('validate5', function(event, data) {
	//console.log(data);
	//alert("hello validate 5");
	var mystring="pIdss";
	mystring=$(".pIdss").text();
	
	var fieldtype = mystring.split(",");
	for (var i = 0; i < fieldtype.length; i++) {

		var fieldsvalues = fieldtype[i].split("#")[0];
		var fieldsnames = fieldtype[i].split("#")[1];
		var colType = fieldtype[i].split("#")[2]; // fieldsType[i]=first_name#t
		var validationType = fieldtype[i].split("#")[3];

		$("#" + fieldsvalues).css("background-color", "");
		errZone5.innerHTML = "";
		if (fieldsvalues != "") {
			if (colType == "D") {

				if ($("#" + fieldsvalues).val() == "" || $("#" + fieldsvalues).val() == "-1") {
					errZone5.innerHTML = "<div class='user_form_inputError2'>Please Select " + fieldsnames + " Value from Drop Down </div>";
					setTimeout(function() {
						$("#errZone5").fadeIn();
					}, 10);
					setTimeout(function() {
						$("#errZone5").fadeOut();
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
						errZone5.innerHTML = "<div class='user_form_inputError2'>Please Enter Numeric Values of " + fieldsnames + "</div>";
						setTimeout(function() {
							$("#errZone5").fadeIn();
						}, 10);
						setTimeout(function() {
							$("#errZone5").fadeOut();
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
						errZone5.innerHTML = "<div class='user_form_inputError2'>Please Enter Correct Time Format [HH:MM] of " + fieldsnames + " Field</div>";
						setTimeout(function() {
							$("#errZone5").fadeIn();
						}, 10);
						setTimeout(function() {
							$("#errZone5").fadeOut();
						}, 2000);
						$("#" + fieldsvalues).focus();
						$("#" + fieldsvalues).css("background-color", "#ff701a");
						event.originalEvent.options.submit = false;
						break;
					}
				}
				if(validationType=="m"){
					var pattern = /^\d{10}$/;
			           if($("#"+fieldsvalues).val().length > 10 || $("#"+fieldsvalues).val().length < 10)
			            {
			                errZone5.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
			                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			                $("#"+fieldsvalues).focus();
			                setTimeout(function(){ $("#errZone5").fadeIn(); }, 10);
			                setTimeout(function(){ $("#errZone5").fadeOut(); }, 2000);
			                event.originalEvent.options.submit = false;
			                break;
			            }
			            else if (!pattern.test($("#"+fieldsvalues).val())) {
			                errZone5.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
			                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			                $("#"+fieldsvalues).focus();
			                setTimeout(function(){ $("#errZon5e").fadeIn(); }, 10);
			                setTimeout(function(){ $("#errZone5").fadeOut(); }, 2000);
			                event.originalEvent.options.submit = false;
			                break;
			             }
			                else if(($("#"+fieldsvalues).val().charAt(0)!="9") && ($("#"+fieldsvalues).val().charAt(0)!="8") && ($("#"+fieldsvalues).val().charAt(0)!="7") && ($("#"+fieldsvalues).val().charAt(0)!="6"))
			             {
			                errZone5.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
			                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			                $("#"+fieldsvalues).focus();
			                setTimeout(function(){ $("#errZone5").fadeIn(); }, 10);
			                setTimeout(function(){ $("#errZone5").fadeOut(); }, 2000);
			                event.originalEvent.options.submit = false;
			                break;
			               }
			             } 

				else if (validationType == "an") {
					var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
					if (!(allphanumeric.test($("#" + fieldsvalues).val()))) {
						errZone5.innerHTML = "<div class='user_form_inputError2'>Please Enter Alpha Numeric of " + fieldsnames + "</div>";
						setTimeout(function() {
							$("#errZone5").fadeIn();
						}, 10);
						setTimeout(function() {
							$("#errZone5").fadeOut();
						}, 2000);
						$("#" + fieldsvalues).focus();
						$("#" + fieldsvalues).css("background-color", "#ff701a");
						event.originalEvent.options.submit = false;
						break;
					}
				} else if (validationType == "a") {
					if (!(/^[a-zA-Z ]+$/.test($("#" + fieldsvalues).val()))) {
						errZone5.innerHTML = "<div class='user_form_inputError2'>Please Enter Alphabate Values of " + fieldsnames + "</div>";
						setTimeout(function() {
							$("#errZone5").fadeIn();
						}, 10);
						setTimeout(function() {
							$("#errZone5").fadeOut();
						}, 2000);
						$("#" + fieldsvalues).focus();
						$("#" + fieldsvalues).css("background-color", "#ff701a");
						event.originalEvent.options.submit = false;
						break;
					}
				}

				else if (validationType == "sc") {
					if ($("#" + fieldsvalues).val().length < 1) {
						errZone5.innerHTML = "<div class='user_form_inputError2'>Enter " + fieldsnames + " !!! </div>";
						$("#" + fieldsvalues).css("background-color", "#ff701a"); // 255;165;79
						$("#" + fieldsvalues).focus();
						setTimeout(function() {
							$("#errZone5").fadeIn();
						}, 10);
						setTimeout(function() {
							$("#errZone5").fadeOut();
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
							$("#errZone5").fadeIn();
						}, 10);
						setTimeout(function() {
							$("#errZone5").fadeOut();
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
							$("#errZone5").fadeIn();
						}, 10);
						setTimeout(function() {
							$("#errZone5").fadeOut();
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

$.subscribe('validate3', function(event, data) {
	//console.log(data);
	//alert("hello validate 3");
	var mystring="pIdsss";
	mystring=$(".pIdsss").text();
//	alert(mystring);
	var fieldtype = mystring.split(",");
	for (var i = 0; i < fieldtype.length; i++) {

		var fieldsvalues = fieldtype[i].split("#")[0];
		var fieldsnames = fieldtype[i].split("#")[1];
		var colType = fieldtype[i].split("#")[2]; // fieldsType[i]=first_name#t
		var validationType = fieldtype[i].split("#")[3];

		$("#" + fieldsvalues).css("background-color", "");
		errZone3.innerHTML = "";
		if (fieldsvalues != "") {
			if (colType == "D") {

				if ($("#" + fieldsvalues).val() == "" || $("#" + fieldsvalues).val() == "-1") {
					errZone3.innerHTML = "<div class='user_form_inputError2'>Please Select " + fieldsnames + " Value from Drop Down </div>";
					setTimeout(function() {
						$("#errZone3").fadeIn();
					}, 10);
					setTimeout(function() {
						$("#errZone3").fadeOut();
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
						errZone3.innerHTML = "<div class='user_form_inputError2'>Please Enter Numeric Values of " + fieldsnames + "</div>";
						setTimeout(function() {
							$("#errZone3").fadeIn();
						}, 10);
						setTimeout(function() {
							$("#errZone3").fadeOut();
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
						errZone3.innerHTML = "<div class='user_form_inputError2'>Please Enter Correct Time Format [HH:MM] of " + fieldsnames + " Field</div>";
						setTimeout(function() {
							$("#errZone3").fadeIn();
						}, 10);
						setTimeout(function() {
							$("#errZone3").fadeOut();
						}, 2000);
						$("#" + fieldsvalues).focus();
						$("#" + fieldsvalues).css("background-color", "#ff701a");
						event.originalEvent.options.submit = false;
						break;
					}
				}
				if(validationType=="m"){
					var pattern = /^\d{10}$/;
			           if($("#"+fieldsvalues).val().length > 10 || $("#"+fieldsvalues).val().length < 10)
			            {
			                errZone3.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
			                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			                $("#"+fieldsvalues).focus();
			                setTimeout(function(){ $("#errZone3").fadeIn(); }, 10);
			                setTimeout(function(){ $("#errZone3").fadeOut(); }, 2000);
			                event.originalEvent.options.submit = false;
			                break;
			            }
			            else if (!pattern.test($("#"+fieldsvalues).val())) {
			                errZone3.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
			                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			                $("#"+fieldsvalues).focus();
			                setTimeout(function(){ $("#errZon5e").fadeIn(); }, 10);
			                setTimeout(function(){ $("#errZone3").fadeOut(); }, 2000);
			                event.originalEvent.options.submit = false;
			                break;
			             }
			                else if(($("#"+fieldsvalues).val().charAt(0)!="9") && ($("#"+fieldsvalues).val().charAt(0)!="8") && ($("#"+fieldsvalues).val().charAt(0)!="7") && ($("#"+fieldsvalues).val().charAt(0)!="6"))
			             {
			                errZone3.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
			                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			                $("#"+fieldsvalues).focus();
			                setTimeout(function(){ $("#errZone3").fadeIn(); }, 10);
			                setTimeout(function(){ $("#errZone3").fadeOut(); }, 2000);
			                event.originalEvent.options.submit = false;
			                break;
			               }
			             } 

				else if (validationType == "an") {
					var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
					if (!(allphanumeric.test($("#" + fieldsvalues).val()))) {
						errZone3.innerHTML = "<div class='user_form_inputError2'>Please Enter Alpha Numeric of " + fieldsnames + "</div>";
						setTimeout(function() {
							$("#errZone3").fadeIn();
						}, 10);
						setTimeout(function() {
							$("#errZone3").fadeOut();
						}, 2000);
						$("#" + fieldsvalues).focus();
						$("#" + fieldsvalues).css("background-color", "#ff701a");
						event.originalEvent.options.submit = false;
						break;
					}
				} else if (validationType == "a") {
					if (!(/^[a-zA-Z ]+$/.test($("#" + fieldsvalues).val()))) {
						errZone3.innerHTML = "<div class='user_form_inputError2'>Please Enter Alphabate Values of " + fieldsnames + "</div>";
						setTimeout(function() {
							$("#errZone3").fadeIn();
						}, 10);
						setTimeout(function() {
							$("#errZone3").fadeOut();
						}, 2000);
						$("#" + fieldsvalues).focus();
						$("#" + fieldsvalues).css("background-color", "#ff701a");
						event.originalEvent.options.submit = false;
						break;
					}
				}

				else if (validationType == "sc") {
					if ($("#" + fieldsvalues).val().length < 1) {
						errZone3.innerHTML = "<div class='user_form_inputError2'>Enter " + fieldsnames + " !!! </div>";
						$("#" + fieldsvalues).css("background-color", "#ff701a"); // 255;165;79
						$("#" + fieldsvalues).focus();
						setTimeout(function() {
							$("#errZone3").fadeIn();
						}, 10);
						setTimeout(function() {
							$("#errZone3").fadeOut();
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
							$("#errZone3").fadeIn();
						}, 10);
						setTimeout(function() {
							$("#errZone3").fadeOut();
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
							$("#errZone3").fadeIn();
						}, 10);
						setTimeout(function() {
							$("#errZone3").fadeOut();
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