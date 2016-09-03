

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
</head>
<body>

<div style="overflow: scroll; height:430px;">
<s:url id="viewCons" action="viewConsInGrid" >
<s:param name="type" value="%{type}"></s:param>
<s:param name="consW" value="%{consW}"></s:param>
<s:param name="mobW" value="%{mobW}"></s:param>
</s:url>


<s:url id="modifyCons" action="modifyCons" />
<sjg:grid 
		  
          
          id="gridBasicDetails"
          href="%{viewCons}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="%{deleteFlag}"
          navigatorEdit="%{modifyFlag}"
          navigatorSearch="true"
          rowList="15,30,50"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="15"
          navigatorEditOptions="{height:230,width:400}"
          navigatorDeleteOptions="{caption:'Inactive',msg: 'Inactive selected record(s)?',bSubmit: 'Inactive', afterSubmit: function () {
			reload();
	    }}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          editurl="%{modifyCons}"
          navigatorEditOptions="{height:230,width:400,reloadAfterSubmit:true, afterSubmit: function () {
									reloadGrid();
	  			  }}"
          shrinkToFit="true"
          autowidth="true"
          onSelectRowTopics="rowselect"
    		
          >
         
         <s:iterator value="masterViewProp" id="masterViewProp" >  
         <s:if test="%{colomnName=='flag'}">
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
	searchoptions="{sopt:['eq','cn']}"
	edittype="select"
	editoptions="{value:'Active:Active;Inactive:Inactive'}"
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
	formatter="%{formatter}"
	search="%{search}"
	hidden="%{hideOrShow}"
	searchoptions="{sopt:['eq','cn']}"
					/>
				</s:else>
	
	</s:iterator>  
	          
          
		
</sjg:grid>
</div>

</body>
</html>



