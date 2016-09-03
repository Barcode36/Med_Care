<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script  type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<script type="text/javascript" src="js/cpservice/cpService.js"></script>
<script type="text/javascript">
$(document).ready(function()
{
	$("#services").multiselect({
		   show: ["", 200],
		   hide: ["explode", 100],
		   click: function(event, ui)
		          {
			        console.log(ui);
			       // getmorefields(ui.value, ui.checked);
			      }
		   
		   
		});
	
	

});
</script>
<style type="text/css">
.ui-multiselect{
width: 244px !important;
}
</style>
</head>
<body>
<sj:dialog
          id="serviceConf"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Seek Approval Action"
          modal="true"
          closeTopics="closeEffectDialog"
         
          >
</sj:dialog>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head"><s:property value="%{headingName}"/></div><img alt="" src="images/forward.png" style="margin-top:10px; float: left;"><div class="head"> Add</div> 
				
	          	 
</div>
<div class="clear"></div>
<div style="overflow:auto; height:auto; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">

<s:form id="formPatAdd" name="formPatAdd" action="PatientAdd"  theme="simple"  method="post" enctype="multipart/form-data">
	<center>
			<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 6px;">
	             <div id="errZone" style="float:center; margin-left: 7px"></div>        
	        </div>
    </center>
    <s:iterator value="pageFieldsColumns" status="status" begin="0" end="13">
     <s:if test="%{mandatory}">
		     		<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
    <s:if test="#status.odd">
    <div class="newColumn">
	     <div class="leftColumn1"><s:property value="%{value}"/>:&nbsp;</div>
		 <div class="rightColumn1">
			 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
			 
			 <s:if test="%{colType == \"D\"}">
			  	<s:select name="%{key}" list="%{colMap}" id="%{key}"  cssClass="textField" theme="simple" cssStyle="width:82%" placeholder="Enter Data" onchange="Reset('.pIds');"/>
			 </s:if >
			 <s:elseif  test="%{colType == \"T\"}">
			 	<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" onchange="Reset('.pIds');"/>
			 </s:elseif>
			  <s:elseif  test="%{colType == \"date\"}">
			 	<sj:datepicker  cssStyle="height: 16px; width: 58px;" cssClass="button"  name="%{key}"  id="%{key}" size="20" maxDate="0"  readonly="true"  changeMonth="true" changeYear="true"  yearRange="1913:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select DOB" />
			 </s:elseif>
			  <s:elseif  test="%{colType == \"dt\"}">
			 	<sj:datepicker  cssStyle="height: 16px; width: 58px;" cssClass="button"  name="%{key}"  id="%{key}" size="20" maxDate="0"  readonly="true"  changeMonth="true" changeYear="true"  yearRange="1913:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select DOB" />
			 </s:elseif>
			  <s:elseif  test="%{colType == \"D+T\"}">
			 odd
			 </s:elseif>
             	
         </div>
    </div>
    </s:if>    
    <s:else>
     <div class="newColumn">
	     <div class="leftColumn1"><s:property value="%{value}"/>:&nbsp;</div>
		 <div class="rightColumn1">
			 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
             	 <s:if test="%{colType == \"D\"}">
	             	 	<s:select name="%{key}" list="%{colMap}" id="%{key}"  cssClass="textField" theme="simple" placeholder="Enter Data" onchange="Reset('.pIds');"/>
				 </s:if >
				 <s:elseif  test="%{colType == \"T\"}">
				 	<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" onchange="Reset('.pIds');"/>
				 </s:elseif>
				  <s:elseif  test="%{colType == \"date\"}">
			 	<sj:datepicker  cssStyle="height: 16px; width: 58px;" cssClass="button"  name="%{key}"  id="%{key}" size="20" maxDate="0"  readonly="true"  changeMonth="true" changeYear="true"  yearRange="1913:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select DOB" />
			 </s:elseif>
			   <s:elseif  test="%{colType == \"dt\"}">
			 	<sj:datepicker  cssStyle="height: 16px; width: 108px;" cssClass="button"  name="%{key}"  id="%{key}" size="20" maxDate="0"  readonly="true"  changeMonth="true" changeYear="true" timepicker="true" yearRange="1913:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Date & Time" />
			 </s:elseif>
			  <s:elseif  test="%{colType == \"MS\"}">
				 <s:select 
				      name="%{key}"   
				      list="#{'EHC':'EHC','OPD':'OPD','Radiology':'Radiology','IPD':'IPD','DayCare':'Day Care','Laboratory':'Laboratory','Emergency':'Emergency'}" 
				      id="%{key}"  
				      cssClass="textField" 
				       cssStyle="width : 10px"
				      theme="simple" 
				      placeholder="Enter Data"
				      multiple="true"
				      onchange="fillServiceField(this.value)"
				      />
				      <a href="#" onclick="getSettingPage()"><img src="images/setting-icon2.png" alt="settings" width="25" height="25"/></a>
			     </s:elseif>
				 <s:elseif  test="%{colType == \"D+T\"}">
			 	 <s:select  list="%{colMap}" name="uhidPrefix" cssClass="textField"  theme="simple" cssStyle="width:70px" />	<s:textfield name="uhidPostfix" cssClass="textField" placeholder="Enter Data" cssStyle="width:165px" onchange="Reset('.pIds');"/>
			 </s:elseif>
         </div>
    </div>
    </s:else>      
   </s:iterator>     
   
   
   
   
   
   <div id="addmorefields"></div>
   
   <!-- Add dynamic created fields of different service fields-->
   
   <div id="addServicefields"></div>
   
   
   <s:iterator value="pageFieldsColumns"  status="status" begin="14" end="16">
     <s:if test="%{mandatory}">
		     		<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
    <s:if test="#status.odd">
    <div class="newColumn">
	     <div class="leftColumn1"><s:property value="%{value}"/>:&nbsp;</div>
		 <div class="rightColumn1">
			 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
			 
			 <s:if test="%{colType == \"D\"}">
			  	<s:select name="%{key}" list="%{colMap}" id="%{key}"  cssClass="textField" theme="simple" cssStyle="width:82%" placeholder="Enter Data" onchange="Reset('.pIds');"/>
			 </s:if >
			 <s:elseif  test="%{colType == \"T\"}">
			 	<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" onchange="Reset('.pIds');"/>
			 </s:elseif>
			  <s:elseif  test="%{colType == \"date\"}">
			 	<sj:datepicker  cssStyle="height: 16px; width: 58px;" cssClass="button"  name="%{key}"  id="%{key}" size="20" maxDate="0"  readonly="true"  changeMonth="true" changeYear="true"  yearRange="1913:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select DOB" />
			 </s:elseif>
			  <s:elseif  test="%{colType == \"dt\"}">
			 	<sj:datepicker  cssStyle="height: 16px; width: 58px;" cssClass="button"  name="%{key}"  id="%{key}" size="20" maxDate="0"  readonly="true"  changeMonth="true" changeYear="true"  yearRange="1913:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select DOB" />
			 </s:elseif>
			 
             	
         </div>
    </div>
    </s:if>    
    <s:else>
     <div class="newColumn">
	     <div class="leftColumn1"><s:property value="%{value}"/>:&nbsp;</div>
		 <div class="rightColumn1">
			 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
             	 <s:if test="%{colType == \"D\"}">
	             	 	<s:select name="%{key}" list="%{colMap}" id="%{key}"  cssClass="textField" theme="simple" placeholder="Enter Data" onchange="Reset('.pIds');"/>
				 </s:if >
				 <s:elseif  test="%{colType == \"T\"}">
				 	<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" onchange="Reset('.pIds');"/>
				 </s:elseif>
				  <s:elseif  test="%{colType == \"date\"}">
			 	<sj:datepicker  cssStyle="height: 16px; width: 58px;" cssClass="button"  name="%{key}"  id="%{key}" size="20" maxDate="0"  readonly="true"  changeMonth="true" changeYear="true"  yearRange="1913:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select DOB" />
			 </s:elseif>
			   <s:elseif  test="%{colType == \"dt\"}">
			 	<sj:datepicker  cssStyle="height: 16px; width: 108px;" cssClass="button"  name="%{key}"  id="%{key}" size="20" maxDate="0"  readonly="true"  changeMonth="true" changeYear="true" timepicker="true" yearRange="1913:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Date & Time" />
			 </s:elseif>
			
         </div>
    </div>
    </s:else>      
   </s:iterator>   
   
     <div class="clear"></div>
	 <div class="fields" align="center">
		<ul>
		<li class="submit" style="background: none;">
			<div class="type-button">
			<div id="ButtonDiv">
               <sj:submit 
                       id="onlineSubmitId"
                       targets="resultTarget" 
                       clearForm="true"
                       value="Save" 
                       button="true"
                       disabled="false"
                       onCompleteTopics="makeEffect"
                       cssStyle="margin-left: 53px;"
		            />
		             <sj:a 
						button="true" href="#"
						onclick="resetForm('formPatAdd');"
						>
						Reset
				  </sj:a>
     		  	  <sj:a 
						button="true" href="#"
						onclick="viewActivityBoardCPS();"
						>
						Back
					</sj:a>
		            
		    </div>
		       <div id="resultTarget"></div>
		      </div>
           </li>
		</ul>
	</div>

</s:form>


<!-- -----------------------------------------------------------------------------EHD FIELDS------------------------------------------------------------------------------------ 
 <div id="EHCDiv" style="display:none">
			      <div class="newColumn" >
			  		<div class="leftColumn1">EHC Packages:</div>
	           		<div class="rightColumn1">
				    <span class="needed"></span>
				  
				          <s:select 
                                      id="ehcpkg"
                                      name="ehcpkg"
                                      list=	"#{'std':'Standard','cstm':'Custom'}"
                                      headerKey="-1"
                                      headerValue="Select EHC Packages" 
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
				    </div>
			        </div>
			     

					 <div class="newColumn">
                    <div class="leftColumn1">Packages:</div>
                                <div class="rightColumn1">
                                <span class="needed"></span>
                                <s:textfield name="pkg" id="pkg" maxlength="200"   placeholder="Enter Data" cssStyle="margin:0px 0px 3px 0px;  width: 245px;"  cssClass="textField"/>                
                               
                   </div>
                  </div>
			     
			     <div class="newColumn">
                    <div class="leftColumn1">Booked Date & Time:</div>
                                <div class="rightColumn1">
                                <span class="needed"></span>
                				<sj:datepicker cssStyle="height: 16px; width: 58px;" timepickerOnly="false"  timepicker="true" timepickerAmPm="false" cssClass="button" id="bookDateTimeEHC" name="bookDateTimeEHC" size="20"   readonly="true"   showOn="focus"   Placeholder="Select Time"/>
	     	                   
                   </div>
                  </div>
			     
			     
			      <div class="newColumn" >
			  		<div class="leftColumn1">Service Manager:</div>
	           		<div class="rightColumn1">
				    <span class="needed"></span>
				  
				          <s:select 
                                      id="srvsMngrEHC"
                                      name="srvsMngrEHC"
                                      list=	"#{'a':'A','b':'B','c':'C'}"
                                      headerKey="-1"
                                      headerValue="Select Service Manager" 
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
				    </div>
			        </div>
			     
			     
			     <div class="newColumn">
                    <div class="leftColumn1">EHC Remarks:</div>
                                <div class="rightColumn1">
                                <span class="needed"></span>
                                <s:textfield name="remarksEHC" id="remarksEHC" maxlength="200"   placeholder="Enter Data" cssStyle="margin:0px 0px 3px 0px;  width: 245px;"  cssClass="textField"/>                
                               
                   </div>
                  </div>
			     
			     </div>
		
					     
  -----------------------------------------------------------------------------OPD FIELDS------------------------------------------------------------------------------------ 
			       <div id="OPDDiv" style="display:none">
			       <div class="newColumn" >
			  		<div class="leftColumn1">Specialty:</div>
	           		<div class="rightColumn1">
				    <span class="needed"></span>
				  
				          <s:select 
                                      id="specialtyOPD"
                                      name="specialtyOPD"
                                      list=	"#{'hrear':'Heart','brain':'Brain'}"
                                      headerKey="-1"
                                      headerValue="Select Specialty" 
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
				    </div>
			        </div>
			     

				 <div class="newColumn" >
			  		<div class="leftColumn1">Doctor Name:</div>
	           		<div class="rightColumn1">
				    <span class="needed"></span>
				  
				          <s:select 
                                      id="drNameOPD"
                                      name="drNameOPD"
                                      list=	"#{'a':'Dr.Mehta','b':'Dr.C.P.Joshi','c':'Dr.R.P.Tiwari'}"
                                      headerKey="-1"
                                      headerValue="Select Doctor Name" 
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
				    </div>
			        </div>			     
			     <div class="newColumn">
                    <div class="leftColumn1">Booked Date & Time:</div>
                                <div class="rightColumn1">
                                <span class="needed"></span>
                				<sj:datepicker cssStyle="height: 16px; width: 58px;" timepickerOnly="false"  timepicker="true" timepickerAmPm="false" cssClass="button" id="bookDateTimeOPD" name="bookDateTimeOPD" size="20"   readonly="true"   showOn="focus"   Placeholder="Select Time"/>
	     	                   
                   </div>
                  </div>
			     
			     
			      <div class="newColumn" >
			  		<div class="leftColumn1">Service Manager:</div>
	           		<div class="rightColumn1">
				    <span class="needed"></span>
				  
				          <s:select 
                                      id="srvsMngrOPD"
                                      name="srvsMngrOPD"
                                      list=	"#{'a':'A','b':'B','c':'C'}"
                                      headerKey="-1"
                                      headerValue="Select Service Manager" 
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
				    </div>
			        </div>
			     
			     
			     <div class="newColumn">
                    <div class="leftColumn1">OPD Remarks:</div>
                                <div class="rightColumn1">
                                <span class="needed"></span>
                                <s:textfield name="remarksOPD" id="remarksOPD" maxlength="200"   placeholder="Enter Data" cssStyle="margin:0px 0px 3px 0px;  width: 245px;"  cssClass="textField"/>                
                               
                   </div>
                  </div>
			     </div>
			     
 -----------------------------------------------------------------------------Radiology FIELDS------------------------------------------------------------------------------------ 
			     <div id="RadiologyDiv" style="display:none">
			      <div class="newColumn" >
			  		<div class="leftColumn1">Modality:</div>
	           		<div class="rightColumn1">
				    <span class="needed"></span>
				  
				          <s:select 
                                      id="modality"
                                      name="modality"
                                      list=	"#{'a':'P','b':'Q','c':'R'}"
                                      headerKey="-1"
                                      headerValue="Select Modality" 
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
				    </div>
			        </div>			     
			     <div class="newColumn">
                    <div class="leftColumn1">Booked Date & Time:</div>
                                <div class="rightColumn1">
                                <span class="needed"></span>
                				<sj:datepicker cssStyle="height: 16px; width: 58px;" timepickerOnly="false"  timepicker="true" timepickerAmPm="false" cssClass="button" id="bookDateTimeRadio" name="bookDateTimeRadio" size="20"   readonly="true"   showOn="focus"   Placeholder="Select Time"/>
	     	                   
                   </div>
                  </div>
			     
			     
			      <div class="newColumn" >
			  		<div class="leftColumn1">Service Manager:</div>
	           		<div class="rightColumn1">
				    <span class="needed"></span>
				  
				          <s:select 
                                      id="srvsMngrRadio"
                                      name="srvsMngrRadio"
                                      list=	"#{'a':'A','b':'B','c':'C'}"
                                      headerKey="-1"
                                      headerValue="Select Service Manager" 
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
				    </div>
			        </div>
			     
			     
			     <div class="newColumn">
                    <div class="leftColumn1">Radiology Remarks:</div>
                                <div class="rightColumn1">
                                <span class="needed"></span>
                                <s:textfield name="remarksRadio" id="remarksRadio" maxlength="200"   placeholder="Enter Data" cssStyle="margin:0px 0px 3px 0px;  width: 245px;"  cssClass="textField"/>                
                               
                   </div>
                  </div>
			     
			     </div>
			     
 -----------------------------------------------------------------------------IPD FIELDS------------------------------------------------------------------------------------ 			     
			     <div id="IPDDiv" style="display:none">
			      <div class="newColumn" >
			  		<div class="leftColumn1">Specialty:</div>
	           		<div class="rightColumn1">
				    <span class="needed"></span>
				  
				          <s:select 
                                      id="specialtyIPD"
                                      name="specialtyIPD"
                                      list=	"#{'hrear':'Heart','brain':'Brain'}"
                                      headerKey="-1"
                                      headerValue="Select Specialty" 
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
				    </div>
			        </div>
			     

				 <div class="newColumn" >
			  		<div class="leftColumn1">Doctor Name:</div>
	           		<div class="rightColumn1">
				    <span class="needed"></span>
				  
				          <s:select 
                                      id="drNameIPD"
                                      name="drNameIPD"
                                      list=	"#{'a':'Dr.Mehta','b':'Dr.C.P.Joshi','c':'Dr.R.P.Tiwari'}"
                                      headerKey="-1"
                                      headerValue="Select Doctor Name" 
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
				    </div>
			        </div>			     
			     <div class="newColumn">
                    <div class="leftColumn1">Booked Date & Time:</div>
                                <div class="rightColumn1">
                                <span class="needed"></span>
                				<sj:datepicker cssStyle="height: 16px; width: 58px;" timepickerOnly="false"  timepicker="true" timepickerAmPm="false" cssClass="button" id="bookDateTimeIPD" name="bookDateTimeIPD" size="20"   readonly="true"   showOn="focus"   Placeholder="Select Time"/>
	     	                   
                   </div>
                  </div>
			     
			     
			      <div class="newColumn" >
			  		<div class="leftColumn1">Service Manager:</div>
	           		<div class="rightColumn1">
				    <span class="needed"></span>
				  
				          <s:select 
                                      id="srvsMngrIPD"
                                      name="srvsMngrIPD"
                                      list=	"#{'a':'A','b':'B','c':'C'}"
                                      headerKey="-1"
                                      headerValue="Select Service Manager" 
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
				    </div>
			        </div>
			     
			     
			     <div class="newColumn">
                    <div class="leftColumn1">Remarks:</div>
                                <div class="rightColumn1">
                                <span class="needed"></span>
                                <s:textfield name="remarksIPD" id="remarksIPD" maxlength="200"   placeholder="Enter Data" cssStyle="margin:0px 0px 3px 0px;  width: 245px;"  cssClass="textField"/>                
                               
                   </div>
                  </div>
			     </div>
			     
			     
			      -----------------------------------------------------------------------------Day Care FIELDS------------------------------------------------------------------------------------ 
			     
			     <div id="DayCareDiv" style="display:none">
			        <div class="newColumn" >
			  		<div class="leftColumn1">Specialty:</div>
	           		<div class="rightColumn1">
				    <span class="needed"></span>
				  
				          <s:select 
                                      id="specialtyDayCare"
                                      name="specialtyDayCare"
                                      list=	"#{'hrear':'Heart','brain':'Brain'}"
                                      headerKey="-1"
                                      headerValue="Select Specialty" 
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
				    </div>
			        </div>
			     

				 <div class="newColumn" >
			  		<div class="leftColumn1">Doctor Name:</div>
	           		<div class="rightColumn1">
				    <span class="needed"></span>
				  
				          <s:select 
                                      id="specialtyDayCare"
                                      name="specialtyDayCare"
                                      list=	"#{'a':'Dr.Mehta','b':'Dr.C.P.Joshi','c':'Dr.R.P.Tiwari'}"
                                      headerKey="-1"
                                      headerValue="Select Doctor Name" 
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
				    </div>
			        </div>			     
			     <div class="newColumn">
                    <div class="leftColumn1">Booked Date & Time:</div>
                                <div class="rightColumn1">
                                <span class="needed"></span>
                				<sj:datepicker cssStyle="height: 16px; width: 58px;" timepickerOnly="false"  timepicker="true" timepickerAmPm="false" cssClass="button" id="specialtyDayCare" name="specialtyDayCare" size="20"   readonly="true"   showOn="focus"   Placeholder="Select Time"/>
	     	                   
                   </div>
                  </div>
			     
			     
			      <div class="newColumn" >
			  		<div class="leftColumn1">Service Manager:</div>
	           		<div class="rightColumn1">
				    <span class="needed"></span>
				  
				          <s:select 
                                      id="specialtyDayCare"
                                      name="specialtyDayCare"
                                      list=	"#{'a':'A','b':'B','c':'C'}"
                                      headerKey="-1"
                                      headerValue="Select Service Manager" 
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
				    </div>
			        </div>
			     
			     
			     <div class="newColumn">
                    <div class="leftColumn1">Day Care Remarks:</div>
                                <div class="rightColumn1">
                                <span class="needed"></span>
                                <s:textfield name="specialtyDayCare" id="specialtyDayCare" maxlength="200"   placeholder="Enter Data" cssStyle="margin:0px 0px 3px 0px;  width: 245px;"  cssClass="textField"/>                
                               
                   </div>
                  </div>
			     </div>
			     
 -----------------------------------------------------------------------------Laboratry FIELDS------------------------------------------------------------------------------------ 			     
			     <div id="LaboratryDiv" style="display:none">
			     <div class="newColumn" >
			  		<div class="leftColumn1">Modality:</div>
	           		<div class="rightColumn1">
				    <span class="needed"></span>
				  
				          <s:select 
                                      id="laboratory"
                                      name="laboratory"
                                      list=	"#{'a':'P','b':'Q','c':'R'}"
                                      headerKey="-1"
                                      headerValue="Select Modality" 
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
				    </div>
			        </div>			     
			     <div class="newColumn">
                    <div class="leftColumn1">Booked Date & Time:</div>
                                <div class="rightColumn1">
                                <span class="needed"></span>
                				<sj:datepicker cssStyle="height: 16px; width: 58px;" timepickerOnly="false"  timepicker="true" timepickerAmPm="false" cssClass="button" id="bookDateTimeLab" name="bookDateTimeLab" size="20"   readonly="true"   showOn="focus"   Placeholder="Select Time"/>
	     	                   
                   </div>
                  </div>
			     
			     
			      <div class="newColumn" >
			  		<div class="leftColumn1">Service Manager:</div>
	           		<div class="rightColumn1">
				    <span class="needed"></span>
				  
				          <s:select 
                                      id="srvsMngrLab"
                                      name="srvsMngrLab"
                                      list=	"#{'a':'A','b':'B','c':'C'}"
                                      headerKey="-1"
                                      headerValue="Select Service Manager" 
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
				    </div>
			        </div>
			     
			     
			     <div class="newColumn">
                    <div class="leftColumn1">Laboratry Remarks:</div>
                                <div class="rightColumn1">
                                <span class="needed"></span>
                                <s:textfield name="remarksLab" id="remarksLab" maxlength="200"   placeholder="Enter Data" cssStyle="margin:0px 0px 3px 0px;  width: 245px;"  cssClass="textField"/>                
                               
                   </div>
                  </div>
			     </div>
			     
			     
 -----------------------------------------------------------------------------Emergency FIELDS------------------------------------------------------------------------------------ 			     
			     <div id="EmergencyDiv" style="display:none">
			      <div class="newColumn" >
			  		<div class="leftColumn1">Specialty:</div>
	           		<div class="rightColumn1">
				    <span class="needed"></span>
				  
				          <s:select 
                                      id="specialtyEmrgncy"
                                      name="specialtyEmrgncy"
                                      list=	"#{'hrear':'Heart','brain':'Brain'}"
                                      headerKey="-1"
                                      headerValue="Select Specialty" 
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
				    </div>
			        </div>
			     

				 <div class="newColumn" >
			  		<div class="leftColumn1">Doctor Name:</div>
	           		<div class="rightColumn1">
				    <span class="needed"></span>
				  
				          <s:select 
                                      id="specialtyEmrgncy"
                                      name="specialtyEmrgncy"
                                      list=	"#{'a':'Dr.Mehta','b':'Dr.C.P.Joshi','c':'Dr.R.P.Tiwari'}"
                                      headerKey="-1"
                                      headerValue="Select Doctor Name" 
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
				    </div>
			        </div>			     
			     <div class="newColumn">
                    <div class="leftColumn1">Booked Date & Time:</div>
                                <div class="rightColumn1">
                                <span class="needed"></span>
                				<sj:datepicker cssStyle="height: 16px; width: 58px;" timepickerOnly="false"  timepicker="true" timepickerAmPm="false" cssClass="button" id="specialtyEmrgncy" name="specialtyEmrgncy" size="20"   readonly="true"   showOn="focus"   Placeholder="Select Time"/>
	     	                   
                   </div>
                  </div>
			     
			     
			      <div class="newColumn" >
			  		<div class="leftColumn1">Service Manager:</div>
	           		<div class="rightColumn1">
				    <span class="needed"></span>
				  
				          <s:select 
                                      id="specialtyEmrgncy"
                                      name="specialtyEmrgncy"
                                      list=	"#{'a':'A','b':'B','c':'C'}"
                                      headerKey="-1"
                                      headerValue="Select Service Manager" 
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
				    </div>
			        </div>
			     
			        <div class="newColumn">
                    <div class="leftColumn1">Emergency Remarks:</div>
                                <div class="rightColumn1">
                                <span class="needed"></span>
                                <s:textfield name="specialtyEmrgncy" id="specialtyEmrgncy" maxlength="200"   placeholder="Enter Data" cssStyle="margin:0px 0px 3px 0px;  width: 245px;"  cssClass="textField"/>                
                               
                   </div>
                  </div>
                  </div>
			       
	
--></div>
</div>
</body>
</html>