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
<s:url id="viewStandardMasterData" action="viewStandardMasterData" escapeAmp="false">
<s:param name="status" value="%{status}"></s:param>
<s:param name="serviceID" value="%{serviceID}"></s:param>
</s:url>
<s:url id="editStandardMasterDataGrid" action="editStandardMasterDataGrid" />
<sjg:grid 
		  id="gridStandardMaster"
          href="%{viewStandardMasterData}"
          gridModel="viewListStandard"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="true"
          navigatorEdit="true"
          navigatorSearch="true"
          rowList="50,100,200,500"
          rownumbers="-1"
          rownumbers="true"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          rowNum="50"
          autowidth="true"
          editurl="%{editStandardMasterDataGrid}"
          shrinkToFit="false"
          onSelectRowTopics="rowselect"
          >
          
		<s:iterator value="masterViewStandard" id="masterViewStandard" > 
				<s:if test="%{colomnName=='status'}">
					<sjg:gridColumn 
						name="%{colomnName}"
						index="%{colomnName}"
						title="%{headingName}"
						width="%{width}"
						align="%{align}"
						editable="true"
						search="%{search}"
						hidden="%{hideOrShow}"
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
					search="%{search}"
					hidden="%{hideOrShow}"
					/>
				</s:else>
		</s:iterator>  
</sjg:grid>
</div>

</body>


</html>