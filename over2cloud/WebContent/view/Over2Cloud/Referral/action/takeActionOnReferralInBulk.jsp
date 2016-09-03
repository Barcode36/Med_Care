<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<html>
<head>
<script type="text/javascript" src="<s:url value="/js/referral/referralValidation.js"/>"></script>



	
<STYLE type="text/css">
/* td.tdAlign {
	//padding: 5px;
}
 */
tr.color {
	background-color: #E6E6E6;
}

:focus {
  background: white;
}
 .ui-autocomplete-input {
	width:209px;
	height:25px;
}
#docIdDiv .ui-button
{
	width:22px;
	height:26px;
	left:0px; 
} 

.rightColumn {
   
    padding: 4px;
    
}
</STYLE>
<SCRIPT type="text/javascript">
$.subscribe('level112', function(event, data)
		{
			setTimeout(function(){ $("#orglevelSuccess1").fadeIn(); }, 10);
		    setTimeout(function(){ $("#orglevelSuccess1").fadeOut(); }, 1000);
		    sussessMessage();
		});
		function sussessMessage()
		{
			 delay(function()
			{
				 $("#takeActionGrid").dialog('close');
				 onSearchData();
					getStatusCounter();
			    }, 500 );
		}
		var delay = (function(){
			  var timer = 0;
			  return function(callback, ms){
			    clearTimeout (timer);
			    timer = setTimeout(callback, ms);
			  };
			})();

	function viewMainPage()
	{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax(
		{
			type : "post",
			url : "view/Over2Cloud/Referral/beforeReferralViewHeader.action",
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
	
	function resetTaskType(formId)
	{
		$('#' + formId).trigger("reset");
	}

	function changeDiv(value)
	{
		if (value == 'Snooze' || value == 'Snooze-I')
		{
			$('#snoozeDiv').show();
			$('#ignoreDiv').hide();
			$('#infDiv').hide();
		} else if (value == 'Ignore' || value == 'Ignore-I')
		{
			$('#snoozeDiv').hide();
			$('#ignoreDiv').show();
			$('#infDiv').hide();
		} else
		{
			$('#infDiv').show();
			$('#snoozeDiv').hide();
			$('#ignoreDiv').hide();
		}
	}
	function changeDivView(value,div1,div2)
	{
		if(value=='2')
		{
			$('#'+div2).show();
			$('#'+div1).hide();
			$("#assign_to_emp_id").val("");
			$("#assign_ref_to").val("");
			$("#assign_to_name_inf").val("");
			$("#assign_to_id_inf").val("");
		}	
		else
		{
			$('#'+div1).show();
			$('#'+div2).hide();
			$("#assign_to_id").val("");
			$("#assign_to_name").val("");
		}	
	}
	$.subscribe('fetchDocName', function(event,data)
	{
		showDetails("assign_to_emp_id_widget","assign_to_name_inf","ref_to_name_div","refto_name_div","assign_ref_to");
	});
	
	$.subscribe('fetchDocId', function(event,data)
	{
		showDetails("assign_ref_to_widget","assign_to_id_inf","ref_to_id_div","refto_id_div","assign_to_emp_id");
	});
	
	function showDetails(sourceId,targetId1,showId1,hideId1,id)
	{
		var temp=$("#"+sourceId+" option:selected").val();
		console.log(temp);
		$("#"+targetId1).val(temp.split("#")[1]);
		$("#spec_info").val(temp.split("#")[3]);
		$("#spec_seen").val(temp.split("#")[3]);
		$("#"+showId1).show();
		$("#"+hideId1).hide();
		/* if(id=="assign_ref_to" || id=="assign_to_emp_id")
		{
			$("#"+id).val(temp.split("#")[0]);
		} */	
	}
	
	//setDocData("assign_to_emp_id_widget","assign_ref_to_widget");
</script>
</head>
<div style="float: left; padding: 5px 0%; width: 100%;">

	<div class="border">

		<sj:dialog id="completionResult1" showEffect="slide"
			hideEffect="explode" autoOpen="false" 
			cssStyle="overflow:hidden;text-align:center;margin-top:10px;"
			modal="true">
			<div id="result1"></div>
		</sj:dialog>
		<s:form id="formone1" name="formone1"
			namespace="/view/Over2Cloud/Referral" action="takeActionOnReferral"
			method="post" enctype="multipart/form-data" theme="simple">
			 
			<center> <div style="float:center; border-color: black; background-color: rgb(255,99,71); 
		    color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
		    <div id="errZone1" style="float:center; margin-left: 7px"></div></div></center>
			
			<s:property value="%{ticketNo}"/>
			<s:property value="%{refTo}"/>
			
			<s:hidden name="rid" id="rid" value="%{id}"></s:hidden>
			<s:hidden name="refToSpec" id="refToSpec" value="%{refToSpec}"></s:hidden>
			<s:hidden name="prtType" id="prtType" value="%{priority}"></s:hidden>
			<s:hidden name="ticketNo" id="ticketNo" value="%{ticketNo}"></s:hidden>
			<s:hidden name="bed" id="bed" value="%{bed}"></s:hidden>
			<s:hidden name="uhid" id="uhid" value="%{uhid}"></s:hidden>
			<s:hidden name="refTo" id="refTo" value="%{refTo}"></s:hidden>
			<s:hidden name="refToSpec" id="refToSpec" value="%{refToSpec}"></s:hidden>
			<s:hidden name="refBySpec" id="refBySpec" value="%{refBySpec}"></s:hidden>
			<s:hidden name="refBy" id="refBy" value="%{refBy}"></s:hidden>
			<s:hidden name="empId" id="empId" value="%{empId}"></s:hidden>
			<s:hidden name="empName" id="empName" value="%{empName}"></s:hidden>
			<s:param name="refTo"   value="%{refTo}"></s:param>

			<div class="newColumn" align="center">
				<div class="leftColumn" ><b>Action:</b></div>
				<div class="rightColumn"  align="center">
				<s:if test="%{status== 'Not Informed'}">
				<s:select id="status1" name="status" list="#{'Informed':'Informed'}" value=""
						theme="simple" cssClass="button" onchange="changeDiv(this.value)" cssStyle="width:72%;height:26px;"/>
				</s:if>
				<s:else>
					<s:select id="status1" name="status" list="%{statusMap}" value=""
						theme="simple" cssClass="button" onchange="changeDiv(this.value)" cssStyle="width:72%;height:26px;"/>
				</s:else>					
				</div>
			</div>

			<div id="infDiv">
				<s:if test="%{status== 'Not Informed' || status == 'OCC Park'}">
				
					<div class="newColumn" >
						<div class="leftColumn" ><b>Informed To:</b></div>
						<div class="rightColumn" >
							<s:radio label="assignId" name="assignId" list="#{'1':'Doctor','2':'Employee'}" value="1" theme="simple"  onchange="changeDivView(this.value,'docIdDiv','empIdDiv')"/>
						</div>
					</div>
					
					<div id="empIdDiv" style="display:none;">
						<div class="newColumn">
						<span class="pIdsNotInformed" style="display: none;">assign_to_id#Informed To EmpID#T#sc,</span>
						<div class="leftColumn" ><b>Informed To EmpID:</b></div>
						<div class="rightColumn" >
							 <span class="needed"></span>
							<s:textfield id="assign_to_id" name="assign_to_id" theme="simple"
								cssClass="textField" cssStyle="width:150px;"
								placeholder="Enter Informed EmpID"
								onblur="getDetailsData(this.value,'emp','assign_to_name','errZone1','assign_to_id')" />
						</div>
					</div>
					<div class="newColumn" >
					<!--<span class="pIdsBulk" style="display: none;">assign_to_name#Action#D#,</span>
						--><div class="leftColumn" ><b>Informed To Name:</b></div>
						<div class="rightColumn">
						 <!--<span class="needed"></span>
							--><s:textfield id="assign_to_name" name="assign_to_name"
								theme="simple" cssClass="textField" cssStyle="width:150px;"
								placeholder="Informed To Name" readonly="true" />
						</div>
					</div>
					
					</div>
					
					<div id="docIdDiv">
						<div class="newColumn" >
							<div class="leftColumn" ><b>Doctor ID:</b></div>
								<span class="pIdsNotInformed" style="display: none;">assign_to_emp_id#Doctor ID#D,</span>
							<div class="rightColumn">
									<span class="needed"></span>
									<div id="refto_id_div">
										<sj:autocompleter id="assign_to_emp_id" name="assign_to_emp_id" list="doctorIdMap" selectBox="false" selectBoxIcon="false"
							       			cssClass="textField" theme="simple" onSelectTopics="fetchDocName"/>
									</div>
							    	<div id="ref_to_id_div" style="display: none;">
							    		<s:textfield id="assign_to_id_inf" name="assign_to_id_inf" cssClass="textField" theme="simple" cssStyle="width:150px;"></s:textfield>
							    	</div>
							</div>
				 		</div>
						 <div class="newColumn">
						 	<div class="leftColumn" ><b>Doctor Name:</b></div>
							<div class="rightColumn">
								<div id="refto_name_div">
									<sj:autocompleter id="assign_ref_to" name="assign_ref_to" list="doctorNameMap" selectBox="false" selectBoxIcon="false" 
						       				cssClass="textField" theme="simple" onSelectTopics="fetchDocId"/>
								</div>
						    	<div id="ref_to_name_div" style="display: none;">
						    		<s:textfield id="assign_to_name_inf" name="assign_to_name_inf" cssClass="textField" theme="simple" cssStyle="width:150px;"></s:textfield>
						    	</div>
							</div>
					 	</div>
					 	
					 		<div class="newColumn">
						 	<div class="leftColumn" ><b>Doctor Spec:</b></div>
							<div class="rightColumn">
						    		<s:textfield id="spec_info" cssClass="textField" readonly="true" theme="simple" cssStyle="width:150px;"></s:textfield>
						    	</div>
							</div>
					</div>
					
					<div class="newColumn" >
						
						<div class="leftColumn" ><b>Mobile No:</b></div>
						<div class="rightColumn" >
						
							<s:textfield id="mob_no1" name="mob_no" theme="simple"
								cssClass="textField" placeholder="Enter Mobile No" maxlength="10"
								cssStyle="width:150px;" />
						</div>
					</div>
					<div class="newColumn" >
						<div class="leftColumn" ><b>Remark:</b></div>
						<div class="rightColumn" >
							<s:textarea id="comments" name="comments" theme="simple"
								cssClass="textField" placeholder="Enter Remark"
								cssStyle="width:150px;" />
						</div>
					</div>
					
				</s:if>
				<s:elseif test="%{status=='Informed' || status=='Dept Park'}">
					<div class="newColumn" >
						<div class="leftColumn" ><b>Seen By:</b></div>
						<div class="rightColumn" >
							<s:radio theme="simple" id="assignId" name="assignId" list="#{'1':'Doctor','2':'Employee'}" value="1" onchange="changeDivView(this.value,'seenDocDiv','seenEmpDiv')"/>
						</div>
					</div>
					<div id="seenDocDiv">
						<div class="newColumn" >
							<div class="leftColumn" ><b>Doctor ID:</b></div>
							<span class="pIdsSeen" style="display: none;">assign_to_emp_id#Doctor ID#D,</span>
							<div class="rightColumn">
								<span class="needed"></span>
									<div id="refto_id_div">
										<sj:autocompleter id="assign_to_emp_id" name="assign_to_emp_id" list="doctorIdMap" placeholder="Enter ID" selectBox="false" selectBoxIcon="false"
							       			cssClass="textField" theme="simple" onSelectTopics="fetchDocName"/>
									</div>
							    	<div id="ref_to_id_div" style="display: none;">
							    		<s:textfield id="assign_to_id_inf" name="assign_to_id_inf" cssClass="textField" theme="simple" cssStyle="width:150px;"></s:textfield>
							    	</div>
							</div>
				 		</div>
						 <div class="newColumn">
						 	<div class="leftColumn" ><b>Doctor Name:</b></div>
							<div class="rightColumn">
								<div id="refto_name_div">
									<sj:autocompleter id="assign_ref_to" name="assign_ref_to" list="doctorNameMap"  placeholder="Enter Name" selectBox="false" selectBoxIcon="false" 
						       				cssClass="textField" theme="simple" onSelectTopics="fetchDocId"/>
								</div>
						    	<div id="ref_to_name_div" style="display: none;">
						    		<s:textfield id="assign_to_name_inf" name="assign_to_name_inf" cssClass="textField" theme="simple" cssStyle="width:150px;"></s:textfield>
						    	</div>
							</div>
					 	</div>
					 	
					 		<div class="newColumn">
						 	<div class="leftColumn" ><b>Doctor Spec:</b></div>
							<div class="rightColumn">
						    		<s:textfield id="spec_seen" cssClass="textField" readonly="true" theme="simple" cssStyle="width:150px;"></s:textfield>
						    	</div>
							</div>
					</div>
					<div id="seenEmpDiv" style="display:none;">
						<div class="newColumn" >
							<span class="pIdsSeen" style="display: none;">assign_to_id#Emp
								ID#T#sc,</span> 
							<div class="leftColumn" ><b>Seen By EmpID:</b></div>
							<div class="rightColumn" >
								 <span class="needed"></span> 
								<s:textfield id="assign_to_id" name="assign_to_id"
									cssClass="textField" theme="simple"
									placeholder="Enter Seen EmpID"
									onblur="getDetailsData(this.value,'emp','assign_to_name','errZone1','assign_to_id')"
									cssStyle="width:150px;" />
	
							</div>
						</div>
	
						<div class="newColumn" >
							<div class="leftColumn" ><b>Seen By Name:</b></div>
							<div class="rightColumn" >
								<s:textfield id="assign_to_name" name="assign_to_name"
									cssClass="textField" theme="simple" placeholder="Seen By Name"
									cssStyle="width:150px;" />
							</div>
						</div>
					</div>
					<div class="newColumn" >
						<span class="pIdsSeen" style="display: none;">assign_close_by#Close By#D#,</span>
						<div class="leftColumn" ><b>Close By:</b></div>
						<div class="rightColumn" >
							<span class="needed"></span>
							<s:select id="assign_close_by" name="assign_close_by"
								cssStyle="width:150px;"
								list="#{'-1':'Select Close By','Doctor':'Doctor','Nurshing':'Nurshing', 'Self':'Self'}"
								theme="simple" cssClass="button" cssStyle="width:72%;height:26px;"/>
						</div>
					</div>

					<div class="newColumn" >
						<span class="pIdsSeen" style="display: none;">assign_desig#Close By Designation#D#,</span>
						<div class="leftColumn" ><b>Close By Designation:</b></div>
						<div class="rightColumn" >
							<span class="needed"></span>
							<s:select id="assign_desig" name="assign_desig"
								cssStyle="width:150px;"
								list="#{'-1':'Select Designation','Consultant':'Consultant','Resident':'Resident','Medical Officer':'Medical Officer'}"
								
								theme="simple" cssClass="button" cssStyle="width:72%;height:26px;"/>
						</div>
					</div>

					<div class="newColumn" >
						<div class="leftColumn" ><b>Remark:</b></div>
						<div class="rightColumn" >
							<s:textarea id="comments" name="comments" theme="simple"
								cssClass="textField" placeholder="Enter Remark"
								cssStyle="width:150px;" />
						</div>
					</div>

				</s:elseif>
			</div>
			<div id="snoozeDiv" style="display: none;">

				<div class="newColumn" >
					<span class="pIdsSnooze" style="display: none;">sn_upto_date#Park Till
						Date#Date#,</span>
					<div class="leftColumn" ><b>Park Till Date:</b></div>
					<div class="rightColumn" >
						<span class="needed"></span>
						<sj:datepicker name="sn_upto_date" id="sn_upto_date"
							cssStyle="width:125px;" Class="button" size="15"
							displayFormat="dd-mm-yy" readonly="true" showOn="focus"
							Placeholder="Select Parked Till Date" />
					</div>
				</div>

				<div class="newColumn" >
					<span class="pIdsSnooze" style="display: none;">sn_upto_time#Park Till
						Time#Time#,</span>
					<div class="leftColumn" ><b>Park Till Time:</b></div>
					<div class="rightColumn" >
						<span class="needed"></span>
						<sj:datepicker name="sn_upto_time" id="sn_upto_time"
							cssStyle="width:125px;" timepickerOnly="false" timepicker="true"
							timepickerOnly="true" timepickerAmPm="false" Class="button"
							size="15" displayFormat="dd-mm-yy" readonly="true" showOn="focus"
							Placeholder="Select Parked Till Time" />
					</div>
				</div>

				<div class="newColumn" >
					<span class="pIdsSnooze" style="display: none;">snooze_comments#Park Reason#D#sc,</span>
					<div class="leftColumn" ><b>Park Reason:</b></div>
					<div class="rightColumn" >
						<span class="needed"></span>
						<s:textarea id="snooze_comments" name="snooze_comments"
							theme="simple" cssClass="textField"
							placeholder="Enter Park Reason" />
					</div>
				</div>
				
				<div class="newColumn" >
						<%-- <span class="snpIds" style="display: none;">assign_to_id#InformedEmpID#T#sc,</span> --%>
						<div class="leftColumn" ><b>Park By EmpID:</b></div>
						<div class="rightColumn" >
							<%-- <span class="needed"></span> --%>
							<s:textfield id="snooze_by_id"  theme="simple"
								cssClass="textField" cssStyle="width:150px;"
								placeholder="Enter Park By EmpID"
								onblur="getDetailsData(this.value,'emp','snooze_by','errZone1','snooze_by_id')" />
						</div>
					</div>
					<div class="newColumn" >
						<div class="leftColumn" ><b>Park By Name:</b></div>
						<div class="rightColumn" >
							<s:textfield id="snooze_by" name="snooze_by"
								theme="simple" cssClass="textField" cssStyle="width:150px;"
								placeholder="Park By Name" readonly="true" />
						</div>
					</div>

			</div>
			<div id="ignoreDiv" style="display: none;">
				
				<div class="newColumn" >
					<span class="pIdsIgnore" style="display: none;">ignore_comments#Ignore Reason#TextArea#an,</span>
					<div class="leftColumn" ><b>Ignore Reason:</b></div>
					<div class="rightColumn" >
						<span class="needed"></span>
						<s:textarea id="ignore_comments" name="ignore_comments"
							theme="simple" cssClass="textField"
							placeholder="Enter Ignore Reason" />
					</div>
				</div>
				
				<div class="newColumn" >
						<%-- <span class="igpIds" style="display: none;">assign_to_id#InformedEmpID#T#sc,</span> --%>
						<div class="leftColumn" ><b>Ignore By EmpID:</b></div>
						<div class="rightColumn" >
							<%-- <span class="needed"></span> --%>
							<s:textfield id="ignore_by_id"  theme="simple"
								cssClass="textField" cssStyle="width:150px;"
								placeholder="Enter Ignore By EmpID"
								onblur="getDetailsData(this.value,'emp','ignore_by','errZone1','ignore_by_id')" />
						</div>
					</div>
					<div class="newColumn" >
						<div class="leftColumn" ><b>Ignore By Name:</b></div>
						<div class="rightColumn" >
							<s:textfield id="ignore_by" name="ignore_by"
								theme="simple" cssClass="textField" cssStyle="width:150px;"
								placeholder="Ignore By Name" readonly="true" />
						</div>
					</div>
					
					<div class="newColumn" >
						<div class="leftColumn" ><b>Mobile No:</b></div>
						<div class="rightColumn" >
							<s:textfield id="mob_no" name="mob_no_ignore" theme="simple"
								cssClass="textField" placeholder="Enter Mobile No" maxlength="10"
								cssStyle="width:150px;" />
						</div>
					</div>

			</div>
			 
			<div class="clear"></div>
			
			<center>
				<div class="type-button" style="margin-top: 10px;">
						<sj:submit targets="resultAction" clearForm="true" value="Take Action"
						effect="highlight" effectOptions="{ color : '#FFFFFF'}"
						effectDuration="100" button="true" cssClass="submit"
						indicator="indicatorBulk" onCompleteTopics="level112"
						onBeforeTopics="validateBulk"
						 />
				</div>
				
			</center>
			<center>
							<img id="indicatorBulk" src="<s:url value="/images/indicator.gif"/>"
								alt="Loading..." style="display: none" />
					<sj:div id="orglevelSuccess1"  effect="fold">
                   <sj:div  id="resultAction"></sj:div></sj:div>   

			</center>
		</s:form>
	</div>
</div>
</html>