function viewTemplate (data)
{
	var data= $("#"+data).val();
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/>Loading...</center>");
      $.ajax({
    type : "post",
    url  : "view/Over2Cloud/CommunicationOver2Cloud/template/beforeTemplateViewHeader.action?dataFor="+data,
    success : function(subdeptdata) {
      $("#"+"data_part").html(subdeptdata);
},
   error: function() {
           alert("error");
       }
 });

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
        	
      //  	$("#completionResult").dialog('open');
        	}
		
		
	}		
});
$.subscribe('validateFirst', function(event,data)
		{
	//alert("karnika");
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
	    if($("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
	    {
	    errZone.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
        setTimeout(function(){ $("#errZone.fadeIn(); ")}, 10);
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
		if(!(numeric.test($("#"+fieldsvalues).val().trim().length < 1))){
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
	     var allphanumeric = /^[A-Za-z0-9 -]{3,20}$/;
		if(!(allphanumeric.test($("#"+fieldsvalues).val().trim().length < 1))){
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
		if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val().trim().length < 1))){
	    rerrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
        setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
	    setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
        $("#"+fieldsvalues).focus();
        $("#"+fieldsvalues).css("background-color","#ff701a");
        event.originalEvent.options.submit = false;
        break;     
          }
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
		if(!(allphanumeric.test($("#"+fieldsvalues).val().trim().length < 1))){
		errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
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
}		
if(event.originalEvent.options.submit != false)
{
	$('#completionResult').dialog('open');
} 	
});

$.subscribe('validateadressing', function(event,data)
{
	var mystring = $(".pIdsaddrss").text();
    var fieldtype = mystring.split(",");
    var pattern = /^\d{10}$/;
	for(var i=0; i<fieldtype.length; i++)
	{
		
		var fieldsvalues = fieldtype[i].split("#")[0];
		var fieldsnames = fieldtype[i].split("#")[1];
		var colType = fieldtype[i].split("#")[2];   //fieldsType[i]=first_name#t
		var validationType = fieldtype[i].split("#")[3];
	      
		$("#"+fieldsvalues).css("background-color","");
		addresserrZone.innerHTML="";
		if(fieldsvalues!= "" )
		{
		    if(colType=="D"){
		    if($("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
		    {
		    addresserrZone.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
	        setTimeout(function(){ $("#addresserrZone").fadeIn(); }, 10);
		    setTimeout(function(){ $("#addresserrZone").fadeOut(); }, 2000);
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
			addresserrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Numeric Values of "+fieldsnames+"</div>";
	        setTimeout(function(){ $("#addresserrZone").fadeIn(); }, 10);
		    setTimeout(function(){ $("#addresserrZone").fadeOut(); }, 2000);
	        $("#"+fieldsvalues).focus();
	        $("#"+fieldsvalues).css("background-color","#ff701a");
	        event.originalEvent.options.submit = false;
	        break;
	          }	
		     }
			else if(validationType=="an"){
		     var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
			if(!(allphanumeric.test($("#"+fieldsvalues).val().trim()))){
			addresserrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
	        setTimeout(function(){ $("#addresserrZone").fadeIn(); }, 10);
		    setTimeout(function(){ $("#addresserrZone").fadeOut(); }, 2000);
	        $("#"+fieldsvalues).focus();
	        $("#"+fieldsvalues).css("background-color","#ff701a");
	        event.originalEvent.options.submit = false;
	        break;
	          }
			}
			else if(validationType=="a"){
			if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val().trim()))){
		    addresserrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
	        setTimeout(function(){ $("#addresserrZone").fadeIn(); }, 10);
		    setTimeout(function(){ $("#addresserrZone").fadeOut(); }, 2000);
	        $("#"+fieldsvalues).focus();
	        $("#"+fieldsvalues).css("background-color","#ff701a");
	        event.originalEvent.options.submit = false;
	        break;     
	          }
			 }
			else if(validationType=="m"){
		   if($("#"+fieldsvalues).val().trim().length > 10 || $("#"+fieldsvalues).val().trim().length < 10)
			{
				addresserrZone.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
				$("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
				$("#"+fieldsvalues).focus();
				setTimeout(function(){ $("#addresserrZone").fadeIn(); }, 10);
		        setTimeout(function(){ $("#addresserrZone").fadeOut(); }, 2000);
		        event.originalEvent.options.submit = false;
				break;
			}
		    else if (!pattern.test($("#"+fieldsvalues).val().trim())) {
			    addresserrZone.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
				$("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
				$("#"+fieldsvalues).focus();
				setTimeout(function(){ $("#addresserrZone").fadeIn(); }, 10);
		        setTimeout(function(){ $("#addresserrZone").fadeOut(); }, 2000);
		        event.originalEvent.options.submit = false;
				break;
			 }
				else if(($("#"+fieldsvalues).val().trim().charAt(0)!="9") && ($("#"+fieldsvalues).val().trim().charAt(0)!="8") && ($("#"+fieldsvalues).val().trim().charAt(0)!="7") && ($("#"+fieldsvalues).val().trim().charAt(0)!="6"))
			 {
				addresserrZone.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
				$("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
				$("#"+fieldsvalues).focus();
				setTimeout(function(){ $("#addresserrZone").fadeIn(); }, 10);
		        setTimeout(function(){ $("#addresserrZone").fadeOut(); }, 2000);
		        event.originalEvent.options.submit = false;
				break;
			   }
		     } 
			 else if(validationType =="e"){
		     if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val().trim())){
		     }else{
		    addresserrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
		    $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			$("#"+fieldsvalues).focus();
			setTimeout(function(){ $("#addresserrZone").fadeIn(); }, 10);
		    setTimeout(function(){ $("#addresserrZone").fadeOut(); }, 2000);
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
			addresserrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
	        setTimeout(function(){ $("#addresserrZone").fadeIn(); }, 10);
		    setTimeout(function(){ $("#addresserrZone").fadeOut(); }, 2000);
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
		    addresserrZone.innerHTML="<div class='user_form_inputError2'>Please Select Time "+fieldsnames+" Value </div>";
	        setTimeout(function(){ $("#addresserrZone").fadeIn(); }, 10);
		    setTimeout(function(){ $("#addresserrZone").fadeOut(); }, 2000);
	        $("#"+fieldsvalues).focus();
	        $("#"+fieldsvalues).css("background-color","#ff701a");
	        event.originalEvent.options.submit = false;
			break;	
			  }	
			}
			else if(colType=="Date"){
			if($("#"+fieldsvalues).val().trim()=="")
		    {
		    addresserrZone.innerHTML="<div class='user_form_inputError2'>Please Select Date "+fieldsnames+" Value  </div>";
	        setTimeout(function(){ $("#addresserrZone").fadeIn(); }, 10);
		    setTimeout(function(){ $("#addresserrZone").fadeOut(); }, 2000);
	        $("#"+fieldsvalues).focus();
	        $("#"+fieldsvalues).css("background-color","#ff701a");
	        event.originalEvent.options.submit = false;
			break;	
			  }
			 }  
		}
	}		
});

function changeGroupname(divId,param)
{
	if(param!='-1'){
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CommunicationOver2Cloud/Comm/getViaAjaxcontactsubType.action?select_param="+param,
	    success : function(Data){
			$('#'+divId+' option').remove();
			$('#'+divId).append($("<option></option>").attr("value",-1).text("Select Contact Sub Type"));
	    	$(Data).each(function(index)
			{
			   $('#'+divId).append($("<option></option>").attr("value",this.id).text(this.name));
			});
		    },
	   error: function() {
	        alert("error");
	    }
	 });
	}
}
function changsubcontactType(divId,param){

	if(param!='-1'){
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CommunicationOver2Cloud/Comm/getViaAjaxcontactType.action?select_param="+param,
	    success : function(saveData)
	    {
	    	$("#clienttddddd").html(saveData);
		 },
	   error: function() {
	        alert("error");
	    }
	 });
	}
}