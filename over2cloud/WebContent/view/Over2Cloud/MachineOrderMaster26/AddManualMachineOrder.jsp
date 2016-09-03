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

<script type="text/javascript" src="<s:url value="/js/common/commonvalidation.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<SCRIPT type="text/javascript"><!--<!--

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
			 
			viewmainkeyword();
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

	function viewmainkeyword()
	{
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Text2Mail/beforemainKeywordsView.action",
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
	
	
	function getKeyAvailabilty(data)
	{
	    var conP = "<%=request.getContextPath()%>";
	    
	    if(data!="")
	    {
	         document.getElementById("indicator2").style.display="block";
	         $.getJSON(conP+"/view/Over2Cloud/Text2Mail/checkmainKeyword.action?mainKeywordChk="+data,
	            function(checkMainKey){
	                 $("#userStatus").html(checkMainKey.msg);
	                 document.getElementById("indicator2").style.display="none";
	                    
	        });
	    }
	    
	}

	function getPatientDetails(data){
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/MachineOrder/fetchPatientDetails.action?uhid="+data,
		    async:false,
			success : function(empData)
			{
				console.log(empData);
				if(empData.length != 0)
				{
					$("#pName").val(empData[0].pName);
					$("#roomNo").val(empData[0].bedNo);
					$("#nursingUnit").val(empData[0].nursingUnit);
					$("#treatingDoc").val(empData[0].admDoc);
					$("#treatingSpec").val(empData[0].admSpec);


				}
				else
				{
					$("#pName").val("");
					$("#roomNo").val("");
					$("#nursingUnit").val("");
					$("#treatingDoc").val("");
					$("#treatingSpec").val("");
				}
		    },
		    error : function () {
				//alert("Somthing is wrong to get Data");
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
	<div class="list-icon">
<div class="head">Manual Order</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div>
</div>
	
	<div class="container_block" >
		<div style="float: left; padding: 40px 1%; width: 98%;">
			<div class="border">
					<div class="form_inner" id="form_reg" style="margin-top: 10px;">
						<s:form id="formone" name="formone" namespace="/view/Over2Cloud/Text2Mail" action="addMainKeyword" theme="simple" method="post" enctype="multipart/form-data">
							<div style="float: left; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
								<div id="errZone" style="float: left; margin-left: 7px"></div>
							</div>
							<br/>
							
	                      			<span id="mandatoryFields" class="pIds" style="display: none;"><s:property value="%{key}" />#<s:property value="%{value}" />#<s:property value="%{colType}" />#<s:property value="%{validationType}" />,</span>
	                 			
	                 			
	                 				<div class="newColumn">
	                 					<div class="rightColumn1">
	                 						
		                 					
	   										<s:textfield name="uhid"  id="uhid" onchange="getPatientDetails(this.value);" cssClass="textField" placeholder="UHID" cssStyle="margin:0px 0px 10px 0px; width:130%" />
                      						
										</div>
	                 				</div>
	                 			
	                 				<div class="newColumn">
		                 				
										<div class="rightColumn1">
	                 						
										
											<s:textfield name="pName" id="pName"   cssClass="textField" placeholder="Patient Name" cssStyle="margin:0px 0px 10px 0px; width:130%" />
										</div>
									</div>
									
									
									<div class="newColumn">
	                 					<div class="rightColumn1">
	                 						
		                 					
	   										<s:textfield name="roomNo"  id="roomNo"  cssClass="textField" placeholder="Room No." cssStyle="margin:0px 0px 10px 0px; width:130%" />
                      						
										</div>
	                 				</div>
	                 			
	                 				<div class="newColumn">
										<div class="rightColumn1">
	                 						
										
											<s:textfield name="nursingUnit" id="nursingUnit" cssClass="textField" placeholder="Nursing Unit" cssStyle="margin:0px 0px 10px 0px; width:130%" />
										</div>
									</div>
									
									<div class="newColumn">
	                 					<div class="rightColumn1">
	                 						
		                 					
	   										<s:textfield name="treatingDoc"  id="treatingDoc"  cssClass="textField" placeholder="Adm. Doctor" cssStyle="margin:0px 0px 10px 0px; width:130%" />
                      						
										</div>
	                 				</div>
	                 			
	                 				<div class="newColumn">
										<div class="rightColumn1">
	                 						
										
											<s:textfield name="treatingSpec" id="treatingSpec" cssClass="textField" placeholder="Adm. Spec" cssStyle="margin:0px 0px 10px 0px; width:130%" />
										</div>
									</div>
									
									<div class="newColumn">
	                 					<div class="rightColumn1">
	                 						
		                 					
	   										<s:textfield name="order"  id="order"  cssClass="textField" placeholder="Order" cssStyle="margin:0px 0px 10px 0px; width:130%" />
                      						
                      					
		                              
                  	
                      						
                      						
										</div>
	                 				</div>
	                 			
	                 				<div class="newColumn">
										<div class="rightColumn1">
	                 						
										
											<s:textfield name="orderType" id="orderType" cssClass="textField" placeholder="Order Type" cssStyle="margin:0px 0px 10px 0px; width:130%" />
										</div>
									</div>
	                 			
	                 			
	                 			<div class="newColumn">
	                 					<div class="rightColumn1">
	                 						
		                 					
	   										<s:textfield name="priority"  id="priority"  cssClass="textField" placeholder="Priority" cssStyle="margin:0px 0px 10px 0px; width:130%" />
                      						
										</div>
	                 				</div>
	                 			
	                 				<div class="newColumn">
										<div class="rightColumn1">
	                 						
										
											<s:textfield name="remarks" id="remarks" cssClass="textField" placeholder="Remarks" cssStyle="margin:0px 0px 10px 0px; width:130%" />
										</div>
									</div>
	                 			
							
							
							
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
								     
							     />
							   	<sj:a 
							 button="true" href="#"
							 onclick="reset('formone');"
							 cssClass="submit"
						>
						
						Reset
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
