<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script>
	function showAssetInfo(dept_subdept_id,value)
	{
		if(value=='Alloted')
		{
			$("#assetDashboardDataDilog").dialog({title: 'Current Alloted Asset To'});
		}
		if(value=='free')
		{
			$("#assetDashboardDataDilog").dialog({title: 'Current Free Asset'});
		}
		$.ajax({
    	    type : "post",
    	    url : "/cloudapp/view/Over2Cloud/AssetOver2Cloud/Asset/createViewPage4Asset.action?value="+value+"&dept_subdept_id="+dept_subdept_id+"&column4=allotmentDetail",
    	    success : function(data) {
    		$("#assetDashboardDataDiv").html(data);
        	$("#assetDashboardDataDilog").dialog('open');
    	},
    	   error: function() {
    	        alert("error");
    	    }
    	 });
		
	}
</script>
</head>
<body>
<div class="page_title"><h1>Asset >> Dashboard</h1></div>
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<div style="height: 300px; overflow: auto;">

	<table border="0" style="border-collapse: collapse;">
	<thead style="font-size: 13px;font-weight: normal;color: #039;">
    	<tr>
        	<th scope="col" style="width: 130px;">Department</th>
            <th scope="col" style="width: 130px;">Pending</th>
            <th scope="col" style="width: 130px;">Snooze</th>
            <th scope="col" style="width: 130px;">High Priority</th>
            <th scope="col" style="width: 130px;">Ignore</th>
            <th scope="col" style="width: 130px;">Resolved</th>
        </tr>
    </thead>
	<tr>
	<td>
		<table>
		<s:iterator value="deptNameList">
		<tr>
			<td style="padding: 8px;">
			<a style="cursor: pointer;text-decoration: none;" href="#"><b><s:property value="%{value}"/></b></a>
			</td>
		</tr>
		</s:iterator>
		</table>
	</td>
	<td>
		<table>
		<s:iterator value="psCount">
		<tr>
			<td align="center" style="padding: 8px;">
			<a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="%{value}"/></b></a>
			</td>
		</tr>
		</s:iterator>
		</table>
	</td>
	
	<td>
		<table>
		<s:iterator value="szsCount">
		<tr>
			<td align="center" style="padding: 8px;">
			<a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="%{value}"/></b></a>
			</td>
		</tr>
		</s:iterator>
		</table>
	</td>
	
	<td>
		<table>
		<s:iterator value="hpsCount">
		<tr>
			<td align="center" style="padding: 8px;">
			<a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="%{value}"/></b></a>
			</td>
		</tr>
		</s:iterator>
		</table>
	</td>
	
	<td>
		<table>
		<s:iterator value="igsCount">
		<tr>
			<td align="center" style="padding: 8px;" >
			<a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="%{value}"/></b></a>
			</td>
		</tr>
		</s:iterator>
		</table>
	</td>
	
	<td>
		<table>
		<s:iterator value="rsCount">
		<tr>
			<td align="center" style="padding: 8px;">
			<a style="cursor: pointer;text-decoration: none;"  href="#"><b><s:property value="%{value}"/></b></a>
			</td>
		</tr>
		</s:iterator>
		</table>
	</td>
	
	</tr>
	</table>
</div>
</div>
</div>
<sj:dialog id="assetDashboardDataDilog" modal="true" effect="slide" autoOpen="false" width="800" title="" loadingText="Please Wait" overlayColor="#fff" overlayOpacity="1.0" position="['center','top']" >
	<div id="assetDashboardDataDiv"></div>
</sj:dialog>
</body>
</html>