<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="/js/organization.js"/>"></script>
<script src="<s:url value="/js/common/commonvalidation.js"/>"></script>
<script src="<s:url value="/js/employeeOver2Cloud.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<SCRIPT type="text/javascript">
$.subscribe('completeData', function(event,data)
        {
          setTimeout(function(){ $("#foldeffect").fadeIn(); }, 10);
          setTimeout(function(){ $("#foldeffect").fadeOut(); }, 4000);
         
        });

function clearAllErroDiv()
{
	$("div[id^='error']").each(function() {
  	  // `this` now refers to the current element
	     this.innerHTML="";
  	});
}
</script>
<script type="text/javascript">
       function viewSubDept()
	    {
    	   $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		var conP = "<%=request.getContextPath()%>";
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/commonModules/beforeSubDepartmentView.action?subDeptfalg=1",
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
<div class="clear"></div>

<div class="middle-content">

	
<div class="list-icon">
<div class="head">Subdepartment</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div>
</div>
	<div class="clear"></div>
	<div class="border">
		<div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
  		<div id="errZone" style="float:left; margin-left: 7px"></div>        
        </div>
          <div class="clear"></div>
		<div style="width: 100%; text-align: center; padding-top: 10px;">
				<s:form id="formone" name="formone" cssClass="cssn_form" namespace="/view/Over2Cloud/commonModules" action="createSubDepartment" theme="simple"  method="post"enctype="multipart/form-data" >
					<s:hidden name="orgLevel" id="orgLevel" value="%{levelOforganization}"></s:hidden>
					<s:hidden name="levelOforganization" id="levelOforganization" value="%{levelOforganization}"></s:hidden>
					<div class="menubox">
					<div class="newColumn" id="hideThis">
					 <span id="mandatoryFields" class="pIds" style="display: none; ">level#Registration Level#D#a,</span>
						<div class="leftColumn" style="margin-left: -133px;">Registration Level<font color="red"></font> :</div>
						<span class="needed" style="margin-left: -5px; margin-top: 8px; height: 23px;"></span>
                  		<div class="rightColumn">
		                 
		                  <s:select 
		                              id="level"
		                              name="level" 
		                              list="levelList"
		                              headerKey="-1"
		                              headerValue="Select Level For Creation" 
		                              cssClass="textField"
		                              cssStyle="width:123%;"
		                              onchange="selectorgLevelORG(this.value,'0')"
		                              >
		                  </s:select></div>
						
					</div>
					<div class="clear"></div>
						<div class="newColumn">
	
							<div class="leftColumn1"><s:property value="%{level1LabelName}"/><font color="red"></font>:</div>
							<span class="needed" style="margin-left: -5px; margin-top: 8px; height: 23px;"></span>
							<div class="rightColumn1">
							<s:textfield name="level1" id="level1" readonly="true" cssStyle="margin:0px 0px 10px 0px;width:81%;" cssClass="textField" />
							</div>
						</div>
						
						<div class="newColumn">
						 <span id="mandatoryFields" class="pIds" style="display: none; ">level2org#Country#D#a,</span>
							<div class="leftColumn1" style="margin-left: -55px;"><s:property value="%{level2LabelName}"/><font color="red"></font>:</div>
							<span class="needed" style="margin-left: -5px; margin-top: 8px; height: 23px;"></span>
							<div class="rightColumn1">
								<s:select 
	                              id="level2org"
	                              name="level2org" 
	                              list="level2"
	                              headerKey="-1"
	                              headerValue="Select Level" 
	                               cssClass="textField"
		                           cssStyle="width:82%;"
	                              onchange="getNextLowerLevel3ForDept(this.value,'b','0','0','level3Div','deptID','level4Div','level5Div','','0');"
	                              >
	                  			</s:select>
							</div>
						</div>
						<div class="clear"></div>
						<div id="level3Div"></div>
						<div id="level4Div"></div>
						<div class="clear"></div>
						<div id="level5Div"></div>
						<div class="clear"></div>
						<div class="newColumn">
						        <span id="mandatoryFields" class="pIds" style="display: none; ">deptname#<s:property value="%{deptLabelName}"/>#D#a,</span>
							<div class="leftColumn1"><s:property value="%{deptLabelName}"/><font color="red"></font>:</div>
							<span class="needed" style="margin-left: -5px; margin-top: 8px; height: 23px;"></span>
							<div class="rightColumn1">
							<div id="deptID" style="width: 100%;">
								<s:select 
		                              id="deptname"
		                              name="deptname"
		                              list="deptList"
		                              headerKey="-1"
		                              headerValue="Select Department" 
		                              cssClass="textField"
		                              cssStyle="width:82%;"
		                              onchange="getSubDept(this.value,'0','subdeptid');"
		                              >
		                 	 	</s:select>
		                 	 	</div>
		                
							</div>
						</div>
						<div class="newColumn">
						   <span id="mandatoryFields" class="pIds" style="display: none; ">subdeptName#<s:property value="%{subDeptLabelName}"/>#T#a,</span>
							<div class="leftColumn1" style="margin-left: -54px;"><s:property value="%{subDeptLabelName}"/><font color="red"></font>:</div>
							<span class="needed" style="margin-left: -5px; margin-top: 8px; height: 23px;"></span>
							<div class="rightColumn1">
								<s:textfield name="subdeptName"  id="subdeptName" cssStyle="width:80%;" cssClass="textField" onblur="hideErrorDiv('errorOfficDept')" placeholder="Enter Sub-Department Name"/> <sj:a value="+" onclick="adddiv('100')" button="true" cssClass="button" cssStyle="margin-left: 5px;">+</sj:a>
							</div>
						</div>
						<div class="clear"></div>
							<s:iterator value="subDeptOtherData" status="counter">
								<s:if test="#counter.odd">
									<div class="newColumn">
									<span id="mandatoryFields" class="pIds" style="display: none; ">subdeptName#<s:property value="%{value}"/>#T#a,</span>
										<div class="leftColumn1"><s:property value="%{value}"/><font color="red"></font>: </div>
										<span class="needed" style="margin-left: -5px; margin-top: 8px; height: 23px;"></span>
										<div class="rightColumn1"><s:textfield name="%{key}" id="%{key}"maxlength="50" cssStyle="margin:0px 0px 10px 0px" cssClass="form_menu_inputtext" placeholder="Enter Data"/></div>
									</div>
								</s:if>
								<s:else>
									<div class="newColumn">
									<span id="mandatoryFields" class="pIds" style="display: none; ">subdeptName#<s:property value="%{value}"/>#T#a,</span>
										<div  class="leftColumn"><s:property value="%{value}"/>:</div>
										<div  class="rightColumn">
											<s:textfield name="%{key}" id="%{key}"maxlength="50" cssStyle="margin:0px 0px 10px 0px" cssClass="textField" placeholder="Enter Data"/>
										</div>
									</div>
							 	</s:else>
							 </s:iterator>
							 
							 <div class="clear"></div>
							 <s:iterator begin="100" end="120" var="deptIndx">
								<div id="<s:property value="%{#deptIndx}"/>" style="display: none">
							       <div class="form_menubox">
									 <s:iterator value="subDeptOtherData" status="counter">
									 <s:if test="#counter.count%2 != 0">
					                  <div class="user_form_text"><s:property value="%{value}"/>:</div>
					                  <div class="user_form_input"><s:textfield name="%{key}" id="%{key}%{#deptIndx}" maxlength="50" cssStyle="margin:0px 0px 10px 0px;width:80%;" cssClass="form_menu_inputtext" placeholder="Enter Data"/></div>
									 </s:if><s:else>
									  <div class="user_form_text1"><s:property value="%{value}"/>:</div>
					                  <div class="user_form_input"><s:textfield name="%{key}" id="%{key}%{#deptIndx}" maxlength="50" cssStyle="margin:0px 0px 10px 0px;width:80%;" cssClass="form_menu_inputtext" placeholder="Enter Data"/></div>
									 </s:else>
									 </s:iterator>
									</div>
									 
									 <div style="float:left; width:50%;">
							           <div class="label" style="float:left; width:24%;"><s:property value="%{subDeptLabelName}"/><font color="red"></font>:</div>
							           <span class="needed" style="margin-left: -5px; margin-top: 8px; height: 23px;"></span>
							           <div class="label" style="float:left; text-align:left; width:70%;"><s:textfield name="subdeptName" cssStyle="width:80%;" id="subdeptName%{#deptIndx}"  cssClass="textField" placeholder="Enter Sub-Department Name" />
							           </div>
							           
							           <div style="float: right;margin-right: -17px; margin-top: -35px;">
							            <s:if test="#deptIndx==120">
							                  <sj:submit value="-" onclick="deletediv('%{#deptIndx}')" button="true"/>
							            </s:if>
							     
							            <s:else>
							               <sj:submit value="+" onclick="adddiv('%{#deptIndx+1}')" button="true" cssClass="button" cssStyle="margin-left: 5px;"/>
							               <sj:submit value="-" onclick="deletediv('%{#deptIndx}')" button="true" cssClass="button" cssStyle="margin-left: 5px;"/>
							            </s:else>
							            </div>
							       </div>
							  	</div>
							  	
						</s:iterator>
						
						<div class="clear"></div><br>
				<div class="buttonStyle">
				<sj:submit 
				            id="xxxxx"
	                        targets="Result" 
	                        clearForm="true"
	                        value="  Save  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="completeData"
	                        cssClass="submit"
	                        indicator="indicator2"
	                        onBeforeTopics="validate"
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
							onclick="viewSubDept();"
							cssClass="submit"
						>
						
						Back
					</sj:a>
				
				 <sj:div id="foldeffect"  effect="fold">
                    <div id="Result"></div>
               </sj:div> 
					</div>
				
				
</div>				</s:form>
				
		</div>
	</div>
</div>
</body>
</html>