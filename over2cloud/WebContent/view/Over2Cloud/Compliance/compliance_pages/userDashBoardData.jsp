<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}" />
<script type="text/javascript" src="<s:url value="/js/compliance/compliance.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<SCRIPT type="text/javascript">
function complTakeAction(cellvalue, options, rowObject) 
{
   return "<a href='#' onClick='complianceTakeAction("+rowObject.id+")'><b>Take Action</b></a>";
}
function complianceTakeAction(valuepassed) 
{
    $("#takeActionGrid").load("<%=request.getContextPath()%>/view/Over2Cloud/Compliance/compliance_pages/beforeTakeAction?complID="+valuepassed);
    $("#takeActionGrid").dialog('open');
       return false;
}
function complianceAction()
{
    var valuepassed;
    valuepassed = $("#gridedittable").jqGrid('getGridParam','selarrrow');
    
    if(valuepassed=="")
    {
         alert("Please select atleast one check box...");        
         return false;
    }
    $("#takeActionGrid").load("<%=request.getContextPath()%>/view/Over2Cloud/Compliance/compliance_pages/beforeTakeAction?complID="+valuepassed);
    $("#takeActionGrid").dialog('open');
       return false;
}
function downloadAction()
{
    var sel_id;
    sel_id = $("#gridedittable").jqGrid('getGridParam','selarrrow');
    if(sel_id=="")
    {
         alert("Please select atleast one check box...");        
         return false;
    }
    else
    {
        $("#takeActionGrid").dialog({title: 'Check Column ',width: 350,height: 600});
        $("#takeActionGrid").dialog('open');
        $.ajax
        ({
            type : "post",
            url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/sendMailConfirmAction.action?selectedId="+sel_id,
            success : function(data) 
            {
                 $("#takeActionGrid").html(data);
            },
            error: function() 
            {
                alert("error");
            }
         });
    }
}
$.subscribe('getPrintData', function(event,data) 
{
    var id;
    id = $("#gridedittable").jqGrid('getGridParam','selarrrow');
    if(id=="")
    {
         alert("Please select atleast one check box...");                        
         return false;
    }
    else
    {
         var deptName = jQuery("#gridedittable").jqGrid('getCell',id,'dept.deptName');
        var taskName = jQuery("#gridedittable").jqGrid('getCell',id,'ctn.taskName');
        var taskType = jQuery("#gridedittable").jqGrid('getCell',id,'ctyp.taskType');
        var frequency = jQuery("#gridedittable").jqGrid('getCell',id,'comp.frequency');
        var dueDate = jQuery("#gridedittable").jqGrid('getCell',id,'comp.dueDate');
        var dueTime = jQuery("#gridedittable").jqGrid('getCell',id,'comp.dueTime');
        var reminderFor = jQuery("#gridedittable").jqGrid('getCell',id,'comp.reminder_for');
        var status = jQuery("#gridedittable").jqGrid('getCell',id,'actionStatus');
        $("#printSelect").load("<%=request.getContextPath()%>/view/Over2Cloud/Compliance/compliance_pages/printCompInfo?deptName="+deptName.split(" ").join("%20")+"&taskName="+taskName.split(" ").join("%20")+"&frequency="+frequency.split(" ").join("%20")+"&dueDate="+dueDate.split(" ").join("%20")+"&reminderFor="+reminderFor.split(" ").join("%20")+"&dueTime="+dueTime.split(" ").join("%20")+"&taskType="+taskType.split(" ").join("%20")+"&status="+status.split(" ").join("%20"));
        $("#printSelect").dialog('open');
    }
 });

function openDialog()
{
    var sel_id;
    sel_id = $("#gridedittable").jqGrid('getGridParam','selarrrow');
    if(sel_id=="")
    {
         alert("Please select atleast one check box...");        
         return false;
    }
    else
    {
        $("#takeActionGrid").dialog({title: 'Send Mail ',width: 950,height: 250});
        $("#takeActionGrid").dialog('open');
        $.ajax
        ({
            type : "post",
            url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/beforeSendMail.action?selectedId="+sel_id+"&actionName=sendMailConfirmAction",
            success : function(data) 
            {
                 $("#takeActionGrid").html(data);
            },
            error: function() 
            {
                alert("error");
            }
        });
    }
}

$.subscribe('editCompliance',function(event,data)
        {
    alert("edit");
            var sel_id;
            sel_id = $('#gridedittable').jqGrid('getGridParam','selarrrow');
            if(sel_id=='')
            {
                  alert("Please select atleast one check box...");        
                  return false;
            }
            else
            {
                $.ajax
                (
                    {
                        type : "post",
                        url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/getCompliance4EditByEdit.action?selectedId="+sel_id,
                        success : function(data)
                        {
                            $('#data_part').html(data);
                        },
                        error : function()
                        {
                            alert("Error");
                        }    
                    }
                );
            }
        }
    );


function getActionData(freq,due,deptNam,taskTyp,taskNam)
{
     var frequency=$("#"+freq).val();
     var dueDate=$("#"+due).val();
     var deptName=$("#"+deptNam).val();
     var taskType = $("#"+taskTyp).val();
     var taskName = $("#"+taskNam).val();
     $.ajax
     ({
        type : "post",
        url : "view/Over2Cloud/Compliance/compliance_pages/logedUserComplDashboard.action?frequency="+frequency+"&dueDate="+dueDate+"&deptName="+deptName+"&taskType="+taskType+"&taskName="+taskName,
        success : function(subdeptdata) 
        {
               $("#"+"data_part").html(subdeptdata);
               $("#dueDate").val("");
        },
        error: function() 
        {
            alert("error");
        }
     });
}

function ViewCompletionTip(cellvalue, options, rowObject) 
{
    return "<a href='#' title='Download' onClick='CompletionTip("+rowObject.id+")'>View</a>";
}
function ViewKR(cellvalue, options, rowObject) 
{
    return "<a href='#' title='Download' onClick='KR("+rowObject.id+")'>View</a>";
}

function CompletionTip(taskId) 
{
    $.ajax({
        type : "post",
        url : "view/Over2Cloud/Compliance/compl_task_page/ViewCompletionTipKRAction.action?dataId="+taskId+"&dataFor=CompletionTip&module=Action",
        success : function(data) 
        {
            $("#krCompletionTip").dialog({title: 'Completion Tip',width: 300,height: 200});
            $("#krCompletionTip").dialog('open');
            $("#krCompletionTip").html(data);
        },
        error: function()
        {
            alert("error");
        }
     });
}
function KR(taskId) 
{
    $.ajax({
        type : "post",
        url : "view/Over2Cloud/Compliance/compl_task_page/ViewCompletionTipKRAction.action?dataId="+taskId+"&dataFor=KRName&module=Action",
        success : function(data) 
        {
        $("#krCompletionTip").dialog({title: 'KR Name',width: 300,height: 200});
        $("#krCompletionTip").dialog('open');
        $("#krCompletionTip").html(data);
        },
        error: function()
        {
            alert("error");
        }
     });
}





</SCRIPT>
</head>
<body>
<sj:dialog
          id="krCompletionTip"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Take Action on Operation Task"
          modal="true"
          closeTopics="closeEffectDialog"
          width="700"
          height="350"
          >
</sj:dialog>
<center>
          <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 50%; height: 10%; font-size: small; border: 0px solid red; border-radius: 6px;">
            <div id="errZone" style="float:center; margin-left: 7px"></div>
          </div>
    </center>
<sj:dialog id="printSelect" title="Print Ticket" autoOpen="false"  modal="true" height="320" width="700" showEffect="drop"></sj:dialog>
<div class="middle-content">
<div class="list-icon">
     <div class="head">
     Operation Task</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
     <div class="head">Action</div> 
</div>

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
<div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
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
                          <sj:a id="action" cssClass="button" cssStyle="height: 25px;" button="true" title="Action" onClick="complianceAction();">Action</sj:a> 
                          
                          <s:select  
                                      id                    =        "deptName"
                                      name                =        "deptName"
                                      list                =        "deptNameList"
                                      headerKey            =        "-1"
                                      headerValue            =        "Contact Sub-Type" 
                                    cssClass            =        "button"
                                     theme                =        "simple"
                                     cssStyle="height: 28px;width: 120px;"
                                     onchange="commonSelectAjaxCall('compl_task_type','id','taskType','departName',this.value,'','','taskType','ASC','taskType','Task Type');"
                                  >
                          </s:select>
                          
                          <s:select  
                                      id                    =        "taskType"
                                      name                =        "taskType"
                                      list                =        "{'No Data'}"
                                      headerKey            =        "-1"
                                      headerValue            =        "Task Type" 
                                    cssClass            =        "button"
                                     theme                =        "simple"
                                     cssStyle="height: 28px;width: 115px;"
                                     onchange="commonSelectAjaxCall('compliance_task','id','taskName','taskType',this.value,'departName','deptName','taskName','ASC','taskName','Task Name');"
                                  >
                          </s:select>
                          
                          <s:select  
                                      id                    =        "taskName"
                                      name                =        "taskName"
                                      list                =        "{'No Data'}"
                                      headerKey            =        "-1"
                                      headerValue            =        "Task Name" 
                                    cssClass            =        "button"
                                     theme                =        "simple"
                                     cssStyle="height: 28px;width: 115px;"
                                  >
                          </s:select>
                          
                          <s:select  
                                      id                    =        "frequency"
                                      name                =        "frequency"
                                      list                =        "#{'OT':'One-Time','D':'Daily','W':'Weekly','M':'Monthly','BM':'Bi-Monthly','Q':'Quaterly','HY':'Half Yearly','Y':'Yearly'}"
                                      headerKey            =        "-1"
                                      headerValue            =        "Frequency" 
                                    cssClass            =        "button"
                                     theme                =        "simple"
                                     cssStyle="height: 28px;width: 111px;"
                                  >
                         </s:select>
                         <sj:datepicker id="dueDate" name="dueDate" cssClass="button" cssStyle="width: 111px;border: 1px solid #e2e9ef;border-top: 2px solid #cbd3e2;padding: 1px;font-size: 12px;outline: medium none;height: 25px; margin-top: -30px;text-align: center;" readonly="true"  size="20" changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Due Date"/>
                         <sj:a button="true" cssClass="button" cssStyle="height: 25px;" onclick="getActionData('frequency','dueDate','deptName','taskType','taskName');">OK</sj:a>
                         <sj:a button="true" cssClass="button" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" title="Refresh" onclick="getActionData('frequency','dueDate','deptName','taskType','taskName');"></sj:a>
                         <sj:a button="true" cssClass="button"  cssStyle="height: 25px;" onClickTopics="editCompliance">Edit</sj:a>
                         </td></tr></tbody>
                      </table>
                  </td>
                  <td class="alignright printTitle">
                    <sj:a id="sendButton111" buttonIcon="ui-icon-arrowthickstop-1-s" cssClass="button" button="true" title="Download" cssStyle="height:25px; width:32px" onclick="downloadAction();"></sj:a>
                    <sj:a button="true" cssClass="button" buttonIcon="ui-icon-print" cssStyle="height:25px; width:32px" title="Print" onClickTopics="getPrintData"></sj:a>
                     <sj:a id="sendMailButton1111"  cssClass="button" buttonIcon="ui-icon-mail-closed" button="true" cssStyle="height:25px; width:32px" title="Send Mail" onclick="openDialog();"></sj:a>
                  </td>
            </tr>
        </tbody>
    </table>
</div>
</div>
<s:hidden id="empID" value="%{departId}"/>
<s:hidden id="frequency" value="%{frequency}"/>
<s:hidden id="dueDate" value="%{dueDate}"/>
<div style="overflow: scroll; height: 430px;">
    <s:url id="veiwTaskType" action="getUpCommingUserCompliancesOnDash" escapeAmp="false">
    <s:param name="departId"  value="%{departId}" />
    <s:param name="frequency"  value="%{frequency}" />
    <s:param name="dueDate"  value="%{dueDate}" />
    <s:param name = "deptName" value = "%{deptName}"/>
    <s:param name = "taskType" value = "%{taskType}"/>
    <s:param name = "taskName" value = "%{taskName}"/>
    </s:url>
    
        <sjg:grid 
          id="gridedittable"
          href="%{veiwTaskType}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorSearch="true"
          navigatorDelete="false"
          navigatorEdit="false"
          groupField="['actionStatus']"
          groupColumnShow="[true]"
          groupCollapse="true"
          groupText="['<b>{0}-{1}</b>']"
          rowList="15,20,25,30"
          rowNum="15"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          rownumbers="true"
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400}"
          navigatorViewOptions="{height:230,width:400}"
          editurl="%{viewModifyTaskType}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          shrinkToFit="false"
          autowidth="true"
          loadonce="true"
          multiselect="true"
          onSelectRowTopics="rowselect"
          >
        <s:iterator value="masterViewProp" id="masterViewProp" >  
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
        </s:iterator>  
        
        <sjg:gridColumn 
                name="completion_tip"
                index="completion_tip"
                title="Completion Tip"
                width="100"
                align="center"
                editable="false"
                formatter="ViewCompletionTip"
                search="false"
                hidden="false"
            />
            
            <sjg:gridColumn 
                name="kr_name"
                index="kr_name"
                title="KR"
                width="100"
                align="center"
                editable="false"
                formatter="ViewKR"
                search="false"
                hidden="false"
            />
        
       </sjg:grid>
</div>
</div>
</div>
</body>
</html>