

function showDataLoc(divId){
	var dept=$("#deptnameLoc").val();
	if(measure1=="Location"){
	
	
			url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/dataCounterLocation.action?data4=table&filterDeptId="+dept+"&flag=location&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
		
			 $("#"+divId).html("<center><br/><br/><br/><br/><br/><br/><br/><img src=images/lightbox-ico-loading.gif></center>");
	$.ajax( {
		type :"post",
		url : url1,
		success : function(statusdata) 
		{   //console.log("statusdata");
			//console.log(statusdata);
			
				$("#"+divId).html(statusdata);
	
		
		},
		error : function() {
			alert("error");
		}
	});
	}else if(measure1=="time"){
		

		url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/dataCounterTime.action?data4=table&filterDeptId="+dept+"&flag=time&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
	
		 $("#"+divId).html("<center><br/><br/><br/><br/><br/><br/><br/><img src=images/lightbox-ico-loading.gif></center>");
$.ajax( {
	type :"post",
	url : url1,
	success : function(statusdata) 
	{   //console.log("statusdata");
		//console.log(statusdata);
		
			$("#mytest").html(statusdata);

	
	},
	error : function() {
		alert("error");
	}
});
	}
}

