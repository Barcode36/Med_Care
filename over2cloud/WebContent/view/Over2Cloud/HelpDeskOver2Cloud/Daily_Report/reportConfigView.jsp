<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<s:url id="report_URL" action="viewReportData" escapeAmp="false">
<s:param name="searchField" value="%{searchField}" />
<s:param name="searchString" value="%{searchString}" />
<s:param name="searchOper" value="%{searchOper}" />
</s:url>
<s:url id="modifyReport_URL" action="modifyReport" /> 
<div id="time_picker_div" style="display:none">
     <sj:datepicker id="res_Time_pick" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" />
</div>
<div id="ui-datepicker-div" style="display:none">
     <sj:datepicker  value="today" id="date3" name="date3" displayFormat="yyyy-mm-dd"/>
</div>

        <sjg:grid 
		  id="gridreport"
          href="%{report_URL}"
          gridModel="reportData"
          groupField="['dept']"
    	  groupColumnShow="[false]"
    	  groupCollapse="true"
    	  groupText="['<b>{0}: {1} </b>']"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="true"
          navigatorEdit="true"
          navigatorSearch="true"
          rowList="50,100,150,200"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="false"  
          loadingText="Requesting Data..."  
          rowNum="50"
          autowidth="true"
          editurl="%{modifyReport_URL}" 
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
               navigatorEditOptions="{height:230,width:400,reloadAfterSubmit:true, afterSubmit: function () {
									reloadPage();
	  			  }}"
		          navigatorDeleteOptions="{caption:'Inactive',msg: 'Inactive selected record(s)?',bSubmit: 'Inactive', afterSubmit: function () {
			reloadPage();
	    }}"
          navigatorViewOptions="{height:400,width:400}"
          shrinkToFit="false"
          onSelectRowTopics="rowselect"
          >
		           <sjg:gridColumn 
		      						name="id"
		      						index="id"
		      						title="ID"
		      						width="100"
		      						align="center"
		      						editable="true"
		      						hidden="true"
		      						key="true"
		 							/>
		 							
		 			<sjg:gridColumn 
		      						name="dept"
		      						index="dept"
		      						title="Department"
		      						width="100"
		      						align="center"
		      						editable="false"
		      						hidden="false"
		 							/>
		 							
		 			<sjg:gridColumn 
		      						name="empName"
		      						index="empName"
		      						title="Employee"
		      						width="150"
		      						align="center"
		      						editable="false"
		      						hidden="false"
		 							/>				
		 							
		 		   <sjg:gridColumn 
		      						name="module"
		      						index="module"
		      						title="For Module"
		      						width="120"
		      						editable="false"
		      						align="center"
		 							/>
		 							
		 			<sjg:gridColumn 
		      						name="report_level"
		      						index="report_level"
		      						title="Report For"
		      						width="150"
		      						align="center"
		      						editable="false"
		 							/>
		 							
		 			<sjg:gridColumn 
		      						name="report_type"
		      						index="report_type"
		      						title="Frequency"
		      						width="150"
		      						align="center"
		      						editable="true"
		      						edittype="select"
		      						editoptions="{value:'D:Daily;W:Weekly;M:Monthly;Q:Quartely;H:Half Yearly'}"
		 							/>	
		 							
		 			<sjg:gridColumn 
		      						name="email_subject"
		      						index="email_subject"
		      						title="Subject"
		      						width="175"
		      						align="center"
		      						editable="true"
		      						hidden="true"
		 							/>
		 							
		 			<sjg:gridColumn 
		      						name="report_date"
		      						index="report_date"
		      						title="Start From"
		      						width="100"
		      						align="center"
		      						editable="true"
                            		formatter="date"
		                    		formatoptions="{newformat : 'd-m-Y', srcformat : 'd-m-Y'}"
			                        editoptions="{dataInit:datePick}"
		 							/>
		 							
		 							
		 			<sjg:gridColumn 
		      						name="report_time"
		      						index="report_time"
		      						title="Schedule At"
		      						width="100"
		      						align="center"
		      						editable="true"
                            		formatter="date"
		                    		formatoptions="{newformat : 'H:i', srcformat : 'H:i'}"
		                    		editoptions="{dataInit:timePick}"
		 							/>		
		 							
		 			<sjg:gridColumn 
		      						name="sms"
		      						index="sms"
		      						title="SMS"
		      						width="90"
		      						align="center"
		      						editable="true"
		      						edittype="select"
		      						editoptions="{value:'Y:Yes;N:No'}"
		 							/>			
		 							
		 							
		 			<sjg:gridColumn 
		      						name="mail"
		      						index="mail"
		      						title="Mail"
		      						width="90"
		      						align="center"
		      						editable="true"
		      						edittype="select"
		      						editoptions="{value:'Y:Yes;N:No'}"
		 							/>			
		 			
		 			<sjg:gridColumn 
		      						name="status"
		      						index="status"
		      						title="Status"
		      						width="99"
		      						align="center"
		      						editable="true"
		      					    edittype="select"
									editoptions="{value:'Active:Active;Inactive:Inactive'}"
		 							/>	
		 							
		 							<sjg:gridColumn 
		      						name="deactiveOn"
		      						index="deactiveOn"
		      						title="Date"
		      						width="99"
		      						align="center"
		      						editable="false"
		 							/>			
		 			
		 																		
	</sjg:grid>
</body>
</html>