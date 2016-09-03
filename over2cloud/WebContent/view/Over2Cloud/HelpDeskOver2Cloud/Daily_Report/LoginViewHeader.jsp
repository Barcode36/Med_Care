<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/helpdesk/reportconfig.js"/>"></script>
 
 <script type="text/javascript">
function refreshRow()
{
	gridLoad(); 
}
 
function gridLoad()
{
	var field=null,string=null;
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Daily_Report/loginUserView.action?searchField="+field+"&searchString="+string,
	    success : function(subdeptdata) {
       $("#"+"result").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
gridLoad();

 
function getOnChangeLoginUser(field,string)
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Daily_Report/loginUserView.action?searchField="+field+"&searchString="+string,
	    success : function(subdeptdata) {
	   $("#"+"result").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function wildSearch()
{
	
	var field="null";
	 var string= $("#wildSearch").val();
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Daily_Report/loginUserView.action?searchField="+field+"&searchString="+string,
	    success : function(subdeptdata) {
	   $("#"+"result").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function logoutUser()
{
	//alert("hiii");
var acntId = $("#id").val();
var id = jQuery("#gridreport").jqGrid('getGridParam', 'selarrrow');
var feedid    = jQuery("#gridreport").jqGrid('getGridParam', 'selrow');
  if(id.length==0 )
{
     	alert("Please select atleast one check box...");        
     	 
} 
else if(feedid!=null)
 {
 $("#result").html('<br><br><br><br><br><br><br><br><center><img src="images/ajax-loaderNew.gif"></center>');
 	 $.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Daily_Report/beforeLogoutUser.action?feedId="+id,
	    success : function(subdeptdata) {
	    	gridLoad();
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

<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">Login&nbsp;Report</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 
</div> 

<div class="clear"></div>
<div class="listviewBorder" style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<!-- Code For Header Strip -->
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <!-- Block for insert Left Hand Side Button -->
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">    <!-- Insert Code Here -->  
				 			<sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true"  onclick="refreshRow()"></sj:a>
				  	   	 
					           <s:select  
						      		id					=		"dept"
						    		name				=		"dept"
						    		list				=		"#{'1': 'Barber', '2' : 'Biomedical', '3': 'Dietician', '4' : 'Engineering', '5' : 'Food & Beverages', '6' : 'Housekeeping', '7' : 'Operations', '8' : 'OCC' }"
						      		headerKey           =       "-1"
							        headerValue         =       "All Department"
							       cssClass             =      "button"
							       cssStyle             =      "margin-top: -4px;margin-left:4px"
							      theme                 =       "simple"
						      	 onchange			=		"getOnChangeLoginUser('id',this.value) "
						      	 >
						      	 </s:select>
					    
					     </td>
					     <td class="pL10"> 
					         	<s:textfield  id="wildSearch" name="wildSearch" onkeyup="wildSearch();" cssClass="button" cssStyle="width: 100px;height: 20px;margin-top: -4px ;margin-left: 2px;" Placeholder="Enter Any Value" theme="simple"/>
	     	    
					     </td>
					     
					     </tr></tbody>
					  </table>
				  </td>
				  
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				      <sj:a id="logoutButton"  button="true"  cssClass="button" cssStyle="height:25px;" title="LogOut" buttonIcon="ui-icon-locked" onclick="logoutUser();">LogOut</sj:a>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<!-- Code End for Header Strip -->
<div style="overflow: scroll; height: 280px;">
 
<div id="result"></div>
</div>
</div>
</div>
</body>
</html>