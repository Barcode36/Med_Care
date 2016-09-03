<%@ taglib prefix="s" uri="/struts-tags" %>
<head>
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
</head>
<s:select 
            id="subdept"
            name="subdept" 
            list="subDepartmentList"
            headerKey="-1"
            headerValue="Select Sub-Department" 
            cssClass="textField"
		    cssStyle="width:102%"
            onchange="getEmpListWithSubDeptName(this.value);"
            >
</s:select>