<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<SCRIPT type="text/javascript" src="js/commanValidation/validation.js"></SCRIPT>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<script type="text/javascript">
function back(moduleName)
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax
	({
		type : "post",
		url  : "/over2cloud/view/Over2Cloud/SeverityOver2Cloud/beforeViewSeverity.action?moduleName="+moduleName,
		success : function(data)
		{
			$("#data_part").html(data);
		},
		error : function()
		{
			alert("Problem in data fetch");
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
	 <div class="head">Severity Levels</div><img alt="" src="images/forward.png" style="margin-top:10px; float: left;"><div class="head">Add</div> 
</div>
<div class="clear"></div>
<div style="height: 400px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
    
	<s:form id="formone" name="formone" action="addSeverityLevel" theme="css_xhtml"  method="post" enctype="multipart/form-data" >
	<s:hidden  id="moduleName" name="moduleName" value="%{moduleName}"></s:hidden>
    <center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone" style="float:center; margin-left: 7px"></div></div></center>          
        <span id="severity" class="pIds" style="display: none; ">deptName#Department#D,</span>
      
       <div class="newColumn">
		<div class="leftColumn">Department:</div>
		<div class="rightColumn">
			 <span class="needed"></span> 
				 <s:select 
                       id="deptName"
                       name="deptName"
                       list="serviceDeptMap"
                       headerKey="-1"
                       headerValue="Select Department" 
                       cssClass="select"
                       cssStyle="width:82%"
                       >
				</s:select>
			</div>
		</div>
	 
		<div class="newColumn">
		<div class="leftColumn">Consider:</div>
		<div class="rightColumn">
			 <span class="needed"></span> 
				 <s:select 
                       id="severityCheckOn"
                       name="severityCheckOn"
                       list="#{'AT':'Addressing Time','RT':'Resolution Time','ET':'Escalation Time'}"
                       cssClass="select"
                       cssStyle="width:82%"
                       >
				</s:select>
			</div>
		</div>
	 
		<div class="newColumn">
		<div class="leftColumn">Level:</div>
		<div class="rightColumn">
			 <span class="needed"></span> 
			 <s:select 
                       id="severityLevel"
                       name="severityLevel"
                       list="#{'1':'1st Level','2':'2nd Level','3':'3rd Level','4':'4th Level','5':'5th Level'}"
                       cssClass="select"
                       cssStyle="width:82%"
                       >
				</s:select>
		</div>
		</div>
		<div class="clear"></div>  
		<div class="newColumn">
		<div class="leftColumn">From:</div>
		<div class="rightColumn">
		 <span class="needed"></span> 
			<s:textfield name="fromTime"  id="fromTime"  cssClass="textField" placeholder="Enter From" cssStyle="margin:0px 0px 10px 0px; width:80%"/>
		</div>
		</div>
		
		<div class="newColumn">
		<div class="leftColumn">To:</div>
		<div class="rightColumn">
		 <span class="needed"></span> 
			<s:textfield name="toTime"  id="toTime"  cssClass="textField" placeholder="Enter To" cssStyle="margin:0px 0px 10px 0px; width:80%"/>
		</div>
		</div>
		
		     
		<div id="newDiv" style="float: right;width: 10%;margin-top: -46px;"><sj:a value="+" onclick="adddiv('100')" button="true" cssStyle="margin-left: 44%;margin-top: 1%;">+</sj:a></div>
	      
		  <s:iterator begin="100" end="103" var="levelIndx">
	      <div id="<s:property value="%{#levelIndx}"/>" style="display: none">
			<div class="newColumn">
		    <div class="leftColumn">Level:</div>
		    <div class="rightColumn">
		     <span class="needed"></span> 
			<s:select 
	                      id="severityLevel%{#levelIndx}"
	                      name="severityLevel"
	                      list="#{'1':'1st Level','2':'2nd Level','3':'3rd Level','4':'4th Level','5':'5th Level'}"
	                      cssClass="select"
	                      cssStyle="width:82%"
	                      >
			</s:select>
			</div>
		    </div>
		    <div class="clear"></div>  
			<div class="newColumn">
		    <div class="leftColumn">From:</div>
		    <div class="rightColumn">
		     <span class="needed"></span> 
				<s:textfield name="fromTime"  id="fromTime"  cssClass="textField" placeholder="Enter From Time" cssStyle="margin:0px 0px 10px 0px; width:80%"/>
			</div>
		    </div>
		
			<div class="newColumn">
		    <div class="leftColumn">To:</div>
		    <div class="rightColumn">
		     <span class="needed"></span> 
				<s:textfield name="toTime"  id="toTime"  cssClass="textField" placeholder="Enter To Time" cssStyle="margin:0px 0px 10px 0px; width:80%"/>
			</div>
		    </div>
			
			<div style="float: left;margin-left: 85%;margin-top: -29px;width: 44%">	
					<s:if test="#levelIndx==103">
	                    <div class="user_form_button2"><sj:a value="-" onclick="deletediv('%{#levelIndx}')" button="true">-</sj:a></div>
	                </s:if>
					<s:else>
	  		  			<div class="user_form_button2"><sj:a value="+" onclick="adddiv('%{#levelIndx+1}')" button="true" cssStyle="margin-left: 107px;margin-top: -16px;">+</sj:a></div>
	          			<div class="user_form_button3" style="margin-left: -4px;"><sj:a value="-" onclick="deletediv('%{#levelIndx}')" cssStyle="margin-left: 117px;margin-top: -16px;" button="true">-</sj:a></div>
	       			</s:else>
       			</div>
				</div>
		 </s:iterator>
        <span id="severity" class="pIds" style="display: none; ">fromTime#From#T#t,</span>
      <span id="severity" class="pIds" style="display: none; ">toTime#To#T#t,</span>
          
      
          
            <div class="clear"></div>
            <div class="clear"></div>
		   <div style="width: 100%; text-align: center; padding-bottom: 10px;">
	                <sj:submit 
	                        targets="target1" 
	                        clearForm="true"
	                        value=" Save " 
	                        effect="highlight" 
			                effectOptions="{ color : '#222222'}" 
			                effectDuration="5000" 
	                        button="true"
	                        cssClass="submit"
	                        indicator="indicator1"  
	                        onCompleteTopics="beforeFirstAccordian"
	                        onBeforeTopics="validate"
	                        cssStyle="margin-left: -40px;"
	                        />
	            <sj:a cssStyle="margin-left: 175px;margin-top: -45px;" button="true" href="#" onclick="resetForm('formone');resetColor('severity');">Reset</sj:a>
	            <sj:a cssStyle="margin-left: 2px;margin-top: -45px;" button="true" href="#" onclick="back('HDM');">Back</sj:a>
	               </div>
	               
		        <center><img id="indicator1" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
		  <sj:div id="foldeffect1" effect="fold"><div id="target1"></div></sj:div>  
			</s:form>
			</div>
     
</div>
</body>
</html>