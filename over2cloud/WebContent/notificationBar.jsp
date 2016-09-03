<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%
String userTpe=(String)session.getAttribute("userTpe");
%>
<html>
<head>
<script type="text/javascript">

function viewAllFeedData(moduleFalg,status,via_from,limitFlag)
{
	$.ajax({
	    type : "post",
	    url : "allFeedbackView.action?moduleFlagVar="+moduleFalg+"&status="+status+"&viaFrom="+via_from+"&limitFlag="+limitFlag,
	    success : function(subdeptdata11) {
       $("#"+"data_part").html(subdeptdata11);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function viewSelectData4Feedback(moduleFalg,divID,ajaxImage,status,via_from,limitFlag)
{
	document.getElementById(ajaxImage.id).style.display="block";
	$.ajax({
	    type : "post",
	    url : "selectData4Feedback.action?moduleFlagVar="+moduleFalg+"&status="+status+"&viaFrom="+via_from+"&limitFlag="+limitFlag,
	    success : function(feeddata) {
		document.getElementById(ajaxImage.id).style.display="none";
      $("#"+divID.id).html(feeddata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

</script>
</head>
<body>
<div class="notification">
					   <ul>
						<li>
						<a href="javascript:void();" id="calling" onmouseover="viewSelectData4Feedback(1,viaCallData,viacallAjax,'Pending','Call',1);">
					    <span>
							<s:if test="viaCall_Count!=0"><s:property value="%{viaCall_Count}"/></s:if>
						</span>
						</a>
						<ul>
							 <li>
								<div class="notification_detailed">
									 <div class="notifications_pointer" style=" text-indent:47px;"><img src="images/arrow_sub.png" width="12" height="7" alt="" title="" /></div>
									 <div id="viacallAjax" style="display: none;">
											     <br><center><img id="indicator2" src="images/notificationloader.gif" height="2%" width="5%"/></center>
									 </div>
											
									 <!-- ajax will draw data here of a notification -->
									 <div id="viaCallData"></div>
									 <div class="notifications_all"><a href="javascript:void();" onclick="viewAllFeedData(1,'Pending','Call',0);">Total View Call Feedback</a></div>
								</div>
							 </li>
						</ul>
					    </li>
					    <li><a href="javascript:void();" id="comp" onmouseover="viewSelectData4Feedback(2,viaOnlineData,viaonlineAjax,'Pending','Online',1);">
						    <span>
								<s:if test="viaOnline_Count!=0"><s:property value="%{viaOnline_Count}"/></s:if>
							</span>
						</a>
						<ul>
							 <li>
								<div class="notification_detailed">
									 <div class="notifications_pointer" style=" text-indent:47px;"><img src="images/arrow_sub.png" width="12" height="7" alt="" title="" /></div>
									 <div id="viaonlineAjax" style="display: none;">
											     <br><center><img id="indicator2" src="images/notificationloader.gif" height="2%" width="5%"/></center>
									 </div>
											
									 <!-- ajax will draw data here of a notification -->
									 <div id="viaOnlineData"></div>
									 <div class="notifications_all"><a href="javascript:void();" onclick="viewAllFeedData(2,'Pending','Online',0);">Total View Online Feedback</a></div>
								</div>
							 </li>
						</ul>
					    </li>
					    
					   </ul>
					</div>
</body>
</html>