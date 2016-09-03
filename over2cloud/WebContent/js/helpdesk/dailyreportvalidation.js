$.subscribe('validate', function(event,data)
		{
			var mystring=$(".pIds").text();
			//alert(mystring);
		    var fieldtype = mystring.split(",");
		    var pattern = /^\d{10}$/;
		    for(var i=0; i<fieldtype.length; i++)
		    {
		        var fieldsvalues = fieldtype[i].split("#")[0];
		        var fieldsnames = fieldtype[i].split("#")[1];
		        var colType = fieldtype[i].split("#")[2]; 
		       // alert(colType);//fieldsType[i]=first_name#t
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
			            else if(colType=="C")
			            {
			            	if(document.formone.sms.checked==false && document.formone.mail.checked==false)
			            	{
			            		errZone.innerHTML="<div class='user_form_inputError2'>Please Select Check SMS OR MAIL Check Box  </div>";
					            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					            setTimeout(function(){ $("#errZone").fadeOut(); }, 5000);
					            event.originalEvent.options.submit = false;
					            break;
			            	}
			            }
		            
		        	}
		        
		    	} 
		    /*if(event.originalEvent.options.submit != false)
		    {
		    	$("#reportDialouge").dialog('open');
		    }*/
		});