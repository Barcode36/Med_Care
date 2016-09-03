<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

<!-- JS Calling view viewing the organization attached logo view -->
<SCRIPT type="text/javascript">

function showLogo(cellvalue, options, row) {
		return "<a href='#' onClick='javascript:openDialog("+row.companyIcon+")'>" + cellvalue + "</a>";
}
/*
 * Open Dialog with Employee Details
 */
function openDialog(logoId) 
 {
	$("#logoShow").dialog('open');
	$("#logoShow").load("<%=request.getContextPath()%>/view/Over2Cloud/OrgnizationOver2Cloud/logoCompany.jsp?logoId="+logoId);
 }

function addNewOrg()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	var conP = "<%=request.getContextPath()%>";
	$.ajax({
	    type : "post",
	    url : conP+"/view/Over2Cloud/commonModules/beforeOrganization.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function addNewOrgEdit()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/commonModules/beforeOrganizationViewTable.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function searchData()
{
	jQuery("#gridedittable").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:350,modal:true} );
}

</SCRIPT>
<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	
function editRow()
{
	if(row==0)
	{
		alert("Please select atleast one row.");
	}
	else
	{
		jQuery("#gridedittable").jqGrid('editGridRow', row ,{width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true});
	}	
}

function deleteRow()
{
	$("#gridedittable").jqGrid('delGridRow',row, {reloadAfterSubmit:true,top:0,left:350,modal:true});
}

function reload(rowid, result) {
	  $("#gridedittable").trigger("reloadGrid"); 
	}



</script>
<STYLE type="text/css">

	
</STYLE>
</head>
<body>
<sj:dialog id="facilityisnotavilable" buttons="{ 'OK ':function() { },'Cancel':function() { },}"  showEffect="slide" hideEffect="explode" autoOpen="false" modal="true" title="This facility is not available in Existing Module"openTopics="openEffectDialog"closeTopics="closeEffectDialog"modal="true" width="390" height="100"/>
<sj:dialog
           id="logoShow"
           showEffect="slide"
           hideEffect="explode"
           autoOpen="false"
           title="Logo"
           modal="true"
           width="150"
           >
          
</sj:dialog>
<div class="list-icon">
<div class="head"><s:property value="%{mainHeaderName}" /></div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div>
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
					<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow()"></sj:a>
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchData()"></sj:a>
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="reload()"></sj:a>

					    </td></tr>
					    </tbody>
					  </table>
				  </td>
				  
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				   <sj:a button="true" cssClass="button" cssStyle="height:25px; width:32px" title="View" buttonIcon="ui-icon-document" onclick="addNewOrgEdit();"></sj:a>
				     <!--  <sj:a button="true" cssClass="button" cssStyle="height:25px;" title="Add" buttonIcon="ui-icon-plus" onclick="addNewOrg();">Add</sj:a> --> 
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<!-- Code End for Header Strip -->
 <div style="overflow: scroll; height: 430px;">
<!-- Level 1 to 5 Data VIEW URL CALLING Part Starts HERE -->
<s:url id="viewLevel1Org" action="viewLevel1Org" />
<s:url id="viewLevel2Org" action="viewLevel2Org" />
<s:url id="viewLevel3Org" action="viewLevel3Org" />
<s:url id="viewLevel4Org" action="viewLevel4Org" />
<s:url id="viewLevel5Org" action="viewLevel5Org" />
<!-- Level 1 to 5 Data VIEW URL CALLING Part ENDS HERE -->

<!-- Level 1 to 5 Data Modification URL CALLING Part Starts HERE -->
<s:url id="viewModifyLevel1Org" action="viewModifyLevel1Org" />
<s:url id="viewModifyLevel2Org" action="viewModifyLevel2Org" />
<s:url id="viewModifyLevel3Org" action="viewModifyLevel3Org" />
<s:url id="viewModifyLevel4Org" action="viewModifyLevel4Org" />
<s:url id="viewModifyLevel5Org" action="viewModifyLevel5Org" />
<!-- Level 1 to 5 Data Modification URL CALLING Part ENDS HERE -->
 
<sjg:grid 
		  id="gridedittable"
          href="%{viewLevel1Org}"
          gridModel="level1DataObject"
          navigator="true"
          navigatorAdd="false"
          navigatorView="false"
          navigatorDelete="false"
          navigatorEdit="true"
          navigatorSearch="true"
          rowList="15,30,50"
          rowNum="15"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          autowidth="true"
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          editurl="%{viewModifyLevel1Org}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          onSelectRowTopics="rowselect"
          >
          
	          <s:if test="levelOfComp>1">
		          <sjg:grid 
				          id="gridedittable1"
				          caption="%{headerName1}"
				          href="%{viewLevel2Org}"
				          gridModel="level1DataObject"
				          navigator="true"
				          navigatorAdd="false"
				          navigatorView="true"
				          navigatorDelete="false"
				          navigatorEdit="true"
				          navigatorSearch="false"
				          rowList="15,20,25"
				          rownumbers="-1"
				          viewrecords="true"       
				          pager="true"
				          pagerButtons="true"
				          pagerInput="false"   
				          multiselect="true"  
				          loadingText="Requesting Data..."  
				          rowNum="15"
				          autowidth="true"
				          navigatorSearchOptions="{sopt:['eq','cn']}"
				          editurl="%{viewModifyLevel2Org}"
				          editinline="false"
				          navigatorEditOptions="{reloadAfterSubmit:true}"
				          shrinkToFit="false"
		          >
		          
			          <s:if test="levelOfComp>2">
				          <sjg:grid 
				          id="gridedittable2"
				          caption="%{headerName2}"
				          href="%{viewLevel3Org}"
				          gridModel="level1DataObject"
				          navigator="true"
				          navigatorAdd="false"
				          navigatorView="true"
				          navigatorDelete="false"
				          navigatorEdit="%{modifyPrvlgs}"
				          navigatorSearch="true"
				          rowList="10,15,20,25"
				          rownumbers="-1"
				          viewrecords="true"       
				          pager="true"
				          pagerButtons="true"
				          pagerInput="false"   
				          multiselect="true"  
				          loadingText="Requesting Data..."  
				          rowNum="5"
				          width="1120"
				          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
				          editurl="%{viewModifyLevel3Org}"
				          navigatorEditOptions="{reloadAfterSubmit:true}"
				          shrinkToFit="false"
				          >
				          
				          <s:if test="levelOfComp>3">
					          <sjg:grid 
					          id="gridedittable3"
					          caption="%{headerName3}"
					          href="%{viewLevel4Org}"
					          gridModel="level1DataObject"
					          navigator="true"
					          navigatorAdd="false"
					          navigatorView="true"
					          navigatorDelete="false"
					          navigatorEdit="%{modifyPrvlgs}"
					          navigatorSearch="true"
					          rowList="10,15,20,25"
					          rownumbers="-1"
					          viewrecords="true"       
					          pager="true"
					          pagerButtons="true"
					          pagerInput="false"   
					          multiselect="true"  
					          loadingText="Requesting Data..."  
					          rowNum="5"
					          width="1120"
					          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
					          editurl="%{viewModifyLevel4Org}"
					          navigatorEditOptions="{reloadAfterSubmit:true}"
					          shrinkToFit="false"
					          >
				          
					          <s:if test="levelOfComp>4">
						          <sjg:grid 
						          id="gridedittable4"
						          caption="%{headerName4}"
						          href="%{viewLevel5Org}"
						          gridModel="level1DataObject"
						          navigator="true"
						          navigatorAdd="false"
						          navigatorView="true"
						          navigatorDelete="false"
						          navigatorEdit="%{modifyPrvlgs}"
						          navigatorSearch="true"
						          rowList="10,15,20,25"
						          rownumbers="-1"
						          viewrecords="true"       
						          pager="true"
						          pagerButtons="true"
						          pagerInput="false"   
						          multiselect="true"  
						          loadingText="Requesting Data..."  
						          rowNum="5"
						          width="1120"
						          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
						          editurl="%{viewModifyLevel5Org}"
						          navigatorEditOptions="{reloadAfterSubmit:true}"
						          shrinkToFit="false"
						          >
					          
					           	<s:iterator value="level5ColmnNames" id="level5ColmnNames" >  
					            <sjg:gridColumn 
					            	name="%{colomnName}"
					                index="%{colomnName}"
					                title="%{headingName}"
					                width="%{width}"
					                align="%{align}"
					                editable="%{editable}"
					                search="%{search}"
					                hidden="%{hideOrShow}"
					                />
									</s:iterator>  
					             </sjg:grid>
					            </s:if>
				                        
				           				<s:iterator value="level4ColmnNames" id="level4ColmnNames" >  
				                          <sjg:gridColumn 
				                            name="%{colomnName}"
				                            index="%{colomnName}"
				                            title="%{headingName}"
				                            width="%{width}"
				                            align="%{align}"
				                            editable="%{editable}"
				                            search="%{search}"
				                            hidden="%{hideOrShow}"
				                            />
				                          
				                          </s:iterator>  
				                            
				          	</sjg:grid>
				          </s:if>
				          
				           <s:iterator value="level3ColmnNames" id="level3ColmnNames" >  
				                          <sjg:gridColumn 
				                            name="%{colomnName}"
				                            index="%{colomnName}"
				                            title="%{headingName}"
				                            width="%{width}"
				                            align="%{align}"
				                            editable="%{editable}"
				                            search="%{search}"
				                            hidden="%{hideOrShow}"
				                            />
				                          
				          </s:iterator>  
				          </sjg:grid>
			          </s:if>
		           <s:iterator value="level2ColmnNames" id="level2ColmnNames" >  
		                 <sjg:gridColumn 
			                 name="%{colomnName}"
			                 index="%{colomnName}"
			                 title="%{headingName}"
			                 width="%{width}"
			                 align="%{align}"
			                 editable="%{editable}"
			                 search="%{search}"
			                 hidden="%{hideOrShow}"
		                 />
						</s:iterator>  
		                            
		          </sjg:grid>
	          </s:if>
          
		<s:iterator value="level1ColmnNames" id="level1ColmnNames" >  
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
</div>
</body>
</html>