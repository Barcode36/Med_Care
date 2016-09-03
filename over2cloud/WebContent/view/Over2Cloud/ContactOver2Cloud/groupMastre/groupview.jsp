<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>


<script type="text/javascript">
	//Conatct masters works STARTS from here
	    function getselectgroupid() {
	    var s;
	    var myArray = [];
	    var mymobileArray = [];
    	 s = $("#gridgrouping").jqGrid('getGridParam','selarrrow');
    	  for(var i=0; i<s.length; i++){
	         myArray.push(jQuery("#gridgrouping").jqGrid('getCell',s[i], 'emailid'));
	         mymobileArray.push(jQuery("#gridgrouping").jqGrid('getCell',s[i], 'mobilenumber'));
	        }
    	  var emailidtwo= "pankajk@dreamsol.biz";
	      var first_name= "Dear";
          var conP = "<%=request.getContextPath()%>";
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : conP+"/view/groupOver2Cloud/sendgroupmail.action?id="+s+"&first_name="+first_name.split(" ").join("%20")+"&emailidone="+myArray+"&emailidtwo="+emailidtwo+"&mobileno="+mymobileArray,
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
	//Conatct masters works STARTS from here
	 function groupdataAdd(){
	 var groupfalg=true;
     var conP = "<%=request.getContextPath()%>";
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		     type : "post",
		     url : "view/Over2Cloud/CommunicationOver2Cloud/Comm/addnewroupdataurl.action?groupfalg="+groupfalg,  
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
	//Conatct masters works STARTS from here
	 function editegroupdata(){
	 var groupfalg=true;
     var conP = "<%=request.getContextPath()%>";
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		     type : "post",
		     url : "view/Over2Cloud/CommunicationOver2Cloud/Comm/addroupdataurl.action?groupfalg="+groupfalg,  
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
function selectgroupnamesssss(id){
    var conP = "<%=request.getContextPath()%>";
		$.ajax({
		     type : "post",
		     url : "view/Over2Cloud/CommunicationOver2Cloud/Comm/getselectedgroupdata.action?select_param="+id,  
		    success : function(subdeptdata) {
	       $("#"+"ajaxgroup").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
  }

</script>
<script type="text/javascript">
	function deleteRow(){
		 var row = $("#gridgrouping").jqGrid('getGridParam','selarrrow');
		 var  groupid = jQuery("#gridgrouping").jqGrid('getCell',row, 'groupid');
		   $("[name='groupid']").val(groupid);
		$("#gridgrouping").jqGrid('delGridRow',row+"-"+groupid, {height:120,reloadAfterSubmit:true,top:0,left:50,modal:true,});
		
	}
	
	function searchRow(){
		jQuery("#gridgrouping").jqGrid('searchGrid', {multipleSearch:false,reloadAfterSubmit:true,top:0,left:350,modal:true} );
	}
	function refreshRow(row, result) {
		  $("#gridgrouping").trigger("reloadGrid"); 
	}
	
</script>


</head>
<body>
<div class="list-icon">
	 <div class="head">Group</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div> 
</div> 
<div style=" float:left; padding:1px 1%; width:98%; height: 350px;">
<div class="clear"></div>
 <br><div class="listviewBorder">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
<tbody><tr></tr><tr><td></td></tr><tr><td> 
<table class="floatL" border="0" cellpadding="0" cellspacing="0">
<tbody><tr><td class="pL10"> 
					<sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="deleteRow()"></sj:a>
					<sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="searchRow()"></sj:a>
					<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="refreshRow()"></sj:a>
					
					
					<s:select id="actionStatus" name="actionStatus"  list="groupmapobject"  headerKey="-1" headerValue="Select Group Name"   onchange="selectgroupnamesssss(this.value);" cssClass="button"  cssStyle="margin-top: -26px;margin-left: 2px;height: 26px;"/>
</td></tr></tbody></table>
</td>
<td class="alignright printTitle">
<sj:a  button="true" cssClass="button" cssStyle="height:25px; width:32px"  title="Download" buttonIcon="ui-icon-arrowstop-1-s" onclick="excelDownload();" />
<sj:a  button="true"  cssClass="button" cssStyle="height:25px; width:32px"  title="Upload" buttonIcon="ui-icon-arrowstop-1-n" onclick="excelUpload1();" /> 
<sj:a id="addnewButton" title="Add Group Data"  buttonIcon="ui-icon-circle-plus" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="editegroupdata();"></sj:a>			          
<sj:a id="addButton"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="groupdataAdd();">Add</sj:a>
</td> 
</tr></tbody></table></div></div>
 <div class="clear"></div>
 <div style="display: none">
 </div>
<s:url id="viewgroupData" action="viewgroupData" escapeAmp="false">
</s:url>
<s:url id="editgroupurl" action="editgroupurl" escapeAmp="false">
</s:url>
<div id="ajaxgroup">
         <sjg:grid
    	id="gridgrouping"
    	loadonce="false"
    	href="%{viewgroupData}"
    	gridModel="groupDetail"
    	groupField="['name']"
    	groupColumnShow="[true]"
    	groupCollapse="true"
    	groupText="['<b>{0}: {1} </b>']"
    	navigator="true"
    	navigatorAdd="false"
    	navigatorEdit="false"
    	navigatorDelete="false"
    	navigatorView="true"
    	rowTotal="1000"
    	rowNum="30"
    	autowidth="true"
    	altRows="true"
    	viewrecords="true"
    	pager="true"
    	editurl="%{editgroupurl}"
    	pagerButtons="false"
    	pagerInput="false"
    	multiselect="true"
    	multiboxonly="true"
        shrinkToFit="false"
    > 
    	
		<sjg:gridColumn 
		name="id"
		index="id"
		title="Id"
		width="60"
		align="center"
		editable="false"
		search="false"
		hidden="true"
		/>
		
		<sjg:gridColumn 
		name="empName"
		index="empName"
		title="Contact Name"
		width="200"
		align="center"
		editable="false"
		search="true"
		hidden="false"
		/>
		<sjg:gridColumn 
		name="mobOne"
		index="mobOne"
		title="Mobile Number"
		width="240"
		align="center"
		editable="false"
		search="true"
		hidden="false"
		/>
			<sjg:gridColumn 
		name="emailIdOne"
		index="emailIdOne"
		title="Email Id "
		width="290"
		align="center"
		editable="false"
		search="true"
		hidden="false"
		/>
		<sjg:gridColumn 
		name="address"
		index="address"
		title="Address"
		width="290"
		align="center"
		editable="false"
		search="false"
		hidden="false"
		/>
		<sjg:gridColumn 
		name="city"
		index="city"
		title="City "
		width="90"
		align="center"
		editable="false"
		search="false"
		hidden="false"
		/>
		<sjg:gridColumn 
		name="name"
		index="name"
		title="Group Name"
		width="150"
		align="center"
		editable="false"
		search="true"
		hidden="false"
		/>
		 	<sjg:gridColumn 
		name="groupid"
		index="groupid"
		title="Group Name"
		width="150"
		align="center"
		editable="true"
		search="true"
		hidden="false"
		/>
</sjg:grid>
</div>
</div>
</div>
</body>
</html>