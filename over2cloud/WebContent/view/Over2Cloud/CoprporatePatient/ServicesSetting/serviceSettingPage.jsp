<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Service </div><img alt="" src="images/forward.png" style="margin-top:10px; float: left;"><div class="head"> Configuration</div> 
				
	          	 
</div>
<div class="clear"></div>
<div style="overflow:auto; height:auto; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">

<s:form id="formSetting" name="formSetting" action="serviceSettingAdd"  theme="simple"  method="post" enctype="multipart/form-data">
	<center>
			<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 6px;">
	             <div id="errZone" style="float:center; margin-left: 7px"></div>        
	        </div>
    </center>
   
    <div class="newColumn">
	     <div class="leftColumn1">Field Name:&nbsp;</div>
		 <div class="rightColumn1">
			
             	<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" onchange="Reset('.pIds');"/>
         </div>
    </div>
    
      <div class="newColumn">
	     <div class="leftColumn1">Field Type:&nbsp;</div>
		 <div class="rightColumn1">
			
             	<s:select name="fieldType"   list="#{'T':'Text','D':'Drop Down','RT':'Rich Text','CB':'Check Box','R':'Radio'}" id="fieldtype"  cssClass="textField" theme="simple"  />
         </div>
    </div>
     <div class="newColumn">
	     <div class="leftColumn1">Validation Type:&nbsp;</div>
		 <div class="rightColumn1">
			
             	<s:select name="valType"   list="#{'a':'Aphabates Only','an':'Alphanumeric','m':'Mobile Number ','e':'Email Id'}" id="valType"  cssClass="textField" theme="simple"  />
         </div>
    </div>
     <div class="newColumn">
	     <div class="leftColumn1">Mandatory:&nbsp;</div>
		 <div class="rightColumn1">
			
             <s:select name="mandatory"   list="#{'1':'Yes','2':'No'}" id="valType"  cssClass="textField" theme="simple"  />
         </div>
    </div>
    
   
   
   
       
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
						onclick="resetForm('formone');"
						>
						Reset
				  </sj:a>
     		  	  <sj:a 
						button="true" href="#"
						onclick="back();"
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


<!-- -----------------------------------------------------------------------------EHD FIELDS------------------------------------------------------------------------------------ -->
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
			     
 <!-- -----------------------------------------------------------------------------OPD FIELDS------------------------------------------------------------------------------------ -->
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
			     
<!-- -----------------------------------------------------------------------------Radiology FIELDS------------------------------------------------------------------------------------ -->
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
			     
<!-- -----------------------------------------------------------------------------IPD FIELDS------------------------------------------------------------------------------------ -->			     
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
			     
			     
			     <!-- -----------------------------------------------------------------------------Day Care FIELDS------------------------------------------------------------------------------------ -->
			     
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
			     
<!-- -----------------------------------------------------------------------------Laboratry FIELDS------------------------------------------------------------------------------------ -->			     
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
			     
			     
<!-- -----------------------------------------------------------------------------Emergency FIELDS------------------------------------------------------------------------------------ -->			     
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
			       
	
</div>
</div>
</body>
</html>