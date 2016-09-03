<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/js.js"/>"></script>
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/master.js"></script>

<script type="text/javascript">
$.subscribe('complete', function(event,data)
    {
        setTimeout(function(){ $("#foldeffect").fadeIn(); }, 10);
        setTimeout(function(){ $("#foldeffect").fadeOut(); }, 4000);
    });
</script>
</head>
<body>
<div class="list-icon"><div class="clear"></div><div class="head">
Signup Details Process</div></div>
<div class="container_block"><div style=" float:left; padding:20px 1%; width:98%;">

<div class="page_form">
<div class="clear"></div>
<div style="float: right; margin: 0 0px 10px 0;">
<input class="btn createNewBtn" value="Add New Details" type="button" onclick="addNewSubDept()">
<input class="btn importNewBtn" value="Import Sub Dept" type="button">  
<span class="normalDropDown"> 
 </span>
 </div>
 <div style="float: left; margin: 0 0px 10px 0;">
 <div class="floatL" onmouseout="hideCVoptionMenu()" onmouseover="showCVoptionMenu()"><div class="customDropdown floatL"><select style="display: none;" name="cvid" id="cvid" class="select chzn-done">
<optgroup label="Predefined Views" class="st" style="border:none">

<option value="a" selected="selected">
All Open Leads
</option>
<option value="b" class="st">
My Leads
</option>
<option value="c" class="st">
Todays Leads
</option>
<option value="d" class="st">
Converted Leads
</option>
<option value="e" class="st">
Unread Leads
</option>
<option value="f" class="st">
My Converted Leads
</option>
<option value="g" class="st">
Mailing Labels
</option>
</optgroup><optgroup label="Recent Views" class="st" style="border:none">

<option value="h" class="st">
Recently Created Leads
</option>
<option value="i" class="st">
Recently Modified Leads
</option></optgroup></select><div style="width: 178px;" class="chzn-container chzn-container-single chzn-container-single-nosearch"><a href="javascript:void(0)" class="chzn-single" tabindex="-1"><span>All Open Leads</span><div><b></b></div></a></div></div><span id="cvIcons" class="floatL" style="display:block; padding: 9px 0px 0px 4px;"> 

 </span></div>

<span class="normalDropDown"> 

 </span>
 </div>
 <div class="clear"></div>
<div class="listviewBorder">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 

<input id="sendMailButton" class="button"  value="Send Mail" type="button">
<input name="cvDelete" id="cvDelete" class="button" value="Edit" type="button">	
<input name="cvDelete" id="cvDelete" class="button" value="Delete" type="button">		
<span class="normalDropDown"><input class="dbtn" name="moreAct" id="moreAct" value="More Actions" type="button"></span> 
</td></tr></tbody></table>
<img src="images/spacer_002.gif" class="refbtn"></td><td class="alignright printTitle"><a href="javascript:;"><img src="images/spacer_002.gif" class="sheetIconN" title="Sheet" border="0" height="26" width="33"></a><a href="javascript:;"><img src="images/spacer_002.gif" class="printIconN" title="Print View" border="0" height="26" width="29"></a></td>   
</tr>
</tbody>
</table>
</div>
</div>
<s:form  id="AddAllSingleSpace_Url" action="addAllSingleSpace"  theme="css_xhtml">
<div style="width: 1248px;" id="copyListviewHeaderDiv"><div class="floatL" style="width: 43px; height: 29px; padding-left: 10px;"> <div class="indIcon" style="visibility:hidden; width:23px;"><a class="link" style="padding-left:6px;" href="javascript:void(0);"><img src="images/spacer_002.gif" border="0" height="1" width="1"></a></div> 
</div><div class="floatL" style="width: 43px; height: 29px;"><input name="fixedSelectAll" type="checkbox"></div><div class="floatL" style="width: 266px; height: 29px;"> 
<a href="javascript:;">Lead Name
</a>
 </div><div class="floatL" style="width: 186px; height: 29px;"> 
<a href="javascript:;">Company
</a>
 </div><div class="floatL" style="width: 235px; height: 29px;"> 
<a href="javascript:;">Phone
</a>
 </div><div class="floatL" style="width: 342px; height: 29px;"> 
<a href="javascript:;">Email
</a>
 </div><div class="floatL" style="width: 111px; height: 29px;"></div></div>
 <table id="listViewTable" class="listview" border="0" cellpadding="0" cellspacing="0" width="100%">
<tbody >
<tr class="tableHeadBg" >
<td width="10">
<div class="indIcon" style="visibility:hidden; width:23px;"><a class="link" style="padding-left:6px;" href="javascript:void(0);"><img src="images/spacer_002.gif" border="0" height="1" width="1"></a></div> </td><td width="10"><input name="allcheck" onClick="selectAll()" type="checkbox"></td> 
<td class="tableHead"> 
<a href="javascript:;">Sr.No: 
</a>
</td>
<td class="tableHead"> 
<a href="javascript:;">Organization
</a>
</td>

<td class="tableHead"> 
<a href="javascript:;">Registered User
</a>
 </td>

<td class="tableHead"> 
<a href="javascript:;">Activate Link
</a>
 </td>
<td class="alignright"><a href="#" id="button" ><img src="images/spacer_002.gif"  alt="Search" width="20" height="20" border="0" align="absmiddle" class="advancedsearch floatR" title="Search"></a></td></tr>

<tbody id="effect" class="ui-widget-content ui-corner-all" style="display:none;">
<tr  width="100%" style="display: table-row; background-color: rgb(243, 243, 243);" height="35"><td width="1%"> </td><td width="1%"> 
</td><td class="searchbg"> 
<label for="select"></label>
<select name="select" id="select" class="select">
<option selected="selected" value="contains">contains</option>
<option value="doesn't contain">doesn't contain</option>
<option value="is">is</option>
<option value="isn't">isn't</option>
<option value="starts with">starts with</option>
<option value="ends with">ends with</option>
<option value="Is empty">is empty</option>
<option value="Is not empty">is not empty</option></select>

</td>

<td class="searchbg"> 
<label for="select"></label>
<select name="select" id="select" class="select">
<option selected="selected" value="contains">contains</option>
<option value="doesn't contain">doesn't contain</option>
<option value="is">is</option>
<option value="isn't">isn't</option>
<option value="starts with">starts with</option>
<option value="ends with">ends with</option>
<option value="Is empty">is empty</option>
<option value="Is not empty">is not empty</option></select>

</td>

<td class="searchbg"> 
<label for="select"></label>
<select name="select" id="select" class="select">
<option selected="selected" value="contains">contains</option>
<option value="doesn't contain">doesn't contain</option>
<option value="is">is</option>
<option value="isn't">isn't</option>
<option value="starts with">starts with</option>
<option value="ends with">ends with</option>
<option value="Is empty">is empty</option>
<option value="Is not empty">is not empty</option></select>

</td>

<td class="searchbg"> 
<label for="select"></label>
<select name="select" id="select" class="select">
<option selected="selected" value="contains">contains</option>
<option value="doesn't contain">doesn't contain</option>
<option value="is">is</option>
<option value="isn't">isn't</option>
<option value="starts with">starts with</option>
<option value="ends with">ends with</option>
<option value="Is empty">is empty</option>
<option value="Is not empty">is not empty</option></select>

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
<s:iterator value="SignupMailOb1" id="Signprocess" status="counter">  
<s:set name="id" value="%{uRL}" />
<s:if test="#counter.count%2 != 0">
<tr class="tdout" id="">
<td width="10"> <div style="visibility: hidden;" id="" class="indIcon" align="center"></div></td>
<td class="lvCB"><input name="chk" value="" type="checkbox"></td> 
<td><s:property value="#counter.count" /></td>
<td><s:property value="orgnizationName" /></td>
<td><s:property value="regUserName" /></td> 
<td><font color="red"><a href="${id}" target="_blank" >Click To Activate</a></font></td>
<td>
</td>
</tr>
</s:if>
<s:else>
<tr class="tdout" id="">
<td width="10"> <div style="visibility: hidden;" id="" class="indIcon" align="center"></div></td>
<td class="lvCB"><input name="chk" value="" type="checkbox"></td> 
<td><s:property value="#counter.count" /></td>
<td><s:property value="orgnizationName" /></td>
<td><s:property value="regUserName" /></td> 
<td><font color="red"><a href="${id}" target="_blank" >Click To Activate</a></font></td>
<td>
</td>
</tr>
</s:else>
</s:iterator>
 </tbody>
 </table>
<div class="listviewBorder123">
<div class="listviewButtonLayer">
<table width="100%"><tbody><tr><td id="lvBottomButtonTD">  </td><td> <div class="alignright">
  <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tbody><tr><td> <table align="right" border="0" cellpadding="0" cellspacing="1" height="25"><tbody><tr><td align="center" nowrap="nowrap"> <div class="bodyText"><select name="currentOption" id="currentOption" >
<option value="10" selected="selected">
10 &nbsp; Records per page</option>
<option value="20">
20 &nbsp; Records per page</option>
<option value="30">
30 &nbsp; Records per page</option>
<option value="40">
40 &nbsp; Records per page</option>
<option value="50">
50 &nbsp; Records per page</option></select></div></td>
<td align="right" nowrap="nowrap"> <div id="tcDiv">&nbsp;&nbsp;<nobr></nobr>&nbsp;&nbsp;</div></td></tr></tbody></table></td>
<td width="110"> <table border="0" cellpadding="0" cellspacing="1" height="25"><tbody><tr><td class="viewbg" align="right"> 

<div class="previousDisabled"></div>

 </td><td id="displayCount" nowrap="nowrap"> <b>1</b> to <b>

1

</b> </td><td>  
<div class="nextDisabled"></div></td></tr></tbody></table></td></tr></tbody></table>
</div>
</td></tr>
</tbody>
</table>
</div>
</div>
<br>
<table id="alphaRefTable" border="0" cellpadding="0" cellspacing="0" width="100%" ><tbody><tr><td><span class="t_now" id="cachingspanid" style="display:none"><span id="fetch_msg">Data last fetched on&nbsp;: </span><span id="datafetchdate">Sat, 14 Sep, 2013, 11:32:07 AM</span>&nbsp;&nbsp;
<a class="link" href="javascript:;"></a> </span></td></tr><tr><td>
<table id="alphaTable" border="0" cellpadding="0" cellspacing="3"><tbody><tr><td></td> 
<input name="searchletter" type="hidden"><td class="alphaBgSel" style="width:26" nowrap="nowrap"><div class="bodyText" align="center">All</div></td> 
<td class="alphaBg"><div align="center"><a id="alphaSearch_A" class="link" onClick="alphaSearch('A','recordHistory')" href="javascript:alphaSearch('A','submit')">A</a></div></td>
<td class="alphaBg"><div align="center"><a id="alphaSearch_B" class="link" onClick="alphaSearch('B','recordHistory')" href="javascript:alphaSearch('B','submit')">B</a></div></td>
<td class="alphaBg"><div align="center"><a id="alphaSearch_C" class="link" onClick="alphaSearch('C','recordHistory')" href="javascript:alphaSearch('C','submit')">C</a></div></td>
<td class="alphaBg"><div align="center"><a id="alphaSearch_D" class="link" onClick="alphaSearch('D','recordHistory')" href="javascript:alphaSearch('D','submit')">D</a></div></td>
<td class="alphaBg"><div align="center"><a id="alphaSearch_E" class="link" onClick="alphaSearch('E','recordHistory')" href="javascript:alphaSearch('E','submit')">E</a></div></td>
<td class="alphaBg"><div align="center"><a id="alphaSearch_F" class="link" onClick="alphaSearch('F','recordHistory')" href="javascript:alphaSearch('F','submit')">F</a></div></td>
<td class="alphaBg"><div align="center"><a id="alphaSearch_G" class="link" onClick="alphaSearch('G','recordHistory')" href="javascript:alphaSearch('G','submit')">G</a></div></td>
<td class="alphaBg"><div align="center"><a id="alphaSearch_H" class="link" onClick="alphaSearch('H','recordHistory')" href="javascript:alphaSearch('H','submit')">H</a></div></td>
<td class="alphaBg"><div align="center"><a id="alphaSearch_I" class="link" onClick="alphaSearch('I','recordHistory')" href="javascript:alphaSearch('I','submit')">I</a></div></td>
<td class="alphaBg"><div align="center"><a id="alphaSearch_J" class="link" onClick="alphaSearch('J','recordHistory')" href="javascript:alphaSearch('J','submit')">J</a></div></td>
<td class="alphaBg"><div align="center"><a id="alphaSearch_K" class="link" onClick="alphaSearch('K','recordHistory')" href="javascript:alphaSearch('K','submit')">K</a></div></td>
<td class="alphaBg"><div align="center"><a id="alphaSearch_L" class="link" onClick="alphaSearch('L','recordHistory')" href="javascript:alphaSearch('L','submit')">L</a></div></td>
<td class="alphaBg"><div align="center"><a id="alphaSearch_M" class="link" onClick="alphaSearch('M','recordHistory')" href="javascript:alphaSearch('M','submit')">M</a></div></td>
<td class="alphaBg"><div align="center"><a id="alphaSearch_N" class="link" onClick="alphaSearch('N','recordHistory')" href="javascript:alphaSearch('N','submit')">N</a></div></td>
<td class="alphaBg"><div align="center"><a id="alphaSearch_O" class="link" onClick="alphaSearch('O','recordHistory')" href="javascript:alphaSearch('O','submit')">O</a></div></td>
<td class="alphaBg"><div align="center"><a id="alphaSearch_P" class="link" onClick="alphaSearch('P','recordHistory')" href="javascript:alphaSearch('P','submit')">P</a></div></td>
<td class="alphaBg"><div align="center"><a id="alphaSearch_Q" class="link" onClick="alphaSearch('Q','recordHistory')" href="javascript:alphaSearch('Q','submit')">Q</a></div></td>
<td class="alphaBg"><div align="center"><a id="alphaSearch_R" class="link" onClick="alphaSearch('R','recordHistory')" href="javascript:alphaSearch('R','submit')">R</a></div></td>
<td class="alphaBg"><div align="center"><a id="alphaSearch_S" class="link" onClick="alphaSearch('S','recordHistory')" href="javascript:alphaSearch('S','submit')">S</a></div></td>
<td class="alphaBg"><div align="center"><a id="alphaSearch_T" class="link" onClick="alphaSearch('T','recordHistory')" href="javascript:alphaSearch('T','submit')">T</a></div></td>
<td class="alphaBg"><div align="center"><a id="alphaSearch_U" class="link" onClick="alphaSearch('U','recordHistory')" href="javascript:alphaSearch('U','submit')">U</a></div></td>
<td class="alphaBg"><div align="center"><a id="alphaSearch_V" class="link" onClick="alphaSearch('V','recordHistory')" href="javascript:alphaSearch('V','submit')">V</a></div></td>
<td class="alphaBg"><div align="center"><a id="alphaSearch_W" class="link" onClick="alphaSearch('W','recordHistory')" href="javascript:alphaSearch('W','submit')">W</a></div></td>
<td class="alphaBg"><div align="center"><a id="alphaSearch_X" class="link" onClick="alphaSearch('X','recordHistory')" href="javascript:alphaSearch('X','submit')">X</a></div></td>
<td class="alphaBg"><div align="center"><a id="alphaSearch_Y" class="link" onClick="alphaSearch('Y','recordHistory')" href="javascript:alphaSearch('Y','submit')">Y</a></div></td>
<td class="alphaBg"><div align="center"><a id="alphaSearch_Z" class="link" onClick="alphaSearch('Z','recordHistory')" href="javascript:alphaSearch('Z','submit')">Z</a></div></td></tr></tbody></table> </td></tr></tbody></table>

</s:form>
</div>
</div>
</div></div>
</body>

</html>