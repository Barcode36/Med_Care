function getDeptNamesForMappedOffice(divId,headOfficeId)
{
	$.ajax({
		type :"post",
		url :"view/Over2Cloud/commonModules/getdeptForMappedOrg.action?mappedOrgId="+headOfficeId,
		success : function(empData){
		$('#'+divId+' option').remove();
		$('#'+divId).append($("<option></option>").attr("value",-1).text("Select Department"));
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


function getDesgNamesForMappedOffice(divId,deptId)
{
	$.ajax({
		type :"post",
		url :"view/Over2Cloud/commonModules/getdesgForMappedOrg.action?deptname="+deptId,
		success : function(empData){
		$('#'+divId+' option').remove();
		$('#'+divId).append($("<option></option>").attr("value",-1).text("Select Designation"));
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
