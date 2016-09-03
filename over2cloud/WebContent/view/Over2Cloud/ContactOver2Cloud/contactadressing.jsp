<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${! empty( listadressingTextArea ) }">
 <div class="form_menubox">
				 <s:iterator value="listadressingTextArea" status="counter" begin="0" end="0">
				 	 <s:if test="%{mandatory}">
				 <span  class="pIdsaddrss" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				 </s:if>
				 <s:if test="#counter.count%2 != 0">
				  <s:if test="%{mandatory}">
                  <div class="user_form_text"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input"><span class="needed"></span><s:textarea name="%{field_value}" id="%{field_value}"  value="%{default_value}" cssStyle="margin:0px 0px 10px 0px" maxlength="50"  cssClass="form_menu_inputtext" placeholder="Enter Data"/></div>
				 </s:if>
				 <s:else>
				  <div class="user_form_text"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input"><s:textarea name="%{field_value}" id="%{field_value}"  value="%{default_value}" cssStyle="margin:0px 0px 10px 0px" maxlength="50"  cssClass="form_menu_inputtext" placeholder="Enter Data"/></div>
				 </s:else>
				 </s:if>
				 <s:else>
				 <s:if test="%{mandatory}">
				 <div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input"><span class="needed"></span><s:textarea name="%{field_value}" id="%{field_value}"  value="%{default_value}"  cssStyle="margin:0px 0px 10px 0px" maxlength="50"  cssClass="form_menu_inputtext" placeholder="Enter Data"/></div>
				 </s:if>
				 <s:else>
				 <div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input"><s:textarea name="%{field_value}" id="%{field_value}"  value="%{default_value}"  cssStyle="margin:0px 0px 10px 0px" maxlength="50"  cssClass="form_menu_inputtext" placeholder="Enter Data"/></div>
				 </s:else>
				  </s:else>
				 </s:iterator>
				 
                  </div>

</c:if>

<c:if test="${! empty( listadressingconfiguration ) }">
 <div class="form_menubox">
				 <s:iterator value="listadressingconfiguration" status="counter">
				 	 <s:if test="%{mandatory}">
				 <span  class="pIdsaddrss" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				 </s:if>
				 <s:if test="#counter.count%2 != 0">
				   <s:if test="%{mandatory}">
                  <div class="user_form_text"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input"><span class="needed"></span><s:textfield name="%{field_value}" id="%{field_value}" value="%{default_value}" cssStyle="margin:0px 0px 10px 0px" maxlength="50"  cssClass="form_menu_inputtext" placeholder="Enter Data"/></div>
				  </s:if>
				  <s:else>
				  <div class="user_form_text"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input"><s:textfield name="%{field_value}" id="%{field_value}" value="%{default_value}" cssStyle="margin:0px 0px 10px 0px" maxlength="50"  cssClass="form_menu_inputtext" placeholder="Enter Data"/></div>
				  </s:else>
				 </s:if>
				  <s:else>
				  <s:if test="%{mandatory}">
				 <div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input"><span class="needed"></span><s:textfield name="%{field_value}" id="%{field_value}" value="%{default_value}" cssStyle="margin:0px 0px 10px 0px" maxlength="50"  cssClass="form_menu_inputtext" placeholder="Enter Data"/></div>
				  </s:if>
				   <s:else>
				  <div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input"><s:textfield name="%{field_value}" id="%{field_value}" value="%{default_value}" cssStyle="margin:0px 0px 10px 0px" maxlength="50"  cssClass="form_menu_inputtext" placeholder="Enter Data"/></div>
				 </s:else>
				 </s:else>
				 </s:iterator>
				 
                  </div>
</c:if>

<c:if test="${! empty( listadressingTextArea ) }">
 <div class="form_menubox">
				 <s:iterator value="listadressingTextArea" status="counter" begin="1" end="1">
				 	 <s:if test="%{mandatory}">
				 <span  class="pIdsaddrss" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				 </s:if>
				 <s:if test="#counter.count%2 != 0">
				 <s:if test="%{mandatory}">
				 <div class="user_form_text"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input"><span class="needed"></span><s:textarea name="%{field_value}" id="%{field_value}"  value="%{default_value}" cols="60" rows="2" /></div>
				 </s:if>
				 <s:else>
				 <div class="user_form_text"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input"><s:textarea name="%{field_value}" id="%{field_value}"  value="%{default_value}" cols="60" rows="2" /></div>
				 </s:else>
				 </s:if>
				 <s:else>
				 <s:if test="%{mandatory}">
				 <div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input"><span class="needed"></span><s:textarea name="%{field_value}" id="%{field_value}"  value="%{default_value}" cols="60" rows="2"  cssStyle="margin:0px 0px 10px 0px" /></div>
				 </s:if>
				 <s:else>
				 <div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input"><s:textarea name="%{field_value}" id="%{field_value}"  value="%{default_value}" cols="60" rows="2"  cssStyle="margin:0px 0px 10px 0px" /></div>
				 </s:else>
                </s:else>
				 </s:iterator>
                  </div>

</c:if>