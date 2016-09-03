$.subscribe('clearAllDatePicker', function(event,data)
		{
		$('#frequency').find('option:first').attr('selected', 'selected');
		$("#reminder1" ).datepicker( 'setDate', null);
		$("#reminder2" ).datepicker( 'setDate', null);
		$("#reminder3" ).datepicker( 'setDate', null);
		$("#dateDiff").val("0");
		
		$("#reminder1" ).datepicker( "option", "maxDate", 0);
		$("#reminder1" ).datepicker( "option", "minDate", 0);
		var remindDate= $("#dueDate").val();
		var frequency= $("#frequency").val();
		var minDateValue= $("#dateDiff").val();
		
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/getReminder.action?dueDate="+remindDate+"&frequency="+frequency+"&minDateValue="+minDateValue,
		    success : function(data) {
			$("#dateDiff").val(data.minDateValue);
			$("#reminder1" ).datepicker( "option", "maxDate", data.maxDate);
			$("#reminder1" ).datepicker( "option", "minDate", data.minDate);
		    	
		},
		   error: function() {
		        alert("error");
		    }
		 });
		
		});

$.subscribe('clear2ndDatePicker', function(event,data)
		{
		$("#reminder2" ).datepicker( 'setDate', null);
		$("#reminder3" ).datepicker( 'setDate', null);
		var remindDate= $("#reminder1").val();
		var frequency= $("#frequency").val();
		var minDateValue= $("#dateDiff").val();
		
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/getReminder.action?dueDate="+remindDate+"&frequency="+frequency+"&minDateValue="+minDateValue,
		    success : function(data) {
			$("#dateDiff").val(data.minDateValue);
			$("#reminder2" ).datepicker( "option", "maxDate", data.maxDate);
			$("#reminder2" ).datepicker( "option", "minDate", data.minDate);
		    	
		},
		   error: function() {
		        alert("error");
		    }
		 });
		
		});

$.subscribe('clear3rdDatePicker', function(event,data)
		{
		$("#reminder3" ).datepicker( 'setDate', null);
		var remindDate= $("#reminder2").val();
		var frequency= $("#frequency").val();
		var minDateValue= $("#dateDiff").val();
		
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/getReminder.action?dueDate="+remindDate+"&frequency="+frequency+"&minDateValue="+minDateValue,
		    success : function(data) {
			$("#dateDiff").val(data.minDateValue);
			$("#reminder3" ).datepicker( "option", "maxDate", data.maxDate);
			$("#reminder3" ).datepicker( "option", "minDate", data.minDate);
		    	
		},
		   error: function() {
		        alert("error");
		    }
		 });
		
		});

function validateReminder(dueDate,freq,dateDiv)
{
	$("#reminder1" ).datepicker( 'setDate', null);
	$("#reminder2" ).datepicker( 'setDate', null);
	$("#reminder3" ).datepicker( 'setDate', null);
	var frequency= $("#"+freq).val();
	if(frequency=="D")
	{
		$("#reminderDiv").hide();
	}
	else
	{
		$("#reminderDiv").show();
		var remindDate= $("#"+dueDate).val();
		$("#dateDiff").val(0);
		var minDateValue= $("#dateDiff").val();
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/getReminder.action?dueDate="+remindDate+"&frequency="+frequency+"&minDateValue="+minDateValue,
		    success : function(data) {
			$("#dateDiff").val(data.minDateValue);
			$("#reminder1" ).datepicker( "option", "maxDate", data.maxDate);
			$("#reminder1" ).datepicker( "option", "minDate", data.minDate);
		    	
		},
		   error: function() {
		        alert("error");
		    }
		 });
	}
	
}
//fuction for redirect to activity Page    
function activityBoard()
{
    $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : "view/Over2Cloud/Compliance/compliance_pages/beforeActivityDashboard.action",
        success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
    
}
function disableReminder(value)
{
	if(value =="D")
	{
		$("#reminder1" ).datepicker( 'setDate', null);
		$("#reminder2" ).datepicker( 'setDate', null);
		$("#reminder3" ).datepicker( 'setDate', null);
		$("#dateDiff").val("0");
	}
	else
	{
	}
}

function findHiddenDiv(complValue)
{
 	if(complValue=="remToSelf")
 	{
 		$("#emp_id option:selected").removeAttr("selected");
 		$("#showAllotToDiv").hide();
 	}
 	else if(complValue=="remToOther")
 	{
 		$("#emp_id option:selected").removeAttr("selected");
 		$("#showAllotToDiv").show();
 	}
 	else if(complValue=="remToBoth")
 	{
 		$("#emp_id option:selected").removeAttr("selected");
 		$("#showAllotToDiv").show();
 	} 	
 	else if(complValue=="Y")
 	{
 		document.getElementById("escalationDIV").style.display="block";
 	}
 	else if(complValue=="N")
 	{
 		document.getElementById("escalationDIV").style.display="none";
 	}
}

function dselectBelowEsc(esc2,esc3,esc4,esc5)
{
	var selectedContactId = 0;
	if(esc2=='l2emp_id')
	{
		$("#l3emp_id option:selected").removeAttr("selected");
		$("#l4emp_id option:selected").removeAttr("selected");
		$("#l5emp_id option:selected").removeAttr("selected");
		$("#l3EscDuration" ).datepicker( 'setDate', null);
		$("#l4EscDuration" ).datepicker( 'setDate', null);
		$("#l5EscDuration" ).datepicker( 'setDate', null);
		
		selectedContactId = $("#l2emp_id").val();
		
		$.ajax({
			type :"post",
			url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/getNextEscMap4Edit.action?selectedId="+selectedContactId,
			success : function(empData){
			$('#l3emp_id option').remove();
			$('#l4emp_id option').remove();
			$('#l5emp_id option').remove();
			
	    	$(empData).each(function(index)
			{
			   $('#l3emp_id').append($("<option></option>").attr("value",this.ID).text(this.NAME));
			   $('#l4emp_id').append($("<option></option>").attr("value",this.ID).text(this.NAME));
			   $('#l5emp_id').append($("<option></option>").attr("value",this.ID).text(this.NAME));
			});
		    },
		    error : function () {
				alert("Somthing is wrong to get Data");
			}
		});
	}
	else if(esc3=='l3emp_id')
	{
		$("#l4emp_id option:selected").removeAttr("selected");
		$("#l5emp_id option:selected").removeAttr("selected");
		$("#l4EscDuration" ).datepicker( 'setDate', null);
		$("#l5EscDuration" ).datepicker( 'setDate', null);
		selectedContactId = $("#l2emp_id").val();
		selectedContactId = selectedContactId+","+$("#l3emp_id").val();
		alert(selectedContactId);
		$.ajax({
			type :"post",
			url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/getNextEscMap4Edit.action?selectedId="+selectedContactId,
			success : function(empData){
			$('#l4emp_id option').remove();
			$('#l5emp_id option').remove();
			
	    	$(empData).each(function(index)
			{
			   $('#l4emp_id').append($("<option></option>").attr("value",this.ID).text(this.NAME));
			   $('#l5emp_id').append($("<option></option>").attr("value",this.ID).text(this.NAME));
			});
		    },
		    error : function () {
				alert("Somthing is wrong to get Data");
			}
		});
		
	}
	else if(esc4=='l3emp_id')
	{
		$("#l5emp_id option:selected").removeAttr("selected");
		$("#l5EscDuration" ).datepicker( 'setDate', null);
		selectedContactId = $("#l2emp_id").val();
		selectedContactId = selectedContactId+","+$("#l3emp_id").val()+","+$("#l4emp_id").val();
		alert(selectedContactId);
		$.ajax({
			type :"post",
			url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/getNextEscMap4Edit.action?selectedId="+selectedContactId,
			success : function(empData){
			$('#l5emp_id option').remove();
			
	    	$(empData).each(function(index)
			{
			   $('#l5emp_id').append($("<option></option>").attr("value",this.ID).text(this.NAME));
			});
		    },
		    error : function () {
				alert("Somthing is wrong to get Data");
			}
		});
	}
	else if(esc2=='' && esc3=='' && esc4=='' && esc5=='')
	{
		$("#l2emp_id option:selected").removeAttr("selected");
		$("#l3emp_id option:selected").removeAttr("selected");
		$("#l4emp_id option:selected").removeAttr("selected");
		$("#l5emp_id option:selected").removeAttr("selected");
		
		$("#l2EscDuration" ).datepicker( 'setDate', null);
		$("#l3EscDuration" ).datepicker( 'setDate', null);
		$("#l4EscDuration" ).datepicker( 'setDate', null);
		$("#l5EscDuration" ).datepicker( 'setDate', null);
		
		
		$.ajax({
			type :"post",
			url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/getNextEscMap4Edit.action?selectedId="+selectedContactId,
			success : function(empData){
			$('#l2emp_id option').remove();	
			$('#l3emp_id option').remove();
			$('#l4emp_id option').remove();
			$('#l5emp_id option').remove();
			
	    	$(empData).each(function(index)
			{
			   $('#l2emp_id').append($("<option></option>").attr("value",this.ID).text(this.NAME));
			   $('#l3emp_id').append($("<option></option>").attr("value",this.ID).text(this.NAME));
			   $('#l4emp_id').append($("<option></option>").attr("value",this.ID).text(this.NAME));
			   $('#l5emp_id').append($("<option></option>").attr("value",this.ID).text(this.NAME));
			});
		    },
		    error : function () {
				alert("Somthing is wrong to get Data");
			}
		});
	}
}

function viewCompl()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compliance_pages/logedUserComplDashboard.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function configureCompliance()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    	type : "post",
	    url : "view/Over2Cloud/Compliance/compliance_pages/createConfigurationComplView.action",
	    success : function(data) {
       $("#"+"data_part").html(data);
	},
	   error: function() {
            alert("error");
        }
	 });
}
