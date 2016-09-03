<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<s:if test="#session['uName']==null || #session['uName']==''">
<jsp:forward page="/view/common_pages/invalidSession.jsp"/>
</s:if>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
$.subscribe('getSelected4Save', function(event,data) {
	var sel_id;
	sel_id=$("#gridFbDraftExcel").jqGrid('getGridParam','selarrrow');
	$.ajax({
		 type : "post",
		 url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Feedback_Draft/saveFbDraftExcel.action?selectedId="+sel_id,
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
</head>
<body>
<div id="fbDraftExcelResult">
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<div class="form_inner" id="form_reg">
<div class="page_form">
<s:url id="fbDraftExcelShow_URL" action="showFbDraftExcel" />
<center>
<sjg:grid  id="gridFbDraftExcel"
           caption="Feedback Draft Detail"
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
	       rowList="800,850,900"
	       rowNum="800" 
	       rownumbers="false" 
	       multiselect="false" 
	       viewrecords="true"
	       onSelectRowTopics="rowselect"
	       width="950"
	      >
           <sjg:gridColumn  name="feedtype"
                            index="feedtype"
                            title="Task Type"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
          
           <sjg:gridColumn  name="category"
                            index="category"
                            title="Category"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
            
         
          
           <sjg:gridColumn  name="sub_category"
                            index="sub_category"
                            title="Sub Category"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="feed_msg"
                            index="feed_msg"
                            title="Feedback Brief"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
          
           <sjg:gridColumn  name="addressing_time"
                            index="addressing_time"
                            title="Addressing Time"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="90"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="resolution_time"
                            index="resolution_time"
                            title="Resolution Time"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="90"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="esclevel"
                            index="esclevel"
                            title="Esc Level"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="70"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           
           
           <sjg:gridColumn  name="needesc"
                            index="needesc"
                            title="Need Escalation"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="90"
                            key="true"
                            align="center">
           </sjg:gridColumn>
       </sjg:grid>
<sj:submit id="saveExcelButton" value="Save" onClickTopics="getSelected4Save" button="true"  />
</center>
</div>
</div>
</div>
</div>
</div>
</body>
</html>