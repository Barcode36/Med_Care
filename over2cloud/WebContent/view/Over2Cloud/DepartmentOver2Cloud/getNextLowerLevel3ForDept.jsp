<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="inputmain">
<div class="user_form_text"><s:property value="%{level3LabelName}"/>:</div>
<div class="user_form_input inputarea">
<span class="needed"></span>
                  <s:select 
                              id="level3org"
                              name="level3org" 
                              list="level3"
                              headerKey="-1"
                              headerValue="--Select Level--" 
                              cssClass="textField"
                              cssStyle="width:130%"
                              onchange="getNextLowerLevel4ForDept(this.value,'level4Div');"
                              >
                  </s:select>
                  <div id="errorlevel3org" class="errordiv"></div>
                  </div>
</div>