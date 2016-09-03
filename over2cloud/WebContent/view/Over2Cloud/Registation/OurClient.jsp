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

var f = new Array();
var i=0;
function showLogo(cellvalue, options, row) {
	var xyz="Product List";
	var c = row.productid+"_"+row.productcode+"_"+row.productappuser;
	f[row.productid] = c;
    return "<a href='#' onClick='javascript:openDialog("+row.productid+")'>" +xyz+ "</a>";
}

function openDialog(productid) 
{
	var c = f[productid];
	var m;
	for(m=0; m<c.length; m++)
	{
		if(c.indexOf("#") !== -1)
		{
			c = c.replace("#",",");
		}
	}
$("#allproductList").dialog('open');
$("#allproductList").load("<%=request.getContextPath()%>/view/Over2Cloud/Registation/allProductaction?productid="+c);
}

$.subscribe('completegrid', function(event, data) {
    var records = jQuery("#gridView").jqGrid('getGridParam', 'records');
            alert(records);
});
</script>
<script type="text/javascript">
function pageisNotAvibale(){
$("#facilityisnotavilable").dialog('open');
}
</script>
<sj:dialog id="facilityisnotavilable" buttons="{ 'OK ':function() { },'Cancel':function() { },}"  showEffect="slide" hideEffect="explode" autoOpen="false" modal="true" title="This facility is not available in Existing Module"openTopics="openEffectDialog"closeTopics="closeEffectDialog"modal="true" width="390" height="100"/>
<sj:dialog 
    	id="allproductList" 
    	showEffect="slide" 
    	hideEffect="explode" 
    	autoOpen="false" 
    	modal="true" 
    	title="Application List For User Wise"
    	closeTopics="closeEffectDialog"
    	width="320"
    	height="250"
    >  
    </sj:dialog>
<div class="list-icon"><div class="clear"></div><div class="head">
Client Information</div></div>
<div class="container_block"><div style=" float:left; padding:20px 1%; width:98%;">

<div class="page_form">
<div class="clear"></div>
<div style="float: right; margin: 0 0px 10px 0;">
<input class="btn createNewBtn" value="Add New Details" type="button" onclick="pageisNotAvibale();">
<input class="btn importNewBtn" value="Import Sub Dept" type="button" onclick="pageisNotAvibale();">  
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
<input name="cvDelete" id="cvDelete" class="button" value="Edit" type="button" onclick="pageisNotAvibale();">	
<input name="cvDelete" id="cvDelete" class="button" value="Delete" type="button" onclick="pageisNotAvibale();">		
<span class="normalDropDown"><input class="dbtn" name="moreAct" id="moreAct" value="More Actions" type="button" onclick="pageisNotAvibale();"></span> 
</td></tr></tbody></table>
<img src="images/spacer_002.gif" class="refbtn"></td><td class="alignright printTitle"><a href="javascript:;"><img src="images/spacer_002.gif" class="sheetIconN" title="Sheet" border="0" height="26" width="33"></a><a href="javascript:;"><img src="images/spacer_002.gif" class="printIconN" title="Print View" border="0" height="26" width="29"></a></td>   


</tr></tbody></table></div></div>
<s:url id="OurClienturl" action="OurClienturlGrid"/>
<s:url id="OurClienturl1" action="SubOurClienturlGrid"/>
    <sjg:grid
    	id="gridView"
    	caption="Client View Information"
    	dataType="json"
    	href="%{OurClienturl}"
    	pager="true"
    	navigator="true"
    	navigatorSearchOptions="{sopt:['eq','ne','lt','gt']}"
    	navigatorAdd="false"
    	navigatorView="false"
    	viewrecords="true"
    	gridModel="regHries"
    	rowList="30,100,500"
    	rowNum="30"
    	rownumbers="true"
    	autowidth="true"
    	shrinkToFit="false"
    	loadonce="false"
    >
   <sjg:grid
    	id="gridView1"
    	caption="Client View Information"
    	dataType="json"
    	href="%{OurClienturl1}"
    	pager="true"
    	navigator="true"
    	navigatorSearchOptions="{sopt:['eq','ne','lt','gt']}"
    	navigatorAdd="false"
    	navigatorView="false"
    	viewrecords="true"
    	gridModel="regHries"
    	rowList="30,100,500"
    	rowNum="30"
    	rownumbers="true"
    	autowidth="true"
    	shrinkToFit="false"
    	loadonce="false"
    >
    
    <sjg:grid
    	id="gridView2"
    	caption="Client View Information"
    	dataType="json"
    	href="%{OurClienturl1}"
    	pager="true"
    	navigator="true"
    	navigatorSearchOptions="{sopt:['eq','ne','lt','gt']}"
    	navigatorAdd="false"
    	navigatorView="false"
    	viewrecords="true"
    	gridModel="regHries"
    	rowList="30,100,500"
    	rowNum="30"
    	rownumbers="true"
    	autowidth="true"
    	shrinkToFit="false"
    	loadonce="false"
    >
    
     <sjg:grid
    	id="gridView3"
    	caption="Client View Information"
    	dataType="json"
    	href="%{OurClienturl1}"
    	pager="true"
    	navigator="true"
    	navigatorSearchOptions="{sopt:['eq','ne','lt','gt']}"
    	navigatorAdd="false"
    	navigatorView="false"
    	viewrecords="true"
    	gridModel="regHries"
    	rowList="30,100,500"
    	rowNum="30"
    	rownumbers="true"
    	autowidth="true"
    	shrinkToFit="false"
    	loadonce="false"
    >
    
        <sjg:gridColumn name="combinekey" index="combinekey" title="CombineKey" key="true"  hidden="true"  sortable="false"  />
    	<sjg:gridColumn name="orgname" index="orgname" title="Orgnization Name" editable="true" edittype="text" sortable="true" />
    	<sjg:gridColumn name="orgusername" index="orgusername" title="User Name" editable="true" edittype="text"  sortable="false"/>
    	<sjg:gridColumn name="accounttype" index="accounttype" title="Account Type" editable="true" edittype="text"  sortable="false"/>
    	<sjg:gridColumn name="accountid" index="accountid" title="Account Id" sortable="false"/>
    	<sjg:gridColumn name="productuser" index="productuser" title="Total User" sortable="false"/>
        <sjg:gridColumn name="validifyfrom" index="validifyfrom" title="Validity From" sortable="false"/>
        <sjg:gridColumn name="valididyto" index="valididyto" title="Validity To" sortable="false"/>
        <sjg:gridColumn name="productid" index="productid" title="Product List" sortable="false" formatter="showLogo"/>
        <sjg:gridColumn name="productcode" index="productcode" title="Product Code" sortable="false" hidden="true" />
        <sjg:gridColumn name="productappuser" index="productappuser" title="Product User" sortable="false" hidden="true" />
    
       </sjg:grid>
    
    
    
    
        <sjg:gridColumn name="combinekey" index="combinekey" title="CombineKey" key="true"  hidden="true"  sortable="false"  />
    	<sjg:gridColumn name="orgname" index="orgname" title="Orgnization Name" editable="true" edittype="text" sortable="true" />
    	<sjg:gridColumn name="orgusername" index="orgusername" title="User Name" editable="true" edittype="text"  sortable="false"/>
    	<sjg:gridColumn name="accounttype" index="accounttype" title="Account Type" editable="true" edittype="text"  sortable="false"/>
    	<sjg:gridColumn name="accountid" index="accountid" title="Account Id" sortable="false"/>
    	<sjg:gridColumn name="productuser" index="productuser" title="Total User" sortable="false"/>
        <sjg:gridColumn name="validifyfrom" index="validifyfrom" title="Validity From" sortable="false"/>
        <sjg:gridColumn name="valididyto" index="valididyto" title="Validity To" sortable="false"/>
        <sjg:gridColumn name="productid" index="productid" title="Product List" sortable="false" formatter="showLogo"/>
        <sjg:gridColumn name="productcode" index="productcode" title="Product Code" sortable="false" hidden="true" />
        <sjg:gridColumn name="productappuser" index="productappuser" title="Product User" sortable="false" hidden="true" />
    
     </sjg:grid>
        
        <sjg:gridColumn name="combinekey" index="combinekey" title="CombineKey" key="true"  hidden="true"  sortable="false"  />
    	<sjg:gridColumn name="orgname" index="orgname" title="Orgnization Name" editable="true" edittype="text" sortable="true" />
    	<sjg:gridColumn name="orgusername" index="orgusername" title="User Name" editable="true" edittype="text"  sortable="false"/>
    	<sjg:gridColumn name="accounttype" index="accounttype" title="Account Type" editable="true" edittype="text"  sortable="false"/>
    	<sjg:gridColumn name="accountid" index="accountid" title="Account Id" sortable="false"/>
    	<sjg:gridColumn name="productuser" index="productuser" title="Total User" sortable="false"/>
        <sjg:gridColumn name="validifyfrom" index="validifyfrom" title="Validity From" sortable="false"/>
        <sjg:gridColumn name="valididyto" index="valididyto" title="Validity To" sortable="false"/>
        <sjg:gridColumn name="productid" index="productid" title="Product List" sortable="false" formatter="showLogo"/>
        <sjg:gridColumn name="productcode" index="productcode" title="Product Code" sortable="false" hidden="true" />
        <sjg:gridColumn name="productappuser" index="productappuser" title="Product User" sortable="false" hidden="true" />
   
   
   </sjg:grid>
   
        <sjg:gridColumn name="combinekey" index="combinekey" title="CombineKey" key="true" hidden="true" sortable="false"  />
    	<sjg:gridColumn name="orgname" index="orgname" title="Orgnization Name" editable="true" edittype="text" sortable="true" />
    	<sjg:gridColumn name="orgusername" index="orgusername" title="User Name" editable="true" edittype="text"  sortable="false"/>
    	<sjg:gridColumn name="accounttype" index="accounttype" title="Account Type" editable="true" edittype="text"  sortable="false"/>
    	<sjg:gridColumn name="accountid" index="accountid" title="Account Id" sortable="false"/>
    	<sjg:gridColumn name="productuser" index="productuser" title="Total User" sortable="false"/>
        <sjg:gridColumn name="validifyfrom" index="validifyfrom" title="Validity From" sortable="false"/>
        <sjg:gridColumn name="valididyto" index="valididyto" title="Validity To" sortable="false"/>
        <sjg:gridColumn name="productid" index="productid" title="Product List" sortable="false" formatter="showLogo"/>
        <sjg:gridColumn name="productcode" index="productcode" title="Product Code" sortable="false" hidden="true" />
        <sjg:gridColumn name="productappuser" index="productappuser" title="Product User" sortable="false" hidden="true" />
   
   
   
    </sjg:grid>
</div>
</div>
</div>
</div>
