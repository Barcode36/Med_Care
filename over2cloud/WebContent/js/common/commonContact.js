function mobExists(mobileNo)
{
	var subGroupId=$("#subGroupId").val();
	 document.getElementById("indicator2").style.display="block";
	 $.getJSON("/over2cloud/view/Over2Cloud/hr/checkMobile.action?mobOne="+mobileNo+"&subGroupId="+subGroupId,
		function(empdata){
   	     $("#empStatus").html(empdata.msg);
   	     document.getElementById("indicator2").style.display="none";
   });
	
}

function getsubGroupByGroup(divId,groupId)
{
		var regLevel=$("#regLevel").val();
			$.ajax({
				type :"post",
				url :"view/Over2Cloud/hr/getSubGroupByGroupId.action?regLevel="+regLevel+"&groupId="+groupId,
				success : function(empData){
				$('#'+divId+' option').remove();
				$('#'+divId).append($("<option></option>").attr("value",-1).text("Select Contact Sub-Type"));
		    	$(empData).each(function(index)
				{
				   $('#'+divId).append($("<option></option>").attr("value",this.id).text(this.name));
				});
			    },
			    error : function () {
					alert("Somthing is wrong to get Data");
				}
			});
}

function getCurrentColumnContact(downloadType,moduleName,dialogId,dataDiv)
{
	
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/hr/currentColumnContact.action?downloadType="+downloadType+"&download4="+moduleName,
	    success : function(data) {
		$("#"+dataDiv).html(data);
		$("#"+dialogId).dialog('open');
	},
	   error: function() {
	        alert("error");
	    }
	 });
}

function getsubIndustry(divId,industryId)
{
	if(industryId!=null)
	{
		$.ajax({
			type :"post",
			url :"view/Over2Cloud/hr/getSubSubIndustry.action?industryId="+industryId,
			success : function(empData){
			$('#'+divId+' option').remove();
			$('#'+divId).append($("<option></option>").attr("value",-1).text("Select Sub-Industry"));
	    	$(empData).each(function(index)
			{
			   $('#'+divId).append($("<option></option>").attr("value",this.id).text(this.name));
			});
		    },
		    error : function () {
				alert("Somthing is wrong to get Data");
			}
		});
	}
}

function getGroupNamesForMappedOffice(divId,headOfficeId)
{
	$.ajax({
		type :"post",
		url :"view/Over2Cloud/hr/getGroupOfMappedLocation.action?regLevel="+headOfficeId,
		success : function(empData){
		$('#'+divId+' option').remove();
		$('#'+divId).append($("<option></option>").attr("value",-1).text("Select Contact Type"));
    	$(empData).each(function(index)
		{
		   $('#'+divId).append($("<option></option>").attr("value",this.id).text(this.name));
		});
	    },
	    error : function () {
			alert("Somthing is wrong to get Data");
		}
	});
}

function getContactNames(subGroupId)
{
	var regLevel=$("#regLevel").val();
	var groupId=$("#groupId").val();
	
}


function getContactFor(subGroupId)
{
	var regLevel=$("#regLevel").val();
	var groupId=$("#groupId").val();
	if(subGroupId!=null)
	{
		$("#contactDetails").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/hr/getContactForm.action?subGroupId="+subGroupId+"&regLevel="+regLevel+"&groupId="+groupId,
		    success : function(subdeptdata) {
	       $("#"+"contactDetails").html(subdeptdata);
	       document.getElementById("submitDiv").style.display="block";
		},
		   error: function() {
	            alert("error");
	            document.getElementById("submitDiv").style.display="none";
	        }
		 });
	}
}


function getContactNamesForUser(subGroupId,divId)
{
			$.ajax({
				type :"post",
				url :"view/Over2Cloud/hr/getContBySubGroupId.action",
				data:"subGroupId="+subGroupId,
				success : function(empData){
				$('#'+divId+' option').remove();
				$('#'+divId).append($("<option></option>").attr("value",-1).text("Select Contact Sub-Type"));
		    	$(empData).each(function(index)
				{
				   $('#'+divId).append($("<option></option>").attr("value",this.id).text(this.name));
				});
			    },
			    error : function () {
					alert("Somthing is wrong to get Data");
				}
			});
}

function getEmpDetails(empId)
{
	$.ajax({
		type : "post",
		url : "view/Over2Cloud/hr/getEmpDetails.action",
		data : "empId="+empId,
		success : function(data){
			$('#mobileNo').val(data.mobileNo);
			$('#name').val(data.name);
		},
		error : function(){
			alert("Error");
		}
		});
}
function cancelusermaster(){
	var conP = "<%=request.getContextPath()%>";
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/hr/beforeUserViewHeader.action?empModuleFalgForDeptSubDept=1",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });

}