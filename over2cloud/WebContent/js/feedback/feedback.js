$.subscribe('validateActionDash', function(event,data)
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
				mystring=$(".pIds").text();
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
			            else if(colType=="TextArea")
			            {
				            if(validationType=="an")
				            {
					            var allphanumeric = /^[A-Za-z0-9 ]{3,80}$/;
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
				
		    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		    	$.ajax({
		    	    type : "post",
		    	    url : "view/Over2Cloud/feedback/showDashBoardNormal.action",
		    	    success : function(subdeptdata) {
		    $("#"+"data_part").html(subdeptdata);
		    	},
		    	   error: function() {
		       alert("error");
		    }
		    	 });
		    }
		});




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
				mystring=$(".pIds").text();
			}
	//		alert("My String >>"+mystring);
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
		            	//alert("<<<<<<<<<<<"+colType+">>>>>>>>>>>"+validationType);
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
			            else if(colType=="TextArea")
			            {
				            if(validationType=="an")
				            {
					            var allphanumeric = /^[A-Za-z0-9 ]{3,80}$/;
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
		    }
		});


function getFeedSubDeptCategory(subDeptId,destAjaxDiv) {
	//alert("Hello"+subDeptId+"Destination is as <><><>>>>>>>>>>>>>>>>>>>>>>>>>"+destAjaxDiv);
//	alert("/cloudapp/view/Over2Cloud/feedback/FBCatAjax.action?destination="+ destAjaxDiv+"&deptOrSubDeptId="+subDeptId);
	 {
	$("#tosdId").val(subDeptId);
	
	//alert("/over2cloud/view/Over2Cloud/feedback/FBCatAjax.action?destination="+ destAjaxDiv+"&subdept="+subDeptId);
	
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/feedback/FBCatAjax.action?destination="+ destAjaxDiv+"&subdept="+subDeptId,
		success : function(feedTypeData){
	//	alert(feedTypeData);
		$("#" + destAjaxDiv).html(feedTypeData);
	    },
	    error : function () {
			alert("Somthing is wrong to get Feed Category");
		}
	});
	}
}


$.subscribe('validateTicketOnline',function(event, data)
		 {
			$("#confirmEscalationDialog").dialog('open');
		 });

function getFeedSubCategoryFeedback(categoryId,destAjaxDiv)
{
//	alert("Hello"+categoryId+">>>>"+destAjaxDiv);
	//alert("categoryId is as >>>>>"+categoryId+"destAjaxDiv is as >>>>>>>>>>"+destAjaxDiv+"URL is as +/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Feedback_Draft/FBSubCategoryAjax.action?destination="+destAjaxDiv+"&catg="+categoryId);
	var catgId=$("#"+categoryId).val();
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/Feedback_Over2Cloud/Feedback_Draft/FBSubCategoryAjax.action?destination="+destAjaxDiv+"&catg="+categoryId,
		success : function(categoryData){
		$("#" + destAjaxDiv).html(categoryData);
	    },
	    error : function () {
			alert("Somthing is wrong to get Category");
		}
	});
}
