<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
 <script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<style type="text/css">
.rightColumn
{
	width: 30%;
}
.newColumn
{
	width: 100%;
	height: 51px;
}


</style>

<script type="text/javascript">
function manual1(caseManual)
{
	manualReassignPage(caseManual);
}
function getCallPage()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/beforeFeedViaCall.action?feedStatus=call&dataFor=HDM",
	    success : function(feeddraft) {
       $("#"+"data_part").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function resetForm(formId)
{
	$('#'+formId).trigger("reset");
	 $("#textDepartment").css('display','none');  
	 $("#textDepartmentauto").attr('name','todept').css('display','block');
	 $("#textDepartmentId").attr('name','');
	 $("#normalSubdept").html("deptName#To Department#D#,");
	 $("#ddSubdept").html("deptName#To Department#D#,");
	 $("#room").val('');
	 $("#subCatg").val('');
	 $("#catId").hide();
	 $("#catId1").show();
}

function viewActivityBoard()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Activity_Board/viewActivityBoardHeader.action",
	    success : function(feeddraft) {
       $("#"+"data_part").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}
var a=0;
$.subscribe('changeFlag', function(event,data)
{
	//alert("INBSIODE change flag");
       a=1;
});
$.subscribe('changeFlag1', function(event,data)
{
	   a=2;
	  // alert("INBSIODE change flag 11111");
});

$.subscribe('changeGrid', function(event,data)
{
	//onloadData();
	//onloadTicketLodge();
});
$.subscribe('getSubFloorAndAllotTODetail', function(event,data)
		{
	//getSubFloorOtherDetail($("#room").val());
	fetchAllotedTo('tosubdept',data.value,'allotto','floorID',a);
	 	});


</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
  <div class="list-icon">
	<div class="head">Ticket Lodge</div>  
	          	 
</div>  
<div >
<s:form id="formone" name="formone" action="FeedbackViaOnlineNewAction"  theme="css_xhtml"  method="post" enctype="multipart/form-data" >
<s:hidden name="viaFrom" value="online"></s:hidden>
<s:hidden name="pageFor"  value="SD"></s:hidden>
<s:hidden name="subCategory_extra" id="test"></s:hidden>
		<center>
			<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
	             <div id="errZone" style="float:center; margin-left: 7px"></div>        
	        </div>
        </center>    
   
	 <span id="normalSubdept" class="pIds" style="display: none; ">room#Room#D#,</span>
     <span id="ddSubdept" class="ddPids" style="display: none; ">room#Room#D#,</span>
     <div class="clear"></div>
	 <div class="newColumn">
         <div class="leftColumn" style="margin-left: -9%;margin-top:2%;">Room No:&nbsp;</div>
			<div class="rightColumn">
				<span class="needed" style="margin-top:10%;" ></span>
			        <sj:autocompleter
					 id="room"
					name="location"
					 list="allRoomNo"
				 	selectBox="true"
					selectBoxIcon="true"
					 onSelectTopics="changeFlag,getSubFloorAndAllotTODetail"
					 
				 	/>
                  
			</div>
	 </div>
	   		 <span id="normalSubdept" class="pIds" style="display: none; ">subCatg#Sub Category#D#,</span>
       		 <span id="ddSubdept" class="ddPids" style="display: none; ">subCatg#Sub Category#D#,</span>
       <div class="newColumn"> 
    	<div class="leftColumn" style="margin-left:-9%;margin-top:2%">Sub-Category:&nbsp;</div>
	        <div class="rightColumn">
	             <span class="needed" style="margin-top:10%;"></span>
	          <sj:autocompleter
					 id="subCatg"
					name="subCategory"
					 list="subCategList"
				 	selectBox="true"
					selectBoxIcon="true"
					onSelectTopics="changeFlag1,getFeedBreifViaAjax,getSubFloorAndAllotTODetail"
					/>
                  
             </div>
         </div>
       
    <div class="clear"></div>
    
       
       <div class="newColumn">
	      <div class="leftColumn" style="margin-left: -9%;margin-top:2%;">Category:&nbsp;</div>
			<div class="rightColumn">
			 	<span class="needed" style="margin-top:10%;"></span>
		      	 <div id="catId" style="display: none;">
             	  <s:textfield   id="category12" style="margin-top:10%;width:166%;"   readonly="true" cssClass="textField" placeholder="Enter Data" onchange="Reset('.pIds');"/>
             	</div> 	
             	<div id="catId1">
             	<sj:autocompleter
					id="cate"
					name="category"
					list="{'No Data'}"
				 	selectBox="true"
					selectBoxIcon="true"
					onSelectTopics="getSubCategory"
					/>
             	</div>
             </div>
            </div>
             <div id="textDepartment" class="newColumn" style="display: none;">
	      <div class="leftColumn" style="margin-left: -9%;margin-top:2%;">Department:&nbsp;</div>
			<div class="rightColumn">
		 	 <span class="needed" style="margin-top:10%;"></span>
		 	 <s:hidden id="deptName" name="todept"></s:hidden>
             	<s:textfield id="textDepartmentval"  style="margin-top:10%;width:161%;" cssClass="textField" onchange="Reset('.pIds');" readonly="true"/>
             </div>
            </div> 
    	           <div class="newColumn" id="textDepartmentauto">
	      			<div class="leftColumn" style="margin-left:-9%;margin-top:2%;">Department:&nbsp;</div>
					        <div class="rightColumn">
					           <span class="needed" style="margin-top:10%;"></span>
				                   <span id="ddSubdept" class="ddPids" style="display: none; ">todept#To Department#D#,</span>
              
									    <sj:autocompleter
										id="todept"
										name="todept"
										list="serviceDeptList"
									 	selectBox="true"
										selectBoxIcon="true"
										onChangeTopics="autocompleteChange"
										onFocusTopics="autocompleteFocus"
										onSelectTopics="getcategory"
										/>
                           </div>
                     </div>
   			 
    		<div class="clear"></div>
           <div class="newColumn" style="margin-bottom: 40px;">
	      <div class="leftColumn" style="margin-left: -9%;margin-top:2%;">Feedback Brief:&nbsp;</div>
			<div class="rightColumn">
		 		 <span class="needed" style="margin-top:10%;">
			 </span>
		         	  <s:textarea name="feed_brief"  id="feed_brief" style="margin-top:10%;width:160%;"  cssClass="textField" placeholder="Enter Data" onchange="Reset('.pIds');"/>
             </div>
            </div> 
      <div class="clear"></div>
      <div class="newColumn">
         <div class="leftColumn" style="margin-left: -9%;margin-top:2%;"> <a href="#" onclick="manual1('Case4');"><img alt="Manual Reassign" title="Manual Reassign" src="images/associate_activity.png"  /></a> Allot To:&nbsp;</div>
			<div class="rightColumn">
				<span class="needed"style="margin-top:10%;"></span>
				 <div id="allotDiv2" >
                  <s:hidden name="allotto"  id="allotto1"/>
                  <s:textfield  id="allotto3" readonly="true" style="margin-top:10%;width:166%;"  cssClass="textField" placeholder="Enter Data" onchange="Reset('.pIds');"/>
                 
                  </div>
			</div>
	 </div>    
             
     <s:hidden id="tosubdept" name="tosubdept"/>
     	<%-- <div class="newColumn">
	       	<div class="rightColumn" style="margin-left:40px">
	     		<div style="width: auto; float: left; text-align: left; line-height:25px; margin-right: 10px;margin-left: 41px;">SMS:</div><div style="width: auto; float: left; text-align: left;  line-height:25px; margin-right: -9px; vertical-align: middle; padding-top: 5px;"><s:checkbox name="recvSMS" id="recvSMS" value="true"></s:checkbox></div>
	     		<div style="width: auto;   text-align: left; line-height:25px; margin-right: 10px; margin-left: 130px;">Email:</div><div style="width: auto; float: left; text-align: left;  line-height:25px; margin-right: 10px; vertical-align: middle; margin-top: 5px;margin-left: 95px;"><s:checkbox name="recvEmail" style="margin-left: 80px;
				margin-top: -24px;" id="recvEmail"></s:checkbox></div>
	     	</div>
       </div> --%>
      <s:token name="token"></s:token> 
     <div class="clear"></div>
	 <div class="fields" align="center">
				<ul>
				<li class="submit" style="background: none;">
					<div class="type-button">
					<div id="ButtonDiv">
	                <sj:submit 
	                        id="onlineSubmitIdHDM"
	                        targets="confirmEscalationDialog" 
	                        clearForm="true"
	                        value="Register" 
	                        button="true"
	                        cssClass="submit"
	                        onBeforeTopics="validateOnlineHDM"
	                        
	                        onCompleteTopics="beforeFirstAccordian"
	                        cssStyle="margin-left: -59px;margin-top: 12px"
	                        
				            />
				    </div>
				            <sj:a cssStyle="margin-left: 120px;margin-top: -28px;" button="true" href="#" onclick="onloadTicketLodge(); ">Reset</sj:a>
				      </div>
	               </li>
				</ul>
				</div>
				
			    <center>
			         <sj:dialog id="confirmEscalationDialog" autoOpen="false" closeOnEscape="true" modal="true" title="Success Message" width="700" height="200" showEffect="slide" hideEffect="explode" position="['center','center']"></sj:dialog>
			         
			       <sj:dialog id="reassign" autoOpen="false"  closeOnEscape="true" modal="true" title="Success Message" width="700" height="200" showEffect="slide" hideEffect="explode" position="['center','center']"></sj:dialog>
                </center>
</s:form>
</div>
</div>
</body>

</html>