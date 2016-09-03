<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="/js/organization.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<script src="<s:url value="/js/common/commonvalidation.js"/>"></script>
<SCRIPT type="text/javascript">
$.subscribe('completeData', function(event,data)
        {
          setTimeout(function(){ $("#foldeffect").fadeIn(); }, 10);
          setTimeout(function(){ $("#foldeffect").fadeOut(); }, 4000);
         
        });

	function viewDept()
	{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			var conP = "<%=request.getContextPath()%>";
	    $.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/commonModules/beforeDepartmentView.action?deptFlag=1",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}

	function reset(formId) {
		  $("#formId").trigger("reset"); 
		}
</script>
</head>
<body>
<div class="clear"></div>

<div class="middle-content">

<div class="list-icon">
<div class="head">Department</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div>
</div>
	<div class="clear"></div>
	<div style="min-height: 10px; height: auto; width: 100%;"></div>
		<div class="border">
			<div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
  		<div id="errZone" style="float:left; margin-left: 7px"></div>        
        </div>
			<div><br>
				
				<s:form id="formone" name="formone" cssClass="cssn_form" namespace="/view/Over2Cloud/commonModules" action="createDepartment" theme="simple"  method="post"enctype="multipart/form-data" >
				 <s:hidden name="orgLevel" id="orgLevel" value="%{levelOforganization}"></s:hidden>
				 <s:hidden name="levelOforganization" id="levelOforganization" value="%{levelOforganization}"></s:hidden>
				<div class="menubox">
					<div class="newColumn" id="hideThis">
					 <span id="mandatoryFields" class="pIds" style="display: none; ">level#Registration Level#D#a,</span>
						<div class="leftColumn" style="margin-left: -150px;">Registration Level<font color="red"></font>:</div>
						<span class="needed" style="margin-left: -2px; margin-top: 8px;"></span>
						<div class="rightColumn">
							<s:select
                              id="level"
                              name="level" 
                              list="levelList"
                              headerKey="-1"
                              headerValue="Select Level For Creation" 
                              cssClass="select"
                              cssStyle="width:106%"
                              onchange="selectorgLevelORG(this.value,'0')"
                              >
                  			</s:select>
                  			
						</div>
						
					</div>
					
				
				<div class="clear"></div>
					<div class="leftColumn" style="margin-left: 5px;">
						<div class="leftColumn1"><s:property value="%{level1LabelName}"/><font color="red"></font>:</div>
						<span class="needed" style="margin-left: -5px; margin-top: 8px; height: 23px;"></span>
						<div class="rightColumn1"><s:textfield name="level1" id="level1"maxlength="50"  readonly="true" cssStyle="width:74%;"  cssClass="textField" placeholder="Enter Data"/></div>
					</div>
								
					<div class="rightColumn">
					 <span id="mandatoryFields" class="pIds" style="display: none; ">level2org#<s:property value="%{level2LabelName}"/>#D#a,</span>
						<div class="leftColumn1"><s:property value="%{level2LabelName}"/><font color="red"></font>:</div>
						<span class="needed" style="margin-left: -5px; margin-top: 8px; height: 23px;"></span>
						<div class="rightColumn1">
							<s:select 
	                              id="level2org"
	                              name="level2org" 
	                              list="level2"
	                              headerKey="-1"
	                              headerValue="Select Level" 
	                              cssClass="select"
                              	  cssStyle="width:82%"
	                              onchange="getNextLowerLevel3ForDept(this.value,'a','0','0','level3Div','','level4Div','level5Div','','0');"
	                              >
                 				 </s:select>
						</div>
					</div>
					<div class="clear"></div>
					<div id="level3Div"></div><div id="level4Div"></div>
					<div class="clear"></div>	
					<div id="level5Div"></div>
					<div class="clear"></div>	
				
					<s:iterator value="deptDataOther" status="counter">
						<s:if test="#counter.odd">
							<div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1"><s:textfield name="%{key}" id="%{key}"maxlength="50"   cssClass="textField" placeholder="Enter Data"/></div>
							</div>
						</s:if>
						<s:else>
							<div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1"><s:textfield name="%{key}" id="%{key}"maxlength="50"   cssClass="textField" placeholder="Enter Data"/></div>
							</div>
						</s:else>
					</s:iterator>
				 <span id="mandatoryFields" class="pIds" style="display: none; ">deptName#<s:property value="%{deptLabelName}"/>#T#an,</span>
				<div class="clear"></div>	
					<div class="newColumn">
						<div class="leftColumn1"><s:property value="%{deptLabelName}"/><font color="red"></font>:</div>
						<span class="needed" style="margin-left: -5px; margin-top: 8px; height: 23px;"></span>
						<div class="rightColumn1"><s:textfield name="deptName"  id="deptName" cssStyle="width:70%;"  cssClass="textField" placeholder="Enter Department Name"/><sj:a value="+" onclick="adddiv('100')" button="true" cssClass="submit" cssStyle="margin-left: 60px;">+</sj:a></div>
					</div>
					
					<s:iterator begin="100" end="120" var="deptIndx">
						<div class="clear"></div>
						 <div id="<s:property value="%{#deptIndx}"/>" style="display: none">
						 
							 <s:iterator value="deptDataOther" status="counter">
							 	<div class="newColumn">
									<div class="leftColumn"><s:property value="%{value}"/><font color="red"></font> :</div>
									<span class="needed" style="margin-left: -5px; margin-top: 8px; height: 23px;"></span>
									<div class="rightColumn1"><s:textfield name="%{key}" id="%{key}%{#deptIndx}"maxlength="50"  cssClass="textField" placeholder="Enter Data"/></div>
								</div>
							 </s:iterator>
						
							<div class="newColumn">
								<div class="leftColumn1"><s:property value="%{deptLabelName}"/><font color="red"></font>:</div>
								<span class="needed" style="margin-left: -5px; margin-top: 8px; height: 23px;"></span>
								<div class="rightColumn1">
									<s:textfield name="deptName"  id="deptName%{#deptIndx}" cssStyle="width:70%;" cssClass="textField" placeholder="Enter Department Name" />
									</div>
									
							<div style="float: right; margin-top: -37px;">
							<s:if test="#deptIndx==113">
								<div class="user_form_button2"><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true"> - </sj:a></div>
							</s:if>
           					<s:else>
          	 					<div class="user_form_button2"><sj:a value="+"  onclick="adddiv('%{#deptIndx+1}')" button="true"> + </sj:a></div>
          						<div class="user_form_button3" style="padding-left: 10px;"><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true"> - </sj:a></div>
       						</s:else>
       						</div>
							</div>
							
						</div>
					</s:iterator>
					<div class="clear"></div><br><br>
				<div class="fields" align="center">
				<sj:submit 
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
							onclick="viewDept();"
							cssClass="submit"
						>
						
						Back
					</sj:a>
				 <sj:div id="foldeffect"  effect="fold">
                    <div id="Result"></div>
               </sj:div>
				</div>
		
				
              
              
	</div>		</s:form>
	</div>
	</div>
</div>

</body>
</html>