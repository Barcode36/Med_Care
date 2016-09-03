<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="/js/organization.js"/>"></script>
<script src="<s:url value="/js/common/commonvalidation.js"/>"></script>
<script src="<s:url value="/js/employeeOver2Cloud.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<SCRIPT type="text/javascript">
	function cancelemployeeView(){
	var conP = "<%=request.getContextPath()%>";
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/hr/beforeEmployeeView.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	</SCRIPT>
<SCRIPT type="text/javascript">
var status=false;
function validImage()
{
  if(document.formone.empLogo.value!="")
  {
	  temp=document.formone.empLogo.value.split(".");
	  if(temp[1]!="jpg" && temp[1]!="jpeg" && temp[1]!="bmp")
	  {
		  validImageError.innerHTML="<div class='user_form_inputError2'>Select only JPG/ JPEG/ BMP file</div>";
    	  document.formone.empLogo.focus();
    	  status = false;
	  }
	  else
	  {
		  validImageError.innerHTML="";
		  status = true;
	  }
  }
 
}
$.subscribe('level1', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#orglevel1").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1").fadeOut(); }, 4000);
	       });

$.subscribe('level2', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#orglevel2").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel2").fadeOut(); }, 4000);
	       });

$.subscribe('level3', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#orglevel3").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel3").fadeOut(); }, 4000);
	       });

$.subscribe('level5', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#orglevel5").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel5").fadeOut(); }, 4000);
	       });

$.subscribe('level4', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#orglevel4").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel4").fadeOut(); }, 4000);
	       });



</SCRIPT>
<script type="text/javascript">
	function employeeView(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		var conP = "<%=request.getContextPath()%>";
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/hr/employeeViewHeader.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	
	function employee(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		var conP = "<%=request.getContextPath()%>";
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/hr/beforeEmployee.action?empModuleFalgForDeptSubDept=1",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}


	  function Reset()
	   	{
	   		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	   			var conP = "<%=request.getContextPath()%>";
	   	    $.ajax({
	   		    type : "post",
	   		 url : conP+"/view/Over2Cloud/hr/beforeEmployee.action?empModuleFalgForDeptSubDept=1",
	   		    success : function(subdeptdata) {
	   	       $("#"+"data_part").html(subdeptdata);
	   		},
	   		   error: function() {
	   	            alert("error");
	   	        }
	   		 });
	}
	 
	
</script>

		
<script type="text/javascript">
$.subscribe('secondValidate', function(event,data)
		{
var mystring = $(".rspIds").text();
var fieldtype = mystring.split(",");
var pattern = /^\d{10}$/;
for(var i=0; i<fieldtype.length; i++)
{
	
	var fieldsvalues = fieldtype[i].split("#")[0];
	var fieldsnames = fieldtype[i].split("#")[1];
	var colType = fieldtype[i].split("#")[2];   //fieldsType[i]=first_name#t
	var validationType = fieldtype[i].split("#")[3];
      
	$("#"+fieldsvalues).css("background-color","");
	rserrZone.innerHTML="";
	if(fieldsvalues!= "" )
	{
	    if(colType=="D"){
	    if($("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
	    {
	    rserrZone.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
        setTimeout(function(){ $("#errsrserrZone.fadeIn(); ")}, 10);
	    setTimeout(function(){ $("#rserrZone").fadeOut(); }, 2000);
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
		rserrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Numeric Values of "+fieldsnames+"</div>";
        setTimeout(function(){ $("#rserrZone").fadeIn(); }, 10);
	    setTimeout(function(){ $("#rserrZone").fadeOut(); }, 2000);
        $("#"+fieldsvalues).focus();
        $("#"+fieldsvalues).css("background-color","#ff701a");
        event.originalEvent.options.submit = false;
        break;
          }	
	     }
		else if(validationType=="an"){
	     var allphanumeric = /^[A-Za-z0-9 -]{3,20}$/;
		if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
		rserrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
        setTimeout(function(){ $("#rserrZone").fadeIn(); }, 10);
	    setTimeout(function(){ $("#rserrZone").fadeOut(); }, 2000);
        $("#"+fieldsvalues).focus();
        $("#"+fieldsvalues).css("background-color","#ff701a");
        event.originalEvent.options.submit = false;
        break;
          }
		}
		else if(validationType=="a"){
		if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val()))){
	    rserrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
        setTimeout(function(){ $("#rserrZone").fadeIn(); }, 10);
	    setTimeout(function(){ $("#rserrZone").fadeOut(); }, 2000);
        $("#"+fieldsvalues).focus();
        $("#"+fieldsvalues).css("background-color","#ff701a");
        event.originalEvent.options.submit = false;
        break;     
          }
		 }
		else if(validationType=="m"){
	   if($("#"+fieldsvalues).val().length > 10 || $("#"+fieldsvalues).val().length < 10)
		{
			rserrZone.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
			$("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			$("#"+fieldsvalues).focus();
			setTimeout(function(){ $("#rserrZone").fadeIn(); }, 10);
	        setTimeout(function(){ $("#rserrZone").fadeOut(); }, 2000);
	        event.originalEvent.options.submit = false;
			break;
		}
	    else if (!pattern.test($("#"+fieldsvalues).val())) {
		    rserrZone.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
			$("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			$("#"+fieldsvalues).focus();
			setTimeout(function(){ $("#rserrZone").fadeIn(); }, 10);
	        setTimeout(function(){ $("#rserrZone").fadeOut(); }, 2000);
	        event.originalEvent.options.submit = false;
			break;
		 }
			else if(($("#"+fieldsvalues).val().charAt(0)!="9") && ($("#"+fieldsvalues).val().charAt(0)!="8") && ($("#"+fieldsvalues).val().charAt(0)!="7") && ($("#"+fieldsvalues).val().charAt(0)!="6"))
		 {
			rserrZone.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
			$("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			$("#"+fieldsvalues).focus();
			setTimeout(function(){ $("#rserrZone").fadeIn(); }, 10);
	        setTimeout(function(){ $("#rserrZone").fadeOut(); }, 2000);
	        event.originalEvent.options.submit = false;
			break;
		   }
	     } 
		 else if(validationType =="e"){
	     if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val())){
	     }else{
	    rserrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
	    $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		$("#"+fieldsvalues).focus();
		setTimeout(function(){ $("#rserrZone").fadeIn(); }, 10);
	    setTimeout(function(){ $("#rserrZone").fadeOut(); }, 2000);
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
		if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
		rserrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
        setTimeout(function(){ $("#rserrZone").fadeIn(); }, 10);
	    setTimeout(function(){ $("#rserrZone").fadeOut(); }, 2000);
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
	    rserrZone.innerHTML="<div class='user_form_inputError2'>Please Select Time "+fieldsnames+" Value </div>";
        setTimeout(function(){ $("#rserrZone").fadeIn(); }, 10);
	    setTimeout(function(){ $("#rserrZone").fadeOut(); }, 2000);
        $("#"+fieldsvalues).focus();
        $("#"+fieldsvalues).css("background-color","#ff701a");
        event.originalEvent.options.submit = false;
		break;	
		  }	
		}
		else if(colType=="Date"){
		if($("#"+fieldsvalues).val()=="")
	    {
	    rserrZone.innerHTML="<div class='user_form_inputError2'>Please Select Date "+fieldsnames+" Value  </div>";
        setTimeout(function(){ $("#rserrZone").fadeIn(); }, 10);
	    setTimeout(function(){ $("#rserrZone").fadeOut(); }, 2000);
        $("#"+fieldsvalues).focus();
        $("#"+fieldsvalues).css("background-color","#ff701a");
        event.originalEvent.options.submit = false;
		break;	
		  }
		 }  
	}
}		
	
});




$.subscribe('thirdValidate', function(event,data)
		{
var mystring = $(".aspIds").text();
var fieldtype = mystring.split(",");
var pattern = /^\d{10}$/;
for(var i=0; i<fieldtype.length; i++)
{
	
	var fieldsvalues = fieldtype[i].split("#")[0];
	var fieldsnames = fieldtype[i].split("#")[1];
	var colType = fieldtype[i].split("#")[2];   //fieldsType[i]=first_name#t
	var validationType = fieldtype[i].split("#")[3];
      
	$("#"+fieldsvalues).css("background-color","");
	aserrZone.innerHTML="";
	if(fieldsvalues!= "" )
	{
	    if(colType=="D"){
	    if($("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
	    {
	    aserrZone.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
        setTimeout(function(){ $("#errsaserrZone.fadeIn(); ")}, 10);
	    setTimeout(function(){ $("#aserrZone").fadeOut(); }, 2000);
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
		aserrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Numeric Values of "+fieldsnames+"</div>";
        setTimeout(function(){ $("#aserrZone").fadeIn(); }, 10);
	    setTimeout(function(){ $("#aserrZone").fadeOut(); }, 2000);
        $("#"+fieldsvalues).focus();
        $("#"+fieldsvalues).css("background-color","#ff701a");
        event.originalEvent.options.submit = false;
        break;
          }	
	     }
		else if(validationType=="an"){
	     var allphanumeric = /^[A-Za-z0-9 -]{3,20}$/;
		if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
		aserrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
        setTimeout(function(){ $("#aserrZone").fadeIn(); }, 10);
	    setTimeout(function(){ $("#aserrZone").fadeOut(); }, 2000);
        $("#"+fieldsvalues).focus();
        $("#"+fieldsvalues).css("background-color","#ff701a");
        event.originalEvent.options.submit = false;
        break;
          }
		}
		else if(validationType=="a"){
		if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val()))){
	    aserrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
        setTimeout(function(){ $("#aserrZone").fadeIn(); }, 10);
	    setTimeout(function(){ $("#aserrZone").fadeOut(); }, 2000);
        $("#"+fieldsvalues).focus();
        $("#"+fieldsvalues).css("background-color","#ff701a");
        event.originalEvent.options.submit = false;
        break;     
          }
		 }
		else if(validationType=="m"){
	   if($("#"+fieldsvalues).val().length > 10 || $("#"+fieldsvalues).val().length < 10)
		{
			aserrZone.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
			$("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			$("#"+fieldsvalues).focus();
			setTimeout(function(){ $("#aserrZone").fadeIn(); }, 10);
	        setTimeout(function(){ $("#aserrZone").fadeOut(); }, 2000);
	        event.originalEvent.options.submit = false;
			break;
		}
	    else if (!pattern.test($("#"+fieldsvalues).val())) {
		    aserrZone.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
			$("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			$("#"+fieldsvalues).focus();
			setTimeout(function(){ $("#aserrZone").fadeIn(); }, 10);
	        setTimeout(function(){ $("#aserrZone").fadeOut(); }, 2000);
	        event.originalEvent.options.submit = false;
			break;
		 }
			else if(($("#"+fieldsvalues).val().charAt(0)!="9") && ($("#"+fieldsvalues).val().charAt(0)!="8") && ($("#"+fieldsvalues).val().charAt(0)!="7") && ($("#"+fieldsvalues).val().charAt(0)!="6"))
		 {
			aserrZone.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
			$("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			$("#"+fieldsvalues).focus();
			setTimeout(function(){ $("#aserrZone").fadeIn(); }, 10);
	        setTimeout(function(){ $("#aserrZone").fadeOut(); }, 2000);
	        event.originalEvent.options.submit = false;
			break;
		   }
	     } 
		 else if(validationType =="e"){
	     if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val())){
	     }else{
	    aserrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
	    $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		$("#"+fieldsvalues).focus();
		setTimeout(function(){ $("#aserrZone").fadeIn(); }, 10);
	    setTimeout(function(){ $("#aserrZone").fadeOut(); }, 2000);
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
		if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
		aserrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
        setTimeout(function(){ $("#aserrZone").fadeIn(); }, 10);
	    setTimeout(function(){ $("#aserrZone").fadeOut(); }, 2000);
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
	    aserrZone.innerHTML="<div class='user_form_inputError2'>Please Select Time "+fieldsnames+" Value </div>";
        setTimeout(function(){ $("#aserrZone").fadeIn(); }, 10);
	    setTimeout(function(){ $("#aserrZone").fadeOut(); }, 2000);
        $("#"+fieldsvalues).focus();
        $("#"+fieldsvalues).css("background-color","#ff701a");
        event.originalEvent.options.submit = false;
		break;	
		  }	
		}
		else if(colType=="Date"){
		if($("#"+fieldsvalues).val()=="")
	    {
	    aserrZone.innerHTML="<div class='user_form_inputError2'>Please Select Date "+fieldsnames+" Value  </div>";
        setTimeout(function(){ $("#aserrZone").fadeIn(); }, 10);
	    setTimeout(function(){ $("#aserrZone").fadeOut(); }, 2000);
        $("#"+fieldsvalues).focus();
        $("#"+fieldsvalues).css("background-color","#ff701a");
        event.originalEvent.options.submit = false;
		break;	
		  }
		 }  
	}
}		
	
});


$.subscribe('fourthValidate', function(event,data)
		{
var mystring = $(".dspIds").text();
var fieldtype = mystring.split(",");
var pattern = /^\d{10}$/;
for(var i=0; i<fieldtype.length; i++)
{
	
	var fieldsvalues = fieldtype[i].split("#")[0];
	var fieldsnames = fieldtype[i].split("#")[1];
	var colType = fieldtype[i].split("#")[2];   //fieldsType[i]=first_name#t
	var validationType = fieldtype[i].split("#")[3];
      
	$("#"+fieldsvalues).css("background-color","");
	dserrZone.innerHTML="";
	if(fieldsvalues!= "" )
	{
	    if(colType=="D"){
	    if($("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
	    {
	    dserrZone.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
        setTimeout(function(){ $("#errsdserrZone.fadeIn(); ")}, 10);
	    setTimeout(function(){ $("#dserrZone").fadeOut(); }, 2000);
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
		dserrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Numeric Values of "+fieldsnames+"</div>";
        setTimeout(function(){ $("#dserrZone").fadeIn(); }, 10);
	    setTimeout(function(){ $("#dserrZone").fadeOut(); }, 2000);
        $("#"+fieldsvalues).focus();
        $("#"+fieldsvalues).css("background-color","#ff701a");
        event.originalEvent.options.submit = false;
        break;
          }	
	     }
		else if(validationType=="an"){
	     var allphanumeric = /^[A-Za-z0-9 -]{3,20}$/;
		if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
		dserrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
        setTimeout(function(){ $("#dserrZone").fadeIn(); }, 10);
	    setTimeout(function(){ $("#dserrZone").fadeOut(); }, 2000);
        $("#"+fieldsvalues).focus();
        $("#"+fieldsvalues).css("background-color","#ff701a");
        event.originalEvent.options.submit = false;
        break;
          }
		}
		else if(validationType=="a"){
		if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val()))){
	    dserrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
        setTimeout(function(){ $("#dserrZone").fadeIn(); }, 10);
	    setTimeout(function(){ $("#dserrZone").fadeOut(); }, 2000);
        $("#"+fieldsvalues).focus();
        $("#"+fieldsvalues).css("background-color","#ff701a");
        event.originalEvent.options.submit = false;
        break;     
          }
		 }
		else if(validationType=="m"){
	   if($("#"+fieldsvalues).val().length > 10 || $("#"+fieldsvalues).val().length < 10)
		{
			dserrZone.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
			$("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			$("#"+fieldsvalues).focus();
			setTimeout(function(){ $("#dserrZone").fadeIn(); }, 10);
	        setTimeout(function(){ $("#dserrZone").fadeOut(); }, 2000);
	        event.originalEvent.options.submit = false;
			break;
		}
	    else if (!pattern.test($("#"+fieldsvalues).val())) {
		    dserrZone.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
			$("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			$("#"+fieldsvalues).focus();
			setTimeout(function(){ $("#dserrZone").fadeIn(); }, 10);
	        setTimeout(function(){ $("#dserrZone").fadeOut(); }, 2000);
	        event.originalEvent.options.submit = false;
			break;
		 }
			else if(($("#"+fieldsvalues).val().charAt(0)!="9") && ($("#"+fieldsvalues).val().charAt(0)!="8") && ($("#"+fieldsvalues).val().charAt(0)!="7") && ($("#"+fieldsvalues).val().charAt(0)!="6"))
		 {
			dserrZone.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
			$("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
			$("#"+fieldsvalues).focus();
			setTimeout(function(){ $("#dserrZone").fadeIn(); }, 10);
	        setTimeout(function(){ $("#dserrZone").fadeOut(); }, 2000);
	        event.originalEvent.options.submit = false;
			break;
		   }
	     } 
		 else if(validationType =="e"){
	     if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val())){
	     }else{
	    dserrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
	    $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		$("#"+fieldsvalues).focus();
		setTimeout(function(){ $("#dserrZone").fadeIn(); }, 10);
	    setTimeout(function(){ $("#dserrZone").fadeOut(); }, 2000);
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
		if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
		dserrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
        setTimeout(function(){ $("#dserrZone").fadeIn(); }, 10);
	    setTimeout(function(){ $("#dserrZone").fadeOut(); }, 2000);
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
	    dserrZone.innerHTML="<div class='user_form_inputError2'>Please Select Time "+fieldsnames+" Value </div>";
        setTimeout(function(){ $("#dserrZone").fadeIn(); }, 10);
	    setTimeout(function(){ $("#dserrZone").fadeOut(); }, 2000);
        $("#"+fieldsvalues).focus();
        $("#"+fieldsvalues).css("background-color","#ff701a");
        event.originalEvent.options.submit = false;
		break;	
		  }	
		}
		else if(colType=="Date"){
		if($("#"+fieldsvalues).val()=="")
	    {
	    dserrZone.innerHTML="<div class='user_form_inputError2'>Please Select Date "+fieldsnames+" Value  </div>";
        setTimeout(function(){ $("#dserrZone").fadeIn(); }, 10);
	    setTimeout(function(){ $("#dserrZone").fadeOut(); }, 2000);
        $("#"+fieldsvalues).focus();
        $("#"+fieldsvalues).css("background-color","#ff701a");
        event.originalEvent.options.submit = false;
		break;	
		  }
		 }  
	}
}		
	
});


function reset(formId) {
	  $("#"+formId).trigger("reset"); 
	}
function getDepartmentForMappedOffice(divId,headOfficeId)
{
	$.ajax({
		type :"post",
		url :"view/Over2Cloud/hr/getDepartOfMappedLocation.action?regLevel="+headOfficeId,
		success : function(empData){
		$('#'+divId+' option').remove();
		$('#'+divId).append($("<option></option>").attr("value",-1).text("Select Group"));
    	$(empData).each(function(index)
		{
		   $('#'+divId).append($("<option></option>").attr("value",this.id).text(this.name));
		   $('#groupId').val(this.groupId);
		});
	    },
	    error : function () {
			alert("Somthing is wrong to get Data");
		}
	});
}

</script>
<script type="text/javascript">

function totalExp()
{
	
if(document.getElementById("textfields").value == "0")
{
document.getElementById("workExpDiv").style.display="none";
}
else
{
    document.getElementById('workExpDiv').style.display = 'block';
}
}
</script>
</head>
<body>
<div class="clear"></div>

<div class="middle-content">

	<div class="list-icon">
	<div class="head">Employee</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div>
	</div>
	<div class="clear"></div>
	<div class="border">
		<div class="container_block"><div style=" float:left; padding:20px 1%; width:98%;">
		<sj:accordion id="accordion" autoHeight="false">
		<s:if test="empForAddBasic>0">
		<sj:accordionItem title="Basic Record" id="oneId"> 
			<s:form id="formone" name="formone" namespace="/view/Over2Cloud/hr" action="createEmpBasic" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
		      		<center>
				<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
				<div id="errZone" style="float:center; margin-left: 7px"></div>
				</div></center>
				 
				 <s:iterator value="contactDD" status="counter">
				  <s:if test="%{mandatory}">
                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>
		         
				  <s:if test="%{key=='regLevel'}">
				  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span> </s:if>
				  	<s:select 
		                              id="regLevel"
		                              name="regLevel" 
		                              list="officeMap"
		                              headerKey="-1"
		                              headerValue="Select Mapped Office" 
		                              cssClass="textField"
		                              onchange="getDepartmentForMappedOffice('deptname',this.value);"
		                              >
		                  </s:select>
		                   </div>
				     	</div>
				  </s:if>
				  <s:elseif test="%{key=='deptname'}">
				    <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span> </s:if>
				  	<s:select 
		                              id="deptname"
		                              name="subGroupId" 
		                              list="{'No Data'}"
		                              headerKey="-1"
		                              headerValue="Select Contact Sub-Type" 
		                              cssClass="textField"
		                              onchange="getContactFor(this.value);"
		                              >
		                  </s:select>
		                  </div>
		                  </div>
				  </s:elseif>
			   
				 </s:iterator>
                 <s:hidden id="groupId" name="groupId"/>
                 <s:hidden id="subGroupId" name="subGroupId"/>
                 <s:iterator value="contactTextBox" status="counter">
				  <s:if test="%{mandatory}">
                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>
				  <s:if test="#counter.count%2 !=0">
		         <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}">
				  	<span class="needed">
				  	</span>
				  </s:if>
				  <s:if test="%{key=='mobOne'}">
				  	<s:textfield name="%{key}" id="%{key}" maxlength="200" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" onchange="mobExists(this.value);"/>
				  	<img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/>
				  	<div id="empStatus" style="color: red;font-size: small;"></div>
				  </s:if>
				  <s:elseif test="%{key=='mobileNo'}">
				  	<s:textfield name="%{key}" id="%{key}" maxlength="200" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" />
				  </s:elseif>
				  <s:else>
				  	<s:textfield name="%{key}" id="%{key}" maxlength="200" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" />
				  </s:else>				  
					</div>
			      </div>
			      </s:if>
			      <s:else>
		          <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}">
				  	<span class="needed">
				  	</span>
				  </s:if>
				  	<s:if test="%{key=='mobOne'}">
				  	<s:textfield name="%{key}" id="%{key}" maxlength="200" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" onchange="mobExists(this.value);"/>
				  	<img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/>
				  	<s:hidden id="status" name="status"></s:hidden>
				  	<div id="empStatus" style="color: red;font-size: small;"></div>
				  </s:if>
				  <s:elseif test="%{key=='mobileNo'}">
				  	<s:textfield name="%{key}" id="%{key}" maxlength="200" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" />
				  </s:elseif>
				  <s:else>
				  	<s:textfield name="%{key}" id="%{key}" maxlength="200" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" />
				  </s:else>					  
				  </div>
			      </div>
				 </s:else>
				 </s:iterator>
				 <s:iterator value="contactDateTimeBox" status="status">
				  <s:if test="%{mandatory}">
                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>
				  <s:if test="#status.even">
		         <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}">
				  	<span class="needed">
				  	</span>
				  </s:if>
				  	<sj:datepicker id="%{key}"  name="%{key}"  readonly="true" cssClass="textField" size="20"   changeMonth="true" changeYear="true"  showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Date"/>
					</div>
			      </div>
			      </s:if>
			      <s:elseif test="#status.odd">
		          <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}">
				  	<span class="needed">
				  	</span>
				  </s:if>
				  	<sj:datepicker id="%{key}"  name="%{key}"  readonly="true" cssClass="textField" size="20"   changeMonth="true" changeYear="true"  showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Date"/>
				  </div>
			      </div>
				 </s:elseif>
				 </s:iterator>
				 <s:iterator value="contactFileBox" status="counter1">
				  <s:if test="%{mandatory}">
                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>
				  <s:if test="#counter1.count%2 !=0">
		         <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}">
				  	<span class="needed">
				  	</span>
				  </s:if>
				  	<s:file name="%{key}" id="%{key}" placeholder="Enter Data"  cssClass="textField"/>
					</div>
			      </div>
			      </s:if>
			      <s:else>
		          <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}">
				  	<span class="needed">
				  	</span>
				  </s:if>
				  	<s:file name="%{key}" id="%{key}" placeholder="Enter Data"  cssClass="textField"/>
				  </div>
			      </div>
				 </s:else>
				 </s:iterator>
				 <s:iterator value="contactFormDDBox" status="counter1">
				  <s:if test="%{mandatory}">
                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>
				  <s:if test="#counter1.count%2 !=0">
		         <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}">
				  	<span class="needed">
				  	</span>
				  </s:if>
				  	<s:if test="%{key=='industry'}">
				  	<s:select 
		                              id="industry"
		                              name="industry" 
		                              list="industryMap"
		                              headerKey="-1"
		                              headerValue="Select Sub-Industry"  
		                              cssClass="textField"
		                              onchange="getsubIndustry('subIndustry',this.value);"
		                              >
		                  </s:select>
				  </s:if>
				  <s:elseif test="%{key=='subIndustry'}">
				  	<s:select 
		                              id="subIndustry"
		                              name="subIndustry" 
		                              list="{'No Data'}"
		                              headerKey="-1"
		                              headerValue="Select Sub-Industry" 
		                              cssClass="textField"
		                              >
		                  </s:select>
				  </s:elseif>
				  <s:elseif test="%{key=='clientDept'}">
				  	<s:select 
		                              id="clientDept"
		                              name="clientDept" 
		                              list="deptMap"
		                              headerKey="-1"
		                              headerValue="Select Department" 
		                              cssClass="textField"
		                              >
		                  </s:select>
				  </s:elseif>
					</div>
			      </div>
			      </s:if>
			      <s:else>
		          <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}">
				  	<span class="needed">
				  	</span>
				  </s:if>
				  	<s:if test="%{key=='industry'}">
				  	<s:select 
		                              id="industry"
		                              name="industry" 
		                              list="industryMap"
		                              headerKey="-1"
		                              headerValue="Select Sub-Industry"  
		                              cssClass="textField"
		                              onchange="getsubIndustry('subIndustry',this.value);"
		                              >
		                  </s:select>
				  </s:if>
				  <s:elseif test="%{key=='subIndustry'}">
				  	<s:select 
		                              id="subIndustry"
		                              name="subIndustry" 
		                              list="{'No Data'}"
		                              headerKey="-1"
		                              headerValue="Select Sub-Industry" 
		                              cssClass="textField"
		                              >
		                  </s:select>
				  </s:elseif>
				  <s:elseif test="%{key=='clientDept'}">
				  	<s:select 
		                              id="clientDept"
		                              name="clientDept" 
		                              list="deptMap"
		                              headerKey="-1"
		                              headerValue="Select Department" 
		                              cssClass="textField"
		                              >
		                  </s:select>
				  </s:elseif>
				  </div>
			      </div>
				 </s:else>
				 </s:iterator>
		    
				<!-- Buttons -->
				
				<div class="clear"></div>
				<div class="fields">
			<div class="fields" align="center">
				<sj:submit 
		                        targets="assoresult1" 
		                        clearForm="true"
		                        value="  Save  " 
		                        effect="highlight"
		                        effectOptions="{ color : '#222222'}"
		                        effectDuration="5000"
		                        button="true"
		                        onCompleteTopics="level1"
		                        cssClass="submit"
		                        indicator="indicator2"
		                        style="margin-left: -108px;"
		                        onBeforeTopics="validate"
				/>
				<div id=reset style="margin-top: -25px; margin-left: 95px;">
				<sj:a 
							 button="true" href="#"
							 onclick="reset('formone'); resetColor('.pIds')"
							 cssClass="submit"
						>
						
						Reset
					</sj:a>
				
					<sj:a 
							button="true" href="#"
							onclick="employeeView()"
							cssClass="submit"
						>
						
						Back
					</sj:a>
					</div>
				
				<sj:div id="orglevel1"  effect="fold">
                    <div id="assoresult1"></div>
            </sj:div>
              
	</div>
	</div>
</s:form>
	</sj:accordionItem>
</s:if>
<s:if test="empForAddPrsl>0 && empNameForOther!=null">
<sj:accordionItem title=" Personal & Medical Record" id="twoId"  onclick="getAllEmpList('empName1','mobNoPerTemp');">  
<s:form id="formTwo" name="formTwo" theme="css_xhtml" namespace="/view/Over2Cloud/hr" action="createEmpPersonal"  method="post" enctype="multipart/form-data">
			
			<center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="rserrZone" style="float:center; margin-left: 7px"></div></div></center>
			 <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{empNameForOther}"/>:</div>
								 <span id="gggggg" class="rspIds" style="display: none; "><s:property value="%{empNameForOther}"/>#D#,</span>
								<div class="rightColumn1">
								<s:if test="%{mandatory}">
				  	<span class="needed">
				  	</span>
				  </s:if>
				   
				   <s:select 
                             id="empName1"
                             name="empName" 
                             list="{'No Data'}" 
                             headerKey="-1"
                             headerValue="Select Employee Name" 
				             cssStyle="width:82%"
				             cssClass="select"
				              >
				             
                              
                  </s:select>
                  
			</div>
			</div>
			<%--  <div class="newColumn">
				<div class="leftColumn1">Mobile No:</div>
				<div class="rightColumn1">
				<s:textfield name="mobNoPerTemp" id="mobNoPerTemp" cssClass="textField" maxlength="10" placeholder="Employee Mobile No" readonly="true"/>
			</div>
			</div> --%>
			
				 <s:iterator value="empPerLevels" >
				  <s:if test="%{mandatory}">
				  <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				 </s:if>
				 <s:if test="%{key == 'sname'}">
				  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  	<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
				  </div>
			      </div>
				  </s:if>
				 </s:iterator>
				 
				 <s:iterator value="empPerLevels" >
				  <s:if test="%{mandatory}">
				  <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				 </s:if>
				 <s:if test="%{key == 'fname'}">
				  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  	<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
				  </div>
			      </div>
				  </s:if>
				 <s:if test="%{key == 'mname'}">
				   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  	<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
				  </div>
			      </div>
				 </s:if>
				 </s:iterator>
				 
				  <s:iterator value="empPerLevels" >
				  <s:if test="%{mandatory}">
				  <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				 </s:if>
				 <s:if test="%{key == 'coneName'}">
				  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  	<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
				  </div>
			      </div>
				  </s:if>
				 <s:if test="%{key == 'conedob_date'}">
				   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  	<sj:datepicker name="%{key}" id="%{key}" readonly="true" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px" displayFormat="dd-mm-yy" />
				  </div>
			      </div>
				 </s:if>
				 </s:iterator>
				 
				 
				  <s:iterator value="empPerLevels" >
				  <s:if test="%{mandatory}">
				  <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				 </s:if>
				 <s:if test="%{key == 'ctwoName'}">
				  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  	
				  	<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
				  </div>
			      </div>
				  </s:if>
				 <s:if test="%{key == 'ctwodob_date'}">
				   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  	<sj:datepicker name="%{key}" id="%{key}" readonly="true" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px" displayFormat="dd-mm-yy" />
				  </div>
			      </div>
				 </s:if>
				 </s:iterator>
				 
				  <s:iterator value="empPerLevels" >
				  <s:if test="%{mandatory}">
				  <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				 </s:if>
				 <s:if test="%{key == 'address'}">
				  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  	<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
				  </div>
			      </div>
				  </s:if>
				 <s:if test="%{key == 'landmark'}">
				   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  	<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
				  </div>
			      </div>
				 </s:if>
				 </s:iterator>
				 
				 
				 <s:iterator value="empPerLevels" >
				  <s:if test="%{mandatory}">
				  <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				 </s:if>
				 <s:if test="%{key == 'city'}">
				  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  	<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
				  </div>
			      </div>
				  </s:if>
				 <s:if test="%{key == 'pincode'}">
				   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  	<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
				  </div>
			      </div>
				 </s:if>
				 </s:iterator>
				 
				 
				 <s:iterator value="empPerLevels" >
				  <s:if test="%{mandatory}">
				  <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				 </s:if>
				 <s:if test="%{key == 'refName'}">
				  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  	<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
				  </div>
			      </div>
				  </s:if>
				 <s:if test="%{key == 'refMob'}">
				   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  	<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
				  </div>
			      </div>
				 </s:if>
				 </s:iterator>
				 
				 <s:iterator value="empPerLevels" >
				  <s:if test="%{mandatory}">
				  <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				 </s:if>
				 <s:if test="%{key == 'ref2Name'}">
				  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  	<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
				  </div>
			      </div>
				  </s:if>
				 <s:if test="%{key == 'ref2mob'}">
				   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  	<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
				  </div>
			      </div>
				 </s:if>
				 </s:iterator>
				 
				 
				  <s:iterator value="empPerLevels" >
				  <s:if test="%{mandatory}">
				  <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				 </s:if>
				 <s:if test="%{key == 'contactNo'}">
				  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  	<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
				  </div>
			      </div>
				  </s:if>
				 <s:if test="%{key == 'emernO'}">
				   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  	<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
				  </div>
			      </div>
				 </s:if>
				 </s:iterator>
				 
				 
				 <s:iterator value="empPerLevels" >
				  <s:if test="%{mandatory}">
				  <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				 </s:if>
				 <s:if test="%{key == 'bloodgrp'}">
				  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  	<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
				  </div>
			      </div>
				  </s:if>
				 <s:if test="%{key == 'alergicto'}">
				   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  	<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
				  </div>
			      </div>
				 </s:if>
				 </s:iterator>
				 
				 
				 <s:iterator value="empPerLevels" >
				  <s:if test="%{mandatory}">
				  <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				 </s:if>
				 <s:if test="%{key == 'othermedical'}">
				  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  	<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
				  </div>
			      </div>
				  </s:if>
				
				 </s:iterator>
				 
				  <s:iterator value="empPerFileBox" >
				 <s:if test="%{mandatory}">
                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>
				 
		         <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								
				  	<s:file name="%{key}" id="%{key}" placeholder="Enter Data"  cssClass="textField"/>
					</div>
			      </div>
			     
				  </s:iterator>
				 
			
				 <s:iterator value="empPerCalendr" status="counter">
				  <s:if test="%{mandatory}">
				  <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				 </s:if>
				 <s:if test="#counter.count%2 != 0">
				  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  	<sj:datepicker id="%{key}"  name="%{key}"  readonly="true" cssClass="textField" size="20"   changeMonth="true" changeYear="true"  showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Date"/>
				  </div>
			      </div>
				  </s:if>
				  <s:else>
				  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  	<sj:datepicker id="%{key}"  name="%{key}"  readonly="true" cssClass="textField" size="20"   changeMonth="true" changeYear="true"  showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Date"/>
				  </div>
			      </div>
				</s:else>
				 </s:iterator>
				 <div class=clear></div>
	          <center><img id="indicator3" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
		<div style="width: 100%; text-align: center; padding-bottom: 10px;">
	                <sj:submit 
	                        targets="orglevel2Div" 
	                        clearForm="true"
	                        value="  Save  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        cssClass="submit"
	                        indicator="indicator3"
	                        style="margin-left: -108px;"
	                        onCompleteTopics="level2"
	                        onBeforeTopics="secondValidate"
	                        />
	                        
	                        	<div id=reset style="margin-top: -25px; margin-left: 95px;">
				<sj:a 
							 button="true" href="#"
							 onclick="reset('formone');"
							 cssClass="submit"
						>
						
						Reset
					</sj:a>
				
					<sj:a 
							button="true" href="#"
							onclick="employeeView()"
							cssClass="submit"
						>
						
						Back
					</sj:a>
					</div>
	                       </div>
				<sj:div id="orglevel2"  effect="fold">
                    <div id="orglevel2Div"></div>
            </sj:div>
	                        
	                
</s:form>	         
</sj:accordionItem>
</s:if>
<s:if test="empForAddWorkQua>0  && empNameForOther!=null">
<sj:accordionItem title="Educational Record " onclick="getAllEmpList('empName11','mob2');">                      

        <!-- Start Point For Employee Work Experience-->
                          
		 <s:form  id="empProfsnl" name="empProfsnl"  theme="css_xhtml" namespace="/view/Over2Cloud/hr" action="createEmpPrfsnl"  method="post" >
		  <center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="aserrZone" style="float:center; margin-left: 7px"></div></div></center>
		<div  style="position:relative;">
	       <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{empNameForOther}"/>:</div>
								 <span id="hhhhh" class="aspIds" style="display: none; ">empName11#<s:property value="%{empNameForOther}"/>#D#,</span>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
								   <s:select 
				                              cssClass="select"
				                              id="empName11"
				                              name="empName" 
				                              list="{'No Data'}" 
				                              headerKey="-1"
				                              headerValue="Select Employee" 
				                              cssClass="form_menu_inputtext"
									          cssStyle="width:82%"
				                              >
				                  </s:select>
				  				</div>
		  </div>
           <%--  <div class="newColumn">
								<div class="leftColumn1">Mobile No:</div>
								<div class="rightColumn1">
				  <s:textfield name="mob2" id="mob2"  cssClass="textField" readonly="true"/>
				  </div>
		 </div>    --%>    
		 
		  <div class="newColumn">
								<div class="leftColumn1">Institution:</div>
								 <span id="hhhhh" class="aspIds" style="display: none; ">acadmic#Education#T#a,</span>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  <s:textfield name="acadmic" id="acadmic"  cssClass="textField" placeholder="Enter Education"/>
				  </div>
		 </div>
		 
		  <div class="newColumn">
								<div class="leftColumn1">Aggregate:</div>
								 <span id="hhhhh" class="aspIds" style="display: none; ">acadmic#Education#T#a,</span>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				 <s:textfield name="aggregate" id="aggregate" cssClass="textField" maxlength="80" placeholder="Enter Aggregate Percentage"/></div>
		  </div>
		  
		  <div class="newColumn">
								<div class="leftColumn1">Subject:</div>
								<div class="rightColumn1">
				<s:textfield name="subject" id="subject" cssClass="textField" maxlength="100" placeholder="Enter Subject"/>
				  </div>
		 </div>
		 
		 <div class="newColumn">
								<div class="leftColumn1">Year:</div>
								<div class="rightColumn1">
				<s:textfield name="year" id="year" cssClass="textField" maxlength="100" placeholder="Enter Year Of Passing"/>
				  </div>
		 </div>
            
         
           <div class="newColumn">
								<div class="leftColumn1">College:</div>
								<div class="rightColumn1">
				<s:textfield name="college" id="college" cssClass="textField" maxlength="100" placeholder="Enter College Name"/>
				  </div>
		 </div>
		   <div class="newColumn">
								<div class="leftColumn1">University:</div>
								<div class="rightColumn1">
				<s:textfield name="university" id="university" cssClass="textField" maxlength="80" placeholder="Enter University Name"/>
				  </div>
		 </div>
		  <div class="newColumn">
								<div class="leftColumn1">Supporting Doc:</div>
								<div class="rightColumn1">
								
				  	<s:file name="empDocument" id="empDocument" placeholder="Enter Data"  cssClass="textField"/>
					</div>
			      </div>
		  <div class="newColumn">
		 <div class="rightColumn1">
	      <div class="user_form_button2" id="add_button"><sj:submit value="+" onclick="adddiv('200')" button="true" title="Add More Education"/></div>
	      <div class="user_form_button3" style="position:absolute; right:50px; bottom:2px; top:92px;"><sj:submit value="-" onclick="deletediv('%{#deptIndx}')" button="true" title="Remove"/></div>
	     </div>
	   </div>  
           
          <s:iterator begin="200" end="204" var="deptIndx">
	          <div id="<s:property value="%{#deptIndx}"/>" style="display: none; position:relative;">
	            <div class="clear"></div>
		          <div class="newColumn">
								<div class="leftColumn1">Institution:</div>
								 <span id="hhhhh" class="aspIds" style="display: none; ">acadmic#Education#T#a,</span>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  <s:textfield name="acadmic" id="acadmic"  cssClass="textField" placeholder="Enter Education"/>
				  </div>
		 </div>
		 
		  <div class="newColumn">
								<div class="leftColumn1">Aggregate:</div>
								 <span id="hhhhh" class="aspIds" style="display: none; ">acadmic#Education#T#a,</span>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				 <s:textfield name="aggregate" id="aggregate" cssClass="textField" maxlength="80" placeholder="Enter Aggregate Percentage"/></div>
				  </div>
		
		  <div class="newColumn">
								<div class="leftColumn1">Subject:</div>
								<div class="rightColumn1">
				<s:textfield name="subject" id="subject" cssClass="textField" maxlength="100" placeholder="Enter Subject"/>
				  </div>
		 </div>
		   <div class="newColumn">
								<div class="leftColumn1">Year:</div>
								<div class="rightColumn1">
				<s:textfield name="year" id="year" cssClass="textField" maxlength="100" placeholder="Enter Year Of Passing"/>
				  </div>
		 </div>
       <div class="newColumn">
								<div class="leftColumn1">College:</div>
								<div class="rightColumn1">
				<s:textfield name="college" id="college" cssClass="textField" maxlength="100" placeholder="Enter College Name"/>
				  </div>
		 </div>
		   <div class="newColumn">
								<div class="leftColumn1">University:</div>
								<div class="rightColumn1">
				<s:textfield name="university" id="university" cssClass="textField" maxlength="80" placeholder="Enter University Name"/>
				  </div>
		 </div> 
		 
		  <div class="newColumn">
								<div class="leftColumn1">Supporting Doc:</div>
								<div class="rightColumn1">
								
				  	<s:file name="empDocument" id="empDocument" placeholder="Enter Data"  cssClass="textField"/>
					</div>
			      </div>
		 <div class="clear"></div>
		 	<div style="margin-top: 30px;">
							<s:if test="#deptIndx==113">
								<div class="user_form_button2"><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true"> - </sj:a></div>
							</s:if>
           					<s:else>
          	 					<div class="user_form_button2"><sj:a value="+"  onclick="adddiv('%{#deptIndx+1}')" button="true"> + </sj:a></div>
          						<div class="user_form_button3" style="padding-left: 10px;"><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true"> - </sj:a></div>
       						</s:else>
       						</div>
			 </div>     
			    
		         
          </s:iterator>  
          <br>
          <div class="form_menubox"><a href="#" onclick="checkStatus();"  class="user_form_text2" title="Add Work Experience / Remove Work Experience"><font color="#175810" id=""><b></b></font></a>
          </div> 
          <!-- Employee Exprioence work starts here -->
          <div id="workExprncDiv" style="display: none">
          
          		
				 <s:iterator value="empWorkExpLevels" status="counter">
				  <s:if test="%{mandatory}">
				  <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				 </s:if>
				 <s:if test="#counter.count%2 != 0">
				 <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				 				<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
				  </div>
				 </div>
				
				  </s:if>
				  <s:else>
				   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				 				<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
				  </div>
				 </div>
				 </s:else>
				 </s:iterator>
				 
				 
				
				 <s:iterator value="empWorkExpCalendr"  status="counter">
				 <s:if test="%{mandatory}">
				  <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				 </s:if>
				 <s:if test="#counter.count%2 != 0">
				   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				 				<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
				  </div>
				 </div>
				  </s:if>
				  <s:else>
				 <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				 				<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
				  </div>
				 </div>
				</s:else>
				 </s:iterator>
	     </div>
<div class="clear"> </div>
	     <center><img id="indicator4" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
        		<div style="width: 100%; text-align: center; padding-bottom: 10px;">
        			
	                <sj:submit 
	                        targets="orglevel4Div" 
	                        clearForm="true"
	                        value="  Save  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="level4"
	                        cssClass="submit"
	                         style="margin-left: -108px;"
	                        indicator="indicator4"
	                        onBeforeTopics="thirdValidate"
	                        />
	         <div id=reset style="margin-top: -25px; margin-left: 95px;">
				<sj:a 
							 button="true" href="#"
							 onclick="reset('formone');"
							 cssClass="submit"
						>
						
						Reset
					</sj:a>
				
					<sj:a 
							button="true" href="#"
							onclick="employeeView()"
							cssClass="submit"
						>
						
						Back
					</sj:a>
					</div>
				 
				<sj:div id="orglevel4"  effect="fold">
                    <div id="orglevel4Div"></div>
               </sj:div>
               </div>
        </s:form>
           
        <!-- END Point For Employee Work Record Fields-->
     
</sj:accordionItem>
</s:if>

<s:if test="empForAddWorkQua>0  && empNameForOther!=null">
<sj:accordionItem title="Professional Record " onclick="getAllEmpList('empName3','mob2');">                      

        <!-- Start Point For Employee Work Experience-->
                          
		 <s:form  id="empwork" name="empwork"  theme="css_xhtml" namespace="/view/Over2Cloud/hr" action="createEmpWorkExpDetails"  method="post" enctype="multipart/form-data">
		  <center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="aserrZone" style="float:center; margin-left: 7px"></div></div></center>
		
	       <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{empNameForOther}"/>:</div>
								 <span id="hhhhh" class="aspIds" style="display: none; ">empName3#<s:property value="%{empNameForOther}"/>#D#,</span>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
								   <s:select 
				                              cssClass="select"
				                              id="empName3"
				                              name="empName" 
				                              list="{'No Data'}" 
				                              headerKey="-1"
				                              headerValue="Select Employee" 
				                              cssClass="form_menu_inputtext"
									          cssStyle="width:82%"
				                              >
				                  </s:select>
				  				</div>
		  </div>
<s:iterator value="empWorkExpLevels" >
				  <s:if test="%{mandatory}">
				  <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				 </s:if>
				 <s:if test="%{key == 'workExp'}">
				  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  	<s:textfield name="%{key}" id="textfields"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px" onkeyup="totalExp()"/>
				  </div>
			      </div>
				  </s:if>
				 </s:iterator>
				 
		<div id="workExpDiv" style="display: none">		 
				 <s:iterator value="empWorkExpLevels" >
				  <s:if test="%{mandatory}">
				  <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				 </s:if>
				 <s:if test="%{key == 'cname'}">
				  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  	<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
				  </div>
			      </div>
				  </s:if>
				 <s:if test="%{key == 'salary'}">
				   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  	<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
				  </div>
			      </div>
				 </s:if>
				 </s:iterator>
				 
				  <s:iterator value="empWorkExpLevels" >
				  <s:if test="%{mandatory}">
				  <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				 </s:if>
				 <s:if test="%{key == 'phoneNo'}">
				  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  	<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
				  </div>
			      </div>
				  </s:if>
				 <s:if test="%{key == 'caddress'}">
				   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  	<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
				  </div>
			      </div>
				 </s:if>
				 </s:iterator>
				 
				 
				  <s:iterator value="empWorkExpLevels" >
				  <s:if test="%{mandatory}">
				  <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				 </s:if>
				 <s:if test="%{key == 'designation'}">
				  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  	<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
				  </div>
			      </div>
				  </s:if>
				 <s:if test="%{key == 'cdepartment'}">
				   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  	<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
				  </div>
			      </div>
				 </s:if>
				 </s:iterator>
				 
				  <s:iterator value="empWorkExpLevels" >
				  <s:if test="%{mandatory}">
				  <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				 </s:if>
				 <s:if test="%{key == 'pfrom'}">
				  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  	<sj:datepicker name="%{key}" id="%{key}" readonly="true" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px" displayFormat="dd-mm-yy" />
				  </div>
			      </div>
				  </s:if>
				 <s:if test="%{key == 'pto'}">
				   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  	<sj:datepicker name="%{key}" id="%{key}" readonly="true" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px" displayFormat="dd-mm-yy" />
				  </div>
			      </div>
				 </s:if>
				 </s:iterator>
				 
				 
				 <s:iterator value="empWorkExpLevels" >
				  <s:if test="%{mandatory}">
				  <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				 </s:if>
				 <s:if test="%{key == 'refOneName'}">
				  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  	<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
				  </div>
			      </div>
				  </s:if>
				 <s:if test="%{key == 'refOneMobNo'}">
				   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  	<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
				  </div>
			      </div>
				 </s:if>
				 </s:iterator>
				 
				 
				 <s:iterator value="empWorkExpLevels" >
				  <s:if test="%{mandatory}">
				  <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				 </s:if>
				 <s:if test="%{key == 'reftwoName'}">
				  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  	<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
				  </div>
			      </div>
				  </s:if>
				 <s:if test="%{key == 'refTwoMobNo'}">
				   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				  	<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
				  </div>
			      </div>
				 </s:if>
				 </s:iterator>
				 
				 <s:iterator value="empWorkExpFileBox" >
				 <s:if test="%{mandatory}">
                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>
				 
		         <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								
				  	<s:file name="%{key}" id="%{key}" placeholder="Enter Data"  cssClass="textField"/>
					</div>
			      </div>
			     
				  </s:iterator>
		</div>		 
<div class="clear"> </div>
	     <center><img id="indicator7" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
        		<div style="width: 100%; text-align: center; padding-bottom: 10px;">
        			
	                <sj:submit 
	                        targets="orglevel5Div" 
	                        clearForm="true"
	                        value="  Save  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="level5"
	                        cssClass="submit"
	                         style="margin-left: -108px;"
	                        indicator="indicator7"
	                        />
	         <div id=reset style="margin-top: -25px; margin-left: 95px;">
				<sj:a 
							 button="true" href="#"
							 onclick="reset('formone');"
							 cssClass="submit"
						>
						
						Reset
					</sj:a>
				
					<sj:a 
							button="true" href="#"
							onclick="employeeView()"
							cssClass="submit"
						>
						
						Back
					</sj:a>
					</div>
				 
				<sj:div id="orglevel5"  effect="fold">
                    <div id="orglevel5Div"></div>
               </sj:div>
               </div>
        </s:form>
           
        <!-- END Point For Employee Work Record Fields-->
     
</sj:accordionItem>
</s:if>
<s:if test="empForAddBaank>0  && empNameForOther!=null">
<sj:accordionItem title="Bank Record"  onclick="getAllEmpList('empName4','mobNoPerTemp');">
	    <s:form  id="empBankAccount"  theme="css_xhtml" namespace="/view/Over2Cloud/hr" action="createEmpBankDetails"  method="post" >
			<center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="dserrZone" style="float:center; margin-left: 7px"></div></div></center>
                
                <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{empNameForOther}"/>:</div>
								<div class="rightColumn1"> <span id="hhhhh" class="dspIds" style="display: none; ">empName4#<s:property value="%{empNameForOther}"/>#D#,</span>
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				 				 <s:select 
		                              cssClass="form_menu_inputtext"
		                              id="empName4"
		                              name="empName" 
		                              list="{'No Data'}" 
		                              headerKey="-1"
		                              headerValue="Select Employee" 
		                              cssClass="textField"
							          cssStyle="width:82%"
		                              > 
                 				 </s:select>
				  </div>
				   </div>
                 
                  
                  <s:if test="accountTypeLevel!=null">
                  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{accountTypeLevel}"/>:</div>
								<div class="rightColumn1">   <span id="hhhhh" class="dspIds" style="display: none; ">acType#<s:property value="%{accountTypeLevel}"/>#D#,</span>
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				 				 <s:select 
		                            cssClass="form_menu_inputtext"
		                            id="acType"
		                            name="acType"
		                            list="{'Current A/c','Saving A/c'}"
		                            headerKey="-1"
		                            cssClass="textField"
							        cssStyle="width:82%"
		                            headerValue="Select Account Type"
		                            >
                 				 </s:select>
				  </div>
				  </div>
                  </s:if>

				 <s:iterator value="empBankLevels" status="counter">
				 <s:if test="#counter.count%2 != 0">
				   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">  
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				 				<s:textfield name="%{key}" id="%{key}" cssStyle="margin:0px 0px 10px 0px"   cssClass="textField" placeholder="Enter Data"/>
				  </div>
				  </div>
				  </s:if>
				  <s:else>
				   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">  
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				 				<s:textfield name="%{key}" id="%{key}" cssStyle="margin:0px 0px 10px 0px"   cssClass="textField" placeholder="Enter Data"/>
				  </div>
				  </div>
				 </s:else>
				 </s:iterator>
				  <s:iterator value="bankFileBox" >
				 <s:if test="%{mandatory}">
                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>
				 
		         <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								
				  	<s:file name="%{key}" id="%{key}" placeholder="Enter Data"  cssClass="textField"/>
					</div>
			      </div>
			     
				  </s:iterator>
				  
				  
				  
				  
				  
			<div class="clear"></div>	 
            <center><img id="indicator5" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
          <div style="width: 100%; text-align: center; padding-bottom: 10px;">
	                <sj:submit 
	                        targets="orglevel3Div" 
	                        clearForm="true"
	                        value="  Save  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="level3"
	                        cssClass="submit"
	                         style="margin-left: -108px;"
	                        indicator="indicator5"
	                        onBeforeTopics="fourthValidate"
	                        />
	                        <div id=reset style="margin-top: -25px; margin-left: 95px;">
				<sj:a 
							 button="true" href="#"
							 onclick="reset('formone');"
							 cssClass="submit"
						>
						
						Reset
					</sj:a>
				
					<sj:a 
							button="true" href="#"
							onclick="employeeView()"
							cssClass="submit"
						>
						
						Back
					</sj:a>
					</div> 
	                   
				<sj:div id="orglevel3"  effect="fold">
                    <div id="orglevel3Div"></div>
               </sj:div>
               </div>
           
        </s:form>
</sj:accordionItem>
</s:if>

</sj:accordion>
</div>
</div>
</div>
</div>
</body>
</html>
