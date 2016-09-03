<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<html>
<head>
<script type="text/javascript"
	src="<s:url value="/js/criticalPatient/criticalValidation.js"/>"></script>
<STYLE type="text/css">
/* td.tdAlign {
	//padding: 5px;
}
 */
 :focus {
  background: white;
}
 
tr.color {
	background-color: #E6E6E6;
}

.ui-autocomplete-input {
	width: 112px;
	height: 26px;
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
			//resetForm();
			//onSearchData();
			//pullDDValueAfterLodge();
			//resetFormField(formone);
			//criticalTicketLodge();
		}, 10);
		setTimeout(function()
		{
			$("#result1").fadeOut();
			cancelButton();
			//resetForm();
			//onSearchData();
			//pullDDValueAfterLodge();
			//resetFormField(formone);
		//	criticalTicketLodge();
		
		}, 1000);
		resetForm('formone1');

		//criticalTicketLodge();
		//resetForm();
		//onSearchData();
	//	pullDDValueAfterLodge();
		//resetFormField(formone);
		
		//resetTaskType('taskTypeForm');
	});
	function cancelButton()
	{
		$("#takeActionGrid").dialog('close');
		$('#completionResult1').dialog('close');
		
	//	resetForm();
	
		onSearchData();
		getStatusCounter();
		criticalTicketLodge();
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
		
		/* $.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Referral/getReasonName.action?value="+value,
		    success : function(data) {
			//alert(JSON.stringify(data));
			//resetForm('formone1');
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
		*/
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
			$("#comments_Close-p").val("");
			$("#snooze_mob_no").val("");
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
			$("#mob_no_ignore").val("");
			$("#assign_to_id").val("");
			$("#assign_to_name").val("");
			$("#assign_to_emp_id").val("");
			$("#assign_ref_to").val("");
			$("#spec_info").val("");
			$("#comments_Close-p").val("");
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
			$("#mob_no1").val("");
			$("#ignore_comment").val("");
			$("#ignore_by_id").val("");
			$("#ignore_by").val("");
			$("#mob_no").val("");
			$("#assign_close_by").val("");
			$("#assign_desig").val("");
			$("#assign_to_name_inf").val("");
			$("#spec_info").val("");
			$("#assign_to_emp_id_widget").val("");
			$("#assign_to_emp_id").val("");
			$("#assign_to_id_inf").val("");
			$("#spec_seen").val("");
			

			
		}
	}
	function changeDivView(value,div1,div2)
	{
		
		//var spans = $('.kpIds');
		if(value=='2')
		{
			

			//spans.text(''); // clear the text
			//spans.hide(); // make them display: none
			//spans.remove(); // remove them from the DOM completely
			//spans.empty(); // remove all their content  removeClass
			 $("#AnpIds_doc_ID").addClass("AnpIds");
			 $("#dnpIds_doc_id").removeClass("dnpIds");
			 
			$("#ref_to_id_div").hide();
			$('#'+div2).show();
			$('#'+div1).hide();
			$("#assign_to_emp_id").val("NA");
			$("#assign_ref_to").val("");
			$("#assign_to_name_inf").val("");
			$("#assign_to_id_inf").val("");
			$("#assign_close_by").val("");
			$("#assign_to_emp_id").val("");
			$("#spec_seen").val("");
			$("#assign_to_emp_id_widget").val("");
			$("#assign_to_id_inf").clear();
			$("#assign_to_emp_id").clear();
			$("#assign_desig").val("");
			$("#comments_Close-p").val("");
			$("#assign_desig").val("");
			$("#assign_desig").val("");
			//$kpIds.removeClass();
			//spans.text('');
			//resetForm('formone1');
		}	
		else
		{
			
			$('#'+div1).show();
			$('#'+div2).hide();
			$("#assign_to_id").val("");
			$("#assign_to_name").val("");
			$("#assign_close_by").val("");
			$("#comments_Close-p").val("");
		//	spans.addClass('kpIds');
			 $("#dnpIds_doc_id").addClass("dnpIds");
			 $("#AnpIds_doc_ID").removeClass("AnpIds");
			
			
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

	function resetForm(formId) 
	{
		
		 

		 changeDivView('1','seenDocDiv','seenEmpDiv');
			changeDiv("Seen","snooze_comments","ignore_comments");
			 $('#'+formId).trigger("reset");
			 //document.getElementById("bhudutt").style.visibility = 'visible';
		 //document.getElementById("bhudutt").style.visibility = 'visible';
		
		$("#refto_id_div").show();
		$("#ref_to_id_div").hide();
		$("#refto_name_div").show();
		$("#ref_to_name_div").hide();
		$("#infDiv").show();
		
		
		
		
	}
	function viewData()
	{
		//alert($("#data").val());
		
		$("#viewData").html($("#data").val());
		var data= $("#dataInfo").val().split(',');
		$("#patient_mob").val(data[7]);
		$("#patient_mob1").val(data[7]);
		$("#patientname").val(data[6]);
		$("#doc_mob").val(data[10]);
		$("#docname").val(data[9]);


		//aarif
		var paritalInfo=$("#infoPartial").val();
		if(paritalInfo!=null && paritalInfo!="" && paritalInfo!="NA,NA")
			{
			//alert(""+partialData);
					var partialData= paritalInfo.split(',');
					var data= $("#dataInfo").val().split(',');
					$("#instruction").val(partialData[1]);
					$("#instruction").prop('readonly', true);
					//$("#authorization").prop('disabled', true);
					$("#comments_Close-p").val(partialData[0]);
					
					$("#statusChn").val("Close");
					$("#mobile").show();
					$("#patient_mob12").val(data[7]);
					
					$('#authorization option').remove();
					$('#authorization').append($("<option></option>").attr("value","Yes").text("Yes"));
			}
		
		
		
	}
	viewData();

//add on 12-02-2016 add no further action in case of OPD 
	function seek(value)
	{ 
		//alert("abc");
		//alert("va..."+value);
		if(value=="Yes")
			{
			$("#seek").show();
			$("#noseek").hide();
			$("#noFurtherAction").hide();
			$("#noResponseDiv").hide();
			$("#statusChn").val("Close-P");
			}
		else
			{
				$("#comment_seek_no").hide();
				if(value=="No Further Action Required")
				{
					$("#seek").hide();
					$("#noseek").hide();
					$("#noResponseDiv").hide();
					$("#noFurtherAction").show();
					$(".ui-button").css("left","0px");
					$("#statusChn").val("No Further Action Required");
				}
				else if(value=="No Response")
				{
					//alert("Inside No response");
					$("#noFurtherAction").hide();
					$("#seek").hide();
					$("#noseek").hide();
					$("#noResponseDiv").show();
					$("#statusChn").val("No Response");
				}
				else
				{
				$("#noseek").show();
				$("#seek").hide();
				$("#noResponseDiv").hide();
				$("#noFurtherAction").hide();
				$("#statusChn").val("Close");
				}
			}
	}
	
	
	//function seek(value)
	//{ 
		//if(value=="Yes")
			//{
		//	$("#seek").show();
			//$("#noseek").hide();
		//	}
	//	else
		//	{
			//$("#noseek").show();
		//	$("#seek").hide();
			//$("#comment_seek_no").hide();
		//	}
//	}
	
	
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
			namespace="/view/Over2Cloud/Critical" action="closeCriticalTicket"
			method="post" enctype="multipart/form-data" theme="simple">
			 <center>
		    <div style="float:center; border-color: black; background-color: rgb(255,99,71); 
		    color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
		    <div id="errZone1" style="float:center; margin-left: 7px"></div></div></center>
			
				<s:hidden name="patient_type" id="patient_type" value="%{patient_type}"></s:hidden>
					<s:hidden name="viewData" id="data" value="%{viewData}"></s:hidden>
				<s:hidden  id="dataInfo" value="%{data}"></s:hidden>
				<s:hidden name="rid" id="rid" value="%{pid}"></s:hidden>
			
			<div id="viewData"></div>
			<s:hidden name="infoPartial" id="infoPartial" value="%{infoPartial}"></s:hidden>
			
			<s:if test="%{patient_type=='IPD' || patient_type=='EM'}">

			<div class="newColumn" align="center">
			
				<div class="leftColumn" ><b>Action:</b></div>
				<div class="rightColumn"  align="center">
				
					<s:select id="status1" name="status" list="%{statusMap}"
						theme="simple" cssClass="button" onchange="changeDiv(this.value,'snooze_comments','ignore_comments')" cssStyle="width:72%;height:26px;"/>
				</div>
			</div>


			<div id="infDiv">
				<s:if test="%{status=='Informed'}">
					<div class="newColumn" >
						<div class="leftColumn" ><b>Closed By:</b></div>
						<div class="rightColumn" >
							<s:radio  id="assignIdInform" name="assignId"  list="#{'1':'Doctor','2':'Employee'}" value="1" theme="simple"  onchange="changeDivView(this.value,'docIdDiv','empIdDiv')"/>
						</div>
					</div>
					
					<div id="empIdDiv" style="display:none;">
						<div class="newColumn" >
						 <span id="AnpIds_doc_ID" style="display: none;">assign_to_id#InformedEmpID#T#sc,</span> 
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
							
									<div id="refto_id_div" style="display: block; ">
										<sj:autocompleter id="assign_to_emp_id"  list="doctorIdMap" selectBox="true" selectBoxIcon="true"
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
								<div id="refto_name_div" style="display: block;">
									<sj:autocompleter id="assign_ref_to" name="assign_to_name_inf" list="doctorNameMap" selectBox="true" selectBoxIcon="true" 
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
					
					<div class="newColumn" style="display: none;" >
					<span class="inpIds" style="display: none;">mob_no1#Mobile No#m,</span>
						<div class="leftColumn" ><b>Mobile No:</b></div>
						<div class="rightColumn" >
							<s:textfield id="mob_no1" name="mob_no" theme="simple"
								cssClass="textField" placeholder="Enter Mobile No"
								cssStyle="width:150px;" />
						</div>
					</div>
					<%-- <div class="newColumn" >
						<div class="leftColumn" ><b>Remark:</b></div>
						<div class="rightColumn" >
							<s:textarea id="comments" name="comments" theme="simple"
								cssClass="button" placeholder="Enter Remark"
								cssStyle="width:150px;" />
						</div>
					</div>
					 --%>
				</s:if>
			
			</div>
			<div id="snoozeDiv" style="display: none;">

				<div class="newColumn" >
					<span class="snpIds" style="display: none;">sn_upto_date#Park
						Date#Date#,</span>
					<div class="leftColumn" ><b>Park Till Date:</b></div>
					<div class="rightColumn" >
						<span class="needed"></span>
						<sj:datepicker name="sn_upto_date" id="sn_upto_date"
							 showOn="focus" timepickerOnly="true" timepickerGridHour="4"  displayFormat="dd-mm-yy" timepickerGridMinute="10" timepickerStepMinute="10"   cssClass="textField"
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
							timepickerOnly="true" timepickerAmPm="false" cssClass="textField"
							size="15"  readonly="true" showOn="focus"
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
                                      list="#{'Patient Not Present':'Patient Not Present'}"
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
						<%-- <span class="snpIds" style="display: none;">assign_to_id#InformedEmpID#T#sc,</span> --%>
						<div class="leftColumn" ><b>Park By EmpID:</b></div>
						<div class="rightColumn" >
							<%-- <span class="needed"></span> --%>
							<s:textfield id="snooze_by_id"  name="snooze_by_id" theme="simple"
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
								placeholder="Park By Name" readonly="false" />
						</div>
					</div>
					
					<div class="newColumn" >
						<div class="leftColumn" ><b>Mobile No:</b></div>
						<div class="rightColumn" >
							<s:textfield id="snooze_mob_no" name="snooze_mob_no" theme="simple"
								cssClass="textField" placeholder="Enter Mobile No"
								cssStyle="width:150px;" />
						</div>
					</div>
					
					<%-- <div class="newColumn" >
						<div class="leftColumn" ><b>Remark:</b></div>
						<div class="rightColumn" >
							<s:textfield id="sn_remarks" name="sn_remarks" theme="simple"
								cssClass="textField" placeholder="Enter Remark"
								cssStyle="width:150px;" />
						</div>
					</div>
 --%>
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
                                      list="#{'Ignore Order':'Ignore Order'}"   
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
						<%-- <span class="igpIds" style="display: none;">assign_to_id#InformedEmpID#T#sc,</span> --%>
						<div class="leftColumn" ><b>Ignore By EmpID:</b></div>
						<div class="rightColumn" >
							<%-- <span class="needed"></span> --%>
							<s:textfield id="ignore_by_id" name="ignore_by_id" theme="simple"
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
							<s:textfield id="mob_no_ignore" name="mob_no_ignore" theme="simple"
								cssClass="textField" placeholder="Enter Mobile No"
								cssStyle="width:150px;" />
						</div>
					</div>
<%-- 
			<div class="newColumn" >
						<div class="leftColumn" ><b>Remark:</b></div>
						<div class="rightColumn" >
							<s:textfield id="Ig_remarks" name="Ig_remarks" theme="simple"
								cssClass="textField" placeholder="Enter Remark"
								cssStyle="width:150px;" />
						</div>
					</div> --%>
			</div>
			
			</s:if>
			
			<s:else>
			<s:hidden id ="statusChn" name="status" value="Close-P"></s:hidden>
			<div class="newColumn" >
						<div class="leftColumn" ><b>Seek Authorization:</b></div>
						<div class="rightColumn" >
							<s:select label="authorization" name="authorization" id="authorization" list="#{'Yes':'Yes','No':'No','No Further Action Required':'No Further Action Required','No Response':'No Response'}" value="1" onchange="seek(this.value)" style="height:28px;"/>
						</div>
					</div>
			<div id="seek" style="display: block;">
			
			
			
					</div>
					<div id="noseek" style="display: none;">
					
					<div class="newColumn" align="center">
				<div class="leftColumn" ><b>Con-Call Doctor Name:</b></div>
				<div class="rightColumn"  align="center">
					<s:textfield id="docname" name="doc_name" 
						theme="simple" cssClass="button"  cssStyle="width:72%;height:26px;"/>
				</div>
			</div>
			
			<div class="newColumn" >
						<div class="leftColumn" ><b>Con-Call Doctor Mobile:</b></div>
						<div class="rightColumn" >
							<s:textfield id="doc_mob" name="doc_mob" maxlength="10"
						theme="simple" cssClass="button"  cssStyle="width:72%;height:26px;"/>
						</div>
					</div>
					
					
					
					<div class="newColumn" align="center">
				<div class="leftColumn" ><b>Patient Name:</b></div>
				<div class="rightColumn"  align="center">
					<s:textfield id="patientname" name="patient_name" 
						theme="simple" cssClass="button"  cssStyle="width:72%;height:26px;" readonly="true"/>
				</div>
			</div>
			<div class="newColumn" align="center">
				<div class="leftColumn" ><b>Patient Mobile:</b></div>
				<div class="rightColumn"  align="center">
					<s:textfield id="patient_mob12" name="patient_mob" maxlength="10"
						theme="simple" cssClass="button"  cssStyle="width:72%;height:26px;"/>
				</div>
			</div>
			
			<div class="newColumn" align="center">
				<div class="leftColumn" ><b>Send SMS:</b></div>
				<div class="rightColumn"  align="center">
					<s:checkbox id="send_sms" name="send_sms" 
						theme="simple" value="false" cssStyle="margin-top: 9px;"/>
				</div>
			</div>
					</div>
					
					<!-- field for OPD NO Forther Action Required -->
					
					
					<div id="noFurtherAction" style="display: none;">
						<div class="newColumn" >
						
							<div class="leftColumn" ><b>Doctor ID:</b></div>
							<div class="rightColumn">
							
									<div id="refto_id_div" style="display: block; ">
										<sj:autocompleter id="assign_to_emp_id"  list="doctorIdMap" selectBox="true" selectBoxIcon="true"
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
								<div id="refto_name_div" style="display: block;">
									<sj:autocompleter id="assign_ref_to" name="assign_to_name_inf" list="doctorNameMap" selectBox="true" selectBoxIcon="true" 
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
					<!-- NO RESPOINSE CASE FIEL ADDED  BY AARIF -->
					<div id="noResponseDiv" style="display: none;">
									<div class="newColumn">
						 	<div class="leftColumn" ><b>To Whom:</b></div>
						 	
							<div class="rightColumn">
						    		<s:textfield id="toWhome" name="toWhome" cssClass="textField" readonly="false" theme="simple" cssStyle="width:150px;"></s:textfield>
						    	</div>
						    
							</div>
					</div>
					
					<div id="mobile" style="display: none;">
				<div class="newColumn" align="center">
				<div class="leftColumn" ><b>Patient Mobile:</b></div>
				<div class="rightColumn"  align="center">
					<s:textfield id="patient_mob_sms" name="patient_mob_sms" maxlength="10"
						theme="simple" cssClass="button"  cssStyle="width:72%;height:26px;"/>
				</div>
			</div>
			<div class="newColumn" align="center">
				<div class="leftColumn" ><b>Send SMS:</b></div>
				<div class="rightColumn"  align="center">
					<s:checkbox id="send_sms" name="send_sms" 
						theme="simple" value="false" cssStyle="margin-top: 9px;"/>
				</div>
			</div>
			
			</div>
					
				<div id="comment_seek_no">
			<div class="newColumn" >
						<div class="leftColumn" ><b>Instruction:</b></div>
						<div class="rightColumn" >
							<s:textarea id="instruction" name="instruction" theme="simple"
								cssClass="button" placeholder="Enter Instruction"
								cssStyle="width:150px;"/>
						</div>
					</div></div>
			</s:else>
			<div class="newColumn" >
						<div class="leftColumn" ><b>Comment:</b></div>
						<div class="rightColumn" >
							<s:textarea id="comments_Close-p" name="comments" theme="simple"
								cssClass="button" placeholder="Enter Message"
								cssStyle="width:150px;" />
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
						/><!--
						
						
						
						--><%-- <sj:a  button="true" href="#" onclick="resetForm('formone1');">Reset</sj:a> --%>
				</div>
			</center>
		</s:form>
	</div>
</div>
</html>