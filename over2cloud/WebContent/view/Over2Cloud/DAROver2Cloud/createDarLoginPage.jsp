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
$.subscribe('completeData', function(event,data)
{
 //$("#indicatorRuc").css("display","none");
//$("#buttonid").css("display","block");
  //$("#buttonadd").prop("disabled","false");
       setTimeout(function(){ $("#darlevel22").fadeIn(); }, 10);
       setTimeout(function(){ $("#darlevel22").fadeOut(); }, 4000);
       $('select').find('option:first').attr('selected', 'selected');
 });

function resetForm(formId) 
{
	 $('#'+formId).trigger("reset");
}
</script>
</head>
<body>
<div class="list-icon">
	 <div class="head">Project</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Activity Submission</div> 
</div>
<div style=" float:left; padding:5px 0%; width:100%;">
<div class="border">
<div class="fields" align="right">
		  <ul>
			 <li class="submit" style="background:none;">
			 <div class="type-button">
	         <sj:submit    
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        button="true"
	                        value=" View Pending Task " 
	                        cssClass="submit"
	                        onclick="getPendingTask(this.value)"
	                        />
	        </div>
	        </li>
		  </ul>
</div>
<s:form id="formtask" name="formtask" action="addDarRegis" theme="css_xhtml"  method="post" enctype="multipart/form-data" >
		     <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             	<div id="abc" style="float:left; margin-left: 7px">
             	</div> 
             </div>
             
		<s:iterator value="darDropMap">
		     <s:if test="%{mandatory}">
              <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
		      	 <s:if test="%{key == 'taskname'}">
	                  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:select 
                              id="%{key}"
                              name="%{key}"
                              list='listTaskName'
                              headerKey="-1"
                              headerValue="Select Project Name" 
                              cssClass="select"
                              cssStyle="width:82%"
                              onchange="getProjectDetails(this.value)"
                              >
                              </s:select>
			  </div>
			  </div>
			  </s:if>
			  <s:if test="%{key == 'pstatus'}">
			  <div class="newColumn">
						<div class="leftColumn1"><s:property value="%{value}"/>:</div>
						<div class="rightColumn1">
                           <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                           <s:select 
                            id="%{key}"
                            name="%{key}"
                            list="{'Pending','Partially Pending','Done','Snooze'}"
                            headerKey="-1"
                            headerValue="Select Project Status" 
                            cssClass="select"
                            cssStyle="width:82%"
                            onchange="getSnoozeDetails(this.value,'snoozeDiv')"
                            >
                            </s:select>
			  </div>
			  </div>
			  </s:if>
  </s:iterator>
   <div id="snoozeDiv" style="display: none;">
		   <div class="newColumn">
								<div class="leftColumn1">Snooze Date:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
              				    <sj:datepicker name="snoozeDate" id="snoozeDate" timepicker="true"  readonly="true"  changeMonth="true" changeYear="true" yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssStyle="margin:0px 0px 10px 0px"   cssClass="textField" placeholder="Select Date" /></div>
                </div>
          
                <div class="newColumn">
								<div class="leftColumn1">Snooze Reason:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:textfield name="snoozeReason" id="snoozeReason"  maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
               </div>
   </div>
   <s:iterator value="darColumnMap" begin="0" end="6">
             <s:if test="%{mandatory}">
              <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
                <s:if test="#status.odd">
	                   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:textfield name="%{key}" id="%{key}" readonly="%{editable}" maxlength="500" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>					     
					  </div>
					  </s:if>
					   <s:else>
					    <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:textfield name="%{key}" id="%{key}" readonly="%{editable}"  maxlength="500" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>					     
					  </div>
		     </s:else>
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
    
     <s:iterator value="darColumnMap" begin="7" end="8">
            <s:if test="%{mandatory}">
              <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
                <s:if test="#status.odd">
	                   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:textfield name="%{key}" id="%{key}" maxlength="500" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>					     
					  </div>
					  </s:if>
					   <s:else>
					    <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:textfield name="%{key}" id="%{key}" maxlength="500" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>					     
					  </div>
					   
					   </s:else>
   </s:iterator>
      
        <div class="user_form_button2"><sj:submit value="+" onclick="adddiv('100')" button="true" cssStyle="margin-left: 928px;"/></div>
			     
		<s:iterator begin="100" end="105" var="moduleIndx" status="status">
		<div id="<s:property value="%{#moduleIndx}"/>" style="display: none">
			    
		 <s:iterator value="darDropMap" status="counter">
			 <s:if test="%{mandatory}">
              <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
             <div class="clear"></div> 
		      <s:if test="%{key == 'taskname'}">
	          <div class="newColumn">
							 <div class="leftColumn1"><s:property value="%{value}"/>:</div>
							 <div class="rightColumn1">
	                         <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                         <s:select 
                              id="%{key}"
                              name="%{key}"
                              list='listTaskName'
                              headerKey="-1"
                              headerValue="Select Project Name" 
                              cssClass="select"
                              cssStyle="width:82%"
                              onchange="getClientData(this.value,'%{#status.count}')"
                              >
                              </s:select>
			  </div>
			  </div>
			  </s:if>
			  <s:if test="%{key == 'pstatus'}">
			  <div class="newColumn">
						<div class="leftColumn1"><s:property value="%{value}"/>:</div>
						<div class="rightColumn1">
                           <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                           <s:select 
                            id="%{key}"
                            name="%{key}"
                            list="{'Pending','Partially Pending','Done','Snooze'}"
                            headerKey="-1"
                            headerValue="Select Project Status" 
                            cssClass="select"
                            cssStyle="width:82%"
                            onchange="getSnoozeDetails(this.value,'snoozeDiv')"
                            >
                            </s:select>
			  </div>
			  </div>
			  </s:if>
		 </s:iterator>
		 <div id="snoozeDiv" style="display: none">
		   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <sj:datepicker name="snoozeDate" id="snoozeDate" readonly="true"  changeMonth="true" changeYear="true" yearRange="1890:2020" showOn="focus" displayFormat="dd-mm-yy" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" placeholder="Select Date" /></div>
                </div>
          
                <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:textarea name="snoozeReason" id="snoozeReason" readonly="true" maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
               </div>
		 </div>
		 <div class="clear"></div>  
	<s:iterator value="darColumnMap">
            <s:if test="%{mandatory}">
              <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
             
                <s:if test="%{key == 'intiation'}">
                <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:textfield name="%{key}" id="%{key}%{#status.count}" readonly="true" maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
                </div>
                </s:if>
                <s:elseif test="%{key == 'completion'}">
                <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:textfield name="%{key}" id="%{key}%{#status.count}" readonly="true" maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
               </div>
                </s:elseif>
                <s:elseif test="%{key == 'technical_Date'}">
                <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:textfield name="%{key}" id="%{key}%{#status.count}" readonly="true" maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
                </div>
                </s:elseif>
                <s:elseif test="%{key == 'functional_Date'}">
                <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:textfield name="%{key}" id="%{key}%{#status.count}" readonly="true" maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
               </div>
                </s:elseif>
                <s:if test="%{key == 'tactionStatus'}">
                <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:textfield name="%{key}" id="%{key}" maxlength="500" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
                </div>
                </s:if>
               <s:if test="%{key == 'tommorow'}">
               <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:textfield name="%{key}" id="%{key}" maxlength="500" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
               </div>
               </s:if>
               <s:if test="%{key == 'compercentage'}">
               <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:textfield name="%{key}" id="%{key}" maxlength="500" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
               </div>
               </s:if>
               <s:if test="%{key == 'manhour'}">
               <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:textfield name="%{key}" id="%{key}" maxlength="500" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
               </div>
               </s:if>
               <s:if test="%{key == 'cost'}">
               <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:textfield name="%{key}" id="%{key}" maxlength="500" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
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
	                            <s:file name="%{key}%{#status.count}" id="%{key}" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"  /></div>
                </div>
    </s:iterator>
		             <s:if test="#moduleIndx==105">
		                  <div class="user_form_button2"><sj:submit value="-" onclick="deletediv('%{#moduleIndx}')" button="true"/></div>
		            </s:if>
		            <s:else>
		               <div class="user_form_button2"><sj:submit value="+" onclick="adddiv('%{#moduleIndx+1}')" button="true"/></div>
		               <div class="user_form_button3"><sj:submit value="-" onclick="deletediv('%{#moduleIndx}')" button="true"/></div>
		            </s:else>
		           
		       </div>
			 </s:iterator>
			  <div class="clear"></div> 
	      <s:iterator value="darColumnMap" begin="9" >
            <s:if test="%{mandatory}">
              <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
                <s:if test="#status.odd">
	                   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:textfield name="%{key}" id="%{key}" maxlength="500" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>					     
					  </div>
					  </s:if>
					   <s:else>
					    <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:textfield name="%{key}" id="%{key}" maxlength="500" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>					     
					  </div>
					   
					   </s:else>
   </s:iterator>
    
			 
 		   <div class="clear"></div> 
		   <div class="fields" align="center">
		    <ul>
			 <li class="submit" style="background:none;">
			 <div class="type-button">
			 <center>
	         <sj:submit 
	                        targets="darlevel22Div" 
	                        id="buttonadd"
	                        clearForm="true"
	                        value="Submit" 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="completeData"
	                        cssClass="submit"
	                        onBeforeTopics="darvalidate"
	                        indicator="indicatorRuc"
	                         cssStyle="margin-left: -21px;"
	                        />
	                   
			        
			        <sj:a cssStyle="margin-left: 183px;margin-top: -45px;" button="true" href="#" onclick="resetForm('formtask');">Reset</sj:a>
				            <sj:a cssStyle="margin-left: -1px;margin-top: -45px;" button="true" href="#" onclick="viewDar();">Back</sj:a>
	           </center>                 
	        </div>
	        </li>
		  </ul>
		  <sj:div id="darlevel22"  effect="fold">
                    <div id="darlevel22Div"></div>
          </sj:div>
	      </div>
	      <center><img id="indicatorRuc" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
	      <s:hidden id="indexVal" name="indexVal" value="1"></s:hidden>
		
</s:form>
</div>
</div>
</body>
</html>
