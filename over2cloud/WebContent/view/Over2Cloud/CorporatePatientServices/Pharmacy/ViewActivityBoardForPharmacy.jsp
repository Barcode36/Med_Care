<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
 <%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 
</head>

<body>

 
 <s:url id="viewActivityBoardDataForPharmacy" action="viewActivityBoardDataForPharmacy" escapeAmp="false">
  <s:param name="dataWild" value="%{dataWild}"></s:param>
 <s:param name="priority" value="%{priority}"></s:param>
 <s:param name="minDateValue" value="%{minDateValue}"></s:param>
 <s:param name="maxDateValue" value="%{maxDateValue}"></s:param>
  <s:param name="feedStatus" value="%{feedStatus}"></s:param>
   <s:param name="nursingUnit" value="%{nursingUnit}"></s:param>
   <s:param name="uhid" value="%{uhid}"></s:param>
  <s:param name="patientName" value="%{patientName}"></s:param>
  <s:param name="encounterId" value="%{encounterId}"></s:param>
   
   <s:param name="roomNo" value="%{roomNo}"></s:param>
      </s:url>
		<sjg:grid 
	          id="gridedittable"
	          href="%{viewActivityBoardDataForPharmacy}"
	          gridModel="masterViewProp"
	          groupField="['status']"
	           groupColumnShow="[false]"
	          groupCollapse="false"
	          groupText="['<b>{0}: {1}</b>']"
	          groupOrder="['desc']"
	          viewrecords="true"   
	          rowList="8000,9000,10000,11000,12000,13000"
	          rowNum="8000"
	          navigatorRefresh="false"
	          sortable="true"
	          pager="true"
	          gridview="true"
	          pagerButtons="true"
	          rownumbers="true"
	          pagerInput="true"   
	          navigatorSearchOptions="{sopt:['eq','cn']}"  
	          userDataOnFooter="true"
	            shrinkToFit="true"
	          loadonce="true"
	        	 filter="true"
	        	loadingText="Requesting Data..."  
       		 filterOptions="{searchOnEnter:false,defaultSearch:'cn'}"
       		 multiselect="true"
       		      	>
			<s:iterator value="viewPageColumns" id="viewPageColumns" >
		 			<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="%{headingName}"
					width="%{width}"
					align="center"
					editable="%{editable}"
					formatter="%{formatter}"
					search="%{search}"
					hidden="%{hideOrShow}"
				/>
			 
			</s:iterator>      
		</sjg:grid>
 
 
	
		
</body>
</html>