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
<script type="text/javascript" src="<s:url value="/js/compliance/ComplianceMenuCtrl.js"/>"></script>
<script type="text/javascript">
	function floorSetting()
	{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/FloorSettingOver2Cloud/CreateFloorSetting.action?flag=CreateFloor",
	    success : function(data) {
	   		$("#data_part").html(data);
		},
	    error: function() {
	        alert("error");
	    }
	 	});
	}
	function wingsSetting()
	{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/FloorSettingOver2Cloud/CreateFloorSetting.action?flag=CreateWings",
	    success : function(data) {
	   		$("#data_part").html(data);
		},
	    error: function() {
	        alert("error");
	    }
	 	});
	}
	
	function roomSetting()
	{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/FloorSettingOver2Cloud/CreateFloorSetting.action?flag=CreateRoom",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	}
	function ccSetting()
	{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/FloorSettingOver2Cloud/CreateCCSetting.action",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	}
	function uploadExcel()
	{
		
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/FloorSettingOver2Cloud/readExcel.action",
		    success : function(data) {
	       		$("#data_part").html(data);
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
	 <div class="head">
	 Floor Setting</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">View</div> 
</div>
<div class="clear"></div>
<div class="listviewBorder"  style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
	<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
	<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
	<tbody>
	<tr></tr>
	<tr><td></td></tr>
	<tr>
	    <td>
		    <table class="floatL" border="0" cellpadding="0" cellspacing="0">
		    <tbody><tr><td class="pL10"></td></tr></tbody>
		    </table>
	    </td>
	     <!-- azad 25 July --> 
	    <td class="alignleft printTitle">
	    </td>
	    
	    
	    <td class="alignright printTitle">
	    <sj:a id="mappingButton5" button="true"  buttonIcon="ui-icon-plus"  cssClass="button" onclick="uploadExcel();">Upload</sj:a>
         <sj:a id="mappingButton1"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="floorSetting();">Floor Config</sj:a>
         <sj:a id="mappingButton4"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="wingsSetting();">Wings Config</sj:a>
         <sj:a id="mappingButton2"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="roomSetting();">Room Config</sj:a>
         <sj:a id="mappingButton3"  button="true"  buttonIcon="ui-icon-plus" cssClass="button" onclick="ccSetting();">CC Mapping</sj:a>
	    </td>   
	</tr>
	</tbody>
	</table>
	</div>
	</div>
<div class="clear"></div>
<div style="overflow: scroll; height: 430px;">
<s:url id="gridMappedFloor1" action="gridMappedFloor1"/>
<s:url id="gridMappedWings" action="gridMappedWings"/>
<s:url id="gridMappedRoom2" action="gridMappedRoom2"/>
<s:url id="gridMappedEmp_URL3" action="gridMappedEmp3"/>
<sjg:grid 
		            id="mappedEmpGrid1"
          			href="%{gridMappedFloor1}"
         			gridModel="masterViewList"
          			navigator="true"
          			navigatorAdd="false"
          			navigatorView="false"
          			navigatorDelete="false"
          			navigatorEdit="false"
          			navigatorSearch="false"
          			rowList="100,200,500"
          			rownumbers="-1"
          			viewrecords="true"       
          			pager="true"
          			pagerButtons="true"
          			pagerInput="true"   
          			multiselect="false"  
          			loadonce="%{loadonce}"
          			loadingText="Requesting Data..."  
          			rowNum="100"
          			autowidth="true"
          			navigatorEditOptions="{height:250,width:450}"
          			navigatorSearchOptions="{sopt:['eq','cn']}"
          		    navigatorEditOptions="{reloadAfterSubmit:true}"
          		    shrinkToFit="false"
                    >
          
          			<sjg:grid 
		            			id="mappedEmpGrid0"
          						href="%{gridMappedWings}"
         						gridModel="masterViewList0"
          						navigator="true"
          						navigatorAdd="false"
          						navigatorView="false"
          						navigatorDelete="false"
          						navigatorSearch="false"
          						navigatorEdit="false"
          					    rowList="10,25,50"
          						rownumbers="-1"
          						viewrecords="true"       
          						pager="true"
          						pagerButtons="true"
          						pagerInput="true"   
          						multiselect="false"  
          						loadonce="%{loadonce}"
          						loadingText="Requesting Data..."  
          						rowNum="10"
          						autowidth="true"
          						navigatorEditOptions="{height:230,width:400}"
          						navigatorSearchOptions="{sopt:['eq','cn']}"
          		    			navigatorEditOptions="{reloadAfterSubmit:true}"
          		    			shrinkToFit="false"
                    			>
          
          			<sjg:grid 
		            			id="mappedEmpGrid2"
          						href="%{gridMappedRoom2}"
         						gridModel="masterViewList1"
          						navigator="true"
          						navigatorAdd="false"
          						navigatorView="false"
          						navigatorDelete="false"
          						navigatorSearch="false"
          						navigatorEdit="false"
          					    rowList="10,25,50"
          						rownumbers="-1"
          						viewrecords="true"       
          						pager="true"
          						pagerButtons="true"
          						pagerInput="true"   
          						multiselect="false"  
          						loadonce="%{loadonce}"
          						loadingText="Requesting Data..."  
          						rowNum="10"
          						autowidth="true"
          						navigatorEditOptions="{height:230,width:400}"
          						navigatorSearchOptions="{sopt:['eq','cn']}"
          		    			navigatorEditOptions="{reloadAfterSubmit:true}"
          		    			shrinkToFit="false"
                    			>
        						<sjg:grid 
		            			id="mappedEmpGrid3"
          						href="%{gridMappedEmp_URL3}"
         						gridModel="masterViewList2"
          						navigator="true"
          						navigatorAdd="false"
          						navigatorView="false"
          						navigatorDelete="false"
          						navigatorSearch="false"
          						navigatorEdit="false"
          					    rowList="10,25,50"
          						rownumbers="-1"
          						viewrecords="true"       
          						pager="true"
          						pagerButtons="true"
          						pagerInput="true"   
          						multiselect="false"  
          						loadonce="%{loadonce}"
          						loadingText="Requesting Data..."  
          						rowNum="10"
          						autowidth="true"
          						navigatorEditOptions="{height:230,width:400}"
          						navigatorSearchOptions="{sopt:['eq','cn']}"
          		    			navigatorEditOptions="{reloadAfterSubmit:true}"
          		    			shrinkToFit="true"
                    			>
                    			
                    			<sjg:gridColumn 
										name="id"
										index="id"
										title="id"
									    width="100"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="true"
									/>
									
         							 <sjg:gridColumn 
										name="empName"
										index="empName"
										title="Mapped Person"
									    width="290"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
									/>
								
									<sjg:gridColumn 
										name="department"
										index="department"
										title="Department"
									    width="150"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
										/>
									
									<sjg:gridColumn 
										name="mobileNo"
										index="mobileNo"
										title="Mobile No"
									    width="220"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
									/>
									
									<sjg:gridColumn 
										name="emailid"
										index="emailid"
										title="Email Id"
									    width="240"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
									/>
 									</sjg:grid>
        
	        						<sjg:gridColumn 
										name="id"
										index="id"
										title="id"
									    width="100"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="true"
									/>
          
				 					<sjg:gridColumn 
										name="roomNo"
										index="roomNo"
										title="Room No"
									    width="560"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
									/>
								
								<sjg:gridColumn 
										name="extention"
										index="extention"
										title="Extention"
									    width="550"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
										/>
 							   </sjg:grid>
 							   
 							   <sjg:gridColumn 
										name="gridWings"
										index="gridWings"
										title="Wings Name"
									    width="1200"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
										/>
	        						
 									</sjg:grid> 
 							   
        <sjg:gridColumn 
										name="id"
										index="id"
										title="id"
									    width="100"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="true"
									/>
          
          							<sjg:gridColumn 
										name="gridFloorName"
										index="gridFloorName"
										title="Floor Name"
									    width="700"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
									/>
          
		          					<sjg:gridColumn 
										name="gridFloorCode"
										index="gridFloorCode"
										title="Floor Code"
									    width="650"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
										/>
	        						
 									</sjg:grid> 
</div>
</div>
</div>
</body>
</html>