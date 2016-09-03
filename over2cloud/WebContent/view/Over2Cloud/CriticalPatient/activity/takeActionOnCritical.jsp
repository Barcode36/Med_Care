<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<html>
<head>
<script type="text/javascript"
	src="<s:url value="/js/referral/referralValidation.js"/>"></script>
<STYLE type="text/css">
/* td.tdAlign {
	//padding: 5px;
}
 */
tr.color {
	background-color: #E6E6E6;
}

 .ui-autocomplete-input {
	width:209px;
	height:25px;
}
#docIdDiv .ui-button
{
	width:22px;
	height:26px;
	left:0px !important; 
} 
.rightColumn {
   
    padding: 4px;
    
}
</STYLE>
<SCRIPT type="text/javascript">
	$.subscribe('level1', function(event, data)
	{
		$("#completionResult1").dialog({title: 'Confirmation Message',width: 400,height: 150});
		$("#completionResult1").dialog('open');
		setTimeout(function()
		{
			$("#result1").fadeIn();
		}, 10);
		setTimeout(function()
		{
			$("#result1").fadeOut();
			cancelButton();
		}, 1000);
		//resetTaskType('taskTypeForm');
	});
	function cancelButton()
	{
	 	onSearchData();
	 	getStatusCounter();
	  	$("#takeActionGrid").dialog('close');
		$('#completionResult1').dialog('close');
	 	
	}
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

	function changeDiv(value,div,div1)
	{

		//alert("divid "+div+"value  "+value);
		
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Referral/getReasonName.action?value="+value,
		    success : function(data) {
			//alert(JSON.stringify(data));
	    	$('#'+div+' option').remove();
			$('#'+div).append($("<option></option>").attr("value",-1).text('Select Park Reason'));
			$('#'+div1+' option').remove();
			$('#'+div1).append($("<option></option>").attr("value",-1).text('Select Ignore Reason'));
			$(data).each(function(index)
			{
	   			$('#'+div).append($("<option></option>").attr("value",this.NAME).text(this.NAME));
	   			$('#'+div1).append($("<option></option>").attr("value",this.NAME).text(this.NAME));
				});
			},
			
			
	   		error: function() 
	   		{
	        alert("error");
	    	}
	 });
		if (value == 'Snooze' || value == 'Snooze-I')
		{
			$('#snoozeDiv').show();
			$('#ignoreDiv').hide();
			$('#infDiv').hide();
			$("#assign_to_id").val("");
			$("#assign_to_name").val("");
			$("#assign_to_emp_id").val("");
			$("#assign_ref_to").val("");
			$("#spec_info").val("");
			$("#comments").val("");

			$("#ignore_comment").val("");
			$("#ignore_by_id").val("");
			$("#ignore_by").val("");
			
		} else if (value == 'Ignore' || value == 'Ignore-I')
		{
			$('#snoozeDiv').hide();
			$('#ignoreDiv').show();
			$('#infDiv').hide();
			$("#sn_upto_date").val("");
			$("#sn_upto_time").val("");
			$("#snooze_comments").val("");
			$("#snooze_by_id").val("");
			$("#snooze_by_id").val("");
			$("#snooze_by").val("");
			$("#mob_no").val("");
			$("#assign_to_id").val("");
			$("#assign_to_name").val("");
			$("#assign_to_emp_id").val("");
			$("#assign_ref_to").val("");
			$("#spec_info").val("");
			$("#comments").val("");
		} else
		{
			$('#infDiv').show();
			$('#snoozeDiv').hide();
			$('#ignoreDiv').hide();
			$("#sn_upto_date").val("");
			$("#sn_upto_time").val("");
			$("#snooze_comments").val("");
			$("#snooze_by_id").val("");
			$("#snooze_by_id").val("");
			$("#snooze_by").val("");
			$("#mob_no").val("");
			$("#ignore_comment").val("");
			$("#ignore_by_id").val("");
			$("#ignore_by").val("");
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
		showDetails1("assign_to_emp_id_widget","assign_to_name_inf","ref_to_name_div","refto_name_div","assign_ref_to");
	});
	
	$.subscribe('fetchDocId', function(event,data)
	{
		showDetails1("assign_ref_to_widget","assign_to_id_inf","ref_to_id_div","refto_id_div","assign_to_emp_id");
	});
	
	function showDetails1(sourceId,targetId1,showId1,hideId1,id)
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
	function viewData()
	{
		//alert($("#data").val());
		
		$("#viewData").html($("#data").val());
	}
	viewData();
	
	function displayFullDetail(id)
	{
		
	}
	
	function viewAction(value)
	{
		if(value=="viewActionPage")
			{
			 $("#viewActionPage").show();
			 $("#sms").hide();
			}
		else
			{
			$("#viewActionPage").hide();
			 $("#sms").show();
			}
		
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
			<div id="viewData"></div>
		<div id="sms">
		<s:form id="sendsms" name="sendsms"
			namespace="/view/Over2Cloud/Critical" action="takeActionOnCritical"
			method="post" enctype="multipart/form-data" theme="simple">
			<div
				style="float: center; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
				<div id="errZone1" style="float: center; margin-left: 7px"
					align="center"></div>
			</div>
			
		<s:hidden name="sendsms" value="sendsms"/>
			<s:hidden name="ticketNo" id="ticketNo" value="%{ticketNo}"></s:hidden>
			<s:hidden name="bed" id="bed" value="%{bed}"></s:hidden>
				<s:hidden name="rid" id="rid" value="%{pid}"></s:hidden>
			<s:hidden name="uhid" id="uhid" value="%{uhid}"></s:hidden>
			<s:hidden name="viewData" id="data" value="%{viewData}"></s:hidden>
			<s:hidden name="refToSpec" id="refToSpec" value="%{refToSpec}"></s:hidden>
			<s:hidden name="refBySpec" id="refBySpec" value="%{refBySpec}"></s:hidden>
			<s:hidden name="refBy" id="refBy" value="%{refBy}"></s:hidden>
			
			

				<div class="newColumn" align="center">
				<div class="leftColumn" ><b>Nursing Unit:</b></div>
				<div class="rightColumn"  align="center">
					<s:textfield id="nursing_sms" name="nursing_sms" value="%{nursing_unit}"
						theme="simple" cssClass="button"  cssStyle="width:72%;height:26px;"/>
				</div>
			</div>
			
			
			<div class="newColumn" align="center">
				<div class="leftColumn" ><b>Nursing Unit Mobile:</b></div>
				<div class="rightColumn"  align="center">
					<s:textfield id="nursing_mob" name="nursing_mob" 
						theme="simple" cssClass="button"  cssStyle="width:72%;height:26px;"/>
				</div>
			</div>
			
			
			<div class="newColumn" align="center">
				<div class="leftColumn" ><b>Treating Doctor:</b></div>
				<div class="rightColumn"  align="center">
					<s:textfield id="adm_doc_sms" name="adm_doc_sms"  value="%{admdoc}"
						theme="simple" cssClass="button"  cssStyle="width:72%;height:26px;"/>
				</div>
			</div>
			
			
			<div class="newColumn" align="center">
				<div class="leftColumn" ><b>Treating Mobile:</b></div>
				<div class="rightColumn"  align="center">
					<s:textfield id="adm_doc_mob" name="adm_doc_mob"  value="%{admdoc_mob}"
						theme="simple" cssClass="button"  cssStyle="width:72%;height:26px;"/>
				</div>
			</div>
			
			
			<center>
				<img id="indicator23" src="<s:url value="/images/indicator.gif"/>"
					alt="Loading..." style="display: none" />
			</center>
			<div class="clear"></div>
			<center>
				<div class="type-button" style="margin-top: 10px;">
					<sj:submit targets="result1" clearForm="true" value="Send SMS"
						effect="highlight" effectOptions="{ color : '#FFFFFF'}"
						effectDuration="100" button="true" cssClass="submit"
						indicator="indicator23" onCompleteTopics="level1"
						onBeforeTopics="validate1" />
						 <sj:a
                        button="true" href="#" value="View" onclick="viewAction('viewActionPage');" 
                        >Close
                    </sj:a>
				</div>
				
			</center>

	</s:form>
</div>

<div id="viewActionPage" style="display: none;">

		<s:form id="sendsms" name="sendsms"
			namespace="/view/Over2Cloud/Critical" action="takeActionOnCritical"
			method="post" enctype="multipart/form-data" theme="simple">
			<div
				style="float: center; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
				<div id="errZone1" style="float: center; margin-left: 7px"
					align="center"></div>
			</div>
			
			
			<s:hidden name="ticketNo" id="ticketNo" value="%{ticketNo}"></s:hidden>
			<s:hidden name="bed" id="bed" value="%{bed}"></s:hidden>
				<s:hidden name="rid" id="rid" value="%{pid}"></s:hidden>
			<s:hidden name="uhid" id="uhid" value="%{uhid}"></s:hidden>
			<s:hidden name="viewData" id="data" value="%{viewData}"></s:hidden>
			<s:hidden name="refToSpec" id="refToSpec" value="%{refToSpec}"></s:hidden>
			<s:hidden name="refBySpec" id="refBySpec" value="%{refBySpec}"></s:hidden>
			<s:hidden name="refBy" id="refBy" value="%{refBy}"></s:hidden>
			
			
	 <div class="newColumn" align="center">
				<div class="leftColumn" ><b>Action:</b></div>
				<div class="rightColumn"  align="center">
					<s:select id="status1" name="status" list="%{statusMap}"
						theme="simple" cssClass="button" onchange="changeDiv(this.value,'snooze_comments','ignore_comments')" cssStyle="width:72%;height:26px;"/>
				</div>
			</div>

			<div id="infDiv">
				<s:if test="%{status=='Not Informed' || status=='OCC Park'}">
					<div class="newColumn" >
						<div class="leftColumn" ><b>Informed To:</b></div>
						<div class="rightColumn" >
							<s:radio label="assignId" name="assignId" list="#{'1':'Doctor','2':'Employee'}" value="1" theme="simple"  onchange="changeDivView(this.value,'docIdDiv','empIdDiv')"/>
						</div>
					</div>
					
					<div id="empIdDiv" style="display:none;">
						<div class="newColumn" >
						<span class="inpIds" style="display: none;">assign_to_id#InformedEmpID#T#sc,</span>
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
						<div class="leftColumn" ><b>Informed To Name:</b></div>
						<div class="rightColumn" >
							<s:textfield id="assign_to_name" name="assign_to_name"
								theme="simple" cssClass="textField" cssStyle="width:150px;"
								placeholder="Informed To Name" readonly="true" />
						</div>
					</div>
					
					</div>
					
					<div id="docIdDiv">
						<div class="newColumn" >
							<div class="leftColumn" ><b>Doctor ID:</b></div>
							<div class="rightColumn">
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
							<s:textfield id="mob_no" name="mob_no" theme="simple"
								cssClass="textField" placeholder="Enter Mobile No"
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
							<s:radio label="assignId" name="assignId" list="#{'1':'Doctor','2':'Employee'}" value="1" onchange="changeDivView(this.value,'seenDocDiv','seenEmpDiv')"/>
						</div>
					</div>
					<div id="seenDocDiv">
						<div class="newColumn" >
						<span class="spIds" style="display: none;">assign_to_emp_id#Doctor
								ID#D,</span>
							<div class="leftColumn" ><b>Doctor ID:</b></div>
							<div class="rightColumn">
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
									<sj:autocompleter id="assign_ref_to" name="assign_ref_to" list="#{'1':'Doctor','2':'Employee'}" selectBox="false" selectBoxIcon="false" 
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
							<span class="spIds" style="display: none;">assign_to_id#Emp
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
						<span class="spIds" style="display: none;">assign_close_by#Assign
							Close By#D#,</span>
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
						<span class="spIds" style="display: none;">assign_desig#Assign
							Designation#D#,</span>
						<div class="leftColumn" ><b>Close By Designation:</b></div>
						<div class="rightColumn" >
							<span class="needed"></span>
							<s:select id="assign_desig" name="assign_desig"
								cssStyle="width:150px;"
								list="#{'-1':'Select Designation','Chairman':'Chairman','Director':'Director','Associate Director':'Associate Director','Consultant':'Consultant','Associate Consultant':'Associate Consultant','Attending Consultant':'Attending Consultant','Resident':'Resident','Medical Doctor':'Medical Doctor','Not Known':'Not Known'}"
								theme="simple" cssClass="textfield" cssStyle="width:72%;height:26px;"/>
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
					<span class="snpIds" style="display: none;">sn_upto_date#Park
						Date#Date#,</span>
					<div class="leftColumn" ><b>Park Till Date:</b></div>
					<div class="rightColumn" >
						<span class="needed"></span>
						<sj:datepicker name="sn_upto_date" id="sn_upto_date"
							cssStyle="width:125px;" Class="textField" size="15"
							displayFormat="dd-mm-yy" readonly="true" showOn="focus"
							Placeholder="Select Parked Till Date" />
					</div>
				</div>

				<div class="newColumn" >
					<span class="snpIds" style="display: none;">sn_upto_time#Park
						Time#Time#,</span>
					<div class="leftColumn" ><b>Park Till Time:</b></div>
					<div class="rightColumn" >
						<span class="needed"></span>
						<sj:datepicker name="sn_upto_time" id="sn_upto_time"
							cssStyle="width:125px;" timepickerOnly="false" timepicker="true"
							timepickerOnly="true" timepickerAmPm="false" Class="textField"
							size="15" displayFormat="dd-mm-yy" readonly="true" showOn="focus"
							Placeholder="Select Parked Till Time" />
					</div>
				</div>

				<div class="newColumn" >
					<span class="snpIds" style="display: none;">snooze_comments#Park Reason#D#sc,</span>
					<div class="leftColumn" ><b>Park Reason:</b></div>
					<div class="rightColumn" >
						<span class="needed"></span>
						<s:select 
                                      id="snooze_comments"
                                      name="snooze_comments" 
                                      list="{'No Data'}"  
                                      headerKey="-1"
                                      headerValue="Select Park Reason"
                                      cssClass="select"
                                      cssStyle="width:72%"
                                      theme="simple"
                                      >
                          </s:select>
					</div>
				</div>
				
				<div class="newColumn" >
						<span class="snpIds" style="display: none;">assign_to_id#InformedEmpID#T#sc,</span>
						<div class="leftColumn" ><b>Park By EmpID:</b></div>
						<div class="rightColumn" >
							<span class="needed"></span>
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
					
					<div class="newColumn" >
						<div class="leftColumn" ><b>Mobile No:</b></div>
						<div class="rightColumn" >
							<s:textfield id="mob_no" name="snooze_mob_no" theme="simple"
								cssClass="textField" placeholder="Enter Mobile No"
								cssStyle="width:150px;" />
						</div>
					</div>

			</div>
			<div id="ignoreDiv" style="display: none;">
				
				<div class="newColumn" >
					<span class="igpIds" style="display: none;">ignore_comments#Ignore Reason#D#sc,</span>
					<div class="leftColumn" ><b>Ignore Reason:</b></div>
					<div class="rightColumn" >
						<span class="needed"></span>
						<s:select 
                                      id="ignore_comments"
                                      name="ignore_comments" 
                                      list="{'No Data'}"  
                                      headerKey="-1"
                                      headerValue="Select Ignore Reason"
                                      cssClass="select"
                                      cssStyle="width:72%"
                                      theme="simple"
                                      >
                          </s:select>
					</div>
				</div>
				
				<div class="newColumn" >
						<span class="igpIds" style="display: none;">assign_to_id#InformedEmpID#T#sc,</span>
						<div class="leftColumn" ><b>Ignore By EmpID:</b></div>
						<div class="rightColumn" >
							<span class="needed"></span>
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
								cssClass="textField" placeholder="Enter Mobile No"
								cssStyle="width:150px;" />
						</div>
					</div>

			</div>
			<center>
				<img id="indicator23" src="<s:url value="/images/indicator.gif"/>"
					alt="Loading..." style="display: none" />
			</center>
			<div class="clear"></div>
			<center>
				<div class="type-button" style="margin-top: 10px;">
					<sj:submit targets="result1" clearForm="true" value="Take Action"
						effect="highlight" effectOptions="{ color : '#FFFFFF'}"
						effectDuration="100" button="true" cssClass="submit"
						indicator="indicator23" onCompleteTopics="level1"
						onBeforeTopics="validate1" />
						
							 <sj:a
                        button="true" href="#" value="View" onclick="viewAction('smsPage');" 
                        >Send SMS
                    </sj:a>
				</div>
			</center> 
		</s:form>
		
		</div>
	</div>
</div>
</html>