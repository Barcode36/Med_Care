<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="notification_content">
<s:iterator value="notificationData" status="counter">
<s:if test="#counter.count%2 != 0">
<p><s:property value="taskBrief"/></p>
</s:if>
<s:else>
<p class="alternate_note"><s:property value="taskBrief"/></p>
</s:else>
</s:iterator>  
</div>