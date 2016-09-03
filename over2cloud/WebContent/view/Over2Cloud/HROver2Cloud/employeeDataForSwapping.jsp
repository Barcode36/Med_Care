<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="form_menubox">
<div class="user_form_text">History Employee:*</div>
<div class="user_form_input">
<s:select 
id="hisEmp"
name="hisEmp"
list="empListData"
headerKey="-1"
headerValue="--Select History Employee--" 
cssClass="form_menu_inputselect"
>
</s:select>
</div>
<div class="user_form_text1">Swap With:*</div>
<div class="user_form_input">
<s:select 
id="withEmp"
name="withEmp"
list="empListData1"
headerKey="-1"
headerValue="--Select Swap With Employee--" 
cssClass="form_menu_inputselect"
>
</s:select>
</div>
</div>