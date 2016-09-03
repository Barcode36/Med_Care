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
tr.color {
	
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

.newColumnVertical{
	float:left; 
	width:100%;
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
		//	onSearchData();
			//pullDDValueAfterLodge();
			resetFormField(formone);
		}, 1000);
		//resetTaskType('taskTypeForm');
	});
	function cancelButton()
	{
	 	onSearchData();
	 	getStatusCounter();
	  	$("#takeActionGrid").dialog('close');
		$('#completionResult1').dialog('close');
		//resetForm();
		//
		//pullDDValueAfterLodge();
		//resetFormField(formone);
		criticalTicketLodge();
		onSearchData();
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

//aarif
// <span class="AnpIds" style="display: none;">assign_to_id#InformedEmpID#T#sc,</span> 
	
	function resetTaskType(formId)
	{
		$('#' + formId).trigger("reset");
	}

	
	function viewData()
	{
		//alert($("#data").val());
		
	//	$("#viewData").html($("#data").val());
		var paritalInfo=$("#infoPartial").val();
		if(paritalInfo!=null && paritalInfo!=""){
					var partialData= paritalInfo.split(',');
					$("#nursing_name").val(partialData[6]);
					$("#nursing_mob").val(partialData[0]);
					$("#nursing_mob_action").val(partialData[0]);
					$("#adm_doc_mob").val(partialData[3]);
					$("#doc_mob").val(partialData[3]);
					$("#doc_name_val").val(partialData[4]);
					//alert(partialData[4]+",,,,,,  "+$("#doc_name_val").val());
					$("#nursing_comment").val(partialData[1]);
					$("#doc_comment").val(partialData[5]);
					if($("#nursing_mob_action").val()!=null && $("#nursing_mob_action").val()!="NA" && $("#nursing_mob_action").val()!="")
					{
						$('#nursecheck').prop('checked', true);
						shownursing('nurse');
						
						}
					if($("#doc_mob").val()!=null && $("#doc_mob").val()!="NA" && $("#doc_mob").val()!="")
					{
						$('#doccheck').prop('checked', true);
						shownursing('doc');
						
						}
			}
		else{
		var data= $("#dataInfo").val().split(',');
		$("#patient_mob").val(data[7]);
		$("#patient_mob1").val(data[7]);
		$("#patientname").val(data[6]);
		$("#adm_doc_mob").val(data[10]);
		$("#adm_doc_sms").val(data[9]);
		$("#nursing_sms").val(data[11]);
		$("#test_type1").val(data[16]);
		$("#test_type").val(data[16]);
		$("#nursing_name").val(data[11]);
		
		$("#doc_name_val").val(data[9]);
		$("#doc_mob").val(data[10]);
		}
	}
	//viewData();
	
	
	
	function viewAction(value)
	{
		//alert(value);
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
	function shownursing(id)
	{
	var nurse=$("#nursecheck").is(":checked");
	var doc=$("#doccheck").is(":checked");
	
	if(nurse==true && doc==true)
		{
		  $("#nurreson").hide();
		   $("#docreson").hide();
		   $("#nurse").show();
		   $("#doc").show();
		   
		  /*  document.getElementById("pIdsss_nurse_mob").className = "pIdsss";
		   document.getElementById("pIdsss_doc_mob").className = "pIdsss"; */
		   $("#pIdsss_nurse_mob").addClass("pIdsss");
		   $("#pIdsss_doc_mob").addClass("pIdsss");
		  
		}
	else
		{
	  if(id=="nurse" )
		  {
		   if(nurse==true )
			   {
			 
			   $("#nurse").show();
			   $("#nurreson").hide();
			   $("#docreson").show();
			  // document.getElementById("pIdsss_nurse_mob").className = "pIdsss";
			   $("#pIdsss_nurse_mob").addClass("pIdsss");
			   }
		   else
			   {
			   $("#nurse").hide();
			   $("#nurreson").show();
			   $("#pIdsss_nurse_mob").removeClass("pIdsss");
			   }
		  }
	  if(id=="doc")
	  {
	   if(doc==true)
		   {
		 
		   $("#doc").show();
		   $("#nurreson").show();
		   $("#docreson").hide();
		  // document.getElementById("pIdsss_doc_mob").className = "pIdsss";
		   $("#pIdsss_doc_mob").addClass("pIdsss");
		   }
	   else
		   {
		   $("#docreson").show();
		   $("#doc").hide();
		   $("#pIdsss_doc_mob").removeClass("pIdsss");
		   }
	  }
		}
	}
	
	function fetchDetailsData(docID) {
			$.ajax({
				type : "post",
				url : "view/Over2Cloud/Critical/fetchDetailsData.action?docID=" + docID,// + "&dataFor=" + dataFor + "&patType=" +$("#patient_status").val(),
				success : function(data) {
				$("#doc_name_val").val(data[0].name);
				},
				error : function() {
					alert("error");
				}
			});
	}


	function fetchDetailsEmp(uhid) {
		var divId='nursing_name1';
		dataFor = 'emp';
		$.ajax({
			type : "post",
			url : "view/Over2Cloud/Critical/fetchDetails.action?uhid=" + uhid + "&dataFor=" + dataFor + "&patType='EMP'",
			success : function(data) {
			if(data[0].fName==undefined)
			{
				$("#" + divId).val('NA');
			}
			else
			{
				$("#" + divId).val(data[0].fName+ " " + data[0].lName);
			}
			},
			error : function() {
				alert("error");
			}
		});
}

	
	//setDocData("assign_to_emp_id_widget","assign_ref_to_widget");
</script>
</head>

<sj:dialog
          id="viewTest"
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

<div style="float: left; padding: 5px 0%; width: 100%;">

	<div class="border">

		<sj:dialog id="completionResult1" showEffect="slide"
			hideEffect="explode" autoOpen="false" 
			cssStyle="overflow:hidden;text-align:center;margin-top:10px;"
			modal="true">
			<div id="result1"></div>
		</sj:dialog>
			
		<div id="sms">
		<s:form id="sendsms" name="sendsms"
			namespace="/view/Over2Cloud/Critical" action="takeBulkActionOnCritical"
			method="post" enctype="multipart/form-data" theme="simple">
			<div
				style="float: center; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
				<div id="errZone5" style="float: center; margin-left: 7px"
					align="center"></div>
			</div>
			
				<s:hidden name="sendsms" value="sendsms"/>
				<s:hidden name="complainIds" value="%{complainIds}"/>
				
				
				<s:hidden name="viewData" id="data" value="%{viewData}"></s:hidden>
				<s:hidden name="dataInfo" id="dataInfo" value="%{data}"></s:hidden>
				<s:hidden name="infoPartial" id="infoPartial" value="%{infoPartial}"></s:hidden>
				
			

				<div class="newColumn" align="center">
				<div class="leftColumn" ><b>Nursing Unit:</b></div>
				<div class="rightColumn"  align="center">
					<s:textfield id="nursing_sms" name="nursing_sms" 
						theme="simple" cssClass="button"  cssStyle="width:72%;height:26px;"/>
				</div>
			</div>
			
			
			<div class="newColumn" align="center">
				<div class="leftColumn" ><b>Nursing Unit Mobile:</b></div>
				<span class="pIdss" style="display: none;">nursing_mob#Nursing Mobile#T#m,</span>
				<div class="rightColumn"  align="center">
				<span class="needed"></span>
					<s:textfield id="nursing_mob" name="nursing_mob" 
						theme="simple" cssClass="button"  cssStyle="width:72%;height:26px;" maxlength="10"/>
				</div>
			</div>
			
			
			<div class="newColumn" align="center">
				<div class="leftColumn" ><b>Treating Doctor:</b></div>
				<div class="rightColumn"  align="center">
					<s:textfield id="adm_doc_sms" name="adm_doc_sms"  
						theme="simple" cssClass="button"  cssStyle="width:72%;height:26px;" />
				</div>
			</div>
			
			
			<div class="newColumn" align="center">
				<div class="leftColumn" ><b>Treating Mobile:</b></div>
				<!--<span class="pIdss" style="display: none;">adm_doc_mob#Treating Mobile#T#m,</span>
				--><div class="rightColumn"  align="center">
				<!--<span class="needed"></span>
					--><s:textfield id="adm_doc_mob" name="adm_doc_mob"  
						theme="simple" cssClass="button"  cssStyle="width:72%;height:26px;" maxlength="10"/>
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
						onBeforeTopics="validate5" />
						 <sj:a
                        button="true" href="#" value="View" onclick="viewAction('viewActionPage');" 
                        >Close
                    </sj:a>
				</div>
				
			</center>

	</s:form>
</div>

<div id="viewActionPage" style="display: none;">

		<s:form id="takeAction" name="takeAction"
			namespace="/view/Over2Cloud/Critical" action="takeBulkActionOnCritical"
			method="post" enctype="multipart/form-data" theme="simple">
			<div
				style="float: center; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
				<div id="errZone3" style="float: center; margin-left: 7px"
					align="center"></div>
			</div>
		
			
			<s:hidden name="ticketNo" id="ticketNo" value="%{ticketNo}"></s:hidden>
			<%-- <s:hidden name="test_type" id="test_type1" value="%{test_type}"></s:hidden> --%>
			<s:hidden name="test_type" id="test_type1" ></s:hidden>
			<s:hidden name="complainIds" value="%{complainIds}"/>
			<s:hidden name="viewData" id="data" value="%{viewData}"></s:hidden>
			<s:hidden name="dataInfo" id="dataInfo" value="%{data}"></s:hidden>
			<s:if test="%{patient_type=='IPD'}">
			
			</s:if>
			<table  id="outerTable" style="width: 100%">
			
			
			<tr>
			
			<td>
					<div class="newColumnVertical" align="center">
				<div class="leftColumn" ><b>Nursing Unit:</b></div>
				<div class="rightColumn"  align="center" align="center" style="margin-top: 8px;">
					<s:checkbox name="nursecheck" id="nursecheck" onclick="shownursing('nurse');"/>
				</div>
			</div>
			</td>
			
					<td>
							<div class="newColumnVertical" align="center">
				<div class="leftColumn" ><b>Treating Doctor:</b></div>
				<div class="rightColumn"  align="center" style="margin-top: 8px;"  >
					<s:checkbox name="doccheck" id="doccheck" onclick="shownursing('doc');"/>
				</div>
			</div>
					</td>
			
			</tr>
			
			
			</table>
			
			
			<table  id="outerTable" style="width: 100%">
					<tr>
						<td id="abcd" style="width: 50%"><table  id="innerTable" style="width: 100%">
			<tr><td>
			<div id="nurse" style="display: none;">
			<table id="innerTable" style="width: 100%">
			
			<tr><td>
			<div class="newColumnVertical" align="center">
				<div class="leftColumn" ><b>Employee ID:</b></div>
				<div class="rightColumn"  align="center">
					<s:textfield id="nursing_uhid"
						theme="simple" cssClass="button"  cssStyle="width:72%;height:26px;"
						onblur="fetchDetailsEmp(this.value); "/>
				</div>
			</div></td></tr>
			
			<tr><td>
			<div class="newColumnVertical" align="center">
				<div class="leftColumn" ><b>Nursing Name:</b></div>
				<div class="rightColumn"  align="center">
					<s:textfield id="nursing_name1" name="nursing_name" 
						theme="simple" cssClass="button"  cssStyle="width:72%;height:26px;"/>
				</div>
			</div></td></tr>
			<tr><td>
			<div class="newColumnVertical" align="center">
				<div class="leftColumn" ><b>Nursing Mobile:</b></div>
				<div class="rightColumn"  align="center">
					<s:textfield id="nursing_mob_action" name="nursing_mob" 
						theme="simple" cssClass="button"  cssStyle="width:72%;height:26px;"  maxlength="10"/>
				</div>
			</div></td></tr>
			
			<tr><td>
			
			<div class="newColumnVertical" align="center">
				<div class="leftColumn" ><b>Nursing Comment:</b></div>
				<div class="rightColumn"  align="center">
					<s:textfield id="nursing_comment" name="nursing_comment" 
						theme="simple" cssClass="button"  cssStyle="width:72%;height:26px;"/>
				</div>
			</div></td></tr></table>
			</div></td></tr>
			<tr><td>
			<div id="docreson" style="display: none;">
			<div class="newColumnVertical" align="center">
				<div class="leftColumn" ><b>Doctor  Not Intemate Reason:</b></div>
				<div class="rightColumn"  align="center">
					<s:textarea id="doc_reason" name="doc_reason" 
						theme="simple" cssClass="button"  cssStyle="width:72%;height:26px;"/>
				</div>
			</div>
			</div></td></tr>
			</table>
						
						
						
						</td><td id="abcd" style="width: 50%">
								<table  id="innerTable" style="width: 100%"><tr><td>
							
						<div id="doc" style="display: none;">
						<table id="innerTable" style="width: 100%">
						
						<tr><td>
			<div class="newColumnVertical" align="center">
				<div class="leftColumn" ><b>Doctor ID:</b></div>
				<div class="rightColumn"  align="center">
					<s:textfield id="doc_id"  
						theme="simple" cssClass="button"   cssStyle="width:72%;height:26px;"
						onblur="fetchDetailsData(this.value); "/>
				</div>
			</div></td></tr>
			
						<tr><td>
			<div class="newColumnVertical" align="center">
				<div class="leftColumn" ><b>Doctor Name:</b></div>
				<div class="rightColumn"  align="center">
					<s:textfield id="doc_name_val" name="doc_name" 
						theme="simple" cssClass="button"   cssStyle="width:72%;height:26px;"/>
				</div>
			</div></td></tr>
			
				<tr><td>
			<div class="newColumnVertical" align="center">
				<div class="leftColumn" ><b>Doctor Mobile:</b></div>
				<div class="rightColumn"  align="center">
				<div class="rightColumn"  align="center">
					<s:textfield id="doc_mob" name="doc_mob" 
						theme="simple" cssClass="button"   cssStyle="width:147%;height:26px;" maxlength="10"/>
				</div>
			</div></td></tr>
			<tr><td>
			<div class="newColumnVertical" align="center">
				<div class="leftColumn" ><b>Doctor Comment:</b></div>
				<div class="rightColumn"  align="center">
					<s:textfield id="doc_comment" name="doc_comment" 
						theme="simple" cssClass="button"  cssStyle="width:72%;height:26px;" />
				</div>
			</div></td></tr></table>
			</div></td></tr>
			<tr><td>
			<div id="nurreson" style="display: none;">
			<div class="newColumnVertical" align="center">
				<div class="leftColumn" ><b>Nurse Not Intemate Reason:</b></div>
				<div class="rightColumn"  align="center">
					<s:textarea id="nurse_reason" name="nurse_reason" 
						theme="simple" cssClass="button"  cssStyle="width:72%;height:26px;"/>
				</div>
			</div>
			</div></td></tr>
						
						</table></td>
				</tr>
			</table>
			
			
		
			
			
			
			
			
			
			
	
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
						onBeforeTopics="validate3" /><!--
						
							 --><sj:a
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