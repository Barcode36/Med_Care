<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript">
$.subscribe('complete', function(event,data)
        {
          setTimeout(function(){ $("#foldeffect1").fadeIn(); }, 50);
          setTimeout(function(){ $("#foldeffect1").fadeOut(); }, 4000);
        });
</script>
<script type="text/javascript">
function pageisNotAvibale(){
$("#facilityisnotavilable").dialog('open');
}
</script>
<sj:dialog id="facilityisnotavilable" buttons="{ 'OK ':function() { },'Cancel':function() { },}"  showEffect="slide" hideEffect="explode" autoOpen="false" modal="true" title="This facility is not available in Existing Module"openTopics="openEffectDialog"closeTopics="closeEffectDialog"modal="true" width="390" height="100"/>
<div class="page_title"><h1>Client Data Base >> Deleted</h1></div>
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<center>
<div class="form_inner" id="form_reg">
<div class="page_form">
	<s:url id="remoteurl" action="clientdbViewRecord"/>
    <s:url id="deleteurlRecord" action="deleteurlRecordClient"/>
	
    <sjg:grid
    	id="gridView5"
    	caption="Data Base Registration View"
    	dataType="json"
    	href="%{remoteurl}"
    	pager="true"
    	navigator="true"
    	navigatorSearchOptions="{sopt:['eq','ne','lt','gt']}"
    	navigatorAdd="false"
    	navigatorView="false"
    	navigatorDelete="true"
    	navigatorEdit="false"
    	gridModel="clientDBList"
    	editurl="%{deleteurlRecord}"
    	rowList="30,100,500"
    	rowNum="30"
    	viewrecords="true"
    	width="680"
    	shrinkToFit="false"
    	loadonce="false"
    	multiselect="true"
    	rownumbers="true"
    	navigatorEditOptions="{reloadAfterSubmit:true}"
    >
    	<sjg:gridColumn name="id" index="id" title="ID" hidden="true" key="true" sortable="false"/>
    	<sjg:gridColumn name="dbServerIp_name" index="dbServerIp_name" title="Server IP/Name" editable="true" edittype="text" sortable="true" />
    	<sjg:gridColumn name="dbserverport" index="dbserverport" title="DB Server Port" editable="true" edittype="text"  sortable="true"/>
    	<sjg:gridColumn name="dbusername" index="dbusername" title="DB User Name" editable="true" edittype="text" sortable="false"/>
    	<sjg:gridColumn name="isoCountrycode" index="isoCountrycode" title="Country Code" editable="false" edittype="text" sortable="false"/>
    </sjg:grid>
</div>
</div>
</center>
</div>
</div>
