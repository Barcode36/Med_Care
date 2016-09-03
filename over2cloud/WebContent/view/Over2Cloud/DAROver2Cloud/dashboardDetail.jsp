<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<link  type="text/css" href="<s:url value="/css/table.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/js.js"/>"></script>
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}" />
<SCRIPT type="text/javascript">
$.subscribe('getDarSubmission', function(event,data)
		  {
			  var taskid            = jQuery("#gridedittable23").jqGrid('getGridParam', 'selrow');
			  $("#dar_status").load("view/Over2Cloud/DAROver2Cloud/beforeDashAddAction.action?taskIdForDash="+taskid);
			  $("#dar_status").dialog('open');
				});
</SCRIPT>
</head>
<body>
<div class="page_title"><h1><s:property value="%{mainHeaderName}"/> </h1></div>
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<!-- <s:property value="%{idDash}"/> -->
<s:url id="dashboardView" action="dashboardView" escapeAmp="false">
   <s:param name="idDash" value="%{idDash}" />
</s:url>

<center>
<div class="form_inner" id="form_reg">
<div class="page_form">
<center>
<sjg:grid 
		  id="gridedittable23"
          caption="%{mainHeaderName}"
          href="%{dashboardView}"
          gridModel="viewListForDashboard"
          navigatorEdit="false"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorSearch="true"
          rowList="10,15,20,25"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="5"
          width="700"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ew']}"
          shrinkToFit="false"
          navigatorViewOptions="{width:500}"
          >
          
		<s:iterator value="taskTypeViewProp" id="taskTypeViewProp" >  
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

        <sj:dialog
          id="dar_status"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Take Action on DAR Submission"
          modal="true"
          closeTopics="closeEffectDialog"
          width="800"
          height="500"
          draggable="true"
    	  resizable="true"
          >
          </sj:dialog>
    <center>
             <div class="fields">
		     <ul>
			 <li class="submit" >
			 <div class="type-button">
	         <sj:submit 
	                        value=" Take Action " 
	                        effect="highlight"
	                        button="true"
	                        cssClass="submit"
	                        onClickTopics="getDarSubmission"
	                        />
	        </div>
	        </li>
		  </ul>
	      </div>
	         </center>
	         <div id="dar_status"></div>
	          <sj:dialog
          id="dar_status"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Take Action"
          modal="true"
          closeTopics="closeEffectDialog"
          width="800"
          height="500"
          draggable="true"
    	  resizable="true"
          >
          </sj:dialog>

</center>
</div>
</div>
</center>
</div>
</div>
</body>
</html>