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
datePick = function(elem) {
        $(elem).timepicker({timeFormat: 'hh:mm'});
        $('#time_picker_div').css("z-index", 2000);
}
</script>
</head>
<body>
<div class="page_title"><h1>Feedback Draft Detail</h1></div>
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<div class="form_inner" id="form_reg">
<div class="page_form">

<s:url id="subDept_URL" action="viewFeedSubDept" />
<s:url id="feedType_URL" action="viewFeedType" />
<s:url id="feedCategory_URL" action="viewFeedCategory" />
<s:url id="feedSubCategory_URL" action="viewFeedSubCategory" />

<s:url id="editFeedType_URL" action="editFeedType"></s:url>
<s:url id="editfeedCategory_URL" action="editfeedCategory"></s:url>
<s:url id="editfeedSubCategory_URL" action="editfeedSubCategory"></s:url>
<div id="time_picker_div" style="display:none">
     <sj:datepicker id="res_Time_pick" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" />
</div>
<center>
<sjg:grid 
		  id="gridedittable"
          caption="%{subDeptHeader}"
          href="%{subDept_URL}"
          gridModel="subDeptData"
          navigator="true"
          navigatorAdd="false"
          navigatorView="false"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="false"
          rowList="10,25,50"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="true"  
          loadonce="%{loadonce}"
          loadingText="Requesting Data..."  
          rowNum="10"
          width="835"
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          >
          <sjg:grid 
		            id="gridfeedtype"
                    caption="%{feedTypeHeader}"
          			href="%{feedType_URL}"
         			gridModel="feedTypeData"
          			navigator="true"
          			navigatorAdd="false"
          			navigatorView="false"
          			navigatorDelete="false"
          			navigatorEdit="%{modifyFlag}"
          			navigatorSearch="false"
          			rowList="10,25,50"
          			rownumbers="-1"
          			viewrecords="true"       
          			pager="true"
          			pagerButtons="true"
          			pagerInput="true"   
          			multiselect="true"  
          			loadonce="%{loadonce}"
          			loadingText="Requesting Data..."  
          			rowNum="10"
          			width="742"
          			editurl="%{editFeedType_URL}"
          			navigatorEditOptions="{height:250,width:450}"
          			navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          		    navigatorEditOptions="{reloadAfterSubmit:true}"
          		    shrinkToFit="false"
                    >
                    
                <sjg:grid 
		            			id="gridfeedcategory"
                    			caption="%{categoryHeader}"
          						href="%{feedCategory_URL}"
         						gridModel="feedCategoryData"
          						navigator="true"
          						navigatorAdd="false"
          						navigatorView="false"
          						navigatorDelete="false"
          						navigatorSearch="false"
          						navigatorEdit="%{modifyFlag}"
          					    rowList="10,25,50"
          						rownumbers="-1"
          						viewrecords="true"       
          						pager="true"
          						pagerButtons="true"
          						pagerInput="true"   
          						multiselect="true"  
          						loadonce="%{loadonce}"
          						loadingText="Requesting Data..."  
          						rowNum="10"
          						width="649"
          						editurl="%{editfeedCategory_URL}"
          						navigatorEditOptions="{height:230,width:400}"
          						navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          		    			navigatorEditOptions="{reloadAfterSubmit:true}"
          		    			shrinkToFit="false"
                    			>
                    
          						<sjg:grid 
		            							id="gridfeedsubcategory"
                    							caption="%{subCategoryHeader}"
          										href="%{feedSubCategory_URL}"
         										gridModel="feedSubCategoryData"
          										navigator="true"
          										navigatorAdd="false"
          										navigatorView="false"
          										navigatorDelete="false"
          										navigatorSearch="false"
          										navigatorEdit="%{modifyFlag}"
          										rowList="10,25,50"
          										rownumbers="-1"
          										viewrecords="true"       
          										pager="true"
          										pagerButtons="true"
          										pagerInput="true"   
          										multiselect="true"  
          										loadingText="Requesting Data..."  
          										loadonce="%{loadonce}"
          										rowNum="10"
          										width="555"
          										editurl="%{editfeedSubCategory_URL}"
          										navigatorEditOptions="{height:230,width:400}"
          										navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          		    							navigatorEditOptions="{reloadAfterSubmit:true}"
          		    							shrinkToFit="false"
                    							>
          
		 										<s:iterator value="feedSubCategoryColumnNames" id="feedSubCategoryColumnNames" >  
		 										<sjg:gridColumn 
		      													name="%{colomnName}"
		      													index="%{colomnName}"
		      													title="%{headingName}"
		      												    width="%{width}"
		      													align="%{align}"
		      													editable="%{editable}"
		      													formatter="%{formatter}"
		      													formatoptions="%{editoptions}"
		      					    							search="%{search}"
		      													hidden="%{hideOrShow}"
		 														/>
				   								</s:iterator>  
				   								
		 										</sjg:grid>        
          
		 							   <s:iterator value="feedCategoryColumnNames" id="feedCategoryColumnNames" >  
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
          
		 						 <s:iterator value="feedTypeColumnNames" id="feedTypeColumnNames" >  
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
          
		           <s:iterator value="subDeptColumnNames" id="subDeptColumnNames" >  
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
</center>
</div>
</div>
</div>
</div>
</body>
</html>