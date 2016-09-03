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
$.subscribe('getSelected4Save', function(event,data) {
	var sel_id;
	sel_id = $("#gridedittable").jqGrid('getGridParam','selarrrow');
	alert(sel_id);
	if(sel_id=="")
	   {
	     alert("Please select atleast a ckeck box...");        
	     return false;
	   }
    	$.ajax({
			 type : "post",
			 url : "/cloudapp/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/sendFeedbackViaOnline.action?selectedId="+sel_id+
			 "&tosubdept=<s:property value="%{#parameters.tosubdept}"/>&subCategory=<s:property value="%{#parameters.subCategory}"/>&feedBreif=<s:property  value="%{#parameters.feed_brief}" />&viaFrom=<s:property  value="%{#parameters.viaFrom}" />&location=<s:property  value="%{#parameters.location}"/>",
			 success : function(saveData) {
				 //$("#viewPrint").html(subdeptdata);
    			//	 alert("sucess");
    				 $("#escalationMapResult").html(saveData);
				 
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
<div class="page_title"><h1><s:property value="Khushal Singh"/> </h1></div>
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<div class="form_inner" id="form_reg">
<div class="page_form">
<s:url id="emp4escalation_URL" action="getEmp4Escalation" >
<s:param name="tosubdept" value="%{#parameters.tosubdept}"></s:param>
</s:url>
<center>
<div id="escalationMapResult">
<sjg:grid 
		  id="gridedittable"
          caption="Employee Detail"
          href="%{emp4escalation_URL}"
          gridModel="feedData"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="false"
          rowList="10,25,50"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="10"
          width="600"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          >
		           <sjg:gridColumn 
		      						name="empId"
		      						index="empId"
		      						title="Employee ID"
		      						width="150"
		      						align="center"
		      						key="true"
		      						hidden="true"
		 							/>
		 			<sjg:gridColumn 
		      						name="empName"
		      						index="empName"
		      						title="Employee Name"
		      						width="150"
		      						align="center"
		 							/>				
		  </sjg:grid>
		  <div id="bt" style="display: block">
  		<sj:submit id="saveExcelButton" value="Save" onClickTopics="getSelected4Save" button="true"  />
  	</div>
   </div>
</center>
</div>
</div>
</div>
</div>
</body>
</html>