<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/helpdesk/shiftConf.js"/>"></script>
<script type="text/javascript">
datePick = function(elem) {
        $(elem).timepicker({timeFormat: 'hh:mm'});
        $('#time_picker_div').css("z-index", 2000);
}
</script>
<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	
</script>
</head>
<body>
<div class="middle-content">
<!-- Header Strip Div Start -->
<div class="list-icon">
	 <div class="head">Shift</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head"> View</div> 
</div>
<div class="clear"></div>
<div class="listviewBorder"  style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<!-- Code For Header Strip -->
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <!-- Block for insert Left Hand Side Button -->
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">    <!-- Insert Code Here -->   </td></tr></tbody>
					  </table>
				  </td>
				  
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				      <sj:a id="addButton"  button="true"  cssClass="button" cssStyle="height:25px;" title="Add" buttonIcon="ui-icon-plus" onclick="addNewShift('viewType','dataFor');">Add</sj:a>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<!-- Code End for Header Strip -->
<div style="overflow: scroll; height: 430px;">
<s:hidden id="dataFor" value="%{dataFor}"/>
<s:hidden id="viewType" value="%{viewType}"/>
<s:url id="shiftConf_URL" action="viewShiftConf"  escapeAmp="false">
<s:param name="viewType" value="%{viewType}"></s:param>
<s:param name="dataFor" value="%{dataFor}"></s:param>
</s:url>
<s:url id="editShiftConf_URL" action="editShiftConf"></s:url>
<div id="time_picker_div" style="display:none">
     <sj:datepicker id="res_Time_pick" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" />
</div>

<center>
<sjg:grid 
		  id="gridshifttable"
          href="%{shiftConf_URL}"
          gridModel="shiftData"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="%{modifyFlag}"
          navigatorSearch="false"
          rowList="10,25,50"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="false"  
          loadonce="%{loadonce}"
          loadingText="Requesting Data..."  
          rowNum="10"
          autowidth="true"
          editurl="%{editShiftConf_URL}"
          navigatorEditOptions="{height:230,width:400}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          shrinkToFit="false"
          >
		  <s:iterator value="viewPageColumns" id="viewPageColumns" >  
		           <sjg:gridColumn 
		      						name="%{colomnName}"
		      						index="%{colomnName}"
		      						title="%{headingName}"
		      						width="%{width}"
		      						align="%{align}"
		      						editable="%{editable}"
		      						formatter="%{formatter}"
		      						search="%{search}"
		      						hidden="%{hideOrShow}"
		 							/>
	      </s:iterator>  
				 
				 <sjg:gridColumn    name="shiftFrom"
                           		    index="shiftFrom"
                                    title="Shift From"
                            		editable="true"
                            		formatter="date"
		                    		formatoptions="{newformat : 'H:i', srcformat : 'H:i'}"
		                    		editoptions="{dataInit:datePick}"
		                    		sortable="true"
		                    		search="true"
                            		width="560"
                            		align="center">
                </sjg:gridColumn>
           
                <sjg:gridColumn  	name="shiftTo"
                            		index="shiftTo"
                            		title="Shift To"
                            		editable="true"
                            		formatter="date"
		                    		formatoptions="{newformat : 'H:i', srcformat : 'H:i'}"
		                    		editoptions="{dataInit:datePick}"
		                    		sortable="true"
                            		search="true"
                            		width="550"
                            		align="center">
                </sjg:gridColumn>
</sjg:grid>
          
	
</center>
</div>
</div>
</div>
</body>
</html>