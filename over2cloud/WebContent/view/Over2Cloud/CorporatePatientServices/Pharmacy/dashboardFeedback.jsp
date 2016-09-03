 <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/js.js"/>"></script>
  <script type="text/javascript">
 function takeMedicineDetail(cellvalue, options, rowObject) 
 {
  	return "<a href='#' title='View Data' onClick=viewMedicineDetail('"+rowObject.encounter_id+"')><font color='blue'>Medicine</font></a>";	 
 }

  
 function viewMedicineDetail(id) 
 {
 	 
 	$("#takeActionGrid1").dialog('open');
 	$("#takeActionGrid1").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
 	$("#takeActionGrid1").dialog({title: 'Medicine Detail ',width: 1000,height: "auto"});
   	$.ajax
 	({
 	type : "post",
 	url  : "view/Over2Cloud/CorporatePatientServices/Pharmacy/viewMedicineDetailItem.action?encounterId="+id,
  	success : function(data)
 	{
 	$("#takeActionGrid1").html(data);
 	},
 	error : function()
 	{
 	alert("Error on data fetch");
 	} 
 	});
 }
 function pharmaPriorityColorCode(cellvalue, options, rowObject) 
 {
 	 
 	if(rowObject.priority=='Urgent'){
 		return "<span class='cellWithoutBackground'   style='display: block;background-color:#E60000';><font color='#ffffff'><b>"+rowObject.priority+"</b></font></span>";
 		}
 	if(rowObject.priority=='Routine'){
 		return "<span class='cellWithoutBackground'   style='display: block;background-color:white';><font color='#000000'><b>"+rowObject.priority+"</b></font></span>";
 		}
 }
  
 function pharmaHistory(cellvalue, options, rowObject) 
 {
 	 
 	 	return "<a href='#' title='View Data' onClick=viewPharmaHistoryDetail('"+rowObject.id+"')><font color='blue'>"+rowObject.encounter_id+"</font></a>";	 
 }


 function viewPharmaHistoryDetail(id) 
 {
 	 
 	$("#takeActionGrid1").dialog('open');
 	$("#takeActionGrid1").html("<br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
 	$("#takeActionGrid1").dialog({title: 'Order History',width: 1000,height: "auto"});
   	$.ajax
 	({
 	type : "post",
 	url  : "view/Over2Cloud/CorporatePatientServices/Pharmacy/viewPharmaHistoryDetail.action?complianId="+id,
  	success : function(data)
 	{
 	$("#takeActionGrid1").html(data);
 	},
 	error : function()
 	{
 	alert("Error on data fetch");
 	} 
 	});
 } 
   
 </script>
  
</head>
<body>
	<sj:dialog id="takeActionGrid1" showEffect="slide" hideEffect="explode"
		autoOpen="false" title="Seek Approval Action" modal="true"
		closeTopics="closeEffectDialog" width="1000" height="400">
	</sj:dialog> 
<div class="clear"></div>
<div class="middle-content">
<div class="border" >
 
   
<s:url id="viewBoardData" action="dashFeedbackDetail" escapeAmp="false" namespace="/view/Over2Cloud/CorporatePatientServices/Pharmacy">
<s:param name="status" value="%{status}"></s:param>
<s:param name="fromDate" value="%{fromDate}"></s:param>
<s:param name="toDate" value="%{toDate}"></s:param>
<s:param name="fromTime" value="%{fromTime}"></s:param>
<s:param name="toTime" value="%{toTime}"></s:param>
<s:param name="fromTime" value="%{fromTime}"></s:param>
<s:param name="nursingUnit" value="%{nursingUnit}"></s:param>
<s:param name="location" value="%{location}"></s:param>
<s:param name="id" value="%{id}"></s:param>
<s:param name="tableFor" value="%{tableFor}"></s:param>
 </s:url>

<sjg:grid 
		  id="gridedittable8"
          href="%{viewBoardData}"
          gridModel="masterViewProp"
          navigator="false"
          navigatorAdd="false"
          navigatorView="false"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="false"
          rowList="50,500,1000,2000,5000"
          editinline="true"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="false"  
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400,closeAfterEdit:true,reloadAfterSubmit:true}"
		  navigatorDeleteOptions="{caption:'Inactive',msg: 'Inactive selected record(s)?',bSubmit: 'Inactive',closeAfterEdit:true,reloadAfterSubmit:true}"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          rowNum="50"
          autowidth="true"
          shrinkToFit="true"
          onSelectRowTopics="rowselect"
          navigatorCloneToTop="true"
          >
          
		<s:iterator value="viewPageColumns" id="viewPageColumns" > 
		
					<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="%{headingName}"
					width="%{width}"
					align="%{align}"
					editable="%{editable}"
					search="%{search}"
					hidden="%{hideOrShow}"
					formatter="%{formatter}"
					/>
 		</s:iterator>  
</sjg:grid>
</div>
</div>
</body>


</html>