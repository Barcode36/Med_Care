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
<SCRIPT type="text/javascript" src="js/common/commonContact.js"></SCRIPT>
<SCRIPT type="text/javascript">
function cancelusermaster(){
		var conP = "<%=request.getContextPath()%>";
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/hr/beforeUserView.action?empModuleFalgForDeptSubDept=1",
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

</SCRIPT>
<SCRIPT type="text/javascript">
function getUserAvailabilty(userName)
{
	
    var conP = "<%=request.getContextPath()%>";
	document.getElementById("status").value="";
	if(userName!="")
	{
		 document.getElementById("indicator2").style.display="block";
		 $.getJSON(conP+"/view/Over2Cloud/hr/checkUserName.action?userName="+userName,
			function(checkEmp){
	    	     $("#userStatus").html(checkEmp.msg);
	    	     document.getElementById("indicator2").style.display="none";
	    	   document.getElementById("status").value=checkEmp.status;
	    });
	}
}
</SCRIPT>
<SCRIPT type="text/javascript">

$.subscribe('checkMe', function(event,data)
        {
			var ctrl = $('select').attr("selectedIndex").text();
			
			alert(ctrl.length);   
			alert(ctrl);   
			   
			event.originalEvent.options.submit = false;	    
         
        });
$.subscribe('level11', function(event,data)
        {
          setTimeout(function(){ $("#orglevel12").fadeIn(); }, 10);
          setTimeout(function(){ $("#orglevel12").fadeOut(); }, 4000);
          $('select').find('option:first').attr('selected', 'selected');
         
        });
</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
	<div class="list-icon">
	<div class="head">User</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div>
	</div>
	<div class="clear"></div>
	<div style="min-height: 10px; height: auto; width: 100%;"></div>
	<div class="border">
		<div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
  		<div id="errZone" style="float:left; margin-left: 7px"></div>        
        </div>
          <div class="clear"></div>
          <s:form id="formone" name="formone" namespace="/view/Over2Cloud/hr" action="addContact" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
                           <center>
			                 <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
                             <div id="errZone" style="float:center; margin-left: 7px"></div>        
                             </div>
                           </center>
				 <s:iterator value="userDD" status="counter">
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
				  <s:if test="%{key=='regLevel'}">
				  	<s:select 
		                              id="regLevel"
		                              name="regLevel" 
		                              list="officeMap"
		                              headerKey="-1"
		                              headerValue="Select Mapped Office" 
		                              cssClass="textField"
		                              onchange="getGroupNamesForMappedOffice('groupId',this.value);"
		                              >
		                  </s:select>
				  </s:if>
				  <s:elseif test="%{key=='groupId'}">
				  	<s:select 
		                              id="groupId"
		                              name="groupId" 
		                              list="groupMap"
		                              headerKey="-1"
		                              headerValue="Select Contact Type" 
		                              cssClass="textField"
		                              onchange="getsubGroupByGroup('subGroupId',this.value);"
		                              >
		                  </s:select>
				  </s:elseif>
				  <s:elseif test="%{key=='subGroupId'}">
				  	<s:select 
		                              id="subGroupId"
		                              name="subGroupId" 
		                              list="{'No Data'}"
		                              headerKey="-1"
		                              headerValue="Select Contact Sub-Type" 
		                              cssClass="textField"
		                              onchange="getContactFor(this.value);"
		                              >
		                  </s:select>
				  </s:elseif>
				  <s:elseif test="%{key=='contactName'}">
				  	<s:select 
		                              id="contactName"
		                              name="contactName" 
		                              list="contactMap"
		                              headerKey="-1"
		                              headerValue="Select Contact" 
		                              cssClass="textField"
		                              onchange="getContactNames(this.value);"
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
				  <s:if test="%{key=='regLevel'}">
				  	<s:select 
		                              id="regLevel"
		                              name="regLevel" 
		                              list="officeMap"
		                              headerKey="-1"
		                              headerValue="Select Mapped Office" 
		                              cssClass="textField"
		                              onchange="getGroupNamesForMappedOffice('groupId',this.value);"
		                              >
		                  </s:select>
				  </s:if>
				  <s:elseif test="%{key=='groupId'}">
				  	<s:select 
		                              id="groupId"
		                              name="groupId" 
		                              list="groupMap"
		                              headerKey="-1"
		                              headerValue="Select Contact Type" 
		                              cssClass="textField"
		                              onchange="getsubGroupByGroup('subGroupId',this.value);"
		                              >
		                  </s:select>
				  </s:elseif>
				  <s:elseif test="%{key=='subGroupId'}">
				  	<s:select 
		                              id="subGroupId"
		                              name="subGroupId" 
		                              list="{'No Data'}"
		                              headerKey="-1"
		                              headerValue="Select Contact Sub-Type" 
		                              cssClass="textField"
		                              onchange="getContactFor(this.value);"
		                              >
		                  </s:select>
				  </s:elseif>
				  <s:elseif test="%{key=='contactName'}">
				  	<s:select 
		                              id="contactName"
		                              name="contactName" 
		                              list="contactMap"
		                              headerKey="-1"
		                              headerValue="Select Contact" 
		                              cssClass="textField"
		                              onchange="getContactNames(this.value);"
		                              >
		                  </s:select>
				  </s:elseif>
				  </div>
			      </div>
				 </s:else>
				 </s:iterator>
				 <div id="contactDetails" >
				 	</div>
				 <div class="clear"></div>
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
		                     onclick="resetForm('formone');"
			            />&nbsp;&nbsp;
			            <sj:a
	                        cssStyle="margin-left: 276px;margin-top: -58px;"
						button="true" href="#" value="View" onclick="viewGroup();" cssStyle="margin-left: 266px;margin-top: -74px;" 
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
</div></body>
</html>


