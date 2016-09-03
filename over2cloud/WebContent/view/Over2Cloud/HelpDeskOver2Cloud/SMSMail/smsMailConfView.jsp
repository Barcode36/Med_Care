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
function addNotification()
{
	$("#moreSettingDiv").html("<br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Daily_Report/beforeSMSMailConf.action",
	    success : function(subdeptdata) {
       $("#"+"moreSettingDiv").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Notification</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">View</div> 
</div>
<div class="clear"></div>
<!-- Code For Header Strip -->
<div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">   
					      </td></tr></tbody>
					  </table>
				  </td>
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
							<sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addNotification();">Add</sj:a>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<div style="overflow: scroll; height: 273px;">
<s:url id="veiwTaskType" action="sMSMailConfView" />
<s:url id="viewModifyTaskType" action="viewModifyTaskTypeAction" />
<sjg:grid 
		 
          id="gridedittable"
          href="%{veiwTaskType}"
          gridModel="subDeptData"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="true"
          navigatorEdit="true"
          navigatorSearch="true"
          editinline="false"
          rowList="100,200,500"
          rowNum="100"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          rownumbers="-1"
          pagerInput="true"   
          multiselect="false"
          navigatorSearchOptions="{sopt:['eq','cn']}"  
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400}"
          editurl="%{viewModifyTaskType}"
          navigatorEditOptions="{closeAfterEdit:true,reloadAfterSubmit:true}"
          shrinkToFit="false"
          autowidth="true"
          loadonce="true"
          onSelectRowTopics="rowselect"
          onEditInlineSuccessTopics="oneditsuccess"
          
          >
				<sjg:gridColumn 
				name="allotByEmail"
				index="allotByEmail"
				title="Allot By Email"
				width="230"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				/>
				
				<sjg:gridColumn 
				name="allotBySMS"
				index="allotBySMS"
				title="Allot By SMS"
				width="230"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				/>
				
				<sjg:gridColumn 
				name="allotToEmail"
				index="allotToEmail"
				title="Allot To Email"
				width="230"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				/>
				
				<sjg:gridColumn 
				name="allotToSMS"
				index="allotToSMS"
				title="Allot To SMS"
				width="230"
				align="center"
				editable="false"
				search="false"
				hidden="false"
				/>
</sjg:grid>
</div>
</div>
</div>
</body>

</html>