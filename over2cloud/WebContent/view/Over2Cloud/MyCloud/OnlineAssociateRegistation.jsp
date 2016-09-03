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
$.subscribe('blockmethod', function() {
var f = new Array();
var i=0;
var id = jQuery("#onlinegridview").jqGrid('getGridParam', 'selrow');
if(id!=null && id!="-1"){
var accountid = jQuery("#onlinegridview").jqGrid('getCell', id, 'accountid');
var accountids = jQuery("#onlinegridview").jqGrid('getCell', id, 'accountids');
  var islive = jQuery("#onlinegridview").jqGrid('getCell', id, 'islive');
	var xyz="Product List";
	var c = accountids+"-"+accountid+"-"+islive;
	f[accountids] = c;
    var productid = accountids;
	var c = f[productid];
	var m;
	for(m=0; m<c.length; m++)
	{
		if(c.indexOf("#") !== -1)
		{
			c = c.replace("#",",");
		}
$("#allproductList").dialog('open');
$("#allproductList").load("<%=request.getContextPath()%>/view/Over2Cloud/Registation/BlockallourClient?productid="+c);
}
 }
else{
alert("Please First select Row");
}
});
</script>
<script type="text/javascript">
function pageisNotAvibale(){
$("#facilityisnotavilable").dialog('open');
}
</script>
<sj:dialog id="allproductList" showEffect="slide"  buttons="{ 'OK ':function() { },'Cancel':function() { },}" hideEffect="explode" autoOpen="false" modal="true" title="Block/UnBlock Client" closeTopics="closeEffectDialog" width="300" height="100"> </sj:dialog>
<sj:dialog id="facilityisnotavilable" buttons="{ 'OK ':function() { },'Cancel':function() { },}"  showEffect="slide" hideEffect="explode" autoOpen="false" modal="true" title="This facility is not available in Existing Module"openTopics="openEffectDialog"closeTopics="closeEffectDialog"modal="true" width="390" height="100"/>
<div class="list-icon"><div class="clear"></div><div class="head">
Online Associate Details</div></div>
<div class="container_block"><div style=" float:left; padding:20px 1%; width:98%;">

<div class="page_form">
<div class="clear"></div>
<div style="float: right; margin: 0 0px 10px 0;">
<input class="btn createNewBtn" value="Add New Details" type="button" onclick="pageisNotAvibale();">
<input class="btn importNewBtn" value="Import Sub Dept" type="button" onchange="pageisNotAvibale();">  
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

<input id="sendMailButton" class="button"  value="Send Mail" type="button" onclick="pageisNotAvibale();">
<sj:submit id="gridUser_editbutton" value="Block/Unblock" cssClass="button" cssStyle="button" onClickTopics="blockmethod" button="true" />
<input name="cvDelete" id="cvDelete" class="button" value="Edit" type="button" onclick="pageisNotAvibale();">	
<input name="cvDelete" id="cvDelete" class="button" value="Delete" type="button" onclick="pageisNotAvibale();">		
<span class="normalDropDown"><input class="dbtn" name="moreAct" id="moreAct" value="More Actions" type="button" onclick="pageisNotAvibale();"></span> 
</td></tr></tbody></table>
<img src="images/spacer_002.gif" class="refbtn"></td><td class="alignright printTitle"><a href="javascript:;"><img src="images/spacer_002.gif" class="sheetIconN" title="Sheet" border="0" height="26" width="33"></a><a href="javascript:;"><img src="images/spacer_002.gif" class="printIconN" title="Print View" border="0" height="26" width="29"></a></td>   


</tr></tbody></table></div></div>
<s:url id="onAssociateclientUrl" action="onLineAssocaiateClient.action"/>
    <sjg:grid
    	id="onlinegridview"
    	caption="Online Associate Details"
    	dataType="json"
    	href="%{onAssociateclientUrl}"
    	pager="true"
    	navigator="true"
    	navigatorSearchOptions="{sopt:['eq','ne','lt','gt']}"
    	navigatorAdd="false"
    	navigatorView="true"
    	navigatorDelete="false"
    	navigatorEdit="false"
    	viewrecords="true"
    	gridModel="regHries"
    	rowList="30,100,500"
    	rowNum="30"
    	rownumbers="true"
    	multiselect="true"
    	autowidth="true"
    	loadonce="false"
    >
        <sjg:gridColumn name="accountid" width="80" index="accountid" title="Account Id" sortable="false" align="center"/>
        <sjg:gridColumn name="orgname"  width="200" index="orgname" title="Orgnization Name" editable="true" edittype="text" sortable="true" align="center"/>
    	<sjg:gridColumn name="orgusername" width="200" index="orgusername" title="Registered BY" editable="true" edittype="text"  sortable="false" align="center"/>
    	<sjg:gridColumn name="city" width="200" index="city" title="City" editable="true" edittype="text"  sortable="false" align="center"/>
    	<sjg:gridColumn name="country" width="130" index="country" title="Country" sortable="false" align="center"/>
    	<sjg:gridColumn name="signupstatus" width="200" index="signupstatus" title="SignUp Status" sortable="false" align="center"/>
    	<sjg:gridColumn name="accounttype" width="300" index="accounttype" title="Account Type" editable="true" edittype="text"  sortable="false" align="center"/>
        <sjg:gridColumn name="islive" width="100" index="islive" title="Block/Unblock" editable="true" edittype="text"   sortable="false"/>
        <sjg:gridColumn name="accountids" width="100" index="accountids" title="Block/Unblock" editable="true" edittype="text"  hidden="true"   sortable="false"/>
    </sjg:grid>
</div>
</div>
</div>
</div>
