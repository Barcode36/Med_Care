<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<sj:accordion id="accordion" active="false"  onmouseover="true" autoHeight="false" collapsible="true" cssStyle="width:80%height:80%;">
     <s:iterator value="userRightsList">
     <sj:accordionItem title="%{module}" id="%{module}ID" name="%{module}ID" onclick="getModuleName(%{module}ID);">  
          <div id="<s:property value="module"/>" >
          <div class="colorbox">
          <table style="width:80%;"  class="clearfix" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr style="background-color: black; color: white;">
                     <td width="200" align="center" valign="middle" background="<s:url value="/images/colorbox.gif"/>" class="trborder"><strong>Modules</strong></td>
                     <td width="200" align="center" valign="middle" background="<s:url value="/images/colorbox.gif"/>" class="trborder"><strong>Add</strong></td>
                     <td width="200" align="center" valign="middle" background="<s:url value="/images/colorbox.gif"/>" class="trborder"><strong>View</strong></td>
                     <td width="200" align="center" valign="middle" background="<s:url value="/images/colorbox.gif"/>" class="trborder"><strong>Modify</strong></td>
                     <td width="200" align="center" valign="middle" background="<s:url value="/images/colorbox.gif"/>" class="trborder"><strong>Delete</strong></td>
                </tr>
                  <tr>
                     <td width="200" align="center" valign="middle" background="<s:url value="/images/colorbox.gif"/>" class="trborder"><strong>Select All</strong></td>
                     <td width="200" align="center" valign="middle" background="<s:url value="/images/colorbox.gif"/>" class="trborder"><strong><s:checkbox theme="simple" disabled="false" class="%{module}ID_ADD" name="ADD" id="%{module}ID_ADD" onclick="checkFields('ADD','%{module}ID');" /></strong></td>
                     <td width="200" align="center" valign="middle" background="<s:url value="/images/colorbox.gif"/>" class="trborder"><strong><s:checkbox theme="simple" disabled="false"  name="VIEW" id="%{module}ID_VIEW" onclick="checkFields('VIEW','%{module}ID');"/></strong></td>
                     <td width="200" align="center" valign="middle" background="<s:url value="/images/colorbox.gif"/>" class="trborder"><strong><s:checkbox theme="simple" disabled="false"  name="MODIFY" id="%{module}ID_MODIFY" onclick="checkFields('MODIFY','%{module}ID');"/></strong></td>
                     <td width="200" align="center" valign="middle" background="<s:url value="/images/colorbox.gif"/>" class="trborder"><strong><s:checkbox theme="simple" disabled="false"  name="DELETE"  id="%{module}ID_DELETE" onclick="checkFields('DELETE','%{module}ID');"/></strong></td>
                </tr>
                <s:iterator value="list" status="st">
                    <s:if test="%{#st.odd}">
                        <tr style=" border-bottom:1px solid #d3e5ed; background-color: #FAF6F6;">
                    </s:if>
                    <s:else>
                        <tr style=" border-bottom:1px solid #d3e5ed;">
                    </s:else>
                    <s:property value="ADD%{#st.odd}"/>
                           <td width="200" align="left" valign="middle" background="<s:url value="/images/colorbox.gif"/>" class="trborder"><strong><s:property value="rightsHeading"/> </strong></td>
                         <td width="200" align="center" valign="middle" background="<s:url value="/images/colorbox.gif"/>" class="trborder">
                           
                       <span class="rig" style="display: none; " ><s:property value="module"/>,<s:property value="%{rightsName}"/>#</span>    
                           
                             <s:if test="%{add}"><s:checkbox theme="simple" class="%{module}ID_ADDCHILD" disabled="false" id="%{rightsName}_ADD"  name="%{rightsName}_ADD" fieldValue="%{rightsName}_ADD" /></s:if>
                             <s:elseif test="%{add == 'HAVING'}"><s:checkbox theme="simple" class="%{module}ID_ADDCHILD" disabled="false" value="true" name="%{rightsName}_ADD" fieldValue="%{rightsName}_ADD" id="%{rightsName}_ADD"/></s:elseif>
                             <s:else><s:checkbox theme="simple" disabled="true" class="%{module}ID_ADDCHILD"  name="%{rightsName}_ADD" id="%{rightsName}_ADD"/></s:else>
                         </td>
                         <td width="200" align="center" valign="middle" background="<s:url value="/images/colorbox.gif"/>" class="trborder">
                              <s:if test="%{view}"><s:checkbox theme="simple" disabled="false"  name="%{rightsName}_VIEW" id="%{rightsName}_VIEW" fieldValue="%{rightsName}_VIEW"  /></s:if>
                              <s:elseif test="%{view == 'HAVING'}"><s:checkbox theme="simple" disabled="false" value="true" id="%{rightsName}_VIEW" name="%{rightsName}_VIEW" fieldValue="%{rightsName}_VIEW" /></s:elseif>
                             <s:else><s:checkbox theme="simple" disabled="true" id="%{rightsName}_VIEW"  name="%{rightsName}_VIEW" /></s:else>
                         </td>
                         <td width="200" align="center" valign="middle" background="<s:url value="/images/colorbox.gif"/>" class="trborder">
                              <s:if test="%{modify}"><s:checkbox theme="simple" disabled="false"  name="%{rightsName}_MODIFY" fieldValue="%{rightsName}_MODIFY" id="%{rightsName}_MODIFY"/></s:if>
                              <s:elseif test="%{modify == 'HAVING'}"><s:checkbox theme="simple" disabled="false" value="true" name="%{rightsName}_MODIFY" id="%{rightsName}_MODIFY" fieldValue="%{rightsName}_MODIFY" /></s:elseif>
                             <s:else><s:checkbox theme="simple" disabled="true"  name="%{rightsName}_MODIFY" id="%{rightsName}_MODIFY"/></s:else>
                         </td>
                         <td width="200" align="center" valign="middle" background="<s:url value="/images/colorbox.gif"/>" class="trborder">
                              <s:if test="%{delete}"><s:checkbox theme="simple" disabled="false"  name="%{rightsName}_DELETE" fieldValue="%{rightsName}_DELETE" id="%{rightsName}_DELETE"/></s:if>
                              <s:elseif test="%{delete == 'HAVING'}"><s:checkbox theme="simple" disabled="false" value="true" name="%{rightsName}_DELETE" fieldValue="%{rightsName}_DELETE" id="%{rightsName}_DELETE"/></s:elseif>
                             <s:else><s:checkbox theme="simple" disabled="true"  name="%{rightsName}_DELETE" id="%{rightsName}_DELETE"/></s:else>
                         
                         </td>
                </s:iterator>
                
               </table>
               </div>
               </div> 
           </sj:accordionItem>
     </s:iterator>
     </sj:accordion>
   <br>