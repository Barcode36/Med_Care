<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="inputmain">
<div class="user_form_text1"><s:property value="%{level4LabelName}"/>:</div>
<div class="user_form_input  inputarea">
<span class="needed"></span>
                  <s:select 
                              id="level4org"
                              name="level4org" 
                              list="level4"
                              headerKey="-1"
                              headerValue="--Select Level--" 
                              cssClass="textField"
                              cssStyle="width:130%"
                              onchange="getNextLowerLevel5ForDept(this.value,'level5Div');"
                              >
                  </s:select>
                   <div id="errorlevel4org" class="errordiv"></div>
                  </div>
                  </div>