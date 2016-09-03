<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
	<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
 <script type='text/javascript' src='js/jquery.colorbox.js'></script>
    <script type='text/javascript' charset='utf-8' src='js/popbox.js'></script>
    <link href="css/colorbox.css" rel="stylesheet" type="text/css" media="all" />
</head>
<body>

<s:url  namespace="/view/Over2Cloud/CommunicationOver2Cloud/template" action="gettemplatemsgcontaint.action" var="urldocumentTag" escapeAmp="true">

</s:url>
<form name="showcvdetails" method="post">
    <div style="width: 98%; padding: 0 1% 0 1%;" id="idForCV" class="idForCVpadding"><div class="listviewBorder"><div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
   <div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 

<span class="normalDropDown">
  
   <input type="submit" name="Submit" value="" class="button" style="float:left" /><!--END REGISTER BUTTON-->
</span> 
</td></tr></tbody></table>

</td><td class="alignright printTitle"><a href="javascript:;"></a></td>   


</tr></tbody></table></div></div>

 <table id="listViewTable" class="listview" border="0" cellpadding="0" cellspacing="0" width="100%">
<tbody ><tr class="tableHeadBg" >
<td width="10"> <div class="indIcon" style="visibility:hidden; width:23px;">
<a class="link" style="padding-left:6px;" href="javascript:void(0);">
</a></div> </td><td width="10"><input name="allcheck" onClick="selectAll()" type="checkbox"></td> 
<td class="tableHead"> 
<a href="javascript:;">Id
</a>
 </td>
<td class="tableHead"> 
<a href="javascript:;">Use Template
</a>
 </td>
<td class="tableHead"> 
<a href="javascript:;">Template Name
</a>
 </td>

 <td class="tableHead"> 
<a href="javascript:;">
</a>
 </td>
<td class="tableHead"> 
<a href="javascript:;">
</a>
 </td>
<td class="alignright">
</td>
</tr>

<tbody id="effect" class="ui-widget-content ui-corner-all" style="display:none;">
<tr  width="100%" style="display: table-row; background-color: rgb(243, 243, 243);" height="35"><td width="1%"> </td><td width="1%"> 
</td>

<td class="searchbg">&nbsp;</td></tr><tr style="display: table-row; background-color: rgb(243, 243, 243);"><td width="1%"> </td><td width="1%"> 
</td><td class="searchbg" valign="top"> 
<input id="advSearch_text_1" class="textField" style="width:90%; height:20px;" name="advSearch_text_1" onKeyDown="handleButton('button_AdvanceSearchGO',event)" maxlength="250" type="text"></td>

<td class="searchbg" valign="top"> 
<input id="advSearch_text_2" class="textField" style="width:90%; height:20px;" name="advSearch_text_2" maxlength="250" type="text"></td>

<td class="searchbg" valign="top"> 
<input id="advSearch_text_3" class="textField" style="width:90%; height:20px;" name="advSearch_text_3"  maxlength="250" type="text"></td>

<td class="searchbg" valign="top"> 
<input id="advSearch_text_4" class="textField" style="width:90%; height:20px;" name="advSearch_text_4"  maxlength="250" type="text"></td>

<td style="padding-bottom:5px;" class="alignleft searchbg" valign="top"><input class="btn" value="Search" name="button_AdvanceSearchGO"  type="button">
</td></tr><tbody > </tbody>


<tbody id="lvTred">

 <s:iterator value="viewtemplateContents" status="counter" var="variable">
<tr class="tdout" id="">
<td width="10"> <div style="visibility: hidden;" id="" class="indIcon" align="center"></div></td>
<td class="lvCB"></td> 

<td><s:property value="id"></s:property>
</td>
<td style="cursor: pointer;color: ">
<s:a cssClass="booking"  href="%{urldocumentTag}?id=%{id}+&mobileNo=%{#parameters.mobileNo}+&hours=%{#parameters.hours}+&date=%{#parameters.date}+&dates=%{#parameters.dates}+&day=%{#parameters.day}" id="%{id}" theme="simple">
<s:property value="template_id"></s:property></s:a>
</td>
<td>
<s:property value="template"></s:property>
</td>
</tr>
</s:iterator>
 </tbody></table>
<div class="listviewBorder123">
<div class="listviewButtonLayer">
<table width="100%"><tbody><tr><td id="lvBottomButtonTD">  </td>
<td> <div class="alignright">
</div>
</td>
</tr>
</tbody>
</table>
</div>
</div>
</div>
<br>
<table id="alphaRefTable" border="0" cellpadding="0" cellspacing="0" width="100%" ><tbody><tr><td><span class="t_now" id="cachingspanid" style="display:none"><span id="fetch_msg">Data last fetched on&nbsp;: </span><span id="datafetchdate">Sat, 14 Sep, 2013, 11:32:07 AM</span>&nbsp;&nbsp;
<a class="link" href="javascript:;"></a> </span></td></tr><tr><td>
 </td></tr></tbody></table>

</div></form>


</body>
<script>
			$(document).ready(function(){
				//Examples of how to assign the ColorBox event to elements
				$(".pg").colorbox({rel:'pg', slideshow:true});
				$(".video").colorbox({rel:'video', transition:"elastic",});
				$(".google-map").colorbox({rel:'google-map', transition:"elastic",});
				$(".booking").colorbox({rel:'booking', transition:"elastic",});
				$(".share").colorbox({rel:'share', transition:"elastic",});
				$(".iframe").colorbox({iframe:true, width:"40%", height:"40%"});
								
			});
</script>
</html>