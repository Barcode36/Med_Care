<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

<script type="text/javascript" src="<s:url value="/js/communication/CommunicationInstantMessage.js"/>"></script>



<script type="text/javascript">
function associateHyperlink(cellvalue, options, row)
{
	return "<a href='#' onClick='javascript:openAssociateCatView("+row.id+")'>" + cellvalue + "</a>";
}
function openAssociateCatView(id)
{
	$("#categoryOfferingDiv").html("");
	$("#categoryOfferingDiv").load("<%=request.getContextPath()%>/view/Over2Cloud/wfpm/masters/beforeFetchAssociateCatCost.action?id="+id);
}



function addinstantmsggg()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/>Loading...</center>");
	$.ajax({
	    type : "post",
	    url  : "view/Over2Cloud/CommunicationOver2Cloud/Comm/beforeInstantMessageAdd.action",
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

</script>
<script type="text/javascript">
function selectpushreportstatus(id)
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CommunicationOver2Cloud/accountstatus/getpushreportstatus.action?status="+id,
	    success : function(subdeptdata) {
       $("#"+"ajaxresponcediv").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });

	}
	function getmobilenumberdata(mobile){
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/CommunicationOver2Cloud/accountstatus/getdataselectmobile.action?mobileNo="+mobile,
		    success : function(subdeptdata) {
	       $("#"+"ajaxresponcediv").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	
	function getselecteddate(){
		var from_date= $('#from_date').val();
		var  to_date= $("#to_date").val();
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/CommunicationOver2Cloud/accountstatus/getpushreportselecteddate.action?from_date="+from_date+"&to_date="+to_date,
		    success : function(subdeptdata) {
	       $("#"+"ajaxresponcediv").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Auto Push report</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div>

</div>
<div style=" float:left; padding:10px 1%; width:98%;">

<br><div class="listviewBorder">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
<tbody><tr></tr><tr><td></td></tr><tr><td> 
<table class="floatL" border="0" cellpadding="0" cellspacing="0">
<tbody><tr><td class="pL10"> 
					<sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editRow()"></sj:a>
					<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchRow()"></sj:a>
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="refreshRow()"></sj:a>
                     
                     <s:select id="status" name="status"  list="#{'-1':'Select All Status','3':'Sent SMS','0':'Unsent SMS','1':'Reject SMS'}"   onchange="selectpushreportstatus(this.value);"     cssClass="button"  theme="simple"       cssStyle="margin-top: -26px;margin-left: 2px;height: 26px;"></s:select>
                 
                    &nbsp;Mobile No:<s:textfield name="mobileNo" id="mobileNo"  cssClass="button"    onkeyup="getmobilenumberdata(this.value);"  cssStyle="margin-top: 1px;margin-left: 2px;height: 16px; width: 8%;"  theme="simple"/>
                    From:<sj:datepicker name="from_date"  id="from_date" showOn="focus" displayFormat="dd-mm-yy" value="today"   onchange ="getselecteddate();"     yearRange="2014:2020" readonly="true" cssClass="button" style="margin: 0px 6px 10px; width: 8%;"/>
					To:<sj:datepicker name="to_date"  id="to_date" showOn="focus" displayFormat="dd-mm-yy"    onchange="getselecteddate();" value="today" yearRange="2014:2020" readonly="true"   cssClass="button"  onchange="searchDateWiseVisitorData();" style="margin: 0px 6px 10px; width: 8%;"/>
				      
</td></tr></tbody></table>
</td>
<td class="alignright printTitle">
<sj:a  button="true" cssClass="button" cssStyle="height:25px; width:32px"  title="Download" buttonIcon="ui-icon-arrowstop-1-s" onclick="excelDownload();" />
				          
			               
			              
				           
				           
</td> 
</tr></tbody></table></div></div>
<s:url id="viewautopushGrid" action="viewautopushGridurl" >
</s:url>
<div id="ajaxresponcediv">
<sjg:grid 
		  id="gridedittable"
          href="%{viewautopushGrid}"
          gridModel="pushreportModel"
          navigator="true"
          navigatorAdd="false"
          navigatorView="false"
          navigatorDelete="%{deleteFlag}"
          navigatorEdit="%{modifyFlag}"
          navigatorSearch="true"
          rowList="15,25,50"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="false"  
          loadingText="Requesting Data..."  
          rowNum="10"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          editurl="%{viewModifyGrid}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          autowidth="true"
          >
          <s:iterator value="mapObj" id="mapObj"> 
          <s:if test="%{key=='sms_id'}"> 
		<sjg:gridColumn 
		name="%{key}"
		index="%{key}"
		title="%{value}"
		width="100"
		align="center"
		editable="false"
		search="%{search}"
		hidden="true"
		/>
		</s:if>
		<s:elseif test="%{key=='message'}"> 
		<sjg:gridColumn 
		name="%{key}"
		index="%{key}"
		title="%{value}"
		width="300"
		align="center"
		editable="false"
		search="%{search}"
		hidden="%{hideOrShow}"
		/>
		</s:elseif>
		<s:else>
		<sjg:gridColumn 
		name="%{key}"
		index="%{key}"
		title="%{value}"
		width="200"
		align="center"
		editable="false"
		search="%{search}"
		hidden="%{hideOrShow}"
		/>
		</s:else>
		</s:iterator> 
	          
          
		
</sjg:grid>
</div>
</div>
</div>
</div>

</body>
</html>