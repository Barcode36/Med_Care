function resetForm(formId)
{
    $('#'+formId).trigger("reset");
     $("#formDiv").html("");
}


function getFullDetail(patId)
{
    if (document.getElementById(patId).value!=''&&patId=='clientId') 
    {
        $("#patientDetialsViewDiv").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
      var patientId = document.getElementById(patId).value;
      var patType= document.getElementById("compType").value;
      $.ajax({
          type : "post",
          url : "view/Over2Cloud/feedback/getDataForIdInJson.action?patientId="+patientId+"&patType="+patType,
          success : function(subdeptdata) 
          {
              $("#"+"patientDetialsViewDiv").html(subdeptdata);
          },
         error: function() 
         {
             alert("error");
         }
       });
    }
}

$.subscribe('validateForm', function(event,data)
{
//    alert("hh");
     $("#choseHospital").css("background-color","#ffffff");
    if($("#formcheck").val()=='checkedform'){
        var mystring = $(".pIds").text();
        var fieldtype = mystring.split(",");
        var pattern = /^\d{10}$/;
        for(var i=0; i<fieldtype.length; i++)
        {
            
            var fieldsvalues = fieldtype[i].split("#")[0];
            var fieldsnames = fieldtype[i].split("#")[1];
            var colType = fieldtype[i].split("#")[2];   //fieldsType[i]=first_name#t
            var validationType = fieldtype[i].split("#")[3];
           
            var value1=document.getElementsByName("targetOn");
            var value2=document.getElementsByName("overAll");
            
            if ($("#choseHospital").val()==0||$("#choseHospital").val()==null) {
                
                  errZone1.innerHTML="<div class='user_form_inputError2'>Please Select Choose Us Because Of Field </div>";
                  setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
                  setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
                  $("#choseHospital").focus();
                  $("#choseHospital").css("background-color","#ff701a");
                 event.originalEvent.options.submit = false;
                 break;
            }
            
            if (value1[0].checked==false&&value1[1].checked==false) {
                  errZone1.innerHTML="<div class='user_form_inputError2'>Please Select Recommend Us To Other Field </div>";
                  setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
                  setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
                 $("#targetOn").focus();
                  $("#targetOn").css("background-color","#ff701a");
                 event.originalEvent.options.submit = false;
                 break;
            }
            if (value2[0].checked==false&&value2[1].checked==false) {
                  errZone1.innerHTML="<div class='user_form_inputError2'>Please Select Over All Satisfied Field </div>";
                    setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
                    setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
                 $("#overAll").focus();
                  $("#overAll").css("background-color","#ff701a");
                 event.originalEvent.options.submit = false;
                 break;
            }
            
          
           
          
            if(fieldsvalues!= "" )
            {
                if(colType=="D"){
               
                if($("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
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
                if(validationType=="n"){
                var numeric= /^[0-9]+$/;
                if(!(numeric.test($("#"+fieldsvalues).val()))){
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
                 var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
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
                else if(validationType=="a"){
                if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val()))){
                errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
                $("#"+fieldsvalues).focus();
                $("#"+fieldsvalues).css("background-color","#ff701a");
                event.originalEvent.options.submit = false;
                break;     
                  }
                 }
                else if(validationType=="m"){
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
                 else if(validationType =="e"){
                 if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val())){
                 }else{
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
                var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
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
            }
            else
            {
                $("#confirmEscalationDialog").dialog('open');
                $("#confirmEscalationDialog").dialog({title:'Feedback Form Opening Status'});
            }
        }
    }else {
        errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Data Before Submitting  </div>";
        setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
        setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
        $("#patientDetialsViewDiv").focus();
        event.originalEvent.options.submit = false;
        
    }
            
});