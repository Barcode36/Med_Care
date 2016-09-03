$.subscribe('validateAction', function(event,data)
{
	var feedstatus=document.getElementById('feedStatus').value;
			var mystring=null;
			var status_new=$("#old_status").val();
			if($("#status").val()!='' && $("#status").val()=='Resolved')
			{
				mystring=$(".reSpIds").text();
			}
			else if($("#status").val()!='' && $("#status").val()=='Snooze')
			{
				mystring=$(".sZpIds").text();
			}
			else if($("#status").val()!='' && $("#status").val()=='Re-Opened')
			{
				mystring=$(".reOpIds").text();
			}
			else if($("#status").val()!='' && $("#status").val()=='High Priority')
			{
				mystring=$(".hPpIds").text();
			}
			else if($("#status").val()!='' && $("#status").val()=='Ignore')
			{
				mystring=$(".iGpIds").text();
			}
			else if($("#status").val()!='' && $("#status").val()=='Re-Assign')
			{
				mystring=$(".reAssignPIds").text();
			}
			
			else
			{
				mystring=$(".pIds1").text();
			}
		    var fieldtype = mystring.split(",");
		    var pattern = /^\d{10}$/;
		    for(var i=0; i<fieldtype.length; i++)
		    {
		        var fieldsvalues = fieldtype[i].split("#")[0];
		        var fieldsnames = fieldtype[i].split("#")[1];
		        var colType = fieldtype[i].split("#")[2]; 
		        var validationType = fieldtype[i].split("#")[3];
		    //    alert(fieldsvalues+"  "+fieldsnames+" "+colType+"  "+$("#"+fieldsvalues).val());
		        $("#"+fieldsvalues).css("background-color","");
		        errZone1.innerHTML="";
		        if(fieldsvalues!= "" )
		        {
		            if(colType=="D")
		            {
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
			            
			            
		            }
		            else if(colType =="T")
		            {
			            if(validationType=="n")
			            {
				            var numeric= /^[0-9]+$/;
				            if(!(numeric.test($("#"+fieldsvalues).val())))
					        {
					            errZone1.innerHTML="<div class='user_form_inputError2'>Please Enter Numeric Values of "+fieldsnames+"</div>";
					            setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
					            setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
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
					            errZone1.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
					            setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
					            setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
					            $("#"+fieldsvalues).focus();
					            $("#"+fieldsvalues).css("background-color","#ff701a");
					            event.originalEvent.options.submit = false;
					            break;
				            }
			            }
			            
			            else if(validationType=="anl")
			            {
				            var allphanumeric = /^[A-Za-z0-9 ]{1,80}$/;
				            if(!(allphanumeric.test($("#"+fieldsvalues).val())))
					        {
					            errZone1.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
					            setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
					            setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
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
					            errZone1.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
					            setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
					            setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
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
				                errZone1.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
				                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
				                $("#"+fieldsvalues).focus();
				                setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
				                setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
				                event.originalEvent.options.submit = false;
				                break;
				            }
				            else if (!pattern.test($("#"+fieldsvalues).val()))
				            {
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
				                errZone1.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
				                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
				                $("#"+fieldsvalues).focus();
				                setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
				                setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
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
					            errZone1.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
					            $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
					            $("#"+fieldsvalues).focus();
					            setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
					            setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
					            event.originalEvent.options.submit = false;
					            break;
			            	 }
			              }
			              else if(validationType =="w")
			              {
			              }
			              else if(validationType =="sc"){
			            	  if($("#"+fieldsvalues).val().length < 1)
					             {
					                errZone1.innerHTML="<div class='user_form_inputError2'>Enter "+fieldsnames+" !!! </div>";
					                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
					                $("#"+fieldsvalues).focus();
					                setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
					                setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
					                event.originalEvent.options.submit = false;
					                break;
					             }
			             }
		             	}
			            else if(colType=="TextArea")
			            {
				            if(validationType=="an")
				            {
					            var allphanumeric = /^[A-Za-z0-9 ]{3,80}$/;
					            if(!(allphanumeric.test($("#"+fieldsvalues).val())))
					            {
						            errZone1.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
						            setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
						            setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
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
					            errZone1.innerHTML="<div class='user_form_inputError2'>Please Select Time "+fieldsnames+" Value </div>";
					            setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
					            setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
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
		    
		    
		    if(event.originalEvent.options.submit != false)
		    {
		    	/* $("#gridedittable").jqGrid('setGridParam', {
		 			url:"/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/viewFeedbackDetail.action?feedStatus="+feedstatus
		 			,datatype:'JSON'
		 			}).trigger("reloadGrid");
		    	 validate();*/
		    /*	
		    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
				$.ajax({
				    type : "post",
				    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/beforeFeedAction.action?feedStatus="+status_new,
				    success : function(subdeptdata) {
			       $("#"+"data_part").html(subdeptdata);
				},
				   error: function() {
			            alert("error");
			        }
				 });
				
				
				*/
		    	 $("#takeActionGrid").dialog('close');
		    	 onloadData(); 
		    
		    }
		});

$.subscribe('successfullSubmission',function(event,data)
 {
	var status = $("#old_status").val();
    $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
  	$.ajax({
  	    type : "post",
  	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset_Complaint/beforeFeedView.action?feedStatus="+status+"&moduleName=ASTM&dataFlag=N&remark=N",
  	    success : function(data) 
  	    {
  			$("#"+"data_part").html(data);
  	    },
  	    error: function() 
  	    {
              alert("error");
          }
  	 });	
 });

$.subscribe('closeProgressbar',function(event, data)
 {
	 document.getElementById("indicator1").style.display="none";
	 setTimeout(function(){ $("#feed_status").dialog('close'); }, 500);
 });

function validate()
 {
	 document.getElementById("indicator1").style.display="block";
	 document.getElementById("bt").style.display="none";
 }