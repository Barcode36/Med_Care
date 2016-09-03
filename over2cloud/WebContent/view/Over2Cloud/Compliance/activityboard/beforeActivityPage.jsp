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
<STYLE>
.textfieldbgcolr {
    background-color: #E8E8E8;
}

</style>
<SCRIPT type="text/javascript">
function checkList(cellvalue, options, rowObject) 
{
     var context_path = '<%=request.getContextPath()%>';
     return "<img title='CheckList' src='"+ context_path +"/images/OTM/check.jpg' height='20' width='20' onClick='javascript:checkListFormatter("+rowObject.id+");' /></a>";

}
function checkListFormatter(id) 
{
    var taskName = jQuery("#activityBoardGrid").jqGrid('getCell',id,'ts.taskName');
    var taskId = jQuery("#activityBoardGrid").jqGrid('getCell',id,'comp.compid_prefix');
    
    $("#takeActionGrid").dialog({title: 'Check List for '+taskName+', Task ID: '+taskId,width: 700,height:500});
    $("#takeActionGrid").dialog('open');
    $("#takeActionGrid").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $("#takeActionGrid").load("<%=request.getContextPath()%>/view/Over2Cloud/Compliance/compliance_pages/viewCheckList?complID="+id+"&dataFrom=Task");
    
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
   		return "<a href='#' onClick='viewStatusFormatter("+rowObject.id+");' ><font color='blue'>"+cellvalue+"</font></a>";
     }
}
function viewStatusFormatter(id) 
{
    var taskName = jQuery("#activityBoardGrid").jqGrid('getCell',id,'ts.taskName');
    var taskId = jQuery("#activityBoardGrid").jqGrid('getCell',id,'comp.compid_prefix');
    $("#takeActionGrid").dialog({title: 'Activity Cycle for '+taskName+', Task ID: '+taskId,width: 700,height: 600});
    
    if(typeof id !== 'undefined')
    {
        $("#takeActionGrid").dialog('open');
        $("#takeActionGrid").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    }
    $("#takeActionGrid").load("<%=request.getContextPath()%>/view/Over2Cloud/Compliance/compliance_pages/viewStatusFormatter?complID="+id);
       return false;
}
function viewTAT(cellvalue, options, rowObject) 
{
    return "<a href='#' onClick='viewTATFormatterChange("+rowObject.id+");' ><font color='blue'>"+cellvalue+"</font></a>";

}
function viewTATFormatterChange(id) 
{
    var taskName = jQuery("#activityBoardGrid").jqGrid('getCell',id,'ts.taskName');
    var taskId = jQuery("#activityBoardGrid").jqGrid('getCell',id,'comp.compid_prefix');
    
    $("#takeActionGrid").dialog('open');
    $("#takeActionGrid").dialog({title: 'TAT Status for '+taskName+', Task ID: '+taskId,width: 650,height: 230});
    $("#takeActionGrid").load("<%=request.getContextPath()%>/view/Over2Cloud/Compliance/compliance_pages/viewTATFormatter?complID="+id);
    
       return false;
}
function allotedTo(cellvalue, options, rowObject) 
{
    return "<a href='#' onClick='viewAllotedToFormatter("+rowObject.id+");' ><font color='blue'>"+cellvalue+"</font></a>";

}
function viewAllotedToFormatter(id) 
{
    var taskName = jQuery("#activityBoardGrid").jqGrid('getCell',id,'ts.taskName');
    var taskId = jQuery("#activityBoardGrid").jqGrid('getCell',id,'comp.compid_prefix');
    
    $("#takeActionGrid").dialog('open');
    $("#takeActionGrid").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $("#takeActionGrid").dialog({title: 'Allot To Details for Task ID: '+taskId,width: 350,height: 300});
    $("#takeActionGrid").load("<%=request.getContextPath()%>/view/Over2Cloud/Compliance/compliance_pages/viewAllotedToFormatter?complID="+id);
    
       return false;
}
function allotedBy(cellvalue, options, rowObject) 
{
    return "<a href='#' onClick='viewAllotedBYFormatter("+rowObject.id+");' ><font color='blue'>"+cellvalue+"</font></a>";

}
function viewAllotedBYFormatter(id) 
{
    var taskName = jQuery("#activityBoardGrid").jqGrid('getCell',id,'ts.taskName');
    var taskId = jQuery("#activityBoardGrid").jqGrid('getCell',id,'comp.compid_prefix');
    
    $("#takeActionGrid").dialog('open');
    $("#takeActionGrid").dialog({title: 'Allot By Details',width: 300,height: 200});
    $("#takeActionGrid").load("<%=request.getContextPath()%>/view/Over2Cloud/Compliance/compliance_pages/viewAllotedByFormatter?complID="+id);
    
       return false;
}
function reminder(cellvalue, options, rowObject) 
{
    return "<a href='#' onClick='viewReminderFormatter("+rowObject.id+");' ><font color='blue'>"+cellvalue+"</font></a>";

}
function viewReminderFormatter(id) 
{
    var taskName = jQuery("#activityBoardGrid").jqGrid('getCell',id,'ts.taskName');
    var taskId = jQuery("#activityBoardGrid").jqGrid('getCell',id,'comp.compid_prefix');
    
    $("#takeActionGrid").dialog('open');
    $("#takeActionGrid").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $("#takeActionGrid").dialog({title: 'Upcomming Reminder for '+taskName,width: 400,height: 160});
    $("#takeActionGrid").load("<%=request.getContextPath()%>/view/Over2Cloud/Compliance/compliance_pages/viewReminderFormatter?complID="+id);
    
       return false;
}
function historyFullView(cellvalue, options, rowObject) 
{
    return "<a href='#' onClick='viewFullViewFormatter("+rowObject.id+");' ><font color='blue'>"+cellvalue+"</font></a>";

}
function viewFullViewFormatter(id) 
{
    $("#takeActionGrid1111").dialog('open');
    $("#takeActionGrid1111").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    var taskName = jQuery("#activityBoardGrid").jqGrid('getCell',id,'ts.taskName');
   // var freq = jQuery("#activityBoardGrid").jqGrid('getCell',id,'comp.frequency');
    $("#takeActionGrid1111").dialog({title: 'Task History For '+taskName,width: 850,height: 400});
    $("#takeActionGrid1111").load("<%=request.getContextPath()%>/view/Over2Cloud/Compliance/compliance_pages/viewFullViewFormatter?complID="+id);
       return false;
}

function budgetedView(cellvalue, options, rowObject) 
{
    if(cellvalue=='NA')
    {
        return "<a href='#' ><font color='blue'>"+cellvalue+"</font></a>";
    }
    else
    {
        return "<a href='#' onClick='viewbudgetedFormatter("+rowObject.id+");' ><font color='blue'>"+cellvalue+"</font></a>";
    }
}
function viewbudgetedFormatter(id) 
{
    $("#takeActionGrid").dialog('open');
    $("#takeActionGrid").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $("#takeActionGrid").dialog({title: 'Budgeted History',width: 300,height: 200});
    $("#takeActionGrid").load("<%=request.getContextPath()%>/view/Over2Cloud/Compliance/compliance_pages/viewBudgetedFormatter?complID="+id);
       return false;
}
function complianceAction()
{
	var id;
	id = $("#activityBoardGrid").jqGrid('getGridParam','selarrrow');
	var status = jQuery("#activityBoardGrid").jqGrid('getCell',id,'comp.actionStatus');
	if(id.length==0 )
	{
	     alert("Please select atleast one check box...");        
	     return false;
	}
	else if(id.length>1)
	{
		 alert("Please select only one check box...");        
	     return false;
	}
	else if(status.indexOf("Done")>=0)
	{
		alert("Task is Already Done...");        
	     return false;
	}
    else
	{
	  var taskName = jQuery("#activityBoardGrid").jqGrid('getCell',id,'ts.taskName');
	    var taskId = jQuery("#activityBoardGrid").jqGrid('getCell',id,'comp.compid_prefix');
	    $("#takeActionGrid").load("<%=request.getContextPath()%>/view/Over2Cloud/Compliance/compliance_pages/beforeTakeAction?complID="+id);
	    $("#takeActionGrid").dialog('open');
	    $("#takeActionGrid").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	    $("#takeActionGrid").dialog({title: 'Take Action for '+taskName+', Task ID: '+taskId,width: 1200,height: 400});
	    
	       return false;
	}
}
function downloadAction()
{
	var sel_id;
	sel_id = $("#activityBoardGrid").jqGrid('getGridParam','selarrrow');
	if(sel_id=="")
	{
	     alert("Please select atleast one check box...");        
	     return false;
	}
	else
	{
		$("#takeActionGrid").dialog({title: 'Check Column ',width: 350,height: 600});
		$("#takeActionGrid").dialog('open');
		$("#takeActionGrid").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
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
		id = $("#activityBoardGrid").jqGrid('getGridParam','selarrrow');
		if(id.length==0)
	 	{
	 	     alert("Please select atleast one check box...");        			    
	 	     return false;
	 	}
		else if(id.length>1)
		{
			 alert("Please select only one check box...");        			    
	 	     return false;
		}
	 	else
	 	{
	 		$("#printDialog").dialog('open');
	 		 $("#printDialog").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif>");
	 		//$("#dialogPrint").load("over2cloud/view/Over2Cloud/Compliance/compliance_pages/printCompInfo?deptName="+deptName.split(" ").join("%20")+"&taskName="+taskName.split(" ").join("%20")+"&frequency="+frequency.split(" ").join("%20")+"&dueDate="+dueDate.split(" ").join("%20")+"&reminderFor="+reminderFor.split(" ").join("%20")+"&dueTime="+dueTime.split(" ").join("%20")+"&taskType="+taskType.split(" ").join("%20")+"&status="+status.split(" ").join("%20"));
	 		$("#printDialog").load("<%=request.getContextPath()%>/view/Over2Cloud/Compliance/compliance_pages/printCompInfo?id="+id);
		}
});
 

function editCompliance()
{
	var id;
	id = $("#activityBoardGrid").jqGrid('getGridParam','selarrrow');
	var status = jQuery("#activityBoardGrid").jqGrid('getCell',id,'comp.actionStatus');
	if(id.length==0 )
	{
	     alert("Please select atleast one check box...");        
	     return false;
	}else if(id.length>1)
	{
		 alert("Please select only one check box...");        
	     return false;
	}
	else if(status.indexOf("Done")>=0)
	{
		 alert("Task is Already Done...");        
	     return false;
	}
    else
    {
    	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
        $.ajax
        (
            {
                type : "post",
                url : "/over2cloud/view/Over2Cloud/Compliance/compliance_pages/getCompliance4EditByEdit.action?selectedId="+id,
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
};
    
function openDialog()
{
    var sel_id;
    sel_id = $("#activityBoardGrid").jqGrid('getGridParam','selarrrow');
    if(sel_id=="")
    {
         alert("Please select atleast one check box...");        
         return false;
    }
    else
    {
        $("#takeActionGrid").dialog({title: 'Send Mail ',width: 950,height: 250});
        $("#takeActionGrid").dialog('open');
        $("#takeActionGrid").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
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

function gridActivityData()
{
     var fromDate=$("#fromDate").val();
     var toDate=$("#toDate").val();
     var actionStatus=$("#actionStatus").val();
     var periodSort=$("#periodSort").val();
     var deptId=$("#deptId").val();
     var freque =$("#frequency").val();
     var budget=$("#budgeted").val();
     var searching=$("#searching").val();
     var shareStatus=$("#shareStatus").val();
     var timmings=$("#timmings").val();
     var type=$("#taskType").val();
     var taskname=$("#taskName").val();
    
    $.ajax({
        type : "post",
        url : "view/Over2Cloud/Compliance/compliance_pages/activityDashboardView.action?fromDate="+fromDate+"&toDate="+toDate+"&actionStatus="+actionStatus+"&searchPeriodOn="+periodSort+"&deptId="+deptId+"&frequency="+freque+"&budget="+budget+"&searching="+searching+"&timmings="+timmings+"&shareStatus="+shareStatus+"&type="+type+"&taskName="+taskname,
        success : function(data) 
        {
            $.ajax({
                type : "post",
                url : "view/Over2Cloud/Compliance/compliance_pages/fetchCounters.action?fromDate="+fromDate+"&toDate="+toDate+"&actionStatus="+actionStatus+"&searchPeriodOn="+periodSort+"&deptId="+deptId+"&frequency="+freque+"&budget="+budget+"&searching="+searching+"&timmings="+timmings+"&shareStatus="+shareStatus+"&type="+type+"&taskName="+taskname,
                success : function(testdata) 
                {
                    $("#pendingDiv").val(testdata.Pending);
                    $("#snoozeDiv").val(testdata.Snooze);
                    $("#resolveDiv").val(testdata.Done);
                    $("#totalDiv").val(testdata.Total);
                },
                error: function()
                {
                    alert("error");
                }
             });
            $("#resultDiv").html(data);
        },
        error: function()
        {
            alert("error");
        }
     });
}
gridActivityData();

function getProductivty()
{
	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Compliance/compliance_pages/beforeEmployeeProductivity.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
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

<sj:dialog
          id="printDialog"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Print"
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
     <div class="head">Operations</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
     <div class="head">
    <div class="head">
       Activity Pending: <s:textfield id="pendingDiv" cssClass="textfieldbgcolr" cssStyle="width: 21px;" disabled="true"/>
        Snooze: <s:textfield id="snoozeDiv" cssClass="textfieldbgcolr" cssStyle="width: 21px;" disabled="true"/>
        Done: <s:textfield id="resolveDiv" cssClass="textfieldbgcolr" cssStyle="width: 21px;" disabled="true"/>
        Total:<s:textfield id="totalDiv" cssClass="textfieldbgcolr" cssStyle="width: 21px;" disabled="true"/>
    </div>
    </div> 
     <div id="test"  class="alignright printTitle">
      <sj:a  cssStyle="margin-top: 5px;" title="Productivity" onclick="getProductivty();"><img src="images/productivity.jpg" width="32px" height="27px" /></sj:a>
   		 <sj:a id="configure" cssClass="button" button="true" cssStyle="margin-top: -9px;" buttonIcon="ui-icon-plus" title="Configure" onClick="complianceConfigure();">Configure</sj:a>
    </div>
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
          position="center"
          >
</sj:dialog>

<sj:dialog
          id="takeActionGrid1111"
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
        <tbody><tr></tr><tr><td></td></tr><tr><td> 
            <table class="floatL" border="0" cellpadding="0" cellspacing="0">
        <tbody>
        <tr>
        <td class="alignright printTitle"> 
         <sj:datepicker cssStyle="height: 13px;margin-left: 9px; width: 60px;" onchange="gridActivityData();"  cssClass="button" id="fromDate" name="fromDate" size="20" value="%{fromDate}" maxDate="0"   readonly="true"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="From Date"/>
         <sj:datepicker cssStyle="height: 13px; width: 60px;" onchange="gridActivityData();" cssClass="button" id="toDate" name="toDate" size="20" value="%{toDate}"   readonly="true" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="To Date"/>
         
        <s:select  
                id                    =        "periodSort"
                name                =        "searchPeriodOn"
                list                =        "#{'All':'All Date','actionTakenDate':'Action Taken Date','dueDate':'Due Date'}"
                  theme                =        "simple"
                  cssClass            =        "button"
                  cssStyle            =        "height: 26px;width: 110px;"
                  onchange="gridActivityData();"
               >
               </s:select>
              
                <s:select  
                id                    =        "shareStatus"
                name                =        "shareStatus"
                list                =        "#{'ShareMe':'For Me','ShareBy':'By Me'}"
                  theme                  =        "simple"
                  cssClass            =        "button"
                  cssStyle            =        "height: 26px;width: 90px;"
                  onchange="gridActivityData();"
               >
              </s:select>
              
              
               <s:select  
                id                    =        "deptId"
                name                =        "deptId"
                list                =        "deptName"
                headerKey="All"
                headerValue="All Dept"
                  theme                  =        "simple"
                  cssClass            =        "button"
                  cssStyle            =        "height: 26px;width: 100px;"
                  onchange="gridActivityData(); commonSelectAjaxCall('compl_task_type','id','taskType','departName',this.value,'','','taskType','ASC','taskType','All Task Type');"
               >
              </s:select>
              <s:select  
			              id                    =        "taskType"
			              name                =        "taskType"
			              list                =        "taskTypeMap"
			            cssClass            =        "button"
			             theme                =        "simple"
			            cssStyle            =        "height: 26px;width: 125px;"
			                 onchange="gridActivityData();commonSelectAjaxCall('compliance_task','id','taskName','taskType',this.value,'departName','deptName','taskName','ASC','taskName','All Task Name');"
			          >
               </s:select>
                <s:select  
                                      id                    =        "taskName"
                                      name                =        "taskName"
                                      list                =        "{'No Data'}"
                                      headerKey            =        "All"
                                      headerValue            =        "All Task Name" 
                                    cssClass            =        "button"
                                     theme                =        "simple"
                                     cssStyle="height: 28px;width: 130px;"
                                      onchange="gridActivityData();"
                                  >
                          </s:select>
                          
                          
              <td> 
             
              </td>
              
           </tr>
     </tbody>
        </table>
        
        <td class="alignright printTitle">
           
            <sj:a id="sendButton111Download" cssStyle="height:25px; width:32px"  buttonIcon="ui-icon-arrowthickstop-1-s" cssClass="button" title="Download" button="true"  onclick="downloadAction();"></sj:a>
            <sj:a  button="true" cssStyle="height:25px; width:32px"  cssClass="button" buttonIcon="ui-icon-print" title="Print" onClickTopics="getPrintData"></sj:a>
            <sj:a id="sendMailButton"  cssClass="button" buttonIcon="ui-icon-mail-closed" button="true" cssStyle="height:25px; width:32px" title="Send Mail" onclick="openDialog();"></sj:a>
            <sj:a button="true" cssClass="button" onclick="editCompliance()">Edit</sj:a>
            <sj:a id="action" cssClass="button" button="true" title="Action" onClick="complianceAction();">Action</sj:a>
            
        </td>
        </tr>
    
        <tr><td> 
            <table class="floatL" border="0" cellpadding="0" cellspacing="0">
        <tbody>
        <tr>
        <td class="alignright printTitle"> 
           <s:select  
            id                    =        "actionStatus"
            name                =        "actionStatus"
            list				=		"#{'All':'All Status','Done':'Done','Pending':'Pending','Snooze':'Snooze','Recurring':'Recurring','Reschedule':'Reschedule'}"
              theme                =        "simple"
              cssClass            =        "button"
            cssStyle            =        "height: 26px;margin-left: 14px;width: 110px;"
              onchange="gridActivityData();"
           >
              </s:select>
        <s:select  
            id                    =        "frequency"
            name                =        "frequency"
            list                =        "#{'All':'All Frequency','OT':'One-Time','D':'Daily','W':'Weekly','M':'Monthly','BM':'Bi-Monthly','Q':'Quaterly','HY':'Half Yearly','Y':'Yearly','O':'Other'}"
              theme                =        "simple"
              cssClass            =        "button"
              cssStyle            =        "height: 26px;margin-top: 3px;margin-left: -1px;width: 125px;"
              onchange="gridActivityData();"
           >
          </s:select>
               <s:select  
            id                    =        "budgeted"
            name                =        "budgeted"
            list                =        "#{'All':'All Budget','B':'Budgeted','NB':'Non-Budgeted'}"
              theme                =        "simple"
              cssClass            =        "button"
              cssStyle            =        "height: 26px;margin-top: 3px;width: 115px;"
              onchange="gridActivityData();"
           >
          </s:select>
          <s:select  
            id                    =        "timmings"
            name                =        "timmings"
            list                =        "#{'All':'All Time','onTime':'On Time','offTime':'Off Time'}"
              theme                =        "simple"
              cssClass            =        "button"
              cssStyle            =        "height: 26px;margin-top: 3px;width: 100px;"
              onchange="gridActivityData();"
           >
          </s:select>
           
              </td>
              <td class="alignright printTitle">
            <s:textfield 
            id="searching" 
            name="searching" 
            onclick="gridActivityData();" 
            theme="simple" cssClass="button" 
            cssStyle="margin-left:-7px;margin-top: 5px;height: 15px;width:70px;"
             Placeholder="Task Name" onchange="getSearchData(this.value)"/>
        </td>
             </tr>
         </tbody>
        </table>
        </tr>
    
        

</tbody>

</table></div>
</div>

<div style="overflow: scroll; height: 430px;">
<div id="resultDiv"></div>
</div>
</div>
</div>
</body>
</html>