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
		 url : "/over2cloud/view/Over2Cloud/CorporatePatientServices/cpservices/saveFbDraftExcel.action?selectedId="+sel_id,
		 success : function(saveData) {
				//$("#ExcelDialog").dialog({height:80,width:500, title:'Success Message'});
				//$("#ExcelDialog").dialog('widget').position({my:'center',at:'center',of:window});
				$("#completionResult11").html(saveData);
				$('#completionResult11').dialog('open');
				setTimeout(function(){ $("#completionResult11").dialog('close'); }, 4000);
				setTimeout(function(){ $("#ExcelDialog").dialog('close'); }, 4000);
				setTimeout(function(){ back(); }, 4000);
				
			},
			error: function() {
			            alert("error");
			}
			});
	});
function back()
{
	CpsRotaMap();
}
</script>
</head>
<body>
<sj:dialog
          id="completionResult11"
          showEffect="slide" 
          hideEffect="explode" 
          openTopics="openEffectDialog"
          closeTopics="closeEffectDialog"
          autoOpen="false"
          title="Confirmation Message"
          modal="true"
          draggable="true"
          resizable="true"
         
          >
          <sj:div id="complTarget" cssStyle="" effect="fold"></sj:div>
</sj:dialog>
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
           <sjg:gridColumn  name="location"
                            index="location"
                            title="Location"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="employee"
                            index="employee"
                            title="Employee"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
           
            <sjg:gridColumn  name="mobile"
                            index="mobile"
                            title="Mobile"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
           
            <sjg:gridColumn  name="day01"
                            index="day01"
                            title="day01"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
          
           
            <sjg:gridColumn  name="day02"
                            index="day02"
                            title="day02"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="day03"
                            index="day03"
                            title="day03"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="day04"
                            index="day04"
                            title="day04"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="day05"
                            index="day05"
                            title="day05"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="day06"
                            index="day06"
                            title="day06"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="day07"
                            index="day07"
                            title="day07"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="day08"
                            index="day08"
                            title="day08"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
           
               <sjg:gridColumn  name="day09"
                            index="day09"
                            title="day09"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
           
               <sjg:gridColumn  name="day10"
                            index="day10"
                            title="day10"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
           
           
               <sjg:gridColumn  name="day11"
                            index="day11"
                            title="day11"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="day12"
                            index="day12"
                            title="day12"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="day13"
                            index="day13"
                            title="day13"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
           
           
           <sjg:gridColumn  name="day14"
                            index="day14"
                            title="day14"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="day15"
                            index="day15"
                            title="day15"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
            <sjg:gridColumn  name="day16"
                            index="day16"
                            title="day16"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="day17"
                            index="day17"
                            title="day17"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
            <sjg:gridColumn  name="day18"
                            index="day18"
                            title="day18"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="day19"
                            index="day19"
                            title="day19"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
            <sjg:gridColumn  name="day20"
                            index="day20"
                            title="day20"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="day21"
                            index="day21"
                            title="day21"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
            <sjg:gridColumn  name="day22"
                            index="day22"
                            title="day22"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="day23"
                            index="day23"
                            title="day23"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
            <sjg:gridColumn  name="day24"
                            index="day24"
                            title="day24"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="day25"
                            index="day25"
                            title="day25"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
            <sjg:gridColumn  name="day26"
                            index="day26"
                            title="day26"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="day27"
                            index="day27"
                            title="day27"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
            <sjg:gridColumn  name="day28"
                            index="day28"
                            title="day28"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="day29"
                            index="day29"
                            title="day29"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
            <sjg:gridColumn  name="day30"
                            index="day30"
                            title="day30"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
           
           <sjg:gridColumn  name="day31"
                            index="day31"
                            title="day31"
                            editable="false"
                            sortable="true"
                            search="false"
                            width="100"
                            align="center">
           </sjg:gridColumn>
           
           
           
       </sjg:grid>
       
       <br><br><br>
<sj:submit  id="saveExcelButton" cssStyle="margin-left" value="Save" onClickTopics="getSelected4Save"  button="true"  />
</center>
</body>
</html>