<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="/js/organization.js"/>"></script>
<script src="<s:url value="/js/employeeOver2Cloud.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/task_js/task.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<script type="text/javascript">


$.subscribe('beforeFirst', function(event,data)
	       {
    
			$("#indicatorRuc").css("display","block");
			//$("#buttonid").css("display","none");
			$("#buttonadd").prop("disabled","true");
	       });

$.subscribe('completeData', function(event,data)
	       {
			$("#indicatorRuc").css("display","none");
			//$("#buttonid").css("display","block");
			$("#buttonadd").prop("disabled","false");
			 setTimeout(function(){ $("#darlevel22").fadeIn(); }, 10);
	         setTimeout(function(){ $("#darlevel22").fadeOut(); }, 4000);
	       });


</script>
</head>
<body>
<div class="page_title"><h1>DAR Submission</h1><span class="needed_block">= indicates mandatory fields</span></div>
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<sj:accordion id="accordion" autoHeight="false" >
<sj:accordionItem title="DAR Submission >>ADD"  id="twoId" >  
<div class="form_inner" id="form_reg">
<div class="page_form">

<s:form id="formtask" name="formtask" action="addDarRegis" theme="css_xhtml"  method="post" enctype="multipart/form-data" >
   <s:param name="dashDetailid" value="%{taskIdForDash}" />
   <s:hidden id="dashDetailid" name="dashDetailid" value="%{taskIdForDash}" ></s:hidden>
		     <div class="form_menubox">
		     <s:iterator value="darDropMap">
		      	 <s:if test="%{key == 'tasknameee'}">
	                  <div class="user_form_text"><s:property value="%{value}"/>:</div>
	                   <s:select 
                              id="%{key}"
                              name="%{key}"
                              list='listTaskName'
                              headerKey="-1"
                              headerValue="--Select Task Name--" 
                              cssClass="form_menu_inputselect"
                              onchange="getDashData(this.value)"
                              >
                  </s:select>
                  <div id="errortasknameee" class="errordiv"></div>
					 </s:if>
					 </s:iterator>
				  <s:iterator value="darColumnMap">
               <s:if test="%{key == 'guidancee'}">
               <div class="user_form_text1"><s:property value="%{value}"/>:</div>
                 <div class="user_form_input"><s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" cssClass="form_menu_inputtext"/></div>
                  <div id="errorstatus" class="errordiv"></div>
                </s:if>
                </s:iterator>
                  </div>
                      <div class="form_menubox">
				  <s:iterator value="darColumnMap">
               <s:if test="%{key == 'allotedbyy'}">
               <div class="user_form_text"><s:property value="%{value}"/>:</div>
                 <div class="user_form_input"><s:textfield name="%{key}" id="%{key}" maxlength="50" placeholder="Enter Data" cssClass="form_menu_inputtext"/></div>
                  <div id="errorstatus" class="errordiv"></div>
                </s:if>
                </s:iterator>
				 
				  <s:iterator value="darColumnMap">
               <s:if test="%{key == 'allotedtoo'}">
               <div class="user_form_text1"><s:property value="%{value}"/>:</div>
                 <div class="user_form_input"><s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" cssClass="form_menu_inputtext"/></div>
                  <div id="errorstatus" class="errordiv"></div>
                </s:if>
                </s:iterator>
                </div>
                    <div class="form_menubox">
				  <s:iterator value="darColumnMap">
               <s:if test="%{key == 'initiondate'}">
               <div class="user_form_text"><s:property value="%{value}"/>:</div>
                 <div class="user_form_input"><s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" cssClass="form_menu_inputtext"/></div>
                  <div id="errorstatus" class="errordiv"></div>
                </s:if>
                </s:iterator>
				  <s:iterator value="darColumnMap">
               <s:if test="%{key == 'comlpetiondate'}">
               <div class="user_form_text1"><s:property value="%{value}"/>:</div>
                 <div class="user_form_input"><s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" cssClass="form_menu_inputtext"/></div>
                  <div id="errorstatus" class="errordiv"></div>
                </s:if>
                </s:iterator>
                </div>
               
                 <div class="form_menubox">
                 <s:iterator value="darColumnMap">
                <s:if test="%{key == 'compercentage'}">
                <div class="user_form_text"><s:property value="%{value}"/>:</div>
                <div class="user_form_input"> <span class="needed"></span>
                               <sj:spinner 
    	                            name="%{key}"
                                	id="%{key}" 
    	                            min="5" 
    	                            max="100" 
    	                           step="5"  
    	                           value="5" />
    	                           </div>
               <div id="errorpercentage" class="errordiv"></div>
              
                </s:if>
                </s:iterator>
                   <s:iterator value="darDropMap">
					  <s:if test="%{key == 'statuss'}">
					  <div class="user_form_text1"><s:property value="%{value}"/>:</div>
	                 <s:select 
                              id="%{key}"
                              name="%{key}"
                              list="{'Pending','Done','Partially Done'}"
                              headerKey="-1"
                              headerValue="--Select Task Status-" 
                              cssClass="form_menu_inputselect"
                              >
                   </s:select>
					  </s:if>
				    </s:iterator>
                </div>
                   <div class="form_menubox">
				  <s:iterator value="darColumnMap">
               <s:if test="%{key == 'actiontaken'}">
               <div class="user_form_text"><s:property value="%{value}"/>:</div>
                 <div class="user_form_input"><s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" cssClass="form_menu_inputtext"/></div>
                  <div id="errorstatus" class="errordiv"></div>
                </s:if>
                </s:iterator>
				  <s:iterator value="darColumnMap">
               <s:if test="%{key == 'tommorow'}">
               <div class="user_form_text1"><s:property value="%{value}"/>:</div>
                 <div class="user_form_input"><s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" cssClass="form_menu_inputtext"/></div>
                  <div id="errorstatus" class="errordiv"></div>
                </s:if>
                </s:iterator>
                </div>
                
                
                <div class="form_menubox">
                <s:iterator value="darColumnMap">
               <s:if test="%{key == 'today'}">
               <div class="user_form_text"><s:property value="%{value}"/>:</div>
                 <div class="user_form_input"><s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" cssClass="form_menu_inputtext"/></div>
                  <div id="errorstatus" class="errordiv"></div>
                </s:if>
                </s:iterator>
               <s:iterator value="darColumnMap">
               <s:if test="%{key == 'otherworkk'}">
               <div class="user_form_text1"><s:property value="%{value}"/>:</div>
                 <div class="user_form_input"><s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" cssClass="form_menu_inputtext"/></div>
                  <div id="errorstatus" class="errordiv"></div>
                </s:if>
                </s:iterator>
                 </div>
                  <div class="form_menubox">
                 <s:iterator value="darFileMap">
				 <div class="user_form_text"><s:property value="%{value}"/>:</div>
                  <div class="user_form_input" style="color:black;"><s:file name="%{key}" id="%{key}" cssClass="form_menu_inputtext"  cssStyle="margin:0px 0px 10px 0px"  /></div>
		        </s:iterator>
		        </div>
		        
               <b><font color="red" face="verdana">Note: Without uploading the relevant document your task will be treated as incomplete</font> </b>   
                  
		   <div class="fields">
		    <ul>
			 <li class="submit" style="background:none;">
			 <div class="type-button">
	         <sj:submit 
	                        targets="target1" 
	                        id="buttonadd"
	                        clearForm="true"
	                        value="  Add  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="completeData"
	                        cssClass="submit"
	                        onBeforeTopics="beforeFirst"
	                        indicator="indicatorRuc"
	                        />
	        </div>
	        </li>
		  </ul>
		  <sj:div id="darlevel22"  effect="fold">
                    <div id="darlevel22Div"></div>
               </sj:div>
	      </div>
	      <center><img id="indicatorRuc" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
		  <sj:div id="foldeffect" cssClass="sub_class1"  effect="fold"><div id="target1"></div></sj:div>
	 
	 </s:form>
	  </div>
	  </div>
</sj:accordionItem>
</sj:accordion>
</div>
</div>
</body>
</html>