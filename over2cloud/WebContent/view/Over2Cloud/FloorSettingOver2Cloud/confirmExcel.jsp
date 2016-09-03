<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<s:if test="#session['uName']==null || #session['uName']==''">
<jsp:forward page="/view/common_pages/invalidSession.jsp"/>
</s:if>

<html/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
$.subscribe('getSelected4Save', function(event,data) {
	var sel_id;
	sel_id=$("#gridFbDraftExcel").jqGrid('getGridParam','selarrrow');
	$.ajax({
		 type : "post",
		 url : "/over2cloud/view/Over2Cloud/FloorSettingOver2Cloud/saveFbDraftExcel.action?selectedId="+sel_id,
		 success : function(saveData) {
				$("#ExcelDialog").dialog({height:80,width:500, title:'Success Message'});
				$("#ExcelDialog").dialog('widget').position({my:'center',at:'center',of:window});
				$("#fbDraftExcelResult").html(saveData);
				setTimeout(function(){ $("#ExcelDialog").dialog('close'); }, 2000);
			},
			error: function() {
			            alert("error");
			}
			});
	});
	
</script>
<body>
<s:url id="fbDraftExcelShow_URL" action="showFbDraftExcel" />
<center>
<sjg:grid  id="gridFbDraftExcel"
           gridModel="gridFbDraftExcelModel"
           href="%{fbDraftExcelShow_URL}" 
           pager="true" 
	       pagerButtons="true"
	       navigator="true" 
	       navigatorAdd="false" 
	       navigatorEdit="false" 
	       navigatorSearch="false"
	       navigatorView="true"
	       navigatorDelete="false" 
	       rowList="1000,2500,4000"
	       rowNum="1000" 
	       rownumbers="true" 
	       multiselect="false" 
	       viewrecords="true"
	       onSelectRowTopics="rowselect"
	       width="1133"
	      >
           <sjg:gridColumn  name="floorname"
                            index="floorname"
                            title="Floor Name"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="roomno"
                            index="roomno"
                            title="Roomno"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
           
           
          
           <sjg:gridColumn  name="wingsname"
                            index="wingsname"
                            title="Wings Name"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
       </sjg:grid>
       
       
<sj:submit id="saveExcelButton" cssStyle="margin-left" value="Save" onClickTopics="getSelected4Save" button="true"  />
</center>
</body>
</html>