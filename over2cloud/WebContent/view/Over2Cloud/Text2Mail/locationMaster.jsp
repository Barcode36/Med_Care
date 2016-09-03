<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<style type="text/css">
.user_form_input {
	margin-bottom: 10px;
}
</style>
<html>
<head>

<script src="<s:url value="/js/common/commonvalidation.js"/>"></script>

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
			
			$('#completionResult').dialog('close');
			 
			 viewLocation();
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

	function viewLocation()
	{
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Text2Mail/beforeLocationView.action",
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
	<div class="list-icon">
<div class="head">Location</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div>
</div>
	
	<div class="container_block">
		<div style="float: left; padding: 20px 1%; width: 98%;">
			<div class="border">
					<div class="form_inner" id="form_reg" style="margin-top: 10px;">
						<s:form id="formone" name="formone" namespace="/view/Over2Cloud/Text2Mail" action="addLocation" theme="simple" method="post" enctype="multipart/form-data">
							<div style="float: left; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
								<div id="errZone" style="float: left; margin-left: 7px"></div>
							</div>
							<br/>
							
							<s:iterator value="configKeyTextBox" status="counter">
								<s:if test="%{mandatory}">
	                      			<span id="mandatoryFields" class="pIds" style="display: none;"><s:property value="%{key}" />#<s:property value="%{value}" />#<s:property value="%{colType}" />#<s:property value="%{validationType}" />,</span>
	                 			</s:if>
	                 			
	                 			<s:if test="#counter.odd">
	                 				<div class="newColumn">
	                 					<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	                 					<div class="rightColumn1">
	                 						<s:if test="%{mandatory}"><span class="needed"></span>
		                 					</s:if>
	   										<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
										</div>
	                 				</div>
	                 			</s:if>
	                 			<s:else>
	                 				<div class="newColumn">
		                 				<div class="leftColumn1"><s:property value="%{value}" />:</div>
										<div class="rightColumn1">
										<s:if test="%{mandatory}">
	                 						<span class="needed"></span>
	                 					</s:if>
										
											<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" />
										</div>
									</div>
	                 			</s:else>
							</s:iterator>
							<div class="clear"></div>
							<s:iterator value="leadGenerationDropDown" status="counter">
								<s:if test="%{mandatory}">
									<span id="mandatoryFields" class="pIds" style="display: none;"><s:property value="%{key}" />#<s:property value="%{value}" />#<s:property value="%{colType}" />#<s:property value="%{validationType}" />,</span>
								</s:if>
							</s:iterator>
							<div class="clear"></div>
							<s:if test="%{ddTrue != null}">
								<div class="newColumn">
								<div class="leftColumn1"><s:property value="%{starRating}" /> : </div>
								<div class="rightColumn1">
									<s:if test="%{ddTrue == 'true'}">
										<span class="needed"></span>
									</s:if>
									<s:select 
										id="starRating" 
										name="starRating"
										list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5'}"
										headerKey="-1" 
										headerValue="--Select Rating--"
										cssClass="textField"
										cssStyle="margin:0px 0px 10px 0px">
									</s:select>
								</div>
								<div class="newColumn"></div>
								</div>
							</s:if>
							<s:if test="%{sourceTrue != null}">
								<div class="newColumn">
									<div class="leftColumn1"><s:property value="%{SourceName}" /> : </div>
									<div class="rightColumn1">
										<s:if test="%{sourceTrue == 'true'}">
											<span class="needed"></span>
										</s:if>
										
										<s:select
											id="sourceName" 
											name="sourceName" 
											list="sourceList" 
											headerKey="-1"
											headerValue="--Select Source--" 
											cssClass="textField"
											cssStyle="margin:0px 0px 10px 0px">
										</s:select>
									</div>
								</div>
							</s:if>
							<div class="clear"></div>
							<s:if test="%{targetTrue != null}">	
								<div class="newColumn">
									<div class="leftColumn1"> <s:property value="%{targetAchieved}" />:</div>
									<div class="rightColumn1">
									<s:if test="%{targetTrue == 'true'}">
										<span class="needed"></span>
									</s:if>
									<s:select
										id="targetAchieved" 
										name="targetAchieved" 
										list="targetKpiLiist"
										headerKey="-1" 
										headerValue="--Select Target--"
										cssClass="textField"
										cssStyle="margin:0px 0px 10px 0px">
									</s:select>
									</div>
								</div>
							</s:if>
							
							<div class="clear"></div>
   							<div class="fields">
							<div style="width: 100%; text-align: center; padding-bottom: 10px;">
								<sj:submit 
								     targets="result" 
								     clearForm="true"
								     value="  Save  " 
								     effect="highlight"
								     effectOptions="{ color : '#222222'}"
								     effectDuration="100"
								     button="true"
								     onBeforeTopics="validate"
								     onCompleteTopics="level"
								     cssClass="submit"
								     indicator="indicator2"
								     
								     
							     />
							   	<sj:a 
							 button="true" href="#"
							 onclick="reset('formone');"
							 cssClass="submit"
						>
						
						Reset
					</sj:a>
					<sj:a 
							button="true" href="#"
							onclick="viewLocation();"
							cssClass="submit"
						>
						
						Back
					</sj:a>
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
