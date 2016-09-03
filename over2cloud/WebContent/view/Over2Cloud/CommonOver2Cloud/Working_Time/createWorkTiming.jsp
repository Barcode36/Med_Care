<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/common/workingTime.js"/>"></script>
<script type="text/javascript">
function showhidediv(fieldvalue,daydiv,timediv)
{
	if(fieldvalue=='AS')
	{
		document.getElementById(daydiv).style.display="none";
		document.getElementById(timediv).style.display="block";
	}
	else if(fieldvalue=='SA')
	{
		document.getElementById(daydiv).style.display="block";
		document.getElementById(timediv).style.display="none";
	}
	else if(fieldvalue=='SS')
	{
		document.getElementById(daydiv).style.display="block";
		document.getElementById(timediv).style.display="block";
	}
}
</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<!-- Header Strip Div Start -->
<div class="list-icon">
	 <div class="head">Working Hours</div><img alt="" src="images/forward.png" style="margin-top:10px; float: left;"><div class="head">Add</div> 
</div>
<div class="clear"></div>
<div style="height: 250px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
<s:hidden  id="dataFor" value="%{dataFor}"></s:hidden>
	<s:form id="formone" name="formone" action="addWorkingTimings" theme="css_xhtml"  method="post" enctype="multipart/form-data" >
	<s:hidden name="dataFor" value="%{dataFor}"/>
    <center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone" style="float:center; margin-left: 7px"></div></div></center>          
          
       <s:iterator value="createColumnList" status="status" begin="0" end="1">
          <s:if test="%{mandatory}"><span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span></s:if>
       			
				<s:if test="key=='dept'">
				   <div class="clear"></div>
					     <div class="newColumn">
			              <div class="leftColumn"><s:property value="%{value}"/>:</div>
							  <div class="rightColumn">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                   <s:select 
				                              id="%{key}"
				                              name="%{key}"
				                              list="deptList"
				                              headerKey="A"
				                              headerValue="All Department" 
				                              cssClass="select"
				                              cssStyle="width:82%"
				                              >
				                   </s:select>
				              </div>
			              </div>
               </s:if>
               <s:elseif test="key=='working_type'">
	       			 <div class="newColumn">
		       			 <div class="leftColumn"><s:property value="%{value}"/>:</div>
		       			 <div class="rightColumn">
		       			   <s:if test="%{mandatory}"><span class="needed"></span></s:if>
			                  <s:select 
			                              id="%{key}"
			                              name="%{key}"
			                              list="#{'AS' : 'All Days & Specific Time','SA' : 'Specific Days & All Time','SS' : 'Specific Days & Specific Time'}"
		                                  headerKey="AA"
		                                  headerValue="All Days & All Time" 
			                              cssClass="select"
			                              cssStyle="width:82%"
			                              onchange="showhidediv(this.value,'daydiv','timediv');"
			                              >
			                  </s:select>
						</div>
					</div>
				</s:elseif>
				 </s:iterator>
				 <div class="clear"></div>
				 <s:div id="daydiv" cssStyle="display :none;">
				 <s:iterator value="createColumnList" begin="3" end="4">
				 <s:if test="key=='fromDay'">
				 <div class="newColumn">
							 <div class="leftColumn"><s:property value="%{value}"/>:</div>
							 <div class="rightColumn">
							 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
							 <s:select 
			                              id="%{key}"
			                              name="%{key}"
			                              list="#{'2' : 'Tuesday','3' : 'Wednesday','4' : 'Thursday','5' : 'Friday','6' : 'Saturday','7' : 'Sunday'}"
		                                  headerKey="1"
		                                  headerValue="Monday" 
			                              cssClass="select"
			                              cssStyle="width:82%"
			                              onchange="showhidediv(this.value,'daydiv','timediv');"
			                              >
			                  </s:select>							 	
						     </div>
						</div>
				</s:if>
				<s:elseif test="key=='toDay'">
				 <div class="newColumn">
							 <div class="leftColumn"><s:property value="%{value}"/>:</div>
							 <div class="rightColumn">
							 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
							 	 <s:select 
			                              id="%{key}"
			                              name="%{key}"
			                              list="#{'2' : 'Tuesday','3' : 'Wednesday','4' : 'Thursday','5' : 'Friday','6' : 'Saturday'}"
		                                  headerKey="7"
		                                  headerValue="Sunday" 
			                              cssClass="select"
			                              cssStyle="width:82%"
			                              onchange="showhidediv(this.value,'daydiv','timediv');"
			                              >
			                  </s:select>	
						     </div>
						</div>
				</s:elseif>
				</s:iterator>
				</s:div>
				<div class="clear"></div>
				<s:div id="timediv" cssStyle="display :none;">
				<s:iterator value="createColumnList" begin="5" end="6">
				<s:if test="key=='fromTime'">
				 <div class="newColumn">
							 <div class="leftColumn"><s:property value="%{value}"/>:</div>
							 <div class="rightColumn">
							 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
							 	<sj:datepicker name="%{key}" id="%{key}"  placeholder="Enter Data" readonly="true" showOn="focus"  timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="5" cssClass="textField" />
						     </div>
						</div>
				</s:if>
				
				<s:elseif test="key=='toTime'">
				 <div class="newColumn">
							 <div class="leftColumn"><s:property value="%{value}"/>:</div>
							 <div class="rightColumn">
							 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
							 	<sj:datepicker name="%{key}" id="%{key}"  placeholder="Enter Data" readonly="true" showOn="focus"  timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="5" cssClass="textField" />
						     </div>
						</div>
				</s:elseif>
            </s:iterator>
            </s:div>
            <div class="clear"></div>
             <s:iterator value="createColumnList" begin="2" end="2">
	       			 <div class="newColumn">
		       			 <div class="leftColumn"><s:property value="%{value}"/>:</div>
		       			 <div class="rightColumn">
		       			   <s:if test="%{mandatory}"><span class="needed"></span></s:if>
			                  <s:select 
			                              id="%{key}"
			                              name="%{key}"
			                              list="#{'Y' : 'Yes'}"
		                                  headerKey="N"
		                                  headerValue="NO" 
			                              cssClass="select"
			                              cssStyle="width:82%"
			                              >
			                  </s:select>
						  </div>
					 </div>
            </s:iterator>
            <div class="clear"></div>
            <div class="clear"></div>
		   <div style="width: 100%; text-align: center; padding-bottom: 10px;">
	                <sj:submit 
	                        targets="target1" 
	                        clearForm="true"
	                        value=" Save " 
	                        effect="highlight" 
			                effectOptions="{ color : '#222222'}" 
			                effectDuration="5000" 
	                        button="true"
	                        cssClass="submit"
	                        indicator="indicator1"  
	                        onCompleteTopics="beforeFirstAccordian"
	                        cssStyle="margin-left: -40px;"
	                        />
	            <sj:a cssStyle="margin-left: 175px;margin-top: -45px;" button="true" href="#" onclick="resetForm('formone');">Reset</sj:a>
	            <sj:a cssStyle="margin-left: 2px;margin-top: -45px;" button="true" href="#" onclick="backForm('dataFor');">Back</sj:a>
	               </div>
	               
		        <center><img id="indicator1" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
		  <sj:div id="foldeffect1" effect="fold"><div id="target1"></div></sj:div>  
			</s:form>
			</div>
     
</div>
</body>
</html>
