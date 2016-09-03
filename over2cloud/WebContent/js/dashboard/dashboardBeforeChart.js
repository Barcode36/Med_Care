var maxDivId1='1stColumnBar';
var maxDivId2='2ndColumn2Axes';
var maxDivId3='PieCateg';
var status1='total';
var title1='All';
var color1='#09A1A4';
var status2='total';
var title2='All';
var color2='#0CD172';



function showData(id,maxdiv)
{
	
	
	if(maxdiv!='')
	{
		$("#maximizeDataDiv").html("<center><br><br><br><br><img src=images/ajax-loaderNew.gif></center>");
	}else{
		$("#"+id).html("<center><br><br><br><br><img src=images/ajax-loaderNew.gif></center>");
	}
		
	//alert(filterFlag);
	if(id=='jqxChart')
	{
		$(".statusPanel1").hide();
		$(".statusPanelPie").hide();
		maxDivId1='1stTable';
		/*var url1='';
		var deptId=$("#deptname").val();
		if(deptId=='-1')
		{
			url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/dataCounterStatus.action?dashFor=Status&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			
		}else
		{*/
			url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/dataCounterStatus.action?data4=table&dashFor=Status&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&filterFlag="+filterFlag+"&filterDeptId="+filterDeptId;
		//}
		
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
	});}else if(id=='levelChart')
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
			url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/dataCounterStatus.action?data4=table&dashFor=level&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val();
			
		}else
		{
			url1="view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/dataCounterStatus.action?data4=table&dashFor=level&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&filterFlag=H"+"&filterDeptId="+deptId;
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
		 var dept=$("#deptnameCateg").val();
		 var dashFor='M';
		 if(dept!=-1){
			 dashFor='H';
		 }
		 if(maxdiv!='')
			{
				$("#maximizeDataDiv").html("<center><br><br><br><br><img src=images/ajax-loaderNew.gif></center>");
			}else{
				$("#"+id).html("<center><br><br><br><br><img src=images/ajax-loaderNew.gif></center>");
			}
			$.ajax( {
				type :"post",
				url : "view/Over2Cloud/HelpDeskOver2Cloud/Dashboard_Pages/dataCounterStatus.action?dashFor=categ&filterFlag="+dashFor+"&fromDate="+$('#sdate').val()+"&toDate="+$('#edate').val()+"&filterDeptId="+dept,
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


