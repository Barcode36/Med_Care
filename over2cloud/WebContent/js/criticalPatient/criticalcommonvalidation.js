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
			             else if(validationType =="sc"){
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
		}else{
        	
       //	$("#completionResult").dialog('open');
        	}
		
		
	}	
	if(event.originalEvent.options.submit != false)
	{
		$('#completionResult').dialog('open');
	} 	
});


$.subscribe('validateUploadForm', function(event,data)
		{
			var mystring=$(".pIds").text();
		    var fieldtype = mystring.split(",");
		    var pattern = /^\d{10}$/;
		    for(var i=0; i<fieldtype.length; i++)
		    {
		        var fieldsvalues = fieldtype[i].split("#")[0];
		        var fieldsnames = fieldtype[i].split("#")[1];
		        var colType = fieldtype[i].split("#")[2];  
		        var validationType = fieldtype[i].split("#")[3];
		        $("#"+fieldsvalues).css("background-color","");
		        errZoneformtwo.innerHTML="";
		        if(fieldsvalues!= "" )
		        {
		             if(colType=="EX"){
			             if (/\.(xls[mx]?)$/.test($("#"+fieldsvalues).val())){
			             }else{
			            errZoneformtwo.innerHTML="<div class='user_form_inputError2'>Only Excel File (.xls or .xlsx Formate </div>";
			            $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			            $("#"+fieldsvalues).focus();
			            setTimeout(function(){ $("#errZoneformtwo").fadeIn(); }, 10);
			            setTimeout(function(){ $("#errZoneformtwo").fadeOut(); }, 2000);
			            event.originalEvent.options.submit = false;
			            break;
			               }
			             } 
		        }
		    }  
		    if(event.originalEvent.options.submit != false)
		    {
		    	$("#ExcelDialog").dialog('open');
		    }
		});


