<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript">
$.subscribe('complete', function(event,data)
        {
          setTimeout(function(){ $("#foldeffect1").fadeIn(); }, 50);
          setTimeout(function(){ $("#foldeffect1").fadeOut(); }, 4000);
        });
</script>
<script type="text/javascript">
function pageisNotAvibale(){
$("#facilityisnotavilable").dialog('open');
}
</script>
<sj:dialog id="facilityisnotavilable" buttons="{ 'OK ':function() { },'Cancel':function() { },}"  showEffect="slide" hideEffect="explode" autoOpen="false" modal="true" title="This facility is not available in Existing Module"openTopics="openEffectDialog"closeTopics="closeEffectDialog"modal="true" width="390" height="100"/>
<div class="list-icon"><div class="clear"></div><div class="head">
Client Data Base >> View</div></div>
<div style=" float:left; padding:10px 1%; width:98%; height: 350px;">

<!-- Level 1 to 5 Data Modification URL CALLING Part ENDS HERE -->
<div class="clear"></div>
<div class="rightHeaderButton">
<input class="btn createNewBtn" value="Create New Client Db" type="button" onclick="clientdbAdd()">
<input class="btn importNewBtn" value="Import User List" type="button" onclick="pageisNotAvibale();">  
<span class="normalDropDown"> 

 </span>
 </div>
 
 <div class="leftHeaderButton">
 <div class="floatL" onmouseout="hideCVoptionMenu()" onmouseover="showCVoptionMenu()"><div class="customDropdown floatL"><select style="display: none;" name="cvid" id="cvid" class="select chzn-done">
<optgroup label="Predefined Views" class="st" style="border:none">

<option value="a" selected="selected">
All Open Search
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

<input id="sendMailButton" class="button"  value="Send Mail" type="button" onclick="pageisNotAvibale();">
<sj:submit id="gridUser_editbutton" value="Edit" cssClass="button" cssStyle="button" onClickTopics="editgrid" button="true" />		
<input id="sendMailButton" class="button"  value="Delete" type="button" onclick="pageisNotAvibale();">
<span class="normalDropDown"><input class="dbtn" name="moreAct" id="moreAct" value="More Actions" type="button" onclick="pageisNotAvibale();"></span> 
</td></tr></tbody></table>
<img src="images/spacer_002.gif" class="refbtn"></td><td class="alignright printTitle"><a href="javascript:;"><img src="images/spacer_002.gif" class="sheetIconN" title="Sheet" border="0" height="26" width="33"></a><a href="javascript:;"><img src="images/spacer_002.gif" class="printIconN" title="Print View" border="0" height="26" width="29"></a></td>   


</tr></tbody></table></div></div>
	<s:url id="remoteurl" action="clientdbViewRecord"/>
	
    <sjg:grid
    	id="gridView1"
    	caption="Data Base Registration View"
    	dataType="json"
    	href="%{remoteurl}"
    	pager="true"
    	navigator="true"
    	navigatorSearchOptions="{sopt:['eq','ne','lt','gt']}"
    	navigatorAdd="false"
    	navigatorView="false"
    	navigatorDelete="false"
    	navigatorEdit="false"
    	gridModel="clientDBList"
    	rowList="30,100,500"
    	rowNum="30"
    	viewrecords="true"
    	autowidth="true"
    	shrinkToFit="false"
    	loadonce="false"
    	multiselect="true"
    	rownumbers="true"
    	navigatorEditOptions="{reloadAfterSubmit:true}"
    >
    	<sjg:gridColumn name="id" index="id" title="ID" hidden="true" key="true" width="50" sortable="false"/>
    	<sjg:gridColumn name="dbServerIp_name" index="dbServerIp_name" title="Server IP/Name" width="300" editable="true" edittype="text" sortable="true" />
    	<sjg:gridColumn name="dbserverport" index="dbserverport" title="DB Server Port" editable="true" width="300" edittype="text"  sortable="true"/>
    	<sjg:gridColumn name="dbusername" index="dbusername" title="DB User Name" editable="true" edittype="text" width="300" sortable="false"/>
    	<sjg:gridColumn name="isoCountrycode" index="isoCountrycode" title="Country Code" editable="false" edittype="text" width="270" sortable="false"/>
    </sjg:grid>
</div>
</div>
