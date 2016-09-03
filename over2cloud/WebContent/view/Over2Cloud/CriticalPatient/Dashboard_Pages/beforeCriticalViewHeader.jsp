<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
.TitleBorder {
	float: left;
    height: 27px;
    border: 1px solid #e0bc27;
    margin: 2px 1px 0px 2px;
    background: #EFC3C3;
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
    margin: 2px 1px 0px 2px;
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

<script type="text/javascript" src="js/criticalPatient/criticalLodgeValidation.js"></script>
<script type="text/javascript">
 	
  	$.subscribe('rowselect', function(event, data) {
		row = event.originalEvent.id;
	});
	
	function fetchnurshingUnit()
	{
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Critical/fetchNursingUnit.action?fromDate="+$("#fromDate").val()+"&toDate="+$("#toDate").val()+"&searchString="+$("#patient_speciality1").val(),
		    success : function(data) {
			
			console.log(data);
				var empData=data[0];
		    	$('#nursingUnit1 option').remove();
				$('#nursingUnit1').append($("<option></option>").attr("value",-1).text("Nursing"));
				empData=data[0];
		    	$(empData).each(function(index)
				{
				  $('#nursingUnit1').append($("<option></option>").attr("value",this.name).text(this.name));
				});
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	function fetchDoctor()
	{
 
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Critical/fetchDoctor.action?fromDate="+$("#fromDate").val()+"&toDate="+$("#toDate").val()+"&searchString="+$("#nursingUnit1").val(),
		    success : function(data) {
				var empData=data[0];
		    	$('#doc_name11 option').remove();
				$('#doc_name11').append($("<option></option>").attr("value",-1).text("Doctor"));
				empData=data[0];
		    	$(empData).each(function(index)
				{
				  $('#doc_name11').append($("<option></option>").attr("value",this.name).text(this.name));
				});

		    	 
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	function onSearchDataGrid()
	{

		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Critical/beforeCriticalData.action?fromDate="+$('#fromDate').val()+"&toDate="+$('#toDate').val()+"&status="+$('#status1').val()+"&nursingUnit="+$('#nursingUnit1').val()+"&doc_name1="+$('#doc_name11').val()+"&level="+$('#level1').val()+"&patient_status="+$('#patient_status').val()+"&patient_speciality="+$("#patient_speciality1").val()+"&searchString="+$('#searchString').val(),
		    success : function(data) {
	       $("#datapart").html(data);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	function fetchAllSpeciality(){
		$.ajax({	
		type : "post",
		 url : "view/Over2Cloud/Critical/fetchAllSpecialityDD.action?fromDate="+$("#fromDate").val()+"&toDate="+$("#toDate").val(),
		success : function(data) 
		{
			var empData=data[0];
	    	$('#patient_speciality1 option').remove();
			$('#patient_speciality1').append($("<option></option>").attr("value",-1).text("Speciality"));
			empData=data[0];
	    	$(empData).each(function(index)
			{
	    		$('#patient_speciality1').append($("<option></option>").attr("value",this.name).text(this.name));
			});
		},
		error: function() {
		    alert("error");
		}
	});
}

	function downloadExcel()
	{
		$("#takeActionGrid").dialog({title: 'Check Column',width: 350,height: 600});
		$("#takeActionGrid").dialog('open');
		$("#takeActionGrid").html("<br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");

		$.ajax({
		    type : "post",
		 	url : "/over2cloud/view/Over2Cloud/Critical/beforeCriticalDownload.action?fromDate="+$('#fromDate').val()+"&toDate="+$('#toDate').val()+"&status="+$('#status1').val()+"&nursingUnit="+$('#nursingUnit1').val()+"&doc_name1="+$('#doc_name11').val()+"&level="+$('#level1').val()+"&patient_status="+$('#patient_status').val()+"&patient_speciality="+$("#patient_speciality1").val()+"&searchString="+$('#searchString').val(),
		 	success : function(data) 
		    {
	 			$("#takeActionGrid").html(data);
			},
		   error: function() {
		        alert("error");
		    }
		 });
	}
		
	onSearchDataGrid();	
	fetchAllSpeciality();
	getStatusCounter();
	
</script> 
 
</head>
<body>
<sj:dialog
          id="takeActionGrid"
          showEffect="slide" 
          hideEffect="explode" 
          autoOpen="false"
           width="1000"
          height="350"
          position="center"
          >
</sj:dialog>

<div class="clear"></div>
<div class="middle-content">
 
<div class="clear"></div>
<div id="listPart"  style="border-radius:6px; height:99%; width:100%;float:top;border: 1px solid #e4e4e5; margin: 0px 0px 0px 0px;border-right: 1px solid rgba(176, 165, 165, 0.54);">
			<div  class="listviewButtonLayer" id="listviewButtonLayerDiv" style="width: 100%">
			<div >
			<table >
			<tbody><tr><th>
			<table >
			<tbody>
			<tr class="alignright printTitle">
				<td >
				    <sj:datepicker  cssStyle="height: 25px; width: 96px;float:left;" cssClass="button" id="fromDate" name="fromDate" size="20" maxDate="0" value="%{fromDate}" readonly="true"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="From Date" onchange="onSearchDataGrid();fetchAllSpeciality();getStatusCounter();"/>
			
			         
				</td>
				<td>
					 <sj:datepicker cssStyle="height: 25px; width: 96px;float:left;" cssClass="button" id="toDate" name="toDate" size="20" value="%{toDate}"   readonly="true" yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="To Date" onchange="onSearchDataGrid();fetchAllSpeciality();getStatusCounter();"/>
				</td>
				<td>
				<s:select
						id="status1" 
						name="status" 
						list="#{'AllStatus':'All Status','Informed':'Informed','Not Informed':'Not Informed','Snooze':'Snooze','Informed-P':'Informed-P','Close':'Close','No Further Action':'No Further Action','Ignore':'OCC Ignore'}"
						cssClass="button"
						theme="simple"
						headerKey = "-1"
						headerValue = "Status"
					 	cssStyle="width: 91px;height:25px;float:left;"
						onchange="onSearchDataGrid();"
						>
					</s:select>
					</td>
					
					<td>
				<s:select
						id="level1" 
						name="level" 
						list="#{'Level1':'Level1','Level2':'Level2','Level3':'Level3','Level4':'Level4','Level5':'Level5'}"
						cssClass="button"
						theme="simple"
						headerKey = "-1"
						headerValue = "Level"
					 	cssStyle="width: 91px;height:25px;float:left;"
						onchange="onSearchDataGrid();"
						>
					</s:select>
					</td>
					
					<td>
				<s:select
						id="patient_status" 
						name="patient_statusA" 
						list="#{'IPD':'IPD','OPD':'OPD','Other':'Other'}"
						cssClass="button"
						theme="simple"
						headerKey = "-1"
						headerValue = "Type"
					 	cssStyle="width: 91px;height:25px;float:left;"
						onchange="onSearchDataGrid();">
						
					</s:select>
					</td>
					
					<td>
				<s:select
						id="patient_speciality1" 
						name="patient_speciality" 
						list="{'No Data'}"
						cssClass="button"
						theme="simple"
						headerKey = "-1"
						headerValue = "Specialty"
					 	cssStyle="width: 110px;height:25px;float:left;"
						onchange="onSearchDataGrid();fetchnurshingUnit();">
						
					</s:select>
					</td>
					
					<td>	
					 <s:select
						id="nursingUnit1" 
						name="nursingUnit" 
						list="{'No Nursing Unit'}"
						headerKey="-1"
						headerValue="Nursing"
						cssClass="button"
						theme="simple"
						cssStyle="width: 100px;height:25px;float:left;"
						onchange="onSearchDataGrid();fetchDoctor();"
						tabindex="-1">
					</s:select> 
				</td>
				
				<td>	
					 <s:select
						id="doc_name11" 
						name="doc_name1" 
						list="{'No Doctor'}"
						headerKey="-1"
						headerValue="Doctor"
						cssClass="button"
						theme="simple"
						cssStyle="width: 91px;height:25px;float:left;"
						onchange="onSearchDataGrid();"
						tabindex="-1">
					</s:select> 
				</td>
			
				<td>	
					 <s:textfield name="searchStr" id="searchString" tabindex="-1" placeholder="Search Any Value" theme="simple" cssClass="button" onkeyup="onSearchDataGrid()" cssStyle="height:25px; width:130px;float:left;"/>
				</td>
				
				
				<td>
					<sj:a button="true" cssClass="button" tabindex="-1" title="Refresh" onclick="onSearchDataGrid();getStatusCounter();resetFields();" cssStyle="height:22px; width:30px;float:left;" buttonIcon="ui-icon-refresh"/>
				</td>
				
				<td>
					<sj:a  button="true" cssClass="button" tabindex="-1" cssStyle="height:22px; width:30px;float:left;"  title="Download"   onClick="downloadExcel();" buttonIcon="ui-icon-arrowstop-1-s"></sj:a>
				</td>
				<%--<td>
					<sj:a  button="true" cssClass="button" tabindex="-1" cssStyle="margin-top: 0px;height:23px; width:111px;float:left;"  title="Bulk Close"  onClick="takeBulkActionOnClick();" ><font style="font-size: 12px;">Bulk Action</font></sj:a>
				</td>
				 <td>
					<sj:a  button="true" cssClass="button" tabindex="-1" cssStyle="height:22px; width:30px;float:left;"  title="Download"   onClick="downloadFeedStatus();" buttonIcon="ui-icon-arrowstop-1-s"></sj:a>
				</td>
				 --%>

				 
				

</tr></tbody></table>
</th>

</tr></tbody></table></div></div>
<div id="datapart" onmouseover="StopRefresh()" tabindex="0" onmouseout="StartRefresh()" style="overflow:hidden;border-radius:6px; height:100%; width:100%; float:left;border: 1px solid #e4e4e5; margin: 0px 0px 0px 0px;border-right: 1px solid rgba(176, 165, 165, 0.54);">
 </div>
 </div>
</div>
</body>
</html>