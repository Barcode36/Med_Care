<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<% String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/helpdesk/roaster.js"/>"></script>
<script>
$.subscribe('oneditsuccess', function(event, data){
	var message = event.originalEvent.response.statusText;
	$("#gridinfo").html('<p>Status: ' + message + '</p>');
});
function mapLocation(){
	
	$("#locationDialog").dialog('open');
	$("#locationDialog").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Escalation_Conf/dragdrop2.jsp",
	    success : function(data) {
	    	$("#locationDialog").html(data);
		},
	    error: function() {
            alert("error");
        }
	 });
}
</script>
</head>
<body>
<sj:dialog
          id="locationDialog"
          showEffect="clip"
          hideEffect="clip"
          autoOpen="false"
          title="Seek Approval Action"
          modal="true"
          closeTopics="closeEffectDialog"
          width="1350"
          height="600"
          >
</sj:dialog>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">Roaster</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Manage</div> 
</div>
<div class="clear"></div>
<!-- //////////////////////////////////////////// -->
 <div class="clear"></div>
 <div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
 <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
<tbody><tr></tr>
<tr><td></td></tr>
<tr>
    <td>
	    <table class="floatL" border="0" cellpadding="0" cellspacing="0">
	    <tbody><tr><td class="pL10">
	    </td></tr></tbody>
	    </table>
    </td>
  
  <td class="alignright printTitle">
	  <sj:a id="locationMapping"  button="true"  cssClass="button" cssStyle="height:25px; width:120px" title="Location Mapping"   onclick="mapLocation();" >Map Location</sj:a>
	<sj:a id="uploadButton"  button="true"   cssClass="button"  cssStyle="height:25px; width:32px" title="Upload" buttonIcon="ui-icon-arrowstop-1-n"  onclick="uploadRoaster('viewType','dataFor');"></sj:a>
	  <sj:a id="addButton"  button="true"  cssClass="button" cssStyle="height:25px;" title="Add" buttonIcon="ui-icon-plus" onclick="addRoaster('viewType','dataFor');">Add</sj:a>
  </td>
</tr>
</tbody>
</table></div></div>
<!-- //////////////////////////////////////////////// -->
<div style="overflow: scroll; height: 430px;">
<s:hidden id="dataFor" value="%{dataFor}"/>
<s:hidden id="viewType" value="%{viewType}"/>
<s:url id="roasterApply_URL" action="applyRoaster" escapeAmp="false">
<s:param name="dataFor" value="%{dataFor}"></s:param>
<s:param name="viewType" value="%{viewType}"></s:param>
</s:url>
<s:url id="modifyRoaster_URL" action="modifyRoaster" /> 
<sjg:grid 
		  id="gridedittable"
          gridModel="roasterConfData"
          href="%{roasterApply_URL}"
          groupField="['dept_subdept']"
    	  groupColumnShow="[false]"
    	  groupCollapse="true"
    	  groupText="['<b>{0}: {1} Employees</b>']"
    	  navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="true"
          navigatorEdit="false"
          navigatorSearch="true"
          rowList="10,25,50"
	      rownumbers="-1"
	      viewrecords="true"       
	      pager="true"
	      pagerButtons="true"
	      pagerInput="true"   
	     multiselect="true"  
	     loadingText="Requesting Data..."  
	      loadonce="%{loadonce}"
	       rowNum="10"
	       autowidth="true"
          editurl="%{modifyRoaster_URL}" 
          onEditInlineAfterSaveTopics="oneditsuccess"
          editinline="true"
          shrinkToFit="false"
          navigatorEditOptions="{height:230, width:500,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true}"
          navigatorSearchOptions="{sopt:['cn','eq'],height:230, width:500,top:0,left:350,modal:true}"
           navigatorDeleteOptions="{height:230, width:500,top:0,left:350,modal:true}"
           navigatorViewOptions="{height:230, width:500,top:0,left:350,modal:true}"
          
          >
		  <s:iterator value="applyEmpRoaster" id="applyEmpRoaster" >  
		  <sjg:gridColumn 
		      				name="%{colomnName}"
		      				index="%{colomnName}"
		      				title="%{headingName}"
		      				width="%{width}"
		      				align="center"
		      				editable="%{editable}"
		      				edittype="%{edittype}"
		      				editoptions="%{editoptions}"
		      				search="%{search}"
		      				hidden="%{hideOrShow}"
		      				key="%{key}"
		 					/>
		  </s:iterator>  
		  </sjg:grid>
		  </div>
</div>
</div>
</body>
</html>