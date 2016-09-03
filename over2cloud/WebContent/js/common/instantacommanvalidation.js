$.subscribe('validate', function(event,data)
{
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
		            else{
		            	  $('#takeActionGrid').dialog('open');
		              }
		            }
			  
		            else if(colType =="T"){
		            	
		            	if($("#ASExcel").val()!==""){
		            	    errZone.innerHTML="<div class='user_form_inputError2'>Please Use Upload Button during Excel Uploading. </div>";
			                $("#ASExcel").css("background-color","#ff701a");  //255;165;79
			                $("#ASExcel").focus();
			                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
			                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
			                event.originalEvent.options.submit = false;
			                break;
		            	}
		            	
		            	
		            	if($("#contactid").val()=="-1" && $("#groupName").val()==null){
		            	 
		            		if(validationType=="m")
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
				               
					            	
					             } else{
					            	   
					            	 
					              }  
					            
				             }
		            	
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
				            else{
				            	 $('#takeActionGrid').dialog('open');
				            }
			             }
			            else if(validationType=="ans")
			             {
				            var allphanumeric =  /^[0-9a-zA-Z\s\r\n@!#\$\^%&*()+=\-\[\]\\\';,\.\/\{\}\|\":<>\?]+$/;
				            if(!(allphanumeric.test($("#"+fieldsvalues).val())))
				             {
					            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric and Special Characters of "+fieldsnames+"</div>";
					            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
					            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
					            $("#"+fieldsvalues).focus();
					            $("#"+fieldsvalues).css("background-color","#ff701a");
					            event.originalEvent.options.submit = false;
					            break;
				              }else{
				            	  $('#takeActionGrid').dialog('open');
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
		}
	}		
});
