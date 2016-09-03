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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/master.js"></script>
<script type="text/javascript">
$.subscribe('complete', function(event,data)
    {
        setTimeout(function(){ $("#foldeffect").fadeIn(); }, 10);
        setTimeout(function(){ $("#foldeffect").fadeOut(); }, 4000);
    });


var a=0;
function selectAll()
{
	try
	{
		if(a==0)
		{
			for(var i=0; i < document.formtwo.spaceAddress.length; i++){
				if(!document.formtwo.spaceAddress[i].checked)
					document.formtwo.spaceAddress[i].checked=true;
			}
			a=1;
		}
		else
		{
			for(var i=0; i < document.formtwo.spaceAddress.length; i++){
					document.formtwo.spaceAddress[i].checked=false;
			}
			a=0;
		}
	}
	catch(err)
	{
		
		if(a==0)
		{
				if(!document.formtwo.spaceAddress.checked)
				{
					document.formtwo.spaceAddress.checked=true;
					a=1;
				}
		}
		else
		{
			document.formtwo.spaceAddress.checked=false;
			a=0;
		}
		
	}
}
</script>
</head>
<body>

<div class="container_block"><div style=" float:left; padding:20px 1%; width:98%;">

<div class="page_form">
<div class="clear"></div>
<div style="float: right; margin: 0 0px 10px 0;">
 </div>
 <div style="float: left; margin: 0 0px 10px 0;">
 </div>
 <div class="clear"></div>
<s:form  id="BlockChunkSpace_Url" action="blockChunkAllData"  theme="css_xhtml" name="formtwo">
<div class="listviewBorder">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 

 <sj:submit targets="Result" clearForm="true" value="Add Record" href="%{BlockChunkSpace_Url}" effect="highlight" effectOptions="{ color :'#222222'}" effectDuration="5000" button="true" onCompleteTopics="complete" indicator="indicator"/>	
</td></tr></tbody></table>
<img src="images/spacer_002.gif" class="refbtn"></td><td class="alignright printTitle"><a href="javascript:;"><img src="images/spacer_002.gif" class="sheetIconN" title="Sheet" border="0" height="26" width="33"></a><a href="javascript:;"><img src="images/spacer_002.gif" class="printIconN" title="Print View" border="0" height="26" width="29"></a></td>   
</tr>
</tbody>
</table>
</div>
</div>
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
<a href="javascript:;">Slab From
</a>
</td>

<td class="tableHead"> 
<a href="javascript:;">Slab To
</a>
 </td>

<td class="tableHead"> 
<a href="javascript:;">Country
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
<s:iterator value="blockChunkGridmodel" id="blockChunbkId" status="counter">  
<s:if test="#counter.count%2 != 0">
<tr class="tdout" id="">
<td width="10"> <div style="visibility: hidden;" id="" class="indIcon" align="center"></div></td>
<td><s:property value="#counter.count" /></td>
<td class="lvCB"><s:set name="id" value="%{combinekey}" /><input type="checkbox" checked="checked" style="width:20px;"  value="${id}" id="spaceAddress" name="spaceAddress"></td> 
<td><s:property value="userFromchunk" /></td>
<td><s:property value="userTochunk" /></td> 
<td><s:property value="country" /></td>
<td>
</td>
</tr>
</s:if>
<s:else>
<tr class="tdout" id="">
<td width="10"> <div style="visibility: hidden;" id="" class="indIcon" align="center"></div></td>
<td><s:property value="#counter.count" /></td>
<td class="lvCB"><s:set name="id" value="%{combinekey}" /><input type="checkbox" checked="checked" style="width:20px;"  value="${id}" id="spaceAddress" name="spaceAddress"></td> 
<td><s:property value="userFromchunk" /></td>
<td><s:property value="userTochunk" /></td> 
<td><s:property value="country" /></td>
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
       <center> 
      <img id="indicator" src="<s:url value="/images/ajax-loader.gif"/>" alt="Loading..."   style="display:none"/>
             <div class="type-button">
            
                        <sj:div id="foldeffect"  effect="fold">
                    <div id="Result"></div>
               </sj:div>
              </div>         
           </center>      
    
     </div>
      </s:form>
</div>
</div>
</div>
</body>
</html>