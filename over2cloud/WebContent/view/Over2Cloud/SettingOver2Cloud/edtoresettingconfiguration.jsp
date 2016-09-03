<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="/js/asset/AssetCommonValidation.js"/>"></script>
<SCRIPT type="text/javascript">
$.subscribe('result', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#bscconatct").fadeIn(); }, 10);
	         setTimeout(function(){ $("#bscconatct").fadeOut(); }, 4000);
	       });
</script>
</head>
<div class="list-icon">
	<div class="head"><s:property value="%{Header_name}"/> >> Add</div>
</div>
<div class="clear"></div>
<div style=" float:left; width:100%; height: 350px;">
<div class="border">
<s:if test= "Header_name!=null">

		 <s:form id="formone" name="formone" namespace="/view/CloudApps" action="addDetails" theme="simple"  method="post"enctype="multipart/form-data" >
	             <s:hidden name="mappedtablele" key="mappedtablele" value="%{mappedtablele}"/>   
			     <s:hidden name="createtable" key="createtable" value="%{mapedtable}"/>   
                   <center>
			      <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
                  <div id="errZone" style="float:center; margin-left: 7px"></div>        
                  </div>
                  </center>
                  
                   <s:iterator value="listsettingPerdropdownconfiguration" status="counter">
                   
                    <s:if test="%{mandatory}">
                    <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                   </s:if>
				   <s:if test="#counter.count%2 != 0">
				   <s:if test="%{mandatory}">
                   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{field_name}"/>:</div>
								<div class="rightColumn1"><span class="needed"></span>
                                <s:textfield name="%{field_value}" id="%{field_value}" cssStyle="margin:0px 0px 10px 0px" maxlength="50" placeholder="Enter Data" cssClass="textField"/></div>
				   </div>
				   </s:if>
				   <s:else>
				   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{field_name}"/>:</div>
								<div class="rightColumn1"><s:textfield name="%{field_value}" id="%{field_value}" cssStyle="margin:0px 0px 10px 0px" maxlength="50" placeholder="Enter Data" cssClass="textField"/></div>
				   </div>
				   </s:else>
				   
				   </s:if>
				   <s:else>
				    <s:if test="%{mandatory}">
				 <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{field_name}"/>:</div>
								<div class="rightColumn1"><span class="needed"></span>
                                <s:textfield name="%{field_value}" id="%{field_value}" cssStyle="margin:0px 0px 10px 0px" maxlength="50" placeholder="Enter Data" cssClass="textField"/></div>
				 </div>
				 </s:if>
				  <s:else>
				 <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{field_name}"/>:</div>
								<div class="rightColumn1">
                                <s:textfield name="%{field_value}" id="%{field_value}" cssStyle="margin:0px 0px 10px 0px" maxlength="50" placeholder="Enter Data" cssClass="textField"/></div>
				 </div>
				  </s:else>
				 </s:else>
				 </s:iterator>

				  <s:iterator value="listsettingtextconfiguration" status="counter">
				  
				     <s:if test="%{mandatory}">
                    <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                     </s:if>
				  <s:if test="#counter.count%2 != 0">
                   <s:if test="%{mandatory}">
                 <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{field_name}"/>:</div>
								<div class="rightColumn1"><span class="needed"></span>
								<s:textfield name="%{field_value}" id="%{field_value}" cssStyle="margin:0px 0px 10px 0px" maxlength="50" placeholder="Enter Data" cssClass="textField"/></div>
			    </div>
				   </s:if>
				   <s:else>
				 <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{field_name}"/>:</div>
								<div class="rightColumn1">
                                 <s:textfield name="%{field_value}" id="%{field_value}" cssStyle="margin:0px 0px 10px 0px" maxlength="50" placeholder="Enter Data" cssClass="textField" /></div>
				 </div>
				  </s:else>
				   </s:if>
				   <s:else>
				  <s:if test="%{mandatory}">
				  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{field_name}"/>:</div>
								<div class="rightColumn1"><span class="needed"></span><s:textfield name="%{field_value}" id="%{field_value}" cssStyle="margin:0px 0px 10px 0px" maxlength="50" placeholder="Enter Data" cssClass="textField"/></div>
				 </div>
				 </s:if>
				  <s:else>
				 <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{field_name}"/>:</div>
								<div class="rightColumn1"><s:textfield name="%{field_value}" id="%{field_value}" cssStyle="margin:0px 0px 10px 0px" maxlength="50" placeholder="Enter Data" cssClass="form_menu_inputtext"/></div>
				 </div>
				 </s:else>
				 </s:else>
				 </s:iterator>
			
				  <s:iterator value="listsettingPerCalendrconfiguration" status="counter">
				     <s:if test="%{mandatory}">
                    <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                    </s:if>
				    <s:if test="#counter.count%2 != 0">
				    <s:if test="%{mandatory}">
                    <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{field_name}"/>:</div>
								<div class="rightColumn1"><span class="needed"></span><sj:datepicker name="%{field_value}" id="%{field_value}" changeMonth="true" changeYear="true" yearRange="1890:2020" cssStyle="margin:0px 0px 10px 0px" showOn="focus" displayFormat="dd-mm-yy" cssClass="textField" placeholder="Select Date"/></div>
				    </div>
				    </s:if>
				    <s:else>
				    <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{field_name}"/>:</div>
								<div class="rightColumn1"><sj:datepicker name="%{field_value}" id="%{field_value}" changeMonth="true" changeYear="true" yearRange="1890:2020" cssStyle="margin:0px 0px 10px 0px" showOn="focus" displayFormat="dd-mm-yy" cssClass="textField" placeholder="Select Date"/></div>
				    </div>
				    </s:else>
				    </s:if>
				    <s:else>
				      <s:if test="%{mandatory}">
				          <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{field_name}"/>:</div>
								<div class="rightColumn1"><span class="needed"></span><sj:datepicker name="%{field_value}" id="%{field_value}" changeMonth="true" changeYear="true" yearRange="1890:2020" cssStyle="margin:0px 0px 10px 0px" showOn="focus" displayFormat="dd-mm-yy" cssClass="textField" placeholder="Select Date"/></div>
				          </div>
				   </s:if>
				   <s:else>
				   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{field_name}"/>:</div>
								<div class="rightColumn1"><sj:datepicker name="%{field_value}" id="%{field_value}" changeMonth="true" changeYear="true" yearRange="1890:2020" cssStyle="margin:0px 0px 10px 0px" showOn="focus" displayFormat="dd-mm-yy" cssClass="textField" placeholder="Select Date"/></div>
				   </div>
			     </s:else>
				 </s:else>
				 </s:iterator>
			
				 <s:iterator value="listsettingCalendrTimeconfiguration" status="counter">
				      <s:if test="%{mandatory}">
                     <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                     </s:if>
				 <s:if test="#counter.count%2 != 0">
				 <s:if test="%{mandatory}">
                 <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{field_name}"/>:</div>
								<div class="rightColumn1"><span class="needed"></span><sj:datepicker name="%{field_value}" id="%{field_value}"  timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssStyle="margin:0px 0px 10px 0px" cssClass="textField"/></div>
				 </div>
				   </s:if>
				   <s:else>
				   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{field_name}"/>:</div>
								<div class="rightColumn1"><sj:datepicker name="%{field_value}" id="%{field_value}"  timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssStyle="margin:0px 0px 10px 0px" cssClass="textField"/></div>
				   </div>
				   </s:else>
				   </s:if>
				   <s:else>
				  <s:if test="%{mandatory}">
				  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{field_name}"/>:</div>
								<div class="rightColumn1"><span class="needed"></span><sj:datepicker name="%{field_value}" id="%{field_value}"  timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssStyle="margin:0px 0px 10px 0px" cssClass="textField"/></div>
				  </div>
				  </s:if>
				  <s:else>
				 <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{field_name}"/>:</div>
								<div class="rightColumn1"><sj:datepicker name="%{field_value}" id="%{field_value}"  timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" cssStyle="margin:0px 0px 10px 0px" cssClass="textField"/></div>
				 </div>
				  </s:else>
				  </s:else>
				 </s:iterator>
		
				  <s:iterator value="listsettingtextAreaconfiguration" status="counter">
				    <s:if test="%{mandatory}">
                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                   </s:if>
				  <s:if test="#counter.count%2 != 0">
                  <s:if test="%{mandatory}">
                 <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{field_name}"/>:</div>
								<div class="rightColumn1"><span class="needed"></span><s:textarea name="%{field_value}" id="%{field_value}" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
				</div>
				  </s:if>
				  <s:else>
				 <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{field_name}"/>:</div>
								<div class="rightColumn1"><s:textarea name="%{field_value}" id="%{field_value}" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
				 </div>
				  </s:else>
				  
				  </s:if>
				  <s:else>
				   <s:if test="%{mandatory}">
				  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{field_name}"/>:</div>
								<div class="rightColumn1"><span class="needed"></span><s:textarea name="%{field_value}" id="%{field_value}" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
				  </div>
				  </s:if>
				  <s:else>
				   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{field_name}"/>:</div>
								<div class="rightColumn1"><s:textarea name="%{field_value}" id="%{field_value}" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
				  </div>
				  </s:else>
				  </s:else>
				 </s:iterator>
             	
				<center>
				<img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				<div class="fields">
				<div class="clear"></div>
				<div class="clear"></div>
				<ul>
				<li class="submit" style="background: none;">
					<div class="type-button">
					<center>
	                <sj:submit 
	                        targets="conatbsic" 
	                        clearForm="true"
	                        value="  Register  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="result"
	                        cssClass="submit"
	                        indicator="indicator2"
	                        onBeforeTopics="validate"
	                        />
	                        </center>
	               </div>
				</ul>
				<sj:div id="bscconatct"  effect="fold">
                    <div id="conatbsic"></div>
               </sj:div>
               </div>
			</s:form>
</s:if>
</div>
</div>
</html>
