<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/communication/commincationHindimsgAndReport.js"/>"></script>
<script type="text/javascript"><!--
function associateHyperlink(cellvalue, options, row)
{
	return "<a href='#' onClick='javascript:openAssociateCatView("+row.id+")'>" + cellvalue + "</a>";
}
function openAssociateCatView(id)
{
	$("#categoryOfferingDiv").html("");
	$("#categoryOfferingDiv").load("<%=request.getContextPath()%>/view/Over2Cloud/wfpm/masters/beforeFetchAssociateCatCost.action?id="+id);
}





















</script>

</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
<div class="clear"></div>
<div class="head">Hindi Report >> View</div></div>
<div style=" float:left; padding:10px 1%; width:98%;">
<center>
<div class="clear"></div>
<!--<div id="ui-datepicker-div" style="display:none">
     <sj:datepicker  value="today" id="date3" name="date3" displayFormat="dd.mm.yy" />
</div>
--><div class="rightHeaderButton">
<!--<sj:a  button="true" onclick="addinstantmsg();">Add</sj:a>
--><input class="btn createNewBtn" value="Add " type="button" onclick="hindiInstantReport();">
<div class="clear"></div>
</div>
<div class="clear"></div>



<div class="listviewBorder">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
<tbody><tr></tr><tr><td></td></tr><tr><td> 
<table class="floatL" border="0" cellpadding="0" cellspacing="0">
<tbody><tr><td class="pL10"> 
<!--<sj:a id="sendMailButton" cssClass="button" button="true">Send Mail</sj:a>
<sj:a id="sendSMSButton" cssClass="button" button="true">Send SMS</sj:a>
<sj:a id="editButton" buttonIcon="ui-icon-pencil" cssClass="button" button="true" onclick="modifyAsset();">Edit</sj:a>	
--></td></tr></tbody></table>
</td>
<td class="alignright printTitle">
<!--<a href="javascript:;"><img src="images/spacer_002.gif" class="sheetIconN" title="Sheet" border="0" height="26" width="33"></a>
<a href="javascript:;"><img src="images/spacer_002.gif" class="printIconN" title="Print View" border="0" height="26" width="29"></a>
--> 
<sj:a id="downloadExceProc"  button="true" buttonIcon="ui-icon-arrowreturnthick-1-s" cssClass="button" onclick="getCurrentColumn('downloadAllData','allModuleDetail','downloadColumnAllModDialog','downloadAllModColumnDiv')" >Download</sj:a>	
<sj:a  button="true" cssClass="button" buttonIcon="ui-icon-arrowreturnthick-1-n" onclick="importAsset();">Import</sj:a>
<sj:a  button="true" cssClass="button" buttonIcon="ui-icon-print" >Print</sj:a>
</td> 
</tr></tbody></table></div></div>
<s:url id="viewInstantMessageGrid" action="beforeinstanthindiReportView" >
</s:url>
<center>
<sjg:grid 
		  id="gridedittable"
          href="%{viewInstantMessageGrid}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="%{deleteFlag}"
          navigatorEdit="%{modifyFlag}"
          navigatorSearch="false"
          rowList="10,15,20,25"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="10"
          
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          editurl="%{viewModifyGrid}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="true"
          autowidth="true"
          >
          <s:iterator value="viewInstantMsgGrid" id="viewInstantMsgGrid" >  
		<sjg:gridColumn 
		name="%{colomnName}"
		index="%{colomnName}"
		title="%{headingName}"
		width="%{width}"
		align="%{align}"
		editable="%{editable}"
		formatter="%{formatter}"
		search="%{search}"
		hidden="%{hideOrShow}"
		/>
		</s:iterator> 
	          
          
		
</sjg:grid>
</center>
</div></center>
</div>
</div>

</body>
</html>