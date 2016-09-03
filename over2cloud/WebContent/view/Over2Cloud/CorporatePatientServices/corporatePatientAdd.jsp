<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<SCRIPT type="text/javascript" src="js/commanValidation/validation.js"></SCRIPT>
<script type="text/javascript" src="js/hr/commonHr.js"></script>
<script  type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<SCRIPT type="text/javascript">
$(document).ready(function()
		 {
		 	$("#service").multiselect({
		 	   show: ["", 200],
		 	   hide: ["explode", 1000],
		 	});
		 	 
		 });
 
function reset(formId)
{
    $('#'+formId).trigger("reset");
    //document.getElementById('result').style.display='none';
      setTimeout(function(){ $("#result").fadeOut(); }, 4000);
}

$.subscribe('level1', function(event,data)
           {
             setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
             setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
           });


 
function toTitleCase(str,id)
{
    document.getElementById(id).value=str.replace(/\w\S*/g, function(txt){return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();});
}

function getServiceType(serviceId)
{
	var serviceType1='1';
	var serviceType=$("#"+serviceId).val();
	if(serviceType.length>1)
	  serviceType1=serviceType.split(",");
	 
	 for(var a=1;a<=serviceType1.length;a++)
	{
	  if(serviceType=='EHC')
	  {
		 	showHideFeedDiv('EHCDiv','RadiologyDiv','IPDDiv','DayCareDiv','LaboratoryDiv','EmergencyDiv','OPDDiv');
	  }
	 else if(serviceType=='OPD')
	  {
		 showHideFeedDiv('OPDDiv','RadiologyDiv','IPDDiv','DayCareDiv','LaboratoryDiv','EmergencyDiv','EHCDiv');  }
	 else if(serviceType=='Radiology')
	  {
		 showHideFeedDiv('RadiologyDiv','EHCDiv','IPDDiv','DayCareDiv','LaboratoryDiv','EmergencyDiv','OPDDiv');  }	  
	 else if(serviceType=='IPD')
	  {
		 showHideFeedDiv('IPDDiv','RadiologyDiv','EHCDiv','DayCareDiv','LaboratoryDiv','EmergencyDiv','OPDDiv');  }	  
	 else if(serviceType=='Day Care')
	  {
		 showHideFeedDiv('DayCareDiv','RadiologyDiv','IPDDiv','EHCDiv','LaboratoryDiv','EmergencyDiv','OPDDiv');  }	  
	 else if(serviceType=='Laboratory')
	  {
		 showHideFeedDiv('LaboratoryDiv','RadiologyDiv','IPDDiv','DayCareDiv','EHCDiv','EmergencyDiv','OPDDiv');  }	  
	 else if(serviceType=='Emergency')
	  {
		 showHideFeedDiv('EmergencyDiv','RadiologyDiv','IPDDiv','DayCareDiv','LaboratoryDiv','EHCDiv','OPDDiv'); }	  
	}
}
function showHideFeedDiv(showDiv,hideDiv1,hideDiv2,hideDiv3,hideDiv4,hideDiv5,hideDiv6)
{
	if($("#"+showDiv).css('display') == 'none')
		$("#"+showDiv).show('slow');
	 
	if($("#"+hideDiv1).css('display') != 'none')
		$("#"+hideDiv1).hide('slow');

	if($("#"+hideDiv2).css('display') != 'none')
	$("#"+hideDiv2).hide('slow');
	
    if($("#"+hideDiv3).css('display') != 'none')
	$("#"+hideDiv3).hide('slow');

    if($("#"+hideDiv4).css('display') != 'none')
        $("#"+hideDiv4).hide('slow');
    
    if($("#"+hideDiv5).css('display') != 'none')
        $("#"+hideDiv5).hide('slow');
    
    if($("#"+hideDiv6).css('display') != 'none')
        $("#"+hideDiv6).hide('slow');
}
</script>
</head>
<body>
<div class="list-icon">
     <div class="head">Corporate Patient</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div> 
</div>
<div class="clear"></div>
     <div style="width: 100%; center; padding-top: 10px;">
       <div class="border" style="height: 50%">
   	<s:form id="formone" name="formone" namespace="/view/Over2Cloud/hr" action="addGroup" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
          <center>
            <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
            <div id="errZone" style="float:center; margin-left: 7px"></div>        
            </div>
          </center>
             
                 <div class="newColumn">
                                <div class="leftColumn1">From Account Manager:</div>
                                <div class="rightColumn1">
                 				 <span class="needed"></span>
                          <s:select 
                                      id="accntMngr"
                                      name="accntMngr"
                                      list=	"#{'jay':'Jay','raj':'Raj','aashu':'Aashu'}"
	 								  headerKey="-1"
                                      headerValue="Select Account Manager" 
                                      cssClass="textField"
                                        cssStyle="width:82%"
                                      onchange=""
                                      >
                          </s:select>
                    </div>
                    </div>
                 
			       	<div class="newColumn" >
			  		<div class="leftColumn1">Corporate Name:</div>
	           		<div class="rightColumn1">
				    <span class="needed"></span>
				  
				    <s:select 
                                      id="corptName"
                                     name="corptName"
                                      headerKey="-1"
                                      headerValue="Select Corporate Name" 
                                      list=	"#{'a':'KPMG','b':'Microsoft','c':'PWC','d':'IBM'}"
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      onchange=""
                                      >
                   </s:select>
				    </div>
			        </div>
			        
			        
			        <div class="newColumn" >
			  		<div class="leftColumn1">Corporate Type:</div>
	           		<div class="rightColumn1">
				    <span class="needed"></span>
				  
				          <s:select 
                                      id="corptType"
                                      name="corptType"
                                      list=	"#{'x':'X','y':'Y','z':'Z'}"
                                      headerKey="-1"
                                      headerValue="Select Corporate Type" 
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
				    </div>
			        </div>
			        
			         <div class="newColumn" >
			  		<div class="leftColumn1">Status:</div>
	           		<div class="rightColumn1">
				    <span class="needed"></span>
				  
				          <s:select 
                                      id="status"
                                      name="status"
                                      list=	"#{'stnd':'Standard','vvip':'VVIP','prt':'Priority','other':'Other'}"
                                      headerKey="-1"
                                      headerValue="Select Status" 
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
				    </div>
			        </div>
			        
			         <div class="newColumn" >
			  		<div class="leftColumn1">Patient Name:</div>
	           		<div class="rightColumn1">
				    <span class="needed"></span>
				  
				          <s:select 
                                      id="patientName"
                                      name="patientName"
                                      list=	"#{ 'a':'Vijay','b':'Ajay','c':'Kumar','d':'Ramesh'}"
                                      headerKey="-1"
                                      headerValue="Select Patient Name" 
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
				    </div>
			        </div>
			       
			       
			        <div class="newColumn">
                    <div class="leftColumn1">UHID:</div>
                                <div class="rightColumn1">
                                <span class="needed"></span>
                                <s:textfield name="uhid" id="uhid" maxlength="200"   placeholder="Enter Data" cssStyle="margin:0px 0px 3px 0px;  width: 366px;"  cssClass="textField"/>                
                               
                   </div>
                  </div>
                  
                   <div class="newColumn">
                    <div class="leftColumn1">Mobile No.:</div>
                                <div class="rightColumn1">
                                <span class="needed"></span>
                                <s:textfield name="mbl" id="mbl" maxlength="200"   placeholder="Enter Data" cssStyle="margin:0px 0px 3px 0px;  width: 366px;"  cssClass="textField"/>                
                               
                   </div>
                  </div>
                  
                  <div class="newColumn">
                    <div class="leftColumn1">Email ID:</div>
                                <div class="rightColumn1">
                                <span class="needed"></span>
                                <s:textfield name="email" id="email" maxlength="200"   placeholder="Enter Data" cssStyle="margin:0px 0px 3px 0px;  width: 366px;"  cssClass="textField"/>                
                               
                   </div>
                  </div>
                  
                  <div class="newColumn">
                    <div class="leftColumn1">DOB:</div>
                                <div class="rightColumn1">
                                <span class="needed"></span>
                                <sj:datepicker  cssStyle="height: 16px; width: 58px;" cssClass="button" id="dob" name="dob" size="20" maxDate="0"  readonly="true"   yearRange="2013:2050" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select DOB" />
	                        
                   </div>
                  </div>
                  
                    <div class="newColumn" >
			  		<div class="leftColumn1">Gender:</div>
	           		<div class="rightColumn1">
				    <span class="needed"></span>
				  
				          <s:select 
                                      id="gender"
                                      name="gender"
                                      list=	"#{'male':'Male','female':'Female','other':'Other'}"
                                      headerKey="-1"
                                      headerValue="Select Gender" 
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
				    </div>
			        </div>
			        
			        <div class="newColumn" >
			  		<div class="leftColumn1">State:</div>
	           		<div class="rightColumn1">
				    <span class="needed"></span>
				  
				          <s:select 
                                      id="state"
                                      name="state"
                                      list=	"#{'rjn':'Rajasthan','up':'U.P','mp':'M.P'}"
                                      headerKey="-1"
                                      headerValue="Select State" 
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
				    </div>
			        </div>
			        
			        <div class="newColumn" >
			  		<div class="leftColumn1">City:</div>
	           		<div class="rightColumn1">
				    <span class="needed"></span>
				  
				          <s:select 
                                      id="city"
                                      name="city"
                                      list=	"#{'dhl':'Dehli','klt':'Kolkata','chn':'Chennai'}"
                                      headerKey="-1"
                                      headerValue="Select City" 
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
				    </div>
			        </div>
			        
			        
			        <div class="newColumn" >
			  		<div class="leftColumn1">Country:</div>
	           		<div class="rightColumn1">
				    <span class="needed"></span>
				  
				          <s:select 
                                      id="contury"
                                      name="contury"
                                      list=	"#{'ind':'India','usa':'USA','france':'France'}"
                                      headerKey="-1"
                                      headerValue="Select Country" 
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
				    </div>
			        </div>
			        
			         <div class="newColumn" >
			  		<div class="leftColumn1">Sevices:</div>
	           		<div class="rightColumn1">
				    <span class="needed"></span>
				           <s:select 
                                      id="service"
                                      name="service"
                                      list=	"#{'EHC':'EHC','OPD':'OPD','Radiology':'Radiology','IPD':'IPD','Day Care':'Day Care','Laboratory':'Laboratory','Emergency':'Emergency'}"
                                      cssStyle	="height:50%"
                                      multiple="true"
                                      onchange="getServiceType('service')"
                                        
                                      >
                          </s:select>
				    </div>
			        </div>
			        
			        
			        
			     
			         
			     
			     
			  
			     
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
                                <s:textfield name="pkg" id="pkg" maxlength="200"   placeholder="Enter Data" cssStyle="margin:0px 0px 3px 0px;  width: 366px;"  cssClass="textField"/>                
                               
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
                    <div class="leftColumn1">Remarks:</div>
                                <div class="rightColumn1">
                                <span class="needed"></span>
                                <s:textfield name="remarksEHC" id="remarksEHC" maxlength="200"   placeholder="Enter Data" cssStyle="margin:0px 0px 3px 0px;  width: 366px;"  cssClass="textField"/>                
                               
                   </div>
                  </div>
			     
			     </div>
			     
			     
			     
			     
			     
			     
			     
			     
			     
			     
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
                    <div class="leftColumn1">Remarks:</div>
                                <div class="rightColumn1">
                                <span class="needed"></span>
                                <s:textfield name="remarksOPD" id="remarksOPD" maxlength="200"   placeholder="Enter Data" cssStyle="margin:0px 0px 3px 0px;  width: 366px;"  cssClass="textField"/>                
                               
                   </div>
                  </div>
			     </div>
			     
			     
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
                    <div class="leftColumn1">Remarks:</div>
                                <div class="rightColumn1">
                                <span class="needed"></span>
                                <s:textfield name="remarksRadio" id="remarksRadio" maxlength="200"   placeholder="Enter Data" cssStyle="margin:0px 0px 3px 0px;  width: 366px;"  cssClass="textField"/>                
                               
                   </div>
                  </div>
			     
			     </div>
			     
			     
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
                                <s:textfield name="remarksIPD" id="remarksIPD" maxlength="200"   placeholder="Enter Data" cssStyle="margin:0px 0px 3px 0px;  width: 366px;"  cssClass="textField"/>                
                               
                   </div>
                  </div>
			     </div>
			     
			     
			     
			     
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
                    <div class="leftColumn1">Remarks:</div>
                                <div class="rightColumn1">
                                <span class="needed"></span>
                                <s:textfield name="specialtyDayCare" id="specialtyDayCare" maxlength="200"   placeholder="Enter Data" cssStyle="margin:0px 0px 3px 0px;  width: 366px;"  cssClass="textField"/>                
                               
                   </div>
                  </div>
			     </div>
			     
			     
			     <div id="LaboratoryDiv" style="display:none">
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
                    <div class="leftColumn1">Remarks:</div>
                                <div class="rightColumn1">
                                <span class="needed"></span>
                                <s:textfield name="remarksLab" id="remarksLab" maxlength="200"   placeholder="Enter Data" cssStyle="margin:0px 0px 3px 0px;  width: 366px;"  cssClass="textField"/>                
                               
                   </div>
                  </div>
			     </div>
			     
			     
			     
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
                    <div class="leftColumn1">Remarks:</div>
                                <div class="rightColumn1">
                                <span class="needed"></span>
                                <s:textfield name="specialtyEmrgncy" id="specialtyEmrgncy" maxlength="200"   placeholder="Enter Data" cssStyle="margin:0px 0px 3px 0px;  width: 366px;"  cssClass="textField"/>                
                               
                   </div>
                  </div>
                  </div>
			       
			     
			     
			     
			     
			       <div class="newColumn" >
			  		<div class="leftColumn1">Medanta Location:</div>
	           		<div class="rightColumn1">
				    <span class="needed"></span>
				  
				          <s:select 
                                      id="location"
                                      name="location"
                                      list=	"#{'dhl':'Dehli','noida':'Noida','grgnv':'Gurgaon'}"
                                      headerKey="-1"
                                      headerValue="Select Medanta Location" 
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
				    </div>
			        </div>
			        
			        
			        <div class="newColumn">
                    <div class="leftColumn1">Day & Time:</div>
                                <div class="rightColumn1">
                                <span class="needed"></span>
                				<sj:datepicker cssStyle="height: 16px; width: 58px;" timepickerOnly="true" timepicker="true" timepickerAmPm="false" cssClass="button" id="time" name="time" size="20"   readonly="true"   showOn="focus"   Placeholder="Select Time"/>
	     	                   
                   </div>
                  </div>
                  
                  <div class="newColumn">
                    <div class="leftColumn1">Remarks:</div>
                                <div class="rightColumn1">
                                <span class="needed"></span>
                                <s:textfield name="remarks" id="remarks" maxlength="200"   placeholder="Enter Data" cssStyle="margin:0px 0px 3px 0px;  width: 366px;"  cssClass="textField"/>                
                               
                   </div>
                  </div>
			        
			     
			     
			     
                <center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
                <div class="clear" >
                <div style="padding-bottom: 10px;margin-left:-80px" align="center">
                        <sj:submit 
                             targets="level123" 
                             clearForm="true"
                             value="Save" 
                             effect="highlight"
                             effectOptions="{ color : '#222222'}"
                             effectDuration="5000"
                             button="true" 
                              onBeforeTopics="validate"
                             onCompleteTopics="level1"
                        />
                        &nbsp;&nbsp;
                        <sj:submit 
                             value="Reset" 
                             button="true"
                             cssStyle="margin-left: 139px;margin-top: -43px;"
                             onclick="reset('formone');resetColor('.pIds');"
                        />&nbsp;&nbsp;
                        <sj:a
                            cssStyle="margin-left: 276px;margin-top: -58px;"
                        button="true" href="#" value="View" onclick="" cssStyle="margin-left: 266px;margin-top: -74px;" 
                        >Back
                    </sj:a>
                    </div>
               </div>
               <sj:div id="orglevel1Div"  effect="fold">
                   <sj:div id="level123">
                   </sj:div>
               </sj:div>
   </s:form>          
</div>
</div>
</body>
</html>