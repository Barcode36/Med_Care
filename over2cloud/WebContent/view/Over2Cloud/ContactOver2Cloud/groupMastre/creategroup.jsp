<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s"  uri="/struts-tags"%>
    <%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
	<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="/js/common/commonvalidation.js"/>"></script>
<title>Insert title here</title>
<script type="text/javascript">
$.subscribe('result', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#groupcreation").fadeIn(); }, 10);
	         setTimeout(function(){ $("#groupcreation").fadeOut(); }, 4000);
	       });

function resetForm(formId)
{
	$('#'+formId).trigger("reset");
}
	       </script>
	       
	<script type="text/javascript">
         function groupbackView(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
					$.ajax({
					    type : "post",
					    url : "view/Over2Cloud/CommunicationOver2Cloud/Comm/groupdetailsview?",
					    success : function(subdeptdata) {
				       $("#"+"data_part").html(subdeptdata);
					},
					   error: function() {
				            alert("error");
				        }
					 });
				}

</script>
</head>
<body>

<div class="clear"></div>
<div class="middle-content">
	<div class="list-icon">
		<div class="head">Group</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div>
	</div>
	<div class="clear"></div>
	<div style="min-height: 10px; height: auto; width: 100%;"></div>
	<div class="border">
		<div style="width: 100%; text-align: center; padding-top: 10px;">
							
<div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
  <div id="errZone" style="float:left; margin-left: 7px"></div>  
        </div>
             
<s:form id="form" name="form"  namespace="/view/Over2Cloud/CommunicationOver2Cloud/Comm" action="addgroup" theme="simple"  method="post"enctype="multipart/form-data" >
                <span id="mandatoryFields" class="pIds" style="display: none; ">createnew_group#Create New Group#T#an,</span>
							<div class="newColumn">
						<div class="leftColumn1">Create New Group:</div>
						<div class="rightColumn">
					     <span class="needed"></span>
					    <s:textfield name="createnew_group" id="createnew_group"  cssStyle="margin:0px 0px 10px 0px"  cssClass="form_menu_inputtext" />
						</div>
					     </div>
							<div class="newColumn">
							<span id="mandatoryFields" class="pIds" style="display: none; ">comment#Comment#T#an,</span>
						<div class="leftColumn1">Comment :</div>
						<div class="rightColumn">
					     <span class="needed"></span>
					    <s:textarea name="comment" id="comment"  rows="5" cols="80" cssStyle="margin:0px 0px 10px 0px"  />
						</div>
					     </div>
							
						<div class="clear"></div>	
						                
		<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
			<div class="type-button" style="margin-left: -200px;">
<sj:div id="groupcreation"  effect="fold">
                    <div id="creategroup"></div>
               </sj:div>
               
               	<br>
			<sj:submit 
          			   targets="creategroup" 
                       clearForm="true"
                       value="Add" 
                       effect="highlight"
                       effectOptions="{ color : '#222222'}"
                       effectDuration="5000"
                       button="true"
                       onCompleteTopics="result"
                       cssClass="submit"
                       cssStyle="margin-right: 2px;margin-bottom: 29px;"
                       indicator="indicator2"
                       onBeforeTopics="validate"
                       openDialog="takeActionGrid"
         />
         <sj:a 
				     	href="#" 
		               	button="true" 
		               	onclick="resetForm('form');"
						cssStyle="margin-top: -28px;"						>
					  	Reset
		   </sj:a>
		   
		   <sj:a 
				     	href="#" 
		               	button="true" 
		               	onclick="groupbackView();"
						cssStyle="margin-right: -137px;margin-top: -28px;"
						>
					  	Back
		   </sj:a>
    </s:form>
</div>
</div>

</div>
</body>
</html>