function getAvailableDeptViaAjax(deptId, officeTypeId, comId, branchId,
		locationId) {
	var dept = $("#" + deptId).val();
	var officeType = $("#" + officeTypeId).val();
	  var comVal=$("#"+comId).val();
	document.getElementById("indicatorDept").style.display = "block";
	
	var url = "/sfa/view/admin_pages/setting_admin/deptAvailabilty.action?dept="
			+ dept + "&officeType=" + officeType;
	
	if (officeType == '1')
		
		url = url + "&branchId=" + $("#" + branchId).val();
	
	else if (officeType == '2')

		url = url + "&locationId=" + $("#" + locationId).val();
	
	else if (officeType == '3') {
		
		url = url + "&comId=" + comVal;
	}

	$.getJSON(url, function(deptdata) {
		$("#deptStatus").html(deptdata.msg);
		document.getElementById("indicatorDept").style.display = "none";
		document.getElementById("statusDept").value = deptdata.status;

	});

}

function getAvailableSubDeptViaAjax(subdeptId) {
	var subDept = $("#" + subdeptId).val();

	document.getElementById("indicator2").style.display = "block";
	$
			.getJSON(
					"/sfa/view/admin_pages/company_admin/subDeptAvailabilty.action?subDeptName="
							+ subDept,
					function(companydata) {
						$("#subDeptStatus").html(companydata.msg);
						document.getElementById("indicatorSubDept").style.display = "none";
						document.getElementById("statusSubDept").value = companydata.status;

					});

}
