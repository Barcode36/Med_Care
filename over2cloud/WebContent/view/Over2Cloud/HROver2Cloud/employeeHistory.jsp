<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="/js/organization.js"/>"></script>
<script src="<s:url value="/js/employeeOver2Cloud.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<SCRIPT type="text/javascript">
function getAllSwapAndSwappedWithData()
{
	$.ajax({
	    type : "post",
	    url : "<%=request.getContextPath()%>/view/Over2Cloud/hr/getAllSwapAndSwappedWithDataFromAction.action",
	    success : function(companyData) {
		$("#putData").html(companyData);
	},
	   error: function() {
	        alert("error");
	    }
	 });
}
</SCRIPT>
<SCRIPT type="text/javascript">
$.subscribe('completeData', function(event,data)
        {
          setTimeout(function(){ $("#orglevel1").fadeIn(); }, 10);
          setTimeout(function(){ $("#orglevel1").fadeOut(); }, 4000);
         
        });
</script>
</head>
<body>
<div class="page_title"><h1>Employee >> History</h1></div>
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<sj:accordion id="accordion" autoHeight="false">
<sj:accordionItem title="History Regsitration" id="oneId">  
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formone" name="formone" namespace="/view/Over2Cloud/hr" action="makeHistoryOfUser" theme="css_xhtml"  method="post">
				<!-- put these two hidden var alwayz with the same name and id as  orgLevel,levelOforganization for work with multilevel organization -->
				<s:hidden name="orgLevel" id="orgLevel" value="%{levelOforganization}"></s:hidden>
				 <s:hidden name="levelOforganization" id="levelOforganization" value="%{levelOforganization}"></s:hidden>
				 <s:hidden name="deptHierarchy" id="deptHierarchy" value="%{deptHierarchy}"></s:hidden>
				 <!-- put these two hidden var alwayz with the same name and id as  orgLevel,levelOforganization for work with multilevel organization -->
				<div class="form_menubox" id="hideThis">
                  <div class="user_form_text">Registration Level:*</div>
                  <div class="user_form_input">
                  <s:select 
                              id="level"
                              name="level" 
                              list="levelList"
                              headerKey="-1"
                              headerValue="--Select Level For Creation--" 
                              cssClass="form_menu_inputselect"
                              onchange="selectorgLevelORG(this.value,'1');"
                              >
                  </s:select></div>
             	</div>
				
				<div id="showhis" style="display: none;">
				<div class="form_menubox">
                  <div class="user_form_text"><s:property value="%{level1LabelName}"/>:*</div>
                  <div class="user_form_input"><s:label name="level1" id="level1"  maxlength="150" cssClass="form_menu_inputtext"/></div>
                  <div class="user_form_text1"><s:property value="%{level2LabelName}"/>:*</div>
                  <div class="user_form_input">
                  <s:select 
                              id="level2org"
                              name="level2org" 
                              list="level2"
                              headerKey="-1"
                              headerValue="--Select Level--" 
                              cssClass="form_menu_inputselect"
                              onchange="getNextLowerLevel3ForDept(this.value,'b','2','1','level3Div','deptID');"
                              >
                  </s:select></div>
             	</div>
             	<div class="form_menubox">
             	<div id="level3Div"></div><div id="level4Div"></div>
             	</div>
             	<div class="form_menubox">
             	<div id="level5Div"></div>
             	</div>
             	
             <div class="form_menubox">
              <div class="user_form_text"><s:property value="%{deptLabelName}"/>:*</div>
              <div class="user_form_input">
             	 <div id="deptID">
                  <s:select 
                              id="deptname"
                              name="deptname"
                              list="deptList"
                              headerKey="-1"
                              headerValue="--Select Department--" 
                              cssClass="form_menu_inputselect"
                              onchange="getSubDept(this.value,'1','subDeptId');"
                              >
                  </s:select>
                  </div>
                  </div>
                  <s:if test="deptHierarchy>1">
                  <div class="user_form_text1"><s:property value="%{subDeptLabelName}"/>:*</div>
                <div class="user_form_input">
             	 <div id="subDeptId">
                  <s:select 
                              id="subdept"
                              name="subdept"
                              list="subDepartmentList"
                              headerKey="-1"
                              headerValue="--Select Sub-Department--" 
                              cssClass="form_menu_inputselect"
                              >
                  </s:select>
                  </div>
                  </div>
                 </s:if> 
		     </div>
   
	  			 <div class="form_menubox">
	                  <div class="user_form_text">Employee Name:*</div>
	                  <div class="user_form_input">
	                  <div id="AjaxDivp">
	                  <s:select 
	                              cssClass="form_menu_inputselect"
	                              id="empName"
	                              name="empName" 
	                              list="{'No Data'}" 
	                              headerKey="-1"
	                              headerValue="Select Employee Name" 
	                              > 
	                  </s:select>
	                  </div>
	                  </div>
	                 
	                  <div class="user_form_text1">Mobile No:*</div>
	                  <div class="user_form_input">
	                  <div id="AjaxDivMohb">
	                  <s:textfield name="mobileNo" id="mobileNo" cssClass="form_menu_inputtext" maxlength="10" placeholder="Auto Mobile No" readonly="true"/>
	                  </div>
	                  </div>
			     </div>
     
		     
		         <div class="form_menubox">
		          <div class="user_form_text">Name:*</div>
		          <div class="user_form_input"><s:textfield name="name" id="name"  cssClass="form_menu_inputtext" maxlength="100" placeholder="Auto Emp Name" readonly="true"/></div>
				 </div> 

				<!-- Buttons -->
				<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				<div class="fields">
				<ul>
				<li class="submit" style="background: none;">
					<div class="type-button">
	                <sj:submit 
	                        targets="orglevel1Div" 
	                        clearForm="true"
	                        value="  Make History  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="completeData"
	                        cssClass="submit"
	                        indicator="indicator2"
	                        />
	               </div>
				</ul>
				<sj:div id="orglevel1"  effect="fold">
                    <div id="orglevel1Div"></div>
               </sj:div>
               </div>
               </div>
			</s:form>
</div>
</div>
</sj:accordionItem>

<sj:accordionItem title="Swap Employee" id="twoID" onclick="getAllSwapAndSwappedWithData();">  
<center> <b>---------------------NEED TO WOWK LATER ON THIS DUE TO NO MODULE USE------------------ </b></center>
<br>
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formTwo" name="formTwo" namespace="/view/Over2Cloud/hr"  action="swapEmp" theme="css_xhtml"  method="post">
				
				<div id="putData"></div>
				<!-- Buttons -->
				<div class="fields">
				<ul>
				<li class="submit" style="background: none;">
					<div class="type-button">
	                <sj:submit 
	                        targets="Result" 
	                        clearForm="true"
	                        value="  Swapping  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="completeData"
	                        cssClass="submit"
	                        />
	               </div>
				</ul>
				<sj:div id="foldeffect"  effect="fold">
                    <div id="Result"></div>
               </sj:div>
               </div>
			</s:form>
</div>
</div>
</sj:accordionItem>

</sj:accordion>
</div>
</div>
</body>
</html>
