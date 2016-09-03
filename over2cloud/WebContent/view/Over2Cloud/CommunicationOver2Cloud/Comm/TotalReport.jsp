<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

<script type="text/javascript" src="<s:url value="/js/communication/CommunicationInstantMessage.js"/>"></script>



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








</script>

</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
<div class="clear"></div>
<div class="head">Total Report >> View</div></div>
<div style=" float:left; padding:10px 1%; width:98%;">
<center>
<div class="clear"></div>
<!--<div id="ui-datepicker-div" style="display:none">
     <sj:datepicker  value="today" id="date3" name="date3" displayFormat="dd.mm.yy" />
</div>
--><div class="rightHeaderButton">
<!--<sj:a  button="true" onclick="addinstantmsg();">Add</sj:a>
--><!--<input class="btn createNewBtn" value="Add " type="button" onclick="addinstantmsg();">
--><div class="clear"></div>
</div>
<div class="clear"></div>



<div class="listviewBorder">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
<tbody><tr></tr><tr><td></td></tr><tr><td> 
<table class="floatL" border="0" cellpadding="0" cellspacing="0">
<tbody><tr><td class="pL10"> 
 From Date:&nbsp;&nbsp;&nbsp;
     <sj:datepicker name="fromDate" id="fromDate" value="%{fromDate}"  changeMonth="true" changeYear="true" yearRange="2010:2015" cssStyle="margin:0px 0px 10px 0px" showOn="focus" displayFormat="dd-mm-yy"  placeholder="Select From Date"/>
     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;To Date:&nbsp;&nbsp;&nbsp;
     <sj:datepicker name="toDate" id="toDate" value="%{toDate}" changeMonth="true" changeYear="true" yearRange="2010:2015" cssStyle="margin:0px 0px 10px 0px" showOn="focus" displayFormat="dd-mm-yy"  placeholder="Select To Date"/>
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr></tbody></table>
</td>
<td class="alignright printTitle">
<!--<a href="javascript:;"><img src="images/spacer_002.gif" class="sheetIconN" title="Sheet" border="0" height="26" width="33"></a>
<a href="javascript:;"><img src="images/spacer_002.gif" class="printIconN" title="Print View" border="0" height="26" width="29"></a>
--> 
<sj:a id="downloadExceProc"  button="true" buttonIcon="ui-icon-arrowreturnthick-1-s" cssClass="button" onclick="getCurrentColumn('downloadAllData','allModuleDetail','downloadColumnAllModDialog','downloadAllModColumnDiv')" >Download</sj:a>	

<sj:a  button="true" cssClass="button" buttonIcon="ui-icon-print" >Print</sj:a>
</td> 
</tr></tbody></table></div></div>
<s:url id="totalReportGridGrid" action="totalReport" >
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
          <s:iterator value="viewTotalReportGrid" id="viewTotalReportGrid" >  
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