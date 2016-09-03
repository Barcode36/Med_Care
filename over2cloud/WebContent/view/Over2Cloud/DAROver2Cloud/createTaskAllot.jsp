<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/task_js/task.js"/>"></script>
<script type="text/javascript">
$.subscribe('getDateCompletion', function(event,data)
{
	var fromDate = $("#intiation").val();
	var toDate = $("#completion").val();
     if (Date.parse(fromDate) > Date.parse(toDate))
     {
      	alert("Invalid Date Range!\nStart Date cannot be after End Date!");
      	return false;
	 }
});
$.subscribe('completeData', function(event,data)
{
	  setTimeout(function(){ $("#foldeffect").fadeIn(); }, 10);
      setTimeout(function(){ $("#foldeffect").fadeOut(); }, 4000);
      $('select').find('option:first').attr('selected', 'selected');
      $('#result').hide();
      $('#attachment1').val('');
      mail();
     
});
function mail()
{
	//$(".mail").attr('checked', true);
	 $("#mail").prop( "checked", true );
}
mail();
function resetForm(formId) 
{
	 $('#'+formId).trigger("reset");
}
function projectDetails()
{
	var id=$("#taskId").val();
	
	getOtherDetails(id,'result');
}
projectDetails();
</script>
</head>
<body>
<div class="list-icon">
	 <div class="head">Project</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Registration</div> 
 <div id="test"  class="alignright printTitle">
   		 <sj:a id="register" cssClass="button" button="true" cssStyle="margin-top: 3px;" buttonIcon="ui-icon-plus" title="Register" onClick="createTask();">Register</sj:a>
    </div>


</div>
 <div style=" float:left; padding:5px 0%; width:100%;">
	<div class="border" style="overflow-x:hidden;">
        <s:form id="formtask" name="formtask" action="allotTask" theme="css_xhtml"  method="post" enctype="multipart/form-data" >
		   <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="errZone" style="float:left; margin-left: 7px">
             </div> 
          </div>
              <%--  <div class="newColumn">
								<div class="leftColumn1">UnAlloted Projects:</div>
								<div class="rightColumn1">
	                        <span class="needed"></span>
                 			    <s:select 
                              id="taskId"
                              name="taskId"
                              list="listtask"
                              headerKey="-1"
                              headerValue="Select UnAlloted Projects" 
                              cssClass="select"
                              cssStyle="width:82%"
                              onchange="getOtherDetails(this.value,'result');"
                              >
                  				</s:select>
               </div>
               </div> --%>
               <s:hidden id="dataFor" value="%{dataFor}"/>
               <s:hidden id="taskId" name="taskId" value="%{taskId}"/>
                <div class="clear"></div> 
               <div id="result"></div>
		    <div class="clear"></div> 
		      <s:iterator value="taskDropMap">
		      <s:if test="%{mandatory}">
                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
              </s:if> 
			
              <s:if test="%{key == 'allotedto'}">
              <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                 			    <s:select 
                              id="%{key}"
                              name="%{key}"
                              list="listAllotedemployee"
                              headerKey="-1"
                              headerValue="Select Alloted To" 
                              cssClass="select"
                              cssStyle="width:82%"
                              onchange="getAllotedDetails(this.value,'allotedby','validate_By_1','validate_By_2');"
                              >
                  				</s:select>
               </div>
               </div>   				
               </s:if>
                  <s:if test="%{key == 'guidance'}">
                 <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                 				 <s:select 
                              id="%{key}"
                              name="%{key}"
                              list='listAllotedemployee'
                              headerKey="-1"
                              headerValue="Select Co-owner" 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                  				</s:select>
                  </div>
                  </div>  				
                  </s:if>
              
                </s:iterator>
                
	     		
     		  <s:iterator value="taskDropMap">
	     		 <s:if test="%{mandatory}">
                 <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>
	     		   <s:if test="%{key == 'validate_By_2'}">
                     <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                  				<s:select 
                              id="%{key}"
                              name="%{key}"
                              list="{'No Data'}"
                              headerKey="-1"
                              headerValue="Select Technical Review" 
                              cssClass="select"
                              cssStyle="width:82%"
                              onchange="disableFields(this.value,'technical_Date')"
                              >
                 				 </s:select>
                </div>
                </div>
                </s:if>
	     		</s:iterator>
	     		
	     		<s:iterator value="taskCalenderMap"  status="counter">
                   <s:if test="%{mandatory}">
                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                   </s:if>  
           
				      <s:if test="key == 'technical_Date'">
	                   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <sj:datepicker name="%{key}" timepicker="true"  id="%{key}"  minDate="0" readonly="true" changeMonth="true" changeYear="true" yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" placeholder="Select Date" /></div>					     
					  </div>
					  </s:if>
					
    		    </s:iterator>
    		    
	            <s:iterator value="taskDropMap">
	     		 <s:if test="%{mandatory}">
                 <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>
	     	    <s:if test="%{key == 'validate_By_1'}">
             	<div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                  				<s:select 
                              id="%{key}"
                              name="%{key}"
                              list="{'No Data'}"
                              headerKey="-1"
                              headerValue="Select Functional Review" 
                              cssClass="select"
                              cssStyle="width:82%"
                              onchange="disableFields(this.value,'functional_Date')"
                              >
                 				 </s:select>
               </div>
               </div>
               </s:if>
	     		
	     		</s:iterator>
	     		<s:iterator value="taskCalenderMap"  status="counter" begin="3">
                   <s:if test="%{mandatory}">
                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                   </s:if>  
           
	                   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <sj:datepicker name="%{key}" id="%{key}"  timepicker="true"  minDate="0" readonly="true" changeMonth="true" changeYear="true" yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" placeholder="Select Date" /></div>					     
					  </div>
					
	     		 </s:iterator>
	     		 <s:iterator value="taskCalenderMap"  status="counter" begin="0" end="1">
                   <s:if test="%{mandatory}">
                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                   </s:if>  
           
				      <s:if test="%{key=='intiation'}">
	                   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <sj:datepicker name="%{key}" id="%{key}"  minDate="0" readonly="true" changeMonth="true" changeYear="true" yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" placeholder="Select Date" /></div>					     
					  </div>
					  </s:if>
					   <s:else>
					    <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                               <sj:datepicker name="%{key}" id="%{key}" timepicker="true"  minDate="0" readonly="true"  changeMonth="true" changeYear="true" yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssStyle="margin:0px 0px 10px 0px"   cssClass="textField" placeholder="Select Date" /></div>					     
					  </div>
					   
					   </s:else>
	     		    </s:iterator>
		  <div class="clear"></div> 
		    <s:iterator value="taskFileMap" status="counter" begin="1">
				 <s:if test="%{mandatory}">
                 <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>
		        <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:file name="%{key}" id="%{key}" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"  /></div>
		       </div>
		    </s:iterator>
		    
		      <div class="newColumn">
		      					<div class="leftColumn1">Mail:</div>
								<div class="rightColumn1" style="margin-top: 6px;">
                                <s:checkbox name="mail" id="mail" checked="checked" /></div>
                         
                               
		       </div>
		       <div class="newColumn" style="margin-left: 56%;margin-top: -52px;">
	      		            <div class="leftColumn1">SMS:</div>
							<div class="rightColumn1" style="margin-top: 6px;">
                            <s:checkbox name="sms" id="sms"/></div>			
		       </div> 
		    
		     <div class="clear"></div> 
             <center><img id="indicator222" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
		     <center>
			 <div class="type-button" style="text-align: center;">
	         <sj:submit 
	                        targets="target1" 
	                        clearForm="true"
	                        id="taskADD"
	                        value=" Register " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="completeData"
	                        onBeforeTopics="validate"
	                        cssClass="submit"
	                        indicator="indicator222"
	                        cssStyle="margin-left: -51px;"
	                        />
	   
			        
		              <sj:a cssStyle="margin-left: 181px;margin-top: -45px;" button="true" href="#" onclick="resetForm('formtask');">Reset</sj:a>
		            <s:if test="%{dataFor=='activtyBoard'}">
		               <sj:a cssStyle="margin-left: 0px;margin-top: -45px;" button="true" href="#" onclick="viewactivtyBoard();">Back</sj:a> 
		            </s:if>
		            <s:else>
		               <sj:a cssStyle="margin-left: 0px;margin-top: -45px;" button="true" href="#" onclick="viewTask();">Back</sj:a> 
		            </s:else>
	        </div>
	        </center>
		  <sj:div id="foldeffect" effect="fold">
		  <div id="target1"></div>
		  </sj:div>
	 
	 </s:form>
	  </div>
	  </div>
</body>
</html>
