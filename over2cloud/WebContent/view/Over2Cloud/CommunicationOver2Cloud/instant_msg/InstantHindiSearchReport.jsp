<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>
<style type="text/css">

.user_form_input{
    margin-bottom:10px;
}

</style>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>



<SCRIPT type="text/javascript">

function searchReport()
{
	 
	 $.ajax({
		    type : "post",
		    url : "view/Over2Cloud/CommunicationOver2Cloud/Comm/beforeAutoPushReportView.action",
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
	<div class="head"><h3>Instant Hindi Search Report</h3></div>
	</div>
	<div class="clear"></div>
<div style=" float:left; padding:10px 0%; width:100%;">
<div class="clear"></div>
<div class="border">

<div class="container_block">
<div style=" float:left; padding:20px 1%; width:98%;">


<s:form id="formtwo" name="formtwo" namespace="/view/Over2Cloud/CommunicationOver2Cloud/Comm" action="instantHindiSearchReport" theme="css_xhtml"  method="post" >
<div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
  <div id="errZone" style="float:left; margin-left: 7px"></div>        
        </div>
             	
   	
   	
   	
						
						<div class="clear"></div>
   	<!-- Text box -->
   	
               <s:iterator value="mobileNum" status="counter">
                   <s:if test="%{mandatory}">
                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>  
           
				 	 <s:if test="#counter.count%2 != 0">
				 
				 <s:if test="%{mandatory}">
	                   <div class="rightColumn">
						<div class="leftColumn1"><s:property value="%{value}"/>:</div>
						<div class="rightColumn">
					     <span class="needed"></span><s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
					     </div></div>
						  </s:if>
					     <s:else>
					     <div class="rightColumn">
						<div class="leftColumn1"><s:property value="%{value}"/>:</div>
						<div class="rightColumn"><s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
					     </div></div>
					     </s:else>
					  </s:if>
					   </s:iterator>
					  <s:iterator value="messageTypeDropDown" status="counter">
<s:if test="%{mandatory}">
<span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
</s:if>
 <s:if test="%{mandatory}">
	                   <div class="rightColumn">
						<div class="leftColumn1"><s:property value="%{value}"/>:</div>
						<div class="rightColumn"> <span class="needed"></span>
						<s:select 
                              id="%{key}"
                              name="%{key}" 
                              list="{''}"
                              headerKey="-1"
                              headerValue="Select" 
                             cssClass="select"
                             cssStyle="width:82%"
                              >
                   
                  </s:select>
					    
					     </div></div>
						  </s:if>
					     <s:else>
					     <div class="rightColumn">
						<div class="leftColumn1"><s:property value="%{value}"/>:</div>
						<div class="rightColumn">
						<s:select 
                              id="%{Key}"
                              name="%{key}" 
                              list="{''}"
                              headerKey="-1"
                              headerValue="Select" 
                             cssClass="select"
                              cssStyle="width:82%"
                              >
                   
                  </s:select>
					     </div></div>
					     </s:else>

</s:iterator>
					  
   	<div class="clear"></div>
   

<s:iterator value="messageTextTextBox" status="counter">
					  <s:if test="%{mandatory}">
                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>  
           
				 	 <s:if test="#counter.count%2 != 0">
					   <s:if test="%{mandatory}">
					    <div class="rightColumn">
						<div class="leftColumn1"><s:property value="%{value}"/>:</div>
						<div class="rightColumn"><span class="needed"></span>
					     <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px; "/>
					     </div></div>
					    </s:if>
					    <s:else>
					    <div class="rightColumn">
						<div class="leftColumn1"><s:property value="%{value}"/>:</div>
						<div class="rightColumn">
					    <s:textarea name="smsData" cols="40" rows="30" id="smsData" 
class="form_menu_inputtext" style="margin: 0px 0px 10px; width: 200px; height: 60px;"/>
					     </div></div>
					    </s:else>
					 
			</s:if>
				 </s:iterator>
				 <div class="clear"></div>
				 	

<div class="clear"></div>
<s:iterator value="fromTextBox" status="counter">
					  <s:if test="%{mandatory}">
                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>  
           
				 	 <s:if test="#counter.count%2 != 0">
					   <s:if test="%{mandatory}">
					    <div class="rightColumn">
						<div class="leftColumn1"><s:property value="%{value}"/>:</div>
						<div class="rightColumn"><span class="needed"></span>
					     <sj:datepicker id="date11" name="date11" readonly="true" placeholder="Enter Date" showOn="focus" displayFormat="dd-M"  cssClass="textField" />
					     </div></div>
					    </s:if>
					    <s:else>
					    <div class="rightColumn">
						<div class="leftColumn1"><s:property value="%{value}"/>:</div>
						<div class="rightColumn">
					     <sj:datepicker id="date11" name="date11" readonly="true" placeholder="Enter Date" showOn="focus" displayFormat="dd-M"  cssClass="textField" />
					     </div></div>
					    </s:else>
					 
			</s:if>
				 </s:iterator>


<s:iterator value="toTextBox" status="counter">
					  <s:if test="%{mandatory}">
                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>  
           
				 	 <s:if test="#counter.count%2 != 0">
					   <s:if test="%{mandatory}">
					    <div class="rightColumn">
						<div class="leftColumn1"><s:property value="%{value}"/>:</div>
						<div class="rightColumn"><span class="needed"></span>
					    <sj:datepicker id="date11" name="date11" readonly="true" placeholder="Enter Date" showOn="focus" displayFormat="dd-M"  cssClass="textField" />
					     </div></div>
					    </s:if>
					    <s:else>
					    <div class="rightColumn">
						<div class="leftColumn1"><s:property value="%{value}"/>:</div>
						<div class="rightColumn">
					   <sj:datepicker id="date1" name="date1" readonly="true" placeholder="Enter Date" showOn="focus" displayFormat="dd-M"  cssClass="textField" />
					     </div></div>
					    </s:else>
					 
			</s:if>
				 </s:iterator>
				 

     
            

               
<!-- Buttons -->
<div class="clear"></div>
 <center><img id="indicator3" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
<div style="width: 100%; text-align: center; padding-bottom: 10px;">

        <sj:submit 
           
                       clearForm="true"
                       value="Search" 
                       effect="highlight"
                       effectOptions="{ color : '#222222'}"
                       effectDuration="5000"
                       button="true"
                       onCompleteTopics="level1"
                        cssClass="submit"
                        
                       indicator="indicator2"
                       onBeforeTopics="validate"
                       onClick="searchReport()"
                      
         />
         
         
   </div>

 
</s:form>  
               



  
							
               


  


</div>
</div>
</div></div>

</body>
</html>