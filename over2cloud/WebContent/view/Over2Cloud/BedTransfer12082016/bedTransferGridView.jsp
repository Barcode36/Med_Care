<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<!-- Code End for Header Strip -->
 <div class="clear"></div>
<s:url id="viewActivityBoardData" action="viewActivityBoardData" escapeAmp="false">
	 <s:param name="fromDate" value="%{#parameters.fromDate}"></s:param>
	 <s:param name="toDate" value="%{#parameters.toDate}"></s:param>
	  <s:param name="fromDaysForDate" value="%{#parameters.fromDaysForDate}"></s:param>
	 <s:param name="toDaysForDate" value="%{#parameters.toDaysForDate}"></s:param>
	 <s:param name="sdate" value="%{#parameters.sdate}"></s:param>
	 <s:param name="edate" value="%{#parameters.edate}"></s:param>
	 <s:param name="location" value="%{#parameters.location}"></s:param>
	 <s:param name="nursing" value="%{#parameters.nursing}"></s:param>
	  <s:param name="doctor" value="%{#parameters.doctor}"></s:param>
	 <s:param name="speciality" value="%{#parameters.speciality}"></s:param>
	 <s:param name="patDays" value="%{#parameters.patDays}"></s:param>
	 <s:param name="range" value="%{#parameters.range}"></s:param>
	  <s:param name="patDaysForDate" value="%{#parameters.patDaysForDate}"></s:param>
	 <s:param name="rangeForDate" value="%{#parameters.rangeForDate}"></s:param>
</s:url>
<sjg:grid 
		  id="gridBedTransfer"
          href="%{viewActivityBoardData}"
          gridModel="masterViewProp"
           groupField="['TFR_REQ_STATUS']"
	      groupCollapse="true"
	      groupText="['<b>{0}: {1}</b>']"
          navigator="false"
          navigatorAdd="false"
          navigatorView="false"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="false"
          viewrecords="true"   
	      rowList="1000,2000,6000,10000"
          rownumbers="true"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          userDataOnFooter="true"
          sortable="true"
          pagerInput="true"   
          multiselect="false"  
          gridview="true"
          loadonce="true"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          rowNum="1000"
          autowidth="true"
          filter="true"
          filterOptions="{searchOnEnter:false,defaultSearch:'cn'}"
          shrinkToFit="true"
          onCompleteTopics="loadComplete"
          scrollrows="true"
          >
          
		<s:iterator value="viewPageColumns" id="masterViewProp" > 
				 
					<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="%{headingName}"
					width="%{width}"
					align="%{align}"
					editable="%{editable}"
					search="%{search}"
					hidden="%{hideOrShow}"
					formatter="%{formatter}"
					/>
				 
		</s:iterator>  
</sjg:grid>
</body>
</html>
 <script>
    $.subscribe('loadComplete', function (event, data){
         $("#gridLongPatientStay").jqGrid('setGridParam', { ignoreCase: true});
    });
     </script>
  