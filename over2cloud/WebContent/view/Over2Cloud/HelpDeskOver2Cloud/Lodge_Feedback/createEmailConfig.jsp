<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Feedback Lodge Via Onlinefghrtf</div>
</div>
<div class="clear"></div>
<div style="overflow:auto; height:430px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
<s:form id="formone" name="formone" action="registerEmailConfigure"  theme="css_xhtml"  method="post" enctype="multipart/form-data">
     
     <div class="secHead">Configure Email ID</div>
     <s:iterator value="emailConfigTextColumns" status="status" begin="0" end="1">
		  <s:if test="#status.odd">
			  <div class="newColumn">
				 <div class="leftColumn"><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
		            <div class="rightColumn">
		                 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                 <s:textfield name="%{key}" id="%{key}"  cssClass="textField" maxlength="80" placeholder="Enter Data" />
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
	  
	  <div class="secHead">Configure Receiver Detail</div>
	  <s:iterator value="subDept_DeptLevelName" status="status">
	        <s:if test="#status.odd">
                   <div class="newColumn">
        			   <div class="leftColumn" ><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
					        <div class="rightColumn">
				                  <s:select 
				                              id="%{key}"
				                              name="%{key}"
				                              list="serviceDeptList"
				                              headerKey="-1"
				                              headerValue="Select Department" 
				                              cssClass="select"
					                          cssStyle="width:82%"
				                              onchange="commonSelectAjaxCall('subdepartment','id','subdeptname','deptid',this.value,'subdeptname','ASC','subdeptname','Select Sub-Department');"
				                              >
				                  </s:select>
                           </div>
                   </div>
	          </s:if>
              <s:else>
                     <div class="newColumn">
					      <div class="leftColumn"><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
							<div class="rightColumn">
				                  <s:select 
				                              id="%{key}"
				                              name="%{key}"
				                              list="{'No Data'}"
				                              headerKey="-1"
				                              headerValue="Select Sub-Department" 
				                              cssClass="select"
				                              cssStyle="width:82%"
				                              onchange="commonSelectAjaxCall('employee_basic','id','empName','subdept',this.value,'empName','ASC','empid','Select Employee');commonSelectAjaxCall('feedback_type','id','fbType','dept_subdept',this.value,'fbType','ASC','feedType','Select Feedback Type');"
				                             >
				                    </s:select>
				           </div>
	                  </div>
                 </s:else>
     </s:iterator>
     
     <s:iterator value="emailConfigTextColumns" status="status" begin="2" end="3">
		  <s:if test="#status.odd">
			  <div class="newColumn">
				 <div class="leftColumn"><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
		            <div class="rightColumn">
		                <s:select 
	                              id="%{key}"
	                              name="%{key}"
	                              list="{'No Data'}"
	                              headerKey="-1"
	                              headerValue="Select Employee" 
	                              cssClass="select"
	                              cssStyle="width:82%">
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
	  
	  <div class="secHead">Configure Category</div>
	  <s:iterator value="emailConfigTextColumns" status="status" begin="4" end="6">
	        <s:if test="#status.odd">
	        <s:if test="key=='feedType'">
                   <div class="newColumn">
        			   <div class="leftColumn" ><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
					        <div class="rightColumn">
				                  <s:select 
				                              id="%{key}"
				                              name="%{key}"
				                              list="{'No Data'}" 
				                              headerKey="-1"
				                              headerValue="Select Feedback Type" 
				                              cssClass="select"
					                          cssStyle="width:82%"
					                          onchange="commonSelectAjaxCall('feedback_category','id','categoryName','fbType',this.value,'categoryName','ASC','category','Select Category');"
				                              >
				                  </s:select>
                           </div>
                   </div>
                  </s:if>
                  <s:else>
                  <div class="newColumn">
        			   <div class="leftColumn" ><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
					        <div class="rightColumn">
					             <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                  <s:select  
				                  			  id="%{key}"
				                  			  name="%{key}"
				                              list="{'No Data'}"
				                              headerKey="-1"
				                              headerValue="Select Sub Category" 
				                              cssClass="select"
					                          cssStyle="width:82%"
				                              >
				                  </s:select>
                           </div>
                   </div>
                  </s:else>
	          </s:if>
              <s:else>
                     <div class="newColumn">
					      <div class="leftColumn"><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
							<div class="rightColumn">
				             	  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                  <s:select 
				                              id="%{key}"
				                              name="%{key}" 
				                              list="{'No Data'}" 
				                              headerKey="-1"
				                              headerValue="Select Category" 
				                              cssClass="select"
				                              cssStyle="width:82%"
				                              onchange="commonSelectAjaxCall('feedback_subcategory','id','subCategoryName','categoryName',this.value,'subCategoryName','ASC','subcatg','Select Sub-Category');"
				                              >
				                  </s:select>
				           </div>
	                  </div>
                 </s:else>
     </s:iterator>
	   <div class="clear"></div>
	    <div class="clear"></div>
	  <div class="fields" align="center">
			<ul>
				<li class="submit" style="background: none;">
					<div class="type-button">
		                <sj:submit 
			                        targets="target1" 
			                        clearForm="true"
			                        value="  Register  " 
			                        button="true"
			                        cssClass="submit"
			                        onCompleteTopics="beforeFirstAccordian" 
			   						indicator="indicator1"  
			             />
	                 </div>
	             </li>
			</ul>
	   </div>
	    <center><img id="indicator1" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
		  <sj:div id="foldeffect1" effect="fold"><div id="target1"></div></sj:div>  
</s:form>
</div>
</div>
</body>
</html>
