<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<%
String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();
%>
<html>
<head>

<script type="text/javascript">

var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
	
});	
</script>
</head>
<body>


<!-- Code For Header Strip -->
<div class="clear"></div>
<div class="listviewBorder" style="margin-top: 1px;width: 97%;margin-left: 5px;" align="center">
<div class="clear"></div>
<s:url id="viewcartData" action="viewcartData" escapeAmp="false">
<s:param name="cartID" value="%{cartID}"/>
<s:param name="searchFieldsFlag" value="%{searchFieldsFlag}"/>
<s:param name="fromDate" value="%{fromDate}"></s:param>
<s:param name="toDate" value="%{toDate}"></s:param>
</s:url>
<s:url id="modifyCartData" action="modifyCartData" />

<center>
<sjg:grid 
		  id="gridBasicDetails"
          href="%{viewcartData}"
          gridModel="masterViewProp"
          navigator="false"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="%{deleteFlag}"
          navigatorEdit="%{modifyFlag}"
          navigatorSearch="true"
          rowList="200,400,700"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="200"
          navigatorDeleteOptions="{caption:'Inactive',msg: 'Inactive selected record(s)?',bSubmit: 'Inactive', afterSubmit: function () {
			reload();
	    }}"
	     navigatorEditOptions="{height:230,width:400,reloadAfterSubmit:true, afterSubmit: function () {
									reloadGrid();
	  			  }}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          editurl="%{modifyCartData}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="true"
          autowidth="true"
          onSelectRowTopics="rowselect"
          filter="true"
           filterOptions="{searchOnEnter:false,defaultSearch:'cn'}"
          >
           
          
	<s:iterator value="viewPageColumns" id="viewPageColumns" > 
	
	
				
					<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="%{headingName}"
					width="%{width}"
					hidden="%{hideOrShow}"
					/>
				
	</s:iterator>  
</sjg:grid>
</center></div>
<br><br>
<center><img id="indicatorViewPage" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
<div id="divFullHistory" style="float:left; width:900px;">

</div>

</body>
</html>