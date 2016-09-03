<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/task_js/task.js"/>"></script>
<script type="text/javascript">
$.subscribe('beforeFirst', function(event,data)
	       {
    
			$("#indicator2").css("display","block");
			//$("#buttonid").css("display","none");
			$("#buttonid").prop("disabled","true");
	       }
);

$.subscribe('completeData', function(event,data)
	       {
			$("#indicator2").css("display","none");
			//$("#buttonid").css("display","block");
			$("#buttonid").prop("disabled","false");
			 setTimeout(function(){ $("#foldeffect1").fadeIn(); }, 10);
	         setTimeout(function(){ $("#foldeffect1").fadeOut(); }, 4000);
	       }
);

function resetForm(formId) 
{
	 $('#'+formId).trigger("reset");
}


function viewProject() 
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/DAROver2Cloud/managementView.action?addFlag=1&viewFlag=1",
	    success : function(clientdata) {
     $("#"+"data_part").html(clientdata);
	},
	   error: function() {
          alert("error");
      }
	 });		
}
</script>
<script type="text/javascript">
</script>
</head>
<body>
<div class="list-icon">
	 <div class="head">Project Management </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div> 
</div>
   <div style=" float:left; padding:5px 0%; width:100%;">
    <div class="border">
      <s:form id="formprojectType" name="formprojectType" namespace="/view/Over2Cloud/DAROver2Cloud" action="addMangementRegis" theme="css_xhtml"  method="post" enctype="multipart/form-data" >
		     <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="errZone" style="float:left; margin-left: 7px">
             </div> 
             </div>
		     <s:iterator value="projectColumnMap" status="counter">
                   <s:if test="%{mandatory}">
                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>  
				 	 <s:if test="#counter.count%2 != 0">
	                  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
					     </div>
					   </div>
					  </s:if>
					  <s:else>
					  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
					     <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px; "/>
					     </div>
					  </div>
					  </s:else>
			
				 </s:iterator>
	    <div class="fields" align="center">
		  <ul>
			 <li class="submit" style="background:none;">
			 <div class="type-button">
	         <sj:submit 
	                        targets="target1" 
	                        id="buttonid"
	                        clearForm="true"
	                        value="  Save " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="completeData"
	                        cssClass="submit"
	                        onBeforeTopics="validate"
	                        indicator="indicator2"
	                        cssStyle="margin-left: -35px;"
	                        />
	                       
						
						<sj:a cssStyle="margin-left: 183px;margin-top: -45px;" button="true" href="#" onclick="resetForm('formprojectType');">Reset</sj:a>
				            <sj:a cssStyle="margin-left: 0px;margin-top: -45px;" button="true" href="#" onclick="viewProject();">Back</sj:a>  
	        </div>
	        </li>
		  </ul>
	      </div>
	      <center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
		  <sj:div id="foldeffect1" cssClass="sub_class1"  effect="fold"><div id="target1"></div></sj:div>
	 
	 </s:form>
	  </div>
	  </div>
</body>
</html>
