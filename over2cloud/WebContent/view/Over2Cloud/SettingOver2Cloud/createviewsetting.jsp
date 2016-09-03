<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript">

function OnlickFunctioncreatemastersetting(valuepassed,fieldvalue,xxxxx){

	$("#data_part").html("<br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	var conP = "<%=request.getContextPath()%>";
	$.ajax({
	    type : "post",
	    url : conP+"/view/CloudApps/createAssetsettingconfiguration.action?id="+valuepassed+"&commonMappedtablele="+fieldvalue+"&mapedtable="+xxxxx,
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
<div class="list-icon">
<div class="clear">
</div><div class="head"><s:property value="%{headerName}"/> >> View</div>
</div>
<div style=" float:left; padding:10px 1%; width:100%;">
<center>
<s:url id="viewsettingData" action="viewsettingData" escapeAmp="false">
<s:param name="mapedtable" value="%{mapedtable}"></s:param>
<s:param name="mappedtablele" value="%{mappedtablele}"></s:param>
</s:url>
<s:url id="editviewsettingData" action="editviewsettingData" escapeAmp="false">
</s:url>
<div class="rightHeaderButton">
<input class="btn createNewBtn" value="Add" type="button" onclick="OnlickFunctioncreatemastersetting('<s:property value="%{id}"/>','<s:property value="%{mappedtablele}"/>','<s:property value="%{mapedtable}"/>');">
</div>
	<div class="border">
<div style=" float:left; width:100%; height: 350px;">
<div class="listviewBorder"><div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
	<sj:a id="editButton" buttonIcon="ui-icon-pencil" cssClass="button" button="true" onclick="modifyAsset();">Edit</sj:a></td>
	</tr>
	</tbody>
	</table>
	</td>
	<td class="alignright printTitle">
	<sj:a  button="true" cssClass="button" buttonIcon="ui-icon-print" >Print</sj:a>
	</td>   
	</tr></tbody></table></div></div>
</div>
<sjg:grid 
		  id="gridedittable"
          href="%{viewsettingData}"
          gridModel="settingDetail"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="false"
          rowList="10,15,20,25"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          loadingText="Requesting Data..."  
          width="1292"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          editurl="%{editviewsettingData}"
          shrinkToFit="false"
          >
          
		<s:iterator value="settingGridColomns" id="settingGridColomns" >  
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

</div>
</div>
</div>

</body>
</html>