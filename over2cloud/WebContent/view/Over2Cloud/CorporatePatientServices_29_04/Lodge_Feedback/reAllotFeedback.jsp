<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>

</head>
<SCRIPT type="text/javascript">
function manualReassignPage(caseManual)
{
	var subcat=$("#feedbackSubCatg").val();
	var flor=$('#location12').val();
	//var subDept=$('#subdept3').val();
//	alert("1:::"+subcat);
	//alert("12:::"+flor);
	//alert("3:::"+subDept);
	$("#confirmEscalationDialog").dialog('open');
	$("#confirmEscalationDialog").dialog({title:'Manual Reassign',width:300,height:300});
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/fetchAllotToManual.action?subCategory="+subcat+"&floor="+flor+"&caseManual="+caseManual,
		success : function(empData)
		{
	    	$("#confirmEscalationDialog").html(empData);
	    },
	    error : function () 
	    {
			alert("Somthing is wrong to get Data");
		}
	});
}
var ab;
$.subscribe('changeReassign', function(event,data)
{
	ab=event.originalEvent.ui.item.value;
	//alert(ab);
});
function setAllotTo()
{
	var allotVal= $("#allottoMan").val();
	/*$("#allotDiv2").show();
	$("#allotDiv1").hide();*/
	//$("#allotto").attr('title',ab);
	var a=ab;
	//alert("a::::: "+a);
	$("#allotID").val(allotVal);
	$("#allotName").val(a);	
	//$("#allotto").attr('name','');
	$("#confirmEscalationDialog").dialog('close');
	
}
</SCRIPT>

<body>
<div class="clear"></div>
<div class="secHead">Re-Assign To</div>

<s:hidden id="feedbackSubCatg" name="feedbackSubCatg" value="%{subCategory}"/>
<s:hidden id="location12" value="%{location}"/>
             <div class="newColumn">
        	 <div class="leftColumn" > <a href="#" onclick="manualReassignPage('case4');"><img alt="manual reassign" title="manual reassign" src="images/associate_activity.png"  /></a> Allot To:&nbsp;</div>
			<div class="rightColumn">
				<span class="needed"></span>
                  <s:hidden name="allotto"  id="allotID"/>
                  <s:textfield  id="allotName" readonly="true"  cssClass="textField" placeholder="Enter Data" />
			</div>
		   </div> 
	 		<div class="newColumn">
		       <div class="leftColumn">Reason:</div>
		            <div class="rightColumn">
		             <span class="needed"></span>
		                   <s:select name="reassignreason" id="reasonReassign" list="{'No Data'}" headerKey="-1"
		                             headerValue="Select Re-Assign" cssClass="select" />
		            </div>
            </div>
	
		    <div class="newColumn">
		       <div class="leftColumn">Remark:</div>
		            <div class="rightColumn">
		            
		                   <s:textfield name="reAssignRemark" id="reAssignRemark" placeholder="Enter Re-Assign Reason"  cssClass="textField" />
		            </div>
            </div>
            </body>
</html>