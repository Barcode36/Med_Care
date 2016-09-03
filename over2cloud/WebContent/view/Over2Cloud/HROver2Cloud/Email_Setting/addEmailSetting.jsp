<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="/js/common/commonvalidation.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/asset/AssetCommonValidation.js"/>"></script>
<SCRIPT type="text/javascript">
$.subscribe('level1', function(event,data)
	       {
	         setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
	       });
function viewEmailSetting()
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/hr/beforeEmailSettingViewHeader.action",
	    success : function(subdeptdata) {
	       $("#"+"moreSettingDiv").html(subdeptdata);
	},
	   error: function() {
	            alert("error");
	        }
	 });
	}

function reset(formId) {
	  $("#"+formId).trigger("reset"); 
	}

</script>
</head>
<body>
<div class="list-icon" style="margin-top: -49px;">
	<div class="head">Email Setting </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div>
</div>
	<div class="clear"></div>
<div style=" float:left; padding:10px 0%; width:100%;">
<div class="clear"></div>
<div class="border">

<div class="container_block">
<div style=" float:left; padding:20px 1%; width:98%;">
<s:form id="formtwo" name="formtwo"  action="insertEmailSettingAdd" theme="css_xhtml"  method="post" >
	        <center>
	             <div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
	                  <div id="errZone" style="float:left; margin-left: 7px"></div>        
	             </div>
	        </center>
						<div class="clear"></div>
   	<!-- Text box -->
				 <s:iterator value="emailColumnDropdown">
				          <s:if test="%{mandatory}">
                           <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                         </s:if>
					  <s:if test="%{key == 'moduleName'}">
	                <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:select 
	                              id="%{key}"
	                              name="%{key}" 
	                              list="moduleMap"
	                              headerKey="-1"
	                              headerValue="Select" 
	                              cssClass="select"
                                  cssStyle="width:82%"
	                              >
                               </s:select>
                               </div>
                      </div>        
                      </s:if> 
                 </s:iterator>  
    
    <s:iterator value="emailColumnText">
                <s:if test="%{mandatory}">
                           <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                         </s:if>
					  <s:if test="%{key == 'server'}">
	                 <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:textfield id="%{Key}" name="%{Key}"  cssStyle="margin:0px 0px 10px 0px" cssClass="textField" placeholder="Enter Data"  ></s:textfield>
                   </div>
                   </div>
                   </s:if>
                    </s:iterator>
                    <div class="clear"></div>	
                     <s:iterator value="emailColumnText">
                <s:if test="%{mandatory}">
                           <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                         </s:if>
                   <s:if test="%{key == 'port'}">
                   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:textfield id="%{Key}" name="%{Key}"  cssClass="textField" placeholder="Enter Data" onblur="changeEndingreading();" cssStyle="margin:0px 0px 10px 0px" ></s:textfield>
                  </div>
                  </div>	
                  </s:if>
                  </s:iterator>
                  
                      <s:iterator value="emailColumnText">
                <s:if test="%{mandatory}">
                           <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                         </s:if>
					  <s:if test="%{key == 'emailid'}">
	                 <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:textfield id="%{Key}" name="%{Key}" cssStyle="margin:0px 0px 10px 0px" cssClass="textField" placeholder="Enter Data"  ></s:textfield>
                   </div>
                   </div>
                   </s:if>
                   </s:iterator>
                   <s:iterator value="emailColumnText">
                <s:if test="%{mandatory}">
                           <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                         </s:if>
                   <s:if test="%{key == 'password'}">
                   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:textfield id="%{Key}" name="%{Key}"  cssClass="textField" placeholder="Enter Data" onblur="changeEndingreading();" cssStyle="margin:0px 0px 10px 0px" ></s:textfield>
                  </div>
                  </div>	
                  </s:if>
                  </s:iterator>
                 
                   <s:iterator value="emailColumnText">
                         <s:if test="%{mandatory}">
                           <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                         </s:if>
                   <s:if test="%{key == 'subject'}">
                   <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:textfield id="%{Key}" name="%{Key}"   cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px" ></s:textfield>
                  </div>
                  </div>	
                  </s:if>
				</s:iterator>
               
<!-- Buttons -->
<div class="clear"></div>
 <center><img id="indicator3" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
<div class="type-button" style="margin-left: -200px;">
<center> 
       <sj:submit 
           			   targets="level123" 
                       clearForm="true"
                       value="Save" 
                       effect="highlight"
                       effectOptions="{ color : '#222222'}"
                       effectDuration="5000"
                       button="true"
                       onCompleteTopics="level1"
                       cssClass="submit"
                       cssStyle="margin-right: 65px;margin-bottom: -9px;"
                       indicator="indicator2"
                       onBeforeTopics="validate"
                       onCompleteTopics="level1"
         />
         
         <sj:a 
				     	href="#" 
		               	button="true" 
		               	 onclick="reset('formtwo');"
						cssStyle="margin-top: -28px;"						>
					  	Reset
		   </sj:a>
		   
		   <sj:a 
				     	href="#" 
		               	button="true" 
		               	onclick="viewEmailSetting();"
						cssStyle="margin-right: -137px;margin-top: -28px;"
						>
					  	Back
		   </sj:a>
      </center>   
   </div>
  <sj:div id="orglevel1Div"  effect="fold">
                   <sj:div id="level123">
                   </sj:div>
               </sj:div>
</s:form>  
</div>
</div>
</div></div>

</body>
</html>