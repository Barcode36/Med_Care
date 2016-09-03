var maxDivId1='1stColumnBar';
var maxDivId2='2ndColumn2Axes';
var maxDivId3='PieCateg';
var status1='Resolved';
var title1='Resolve';
var color1='#5cb85c';
var status2='Pending';
var title2='Pending';
var color2='#d9534f';
var measure1='floor';
var maxDivId4='1stTable';

function showData(id,maxdiv,val)
{
	
	
	if(maxdiv!='')
	{
		$("#maximizeDataDiv").html("<center><br><br><br><br><img src=images/ajax-loaderNew.gif></center>");
	}else{
		$("#"+id).html("<center><br><br><br><br><img src=images/ajax-loaderNew.gif></center>");
	}
		
	
	if(id=='jqxChart')
	{
		$(".statusPanel1").hide();
		$(".statusPanelPie").hide();
		maxDivId1='1stTable';
		var url1='';
		var deptId=$("#deptname").val();
		if(deptId=='-1')
		{
			url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages_Mgt/dataCounterStatus.action?data4=table&dashFor=Status&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			
		}else
		{
			url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages_Mgt/dataCounterStatus.action?data4=table&dashFor=Status&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&filterFlag=H"+"&filterDeptId="+deptId;
		}
		
	$.ajax( {
		type :"post",
		url : url1,
		success : function(statusdata) 
		{   //console.log("statusdata");
			//console.log(statusdata);
			if(maxdiv!=''){
				$("#maximizeDataDiv").html(statusdata);
			}else{
				$("#"+id).html(statusdata);
			}
		
		},
		error : function() {
			alert("error");
		}
	});
 }
	
	else if(id=='reopneChart')
	{
		var filterFlag='';
		var deptId=$("#deptnameReopen").val();
	 	if(deptId!='-1')
			filterFlag='H';
	 	maxDivId4='1stTable';
		url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages_Mgt/dataCounterStatus.action?data4=table&dashFor=Reopen&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&filterFlag="+filterFlag+"&filterDeptId="+deptId;
		 
	$.ajax( {
		type :"post",
		url : url1,
		success : function(statusdata) 
		{   
			if(maxdiv!=''){
				$("#maximizeDataDiv").html(statusdata);
			}else{
				$("#"+id).html(statusdata);
			}
		
		},
		error : function() {
			alert("error");
		}
	});
	}
	else if(id=='levelChart')
	{
	 $(".levelPanel").hide();
		$(".levelPanelPie").hide();
	 maxDivId2='2ndTable';
	 if(maxdiv!='')
		{
			$("#maximizeDataDiv").html("<center><br><br><br><br><img src=images/ajax-loaderNew.gif></center>");
		}else{
			$("#"+id).html("<center><br><br><br><br><img src=images/ajax-loaderNew.gif></center>");
		}
	 var url1='';
		var deptId=$("#deptname1").val();
		if(deptId=='-1')
		{
			url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages_Mgt/dataCounterStatus.action?data4=table&dashFor=level&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			
		}else
		{
			url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages_Mgt/dataCounterStatus.action?data4=table&dashFor=level&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&filterFlag=H"+"&filterDeptId="+deptId;
		}
	 
		$.ajax( {
			type :"post",
			url : url1,
			success : function(statusdata) 
			{
				if(maxdiv!=''){
					$("#maximizeDataDiv").html(statusdata);
				}else{
					$("#"+id).html(statusdata);
				}
			},
			error : function() {
				alert("error");
			}
		});
	 }else if(id=='CategChart')
		{
		 maxDivId3='3rdTable';
		 if(maxdiv!='')
			{
				$("#maximizeDataDiv").html("<center><br><br><br><br><img src=images/ajax-loaderNew.gif></center>");
			}else{
				$("#"+id).html("<center><br><br><br><br><img src=images/ajax-loaderNew.gif></center>");
			}
			$.ajax( {
				type :"post",
				url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages_Mgt/dataCounterStatus.action?data4=table&dashFor=categ&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val(),
				success : function(statusdata) 
				{
					if(maxdiv!=''){
						$("#maximizeDataDiv").html(statusdata);
					}else{
						$("#"+id).html(statusdata);
					}
				},
				error : function() {
					alert("error");
				}
			});
		 }
	
}//show data end 

