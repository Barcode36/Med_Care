<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="inputmain">
<div class="user_form_text"><s:property value="%{level5LabelName}"/>:</div>
<div class="user_form_input inputarea">
<span class="needed"></span>
                  <s:select 
                              id="level5org"
                              name="level5org" 
                              list="level5"
                              headerKey="-1"
                              headerValue="--Select Level--" 
                              cssClass="textField"
                              cssStyle="width:130%"
                              onchange="getDeptData(this.value,'deptID');"
                              >
                  </s:select>
                   <div id="errorlevel5org" class="errordiv"></div>
                  </div>
                  </div>