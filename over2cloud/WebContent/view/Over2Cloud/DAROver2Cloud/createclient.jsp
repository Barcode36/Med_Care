<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="/js/organization.js"/>"></script>
<script src="<s:url value="/js/employeeOver2Cloud.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/task_js/task.js"/>"></script>
<script type="text/javascript">
function viewClient() {
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#data_part").load("<%=request.getContextPath()%>/view/Over2Cloud/DAROver2Cloud/clientView.action?modifyFlag=0&deleteFlag=0&viewFlag=1&addFlag=1");
	}
$.subscribe('completeData', function(event,data)
	       {
			$("#indicator2").css("display","none");
			//$("#buttonid").css("display","block");
			$("#buttonid").prop("disabled","false");
			 setTimeout(function(){ $("#foldeffect1").fadeIn(); }, 10);
	         setTimeout(function(){ $("#foldeffect1").fadeOut(); }, 4000);
	       });

function resetForm(formId) 
{
	 $('#'+formId).trigger("reset");
}



</script>
</head>
<body>
	<div class="list-icon">
	 <div class="head">
	 Client</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Add</div> 
</div>
 <div style=" float:left; padding:5px 0%; width:100%;">
	<div class="border">
    <s:form id="formclientType" name="formclientType" namespace="/view/Over2Cloud/DAROver2Cloud" action="addclientRegis" theme="css_xhtml"  method="post" enctype="multipart/form-data" >
		        <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="errZone" style="float:left; margin-left: 7px">
             </div> 
             </div>
             
		       <s:iterator value="clienttypeColumnMap" status="counter">
		       
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
					            <s:textfield name="%{key}"  id="%{key}" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" placeholder="Enter Data"/>
					            </div>
				</div>
				</s:else>
				</s:iterator>
				 
	   <center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
	     <div class="clear"></div>
	      <div class="fields" align="center">
		  <ul>
			 <li class="submit" style="background:none;">
			 <div class="type-button">
	         <sj:submit 
	                        targets="target1" 
	                        id="buttonid"
	                        clearForm="true"
	                        value="  Save  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="completeData"
	                        cssClass="submit"
	                        onBeforeTopics="validate"
	                        indicator="indicator2"
	                        cssStyle="margin-left: -40px;"
	                        />
	                        
	                        <sj:a cssStyle="margin-left: 183px;margin-top: -45px;" button="true" href="#" onclick="resetForm('formclientType');">Reset</sj:a>
				            <sj:a cssStyle="margin-left: 0px;margin-top: -45px;" button="true" href="#" onclick="viewClient();">Back</sj:a>
	                        
	                       
	        </div>
	        </li>
		  </ul>
	      </div>
		  <sj:div id="foldeffect1" cssClass="sub_class1"  effect="fold"><div id="target1"></div></sj:div>
		  	<sj:div id="darlevel1"  effect="fold">
                    <div id="darlevel1Div"></div>
               </sj:div>
	 </s:form>
	  </div>
	  </div>
</body>
</html>
