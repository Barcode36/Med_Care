<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%
int temp=0;
%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true"  jquerytheme="mytheme" customBasepath="%{contextz}"/>
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/compliance/compliance.js"/>"></script>
<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<SCRIPT type="text/javascript">
 $.subscribe('makeEffect', function(event,data)
  {
	 setTimeout(function(){ $("#complTarget").fadeIn(); }, 10);
	 setTimeout(function(){ $("#complTarget").fadeOut(); }, 400);
	 $.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Compliance/compliance_pages/beforeComplExcelupload.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		 },
		   error: function() {
	            alert("error");
	        }
		 });
	 
  });
function getTotalday(selectedDate)
{
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/Compliance/compliance_pages/getTotalDays.action?selectedDate="+selectedDate,
		success : function(subDeptData)
		{
			$( "#fromDate" ).datepicker( "option", "maxDate", subDeptData);
	    },
	    error : function () {
			alert("Somthing is wrong to get Sub Department");
		}
	});
}
function changeEnable(timeId,ddId)
{
	$( "#"+timeId).prop("disabled", false);
	$( "#"+ddId).prop("disabled", false);
}
function getReminderValue(freqId,remId1,remId2,remId3,divId)
{
	var frequency="Y";
	var var1=parseInt($("#"+remId1).val());
	var var2=parseInt($("#"+remId2).val());
	var var3=parseInt($("#"+remId3).val());
	var totalReminder=var1+var2+var3;
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/Compliance/compliance_pages/getReminder.action?frequency="+frequency+"&totalReminder="+totalReminder+"&divId="+divId,
		success : function(subDeptData)
		{
			$("#"+divId).html(subDeptData);
	    },
	    error : function () {
			alert("Somthing is wrong to get Sub Department");
		}
	});
}
function changeDDValue(dropdownId)
{
	$('#reminder2').empty();
}

function getNewUploadData(div)
{
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/Compliance/compliance_pages/getNewUploadData.action",
		success : function(data)
		{
			$('#'+div+' option').remove();
			$('#'+div).append($("<option></option>").attr("value",-1).text('Select Task Type'));
			$(data).each(function(index)
			{
			   $('#'+div).append($("<option></option>").attr("value",this.ID).text(this.NAME));
			});
	    },
	    error : function () {
			alert("Somthing is wrong to get getNewUploadData");
		}
	});
	$("#excel").val("");	
}
function getTaskDetail(taskId,divId)
{
	
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/Compliance/compliance_pages/getTaskDetail.action?taskId="+taskId,
		success : function(dataMap)
		{
			$("#taskNameDetailBlock").show();
			
			//$("#taskType").val(dataMap.taskType);
			$("#taskBrief").val(dataMap.taskBrief);
			$("#msg").val(dataMap.msg);
			$("#frequency").val(dataMap.frequency);
			$("#dueDate").val(dataMap.dueDate);
			$("#dueTime").val(dataMap.dueTime);
			$("#taskName").val(dataMap.taskName);
			$("#completion").val(dataMap.completion);
			$("#complExcelDataId").val(dataMap.complExcelDataId);
			
			if(dataMap.frequency=="OT" || dataMap.frequency=="D")
			{
				$("#reminderBlock").hide();
			}
			else
			{
				$("#reminderBlock").show();
			}
	    },
	    error : function () {
			alert("Somthing is wrong to get Sub Department");
		}
	});
	
}

function getCurrentColumn()
{
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/Compliance/compliance_pages/downloadFormat.action"
	});
}

$.subscribe('excelConfigValidate', function(event,data)
		  {
	var mystring=null;
	mystring=$(".pIds").text();
	var fieldtype = mystring.split(","); 
    var pattern = /^\d{10}$/;
    for(var i=0; i<fieldtype.length; i++)
    {
        var fieldsvalues   = fieldtype[i].split("#")[0];
        var fieldsnames    = fieldtype[i].split("#")[1];
        var colType        = fieldtype[i].split("#")[2];   
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
            else if(colType=="date")
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
            else if(colType=="R")
            {
            	if($('#reminder_forremToSelf').is(':checked') || $('#reminder_forremToOther').is(':checked') || $('#reminder_forremToBoth').is(':checked'))
        	    { 
        	    }
        	 	else
        	 	{
        		 	errZone.innerHTML="<div class='user_form_inputError2'>Please Select Date "+"Remind To"+" Value  </div>";
         			setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
         			setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
         			$("#"+fieldsvalues).focus();
         			$("#"+fieldsvalues).css("background-color","#ff701a");
         			event.originalEvent.options.submit = false;
         			break;   
        	 	}
            	if($('#remindModeSMS').is(':checked') || $('#remindModeMail').is(':checked') || $('#remindModeBoth').is(':checked') || $('#remindModeNone').is(':checked'))
        	    { 
        	    }
        	 	else
        	 	{
        		 	errZone.innerHTML="<div class='user_form_inputError2'>Please Select Date "+"Mode"+" Value  </div>";
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
            				var allphanumeric = /^[A-Za-z0-9 ]{1,50}$/;
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
            			if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val())) || $("#"+fieldsvalues).val()=='NA')
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
            else if(colType=="TA")
            {
            if(validationType=="an"){
            var allphanumeric = /^[A-Za-z0-9 ]{1,100}$/;
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
            else if(colType=="time"){
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
            else if(colType=="date")
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
            	if(document.formone.recvSMS.checked==true && document.formone.recvEmail.checked==true)
            	{
 		            if($("#feed_by_mobno").val().length > 10 || $("#feed_by_mobno").val().length < 10)
 		            {
 		                errZone.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
 		                $("#feed_by_mobno").focus();
			            $("#feed_by_mobno").css("background-color","#ff701a");
 		                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
 		                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
 		                event.originalEvent.options.submit = false;
 		                break;
 		            }
 		            else if (!pattern.test($("#feed_by_mobno").val())) {
 		                errZone.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
 		                $("#feed_by_mobno").focus();
			            $("#feed_by_mobno").css("background-color","#ff701a");
 		                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
 		                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
 		                event.originalEvent.options.submit = false;
 		                break;
 		             }
 		             else if(($("#feed_by_mobno").val().charAt(0)!="9") && ($("#feed_by_mobno").val().charAt(0)!="8") && ($("#feed_by_mobno").val().charAt(0)!="7") && ($("#feed_by_mobno").val().charAt(0)!="6"))
 		             {
 		                errZone.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
 		                $("#feed_by_mobno").focus();
			            $("#feed_by_mobno").css("background-color","#ff701a");
 		                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
 		                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
 		                event.originalEvent.options.submit = false;
 		                break;
 		             }
 		             else if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#feed_by_emailid").val())){
		             }else{
				            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
				            $("#feed_by_emailid").css("background-color","#ff701a");  //255;165;79
				            $("#feed_by_emailid").focus();
				            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
				            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
				            event.originalEvent.options.submit = false;
				            break;
				     }
            	}
            	else if(document.formone.recvSMS.checked==true)
            	{
            		if($("#feed_by_mobno").val().length > 10 || $("#feed_by_mobno").val().length < 10)
 		            {
 		                errZone.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
 		                $("#feed_by_mobno").focus();
			            $("#feed_by_mobno").css("background-color","#ff701a");
 		                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
 		                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
 		                event.originalEvent.options.submit = false;
 		                break;
 		            }
 		            else if (!pattern.test($("#feed_by_mobno").val())) {
 		                errZone.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
 		                $("#feed_by_mobno").focus();
			            $("#feed_by_mobno").css("background-color","#ff701a");
 		                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
 		                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
 		                event.originalEvent.options.submit = false;
 		                break;
 		             }
 		             else if(($("#feed_by_mobno").val().charAt(0)!="9") && ($("#feed_by_mobno").val().charAt(0)!="8") && ($("#feed_by_mobno").val().charAt(0)!="7") && ($("#feed_by_mobno").val().charAt(0)!="6"))
 		             {
 		                errZone.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
 		                $("#feed_by_mobno").focus();
			            $("#feed_by_mobno").css("background-color","#ff701a");
 		                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
 		                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
 		                event.originalEvent.options.submit = false;
 		                break;
 		             }
            	}
            	else if(document.formone.recvEmail.checked==true)
            	{
            		if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#feed_by_emailid").val()))
                	{
		            }
		            else
			        {
				            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
				            $("#feed_by_emailid").css("background-color","#ff701a");  //255;165;79
				            $("#feed_by_emailid").focus();
				            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
				            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
				            event.originalEvent.options.submit = false;
				            break;
				    }
            	}
            }
        }
    }
    if(event.originalEvent.options.submit != false)
    {
    	if($("#escalation").val()!='' && $("#escalation").val()=='Y')
		{
    		if($("#escalation_level_1").val()==null || $("#escalation_level_1").val()=="" || $("#escalation_level_1").val()=="-1")
    		{
    			errZone.innerHTML="<div class='user_form_inputError2'>Please Select Escalation L1 Value from Drop Down </div>";
        		setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
        		setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
        		$("#escalation_level_1").focus();
        		$("#escalation_level_1").css("background-color","#ff701a");
        		event.originalEvent.options.submit = false;
    		}
    		else if($("#l1EscDuration").val()==null || $("#l1EscDuration").val()=="")
    		{
    			errZone.innerHTML="<div class='user_form_inputError2'>Please L1 Esc Duration </div>";
        		setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
        		setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
        		$("#l1EscDuration").focus();
        		$("#l1EscDuration").css("background-color","#ff701a");
        		event.originalEvent.options.submit = false;
    		}
		}
    }
		  });
function resetCompl(formId)
{
	$('#'+formId).trigger("reset");
	$("#excel").val("");
	document.getElementById("taskNameDetailBlock").style.display="none";
	document.getElementById("file").style.display="none";
	document.getElementById("configCompliance1").style.display="none";
	document.getElementById("configCompliance2").style.display="none";
	document.getElementById("escalationDIV").style.display="none";
	document.getElementById("escalationDIV1").style.display="none";
	document.getElementById("escalationDIV2").style.display="none";
	document.getElementById("escalationDIV3").style.display="none";
	document.getElementById("remToOther").style.display="none";
	document.getElementById("remToBoth").style.display="none";
	document.getElementById("reminderBlock").style.display="block";
}
</SCRIPT>

</head>
<body>

<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Operation Task</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Configure</div> 
	  <div id="test"  class="alignright printTitle">
				   <sj:a id="sendActivity"  cssClass="button" button="true" title="Activity Board" cssStyle="margin-top: 4px;" onclick="activtyBoard();">Activity Board</sj:a>
		</div>	
</div>
<div class="clear"></div>
<div style="overflow-x:hidden; height:400px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">

       <center>
	   <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 50%; height: 10%; font-size: small; border: 0px solid red; border-radius: 6px;">
	   <div id="errZone" style="float:center; margin-left: 7px"></div>
	   </div>
	   </center>
	      <div id="test" class="rightColumn" style="position: static; margin-left:  106px; margin-top:-15px;">
	   <s:form id="formmatDownload" name="formmatDownload" action="downloadFormat" namespace="/view/Over2Cloud/Compliance/compliance_pages" theme="simple"  method="post" enctype="multipart/form-data" >
	   		<sj:a id='formatDownload' button="true" formIds="formmatDownload" style="top: 9px;left: -79px;">Download Format</sj:a>
	   </s:form>
	   </div>
	   <s:form id="upload_refresh" name="upload_refresh" action="uploadComliance" namespace="/view/Over2Cloud/Compliance/compliance_pages" theme="simple"  method="post" enctype="multipart/form-data" >
       <div class="newColumn">
       <div class="leftColumn">Department</div>
       <div class="rightColumn">
       <span class="needed"></span>
       <div id="taskTypeDiv">
	   <s:select  
                  id					=	"deptName"
                  list				=		"deptList"
                  name				=		"userDeptID"
                  headerKey			=		"-1"
                  headerValue			=	"Select Department" 
                  cssClass			=		"select"
				  cssStyle			=		"width:82%"
             >
	   </s:select>
	   </div>
	   </div>
	   </div>
       
       
       <div class="newColumn">
	   <div class="leftColumn">Excel File&nbsp;:</div>
	   <div class="rightColumn">
       <s:file name="excel" id="excel"/></div>
	   </div>
	   <sj:submit  value=" Upload " targets="createAssetBasic"  clearForm="true" button="true"  onBeforeTopics="validExcelAsset"/>
	     <sj:a id="back" button="true"  onclick="complianceConfigure();">Back</sj:a>
	   <!--<sj:a id='refresh' button="true"  onclick="getNewUploadData('taskType')">Refresh</sj:a>
	   --></s:form>
	   
	   
	
	   
	   <!--<s:form id="configCompExcelData" name="configCompExcelData" action="configCompExcelData" namespace="/view/Over2Cloud/Compliance/compliance_pages" theme="simple"  method="post" enctype="multipart/form-data" >
	   <div class="clear"></div>
	   
   	   <span id="complSpan" class="pIds" style="display: none; ">taskType#Task Type#D#,taskType#Task Type#,</span>
       <span id="newcomplSpan" class="newpIds" style="display: none; ">taskType#Task Type#D#,taskType#Task Type#,</span>
       <span id="remind2Other" class="rem2otherpIds" style="display: none; ">taskType#Task Type#D#,taskType#Task Type#,</span>
       <span id="remind2Both" class="rem2bothpIds" style="display: none; ">taskType#Task Type#D#,taskType#Task Type#,</span>
       <span id="remind2OtherNew" class="rem2othernewpIds" style="display: none; ">taskType#Task Type#D#,taskType#Task Type#,</span>
       <span id="remind2BothNew" class="rem2bothnewpIds" style="display: none; ">taskType#Task Type#D#,taskType#Task Type#,</span>
       
       <span id="complSpan" class="pIds" style="display: none; ">excelDataId#Task Name#D#,taskType#Task Type#T#D,</span>
       <span id="newcomplSpan" class="newpIds" style="display: none; ">excelDataId#Task Name#D#,taskType#Task Type#T#D,</span>
       <span id="remind2Other" class="rem2otherpIds" style="display: none; ">excelDataId#Task Name#D#,taskType#Task Type#T#D,</span>
       <span id="remind2Both" class="rem2bothpIds" style="display: none; ">excelDataId#Task Name#D#,taskType#Task Type#T#D,</span>
       <span id="remind2OtherNew" class="rem2othernewpIds" style="display: none; ">excelDataId#Task Name#D#,taskType#Task Type#T#D,</span>
       <span id="remind2BothNew" class="rem2bothnewpIds" style="display: none; ">excelDataId#Task Name#D#,taskType#Task Type#T#D,</span>
       
       
       
  	   <div class="newColumn">
       <div class="leftColumn">Task Type&nbsp;:</div>
       <div class="rightColumn">
       <span class="needed"></span>
       <div id="taskTypeDiv">
	   <s:select  
	                        id					=		"taskType"
	                        list				=		"excelDataMap"
	                        name				=		"taskType"
	                        headerKey			=		"-1"
	                        headerValue			=		"Select Task Type" 
	                        cssClass			=		"select"
							cssStyle			=		"width:82%"
							onchange			=		"getTaskNameDetails(this.value,'escelData','excelDataId');"
	                           >
	   </s:select>
	   </div>
	   </div>
	   </div>
	   
	   <div class="newColumn">
       <div class="leftColumn">Task Name&nbsp;:</div>
       <div class="rightColumn">
       <span class="needed"></span>
	   <s:select  
	                        id					=		"excelDataId"
	                        list				=		"{'No Data'}"
	                        name				=		"taskName"
	                        headerKey			=		"-1"
	                        headerValue			=		"Select Task Name" 
	                        cssClass			=		"select"
							cssStyle			=		"width:82%"
							onchange			=		"getTaskDetail(this.value,'taskNameDetailBlock');"
	                           >
	   </s:select>
	   </div>
	   </div>
	   <div id="taskNameDetailBlock" style="display:none">
	   
	   <div class="newColumn">
  	   <div class="leftColumn">Task Type&nbsp;:</div>
       <div class="rightColumn">
       <s:textfield name="taskType" id="taskType"  maxlength="50" readonly="true" placeholder="Enter Data" cssClass="textField"/>
       <s:hidden name="taskName" id="taskName"  maxlength="50" readonly="true" placeholder="Enter Data" cssClass="textField"/>
       </div>
       </div>        
       
       
       <div class="newColumn">
  	   <div class="leftColumn">Task Brief&nbsp;:</div>
       <div class="rightColumn">
       <s:textfield name="taskBrief" id="taskBrief"  maxlength="50" readonly="true" placeholder="Enter Data" cssClass="textField"/>
       <s:hidden id="complExcelDataId" name="complExcelDataId"/>
       </div>
       </div>        
       
       <div class="newColumn">
  	   <div class="leftColumn">Reminder Msg&nbsp;:</div>
       <div class="rightColumn">
       <s:textfield name="msg" id="msg"  maxlength="50" readonly="true" placeholder="Enter Data" cssClass="textField"/>
       </div>
       </div>  
       
       <div class="newColumn">
  	   <div class="leftColumn">Completion Tip&nbsp;:</div>
       <div class="rightColumn">
       <s:textfield name="completion" id="completion"  maxlength="50" readonly="true" placeholder="Enter Data" cssClass="textField"/>
       </div>
       </div>      
       
       <div class="newColumn">
  	   <div class="leftColumn">Frequency&nbsp;:</div>
       <div class="rightColumn">
       <s:textfield name="frequency" id="frequency"   maxlength="50" readonly="true" placeholder="Enter Data" cssClass="textField"/>
       </div>
       </div>        
       
       <div class="newColumn">
  	   <div class="leftColumn">Due Date&nbsp;:</div>
       <div class="rightColumn">
       <s:textfield name="dueDate" id="dueDate"  maxlength="50" readonly="true" placeholder="Enter Data" cssClass="textField"/>
       </div>
       </div>        
       
       <div class="newColumn">
  	   <div class="leftColumn">Due Time&nbsp;:</div>
       <div class="rightColumn">
       <s:textfield name="dueTime" id="dueTime"  maxlength="50" readonly="true" placeholder="Enter Data" cssClass="textField"/>
       </div>
       </div>        
	   </div>
	   
	   <s:hidden id="dateDiff" value="0"/>
	   <div id="reminderBlock" style="display: block">
	   <s:iterator value="complianceCalender" begin="1" end="1" status="status">
	   <div class="newColumn">
	   <div class="leftColumn">Reminder&nbsp;3:&nbsp;</div>
	   <div class="rightColumn">
       		<sj:datepicker id="%{field_value}" name="%{field_value}" cssClass="textField" size="20" onclick="getDocu('configCompliance1');" changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Date"/>
	   </div>
	   </div>
  	   </s:iterator>
  	   
  	   <div id="configCompliance1" style="display: none;">
  	   <s:iterator value="complianceCalender" begin="2" end="2" status="status">
  	   <s:if test="%{mandatory}">
       </s:if>
	   <s:if test="#status.odd">
       <div class="newColumn">
       <div class="leftColumn"><s:property value="%{field_name}"/>:&nbsp;</div>
       <div class="rightColumn">
       		<sj:datepicker id="%{field_value}" name="%{field_value}" cssClass="textField" size="20" changeMonth="true" onclick="getDocu('configCompliance2');" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Date"/>
	   </div>
	   </div>
	   </s:if>
	   </s:iterator>
       </div> 
        <div id="configCompliance2" style="display: none;">
  	   <s:iterator value="complianceCalender" begin="3" end="3" status="status">
  	   <s:if test="%{mandatory}">
       </s:if>
	  	   <div class="newColumn">
	       <div class="leftColumn">Reminder&nbsp;1:&nbsp;</div>
	       <div class="rightColumn">
	       <sj:datepicker id="%{field_value}" name="%{field_value}" cssClass="textField" size="20" changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Date"/>
	       </div>
	       </div>
       </s:iterator>
       </div> 
       </div> 
		
       <s:iterator value="complianceFile" begin="0" end="0">
  	   <s:if test="%{mandatory}">
       </s:if>
       <div class="newColumn">
       <div class="leftColumn"><s:property value="%{field_name}"/>:&nbsp;</div>
       <div class="rightColumn">
	   <s:if test="%{mandatory}"><span class="needed"></span></s:if>
       <s:file name="%{field_value}" id="%{field_value}" onchange="getDocu();"  placeholder="Enter Data"  cssClass="textField"/>
       </div>
       </div>
       </s:iterator> 
        
       <div id="file" style="display:none;">
       <s:iterator value="complianceFile" begin="1">
	   <div class="newColumn">
       <div class="leftColumn"><s:property value="%{field_name}"/>:&nbsp;</div>
       <div class="rightColumn">
       <s:file name="%{field_value}" id="%{field_value}" maxlength="50"  placeholder="Enter Data" cssClass="textField"/>
       </div>
       </div>
  	   </s:iterator>  
  	   </div>  


       <s:iterator value="complianceRadio">
       <s:if test="%{mandatory}">
       </s:if>
       <span id="complSpan" class="pIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
       <span id="remind2Other" class="rem2otherpIds" style="display: none; ">emp_id#Employee#D#,</span>
       <span id="remind2Both" class="rem2bothpIds" style="display: none; ">emp_id1#Employee#D#,</span>
       <span id="remind2OtherNew" class="rem2othernewpIds" style="display: none; ">emp_id#Employee#D#,</span>
       <span id="remind2BothNew" class="rem2bothnewpIds" style="display: none; ">emp_id1#Employee#D#,</span>
   
       <s:if test="%{field_value == 'remindMode'}">
       <div class="newColumn">
       <div class="leftColumn"><s:property value="%{field_name}"/>&nbsp;:</div>
       <div class="rightColumn">
       <s:if test="%{mandatory}"><span class="needed"></span></s:if>
       <s:radio list="viaFrom" name="%{field_value}" id="%{field_value}" value="%{'Both'}" onclick="findHiddenDiv(this.value);" />
       </div>
       </div>
	   </s:if>
       </s:iterator>
       
       <s:iterator value="complianceRadio">
       <s:if test="%{mandatory}">
       </s:if>
       <span id="complSpan" class="pIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
       <span id="remind2Other" class="rem2otherpIds" style="display: none; ">emp_id#Employee#D#,</span>
       <span id="remind2Both" class="rem2bothpIds" style="display: none; ">emp_id1#Employee#D#,</span>
       <span id="remind2OtherNew" class="rem2othernewpIds" style="display: none; ">emp_id#Employee#D#,</span>
       <span id="remind2BothNew" class="rem2bothnewpIds" style="display: none; ">emp_id1#Employee#D#,</span>
     
        <s:if test="%{field_value == 'reminder_for'}">
       <div class="newColumn">
       <div class="leftColumn"><s:property value="%{field_name}"/>&nbsp;:</div>
       <div class="rightColumn">
       <s:if test="%{mandatory}"><span class="needed"></span></s:if>
       <s:radio list="remindToMap" name="%{field_value}" id="%{field_value}"  value="%{'remToSelf'}" onclick="findHiddenDiv(this.value);"/>&nbsp;&nbsp;&nbsp;
       </div>
       </div>
	   </s:if>
       </s:iterator>
       
       <div id="ownerShip" style="display:none;">
       <s:iterator value="complianceRadio">
       <s:if test="%{mandatory}">
       </s:if>
       <span id="complSpan" class="pIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
       <span id="remind2Other" class="rem2otherpIds" style="display: none; ">emp_id#Employee#D#,</span>
       <span id="remind2Both" class="rem2bothpIds" style="display: none; ">emp_id1#Employee#D#,</span>
       <span id="remind2OtherNew" class="rem2othernewpIds" style="display: none; ">emp_id#Employee#D#,</span>
       <span id="remind2BothNew" class="rem2bothnewpIds" style="display: none; ">emp_id1#Employee#D#,</span>
      
       <s:if test="%{field_value == 'action_type'}">
       <div class="newColumn">
       <div class="leftColumn"><s:property value="%{field_name}"/>:&nbsp;</div>
       <div class="rightColumn">
       <s:if test="%{mandatory}"><span class="needed"></span></s:if>
       <s:radio list="actionType" name="%{field_value}" id="%{field_value}" value="%{'individual'}"/>
       </div>
       </div>
	   </s:if>
       </s:iterator>
       </div>

       <div id="remToOther" style="display: none">
	   <div class="newColumn">
       <div class="leftColumn">Employee&nbsp;:</div>
       <div class="rightColumn">
       <span class="needed"></span>
	   <s:select  
                              	id			="emp_id"
                              	name		="emp_id"
                              	list		="emplMap"
                              	headerKey	="-1"
                              	headerValue ="Select Employee" 
                              	multiple	="true"
                              	cssClass	="select"
								cssStyle	="width:82%;height:40%"
                          />
  		</div>
      	</div>
      	</div>
      	
        <div id="remToBoth" style="display:none">
      	<div class="newColumn">
        <div class="leftColumn">Employee&nbsp;:</div>
        <div class="rightColumn">
        <span class="needed"></span>
		<s:select  
                              	id			="emp_id1"
                              	name		="emp_id"
                              	list		="emplMap"
                              	headerKey	="-1"
                              	headerValue ="Select Employee" 
                              	multiple	="true"
                              	cssClass	="select"
								cssStyle	="width:82%;height:40%"
                          />
  		</div>
        </div>
  		</div>
  		
  		  <s:iterator value="complianceCheckBox">
	        <div class="newColumn">
		       <div class="leftColumn"><s:property value="%{field_name}"/>:&nbsp;</div>
		       <div style="display: block;" id="self">
			       <div style="margin-left: -4px; margin-top: 11px;">Self:</div>
			       <div class="rightColumn" style="margin-left: 348px; margin-top: -37px;">
				       <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				       Mail <s:checkbox name="selfmail" id="mail" />
				       SMS <s:checkbox name="selfsms" id="sms"/>
			       </div>
		       </div>
		       <div style="display: none;" id="other">
			       <div class="leftColumn" style="margin-left: 33px; margin-top: -12px;">Other:</div>
			    	 <div class="rightColumn" style="margin-left: 348px; margin-top: -37px;">
				       <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				       Mail <s:checkbox name="othermail" id="mail" />
				       SMS <s:checkbox name="othersms" id="sms"/>
			       </div>
		       </div>
	       </div>
       </s:iterator>
       
        <s:iterator value="complianceDropDown" begin="3" status="status">
        <s:if test="%{mandatory}">
		<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
		<span id="remind2Both" class="rem2bothpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
		<span id="remind2Other" class="rem2otherpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
		<span id="newcomplSpan" class="newpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>        
		<span id="remind2OtherNew" class="rem2othernewpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
		<span id="remind2Other" class="rem2otherpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
		<span id="remind2Both" class="rem2bothpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
        <span id="remind2BothNew" class="rem2bothnewpIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
		
        </s:if>
  		<s:if test="#status.odd">
        <div class="newColumn">
        <div class="leftColumn"><s:property value="%{field_name}"/>&nbsp;:</div>
        <div class="rightColumn">
        <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	    <s:select  
	                        id			= "escalation"
	                        name		= "escalation"
	                        list		= "escalationMap"
	                        headerKey	= "-1"
	                        headerValue = "Escalation" 
                            onchange	= "findHiddenDiv(this.value);"
                            cssClass	="select"
							cssStyle	="width:82%"
	                           >
	    </s:select>
	    </div>
	    </div>
  	    </s:if>
  	    <div id="escalationDIV" style="display:none">
  	    <div class="clear"></div>
  		<div class="newColumn">
        <div class="leftColumn">L 2:&nbsp;:</div>
        <div class="rightColumn"><span class="needed"></span>
						<s:select  
                              	id			="escalation_level_1"
                              	name		="escalation_level_1"
                              	list		="escL1Emp"
                              	cssClass	="select"
								cssStyle	="width:82%;height:40%"
                              	multiple	="true"
                              	onchange="getNextEscMap('escalation_level_1','escalation_level_2','escalation_level_3','escalation_level_2');"
                          />
          </div>
      	  </div>
      	  <div class="newColumn">
          <div class="leftColumn">L 2 TAT&nbsp;:</div>
          <div class="rightColumn"><span class="needed"></span>
		  <sj:datepicker id="l1EscDuration" name="l1EscDuration" readonly="true" onchange="changeEnable('l2EscDuration','escalation_level_2');getDocu('escalationDIV1');" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
          </div>
          </div>
          </div>
          
          <div id="escalationDIV1" style="display:none">
     	  <div class="clear"></div>
          <div class="newColumn">
          <div class="leftColumn">L 3:&nbsp;:</div>
          <div class="rightColumn">
          <div id="l2">
						<s:select  
                              	id			="escalation_level_2"
                              	name		="escalation_level_2"
                              	list		="emplMap"
								cssClass	="select"
								cssStyle	="width:82%;height:40%"
                              	multiple	="true"
                              	disabled	="true"
                              onchange="getNextEscMap('escalation_level_1','escalation_level_2','escalation_level_3','escalation_level_3');"
                              	
                          />
          </div>
      	  </div>
      	  </div>
          <div class="newColumn">
          <div class="leftColumn">L 3 TAT&nbsp;:</div>
          <div class="rightColumn">
          <sj:datepicker id="l2EscDuration" name="l2EscDuration" disabled="true" readonly="true" onchange="changeEnable('l3EscDuration','escalation_level_3');getDocu('escalationDIV2');" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
          </div>
          </div>
          </div>
     
     	  <div id="escalationDIV2" style="display:none">
     	  <div class="clear"></div>
     	  <div class="newColumn">
          <div class="leftColumn">L 4:&nbsp;:</div>
          <div class="rightColumn">
          <div id="l3">
						<s:select  
                              	id			="escalation_level_3"
                              	name		="escalation_level_3"
                              	list		="emplMap"
                              	cssClass	="select"
								cssStyle	="width:82%;height:40%"
                              	multiple	="true"
                              	disabled	="true"
                              	onchange="getNextEscMap('escalation_level_1','escalation_level_2','escalation_level_3','escalation_level_4');"
                              	
                          />
          </div>
          </div>
          </div>
          <div class="newColumn">
          <div class="leftColumn">L 4 TAT&nbsp;:</div>
          <div class="rightColumn">
          <sj:datepicker id="l3EscDuration" name="l3EscDuration" disabled="true" readonly="true" onchange="changeEnable('l4EscDuration','escalation_level_4');getDocu('escalationDIV3');" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
          </div>
          </div>
          </div>
        
          <div id="escalationDIV3" style="display:none">
          <div class="clear"></div>
          <div class="newColumn">
          <div class="leftColumn">L 5:&nbsp;:</div>
          <div class="rightColumn">
          <div id="l4">
						<s:select  
                              	id			="escalation_level_4"
                              	name		="escalation_level_4"
                              	list		="emplMap"
                              	cssClass	="select"
								cssStyle	="width:82%;height:40%"
                              	multiple	="true"
                              	disabled="true"
                              	
                          />
          </div>
          </div>
          </div>
          <div class="newColumn">
          <div class="leftColumn">L 5 TAT&nbsp;:</div>
          <div class="rightColumn">
          <sj:datepicker id="l4EscDuration" name="l4EscDuration" disabled="true" readonly="true" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssClass="textField"/>
          </div>
          </div>
          </div>
  	      </s:iterator>
  	      
  	    
         <div class="clear"></div>
         <div class="clear"></div>
         <br>
         <div class="fields">
         <center>
		 <ul>
		 <li class="submit" style="background:none;">
		 <div class="type-button">
	     <sj:submit 
         				targets			=	"complTarget" 
         				clearForm		=	"true"
         				value			=	" Save " 
         				button			=	"true"
         				cssClass		=	"submit"
                 		effect			=	"highlight"
                 		effectOptions	=	"{ color : '#222222'}"
                 		effectDuration	=	"5000"
                 		onCompleteTopics=	"makeEffect"
                 		onBeforeTopics  =   "excelConfigValidate"
                        
     		  	  />
     		  	   <sj:a 
						button="true" href="#"
						onclick="resetCompl('configCompExcelData');"
						>
						Reset
					</sj:a>
					 <sj:a 
						button="true" href="#"
						onclick="viewCompl();"
						>
						View
					</sj:a>
	      </div>
	      </li>
		  </ul></center>
		  <sj:div id="complTarget"  effect="fold"> </sj:div>
		  </div>
          <center>
	 	  <img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/>
	      </center> 
          </s:form>
--><sj:dialog
                  id="AssetBasicDialog"
                  autoOpen="false"
                  closeOnEscape="true"
                  modal="true"
                  title="Compliance Detail Upload Via Excel"
                  width="600"
                  height="350"
                  showEffect="slide"
                  hideEffect="explode">
                  <sj:div id="foldeffectExcel" effect="fold">
                     <div id="saveExcel"></div>
                  </sj:div>
                  <div id="createAssetBasic"></div>
        	</sj:dialog>
</div>
</div>
</body>
</html>
