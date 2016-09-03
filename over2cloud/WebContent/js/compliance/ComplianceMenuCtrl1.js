function addNewTaskType()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Compliance/compl_task_type_page/beforeComplTaskType.action",
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}

function addNewTaskName()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Compliance/compl_task_page/beforeComplTask.action",
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}
function addNewContact(moduleName)
{
	var dataFor=$("#"+moduleName).val();
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Compliance/compl_contacts_pages/beforeComplContact.action?moduleName="+dataFor,
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}
function showComplDetails(deptId,frequency,totalOrMissed,compId)
{
	$("#complianceDialog").dialog({title: 'Frequency wise Compliance Status'});
	$("#complianceDialog").dialog('open');
	$("#datadiv").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/mgmtDashboardData.action?departId="+deptId+"&frequency="+frequency+"&totalOrMissed="+totalOrMissed+"&data4=mgmtDashboard&compId="+compId,
	    success : function(data) 
	    {
			$("#datadiv").html(data);
	},
	   error: function() {
	        alert("error");
	    }
	 });
}

function showAgeingComplDetails(deptId,frequency,totalOrMissed,compId)
{
	$("#complianceDialog").dialog({title: 'Compliance Ageing'});
	$("#complianceDialog").dialog('open');
	$("#datadiv").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/mgmtDashboardData.action?departId="+deptId+"&frequency="+frequency+"&totalOrMissed="+totalOrMissed+"&data4=ageingDashboard&compId="+compId,
	    success : function(data) 
	    {
			$("#datadiv").html(data);
	},
	   error: function() {
	        alert("error");
	    }
	 });
}
function showDetailsPie(dataBlockDiv,graphBlockDiv)
{
	$("#"+graphBlockDiv).show();
	$("#"+dataBlockDiv).hide();
}

function showDetailsData(dataBlockDiv,graphBlockDiv)
{
	$("#"+dataBlockDiv).show();
	$("#"+graphBlockDiv).hide();
}
function maximizeDetails(divBlock,blockHeading)
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/getDashboardData.action?maximizeDivBlock="+divBlock,
	    success : function(data) 
	    {
			$("#maximizeDataDiv").html(data);
			$("#maxmizeViewDialog").dialog({title:blockHeading});
			$("#maxmizeViewDialog").dialog('open');
	},
	   error: function() {
	        alert("error");
	    }
	 });
}

function showAllComplDetails(deptId,status,compId)
{
	$("#complianceDialog").dialog({title: 'Compliance Status'});
	$("#complianceDialog").dialog('open');
	$("#datadiv").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/mgmtDashboardData.action?departId="+deptId+"&status="+status+"&data4=complianceStatus&compId="+compId,
	    success : function(data) 
	    {
			$("#datadiv").html(data);
	},
	   error: function() {
	        alert("error");
	    }
	 });
}
function showAllTodayCompl(deptId,status,complId)
{
	$("#complianceDialog").dialog({title: 'Compliance Due Today'});
	$("#complianceDialog").dialog('open');
	$("#datadiv").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/mgmtDashboardData.action?departId="+deptId+"&status="+status+"&compId="+complId+"&dataFor=todayData",
	    success : function(data) 
	    {
			$("#datadiv").html(data);
	},
	   error: function() {
	        alert("error");
	    }
	 });
}

function lastMonthCompDetail(deptId,status,blockHeading,complId)
{
	$("#complianceDialog").dialog({title: blockHeading});
	$("#complianceDialog").dialog('open');
	$("#datadiv").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/lastMonthCompDetail.action?deptId="+deptId+"&actionStatus="+status+"&compId="+complId,
	    success : function(data) 
	    {
			$("#datadiv").html(data);
	},
	   error: function() {
	        alert("error");
	    }
	 });
}

function addComplTemplate()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/beforeCompTemplateAdd.action",
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}

function refresh(divBlock)
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/getDashboardData.action?maximizeDivBlock="+divBlock,
	    success : function(data) 
	    {
			$("#"+divBlock).html(data);
	    },
	   error: function() {
	        alert("error");
	    }
	 });
}

function getLevelMappedEmp(level,data4)
{
	var dataFor=$("#"+data4).val();
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Compliance/compl_contacts_pages/getMappedEmp.action?mappedLevel="+level+"&moduleName="+dataFor,
	    success : function(data) 
	    {
			$('#empDD option').remove();
			$('#empDD').append($("<option></option>").attr("value",-1).text('Select Mapped Emp'));
			$(data).each(function(index)
			{
				$('#empDD').append($("<option></option>").attr("value",this.id).text(this.name));
			});
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}

function getUnMappedEmp(mappedEmp,data4,selectedLevel,cntMapping)
{
	var empName=$("#"+mappedEmp).val();
	var dataFor=$("#"+data4).val();
	var mappedLevel=$("#"+selectedLevel).val();
	
	$("#unMappedTarget").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Compliance/compl_contacts_pages/getUnMappedEmp.action?empName="+empName+"&moduleName="+dataFor+"&mappedLevel="+mappedLevel+"&mapping_sharing="+cntMapping,
	    success : function(data) 
	    {
			$("#"+"unMappedTarget").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}

function addNewSharing(dataFor)
{
	var moduleName=$("#"+dataFor).val();
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compl_contacts_pages/createContactSharing.action?moduleName="+moduleName,
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}

function mapNewContact(moduleName)
{
	var dataFor=$("#"+moduleName).val();
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Compliance/compl_contacts_pages/beforeMappedContact.action?moduleName="+dataFor,
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}
function backContact()
{
	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Compliance/compl_contacts_pages/beforeComplContactView.action?modifyFlag=0&deleteFlag=1&moduleName=COMPL",
		    success : function(data) 
		    {
				$("#"+"data_part").html(data);
		    },
		    error: function() 
		    {
	            alert("error");
	        }
		 });
	
}
function backSharingData(dataFor){
	var moduleName=$("#"+dataFor).val();
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compl_contacts_pages/viewContactSharing.action?moduleName="+moduleName,
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}

function backMappingData(dataFor)
{
	var moduleName=$("#"+dataFor).val();
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/compl_contacts_pages/viewContactMapping.action?moduleName="+moduleName,
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}
