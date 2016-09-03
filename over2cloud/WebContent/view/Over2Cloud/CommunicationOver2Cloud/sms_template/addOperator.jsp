<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<style>
table,th,td
{
border:1px solid black;
border-collapse:collapse;
}
</style>
<script src="<s:url value="/js/common/commonvalidation.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/communication/CommuincationBlackList.js"/>"></script>
<SCRIPT type="text/javascript">

function viewOperator()
{
$.ajax({
			    type : "post",
			    url  : "view/Over2Cloud/CommunicationOver2Cloud/template/beforeOperatorView.action",
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
<div class="list-icon">
	<div class="head">Operator</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div>
</div>
<div class="clear"></div>
<div class="border">
<div class="container_block">
<div style=" float:left; padding:20px 1%; width:98%;">
<s:form id="formtwo" name="formtwo" namespace="/view/Over2Cloud/CommunicationOver2Cloud/template" action="inserOperatorData" theme="css_xhtml"  method="post" >
<div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
  <div id="errZone" style="float:left; margin-left: 7px"></div>        
        </div>
						<div class="clear"></div>
   	<!-- Text box -->
               <s:iterator value="operatorname" status="counter">
                   <s:if test="%{mandatory}">
                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>  
                 
	                   <div class="rightColumn">
						<div class="leftColumn1"><s:property value="%{value}"/></div>
						<div class="rightColumn"> <s:if test="%{mandatory}">
					     <span class="needed"></span></s:if>
					     <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
					     </div></div>
					    
			</s:iterator>
					<div class="clear"></div>
					
<!-- Buttons -->
<div class="clear"></div>
 <center><img id="indicator3" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
	<div class="type-button" style="margin-left: -200px;">
<center> 
       <sj:submit 
           targets="leadresult" 
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
         />
         
         <sj:a 
				     	href="#" 
		               	button="true" 
		               	onclick="viewMessageDraft1();"
						cssStyle="margin-top: -28px;"						>
					  	Reset
		   </sj:a>
		   
		   <sj:a 
				     	href="#" 
		               	button="true" 
		               	onclick="viewOperator();"
						cssStyle="margin-right: -137px;margin-top: -28px;"
						>
					  	Back
		   </sj:a>
      </center>   
   </div>
</s:form>  
</div>
</div>
</div>



</body>
</html>
