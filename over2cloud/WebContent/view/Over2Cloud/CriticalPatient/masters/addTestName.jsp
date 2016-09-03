<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
	<s:url  id="contextz" value="/template/theme" />
	<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
	<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
	<script type="text/javascript" src="<s:url value="/js/KRAKPI/kra.js"/>"></script>
	<script type="text/javascript" src="<s:url value="/js/criticalPatient/criticalcommonvalidation.js"/>"></script>
	<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
	<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
	<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
	<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>	

<script type="text/javascript">

$.subscribe('makeEffect', function(event,data)
		  {
			 setTimeout(function(){ $("#complTarget").fadeIn(); }, 10);
			 setTimeout(function(){ $("#complTarget").fadeOut(); cancelButton();}, 10000);
			 
			 //resetTestName('testName');
		  });
		function cancelButton()
		{
		$('#completionResult').dialog('close');
		addTestName();
		}
		 
		 $(document).ready(function()
					{
					$("#test_type").multiselect({
						   show: ["", 200],
						   header: true,
					    	 noneSelectedText: "Select Test Type",
						   hide: ["explode", 1000]
						});
					});

			
		 function checkValues()
		 {
			 var x =$("#test_type").val().length;
			 if(x=='1')
				{
					
					$("#addDiv").show();
					$("#complSpan").html("");
				}
				else
				{
					
					$("#addDiv").hide();
					
				}	
		 	
		 }

</script>
<style type="text/css">

.leftColumn {
    
    width: 30%;
}
</style>
</head>
<body>
<sj:dialog
          id="completionResult"
          showEffect="slide" 
          hideEffect="explode" 
          openTopics="openEffectDialog"
          closeTopics="closeEffectDialog"
          autoOpen="false"
          title="Confirmation Message"
          modal="true"
          draggable="true"
          resizable="true"
          buttons="{ 
                    'Close':function() { cancelButton(); } 
                                                            }" 
          >
          <sj:div id="complTarget" cssStyle="" effect="fold"></sj:div>
</sj:dialog>

<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Test Name</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Add</div> 
</div>
<div class="clear"></div> 

<div style="overflow:auto; width: 95%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px;
    -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10);
    padding: 1%; overflow: auto;"  class="container_block">
   
    <s:form id="testName" name="testName" action="addTestName" namespace="/view/Over2Cloud/CriticalMaster" theme="simple"  method="post"enctype="multipart/form-data" >
	  
	  <div class="clear"></div>
	   <div class="clear"></div> 
	   <center>
	  	<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 50%; height: 10%; font-size: small; border: 0px solid red; border-radius: 6px;">
		<div id="errZone" style="float:center; margin-left: 7px"></div>
	  	</div>
	  </center>
	   	  
			      
			           	         
		  <s:iterator value="dropDown" status="status" >
		     <s:if test="%{mandatory}">
		     	<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
            
			       	<div class="newColumn" >
			  		<div class="leftColumn"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn">
				   		 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				         	 <s:select 
                                 	 id="%{key}"
                                 	 name="%{key}" 
                                 	 list="testTypeMap"
                                 	 headerKey="-1"
                                  	cssClass="select"
                                  	cssStyle="width:18.5%"
                                 	 theme="simple"
                                  	multiple="true"
                                  	onchange="checkValues();"
                                	>
                         		 </s:select>
				    </div>
			        </div>
			      
		  </s:iterator>

	<s:iterator value="textBox" status="status" begin="0" end="1">
		<s:if test="%{mandatory}">
			<span id="complSpan" class="pIds" style="display: none;"><s:property
				value="%{key}" />#<s:property value="%{value}" />#<s:property
				value="%{colType}" />#<s:property value="%{validationType}" />,</span>
		</s:if>

		<div class="newColumn">
		<div class="leftColumn"><s:property value="%{value}" />:</div>
		<div class="rightColumn"><s:if test="%{mandatory}">
			<span class="needed"></span>
		</s:if> <s:textfield cssStyle="width: 79%" name="%{key}" id="%{key}"
			maxlength="50" cssClass="textField" placeholder="Enter Data" />
	
		</div>
		</div>

	</s:iterator>


<s:iterator value="textBox" status="status" begin="2" end="2">
		<s:if test="%{mandatory}">
			<!--<span id="complSpan" class="pIds" style="display: none;"><s:property
				value="%{key}" />#<s:property value="%{value}" />#<s:property
				value="%{colType}" />#<s:property value="%{validationType}" />,</span>
		--></s:if>

		<div class="newColumn">
		<div class="leftColumn"><s:property value="%{value}" />:</div>
		<div class="rightColumn"><s:if test="%{mandatory}">
			<span class="needed"></span>
			</s:if> 
			  <s:radio name="%{key}" id="%{key}" list="#{'Male':'Male','Female':'Female'}" />
		
		
		</div>
		</div>

	</s:iterator>

<s:iterator value="textBox" status="status" begin="3" end="4">
		<s:if test="%{mandatory}">
			<span id="complSpan" class="pIds" style="display: none;"><s:property
				value="%{key}" />#<s:property value="%{value}" />#<s:property
				value="%{colType}" />#<s:property value="%{validationType}" />,</span>
		</s:if>

		<div class="newColumn">
		<div class="leftColumn"><s:property value="%{value}" />:</div>
		<div class="rightColumn"><s:if test="%{mandatory}">
			<span class="needed"></span>
		</s:if> <s:textfield cssStyle="width: 79%" name="%{key}" id="%{key}"
			maxlength="50" cssClass="textField" placeholder="Enter Data" />
	
		</div>
		</div>

	</s:iterator>

	<!-- ADD DIV Code  -->
		  	<div id="extraDiv">
                 <s:iterator begin="100" end="109" var="typeIndx">
                        <div class="clear"></div>
                         <div id="<s:property value="%{#typeIndx}"/>" style="display: none ;   " class="container_block" >
                             <s:iterator value="textBox" status="counter" >
                            <div class="newColumn">
                                <div class="leftColumn" ><s:property value="%{value}" />:</div>
                                 <s:if test="%{mandatory}">
                                <span class="needed" style=" margin-left: -4px;"></span>
                                </s:if>
                                <div class="rightColumn">
                            
				         <s:if test="#counter.count%2 !=0">
				         	 <s:textfield cssStyle="width: 79%" name="%{key}"  id="%{key}%{#typeIndx}"  cssClass="textField"  placeholder="Enter Data" />
                                   
				       <div style="">
                            <s:if test="#typeIndx==110">
                                <div class="user_form_button2"><sj:a value="-" onclick="deletediv('%{#typeIndx}')" button="true"> - </sj:a></div>
                            </s:if>
                               <s:else>
                                   <div class="user_form_button2" style="float: right;margin-right: -610px;margin-top: -29px;"><sj:a value="+"  onclick="adddiv('%{#typeIndx+1}')" button="true"> + </sj:a></div>
                                  <div class="user_form_button3 " style="float: right;margin-right: -649px;margin-top: -29px;"><sj:a value="-" onclick="deletediv('%{#typeIndx}')" button="true"> - </sj:a></div>
                               </s:else>
                               </div>
				         </s:if>
				         <s:else>
				         	<s:textfield  name="%{key}" id="%{key}%{#typeIndx}"  maxlength="50" cssClass="textField" placeholder="Enter Data"/>
				         </s:else>      
                                    </div>
                                    
                            
                            </div>
                            
                            </s:iterator>
                        </div>
                    </s:iterator>
                    </div>
			
			<!--  End ADD Div -->
		  
		
		<center>
	 	 	<img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/>
		</center>
		<center>
		<div class="clear"></div>
        <div class="clear"></div>
        <br>
		<div class="fields">
		<center>
		 <ul>
			<li class="submit" style="background:none;">
			<div class="type-button">
	        <sj:submit 
         				targets			=	"complTarget" 
         				clearForm		=	"true"
         				value			=	" Save " 
         				button			=	"true"
                 		effect			=	"highlight"
	                    effectOptions	=	"{ color : '#ffffff'}"
                 		effectDuration	=	"5000"
                 		onCompleteTopics=	"makeEffect"
                        onBeforeTopics="validate"
     		  	  />
     		  	    <sj:a 
						button="true" href="#" 
						onclick="resetTaskName('testName');resetColor('.pIds');"
						>
						Reset
					</sj:a>
     		  	  <sj:a 
						button="true" href="#" 
						onclick="addTestName();"
						>
						Back
					</sj:a>
	        </div>
	        </li>
		 </ul>
		 </center>
		 <br>
		
		 </div>
		 </center>
		 
   </s:form>
   </div>
</div>
</body>
</html>