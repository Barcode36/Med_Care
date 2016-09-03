<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<%
String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript">

var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
	
});	
timePick = function(elem) {
    $(elem).timepicker({timeFormat: 'hh:mm'});
    $('#time_picker_div').css("z-index", 2000);
}
</script>
</head>
<body>
<!-- Code For Header Strip -->
<div class="clear"></div>
<div class="listviewBorder"  align="center">
<s:url id="viewMachineDataMaster" action="fetchGridDataforCartSet" >
<s:param name="searchFields" value="%{searchFields}"></s:param>
<s:param name="SearchValue" value="%{SearchValue}"></s:param>
</s:url>
<s:url id="modifyMachineCartSet" action="modifyMachineCartSet" />
<div id="time_picker_div" style="display:none">
     <sj:datepicker id="res_Time_pick" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" />
</div>
<center>
<sjg:grid 
		  id="gridBasicDetails"
          href="%{viewMachineDataMaster}"
          gridModel="machineMasterData"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="%{deleteFlag}"
          navigatorEdit="%{modifyFlag}"
          navigatorSearch="true"
          rowList="15,20,25"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="15"
          navigatorDeleteOptions="{caption:'Inactive',msg: 'Inactive selected record(s)?',bSubmit: 'Inactive', afterSubmit: function () {
			reload();
	    }}"
	     navigatorEditOptions="{height:230,width:400,reloadAfterSubmit:true, afterSubmit: function () {
									reloadGrid();
	  			  }}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          editurl="%{modifyMachineCartSet}"
       navigatorEditOptions="{closeAfterEdit:true,reloadAfterSubmit:false}"
          shrinkToFit="true"
          autowidth="true"
          onSelectRowTopics="rowselect"
          
          >
           
          
	<s:iterator value="masterViewProp" id="masterViewProp" >  

				<s:if test="%{colomnName=='status'}">
				<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="%{headingName}"
					width="%{width}"
					align="%{align}"
					editable="true"
					edittype="select"
					search="%{search}"
					hidden="%{hideOrShow}"
					editoptions="{value:'Active:Active;Inactive:Inactive'}"
					/>
				</s:if>
				
				
				<s:elseif test="%{colomnName=='cart_id'}">
				<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="%{headingName}"
					width="%{width}"
					align="%{align}"
					editable="true"
					edittype="select"
					search="%{search}"
					hidden="%{hideOrShow}"
					editoptions="{value:'1:cart1;2:cart2; 3:cart3'}"
					/>
				</s:elseif>
				
				
				
				<s:else>
					<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="%{headingName}"
					width="%{width}"
					align="%{align}"
					editable="%{editable}"
					search="%{search}"
					hidden="%{hideOrShow}"
					/>
				</s:else>
	</s:iterator>  
</sjg:grid>
</center></div>
<br><br>
<center><img id="indicatorViewPage" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
<div id="divFullHistory" style="float:left; width:900px;">
</div>

</body>
</html>