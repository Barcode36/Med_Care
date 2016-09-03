<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<% String userRights=session.getAttribute("userRights")== null ? "" : session.getAttribute("userRights").toString();%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/helpdesk/fbdraft.js"/>"></script>
<script type="text/javascript">
timePick = function(elem) {
        $(elem).timepicker({timeFormat: 'hh:mm'});
        $('#time_picker_div').css("z-index", 2000);
}

function commSearch()
{
var feedbackType=$("#feedbackType").val();
//	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Feedback_Draft/beforeFeedDraftView.action?dataFor=ASTM&viewType=D&feedbackType="+feedbackType,
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
<div class="clear"></div>
<div class="middle-content">
<!-- Header Strip Div Start -->
<div class="list-icon">
	 <div class="head">Configure Feedback</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">View</div> 
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
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0" width="100%">
					    <tbody><tr><td class="pL10"><!-- Insert Code Here --><!--
					    
					    				    				    <s:select
									id="feedbackType" 
									name="feedbackType" 
									list="feedbackTypeMap"
									headerKey="-1"
									headerValue="Select Feedback Type" 
									cssStyle="height: 28px; width: 25%;"
									theme="simple"
									cssClass="button"
									onchange="commSearch();"
								/>-->
	
					    
					    </td></tr></tbody>
					  </table>
				  </td>
				  
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle"><!--
				      <sj:a id="downloadButton"  button="true"  buttonIcon="ui-icon-arrowstop-1-s" cssClass="button" cssStyle="height:25px; width:32px" title="Download" onclick="downloadFeedback('viewType','dataFor');" ></sj:a>
                      --><sj:a id="uploadButton"    button="true"  buttonIcon="ui-icon-arrowstop-1-n" cssClass="button" cssStyle="height:25px; width:32px" title="Upload"  onclick="uploadFeedback('viewType','dataFor');"></sj:a>
				      <%if(true){%>
				          <sj:a id="addButton"  button="true"  cssClass="button" cssStyle="height:25px;" title="Add" buttonIcon="ui-icon-plus" onclick="addNewFeedback('viewType','dataFor');">Add</sj:a>
				      <%}%>
				  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<!-- Code End for Header Strip -->

<div style="overflow: scroll; height: 430px;">
<s:hidden id="dataFor" value="%{dataFor}"/>
<s:hidden id="viewType" value="%{viewType}"/>
<s:url id="feedType_URL" action="viewDeptwiseFeedType" escapeAmp="false">
<s:param name="dataFor" value="%{dataFor}"/>
<s:param name="feedbackType" value="%{feedbackType}"/>
</s:url>
<s:url id="feedCategory_URL" action="viewAssetFeedCategory" />
<s:url id="feedSubCategory_URL" action="viewFeedSubCategory" />
<s:url id="editFeedType_URL" action="editFeedType"></s:url>
<s:url id="editfeedCategory_URL" action="editfeedCategory"></s:url>
<s:url id="editfeedSubCategory_URL" action="editfeedSubCategory"></s:url>
<div id="time_picker_div" style="display:none">
     <sj:datepicker id="res_Time_pick" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="10" />
</div>

          <sjg:grid 
		            id="gridfeedtype"
          			href="%{feedType_URL}"
         			gridModel="feedTypeData"
          			navigator="true"
          			navigatorAdd="false"
          			navigatorView="false"
          			navigatorDelete="false"
          			navigatorEdit="true"
          			navigatorSearch="true"
          			rowList="10,25,50"
          			viewrecords="true"       
          			pager="true"
          			pagerButtons="true"
          			pagerInput="true"   
          			multiselect="true"  
          			loadonce="false"
          			loadingText="Requesting Data..."  
          			rowNum="10"
          			autowidth="true"
          			editurl="%{editFeedType_URL}"
          			navigatorEditOptions="{height:250,width:450}"
          			navigatorSearchOptions="{sopt:['cn','eq']}"
          		    navigatorEditOptions="{reloadAfterSubmit:true}"
          		    shrinkToFit="false"
                    >
                    
                <sjg:grid 
		            			id="gridfeedcategory"
          						href="%{feedCategory_URL}"
         						gridModel="feedCategoryData"
          						navigator="true"
          						navigatorAdd="false"
          						navigatorView="false"
          						navigatorDelete="false"
          						navigatorSearch="true"
          						navigatorEdit="true"
          					    rowList="10,25,50"
          						viewrecords="true"       
          						pager="true"
          						pagerButtons="true"
          						pagerInput="true"   
          						multiselect="true"  
          						loadonce="false"
          						loadingText="Requesting Data..."  
          						rowNum="10"
          						autowidth="true"
          						editurl="%{editfeedCategory_URL}"
          						navigatorEditOptions="{height:230,width:400}"
          						navigatorSearchOptions="{sopt:['cn','eq']}"
          		    			navigatorEditOptions="{reloadAfterSubmit:true}"
          		    			shrinkToFit="false"
                    			>
                    
          						<sjg:grid 
		            							id="gridfeedsubcategory"
          										href="%{feedSubCategory_URL}"
         										gridModel="feedSubCategoryData"
          										navigator="true"
          										navigatorAdd="false"
          										navigatorView="false"
          										navigatorDelete="false"
          										navigatorSearch="true"
          										navigatorEdit="true"
          										rowList="10,25,50"
          										viewrecords="true"       
          										pager="true"
          										pagerButtons="true"
          										pagerInput="true"   
          										multiselect="true"  
          										loadingText="Requesting Data..."  
          										loadonce="false"
          										rowNum="10"
          										autowidth="true"
          										editurl="%{editfeedSubCategory_URL}"
          										navigatorEditOptions="{height:230,width:400}"
          										navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          		    							navigatorEditOptions="{reloadAfterSubmit:true}"
          		    							onEditInlineSuccessTopics="test"
          		    							shrinkToFit="false"
                    							>
          
		 										<s:iterator value="viewPageColumns" begin="4" id="feedSubCategoryColumnNames" >  
		 										<sjg:gridColumn 
		      													name="%{colomnName}"
		      													index="%{colomnName}"
		      													title="%{headingName}"
		      												    width="158"
		      													align="%{align}"
		      													editable="%{editable}"
		      													formatter="%{formatter}"
		      													formatoptions="%{editoptions}"
		      					    							search="true"
		      													hidden="%{hideOrShow}"
		 														/>
				   								</s:iterator> 
				   								
				   								<sjg:gridColumn 
		      													name="addressingTime"
		      													index="addressingTime"
		      													title="Addressing Time"
		      												    width="100"
		      													align="center"
		      													editable="true"
		      													editoptions="{dataInit:timePick}"
		      													search="false"
		 														/>
		 														
		 										<sjg:gridColumn 
		      													name="resolutionTime"
		      													index="resolutionTime"
		      													title="Resolution Time"
		      												    width="100"
		      													align="center"
		      													editable="true"
		      													editoptions="{dataInit:timePick}"
		      													search="false"
		 														/>
		 														
		 										<sjg:gridColumn 
		      													name="escalateTime"
		      													index="escalateTime"
		      													title="Escalation Time"
		      												    width="100"
		      													align="center"
		      													search="false"
		 														/>								 
				   								
				   								<sjg:gridColumn 
		      													name="escalationDuration"
		      													index="escalationDuration"
		      													title="Esc Duration"
		      												    width="98"
		      													align="center"
		      													editable="true"
		      													edittype="select"
		      				                                    editoptions="{value:'1:One;2:Two;3:Three;4:Four;5:Five'}"
		      					    							search="false"
		 														/>
				   								
				   								<sjg:gridColumn 
		      													name="escalationLevel"
		      													index="escalationLevel"
		      													title="Esc Level"
		      												    width="100"
		      													align="center"
		      													editable="true"
		      													edittype="select"
		      				                                    editoptions="{value:'Level1:Level1;Level2:Level2;Level3:Level3;Level4:Level4'}"
		      					    							search="true"
		 														/>
				   								
				   								<sjg:gridColumn 
		      													name="needEsc"
		      													index="needEsc"
		      													title="Need Escalation"
		      												    width="100"
		      													align="center"
		      													editable="true"
		      													edittype="select"
		      				                                    editoptions="{value:'Y:Yes;N:No'}"
		      					    							search="true"
		 														/>
		 														
		 										<sjg:gridColumn 
		      													name="hide_show"
		      													index="hide_show"
		      													title="Status"
		      												    width="100"
		      													align="center"
		      													editable="false"
		      													edittype="select"
		      				                                    editoptions="{value:'Active:Active;D-Active:D-Active'}"
		      					    							search="true"
		      					    							hidden="false"
		 														/>
		 										</sjg:grid>        
		 							   <s:iterator value="viewPageColumns" begin="2" end="3" id="feedCategoryColumnNames" >  
		 								<sjg:gridColumn 
		      												name="%{colomnName}"
		      												index="%{colomnName}"
		      												title="%{headingName}"
		      												width="1013"
		      												align="%{align}"
		      												editable="%{editable}"
		      												formatter="%{formatter}"
		      					    						search="true"
		      												hidden="%{hideOrShow}"
		 													/>
				   						</s:iterator>  
				   						<sjg:gridColumn 
		      													name="hide_show"
		      													index="hide_show"
		      													title="Status"
		      												    width="100"
		      													align="center"
		      													editable="false"
		      													edittype="select"
		      				                                    editoptions="{value:'Active:Active;D-Active:D-Active'}"
		      					    							search="true"
		      					    							hidden="false"
		 														/>
		 								</sjg:grid>        
		 						 <s:iterator value="viewPageColumns" begin="0" end="1" id="feedTypeColumnNames" >  
		 						  <sjg:gridColumn 
		      										 name="%{colomnName}"
		      										 index="%{colomnName}"
		      										 title="%{headingName}"
		      										 width="1213"
		      										 align="%{align}"
		      										 editable="%{editable}"
		      										 formatter="%{formatter}"
		      					    				 search="true"
		      										 hidden="%{hideOrShow}"
		 											 />
				                 </s:iterator>  
		                         </sjg:grid>
          	
</div>
</div>
</div>
</body>
</html>