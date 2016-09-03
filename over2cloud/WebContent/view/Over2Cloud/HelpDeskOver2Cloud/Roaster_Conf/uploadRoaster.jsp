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
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
  <div class="head">Roaster</div><img alt="" src="images/forward.png" style="margin-top:10px; float: left;"><div class="head"> Upload</div>
</div>
<div class="clear"></div>
<div style="height:150px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">

<s:form id="formone" name="formone" action="uploadRoasterExcel" theme="css_xhtml"  method="post" enctype="multipart/form-data" >
<s:hidden name="dataFor" value="%{dataFor}"/>
<s:hidden name="viewType" value="%{viewType}"/>

<center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZoneformtwo" style="float:center; margin-left: 7px"></div></div></center> 	
   <s:iterator value="pageFieldsColumns" status="status">
	         <s:if test="%{mandatory}">
                <span id="mandatoryFields" class="pIdsformtwo" style="display: none; "><s:property value="%{key}"/>1#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
	         <s:if test="#status.odd">
                   <div class="newColumn">
        			   <div class="leftColumn" ><s:property value="%{value}"/>:</div>
					        <div class="rightColumn">
					             <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                  <s:select  
				                  			  id="%{key}1"
				                              name="dept"
				                              list="serviceDeptList"
				                              headerKey="-1"
				                              headerValue="Select Department" 
				                              cssClass="select"
					                          cssStyle="width:82%"
				                              onchange="getServiceDept('subdepartment','id','subdeptname','deptid',this.value,'moduleName','HDM','subdeptname','ASC','subdeptname1','Select Sub-Department');"
				                              >
				                  </s:select>
                           </div>
                   </div>
	          </s:if>
              <s:else>
                     <div class="newColumn">
					      <div class="leftColumn"><s:property value="%{value}"/>:</div>
							<div class="rightColumn">
				             	  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                  <s:select 
				                              id="%{key}1"
				                              name="subdept"
				                              list="{'No Data'}"
				                              headerKey="-1"
				                              headerValue="Select Sub-Department" 
				                              cssClass="select"
					                          cssStyle="width:82%"
				                              >
				                  </s:select>
				           </div>
	                  </div>
                 </s:else>
          </s:iterator>
          
          <div>
		       <span id="mandatoryFields" class="pIdsformtwo" style="display: none; ">empExcel#empExcel#EX#ex,</span>
               <div class="newColumn">
			      <div class="leftColumn" >Excel:</div>
			         <div class="rightColumn">
                         <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                         <s:file name="empExcel" id="empExcel" cssClass="textField" />
                     </div>
			   </div>
			   <div class="newColumn">
			      <div class="leftColumn" >Excel format:</div>
                  <div class="rightColumn">
				     <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                 <a href="<%=request.getContextPath()%>/view/Over2Cloud/HelpDeskOver2Cloud/Roaster_Conf/excel_format/Roaster.xls" ><font color="#194d65">Download </font></a>
               </div>
		      </div>
           </div>     
           <div class="clear"></div>
           <div class="fields">
		    <center>
			<ul>
				<li class="submit" style="background:none;">
					<div class="type-button">
	                <sj:submit 
	                        targets="createExcel" 
	                        clearForm="true"
	                        value="  Upload  " 
	                        button="true"
	                        onBeforeTopics="validateUploadForm"
	                        cssStyle="margin-left: -40px;"
	                        />
				            <sj:a cssStyle="margin-left: 210px;margin-top: -45px;" button="true" href="#" onclick="resetForm('formone');">Reset</sj:a>
				            <sj:a cssStyle="margin-left: 7px;margin-top: -45px;" button="true" href="#" onclick="applyRoaster();">Back</sj:a>
	               </div>
	               </li>
		     </ul>
		     </center>
			</div>
			
			 <center>
			 <sj:dialog id="ExcelDialog" autoOpen="false" closeOnEscape="true"  modal="true" title="Roaster >> Employee List" width="1150"  height="450" showEffect="slide" hideEffect="explode">
                     <center> <div id="createExcel"></div></center>
             </sj:dialog>
             </center>
		    
     </s:form>
</div>
</div>
</body>
</html>
