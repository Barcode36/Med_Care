function addNewTicketSeries(moduleId)
{
	var module=$("#"+moduleId).val();
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Ticket_Config/beforeTicketConfigAdd.action?ticketflag=y&dataFor="+module,
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}


function backForm(mod)
{
	var module =$("#"+mod).val();
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Ticket_Config/beforeTicketConfigView.action?ticketflag=v&dataFor="+module,
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}


function getTicketType(tickettype,n,d1,d2,d3,d4)
{
	if (tickettype=='N') {
		 document.getElementById(d1).style.display="none";
		 document.getElementById(n).style.display="block";
		 document.getElementById(d2).style.display="none";
		 document.getElementById(d3).style.display="none";
		 document.getElementById(d4).style.display="none";
	}
	else if (tickettype=='D') {
		 document.getElementById(d1).style.display="block";
		 document.getElementById(n).style.display="none";
		 document.getElementById(d2).style.display="block";
		 document.getElementById(d3).style.display="block";
		 document.getElementById(d4).style.display="block";
	}
}


function getPrefix(deptid,module)
{
	var mod=$("#"+module).val();
       $.ajax({
   		type :"post",
   		url :"/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Ticket_Config/getPrefixViaAjax.action?deptid="+deptid+"&dataFor="+mod,
   		success : function(subDeptData){
   		$('#prefix').val(subDeptData);
   	    },
   	    error : function () {
   			alert("Somthing is wrong to get Sub Department");
   		}
   	});
}

function getChangeDiv(value)
{
	if(value=='N')
	{
		$("#normal").show();
		$("#deptWise").hide();
		$("#allDeptWise").hide();
	}
	else if(value=='D')
	{
		$("#normal").hide();
		$("#deptWise").show();
		$("#allDeptWise").show();
	}
	else if(value=='A')
	{
		$("#normal").hide();
		$("#deptWise").hide();
		$("#allDeptWise").show();
	}
	else
	{
		$("#normal").hide();
		$("#deptWise").hide();
		$("#allDeptWise").hide();
	}
}

function setDemoSeries()
{
	var value= $("#ticket_type").val();
	var abbValue = $("#abb").val();
	var dataFor=$("#dataFor").val();
	
	var finalSeries="";
	if(abbValue!='')
	{
		finalSeries=finalSeries+abbValue;
	}
	if(value=='N')
	{
		
		if(dataFor=="COMPL")
		{
			var frequency= $("#frequency").val();
			if(frequency=="Yes")
			{
				finalSeries=finalSeries+"F";
			}
		}
		finalSeries = finalSeries.toUpperCase();
		var n_series=$("#n_series").val();
		finalSeries=finalSeries+n_series;
	}
	else if(value=='D')
	{
		var deptName = $("#deptName option:selected").text();
		var noChar = $("#deptfit").val();
		var deptPrefix = deptName.substring(0,noChar);
		finalSeries = finalSeries+deptPrefix;
		if(dataFor=="COMPL")
		{
			var frequency= $("#frequency").val();
			if(frequency=="Yes")
			{
				finalSeries=finalSeries+"F";
			}
		}
		finalSeries = finalSeries.toUpperCase();
		var d_series=$("#d_series").val();
		finalSeries=finalSeries+d_series;
	}
	else if(value=='A')
	{
		var noChar = $("#deptfit").val();
		if(noChar=="1")
			finalSeries=finalSeries+"X";
		else if(noChar=="2")
			finalSeries=finalSeries+"XX";
		else if(noChar=="3")
			finalSeries=finalSeries+"XXX";
		else if(noChar=="4")
			finalSeries=finalSeries+"XXXX";
		
		if(dataFor=="COMPL")
		{
			var frequency= $("#frequency").val();
			if(frequency=="Yes")
			{
				finalSeries=finalSeries+"F";
			}
		}
		finalSeries = finalSeries.toUpperCase();
		var d_series=$("#d_series").val();
		finalSeries=finalSeries+d_series;
	}
	$("#demoseries").val(finalSeries);
	
}

$.subscribe('validateTicketSeries', function(event,data)
		{
					var mystring=null;
					if($("#ticket_type").val()!='' && $("#ticket_type").val()=='D')
					{
						mystring=$(".ddPids").text();
					}
					else 
					{
						mystring=$(".normalpIds").text();
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
						            var allphanumeric = /^[A-Za-z0-9 ]{1,20}$/;
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
						            if(!(/^[a-zA-Z]+$/.test($("#"+fieldsvalues).val())))
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
				             	}
					            else if(colType=="TextArea")
					            {
						            if(validationType=="an")
						            {
							            var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
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
						            else if(validationType=="mg")
						            {
						            }   
					            }
					            else if(colType=="Time")
					            {
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
					            else if(colType=="Date")
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
				        	}
				        
				    	} 
				});