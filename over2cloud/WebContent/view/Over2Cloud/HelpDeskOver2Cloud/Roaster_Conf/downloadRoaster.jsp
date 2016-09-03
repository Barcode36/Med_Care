<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/fbdraft.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/fbdraftvalidation.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/roaster.js"/>"></script>
<script type="text/javascript">
$.subscribe('validateRoasterExcel',function(event, data)
 {
    event.originalEvent.options.submit = true;
    $("#ExcelDialog").dialog('open');
 });    
		
function getHomePage()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Roaster_Conf/beforeRoaster.action?flag=apply",
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
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">Roaster</div><img alt="" src="images/forward.png" style="margin-top:10px; float: left;"><div class="head"> Download</div>
</div>
<div class="clear"></div>
<div style="height:110px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">

<!--Start of second accordian for registering the Feedback Sub Category-->
<s:form id="formone" name="formone" action="downloadRoasterExcel" theme="css_xhtml"  method="post" enctype="multipart/form-data" >
<s:hidden name="deptHierarchy" id="deptHierarchy" value="%{deptHierarchy}"></s:hidden>
<s:hidden name="dept" id="dept" value="%{dept}"></s:hidden>
             	
		  <s:iterator value="subDept_DeptLevelName" status="status">
	         <s:if test="%{mandatory}">
            	<span id="mandatoryFields" class="pIdsformthree" style="display: none; "><s:property value="%{key}"/>3#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
   			</s:if>
	         <s:if test="#status.odd">
                   <div class="newColumn">
        			   <div class="leftColumn" ><s:property value="%{value}"/>:</div>
					        <div class="rightColumn">
					             <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                 <s:textfield name="deptName" id="deptName" value="%{DeptName}" cssClass="textField"/>
                           </div>
                   </div>
	          </s:if>
              <s:else>
                     <div class="newColumn">
					      <div class="leftColumn"><s:property value="%{value}"/>:</div>
							<div class="rightColumn">
				             	  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                  <s:select 
				                              id="subdept1"
				                              name="subdept"
				                              list="downloadSubDeptList"
				                              headerKey="-1"
				                              headerValue="All Sub-Department" 
				                              cssClass="select"
				                              cssStyle="width:82%"
				                              >
				                  </s:select>
				           </div>
	                  </div>
                 </s:else>
          </s:iterator>
          <div class="clear"></div>
          <br>
          <div class="fields">
		    <center>
			<ul>
			  <li class="submit" style="background:none;">
				<div class="type-button">
	                <sj:a
	                        targets="createExcel" 
	                        button="true"
	                        onBeforeTopics="validateRoasterExcel"
	                        formIds="formone"
	                        >Search
	                        </sj:a>&nbsp;
	                        
	                         <sj:a 
					     	id="cancel8" 
							href="#" 
							indicator="indicator" 
							button="true" 
							onclick="resetForm('formone');"
							>Reset
					</sj:a>&nbsp;
	                 <sj:a 
					     	id="cancel4" 
							href="#" 
							indicator="indicator" 
							button="true" 
							onclick="applyRoaster();"
							>Back
					</sj:a>
	               </div>
	               </li>
	               </ul>
		     <center> 
		            <sj:dialog id="ExcelDialog" autoOpen="false"  closeOnEscape="true" modal="true" title="Employee List" width="1200"  height="600" showEffect="slide" hideEffect="explode" position="['center','top']">
                         <center><div id="createExcel"></div> </center>
                    </sj:dialog>
             </center>
</s:form>
</div>
</div>
</body>
</html>
