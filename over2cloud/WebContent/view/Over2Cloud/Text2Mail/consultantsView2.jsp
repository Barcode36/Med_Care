<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<%
String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();
%>
<html>
<head>
<SCRIPT type="text/javascript"> 
function createConsultants()
{

	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Text2Mail/beforeConsultants.action",
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
 
	}

function back()
{

	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Text2Mail/beforeMappingView2.action",
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });

	}



function addConsultants()
{
var data4=$("#map").val();
var map=$("#map").val();
var sel_id;
sel_id=$("#gridBasicDetails").jqGrid('getGridParam','selarrrow');
alert(sel_id);

if(sel_id=="")
{
     alert("Please select atleast a ckeck box...");        
     return false;
}
else
{
	alert(map);
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Text2Mail/mapWithExecutive.action?id="+sel_id+"&map="+data4,
	    success : function(data) 
	    {
		$("#viewempDiv").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/Text2Mail/beforeConsultantsView2.action?destination="+ sel_id+"&mapWith="+map,
		success : function(empData)
		{
			$("#viewempDiv").html(empData);
	    },
	    error : function () 
	    {
			alert("Somthing is wrong to get Employee Data");
		}
		});
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
	
}	
}


</SCRIPT>
<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	
function editRow()
{
	jQuery("#gridBasicDetails").jqGrid('editGridRow', row ,{height:200, width:425,reloadAfterSubmit:false,closeAfterEdit:true,top:0,left:0,modal:true});
}

function deleteRow()
{
	//$("#gridBasicDetails").jqGrid('delGridRow',row, {height:200,reloadAfterSubmit:true});
	row = $("#gridBasicDetails").jqGrid('getGridParam','selarrrow');
	$("#gridBasicDetails").jqGrid('delGridRow',row, {reloadAfterSubmit:true,top:0,modal:true});
}

function reload(rowid, result) {
	  $("#gridBasicDetails").trigger("reloadGrid"); 
	}


function searchData()
{
	jQuery("#gridBasicDetails").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:350,modal:true, cssClass:"textField"} );
	
}
</script>
</head>
<body>
 
<div class="clear"></div>

<!-- Code For Header Strip -->
<div class="clear"></div>
<div style="height:370px; width: 96%; margin: 1%;  padding: 1%;">
	<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie">
		<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody><tr></tr><tr><td></td></tr><tr><td> 
			<table class="floatL" border="0" cellpadding="0" cellspacing="0">
		<tbody><tr>
					 <td class="pL10"> 
				<sj:a id="add" buttonIcon="" cssClass="button" button="true" onclick="addConsultants()"> Save </sj:a>
				<sj:a id="cancel" buttonIcon="" cssClass="button" button="true"  onclick="back()">Back</sj:a>
		          
		</td></tr>
					    </tbody>
					  </table>
				  </td>
				  
				  <!-- Block for insert Right Hand Side Button -->
				  
			</tr>
		</tbody>
	</table>
</div>
</div>
<div class="clear"></div>
<s:url id="viewCons" action="viewConsInGrid" />
<s:url id="modifyCons" action="modifyCons" />
<s:hidden name="map" id="map" value="%{#parameters.mapWith}" ></s:hidden>
<center>
<sjg:grid 
		  id="gridBasicDetails"
          href="%{viewCons}"
          gridModel="masterViewList"
          navigator="false"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="%{deleteFlag}"
          navigatorEdit="%{modifyFlag}"
          navigatorSearch="true"
          rowList="15,20,25"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="15"
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          editurl="%{modifyCons}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="true"
          autowidth="true"
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
	searchoptions="{sopt:['eq','cn']}"
	/>
	</s:iterator>  
</sjg:grid>
</center></div></div>
<br><br>
<%-- <center><img id="indicatorViewPage" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center> --%>
<div id="divFullHistory" style="float:left; width:900px;">

</div>

</body>
</html>