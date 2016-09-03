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
var f = new Array();
var i=0;
function showLogo1(cellvalue, options, row) {

	var xyz="Product List";
	var c = row.accountid+"_"+row.productcode+"_"+row.productuser;
	f[row.reqRechargUpperHric] = c;
    return "<a href='#' onClick='javascript:openDialog("+row.reqRechargUpperHric+")'>" +xyz+ "</a>";
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
$("#allproductList1").dialog('open');
$("#allproductList1").load("<%=request.getContextPath()%>/view/Over2Cloud/Registation/allProductaction?productid="+c);
}
</script>


<script type="text/javascript">
var g = new Array();
var k=0;
function showLogo3(cellvalue, options, row) {
	var xyz="Request To Organization";
	var pp = row.accountid+"_"+row.countryid+"_"+row.orgname;
	g[row.reqRechargUpperHric] =pp;

	
    return "<a href='#' onClick='javascript:openDialog3("+row.reqRechargUpperHric+")'>" +xyz+ "</a>";
}

function openDialog3(productid) 
{
	var pp = g[productid];
	var m;
	for(m=0; m<pp.length; m++)
	{
		if(pp.indexOf("#") !== -1)
		{
			pp = pp.replace("#",",");
		}
	}
$("#allproductList3").dialog('open');
$("#allproductList3").load("<%=request.getContextPath()%>/view/Over2Cloud/AccountMgmt/reqforOrgnization?orgnizationId="+pp);

}
</script>
<script type="text/javascript">
function pageisNotAvibale(){
$("#facilityisnotavilable").dialog('open');
}
</script>
<sj:dialog id="facilityisnotavilable" buttons="{ 'OK ':function() { },'Cancel':function() { },}"  showEffect="slide" hideEffect="explode" autoOpen="false" modal="true" title="This facility is not available in Existing Module"openTopics="openEffectDialog"closeTopics="closeEffectDialog"modal="true" width="390" height="100"/>
<sj:dialog 
    	id="allproductList3" 
    	showEffect="slide" 
    	buttons="{ 'OK ':function() { },'Cancel':function() { },}"
    	hideEffect="explode" 
    	autoOpen="false" 
    	modal="true" 
    	title="Request For Organization"
    	closeTopics="closeEffectDialog"
    	width="950"
    	height="350"
    >  
</sj:dialog>


<sj:dialog 
    	id="allproductList1" 
    	showEffect="slide" 
    	buttons="{ 'OK ':function() { },'Cancel':function() { },}"
    	hideEffect="explode" 
    	autoOpen="false" 
    	modal="true" 
    	title="Product Information Details"
    	closeTopics="closeEffectDialog"
    	width="500"
    	height="200"
    >  
</sj:dialog>

<div class="list-icon"><div class="clear"></div><div class="head">
View Account Details</div></div>
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
<s:url id="viewAccountDetailsUrl" action="viewAccountDetails.action"/>
<sjg:grid
    	id="onlineGridView"
    	caption="View Account Details"
    	dataType="json"
    	href="%{viewAccountDetailsUrl}"
    	pager="true"
    	navigator="true"
    	navigatorSearchOptions="{sopt:['eq','ne','lt','gt']}"
    	navigatorAdd="false"
    	navigatorView="true"
    	navigatorDelete="false"
    	navigatorEdit="false"
    	viewrecords="true"
    	gridModel="griedmodel"
    	rowList="30,100,500"
    	rowNum="30"
    	rownumbers="true"
    	multiselect="true"
    	autowidth="true"
    	shrinkToFit="false"
    >
         <sjg:gridColumn name="accountid"  index="accountid" title="Account Id" width="55" align="center"/>
         <sjg:gridColumn name="orgname"   index="orgname" title="Orgnization Name" align="center"/>
    	 <sjg:gridColumn name="orgRegname" index="orgRegname" title="Registered BY" width="80" align="center"/>
    	 <sjg:gridColumn name="accounttype"  index="accounttype" title="Account Type" align="center" width="220"/>
    	 <sjg:gridColumn name="countryid"  index="countryid" title="Country" align="center" width="80"/>
    	 <sjg:gridColumn name="productcode"  index="productcode" title="Product Code" hidden="true" align="center"/>
    	 <sjg:gridColumn name="productuser"  index="productuser" title="Product User" hidden="true" align="center"/>
    	 <sjg:gridColumn name="totaluser" index="totaluser" title="Total User"  align="center" width="80"/>
    	  <sjg:gridColumn name="productlist" index="productlist" title="Product List" formatter="showLogo1" align="center"/>
         <sjg:gridColumn name="validityfrom"  index="validityfrom" title="Validity From" align="center"/>
         <sjg:gridColumn name="valiudityto"  index="valiudityto" title="Validity To"   align="center"/>
         <sjg:gridColumn name="mailtoOrgnization"  index="mailtoOrgnization"   title="Req. To Orgnization" formatter="showLogo3"  align="center"/>
     </sjg:grid>
    
</div>
</div>
</div>
</div>
