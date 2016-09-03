<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/fbdraft.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/fbdraftvalidation.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>


</head>
<body>
<div class="page_title"><h1>Feedback Draft Registration</h1><span class="needed_block">= indicates mandatory fields</span></div>
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<sj:accordion id="accordion" autoHeight="false">
<s:if test="levelOfFeedDraft>0">
<sj:accordionItem title="%{feedLevel1}" id="acor_item1">  
<div class="form_inner" id="form_reg">
<div class="page_form">
	<s:form id="formone" name="formone" action="addFeedbackType" theme="css_xhtml"  method="post" enctype="multipart/form-data" >
			<s:hidden name="deptHierarchy" id="deptHierarchy" value="%{deptHierarchy}"></s:hidden>
             	
            <div class="form_menubox">
             <s:iterator value="subDept_DeptLevelName" status="status">
              <s:if test="#status.odd">
              <div class="inputmain">
              <div class="user_form_text"><s:property value="%{value}"/>:</div>
              <div class="user_form_input inputarea">
              <span class="needed"></span>
                  <s:select 
                              id="deptname1"
                              name="deptname"
                              list="deptList"
                              headerKey="-1"
                              headerValue="Select Department" 
                              cssClass="form_menu_inputselect"
                              onchange="getSubDept(this.value,'subDeptDiv1',%{deptHierarchy},'1','0','0','0');"
                              >
                  </s:select>
                  <div id="errordept" class="errordiv"></div>
              </div>
              </div>
             </s:if>
             <s:else>
             <div class="inputmain">
              <div class="user_form_text1"><s:property value="%{value}"/>:</div>
              <span class="needed"></span>
              <div class="user_form_input inputarea">
              <div id="subDeptDiv1">
                  <s:select 
                              id="subdept1"
                              name="subdept"
                              list="{'No Data'}"
                              headerKey="-1"
                              headerValue="Select Sub-Department" 
                              cssClass="form_menu_inputselect"
                              >
                  </s:select>
              </div>
               <div id="errorsubdept" class="errordiv"></div>
              </div>
              </div>
                </s:else>
              </s:iterator>
		     </div>
		     
		    <s:iterator value="feedbackTypeColumns">
		     <div class="form_menubox double_add_button">
		         <div class="user_form_text"><s:property value="%{value}"/>:</div>
                 <div class="user_form_input" style="position:relative;"><span class="needed"></span><s:textfield name="%{key}" id="%{key}1"   cssClass="form_menu_inputtext" placeholder="Enter Feedback Type"/><div id="errorFBType" class="errordiv"></div>
		         <div class="user_form_button2 add_button" style="position:absolute; top:3px; right:-60px;"><sj:submit value="+" onclick="adddiv('100')" button="true"/></div></div>
		    </div>
		    
		    
		    <s:iterator begin="100" end="118" var="fbTypeIndx">
	        <div id="<s:property value="%{#fbTypeIndx}"/>" style="display: none">
            <div class="form_menubox double_add_button">
               <div class="user_form_text"><s:property value="%{value}"/>:</div>
               <div class="user_form_input" style="position:relative;"><span class="needed"></span><s:textfield name="%{key}" id="%{key}%{#fbTypeIndx}" cssClass="form_menu_inputtext" placeholder="Enter Feedback Type"/>
     
               <s:if test="#fbTypeIndx==118">
               <div class="user_form_button2 del_button" style="position:absolute; top:3px; right:-60px;"><sj:submit value="-" cssClass="text-bold" onclick="deletediv('%{#fbTypeIndx}')" button="true"/></div>
               </s:if>
     
               <s:else>
               <div class="user_form_button2 add_button" style="position:absolute; top:3px; right:-60px;"><sj:submit value="+" cssClass="text-bold"  onclick="adddiv('%{#fbTypeIndx+1}')" button="true"/></div>
               <div class="user_form_button3 del_button" style="position:absolute; top:3px; right:-97px;"><sj:submit value="-" cssClass="text-bold" onclick="deletediv('%{#fbTypeIndx}')" button="true"/></div>
               </s:else></div>
            </div>
            </div>
	        </s:iterator>  
		    
		    </s:iterator>
		   
     
		    <div class="fields">
			<ul>
				<li class="submit" style="background:none;">
					<div class="type-button">
	                <sj:submit 
	                        targets="target1" 
	                        clearForm="true"
	                        value="  Add  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="beforeFirstAccordian"
	                        cssClass="submit"
	                        indicator="indicator1"
	                        onBeforeTopics="validateFbType"
	                        />
	               </div>
		     </ul>
			</div>
			<center><img id="indicator1" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
			<sj:div id="foldeffect1" cssClass="sub_class1" ><div id="target1"></div></sj:div>
</s:form>
</div>
</div>
</sj:accordionItem>
</s:if>


<s:if test="levelOfFeedDraft>1">
<sj:accordionItem title="%{feedLevel2}" id="acor_item2">  
<div class="form_inner" id="form_reg">
<div class="page_form">
<s:form id="formtwo" name="formtwo" action="addFeedCategory"  theme="css_xhtml"  method="post" enctype="multipart/form-data" >
<s:hidden name="deptHierarchy" id="deptHierarchy1" value="%{deptHierarchy}"></s:hidden>
             	
   <div class="form_menubox">
   <s:iterator value="subDept_DeptLevelName" status="status">
                   <s:if test="#status.odd">
                   <div class="inputmain">
                   <div class="user_form_text" style="text-indent:20px;"><s:property value="%{value}"/>:</div>
                   <span class="needed"></span>
	              <div class="user_form_input inputarea">
	                  <s:select 
	                              id="deptname2"
	                              name="deptname"
	                              list="deptList"
	                              headerKey="-1"
	                              headerValue="Select Department" 
	                              cssClass="form_menu_inputselect"
	                              onchange="getSubDept(this.value,'subDeptDiv2',%{deptHierarchy},'1','1','0','0');"
	                              >
	                  </s:select>
	                   <div id="errordept2" class="errordiv"></div>
	                  </div>
	                  </div>
	                  </s:if>
                      <s:else>
                       <div class="inputmain">
                      <div class="user_form_text1" style="text-indent:10px;"><s:property value="%{value}"/>:</div>
	                  <div class="user_form_input inputarea"><span class="needed"></span>
	             	 <div id="subDeptDiv2">
	                  <s:select 
	                              id="subdept2"
	                              name="subdept"
	                              list="{'No Data'}"
	                              headerKey="-1"
	                              headerValue="Select Sub-Department" 
	                              cssClass="form_menu_inputselect sub_width1"
	                              >
	                  </s:select>
	                  </div>
	                  <div id="errorsubdept2" class="errordiv"></div>
	                  </div>
	                  </div>
                   </s:else>
              </s:iterator>
		     </div>
		     
		     <% int temp1=0; %>
		     <s:iterator value="feedbackCategoryColumns">
		     <%if(temp1%2==0) {%>
		     <div class="form_menubox double_add_button" style="position:relative;">
              <div class="user_form_text"><s:property value="%{value}"/>:</div>
              <div class="user_form_input"><span class="needed"></span>
              <div id="feedType2">
                  <s:select 
                             cssClass="form_menu_inputselect"
                             id="feedType2"
                             name="feedbackType" 
                             list="{'No Data'}" 
                             headerKey="-1"
                             headerValue="Select Feedback Type">
                  </s:select>
              </div>     
              <div id="errorFBType2" class="errordiv"></div>
              </div>
              <%} else {%>
              <div class="user_form_text1"><s:property value="%{value}"/>:</div>
              <div class="user_form_input"><span class="needed"></span><s:textfield name="%{key}" id="%{key}1"  cssClass="form_menu_inputtext sub_width" placeholder="Enter Feedback Category"/><div id="errorFBCatg" class="errordiv"></div></div>
              <div class="user_form_button2 add_button1" style="position:absolute; top:3px; right:6px;"><sj:submit value="+" onclick="adddiv('150')" button="true"/></div>
              </div>
                  
              <s:iterator begin="150" end="168" var="catgIndx">
              <div class="form_menubox double_add_button" style="padding-top:0px;">
	          <div id="<s:property value="%{#catgIndx}"/>" style="display: none">
                  
              <div class="user_form_text">&nbsp;</div>
              <div class="user_form_input">&nbsp;</div>
              <div class="user_form_text1"><s:property value="%{value}"/>:</div>
              <div class="user_form_input" ><span class="needed"></span><s:textfield name="%{key}" id="%{key}" cssClass="form_menu_inputtext sub_width" placeholder="Enter Feedback Category"/>
     
              <s:if test="#catgIndx==168">
                  <div class="user_form_button2 del_button1"><sj:submit value="-" onclick="deletediv('%{#catgIndx}')" button="true"/></div>
              </s:if>
     
              <s:else>
                  <div class="user_form_button2 add_button1" style="position:absolute; margin-top:2px; right:6px;"><sj:submit value="+" onclick="adddiv('%{#catgIndx+1}')" button="true"/></div>
                  <div class="user_form_button3 del_button1" style="position:absolute; margin-top:2px; right:-28px;"><sj:submit value="-" onclick="deletediv('%{#catgIndx}')" button="true"/></div>
              </s:else>
                  
              </div>
              </div>
	          </div>
	          </s:iterator>
		   <%}temp1++; %>
		   </s:iterator>
	 
		   <div class="fields">
				<ul>
				<li class="submit" style="background:none;">
					<div class="type-button">
	                <sj:submit 
	                        targets="feeddraft2" 
	                        clearForm="true"
	                        value="  Add  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="beforeSecondAccordian"
	                        cssClass="submit"
	                        indicator="indicator3"
	                        onBeforeTopics="validateFeedCatg"
	                        />
	               </div>
				</ul>
				</div>
				<center><img id="indicator3" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				<sj:div id="foldeffect2" cssClass="sub_class1"  effect="fold"><div id="feeddraft2"></div></sj:div>
			</s:form>
			</div>
</div>
</sj:accordionItem>
</s:if>





<s:if test="levelOfFeedDraft>2">
<sj:accordionItem title="%{feedLevel3}" id="acor_item3">  
<div class="form_inner" id="form_reg">
<div class="page_form">
<s:form id="formthree" name="formthree" action="addFeedSubCategory"  theme="css_xhtml"  method="post" enctype="multipart/form-data" >
<s:hidden name="deptHierarchy" id="deptHierarchy2" value="%{deptHierarchy}"></s:hidden>
		  
               <div class="form_menubox">
	              <s:iterator value="subDept_DeptLevelName" status="status">
                  <s:if test="#status.odd">
                  <div class="inputmain">
                  <div class="user_form_text" style="text-indent:20px;"><s:property value="%{value}"/>:</div>
                  <span class="needed"></span>
	              <div class="user_form_input inputarea">
	                  <s:select 
	                              id="deptname3"
	                              name="deptname"
	                              list="deptList"
	                              headerKey="-1"
	                              headerValue="Select Department" 
	                              cssClass="form_menu_inputselect"
	                              onchange="getSubDept(this.value,'subDeptDiv3',%{deptHierarchy},'1','1','1','0');"
	                              >
	                  </s:select>
	                  <div id="errordept3" class="errordiv"></div>
	                  </div>
	                  </div>
	                   </s:if>
               <s:else>
               <div class="inputmain">
              <div class="user_form_text1" style="text-indent:10px;"><s:property value="%{value}"/>:</div>
	                  <div class="user_form_input inputarea">
	                  <span class="needed"></span>
	             	 <div id="subDeptDiv3">
	                  <s:select 
	                              id="subdept3"
	                              name="subdept"
	                              list="{'No Data'}"
	                              headerKey="-1"
	                              headerValue="Select Sub-Department" 
	                              cssClass="form_menu_inputselect sub_width1"
	                              >
	                  </s:select>
	                  </div>
	                  <div id="errorsubdept3" class="errordiv"></div>
	                  </div>
	                  </div>
                  </s:else>
              </s:iterator>
		     </div>
		     
		    <% int temp=0; %>
		    <s:iterator value="feedbackSubCategoryDDColumns">
		    <% if(temp%2==0) {%>
		     <div class="form_menubox double_add_button" style="position:relative;">
              <div class="user_form_text"><s:property value="%{value}"/>:</div>
              <div class="user_form_input"><span class="needed"></span>
             	 <div id="feedType3">
                  <s:select 
                             cssClass="form_menu_inputselect"
                             id="feedbackType"
                             name="feedbackType" 
                             list="{'No Data'}" 
                             headerKey="-1"
                             headerValue="Select Feedback Type">
                  </s:select>
                  </div>
                   <div id="errorFBType3" class="errordiv"></div>
                  </div>
                  <%} else { %>
                <div class="user_form_text1"><s:property value="%{value}"/>:</div>
                <div class="user_form_input"><span class="needed"></span>
             	 <div id="categoryDiv3">
                  <s:select 
                            cssClass="form_menu_inputselect sub_width1"
                            id="category3"
                            name="category" 
                            list="{'No Data'}" 
                            headerKey="-1"
                            headerValue="Select Category">
                  </s:select>
                  </div>
                  <div id="errorFBCatg3" class="errordiv"></div>
                  </div>
                  </div>
		    <%}temp++; %>
          </s:iterator> 
      
          <%int temp2=0;%>
		  <s:iterator value="feedbackSubCategoryTextColumns">
		  <%if(temp2%2==0){ %>
		  <div class="form_menubox">
               <div class="user_form_text"><s:property value="%{key}"/>:</div>
               <div class="user_form_input"><span class="needed"></span><s:textfield name="subCategoryName" id="subCategoryName"   maxlength="50" placeholder="Enter Data" cssClass="form_menu_inputtext" /><div id="errorFBSubCatg" class="errordiv"></div></div>
		  <%} else {%>
			   <div class="user_form_text1"><s:property value="%{key}"/>:</div>
               <div class="user_form_input"><span class="needed"></span><s:textfield name="%{key}" id="%{key}"  maxlength="50" placeholder="Enter Data" cssClass="form_menu_inputtext sub_width" /><div id="errorFeedBrief" class="errordiv"></div></div>
		  </div>
		  <%} temp2++; %>
		  </s:iterator>
				 
				 
		  <% int temp3=0;%>
		  <s:iterator value="feedbackSubCategoryTimeColumns">
		  <%if(temp3%2==0){ %>
		  <div class="form_menubox">
               <div class="user_form_text"><s:property value="%{key}"/>:</div>
               <div class="user_form_input"><span class="needed"></span><sj:datepicker name="%{key}" id="%{key}" placeholder="Enter Addressing Time" showOn="focus"  timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="5" cssClass="form_menu_inputtext" /><div id="errorAddressingTime" class="errordiv"></div></div>
		  <% } else {%>
			   <div class="user_form_text1"><s:property value="%{key}"/>:</div>
               <div class="user_form_input"><span class="needed"></span><sj:datepicker name="%{key}" id="%{key}" placeholder="Enter Escalation Time"  showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="5" cssClass="form_menu_inputtext sub_width" /><div id="errorEscalationTime" class="errordiv"></div></div>
			   <div class="user_form_button2" style="position:absolute; margin-top:7px; right:85px;"><sj:submit value="+" onclick="adddiv('200')" button="true"/></div>
		  </div>
		  <%} temp3++;%>
	      </s:iterator>
      
	      <s:iterator begin="200" end="228" var="subCategIndx">
	      <div id="<s:property value="%{#subCategIndx}"/>" style="display: none">
     	  <% int temp4=0; %>
		  <s:iterator value="feedbackSubCategoryTextColumns">
		  <%if(temp4%2==0){ %>
		  <div class="form_menubox">
               <div class="user_form_text"><s:property value="%{value}"/>:</div>
               <div class="user_form_input"><span class="needed"></span><s:textfield name="%{key}" id="%{key}%{#subCategIndx}"  maxlength="50" placeholder="Enter Data" cssClass="form_menu_inputtext"/></div>
		   <%} else {%>
			   <div class="user_form_text1"><s:property value="%{value}"/>:</div>
               <div class="user_form_input"><span class="needed"></span><s:textfield name="%{key}" id="%{key}%{#subCategIndx}"  maxlength="50" placeholder="Enter Data" cssClass="form_menu_inputtext sub_width" /></div>
		  </div>
		  <%} temp4++; %>
		  </s:iterator>
				 
		  <%int temp5=0;%>
		  <s:iterator value="feedbackSubCategoryTimeColumns">
		  <%if(temp5%2==0){%>
		  <div class="form_menubox">
          <div class="user_form_text"><s:property value="%{value}"/>:</div>
          <div class="user_form_input"><span class="needed"></span><sj:datepicker name="%{key}" id="%{key}%{#subCategIndx}"  placeholder="Enter Addressing Time" showOn="focus" onchange="hideErrorDiv('errorAddress');" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="5" cssClass="form_menu_inputtext "/>    </div>
		  <%} else {%>
		  <div class="user_form_text1"><s:property value="%{value}"/>:</div>
          <div class="user_form_input"><span class="needed"></span><sj:datepicker name="%{key}" id="%{key}%{#subCategIndx}"  placeholder="Enter Escalation Time"  onchange="hideErrorDiv('errorEsc');" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="5" cssClass="form_menu_inputtext sub_width" /></div>
		  <div style="position:relative;">
		  <s:if test="#subCategIndx==228">
             <div class="user_form_button2" style="position:absolute; margin-top:7px; right:5px;"><sj:submit value="-" onclick="deletediv('%{#subCategIndx}')" button="true"/></div>
          </s:if>
     
          <s:else>
             <div class="user_form_button2" style="position:absolute; margin-top:3px; right:5px;"><sj:submit value="+" onclick="adddiv('%{#subCategIndx+1}')" button="true"/></div>
             <div class="user_form_button3" style="position:absolute; margin-top:3px; right:-31px;"><sj:submit value="-" onclick="deletediv('%{#subCategIndx}')" button="true"/></div>
          </s:else>
		  </div>
		  </div>
		  <%} temp5++;%>
	 </s:iterator>
	 </div>
	 </s:iterator>
				<div class="fields">
				<ul>
				<li class="submit" style="background:none;">
					<div class="type-button">
	                <sj:submit 
	                        targets="feeddraft3" 
	                        clearForm="true"
	                        value="  Add  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="beforeThirdAccordian"
	                        cssClass="submit"
	                        indicator="indicator4"
	                        onBeforeTopics="validateSubCategory"
	                        />
	               </div>
				</ul>
				</div>
				<center><img id="indicator4" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				<sj:div id="foldeffect3" cssClass="sub_class1"  effect="fold"><div id="feeddraft3"></div></sj:div>
			</s:form>
</div>
</div>
</sj:accordionItem>
</s:if>

</sj:accordion>
</div>
</div>
</body>
</html>