<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/dailyreportvalidation.js"/>"></script>
<script  type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" defer="defer">

$(document).ready(function()
		 {
		 	$("#allot_to").multiselect({
		 	   show: ["", 200],
		 	   hide: ["explode", 1000]
		 	});
		 	$("#register_by_dept").multiselect({
			 	   show: ["", 200],
			 	   hide: ["explode", 1000]
			 	});
		 	$("#register_by").multiselect({
		 	   show: ["", 200],
		 	   hide: ["explode", 1000]
		 	});
		 	$("#ticket_status").multiselect({
		 	   show: ["", 200],
		 	   hide: ["explode", 1000]
		 	});
		 });
</script>
<script type="text/javascript">

		function removeMulti(){
			$(".ui-widget").remove();
		}
		
		
		
		$( "#cboxClose" ).click(function() {
			removeMulti();
			});
		
	function getEmployeesList(id,divId,elementId)
	{
		var deptId=$("#"+id).val();
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Daily_Report/getEmployeeList.action?deptId="+deptId+"&elementId="+elementId,
		    success : function(subdeptdata) {
	       $("#"+divId).html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	 
function backButton()
{
	$("#moreSettingDiv").html("<br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Daily_Report/beforeDailyReportConfigViewHeader.action?pageType=SC",
	    success : function(subdeptdata) {
       $("#"+"moreSettingDiv").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function getEmailContent()
{
	var freq=$("#report_type").val();
	if (freq!='-1')
	{
		var c=$("#report_type option[value="+freq+"]").text();
		var module=$("#module").val();
		if (module!='-1') 
		{
			var b=$("#module option[value="+module+"]").text();
			var dt = new Date();
			var month= dt.getMonth() + 1 ;
			var date=dt.getDate() + "-" + month + "-" +dt.getFullYear() ;
			var subject=c+" Summary Report for "+b+" As On "+date+" & "+dt.getHours() + ":" + dt.getMinutes();
			$("#email_subject").val(subject);
		} 
		else 
		{
			alert("Please Enter Module");
		}
		
	} else 
	{
		alert("Please Enter Frequency");
	}
	
}
function fetchPreview(datafor)
{
	var moduleName=$("#module").val();
	var c=$("#module option[value="+moduleName+"]").text();
	if (moduleName=='-1' || moduleName==null) 
	{
		 $("#emailDialog").dialog('open');
		 $("#emailDialog").dialog({title: ' Warning' ,width: '60',position:'center',height:'80' });
		 $("#emailDialog").html('<center>Please Select Application Name</center>');
	} else 
	{
		if (datafor=='email') 
		{
			 $("#emailDialog").load("<%=request.getContextPath()%>/view/Over2Cloud/HelpDeskOver2Cloud/Daily_Report/reportEmailData.action?module="+moduleName);
			 $("#emailDialog").dialog('open');
			 $("#emailDialog").dialog({title: ' Email Report Content for '+c ,width: '750',height:'600' });	
		}
		else 
		{
			 $("#emailDialog").load("<%=request.getContextPath()%>/view/Over2Cloud/HelpDeskOver2Cloud/Daily_Report/reportSMSData.action?module="+moduleName);
			 $("#emailDialog").dialog('open');
			 $("#emailDialog").dialog({title: ' SMS Report Content for '+c ,width: '750',height:'150' });
		}
		
	}
	 
	
}
</script>
</head>
<body id="report" onunload="removeMulti();">
<sj:dialog
          id="emailDialog"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Email Content"
          modal="true"
          closeTopics="closeEffectDialog"
          width="750"
          height="550"
          >
</sj:dialog>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Configure Summary Report</div><img alt="" src="images/forward.png" style="margin-top:10px; float: left;"><div class="head"> Add</div>
</div>
<div class="clear"></div>
 <div style=" float:left; padding:5px 0%; width:95%;" class="border" style="overflow-x:hidden;">
<s:form id="formone" name="formone" action="addReportConfiguration" theme="css_xhtml"  method="post" enctype="multipart/form-data" >
<center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone" style="float:center; margin-left: 7px"></div></div><center>    	
           
      <s:iterator value="pageFieldsColumns" begin="1" end="1" status="status">
        	<s:if test="%{mandatory}"><span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span></s:if>
       		<s:if test="%{key=='module'}">
       			 <div class="newColumn">
				 <div class="leftColumn"><s:property value="%{value}"/>:</div>
					<div class="rightColumn">
					<s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                  <s:select 
		                              id="%{key}"
		                              name="%{key}"
		                              list="moduleMap"
		                              headerKey="-1"
		                              headerValue="Select Application" 
		                              cssClass="select"
		                              cssStyle="width:82%"
		                                
		                              >
		                  </s:select>
		                 <sj:a id="mailButton" title="Mail" buttonIcon="ui-icon-mail-closed" cssStyle="margin-left: 268px;margin-top: -28px;height:25px; width:32px" cssClass="button" button="true"  onclick="fetchPreview('email')"></sj:a>
		                  <sj:a id="smsButton" title="SMS" buttonIcon="ui-icon-contact" cssStyle="margin-left: 310px;margin-top: -28px;height:25px; width:32px" cssClass="button" button="true"  onclick="fetchPreview('sms')"></sj:a> 
					</div>
			   </div>
       		</s:if>
       </s:iterator>
       	
       	          
      <s:iterator value="pageFieldsColumns" begin="0"  end="0" status="status">
        	<s:if test="%{mandatory}"><span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>1#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span></s:if>
     
       		<s:if test="%{key=='deptName'}">
       			<div class="newColumn">
       			<div class="leftColumn">Department:</div>
				<div class="rightColumn">
					<s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                  <s:select 
	                              id="%{key}1"
	                              name="%{key}"
	                              list="deptList"
	                              headerKey="-1"
	                              headerValue="Select Department" 
	                              cssClass="select"
	                              cssStyle="width:82%"
	                              onchange="commonSelectAjaxCall('employee_basic','id','empName','deptname',this.value,'','','empName','ASC','empId','Select Employee');"
	                              >
	                  </s:select>
				</div>
				</div>
       		</s:if>
       	
       </s:iterator>
       	
       	
       	
       	<s:iterator value="pageFieldsColumns" begin="2"  status="status">
        	<s:if test="%{mandatory}"><span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span></s:if>
       			<s:if test="%{key=='empId'}">
       			<div class="newColumn">
				<div class="leftColumn"><s:property value="%{value}"/>:</div>
				<div class="rightColumn">
				<s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                   <s:select 
		                              id="%{key}"
		                              name="empId"
		                              list="{'No Data'}"
		                              headerKey="-1"
		                              headerValue="Select Employee" 
		                              cssClass="select"
		                              cssStyle="width:82%"
		                              >
		                  </s:select>
				</div>
			    </div> 
       		</s:if>
       		 
       		  		<s:elseif test="%{key=='report_type'}">
       		 			<div class="newColumn">
       			<div class="leftColumn"><s:property value="%{value}"/>:</div>
				<div class="rightColumn">
					<s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                  <s:select 
	                              id="%{key}"
	                              name="reportType"
	                              list="#{'D': 'Daily', 'W' : 'Weekly', 'M': 'Monthly', 'Q' : 'Quarterly', 'H' : 'Half Yearly' }"
	                              headerKey="-1"
	                              headerValue="Select Report Frequency" 
	                              cssClass="select"
	                              cssStyle="width:82%"
	                              />
				</div>
				</div> 
       		</s:elseif>
       	</s:iterator>

       	
      	<s:iterator value="pageFieldsColumnsTT" begin="0" end="0" status="status">
	       	  <div class="newColumn">
				<div class="leftColumn"><s:property value="%{value}"/>:</div>
				<div class="rightColumn">
				<s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                  <s:textarea name="%{key}"  id="%{key}" onclick="getEmailContent();" readonly="true" cssClass="textField" placeholder="Enter Data" />
				</div>
			  </div> 
       	 </s:iterator>
        	<div class="clear"></div>
        	
       	<s:iterator value="pageFieldsColumnsTT" begin="1" status="status">
            <s:if test="%{mandatory}">
             <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
            </s:if>
      		<s:if test="#status.odd">
   			<div class="newColumn">
   			<div class="leftColumn"><s:property value="%{value}"/>:</div>
				<div class="rightColumn">
					<s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                    <sj:datepicker name="%{key}" id="%{key}" placeholder="Enter Report Date" readonly="true" showOn="focus"  changeMonth="true" changeYear="true" yearRange="2013:2020" minDate="0" maxDate="+12m" showAnim="slideDown" duration="fast" displayFormat="dd-mm-yy" cssClass="textField"/>
				</div>
			</div>
      		</s:if>
      		<s:else>
      		<div class="newColumn">
			<div class="leftColumn"><s:property value="%{value}"/>:</div>
				<div class="rightColumn">
				<s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                  <sj:datepicker name="%{key}" id="%{key}" placeholder="Enter Report Time" readonly="true" showOn="focus"  timepicker="true"  timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="5" showAnim="slideDown" duration="fast" cssClass="textField"/>
				</div>
		    </div> 
      		</s:else>
       	</s:iterator>
        	
        	
        <span id="mandatoryFields" class="pIds" style="display: none; ">sms#sms#C#CB,</span>
        <span id="mandatoryFields" class="pIds" style="display: none; ">mail#mail#C#CB,</span>
            
        <div class="newColumn">
	        <div class="leftColumn"></div>
	     	<div class="rightColumn" >
	     		<div style="width: auto; float: left; text-align: left; line-height:25px; margin-right: 10px;">SMS:</div><div style="width: auto; float: left; text-align: left;  line-height:25px; margin-right: 10px; vertical-align: middle; padding-top: 5px;"><s:checkbox name="sms" id="sms"></s:checkbox></div>
	     		<div style="width: auto; float: left; text-align: left; line-height:25px; margin-right: 10px; margin-left: 20px;">Email:</div><div style="width: auto; float: left; text-align: left;  line-height:25px; margin-right: 10px; vertical-align: middle; padding-top: 5px;"><s:checkbox name="mail" id="mail"></s:checkbox></div>
	     	</div>
        </div>
      <div class="clear"></div>
		    
	  <div class="fields" align="center">
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
	                        cssStyle="margin-left: -40px;"
	                        />
				            <sj:a cssStyle="margin-left: 183px;margin-top: -45px;" button="true" href="#" onclick="resetForm('formone');">Reset</sj:a>
				            <sj:a cssStyle="margin-left: 5px;margin-top: -45px;" button="true" href="#" onclick="backButton();">Back</sj:a>
	               </div>
	               </li>
		     </ul>
			<center><img id="indicator1" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
			<sj:div id="foldeffect1" cssClass="sub_class1" ><div id="target1"></div></sj:div>
		</div>
	</center>
	</center>
 </s:form>
</div>
</div>
</body>
</html>