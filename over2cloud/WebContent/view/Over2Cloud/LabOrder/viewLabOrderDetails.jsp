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
<s:url id="viewLabOrderData" action="viewLabOrderData" escapeAmp="false">
	 <s:param name="sdate" value="%{#parameters.sdate}"></s:param>
	 <s:param name="edate" value="%{#parameters.edate}"></s:param>
	 <s:param name="stime" value="%{#parameters.stime}"></s:param>
	 <s:param name="etime" value="%{#parameters.etime}"></s:param>
	 <s:param name="speciality" value="%{#parameters.speciality}"></s:param>
	 <s:param name="location" value="%{#parameters.location}"></s:param>
	 <s:param name="nursingUnit" value="%{#parameters.nursingUnit}"></s:param>
	 <s:param name="addDoctor" value="%{#parameters.addDoctor}"></s:param>
	 <s:param name="labName" value="%{#parameters.labName}"></s:param>
	 <s:param name="sts" value="%{#parameters.sts}"></s:param>
</s:url>
<s:url id="editLabOrderDataGrid" action="editLabOrderDataGrid" />
<sjg:grid 
		  id="gridLabOrder"
          href="%{viewLabOrderData}"
          gridModel="viewList"
          groupField="['LONG_DESC']"
          groupCollapse="false"
           groupDataSorted="true"
          groupText="['<b>{0}: {1}</b>']"
          groupColumnShow="false"
         navigator="false"
          navigator="false"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="true"
          viewrecords="true"   
	      rowList="4000,5000,7000,10000"
          rownumbers="true"
          viewrecords="true"  
          loadingText="Requesting Data..."       
          pager="true"
          pagerButtons="true"
          userDataOnFooter="true"
          sortable="true"
          pagerInput="true"   
          multiselect="false"  
          gridview="true"
          loadonce="true"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          rowNum="4000"
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
         $("#gridLabOrder").jqGrid('setGridParam', { ignoreCase: true});
    });

      
    </script>
     
 
  