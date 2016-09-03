.<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<s:if test="#session['uName']==null || #session['uName']==''">
<jsp:forward page="/view/common_pages/invalidSession.jsp"/>
</s:if>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
$.subscribe('getSelectedSave', function(event,data) 
	{
	var sel_id;
	sel_id=$("#gridExcel").jqGrid('getGridParam','selarrrow');
	alert("sel_id"+sel_id);
	$.ajax({
		 type : "post",
		 url : "view/Over2Cloud/CommunicationOver2Cloud/Comm/savebalclistExcel.action?select_id="+sel_id,
		 success : function(saveData) {
				$("#ExcelDialog").dialog({height:80,width:500, title:'Success Message'});
				$("#ExcelDialog").dialog('widget').position({my:'center',at:'center',of:window});
				$("#excelResult").html(saveData);
				setTimeout(function(){ $("#ExcelDialog").dialog('close'); }, 2000);
			},
			error: function() {
			            alert("error");
			    }
			});
	});
</script>
</head>
<body>
<div id="excelResult">
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<s:url id="excelShow_URL" action="showExcell">
</s:url>
<center>
<sjg:grid  id="gridExcel"
           gridModel="gridModelList"
           href="%{excelShow_URL}" 
           pager="true" 
	       pagerButtons="true"
	       navigator="true" 
	       navigatorAdd="false" 
	       navigatorEdit="false" 
	       navigatorSearch="false"
	       navigatorView="true"
	       navigatorDelete="false" 
	       rowList="800,850,900"
	       rowNum="800" 
	       rownumbers="false" 
	       multiselect="true" 
	       viewrecords="true"
	       onSelectRowTopics="rowselect"
	       width="950"
	      >
           <sjg:gridColumn  name="validNum"
                            index="validNum"
                            title="Valid Num"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="alreadyPresent"
                            index="alreadyPresent"
                            title="Already Present"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
           
           
           <sjg:gridColumn  name="invalidNum"
                            index="invalidNum"
                            title="Invalid Num"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
           
       </sjg:grid>
       <br>
       
<sj:submit id="saveExcelButton" value="Save" onClickTopics="getSelectedSave" button="true"  />
</center>
</div>
</div>
</div>
</body>
</html>