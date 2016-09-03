<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s"  uri="/struts-tags"%>
    <%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
	<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<s:url id="viewgroupDataurl" action="viewgroupDataurl" escapeAmp="false">
<s:param name="groupid" value="%{#parameters.select_param}"></s:param>
</s:url>
<s:url id="editContactDataGrid" action="editContactDataGrid" escapeAmp="false">
</s:url>
         <sjg:grid
    	id="gridgrouping"
    	caption="Group Examples (Grouping)"
    	loadonce="false"
    	href="%{viewgroupDataurl}"
    	gridModel="groupDetail"
    	groupField="['name']"
    	groupColumnShow="[true]"
    	groupCollapse="true"
    	groupText="['<b>{0} - {1} Group(s)</b>']"
    	navigator="true"
    	navigatorAdd="false"
    	navigatorEdit="false"
    	navigatorDelete="false"
    	navigatorView="true"
    	rowTotal="1000"
    	rowNum="30"
    	autowidth="true"
    	altRows="true"
    	viewrecords="true"
    	pager="true"
    	pagerButtons="false"
    	pagerInput="false"
    	multiselect="true"
    	multiboxonly="true"
        shrinkToFit="false"
    > 
    	
		<sjg:gridColumn 
		name="id"
		index="id"
		title="Id"
		width="60"
		align="center"
		editable="false"
		search="false"
		hidden="true"
		/>
		
		<sjg:gridColumn 
		name="empName"
		index="empName"
		title="Contact Name"
		width="200"
		align="center"
		editable="false"
		search="false"
		hidden="false"
		/>
		<sjg:gridColumn 
		name="mobOne"
		index="mobOne"
		title="Mobile Number"
		width="260"
		align="center"
		editable="false"
		search="false"
		hidden="false"
		/>
			<sjg:gridColumn 
		name="emailIdOne"
		index="emailIdOne"
		title="Email Id "
		width="290"
		align="center"
		editable="false"
		search="false"
		hidden="false"
		/>
		<sjg:gridColumn 
		name="address"
		index="address"
		title="Address"
		width="290"
		align="center"
		editable="false"
		search="false"
		hidden="false"
		/>
		<sjg:gridColumn 
		name="city"
		index="city"
		title="City "
		width="90"
		align="center"
		editable="false"
		search="false"
		hidden="false"
		/>
		<sjg:gridColumn 
		name="name"
		index="name"
		title="Group Name"
		width="150"
		align="center"
		editable="false"
		search="false"
		hidden="false"
		/>
		 
</sjg:grid>

</body>
</html>