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
<script type="text/javascript" src="js/hr/commonHr.js"></script>
<SCRIPT type="text/javascript">
$.subscribe('level1', function(event,data)
{
  setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
  setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
});

function viewGroup() {
	var conP = "<%=request.getContextPath()%>";

	
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
   $.ajax({
	    type : "post",
	    //url : "/over2cloud/view/Over2Cloud/commonModules/makeHistory.action",
	    url : "view/Over2Cloud/hr/beforeCommonContactViewHeader.action",
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
	 <div class="head">Primary Contact</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div> 
</div>
<div class="clear"></div>
    <div style="width: 100%; center; padding-top: 10px;">
       <div class="border" style="height: 50%">
   <s:form id="formone" name="formone" namespace="/view/Over2Cloud/hr" action="addContact" theme="css_xhtml"  method="post" enctype="multipart/form-data" >
              <center>
       			<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
                <div id="errZone" style="float:center; margin-left: 7px"></div>        
                </div>
              </center>
                           
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0" width="100%">
					    <tbody><tr>
					    <td class="pL10" width="auto" height="20px"> 
                    <s:if test="%{mgtStatus}">
				  	<s:select 
		                              id="office"
		                              list="officeMap"
		                              headerKey="-1"
		                              headerValue="Select Country Office" 
		                              cssClass="button"
		                              cssStyle="float:left;"
		                              theme="simple"
		                              onchange="getheadoffice(this.value,'headOfficeId');"
		                              >
		                  </s:select>
						<s:select 
		                              id="headOfficeId"
		                              list="{'No Data'}"
		                              headerKey="-1"
		                              headerValue="Select Head Office" 
		                              cssClass="button"
		                              cssStyle="float:left;"
		                              theme="simple"
		                              onchange="getbranchoffice(this.value,'regLevel')"
		                              >
		                  </s:select>
		                  </s:if>
		                   <s:if test="%{hodStatus}">
		                  		<s:select 
		                              id="regLevel"
		                              name="regLevel"
		                              list="officeMap"
		                              headerKey="-1"
		                              headerValue="Select Branch Office" 
		                              cssClass="button"
		                              theme="simple"
		                              cssStyle="float:left;"
		                              onchange="getGroupNamesForMappedOffice('groupId',this.value);"
		                              >
		                     </s:select>
		                     </s:if>
				     	     <s:select 
		                              id="groupId"
		                              name="groupId"
		                              list="groupMap"
		                              headerKey="-1"
		                              headerValue="Select Contact Type" 
		                              cssClass="button"
		                               cssStyle="float:left;"
		                              theme="simple"
		                              onchange="getsubGroupByGroup('deptname',this.value);"
		                              >
		                  </s:select>
				  	<s:select 
		                              id="deptname"
		                              name="deptname" 
		                              list="{'No Data'}"
		                              headerKey="-1"
		                              headerValue="Select Contact Sub-Type" 
		                              cssClass="button"
		                               cssStyle="float:left;"
		                              theme="simple"
		                              onchange="getContactFor(this.value);"
		                              >
		                  </s:select>
								
				 
					     </td></tr></tbody>
					  </table>
				  </td>
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
	      		  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
			 <div class="clear"></div> <div class="clear"></div>
				 <div id="contactDetails" >
				 	</div>
				 <div class="clear"></div>
				  <div class="clear"></div>
			    <center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				<div class="clear" >
				<div id="submitDiv" style="display:none; 10px;margin-left:-80px" align="center">
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
						>Back
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