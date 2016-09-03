<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme"customBasepath="%{contextz}" />
		<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
	<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
	<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
 <script type="text/javascript" src="js/commanValidation/validation.js"></script>
 <script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>	
<SCRIPT type="text/javascript">
	$.subscribe('level1', function(event, data) {
		$("#addDialog").dialog('open');
		setTimeout(function() {
			closeAdd() ;
		}, 1000);
		reset("formone");
	});
</script>

<script type="text/javascript">
	function closeAdd() {
		$("#addDialog").dialog('close');
		backToView();
	}
</script>
<script type="text/javascript">

	function backToView()
	  {
	 	  $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	  		$.ajax({
	  		    type : "post",
	  		    url : "view/Over2Cloud/CorporatePatientServices/cpservices/beforeViewModiality.action",
	  		    success : function(data) 
	  		    {
	  				$("#"+"data_part").html(data);
	  		    },
	  		    error: function() 
	  		    {
	  	            alert("hhh");
	  	        }
	  		 });
	  		
	  }
	function reset(formId) {
		$("#" + formId).trigger("reset");
	}
	 $(document).ready(function()
				{
				$("#serv_loc").multiselect({
					   show: ["", 200],
					   hide: ["explode", 1000]
					});
				});
</script>
</head>
<body>
	<div class="clear"></div>
	<sj:dialog id="addDialog"
		buttons="{'Close':function() { closeAdd(); },}" showEffect="slide"
		hideEffect="explode" autoOpen="false" modal="true"
		title="Status Message" openTopics="openEffectDialog"
		closeTopics="closeEffectDialog" width="350" height="150">
		<sj:div id="level123"></sj:div>
	</sj:dialog>

	<div class="middle-content">

		<div class="list-icon">
			<div class="head">Services of Service</div>
			<div class="head">
				<img alt="" src="images/forward.png"
					style="margin-top: 50%; float: left;">
			</div>
			<div class="head">Add</div>
		</div>
		<div class="clear"></div>
		<div class="border">
			<div class="container_block">
				<div style="float: left; padding: 20px 1%; width: 98%;">
					<s:form id="formone" name="formone"
						namespace="/view/Over2Cloud/CorporatePatientServices/cpservices"
						action="addModalityData" theme="css_xhtml" method="post"
						enctype="multipart/form-data">
						<center>
							<div
								style="float: center; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
								<div id="errZone" style="float: center; margin-left: 7px"></div>
							</div>
						</center>
				<s:iterator value="packageFields" status="status">
						  <s:if test="%{mandatory}">
		     	<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#D#<s:property value="%{validationType}"/>,</span>
             </s:if>
			       	<div class="newColumn" >
			       	 <s:if test="%{key == 'modality'}">
			  		<div class="leftColumn1">Services of Service:</div>
			  		</s:if>
			  		 <s:elseif test="%{key != 'modality'}">
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
			  		</s:elseif>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				       <s:if test="%{key == 'location_name'}">
				         		 <s:select 
                                      id="%{key}"
                                      name="%{key}"
                                      list=	"location"
                                      headerKey="-1"
                                      headerValue="Select Location" 
                                      cssClass="select"
                                      cssStyle="width:60%"
                                      theme="simple"
                                      >
                          </s:select>
                          </s:if>
                          <s:elseif test="%{key == 'service_type'}">
				         		 <s:select 
                                      id="%{key}"
                                      name="%{key}"
                                      list=	"service"
                                      headerKey="-1"
                                      headerValue="Select Service" 
                                      cssClass="select"
                                      cssStyle="width:60%"
                                      theme="simple"
                                      >
                          </s:select>
                          </s:elseif>
				    
				             <s:elseif test="%{key == 'modality'}">
				         		<s:textfield  name="%{key}" id="%{key}"  maxlength="50" cssClass="textField" placeholder="Enter Data" style="width: 59%;" />
				         </s:elseif>
				         <s:else>
				         	<s:textfield  name="%{key}" id="%{key}"  maxlength="50" cssClass="textField" placeholder="Enter Data" style="width: 59%;"/>
				         </s:else>
				 		             
				    </div>
			        </div>
			        
						</s:iterator>
	<div id="newDiv" style="float: right;width: 10%;margin-top: -46px;"><sj:a value="+" onclick="adddiv('100')" button="true" cssStyle="margin-top: 9%;">+</sj:a></div>
    <s:iterator begin="100" end="110" var="levelIndx">
	      <div id="<s:property value="%{#levelIndx}"/>" style="display: none ;"class="container_block" >
		    <div class="clear"></div>  
		 <s:iterator value="packageFields" begin="2" end="3">
		    <div class="newColumn">
		   	 <s:if test="%{key == 'modality'}">
			     <div class="leftColumn1" style="margin-left: 0px;">Services of Service:&nbsp;</div>
			     </s:if>
			     <s:if test="%{key != 'modality'}">
			     <div class="leftColumn1" style="margin-left: 0px;">Brief:&nbsp;</div>
			     </s:if>
				 <div class="rightColumn1">
					 <s:if test="%{mandatory}"><span class="needed"></span>
					<!--<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{key}"/><s:property value="%{#levelIndx}"/>#<s:property value="%{value}"/>#T#<s:property value="%{validationType}"/>,</span>--> 
					 </s:if>
		             	 <s:if test="%{key == 'test_type'}">
				         		<s:textfield  name="%{key}" id="%{key}"  maxlength="200" cssClass="textField" placeholder="Enter Data"style="width: 59%;"/>
				         </s:if>
				         <s:else>
				         		<s:textfield  name="%{key}" id="%{key}"  maxlength="200" cssClass="textField" placeholder="Enter Data"style="width: 59%;"/>
				         </s:else>
		         </div>
		    </div>
		    
   		</s:iterator> 
			
			<div>	
					<s:if test="#levelIndx==110">
	                    <div class="user_form_button2"><sj:a value="-" onclick="deletediv('%{#levelIndx}')" button="true">-</sj:a></div>
	                </s:if>
					<s:else>
	  		  			<div class="user_form_button2"><sj:a value="+" onclick="adddiv('%{#levelIndx+1}')" button="true" cssStyle="margin-left:475px;margin-top: -38px;">+</sj:a></div>
	          			<div class="user_form_button3" style="margin-left: -4px;"><sj:a value="-" onclick="deletediv('%{#levelIndx}')" cssStyle="margin-top: -38px;margin-left:537px" button="true">-</sj:a></div>
	       			</s:else>
       			</div>
				</div>
		 </s:iterator>
		
						
									<!-- Buttons -->
						<center>
							<img id="indicator2" src="<s:url value="/images/indicator.gif"/>"
								alt="Loading..." style="display: none" />
						</center>

						<div class="clear">
							<div style="padding-bottom: 10px; margin-left: -76px"
								align="center">
								<sj:submit targets="level123" clearForm="true" value="Save"
									effect="highlight" effectOptions="{ color : '#FFFFFF'}"
									effectDuration="5000" button="true" onBeforeTopics="validate"
									onCompleteTopics="level1" />
								&nbsp;&nbsp;
								<sj:submit value="Reset" button="true"
									cssStyle="margin-left: 139px;margin-top: -43px;"
									onclick="reset('formone');" />
								&nbsp;&nbsp;
								<sj:a cssStyle="margin-left: 276px;margin-top: -58px;"
									button="true" href="#" value="Back" onclick="backToView()"
									cssStyle="margin-left: 266px;margin-top: -74px;">Back
					</sj:a>
							</div>
						</div>
					</s:form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>