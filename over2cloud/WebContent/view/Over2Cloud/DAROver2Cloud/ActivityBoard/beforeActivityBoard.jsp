<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString(); %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="<s:url value="/js/task_js/task.js"/>"></script>
<script type="text/javascript">
function onChangeData()
{
	var allot=$("#allotment").val();
	var fdate=$("#fromDate").val();
	var todate=$("#toDate").val();
	var taskType=$("#taskType").val();
	var taskP=$("#taskPriority").val();
	var searching=$("#searching").val();
	var taskStatus=$("#taskStatus").val();
	var searchBy=$("#searchBy").val();
	var searchByName=$("#searchByName").val();
	var mileStone=$("#mileStone").val();
	var register=$("#assignBy").val();
	var time=$("#timeStatus").val();
	var client=$("#clientFor").val();
	//var register=$("#assignBy option:selected").val();
	
//	alert("mile stone:::"+mileStone);
	if (allot=='Alloted') 
	{
		$('#div1').show();
		$('#div2').hide();
		$('#darDiv').show();
		$('#allotDiv').hide();
		
	} 
	else 
	{
		$('#div1').hide();
		$('#div2').show();
		$('#darDiv').hide();
		$('#allotDiv').show();
	}
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/DAROver2Cloud/activityData.action?allot="+allot+"&fromdate="+fdate+"&todate="+todate+"&taskType="+taskType+"&taskPriority="+taskP+"&search="+searching+"&taskStatus="+taskStatus+"&searchBy="+searchBy+"&searchByName="+searchByName+"&mileStone="+mileStone+"&allotedBy="+register+"&timeStatus="+time+"&clientfor="+client,
	    success : function(data) {
       $("#"+"viewActivity").html(data);
	},
	  error: function() {
          alert("error");
      }
	 });
}
onChangeData();

function projectNameFormatt(cellvalue, options, rowObject) 
{
    return "<a href='#' onClick='projectNameFormattChange("+rowObject.id+");' ><font color='blue'>"+cellvalue+"</font></a>";

}
function projectNameFormattChange(id) 
{
    var taskId = jQuery("#grid1234").jqGrid('getCell',id,'task.taskname');
    
    $("#takeActionGrid").dialog('open');
    $("#takeActionGrid").dialog({title: 'View Details for '+taskId,width: 900,height: 800});
	$("#takeActionGrid").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $("#takeActionGrid").load("<%=request.getContextPath()%>/view/Over2Cloud/DAROver2Cloud/projectDetails?taskId="+id+"&mode=activity");
    
       return false;
}
function fetchResource()
{
	 $("#takeActionGrid").dialog('open');
	 $("#takeActionGrid").dialog({title: 'Available Resources Details ',width: 900,height: 600});
	 $("#takeActionGrid").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	 $("#takeActionGrid").load("<%=request.getContextPath()%>/view/Over2Cloud/DAROver2Cloud/fetchResource");
}
</script>
</head>
<body>
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

<s:form action="download" id="downloadFile" theme="css_xhtml" name="forms">
<s:hidden name="filePath" id="filePath"/>
<s:hidden name="accessType" id="accessType"/>
</s:form>

 <div class="clear"></div>
 <div class="list-icon">
	 <div class="head">Activity Board</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div> 
    <s:select
							id="allotment" 
							list="#{'Unalloted':'Unalloted','Alloted':'Alloted'}"
				            theme="simple"
				            cssStyle="height: 26px;width:108px;margin-top: 5px;"
							cssClass="button"
							onchange="onChangeData();"
				            />
				            <sj:a id="addButton"  button="true" cssStyle="margin-top: 2px;margin-right: 159px;" cssClass="button"  onclick="fetchResource();">Resource</sj:a>
				                 <div id="test"  class="alignright printTitle">
   		<div id="darDiv">
			 <sj:a id="darSubmitt"  button="true" cssStyle="margin-top: -26px;margin-right: 246px;" cssClass="button"  onclick="createTaskType();">Dar Submitt</sj:a>   		
   		
   		</div>
   		 
   		 <div id="allotDiv">
		 	<sj:a id="addAllotButton" cssStyle="margin-top: -26px;margin-right: 99px;" button="true"  cssClass="button" onclick="createTaskAllot('activtyBoard');">Allot</sj:a>
   		 </div>
   		 <sj:a id="addButtonReg" cssClass="button" button="true" cssStyle="margin-top: -28px;" buttonIcon="ui-icon-plus" title="Register" onClick="createTask('activtyBoard');">Register</sj:a>
    </div>
   </div>  
<div class="clear"></div>
<div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr>
						  <td class="pL10">
				
					   <sj:a id="searchButton"  title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="searchRow()"></sj:a>
					   <sj:a id="refButton"  title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px"  cssClass="button" button="true"  onclick="reloadRow()"></sj:a>
					 
					   <sj:datepicker id="fromDate" name="fromDate" cssClass="button" onchange="onChangeData();" cssStyle="height: 14px;width: 59px;margin-left: 4px;" value="%{fromdate}"  readonly="true"  size="20" changeMonth="true" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select From Date"/>
					   <sj:datepicker id="toDate" name="toDate" cssClass="button" onchange="onChangeData();" cssStyle="height: 14px;width: 59px;margin-left: 2px;" value="%{todate}" readonly="true"  size="20" changeMonth="true" onchange="getDateSearchData(this.value)" changeYear="true"  yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select To Date"/>
 
	
						<s:select
							id="taskType" 
							list="listtask"
							headerKey="-1"
							headerValue="Task Type"
				            theme="simple"
				            cssStyle="height: 26px;width:108px;"
							cssClass="button"
							onchange="onChangeData();"
				            />
				            <div id="div2">
								<s:select
									id="assignBy" 
									name="allotedBy"
									list="registerBY"
									headerKey="-1"
									headerValue="Register By"
						            cssStyle="height: 26px;margin-left: 383px;margin-top: -26px;"
						            theme="simple" 
									cssClass="button"
									onchange="onChangeData();"
						            />
				            </div>
				         <s:select 
							id="taskPriority" 
							list="{'Low','High','Medium'}"
							headerKey="-1"
							headerValue="Task Priority"
							cssStyle="height: 26px;margin-top: -26px;margin-left: 499px;"
				            theme="simple" 
							cssClass="button"
							onchange="onChangeData();"
							/>
							 <s:select  
						    	id					=		"clientFor"
						    	name				=		"clientFor"
						    	headerKey           =       "-1"
					    		headerValue         =		"All Client"
						    	list				=		"#{'PC':'Prospect Client','EC':'Existing Client','PA':'Prospect Associate','EA':'Existing Associate','IN':'Internal','N':'Other'}"
				      		    theme  				=		"simple"
				      		    cssClass			=		"button"
				      		    cssStyle			=		"height: 28px;width: 110px;"
				      		    onchange="onChangeData();"
				      		 >
				     	     </s:select>
							 <s:textfield id="searching" name="searching" onclick="onChangeData();" cssClass="button" cssStyle="margin-top: -26px;height: 14px;width:80px;" Placeholder="Task Name" onchange="getSearchData(this.value)"/>
									    	
							</td>
				
					      </tr></tbody>
					  </table>
				  </td>
				  <td class="alignright printTitle">
				  <div id="div1" style="display: none;"> 
				   <s:select
							id="mileStone" 
							headerKey="-1"
							headerValue="All Milestone"
							list="#{'Allotment':'Allotment','Initiation':'Initiation','Completion':'Completion','Technical':'Technical Review','Functional':'Functional Review'}"
				            theme="simple"
				            cssStyle="height: 26px;width:108px;"
							cssClass="button"
							onchange="onChangeData();"
				            />     
				    		 <s:select
							id="taskStatus" 
							list="#{'Pending':'Pending','Running':'Running','Done':'Completed','Snooze':'Snooze','-1':'All'}"
				            theme="simple"
				            cssStyle="height: 26px;width:108px;"
							cssClass="button"
							onchange="onChangeData();"
				            /> 
				            
		            	 <s:select
							id="searchBy" 
							headerKey="-1"
							headerValue="All Allotments"
							list="allotmentList"
				            theme="simple"
				            cssStyle="height: 26px;width:108px;"
							cssClass="button"
							onchange="onChangeData();"
				            />
				      
				        <s:select
							id="searchByName" 
							headerKey="-1"
							headerValue="All Employee"
							list="searchByname"
				            theme="simple"
				            cssStyle="height: 26px;width:108px;margin-right: 334px;margin-top: -26px;"
							cssClass="button"
							onchange="onChangeData();"
				            />
				             <s:select
							id="timeStatus" 
							headerKey="-1"
							headerValue="All Time"
							list="#{'onTime':'On Time','offTime':'Off Time'}"
				            theme="simple"
				            cssStyle="height: 26px;width:108px;margin-right: 443px;margin-top: -26px;"
							cssClass="button"
							onchange="onChangeData();"
				            />  
						      <sj:a id="techReview"  button="true" cssStyle="margin-right: 330px;" cssClass="button" onclick="projectReview('Technical');">Technical Review</sj:a>
					          <sj:a id="funcReview"  button="true" cssStyle="margin-right: -2px;" cssClass="button" onclick="projectReview('Functional');">Functional Review</sj:a>
				
				         </div>  
				        
			
				
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>	
<div style="overflow: scroll; height: 430px;">
<div id="viewActivity">		
</div>
</div>
</div>
</body>
<script type="text/javascript">
function createTaskType()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $("#data_part").load("<%=request.getContextPath()%>/view/Over2Cloud/DAROver2Cloud/beforeDarAddAction.action");
}
function projectReview(data)
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $("#data_part").load("<%=request.getContextPath()%>/view/Over2Cloud/DAROver2Cloud/beforeReviewdata.action?dataFor="+data);
}

</script>
</html>