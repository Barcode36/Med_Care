<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<div style="height: 23px;" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody>
					    <tr><td class="pL10">
					    </td>
					    </tr>
					    </tbody>
					  </table>
				  </td>
				  <td class="alignright printTitle">
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>

<div style="overflow: scroll; height: 430px;">
<s:url id="href_URL" action="viewProductivitydata" escapeAmp="false">
<s:param name="id" value="%{id}"></s:param>
<s:param name="dataFor" value="%{dataFor}"></s:param>
<s:param name="deptId" value="%{deptId}"/>
<s:param name="subdeptId" value="%{subdeptId}"/>
<s:param name="fromDate" value="%{fromDate}"></s:param>
<s:param name="toDate" value="%{toDate}"></s:param>
<s:param name="byDeptId" value="%{byDeptId}"></s:param>
<s:param name="moduleName" value="%{moduleName}"></s:param>
<s:param name="searchField" value="%{searchField}"></s:param>

</s:url>
<sjg:grid 
		  id="productivityGridCol"
          href="%{href_URL}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="true"
          rowList="10,15,20,50,100,150"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="false"  
          loadonce="true"
          loadingText="Requesting Data..."  
          rowNum="12"
          autowidth="true"
          navigatorEditOptions="{height:230,width:500}"
          navigatorSearchOptions="{sopt:['cn','eq']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          >
		 <s:iterator value="masterViewProp" id="masterViewProp" >  
		
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
		    />
		</s:iterator> 
</sjg:grid>
</div>
</div>
</div>
</body>
</html>