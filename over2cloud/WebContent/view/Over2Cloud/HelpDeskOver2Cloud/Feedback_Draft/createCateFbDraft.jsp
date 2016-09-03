<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/fbdraft.js"/>"></script>
<SCRIPT type="text/javascript">
function getResolutionTime(addressingTime,escalationTime,id)
{
  //alert("Inside Alert  ");
  var addressId = document.getElementById(addressingTime).value;
  var escId = document.getElementById(escalationTime).value;
  $.getJSON("/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Feedback_Draft/resolutionTime11.action?addressTime="+addressId+"&escTime="+escId,
  function(data){
    		       if(data.resolution_time == null){
        		      // alert("Inside If");
    		       $("#escalateTime"+id).val("NA");
    		    }
    		       else {
    		    	  // alert("Inside Else "+data.resolution_time);
    		          	   $("#escalateTime"+id).val(data.resolution_time);
    					}
    		   });
}
</SCRIPT>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Configure Feedback</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div>
</div>
<div class="clear"></div>
<div style="overflow:auto; height:440px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
<s:hidden  id="moduleName" name="moduleName" value="%{dataFor}"></s:hidden>
<sj:accordion id="accordion" autoHeight="false">
<s:if test="levelOfFeedDraft>0">
<sj:accordionItem title="%{feedLevel1}" id="acor_item1">  
	 <s:form id="formone" name="formone" action="addFeedbackType" theme="css_xhtml"  method="post" enctype="multipart/form-data" >
	  <s:hidden name="moduleName"  value="%{dataFor}"/>
	  <s:hidden id="viewType" name="viewType" value="%{pageType}"/>
      <center> 
        <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="errZone" style="float:center; margin-left: 7px"></div>        
        </div>
      </center> 
	          	
     <div style="float:center; width:90%;">
        	<s:iterator value="pageFieldsColumns" status="status" begin="0" end="0">
        		<s:if test="%{mandatory}">
	        	<span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>1#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	        	</s:if>
        			<div style="float:left; width:50%;">
        			<div class="label" style="float:left; width:24%;"><s:property value="%{value}"/>:</div>
					<div class="label" style="float:left; text-align:left; width:70%;">
						<s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                  <s:select 
		                              id="%{key}1"
		                              name="%{key}"
		                              list="deptList"
		                              headerKey="-1"
		                              headerValue="Select Department" 
		                              cssClass="select"
		                              cssStyle="width:82%"
		                              >
		                  </s:select>
					</div>
					</div>
        	</s:iterator>
        	
        	<s:iterator value="pageFieldsColumns" begin="1" end="1">
        	<s:if test="%{mandatory}">
	        	<span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	       </s:if>
				<div style="float:left; width:50%;">
					<div class="label" style="float:left; width:24%;"><s:property value="%{value}"/>:</div>
			           <div class="label" style="float:left; text-align:left; width:70%;"> <s:if test="%{mandatory}"><span class="needed"></span></s:if>
					        <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px; width:80%"/>
					        </div>
					  <div id="newDiv" style="float: right;width: 10%;margin-top: -46px;"><sj:a value="+" onclick="adddiv('100')" button="true" cssStyle="margin-left: -23px;">+</sj:a></div>
				</div>
	        </s:iterator>
	      
	      
		  <s:iterator begin="100" end="118" var="fbTypeIndx">
		  <div class="clear"></div>
	      <div id="<s:property value="%{#fbTypeIndx}"/>" style="display: none">
		  <s:iterator value="pageFieldsColumns" begin="1" end="1">
		   <div style="float:left; width:50%;">
			<div class="label" style="float:left; width:24%;"></div>
			     <div class="label" style="float:left; text-align:left; width:70%;">
				   <div style="float: left;margin-left: 85%;margin-top: -29px;width: 44%"></div>
				 </div>
		   </div>
		  <div style="float:left; width:50%;">
			<div class="label" style="float:left; width:24%;"><s:property value="%{value}"/>:</div>
			     <div class="label" style="float:left; text-align:left; width:70%;"><sj:textfield name="%{key}" id="%{key}" placeholder="Enter Data" cssClass="textField" />
				 <div style="float: left;margin-left: 85%;margin-top: -29px;width: 44%">	
					<s:if test="#deptIndx==118">
	                    <div class="user_form_button2"><sj:a value="-" onclick="deletediv('%{#fbTypeIndx}')" button="true">-</sj:a></div>
	                </s:if>
					<s:else>
	  		  			<div class="user_form_button2"><sj:a value="+" onclick="adddiv('%{#fbTypeIndx+1}')" button="true" cssStyle="margin-left: -16px;">+</sj:a></div>
	          			<div class="user_form_button3" style="margin-left: 0px;"><sj:a value="-" onclick="deletediv('%{#fbTypeIndx}')" cssClass="button" button="true" cssStyle="margin-left: -9px;">-</sj:a></div>
	       			</s:else>
       			</div>
				</div>
		  </div>
		  </s:iterator>
		 </div>
		 </s:iterator>
     </div>
		  
		  <div class="clear"></div>
		 <div style="width: 100%; text-align: center; padding-bottom: 10px;">
			   <sj:submit 
			   targets="target1" 
			   clearForm="true" 
			   value="  Add  "  
			   effect="highlight" 
			   effectOptions="{ color : '#222222'}" 
			   effectDuration="5000" 
			   button="true" 
			   onCompleteTopics="beforeFirstAccordian" 
			   cssClass="submit" 
			   indicator="indicator1"  
			   onBeforeTopics="validate"
			   cssStyle="margin-left: -180px;margin-bottom: -23px;"
	           />
            <sj:a cssStyle="margin-left: 21px;margin-top: -3px;" button="true" href="#" onclick="resetForm('formone');">Reset</sj:a>
            <sj:a cssStyle="margin-left: 2px;margin-top: -4px;" button="true" href="#" onclick="backForm('viewType','moduleName');">Back</sj:a>
		  </div>
				
		  <center><img id="indicator1" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
		  <sj:div id="foldeffect1" effect="fold"><div id="target1"></div></sj:div>  
     </s:form>
    
     </sj:accordionItem>
</s:if>


<s:if test="levelOfFeedDraft>1">
<sj:accordionItem title="%{feedLevel2}" id="acor_item2">
<s:form id="formtwo" name="formtwo" action="addFeedCategory"  theme="css_xhtml"  method="post" enctype="multipart/form-data" >
     <s:hidden name="moduleName"  value="%{dataFor}"/>
	 <s:hidden id="viewType" name="viewType" value="%{pageType}"/>
       <center> 
           <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
                  <div id="errZoneformtwo" style="float:center; margin-left: 7px"></div>        
           </div>
       </center>
	   <div style="float:left; width:90%;">
	   <s:iterator value="pageFieldsColumns" begin="0" end="0" status="status">
	         <s:if test="#status.odd">
	           <s:if test="%{mandatory}"><span id="mandatoryFields" class="pIdsformtwo" style="display: none; "><s:property value="%{key}"/>2#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span></s:if>
                   <div style="float:left; width:50%;">
        			   <div class="label" style="float:left; width:24%;"><s:property value="%{value}"/>:</div>
					        <div class="label" style="float:left; text-align:left; width:70%;">
					             <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                  <s:select 
				                              id="%{key}2"
				                              name="%{key}"
				                              list="deptList"
				                              headerKey="-1"
				                              headerValue="Select Department" 
				                              cssClass="select"
					                          cssStyle="width:82%"
				                              onchange="commonSelectAjaxCall('feedback_type','id','fbType','dept_subdept',this.value,'moduleName','moduleName','fbType','ASC','fbType2','Select Feedback Type');"
				                              >
				                  </s:select>
                           </div>
                   </div>
	          </s:if>
              </s:iterator>
		     <% int temp11=0; %>
		     <s:iterator value="pageFieldsColumns" begin="2" end="3">
		     <s:if test="%{mandatory}">
            		<span id="mandatoryFields" class="pIdsformtwo" style="display: none; "><s:property value="%{key}"/>2#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
		     <%if(temp11%2==0) {%>
		      <s:if test="key=='fbType'">
                <div style="float:left; width:50%;">
        		  <div class="label" style="float:left; width:24%;"><s:property value="%{value}"/>:</div>
					 <div class="label" style="float:left; text-align:left; width:70%;">
					   <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                  <s:select 
		                             cssClass="form_menu_inputselect"
		                             id="%{key}2"
		                             name="feedTypeId" 
		                             list="{'No Data'}" 
		                             headerKey="-1"
		                             headerValue="Select Feedback Type"
		                             cssClass="select"
				                     cssStyle="width:82%">
		                  </s:select>
                    </div>
                 </div>
              </s:if>
              <%} else {%>
              <div style="float:left; width:50%;">
				   <div class="label" style="float:left; width:24%;"><s:property value="%{value}"/>:</div>
						<div class="label" style="float:left; text-align:left; width:70%;">
						<s:if test="%{mandatory}"><span class="needed"></span></s:if>
					 	 <s:select 
		                              id="%{key}2"
		                             name="%{key}" 
		                             list="cateList" 
		                             headerKey="-1"
		                             headerValue="Select Category"
		                             cssClass="select"
				                     cssStyle="width:82%">
		                  </s:select>
					 </div>
					   </div>
             
		   <%}temp11++;  %>
		   </s:iterator>
		   
		  <%--   <s:iterator begin="150" end="168" var="catgIndx">
	          <div id="<s:property value="%{#catgIndx}"/>" style="display: none">
                  
              <div style="float:left; width:50%;">
			   <div class="label" style="float:left; width:24%;"><s:property value="%{value}"/>:</div>
					<div class="label" style="float:left; text-align:left; width:70%;">
						<s:textfield name="%{key}" id="%{key}2" cssStyle="width:68%;" cssClass="textField"  placeholder="Enter Data"/>
					</div>
		      </div>
              <div style="float: left;margin-left: -12%;margin-top: 9px;">
              	<s:if test="#deptIndx==168">
                	<div class="user_form_button2"><sj:a value="-" onclick="deletediv('%{#catgIndx}')" button="true">-</sj:a></div>
              	</s:if>
				<s:else>
	 		  		<div class="user_form_button2"><sj:a value="+" onclick="adddiv('%{#catgIndx+1}')" button="true" cssStyle="margin-left: -4px;">+</sj:a></div>
	         		<div class="user_form_button3" style="margin-left: 3px;"><sj:a value="-" onclick="deletediv('%{#catgIndx}')" button="true">-</sj:a></div>
	      		</s:else>
              </div>
              
               <div style="float:left; width:50%;">
			   <div class="label" style="float:left; width:24%;"></div>
					<div class="label" style="float:left; text-align:left; width:70%;"></div>
		      </div>
              </div>
	          </s:iterator> --%>
		   </div>
	 
	       <center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
	       <div class="clear"></div>
		    <div style="width: 100%; text-align: center; padding-bottom: 10px;">
				
	               <sj:submit 
	                        targets="feeddraft2" 
	                        clearForm="true"
	                        value="  Add  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="beforeSecondAccordian"
	                        cssClass="submit"
	                        indicator="indicator2"
	                        onBeforeTopics="validateFormTwo"
	                        cssStyle="margin-left: -115px;margin-bottom: -23px;"
				            />
				            <sj:a cssStyle="margin-left: 86px;margin-top: -3px;" button="true" href="#" onclick="resetForm('formtwo');">Reset</sj:a>
				            <sj:a cssStyle="margin-left: 2px;margin-top: -4px;" button="true" href="#" onclick="backForm('viewType','moduleName');">Back</sj:a>
				<sj:div id="foldeffect2" effect="fold"><div id="feeddraft2"></div></sj:div>
				</div>
			
			</s:form>
			</sj:accordionItem>
</s:if>

<s:if test="levelOfFeedDraft>2">
<sj:accordionItem title="%{feedLevel3}" id="acor_item3">
<s:form id="formthree" name="formthree" action="addFeedSubCategory"  theme="css_xhtml"  method="post" enctype="multipart/form-data" >
     <s:hidden name="moduleName"  value="%{dataFor}"/>
	 <s:hidden id="viewType" name="viewType" value="%{pageType}"/>
	 <center> 
	   <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZoneformthree" style="float:center; margin-left: 7px"></div></div>
	 </center>
	 
	 <div style="float:left; width:90%;">
	   <s:iterator value="pageFieldsColumns" begin="0" end="0" status="status">
	         <s:if test="#status.odd">
	         <s:if test="%{mandatory}">
	               <span id="mandatoryFields" class="pIdsformthree" style="display: none; "><s:property value="%{key}"/>3#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	         </s:if>
                   <div style="float:left; width:50%;">
        			   <div class="label" style="float:left; width:24%;"><s:property value="%{value}"/>:</div>
					        <div class="label" style="float:left; text-align:left; width:70%;">
					             <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                  <s:select 
				                              id="%{key}3"
				                              name="%{key}"
				                              list="deptList"
				                              headerKey="-1"
				                              headerValue="Select Department" 
				                              cssClass="select"
					                          cssStyle="width:82%"
				                              onchange="commonSelectAjaxCall('feedback_type','id','fbType','dept_subdept',this.value,'moduleName','moduleName','fbType','ASC','fbType3','Select Feedback Type');"
				                              >
				                  </s:select>
                           </div>
                   </div>
	          </s:if>
              </s:iterator>
		      
		     <s:iterator value="pageFieldsColumns" begin="4" end="5" status="status">
	   		 <s:if test="%{mandatory}"><span id="mandatoryFields" class="pIdsformthree" style="display: none; "><s:property value="%{key}"/>3#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span></s:if>
	         <s:if test="#status.odd">
                   <div style="float:left; width:50%;">
        			   <div class="label" style="float:left; width:24%;"><s:property value="%{value}"/>:</div>
					        <div class="label" style="float:left; text-align:left; width:70%;">
					             <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                  <s:select 
				                              id="%{key}3"
				                              list="{'No Data'}" 
				                              headerKey="-1"
				                              headerValue="Select Feedback Type" 
				                              cssClass="select"
					                          cssStyle="width:82%"
				                              onchange="commonSelectAjaxCall('feedback_category','id','categoryName','fbType',this.value,'','','categoryName','ASC','categoryName3','Select Category');">
				                  </s:select>
                           </div>
                   </div>
	          </s:if>
              <s:else>
                     <div style="float:left; width:50%;">
					      <div class="label" style="float:left; width:24%;"><s:property value="%{value}"/>:</div>
							<div class="label" style="float:left; text-align:left; width:70%;">
				             	  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                  <s:select 
				                              id="%{key}3"
				                              name="categoryId"
				                              list="{'No Data'}"
				                              headerKey="-1"
				                              headerValue="Select Category" 
				                              cssClass="select"
				                              cssStyle="width:82%">
				                  </s:select>
				           </div>
	                  </div>
                   </s:else>
              </s:iterator>
		      
		     <s:iterator value="pageFieldsColumns" begin="6" end="7" status="status">
	   		 <s:if test="%{mandatory}"><span id="mandatoryFields" class="pIdsformthree" style="display: none; "><s:property value="%{key}"/>3#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span></s:if>
	         <s:if test="#status.odd">
                   <div style="float:left; width:50%;">
        			   <div class="label" style="float:left; width:24%;"><s:property value="%{value}"/>:</div>
					        <div class="label" style="float:left; text-align:left; width:70%;">
					             <s:if test="%{mandatory}"><span class="needed"></span></s:if>
					              <s:textfield name="%{key}" id="%{key}3"  cssClass="textField"  placeholder="Enter Data"/>
                           </div>
                   </div>
	          </s:if>
              <s:else>
                     <div style="float:left; width:50%;">
					      <div class="label" style="float:left; width:24%;"><s:property value="%{value}"/>:</div>
							<div class="label" style="float:left; text-align:left; width:70%;">
				             	  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				             	  <s:textfield name="%{key}" id="%{key}3" cssClass="textField"  placeholder="Enter Data"/>
				           </div>
	                  </div>
                   </s:else>
              </s:iterator>
		      
		     <s:iterator value="pageFieldsColumns" begin="8" end="11" status="status">
	   		 <s:if test="%{mandatory}"><span id="mandatoryFields" class="pIdsformthree" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span></s:if>
	         <s:if test="#status.odd">
                   <div style="float:left; width:50%;">
        			   <div class="label" style="float:left; width:24%;"><s:property value="%{value}"/>:</div>
					        <div class="label" style="float:left; text-align:left; width:70%;">
					             <s:if test="%{mandatory}"><span class="needed"></span></s:if>
					             <sj:datepicker name="%{key}" id="%{key}"  placeholder="Enter Data" readonly="true" showOn="focus"  timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="5" cssClass="textField" />   
                           </div>
                   </div>
	          </s:if>
              <s:else>
               <div style="float:left; width:50%;">
               <s:if test="key=='escalationDuration'">
		       <div class="label" style="float:left; width:24%;"><s:property value="%{value}"/>:</div>
				<div class="label" style="float:left; text-align:left; width:70%;">
	             	  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	             	 <s:select 
	                              id="%{key}"
	                              name="%{key}"
	                              list="#{'2':'Two Time','3':'Three Time','4':'Four Time','5':'Five Time'}" 
	                              headerKey="1"
	                              headerValue="One Time"
		                          cssClass="select"
	                              cssStyle="width:82%">
	                  </s:select>
	           </div>
	           </s:if>
	           <s:else>
	           <div class="label" style="float:left; width:24%;"><s:property value="%{value}"/>:</div>
				<div class="label" style="float:left; text-align:left; width:70%;">
	             	  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	             	<sj:datepicker name="%{key}" id="%{key}"  placeholder="Enter Data" readonly="true" onchange="getResolutionTime('addressingTime','resolutionTime','');"  showOn="focus"  timepicker="true" timepickerOnly="true" timepickerGridHour="4" timepickerGridMinute="10" timepickerStepMinute="5" cssClass="textField" />
	           </div>
	           </s:else>
              </div>
              </s:else>
              </s:iterator>
		      
		      <s:iterator value="pageFieldsColumns" begin="12" status="status">
	   		 <s:if test="%{mandatory}"><span id="mandatoryFields" class="pIdsformthree" style="display: none; "><s:property value="%{key}"/>3#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span></s:if>
	         <s:if test="#status.odd">
                   <div style="float:left; width:50%;">
                   <s:if test="key=='escalationLevel'">
        			   <div class="label" style="float:left; width:24%;"><s:property value="%{value}"/>:</div>
					        <div class="label" style="float:left; text-align:left; width:70%;">
					             <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                  <s:select 
				                              id="%{key}3"
				                              name="%{key}"
				                              list="#{'Level1':'Level1','Level2':'Level2','Level3':'Level3','Level4':'Level4'}"
					                          headerKey="-1"
					                          headerValue="Select Escalation Level"
				                              cssClass="select"
					                          cssStyle="width:82%"
				                              >
				                  </s:select>
                           </div>
                   </s:if>
                   </div>
	          </s:if>
              <s:else>
                     <div style="float:left; width:50%;">
                     <s:if test="key=='needEsc'">
					      <div class="label" style="float:left; width:24%;"><s:property value="%{value}"/>:</div>
							<div class="label" style="float:left; text-align:left; width:70%;">
				             	  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                  <s:select 
				                              id="%{key}3"
				                              name="%{key}"
				                              list="#{'Y':'Yes','N':'No'}"  
				                              headerKey="-1"
				                              headerValue="Select Need Escalation"
				                              cssClass="select"
				                              cssStyle="width:82%">
				                  </s:select>
				           </div>
				           </s:if>
	                  </div>
                   </s:else>
              </s:iterator>
		      <div class="clear"></div>
		      <center><img id="indicator3" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				<div class="fields">
				<ul>
				<li class="submit" style="background:none;">
					<div class="type-button">
	                <center><sj:submit 
	                        targets="feeddraft3" 
	                        clearForm="true"
	                        value="  Add  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="beforeThirdAccordian"
	                        cssClass="submit"
	                        indicator="indicator3"
	                        onBeforeTopics="validateFormThree"
							cssStyle="margin-left: -40px;margin-bottom: -23px;"
				            />
				            <sj:a cssStyle="margin-left: 160px;margin-top: -3px;" button="true" href="#" onclick="resetForm('formthree');">Reset</sj:a>
				            <sj:a cssStyle="margin-left: 2px;margin-top: -4px;" button="true" href="#" onclick="backForm('viewType','moduleName');">Back</sj:a>
	                        </center>
	               </div>
				</ul>
				<sj:div id="foldeffect3" cssClass="sub_class1"  effect="fold"><div id="feeddraft3"></div></sj:div>
				</div>
		      </div>
</s:form>
</sj:accordionItem>
</s:if>

</sj:accordion>
</div>
</div>
</body>
</html>
