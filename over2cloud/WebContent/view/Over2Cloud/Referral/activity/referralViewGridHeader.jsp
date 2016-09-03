<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%
	String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();
%>
<%String validApp = session.getAttribute("validApp") == null ? "" : session.getAttribute("validApp").toString();
%>
<%
//System.out.println("userRightsList ::::" +userRights);
//Code for checking the level of departments or sub departments
String dbName=(String)session.getAttribute("Dbname");
int levelid=1;
String namesofDepts[]=new String[3];
String names=(String)session.getAttribute("deptLevel");
String tempnamesofDepts[]=null;
////deptlevel,dept1Name#dept2Name#
if(names!=null && !names.equalsIgnoreCase(""))
{
	tempnamesofDepts=names.split(",");
	levelid=Integer.parseInt(tempnamesofDepts[0]);
	namesofDepts=tempnamesofDepts[1].split("#");
}

String userTpe=(String)session.getAttribute("userTpe");

%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 
<style type="text/css">




.TitleBorder {
	float: left;
    height: 27px;
    border: 1px solid #e0bc27;
    margin: 2px 1px 0px 2px;
    background:#E5F2FF;
    box-shadow: 5px 2px 3px 0px rgba(0, 0, 0, 0.2);
    /* overflow: auto; */
    /* -webkit-transform: rotate(5deg); */
    -moz-transform: rotate(5deg);
    -ms-transform: rotate(5deg);
    transform: rotate(2deg);
    /* background-color: #000; */
    /* border-bottom-left-radius: 35%; */
	
}

.TitleBorderSer {
	float: left;
    height: 27px;
    border: 1px solid #A5E244;
    margin: 2px 1px 0px 2px;
    background: white;
    box-shadow: 5px 2px 3px 0px rgba(0, 0, 0, 0.2);
    /* overflow: auto; */
    /* -webkit-transform: rotate(5deg); */
    -moz-transform: rotate(5deg);
    -ms-transform: rotate(5deg);
    transform: rotate(2deg);
    /* background-color: #000; */
    /* border-bottom-left-radius: 35%; */
	
}

.TitleBorder h1 {
	padding-top: 0px;
	margin: 2px 5px 32px 0px;
	
}

h1 {
	font-size: 12px;
	    font-weight: bold;
}

.circle {
	width: 22px;
    height: 22px;
    border-radius: 60px;
    font-size: 12px;
    color: #fff;
    line-height: 22px;
    text-align: center;
    background: #989A8F;
    margin-left: 65px;
    margin-top: -53px;
      box-shadow: 5px 2px 3px 0px rgba(0, 0, 0, 0.2);
    
}

.circleSer {
	width: 22px;
    height: 22px;
    border-radius: 60px;
    font-size: 12px;
    color: #fff;
    line-height: 22px;
    text-align: center;
    background: #2F7BDA;
    margin-left: 65px;
    margin-top: -20px;
      box-shadow: 5px 2px 3px 0px rgba(0, 0, 0, 0.2);
    
}

.contents {
	width: 21px;
	color: white;
	background: rgb(33, 140, 255);
	box-shadow: 4px 3px 2px 2px rgb(224, 224, 209);
}

.contentsfree {
	width: 21px;
	color: white;
	background: rgb(231, 113, 92);
	box-shadow: 4px 3px 2px 2px rgb(224, 224, 209);
}

#empinfo tr:nth-child(even) {
	background: #CCC
}

#empinfo tr:nth-child(odd) {
	background: #FFF
}

#empinfo1 tr:nth-child(even) {
	background: #CCC
}

#empinfo1 tr:nth-child(odd) {
	background: #CCC
}
</style>
<script type="text/javascript">
 	var row=0;
 	var intvrl;
  	$.subscribe('rowselect', function(event, data) {
		row = event.originalEvent.id;
	});
	
  	function ChangeReferralType(value)
  	{
  		 if(value=='1')
  		 {
  			 $('#refDoc').show(); 
  			 
  		 }
  		 if(value=='2')
  		{
  			$('#refDoc').hide(); 
  	 		getStatusCounter();
  			onSearchData();
  				 
  		}
  		  
  	}
	function historyView(cellvalue, options, rowObject) 
	{
		if(rowObject.noresponse_flag=="1" && rowObject.escalate_date!=="null" && rowObject.escalate_date!=="NA")
		{
			return "<a href='#' title='View Data' onClick='viewHistoryOnClick("+rowObject.id+")'><font color=#8B008B>"+cellvalue+"</font></a>";
		}
		else{
		return "<a href='#' title='View Data' onClick='viewHistoryOnClick("+rowObject.id+")'><font color='blue'>"+cellvalue+"</font></a>";
		}
	}

	function viewHistoryOnClick(id) 
	{
		var ticketNo = jQuery("#gridedittable").jqGrid('getCell',id,'ticket_no');
		var refBy = jQuery("#gridedittable").jqGrid('getCell',id,'ref_by');
		var refBySpec = jQuery("#gridedittable").jqGrid('getCell',id,'spec');
		var refTo = jQuery("#gridedittable").jqGrid('getCell',id,'ref_to');
		var refToSpec = jQuery("#gridedittable").jqGrid('getCell',id,'ref_spec');
		var bed = jQuery("#gridedittable").jqGrid('getCell',id,'bed');
		var nu = jQuery("#gridedittable").jqGrid('getCell',id,'nursing_unit');
		var priority = jQuery("#gridedittable").jqGrid('getCell',id,'priority');
		priority=$(priority).text();
		var openDate = jQuery("#gridedittable").jqGrid('getCell',id,'open_date');
		var status = jQuery("#gridedittable").jqGrid('getCell',id,'status');
		var uhid = jQuery("#gridedittable").jqGrid('getCell',id,'uhid');
		var pName = jQuery("#gridedittable").jqGrid('getCell',id,'patient_name');
		var subSpec = jQuery("#gridedittable").jqGrid('getCell',id,'ref_to_sub_spec');
		var empId = jQuery("#gridedittable").jqGrid('getCell',id,'caller_emp_id');
		var empName = jQuery("#gridedittable").jqGrid('getCell',id,'caller_emp_name');
		$("#takeActionGrid").dialog('open');
		$("#takeActionGrid").dialog({title: 'History of '+ticketNo,width: 900,height: 600});
		$.ajax
		({
			type : "post",
			url  : "/over2cloud/view/Over2Cloud/Referral/beforeViewActivityHistoryData?id="+id+"&status="+status+"&refBy="+refBy+"&refTo="+refTo+"&nursingUnit="+nu+"&refBySpec="+refBySpec+"&refToSpec="+refToSpec+"&bed="+bed+"&openDate="+openDate+"&priority="+priority+"&uhid="+uhid+"&patientName="+pName+"&subSpec="+subSpec+"&empId="+empId+"&empName="+empName,
			success : function(data)
			{
				$("#takeActionGrid").html(data);
			},
			error : function()
			{
				alert("Error on data fetch");
			} 
		});
	}
		
	function viewDetails(cellvalue, options, rowObject) 
	{
		if(cellvalue=='Stat')
		{
			return "<h><b><font color='red'>"+cellvalue+"</font></b></h>";
		}	
		else if(cellvalue=='Urgent')
		{
			return "<h><b><font color='#CC00CC'>"+cellvalue+"</font></b></h>";
		}	
		else
		{
			return "<h><font>"+cellvalue+"</font></h>";
		}	
	}
	
	function viewLevel(cellvalue, options, rowObject) 
	{
		if(cellvalue!='Level1')
		{
			return "<font color='red'>"+cellvalue+"</font>";
		}	
		else
		{
			return "<font>"+cellvalue+"</font>";
		}	
	}

	function onSearchData(flag)
	{
		var specialty='-1';
	 var reffTo='-1';
	 var locationWise='No';
	 var nursingUnit= $("#nursingUnit").val();
		if(flag=='nursingUnit')
		{
			specialty=	$("#spec").val();
			reffTo=	$("#reffto").val();
			if(nursingUnit=='-1')
			{
				reffTo='-1';
				specialty='-1';
			}
			if(specialty!='-1')
			{
				specialty=	$("#spec").val();
				reffTo='-1';

			}
			else if(reffTo !='-1')
			{
				reffTo=	$("#reffto").val();
				specialty='-1';
			}
			else if(reffTo !='-1' && specialty=='-1')
			{
				reffTo=	$("#reffto").val();
				specialty='-1';
			}
			else if(reffTo =='-1' && specialty!='-1')
			{
				specialty=	$("#spec").val();
				reffTo='-1';
			}
			else if(reffTo !='-1' && specialty!='-1')
			{
				specialty='-1';
				reffTo='-1';
			}
				
			else
			{ 
			specialty='-1';
			reffTo='-1';
			}
			 
		}
		 
		else
		{
			specialty=	$("#spec").val();
			reffTo=	$("#reffto").val();
		}
		if( $('#locationView').is(":checked"))
		{
			locationWise='Yes';
		}
		else 
		{
			locationWise='NO';
		}
	   	$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Referral/beforeReferralView.action?fromDate="+$("#fromDate").val()+"&toDate="+$("#toDate").val()+"&status="+$("#status").val()+"&priority="+$("#priority1").val()+"&searchString="+$("#searchStr").val()+"&nursingUnit="+ nursingUnit+"&specialty="+specialty+"&level="+$("#level").val()+"&refDocTo="+reffTo+"&refDoc="+$("#refDoc").val()+"&locationWise="+locationWise,
		    success : function(data) {
	       $("#datapart").html(data);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}

//for manual entry ticket

	function onloadTicketLodge( )
	{
		$("#TicketDiv").html("<br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	
		 	$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Referral/beforeReferralLodge.action",
		    success : function(feeddraft) {
	       $("#TicketDiv").html(feeddraft);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	
	function escalateAction(cellvalue, options, rowObject) 
	{
		if(rowObject.status=="Informed" && rowObject.escalate_date=="00:00")
		{
			var context_path = '<%=request.getContextPath()%>';
			return "<a href='#'><img title='Escalate' src='"+ context_path +"/images/escalate.jpg' height='20' width='20' onClick='escalateOnClick("+rowObject.id+")'> </a>";
		}
		else
		{
			return "NA";
		}	
	}
	
	function escalateOnClick(id)
	{
		var ticketNo = jQuery("#gridedittable").jqGrid('getCell',id,'ticket_no');
		ticketNo=$(ticketNo).text();
		var level = jQuery("#gridedittable").jqGrid('getCell',id,'level');
		level=$(level).text();
		var ref_to = jQuery("#gridedittable").jqGrid('getCell',id,'ref_Id');
		var refTo = jQuery("#gridedittable").jqGrid('getCell',id,'ref_to');
		var refToSpec = jQuery("#gridedittable").jqGrid('getCell',id,'ref_spec');
		var uhid = jQuery("#gridedittable").jqGrid('getCell',id,'uhid');
		var bed = jQuery("#gridedittable").jqGrid('getCell',id,'bed');
		var nu = jQuery("#gridedittable").jqGrid('getCell',id,'nursing_unit');
		var priority = jQuery("#gridedittable").jqGrid('getCell',id,'priority');
		
		priority=$(priority).text();
	 	$("#takeActionGrid").dialog({title: 'Escalate Action for '+ticketNo,width: 600,height: 150});
		$("#takeActionGrid").html("<br><center><img src=images/ajax-loaderNew.gif></center>");
		$("#takeActionGrid").dialog('open');
		$.ajax({	
			type : "post",
			url : "/over2cloud/view/Over2Cloud/Referral/beforeEscalateAction.action?id="+id+"&level="+level+"&ref_to="+ref_to+"&refTo="+refTo+"&bed="+nu+"/"+bed+"&priority="+priority+"&ticketNo="+ticketNo+"&uhid="+uhid+"&refToSpec="+refToSpec,
			//url : "/over2cloud/view/Over2Cloud/Referral/beforeEscalateAction.action?id="+id+"&level="+level+"&refTo="+ref_to+"&bed="+bed+"/ "+bed+"/ "+uhid+"&priority="+priority+"&ticketNo="+ticketNo+"&uhid="+uhid+"&refToSpec="+refToSpec,
			success : function(data) 
			{
			    $("#takeActionGrid").html(data);
			},
			error: function() {
			    alert("error");
			}
		});
	}
	
	function takeAction(cellvalue, options, rowObject) 
	{
		var context_path = '<%=request.getContextPath()%>';
		return "<a href='#'><img title='Take Action' src='"+ context_path +"/images/action.jpg' height='20' width='20' onClick='takeActionOnClick("+rowObject.id+")'> </a>";
	}
	
	function takeActionOnClick(id) 
	{
		var status = jQuery("#gridedittable").jqGrid('getCell',id,'status');
		if(id.length==0 )
		{
	 		//alert("Please select atleast one check box...");
	 		$("#takeActionGrid").dialog({title:"Alert!!!",height:100,width:400,dialogClass:'transparent'});
			$("#takeActionGrid").html('<div class="alert alert-danger" role="alert"><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span><span class="sr-only"></span>Please select atleast one check box...</div>');
			$("#takeActionGrid").dialog('open'); 
		}
		else if(status=='Seen' || status=='OCC Ignore' || status=='Dept Ignore')
		{
			//alert("Can't Take Action on "+status+" Referral.");
			$("#takeActionGrid").dialog({title:"Alert!!!",height:100,width:400,dialogClass:'transparent'});
			$("#takeActionGrid").html('<div class="alert alert-danger" role="alert"><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span><span class="sr-only"></span>Can not Take Action on '+status+' Referral.</div>');
			$("#takeActionGrid").dialog('open'); 
		}	
		else
		{
			var ticketNo = jQuery("#gridedittable").jqGrid('getCell',id,'ticket_no');
			ticketNo=$(ticketNo).text();
			var refBy = jQuery("#gridedittable").jqGrid('getCell',id,'ref_by');
			var refBySpec = jQuery("#gridedittable").jqGrid('getCell',id,'spec');
			var refTo = jQuery("#gridedittable").jqGrid('getCell',id,'ref_to');
			var refToSpec = jQuery("#gridedittable").jqGrid('getCell',id,'ref_spec');
			var reason = jQuery("#gridedittable").jqGrid('getCell',id,'reason');
			var bed = jQuery("#gridedittable").jqGrid('getCell',id,'bed');
			var nu = jQuery("#gridedittable").jqGrid('getCell',id,'nursing_unit');
			var priority = jQuery("#gridedittable").jqGrid('getCell',id,'priority');
			priority=$(priority).text();
			var openDate = jQuery("#gridedittable").jqGrid('getCell',id,'open_date');
			var subSpec = jQuery("#gridedittable").jqGrid('getCell',id,'ref_to_sub_spec');
			var level = jQuery("#gridedittable").jqGrid('getCell',id,'level');
			var empId = jQuery("#gridedittable").jqGrid('getCell',id,'caller_emp_id');
			var empName = jQuery("#gridedittable").jqGrid('getCell',id,'caller_emp_name');
			var pat_name = jQuery("#gridedittable").jqGrid('getCell',id,'patient_name');
			
			level=$(level).text();
			var uhid = jQuery("#gridedittable").jqGrid('getCell',id,'uhid');
			$("#takeActionGrid").dialog({title: 'Take Action for '+ticketNo,width: 1000,height: 490});
			$("#takeActionGrid").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			$("#takeActionGrid").dialog('open');
			$.ajax({	
				type : "post",
				url : "/over2cloud/view/Over2Cloud/Referral/beforeTakeActionReferral.action?id="+id+"&status="+status+"&ticketNo="+ticketNo+"&refBy="+refBy+"&refTo="+refTo+"&reason="+reason+"&refBySpec="+refBySpec
				+"&refToSpec="+refToSpec+"&bed="+nu+"/"+bed+"&openDate="+openDate+"&priority="+priority+"&subSpec="+subSpec+"&level="+level+"&uhid="+uhid+"&empId="+empId+"&empName="+empName+"&pat_name="+pat_name,  
				success : function(data) 
				{
				    $("#takeActionGrid").html(data);
				    
				},
				error: function() {
				    alert("error");
				}
			});
		}	
	}


	/*function takeBulkActionOnClick() 
	{
	    var	complainIds = $("#gridedittable").jqGrid('getGridParam','selarrrow');
	  	var ids=complainIds.toString().split(",");
	  	var flag=true;
		 
	 	 if(complainIds.length<=1 )
		{
		     	alert("Please select more than one check box...");    
		     	 flag=false;
	 	} 
	 	 else 
	 		 {
	 		for(var i=0;i<ids.length;i++)
			 {
			 var status = jQuery("#gridedittable").jqGrid('getCell',ids[i],'status');
		 	 if(status!='Informed')
		 		 {
		      	alert("Please select only Informed check box...."); 
		 		 flag=false;
		 		 }
			 }
	 		 }
	 	  
		  if(flag)
		 {
	  	$("#takeActionGrid").dialog({title: 'Take Action',width: 1000,height: 400});
	 	$("#takeActionGrid").dialog('open');
		$("#takeActionGrid").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		
		  $.ajax
		({
		type : "post",
		url  : "/over2cloud/view/Over2Cloud/Referral/beforeTakeActionReferral.action?status=Informed&id="+complainIds,
		async:false,
		success : function(data)
		{
		$("#takeActionGrid").html(data);
		 
		},
		error : function()
		{
		alert("Error on data fetch");
		} 
		}); 
		 
		 }
	}*/
	function takeBulkActionOnClick() 
	{
	    var	complainIds = $("#gridedittable").jqGrid('getGridParam','selarrrow');
	  	var ids=complainIds.toString().split(",");
	  	var flag=true;
		 
	 	 if(complainIds.length<=1 )
		{
		     	alert("Please select more than one check box...");    
		     	 flag=false;
	 	} 
	 	 else 
	 		 {
	 		var temp = jQuery("#gridedittable").jqGrid('getCell',ids[0],'status');
	 		for(var i=0;i<ids.length;i++)
			 {
			 var status = jQuery("#gridedittable").jqGrid('getCell',ids[i],'status');
			 if((status!=temp))
		 		 {
		 		 flag=false;
		 		 }
			 }
	 		 }
	 	  
		  if(flag)
		 {
	  	$("#takeActionGrid").dialog({title: 'Take Action',width: 1000,height: 400});
	 	$("#takeActionGrid").dialog('open');
		$("#takeActionGrid").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		
		  $.ajax
		({
		type : "post",
		url  : "/over2cloud/view/Over2Cloud/Referral/beforeTakeActionReferral.action?status="+status+"&id="+complainIds,
		async:false,
		success : function(data)
		{
		$("#takeActionGrid").html(data);
		 
		},
		error : function()
		{
		alert("Error on data fetch");
		} 
		}); 
		 
		 }
		  else
		  {
			  alert("Please select only Not Informed/ Informed check box only....");
		  }
	}
	
	function refreshActivityBoard()
	{
		  intvrl=setInterval("ReferralActivityRefresh()", (180*1000));
	} 
	function ReferralActivityRefresh()
	{
		onSearchData();
		getStatusCounter();
	} 
	function clearInt()
	{
		clearInterval(intvrl);
	}
	function StopRefresh()
	{
 		clearInterval(intvrl);
	}
	function StartRefresh()
	{
	 	refreshActivityBoard();
	}
	function fetchDropDownData(flag){
		var specialty='';
		var reffTo='';
		if(flag=='nursingUnit')
		{
			 
				specialty='-1';
				reffTo="-1";
		 
		}
		else
		{
			specialty=	$("#spec").val();
			reffTo=$("#reffto").val();
		}
		 
 		$.ajax({	
			type : "post",
			url : "/over2cloud/view/Over2Cloud/Referral/fetchDropDownData.action?fromDate="+$("#fromDate").val()+"&toDate="+$("#toDate").val()+"&status="+$("#status").val()+"&nursingUnit="+$("#nursingUnit").val()+"&specialty="+specialty+"&refDocTo="+reffTo,
			success : function(data) 
			{
			console.log(data);
			if(flag=='spec' )
			{
				flag='nursingUnitSpec';
			} 
	 		if(flag!='spec' )
			{
				
				if(specialty=='-1')
				{
				//alert(flag);
				$('#spec option').remove();
				$('#spec').append($("<option></option>").attr("value",-1).text("All Speciality"));
				var empData=data[1];
		    	$(empData).each(function(index)
				{
				   $('#spec').append($("<option></option>").attr("value",this.name).text(this.name));
				});
				}
		    	 
			}
				if(flag!='nursingUnit' && flag!='spec' )
				{
					//alert(flag);
					if($("#nursingUnit").val()=='-1')
					{
				$('#nursingUnit option').remove();
				$('#nursingUnit').append($("<option></option>").attr("value",-1).text("All Nursing Unit"));
				empData=data[0];
		    	$(empData).each(function(index)
				{
					 
				   $('#nursingUnit').append($("<option></option>").attr("value",this.name).text(this.name));
				});
					}
					else if($("#nursingUnit").val()!='-1')
						{
							reffTo=='-1';
						}
				}
				if(flag=='nursingUnit'  )
				{
					//alert(flag);
					if($("#nursingUnit").val()=='-1')
					{
				$('#nursingUnit option').remove();
				$('#nursingUnit').append($("<option></option>").attr("value",-1).text("All Nursing Unit"));
				empData=data[0];
		    	$(empData).each(function(index)
				{
					 
				   $('#nursingUnit').append($("<option></option>").attr("value",this.name).text(this.name));
				});
					}
					else if($("#nursingUnit").val()!='-1')
						{
							reffTo=='-1';
						}
				}
				if( flag!='reffto')
				{
					if(flag=='nursingUnit')
					{
						if($("#reffto").val() !='-1')
						{
							reffTo='-1';
						}
						else
						{
							reffTo=$("#reffto").val();
						}
					}
					else if(flag=='spec')
					{
						if($("#reffto").val() !='-1')
						{
							reffTo='-1';
						}
						else
						{
							reffTo=$("#reffto").val();
						}
					}
					else
					{
						reffTo=$("#reffto").val();
					}
					if(reffTo=='-1')
					{
		     	$('#reffto option').remove();
				$('#reffto').append($("<option></option>").attr("value",-1).text("All Referred To Doctor"));
				empData=data[2];
		    	$(empData).each(function(index)
				{
				   $('#reffto').append($("<option></option>").attr("value",this.id).text(this.name));
				});
				}
				}
			},
			error: function() {
			    alert("error");
			}
		});
	}
	
	function downloadFeedStatus()
	{
		var locationWise='No';
		if( $('#locationView').is(":checked"))
		{
			locationWise='Yes';
		}
		else 
		{
			locationWise='NO';
		}
	    var	idAll = $("#gridedittable").jqGrid('getGridParam','selarrrow');
	    
	    $("#takeActionGrid").dialog({title: 'Check Column',width: 350,height: 600});
		$("#takeActionGrid").dialog('open');
		$("#takeActionGrid").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");

		$.ajax({
		    type : "get",
		 	url : "/over2cloud/view/Over2Cloud/Referral/beforeReferralDownload.action?fromDate="+$("#fromDate").val()+"&toDate="+$("#toDate").val()+"&status="+$("#status").val()+"&priority="+$("#priority1").val()+"&searchString="+$("#searchStr").val()+"&nursingUnit="+$("#nursingUnit").val()+"&specialty="+$("#spec").val()+"&level="+$("#level").val()+"&locationWise="+locationWise+"&refDoc="+$("#refDoc").val(),
		 	success : function(data) 
		 	
		    {
			    
	 			$("#takeActionGrid").html(data);
			},
			
		   error: function() {
				
		        alert("error");
		    }
		 });
	}
 
	function maximizeWindow(id)
 	{
		if(id=='both')
			{
			$("#both").show();
			$("#grid").hide();
			$("#lodge").hide();
	 		$("#TicketDiv").show();
			$("#listPart").show();
	 		}
		else if(id=='grid')
			{
			$("#grid").show();
			$("#both").hide();
			$("#lodge").hide();
			$("#listPart").show();
	 		$("#TicketDiv").hide();
	
			}
		else if(id=='lodge')
		{
			$("#lodge").show();
			$("#grid").hide();
			$("#both").hide();
 			$("#TicketDiv").show();
 			$("#listPart").hide();
 			
		}
	}
	 
	function getStatusCounter()
	{
		var locationWise='No';
		if( $('#locationView').is(":checked"))
		{
			locationWise='Yes';
		}
		else 
		{
			locationWise='NO';
		}
		  $.ajax
		({
		type : "post",
	 	url  : "view/Over2Cloud/Referral/getCounterStatus.action?fromDate="+$("#fromDate").val()+"&toDate="+$("#toDate").val()+"&status="+$("#status").val()+"&priority="+$("#priority1").val()+"&searchString="+$("#searchStr").val()+"&nursingUnit="+ $("#nursingUnit").val()+"&specialty="+$("#spec").val()+"&level="+$("#level").val()+"&refDocTo="+$("#reffto").val()+"&refDoc="+$("#refDoc").val()+"&locationWise="+locationWise,
		async:false,
		success : function(data)
		{
			console.log(data);
			if(data.length>0){
				var total=0;
				var total1=0;
				$("#statusCounter").empty();
				var temp="";
				if(data[0].Informed>0)
				{
				temp+='<div class="TitleBorder"><h1>Informed</h1><div class="circle">'+data[0].Informed+'</div></div>';
				total+=parseInt(data[0].Informed);
				}
				if(data[0].NotInformed>0)
				{
				temp+='<div class="TitleBorder"><h1>Not Informed</h1><div class="circle">'+data[0].NotInformed+'</div></div>';
				total+=parseInt(data[0].NotInformed);
				}
				if(data[0].Seen>0)
				{
				temp+='<div class="TitleBorder"><h1>Close</h1><div class="circle">'+data[0].Seen+'</div></div>';
				total+=parseInt(data[0].Seen);
				}
				if(data[0].Snooze>0)
				{
				temp+='<div class="TitleBorder"><h1>Snooze</h1><div class="circle">'+data[0].Snooze+'</div></div>';
				total+=parseInt(data[0].Snooze);
				}
				if(data[0].SnoozeD>0)
				{
				temp+='<div class="TitleBorder"><h1>Snooze-D</h1><div class="circle">'+data[0].SnoozeD+'</div></div>';
				total+=parseInt(data[0].SnoozeD);
				}
				if(data[0].Ignore>0)
				{
				temp+='<div class="TitleBorder"><h1>Ignore</h1><div class="circle">'+data[0].Ignore+'</div></div>';
				total+=parseInt(data[1].Ignore);
				}
				if(data[0].IgnoreD>0)
				{
				temp+='<div class="TitleBorder"><h1>Cancel</h1><div class="circle">'+data[0].IgnoreD+'</div></div>';
				total+=parseInt(data[0].IgnoreD);
				}
				
 				temp+='<div class="TitleBorder"><h1>Total</h1><div class="circle">'+total+'</div></div>';
				
				


				
				if(data[1].Informed>0)
				{
				temp+='<div class="TitleBorderSer"><h1>Informed</h1><div class="circleSer">'+data[1].Informed+'</div></div>';
				total1+=parseInt(data[1].Informed);
				}
				if(data[1].NotInformed>0)
				{
				temp+='<div class="TitleBorderSer"><h1>Not Informed</h1><div class="circleSer">'+data[1].NotInformed+'</div></div>';
				total1+=parseInt(data[1].NotInformed);
				}
				if(data[1].Seen>0)
				{
				temp+='<div class="TitleBorderSer"><h1>Close</h1><div class="circleSer">'+data[1].Seen+'</div></div>';
				total1+=parseInt(data[1].Seen);
				}
				if(data[1].Snooze>0)
				{
				temp+='<div class="TitleBorderSer"><h1>Snooze</h1><div class="circleSer">'+data[1].Snooze+'</div></div>';
				total1+=parseInt(data[1].Snooze);
				}
				if(data[1].SnoozeD>0)
				{
				temp+='<div class="TitleBorderSer"><h1>Snooze-D</h1><div class="circleSer">'+data[1].SnoozeD+'</div></div>';
				total1+=parseInt(data[1].SnoozeD);
				}
				if(data[1].Ignore>0)
				{
				temp+='<div class="TitleBorderSer"><h1>Ignore</h1><div class="circleSer">'+data[1].Ignore+'</div></div>';
				total1+=parseInt(data[1].Ignore);
				}
				if(data[1].IgnoreD>0)
				{
				temp+='<div class="TitleBorderSer"><h1>Cancel</h1><div class="circleSer">'+data[1].IgnoreD+'</div></div>';
				total1+=parseInt(data[1].IgnoreD);
				}
				temp+='<div class="TitleBorderSer"><h1>Total</h1><div class="circleSer">'+total1+'</div></div>';
				
				
				
				
				$("#statusCounter").append(temp);
			}
		
		},
		error : function()
		{
		alert("Error on data fetch");
		} 
		}); 
		
	}
	
	//clearInt();
 	//getStatusCounter();
	//fetchDropDownData();
	//refreshActivityBoard();
	



	<%if(userRights.contains("lodgeBoardRef_VIEW")) 
	{%>
	onloadTicketLodge();
	maximizeWindow('lodge');

	<%}%>

	<%if(userRights.contains("ticketLodgeGrid_VIEW")) 
		
	{%>
	getStatusCounter();
	fetchDropDownData();
	onloadTicketLodge();
	clearInt();
	onSearchData();
	refreshActivityBoard();

	<%}%>
<%if(userRights.contains("gridLodge_VIEW")) 
		
	{%>
	getStatusCounter();
	fetchDropDownData();
	clearInt();
	onSearchData();
	refreshActivityBoard();
	maximizeWindow('grid');
	<%}%>

	
	
</script>

</head>
<body><!-- 
<sj:dialog
          id="takeActionGrid"
          showEffect="slide" 
          hideEffect="explode" 
          autoOpen="false"
           width="1000"
          height="350"
          draggable="true"
          resizable="true"
           modal="true"
         onCloseTopics="cancelButtonDialog"
          position="center"
          >
</sj:dialog>
 -->
 
 <sj:dialog
          id="takeActionGrid"
          autoOpen="false" 
        modal="true"
        width="1029"
        height="450"
          position="center"
          >
</sj:dialog>
 
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div id="statusCounter"></div>
	
	<%if(userRights.contains("lodgeBoardRef_VIEW")) 
							{%>
		<div id="both">
		<sj:a  button="true" cssClass="button" tabindex="-1" cssStyle="height:22px; width:30px;float:right;"  title="Lodge Window"   onClick="maximizeWindow('lodge');" buttonIcon="ui-icon-transferthick-e-w"></sj:a>
		</div>
		<%}%>
		<%if(userRights.contains("gridLodge_VIEW")) 
							{%>
		<div id="lodge" style="display: none">
	<sj:a  button="true"  cssClass="button" tabindex="-1" cssStyle="height:22px; width:30px;float:right;"  title="Grid Window"   onClick="maximizeWindow('grid');" buttonIcon="ui-icon-transferthick-e-w"></sj:a>
	</div>
		<%}%>
	<%if(userRights.contains("ticketLodgeGrid_VIEW")) 
							{%>
							<div id="both">
		<sj:a  button="true" cssClass="button" tabindex="-1" cssStyle="height:22px; width:30px;float:right;"  title="Lodge Window"   onClick="maximizeWindow('lodge');" buttonIcon="ui-icon-transferthick-e-w"></sj:a>
		</div>
		<div id="lodge" style="display: none">
	<sj:a  button="true"  cssClass="button" tabindex="-1" cssStyle="height:22px; width:30px;float:right;"  title="Grid Window"   onClick="maximizeWindow('grid');" buttonIcon="ui-icon-transferthick-e-w"></sj:a>
	</div>
	<div id="grid" style="display: none">
	<sj:a  button="true" cssClass="button" tabindex="-1" cssStyle="height:22px; width:30px;float:right;"  title="Lodge & Grid Window"   onClick="maximizeWindow('both');" buttonIcon="ui-icon-extlink"></sj:a>
	</div>
	<%}%>
	
	
	</div>	
<div class="clear"></div><!--
pankaj
--><div style="height:auto; width:99%;    margin: 5px 0px 0px 6px; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; ">
<div id="TicketDiv" style="overflow:hidden;border-radius:6px; height:75%; width:99%; border: 1px solid #e4e4e5; margin: 3px 6px 4px 5px;border-right: 1px solid rgba(176, 165, 165, 0.54);"></div>

<div id="listPart"  style="border-radius:6px; height:60%; width:100%;float:top;border: 1px solid #e4e4e5; margin: 0px 0px 0px 0px;border-right: 1px solid rgba(176, 165, 165, 0.54);">
			<div  class="listviewButtonLayer" id="listviewButtonLayerDiv" style="width: 100%">
			<div >
			<table >
			<tbody><tr><th>
			<table >
			<tbody>
			<tr class="alignright printTitle">
				<td >
				
			         <sj:datepicker cssStyle="height: 12px; width: 60px;float:left;" tabindex="-1" cssClass="button" id="fromDate" name="fromDate" value="%{fromDate}" size="20" maxDate="0"   readonly="true" onchange="onSearchData();fetchDropDownData();getStatusCounter();"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" />
				</td>
				<td>
					 <sj:datepicker cssStyle="height: 12px; width: 60px;float:left;" tabindex="-1" cssClass="button" id="toDate" name="toDate"  value="%{toDate}" size="20" maxDate="0"   readonly="true" onchange="onSearchData();fetchDropDownData();getStatusCounter();"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" />
				</td>
				<td>
					 <s:select
						id="status" 
						name="status" 
						list="#{'All':'All Status','Informed':'Informed','Not Informed':'Not Informed','Snooze':'OCC Park','Snooze-I':'Dept Park','Ignore':'OCC Ignore','Ignore-I':'Dept Ignore','Seen':'Seen','OCC Escalate':'OCC Escalate','Dept Escalate':'Dept Escalate','SnoozeH':'OCC Park History','SnoozeI':'Dept Park History'}"
						cssClass="button"
						theme="simple"
						headerKey = "-1"
						headerValue = "Select Status"
					 	cssStyle="width: 110px;height:25px;float:left;"
						onchange="onSearchData();fetchDropDownData();getStatusCounter();"
						tabindex="-1">
					</s:select>
				</td>
				
				<td>	
					 <s:select
						id="nursingUnit" 
						name="nursingUnit" 
						list="{'No Nursing Unit'}"
						headerKey="-1"
						headerValue="All Nursing Unit"
						cssClass="button"
						theme="simple"
						cssStyle="width: 140px;height:25px;float:left;"
						onchange=" fetchDropDownData('nursingUnit');onSearchData('nursingUnit');getStatusCounter();"
						tabindex="-1">
					</s:select> 
				</td>
				
				<td>	
					  <s:select
						id="spec" 
						name="spec" 
						list="{'No Speciality'}"
						headerKey="-1"
						headerValue="All Specialty"
						cssClass="button"
						theme="simple"
						cssStyle="width: 120px;height:25px;float:left;"
						onchange=" fetchDropDownData('spec');onSearchData('spec');getStatusCounter();"
						tabindex="-1">
					</s:select>  
					<%-- 	<sj:autocompleter id="spec" name="spec" list="{'No Speciality'}" selectBox="true" selectBoxIcon="false" placeHolder="Enter Specialty"
					       		cssStyle="width: 140px;height:25px;float:left;"	cssClass="textField" theme="simple" onSelectTopics="onSearchData" />
					 --%>	
					
				</td>
			 
				<td>	
					 <s:select
						id="reffto" 
						name="reffto" 
						list="{'No Referred Doctor'}"
						headerKey="-1"
						headerValue="All Referred To Doctor"
						cssClass="button"
						theme="simple"
						cssStyle="width: 150px;height:25px;float:left;"
						onchange=" fetchDropDownData('reffto');onSearchData();getStatusCounter();"
						tabindex="-1" >
					</s:select> 
					</td>
					 
		 	
				<td>	
					 <s:select
						id="priority1" 
						name="priority1" 
						list="#{'Routine':'Routine','Stat':'Stat','Urgent':'Urgent'}"
						headerKey="-1"
						headerValue="All Priority"
						cssClass="button"
						theme="simple"
						cssStyle="width: 110px;height:25px;float:left;"
						onchange="onSearchData();getStatusCounter();"
						tabindex="-1">
					</s:select> 
				</td>
				 	<td>	
					 <s:select
						id="level" 
						name="level" 
						list="#{'Level1':'Level1','Level2':'Level2','Level3':'Level3','Level4':'Level4','Level5':'Level5'}"
						headerKey="-1"
						headerValue="All Level"
						cssClass="button"
						theme="simple"
						cssStyle="width: 100px;height:25px;float:left;"
						onchange="onSearchData();getStatusCounter();"
						tabindex="-1" >
					</s:select> 
				</td>
				<!--<s:if test="%{locationForManager=='LocationForManager'}">
<div style="float:left;"><span style="float:left;margin-left: 20px;margin-right: 8px;font-size: 15px;">Referral:</span>
		<s:radio  id="referralType"   name="referralType"  list="#{'1':'For My Speciality','2':'At My Location'}" value="1" theme="simple"  onchange="ChangeReferralType(this.value)"/>
</div>
</s:if>
			  --><%
	if(userTpe.equalsIgnoreCase("N"))
	{%>
	<s:if test="%{departmentView=='DepartmentView'}">
					<td>
					<s:select
						id="refDoc" 
						name="refDoc" 
						list="#{'toMe':'Reffered To Me','byMe':'Reffered By Me'}"
					 	cssClass="button"
						theme="simple"
						cssStyle="width: 140px;height:25px;float:left;"
						onchange="onSearchData();getStatusCounter();"
						tabindex="-1" >
					</s:select> 
					
				</td>
				</s:if>
				<%} %>
					 
				<td>	
					 <s:textfield name="searchStr" id="searchStr" tabindex="-1" placeholder="Search Any Value" theme="simple" cssClass="button" onkeyup="onSearchData()" cssStyle="height:13px; width:85px;float:left;"/>
				</td>
				
					<%if(userRights.contains("action_VIEW")) 
					{%>
					<td>
							<sj:a  button="true" cssClass="button" tabindex="-1" cssStyle="margin-top: 0px;height:23px; width:94px;float:left;"  title="Bulk Action"  onClick="takeBulkActionOnClick();" ><font style="font-size: 12px;">Bulk Action</font></sj:a>

					</td>	
					<%}%>
				
				
				<td>
					<sj:a button="true" cssClass="button" tabindex="-1" title="Refresh" onclick="onSearchData();getStatusCounter();" cssStyle="height:22px; width:30px;float:left;" buttonIcon="ui-icon-refresh"/>
				</td>
				<td>
					<sj:a  button="true" cssClass="button" tabindex="-1" cssStyle="height:22px; width:30px;float:left;"  title="Download"   onclick="downloadFeedStatus();" buttonIcon="ui-icon-arrowstop-1-s"></sj:a>
				</td>
		
		<s:if test="%{locationWise=='LocationManagerView'}">
		<td>		
		 <div style="float: left">
	 	 <input type="checkbox" style= "width: 27px; height: 23px;" title="Location Wise Referral" onchange="onSearchData();getStatusCounter();" id="locationView"/>
		</div>
			</td>	
			</s:if>
				

</tr></tbody></table>
</th>

</tr></tbody></table></div></div>
<div id="datapart" onmouseover="StopRefresh()"  onmouseout="StartRefresh()" style="border-radius:6px; height:60%; width:100%;border: 1px solid #e4e4e5;border-right: 1px solid rgba(176, 165, 165, 0.54);">
 </div>
 </div>
 	</div> 
</div>
</body>
</html>