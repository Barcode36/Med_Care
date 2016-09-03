<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
</head>
<body>

<div class="list-icon">
	<div class="head">Configure </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Field Add</div>
</div>

<div class="clear"></div>
<div style=" float:left; padding:10px 0%; width:100%;">
<div class="clear"></div>
<div class="border">
<div class="container_block">
<div style=" float:left; padding:20px 1%; width:98%;">

<s:form id="formtwo" name="formtwo"  action="addServiceSettingSubmit" theme="css_xhtml"  method="post" >
	
	<div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
	      <div id="errZone" style="float:left; margin-left: 7px"></div>        
	</div>
	        			
	        <!--<span id="mandatoryFields" class="pIds" style="display: none; ">servicesName#Services#D#a,</span>
	        <span id="mandatoryFields" class="pIds" style="display: none; ">field_name#Field Name#T#ans,</span>
	        <span id="mandatoryFields" class="pIds" style="display: none; ">field_value#Field Value#T#ans,</span>
	        <span id="mandatoryFields" class="pIds" style="display: none; ">colType#Column Type#D#a,</span>
	        <span id="mandatoryFields" class="pIds" style="display: none; ">hideOrShow#Hide/Show#D#a,</span>
	        <span id="mandatoryFields" class="pIds" style="display: none; ">align#Align#T#a,</span>
	        <span id="mandatoryFields" class="pIds" style="display: none; ">width#Width#n#a,</span>
	        <span id="mandatoryFields" class="pIds" style="display: none; ">validationType#Validation Type#D#a,</span>
	        <span id="mandatoryFields" class="pIds" style="display: none; ">mandatory#Mandatory#a#a,</span>
	        
	        --><div class="newColumn">
	              <div class="leftColumn">Services:</div>
	                
	                <div class="rightColumn"><span class="needed"></span>
	                          <s:select 
	                               name="servicesName"  
	                               id="servicesName" 
	                               list="#{'EHC':'EHC','OPD':'OPD','Radiology':'Radiology','IPD':'IPD','DayCare':'Day Care','Laboratory':'Laboratory','Emergency':'Emergency'}"   
	                               headerKey="-1"
	                               headerValue="Select Services"
	                               cssClass="textField" 
	                               theme="simple" 
	                               placeholder="Enter Data"
	                            
	                           />
				
	                </div>
	         </div>
	         
	         <div class="newColumn">
	               <div class="leftColumn">Field Name:</div>
	               
	               <div class="rightColumn">
	                       <span class="needed"></span>
	                       <s:textfield   id="field_name" name="field_name" cssStyle="margin:0px 0px 10px 0px" cssClass="textField" placeholder="Enter Data"></s:textfield>         
	               </div>
	        
	         </div>
	         <div class="newColumn">
	              <div class="leftColumn">Field Value:</div>
	              <div class="rightColumn">
	                       <span class="needed"></span>
	                       <s:textfield   id="field_value" name="field_value" cssStyle="margin:0px 0px 10px 0px" cssClass="textField" placeholder="Enter Name Without Space"></s:textfield>         
	              </div>
	        
	         </div>
	         <div class="newColumn">
	           <div class="leftColumn">Column Type:</div>
	                 <div class="rightColumn"><span class="needed"></span>
	                     <s:select 
	                     id="colType"
	                     name="colType"
	                     list="#{'D':'Drop Down', 'T':'Text Field', 'datetime':'Date & Time', 'Time':'Time','Date':'Date'}"
	                     headerKey="-1"
	                     headerValue="Select Column Type"
	                     cssClass="textField"
	                     cssStyle=""
	                     onchange="fillValue('fillcoltype', this.value);"
	                     >
	                     </s:select>  
	                  </div>
	         </div>   
	         <div class="clear"></div>
	         <div id="fillcoltype" style="display:none">
	               <div class="newColumn">
	                   <div class="leftColumn">Fill Data Column Type:</div>
	                   <div class="rightColumn">
	            
	                     <s:textarea id="fillDD" name="fillDDColType" cssStyle="margin:0px 0px 10px 0px" cssClass="textField" placeholder="Enter Data with # seperator"/>
	            
	                    </div>
	                </div>
	          </div>       
	          
	          
	         <div class="newColumn">
	           <div class="leftColumn">Hide/Show:</div>
	                 <div class="rightColumn"><span class="needed"></span>
	                     <s:select 
	                     id="hideOrShow"
	                     name="hideOrShow"
	                     list="#{'false':'Show', 'true':'Hide'}"
	                     headerKey="-1"
	                     headerValue="Select Column Type"
	                     cssClass="textField"
	                     cssStyle=""
	                     >
	                     </s:select>  
	                  </div>
	         </div>   
	         <div class="newColumn">
	           <div class="leftColumn">Align:</div>
	                 <div class="rightColumn"><span class="needed"></span>
	                     <s:select 
	                     id="align"
	                     name="align"
	                     list="#{'left':'Left', 'center':'Center', 'right':'Right'}"
	                     headerKey="-1"
	                     headerValue="Select Column Type"
	                     cssClass="textField"
	                     cssStyle=""
	                     >
	                     </s:select>  
	                  </div>
	         </div>   
	         <div class="newColumn">
	           <div class="leftColumn">Width:</div>
	            
	               <div class="rightColumn"><span class="needed"></span>
	                 
	                       <s:textfield   id="width" name="width" cssStyle="margin:0px 0px 10px 0px" cssClass="textField" placeholder="Enter Data"></s:textfield>         
	               </div>
	        
	         </div>
	        
	         <div class="newColumn">
	           <div class="leftColumn">Validation Type:</div>
	                 <div class="rightColumn"><span class="needed"></span>
	                     <s:select 
	                     id="validationType"
	                     name="validationType"
	                     list="#{'a':'Alphabates', 'an':'Alphanumeric', 'ans':'Aplphanumeric with Characters','e':'Email', 'm':'Mobile'}"
	                     headerKey="-1"
	                     headerValue="Select Column Type"
	                     cssClass="textField"
	                     cssStyle=""
	                     >
	                     </s:select>  
	                  </div>
	         </div>  
	          
	         <div class="newColumn">
	         <div class="leftColumn">Mandatroy:</div>
	                 <div class="rightColumn"><span class="needed"></span>
	                
	                        <s:radio name="mandatory"  id="mandatory" value="%{'0'}" list="#{'1':'Yes', '0':'No'}"></s:radio>
	                    
	                 </div>
	         </div>   


<!-- add field(++) -->
<div id="newDiv" style="float: right;margin-top: -38px; margin-right: 50px;"><sj:a value="+" onclick="adddiv('110')" button="true"> + </sj:a></div>
<div class="clear"></div>
<s:iterator begin="110" end="118" var="deptIndx">
<div id="<s:property value="%{#deptIndx}"/>" style="display: none;">
<div class="border">

	         <div class="newColumn">
	               <div class="leftColumn">Field Name:</div>
	            
	               <div class="rightColumn"><span class="needed"></span>
	                 
	                       <s:textfield   id="field_name%{#deptIndx}" name="field_name" cssStyle="margin:0px 0px 10px 0px" cssClass="textField" placeholder="Enter Data"></s:textfield>         
	               </div>
	        
	         </div>
	         <div class="newColumn">
	              <div class="leftColumn">Field Value:</div>
	              <div class="rightColumn"><span class="needed"></span>
	                 
	                       <s:textfield   id="field_value%{#deptIndx}" name="field_value" cssStyle="margin:0px 0px 10px 0px" cssClass="textField" placeholder="Enter Name Without Space"></s:textfield>         
	              </div>
	        
	         </div>
	         <div class="clear"></div>
	         
	         
	         
	         <div class="newColumn">
	           <div class="leftColumn">Column Type:</div>
	                 <div class="rightColumn"><span class="needed"></span>
	                     <s:select 
	                     id="colType%{#deptIndx}"
	                     name="colType"
	                     list="#{'D':'Drop Down', 'T':'Text Field', 'datetime':'Date & Time', 'Time':'Time', 'Date':'Date'}"
	                     headerKey="-1"
	                     headerValue="Select Column Type"
	                     cssClass="textField"
	                     cssStyle=""
	                     onchange="fillValue('fillcoltype%{#deptIndx}', this.value);"
	                     >
	                     </s:select>  
	                  </div>
	         </div>  
	         
	         
	          <div id="fillcoltype<s:property value="%{#deptIndx}"/>" style="display:none">
	               <div class="newColumn">
	                   <div class="leftColumn">Fill Data Column Type:</div>
	                   <div class="rightColumn"><span class="needed"></span>
	            
	                     <s:textarea id="fillDD" name="fillDDColType" cssStyle="margin:0px 0px 10px 0px" cssClass="textField" placeholder="Enter Data with # seperator"/>
	            
	                    </div>
	                </div>
	          </div>
	               
	         <div class="newColumn">
	           <div class="leftColumn">Hide/Show:</div>
	                 <div class="rightColumn"><span class="needed"></span>
	                     <s:select 
	                     id="hideOrShow%{#deptIndx}"
	                     name="hideOrShow"
	                     list="#{'false':'Show', 'true':'Hide'}"
	                     headerKey="-1"
	                     headerValue="Select Column Type"
	                     cssClass="textField"
	                     cssStyle=""
	                     >
	                     </s:select>  
	                  </div>
	         </div>   
	         <div class="newColumn">
	           <div class="leftColumn">Align:</div>
	                 <div class="rightColumn"><span class="needed"></span>
	                     <s:select 
	                     id="align%{#deptIndx}"
	                     name="align"
	                     list="#{'left':'Left', 'center':'Center', 'right':'Right'}"
	                     headerKey="-1"
	                     headerValue="Select Column Type"
	                     cssClass="textField"
	                     cssStyle=""
	                     >
	                     </s:select>  
	                  </div>
	         </div>   
	         <div class="newColumn">
	           <div class="leftColumn">Width:</div>
	            
	               <div class="rightColumn"><span class="needed"></span>
	                 
	                       <s:textfield   id="width%{#deptIndx}" name="width" cssStyle="margin:0px 0px 10px 0px" cssClass="textField" placeholder="Enter Data"></s:textfield>         
	               </div>
	        
	         </div>
	        
	         <div class="newColumn">
	           <div class="leftColumn">Validation Type:</div>
	                 <div class="rightColumn"><span class="needed"></span>
	                     <s:select 
	                     id="validationType%{#deptIndx}"
	                     name="validationType"
	                     list="#{'a':'Alphabates', 'an':'Alphanumeric', 'ans':'Aplphanumeric with Characters','e':'Email', 'm':'Mobile'}"
	                     headerKey="-1"
	                     headerValue="Select Column Type"
	                     cssClass="textField"
	                     cssStyle=""
	                     >
	                     </s:select>  
	                  </div>
	         </div>   
	         <div class="newColumn">
	           <div class="leftColumn">Mandatory:</div>
	                 <div class="rightColumn"><span class="needed"></span>
	                 <s:radio name="mandatory"  id="mandatory%{#deptIndx}" value="%{'0'}" list="#{'1':'Yes', '0':'No'}"></s:radio>
	                    
	                  </div>
	         </div>   

                 <div style="float: right; margin-top: -45px;">	
		              <s:if test="#deptIndx == 118">
	    	              <div class="user_form_button2"><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true">-</sj:a></div>
                      </s:if>
       	              <s:else>
     	  	              <div class="user_form_button2"><sj:a value="+" onclick="adddiv('%{#deptIndx+1}')" button="true">+</sj:a></div>
           	              <div class="user_form_button3" style="margin-left: 10px;"><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true">-</sj:a></div>
                     </s:else>
	            </div>
 </div>
 </div>
</s:iterator>

<!-----------------------------------------++ END HERE-------------------------------->
<div class="clear"></div>
<div class="clear"></div>
<br>
<center><img id="indicator3" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>

<div class="type-button" style="margin-left: -200px;">
<center> 
       <sj:submit 
           			   targets="level123" 
                       clearForm="true"
                       value="Save" 
                       effect="highlight"
                       effectOptions="{ color : '#ffffff'}"
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
		               	onclick="getSettingPage();"
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
</div>
</div>
</body>
</html>