<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<s:url var="chartDataUrl" action="json-chart-data"/>
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
function refresh(div)
{
	if(div=='1stBlock')
	{
		var type=document.getElementById("tabDataType").value;
	
		if(type=='data')
		{
			$.ajax({
			    type : "post",
			    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAssetData.action",
			    success : function(subdeptdata) {
		       $("#"+div).html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
		else if(type=='chart')
		{
			$.ajax({
			    type : "post",
			    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAsetDataGraph.action?mode=1",
			    success : function(subdeptdata) {
		       $("#"+div).html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
	}
	else if (div=='2stBlock') {
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showPerformanceRank.action",
		    success : function(subdeptdata) {
			 $("#"+div).html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else if (div=='3stBlock') {
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showUserRank.action",
		    success : function(subdeptdata) {
			 $("#"+div).html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else if(div=='4stBlock')
	{
		var type=document.getElementById("tabDataType1").value;
		if(type=='data')
		{
			$.ajax({
			    type : "post",
			    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAssetMatrixData.action",
			    success : function(subdeptdata) {
				 $("#"+div).html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
		else if(type=='chart')
		{
			$.ajax({
			    type : "post",
			    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/assetDueMatrixSupportGraph.action?mode=1",
			    success : function(subdeptdata) {
			
			    $("#"+div).html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
		else if(type=='chart1')
		{
			$.ajax({
			    type : "post",
			    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAssetExpireGraph.action?mode=1",
			    success : function(subdeptdata) {
			
			    $("#"+div).html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
	}
}
function showDetails(div)
{
	var conP = "<%=request.getContextPath()%>";
	if(div=='1stBlock')
	{
		var type=document.getElementById("tabDataType").value;
		if(type=='data')
		{
			$.ajax({
			    type : "post",
			    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAssetData.action",
			    success : function(subdeptdata) {
				 $("#dashboardDialog").html(subdeptdata);
			     $("#dashboardDialog").dialog('open');
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
		else if(type=='chart')
		{
			
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/AssetOver2Cloud/Asset/showAsetDataGraph.action?mode=0",
			    success : function(subdeptdata) {
			   
		       $("#dashboardDialog").html(subdeptdata);
		       $("#dashboardDialog").dialog('open');
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
	}
	else if (div=='2stBlock') {
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showPerformanceRank.action",
		    success : function(subdeptdata) {
			 $("#dashboardDialog").html(subdeptdata);
		     $("#dashboardDialog").dialog('open');
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else if (div=='3stBlock') {
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showUserRank.action",
		    success : function(subdeptdata) {
			 $("#dashboardDialog").html(subdeptdata);
		     $("#dashboardDialog").dialog('open');
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else if(div=='4stBlock')
	{
		var type=document.getElementById("tabDataType1").value;
		
		if(type=='data')
		{
			$.ajax({
			    type : "post",
			    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAssetMatrixData.action",
			    success : function(subdeptdata) {
				 $("#dashboardDialog").html(subdeptdata);
			     $("#dashboardDialog").dialog('open');
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
		else if(type=='chart')
		{
			$.ajax({
			    type : "post",
			    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/assetDueMatrixSupportGraph.action?mode=0",
			    success : function(subdeptdata) {
			
		       $("#dashboardDialog").html(subdeptdata);
		       $("#dashboardDialog").dialog('open');
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
		else if(type=='chart1')
		{
			$.ajax({
			    type : "post",
			    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAssetExpireGraph.action?mode=0",
			    success : function(subdeptdata) {
			   
		       $("#dashboardDialog").html(subdeptdata);
		       $("#dashboardDialog").dialog('open');
			},
			   error: function() {
		            alert("error");
		        }
			 });
		}
	}
}
function showDetailsPie(div)
{
	var conP = "<%=request.getContextPath()%>";
	if(div=='1stBlock')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAsetDataGraph.action?mode=1",
		    success : function(subdeptdata) {
			$("#"+div).html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else if (div=='4stBlock') 
		{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/assetDueMatrixSupportGraph.action?mode=1",
		    success : function(subdeptdata) {
			$("#"+div).html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
}

function showDetailsData(div)
{
	var conP = "<%=request.getContextPath()%>";
	if(div=='1stBlock')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAssetData.action",
		    success : function(subdeptdata) {
			$("#"+div).html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else if (div=='4stBlock') {
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAssetMatrixData.action",
		    success : function(subdeptdata) {
			$("#"+div).html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
}

function showDetailsAssetPie(div)
{
	var conP = "<%=request.getContextPath()%>";
	 if (div=='4stBlock') {
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAssetExpireGraph.action?mode=1",
		    success : function(subdeptdata) {
			$("#"+div).html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
}

// for getting Data*************

function showAssetDetails(deptId,dataFor,type)
{
	if(type=='Asset'){
		$("#dataDialog").dialog({width: 790 , height: 400});
		}
	if(type=='Asset' && dataFor=='Allot'){
		$("#dataDialog").dialog({width: 900 , height: 280});
		}
	if(type=='Asset' && dataFor=='Free'){
		$("#dataDialog").dialog({width: 770 , height: 180});
		}
	if(type=='Asset' && dataFor=='onService'){
		$("#dataDialog").dialog({width: 800 , height: 300});
		}
	if(type=='Spare'){
		$("#dataDialog").dialog({width: 1080 , height: 220});
		}
	if(type=='Spare' && dataFor=='Allot'){
		$("#dataDialog").dialog({width: 930 , height: 180});
		}
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAllAssetDetails.action?deptId="+deptId+"&dataFor="+dataFor+"&type="+type+"",
	    success : function(data) 
	    {
	    	$("#dataDialog").dialog('open');
			$("#dataDialog").html(data);
	},
	   error: function() {
	        alert("error");
	    }
	 });
}

function showSuportDetails(deptId,dataFor,type)
{
	if (type=='Support') {
		$("#dataDialog").dialog({width: 1050 , height: 250});
	}
	if (type=='Expire') {
		$("#dataDialog").dialog({width: 650 , height: 200});
	}
		$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAllSupportDetails.action?deptId="+deptId+"&dataFor="+dataFor+"&type="+type+"",
	    success : function(data) 
	    {
	    	$("#dataDialog").dialog('open');
			$("#dataDialog").html(data);
	},
	   error: function() {
	        alert("error");
	    }
	 });
}

function showRankDetails(assetId,depId,type)
{
		$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/AssetOver2Cloud/Asset/showAllrankDetails.action?deptId="+depId+"&assetid="+assetId+"&type="+type+"",
	    success : function(data) 
	    {
	    	$("#dataDialog1").dialog('open');
			$("#dataDialog1").html(data);
	},
	   error: function() {
	        alert("error");
	    }
	 });
}
</script>
</head>
<body>
<sj:dialog 
    	id="dashboardDialog" 
    	autoOpen="false" 
    	modal="true" 
    	width="600"
		height="500"
    	resizable="false"
    	title="Selected View"
    >
</sj:dialog>
<sj:dialog 
    	id="dataDialog" 
    	autoOpen="false" 
    	modal="true" 
    	width="750"
		height="180"
    	resizable="false"
    	title="View"
    >
</sj:dialog>
<sj:dialog 
    	id="dataDialog1" 
    	autoOpen="false" 
    	modal="true" 
    	width="870"
		height="300"
    	resizable="false"
    	title="View"
    >
</sj:dialog>
<div class="middle-content">

<div class="contentdiv-small" style="overflow: hidden;">
<div class="clear"></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('1stBlock')"><img src="images/Refresh.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetails('1stBlock')"><img src="images/images.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetailsData('1stBlock')"><img src="images/data.jpg" width="15" height="15" alt="Get Data" title="Get Data of Graph" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetailsPie('1stBlock')"><img src="images/pie_chart_icon.jpg" width="15" height="15" alt="Get Data" title="Get Pie Chart" /></a></div>
<div style="height:auto; margin-bottom:10px;" id='1stBlock'>
<div style="height:auto; margin-bottom:10px;" ><B>Asset And Spare Score Sheet</B></div>
<s:hidden id='tabDataType' name="tabDataType" value="data"></s:hidden>
<table  align="center" border="0" width="100%" style="border-style:dotted solid;">
<tbody>
 <tr bgcolor="#669966" bordercolor="black" >
	<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Type</b></font></td>
	<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Total</b></font></td>
	<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Alloted</b></font></td>
	<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Free</b></font></td>
	<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>In Service</b></font></td>
 </tr>
 dsdfsf
 <s:iterator id="assetList1"  status="status" value="%{assetForDashboard.assetList}" >
fvvg
<tr style="background-color:#F9F9F9;">
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b>Asset</b></a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showAssetDetails('<s:property value="#assetList1.departId"/>','Total','Asset');" href="#"><s:property value="#assetList1.totalAsset" /></a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showAssetDetails('<s:property value="#assetList1.departId"/>','Allot','Asset');" href="#"><s:property value="#assetList1.totalAllot" /></a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showAssetDetails('<s:property value="#assetList1.departId"/>','Free','Asset');" href="#"><s:property value="#assetList1.totalFree" /></a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showAssetDetails('<s:property value="#assetList1.departId"/>','onService','Asset');" href="#"><s:property value="#assetList1.onService" /></a></font></td>
</tr>
</s:iterator>
<s:iterator id="assetList2"  status="status" value="%{spareForDashboard.spareList}" >

<tr>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b>Spare</b></a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showAssetDetails('<s:property value="#assetList1.departId"/>','Total','Spare');" href="#"><s:property value="#assetList2.totalAsset" /></a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showAssetDetails('<s:property value="#assetList1.departId"/>','Allot','Spare');" href="#"><s:property value="#assetList2.totalAllot" /></a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showAssetDetails('<s:property value="#assetList1.departId"/>','Free','Spare');" href="#"><s:property value="#assetList2.totalFree" /></a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><s:property value="#assetList2.onService" /></a></font></td>
</tr>
</s:iterator>
</tbody>
</table>
</div>
</div>

<div class="contentdiv-small" style="overflow: hidden;" >
<div class="clear"></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('2stBlock')"><img src="images/Refresh.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetails('2stBlock')"><img src="images/images.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div style="height:auto; margin-bottom:10px;" id='2stBlock'>
<div style="height:auto; margin-bottom:10px;" ><B>Asset Type Performance Rank</B></div>

<table align="center" border="0" width="100%" style="border-style:dotted solid;">
                        			
               <tr >
                   <td valign="bottom">
                       <table  align="center" border="1" style="border-style:dotted solid;" width="100%">
                        	  <tr bgcolor="#780000">
                        			<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rank</b></font></td>
	                                <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Asset Type</b></font></td>
	                                <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Score</b></font></td>
                        	  </tr>
                       </table>
                    </td>
               </tr>
               
               <tr>
                   <td valign="top" style="padding-top: 5px" align="center">
                   <marquee onmouseover="this.stop()" onmouseout="this.start()" scrollamount="2" scrolldelay="0" direction="up" >
					   <table align="center" border="0" width="100%" style="border-style:dotted solid;" >                        		
                             <s:iterator id="assetList2"  status="status" value="%{assetRankForDashboard.assetList}" > 
								<tr bordercolor="#ffffff">
                        		<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showRankDetails('<s:property value="#assetList2.assetid"/>','<s:property value="#assetList2.departId"/>','Asset');" href="#"><b><s:property value="#assetList2.type" /></b></a></font></td>
                                <td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showRankDetails('<s:property value="#assetList2.assetid"/>','<s:property value="#assetList2.departId"/>','Asset');" href="#"><s:property value="#assetList2.assetName" /></a></font></td>
                                <td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showRankDetails('<s:property value="#assetList2.assetid"/>','<s:property value="#assetList2.departId"/>','Asset');"href="#"><s:property value="#assetList2.assetScore" /></a></font></td>
                                </tr>
                        	   </s:iterator>
                        </table>
                   </marquee>
                   </td>
               </tr>
</table>
              
</div>
</div>

<div class="contentdiv-small" style="overflow: hidden;" >
<div class="clear"></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('3stBlock')"><img src="images/Refresh.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetails('3stBlock')"><img src="images/images.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div style="height:auto; margin-bottom:10px;" id='3stBlock'>
<div style="height:auto; margin-bottom:10px;" ><B>User Asset Breakdown Rank</B></div>
<table align="center" border="0" width="100%" style="border-style:dotted solid;">
                        			
               <tr>
                   <td valign="bottom">
                       <table align="center" border="1" style="border-style:dotted solid;" width="100%">
                        	  <tr bgcolor="#000000">
                        			<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Rank</b></font></td>
	                                <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>User Name</b></font></td>
	                                <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Score</b></font></td>
                        	  </tr>
                       </table>
                    </td>
               </tr>
               
               <tr>
                   <td valign="top" style="padding-top: 5px" align="center">
                   <marquee onmouseover="this.stop()" onmouseout="this.start()" scrollamount="2" scrolldelay="0" direction="up" >
					   <table align="center" border="0" width="100%" style="border-style:dotted solid;" >                        		
                             <s:iterator id="assetList2"  status="status" value="%{userRankForDashboard.assetList}" > 
								<tr bordercolor="#ffffff">
                        		<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showRankDetails('<s:property value="#assetList2.assetid"/>','<s:property value="#assetList2.departId"/>','User');" href="#"><b><s:property value="#assetList2.type" /></b></a></font></td>
                                <td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showRankDetails('<s:property value="#assetList2.assetid"/>','<s:property value="#assetList2.departId"/>','User');" href="#"><s:property value="#assetList2.assetName" /></a></font></td>
                                <td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showRankDetails('<s:property value="#assetList2.assetid"/>','<s:property value="#assetList2.departId"/>','User');" href="#"><s:property value="#assetList2.assetScore" /></a></font></td>
                                </tr>
                        	   </s:iterator>
                        </table>
                   </marquee>
                   </td>
               </tr>
              </table>
</div>
</div>

<div class="contentdiv-small" style="overflow: hidden;" >
<div class="clear"></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="refresh('4stBlock')"><img src="images/Refresh.jpg" width="15" height="15" alt="Refresh" title="Refresh" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetails('4stBlock')"><img src="images/images.jpg" width="15" height="15" alt="Maximize" title="Maximize" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetailsData('4stBlock')"><img src="images/data.jpg" width="15" height="15" alt="Get Data" title="Get Data of Graph" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetailsPie('4stBlock')"><img src="images/pie_chart_icon.jpg" width="15" height="15" alt="Get Data" title="Get Pie Chart of support" /></a></div>
<div style="float:right; width:auto; padding:0px 4px 0 0;"><a href="#" onclick="showDetailsAssetPie('4stBlock')"><img src="images/pie_chart_icon.jpg" width="15" height="15" alt="Get Data" title="Get Pie Chart of Asset Expire" /></a></div>
<div style="height:auto; margin-bottom:10px;" id='4stBlock'>
<div style="height:auto; margin-bottom:10px;" ><B>Asset Due Action Matrix</B></div>
<s:hidden id='tabDataType1' name="tabDataType1" value="data"></s:hidden>
<!--<table border="1" width="100%" align="center" style="border-style:dotted solid;">
<tbody>
 <tr bgcolor="FF3333">
	<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Type</b></font></td>
	<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Support</b></font></td>
	<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Asset Expire</b></font></td>
	</tr>
	<tr >
	<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#0000FF" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b>7 Days</b></a></font></td>
	</tr>
	<tr >
	<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#0000FF" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b>30 Days</b></a></font></td>
	</tr>
	<tr>
	<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#0000FF" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b>90 Days</b></a></font></td>
	</tr>
	<tr>
	<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#0000FF" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b>180 Days</b></a></font></td>
	</tr>

</tbody>
</table>
-->
<table border="1" width="100%" align="center" >
<tbody>
 <tr bgcolor="FF3333">
	<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>Type</b></font></td>
	<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>7 Days</b></font></td>
	<td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>30 Days</b></font></td>
 <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>90 Days</b></font></td>
 <td align="center" width="12%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" size="2"><b>180 Days</b></font></td>
 </tr>
 
 <s:iterator id="assetList2"  status="status" value="%{supportForDashboard.assetList}" > 
<tr>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#0000FF" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b>Support</b>   </a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showSuportDetails('<s:property value="#assetList2.departId"/>','7Days','Support');" href="#"><s:property value="#assetList2.next7Days" /></a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showSuportDetails('<s:property value="#assetList2.departId"/>','30Days','Support');" href="#"><s:property value="#assetList2.next10Days" /></a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showSuportDetails('<s:property value="#assetList2.departId"/>','90Days','Support');" href="#"><s:property value="#assetList2.next90Days" /></a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showSuportDetails('<s:property value="#assetList2.departId"/>','180Days','Support');" href="#"><s:property value="#assetList2.next180Days" /></a></font></td>
</tr>
</s:iterator>
 <s:iterator id="assetList2"  status="status" value="%{expireForDashboard.assetList}" > 
<tr>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#0000FF" size="2"><a style="cursor: pointer;text-decoration: none;" href="#"><b>Asset Expire</b></a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showSuportDetails('<s:property value="#assetList2.departId"/>','7Days','Expire');" href="#"><s:property value="#assetList2.next7Days" /></a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showSuportDetails('<s:property value="#assetList2.departId"/>','30Days','Expire');" href="#"><s:property value="#assetList2.next10Days" /></a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showSuportDetails('<s:property value="#assetList2.departId"/>','90Days','Expire');" href="#"><s:property value="#assetList2.next90Days" /></a></font></td>
<td align="center" width="12%"><font face="Arial, Helvetica, sans-serif" color="#000000" size="2"><a style="cursor: pointer;text-decoration: none;" onclick="showSuportDetails('<s:property value="#assetList2.departId"/>','180Days','Expire');" href="#"><s:property value="#assetList2.next180Days" /></a></font></td>

</tr>
</s:iterator>
</tbody>
</table>



</div>
</div>

<div class="contentdiv-small">
<div class="clear"></div>
<div style="height:auto; margin-bottom:10px;" ><B>Asset Ticket Status</B></div>

</div>


<div class="contentdiv-small" style="overflow: hidden;">
<div class="clear"></div>
<div style="height:auto; margin-bottom:10px;" ><B>News And Update</B></div>
<!--

    <sjc:chart
        id="chartPie4"
        cssStyle="width: 300px; height: 230px;"
        pie="true"
        pieLabel="true"
        onClickTopics="getChatInfo"
        onHoverTopics="hover"
    >
    <s:iterator value="%{totalSpareGraph}">
	    <s:if test="key=='Remaining Spare'">
		    <sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="green"
		    		/>	
		    		
		 </s:if>
		 <s:else>
		 	<sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="#1112121"/>
		 </s:else>
    	</s:iterator>
    </sjc:chart>



--></div>



</div>
</body>
</html>