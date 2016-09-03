<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
	var wingsToDisable = '';
	var wingsidsforinput = '';
	var checkAllids = '';
	var selectedWings='';
	var checkAll=false;	
	//alert('department'+$('#department').val());
	function checkall(val, id) 
	{
		modifier();
		var checkidsplithashlist = [];
		checkidsplithashlist=checkAllids.split(",");
		for (var i = 0; i < checkidsplithashlist.length; i++)
		{
			if (checkidsplithashlist[i].indexOf(id) > -1) 
			{
				if (val == true) 
				{
					var disablecheck = $("#"+checkidsplithashlist[i].split("####")[1]+ "kkNAchkbox").attr('disabled');
					if (disablecheck != 'disabled')
					{
						$("#"+checkidsplithashlist[i].split("####")[1]+"kkNAchkbox").prop('checked','checked');
						checkedWingIds.push(checkidsplithashlist[i].split("####")[1]);
					}

				} 
				else
				{
					var disablecheck = $("#"+checkidsplithashlist[i].split("####")[1]+ "kkNAchkbox").attr('disabled');
					if (disablecheck != 'disabled') 
					{
						$("#"+checkidsplithashlist[i].split("####")[1]+"kkNAchkbox").removeAttr('checked');
						var index = checkedWingIds.indexOf(checkidsplithashlist[i].split("####")[1]);
						if (index > -1) {
							checkedWingIds.splice(index, 1);
						}
					}
				}
			}
		}
	}
	
	function mapptoallfloor(val)
	{

		modifier();
		var checkidsplithashlist = [];
		checkidsplithashlist=checkAllids.split(",");
		console.log(checkidsplithashlist);
		for (var i = 0; i < checkidsplithashlist.length; i++)
		{
				if(val == true)
				{
					$("#"+checkidsplithashlist[i].split("####")[0]).prop('checked','checked');
						
					var disablecheck = $("#"+checkidsplithashlist[i].split("####")[1]+ "kkNAchkbox").attr('disabled');
					if (disablecheck != 'disabled')
					{
						$("#"+checkidsplithashlist[i].split("####")[1]+"kkNAchkbox").prop('checked','checked');
						checkedWingIds.push(checkidsplithashlist[i].split("####")[1]);
					}
					
				}else
				{
					$("#"+checkidsplithashlist[i].split("####")[0]).removeAttr('checked');
					$("#"+checkidsplithashlist[i].split("####")[1]).removeAttr('checked');
					var disablecheck = $("#"+checkidsplithashlist[i].split("####")[1]+ "kkNAchkbox").attr('disabled');
				
					if (disablecheck != 'disabled') 
					{
						$("#"+checkidsplithashlist[i].split("####")[1]+"kkNAchkbox").removeAttr('checked');
						var index = checkedWingIds.indexOf(checkidsplithashlist[i].split("####")[1]);
						if (index > -1) {
							checkedWingIds.splice(index, 1);
						}
					}
				}
		}
	}
	 var dept=$("#department option:selected").text();
 	$.ajax({
				type : "post",
				  url : "/over2cloud/view/Over2Cloud/CorporatePatientServices/cpservices/getLocationMapDetail.action?LocId="+dept,
				success : function(data) {
					var floorNA = "NA";
					//	console.log(data);
					var liIds = $('#wings div').map(function(i, n) {
						return $(n).attr('id');
					}).get().join(',');
					var inputids = liIds.split(",");
					var liids2 = '';
					var empid1 = $('#hiddenemp').text();
					for (var i = 0; i < inputids.length; i++) {

						liids2 = $('#' + inputids[i] + ' li').map(
								function(i, n) {
									return $(n).attr('id');
								}).get().join(',');
						var checkemp = liids2.split(',');
						for (var j = 0; j < checkemp.length; j++) {
							if (checkemp[j].split('-')[0] == empid1) {
								wingsToDisable = wingsToDisable + inputids[i];
								wingsToDisable = wingsToDisable + ',';
							}
						}

					}

					var finwingsids = wingsToDisable.split(',');

					for (var i = 0; i < finwingsids.length; i++) {
						if (finwingsids[i].length > 0) {
							wingsidsforinput = wingsidsforinput
									+ finwingsids[i].substring(0,
											finwingsids[i].indexOf("w"));
							wingsidsforinput = wingsidsforinput + ',';
						}

						//console.log(wingsidsforinput);
					}
					$("#main_container").append('<div style="width:98%; background:-webkit-gradient(linear, 0 0, 0 100%, from(#f8f8f8), to(#eaeaea));"><b>Check All:</b> <input id="checkAlldiv" type="checkbox"  style="margin:11px 10px 9px 0px" value="" onchange="mapptoallfloor(this.checked)"></div>');
					var temp;
					$.each(data,function() 
							{

										$
												.each(
														this,
														function(k, v) {
															checkAll=false;
															//console.log(this.v[k].wingid);
															$("#main_container")
																	.append(
																			'<div id="'+k.split(" ")[0]+'title" style="width:98%; background:-webkit-gradient(linear, 0 0, 0 100%, from(#f8f8f8), to(#eaeaea));"><span style="float:left;margin:12px 0px 0px 3px;"><img id="'+k.split(" ")[0]+'kkkshow" title="Expand" class="ui-icon tree-wrap-ltr ui-icon-circlesmall-plus" onclick="showHideLoc(this.id)" /><img id="'+k.split(" ")[0]+'kkkhide" title="Collapse" style="cursor:pointer;display:none;" class="ui-icon ui-icon-circlesmall-minus tree-wrap-ltr" onclick="showHideLoc(this.id)" /></span><input id="'
																					+ k.split(" ")[0]
																					+ '" type="checkbox"  style="margin:11px 10px 9px 0px" value="" onchange="checkall(this.checked,this.id)">'
																					+ k
																					+ '</div>');
															$("#main_container")
																	.append(
																			'<div id="'+k.split(" ")[0]+'divid" style="display:none;">');

															$
																	.each(
																			this,
																			function(
																					k1,
																					v1) {
																				//console.log(k);
																				checkAllids += k.split(" ")[0]+"####"+this.wingid+",";
																				selectedWings+=	this.wingid+",";

																				$(
																						"#"+k.split(" ")[0]+"divid")
																						.append(
																								'<div style=""><input id="'+this.wingid+'kk'+floorNA+'chkbox" type="checkbox"  style="margin-top:5px;" onchange="getChckBoxIds('+this.wingid+',this.checked);" value="'+this.wingid+'">'
																										+ this.wingname
																										+ '</div>');
																				var wingsplittedids = wingsidsforinput
																						.split(',');
																				for (var i = 0; i < wingsplittedids.length; i++)
																				{
																					if (wingsplittedids[i].length > 0) {
																						
																						if (wingsplittedids[i] == this.wingid) {
																							$(
																									"#"
																											+ this.wingid
																											+ 'kk'
																											+ floorNA
																											+ 'chkbox')
																									.attr(
																											"disabled",
																											true);
																							$(
																									"#"
																											+ this.wingid
																											+ 'kk'
																											+ floorNA
																											+ 'chkbox')
																									.prop(
																											"checked",
																											true);
																							checkAll=true;
																						}
																					}
																				}
																			});
															$("#main_container")
															.append(
																	'</div>');
															
															if(checkAll){
																$(
																		"#"+k.split(" ")[0])
																		.prop(
																				"checked",
																				true);
															}
															
														});
									});
					 
				},
				error : function() {
					alert("error");
				}
			});
	function showHideLoc(kid){
		var flrdiv=kid.split("kkk")[0]+"divid";
		var showhide=kid.split("kkk")[1];
		if(showhide=='show'){
			$("#"+flrdiv).show();
			$("#"+kid.split("kkk")[0]+"kkkhide").show();
			$("#"+kid.split("kkk")[0]+"kkkshow").hide();
		}else{
			$("#"+flrdiv).hide();
			$("#"+kid.split("kkk")[0]+"kkkshow").show();
			$("#"+kid.split("kkk")[0]+"kkkhide").hide();
		}
	}
	function getChckBoxIds(wingId,chkChange){
		//console.log(checkedWingIds.toString());
		modifier();
		if (chkChange == true) 
		{
			checkedWingIds.push(wingId);
		}else{
			var index = checkedWingIds.indexOf(wingId);
			if (index > -1) {
				checkedWingIds.splice(index, 1);
			}
		}
	}
	function saveEmployee() {
		var empid=$('#hiddenemp').text();
		var empname=$('#hiddenempname').text();
	 
						 for(var i=0;i<checkedWingIds.length;i++)
						 {
								$('#'+checkedWingIds[i]+'wing ul').append('<li id="'+empid+'-'+checkedWingIds[i]+'">'+empname+'<a href="#"><span class="closeSpan" id="'+empid+'kk'+checkedWingIds[i]+'" onclick="removeEmp(this.id)"><img alt="closeimage" src="images/hide_feed.png" title="Remove" /></span></a></li>');
					          	shift_employeeIdsForSaveAs.push(empid+"#"+checkedWingIds[i]);
					          	shift_employeemapped_wing.push(empid+"#"+checkedWingIds[i]);
						 }
					          
						
			$("#maxmizeViewDialog1").dialog('close');
			$("#save").attr('onclick','save()').css('opacity','1');
			 $("#saveAs").attr('onclick','saveAs()').css('opacity','1');
		

	}
</script>
</head>
<body>
	<center>
		<div id="main_container" style="overflow: auto;"></div>
		<button href="#" onclick="saveEmployee();" id="save1" title="Save Data"
			style="margin-top: 15px;  border-radius: 5px;">
			<font size="2" >Save</font>
		</button>
		<div id="message1"></div>
	</center>
</body>
</html>