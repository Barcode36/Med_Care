<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>

<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/shiftConf.js"/>"></script>

<style type="text/css">

.newColumn {
  float: right;
  width: 149%;
}
</style>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<!-- Header Strip Div Start -->

<div class="clear"></div>
<div style="height: 180px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
<%-- <s:form id="formone" name="formone" action="addShiftWithWingMap" theme="css_xhtml"  method="post" enctype="multipart/form-data" namespace="/view/Over2Cloud/HelpDeskOver2Cloud/Escalation_Conf" > --%>
   <center>
     <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
          <div id="errZone" style="float:center; margin-left: 7px"></div>        
     </div>
   </center>
	       
	        <s:hidden  name="shiftemployeemappedwing" value="%{shiftemployeemappedwing}"/>
	        <s:hidden name="deptId" value="%{deptId}"></s:hidden>
	        
			<s:if test="viewFlag!='F'">
			 <div class="newColumn">
       			   <div class="leftColumn" >Shift Name:</div>
				        <div class="rightColumn">
				             <s:textfield name="shift_name"  id="shift_name1"  cssClass="textField" placeholder="Enter Data" />
				        </div>
		     </div>
			</s:if>
			
		       <div class="clear"></div>
                   <div class="newColumn">
        			   <div class="leftColumn">From:</div>
					        <div class="rightColumn">
					              <s:textfield name="fromShift" id="fromShift" placeholder="Enter Data" showOn="focus" timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="1" cssClass="textField" />
                           </div>
                   </div>
		       
                     <div class="newColumn">
					      <div class="leftColumn">To:</div>
							<div class="rightColumn">
				             	  <s:textfield name="toShift" id="toShift" placeholder="Enter Data" showOn="focus"  timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="1" cssClass="textField" />
				           </div>
	                  </div>
	   <div class="clear"></div>
	   <s:if test="viewFlag!='F'">
			 <center>  <button onclick="saveShift();" id="save1" title="Save Data"
			style="margin-top: 15px;  border-radius: 5px;">
			<font size="2">Save</font>
		</button></center>
			</s:if>
			<s:else>
			 <center>  <button onclick="editShiftTime();" id="save2" title="Save Data"
			style="margin-top: 15px;  border-radius: 5px;">
			<font size="2">Save</font>
		</button></center>
			</s:else>
	 
		  <!-- Buttons -->
	<%-- 	  <center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
		  <div class="fields">
		
			 
			 <div class="type-button" align="center" style="  margin-top: 15px; margin-left: 14px;">
	         <sj:submit 
	                        targets="target1" 
	                        clearForm="true"
	                        value=" Save " 
	                        effect="highlight"
	                        effectOptions="{ color : '#ffffff'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="beforeFirstAccordian"
	                        cssClass="submit"
	                        onBeforeTopics="validate"
	                        indicator="indicator2"
	                        />
	           
	            <sj:a cssStyle="margin-left: 5px;margin-top: -45px;" button="true" href="#" onclick="backForm('viewType','moduleName');">Back</sj:a>
	        </div> --%>
		
		 <%--  <sj:div id="foldeffect1" cssClass="sub_class1"  effect="fold"><div id="target1"></div></sj:div>
	      </div> --%>
	   <%--  </s:form> --%>
</div>
</div>
</body>
</html>