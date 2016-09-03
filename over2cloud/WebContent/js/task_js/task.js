
function createTask()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	//$("#data_part").load("/view/Over2Cloud/DAROver2Cloud/beforeAddAction.action");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/DAROver2Cloud/beforeAddAction.action",
	    success : function(data) {
       $("#"+"data_part").html(data);
	},
	  error: function() {
          alert("error");
      }
	 });
}
function createTaskAllot()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	//$("#data_part").load("/view/Over2Cloud/DAROver2Cloud/beforeAllotTask.action");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/DAROver2Cloud/beforeAllotTask.action",
	    success : function(data) {
       $("#"+"data_part").html(data);
	},
	  error: function() {
          alert("error");
      }
	 });
}

function previousNextDayData(projectID,id,mode)
{
	var monthCount=0;
	if (mode=='forward') 
	{
		monthCount=1;
	} 
	else if(mode=='backward')
	{
		monthCount=-1;
	}
	
	var projId=$("#"+projectID).val();
	getClientData(projId,id,monthCount);
}
function getProjectDetails(projectId)
{
	$.getJSON("view/Over2Cloud/DAROver2Cloud/getProjectData.action?projectId="+projectId,
		      function(data){
		            if(data.technicalDate == null){$("#technical_Date").val("No Technical Review Required");}
		            else {$("#technical_Date").val(data.technicalDate);}
		            
		            if(data.functonalDate == null){$("#functional_Date").val("No Functional Review Required");}
		            else {$("#functional_Date").val(data.functonalDate); }
		          
		            if(data.initiondate == null){$("#intiation").val("NA");}
		            else {$("#intiation").val(data.initiondate); }
		            
		            if(data.comlpetiondate == null){$("#completion").val("NA");}
		            else {$("#completion").val(data.comlpetiondate);}
	});
}
function getClientData(selectID,id,monthCount)
{
	var currentMonth="0";
   if (id=='1') 
   {
	   currentMonth=$("#dateVal").val();
   }
  
    $.getJSON("view/Over2Cloud/DAROver2Cloud/checkClient.action?clientOf="+selectID+"&id="+id+"&monthCounter="+monthCount+"&currentDate="+currentMonth,
      function(data)
      {
        if (monthCount=='0') 
        {
        	if(data.initiondate == null){$("#intiation").val("NA");}
            else {$("#intiation").val(data.initiondate); }
          
            if(data.comlpetiondate == null){$("#completion").val("NA");}
            else {$("#completion").val(data.comlpetiondate);}
              
        	if(data.specificTask == null){$("#specifictask").val("NA");}
            else {$("#specifictask").val(data.specificTask);}
            
            if(data.taskType == null){$("#tasktype").val("NA");}
            else {$("#tasktype").val(data.taskType); }
          
            if(data.priority == null){$("#priority").val("NA");}
            else {$("#priority").val(data.priority); }
            
            if(data.clientFor == null){$("#clientfor").val("NA");}
            else {$("#clientfor").val(data.clientFor);}
            
            if(data.clientName == null){$("#cName").val("NA");}
            else {$("#cName").val(data.clientName);}
            
            if(data.offeringName == null){$("#offering").val("NA");}
            else {$("#offering").val(data.offeringName); }
            
            if(data.guidancee == null){$("#guidance").val("NA");}
            else {$("#guidance").val(data.guidancee); }
          
            if(data.taskAttach == null){$("#attachment").val("NA");}
            else {$("#attachment").val(data.taskAttach); }
		}
        $("#dateDiv").html(data.currentDate);
        $("#dateVal").val(data.currentDate);
        if(data.today == null){$("#tactionStatus").val("NA");}
        else {$("#tactionStatus").val(data.today);}
        
        if(data.tommorow == null){$("#tommorow").val("NA");}
        else {$("#tommorow").val(data.tommorow);}
        
        if(data.compercentage == null){$("#compercentage").val("NA");}
        else {$("#compercentage").val(data.compercentage);}
        
        if(data.darAttach == null){$("#attachmentt").val("NA");}
        else {$("#attachmentt").val(data.darAttach); }
      
        if(data.manHour == null){$("#manhour").val("NA");}
        else {$("#manhour").val(data.manHour); }
        
        if(data.cost == null){$("#cost").val("NA");}
        else {$("#cost").val(data.cost);}
        
        if(data.otherworkk == null){$("#otherWork").val("NA");}
        else {$("#otherWork").val(data.otherworkk);}
        
        if(data.challenges == null){$("#challenges").val("NA");}
        else {$("#challenges").val(data.challenges); }
			
       });
 
}
function getSnoozeDetails(status,divId)
{
	if (status=='Snooze') 
	{
		$("#"+divId).show();
	}
	else 
	{
		$("#"+divId).hide();
	}
}
function getAllotedDetails(allotedToId,allotedBy,val1,val2)
{
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/DAROver2Cloud/getAllotedByData.action?allotedById="+allotedToId,
	    success : function(data) {
	    	$('#'+allotedBy+' option').remove();
			$('#'+allotedBy).append($("<option></option>").attr("value",-1).text('Select Alloted By'));
			$('#'+val1+' option').remove();
			$('#'+val1).append($("<option></option>").attr("value",-1).text('Select Functional Review'));
			$('#'+val2+' option').remove();
			$('#'+val2).append($("<option></option>").attr("value",-1).text('Select Technical Review'));
			$('#'+val1).append($("<option></option>").attr("value","No Functional Review Required").text('No Functional Review Required'));
			$('#'+val2).append($("<option></option>").attr("value","No Technical Review Required").text('No Technical Review Required'));
			$(data).each(function(index)
			{
			   $('#'+allotedBy).append($("<option></option>").attr("value",this.ID).text(this.NAME));
			   $('#'+val1).append($("<option></option>").attr("value",this.ID).text(this.NAME));
			   $('#'+val2).append($("<option></option>").attr("value",this.ID).text(this.NAME));
			});
	},
	   error: function() {
	        alert("error");
	    }
	 });
}
function getClientValues(clientValue,divId)
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/DAROver2Cloud/getClientValueData.action?clientValue="+clientValue,
	    success : function(data) {
	    	$('#'+divId+' option').remove();
			$('#'+divId).append($("<option></option>").attr("value",-1).text('Select Name'));
			
			$(data).each(function(index)
			{
			   $('#'+divId).append($("<option></option>").attr("value",this.ID).text(this.NAME));
			});
	},
	   error: function() {
	        alert("error");
	    }
	 });
}
function getOfferingDetails(nameid,clientFor,divId)
{
	var clientValue=$("#"+clientFor).val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/DAROver2Cloud/getOfferingData.action?clientValue="+clientValue+"&contactName="+nameid,
	    success : function(data) {
	    	$('#'+divId+' option').remove();
			$('#'+divId).append($("<option></option>").attr("value",-1).text('Select Offering'));
			
			$(data).each(function(index)
			{
			   $('#'+divId).append($("<option></option>").attr("value",this.ID).text(this.NAME));
			});
	},
	   error: function() {
	        alert("error");
	    }
	 });
}
function getDashData(selectID)
{
    if(selectID='tasknameee')
    {
    	var selectID1=document.getElementById(selectID).value;
    	$.getJSON("view/Over2Cloud/DAROver2Cloud/checkDash.action?taskIdForDash="+selectID1,
        function(data)
        {
            if(data.allotedbyy == null){$("#allotedbyy").val("NA");}
            else {$("#allotedbyy").val(data.allotedbyy);}
            
            if(data.allotedtoo == null){$("#allotedtoo").val("NA");}
            else {$("#allotedtoo").val(data.allotedtoo); }
            
            if(data.guidancee == null){$("#guidancee").val("NA");}
            else {$("#guidancee").val(data.guidancee);}
            if(data.initiondate == null){$("#initiondate").val("NA");}
            else {$("#initiondate").val(data.initiondate); }
            
            if(data.comlpetiondate == null){$("#comlpetiondate").val("NA");}
            else {$("#comlpetiondate").val(data.comlpetiondate);}
            
    	});
    }
}
function getValidationData(selectID)
{
    	$.getJSON("view/Over2Cloud/DAROver2Cloud/validationDAR.action?idtask="+selectID,
        function(data)
        {
	        if(data.allotedbyy == null){$("#allotedbyy1").val("NA");}
	        else {$("#allotedbyy1").val(data.allotedbyy);}
	        
	        if(data.allotedtoo == null){$("#allotedtoo1").val("NA");}
	        else {$("#allotedtoo1").val(data.allotedtoo); }
	        
	        if(data.guidancee == null){$("#guidancee1").val("NA");}
	        else {$("#guidancee1").val(data.guidancee);}
	        
	        if(data.initiondate == null){$("#initiondate1").val("NA");}
	        else {$("#initiondate1").val(data.initiondate); }
	        
	        if(data.comlpetiondate == null){$("#comlpetiondate1").val("NA");}
	        else {$("#comlpetiondate1").val(data.comlpetiondate);}
	        
	        if(data.statuss == null){$("#statuss1").val("NA");}
	        else {$("#statuss1").val(data.statuss);}
	        
	        if(data.otherworkk == null){$("#otherworkk1").val("NA");}
	        else {$("#otherworkk1").val(data.otherworkk);}
	        
	        if(data.compercentage == null){$("#compercentage1").val("NA");}
	        else {$("#compercentage1").val(data.compercentage);}
	        
	        if(data.actiontaken == null){$("#actiontaken1").val("NA");}
	        else {$("#actiontaken1").val(data.actiontaken);}
	        
	        if(data.tommorow == null){$("#tommorow1").val("NA");}
	        else {$("#tommorow1").val(data.tommorow);}
	        
	        if(data.today == null){$("#today1").val("NA");}
	        else {$("#today1").val(data.today);}
	  });
}

function getValueOfTaskForGraph(selectID)
{
    $.ajax({
    type : "post",
    url : "view/Over2Cloud/DAROver2Cloud/checkGraph.action?graphOf="+selectID,
    success : function(graphData) 
    {
	 $("#"+"graphDiv").html(graphData);
    },
   error: function() {
        alert("error");
    }
 }); 
}   
function getPendingTask(accordianId)
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
    type : "post",
    url : "view/Over2Cloud/DAROver2Cloud/checkPendingDetail.action",
    success : function(clientdata) {
	       $("#"+"data_part").html(clientdata);
    },
   error: function() {
        alert("error");
    }
 }); 
}

function getCurrentColumn(downloadType,moduleName,dialogId,dataDiv,abc,cbz,ijk)
{
		$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/DAROver2Cloud/currentColumn.action?downloadType="+downloadType+"&download4="+moduleName+"&idproduct="+abc+"&idtodate="+cbz+"&idto="+ijk,
	    success : function(data) {
		$("#"+dataDiv).html(data);
		$("#"+dialogId).dialog('open');
	},
	   error: function() {
	        alert("error");
	    }
	 });
}
function viewTask() 
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/DAROver2Cloud/taskRegistrationView.action?viewFlag=1",
	    success : function(clientdata) {
		       $("#"+"data_part").html(clientdata);
	    },
	   error: function() {
	        alert("error");
	    }
	 }); 
}
function viewDar() 
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/DAROver2Cloud/darView.action?viewFlag=1",
	    success : function(clientdata) {
		       $("#"+"data_part").html(clientdata);
	    },
	   error: function() {
	        alert("error");
	    }
	 }); 
}
function disableFields(value,divId)
{
	if (value=='No Technical Review Required' ) 
	{
		$("#"+divId).attr("disabled", true);
		$("#"+divId).val('');
	}
	else if(value=='No Functional Review Required')
	{
		$("#"+divId).attr("disabled", true);
		$("#"+divId).val('');
	}
	else
	{
		$("#"+divId).attr("disabled", false);
	}
}

function getContactPerson(clientId,clientFor,divId1,divId2)
{
     var clientfor=$("#"+clientFor).val();
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/DAROver2Cloud/getClientContactPerson.action?clientValue="+clientfor+"&contactName="+clientId,
	    success : function(data) {
	    	$('#'+divId1+' option').remove();
			$('#'+divId1).append($("<option></option>").attr("value",-1).text('Select To Contact Person'));
			$(data).each(function(index)
			{
			   $('#'+divId1).append($("<option></option>").attr("value",this.ID).text(this.NAME));
			});
	},
	   error: function() {
	        alert("error");
	    }
	 });
	if (divId2=='ccContactPerson1')
	{
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/DAROver2Cloud/getClientContactPersonOnChange.action?clientValue="+clientfor+"&contactName="+clientId,
		    success : function(data) 
		    {
		          $("#ccPersonDiv").html(data);
		    },
		   error: function() {
		        alert("error");
		    }
		 });
	}
}
function getccContactPerson(toPerson,clientFor,clientId,divId)
{
	var clientID=$("#"+clientId).val();
	   var clientfor=$("#"+clientFor).val();
		
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/DAROver2Cloud/getClientContactPersonCC.action?clientValue="+clientfor+"&contactName="+clientID+"&id="+toPerson,
		    success : function(data)
		    {
		    	  $("#ccPersonDiv").html(data);
		    },
		   error: function() {
		        alert("error");
		    }
		 });
}
/*function getCurrentColumn(downloadType,moduleName,dialogId,dataDiv)
{
	
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/DAROver2Cloud/currentColumn.action?downloadType="+downloadType+"&download4="+moduleName,
	    success : function(data) {
		$("#"+dataDiv).html(data);
		$("#"+dialogId).dialog('open');
	},
	   error: function() {
	        alert("error");
	    }
	 });
}*/
$.subscribe('validateTask', function(event,data)
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
            if(!(/^[a-zA-Z]+$/.test($("#"+fieldsvalues).val()))){
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
            else if(colType=="F"){
	            if($("#"+fieldsvalues).val()=="")
	            {
	            errZone.innerHTML="<div class='user_form_inputError2'>Please Select File "+fieldsnames+" Value  </div>";
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

$.subscribe('darvalidate', function(event,data)
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
        abc.innerHTML="";
        if(fieldsvalues!= "" )
        {
            if(colType=="D"){
            if($("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
            {
            	abc.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
            setTimeout(function(){ $("#abc").fadeIn(); }, 10);
            setTimeout(function(){ $("#abc").fadeOut(); }, 2000);
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
            if(!(numeric.test($("#"+fieldsvalues).val()))){
            	abc.innerHTML="<div class='user_form_inputError2'>Please Enter Numeric Values of "+fieldsnames+"</div>";
            setTimeout(function(){ $("#abc").fadeIn(); }, 10);
            setTimeout(function(){ $("#abc").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;
              }    
             }
            else if(validationType=="an"){
            	var allphanumeric = /^[A-Za-z0-9 ]{1,50}$/;
            	if(!(allphanumeric.test($("#"+fieldsvalues).val())))
            	{
            		abc.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
            	setTimeout(function(){ $("#abc").fadeIn(); }, 10);
            	setTimeout(function(){ $("#abc").fadeOut(); }, 2000);
            	$("#"+fieldsvalues).focus();
            	$("#"+fieldsvalues).css("background-color","#ff701a");
            	event.originalEvent.options.submit = false;
            	break;
            	}
            	}
            else if(validationType=="a"){
            	if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val())) || $("#"+fieldsvalues).val()=='NA')
            	{
            		abc.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
            	setTimeout(function(){ $("#abc").fadeIn(); }, 10);
            	setTimeout(function(){ $("#abc").fadeOut(); }, 2000);
            	$("#"+fieldsvalues).focus();
            	$("#"+fieldsvalues).css("background-color","#ff701a");
            	event.originalEvent.options.submit = false;
            	break;    
            	}
            	}
            else if(validationType=="m"){
           if($("#"+fieldsvalues).val().length > 10 || $("#"+fieldsvalues).val().length < 10)
            {
        	   abc.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                $("#"+fieldsvalues).focus();
                setTimeout(function(){ $("#abc").fadeIn(); }, 10);
                setTimeout(function(){ $("#abc").fadeOut(); }, 2000);
                event.originalEvent.options.submit = false;
                break;
            }
            else if (!pattern.test($("#"+fieldsvalues).val())) {
            	abc.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                $("#"+fieldsvalues).focus();
                setTimeout(function(){ $("#abc").fadeIn(); }, 10);
                setTimeout(function(){ $("#abc").fadeOut(); }, 2000);
                event.originalEvent.options.submit = false;
                break;
             }
                else if(($("#"+fieldsvalues).val().charAt(0)!="9") && ($("#"+fieldsvalues).val().charAt(0)!="8") && ($("#"+fieldsvalues).val().charAt(0)!="7") && ($("#"+fieldsvalues).val().charAt(0)!="6"))
             {
                	abc.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                $("#"+fieldsvalues).focus();
                setTimeout(function(){ $("#abc").fadeIn(); }, 10);
                setTimeout(function(){ $("#abc").fadeOut(); }, 2000);
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
            setTimeout(function(){ $("#abc").fadeIn(); }, 10);
            setTimeout(function(){ $("#abc").fadeOut(); }, 2000);
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
            abc.innerHTML="<div class='user_form_inputError2'>Please Select Time "+fieldsnames+" Value </div>";
            setTimeout(function(){ $("#abc").fadeIn(); }, 10);
            setTimeout(function(){ $("#abc").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;    
              }    
            }
            else if(colType=="Date"){
            if($("#"+fieldsvalues).val()=="")
            {
            abc.innerHTML="<div class='user_form_inputError2'>Please Select Date "+fieldsnames+" Value  </div>";
            setTimeout(function(){ $("#abc").fadeIn(); }, 10);
            setTimeout(function(){ $("#abc").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;    
              }
             }  
            else if(colType=="F"){
	            if($("#"+fieldsvalues).val()=="")
	            {
	            abc.innerHTML="<div class='user_form_inputError2'>Please Select File "+fieldsnames+" Value  </div>";
	            setTimeout(function(){ $("#abc").fadeIn(); }, 10);
	            setTimeout(function(){ $("#abc").fadeOut(); }, 2000);
	            $("#"+fieldsvalues).focus();
	            $("#"+fieldsvalues).css("background-color","#ff701a");
	            event.originalEvent.options.submit = false;
	            break;    
	              }
	             }  
        }
    }        
});

$.subscribe('validatedar', function(event,data)
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
            if(!(/^[a-zA-Z]+$/.test($("#"+fieldsvalues).val()))){
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
            else if(colType=="F"){
	            if($("#"+fieldsvalues).val()=="")
	            {
	            	errZone.innerHTML="<div class='user_form_inputError2'>Please Select File "+fieldsnames+" Value  </div>";
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
var indexVal=1;
function adddiv(divID)
{
  document.getElementById(divID).style.display="block";
  indexVal++;
  $("#indexVal").val(indexVal);
}
function deletediv(divID)
{
	document.getElementById(divID).style.display="none";
	indexVal--;
	$("#indexVal").val(indexVal);
}

function getProjectNames(value,divId)
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/DAROver2Cloud/getProjectNames.action?allotedTo="+value,
	    success : function(data) {
	    	$('#'+divId+' option').remove();
			$('#'+divId).append($("<option></option>").attr("value",-1).text('Select Project Name'));
			
			$(data).each(function(index)
			{
			   $('#'+divId).append($("<option></option>").attr("value",this.ID).text(this.NAME));
			});
	},
	   error: function() {
	        alert("error");
	    }
	 });
}
function beforeDownload(attach)
{
	var attachment=$("#"+attach).val();
	alert(">>>>>>>>>  "+attachment);
	
	//return "<a href='/view/Over2Cloud/DAROver2Cloud/download.action?fileName="+attachment+"'></a>";
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/DAROver2Cloud/download.action?fileName="+attachment,
	    success : function(clientdata) 
	    {
		     alert("success");
	    },
	   error: function() {
	        alert("error");
	    }
	 });
	//return "<a href='<%=request.getContextPath()%>/view/Over2Cloud/DAROver2Cloud/download.action?id="+attachment+"'";
}
function getReportDataDAR()
{
	var fromDate=$("#fromDate").val();
	var toDate=$("#toDate").val();
	var allotedTo=$("#allotedTo").val();
	var clientFor=$("#clientFor").val();
	var taskType=$("#tType").val();
	var taskPriority=$("#taskPriority").val();
	var wStatus=$("#workStatus").val();
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/DAROver2Cloud/beforeReportViewData.action",
	    data: "fdate="+fromDate+"&tdate="+toDate+"&allotTo="+allotedTo+"&clientFor="+clientFor+"&taskTyp="+taskType+"&taskPriority="+taskPriority+"&statusWork="+wStatus, 
	    success : function(data) 
	    {
		    $("#resultReportDataDar").html(data);
	    },
	   error: function() {
	        alert("error");
	    }
	 });
}
function getOtherDetails(taskId,div)
{
	if (taskId==-1) 
	{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$("#data_part").load("view/Over2Cloud/DAROver2Cloud/beforeAddAction.action");
		
	} 
	else 
	{
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/DAROver2Cloud/beforeTaskDetails.action",
		    data: "taskId="+taskId, 
		    success : function(data) 
		    {
			    $("#"+div).html(data);
		    },
		   error: function() {
		        alert("error");
		    }
		 });
	}
	
}