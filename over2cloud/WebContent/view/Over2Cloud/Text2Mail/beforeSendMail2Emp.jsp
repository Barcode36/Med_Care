<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
<script type="text/javascript" src="<s:url value="/js/common/commonvalidation.js"/>"></script>
<SCRIPT type="text/javascript">

$.subscribe('level', function(event,data)
		{
			
			 setTimeout(function(){ $("#result").fadeIn(); }, 10);
			 setTimeout(function(){ $("#result").fadeOut();
			 cancelButton();
			 },4000);
			 resetTaskType('formone');
		});
		function cancelButton()
		{
			
			
			$('#takeActionGrid1').dialog('close');
			$('#completionResult').dialog('close');
			 
			viewpullReport();
		}
	$.subscribe('level1', function(event, data) {
		setTimeout( function() {
			$("#leadgntion").fadeIn();
		}, 10);
		setTimeout( function() {
			$("#leadgntion").fadeOut();
		}, 4000);
		$('select').find('option:first').attr('selected', 'selected');
	});

	function viewpullReport()
	{
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Text2Mail/beforePullReportViewHeaderManual.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}


	function reset(formId) {
		  $("#"+formId).trigger("reset"); 
		}
	
	
	function getEmpAvailabilty(divId, data)
	{

	    var regLevel=$("#regLevel").val();
		$.ajax({
			type :"post",
			url :"view/Over2Cloud/Text2Mail/empGetbyDept4Mail.action?dept="+data,
			success : function(empData){
			$('#'+divId+' option').remove();
			$('#'+divId).append($("<option></option>").attr("value",-1).text("Select Employee"));
	    	$(empData).each(function(index)
			{
			   $('#'+divId).append($("<option></option>").attr("value",this.id).text(this.name));
			});
		    },
		    error : function () {
				alert("Somthing is wrong to get Data");
			}
		});
	    
	}

	
</script>

</head>
<sj:dialog
          id="completionResult"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title="Confirmation Message"
          cssStyle="overflow:hidden;text-align:center;margin-top:10px;"
          modal="true"
          width="400"
          height="150"
          draggable="true"
    	  resizable="true"
    	   buttons="{ 
    		'Close':function() { cancelButton(); } 
    		}" 
          >
          <div id="result"></div>
</sj:dialog>
<body>

	
	<div class="container_block">
		<div style="float: left; padding: 20px 1%; width: 98%;">
			<div class="border">
					<div class="form_inner" id="form_reg" style="margin-top: 10px;">
						<s:form id="formone" name="formone" namespace="/view/Over2Cloud/Text2Mail" action="sendMailToEmpT2M" theme="simple" method="post" enctype="multipart/form-data">
						<s:hidden name="fromDate122" value="%{fromDate122}" />
						<s:hidden name="toDate122" value="%{toDate122}" />
						<s:hidden name="cons" value="%{cons}" />
						<s:hidden name="kws" value="%{kws}" />
							<div style="float: left; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
								<div id="errZone" style="float: left; margin-left: 7px"></div>
							</div>
							<br/>
							
							<span id="mandatoryFields" class="pIds" style="display: none; ">dept#Department#D#,</span>
							 <div class="newColumn">
								<div class="leftColumn1">Department:</div>
								
								<div class="rightColumn1">
								
                                <span class="needed"></span>
                                <s:select 
	                              id="dept"
	                              name="dept" 
	                              list="allDeptName1"
	                              headerKey="-1"
	                              headerValue="Select" 
	                              cssClass="select"
                                  cssStyle="width:82%"
                                  onchange="getEmpAvailabilty('emp',this.value);"
                                  
	                              >
                               </s:select>
                               </div>
                      </div> 
                      
                      <span id="mandatoryFields" class="pIds" style="display: none; ">emp#Employee#D#,</span>
                      <div class="newColumn">
								<div class="leftColumn1">Employee:</div>
								<div class="rightColumn1">
                              <span class="needed"></span>
                                <s:select 
	                              id="emp"
	                              name="emp" 
	                              list="allDeptName1"
	                              headerKey="-1"
	                              headerValue="Select" 
	                              cssClass="select"
                                  cssStyle="width:82%"
	                              >
                               </s:select>
                               </div>
                      </div>
							
							
							
							<div class="clear"></div>
   							<div class="fields">
							<div style="width: 100%; text-align: center; padding-bottom: 10px;">
								<sj:submit 
								     targets="result" 
								     clearForm="true"
								     value="  Send  " 
								     effect="highlight"
								     effectOptions="{ color : '#222222'}"
								     effectDuration="100"
								     button="true"
								     onBeforeTopics="validate"
								     onCompleteTopics="level"
								     cssClass="submit"
								     
							     />
							 
							</div>
						</div>
						<div class="clear"></div>
						<sj:div id="leadgntion"  effect="fold">
		                    <div id="leadresult"></div>
		                </sj:div>
						</s:form>
					</div>
			</div>
		</div>
	</div>
</body>
</html>
