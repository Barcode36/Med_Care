<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme"
	customBasepath="%{contextz}" />
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript"
	src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<title>Insert title here</title>

<script type="text/javascript">
	$(document).ready(function()
	{
		$("#empL1").multiselect(
		{
			show : [ "", 200 ],
			hide : [ "explode", 1000 ]
		});
		$("#empL2").multiselect(
		{
			show : [ "", 200 ],
			hide : [ "explode", 1000 ]
		});
		$("#empL3").multiselect(
		{
			show : [ "", 200 ],
			hide : [ "explode", 1000 ]
		});
		$("#empL4").multiselect(
		{
			show : [ "", 200 ],
			hide : [ "explode", 1000 ]
		});

	});
	$.subscribe('level1', function(event, data)
	{
		setTimeout(function()
		{
			$("#patientaddition").fadeIn();
		}, 10);
		setTimeout(function()
		{
			$("#patientaddition").fadeOut();
		}, 4000);

	});
	function resetForm(formId)
	{
		$('#' + formId).trigger("reset");
	}
	function backView()
	{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax(
		{
			type : "post",
			url : "/over2cloud/view/Over2Cloud/Referral/escalation/escalationViewGrid.jsp",
			success : function(data)
			{
				$("#" + "data_part").html(data);
			},
			error : function()
			{
				alert("error");
			}
		});
	}

	function needEsc()
	{
		var data = $("#escalation").val();
		if (data == "Yes")
		{
			$("#l2").fadeIn();

		}
		if (data == "No")
		{
			$("#l2").fadeOut();
		}
	}

	function needEsc2()
	{

		var data = $("#empL1").val();
		if (data != "-1")
		{
			$("#l3").fadeIn();
		}
		if (data == "-1")
		{
			$("#l3").fadeOut();
		}
	}

	function needEsc3()
	{

		var data = $("#empL2").val();
		if (data != "-1")
		{
			$("#l4").fadeIn();

		}
		if (data == "-1")
		{
			$("#l4").fadeOut();
		}

	}

	function needEsc4()
	{

		var data = $("#empL3").val();
		if (data != "-1")
		{
			$("#l5").fadeIn();

		}
		if (data == "-1")
		{
			$("#l5").fadeOut();
		}

	}

	function changeinNextEsc(data, div, sId, sName, nId, nName)
	{
		var l1 = $("#empL1").val();
		var l2 = $("#empL2").val();
		var l3 = $("#empL3").val();
		var l4 = $("#empL4").val();
		$.ajax(
		{
			type : "post",
			url : "view/Over2Cloud/referral/escalation_config/nextEscMap4EmpL2.action?l1=" + l1 + "&l2=" + l2 + "&l3=" + l3 + "&l4=" + l4 + "&div=" + div + "&sId=" + sId + "&sName=" + sName + "&nId=" + nId + "&nName=" + nName+ "&subDept="+$("#esc_sub_dept").val(),
			success : function(data)
			{
				$('#' + div).html(data);
			},
			error : function()
			{
				alert("Somthing is wrong to get get Next excalation Level");
			}
		});
	}
	
	/* function dselectBelowEsc()
	{
		var subDept=$("#subDept").val();
		
			$.ajax({
				type :"post",
				url : "/over2cloud/view/Over2Cloud/referral/escalation_config/getNextEscMap4Edit.action?subDept="+subDept,
				success : function(empData){
				$('#empL2 option').remove();
				
		    	 $(empData).each(function(index)
				{
				   $('#empL2').append($("<option></option>").attr("value",this.ID).text(this.NAME));
				});
			    },
			    error : function () {
					alert("Somthing is wrong to get Data");
				}
			});
	}
	 */
	function getSubDepartment(deptId)
	{
		  $.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Escalation_Conf/depatmentsforlist.action?deptFlag=subdept&deptId="+deptId,
		    success : function(data) {
		    	//console.log(data);
	       		$('#esc_sub_dept option').remove();
	      		$('#esc_sub_dept').append($("<option></option>").attr("value","All").text("Select Specialty"));
	          	$(data).each(function(index)
	      		{
	      		   $('#esc_sub_dept').append($("<option></option>").attr("value",this.id).text(this.name));
	      		});
	       	},
		    error: function() {
	            alert("error");
	        }
		 });
	}
	
</script>

</head>
<body>
	<div class="clear"></div>
	<div class="list-icon">
		<!-- <div class="head">Add Prospective Client</div> -->
		<div class="head">Cross Referral Escalation</div>
		<div class="head">
			<img alt="" src="images/forward.png"
				style="margin-top: 60%; float: left;">
		</div>
		<div class="head">Add</div>
	</div>
	<div class="clear"></div>
	<div class="border">
		<div class="container_block">
			<div style="float: left; padding: 10px 1%; width: 98%;">
				<div
					style="float: left; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
					<div id="errZone" style="float: left; margin-left: 7px"></div>
				</div>
				<br>
				<s:form id="formone" name="formone" namespace="/view/Over2Cloud/referral/escalation_config" action="addEscalation" theme="css_xhtml" method="post" enctype="multipart/form-data">
					<div class="menubox">

						<div class="newColumn">
							<div class="leftColumn1">Department:</div>
							<div class="rightColumn1">
								<span class="needed"></span>
								<s:select id="esc_dept" name="esc_dept" headerKey="-1"
									headerValue="Select Department" list="%{serviceDeptMap}"
									cssClass="select" cssStyle="width:82%" onchange="getSubDepartment(this.value)">
								</s:select>
							</div>
						</div>

						<div class="newColumn">
							<div class="leftColumn1">Specialty:</div>
							<div class="rightColumn1">
								<span class="needed"></span>
								<s:select id="esc_sub_dept" name="esc_sub_dept" list="{''}"
									cssClass="select" cssStyle="width:82%" headerKey="-1"
									headerValue="Select Specialty" onchange="changeinNextEsc('', 'l2esc' ,'empL2', 'l2', 'empL2', 'l3');">
								</s:select>
							</div>
						</div>

						<div class="newColumn">
							<div class="leftColumn1">Priority:</div>
							<div class="rightColumn1">
								<span class="needed"></span>
								<s:select id="priority" name="priority"
									list="#{'Routine':'Routine','Urgent':'Urgent','Stat':'Stat'}"
									cssClass="select" cssStyle="width:82%" headerKey="-1"
									headerValue="Select Priority">
								</s:select>
							</div>
						</div>

						<span id="form2MandatoryFields" class="qIds"
							style="display: none;">Escalation#<s:property
								value="%{OLevel2LevelName}" />#D#,
						</span>
						<div class="newColumn">
							<div class="leftColumn1">Escalation:</div>
							<div class="rightColumn1">
								<span class="needed"></span>
								<s:select id="escalation" name="escalation"
									list="#{ 'No':'No','Yes':'Yes'}" cssClass="select"
									cssStyle="width:82%" onchange="needEsc()">
								</s:select>
							</div>
						</div>

						<div id="l2" style="display: none;">

							<span id="form2MandatoryFields" class="qIds"
								style="display: none;">Employee#empL1#D#,</span>
							<div class="newColumn">
								<div class="leftColumn1">L2:</div>
								<div class="rightColumn1">
									<span class="needed"></span>
									<div id="l2esc">
									<s:select id="empL1" name="l2" list="{''}" cssClass="select"
										cssStyle="width:28%;height:40%" multiple="true" onchange="changeinNextEsc(this.value, 'l3esc' ,'empL2', 'l3', 'empL3', 'l4');"
										>
									</s:select>
									</div>	
								</div>
							</div>

							<div class="newColumn">
								<div class="leftColumn1">L&nbsp;2&nbsp;TAT&nbsp;After:&nbsp;</div>
								<div class="rightColumn1">
									<sj:datepicker id="l2EscDuration" name="tat2" readonly="true"
										onchange="needEsc2();" placeholder="Enter Time" showOn="focus"
										timepicker="true" timepickerOnly="true" timepickerGridHour="4"
										timepickerGridMinute="10" timepickerStepMinute="1"
										cssClass="textField"/>
								</div>
							</div>
							
				<div class="newColumn">
        			   <div class="leftColumn1" >From Time:&nbsp;</div>
					        <div class="rightColumn1">
				                   <sj:datepicker placeholder="From Time" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" timepickerOnly="true" timepicker="true" timepickerAmPm="false" id="fromTime" name="fromTime" size="20"   readonly="true"   showOn="focus"/>
                           </div>
                   </div>
                   
                    <div class="newColumn">
        			   <div class="leftColumn1" >To Time:&nbsp;</div>
					        <div class="rightColumn1">
				                   <sj:datepicker placeholder="To Time" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" timepickerOnly="true" timepicker="true" timepickerAmPm="false" id="toTime" name="toTime" size="20"   readonly="true"   showOn="focus"/>
                           </div>
                   </div>
                   
                    <div class="newColumn">
        			   <div class="leftColumn1" >First Escalation Time:&nbsp;</div>
					        <div class="rightColumn1">
				                   <sj:datepicker placeholder="First Escalation Time" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" timepickerOnly="true" timepicker="true" timepickerAmPm="false" id="firstEsc" name="firstEsc" size="20"   readonly="true"   showOn="focus"/>
                           </div>
                   </div>
                   
                   <div class="newColumn">
        			   <div class="leftColumn1" >Escalation Time Flag:&nbsp;</div>
					        <div class="rightColumn1">
				                  <s:select  id="firstEscFlag"
				                              name="firstEscFlag"
				                              list="#{'1':'Active','0':'Deactive'}"
				                              cssClass="select"
					                          cssStyle="width: 80%"
					                             
				                              >
				         			</s:select>
                           </div>
                   </div>
						</div>

						<div id="l3" style="display: none;">
							<div class="newColumn">
								<div class="leftColumn1">L3:</div>
								<div class="rightColumn1">
									<div id="l3esc">
										<s:select id="empL2" name="l3" list="{''}" cssClass="select"
											cssStyle="width:25%;height:40%" multiple="true"
											onchange="changeinNextEsc(this.value, 'l4esc' ,'empL3', 'l4', 'empL4', 'l5')">
										</s:select>
									</div>
								</div>
							</div>

							<div class="newColumn">
								<div class="leftColumn1">L&nbsp;3&nbsp;TAT&nbsp;After:&nbsp;</div>
								<div class="rightColumn1">
									<sj:datepicker id="l3EscDuration" name="tat3" readonly="true"
										onchange="needEsc3();" placeholder="Enter Time" showOn="focus"
										timepicker="true" timepickerOnly="true" timepickerGridHour="4"
										timepickerGridMinute="10" timepickerStepMinute="1"
										cssClass="textField" />
								</div>
							</div>
						</div>

						<div id="l4" style="display: none;">
							<div class="newColumn">
								<div class="leftColumn1">L4:</div>
								<div class="rightColumn1">
									<div id="l4esc">
										<s:select id="empL3" name="l4" list="{''}" cssClass="select"
											cssStyle="width:25%;height:40%" multiple="true"
											onchange="changeinNextEsc(this.value, 'l5esc' ,'empL4', 'l5', 'empL4', 'l5')">
										</s:select>
									</div>
								</div>
							</div>

							<div class="newColumn">
								<div class="leftColumn1">L&nbsp;4&nbsp;TAT&nbsp;After:&nbsp;</div>
								<div class="rightColumn1">
									<sj:datepicker id="l4EscDuration" name="tat4" readonly="true"
										onchange="needEsc4();" placeholder="Enter Time" showOn="focus"
										timepicker="true" timepickerOnly="true" timepickerGridHour="4"
										timepickerGridMinute="10" timepickerStepMinute="1"
										cssClass="textField" />
								</div>
							</div>
						</div>


						<div id="l5" style="display: none;">
							<div class="newColumn">
								<div class="leftColumn1">L5:</div>
								<div class="rightColumn1">
									<div id="l5esc">
										<s:select id="empL4" name="l5" list="{''}" cssClass="select"
											cssStyle="width:25%;height:40%" multiple="true">
										</s:select>
									</div>
								</div>
							</div>

							<div class="newColumn">
								<div class="leftColumn1">L&nbsp;5&nbsp;TAT&nbsp;After:&nbsp;</div>
								<div class="rightColumn1">
									<sj:datepicker id="l5EscDuration" name="tat5" readonly="true"
										placeholder="Enter Time" showOn="focus" timepicker="true"
										timepickerOnly="true" timepickerGridHour="4"
										timepickerGridMinute="10" timepickerStepMinute="1"
										cssClass="textField" />
								</div>
							</div>
						</div>
					</div>
					<div class="clear"></div>


					<!-- Buttons -->
					<center>
						<img id="indicator2" src="<s:url value="/images/indicator.gif"/>"
							alt="Loading..." style="display: none" />
					</center>
					<div class="buttonStyle"
						style="text-align: center; margin-left: -100px;">
						<sj:submit targets="patientresult" clearForm="true" value="Add"
							effect="highlight" effectOptions="{ color : '#222222'}"
							effectDuration="5000" button="true" onCompleteTopics="level1"
							cssClass="submit" indicator="indicator2"
							 />
						<sj:a name="Reset" href="#" cssClass="submit"
							indicator="indicator" button="true"
							onclick="resetForm('formone');"
							cssStyle="margin-left: 193px;margin-top: -43px;">
					  	Reset
					</sj:a>
						<sj:a name="Cancel" href="#" cssClass="submit"
							indicator="indicator" button="true"
							cssStyle="margin-left: 145px; margin-top: -25px;"
							onclick="backView()" cssStyle="margin-top: -43px;">
					  	Back
					</sj:a>
					</div>


					<sj:div id="patientaddition" effect="fold">
						<div id="patientresult"></div>
					</sj:div>
				</s:form>



			</div>
		</div>
	</div>
</body>
</html>