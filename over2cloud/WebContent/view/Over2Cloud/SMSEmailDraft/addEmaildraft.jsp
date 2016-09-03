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

function viewEmailDraft()
{
	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/SMSEmailDraft/viewEmaildraftsHeader.action",
	    success : function(data)
	     {
	       	$("#data_part").html(data);
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
<div class="list-icon">
	<div class="head">Email Draft </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div>
</div>
	<div class="clear"></div>
<div style=" float:left; padding:10px 0%; width:100%;">
<div class="clear"></div>
<div class="border">

<div class="container_block">
<div style=" float:left; padding:20px 1%; width:98%;">
<s:form id="formtwo" name="formtwo"  action="insertEmaildraft" theme="css_xhtml"  method="post" >
	        <center>
	             <div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
	                  <div id="errZone" style="float:left; margin-left: 7px"></div>        
	             </div>
	        </center>
						<div class="clear"></div>
   	<!-- Text box -->
   	 <s:iterator value="emaildraftColumnDrop">
				          <s:if test="%{mandatory}">
                           <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                         </s:if>
					  <s:if test="%{key == 'module'}">
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
         <s:iterator value="emaildraftColumnText">
                <s:if test="%{mandatory}">
                           <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                         </s:if>
                              <s:if test="#counter.count%2 != 0">
                              <s:if test="%{key == 'triggerOn'}">
                              	  <div class="newColumn">
									<div class="leftColumn1"><s:property value="%{value}"/>:</div>
									<div class="rightColumn1">
	                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                             <sj:datepicker id="%{key}" name="%{key}" readonly="true"   placeholder="Enter Date" showOn="focus" displayFormat="dd-mm-yy" cssClass="textField" />
		                           </div>
	               				  </div>
                              </s:if>
                              <s:else>
	                                <div class="newColumn">
									<div class="leftColumn1"><s:property value="%{value}"/>:</div>
									<div class="rightColumn1">
	                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                           <s:textfield id="%{Key}" name="%{Key}"  cssStyle="margin:0px 0px 10px 0px" cssClass="textField" placeholder="Enter Data"  ></s:textfield>
		                           </div>
	               				  </div>
                              </s:else>
				  
					 </s:if>
					 <s:else>
				    <s:if test="%{key == 'triggerOn'}">
                              	  <div class="newColumn">
									<div class="leftColumn1"><s:property value="%{value}"/>:</div>
									<div class="rightColumn1">
	                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                             <sj:datepicker id="%{key}" name="%{key}" readonly="true"   placeholder="Enter Date" showOn="focus" displayFormat="dd-mm-yy" cssClass="textField" />
		                           </div>
	               				  </div>
                              </s:if>
                              <s:else>
	                                <div class="newColumn">
									<div class="leftColumn1"><s:property value="%{value}"/>:</div>
									<div class="rightColumn1">
	                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                           <s:textfield id="%{Key}" name="%{Key}"  cssStyle="margin:0px 0px 10px 0px" cssClass="textField" placeholder="Enter Data"  ></s:textfield>
		                           </div>
	               				  </div>
                              </s:else>
					 </s:else>
					
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
		               	onclick="viewEmailDraft();"
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