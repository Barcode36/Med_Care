<%@ taglib prefix="s" uri="/struts-tags" %>
<div id="deptID" >
<s:select 
           id="deptname"
           name="deptname" 
           list="deptList"
           headerKey="-1"
           headerValue="Select Department" 
           cssClass="textField"
           cssStyle="width:102%"
           onchange="getSubDept(this.value,'1','subDeptId');"
           >
</s:select>
</div>