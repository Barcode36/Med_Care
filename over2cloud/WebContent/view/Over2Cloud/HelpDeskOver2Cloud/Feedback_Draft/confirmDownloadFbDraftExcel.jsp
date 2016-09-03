<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<s:if test="#session['uName']==null || #session['uName']==''">
<jsp:forward page="/view/common_pages/invalidSession.jsp"/>
</s:if>

<html>
<head>
<script type="text/javascript">
$.subscribe('getSelected4Save', function(event,data) {
	var sel_id;
	sel_id=$("#gridFbDraftExcel").jqGrid('getGridParam','selarrrow');
	if(sel_id=="")
	{
       alert("Please select atleast one value");
	}
	else{
	$.ajax({
		 type : "post",
		 url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Feedback_Draft/downloadFbDraftExcel.action?selectedId="+sel_id,
		 success : function(saveData) {
				$("#ExcelDialog").dialog({height:80,width:600});
				$("#ExcelDialog").dialog('widget').position({my:'center',at:'center',of:window});
				$("#fbDraftExcelResult").html(saveData);
				setTimeout(function(){ $("#ExcelDialog").dialog('close'); }, 2000);
			},
			error: function() {
			            alert("error");
			}
			});
	}
	});
</script>
</head>

<body>
<div id="fbDraftExcelResult">
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<div class="form_inner" id="form_reg">
<div class="page_form">
<s:url id="fbDraftExcelShow_URL" action="showFbDraftExcel4Download" escapeAmp="false">
<s:param name="deptId" value="%{deptId}"></s:param>
<s:param name="subdeptname" value="%{subdeptname}"></s:param>
<s:param name="fbType" value="%{fbType}"></s:param>
<s:param name="categoryName" value="%{categoryName}"></s:param>
<s:param name="viewType" value="%{viewType}"></s:param>
<s:param name="moduleName" value="%{moduleName}"></s:param>
</s:url>
<center>
<sjg:grid  id="gridFbDraftExcel"
           caption="Feedback Draft Detail"
           gridModel="gridFbDraftExcelModel"
           href="%{fbDraftExcelShow_URL}" 
           pager="true" 
	       pagerButtons="true"
	       navigator="false" 
	       navigatorAdd="false" 
	       navigatorEdit="false" 
	       navigatorSearch="false"
	       navigatorView="true"
	       navigatorDelete="false" 
	       rowList="800,850,900"
	       rowNum="980" 
	       rownumbers="true" 
	       multiselect="false" 
	       viewrecords="true"
	       onSelectRowTopics="rowselect"
	       width="1020"
	      >
	      
	       <sjg:gridColumn  name="id"
                            index="id"
                            title="ID"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center"
                            key="true"
                            hidden="true">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="feedtype"
                            index="feedtype"
                            title="Feedback Type"
                            width="100"
                            align="center">
           </sjg:gridColumn>
          
           <sjg:gridColumn  name="category"
                            index="category"
                            title="Category"
                            width="100"
                            align="center">
           </sjg:gridColumn>
            
         
          
           <sjg:gridColumn  name="sub_category"
                            index="sub_category"
                            title="Sub Category"
                            width="120"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="feed_msg"
                            index="feed_msg"
                            title="Feedback Brief"
                            width="100"
                            align="center">
           </sjg:gridColumn>
          
           <sjg:gridColumn  name="addressing_time"
                            index="addressing_time"
                            title="Addressing Time"
                            width="100"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="resolution_time"
                            index="resolution_time"
                            title="Resolution Time Time"
                            width="100"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="escDuration"
                            index="escDuration"
                            title="Escalation Duration"
                            width="100"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="esclevel"
                            index="esclevel"
                            title="Escalation Level"
                            width="80"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="needesc"
                            index="needesc"
                            title="Need Escalation"
                            width="80"
                            align="center">
           </sjg:gridColumn>
       </sjg:grid>

<s:form action="downloadFbDraftExcel" id="feedDraft" namespace="/view/Over2Cloud/HelpDeskOver2Cloud/Feedback_Draft">
   <sj:submit id="saveExcelButton" value="Save"  button="true"  />
</s:form>
</center>
</div>
</div>
</div>
</div>
</body>
