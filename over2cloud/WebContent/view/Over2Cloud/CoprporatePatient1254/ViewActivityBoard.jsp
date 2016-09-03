<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<!-- Code End for Header Strip -->
 <div class="clear"></div>
 <div style="overflow: scroll; height: 590px;">
<s:url id="viewActivityBoardData" action="viewActivityBoardDataCPS" escapeAmp="false">
	
  
    </s:url>

<sjg:grid 
		  id="gridedittable"
          href="%{viewActivityBoardData}"
          gridModel="deptDataViewShow"
          groupField="['groupId']"
          groupColumnShow="[false]"
          groupText="['<b>{0}: {1}</b>']"
          groupCollapse="true"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="true"
          navigatorEdit="true"
          navigatorSearch="true"
          rowList="50,100,200,500"
          editinline="true"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400,closeAfterEdit:true,reloadAfterSubmit:true}"
		  navigatorDeleteOptions="{caption:'Inactive',msg: 'Inactive selected record(s)?',bSubmit: 'Inactive',closeAfterEdit:true,reloadAfterSubmit:true}"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          rowNum="50"
          autowidth="true"
          editurl="%{editDeptDataGrid}"
          shrinkToFit="false"
          onSelectRowTopics="rowselect"
          navigatorCloneToTop="true"
          >
          
		<s:iterator value="viewPageColumns" id="viewPageColumns" > 
		
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
			
		</s:iterator>  
</sjg:grid>
</div>

</body>


</html>