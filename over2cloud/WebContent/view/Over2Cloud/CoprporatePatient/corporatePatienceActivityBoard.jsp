<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%
	String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();
%>
<%String validApp = session.getAttribute("validApp") == null ? "" : session.getAttribute("validApp").toString();
%>
<%
//////System.out.println("userRightsList ::::" +userRights);
//Code for checking the level of departments or sub departments
String dbName=(String)session.getAttribute("Dbname");
int levelid=1;
String namesofDepts[]=new String[3];
String names=(String)session.getAttribute("deptLevel");
String tempnamesofDepts[]=null;
////deptlevel,dept1Name#dept2Name#
if(names!=null && !names.equalsIgnoreCase(""))
{
	tempnamesofDepts=names.split(",");
	levelid=Integer.parseInt(tempnamesofDepts[0]);
	namesofDepts=tempnamesofDepts[1].split("#");
}

String userTpe=(String)session.getAttribute("userTpe");

%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/cpservice/cpService.js"/>"></script>

<style type="text/css">


.TitleBorder {
	float: left;
    height: 27px;
    border: 1px solid #e0bc27;
    margin: 2px -10px 0px 2px;
    background: white;
    box-shadow: 5px 2px 3px 0px rgba(0, 0, 0, 0.2);
    /* overflow: auto; */
    /* -webkit-transform: rotate(5deg); */
    -moz-transform: rotate(5deg);
    -ms-transform: rotate(5deg);
    transform: rotate(2deg);
    /* background-color: #000; */
    /* border-bottom-left-radius: 35%; */
	
}

.TitleBorderSer {
	float: left;
    height: 27px;
    border: 1px solid #A5E244;
    margin: 2px -10px 0px 2px;
    background: white;
    box-shadow: 5px 2px 3px 0px rgba(0, 0, 0, 0.2);
    /* overflow: auto; */
    /* -webkit-transform: rotate(5deg); */
    -moz-transform: rotate(5deg);
    -ms-transform: rotate(5deg);
    transform: rotate(2deg);
    /* background-color: #000; */
    /* border-bottom-left-radius: 35%; */
	
}

.TitleBorder h1 {
	padding-top: 0px;
	margin: 2px 5px 32px 0px;
	
}

h1 {
	font-size: 12px;
	    font-weight: bold;
}

.circle {
	width: 22px;
    height: 22px;
    border-radius: 60px;
    font-size: 12px;
    color: #fff;
    line-height: 22px;
    text-align: center;
    background: #989A8F;
    margin-left: 65px;
    margin-top: -53px;
      box-shadow: 5px 2px 3px 0px rgba(0, 0, 0, 0.2);
    
}

.circleSer {
	width: 22px;
    height: 22px;
    border-radius: 60px;
    font-size: 12px;
    color: #fff;
    line-height: 22px;
    text-align: center;
    background: #2F7BDA;
    margin-left: 65px;
    margin-top: -20px;
      box-shadow: 5px 2px 3px 0px rgba(0, 0, 0, 0.2);
    
}

.contents {
	width: 21px;
	color: white;
	background: rgb(33, 140, 255);
	box-shadow: 4px 3px 2px 2px rgb(224, 224, 209);
}

.contentsfree {
	width: 21px;
	color: white;
	background: rgb(231, 113, 92);
	box-shadow: 4px 3px 2px 2px rgb(224, 224, 209);
}

#empinfo tr:nth-child(even) {
	background: #CCC
}

#empinfo tr:nth-child(odd) {
	background: #FFF
}

#empinfo1 tr:nth-child(even) {
	background: #CCC
}

#empinfo1 tr:nth-child(odd) {
	background: #CCC
}
</style>
<script type="text/javascript" src="js/cpservice/cpService.js"></script>
<SCRIPT type="text/javascript">
function onloadData()
{
	var searchParameter=$("#wildSearch").val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/beforeCPSView.action?minDateValue="+$("#minDateValue").val()+"&maxDateValue="+$("#maxDateValue").val()+"&feed_status="+$("#feed_status1").val()+"&corp_type="+$("#corp_typee").val()+"&patienceSearch="+$("#patienceSearch").val()+"&corp_name="+$("#corp_namee").val()+"&services="+$("#servicese").val()+"&ac_manager="+$("#ac_managerr").val()+"&med_location="+$("#med_locationn").val()+"&wildSearch="+searchParameter+"&searchData="+searchData+"&service_manager="+$("#service_manager").val()+"&status="+$("#status").val(),
	    success : function(data) {
    $("#"+"viewCPS").html(data);
   // loadDropDownDynamic($("#minDateValue").val(),$("#maxDateValue").val());
	},
	   error: function() {
            alert("error");
        }
	 });
	getStatusCounter();
}


</SCRIPT>

<SCRIPT type="text/javascript">
 
function gridCurrentPage()
{
	var sourceId='service_manager'
	$("#"+sourceId+" option:selected").val('-1');
	$("#"+sourceId+" option:selected").text('Service Mng.');
	//corporatePatientView('load');
	onloadData();
}
function wildSearch()
{
	 delay(function()
	{
	  onloadData();
	    }, 3000 );
}
var delay = (function(){
	  var timer = 0;
	  return function(callback, ms){
	    clearTimeout (timer);
	    timer = setTimeout(callback, ms);
	  };
	})();

/*$.subscribe('colorEscalation',function(event,data)
{
	console.log(cooOfficeTicketColorCoding);
	for(var i=0;i<cooOfficeTicketColorCoding.length;i++){
		$("#"+cooOfficeTicketColorCoding[i]).css('background','rgb(255, 102, 179)');
	}
	cooOfficeTicketColorCoding=[];
	for(var i=0;i<cmdOfficeTicketColorCoding.length;i++)
	{
		$("#"+cmdOfficeTicketColorCoding[i]).css('background','rgb(255, 101, 51)');
	}
	cmdOfficeTicketColorCoding=[];
});
var cooOfficeTicketColorCoding=[];
var cmdOfficeTicketColorCoding=[];
CC00CC*/
function priorityVal(cellvalue, options, rowObject) 
{
 	if(cellvalue=="VVIP")
	{
		return "<span class='cellWithoutBackground'   style='display: block;background-color:#E60000';><font color='#ffffff'><b>"+cellvalue+"</b></font></span>";
	}
	/*else if(cellvalue=="VVIP") <span class='cellWithoutBackground' style='background-color:#E60000'>"+cellvalue+"</span></a>";
	{
		return "<font color='#CC00CC'><b>"+cellvalue+"</b></font>";
	}*/
	else
	{
		return "<font color='black'>"+cellvalue+"</font>";

		//$ cellvalue.css('text-shadow: 2px 2px #ff0000');
		
	}


}
// for color full row 

function viewStatus(cellvalue, options, row)
 {
		if(row.stage=='2')
		{	
		 	if(cellvalue=='Pending')
			{
				escalationColorPending.push(row.id);
			}
			else if(cellvalue=='Resolved' && row.level=='Level1')
			{
				colorClosed.push(row.id);
			}
			if(row.level=='Level1')
			{
			}
			else 
			{
				escColorL2.push(row.id);
			}	
		 }	
	return '<a href="#" onClick="viewStatusDetails('+row.feedStatId+','+row.id+','+row.ratingFlag+');" ><b><font color="blue">'+cellvalue+'</font></b></a>';
 }





var escalationColorPending=[];
var colorClosed=[];
var escColorL2=[];

function viewPatientHistory(cellvalue, options, rowObject) 
{
	return "<a href='#' title='View Patience History' onClick='viewPatienceHistoryOnClick("+rowObject.id+")'><font color='blue'>"+cellvalue+"</font></a>";
}

function viewPatienceHistoryOnClick(id)
{
	
	var patientname = jQuery("#gridedittableeee").jqGrid('getCell',id,'patient_name');
	$("#takeActionDilouge").dialog({title: 'View for '+patientname,width: 500,height: "auto"});
	$("#takeActionDilouge").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");

	$("#takeActionDilouge").dialog('open');
	
	
  $.ajax
	({
	type : "post",
	url  : "view/Over2Cloud/CorporatePatientServices/cpservices/beforePatienceActivityHistoryData?id="+id,
	success : function(data)
	{
	$("#takeActionDilouge").html(data);
	},
	error : function()
	{
	alert("Error on data fetch");
	} 
	});
}


function maximizeWindow(id)
	{
	if(id=='both')
		{
		$("#both").show();
		$("#grid").hide();
		$("#lodge").hide();
 		$("#CPSAdd").show();
		$("#gridDiv").show();
 		}
	else if(id=='grid')
		{
		$("#grid").show();
		$("#both").hide();
		$("#lodge").hide();
		$("#gridDiv").show();
 		$("#CPSAdd").hide();

		}
	else if(id=='lodge')
	{
		$("#lodge").show();
		$("#grid").hide();
		$("#both").hide();
			$("#CPSAdd").show();
			$("#gridDiv").hide();
			
	}
}
<%if(userRights.contains("lodgeBoardCPS_VIEW")) 
{%>
	newEntryCPS();
	maximizeWindow('lodge');

<%}%>

<%if(userRights.contains("ticketLodgeGridCPS_VIEW")) 
	
{%>
	newEntryCPS();
	getStatusCounter();
	corporatePatientView("load");
	

<%}%>
<%if(userRights.contains("gridLodgeCPS_VIEW")) 
	
{%>
	getStatusCounter();
	corporatePatientView("load");
	maximizeWindow('grid');
<%}%>
function priorityClick(data)
{
		onloadData(data);
} 
$.subscribe('editGrid',function(event,data)
		{
		 	var rowSel=$('#gridedittableeee').jqGrid('getGridParam','selrow');
			var cellvalue=$('#gridedittableeee').jqGrid ('getCell', rowSel, 'id');
			if(cellvalue==0)
			{
				alert("Please select atleast one row");
			}
			else
			{
		    	editData(cellvalue);
			}
		});
		function editData (id) 
		{
			var patientname = jQuery("#gridedittableeee").jqGrid('getCell',id,'patient_name');
			$("#editDialog").dialog({title: 'Modify For '+patientname,width: 1170,height: "auto"});
			$("#editDialog").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");

			$("#editDialog").dialog('open');
			 $.ajax
				({
				type : "post",
				url  : "view/Over2Cloud/CorporatePatientServices/cpservices/beforePatienceModify?id="+id,
				success : function(data)
				{
				$("#editDialog").html(data);
				},
				error : function()
				{
				alert("Error on data fetch");
				} 
				});
		}
</SCRIPT>
</head>
<body >
 
<sj:dialog id="editDialog" showEffect="slide" hideEffect="explode"
		autoOpen="false" title="History " modal="true"
		closeTopics="closeEffectDialog" width="1170" height="400">
	</sj:dialog>

<sj:dialog id="printSelect" title="Print Ticket" autoOpen="false"
		modal="true" height="600" width="1200" showEffect="drop">
		<sj:dialog id="feed_status" modal="true" effect="slide"
			autoOpen="false" width="1100" hideEffect="explode"
			position="['center','top']"></sj:dialog>
		<div id="ticketsInfo"></div>
	</sj:dialog>
<sj:dialog id="downloadExcel" showEffect="slide" hideEffect="explode"
		autoOpen="false" title="Seek Approval Action" modal="true"
		closeTopics="closeEffectDialog" width="1000" height="400">
	</sj:dialog>


<sj:dialog id="takeActionDilouge" showEffect="slide" hideEffect="explode"
		autoOpen="false" title="History " modal="true"
		closeTopics="closeEffectDialog" width="1000" height="400">
	</sj:dialog>


<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div id="statusCounter"></div>
	
	<%if(userRights.contains("lodgeBoardCPS_VIEW")) 
							{%>
		<div id="both">
		<sj:a  button="true" cssClass="button" tabindex="-1" cssStyle="height:22px; width:30px;float:right;"  title="Lodge Window"   onClick="maximizeWindow('lodge');" buttonIcon="ui-icon-transferthick-e-w"></sj:a>
		</div>
		<%}%>
		<%if(userRights.contains("gridLodgeCPS_VIEW")) 
							{%>
		<div id="lodge" style="display: none">
	<sj:a  button="true"  cssClass="button" tabindex="-1" cssStyle="height:22px; width:30px;float:right;"  title="Grid Window"   onClick="maximizeWindow('grid');" buttonIcon="ui-icon-transferthick-e-w"></sj:a>
	</div>
		<%}%>
	<%if(userRights.contains("ticketLodgeGridCPS_VIEW")) 
							{%>
							<div id="both">
		<sj:a  button="true" cssClass="button" tabindex="-1" cssStyle="height:22px; width:30px;float:right;"  title="Lodge Window"   onClick="maximizeWindow('lodge');" buttonIcon="ui-icon-transferthick-e-w"></sj:a>
		</div>
		<div id="lodge" style="display: none">
	<sj:a  button="true"  cssClass="button" tabindex="-1" cssStyle="height:22px; width:30px;float:right;"  title="Grid Window"   onClick="maximizeWindow('grid');" buttonIcon="ui-icon-transferthick-e-w"></sj:a>
	</div>
	<div id="grid" style="display: none">
	<sj:a  button="true" cssClass="button" tabindex="-1" cssStyle="height:22px; width:30px;float:right;"  title="Lodge & Grid Window"   onClick="maximizeWindow('both');" buttonIcon="ui-icon-extlink"></sj:a>
	</div>
	<%}%>
	
	
	</div>	
<div class="clear"></div>
<div style="overflow:auto;height:623px; width:99%;    margin: 5px 0px 0px 6px; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; ">
<div id="CPSAdd" style="overflow:hidden;border-radius:6px; height:75%; width:99%; border: 1px solid #e4e4e5; margin: 3px 6px 4px 5px;border-right: 1px solid rgba(176, 165, 165, 0.54);"></div>
<s:hidden id="addID" value="o"></s:hidden>
<div id="gridDiv"  style="border-radius:6px; height:100%; width:100%;float:top;border: 1px solid #e4e4e5; margin: 0px 0px 0px 0px;border-right: 1px solid rgba(176, 165, 165, 0.54);">
	 			<div  class="listviewButtonLayer" id="listviewButtonLayerDiv" style="width: 100%">
			<div >
			<table >
			<tbody><tr><th>
			<table >
			<tbody>
			<tr class="alignright printTitle">
				<td >
				 <%if(userRights.contains("editPatientCPS_VIEW")) 
				{%>
				<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssClass="button" cssStyle="height:24px; width:32px" button="true" onClickTopics="editGrid"></sj:a>
				<%}%>
				</td>
				<td >
				<!--<sj:datepicker  cssStyle="height: 14px; width: 94px;margin-left: 8px;" cssClass="button" id="minDateValue" name="minDateValue" size="20" maxDate="0" value="%{minDateValue}" readonly="true"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="From Date" onchange="onloadData();"/>-->
				<sj:datepicker cssStyle="height: 14px; width: 94px;margin-left: 8px;" cssClass="button" id="minDateValue" name="minDateValue" size="20" value="%{minDateValue}"   readonly="true" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="From Date" onchange="onloadData();"/>
				</td>
				<td>
					<sj:datepicker cssStyle="height: 14px; width: 94px;margin-left: -4px;" cssClass="button" id="maxDateValue" name="maxDateValue" size="20" value="%{maxDateValue}"   readonly="true" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="To Date" onchange="onloadData();"/>
				</td>
				 
				
				<td>
				<s:select
				id	=	"status"
	    		list	=	"corpMap"
				headerKey="-1"
				headerValue="Status"
				cssClass="button"
				theme="simple"
				cssStyle="width: 85px;height:25px;float:left;"
				onchange="onloadData();"
				tabindex="-1">
				</s:select>
				</td>
				
		      	 <td>
				<s:select
				id	=	"feed_status1"
	    		name	=	"feed_status"
	    		list	=	"feedList"
				headerKey="-1"
				headerValue="Patient"
				cssClass="button"
				theme="simple"
				cssStyle="width: 85px;height:25px;float:left;"
				onchange="onloadData();"
				tabindex="-1">
				</s:select>
				</td>
				<td>	
				<s:select
				id	=	"corp_namee"
	    		name	=	"corp_name"
	    		list	=	"corpNameMap"
				headerKey="-1"
				headerValue="Corporate Name"
				cssClass="button"
				theme="simple"
				cssStyle="width: 137px;height:25px;float:left;"
				onchange="onloadData();"
				tabindex="-1">
				</s:select> 
				</td>
				
				
				
				<td>	
				<s:select
				id	=	"servicese"
	    		name	=	"servicese"
	    		list	=	"serviceeMap"
				headerKey="-1"
				headerValue="Services"
				cssClass="button"
				theme="simple"
				cssStyle="width: 99px;height:25px;float:left;"
				onchange="onloadData();"
				tabindex="-1">
				</s:select> 
					
				</td>
				
				
				<td>	
				<s:select
				id	=	"med_locationn"
	    		name	=	"med_location"
	    		list	=	"locationMap"
				headerKey="-1"
				headerValue="Location"
				cssClass="button"
				theme="simple"
				cssStyle="width: 99px;height:25px;float:left;"
				onchange="onloadData();"
				tabindex="-1">
				</s:select> 
				</td>
				<td>	
					<s:select
				id	=	"ac_managerr"
	    		name	=	"ac_manager"
	    		list	=	"accountMap"
				headerKey="%{keyExistSec}"
				headerValue="%{valueExistSec}" 
				cssClass="button"
				theme="simple"
				cssStyle="width: 120px;height:25px;float:left;"
				onchange="onloadData();"
				tabindex="-1">
				</s:select> 
				</td>
				<%if(userRights.contains("cpsServiceMng_VIEW")) 
							{%>
				<td>	
					<s:select
				id	=	"service_manager"
	    		name	=	"service_manager"
	    		list	=	"serviceMng"
				headerKey="%{keyExist}"
				headerValue="%{valueExist}" 
				cssClass="button"
				theme="simple"
				cssStyle="width: 120px;height:25px;float:left;"
				onchange="onloadData();"
				tabindex="-1">
				</s:select> 
				</td>
				<%}%>
				
				
				<td>	
				<!--<s:textfield  id="patienceSearch" name="patienceSearch" onkeyup="wildSearch();" cssClass="textField" cssStyle="width: 100px;height: 23px;margin-top: -28px ;margin-left: 2px;" Placeholder="Patience Name" theme="simple"/>-->
	     		<s:textfield name="wildSearch" id="wildSearch" tabindex="-1" placeholder="Search Any Value..." theme="simple" cssClass="button" onkeyup="wildSearch();" cssStyle="height:13px; width:90px;float:left;"/> 
				</td>
				<td>	
				<sj:a  button="true" cssClass="button" cssStyle="margin-top: 0px;margin-left: 2px;height:22px; width:31px;margin-right: 2px;"  title="Refresh"   onClick="gridCurrentPage();" buttonIcon="ui-icon-refresh"></sj:a>
				</td>
				<td>
				<sj:a button="true"style="height: 22px;margin-top:1px;margin-left: 3px;width:31px;margin-right: -2px;"cssClass="button" buttonIcon="ui-icon-print" title="Print"onclick="printData()"></sj:a>
				</td>
				<td>
				<sj:a  button="true" cssClass="button" cssStyle="margin-top: 0px;margin-left: 2px;height:22px; width:31px;margin-right: 2px;"  title="Download"   onClick="downloadFeedStatusORD();" buttonIcon="ui-icon-arrowstop-1-s"></sj:a>
				</td>
				<td>
				<a href="#" style="height: 26px; width: 32px;"> 
				<img src="images/SMSGreen12.png" width="20px" height="20px" 
				style="margin-top: 3px; float:right; margin-right: 2px"
				title="CMD Office" onclick="onloadData('CMD');">
			</a> 
			<a href="#"style="height: 26px; width: 32px;"> 
				<img src="images/Yellow1.png" id="searchData" name="searchData" width="20px" height="20px"
				style="margin-top: 3px; margin-left: 2px; float:right; margin-right: 2px;height: 21px;"
				title="COO Office" onclick="onloadData('COO');">
			</a> 
			<a href="#" style="height: 26px; width: 32px;"> 
				<img src="images/SMSWhite12.png" width="20px" height="20px"
				style="margin-top: 3px; margin-left: 2px; float:right; margin-right: 2px"
				title="CPS" onclick="onloadData('CPS');">
			</a> 
			<a href="#" style="height: 26px; width: 32px;"> 
				<img src="images/SMSred12.png" width="20px" height="20px"
				style="margin-top: 3px; margin-left: 2px; float:right; margin-right: 2px"
				title="VVIP Patient" onclick="onloadData('VVIP');">
			</a>
				</td>

				 
				

</tr></tbody></table>
</th>

</tr></tbody></table></div></div>
<div id="viewCPS"  style="border-radius:6px; height:99%; width:100%;border: 1px solid #e4e4e5;border-right: 1px solid rgba(176, 165, 165, 0.54);">
 </div>
 </div>
 	</div> 
</div>
</body>
</html>