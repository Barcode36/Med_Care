<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%
	String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();
%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="<s:url value="/js/communication/Communicationmsgdraft.js"/>"></script>
<script type="text/javascript"><!--
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	
function editRow()
{

   if(row==0)
	{
		alert("Please select atleast one row.");
	}
	jQuery("#gridedittable").jqGrid('editGridRow', row ,{height:320, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:350,modal:true});
}

function deleteRow()
{

   if(row==0)
	{
		alert("Please select atleast one row.");
	}
	$("#gridedittable").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true});
}
function searchRow()
{
	 $("#gridedittable").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function refreshRow()
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/hr/beforeEmailSettingView.action",
	    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
	            alert("error");
	        }
	 });
}
function addNewGroup()
{
	
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif><br/>Loading...</center>");
	$.ajax({
	    type : "post",
	    url : "/view/Over2Cloud/WFPM/Communication/beforeGroupAdd.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });

	}



function addDailyConfigure()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/hr/beforeEmailSettingAdd.action",
	    success : function(data) {
       		$("#data_part").html(data);
       		
		},
	    error: function() {
            alert("error");
        }
	 });

	}
// fro deshboard

function dashBoardRedirect()
{
	
			alert("hii");

		//window.location.assign("localhost:8085/over2cloud/view/Over2Cloud/Text2Mail/locationData.html");
}

//for location wise search
function chLocation(data){
	var fromDate=$("#fromDate").val();
	var toDate=$("#toDate").val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Text2Mail/beforePullReportView.action?location="+data+"&fromDate122="+fromDate+"&toDate122="+toDate,
	    success : function(feeddraft) {
       $("#datapart").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}
//for consultant wise search
function chCons(data){
	var fromDate=$("#fromDate").val();
	var toDate=$("#toDate").val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Text2Mail/beforePullReportView.action?cons="+data+"&fromDate122="+fromDate+"&toDate122="+toDate,
	    success : function(feeddraft) {
       $("#datapart").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}dashBoardRedirect

//search by keyword 

function chKw(data){
	
	var fromDate=$("#fromDate").val();
	var toDate=$("#toDate").val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Text2Mail/beforePullReportView.action?kws="+data+"&fromDate122="+fromDate+"&toDate122="+toDate,
	    success : function(feeddraft) {
       $("#datapart").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}

//search for Excutive
function chExc(data){
	var fromDate=$("#fromDate").val();
	var toDate=$("#toDate").val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Text2Mail/beforePullReportView.action?chExc="+data+"&fromDate122="+fromDate+"&toDate122="+toDate,
	    success : function(feeddraft) {
       $("#datapart").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}
// for spaciality wise search
function chSpc(data){
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Text2Mail/beforePullReportView.action?spc="+data,
	    success : function(feeddraft) {
       $("#datapart").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}



function count1(){
	var fromDate=$("#fromDate").val();
	var toDate=$("#toDate").val();
	var location=$("#location").val();
	var cons=$("#consultant").val();
	var keyword=$("#keyword").val();
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Text2Mail/counter.action?fromDate122="+fromDate+"&toDate122="+toDate+"&location="+location+"&cons="+cons+"&kws="+keyword,
	    success : function(feeddraft) {
       $("#countrr").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function refreshActivityBoard()
{
	alert("hii");
	  intvrl=setInterval("HDMActivityRefresh()", (5*1000));
} 
function HDMActivityRefresh()
{
	onChangeDate();
} 
function clearInt()
{
	clearInterval(intvrl);
}


function onChangeDate()
{
	var fromDate=$("#fromDate").val();
	var toDate=$("#toDate").val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Text2Mail/beforePullReportView.action?fromDate122="+fromDate+"&toDate122="+toDate,
	    success : function(feeddraft) {
       $("#datapart").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}


function getSearchData()
{
	var type=$("#type").val();
	$.ajax({
 		type :"post",
 		url : "view/Over2Cloud/Text2Mail/beforePullReportView.action",
	    data:"type="+type,
 		success : function(data) 
	    {
			$("#datapart").html(data);
		},
	    error: function()
	    {
	        alert("error");
	    }
 	});
}
function viewHistory(cellvalue, options, rowObject) 
{
	return "<a href='#' title='View Data' onClick='viewHistoryOnClick("+rowObject.id+")'><font color='blue'>"+cellvalue+"</font></a>";
}

function viewHistoryOnClick(id) 
{
	var keyword = jQuery("#gridedittable").jqGrid('getCell',id,'incomingKeyword');
	var mobile = jQuery("#gridedittable").jqGrid('getCell',id,'mobileNo');
	
	$("#takeActionGrid").dialog({title: ' History for '+keyword+' from '+mobile,width: 450,height: 600});
	$("#takeActionGrid").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#takeActionGrid").dialog('open');
	$.ajax
	({
		type : "post",
		url  : "view/Over2Cloud/Text2Mail/beforeHistoryView.action?idH="+id,
		success : function(data)
		{
			$("#takeActionGrid").html(data);
		},
		error : function()
		{
			alert("Error on data fetch");
		} 
	});
}


// for autoAck
function viewAutoAck(cellvalue, options, rowObject) 
{
	return "<a href='#' title='View Data' onClick='viewAutoAckClick("+rowObject.id+")'><font color='blue'>"+cellvalue+"</font></a>";
}

 function viewAutoAckClick(id) 
{
	var keyword = jQuery("#gridedittable").jqGrid('getCell',id,'incomingKeyword');
	var mobile = jQuery("#gridedittable").jqGrid('getCell',id,'mobileNo');
	
	$("#takeActionGridAutoAck").dialog({title: ' AutoAck massage  for '+mobile +' on behalf of Keyword '+keyword,width: 550,height: 200});
	$("#takeActionGridAutoAck").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#takeActionGridAutoAck").dialog('open');
	$.ajax
	({
		type : "post",
		url  : "view/Over2Cloud/Text2Mail/AutoAckClickView.action?idA="+id,
		success : function(data)
		{
			$("#takeActionGridAutoAck").html(data);
		},
		error : function()
		{
			alert("Error on data fetch");
		} 
	});
} 
// for excel download 
function getCurrentColumn()
{
	var fromDate=$("#fromDate").val();
	var toDate=$("#toDate").val();
	var location=$("#location").val();
	var cons=$("#consultant").val();
	var keyword=$("#keyword").val();
		$("#takeActionGrid1").dialog({title: 'Check Column ',width: 350,height: 600});
		$("#takeActionGrid1").dialog('open');
		$("#takeActionGrid1").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax
		({
		    type : "post",
		    url : "view/Over2Cloud/Text2Mail/exclelColumn.action?fromDate122="+fromDate+"&toDate122="+toDate+"&location="+location+"&cons="+cons+"&kws="+keyword,
		    success : function(data) 
		    {
	 			$("#takeActionGrid1").html(data);
			},
		    error: function() 
		    {
		        alert("error");
		    }
		 });
	
	}

// for send mail
function getBeforeSendMail( ) 
{
	var fromDate=$("#fromDate").val();
	var toDate=$("#toDate").val();
	var location=$("#location").val();
	var cons=$("#consultant").val();
	var keyword=$("#keyword").val();
		$("#takeActionGrid1").dialog({title: 'Select Employee to Send Mail from '+fromDate+' to '+toDate,width: 1000,height: 400});
		$("#takeActionGrid1").dialog('open');
		$("#takeActionGrid1").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax
		({
		    type : "post",
		    url : "view/Over2Cloud/Text2Mail/beforeSendMail.action?fromDate122="+fromDate+"&toDate122="+toDate+"&location="+location+"&cons="+cons+"&kws="+keyword,
		    success : function(data) 
		    {
	 			$("#takeActionGrid1").html(data);
			},
		    error: function() 
		    {
		        alert("error");
		    }
		 });
}
refreshActivityBoard();
onChangeDate();


clearInt();

--></script>

</head>
<body>
<sj:dialog
          id="takeActionGrid1"
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
          id="dialogOpen"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title="Executive Vs No. Of Keywords"
          modal="true"
          width="600"
          height="400"
          draggable="true"
    	  resizable="true"
          >
</sj:dialog>
<sj:dialog
          id="takeActionGrid"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="history"
          modal="true"
          closeTopics="closeEffectDialog"
          width="20000"
          height="400"
          >
</sj:dialog>

<sj:dialog 
		id="downloadColumnViewPullReport" 
		modal="true" 
		effect="slide" 
		autoOpen="false" 
		width="300" 
		height="530" 
		title="Pull report Column Name" 
		loadingText="Please Wait" 
		overlayColor="#fff" 
		overlayOpacity="1.0" 
		position="['center','center']" >
	<div id="downloadcolumnView"></div>
</sj:dialog>
<div class="clear"></div>

<sj:dialog
          id="takeActionGridAutoAck"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="history"
          modal="true"
          closeTopics="closeEffectDialog"
          width="20000"
          height="400"
          >
</sj:dialog>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Keyword Report Data</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Total:</div>

<div class="head" id="countrr"><s:property value="%{kw}"/></div> <div class="head" id="countrr2"></div> </div>

<center>
<div class="rightHeaderButton"></div>
<div class="clear"></div>
<div class="listviewBorder" style="width: 97%;margin-left: 20px;" align="center">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
<tbody><tr></tr><tr><td></td></tr><tr><td> 
<table class="floatL" border="0" cellpadding="0" cellspacing="0">
<tbody><tr><td class="pL10"><!-- 
                   <%if(userRights.contains("vemail_MODIFY")) {%>
					<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow()"></sj:a>
					<%} %>
					<%if(userRights.contains("vemail_MODIFY")) {%>
					<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
					<%} %>
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchRow()"></sj:a>
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="refreshRow()"></sj:a>
                    
					-->
					<sj:datepicker cssStyle="height: 16px; width: 70px;"  id="fromDate" name="fromDate" value="%{fromDate}" size="20" maxDate="0"   readonly="true" onchange="onChangeDate();count1()"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" />
					<sj:datepicker cssStyle="height: 16px; width: 70px;"  id="toDate" name="toDate"  value="%{toDate}" size="20" maxDate="0"   readonly="true" onchange="onChangeDate();count1()"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" />
                     
                     
                    <%--  <sj:datepicker cssStyle="height: 13px;margin-left: 9px; width: 60px;" onchange="gridActivityData();"  cssClass="button" id="fromDate" name="fromDate" size="20" value="%{fromDate}" maxDate="0"   readonly="true"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="From Date"/>
         <sj:datepicker cssStyle="height: 13px; width: 60px;" onchange="gridActivityData();" cssClass="button" id="toDate" name="toDate" size="20" value="%{toDate}"   readonly="true" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="To Date"/> --%>
         
					 
						   <sj:select
						   		id="location" 
								name="location" 
								list="locationMap"
								headerKey="-1" 
								headerValue="Location"
								cssClass="button"
							    cssStyle="margin-top: -30px;margin-left:333px; width:105px;"
							    onchange="chLocation(this.value);count1();">
						    </sj:select>
						    
						     <sj:select
						     
						     	id="Speciality" 
								name="Speciality" 
								list="SpecialityMap"
								headerKey="-1" 
								headerValue="Speciality"
								cssClass="button"
							    cssStyle="margin-top: -30px;margin-left:223px; width:109px;"
							    onchange="chSpc(this.value),count()">
						    </sj:select>
						    
						 <%-- 
						    <s:select
						     
						     	id="Exceutive" 
								name="Exceutive" 
								list="%{exe}"
								headerKey="-1" 
								headerValue="Select Exceutive"
								cssClass="button"
							    cssStyle="margin-top: -30px;margin-left:370px"
							    onchange="chExc(this.value),count1()"
							    >
						    </s:select> --%>
						    
						    <sj:select
								id="consultant" 
								name="consultant" 
								list="consultantMap"
								headerKey="-1" 
								headerValue="Consultant"
								cssClass="button"
							    cssStyle="margin-top: -30px;margin-left:3px; width:109px;"
							    onchange="chCons(this.value),count1()">
						    </sj:select>
						    
						     <sj:select
								id="keyword" 
								name="keyword" 
								list="keywrd"
								headerKey="-1" 
								headerValue="Keyword"
								cssClass="button"
								cssStyle="margin-top: -30px;margin-left:113px; width:109px;"
							    onchange="chKw(this.value),count1()">
						    </sj:select>
						    
						    
						    
						    <sj:a  button="true" 
				           cssClass="button" 
				           title="Download"
				           buttonIcon="ui-icon-arrowstop-1-s" 
				           cssStyle="height: 25px;width: 32px;margin-left: 440px;"
				           onclick="getCurrentColumn()"/>
				           
				           <sj:a  button="true" 
				           cssClass="button" 
				           title="Send Mail"
				           value=" Register " 
				           cssStyle="height: 25px;width: 89px;margin-left: 3px;"
				           onclick="getBeforeSendMail()">Send Mail</sj:a>
				           
				           
				           
				            <sj:a  button="true" 
				           cssClass="button" 
				           title="Dashboard"
				           
				             cssStyle="height: 25px;width: 90px ;align:right;"
				           onclick="dashBoardRedirect();">Dashboard</sj:a>
				           
				        
				           
				          
						    
 
					
					
</td>



</tr></tbody></table>
</td>
<td class="alignright printTitle">

<%if(userRights.contains("vemail_ADD")) {%>
<!--<sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="addDailyConfigure();">Add</sj:a>
--><%} %>
</td> 
</tr></tbody></table></div></div>
<s:url id="viewGrids" action="viewPullReportss" escapeAmp="false" >
<s:param name="type" value="%{type}"></s:param>
</s:url>

<div id="datapart"></div>

</div>
</body>
</html>