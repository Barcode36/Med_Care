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
	sel_id=$("#gridEmpRoaster").jqGrid('getGridParam','selarrrow');
	if(sel_id=="")
	   {
	     alert("Please select atleast a ckeck box...");        
	     return false;
	   }
	$.ajax({
		 type : "post",
		 url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Roaster_Conf/downloadRoaster.action?selectedId="+sel_id,
		 success : function(saveData){
				 $("#ExcelDialog").dialog({height:80,width:400, title:'Success Message'});
				 $("#ExcelDialog").dialog('widget').position({my:'center',at:'center',of:window});
				 $("#empExcelResult").html(saveData);
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
<div id="empExcelResult">
<s:url id="downloadExcel_URL" action="downloadExcel" escapeAmp="false">
<s:param name="subdept" value="%{subdept}"></s:param>
<s:param name="dept" value="%{dept}"></s:param>
</s:url>
<center>
<sjg:grid  id="gridEmpRoaster"
           caption="Escalation Detail"
           gridModel="gridEmpExcelModel"
           href="%{downloadExcel_URL}" 
           pager="true" 
	       pagerButtons="true"
	       navigator="true" 
	       navigatorAdd="false" 
	       navigatorEdit="false" 
	       navigatorSearch="false"
	       navigatorView="true"
	       navigatorDelete="false" 
	       rowList="20,50,100"
	       rowNum="20" 
	       rownumbers="true" 
	       multiselect="false" 
	       viewrecords="true"
	       onSelectRowTopics="rowselect"
	       autowidth="true"
	      >
           <sjg:gridColumn  name="empName"
                            index="empName"
                            title="Emp Name"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="200"
                            align="left">
           </sjg:gridColumn>
            
           <sjg:gridColumn  name="empId"
                            index="empId"
                            title="Emp ID"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center"
                            key="true">
           </sjg:gridColumn>
          
           <sjg:gridColumn  name="first"
                            index="first"
                            title="1"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="40"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="second"
                            index="second"
                            title="2"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="40"
                            align="center">
           </sjg:gridColumn>
          
           <sjg:gridColumn  name="three"
                            index="three"
                            title="3"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="40"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="four"
                            index="four"
                            title="4"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="40"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="fifth"
                            index="fifth"
                            title="5"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="40"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="six"
                            index="six"
                            title="6"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="40"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="seven"
                            index="seven"
                            title="7"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="40"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="eight"
                            index="eight"
                            title="8"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="40"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="nine"
                            index="nine"
                            title="9"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="40"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="ten"
                            index="ten"
                            title="10"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="40"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="eleven"
                            index="eleven"
                            title="11"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="40"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="twelve"
                            index="twelve"
                            title="12"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="40"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="thirteen"
                            index="thirteen"
                            title="13"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="40"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="fourteen"
                            index="fourteen"
                            title="14"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="40"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="fifteen"
                            index="fifteen"
                            title="15"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="40"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="sixteen"
                            index="sixteen"
                            title="16"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="40"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="seventeen"
                            index="seventeen"
                            title="17"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="40"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="eighteen"
                            index="eighteen"
                            title="18"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="40"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="nineteen"
                            index="nineteen"
                            title="19"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="40"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="twenty"
                            index="twenty"
                            title="20"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="40"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="twenty_one"
                            index="twenty_one"
                            title="21"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="40"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="twenty_two"
                            index="twenty_two"
                            title="22"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="40"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="twenty_three"
                            index="twenty_three"
                            title="23"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="40"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="twenty_four"
                            index="twenty_four"
                            title="24"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="40"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="twenty_five"
                            index="twenty_five"
                            title="25"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="40"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="twenty_six"
                            index="twenty_six"
                            title="26"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="40"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="twenty_seven"
                            index="twenty_seven"
                            title="27"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="40"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="twenty_eight"
                            index="twenty_eight"
                            title="28"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="40"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="twenty_nine"
                            index="twenty_nine"
                            title="29"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="40"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="thirty"
                            index="thirty"
                            title="30"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="40"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="thirty_one"
                            index="thirty_one"
                            title="31"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="40"
                            key="true"
                            align="center">
           </sjg:gridColumn>
           
            </sjg:grid>

</center>
<br>
<s:form action="downloadRoaster" id="roasterid" namespace="/view/Over2Cloud/HelpDeskOver2Cloud/Roaster_Conf">
<sj:submit id="saveExcelButton" value="Download" button="true"  />
</s:form>
</div>


</body>
