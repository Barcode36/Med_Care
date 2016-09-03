<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/task_js/task.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/feedback.js"/>"></script>
<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<style type="text/css">
.ui-autocomplete-input {

width:60px;
height:20px;
margin-top:10px;
}
.ui-autocomplete { 
height: 150px; 
overflow-y: 
scroll; 
overflow-x: hidden;
}
</style>
<style type="text/css">
.ui-multiselect  {
width:60px;
}
</style>





<script type="text/javascript">
$.subscribe('completeData', function(event,data)
{
	  setTimeout(function(){ $("#foldeffect").fadeIn(); }, 10);
      setTimeout(function(){ $("#foldeffect").fadeOut(); }, 4000);
      $('select').find('option:first').attr('selected', 'selected');
});
function resetForm(formId) 
{
	 $('#'+formId).trigger("reset");
	 
}

function viewComplaintActivityBoard()
		{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Complaint/viewActivityBoardHeader.action",
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

$(document).ready(function()
{
	$("#ccContactPerson1").multiselect({
	   show: ["bounce", 200],
	   hide: ["explode", 1000]
	});

});
</script>
</head>
<body>
<div class="list-icon">
	 <div class="head">Call</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Feedback</div> 
</div>
 <div style=" float:left; padding:5px 0%; width:100%;">
	<div class="border" style="overflow-x:hidden;height:432px;">
	
        <s:form id="formtask" name="formtask" action="/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Complaint/confirmEmployee4Ticket.jsp" theme="css_xhtml"  method="post" enctype="multipart/form-data" >
		  
		   <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="errZone" style="float:left; margin-left: 7px">
             </div> 
          </div>
		  
		      <s:iterator value="complaintDropMap">
			      <s:if test="%{mandatory}">
	                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	              </s:if> 
			    <s:if test="%{key == 'clientfor'}">
               <div class="newColumn">
								<div class="leftColumn1" style="margin-left: 7px; margin-top:8px;width:80px"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1" style="margin-left: 100px; margin-top:-45px">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                               <%--  <s:select 
                              id="%{key}"
                              name="%{key}"
                              list="#{'PC':'Prospect Client','EC':'Existing Client','PA':'Prospect Associate','EA':'Existing Associate','IN':'Internal'}"
                              headerKey="-1"
                              headerValue="Select Feedback For" 
                              cssClass="select"
                              cssStyle="width:82%"
                              onchange="getClientValues(this.value,'cName')"
                              >
                               </s:select> --%>
                                 <sj:autocompleter
					 id="%{key}"
					name="%{key}"
					   list="#{'PC':'Prospect Client','EC':'Existing Client','PA':'Prospect Associate','EA':'Existing Associate','IN':'Internal'}"
                    selectBox="true"
					selectBoxIcon="true"
					onChangeTopics="autocompleteChange"
					onFocusTopics="autocompleteFocus"
					onSelectTopics="getClientValues1"
					/>
				                  
               </div> 
               </div>               
               </s:if>
              <s:if test="%{key == 'cName'}">
              <div class="newColumn">
								<div class="leftColumn1" style="margin-left: 23px; margin-top:8px;"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1" style="margin-left: 92px;margin-top:-45px">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                 			 <%--    <s:select 
                              id="%{key}"
                              name="%{key}"
                              list="{'No Data'}"
                              headerKey="-1"
                              headerValue="Select Name" 
                              cssClass="select"
                              cssStyle="width:82%"
                               onchange="getOfferingDetails(this.value,'clientfor','offering');getContactPerson(this.value,'clientfor','toContactPerson','ccContactPerson1');"
                              >
                  				</s:select> --%>
                  				
                  				  <sj:autocompleter
									id="%{key}"
									name="%{key}"
							    	list="{'No Data'}"
				                    headerKey="-1"
                          		    headerValue="Select Name" 
                                    selectBox="true"
									selectBoxIcon="true"
									onChangeTopics="autocompleteChange"
									onFocusTopics="autocompleteFocus"
									onSelectTopics="getOfferingDetails1"
					/> 
               </div>
               </div>   				
               </s:if>
              <s:if test="%{key == 'offering'}">
             <div class="newColumn">
								<div class="leftColumn1" style="margin-left: 36px; margin-top:8px;"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1" style="margin-left: 103px; margin-top:-45px">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                  				<%-- <s:select 
                              id="%{key}"
                              name="%{key}"
                              list="{'No Data'}"
                              headerKey="-1"
                              headerValue="Select Offering" 
                              cssClass="select"
                              cssStyle="width:82%"
                              onchange="commonSelectAjaxCall2('feedback_type','id','fbType','dept_subdept',this.value,'moduleName','DREAM_HDM','fbType','ASC','fbType','Select Feedback Type');">
                              
                  				</s:select> --%>
                  				
                  				<sj:autocompleter
									id="%{key}"
									name="%{key}"
							    	list="{'No Data'}"
				                    headerKey="-1"
                          		    headerValue="Select Offering" 
                                    selectBox="true"
									selectBoxIcon="true"
									onChangeTopics="autocompleteChange"
									onFocusTopics="autocompleteFocus"
									onSelectTopics="commonSelectAjaxCall21"
					/>
                  </div>
                  </div>
                </s:if>
              
                </s:iterator>
                
               <s:iterator value="feedbackTypeColumns">
                    <div class="newColumn">
								<div class="leftColumn1"  style="margin-left: -13px; margin-top:8px;width:90px"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1" style="margin-left: 94px;margin-top:-45px">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                  				<%-- <s:select 
                              id="%{key}"
                              name="%{key}"
                              list="{'No Data'}"
                              headerKey="-1"
                              headerValue="Select Feedback Type" 
                              cssClass="select"
                              cssStyle="width:82%"
                               onchange="commonSelectAjaxCall('feedback_category','id','categoryName','fbType',this.value,'','','categoryName','ASC','categoryName','Select Category');"
                              
                              >
                  				</s:select> --%>
                  				
                  				<sj:autocompleter
									id="%{key}"
									name="%{key}"
							    	list="{'No Data'}"
				                    headerKey="-1"
                          		    headerValue="Select Feedback Type" 
                                    selectBox="true"
									selectBoxIcon="true"
									onChangeTopics="autocompleteChange"
									onFocusTopics="autocompleteFocus"
									onSelectTopics="commonSelectAjaxCall1"
					/>
                  </div>
                  </div>
               </s:iterator> 
               
                  <s:iterator value="complaintDropMap">
			      <s:if test="%{mandatory}">
	                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	              </s:if> 
			    <s:if test="%{key == 'toContactPerson'}">
               <div class="newColumn">
								<div class="leftColumn1" style="margin-left: -15px; margin-top:8px;width:104px"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1" style="margin-left: 103px; margin-top:-45px">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                              <%--   <s:select 
                              id="%{key}"
                              name="%{key}"
                              list="{'No Data'}"
                              headerKey="-1"
                              headerValue="Select To Contact Person" 
                              cssClass="select"
                              cssStyle="width:82%"
                              onchange="getccContactPerson(this.value,'clientfor','cName','ccContactPerson')"
                              >
                               </s:select> --%>
                               <sj:autocompleter
									id="%{key}"
									name="%{key}"
							    	list="{'No Data'}"
				                    headerKey="-1"
                          		    headerValue="Select To Contact Person" 
                                    selectBox="true"
									selectBoxIcon="true"
									onChangeTopics="autocompleteChange"
									onFocusTopics="autocompleteFocus"
									onSelectTopics="getccContactPerson1"
					/>
               </div> 
               </div>               
               </s:if>
              <s:if test="%{key == 'ccContactPerson'}">
              <div id="ccPersonDiv">
	              <div class="newColumn">
									<div class="leftColumn1" class="leftColumn1" style="margin-left: -11px; margin-top:8px;width:90px"><s:property value="%{value}"/>:</div>
									<div class="rightColumn1" style="margin-left: 98px;margin-top:-37px">
		                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                 			    <s:select 
	                              id="%{key}1"
	                              name="%{key}"
	                              list="{'No Data'}"
	                                  cssStyle="width:90px"
	                              multiple="true"
	                              
	                              >
	                  				</s:select> 
	                  			 
	               </div>
	               </div> 
               </div>  				
               </s:if>
              </s:iterator> 
               <div class="clear"></div> 
             <s:iterator value="feedbackCategoryColumns">
             
                <s:if test="%{key == 'categoryName'}">
              <div class="newColumn">
								<div class="leftColumn1" style="margin-left: -15px; margin-top:8px;width:104px"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1" style="margin-left: 103px; margin-top:-45px">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                 			   <%--  <s:select 
                              id="%{key}"
                              name="%{key}"
                              list="{'No Data'}"
                              headerKey="-1"
                              headerValue="Select Category" 
                              cssClass="select"
                              cssStyle="width:82%"
                               onchange="commonSelectAjaxCall('feedback_subcategory','id','subCategoryName','categoryName',this.value,'','','subCategoryName','ASC','subCategoryName','Select Sub Category');">
                              
                  			</s:select> --%>
                  			 <sj:autocompleter
									id="%{key}"
									name="%{key}"
							    	list="{'No Data'}"
				                    headerKey="-1"
                          		    headerValue="Select Category" 
                                    selectBox="true"
									selectBoxIcon="true"
									onChangeTopics="autocompleteChange"
									onFocusTopics="autocompleteFocus"
									onSelectTopics="commonSelectAjaxCall2"
					/>
               </div>
               </div>   				
               </s:if>
             
             </s:iterator>
                
              <s:iterator value="feedbackSubCategoryTextColumns" status="counter">
             
               <s:if test="%{key == 'subCategoryName'}">
              <div class="newColumn">
								<div class="leftColumn1" style="margin-left: -13px; margin-top:8px;width:90px"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1" style="margin-left: 94px;margin-top:-45px">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                 			  <%--   <s:select 
                              id="%{key}"
                              name="%{key}"
                              list="{'No Data'}"
                              headerKey="-1"
                              headerValue="Select Sub Category" 
                              cssClass="select"
                              cssStyle="width:82%"
                               onchange="getFeedBreifViaAjax(this.value);"
                              >
                  			</s:select> --%>
                  			<sj:autocompleter
									id="%{key}"
									name="%{key}"
							    	list="{'No Data'}"
				                    headerKey="-1"
                          		    headerValue="Select Sub Category" 
                                    selectBox="true"
									selectBoxIcon="true"
									onChangeTopics="autocompleteChange"
									onFocusTopics="autocompleteFocus"
									onSelectTopics="getFeedBreifViaAjax1"
					/>
               </div>
               </div>   				
               </s:if>
                <s:if test="%{key == 'feedBreif'}">
                <div class="newColumn">
								<div class="leftColumn1" style="margin-left: -15px; margin-top:8px;width:104px"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1" style="margin-left: 103px; margin-top:-40px">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                 			   <s:textfield name="%{key}" id="feed_brief"  maxlength="500" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;width:80px;"  cssClass="textField"/>
               </div>
               </div>   				
               </s:if>
             
             </s:iterator>
             
             
              <s:iterator value="feedbackSubCategoryTimeColumns" status="counter">
             
                <s:if test="%{key == 'resolutionTime'}">
                <div class="newColumn">
								<div class="leftColumn1"style="margin-left: -22px; margin-top:8px;width:100px"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1" style="margin-left: 94px;margin-top:-40px">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                 			   <s:textfield name="%{key}" id="%{key}" readonly="true" maxlength="500" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;width:80px;"  cssClass="textField"/>
               </div>
               </div>   				
               </s:if>
             
             </s:iterator>
             
             
              <s:iterator value="complaintDropMap">
                   <s:if test="%{key == 'dept'}">
         
                    <div class="newColumn">
								<div class="leftColumn1" style="margin-left: -12px; margin-top:1px;width:104px"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1" style="margin-left: 103px; margin-top:-45px">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                  				<%-- <s:select 
                              id="%{key}"
                              name="%{key}"
                              list="deptList"
                              headerKey="-1"
                              headerValue="Select Department" 
                              cssClass="select"
                              cssStyle="width:82%"
                                
                              >
                  				</s:select> --%>
                  				
                  				<sj:autocompleter
									id="%{key}"
									name="%{key}"
							    	list="deptList"
				                    headerKey="-1"
                          		    headerValue="Select Department" 
                                    selectBox="true"
									selectBoxIcon="true"
									onChangeTopics="autocompleteChange"
									onFocusTopics="autocompleteFocus"
									 
					/>
                  </div>
                  </div>
                  </s:if>
               </s:iterator> 
                <div class="clear"></div>
             
          <%--      <div class="newColumn">
						<div class="leftColumn1">For department:</div>
						<div class="rightColumn1">
                           <s:if test="%{mandatory}"><span class="needed"></span></s:if>
               			    <s:select 
                            id="forDept"
                            name="forDept"
                            list="deptList"
                            headerKey="-1"
                            headerValue="Select For department" 
                            cssClass="select"
                            cssStyle="width:82%"
                            >
                			</s:select>
             </div>
             </div>  --%>
           
             <div class="newColumn">
		      					<div class="leftColumn1" style="margin-left: 210px; margin-top:-37px;">Mail:</div>
								<div class="rightColumn1" style="margin-left: 273px; margin-top:-32px">
                                <s:checkbox name="mail" id="mail" /></div>
                         
                               
		       </div>
		       <div class="newColumn"  >
	      		            <div class="leftColumn1" style="margin-left: 284px; margin-top:-37px; ">SMS:</div>
							<div class="rightColumn1" style="margin-left: 349px;margin-top:-32px">
                            <s:checkbox name="sms" id="sms"/></div>			
		       </div> 
		    
		     <div class="clear"></div> 
		     <center>
			 <div class="type-button" style="text-align: center;">
	         <sj:submit 
	                        targets="confirmingEscalation" 
	                        clearForm="true"
	                        value=" Save " 
	                        button="true"
	                        onBeforeTopics="validate"
	                        cssClass="submit"
	                        openDialog="confirmDialogBox"
	                         cssStyle="margin-left: -92px;margin-top:33px"
	                        />
		              <sj:a cssStyle="margin-left: 136px;margin-top: -30px;" button="true" href="#" onclick="resetForm('formtask');">Reset</sj:a>
	         
	        </div>
	        </center>
		  <div align="center">
		  <sj:dialog
	          id="confirmDialogBox"
	          showEffect="slide" 
	    	  hideEffect="explode" 
	    	  openTopics="openEffectDialog"
	    	  closeTopics="closeEffectDialog"
	          autoOpen="false"
	          title="Confirm Compliant Call"
	          modal="true"
	          width="1000"
	          height="350"
	          draggable="true"
	    	  resizable="true"
          >
           <div id="confirmingEscalation"></div>
		</sj:dialog>
         </div>
	 
	 </s:form>
	  </div>
	  </div>
</body>
</html>