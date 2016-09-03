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
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Feedback Draft</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Download</div> 
</div>
<div class="clear"></div>
<div style="height:150px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
<s:hidden id="dataFor" name="dataFor" value="%{dataFor}"/>
<s:form id="formone" name="formone" action="fbDraftExcel" theme="css_xhtml"  method="post" enctype="multipart/form-data" >
<s:hidden name="deptId" id="deptId" value="%{deptId}"></s:hidden>
<s:hidden id="moduleName" name="moduleName" value="%{dataFor}"/>
<s:hidden id="viewType" name="viewType" value="%{pageType}"/>

<center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZoneformthree" style="float:center; margin-left: 7px"></div></div></center>	
		  <s:iterator value="pageFieldsColumns" begin="0" end="0" status="status">
	         <s:if test="%{mandatory}">
            	<span id="mandatoryFields" class="pIdsformthree" style="display: none; "><s:property value="%{key}"/>3#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
   			</s:if>
	         <s:if test="#status.odd">
                   <div class="newColumn">
        			   <div class="leftColumn" ><s:property value="%{value}"/>:</div>
					        <div class="rightColumn">
					             <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                 <s:textfield name="deptName" id="deptName3" value="%{deptName}" cssClass="textField"/>
                           </div>
                   </div>
	          </s:if>
          </s:iterator>
     
	   <s:iterator value="pageFieldsColumns" begin="2" end="3" status="status"> 
	   <s:if test="#status.odd">
	    <s:if test="%{mandatory}">
            	<span id="mandatoryFields" class="pIdsformthree" style="display: none; "><s:property value="%{key}"/>3#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
   			</s:if>
	     <div class="newColumn">
			 <div class="leftColumn" ><s:property value="%{value}"/>:</div>
			 <div class="rightColumn">
			     <s:if test="%{mandatory}"><span class="needed"></span></s:if>
			     <s:select 
                             id="%{key}3"
                             name="%{key}" 
                             list="feedTypeList"
                             headerKey="-1"
                             headerValue="Select Feedback Type"
                             cssClass="select"
				             cssStyle="width:82%"
				             onchange="commonSelectAjaxCall('feedback_category','id','categoryName','fbType',this.value,'','','categoryName','ASC','categoryName3','Select Category');">
                  </s:select>
			 </div>
		 </div>
		 </s:if>
		 <s:else>
	     <div class="newColumn">
			<div class="leftColumn"><s:property value="%{value}"/>:</div>
			<div class="rightColumn">
                  <s:select 
                            id="%{key}3"
                            name="%{key}" 
                            list="{'No Data'}" 
                            headerKey="-1"
                            headerValue="Select Category"
                            cssClass="select"
				            cssStyle="width:82%">
                  </s:select>
	        </div>
	     </div>
	     </s:else>
	     </s:iterator>
	     <div class="clear"></div>             
	   <div class="fields" align="center">
			<ul>
				<li class="submit" style="background:none;">
					<div class="type-button">
	                <sj:submit 
	                        targets="createExcel" 
	                        clearForm="true"
	                        value=" Search " 
	                        button="true"
	                        onCompleteTopics="beforeFirstAccordian"
	                        onBeforeTopics="validateDownloadForm"
	                        cssStyle="margin-left: -40px;"
				            />
				            <sj:a cssStyle="margin-left: 190px;margin-top: -45px;" button="true" href="#" onclick="resetForm('formone');">Reset</sj:a>
				             <sj:a cssStyle="margin-left: 5px;margin-top: -45px;" button="true" href="#" onclick="backForm('dataFor');">Back</sj:a>
	               </div>
	               </li>
		     </ul>
			</div>
			  
			   <center>
		      <sj:dialog id="ExcelDialog" autoOpen="false" closeOnEscape="true" modal="true" title="Feedback Draft Data" width="1150" height="450" showEffect="slide" hideEffect="explode">
                     <center> <div id="createExcel"></div> </center>
         </sj:dialog>
          </center>
     </s:form>
</div>
</div>
</body>
</html>
