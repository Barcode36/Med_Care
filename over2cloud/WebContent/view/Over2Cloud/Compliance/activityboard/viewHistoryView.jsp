<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
function checkList(cellvalue, options, rowObject) 
{
	 var context_path = '<%=request.getContextPath()%>';
	return "<img title='CheckList' src='"+ context_path +"/images/OTM/check.jpg' height='20' width='20' onClick='javascript:checkListFormatter11("+rowObject.id+");' /></a>";

}
function checkListFormatter11(id) 
{
	$("#historyView").dialog('open');
	
	var taskId = jQuery("#fullViewHistory").jqGrid('getCell',id,'cr.complainceId');
	$("#historyView").dialog({title: 'Check List for Task ID: '+taskId,width: 700,height: 350});
	$("#historyView").load("<%=request.getContextPath()%>/view/Over2Cloud/Compliance/compliance_pages/viewCheckList?complID="+id+"&dataFrom=History");
	
	   return false;
}
function viewTAT(cellvalue, options, rowObject) 
{
	return "<a href='#' onClick='viewTATFormatter11("+rowObject.id+");' ><font color='blue'>"+cellvalue+"</font></a>";

}
function viewTATFormatter11(id) 
{
	var taskId = jQuery("#fullViewHistory").jqGrid('getCell',id,'cr.complainceId');
	var comp=$("#complID").val();
	
	$("#historyView").dialog('open');
	$("#historyView").dialog({title: 'TAT Status for Task ID: '+taskId,width: 650,height: 230});
	$("#historyView").load("<%=request.getContextPath()%>/view/Over2Cloud/Compliance/compliance_pages/viewTATFormatter?complID="+comp);
	
	   return false;
}
function reminder(cellvalue, options, rowObject) 
{
	return "<a href='#' onClick='viewReminderFormatter11("+rowObject.id+");' ><font color='blue'>"+cellvalue+"</font></a>";
}
function viewReminderFormatter11(id) 
{
	var taskId = jQuery("#fullViewHistory").jqGrid('getCell',id,'cr.complainceId');
	var comp=$("#complID").val();
	$("#historyView").dialog('open');
	$("#historyView").dialog({title: 'Upcomming Reminder for Task ID: '+taskId,width: 400,height: 300});
	$("#historyView").load("<%=request.getContextPath()%>/view/Over2Cloud/Compliance/compliance_pages/viewReminderFormatter?complID="+comp);
	
	   return false;
}
function viewStatus(cellvalue, options, rowObject) 
{
	 if(cellvalue=='Pending')
     {
        return "<a href='#' >"+cellvalue+"</a>";
     }
     else
     {
		return "<a href='#' onClick='viewStatusFormatter11("+rowObject.id+");' ><font color='blue'>"+cellvalue+"</font></a>";
     }
}
function viewStatusFormatter11(id) 
{
	
	var taskId = jQuery("#fullViewHistory").jqGrid('getCell',id,'cr.complainceId');
	var comp=$("#complID").val();
	$("#historyView").dialog('open');
	$("#historyView").dialog({title: 'Activity Cycle for Task ID: '+taskId,width: 700,height: 250});
	$("#historyView").load("<%=request.getContextPath()%>/view/Over2Cloud/Compliance/compliance_pages/viewStatus?complID="+comp+"&taskId="+taskId);
	   return false;
}

function downloadHisory()
{
	var sel_id;
	sel_id = $("#fullViewHistory").jqGrid('getGridParam','selarrrow');
	if(sel_id=="")
	{
	     alert("Please select atleast one check box...");        
	     return false;
	}
	else
	{
		$("#takeActionGrid").dialog({title: 'Check Column',width: 350,height: 600});
		$("#takeActionGrid").dialog('open');
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/sendReportMailConfirmAction.action?selectedId="+sel_id,
		    success : function(data) 
		    {
	 			$("#takeActionGrid").html(data);
			},
		   error: function() {
		        alert("error");
		    }
		 });
	}
}

</script>
</head>
<body>

<sj:dialog
          id="historyView"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title="Operation Task Action"
          modal="true"
          width="1000"
          height="350"
          draggable="true"
    	  resizable="true"
          >
</sj:dialog>
<sj:dialog
          id="takeActionGrid"
          showEffect="slide" 
          hideEffect="explode" 
          openTopics="openEffectDialog"
          closeTopics="closeEffectDialog"
          autoOpen="false"
          title="Operation Task Action"
          modal="true"
          width="1000"
          height="350"
          draggable="true"
          resizable="true"
          >
</sj:dialog>
<div class="clear"></div>
<s:hidden id="complID" value="%{complID}"></s:hidden>
<s:if test="complDetails.size()>0">
<table width="100%" border="1" style="border-collapse: collapse;">
     	<tr bgcolor="#D8D8D8" style="height: 25px;">
          <s:iterator value="%{complDetails}" status="status" >
            <s:if test="%{key=='Department' || key=='Alloted By'}">
                
                    <td align="left" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="left" width="30%" > <s:property value="%{value}"/></td>
                
            </s:if>
        </s:iterator>
        </tr>
    
        <tr style="height: 25px;">
         <s:iterator value="%{complDetails}" status="status" >
            <s:if test="%{key=='Alloted To' || key=='Started From'}">
                    <td align="left" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="left" width="30%" > <s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
        </tr>
        
        <tr bgcolor="#D8D8D8" style="height: 25px;">
          <s:iterator value="%{complDetails}" status="status" >
            <s:if test="%{key=='Frequency' || key=='Ownership'}">
                
                    <td align="left" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="left" width="30%" > <s:property value="%{value}"/></td>
                
            </s:if>
        </s:iterator>
        </tr>
</table>
</s:if>
<div class="clear"></div>
<br><div class="listviewBorder" style="margin-top: 5px;width: 100%;margin-left: 0px;" align="center">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
    <tbody>
    <tr>
      <td>
      <table class="floatL" border="0" cellpadding="0" cellspacing="0">
        <tbody>
        <tr>
        <td class="pL10"> 
         </td></tr></tbody>
      </table>
      </td>
      <td class="alignright printTitle">
    	 <%-- <sj:a id="sendButton111Download" cssStyle="height:25px; width:32px"  buttonIcon="ui-icon-arrowthickstop-1-s" cssClass="button" title="Download" button="true"  onclick="downloadHisory();"></sj:a> --%>
     <sj:a id="sendButtonDownload" cssStyle="height:25px; width:32px"  buttonIcon="ui-icon-arrowthickstop-1-s" cssClass="button" title="Download" button="true"  onclick="downloadHisory();"></sj:a>
      </td>
    </tr>
    </tbody>
    </table>
</div>
</div>
</div>
	<s:url id="fullViewActivity" action="fullViewActivity" escapeAmp="false">
	     <s:param name="complID"  value="%{complID}" />
	</s:url>
	
	    <sjg:grid 
		  id="fullViewHistory"
		  href="%{fullViewActivity}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorEdit="false"
          navigatorDelete="false"
          navigatorSearch="true"
          rowList="150,200,250,300,350"
          rowNum="140"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','bw']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          autowidth="true"
          loadonce="true"
          onSelectRowTopics="rowselect"
          >
          
		<s:iterator value="viewPageColumns" id="viewPageColumns" >  
		<s:if test="colomnName=='id'">
		<sjg:gridColumn 
							name="%{colomnName}"
							index="%{colomnName}"
							title="%{headingName}"
							width="%{width}"
							align="%{align}"
							editable="%{editable}"
							search="%{search}"
							hidden="true"
							key="true"
		/>
		</s:if>
		<s:else>
		<sjg:gridColumn 
							name="%{colomnName}"
							index="%{colomnName}"
							title="%{headingName}"
							width="%{width}"
							align="%{align}"
							editable="%{editable}"
							formatter="%{formatter}"
							search="%{search}"
							hidden="%{hideOrShow}"
		/>
		</s:else>
		</s:iterator>  
</sjg:grid>
</body>
</html>