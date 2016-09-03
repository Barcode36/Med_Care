<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/task_js/task.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<script type="text/javascript">
$.subscribe('completeData', function(event,data)
	       {
			 setTimeout(function(){ $("#darlevel22").fadeIn(); }, 10);
	         setTimeout(function(){ $("#darlevel22").fadeOut(); }, 4000);
	         $('select').find('option:first').attr('selected', 'selected');
	       });

</script>
</head>
<body>

<div style=" float:left; padding:5px 0%; width:100%;">
<div class="border">
<s:form id="formtask111" name="formtask111" action="addDarRegis" theme="css_xhtml"  method="post" enctype="multipart/form-data" >
		
		 <s:hidden name="taskid" id="taskid" value="%{taskid}"></s:hidden> 
		 <s:hidden name="dashboardId" id="dashboardId" value="1"></s:hidden>   
		<div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="abc" style="float:left; margin-left: 7px">
             </div> 
        </div>
             
	 <s:iterator value="darDropMap">
		     <s:if test="%{mandatory}">
              <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
		      	 <s:if test="%{key == 'tasknameee'}">
	                  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                   		    <s:textfield name="%{key}" id="%{key}" readonly="true" value="%{taskname}" maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/>
	                    </div>
			  </div>
			  </s:if>
	 </s:iterator>
	
	<s:iterator value="darColumnMap">
	         <s:if test="%{mandatory}">
              <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
                 <s:if test="%{key == 'guidancee'}">
                 <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:textfield name="%{key}" id="%{key}" value="%{guidance}" readonly="true" maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
                 </div>
                 </s:if>
   </s:iterator>
                
   <s:iterator value="darColumnMap">
             <s:if test="%{mandatory}">
              <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
               <s:if test="%{key == 'allotedbyy'}">
               <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:textfield name="%{key}" id="%{key}" value="%{allotedBy}" readonly="true" maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
                </div>
                </s:if>
  </s:iterator>
                
  <s:iterator value="darColumnMap">
            <s:if test="%{mandatory}">
              <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
                <s:if test="%{key == 'initiondate'}">
                <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:textfield name="%{key}" id="%{key}" value="%{initiation}" readonly="true" maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
                </div>
                </s:if>
                <s:elseif test="%{key == 'comlpetiondate'}">
                <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:textfield name="%{key}" id="%{key}" value="%{completion}" readonly="true" maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
               </div>
                </s:elseif>
  </s:iterator>
   
   <div class="clear"></div>           
   <s:iterator value="darDropMap">
        <s:if test="%{mandatory}">
        <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
        </s:if> 
					  <s:if test="%{key == 'statuss'}">
					  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:select 
                              id="%{key}"
                              name="%{key}"
                              list="{'Pending','Done','Partially Done'}"
                              headerKey="-1"
                              headerValue="--Select Task Status-" 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                              </s:select>
					  </div>
					  </div>
					  </s:if>
  </s:iterator>
  
  <div class="clear"></div>
  <s:iterator value="darColumnMap">
             <s:if test="%{mandatory}">
              <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
		          <s:if test="%{key == 'actiontaken'}">
                <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
                </div>
                </s:if>
   </s:iterator>
                  
   <s:iterator value="darColumnMap">
                   <s:if test="%{mandatory}">
                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if> 
               <s:if test="%{key == 'today'}">
               <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
               </div>
               </s:if>
              
                 
               <s:if test="%{key == 'tommorow'}">
               <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
               </div>
               </s:if>
              
   </s:iterator>
   
   <s:iterator value="darColumnMap">
                 <s:if test="%{mandatory}">
                  <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if> 
                <s:if test="%{key == 'compercentage'}">
                <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <sj:spinner 
    	                            name="%{key}"
                                	id="%{key}" 
    	                            min="5" 
    	                            max="100" 
    	                            step="5"  
    	                            value="5"
    	                            cssStyle="width:84%"  
    	                            cssClass="textField"
    	                            
    	                            
    	                             />
    	                           </div>
              
                </div>
                </s:if>
                <s:if test="%{key == 'otherworkk'}">
                <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:textfield name="%{key}" id="%{key}"maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
               </div>
               </s:if>
   </s:iterator>
   
   <s:iterator value="darFileMap">
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
		   <div class="fields" align="center">
		    <ul>
			 <li class="submit" style="background:none;">
			 <div class="type-button">
	         <sj:submit 
	                        targets="darlevel22Div" 
	                        id="buttonadd"
	                        clearForm="true"
	                        value="  Add  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="completeData"
	                        cssClass="submit"
	                        onBeforeTopics="darvalidate"
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
		
</s:form>
</div>
</div>
</body>
</html>
