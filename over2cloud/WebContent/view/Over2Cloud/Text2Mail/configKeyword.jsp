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
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme"
	customBasepath="%{contextz}" />
<link type="text/css" href="<s:url value="/css/style.css"/>"
	rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/common/commonvalidation.js"/>"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="js/multiselect/jquery-ui.css" rel="stylesheet"
	type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript"
	src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
	<link rel="stylesheet" href="jqwidgets/styles/jqx.base.css" type="text/css" />
    <script type="text/javascript" src="jqwidgets/jqxcore.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxswitchbutton.js"></script>
    <script type="text/javascript" src="jqwidgets/jqxcheckbox.js"></script>

 <style type="text/css">     
        #settings-panel
        {
            background-color: #fff;
            padding: 20px;
            display: inline-block;
            -moz-border-radius: 10px;
            -webkit-border-radius: 10px;
            border-radius: 10px;
            position: relative;
        }
        .settings-section
        {
            background-color: #f7f7f7;
            height: 45px;
            width: 500px;
            border: 1px solid #b4b7bc;
            border-bottom-width: 0px;
        }
        .settings-section-top
        {
            border-bottom-width: 0px;
            -moz-border-radius-topleft: 10px;
            -webkit-border-top-left-radius: 10px;
            border-top-left-radius: 10px;
            -moz-border-radius-topright: 10px;
            -webkit-border-top-right-radius: 10px;
            border-top-right-radius: 10px;            
        }
        .sections-section-bottom
        {
            border-bottom-width: 1px;
            -moz-border-radius-bottomleft: 10px;
            -webkit-border-bottom-left-radius: 10px;
            border-bottom-left-radius: 10px;
            -moz-border-radius-bottomright: 10px;
            -webkit-border-bottom-right-radius: 10px;
            border-bottom-right-radius: 10px;            
        }
        .sections-top-shadow
        {
            width: 500px;
            height: 1px;
            position: absolute;
            top: 21px;
            left: 21px;
            height: 30px;
            border-top: 1px solid #e4e4e4;
            -moz-border-radius-topleft: 10px;
            -webkit-border-top-left-radius: 10px;
            border-top-left-radius: 10px;
            -moz-border-radius-topright: 10px;
            -webkit-border-top-right-radius: 10px;
            border-top-right-radius: 10px;  
        }
        .settings-label
        {
            font-weight: bold;
            font-family: Sans-Serif;
            font-size: 14px;
            margin-left: 14px;
            margin-top: 15px;
            float: left;
        }
        .settings-melody
        {
            color: #385487;
            font-family: Sans-Serif;
            font-size: 14px;
            display: inline-block;
            margin-top: 7px;
        }
        .settings-setter
        {
            float: right;
            margin-right: 14px;
            margin-top: 8px;
        }
        .events-container
        {
            margin-left: 20px;
        }
        #theme
        {
            margin-left: 20px;
            margin-bottom: 20px;
        }
    </style>

<script type="text/javascript">
$(document).ready(function()
	{
	$("#deptIdDD").multiselect({
		   show: ["", 200],
		   hide: ["", 1000]
		});
	});
	
$(document).ready(function () {
    var label = {
            'button1': 'Auto Ack Msg',
            
            
        };
    $('#button1').jqxSwitchButton({ height: 27, width: 81,  checked: true });
   

    $('.jqx-switchbutton').on('checked', function (event) {
        $('#events').text(label[event.target.id] + ': Checked');
        $("#hide").hide();
    });
    $('.jqx-switchbutton').on('unchecked', function (event) {
        $('#events').text(label[event.target.id] + ': Unchecked');
        $("#hide").show();
    });
    $('#button1').bind('checked', function (event) { 

    	 $('#button1res').val("false");
    });
    $('#button1').bind('unchecked', function (event) { 

   	 $('#button1res').val("true");
   });
});
</script>

<script type="text/javascript">
$(document).ready(function()
	{
	$("#empIdDD").multiselect({
		   show: ["", 200],
		   hide: ["", 1000]
		});
	});
</script>

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
			 
			viewKeyword();
		}

	


$.subscribe('completeData22',function(event,data)
		{
	setTimeout(function(){ $("#foldeffect10").fadeIn(); }, 10);
    setTimeout(function(){ $("#foldeffect10").fadeOut(); }, 4000);
    
			
		});
		
		function viewPage()
		{

			$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/Text2Mail/beforeViewConfigKeyword.action",
			    success : function(data) 
			    {
					$("#"+"data_part").html(data);
			    },
			    error: function() 
			    {
		            alert("error");
		        }
			 });
		 
		}
		
		function reset(formone) {
			  $('#'+formone).trigger("reset");
			}

		function check()
		{  
		if($("#ackBySMS").is(':checked'))
		    $("#hide").show();  
		else
		    $("#hide").hide();  
		}
		
		
	
		function checkSunKeyword(value)
		{
	alert(value);
			mydiv1 = document.getElementById('showhis');
			if(value=="Yes")
				{
					 mydiv1.style.display = 'block';
				}
			if(value=="No")
			{
				 mydiv1.style.display = 'none';
			}
			}
			
		
		

		

		function mapContact()
		{		

			if($("#mapWithMail").is(':checked')){
		   	$("#mapContacts").html("<br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		   	$.ajax({
		   	    type : "post",
		   	  url : "view/Over2Cloud/Text2Mail/mapContacts.action",
		   	    success : function(data) 
		   	    {
		   			$("#"+"mapContacts").html(data);
		   	    },
		   	    error: function() 
		   	    {
		               alert("error");
		           }
		   	 });
			}
		}
		function viewKeyword()
		{

			$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/Text2Mail/beforeViewConfigKeyword.action",
			    success : function(data) 
			    {
					$("#"+"data_part").html(data);
			    },
			    error: function() 
			    {
		            alert("error");
		        }
			 });

		}
		 function getEmp (emp)
		{
				var value=$("#"+emp).val();
			//var value=emp;
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/Text2Mail/multi.action?deptID="+value,
			    success : function(data) 
			    {
					$("#"+"empdiv").html(data);
			    },
			    error: function() 
			    {
		            alert("error");
		        }
			 });
		}
		 function subKeywrdData() {
				var mainKeyword=$("#mainKeyword").val();
				$.ajax({
					type :"post",
					url :"view/Over2Cloud/Text2Mail/subKeywordDataDD.action?mainKeywordid="+mainKeyword,
					success : function(empData){
					$('#keyword option').remove();
					$('#keyword').append($("<option></option>").attr("value",-1).text("Select Secondary Keyword"));
			    	$(empData).each(function(index)
					{
					   $('#keyword').append($("<option></option>").attr("value",this.id).text(this.name));
					});
				    },
				    error : function () {
						alert("Somthing is wrong to get Data");
					}
				});
			  }
		

		/* function getAlert(){
			alert("Please select Module name");
		} */
		
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
			<div class="head">Keyword</div>
			<div class="head">
				<img alt="" src="images/forward.png"
					style="margin-top: 50%; float: left;">
			</div>
			<div class="head">Map</div>
		</div>
		<div class="clear"></div>


		<div style="float: left; padding: 10px 0%; width: 100%;">
			<div class="clear"></div>
			<div class="border">
				<div class="container_block">
					<div style="float: left; padding: 20px 1%; width: 98%;">
						<s:form id="formone" name="formone"
							namespace="/view/Over2Cloud/Text2Mail"
							action="afterConfigKeyword" theme="css_xhtml" method="post"
							enctype="multipart/form-data">

							<center>
								<div
									style="float: right; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
									<div id="errZone" style="float: left; margin-left: 7px"></div>
								</div>
							</center>
							
							
							 <s:iterator value="configKeyDropDown" >
				  <s:if test="%{mandatory}">
				  <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				 </s:if>
				 <s:if test="%{key == 'mainKeyword'}">
				  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
								<s:select id="mainKeyword" name="mainKeyword" list="mainModuleMasterMap"
											headerKey="-1" headerValue="Select Primary Keyword"
											cssClass="textField" cssStyle="margin:0px 0px 10px 0px;"
											onchange="subKeywrdData();"
											>
										</s:select>
				  </div>
			      </div>
				  </s:if>
			
				 </s:iterator>
				 
				  <s:iterator value="configKeyDropDown" >
				  <s:if test="%{mandatory}">
				  <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				 </s:if>
				 <s:if test="%{key == 'keyword'}">
				  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
								<s:select id="keyword" name="keyword" list="#{'No data' }"
											headerKey="-1" headerValue="Select Secondary Keyword"
											cssClass="textField" cssStyle="margin:0px 0px 10px 0px;"
											>
										</s:select>
				  </div>
			      </div>
				  </s:if>
			
				 </s:iterator>
                             
                             
                            
                             <%-- <div id="showhis" style="display: none;">
							<s:iterator value="configKeyTextBox" status="counter">
								<s:if test="%{mandatory}">
									<span id="mandatoryFields" class="pIds" style="display: none;"><s:property
											value="%{key}" />#<s:property value="%{value}" />#<s:property
											value="%{colType}" />#<s:property value="%{validationType}" />,</span>
								</s:if>

								<s:if test="#counter.odd">
									
									<div class="newColumn">
										<div class="leftColumn1">
											<s:property value="%{value}" />
											:
										</div>
										<div class="rightColumn1">
											<s:if test="%{mandatory}">
												<span class="needed"></span>
											</s:if>
											<s:textfield name="%{key}" id="%{key}" cssClass="textField"
												placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" />
										</div>
									</div>
								</s:if>
								<div class="clear"></div>
								<s:else>
									<div class="newColumn">
										<div class="leftColumn1">
											<s:property value="%{value}" />
											:
										</div>
										<div class="rightColumn1">
											<s:if test="%{mandatory}">
												<span class="needed"></span>
											</s:if>
											<s:textfield name="%{key}" id="%{key}" cssClass="textField"
												placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" />
										</div>
									</div>
								</s:else>
							</s:iterator>
							</div> --%>
						
							<s:iterator value="configKeyCheckBox" status="counter">
								<s:if test="%{mandatory}">
									<span id="mandatoryFields" class="pIds" style="display: none;"><s:property
											value="%{key}" />#<s:property value="%{value}" />#<s:property
											value="%{colType}" />#<s:property value="%{validationType}" />,</span>
								</s:if>
							</s:iterator>
							
							
            
           
        
							<s:if test="%{CheckBoxExist != null}">
								<div class="newColumn">
									<div class="leftColumn1">
										<s:property value="%{CheckBox}" />
										:
									</div>
									<div class="rightColumn1" style="margin-left: -320px; margin-top: -9px;">
										<s:if test="%{CheckBoxExist == 'true'}">
											<span class="needed"></span>
										</s:if>
										 <div class="settings-setter">
               								<div id="button1" >
               								
               								<%-- <s:checkbox  id="ackBySMS" 
											 /> --%>
											</div>
											<s:hidden id="button1res" value="true" name="ackBySMS" ></s:hidden>
            							</div>
									</div>

								</div>
							</s:if>

							

							<div id=hide>
								<s:iterator value="configKeyTextArea" status="counter">
									<s:if test="%{mandatory}">
										<span id="mandatoryFields" class="pIds" style="display: none;"><s:property
												value="%{key}" />#<s:property value="%{value}" />#<s:property
												value="%{colType}" />#<s:property value="%{validationType}" />,</span>
									</s:if>

									<s:if test="#counter.odd">
										<div class="newColumn">
											<div class="leftColumn1">
												<s:property value="%{value}" />
												:
											</div>
											<div class="rightColumn1">
												<s:if test="%{mandatory}">
													<span class="needed"></span>
												</s:if>
												<s:textarea name="%{key}" id="%{key}"
													value="Hi, thanks for your information. We have updated the same. Wish you all the best. Regards."
													 cssClass="textField"
													 readonly="true"
													placeholder="Enter Data"
													cssStyle="margin:0px 0px 10px 0px;" />
											</div>
										</div>
									</s:if>
									<s:else>
										<div class="newColumn">
											<div class="leftColumn1">
												<s:property value="%{value}" />
												:
											</div>
											<div class="rightColumn1">
												<s:textarea name="%{key}" id="%{key}" cssClass="textField"
													placeholder="Enter Data"
													cssStyle="margin:0px 0px 10px 0px;" />
											</div>
										</div>
									</s:else>
								</s:iterator>
							</div>

					<!-- changes startsssssssssssssssssssssssssssssssssssssss -->
<div class="clear"></div>

					<s:if test="%{allDeptName1.size()==0}">
						<div class="newColumn">
							<div class="leftColumn1">Department Name:</div>
							<div class="rightColumn1">
								<span class="needed"></span>
								<s:textfield id="deptName" maxlength="50" value="%{deptName}"
									cssClass="textField" disabled="true" placeholder="Enter Data"
									cssStyle="width: 92%" />
								<s:hidden id="deptId" name="deptName" maxlength="50"
									value="%{deptId}" cssClass="textField" placeholder="Enter Data" />
							</div>
						</div>
					</s:if>
					<s:else>
						<%-- <span id="complSpan" class="pIds" style="display: none;">deptIdDD#Department
							Name#D#,</span> --%>
						<div class="newColumn">
							<div class="leftColumn1">Department Name:</div>
							<div class="rightColumn1">
								<span class="needed"></span>
								<s:select cssStyle="width:28%" id="deptIdDD" name="multiDept"
									list="allDeptName1" multiple="true" cssClass="textField"
									onchange="getEmp('deptIdDD')">
								</s:select>
							</div>
						</div>


						<!-- FOR emp -->
						<div class="newColumn">
							<div class="leftColumn1">Employee Name:</div>
							<div class="rightColumn1">
								<span class="needed"></span>
								
								<div id="empdiv">
									 <s:select 
				                   cssStyle="width:28%"
				                   id="empIdDD"
				                   name="multifgdghf" 
				                   list="#{'1': 'no data' }" 
				                   multiple="true"
				                   cssClass="textField"
                    		>
        					</s:select> 
								</div>
							</div>
						</div>

					</s:else>

					<!-- CHANGWES ENDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD  -->
					<%-- 			
		   <div class="newColumn">
	       <div class="leftColumn1">Map With Mail:</div>
	       <div class="rightColumn1">
	        <s:checkbox name="mapWithMail" id="mapWithMail" onclick="mapContact();"  cssStyle="margin:9px 0px 10px 0px" />	</div>
					
	</div> --%>

					<div class="clear"></div>

					<!-- <div id="mapContacts"></div> -->
					<!--<div class="newColumn">
		              	<div class="leftColumn1">Number of Characters:</div>
		              		<div class="rightColumn1">
		              		<input type="text" name="countNum" class="tdhead" maxlength="3" style="border: 1px solid #FFFFFF" readonly size="3">
				            </div>
				    
					</div>

-->
					<div class="clear"></div>


					<div class="clear"></div>
					<div class="fields" align="center">
						<sj:submit targets="result" 
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
								     cssStyle="margin-right: 81px;margin-bottom: -9px;"
								     />

						<sj:a button="true" href="#" onclick="reset('formone');"
							cssClass="submit" cssStyle="margin-top: -28px;">
							
						
						Reset
					</sj:a>
						<sj:a button="true" href="#" onclick="viewKeyword();"
							cssClass="submit"
							cssStyle="margin-right: -137px;margin-top: -28px;">
						
						Back
					</sj:a>
						<sj:div id="foldeffect10" effect="fold">
							<div id="Result7"></div>
						</sj:div>
					</div>
					<div class="clear"></div>


					</s:form>
					
					<div class="clear"></div>
					<div class="clear"></div>
					<div style="margin-left: 32px;margin-top: 35px;"> <b>Note: The given auto reply message has already been approved as per TRAI norms to be delivered on DND and NON DND numbers both. In case, if you wish to request for some other message please contact support.</b> </div>
				</div>
			</div>
		</div>
	</div>
	<div></div>
	<div></div>
</body>
</html>