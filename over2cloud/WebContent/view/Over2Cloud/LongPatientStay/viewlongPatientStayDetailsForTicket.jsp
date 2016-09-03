<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
	$(document).ready(function()
			 {
				 document.getElementById('startDate').value=$("#hfromDate").val();
				   document.getElementById('endDate').value=$("#htoDate").val();
			 });
</script>
</head>
<body>
<!-- Code End for Header Strip -->
 <div class="clear"></div>
 <s:hidden id="hfromDate" value="%{sdate}"></s:hidden>
<s:hidden id="htoDate" value="%{edate}"></s:hidden>
<s:url id="viewActivityBoardData1" action="viewActivityBoardDataForTicket" escapeAmp="false">
	 
	  
	 <s:param name="sdate" value="%{#parameters.sdate}"></s:param>
	 <s:param name="edate" value="%{#parameters.edate}"></s:param>
	 <s:param name="location" value="%{#parameters.location}"></s:param>
	 <s:param name="nursing" value="%{#parameters.nursing}"></s:param>
	  <s:param name="doctor" value="%{#parameters.doctor}"></s:param>
	 <s:param name="speciality" value="%{#parameters.speciality}"></s:param>
	 
</s:url>
<sjg:grid 
		  id="gridLongPatientStay1"
          href="%{viewActivityBoardData1}"
          gridModel="masterViewProp"
          groupField="['status']"
          groupOrder="['desc']"
          groupCollapse="false"
          groupText="['<b>{0}: {1}</b>']"
          groupColumnShow="false"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="true"
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
          shrinkToFit="false"
          onCompleteTopics="loadComplete1"
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
  
    $.subscribe('loadComplete1', function (event, data){
         $("#gridLongPatientStay1").jqGrid('setGridParam', { ignoreCase: true});
    });
     </script>
  