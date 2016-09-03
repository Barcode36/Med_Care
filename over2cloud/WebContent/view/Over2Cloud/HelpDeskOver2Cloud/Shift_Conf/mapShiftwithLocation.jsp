<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/helpdesk/shiftConf.js"/>"></script>
<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<script type="text/javascript">
$.subscribe('completeData', function(event,data)
{
  setTimeout(function(){ $("#resultDiv").fadeIn(); }, 10);
  setTimeout(function(){ $("#resultDiv").fadeOut(); }, 4000);
 
});
$(document).ready(function()
{
	$("#deptname").multiselect({
		   show: ["", 200],
		   hide: ["explode", 1000]
		});
	$("#subdeptname").multiselect({
		   show: ["", 200],
		   hide: ["explode", 1000]
		});
	$("#shifts").multiselect({
		   show: ["", 200],
		   hide: ["explode", 1000]
		});
	$("#floor").multiselect({
		   show: ["", 200],
		   hide: ["explode", 1000]
		});
	$("#wing").multiselect({
		   show: ["", 200],
		   hide: ["explode", 1000]
		});
	$("#roomNo").multiselect({
		   show: ["", 200],
		   hide: ["explode", 1000]
		});
});

</script>
</head>
<body>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">Map Shift</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Location</div> 
</div>
<div class="clear"></div>
<div style="height: 180px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
<s:form id="formoneshift" name="formoneshift" action="mapShiftWithLocation" theme="css_xhtml"  method="post" enctype="multipart/form-data" >
<s:hidden id="moduleName" name="moduleName" value="%{dataFor}"/>
<s:hidden id="viewType" name="viewType" value="%{pageType}"/>
   <center>
     <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
          <div id="errZone" style="float:center; margin-left: 7px"></div>        
     </div>
   </center>
   <span id="mandatoryFields" class="pIds" style="display: none; ">deptname#Department#D#,</span>
                   <div class="newColumn">
        			   <div class="leftColumn" >Department:</div>
					        <div class="rightColumn">
					           <span class="needed"></span>
				                  <s:select  
				                              id="deptname"
				                              name="deptname"
				                              list="serviceDeptList"
				                              headerKey="-1"
				                              multiple="true"
				                              cssClass="select"
					                          cssStyle="width:5%"
				                              onchange="getServiceSubDept('subdept');"
				                              >
				                  </s:select>
                           </div>
                   </div>
                   <span id="mandatoryFields" class="pIds" style="display: none; ">subdeptname#Sub Department#D#,</span>
                     <div class="newColumn">
					      <div class="leftColumn">Sub Department:</div>
							<div class="rightColumn">
				             	 <span class="needed"></span>
				                 <div id="subdept">
				                  <s:select 
				                              id="subdeptname"
				                              name="subdeptname"
				                              list="{'No Data'}"
				                              headerKey="-1"
				                              multiple="true"
				                              cssClass="select"
				                              cssStyle="width:5%">
				                  </s:select>
				                 </div>
				           </div>
	                  </div>
	                  <span id="mandatoryFields" class="pIds" style="display: none; ">shifts#Shifts#D#,</span>
	                     <div class="clear"></div>
	                     <div class="newColumn">
					      <div class="leftColumn">Shifts:</div>
							<div class="rightColumn">
				             	<span class="needed"></span>
				                  <div id="shiftDiv">
				                  <s:select 
				                              id="shifts"
                              				  name="shifts"
				                              list="{'No Data'}"
				                              multiple="true"
				                              headerKey="-1"
				                              headerValue="Select shifts" 
				                              cssClass="select"
				                              cssStyle="width:5%">
				                  </s:select>
				                  </div>
				           </div>
	                  </div>
	                  <span id="mandatoryFields" class="pIds" style="display: none; ">floor#Floor#D#,</span>
	                     <div class="newColumn">
					      <div class="leftColumn">Floor:</div>
							<div class="rightColumn">
				             	<span class="needed"></span>
				                  <s:select 
				                              id="floor"
				                              name="floor"
				                              list="floorMap"
				                              multiple="true"
				                              headerKey="-1"
				                              cssClass="select"
				                              onchange="getServiceSubDept('wingDiv');"
				                              cssStyle="width:5%">
				                  </s:select>
				           </div>
	                  </div>
	                  <span id="mandatoryFields" class="pIds" style="display: none; ">wing#Wing#D#,</span>
	                    <div class="newColumn">
					      <div class="leftColumn">Wing:</div>
							<div class="rightColumn">
				             	<span class="needed"></span>
				                 <div id="wingDiv">
				                  <s:select 
				                              id="wing"
				                              name="roomNo"
				                              list="{'No Data'}"
				                              multiple="true"
				                              headerKey="-1"
				                              headerValue="Select Wing" 
				                              cssClass="select"
				                              cssStyle="width:5%">
				                  </s:select>
				                 </div>
				           </div>
	                  </div>
	                 <%--  <span id="mandatoryFields" class="pIds" style="display: none; ">roomNo#Room No#D#,</span>
	                   <div class="newColumn">
					      <div class="leftColumn">Room No:</div>
							<div class="rightColumn">
				         <span class="needed"></span>
				              <div id="roomDiv">
				                  <s:select 
				                              id="roomNo"
                              				  name="roomNo"
				                              list="{'No Data'}"
				                              multiple="true"
				                              headerKey="-1"
				                              headerValue="Select Room No" 
				                              cssClass="select"
				                              cssStyle="width:5%">
				                  </s:select>
				                  </div>
				           </div>
	                  </div> --%>
		 
	   <div class="clear"></div>
		  <!-- Buttons -->
		  <center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
		  <div class="fields">
		  <ul>
			 <li class="submit" style="background:none;">
			 <div class="type-button" align="center">
	         <sj:submit 
	                        targets="resultDiv" 
	                        clearForm="true"
	                        value=" Save " 
	                        effect="shake"
	                        effectOptions="{ color : '#FFFFF'}"
	                        effectDuration="5000"
	                        button="true"
	                        cssClass="submit"
	                        onBeforeTopics="validate"
	                        onCompleteTopics="completeData"
	                        indicator="indicator2"
	                        cssStyle="margin-left: -40px;"
	                        />
	            <sj:a cssStyle="margin-left: 183px;margin-top: -45px;" button="true" href="#" onclick="resetForm('formoneShift');">Reset</sj:a>
	            <sj:a cssStyle="margin-left: 5px;margin-top: -45px;" button="true" href="#" onclick="backForm('viewType','moduleName');">Back</sj:a>
	        </div>
		  </ul>
		  <center><div id="resultDiv"></div></center>
	      </div>
	    </s:form>
</div>
</div>
</body>
</html>
