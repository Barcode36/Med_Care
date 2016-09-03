<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<script>

	function saveSelected()
	{
		alert("Hello bhai how r u ????");
//
	//	var reportName=document.getElementById("reportName").value;
		var reportName = $("#reportName").val();
		var reportDes = $("#reportDesc").val();
		var tableName = $("#dataTableName").val();
		var columnsId = $("#columnsId").val();
		alert("hey!!! the value is:  "+reportName);
		alert("hey!!! the value is:  "+reportDes);
		alert("hey!!! the value is:  "+tableName);
		alert("hey!!! the value is:  "+columnsId);


		var conP = "<%=request.getContextPath()%>";

		alert("Action URL is as <><><> "+conP+"/view/Over2Cloud/reportsConfiguration/saveReportsAdd.action?reportName="+reportName+"&reportDes="+reportDes+"&tableName="+tableName+"&columnName="+columnsId);


		console.log("Action URL is as <><><> "+conP+"/view/Over2Cloud/reportsConfiguration/saveReportsAdd.action?reportName="+reportName.split(" ").join("%20")+"&reportDes="+reportDes.split(" ").join("%20")+"&tableName="+tableName+"&columnName="+columnsId.split(" ").join("%20"));
		
		 $("#excelDialogBox").load(conP+"/view/Over2Cloud/reportsConfiguration/saveReportsAdd.action?reportName="+reportName.split(" ").join("%20")+"&reportDes="+reportDes.split(" ").join("%20")+"&tableName="+tableName.split(" ").join("%20")+"&columnName="+columnsId.split("#").join(","));                                             
		   $("#excelDialogBox").dialog('open');

		
		//$("#ExcelDialog").load(conP+"/view/Over2Cloud/reportsConfiguration/getColumnsNamesNdTableName.action?id="+id+"&tableName="+tableName);                                             
		  // $("#ExcelDialog").dialog('open');
	}

</script>
<s:url  id="contextz" value="/template/theme" />
</head>
<body>
<sj:dialog 
                                id="excelDialogBox" 
                                autoOpen="false"
                    			closeOnEscape="true" 
                    			modal="true" 
                    			title="Reports >> Selected Column Values" 
                    		    width="950" 
                    			height="350" 
                    			showEffect="slide" 
                    			hideEffect="explode">
                    </sj:dialog> 
<div class="page_title"><h1>Selected Data View</h1></div>
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<div class="form_inner" id="form_reg">
<div class="page_form">
<s:url id="viewReport" action="viewReportGrid" escapeAmp="false">
<s:param name="columnName" value="%{columnName}"/>
<s:param name="tableName" value="%{tableName}"/>
</s:url>
<sjg:grid 
		  id="gridedittable"
          caption="%{mainHeaderName}"
          href="%{viewReport}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorSearch="false"
          rowList="10,15,20,25"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="5"
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
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
		/>
		</s:iterator>  
</sjg:grid>

<br />
<br />
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formone" name="formone" theme="simple"  method="post"enctype="multipart/form-data" >
		<s:hidden id="columnsId" value="%{columnName}"></s:hidden>
		<s:hidden id="dataTableName" value="%{tableName}"></s:hidden>
		<s:property value="%{columnName}"/>
		<s:property value="%{tableName}"/>
				 <div class="form_menubox">
                  <div class="user_form_text">Report Name:</div>
                  <div class="user_form_input">
						<s:textfield name="reportName" id="reportName" cssClass="form_menu_inputtext" placeholder="Report Name" />
                  </div>
                  <div class="user_form_text1">Reports Description:</div>
                  <div class="user_form_input">
						<s:textarea name="reportDesc" id="reportDesc" cssClass="form_menu_inputtext" placeholder="Report Description" />
                  </div>
                  </div>
                  
                  <div class="fields">
					<div class="type-button">
					<center>
	                <sj:submit 
	                        value="  Save Reports  " 
	                        button="true"
	                        cssClass="submit"
	                        onclick="saveSelected()"
	                        />
	                        </center>
	               </div>
				</div>
                  
                  
			</s:form>
</div>
</div>
</div>
</div>
</div>
</div>
</body>
</html>