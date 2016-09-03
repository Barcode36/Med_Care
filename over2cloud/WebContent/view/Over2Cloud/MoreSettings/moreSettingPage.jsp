<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>More Settings</title>
<style type="text/css">
.Head{
letter-spacing: 2px;
}
</style>
 

<style>

.selected{
   box-shadow:0px 12px 22px 1px #333;

}</style>

<script type="text/javascript">
function fromEmail()
{
	$("#moreSettingDiv").html("<br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/hr/beforeEmailSettingViewHeader.action",
	    success : function(subdeptdata) {
	       $("#"+"moreSettingDiv").html(subdeptdata);
	},
	   error: function() {
	            alert("error");
	        }
	 });
}

function reports()
{
	$("#moreSettingDiv").html("<br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Daily_Report/beforeDailyReportConfigViewHeader.action?pageType=SC",
	    success : function(subdeptdata) {
       $("#"+"moreSettingDiv").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function loginControl()
{
	$("#moreSettingDiv").html("<br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Daily_Report/loginViewHeader.action",
	    success : function(subdeptdata) {
       $("#"+"moreSettingDiv").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function holiday_listView()
{
	$("#moreSettingDiv").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/leaveManagement/beforeHolidayListView1.action",
	    success : function(subdeptdata) {
       $("#"+"moreSettingDiv").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function highLight(highlight,dehighlight1,dehighlight2)
{
	   $("#"+dehighlight1).removeClass('selected');
	   $("#"+dehighlight2).removeClass('selected');
	    $("#"+highlight).addClass('selected');  
	}

</script>
</head>
<body>
					<table width="100%" height="100%">
						<tr>
							<td style="  border-right: 1px solid rgb(195, 184, 184);" width="15%">
								<table width="100%" height="100%">
									<tr>
										<td align="center"><a href="#" onclick="fromEmail();"><img id="fromemail" onclick="highLight('fromemail','summary','login');"   alt="From Email" src="images/moresetting/from-email.png" width="40px" height="40px"></a></td>
									</tr>
									<tr>
										<td align="center" class="Head">From Email</td>
									</tr>
									<tr>
										<td align="center" class="Head"><a href="#" onclick="reports();"><img id="summary" onclick="highLight('summary','fromemail','login');"  alt="Summary Report" src="images/moresetting/reports.png" width="40px" height="40px"></a></td>
									</tr>
									<tr>
										<td align="center" class="Head">Summary Report</td>
									</tr>
									<tr>	
										<td align="center" class="Head"><a href="#" onclick=" loginControl();"><img id="login" onclick="highLight('login','summary','fromemail');"  alt="Login Control" src="images/moresetting/login-icon-new.png" width="40px" height="40px"></a></td>
									</tr>
									<tr>
										<td align="center" class="Head"> Login Management</td>
									</tr>
									<!-- <tr>	
										<td align="center" class="Head"><a href="#" onclick="holiday_listView();"><img id="holiday" onclick="highLight('holiday','summary','fromemail','login');" alt="Holiday Calender" src="images/moresetting/calendar-icon-new.png" width="40px" height="40px"></a></td>
									</tr>
									<tr>
										<td align="center" class="Head">Holiday Calendar</td>
									</tr> -->
									
								</table>
							
							</td>
							<td align="center"><div id="moreSettingDiv"></div></td>
							
						</tr>
					
					
				</table>
</body>
</html>