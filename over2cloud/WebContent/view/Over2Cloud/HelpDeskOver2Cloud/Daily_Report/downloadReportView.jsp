<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"   uri="/struts-tags"%>
<%@ taglib prefix="sj"  uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<s:if test="#session['uName']==null || #session['uName']==''">
<jsp:forward page="/view/common_pages/invalidSession.jsp"/>
</s:if>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
$.subscribe('getSelected4Save', function(event,data) {
	var emailid = document.getElementById("emailid").value;
	var subject = document.getElementById("subject").value;
	$.ajax({
		 type : "post",
		 url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Daily_Report/downloadReportExcel.action?emailid="+emailid+"&subject="+subject,
		 success : function(saveData) {
				$("#reportDialouge").dialog({height:80,width:400, title:'Success Message'});
				 $("#reportDialouge").dialog('widget').position({my:'center',at:'center',of:window});
				$("#downloadReportResult").html(saveData);
				setTimeout(function(){ $("#reportDialouge").dialog('close'); }, 2000);
			},
			error: function() {
			     alert("error");
			}
			});
	});

</script>
</head>
<body>
<div id="downloadReportResult">
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<div class="form_inner" id="form_reg">
<div class="page_form">
<s:url id="report_URL" action="viewDownloadReportData" escapeAmp="false">
<s:param name="by_dept"   value="%{by_dept}"></s:param>
<s:param name="to_dept"   value="%{to_dept}"></s:param>
<s:param name="to_sdept"  value="%{to_sdept}"></s:param>
<s:param name="feed_type" value="%{feed_type}"></s:param>
<s:param name="category"  value="%{category}"></s:param>
<s:param name="sub_catg"  value="%{sub_catg}"></s:param>
<s:param name="from_date" value="%{from_date}"></s:param>
<s:param name="to_date"   value="%{to_date}"></s:param>
<s:param name="status"    value="%{status}"></s:param>
<s:param name="pageType"  value="%{pageType}"></s:param>
</s:url>
<sjg:grid 
		  id="gridedittable"
          caption="Report"
          href="%{report_URL}"
          gridModel="downloadReportData"
          navigator="true"
          navigatorAdd="false"
          navigatorView="false"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="false"
          rowList="15,30,50"
          rowNum="15"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="false"  
          loadonce="%{loadonce}"
          loadingText="Requesting Data..."  
          autowidth="true"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          shrinkToFit="false"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          >
     
          
		 <s:iterator value="feedbackColumnNames" id="feedbackColumnNames" >  
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
	<br><!--
	
	   <div class="newColumn">
			 <div class="leftColumn">Email Id:</div>
	            <div class="rightColumn">
	                 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                 <s:textfield name="emailid" id="emailid"  cssClass="textField" maxlength="250"  placeholder="Enter Email Id's" />
	            </div>
	   </div>
	    <div class="newColumn">
			 <div class="leftColumn">Subject:</div>
	            <div class="rightColumn">
	                 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                 <s:textfield name="subject" id="subject"  cssClass="textField" maxlength="250"  placeholder="Enter Subject" />
	            </div>
	   </div>
--><div class="clear"></div>
<div align="center">	
<s:form action="downloadReportExcel" id="downloadreportid" namespace="/view/Over2Cloud/HelpDeskOver2Cloud/Daily_Report">
<s:hidden name="status" value="%{status}"/>
   <sj:submit id="saveExcelButton" value=" Download " button="true" />
</s:form>
</div>	
</div>
</div>
</div>
</div>
</div>
</body>
</html>