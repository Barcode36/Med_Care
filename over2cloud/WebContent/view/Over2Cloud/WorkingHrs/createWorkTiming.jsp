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
<script  type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" defer="defer">

$(document).ready(function()
		 {
		 	 
		 	$("#dept").multiselect({
		 	   show: ["", 200],
		 	   hide: ["explode", 1000],
		 	});
		 });
</script>
<script type="text/javascript">
$.subscribe('completeData', function(event,data)
		{
			  setTimeout(function(){ $("#foldeffect").fadeIn(); }, 10);
		      setTimeout(function(){ $("#foldeffect").fadeOut(); }, 4000);
		      $('select').find('option:first').attr('selected', 'selected');
		});
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
function getDeptByModule(dataFor,targetid)
{
	$.ajax(
			{
				type :"post",
				url :"/over2cloud/view/Over2Cloud/WorkingHrs/getConcernDept.action?dataFor="+dataFor,
				success : function(empData)
				{
					$('#'+targetid+' option').remove();
					$('#'+targetid).append($("<option></option>").attr("value","A").text("All Department"));
			    	$(empData).each(function(index)
					{
		  		 		$('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.name));
					});
			    	
	   				},
		    		error : function () 
		    		{
						alert("Somthing is wrong to get Data");
					}
			});
}
function getDay(dataFor,dept,targetid)
{
	$.ajax(
			{
				type :"post",
				url :"/over2cloud/view/Over2Cloud/WorkingHrs/getConcernDay.action?dataFor="+dataFor+"&dept="+dept,
				success : function(empData)
				{
					$('#'+targetid+' option').remove();
					$('#'+targetid).append($("<option></option>").attr("value","A").text("All Department"));
			    	$(empData).each(function(index)
					{
		  		 		$('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.name));
					});
				},
	    		error : function () 
	    		{
					alert("Somthing is wrong to get Data");
				}
			});
}
function back2View()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/WorkingHrs/getHeaderBeforeView.action",
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
          alert("error");
      }
	 });
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
	<s:form id="formone" name="formone" action="addWorkingTimings" theme="css_xhtml"  method="post" enctype="multipart/form-data" >
	<s:hidden name="dataFor" value="%{dataFor}"/>
    <center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone" style="float:center; margin-left: 7px"></div></div></center>          
        <s:iterator value="createColumnList" status="status">
          <s:if test="%{mandatory}"><span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span></s:if>
				<s:if test="key=='moduleName'">
					     <div class="newColumn">
			              <div class="leftColumn"><s:property value="%{value}"/>:</div>
							  <div class="rightColumn">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                   <s:select 
				                              id="%{key}"
				                              name="dataFor"
				                              list="appDetails"
				                              headerKey="-1"
				                              headerValue="Select Module" 
				                              cssClass="select"
				                              cssStyle="width:82%"
				                              onchange="getDeptByModule(this.value,'dept')"
				                              >
				                   </s:select>
				              </div>
			              </div>
               </s:if>
			</s:iterator>
       <s:iterator value="createColumnList" status="status" begin="0" end="1">
          <s:if test="%{mandatory}"><span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span></s:if>
       			
				<s:if test="key=='dept'">
					     <div class="newColumn">
			              <div class="leftColumn"><s:property value="%{value}"/>:</div>
							  <div class="rightColumn">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                   <s:select 
	                              id="%{key}"
	                              name="%{key}"
	                            	list="#{'1': 'Barber', '2' : 'Biomedical', '3': 'Dietician', '4' : 'Engineering', '5' : 'Food & Beverages', '6' : 'Housekeeping', '7' : 'Operations', '8' : 'OCC' }"
						      	   multiple="true"
	                              cssStyle	="height:45%"
	                               
	                              />
				                   
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
				<s:div id="timediv" cssStyle="display :none;">
				<s:iterator value="createColumnList" begin="5" end="6">
				<s:if test="key=='fromTime'">
				 <div class="newColumn">
							 <div class="leftColumn"><s:property value="%{value}"/>:</div>
							 <div class="rightColumn">
							 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
							 	<sj:datepicker name="%{key}" id="%{key}"  placeholder="Enter Data" readonly="true" showOn="focus"  timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10"  timepickerStepMinute="1" cssClass="textField" />
						     </div>
						</div>
				</s:if>
				
				<s:elseif test="key=='toTime'">
				 <div class="newColumn">
							 <div class="leftColumn"><s:property value="%{value}"/>:</div>
							 <div class="rightColumn">
							 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
							 	<sj:datepicker name="%{key}" id="%{key}"  placeholder="Enter Data" readonly="true" showOn="focus"  timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="1" cssClass="textField" />
						     </div>
						</div>
				</s:elseif>
            </s:iterator>
            </s:div>
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
	                        targets="result" 
	                        clearForm="true"
	                        value=" Save " 
	                        effect="highlight" 
			                effectOptions="{ color : '#222222'}" 
			                effectDuration="5000" 
	                        button="true"
	                        cssClass="submit"
	                        indicator="indicator1222"  
	                        onCompleteTopics="completeData"
	                        cssStyle="margin-left: -40px;"
	                        />
	            <sj:a cssStyle="margin-left: 175px;margin-top: -45px;" button="true" href="#" onclick="resetForm('formone');">Reset</sj:a>
	            <sj:a cssStyle="margin-left: 2px;margin-top: -45px;" button="true" href="#" onclick="back2View();">Back</sj:a>
	               </div>
	               
		        <center><img id="indicator1222" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
		
          <sj:div id="foldeffect" effect="fold">
		  	<div id="result"></div>
		  </sj:div>  
		  
			</s:form>
			</div>
     
</div>
</body>
</html>
