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
<script type="text/javascript">
function associateHyperlink(cellvalue, options, row)
{
	return "<a href='#' onClick='javascript:openAssociateCatView("+row.id+")'>" + cellvalue + "</a>";
}
function openAssociateCatView(id)
{
	$("#categoryOfferingDiv").html("");
	$("#categoryOfferingDiv").load("<%=request.getContextPath()%>/view/Over2Cloud/wfpm/masters/beforeFetchAssociateCatCost.action?id="+id);
}













function addNewGroup()
{
	
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif><br/>Loading...</center>");
	$.ajax({
	    type : "post",
	    url : "/view/Over2Cloud/WFPM/Communication/beforeGroupAdd.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });

	}



function addinstantmsg()
{
	var conP = "<%=request.getContextPath()%>";
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/>Loading...</center>");
	$.ajax({
	    type : "post",
	    url : conP+"/view/Over2Cloud/WFPM/Communication/beforeinstantmsgAdd.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });

	}




</script>

</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Hindi Message</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div>
</div>
<div style=" float:left; padding:10px 1%; width:98%;">
<center>
<div class="clear"></div>
<!--<div id="ui-datepicker-div" style="display:none">
     <sj:datepicker  value="today" id="date3" name="date3" displayFormat="dd.mm.yy" />
</div>
--><div class="rightHeaderButton">
<!--<sj:a  button="true" onclick="addinstantmsg();">Add</sj:a>
-->
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
<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow()"></sj:a>
					<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchRow()"></sj:a>
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="refreshRow()"></sj:a>
</td></tr></tbody></table>
</td>
<td class="alignright printTitle">
<sj:a  button="true" cssClass="button" cssStyle="height:25px; width:32px"  title="Download" buttonIcon="ui-icon-arrowstop-1-s" onclick="excelDownload();" />
 <sj:a  button="true"  cssClass="button" cssStyle="height:25px; width:32px"  title="Upload" buttonIcon="ui-icon-arrowstop-1-n" onclick="excelUpload1();" /> 
<sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addHindiInstantmsg();">Add</sj:a>
</td> 
</tr></tbody></table></div></div>
<s:url id="viewInstantMessageGrid" action="viewInstantHindiMsgDataGrid" >
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
          multiselect="false"  
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