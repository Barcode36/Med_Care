<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<SCRIPT type="text/javascript" src="js/designation/designation.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/feedback/feedbackForm.js"></SCRIPT>
<SCRIPT type="text/javascript">
$.subscribe('level1', function(event,data)
	       {
	         setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
	       });

</script>

<script type="text/javascript">
	function designationView(){
	 var conP = "<%=request.getContextPath()%>";
	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/commonModules/beforeDesignationView.action",
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
<body>
<div class="list-icon">
	 <div class="head">Designation</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div>
</div>
<div class="clear"></div>
     <div style="width: 100%; center; padding-top: 10px;">
       <div class="border" style="height: 50%">
   <s:form id="formone" name="formone" namespace="/view/Over2Cloud/commonModules" action="createDesignation"  theme="css_xhtml"  method="post"enctype="multipart/form-data" >
                           <center>
			                 <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
                             <div id="errZone" style="float:center; margin-left: 7px"></div>        
                             </div>
                           </center>
				 <s:iterator value="designationDropDown" status="counter">
				  <s:if test="%{mandatory}">
                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>
				  <s:if test="#counter.count%2 !=0">
		         <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}">
				  	<span class="needed">
				  	</span>
				  </s:if>
				  <s:if test="%{key=='mappedOrgnztnId'}">
				  	<s:select 
		                              id="mappedOrgId"
		                              name="mappedOrgId" 
		                              list="officeMap"
		                              headerKey="-1"
		                              headerValue="Select Mapped Office" 
		                              cssClass="textField"
		                              onchange="getDeptNamesForMappedOffice('deptname',this.value);"
		                              >
		                  </s:select>
				  </s:if>
				  <s:elseif test="%{key=='mappedDeptId'}">
				  	<s:select 
		                              id="deptname"
		                              name="deptname" 
		                              list="deptMap"
		                              headerKey="-1"
		                              headerValue="Select Department" 
		                              cssClass="textField"
		                              >
		                  </s:select>
				  </s:elseif>
					</div>
			      </div>
			      </s:if>
			      <s:else>
		          <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}">
				  	<span class="needed">
				  	</span>
				  </s:if>
					<s:if test="%{key=='mappedOrgnztnId'}">
				  	<s:select 
		                              id="mappedOrgId"
		                              name="mappedOrgId" 
		                              list="officeMap"
		                              headerKey="-1"
		                              headerValue="Select Mapped Office" 
		                              cssClass="textField"
		                              onchange="getDeptNamesForMappedOffice('deptname',this.value);"
		                              >
		                  </s:select>
				  </s:if>
				  <s:elseif test="%{key=='mappedDeptId'}">
				  	<s:select 
		                              id="deptname"
		                              name="deptname" 
		                              list="deptMap"
		                              headerKey="-1"
		                              headerValue="Select Department" 
		                              cssClass="textField"
		                              >
		                  </s:select>
				  </s:elseif>
				  </div>
			      </div>
				 </s:else>
				 </s:iterator>
				 <s:iterator value="designationTextBox" status="counter">
				  <s:if test="%{mandatory}">
                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>
				  <s:if test="#counter.count%2 !=0">
		         <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}">
				  	<span class="needed">
				  	</span>
				  </s:if>
				  	<s:textfield name="%{key}" id="%{key}" maxlength="200" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" />
					</div>
					<sj:a value="+" onclick="adddiv('100')" button="true" cssClass="submit" cssStyle="margin-left: 420px;margin-top: -45px;">+</sj:a>
			      </div>
			      </s:if>
			      <s:else>
		          <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}">
				  	<span class="needed">
				  	</span>
				  </s:if>
				  	<s:textfield name="%{key}" id="%{key}" maxlength="200" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" />
				  </div>
			      </div>
				 </s:else>
				 </s:iterator>
				 <div id="msg_div" style="margin-left: 622px;">
					</div>
				 <s:iterator begin="100" end="120" var="deptIndx">
						<div class="clear"></div>
						 <div id="<s:property value="%{#deptIndx}"/>" style="display: none">
							 <s:iterator value="designationTextBox" status="counter">
							<div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/><font color="red"></font>:</div>
								<span class="needed" style="margin-left: -5px; margin-top: 8px; height: 23px;"></span>
								<div class="rightColumn1">
									<s:textfield name="designationname"  id="designationname%{#deptIndx}" cssStyle="width:55%;" cssClass="textField" placeholder="Enter Designation Name" />
									</div>
									
							<div style="float: right; margin-top: -37px;margin-right: 64px">
							<s:if test="#deptIndx==113">
								<div class="user_form_button2"><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true"> - </sj:a></div>
							</s:if>
           					<s:else>
          	 					<div class="user_form_button2"><sj:a value="+"  onclick="adddiv('%{#deptIndx+1}')" button="true"> + </sj:a></div>
          						<div class="user_form_button3" style="padding-left: 10px;"><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true"> - </sj:a></div>
       						</s:else>
       						</div>
							</div>
							</s:iterator>
						</div>
					</s:iterator>
			    <center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				<div class="clear" >
				<div style="padding-bottom: 10px;margin-left:-80px" align="center">
						<sj:submit 
				             targets="level123" 
		                     clearForm="true"
		                     value="Save" 
		                     effect="highlight"
		                     effectOptions="{ color : '#222222'}"
		                     effectDuration="5000"
		                     button="true"
		                     onBeforeTopics="validate"
		                     onCompleteTopics="level1"
			            />
			            &nbsp;&nbsp;
						<sj:submit 
		                     value="Reset" 
		                     button="true"
		                     cssStyle="margin-left: 139px;margin-top: -43px;"
		                     onclick="reset('formone');"
			            />&nbsp;&nbsp;
			            <sj:a
	                        cssStyle="margin-left: 276px;margin-top: -58px;"
						button="true" href="#" value="View" onclick="designationView();" cssStyle="margin-left: 266px;margin-top: -74px;" 
						>View
					</sj:a>
					</div>
               </div>
               <sj:div id="orglevel1Div"  effect="fold">
                   <sj:div id="level123">
                   </sj:div>
               </sj:div>
   </s:form>          
</div>
</div>
</body>
</html>