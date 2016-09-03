<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<% String userRights=session.getAttribute("userRights")== null ? "" : session.getAttribute("userRights").toString();%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/reportconfig.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/fbdraft.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/normalreportvalidation.js"/>"></script>
<script type="text/javascript">
</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Report</div><img alt="" src="images/forward.png" style="margin-top:10px; float: left;"><div class="head"> Search</div>
</div>
<div class="clear"></div>
<div style="float: right; margin: 10px 12px 10px 0px;">
<span class="normalDropDown"></span>
 </div>
<div style="height:320px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">

<s:form id="formone" name="formone" action="viewDownloadReport" theme="css_xhtml"  method="post" enctype="multipart/form-data" >
<s:hidden name="pageType" id="pageType" value="%{pageType}"></s:hidden>
    <center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="errZone" style="float:center; margin-left: 7px"></div>  
    </center>      
    <s:iterator value="pageFieldsColumns" status="status">
        <s:if test="%{mandatory}">
              <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
        </s:if>
        <s:if test="#status.odd">
	         <s:if test="key=='by_dept'">
	               <div class="newColumn">
        			   <div class="leftColumn" ><s:property value="%{value}"/>:</div>
					        <div class="rightColumn">
					             <s:if test="%{mandatory}"><span class="needed"></span></s:if>
					                <s:select 
				                              id="%{key}"
				                              name="%{key}"
				                              list="deptList"
				                              headerKey="-1"
				                              headerValue="By All Department" 
				                              cssClass="select"
					                          cssStyle="width:82%">
                   					</s:select>
					         </div>
					  </div>
	         </s:if>
	         <s:elseif test="key=='to_sdept'">
	         <div class="newColumn">
        			   <div class="leftColumn" ><s:property value="%{value}"/>:</div>
					        <div class="rightColumn">
					             <s:if test="%{mandatory}"><span class="needed"></span></s:if>
					                <s:select 
				                              id="%{key}"
				                              name="%{key}"
				                              list="{'No Data'}"
				                              headerKey="-1"
				                              headerValue="To All Sub-Department" 
				                              cssClass="select"
					                          cssStyle="width:82%"
					                          onchange="commonSelectAjaxCall2('feedback_type','id','fbType','dept_subdept',this.value,'moduleName','HDM','fbType','ASC','feed_type','Select Feedback Type');">
                   					</s:select>
					         </div>
			 </div>
	         </s:elseif>
	         <s:elseif test="key=='category'">
	         <div class="newColumn">
        			   <div class="leftColumn" ><s:property value="%{value}"/>:</div>
					        <div class="rightColumn">
					             <s:if test="%{mandatory}"><span class="needed"></span></s:if>
					                <s:select 
				                              id="%{key}"
				                              name="%{key}"
				                              list="{'No Data'}"
				                              headerKey="-1"
				                              headerValue="All Category" 
				                              cssClass="select"
					                          cssStyle="width:82%"
					                          onchange="commonSelectAjaxCall('feedback_subcategory','id','subCategoryName','categoryName',this.value,'','','subCategoryName','ASC','sub_catg','Select Sub-Category');">
                   					</s:select>
					         </div>
			 </div>
	         </s:elseif>
	         <s:elseif test="key=='from_date'">
	         <div class="newColumn">
        			   <div class="leftColumn" ><s:property value="%{value}"/>:</div>
					        <div class="rightColumn">
					             <s:if test="%{mandatory}"><span class="needed"></span></s:if>
					             	<sj:datepicker name="%{key}" id="%{key}" placeholder="Enter From Date" readonly="true" showOn="focus"  changeMonth="true" changeYear="true" yearRange="2013:2020" maxDate="+12m" showAnim="slideDown" duration="fast" displayFormat="dd-mm-yy" cssClass="textField"/>
					         </div>
			 </div>
	         </s:elseif>
	         <s:elseif test="key=='from_time'">
	          <div class="newColumn">
        			   <div class="leftColumn" ><s:property value="%{value}"/>:</div>
					        <div class="rightColumn">
					             <s:if test="%{mandatory}"><span class="needed"></span></s:if>
					             	<sj:datepicker name="%{key}" id="%{key}" placeholder="Enter From Time" readonly="true" showOn="focus"  timepicker="true"  timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="5" showAnim="slideDown" duration="fast" cssClass="textField"/>
					         </div>
			 </div>
	         </s:elseif>
	         <s:else>
	         <div class="newColumn">
        			   <div class="leftColumn" ><s:property value="%{value}"/>:</div>
					        <div class="rightColumn">
					             <s:if test="%{mandatory}"><span class="needed"></span></s:if>
					             <s:select 
				                              id="%{key}"
				                              name="%{key}"
				                              list="#{'Resolved': 'Resolved'}"
				                              headerKey="All"
				                              headerValue="All" 
				                              cssClass="select"
					                          cssStyle="width:82%">
				                   </s:select>	
					         </div>
			 </div>
	         </s:else>
        </s:if>
        <s:else>
        <s:if test="key=='to_dept'">
        <div class="newColumn">
		      <div class="leftColumn"><s:property value="%{value}"/>:</div>
				<div class="rightColumn">
	             	  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                  <s:select 
	                              id="%{key}"
	                              name="%{key}"
	                              list="serviceDeptList"
	                              headerKey="-1"
	                              headerValue="To All Department" 
	                              cssClass="select"
	                              cssStyle="width:82%"
	                              onchange="getServiceDept('subdepartment','id','subdeptname','deptid',this.value,'moduleName','HDM','subdeptname','ASC','to_sdept','Select Sub-Department');">
	                  </s:select>
	           </div>
	     </div>
        </s:if>
        <s:elseif test="key=='feed_type'">
        <div class="newColumn">
		      <div class="leftColumn"><s:property value="%{value}"/>:</div>
				<div class="rightColumn">
	             	  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                  <s:select 
	                              id="%{key}"
	                              name="%{key}"
	                              list="{'No Data'}"
	                              headerKey="-1"
	                              headerValue="All Feedback Type" 
	                              cssClass="select"
	                              cssStyle="width:82%"
	                              onchange="commonSelectAjaxCall('feedback_category','id','categoryName','fbType',this.value,'','','categoryName','ASC','category','Select Category');">
	                  </s:select>
	           </div>
	     </div>  
        </s:elseif>
        <s:elseif test="key=='sub_catg'">
        <div class="newColumn">
		      <div class="leftColumn"><s:property value="%{value}"/>:</div>
				<div class="rightColumn">
	             	  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                  <s:select 
	                              id="%{key}"
	                              name="%{key}"
	                              list="{'No Data'}"
	                              headerKey="-1"
	                              headerValue="All Feedback Sub category" 
	                              cssClass="select"
	                              cssStyle="width:82%">
	                  </s:select>
	           </div>
	     </div>  
	     </s:elseif>
       <s:elseif test="key=='to_date'">
	         <div class="newColumn">
			      <div class="leftColumn"><s:property value="%{value}"/>:</div>
					<div class="rightColumn">
		             	  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                      <sj:datepicker name="%{key}" id="%{key}" placeholder="Enter To Date" showOn="focus" readonly="true" changeMonth="true" changeYear="true" yearRange="2013:2020" maxDate="+12m" showAnim="slideDown" duration="fast" displayFormat="dd-mm-yy" cssClass="textField"/>
		           </div>
		     </div>
       </s:elseif>
       <s:elseif test="key=='to_time'">
	        <div class="newColumn">
			      <div class="leftColumn"><s:property value="%{value}"/>:</div>
					<div class="rightColumn">
		             	  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                       <sj:datepicker name="%{key}" id="%{key}" placeholder="Enter To Time" readonly="true" showOn="focus"  timepicker="true"  timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="5" showAnim="slideDown" duration="fast" cssClass="textField"/>
		           </div>
		     </div>
        </s:elseif>
       
        </s:else>
    </s:iterator >       
    <div class="clear"></div>
           <div class="fields" align="center">
			<ul>
				<li class="submit" style="background:none;">
					<div class="type-button">
	                <sj:submit 
	                        targets="confirmingEscalation" 
	                        clearForm="true"
	                        value=" Search " 
	                        button="true"
	                        onBeforeTopics="validate"
	                        onCompleteTopics="beforeFirstAccordian"
	                        cssStyle="margin-left: -40px;"
				            />
				            <sj:a cssStyle="margin-left: 125px;margin-top: -29px;" button="true" href="#" onclick="resetForm('formone');">Reset</sj:a>
	               </div>
	               </li>
		     </ul>
			</div>
			
			<center>
			<sj:dialog id="reportDialouge" autoOpen="false"  closeOnEscape="true" modal="true" title="Report View" width="1200"  height="575" showEffect="slide" hideEffect="explode" position="['center','top']">
                     <center><div id="confirmingEscalation"></div></center>
            </sj:dialog>
           </center>
           <div class="clear"></div>
          <br></br>
           <s:if test="%{pageType=='H'.toString()}" >
            <center><b>Note : You can get data here upto 15th July 2014!!!</b></center>
           </s:if>
     </s:form>
</div>
</div>
</body>
</html>
