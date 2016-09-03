<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
 <%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>

<body >

 
 <s:url id="viewActivityBoardDataCPS" action="viewActivityBoardDataCPS" escapeAmp="false">
 <s:param name="dataWild" value="%{dataWild}"></s:param>
 <s:param name="heart" value="%{heart}"></s:param>
 <s:param name="xray" value="%{xray}"></s:param>
 <s:param name="ultra" value="%{ultra}"></s:param>
 <s:param name="priority" value="%{priority}"></s:param>
 <s:param name="minDateValue" value="%{minDateValue}"></s:param>
 <s:param name="maxDateValue" value="%{maxDateValue}"></s:param>
  <s:param name="feedStatus" value="%{feedStatus}"></s:param>
   <s:param name="nursingUnit" value="%{nursingUnit}"></s:param>
    <s:param name="escLevel" value="%{escLevel}"></s:param>
  
    </s:url>
		<sjg:grid 
	          id="gridedittable1"
	          href="%{viewActivityBoardDataCPS}"
	          gridModel="masterViewProp"
	          groupField="['status','priority']"
	          groupColumnShow="[true,true]"
	          groupCollapse="false"
	          groupText="['<b>{0}: {1}</b>']"
	            groupOrder="['desc']"
	           navigator="true"
	          navigatorAdd="false"
	          navigatorView="false"
	          navigatorDelete="false"
	          navigatorEdit="false"
	          navigatorSearch="false"
	          editinline="false"
	          viewrecords="true"   
	          rowList="8000,9000,10000,11000,12000,13000"
	          rowNum="8000"
	          navigatorRefresh="false"
	          sortable="true"
	          pager="true"
	          dataType="json"
	          gridview="true"
	          pagerButtons="true"
	          rownumbers="true"
	          pagerInput="true"   
	          navigatorSearchOptions="{sopt:['eq','cn']}"  
	          userDataOnFooter="true"
	          navigatorEditOptions="{height:230,width:400}"
	          navigatorEditOptions="{closeAfterEdit:true,reloadAfterSubmit:true}"
	          shrinkToFit="true"
	          autowidth="true"
	          loadonce="true"
	          onSelectRowTopics="rowselect"
	          onEditInlineSuccessTopics="oneditsuccess"
	          multiselect="true"
	          onCompleteTopics="colorStatus"
	       	 filter="true"
       		 filterOptions="{searchOnEnter:false,defaultSearch:'cn'}"
       		       
       		      	>
			<s:iterator value="viewPageColumns" id="viewPageColumns" >
			<s:if test="%{colomnName=='action'}">
			<%if(userRights.contains("ORD_Action_VIEW") || true) 
								{%>
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
				<%} %>
			</s:if>
			<s:else>
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
			</s:else>
				
			</s:iterator>      
		</sjg:grid>
 
 
	
		
</body>
</html>