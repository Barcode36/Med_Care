<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/helpdesk/ticketSeries.js"/>"></script>
<% String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	
function editRow()
{
	jQuery("#gridshifttable").jqGrid('editGridRow', row ,{width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true});
}
</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<!-- Header Strip Div Start -->
<div class="list-icon">
	 <div class="head">Ticket Series </div><img alt="" src="images/forward.png" style="margin-top:10px; float: left;"><div class="head">View</div> 
</div>
<div class="clear"></div>
<div class="listviewBorder"  style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<!-- Code For Header Strip -->
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <!-- Block for insert Left Hand Side Button -->
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody>
					    <tr><td class="pL10">
					        <!-- Insert Code Here --><!--  
					        <sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow()"></sj:a>
							<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchRow()"></sj:a>
							<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="refreshRow()"></sj:a>
				        --></td></tr>
				        </tbody>
					  </table>
				  </td>
				  
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				  <%if(true){ %>
				      <sj:a id="addButton"  button="true"  cssClass="button" cssStyle="height:25px;" title="Add" buttonIcon="ui-icon-plus" onclick="addNewTicketSeries('dataFor');">Add</sj:a>
				  <%}%>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<!-- Code End for Header Strip -->
<div style="overflow: scroll; height: 430px;">
<s:hidden id="dataFor" value="%{dataFor}"/>
<s:url id="ticketConf_URL" action="viewNormalTicketConf" escapeAmp="false">
<s:param name="flag" value="D"></s:param>
<s:param name="dataFor" value="%{dataFor}"></s:param>
</s:url>
<s:url id="editTicketSeries_URL" action="editTicketSeries">
<s:param name="dataFor" value="%{dataFor}"></s:param>
<s:param name="flag" value="%{flag}"></s:param>
</s:url>
<sjg:grid 
		  id="gridshifttable"
          caption="%{shiftHeader}"
          href="%{ticketConf_URL}"
          gridModel="ticketSeriesData"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="true"
          navigatorSearch="true"
          rowList="20,35,50"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="false"  
          loadonce="%{loadonce}"
          loadingText="Requesting Data..."  
          rowNum="20"
          autowidth="true"
          editinline="false"
          editurl="%{editTicketSeries_URL}" 
          onEditInlineAfterSaveTopics="oneditsuccess"
          navigatorSearchOptions="{sopt:['cn','eq']}"
          navigatorEditOptions="{closeAfterEdit:true,reloadAfterSubmit:false}"
          shrinkToFit="false"
          >
          
         <s:iterator value="viewPageColumns"  id="deptwiseColumnNames" >  
	           <sjg:gridColumn 
	      						name="%{colomnName}"
	      						index="%{colomnName}"
	      						title="%{headingName}"
	      						width="420"
	      						align="center"
	      						editable="%{editable}"
	      						formatter="%{formatter}"
	      						search="%{search}"
	      						hidden="%{hideOrShow}"
	 							/>
			 </s:iterator>
</sjg:grid>
</div>
</div>
</div>
</body>
</html>