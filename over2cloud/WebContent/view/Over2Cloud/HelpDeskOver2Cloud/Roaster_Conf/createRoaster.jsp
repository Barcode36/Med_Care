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
<script type="text/javascript">
$.subscribe('rowadd', function(event,data) {
	var sel_id;
	sel_id = $("#gridedittable").jqGrid('getGridParam','selarrrow');
	if(sel_id=="")
	   {
	     alert("Please select atleast a ckeck box...");        
	     return false;
	   }
    	$.ajax({
			 type : "post",
			 url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Roaster_Conf/saveEscalateEmp.action?selectedId="+sel_id,
			 success : function(saveData) {
				 addRoaster('viewType','dataFor');
				},
				error: function() {
				            alert("error");
				}
				});
    	// $("#ticketPrint").dialog('open');
			 
	});
</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">Roaster</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div> 
</div>
<div class="clear"></div>
 <!-- //////////////////////////////////////////// -->
 <div class="listviewBorder"  style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
 <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
<tbody><tr></tr>
<tr><td></td></tr>
<tr>
<td> 
    <table class="floatL" border="0" cellpadding="0" cellspacing="0">
    <tbody><tr><td class="pL10"></td></tr></tbody>
	</table>
</td>
<td class="alignright printTitle">
  <sj:a id="addButton"  button="true"  cssClass="button" cssStyle="height:25px;" title="Manage" buttonIcon="ui-icon-pencil" onclick="applyRoaster('viewType','dataFor');">Manage</sj:a>
</td>
	 
</tr>

</tbody>
</table></div></div>
<!-- //////////////////////////////////////////////// -->
<div style="overflow: scroll; height: 435px;">
<s:hidden id="dataFor" value="%{dataFor}"/>
<s:hidden id="viewType" value="%{viewType}"/>
<s:url id="roasterConf_URL" action="addRoaster" escapeAmp="false">
<s:param name="dataFor" value="%{dataFor}"></s:param>
<s:param name="viewType" value="%{viewType}"></s:param>
</s:url>
<sjg:grid 
		  id="gridedittable"
          href="%{roasterConf_URL}"
          gridModel="roasterConfData"
          groupField="['dept_subdept']"
    	  groupColumnShow="[false]"
    	  groupCollapse="true"
    	  groupText="['<b>{0} - {1} Employees</b>']"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="true"
          navigatorEdit="true"
          navigatorSearch="true"
          rowList="50,100,200"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="true"  
          loadonce="%{loadonce}"
          loadingText="Requesting Data..."  
          rowNum="50"
          autowidth="true"
          navigatorEditOptions="{height:230, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true}"
		  navigatorSearchOptions="{sopt:['cn','eq'],height:230, width:425,top:0,left:350,modal:true}"
		  navigatorDeleteOptions="{height:230, width:425,top:0,left:350,modal:true}"
		  navigatorViewOptions="{height:230, width:425,top:0,left:350,modal:true}"
          shrinkToFit="false"
          >
		  <s:iterator value="addEmpRoaster" id="addEmpRoaster" >  
		  <sjg:gridColumn 
		      				name="%{colomnName}"
		      				index="%{colomnName}"
		      				title="%{headingName}"
		      				align="%{align}"
		      				width="314"
		      				editable="%{editable}"
		      				formatter="%{formatter}"
		      				search="%{search}"
		      				hidden="%{hideOrShow}"
		 					/>
		  </s:iterator>  
		  </sjg:grid>
		  
          <!-- Buttons -->
         <br>
		  <div class="fields">
			  <ul><li class="submit">
				  <div class="type-button" align="center">
		              <sj:submit 
		                         value="Save" 
		                         onClickTopics="rowadd" 
		                         button="true"
		                         cssClass="submit"
		                        />
		          </div>
			  </li></ul>
          </div>
</div>
</div>
</div>
</body>
</html>