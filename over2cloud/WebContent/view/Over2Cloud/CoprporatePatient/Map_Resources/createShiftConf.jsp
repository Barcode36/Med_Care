<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/shiftConf.js"/>"></script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<!-- Header Strip Div Start -->
<div class="list-icon">
	 <div class="head">Shift</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div> 
</div>
<div class="clear"></div>
<div style="height: 180px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
<s:form id="formone" name="formone" action="addShiftConf" theme="css_xhtml"  method="post" enctype="multipart/form-data" >
<s:hidden id="moduleName" name="moduleName" value="%{dataFor}"/>
<s:hidden id="viewType" name="viewType" value="%{pageType}"/>
   <center>
     <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
          <div id="errZone" style="float:center; margin-left: 7px"></div>        
     </div>
   </center>
	        <s:iterator value="pageFieldsColumns" begin="0" end="1" status="status">
              <s:if test="#status.odd">
              <s:if test="%{mandatory}">
              <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>1#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
              </s:if>
                   <div class="newColumn">
        			   <div class="leftColumn" ><s:property value="%{value}"/>:</div>
					        <div class="rightColumn">
					             <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                  <s:select  
				                              id="%{key}1"
				                              name="deptname"
				                              list="serviceDeptList"
				                              headerKey="-1"
				                              headerValue="Select Department" 
				                              cssClass="select"
					                          cssStyle="width:82%"
				                              onchange="getServiceDept('subdepartment','id','subdeptname','deptid',this.value,'moduleName','HDM','subdeptname3','ASC','subdeptname3','Select Sub-Department');"
				                              >
				                  </s:select>
                           </div>
                   </div>
	          </s:if>
              <s:else>
              <s:if test="%{mandatory}">
              <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>3#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
              </s:if>
                     <div class="newColumn">
					      <div class="leftColumn"><s:property value="%{value}"/>:</div>
							<div class="rightColumn">
				             	  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                  <s:select 
				                              id="%{key}3"
                              				  name="subdeptname"
				                              list="{'No Data'}"
				                              headerKey="-1"
				                              headerValue="Select Sub-Department" 
				                              cssClass="select"
				                              cssStyle="width:82%">
				                  </s:select>
				           </div>
	                  </div>
                 </s:else>
           </s:iterator>
	        
		   <s:iterator value="pageFieldsColumns" begin="2" end="2">
		     <s:if test="%{mandatory}">
             <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
			 <div class="newColumn">
       			   <div class="leftColumn" ><s:property value="%{value}"/>:</div>
				        <div class="rightColumn">
				         <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				             <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" />
				        </div>
		     </div>
	        </s:iterator>
		        
		    <s:iterator value="pageFieldsColumns" begin="3" status="status">
		   
		     <s:if test="%{mandatory}">
              <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
		      <s:if test="#status.odd">
		       <div class="clear"></div>
                   <div class="newColumn">
        			   <div class="leftColumn"><s:property value="%{value}"/>:</div>
					        <div class="rightColumn">
					             <s:if test="%{mandatory}"><span class="needed"></span></s:if>
					              <sj:datepicker name="%{key}" id="%{key}" placeholder="Enter Data" readonly="true"  showOn="focus"  timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="5" cssClass="textField" />
                           </div>
                   </div>
		        </s:if>
	            <s:else>
                     <div class="newColumn">
					      <div class="leftColumn"><s:property value="%{value}"/>:</div>
							<div class="rightColumn">
				             	  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				             	  <sj:datepicker name="%{key}" id="%{key}" placeholder="Enter Data" readonly="true"  showOn="focus"  timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="5" cssClass="textField" />
				           </div>
	                  </div>
	            </s:else>
              </s:iterator>
              
		   
		 
	   <div class="clear"></div>
		  <!-- Buttons -->
		  <center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
		  <div class="fields">
		  <ul>
			 <li class="submit" style="background:none;">
			 <div class="type-button" align="center">
	         <sj:submit 
	                        targets="target1" 
	                        clearForm="true"
	                        value=" Save " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="beforeFirstAccordian"
	                        cssClass="submit"
	                        onBeforeTopics="validate"
	                        indicator="indicator2"
	                        cssStyle="margin-left: -40px;"
	                        />
	            <sj:a cssStyle="margin-left: 183px;margin-top: -45px;" button="true" href="#" onclick="resetForm('formone');">Reset</sj:a>
	            <sj:a cssStyle="margin-left: 5px;margin-top: -45px;" button="true" href="#" onclick="backForm('viewType','moduleName');">Back</sj:a>
	        </div>
		  </ul>
		  <sj:div id="foldeffect1" cssClass="sub_class1"  effect="fold"><div id="target1"></div></sj:div>
	      </div>
	    </s:form>
</div>
</div>
</body>
</html>
