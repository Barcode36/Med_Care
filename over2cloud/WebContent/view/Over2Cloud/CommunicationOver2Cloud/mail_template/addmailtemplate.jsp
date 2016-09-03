<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
  <%@ taglib prefix="s"  uri="/struts-tags"%>
    <%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
    <%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

<html>
<script src="<s:url value="/js/common/commanvalidation.js"/>"></script>
<SCRIPT type="text/javascript">
$.subscribe('result', function(event,data) {
	         setTimeout(function(){ $("#reminderresult").fadeIn(); }, 10);
	         setTimeout(function(){ $("#invoicereminder").fadeOut(); }, 4000);
	       });
</script>
<script type="text/javascript">
function cancelinvoicereminder(){
	    var createtinvovcedetails_table="createtinvovcedetails_table";
		var createtinovicedetails_table="createtinovicedetails_table";
        var  id = $("#app_id").val();
        var app_name = $("#app_name").val();
        var conP = "<%=request.getContextPath()%>";
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : conP+"/view/allreport/menuallreport?id="+id+"&createtbasicdetails_table="+createtinvovcedetails_table+"&createaddressdetails_table="+createtinovicedetails_table+"&app_name="+app_name,
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
		<div class="list-icon">
	<div class="head">Mail </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Template</div>
</div>
		<div class="clear"></div>
	<div style="min-height: 10px; height: auto; width: 100%;"></div>
	<div class="border">
	<div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
  <div id="errZone" style="float:left; margin-left: 7px"></div>        
        </div>

		 <s:form id="formRichtext"   namespace="/view/Over2Cloud/CommunicationOver2Cloud/template" action="addtemplateaction" theme="simple"  method="post" >
		 
		  <s:hidden name="escape" value="false"/>
             	 <div class="form_menubox">
                  <div class="user_form_text">Template Type</div>
                  <div class="user_form_input"><s:select 
                              id="whomtoremind"
                              name="whomtoremind" 
                               list="#{'-1':'-Select Template  Type-'}"
                              headerKey="-1"
                              cssClass="form_menu_inputselect"
                              >
                  </s:select>
                  </div>
                  </div>
				 <div class="form_menubox">
			<span id="mandatoryFields" class="pIds" style="display: none; ">template_name#Template Name :#T#a,</span>
                  <div class="user_form_text">Template Name :</div>
                  <div class="user_form_input"><s:textarea  name="template_name" id="template_name" rows="1" cols="58"   placeholder="Enter Template Name" /></div>

             	 </div>
     
        
             	  
		 <div class="form_menubox">
         <div class="user_form_text">Template:</div>
		<span id="mandatoryFields" class="pIds" style="display: none; ">template_body#Template  :#T#ans,</span>
		  <s:textarea name="template_body" id="template_body" cols="95" rows="20" class="form_menu_inputtext"  style="margin: 0px 0px 15px; width: 750px; height: 190px;"/>

		</div>
		<br>
		<center><img id="indicator" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
			<div class="type-button" style="margin-left: -200px;">
			</div>
			<center>
	       	<div class="buttonStyle">
			 <div class="type-button">
				
		   <sj:submit 
			  targets="reminder" 
			  id="submitSimpleRichtext"
			  clearForm="true"
              effect="highlight"
              effectOptions="{ color : '#222222'}"
              effectDuration="5000"
              button="true"
              onCompleteTopics="result"
              cssClass="submit"
              cssStyle="margin-right: 2px;margin-bottom: 29px;"
			  value="Add Template" 
			  indicator="indicator" 
			  button="true"
			  onBeforeTopics="validate"
		/>		
					<sj:div id="reminder"  effect="fold">
                    <div id="reminderresult"></div>
               </sj:div>
	        </div>
	        </div>
		</center>
	 </s:form>	
</div>
</div>
</body>
</html> 