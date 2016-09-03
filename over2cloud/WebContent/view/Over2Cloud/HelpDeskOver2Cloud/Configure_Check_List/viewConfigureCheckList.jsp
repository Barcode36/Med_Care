<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
 
<script type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
	row = event.originalEvent.id;
});	

function checkList(cellvalue, options, rowObject) 
{
    
	var context_path = '<%=request.getContextPath()%>';
     return "  <img title='Completion Tip' src='"+ context_path +"/images/check.jpg' height='20' width='20' onClick='javascript:checkListFormatter("+rowObject.id+");' />";

}
function checkListFormatter(id) 
{ 
    $("#takeActionGrid").dialog({title: 'Completion Tip ' });
    $("#takeActionGrid").dialog('open');
    $("#takeActionGrid").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $("#takeActionGrid").load("<%=request.getContextPath()%>/view/Over2Cloud/HelpDeskOver2Cloud/Configure_Check_List/viewCheckList?SubCatID="+id+"&dataFrom=completionTip");
    
       return false;
} 


function krList(cellvalue, options, rowObject) 
{
 
	var context_path = '<%=request.getContextPath()%>';
     return "  <img title='Completion Tip' src='"+ context_path +"/images/share.jpg' height='20' width='20' onClick='javascript:krFormatter("+rowObject.id+");' />";

}
function krFormatter(id) 
{ 
    $("#takeActionGrid").dialog({title: 'KR List ' });
    $("#takeActionGrid").dialog('open');
    $("#takeActionGrid").html("<br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $("#takeActionGrid").load("<%=request.getContextPath()%>/view/Over2Cloud/HelpDeskOver2Cloud/Configure_Check_List/viewKrList?SubCatID="+id+"&dataFrom=KR");
    
       return false;
} 


function addConfigureCheckList()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Configure_Check_List/addConfigureCheckList.action",
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
          width="400"
          height="350"
          draggable="true"
          resizable="true"
          position="center"
          >
</sj:dialog>
<div class="middle-content">
<!-- Header Strip Div Start -->
<div class="list-icon">
	 <div class="head">Completion Tip</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head"> View</div> 
</div>
<div class="clear"></div>
<div class="listviewBorder"  style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<!-- Code For Header Strip -->
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <!-- Block for insert Left Hand Side Button -->
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">    <!-- Insert Code Here -->   </td></tr></tbody>
					  </table>
				  </td>
				  
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				      <sj:a id="addButton"  button="true"  cssClass="button" cssStyle="height:25px;" title="Add" buttonIcon="ui-icon-plus" onclick="addConfigureCheckList();">Add</sj:a>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<!-- Code End for Header Strip -->
<div style="overflow: scroll; height: 430px;">
  <s:url id="configurechecklist_URL" action="viewConfigureData" />
<!--  <s:url id="editShiftConf_URL" action="editShiftConf"></s:url> -->
 
<sjg:grid 
		  id="gridchecklisttable"
           href="%{configurechecklist_URL}" 
          gridModel="checkListData"
          navigator="true"
          navigatorAdd="false"
          navigatorView="false"
          navigatorDelete="false"
          navigatorEdit="true"
          navigatorSearch="true"
          rowList="10,25,50"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="true"  
           
           loadingText="Requesting Data..."  
          rowNum="10"
          autowidth="true"
            navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['cn','eq']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          onSelectRowTopics="rowselect"
          >
		  
				  
				 <sjg:gridColumn    name="id"
                           		    index="id"
                                    title="ID"
                                    width="100"
                                    editable="false"
                            	 	 sortable="true"
		                    		search="true"
                            		align="center"
                            		hidden="true">
                </sjg:gridColumn>
                
				 <sjg:gridColumn    name="department"
                           		    index="department"
                                    title="Department"
                                    width="100"
                                    editable="false"
                            	 	 sortable="true"
		                    		search="true"
                            		align="center">
                </sjg:gridColumn>
           
                <sjg:gridColumn    name="subdepartment"
                           		    index="subdepartment"
                                    title="Sub Department"
                                    width="200"
                                    editable="false"
                            	 	sortable="true"
		                    		search="true"
                            		align="center">
                </sjg:gridColumn>
                
                 <sjg:gridColumn    name="feedbacktype"
                           		    index="feedbacktype"
                                    title="Feedback Type"
                                    width="200"
                                    editable="false"
                            	  	sortable="true"
		                    		search="true"
                            		align="center">
                </sjg:gridColumn>
                
                
                 <sjg:gridColumn    name="category"
                           		    index="category"
                                    title="Category"
                                    width="150"
                                    editable="false"
                            	 	 sortable="true"
		                    		search="true"
                            		align="center">
                </sjg:gridColumn>
                
                 <sjg:gridColumn    name="subcategory"
                           		    index="subcategory"
                                    title="Sub Category"
                                    width="200"
                                    editable="false"
                            	 	 	sortable="true"
		                    		search="true"
                            		align="center">
                </sjg:gridColumn>
                
                 <sjg:gridColumn    name="completiontip"
                           		    index="completiontip"
                                    title="Completion Tip"
                                    width="200"
                                    editable="false"
                            	 	 sortable="true"
		                    		search="true"
                            		align="center"
                            		formatter="checkList">
                </sjg:gridColumn>
             
              <sjg:gridColumn    name="kr"
                           		    index="kr"
                                    title="KR List"
                                    width="200"
                                    editable="false"
                            	 	 sortable="true"
		                    		search="true"
                            		align="center"
                            		formatter="krList">
                </sjg:gridColumn>
             
             


 </sjg:grid>
          
	           
</div>
</div>
</div>
</body>
</html>