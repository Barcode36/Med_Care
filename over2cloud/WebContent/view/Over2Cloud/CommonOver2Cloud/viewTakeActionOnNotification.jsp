<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

<SCRIPT type="text/javascript">
function takeActionOnGrid(cellvalue, options, row) {

	return "<a href='#' onClick='javascript:openDialog(" + row.id
			+ ")'>" + cellvalue + "</a>";
}

function openDialog(userId) {

	$("#takeAction").load("<%=request.getContextPath()%>/view/Over2Cloud/commonModules/beforeTakeActionOnReuqest.action?id=" + userId);
	$("#takeAction").dialog('open');
}
</SCRIPT>
<sj:dialog 
	id="takeAction" 
	title="Take Action On Request" 
	autoOpen="false" 
	modal="true" 
	width="400" 
	showEffect="slide" 
	hideEffect="explode" 
	height="180"
>
</sj:dialog>
<div class="page_title"><h1>Notification</h1></div>
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<center>
<div class="form_inner" id="form_reg">
<div class="page_form">
	<s:url id="remoteurl" action="viewGridNotification"/>
	
    <sjg:grid
    	id="gridView"
    	caption="Notification"
    	dataType="json"
    	href="%{remoteurl}"
    	pager="true"
    	navigator="true"
    	navigatorSearchOptions="{sopt:['eq','ne','lt','gt']}"
    	navigatorEdit="false"
    	navigatorAdd="false"
    	navigatorView="false"
    	navigatorSearch="false"
    	navigatorDelete="false"
    	gridModel="notificationForAction"
    	rowList="30,100,500"
    	rowNum="30"
    	viewrecords="true"
    	width="720"
    	shrinkToFit="false"
    	loadonce="false"
    	multiselect="true"
    	rownumbers="true"
    	navigatorEditOptions="{reloadAfterSubmit:true}"
    >
    	<sjg:gridColumn name="id" index="id" title="ID" hidden="false" key="true" />
    	<sjg:gridColumn name="accountid" index="accountid" title="Account ID" />
    	<sjg:gridColumn name="country" index="country" title="Country" sortable="true"/>
    	<sjg:gridColumn name="typeofreq" index="typeofreq" title="Req Type" sortable="false" />
    	<sjg:gridColumn name="subject" index="subject" title="Req Subject" sortable="true" formatter="takeActionOnGrid"/>
    	<sjg:gridColumn name="clientdiscription" index="clientdiscription" title="Req Desc"/>
    	<sjg:gridColumn name="insertime" index="insertime" title="Req Time" />
    	<sjg:gridColumn name="inserdate" index="inserdate" title="Req Date" />
    </sjg:grid>
</div>
</div>
</center>
</div>
</div>
