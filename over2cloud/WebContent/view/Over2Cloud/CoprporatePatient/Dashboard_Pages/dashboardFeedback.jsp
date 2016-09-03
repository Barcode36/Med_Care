<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/js.js"/>"></script>

<script type="text/javascript">
		    $.subscribe('getFormData', function(event,data)
		  {
			   var id = jQuery("#gridedittable").jqGrid('getGridParam', 'selarrrow');
				var feedid    = jQuery("#gridedittable").jqGrid('getGridParam', 'selrow');
				 if(id.length==0 )
				{
				     	alert("Please select atleast one check box...");        
				     	 
				}else if(id.length>1)
				{
				 	alert("Please select only one check box...");        
				     	 
				}
				else if(feedid!=null)
				 {
			  
				  $("#printSelect").dialog({title: 'Print Ticket',width: 800,height: 400});
				  $("#printSelect").dialog('open');
				  $("#printSelect").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
				  $("#printSelect").load("view/Over2Cloud/CorporatePatientServices/Lodge_Feedback/beforeViewActivityHistoryData?id="+feedid );
			  }	  
		  });
		  
		    
  
		     
		    
		  function downloadFeedStatus()
		  {
			
		    	 	 
		  	$("#printSelect").dialog({title: 'Check Column',width: 350,height: 600});
		  	$("#printSelect").dialog('open');
		  	$.ajax({
		  	    type : "post",
		  	     url : "view/Over2Cloud/CorporatePatientServices/Dashboard_Pages/feedbackStatusDownloadORD.action?feedStatus="+$('#feedStatus').val()+"&fromDate="+$('#fromDate').val()+"&toDate="+$('#toDate').val()+"&dashFor="+$("#dashFor").val()+"&dataFlag="+$("#dataFlag").val()+"&machineId="+$("#machineId").val()+"&location="+$("#location").val()+"&feedId="+$("#feedId").val(),
				
		  	    		success : function(data) 
		  	    {
		   			$("#printSelect").html(data);
		  		},
		  	   error: function() {
		  	        alert("error in");
		  	    }
		  	 }); 
		  }

</script>
</head>
<body>
<sj:dialog id="printSelect" title="Print Ticket" autoOpen="false"  modal="true" height="400" width="800" showEffect="drop">
<sj:dialog id="feed_status" title="Take Action On Feedback" modal="true" effect="slide" autoOpen="false" width="1000" hideEffect="explode" position="['center','top']"></sj:dialog>
<div id="ticketsInfo"></div>
</sj:dialog>
<input type="hidden" id="conString"  value="<%=request.getContextPath()%>"/>
<s:hidden id="feedStatusValue" value="%{feedStatus}"></s:hidden>
<div class="clear"></div>
<div class="middle-content">
<!-- //////////////////////////////////////////// -->
<div class="clear"></div>
<div class="border" >

<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
<tbody>
<tr><td> 
<table class="floatL" border="0" cellpadding="0" cellspacing="0">
<tbody>
<tr><td class="pL10"> 
<%-- <s:if test="%{feedStatus=='Pending' || feedStatus=='Snooze' || feedStatus=='High Priority'}">
<sj:submit value=" Take Action " button="true" onClickTopics="getStatusForm"/>
</s:if> --%>

</td></tr>
</tbody>
</table>
<td class="alignright printTitle"><sj:a  button="true" cssClass="button" buttonIcon="ui-icon-print" onClickTopics="getFormData">Print</sj:a>
<sj:a  button="true" cssClass="button" cssStyle="height:24px; width:31px;"  title="Download"   onClick="downloadFeedStatus();" buttonIcon="ui-icon-arrowstop-1-s"></sj:a>
</td></tr>
</tbody>
</table>
</div>
</div>

 <div class="clear"></div>
<s:hidden name="feedStatus" value="%{feedStatus}"></s:hidden>
<s:hidden name="escFlag" value="%{escFlag}"></s:hidden>
<s:url id="viewBoardData" action="dashFeedbackDetail" escapeAmp="false" namespace="/view/Over2Cloud/CoprporatePatient/Dashboard_Pages">
<s:param name="minDateValue" value="%{minDateValue}"></s:param>
<s:param name="maxDateValue" value="%{maxDateValue}"></s:param>
<s:param name="corp_name" value="%{corp_name}"></s:param>
<s:param name="services" value="%{services}"></s:param>
<s:param name="status_type" value="%{status_type}"></s:param>
<s:param name="med_location" value="%{med_location}"></s:param>
<s:param name="spec" value="%{spec}"></s:param>
<s:param name="pack" value="%{pack}"></s:param>
<s:param name="service_manager" value="%{service_manager}"></s:param>
<s:param name="ac_manager" value="%{ac_manager}"></s:param>

</s:url>

<sjg:grid 
		  id="gridedittable"
          href="%{viewBoardData}"
          gridModel="cpsGridData"
          navigator="false"
          navigatorAdd="false"
          navigatorView="false"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="false"
          rowList="50,500,1000,2000,5000"
          editinline="true"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400,closeAfterEdit:true,reloadAfterSubmit:true}"
		  navigatorDeleteOptions="{caption:'Inactive',msg: 'Inactive selected record(s)?',bSubmit: 'Inactive',closeAfterEdit:true,reloadAfterSubmit:true}"
          navigatorSearchOptions="{sopt:['eq','cn']}"
          rowNum="50"
          autowidth="true"
          editurl="%{editDeptDataGrid}"
          shrinkToFit="false"
          onSelectRowTopics="rowselect"
          navigatorCloneToTop="true"
          >
          
		<s:iterator value="masterViewPropCPS" id="masterViewPropCPS" > 
		
					<sjg:gridColumn 
					name="%{colomnName}"
					index="%{colomnName}"
					title="%{headingName}"
					width="%{width}"
					align="%{align}"
					editable="%{editable}"
					search="%{search}"
					hidden="%{hideOrShow}"
					/>
			
		</s:iterator>  
</sjg:grid>
</div>
</div>
</body>


</html>