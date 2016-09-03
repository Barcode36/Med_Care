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
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}" />
<script src="<s:url value="/js/lead/LeadCommon.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/common/commonvalidation.js"/>"></script>
<script type="text/javascript">



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
			 
			viewConsultant();
		}


		
		function reset(formId) {
			$("#"+formId).trigger("reset"); 
			}

</script>
<script type="text/javascript">
document.getElementById(100).style.display="none";
document.getElementById(101).style.display="none";
document.getElementById(102).style.display="none";
document.getElementById(103).style.display="none";
</script>
<script type="text/javascript">
	function viewConsultant(){
	 var conP = "<%=request.getContextPath()%>";
	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Text2Mail/beforeConsultantsView.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
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
<div class="middle-content">
<div class="list-icon">
<div class="head">Consultants</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div>
</div>
	<div class="clear"></div>
<div style=" float:left; padding:10px 0%; width:100%;">
<div class="clear"></div>
		<div class="border">
		<div class="container_block">
   <div style=" float:left; padding:20px 1%; width:98%;">
		<s:form id="formone" name="formone" namespace="/view/Over2Cloud/Text2Mail" action="addConsultants" theme="css_xhtml" method="post" enctype="multipart/form-data">
		     <center>
	             <div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
	                  <div id="errZone" style="float:left; margin-left: 7px"></div>        
	             </div>
	        </center>
	<s:iterator value="configKeyTextBox" status="counter" begin="0" end="4">
	<s:if test="%{mandatory}">
	<span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	</s:if>
	<s:if test="#counter.odd">
							 <s:if test="%{key == 'emailId'}">
							 	   <div class="newColumn">
								     <div class="leftColumn1"><s:property value="%{value}"/>:<font color="red"></font> :</div>
			                  		 <div class="rightColumn1">
			                  		 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
					                 <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/><sj:submit value="+" onclick="adddiv('100')" button="true" cssClass="button" cssStyle="margin-left: 5px;"/>
					                 </div>
								
								  </div>
					  		</s:if>
					  			<s:else>
					  			<div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/> :</div>
			                  		<div class="rightColumn1">
			                  		<s:if test="%{mandatory}"><span class="needed"></span></s:if>
					                 <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>            
					                  </div>
								
								</div>
								 </s:else>
				   			</s:if>
	<s:else>
							 <s:if test="%{key == 'emailId'}">
							 <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/> :</div>
		                  		<div class="rightColumn1">
		                  		<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                 <s:textfield name="%{key}"  id="%{key}%{#deptIndx}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"  /><sj:submit value="+" onclick="adddiv('100')" button="true" cssClass="button" cssStyle="margin-left: 5px;"/>
				                </div>
							</div>
							</s:if>
						    <s:else>
						    <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                  		<div class="rightColumn1">
		                  		<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                 <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
				                </div>
							</div>
						  </s:else>
						</s:else>
	</s:iterator> 
	<div style="float: right;margin-top: -35px;">
<div class="user_form_button2"><sj:submit value="+" onclick="adddiv('100')" button="true" cssClass="button" cssStyle="margin-left: 1px;"/></div>
</div>
	<!-- 2nd time  -->
	<div id="100">
<div class="clear"></div>
		<s:iterator value="configKeyTextBox" status="counter" begin="5" end="6">
	<s:if test="%{mandatory}">
	<span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	</s:if>
	<s:if test="#counter.odd">
							 <s:if test="%{key == 'emailId'}">
							 	   <div class="newColumn">
								     <div class="leftColumn1"><s:property value="%{value}"/>:<font color="red"></font> :</div>
			                  		 <div class="rightColumn1">
			                  		 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
					                 <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/><sj:submit value="+" onclick="adddiv('100')" button="true" cssClass="button" cssStyle="margin-left: 5px;"/>
					                 </div>
								
								  </div>
					  		</s:if>
					  			<s:else>
					  			<div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/> :</div>
			                  		<div class="rightColumn1">
			                  		<s:if test="%{mandatory}"><span class="needed"></span></s:if>
					                 <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>            
					                  </div>
								
								</div>
								 </s:else>
				   			</s:if>
	<s:else>
							 <s:if test="%{key == 'emailId'}">
							 <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/> :</div>
		                  		<div class="rightColumn1">
		                  		<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                 <s:textfield name="%{key}"  id="%{key}%{#deptIndx}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"  /><sj:submit value="+" onclick="adddiv('100')" button="true" cssClass="button" cssStyle="margin-left: 5px;"/>
				                </div>
							</div>
							</s:if>
						    <s:else>
						    <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                  		<div class="rightColumn1">
		                  		<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                 <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
				                </div>
							</div>
						  </s:else>
						</s:else>
	</s:iterator> 
	<div style="float: right;margin-top: -35px;">
<div class="user_form_button2"><sj:submit value="+" onclick="adddiv('101')" button="true" cssClass="button" cssStyle="margin-left: 5px;"/></div>
<div class="user_form_button3"></div><sj:submit value="-" onclick="deletediv('100')" button="true" cssClass="button" cssStyle="margin-left: 5px;"/> </div>
	</div>
	
	<!-- 3rd time -->
	<div id="101">
<div class="clear"></div>
		<s:iterator value="configKeyTextBox" status="counter" begin="7" end="8">
	<s:if test="%{mandatory}">
	<span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	</s:if>
	<s:if test="#counter.odd">
							 <s:if test="%{key == 'emailId'}">
							 	   <div class="newColumn">
								     <div class="leftColumn1"><s:property value="%{value}"/>:<font color="red"></font> :</div>
			                  		 <div class="rightColumn1">
			                  		 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
					                 <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/><sj:submit value="+" onclick="adddiv('100')" button="true" cssClass="button" cssStyle="margin-left: 5px;"/>
					                 </div>
								
								  </div>
					  		</s:if>
					  			<s:else>
					  			<div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/> :</div>
			                  		<div class="rightColumn1">
			                  		<s:if test="%{mandatory}"><span class="needed"></span></s:if>
					                 <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>            
					                  </div>
								
								</div>
								 </s:else>
				   			</s:if>
	<s:else>
							 <s:if test="%{key == 'emailId'}">
							 <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/> :</div>
		                  		<div class="rightColumn1">
		                  		<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                 <s:textfield name="%{key}"  id="%{key}%{#deptIndx}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"  /><sj:submit value="+" onclick="adddiv('100')" button="true" cssClass="button" cssStyle="margin-left: 5px;"/>
				                </div>
							</div>
							</s:if>
						    <s:else>
						    <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                  		<div class="rightColumn1">
		                  		<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                 <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
				                </div>
							</div>
						  </s:else>
						</s:else>
	</s:iterator> 
	<div style="float: right;margin-top: -35px;">
<div class="user_form_button2"><sj:submit value="+" onclick="adddiv('102')" button="true" cssClass="button" cssStyle="margin-left: 5px;"/></div>
<div class="user_form_button3"></div><sj:submit value="-" onclick="deletediv('101')" button="true" cssClass="button" cssStyle="margin-left: 5px;"/> </div>
	</div>
	<!-- 4th time  -->
	<div id="102">
<div class="clear"></div>
	<s:iterator value="configKeyTextBox" status="counter" begin="9" end="10">
	<s:if test="%{mandatory}">
	<span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	</s:if>
	<s:if test="#counter.odd">
							 <s:if test="%{key == 'emailId'}">
							 	   <div class="newColumn">
								     <div class="leftColumn1"><s:property value="%{value}"/>:<font color="red"></font> :</div>
			                  		 <div class="rightColumn1">
			                  		 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
					                 <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/><sj:submit value="+" onclick="adddiv('100')" button="true" cssClass="button" cssStyle="margin-left: 5px;"/>
					                 </div>
								
								  </div>
					  		</s:if>
					  			<s:else>
					  			<div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/> :</div>
			                  		<div class="rightColumn1">
			                  		<s:if test="%{mandatory}"><span class="needed"></span></s:if>
					                 <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>            
					                  </div>
								
								</div>
								 </s:else>
				   			</s:if>
	<s:else>
							 <s:if test="%{key == 'emailId'}">
							 <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/> :</div>
		                  		<div class="rightColumn1">
		                  		<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                 <s:textfield name="%{key}"  id="%{key}%{#deptIndx}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"  /><sj:submit value="+" onclick="adddiv('100')" button="true" cssClass="button" cssStyle="margin-left: 5px;"/>
				                </div>
							</div>
							</s:if>
						    <s:else>
						    <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                  		<div class="rightColumn1">
		                  		<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                 <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
				                </div>
							</div>
						  </s:else>
						</s:else>
	</s:iterator> 	
	<div style="float: right;margin-top: -35px;">
<div class="user_form_button2"><sj:submit value="+" onclick="adddiv('103')" button="true" cssClass="button" cssStyle="margin-left: 5px;"/></div>
<div class="user_form_button3"></div><sj:submit value="-" onclick="deletediv('102')" button="true" cssClass="button" cssStyle="margin-left: 5px;"/> </div>
	</div>
	<!--   -->
	<div id="103">
<div class="clear"></div>
		<s:iterator value="configKeyTextBox" status="counter" begin="11" end="12">
	<s:if test="%{mandatory}">
	<span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	</s:if>
	<s:if test="#counter.odd">
							 <s:if test="%{key == 'emailId'}">
							 	   <div class="newColumn">
								     <div class="leftColumn1"><s:property value="%{value}"/>:<font color="red"></font> :</div>
			                  		 <div class="rightColumn1">
			                  		 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
					                 <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/><sj:submit value="+" onclick="adddiv('100')" button="true" cssClass="button" cssStyle="margin-left: 5px;"/>
					                 </div>
								
								  </div>
					  		</s:if>
					  			<s:else>
					  			<div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/> :</div>
			                  		<div class="rightColumn1">
			                  		<s:if test="%{mandatory}"><span class="needed"></span></s:if>
					                 <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>            
					                  </div>
								
								</div>
								 </s:else>
				   			</s:if>
	<s:else>
							 <s:if test="%{key == 'emailId'}">
							 <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/> :</div>
		                  		<div class="rightColumn1">
		                  		<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                 <s:textfield name="%{key}"  id="%{key}%{#deptIndx}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"  /><sj:submit value="+" onclick="adddiv('100')" button="true" cssClass="button" cssStyle="margin-left: 5px;"/>
				                </div>
							</div>
							</s:if>
						    <s:else>
						    <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                  		<div class="rightColumn1">
		                  		<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                 <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
				                </div>
							</div>
						  </s:else>
						</s:else>
	</s:iterator> 
	<div style="float: right;margin-top: -35px;">
<div class="user_form_button3"></div><sj:submit value="-" onclick="deletediv('103')" button="true" cssClass="button" cssStyle="margin-left: 5px;"/> </div>
	</div>
		<s:iterator value="configKeyTextBox" status="counter" begin="13" end="13">
	<s:if test="%{mandatory}">
	<span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	</s:if>
	<s:if test="#counter.odd">
							 <s:if test="%{key == 'emailId'}">
							 	   <div class="newColumn">
								     <div class="leftColumn1"><s:property value="%{value}"/>:<font color="red"></font> :</div>
			                  		 <div class="rightColumn1">
			                  		 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
					                 <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/><sj:submit value="+" onclick="adddiv('100')" button="true" cssClass="button" cssStyle="margin-left: 5px;"/>
					                 </div>
								
								  </div>
					  		</s:if>
					  			<s:else>
					  			<div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/> :</div>
			                  		<div class="rightColumn1">
			                  		<s:if test="%{mandatory}"><span class="needed"></span></s:if>
					                 <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>            
					                  </div>
								
								</div>
								 </s:else>
				   			</s:if>
	<s:else>
							 <s:if test="%{key == 'emailId'}">
							 <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/> :</div>
		                  		<div class="rightColumn1">
		                  		<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                 <s:textfield name="%{key}"  id="%{key}%{#deptIndx}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"  /><sj:submit value="+" onclick="adddiv('100')" button="true" cssClass="button" cssStyle="margin-left: 5px;"/>
				                </div>
							</div>
							</s:if>
						    <s:else>
						    <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
		                  		<div class="rightColumn1">
		                  		<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                 <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
				                </div>
							</div>
						  </s:else>
						</s:else>
	</s:iterator> 
	<div class="clear"></div>
				 		 <s:iterator begin="100" end="120" var="deptIndx">
				 		 <div class="clear"></div>
				 		 
				 		 	<div id="<s:property value="%{#deptIndx}"/>" style="display: none">
				 		 		 
				 		 		 <div class="clear"></div>
				 		 		 <s:iterator value="configKeyTextBox" status="counter">
						 			<s:if test="#counter.odd">
									 <s:if test="%{key == 'emailId'}">
									 	<div class="newColumn">
										<div class="leftColumn1"><s:property value="%{value}"/>:</div>
					                  		<div class="rightColumn1">
					                  		<s:if test="%{mandatory}"><span class="needed"></span></s:if>
							                 <s:textfield name="%{key}"  id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
							                 
											</div>
											<div style="float: left; margin-top: -45px; margin-left: 325px;">
							 				<s:if test="#deptIndx==120">
								                  <sj:submit value="-" onclick="deletediv('%{#deptIndx}')" button="true" cssClass="button"/>
								           	 </s:if>
									         <s:else>
									         	<sj:submit value="+" onclick="adddiv('%{#deptIndx+1}')" button="true" cssClass="button" cssStyle="margin-left: 5px;"/>
									         	<sj:submit value="-" onclick="deletediv('%{#deptIndx}')" button="true" cssClass="button" cssStyle="margin-left: 5px;"/>
									         	<div class="clear"></div>
									         </s:else>
									         </div>
											</div>
							  			</s:if>
							  			<s:if test="%{key == 'mobileNo'}">
									 	<div class="newColumn">
										<div class="leftColumn1"><s:property value="%{value}"/>:</div>
					                  		<div class="rightColumn1">
					                  		<s:if test="%{mandatory}"><span class="needed"></span></s:if>
							                 <s:textfield name="%{key}"  id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
							                 
											</div>
											<div style="float: left; margin-top: -45px; margin-left: 325px;">
							 				
									         </div>
											</div>
							  			</s:if>
							  			
						   			</s:if>
									  <s:else>
										 <s:if test="%{key == 'emailId'}">
										 <div class="newColumn">
											<div class="leftColumn1"><s:property value="%{value}"/> :</div>
					                  		<div class="rightColumn1">
					                  		<s:if test="%{mandatory}"><span class="needed"></span></s:if>
							                 <s:textfield name="%{key}"  id="%{key}%{#deptIndx}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" />
							                 
							                </div>
							                <div style="float: left; margin-top: -47px; margin-left: 412px;">
							 				<s:if test="#deptIndx==120">
								                  <sj:submit value="-" onclick="deletediv('%{#deptIndx}')" button="true" cssClass="button"/>
								           	 </s:if>
									         <s:else>
									         	<sj:submit value="+" onclick="adddiv('%{#deptIndx+1}')" button="true" cssClass="button" cssStyle="margin-left: 5px;"/>
									         	<sj:submit value="-" onclick="deletediv('%{#deptIndx}')" button="true" cssClass="button" cssStyle="margin-left: 5px;"/>
									         	<div class="clear"></div>
									         </s:else>
									         </div>
										</div>
										</s:if>
										<s:if test="%{key == 'mobileNo'}">
										 <div class="newColumn">
											<div class="leftColumn1"><s:property value="%{value}"/> :</div>
					                  		<div class="rightColumn1">
							                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
							                 <s:textfield name="%{key}"  id="%{key}%{#deptIndx}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" />
							                 
							                </div>
							                <div style="float: left; margin-top: 8px; margin-left: -150;">
							 				
									         </div>
										</div>
										</s:if>
									    
									</s:else>
									
				 				</s:iterator>
				 				
				 				
				 		 	</div>
				 		 	
				 		 </s:iterator>
	
	 <s:iterator value="configKeyDropDown" status="counter">
	    <s:if test="%{mandatory}">
	       <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	    </s:if> 	
	</s:iterator>
	
	<s:if test="%{specialityExist != null}">
	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{speciality}"/>:</div>
	<div class="rightColumn1">
	<s:if test="%{specialityExist == 'true'}"><span class="needed"></span></s:if>
	         <s:select 
	                     id="speciality"
	                     name="speciality" 
	                     list="specialityMasterMap"
	                     headerKey="-1"
	                     headerValue="Select Speciality" 
	                      cssClass="select"
	                      cssStyle="width:82%"
	                     >
	         </s:select>
	</div>
	</div>
         </s:if>
         
         <s:if test="%{locationExist != null}">
	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{location}"/>:</div>
	<div class="rightColumn1">
	<s:if test="%{locationExist == 'true'}"><span class="needed"></span></s:if>
	         <s:select 
	                     id="location"
	                     name="location" 
	                     list="locationMasterMap"
	                     headerKey="-1"
	                     headerValue="Select Location" 
	                      cssClass="select"
	                      cssStyle="width:82%"
	                     >
	         </s:select>
	</div>
	</div>
         </s:if>
	
	
	
	
	
	<s:iterator value="configKeyTextArea" status="counter">
	<s:if test="%{mandatory}">
	<span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	</s:if>

	<s:if test="#counter.odd">
	<div class="clear"></div>
	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	<div class="rightColumn1">
	<s:if test="%{mandatory}"><span class="needed"></span>
	</s:if>
	<sj:datepicker name="%{key}"  id="%{key}" changeMonth="true" changeYear="true" yearRange="1970:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="textField" placeholder="Select Anniversary"/>
	</div>
	</div>
	</s:if>
	<s:else>
	<div class="newColumn">
	<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	<div class="rightColumn1">
	<s:if test="%{mandatory}">
	<span class="needed"></span>
	</s:if>
	<sj:datepicker name="%{key}"  id="%{key}" changeMonth="true" changeYear="true" yearRange="1970:2020" showOn="focus" displayFormat="dd-mm-yy" cssClass="textField" placeholder="Select Birthday"/>
	</div>
	</div>
	</s:else>
	</s:iterator>
	<div class="clear"></div>		
	<br>
<div class="clear"></div>	
 <center><img id="indicator3" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>	
			<div class="type-button" style="margin-left: -200px;">
			<center> 	
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
								     cssStyle="margin-right: 73px;
											margin-bottom: -7px;"
				/>
						
					<sj:a 
							 button="true" href="#"
							 onclick="reset('formone');"
							 cssClass="submit"
							 cssStyle="margin-top: -28px;"
						>
						
						Reset
					</sj:a>
					<sj:a 
							button="true" href="#"
							onclick="viewConsultant();"
							cssClass="submit"
							cssStyle="margin-right: -137px;margin-top: -28px;"
						>
						
						Back
					</sj:a>
					</center>
						</div>
				 <sj:div id="foldeffect10"  effect="fold">
                    <div id="Result7"></div>
               </sj:div>
			
				<div class="clear"></div>
				

			</s:form>			
	</div>
	</div>
</div>
</div>
</div></body>
</html>