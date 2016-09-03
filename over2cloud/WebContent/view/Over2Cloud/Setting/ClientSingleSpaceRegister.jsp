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
/*

$.subscribe('SelectedIds', function(event,data) {
	var s;
	var connection=document.getElementById("connection");
    s = $("#clientSpaceSingle").jqGrid('getGridParam','selarrrow');
    alert(connection);
    $("#mySingleSpaceDilog").load("view/Over2Cloud/Setting/allSingleSpaceAction?idList="+s);
    $("#mySingleSpaceDilog").dialog('open');
    return false;
    }
);

**/
$.subscribe('getStatusForm', function(event,data)
        {
            var idlist =$("#clientSpaceSingle").jqGrid('getGridParam','selarrrow');;
            var idwithspace=null;
            var arr = new Array();
            for(i=0; i < idlist.length; i++)
            {
            	var listConnection = jQuery("#clientSpaceSingle").jqGrid('getCell',idlist[i], 'spaceaddress');
            	var chunkspace = jQuery("#clientSpaceSingle").jqGrid('getCell',idlist[i], 'chunkspace');
            	var date = jQuery("#clientSpaceSingle").jqGrid('getCell',idlist[i], 'datetime');
            	var accountid = jQuery("#clientSpaceSingle").jqGrid('getCell',idlist[i], 'accountid');
                idwithspace=idlist[i]+":"+listConnection+":"+chunkspace+":"+accountid;
                arr.push(idwithspace);
            }
            $("#mySingleSpaceDilog").load("view/Over2Cloud/Setting/allSingleSpaceAction?idList="+arr);
            $("#mySingleSpaceDilog").dialog('open');
            return false;
      });
</script>
<script type="text/javascript">
function pageisNotAvibale(){
$("#facilityisnotavilable").dialog('open');
}
</script>
<sj:dialog 
    	id="mySingleSpaceDilog" 
    	showEffect="slide" 
    	hideEffect="explode" 
    	autoOpen="false" 
    	modal="true" 
    	title="Client Space Selection"
    	closeTopics="closeEffectDialog"
    	width="700"
    	height="250"
    >  
    </sj:dialog>
    <sj:dialog id="facilityisnotavilable" buttons="{ 'OK ':function() { },'Cancel':function() { },}"  showEffect="slide" hideEffect="explode" autoOpen="false" modal="true" title="This facility is not available in Existing Module"openTopics="openEffectDialog"closeTopics="closeEffectDialog"modal="true" width="390" height="100"/>
<div class="list-icon"><div class="clear"></div><div class="head">
Single Space Configuration</div></div>
<div style=" float:left; padding:10px 1%; width:98%; height: 350px;">

<!-- Level 1 to 5 Data Modification URL CALLING Part ENDS HERE -->
<div class="clear"></div>
<div class="rightHeaderButton">
<input class="btn createNewBtn" value="Demo Space" type="button" onclick="configureDemoSpace();">
<input class="btn createNewBtn" value="Block Single Space" type="button" onclick="blockSingleSpace();">
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
	<s:url id="SingleSapceConfigurationUrl" action="SingleSapceConfiguration.action"/>
    
    <sjg:grid
    	id="clientSpaceSingle"
    	caption="Single Space Configuration"
    	dataType="json"
    	href="%{SingleSapceConfigurationUrl}"
    	pager="true"
    	navigator="true"
    	rownumbers="true" 
    	navigatorSearchOptions="{sopt:['eq','ne','lt','gt']}"
    	gridModel="gridmodel"
    	groupField="['chunkspace']"
        groupColumnShow="[true]"
        groupCollapse="true"
        groupText="['<b>{0} - {1} AccountId Details</b>']"
    	rowList="500,1000,2000"
    	rowNum="500"
    	multiselect="true"
    	viewrecords="true"
    	autowidth="true"
    	shrinkToFit="false"
    	loadonce="false"
    >
    	<sjg:gridColumn name="id" index="id" title="ID" hidden="true" key="true" width="100" sortable="false"/>
    	<sjg:gridColumn name="chunkspace"  index="chunkspace" title="Space Name" width="300" editable="true" edittype="text" sortable="true" />
    	<sjg:gridColumn name="accountid" index="accountid" title="Account Id" editable="true" width="300" edittype="text"  sortable="false"/>
        <sjg:gridColumn name="datetime" index="datetime" title="Date Time" editable="true" width="300" edittype="text"  sortable="false"/>
        <sjg:gridColumn name="spaceaddress" index="spaceaddress" title="Space Address" editable="true" width="300" edittype="text"  sortable="false"/>
        </sjg:grid>
</div>
   <br>
     <center>
         <sj:submit id="grid_multi_getselectedbutton" value="Configure Space" onClickTopics="getStatusForm" button="true"/>
     </center>
</div>
