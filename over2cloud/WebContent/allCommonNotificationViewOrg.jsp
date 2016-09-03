<%@taglib prefix="s" uri="/struts-tags" %>
<div class="container">

					<div class="sub_container_block"><h1>Organization Notifications, Pending for action</h1></div>
					<s:if test="%{notificationData.size>0}">
					<div class="sub_container_block">
						<ul>
						    <s:iterator value="notificationData" status="counter">
							<li class="commented"><s:property value="taskBrief"/></li>
						    </s:iterator>
						</ul>
					</div>
					</s:if>
</div>