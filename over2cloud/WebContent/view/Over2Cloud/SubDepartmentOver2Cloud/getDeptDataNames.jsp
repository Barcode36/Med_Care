<%@ taglib prefix="s" uri="/struts-tags" %>
<div id="deptID" >
<s:select 
           id="deptname"
           name="deptname" 
           list="deptList"
           headerKey="-1"
           headerValue="Select Department" 
           cssClass="select"
           cssStyle="width:82%"
           onchange="getSubDept22222222222(this.value,'subDept');"
           >
</s:select>
</div>