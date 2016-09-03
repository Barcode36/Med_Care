<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="js/commanValidation/validation.js"></script>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>	
<SCRIPT type="text/javascript">
$.subscribe('level111', function(event,data)
           {
             setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
             setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
           });
 
$.subscribe('level1', function(event, data) {
	$("#addDialog").dialog('open');
	setTimeout(function() {
		closeAdd() ;
	}, 1000);
	reset("formone");
});
</script>

<script type="text/javascript">
function closeAdd() {
	$("#addDialog").dialog('close');
	backToView();
}
</script>
<script type="text/javascript">

function backToView()
  {
 	  $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
  		$.ajax({
  		    type : "post",
  		    url : "view/Over2Cloud/CorporatePatientServices/cpservices/viewBedTypeDetail.action",
  		    success : function(data) 
  		    {
  				$("#"+"data_part").html(data);
  		    },
  		    error: function() 
  		    {
  	            alert("hhh");
  	        }
  		 });
  		
  }
function reset(formId) {
	$("#" + formId).trigger("reset");
}
 </script>
</head>
<body>
<div class="clear"></div>
	<sj:dialog id="addDialog"
		buttons="{'Close':function() { closeAdd(); },}" showEffect="slide"
		hideEffect="explode" autoOpen="false" modal="true"
		title="Status Message" openTopics="openEffectDialog"
		closeTopics="closeEffectDialog" width="350" height="150">
		<sj:div id="level123"></sj:div>
	</sj:dialog>
    <div class="list-icon">
     <div class="head">Bed-Type</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div>
</div>
<div class="clear"></div>
     <div style="width: 100%; center; padding-top: 10px;">
       <div class="border" style="height: 50%">
   <s:form id="formone" name="formone" namespace="/view/Over2Cloud/CorporatePatientServices/cpservices" action="addBedType" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
                           <center>
                             <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
                             <div id="errZone" style="float:center; margin-left: 7px"></div>        
                             </div>
                           </center>
 
                 <s:iterator value="departmentTextBox" status="status">
						  <s:if test="%{mandatory}">
		     	<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
			       	<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				       <s:if test="%{key == 'location_name'}">
				         		 <s:select 
                                      id="%{key}"
                                      name="%{key}"
                                      list=	"locationName"
                                      headerKey="-1"
                                      headerValue="Select Location" 
                                      cssClass="select"
                                      cssStyle="width:81%"
                                      theme="simple"
                                      >
                          </s:select>
                          </s:if>
                          <s:elseif test="%{key == 'service_type'}">
				         		 <s:select 
                                      id="%{key}"
                                      name="%{key}"
                                      list=	"serviceTypeList"
                                      headerKey="-1"
                                      headerValue="Select Service" 
                                      cssClass="select"
                                      cssStyle="width:81%"
                                      theme="simple"
                                      >
                          </s:select>
                          </s:elseif>
				    
				             <s:elseif test="%{key == 'bed_type'}">
				         		<s:textfield  name="%{key}" id="%{key}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
				         </s:elseif>
				         <s:else>
				         	<s:textfield  name="%{key}" id="%{key}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
				         </s:else>
				 		             
				    </div>
			        </div>
			        </s:iterator>
                 
	<div id="newDiv" style="float: right;width: 10%;margin-top: -46px;"><sj:a value="+" onclick="adddiv('100')" button="true" cssStyle="margin-top: 9%;">+</sj:a></div>
    <s:iterator begin="100" end="110" var="levelIndx">
	      <div id="<s:property value="%{#levelIndx}"/>" style="display: none ; width: 95%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px;
    -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10);
    padding: 1%;"  class="container_block" >
		    <div class="clear"></div>  
		 <s:iterator value="departmentTextBox" begin="2" end="3">
		    <div class="newColumn">
			     <div class="leftColumn" style="margin-left: -142px;"><s:property value="%{value}"/>:&nbsp;</div>
				 <div class="rightColumn">
					 <s:if test="%{mandatory}"><span class="needed"></span>
					<!--<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{key}"/><s:property value="%{#levelIndx}"/>#<s:property value="%{value}"/>#T#<s:property value="%{validationType}"/>,</span>--> 
					 </s:if>
		             	 <s:if test="%{key == 'bed_type'}">
				         		<s:textfield  name="%{key}" id="%{key}"  maxlength="200" cssClass="textField" placeholder="Enter Data"style="width: 59%;"/>
				         </s:if>
				         <s:else>
				         		<s:textfield  name="%{key}" id="%{key}"  maxlength="200" cssClass="textField" placeholder="Enter Data"style="width: 59%;"/>
				         </s:else>
		         </div>
		    </div>
		    
   		</s:iterator> 
			
			<div>	
					<s:if test="#levelIndx==110">
	                    <div class="user_form_button2"><sj:a value="-" onclick="deletediv('%{#levelIndx}')" button="true">-</sj:a></div>
	                </s:if>
					<s:else>
	  		  			<div class="user_form_button2"><sj:a value="+" onclick="adddiv('%{#levelIndx+1}')" button="true" cssStyle="margin-left: -20px;margin-top: 10px;">+</sj:a></div>
	          			<div class="user_form_button3" style="margin-left: -4px;"><sj:a value="-" onclick="deletediv('%{#levelIndx}')" cssStyle="margin-top: 8px;" button="true">-</sj:a></div>
	       			</s:else>
       			</div>
				</div>
		 </s:iterator>           
               
                <center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
                <div class="clear" >
                <div style="padding-bottom: 10px;margin-left:-80px" align="center">
                        <sj:submit 
                             targets="level123" 
                             clearForm="true"
                             value="Save" 
                             effect="highlight"
                             effectOptions="{ color : '#FFFFFF'}"
                             effectDuration="5000"
                             button="true"
                             onBeforeTopics="validate"
                             onclick="removCssText();"
                             onCompleteTopics="level1"
                        />
                        &nbsp;&nbsp;
                        <sj:submit 
                             value="Reset" 
                             button="true"
                             cssStyle="margin-left: 139px;margin-top: -43px;"
                             onclick="resetForm('formone');resetColor('.pIds');"
                        />&nbsp;&nbsp;
                        <sj:a
                            cssStyle="margin-left: 276px;margin-top: -58px;"
                        button="true" href="#" value="View" onclick="bed_type_add();" cssStyle="margin-left: 266px;margin-top: -74px;" 
                        >Back
                    </sj:a>
                    </div>
               </div>
               
   </s:form>          
</div>
</div>
</body>
</html>