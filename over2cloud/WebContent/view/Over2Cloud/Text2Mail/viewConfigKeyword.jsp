<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<%
String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

<SCRIPT type="text/javascript">
function createKeyword()
{

	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Text2Mail/beforeConfigKeyword.action",
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
 
	}

</SCRIPT>
<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	
function editRow()
{
	jQuery("#gridBasicDetails").jqGrid('editGridRow', row ,{height:200, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:0,modal:true});
}

function deleteRow()
{
	//$("#gridBasicDetails").jqGrid('delGridRow',row, {height:200,reloadAfterSubmit:true});
	row = $("#gridBasicDetails").jqGrid('getGridParam','selarrrow');
	$("#gridBasicDetails").jqGrid('delGridRow',row, {reloadAfterSubmit:true,top:0,modal:true});
}

function reload(rowid, result) {
	  $("#gridBasicDetails").trigger("reloadGrid"); 
	}

function searchData()
{
	jQuery("#gridBasicDetails").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:350,modal:true, cssClass:"textField"} );
	
}
</script>
</head>
<body>
 
<div class="clear"></div>




<div class="list-icon">
<div class="head">Map Keyword</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div>
</div>

<!-- Code For Header Strip -->
<div class="clear"></div>
<div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">

<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <!-- Block for insert Left Hand Side Button -->
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr>
					 <td class="pL10">
					 
					      <%if(userRights.contains("keyt2m_MODIFY")) {%>
					<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow()"></sj:a>
					     <%} %>
					     <%if(userRights.contains("keyt2m_DELETE")) {%>
					<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
					<%} %>
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchData()"></sj:a>
					<sj:a id="refButton" title="Reload Grid" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="reload()"></sj:a>
			
					    </td></tr>
					    </tbody>
					  </table>
				  </td>
				  
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				  
				    <sj:a button="true" cssClass="button" cssStyle="height:25px;"  title="Add"  buttonIcon="ui-icon-plus" onclick="createKeyword();">Add</sj:a> 
				 
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<div class="clear"></div>
<s:url id="viewKeyword" action="viewKeywordInGrid" />
<s:url id="modifyKeyword" action="modifyKeyword" />

<center>
<sjg:grid 
		  id="gridBasicDetails"
          href="%{viewKeyword}"
          loadonce='true'
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="%{deleteFlag}"
          navigatorEdit="%{modifyFlag}"
          navigatorSearch="true"
          rowList="15,20,25"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="15"
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          editurl="%{modifyKeyword}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="true"
          autowidth="true"
          onSelectRowTopics="rowselect"
          >
           
          
	<s:iterator value="masterViewProp" id="masterViewProp" >  
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
	searchoptions="{sopt:['eq','cn']}"
	/>
	</s:iterator>  
</sjg:grid>
</center></div></div>
<br><br>
<center><img id="indicatorViewPage" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
<div id="divFullHistory" style="float:left; width:900px;">

</div>

</body>
</html>