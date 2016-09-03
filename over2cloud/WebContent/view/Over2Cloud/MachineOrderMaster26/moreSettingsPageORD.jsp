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
<script type="text/javascript">
autoCartSet();
function autoCartSet()
{
	$("#moreSettingDiv").html("<br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/MachineOrder/autoCartHeader.action",
	    success : function(subdeptdata) {
       $("#"+"moreSettingDiv").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

</script>
</head>
<body>
	<table width="100%" height="100%">
		<tr>
			<td style="  border-right: 1px solid rgb(195, 184, 184);" width="15%">
				<table width="100%" height="40%">
					<tr  >
						<td  align="center"  ><a href="#" onclick="autoCartSet();"><img  alt="Auto Cart Allot" src="images/cartSettingsicon.png" width="60px" height="60px"></a></td>
					</tr>
					<tr >
						<td align="center"  >Auto Cart Settings</td>
					</tr>
					<!-- <tr>
						<td align="center" class="Head"><a href="#"><img alt="Lodge Feedback" src="images/moresetting/feedback_icon.jpg" width="60px" height="60px"></a></td>
					</tr>
					<tr>
						<td align="center" class="Head">Lodge Feedback</td>
					</tr> -->
				</table>
			
			</td>
			<td align="center"><div id="moreSettingDiv"></div></td>
		</tr>
    </table>
</body>
</html>