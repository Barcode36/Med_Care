<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<link  type="text/css" href="<s:url value="/css/table.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/js.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/task_js/task.js"/>"></script>
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}" />
<script type="text/javascript">
function getStatusForm()
{
    var taskid            = jQuery("#gridedittable22").jqGrid('getGridParam', 'selrow');
    var taskName          = jQuery("#gridedittable22").jqGrid('getCell',taskid, 'tasknameee');
    var allotedTo         = jQuery("#gridedittable22").jqGrid('getCell',taskid, 'allotedtoo'); 
    var initiation_date   = jQuery("#gridedittable22").jqGrid('getCell',taskid, 'initiondate'); 
    var completion_date   = jQuery("#gridedittable22").jqGrid('getCell',taskid, 'comlpetiondate');
    var status            = jQuery("#gridedittable22").jqGrid('getCell',taskid, 'statuss');
    var  otherwork        = jQuery("#gridedittable22").jqGrid('getCell',taskid, 'otherworkk');
    var today             = jQuery("#gridedittable22").jqGrid('getCell',taskid, 'actiontaken');
    var task              = jQuery("#gridedittable22").jqGrid('getCell',taskid, 'specifictask');
    var date              = jQuery("#gridedittable22").jqGrid('getCell',taskid, 'functional_Date');
    var time              = jQuery("#gridedittable22").jqGrid('getCell',taskid, 'functional_Time');
     
	$("#task_status").load("view/Over2Cloud/DAROver2Cloud/proctivityTakeAction.action?taskNamee="+taskName.split(" ").join("%20")+"&allotedToo="+allotedTo.split(" ").join("%20")+"&initiation_datee="+initiation_date+"&completion_datee="+completion_date+"&statuss="+status.split(" ").join("%20")+"&otherwork="+otherwork.split(" ").join("%20")+"&time="+time+"&today="+today.split(" ").join("%20")+"&task="+task.split(" ").join("%20")+"&date="+date+"&feedId="+taskid);
    $("#task_status").dialog('open');
		}

	 /* * Format a Column as Image */
	 function formatImage(cellvalue, options, row) {
		 var context_path = '<%=request.getContextPath()%>';
		 cellvalue="Search";
	 	return "<img title='Take Action ' src='"+ context_path +"/images/like_reply.png' onClick='getStatusForm()' />"
	 	;}

	 function formatLink(cellvalue, options, rowObject) {
		  return "<a href='myEdit.action?id="+cellvalue +"'>"+cellvalue+"</a>"; }
</script>

</head>

<body>

<div style=" float:left; width:100%;">

<div class="clear"></div>
<s:url id="productivityView" action="productivityView" escapeAmp="false">
<s:param name="idproduct" value="%{idproduct}" />
<s:param name="idtodate" value="%{idtodate}" />
<s:param name="idto" value="%{idto}" />
</s:url>
<center>
<div class="border" style="height: 340px; overflow: auto;">
    <div style=" float:left; width:100%; height: 340px;">
    <div class="listviewBorder">
    <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie"><table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr></tr><tr><td></td></tr><tr><td> <table class="floatL" border="0" cellpadding="0" cellspacing="0"><tbody><tr><td class="pL10"> 
	</td></tr></tbody></table>
	</td>
	 <td class="alignright printTitle"><!--
	  <sj:a id="downloadExceProc"  button="true" buttonIcon="ui-icon-arrowthickstop-1-s" cssClass="button" onclick="getCurrentColumn('downloadExcel','productivity','downloadColumnAstDialog','downloadAstColumnDiv','%{idproduct}','%{idtodate}','%{idto}')">Download</sj:a> 
	 --><sj:a  button="true" 
				           cssClass="button" 
				           cssStyle="height:25px; width:32px"
				           title="Download"
				           buttonIcon="ui-icon-arrowstop-1-s" 
				   onclick="getCurrentColumn('downloadExcel','productivity','downloadColumnAstDialog','downloadAstColumnDiv','%{idproduct}','%{idtodate}','%{idto}')" 
	 />
	 </td>   
	</tr></tbody></table>
	</div>
	</div>
    </div>
    <div style="overflow: scroll; height: 300px;">
<sjg:grid 
		  id="ftgjgfmhg"
          href="%{productivityView}"
          gridModel="viewList"
          navigatorEdit="false"
          loadonce="true"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorSearch="true"
          rowList="10,15,20,25"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          loadingText="Requesting Data..."  
          rowNum="10"
          width="1070"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ew']}"
          shrinkToFit="false"
          navigatorViewOptions="{width:500}"
          >
         
		<s:iterator value="taskTypeViewProp" id="taskTypeViewProp" >  
		<sjg:gridColumn 
	    name="%{colomnName}"
		index="%{colomnName}"
		title="%{headingName}"
		width="%{width}"
		align="%{align}"
		editable="%{editable}"
		search="%{search}"
		hidden="%{hideOrShow}"
		/>
		</s:iterator>  
</sjg:grid>
 <sj:dialog
          id="task_status"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Take Action"
          modal="true"
          closeTopics="closeEffectDialog"
          width="800"
          height="500"
          draggable="true"
    	  resizable="true"
          >
          </sj:dialog>
</div></div>
</div>
</center>
</div>
</body>
<sj:dialog 
	id="downloadColumnAstDialog" 
	modal="true" 
	effect="slide" 
	autoOpen="false" 
	width="300" 
	height="500" 
	title="Productivity Sheet Detail Column Name" 
	loadingText="Please Wait" 
	overlayColor="#fff" 
	overlayOpacity="1.0" 
	position="['center','center']" >
	<div id="downloadAstColumnDiv"></div>
</sj:dialog>
</html>