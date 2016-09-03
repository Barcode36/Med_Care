<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<!-- Code End for Header Strip -->
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Corporate Patient </div>
	<div class="head">
	<img alt="" src="images/forward.png" style="margin-top:10px;margin-right:3px; float: left;">Activity 
	</div>
	
	</div>	
	<div class="clear"></div>
 <div style="width:99%;    margin: 5px 0px 0px 6px; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; ">
 <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
 
<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">   
					     	    <sj:a id="editButton" title="Edit"  buttonIcon="ui-icon-pencil" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="editRow()"></sj:a>
                                <sj:a id="delButton" title="Deactivate" buttonIcon="ui-icon-trash" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="deleteRow()"></sj:a>
					            <sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="searchRow()"></sj:a>
					            <sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssClass="button" cssStyle="height:25px; width:32px" button="true" onclick="reloadRow();"></sj:a>
		                        <s:select 
		                            id="servicesOpt"  
		                            list="#{'EHC':'EHC','OPD':'OPD','Radiology':'Radiology','IPD':'IPD','Day Care':'Day Care','Laboratory':'Laboratory','Emergency':'Emergency'}" 
		                            cssClass="button"
		                            theme="simple" 
		                            headerKey="-1"
		                            headerValue="Select Services "
		                            onchange="getMyServiceDet(this.value);">
		                            
		                         </s:select>
		       	                 
		                     </td></tr></tbody>
					  </table>
				  </td>
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
				           <sj:a button="true" cssClass="button" cssStyle="height:25px;"  title="Add"  buttonIcon="ui-icon-plus" onclick="addConfigurePage();">Add</sj:a> 
				  </td>
			</tr>
		</tbody>
	</table>



</div>
</div>
</div>
<div style="overflow: scroll;">
<s:url id="modifyConfiguration" action="modifyConfiguration"></s:url>
<s:url id="viewSetting" action="viewSetting" escapeAmp="false" >
<s:param name="headingName" value="%{headingName}"></s:param>
</s:url>

<sjg:grid 
		     id="gridSetting"
	          href="%{viewSetting}"
	          gridModel="masterViewProp"
	          navigator="true"
	          navigatorAdd="false"
	          navigatorView="true"
	          navigatorDelete="true"
	          navigatorEdit="true"
	          navigatorSearch="true"
	          editinline="false"
	          viewrecords="true"   
	          rowList="200,400,1000,2000,5000,8000"
	          rowNum="200"
	          pager="true"
	          dataType="json"
	          gridview="true"
	          pagerButtons="true"
	          rownumbers="true"
	          pagerInput="true"   
	          navigatorSearchOptions="{sopt:['eq','cn']}"  
	          loadingText="Requesting Data..."  
	          groupSummary="true"
	          userDataOnFooter="true"
	          
	          navigatorEditOptions="{height:330,width:400}"
	          editurl="%{modifyConfiguration}"
	          navigatorEditOptions="{closeAfterEdit:true,reloadAfterSubmit:true}"
	          
	          shrinkToFit="false"
	          autowidth="true"
	          loadonce="true"
	          onSelectRowTopics="rowselect"
	          onEditInlineSuccessTopics="oneditsuccess"
	          
	          multiselect="true"
	          onCompleteTopics="closeTop"
	       	  filter="true"
       		  filterOptions="{searchOnEnter:false,defaultSearch:'cn'}"
       		 
       		 
          >
          
		<s:iterator value="viewPageColumns" id="viewPageColumns" > 
		
					<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="%{headingName}"
					width="150"
					align="%{align}"
					formatter="%{formatter}"
					editable="%{editable}"
					edittype="%{edittype}"
				    editoptions="%{editoptions}"
					search="%{search}"
					hidden="%{hideOrShow}"
					/>
			
		</s:iterator>  
</sjg:grid>
</div>
</div>
</body>
</html>