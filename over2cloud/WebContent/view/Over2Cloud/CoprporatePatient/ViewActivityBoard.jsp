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
 <div style="overflow: scroll;">
<s:url id="viewActivityBoardDataCPS" action="viewActivityBoardDataCPS" >
</s:url>

<sjg:grid 
		 id="gridedittable"
	          href="%{viewActivityBoardDataCPS}"
	          gridModel="masterViewProp"
	          groupField="['status']"
	          groupColumnShow="[false]"
	          groupCollapse="false"
	          groupText="['<b>{0}: {1}</b>']"
	           groupOrder="['asc']"
	           navigator="true"
	          navigatorAdd="false"
	          navigatorView="true"
	          navigatorDelete="false"
	          navigatorEdit="false"
	          navigatorSearch="true"
	          editinline="false"
	          viewrecords="true"   
	          rowList="200,400,1000,2000,5000,8000"
	          rowNum="200"
	              
	          pager="true"
	          dataType="json"
	          gridview="true"
	          pagerButtons="true"
	          rownumbers="true"
	          pagerInput="true"   
	          navigatorSearchOptions="{sopt:['eq','cn']}"  
	          loadingText="Requesting Data..."  
	          groupSummary="true"
	          userDataOnFooter="true"
	          navigatorEditOptions="{height:230,width:400}"
	          editurl="%{modifyAsset}"
	          navigatorEditOptions="{closeAfterEdit:true,reloadAfterSubmit:true}"
	          shrinkToFit="false"
	          autowidth="true"
	          loadonce="true"
	          onSelectRowTopics="rowselect"
	          onEditInlineSuccessTopics="oneditsuccess"
	        
	          onCompleteTopics="closeTop"
	       	 filter="true"
       		 filterOptions="{searchOnEnter:false,defaultSearch:'cn'}"
       		 
          >
          
		<s:iterator value="viewPageColumns" id="viewPageColumns" > 
		
					<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="%{headingName}"
					width="%{width}".
					
					align="%{align}"
					formatter="%{formatter}"
					editable="%{editable}"
					search="%{search}"
					hidden="%{hideOrShow}"
					/>
			
		</s:iterator>  
</sjg:grid>
</div>

</body>


</html>