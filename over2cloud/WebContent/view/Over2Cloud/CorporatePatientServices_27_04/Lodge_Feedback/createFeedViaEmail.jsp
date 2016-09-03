<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/helpdesk/fbbyemail.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/lodgefeedbackvalidation.js"/>"></script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Feedback Lodge Via Email</div>
</div>
<div class="clear"></div>
<div style="float: right; margin: 10px 12px 10px 0px;">
<sj:a id="configureemail"  button="true"  buttonIcon="ui-icon-arrowreturnthick-1-n" cssClass="button" onclick="configureEmail();">Configure Email</sj:a>
<span class="normalDropDown"></span>
 </div>
<div style="overflow:auto; height:390px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">

<s:form id="formone" name="formone" action="FeedbackViaOnline"  theme="css_xhtml"  method="post" enctype="multipart/form-data">
<s:hidden name="viaFrom" value="online"></s:hidden>
<s:hidden name="subCategory_extra" id="test"></s:hidden>
		<center>
			<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
	             <div id="errZone" style="float:center; margin-left: 7px"></div>        
	        </div>
        </center>    
        
        <s:iterator value="subDept_DeptLevelName" status="status">
			  <s:if test="%{mandatory}">
					     <span id="normalSubdept" class="pIds" style="display: none; "><s:property value="%{key}"/>3#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
					     <span id="ddSubdept" class="ddPids" style="display: none; "><s:property value="%{key}"/>3#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
		      </s:if>
        <s:if test="#status.odd">
                   <div class="newColumn">
        			   <div class="leftColumn" ><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
					        <div class="rightColumn">
					             <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                  <s:select  id="%{key}3"
				                              name="todept"
				                              list="serviceDeptList"
				                              headerKey="-1"
				                              headerValue="Select Department" 
				                              cssClass="select"
					                          cssStyle="width:82%"
				                              onchange="getSubDept(this.value,'subDeptDiv3',%{deptHierarchy},'3','1','1','1');"
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
				             	  <div id="subDeptDiv3">
				                  <s:select 
				                              id="%{key}3"
                              				  name="tosubdept"
				                              list="{'No Data'}"
				                              headerKey="-1"
				                              headerValue="Select Sub-Department" 
				                              cssClass="select"
				                              cssStyle="width:82%">
				                  </s:select>
				                </div>
				           </div>
	                  </div>
                 </s:else>
     </s:iterator>
     
     <s:iterator value="feedbackDDColumns" status="status">
	  <s:if test="%{mandatory}">
             	<s:if test="key=='feedType'">
             		<span id="normalSubdept" class="pIds" style="display: none; ">fbType3#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             		<span id="ddSubdept" class="ddPids" style="display: none; ">fbType3#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>	
             	</s:if>
             	<s:elseif test="key=='category'">
             		<span id="normalSubdept" class="pIds" style="display: none; ">categoryName3#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             		<span id="ddSubdept" class="ddPids" style="display: none; ">categoryName3#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             	</s:elseif>
             	<s:else>
             		<span id="normalSubdept" class="pIds" style="display: none; "><s:property value="%{key}"/>3#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             		<span id="ddSubdept" class="ddPids" style="display: none; "><s:property value="%{key}"/>3#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             	</s:else>
      </s:if>
	      <s:if test="#status.odd">
	      <div class="newColumn">
	    	<div class="leftColumn" ><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
		        <div class="rightColumn">
		             <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		             <div id="feedTypeDiv3">
	                  <s:select  
	                  			  id="%{key}3"
	                              name="feedbackType"
	                              list="{'No Data'}"
	                              headerKey="-1"
	                              headerValue="Select Feedback Type" 
	                              cssClass="select"
		                          cssStyle="width:82%"
	                              >
	                  </s:select>
	                  </div>
	             </div>
	         </div>
	       </s:if>
	       <s:else>
	          <div class="newColumn">
		      <div class="leftColumn"><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
				<div class="rightColumn">
	             	  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	             	  <div id="categoryDiv3">
	                  <s:select 
	                              id="categoryId3"
	                              name="category" 
	                              list="{'No Data'}" 
	                              headerKey="-1"
	                              headerValue="Select Category" 
	                              cssClass="select"
	                              cssStyle="width:82%">
	                  </s:select>
	                </div>
	             </div>
	            </div>
	      </s:else>
     </s:iterator>
     
     <s:iterator value="feedbackDTimeColumns" status="status">
	  <s:if test="%{mandatory}">
	   		 <span id="normalSubdept" class="pIds" style="display: none; "><s:property value="%{key}"/>3#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
       		 <span id="ddSubdept" class="ddPids" style="display: none; "><s:property value="%{key}"/>3#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
       </s:if>
      <s:if test="#status.odd">
      <div class="newColumn">
    	<div class="leftColumn" ><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
	        <div class="rightColumn">
	             <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	             <div id="subCategoryDiv3">
                  <s:select  
                  			  id="%{key}3"
                              name="subCategory"
                              list="{'No Data'}"
                              headerKey="-1"
                              headerValue="Select Sub Category" 
                              cssClass="select"
	                          cssStyle="width:82%"
                              >
                  </s:select>
                  </div>
             </div>
         </div>
       </s:if>
       <s:else>
          <div class="newColumn">
	      <div class="leftColumn"><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
			<div class="rightColumn">
             	  <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" />
             </div>
            </div>
      </s:else>
     </s:iterator>
     
     <s:iterator value="feedbackTextColumns" status="status">
      <s:if test="%{mandatory}">
          	 		<span id="normalSubdept" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             		<span id="ddSubdept" class="ddPids" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
      </s:if>
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
	 
	 <div class="newColumn">
	     <div class="leftColumn"></div>
	     	<div class="rightColumn" >
	     		<div style="width: auto; float: left; text-align: left; line-height:25px; margin-right: 10px;">SMS:</div><div style="width: auto; float: left; text-align: left;  line-height:25px; margin-right: 10px; vertical-align: middle; padding-top: 5px;"><s:checkbox name="recvSMS" id="recvSMS"></s:checkbox></div>
	     		<div style="width: auto; float: left; text-align: left; line-height:25px; margin-right: 10px; margin-left: 20px;">Email:</div><div style="width: auto; float: left; text-align: left;  line-height:25px; margin-right: 10px; vertical-align: middle; padding-top: 5px;"><s:checkbox name="recvEmail" id="recvEmail"></s:checkbox></div>
	     	</div>
        </div>
	  <div class="clear"></div>
	  
	 <div class="fields" align="center">
				<ul>
				<li class="submit" style="background: none;">
					<div class="type-button">
	                <sj:submit 
	                        targets="confirmEscalationDialog" 
	                        clearForm="true"
	                        value="  Register  " 
	                        button="true"
	                        onBeforeTopics="validateOnline"
	                        cssClass="submit"
	                        />
	               </div>
	               </li>
				</ul>
				</div>
				
			    <center>
			         <sj:dialog id="confirmEscalationDialog" autoOpen="false"  closeOnEscape="true" modal="true" title="Success Message" width="700" height="100" showEffect="slide" hideEffect="explode" position="['center','top']"></sj:dialog>
                </center>
</s:form>
</div>
</div>
</body>
</html>
