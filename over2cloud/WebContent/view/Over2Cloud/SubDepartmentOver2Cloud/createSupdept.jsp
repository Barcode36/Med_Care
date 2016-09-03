<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<SCRIPT type="text/javascript" src="js/commanValidation/validation.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/feedback/feedbackForm.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/common/commonContact.js"></SCRIPT>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<SCRIPT type="text/javascript">
$.subscribe('level1', function(event,data)
	       {
	         setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
	       });


function viewsubdept() {
	var conP = "<%=request.getContextPath()%>";
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/commonModules/beforeSubDepartmentView.action?subDeptfalg=1",
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
<div class="list-icon">
	 <div class="head">Sub Department</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div> 
</div>
<div class="clear"></div>
     <div style="width: 100%; center; padding-top: 10px;">
       <div class="border" style="height: 50%">
   <s:form id="formone" name="formone" cssClass="cssn_form" namespace="/view/Over2Cloud/commonModules" action="insertSubDeptData" theme="simple"  method="post"enctype="multipart/form-data" >
                           <center>
			                 <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
                             <div id="errZone" style="float:center; margin-left: 7px"></div>        
                             </div>
                           </center>

	<s:iterator value="pageFieldsColumns" begin="0" end="1" status="status">
          <s:if test="%{mandatory}">
          		<span id="ddSubdept" class="PIds" style="display: none; "><s:property value="%{key}"/>2#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
          </s:if>
	        <s:if test="#status.odd">
                   <div class="newColumn">
        			   <div class="leftColumn" ><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
					        <div class="rightColumn">
					             <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                  <s:select 
				                              id="%{key}2"
				                              list="officeMap"
				                              headerKey="-1"
				                              headerValue="Select Mapped Office" 
				                              cssClass="select"
					                          cssStyle="width:82%"
				                              onchange="commonSelectAjaxCall2('groupinfo','id','groupName','regLevel',this.value,'','','groupName','ASC','groupId2','Select Contact Type');"
				                              >
				                  </s:select>
                           </div>
                   </div>
	          </s:if>
              <s:else>
                     <div class="newColumn">
					      <div class="leftColumn"><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
							<div class="rightColumn">
				             	  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                  <s:select 
				                              id="%{key}2"
				                              list="{'No Data'}"
				                              headerKey="-1"
				                              headerValue="Select Contact Type" 
				                              cssClass="select"
				                              cssStyle="width:82%"
				                              onchange="commonSelectAjaxCall2('department','id','deptName','mappedOrgnztnId',this.value,'','','deptName','ASC','deptid2','Select Department');">
				                  </s:select>
				           </div>
	                  </div>
                 </s:else>
     </s:iterator>
     
     <s:iterator value="pageFieldsColumns" begin="2" end="3" status="status">
          <s:if test="%{mandatory}">
          		<span id="ddSubdept" class="PIds" style="display: none; "><s:property value="%{key}"/>2#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
          </s:if>
	        <s:if test="#status.odd">
                   <div class="newColumn">
        			   <div class="leftColumn" ><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
					        <div class="rightColumn">
					             <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                  <s:select 
				                              id="%{key}2"
				                              name="%{key}"
				                              list="{'No Data'}"
				                              headerKey="-1"
				                              headerValue="Select Department" 
				                              cssClass="select"
					                          cssStyle="width:82%"
				                              >
				                  </s:select>
                           </div>
                   </div>
	          </s:if>
              <s:else>
                     <div class="newColumn">
					      <div class="leftColumn"><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
							<div class="rightColumn">
				             	  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                 <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" />
				           </div>
	                  </div>
                 </s:else>
     </s:iterator>
			      
				 
				 
				
			<div class="clear"></div>
			<br>
            <center><img id="indicator3" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
            <div class="type-button" style="margin-left: -200px;">
            <center> 
                 <sj:submit 
           			   targets="level123" 
                       clearForm="true"
                       value="Save" 
                       effect="highlight"
                       effectOptions="{ color : '#222222'}"
                       effectDuration="5000"
                       button="true"
                       onCompleteTopics="level1"
                       cssClass="submit"
                       cssStyle="margin-right: 3px;margin-bottom: 28px;"
                       indicator="indicator2"
                       onBeforeTopics="validate"
                       onCompleteTopics="level1"
         />
         
         <sj:a 
				     	href="#" 
		               	button="true" 
		               	 onclick="resetform('formone');"
						cssStyle="margin-top: -28px;"						>
					  	Reset
		   </sj:a>
		   
		   <sj:a 
				     	href="#" 
		               	button="true" 
		               	onclick="viewsubdept();"
						cssStyle="margin-right: -137px;margin-top: -28px;"
						>
					  	Back
		   </sj:a>
      </center>   
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