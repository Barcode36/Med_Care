<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="container">

					<div class="sub_container_block"><h1>Your Notifications</h1></div>
					<!-- <div class="sub_container_block"><span class="get_notifications">Get Notifications via:</span> <a href="javascript:void();">Text Message</a><a href="javascript:void();">RSS</a>
					</div> -->
					
					<s:if test="%{notificationData.size>0}">
					<div class="sub_container_block sent_day"><h2>Sent <s:property value="date1"/></h2></div>
					<div class="sub_container_block">
						<ul>
						    <s:iterator value="notificationData" status="counter">
							    <s:if test="%{flag == 'false'}">
								    <s:if test="#counter.count%2 != 0">
										<li class="notify"><s:property value="taskBrief"/></li>
									</s:if>
									<s:else>
										<li class="friends"><s:property value="taskBrief"/></li>
									</s:else>
								</s:if>
								<s:if test="%{flag == 'true'}">
									<li class="notify"><s:property value="taskBrief"/></li>
								</s:if>
								<s:if test="%{flag == 'comment'}">
									<li class="commented"><s:property value="taskBrief"/></li>
								</s:if>
								<s:if test="%{flag == 'like'}">
									<li class="likes"><s:property value="taskBrief"/></li>
								</s:if>
						    </s:iterator>
						</ul>
					</div>
					</s:if>
					<s:if test="%{notificationData1.size>0}">
					<div class="sub_container_block sent_day"><h2>Sent <s:property value="date2"/></h2></div>
					<div class="sub_container_block">
						<ul>
							<s:iterator value="notificationData1" status="counter">
								<s:if test="%{flag == 'false'}">
								    <s:if test="#counter.count%2 != 0">
									<li class="notify"><s:property value="taskBrief"/></li>
									</s:if>
									<s:else>
									<li class="friends"><s:property value="taskBrief"/></li>
									</s:else>
								</s:if>
								<s:if test="%{flag == 'true'}">
									<li class="notify"><s:property value="taskBrief"/></li>
								</s:if>
								<s:if test="%{flag == 'comment'}">
									<li class="commented"><s:property value="taskBrief"/></li>
								</s:if>
								<s:if test="%{flag == 'like'}">
									<li class="likes"><s:property value="taskBrief"/></li>
								</s:if>
						    </s:iterator>
						</ul>
					</div>
					</s:if>
					<s:if test="%{notificationData2.size>0}">
					<div class="sub_container_block sent_day"><h2>Sent <s:property value="date3"/></h2></div>
					<div class="sub_container_block">
						<ul>
							<s:iterator value="notificationData2" status="counter">
								<s:if test="%{flag == 'false'}">
								    <s:if test="#counter.count%2 != 0">
										<li class="notify"><s:property value="taskBrief"/></li>
										</s:if>
										<s:else>
										<li class="friends"><s:property value="taskBrief"/></li>
										</s:else>
									</s:if>
								<s:if test="%{flag == 'true'}">
									<li class="notify"><s:property value="taskBrief"/></li>
								</s:if>
								<s:if test="%{flag == 'comment'}">
									<li class="commented"><s:property value="taskBrief"/></li>
								</s:if>
								<s:if test="%{flag == 'like'}">
									<li class="likes"><s:property value="taskBrief"/></li>
								</s:if>
						    </s:iterator>
						</ul>
					</div>
					</s:if>
</div>