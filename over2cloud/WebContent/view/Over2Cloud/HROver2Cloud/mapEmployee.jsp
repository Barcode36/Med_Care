<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<SCRIPT type="text/javascript">
$.subscribe('level1', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#orglevel1").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1").fadeOut(); }, 4000);
	       });
</SCRIPT>
</head>
<body>
<div class="page_title"><h1>Employee >> Mapping</h1></div>
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<sj:accordion id="accordion" autoHeight="false">
<sj:accordionItem title="Employee Mapping" id="oneId">  
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formone" name="formone" namespace="/view/Over2Cloud/hr" action="mapEmployee" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
				 
				 
				<div class="form_menubox" id="hideThis">
                  <div class="user_form_text">Employee:*</div>
                  <div class="user_form_input">
                  <s:select 
                              id="emp1"
                              name="emp1" 
                              list="empListData"
                              headerKey="-1"
                              headerValue="--Select Employee Name--" 
                              cssClass="form_menu_inputselect"
                              >
                  </s:select></div>
                  
                  <div class="user_form_text">Map With:*</div>
                  <div class="user_form_input">
                  <s:select 
                              id="emp2"
                              name="emp2" 
                              list="empListData"
                              headerKey="-1"
                              headerValue="--Select Employee Name--" 
                              cssClass="form_menu_inputselect"
                              >
                  </s:select></div>
             	</div>
				<!-- Buttons -->
				<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				<div class="fields">
				<ul>
				<li class="submit" style="background: none;">
					<div class="type-button">
	                <sj:submit 
	                        targets="orglevel1Div" 
	                        clearForm="true"
	                        value="  Add Employee  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="level1"
	                        cssClass="submit"
	                        indicator="indicator2"
	                        />
	               </div>
				</ul>
				<sj:div id="orglevel1"  effect="fold">
                    <div id="orglevel1Div"></div>
               </sj:div>
               </div>
			</s:form>
</div>
</div>
</sj:accordionItem>
</sj:accordion>
</div>
</div>
</body>
</html>
