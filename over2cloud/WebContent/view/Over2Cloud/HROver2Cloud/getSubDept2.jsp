<%@ taglib prefix="s" uri="/struts-tags" %>
<s:select 
            id="subdept"
            name="subdept" 
            list="subDepartmentList"
            headerKey="-1"
            headerValue="Select Sub-Department" 
            cssClass="textField"
            cssStyle="width:130%"
            onchange="getEmpListWithSubDeptName(this.value);"
            >
</s:select>