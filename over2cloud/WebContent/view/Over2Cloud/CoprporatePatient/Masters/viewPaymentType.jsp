<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<!-- Code For Header Strip -->
<div class="clear"></div>
<div class="listviewBorder" align="center">
<div class="clear"></div>

<s:url id="paymentTypeGridViewData" action="paymentTypeGridViewData" >
<s:param name="searchFields" value="%{searchFields}"></s:param>
<s:param name="status" value="%{status}"></s:param>
</s:url>
<s:url id="modifyPayment" action="modifyPayment" />
<center>
<sjg:grid 
		  id="gridBasicDetails"
          href="%{paymentTypeGridViewData}"
          gridModel="locationMasterData"
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
			reloadGrid();
	    }}"
	     navigatorEditOptions="{height:230,width:400,reloadAfterSubmit:true, afterSubmit: function () {
									reloadGrid();
	  			  }}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          editurl="%{modifyPayment}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="true"
          autowidth="true"
          onSelectRowTopics="rowselect"
          
          >
           
          
	<s:iterator value="masterViewProp" id="masterViewProp" >  
	<s:if test="%{colomnName=='status'}">
				<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="Status"
					width="%{width}"
					align="%{align}"
					editable="true"
					search="%{search}"
					hidden="%{hideOrShow}"
					edittype="select"
					editoptions="{value:'Active:Active;Inactive:Inactive; Repaire:Repaire'}"
					/>
				
				</s:if>
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