<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<html>
<head>

<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/cpservice/servicesAction.js"/>"></script>
<script type="text/javascript">

$.subscribe('makeEffect', function(event,data)
	  {
	 setTimeout(function(){ $("#orglevel12").fadeIn(); }, 10);
	    setTimeout(function(){ $("#orglevel12").fadeOut(); }, 1000);
	    sussessMessage();
	  });
function sussessMessage()
{
	 delay(function()
	{
	 $("#cpsServicesIdd").dialog('close');
	 newEntryCPS();
	 corporatePatientView('load');
	    }, 1000 );
}
var delay = (function(){
	  var timer = 0;
	  return function(callback, ms){
	    clearTimeout (timer);
	    timer = setTimeout(callback, ms);
	  };
	})();
function cancelButton()
{
	 $("#cpsServicesIdd").dialog('close');
	 newEntryCPS();
	 corporatePatientView('load');
}
function resetForm(formId)
{
	$('#'+formId).trigger("reset");
} 
var locationName;
serviceFieldsss();
function serviceFieldsss()
{
	locationName=$("#location").val();
}
function accountManager(bdate, divId)
{ 
	var date=bdate.substring(0,2);
	$.ajax({
	    type : "post",
	    async:false,
	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/fetchAccountManager.action?id = "+locationName+"&date="+date+"&services="+$("#service_id").val(),
	    success : function(data) {
	    if(data != null){
	    $('#'+divId+' option').remove();
	  $('#'+divId).append($("<option></option>").attr("value",-1).text('Select Service Manager'));
	   $(data).each(function(index)
	   {
	        $('#'+divId).append( $("<option></option>").attr("value",this.id).text(this.Name) );
	  });
	}
	    
	},
	   error: function() {
          alert("error");
      }
	 });
}

function packageGetAction(pack, div){
	var abc;
if(pack=='Custom')
{
	 madLoc =  $("#corpNameID").val();
	 abc='madLoc';
}
else
{
	 madLoc =  $("#madLocmm").val();
	 abc='med_location';
}
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/fetchPackageData.action?"+abc+"="+madLoc+"&pack="+pack,
	    success : function(data) {
	    //console.log(data);
	$('#EHC_packagesA_service option').remove();
	$('#EHC_packagesA_service').append($("<option></option>").attr("value",$("#keyExistSec").val()).text($("#keyExistSec").val()));
    	$(data).each(function(index)
	{
	   $('#EHC_packagesA_service').append($("<option></option>").attr("value",this.id).text(this.Name));
	});
	
	},
	   error: function() {
            alert("error");
        }
	 });
} 
//validation for text counter.
function textCounter(field, countfield, maxlimit,service_remark_opd) {    //162    160
	//alert("Field "+field+" countfield "+countfield+"  maxlimit "+maxlimit+" check "+service_remark_opd);
	//service_remark_opd
	//alert(field.length);
	if (field.length > 0) {
		var a = field;
		//alert("a "+a);
		//console.log(a);
		var iChars = "!@$%^&*()+=-[]';,{}|\":?";
		for ( var i = 0; i < a.length; i++) 
		  {
			if (iChars.indexOf(a.charAt(i)) != -1) 
			{
				errZone.innerHTML = "<div class='user_form_inputError2'>Only alphabets are allowed. </div>";
				//document.formtwo.writeMessage.value = "";
			} 
			else 
			{
				errZone.innerHTML="";
		    }
		  }
	   }
	   
	if(field.length <= maxlimit)
	{
		countfield.value = field.length;
	}
	else
	{
		var a = Math.floor(field.value.length / maxlimit);
		var b = maxlimit * a;
		var c = field.value.length - b;
	    
		countfield.value = c;
		countfield.value += "/";
		countfield.value += Math.floor(field.value.length / maxlimit);
		//alert(">>>>"+field.value);
	}
	//alert(field.value.length );
	if (field.value.length == maxlimit) 
	{
		alert("New Message started!");
	}
}
</script>
<script type="text/javascript">

</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="clear"></div>
<div style=" height:auto; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
<table border="1" width="100%" style="border-collapse: collapse;">
	<tr bgcolor="#D8D8D8" style="height: 25px">
	<s:iterator value="dataMap">
	<s:if test="key=='Ticket No' || key=='Corporate Name' || key=='Patient Name'">
	<td width="10%"><b><s:property value="%{key}"/> <b></td>
	<td width="10%"><s:property value="%{value}"/></td>
	</s:if>
	</s:iterator>
	</tr>
	
	<tr  style="height: 25px">
	<s:iterator value="dataMap">
	<s:if test="key=='Patient Type' || key=='Patient Mobile' || key=='Patient Email'">
	<td width="10%"><b><s:property value="%{key}"/> <b></td>
	<td width="10%"><s:property value="%{value}"/></td>
	</s:if>
	</s:iterator>
	</tr>
	
	<tr bgcolor="#D8D8D8" style="height: 25px">
	<s:iterator value="dataMap">
	<s:if test="key=='Service' || key=='Location' || key=='Preferred Schedule'">
	<td width="10%"><b><s:property value="%{key}"/> <b></td>
	<td width="10%"><s:property value="%{value}"/></td>
	</s:if>
	</s:iterator>
	</tr>
	</table>

	<s:form id="serviceForm" name="formPatAdd" action="addServices"  theme="simple"  method="post" enctype="multipart/form-data">
	 <s:hidden id="medName" value="%{med_location}"></s:hidden>
	 <s:hidden id="corpNameID" value="%{corp_name}"></s:hidden>
	 <s:hidden id="madLoc" value="%{madLoc}"></s:hidden>
	<center>
	<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 6px;">
	             <div id="errZone" style="float:center; margin-left: 7px"></div>        
	        </div>
    </center>
    <s:iterator value="dataMap">
	<s:if test="key=='Corporate Name'">
	<s:hidden id="madLoc"  name="madLoc" value="%{value}" > </s:hidden>
	</s:if>
	</s:iterator>
	<s:iterator value="dataMap">
	<s:if test="key=='Location'">
	<s:hidden id="Loc"  name="Loc" value="%{value}" > </s:hidden>
	</s:if>
	</s:iterator>
	
	
	<s:iterator value="dataMap">
	<s:if test="key=='Patient Type'">
	<s:hidden id="Patient_Type"  name="Patient_Type" value="%{value}" > </s:hidden>
	</s:if>
	</s:iterator>
	
	<s:iterator value="dataMap">
	<s:if test="key=='Preferred Schedule' ">
	<s:hidden id="pefTime"   value="%{value}" > </s:hidden>
	</s:if>
	</s:iterator>
	<s:iterator value="dataMap">
	<s:if test="key=='Ticket No' ">
	<s:hidden name="ticketNo"   value="%{value}" > </s:hidden>
	</s:if>
	<s:if test="key=='Patient Mobile'">
	<s:hidden name="pat_mobile"   value="%{value}" > </s:hidden>
	</s:if>
	<s:if test="key=='Patient Name'">
	<s:hidden name="pat_name"   value="%{value}" > </s:hidden>
	</s:if>
	<s:if test="key=='Service'">
	<s:hidden name="services"   value="%{value}" > </s:hidden>
	</s:if>
	<s:if test="key=='Patient Email'">
	<s:hidden name="pat_email"   value="%{value}" > </s:hidden>
	</s:if>
	</s:iterator>
	<s:hidden id="keyExist"   value="%{keyExist}" > </s:hidden>
	<s:hidden id="valueExist"   value="%{valueExist}" > </s:hidden>
   	<s:hidden id="keyExistSec"   value="%{keyExistSec}" > </s:hidden>
	<s:hidden id="valueExistSec"   value="%{valueExistSec}" > </s:hidden>
	<s:hidden id="madLocmm"   value="%{madLoc}" > </s:hidden>
	<s:hidden id="service_id"   value="%{service_id}" > </s:hidden>
   	<div class="newColumn" >
	<div class="leftColumn">Take Action:</div>
    <div class="rightColumn">
	<span id="validSec" class="pIdsService" style="display: none; ">actionmap#Take Action#D#D,</span>
    <span class="needed"></span>
	<s:select 
    id="actionmap"
    name="action"
    list="#{'A':'Assign','C':'Close', 'P':'Parked'}"
    headerKey="-1"
    headerValue="Select Action" 
    cssClass="select"
    cssStyle="width:75%"
	theme="simple"
	onchange="actionLoad(this.value)"/>
	</div>
    </div>
	<div id="serviceID" style="display:none">
 	

 	<div class="newColumn">
	    <div class="leftColumn">Location:</div>
        <div class="rightColumn"><span class="needed"></span> 
        <span id="locationSpan" class="pIdsService" style="display: none; "></span>
        <s:select 
	    name="location"  
	    id="location" 
	list="#{'-1':'No Data'}"   
	   	headerKey="%{madLoc}"
	headerValue="%{madLoc}" 
	   	cssClass="textField" 
	   	cssStyle="width:82%"
	   	theme="simple" 
	   	placeholder="Enter Data"
	   	onchange="serviceFieldsss()"/>
    </div>
	</div>
	</div>
    <div id="assign" style="display:none">
	<div id="addField"></div>
	<s:hidden id="serviceFiledNameId" name="serviceFiledNameId" value="%{serviceName}"></s:hidden>
	<s:hidden id="serviceFiled" name="serviceFiled" value="%{service_id}"></s:hidden>
	<s:hidden name="cps_id" value="%{id}"></s:hidden>
	</div>
	<div id="close" style="display:none">
	<div class="newColumn" >
    <div class="leftColumn">Reason:</div>
    <div class="rightColumn">
    <span id="reasonAll" class="pIdsService" style="display: none; "></span>
    <span class="needed"></span>
    <s:textfield name="reason" id="reason" maxlength="200"   placeholder="Enter Data" onkeyup="textCounter(this.value,this.form.remLen,160,'reason')" cssStyle="margin:0px 0px 3px 0px;  width: 73%;"  cssClass="textField"/>  
    </div>
   </div>
	</div>

	<div id="park" style="display:none">
	<div class="newColumn" >
    <div class="leftColumn">Parked Till:</div>
    <div class="rightColumn">
    <span id="reasonParkTill" class="pIdsService" style="display: none; "></span>
    <span class="needed" ></span>
   	<sj:datepicker name="parked_till" id="parked_till"   timepickerOnly="false" minDate="0"  timepicker="true" timepickerAmPm="false" Class="button"  size="15" displayFormat="dd-mm-yy"  readonly="true"   showOn="focus"   Placeholder="Select Date and Time"/>
   	</div>
  	</div>
  	<div class="newColumn" >
    <div class="leftColumn">Park Reason:</div>
    <div class="rightColumn">
    <span id="reasonPark" class="pIdsService" style="display: none; "></span>
    <span class="needed"></span>
    <s:textfield name="reason" id="reasonP" maxlength="200"   placeholder="Enter Data" cssStyle="margin:0px 0px 3px 0px;  width: 73%;" onkeyup="textCounter(this.value,this.form.remLen,160,'reasonP')" cssClass="textField"/>  
    </div>
   </div>
	</div>
	<div id="checkSMS" style="display: none">
	 <div class="newColumn" align="center">
		<div class="leftColumn" ><b>Patient Intemation:</b></div>
		<div class="rightColumn"  align="center">
		<s:radio list="#{'1':'Yes','2':'No' }" name="sendSMS" id="sendSMS" value="1"  theme="simple"/>
		</div>
	</div>
	</div>
	
	
	
	<div class="clear"></div>
	<div class="fields" align="center">
	<ul>
	<li class="submit" style="background: none;">
	<div class="type-button">
	<div id="ButtonDiv">
    <sj:submit 
   	id="onlineSubmitIdd"
	targets="resultTarget" 
	clearForm="true"
	value="Save" 
 	button="true"
	effectOptions="{ color : '#222222'}"
	effectDuration="400"
	onCompleteTopics="makeEffect"
	cssStyle="margin-left: 53px;"
	onBeforeTopics="validateService"/>
	<sj:a 
	button="true" href="#"
	onclick="resetForm('serviceForm');">Reset</sj:a>
	</div>
	<sj:div id="orglevel12"  effect="fold">
                   <sj:div  id="resultTarget"></sj:div></sj:div>
	<br>
	</div>
	</li>
	</ul>  
	</div>
	<b>Note: Enter remarks atleast 50 character: &nbsp;&nbsp;&nbsp;&nbsp;<input readonly type="text" id="remLen" size=3 maxlength="3" value="0" style="border:1px solid #e2e9ef;border-top:1px solid #cbd3e2;outline:medium none;padding:2px;margin-top: 16PX;"></b>
</s:form>
 <div id="EHCDiv" style="display:none">
	      
	       <div class="newColumn" >
	  	<div class="leftColumn">EHC Packages:</div>
	           	<div class="rightColumn">
	    <span class="needed"></span>
	  	<span id="EHCvalidationService" class="pIdsService" style="display: none; "></span>
                               <span class="needed"></span>
	          <s:select 
                                      id="EHC_package_type_service"
                                      name="ehc_pack_type"
                                      list=	"corpMap"
                                       headerKey="%{keyExist}"
                                      headerValue="%{valueExist}" 
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                      onchange="packageGetAction(this.value, 'EHC_packagesA_service');"
                                      >
                          </s:select>
	    </div>
	        </div>
	     

	 <div class="newColumn">
                    <div class="leftColumn">Packages:</div>
                                <div class="rightColumn"  >
                                <span class="needed"></span>
                                <span id="EHCvalidationService1" class="pIdsService" style="display: none; "></span>
 	<s:select 
                                      id="EHC_packagesA_service"
                                      name="ehc_pack"
                                     list=	"#{'No Data':'No Data'}"
                                       headerKey="%{keyExistSec}"
                                      headerValue="%{valueExistSec}" 
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                     
                                      >
                          </s:select>
                   </div>
                  </div>
	     
	     <div class="newColumn">
                    <div class="leftColumn">Booked Schedule:</div>
                                <div class="rightColumn">
                                 <span class="needed" ></span>
                              <span id="bookEHC" class="pIdsService" style="display: none; "></span>
                                <sj:datepicker name="service_book_time" id="service_book_timeEHC"  onchange="accountManager(this.value,'service_managerEHC');" timepickerOnly="false" minDate="0"  timepicker="true" timepickerAmPm="false" Class="button"  size="15" displayFormat="dd-mm-yy"  readonly="false"   showOn="focus"   Placeholder="Select Date and Time"/>
                                
	     	                   
                   </div>
                  </div>
	     
	      <div class="newColumn" >
	  	<div class="leftColumn">Service Manager:</div>
	           	<div class="rightColumn">
	    <span class="needed"></span>
	 	<span id="EHCservice_manager" class="pIdsService" style="display: none; "></span>
	          <s:select 
                                      id="service_managerEHC"
                                      name="service_manager"
                                      list="#{'-1':'No Data'}"
                                      headerKey="-1"
                                      headerValue="Select Service Manager" 
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                      >
                          </s:select>
	    </div>
	        </div>
	     
	    
	     <div class="newColumn">
                    <div class="leftColumn">EHC Remarks:</div>
                                <div class="rightColumn">
                                <span class="needed"></span>
                               <span id="EHCremark" class="pIdsService" style="display: none; "></span>
                                <s:textfield name="service_remark" id="service_remarkEHC" maxlength="200" onkeyup="textCounter(this.value,this.form.remLen,160,'service_remarkEHC')"  placeholder="Enter Data" cssStyle="margin:0px 0px 3px 0px;  width: 73%;"  cssClass="textField"/>                
                               
                   </div>
                  </div>
	     
	     </div>
	
	
	
	
<!-- --------------------------------------------------------OPD--------------------------------- -->	     
  	       <div id="OPDDiv" style="display:none">
	       <div class="newColumn" >
	  	<div class="leftColumn">Specialty:</div>
	           	<div class="rightColumn">
	           	<span id="OPDValidationService" class="pIdsService" style="display: none; "></span>
	    <span class="needed"></span>
	          <s:select 
                                      id="OPD_specialtyAService"
                                      name="opd_spec"
                                      list=	"SpecMap"
                                      headerKey="%{keyExist}"
                                      headerValue="%{valueExist}" 
                                      cssStyle="width:75%"
                                      theme="simple"
                                      onchange="docNameGet(this.value, 'OPD_doctor_nameA_ser')"
                                    
                                      >
                          </s:select>
	    </div>
	        </div>
	     
	 <div class="newColumn" >
	  	<div class="leftColumn">Doctor Name:</div>
	           	<div class="rightColumn">
	           	<span id="OPDValidationService1" class="pIdsService" style="display: none; "></span>
	    <span class="needed"></span>
	<s:select 
                                      id="OPD_doctor_nameA_ser"
                                      name="opd_doc"
                                      list=	"#{'-1':'No Data'}"
                                      headerKey="%{keyExistSec}"
                                      headerValue="%{valueExistSec}" 
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                      >
                          </s:select>
	    </div>
	        </div>	     
	     <div class="newColumn">
                    <div class="leftColumn">Booked Schedule:</div>
                                <div class="rightColumn">
                                <span id="OPDbook" class="pIdsService" style="display: none; "></span>
                                <span class="needed"></span>
	     	                  <sj:datepicker id="service_book_timeOPD" name="service_book_time"  onchange="accountManager(this.value,'service_managerOPD');" timepickerOnly="false" minDate="0"  timepicker="true" timepickerAmPm="false" Class="button"  size="15" displayFormat="dd-mm-yy"  readonly="false"   showOn="focus"   Placeholder="Select Date and Time"/>
   
                   </div>
                  </div>
	     
	      <div class="newColumn" >
	  	<div class="leftColumn">Service Manager:</div>
	           	<div class="rightColumn">
	           	<span id="OPDser_manager" class="pIdsService" style="display: none; "></span>
	    <span class="needed" ></span>
	          <s:select 
                                      id="service_managerOPD"
                                      name="service_manager"
                                       list="#{'-1':'No Data'}"
                                      headerKey="-1"
                                      headerValue="Select Service Manager" 
                                      cssClass="select"
                                      cssStyle="width:75%;"
                                      theme="simple"
                                      >
                          </s:select>
	    </div>
	        </div>
	        
	    <div class="newColumn">
                    <div class="leftColumn">OPD Remarks:</div>
                                <div class="rightColumn">
                                <span id="OPDremark" class="pIdsService" style="display: none; "></span>
                                 <span class="needed"></span>
                                <s:textfield name="service_remark" id="service_remark_opd" maxlength="200" onkeyup="textCounter(this.value,this.form.remLen,160,'service_remark_opd')"  placeholder="Enter Data" cssStyle="margin:0px 0px 3px 0px;  width: 73%;"  cssClass="textField"/>     
                   </div>
                  </div>  
	   
	     </div>
	     
	      
	     
	     
 <!-----------------------------------------------------------------------------Radiology FIELDS------------------------------------------------------------------------------------>
	     <div id="RadiologyDiv" style="display:none">
	     
	     <div class="newColumn" >
	  	<div class="leftColumn">Modality:</div>
	           	<div class="rightColumn">
	           	<span id="LabModD" class="pIdsService" style="display: none; "></span>
	    <span class="needed"></span>
	          <s:select 
                                      id="radio_modality_mod"
                                      name="radio_mod"
                                      list=	"#{'-1':'No Data'}"
                                      headerKey="%{keyExist}"
                                      headerValue="%{valueExist}" 
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                      >
                          </s:select>
	    </div>
	        </div>	  
	     
	     <div class="newColumn">
                    <div class="leftColumn">Booked Schedule:</div>
                                <div class="rightColumn">
                                <span id="RadBook" class="pIdsService" style="display: none; "></span>
                                <span class="needed"></span>
                	<sj:datepicker id="service_book_timeRad" name="service_book_time" onchange="accountManager(this.value,'service_managerRad');" timepickerOnly="false" minDate="0"  timepicker="true" timepickerAmPm="false" Class="button"  size="15" displayFormat="dd-mm-yy"  readonly="false"   showOn="focus"   Placeholder="Select Date and Time"/>  
                   </div>
                  </div>
	      <div class="newColumn" >
	  	<div class="leftColumn"">Service Manager:</div>
	           	<div class="rightColumn">
	           	<span id="Radservice_manager" class="pIdsService" style="display: none; "></span>
	    <span class="needed" ></span>
	          <s:select 
                                      id="service_managerRad"
                                      name="service_manager"
                                     list="#{'-1':'No Data'}"
                                      headerKey="-1"
                                      headerValue="Select Service Manager" 
                                      cssClass="select"
                                      cssStyle="width:75%;"
                                      theme="simple"
                                      >
                          </s:select>
	    </div>
	        </div>
	      <div class="newColumn">
                    <div class="leftColumn">Radiology Remarks:</div>
                                <div class="rightColumn">
                                <span id="RadRemark" class="pIdsService" style="display: none; "></span>
                                <span class="needed"></span>
                                <s:textfield name="service_remark" id="service_remarkRad" maxlength="200" onkeyup="textCounter(this.value,this.form.remLen,160,'service_remarkRad')"  placeholder="Enter Data" cssStyle="margin:0px 0px 3px 0px;  width: 73%;"  cssClass="textField"/> 
                   </div>
                  </div>
	        
	     </div>
	     
	     
	     
	     
	        
	     <!-----------------------------------------------------------------------------Facilitation FIELDS------------------------------------------------------------------------------------>
	     <div id="FacilitationDiv" style="display:none">
	     
	       <div class="newColumn" >
	  	<div class="leftColumn">Facilitation For:</div>
	           	<div class="rightColumn">
	           	<span id="FacilitationMod" class="pIdsService" style="display: none; "></span>
	   			 <span class="needed"></span>
	          <s:select 
                                      id="facilitation_mod"
                                      name="facilitation_mod"
                                      list=	"#{'-1':'No Data'}"
                                      headerKey="%{keyExist}"
                                      headerValue="%{valueExist}" 
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                      >
                          </s:select>
	    </div>
	        </div>	  
	     
	     <div class="newColumn">
                    <div class="leftColumn">Booked Schedule:</div>
                                <div class="rightColumn">
                                <span id="FacilitationBook" class="pIdsService" style="display: none; "></span>
                                <span class="needed"></span>
                	<sj:datepicker id="service_book_timeFacilitation" name="service_book_time" onchange="accountManager(this.value,'service_managerFacilitation');" timepickerOnly="false" minDate="0"  timepicker="true" timepickerAmPm="false" Class="button"  size="15" displayFormat="dd-mm-yy"  readonly="false"   showOn="focus"   Placeholder="Select Date and Time"/>  
                   </div>
                  </div>
	      <div class="newColumn" >
	  	<div class="leftColumn"">Service Manager:</div>
	           	<div class="rightColumn">
	           	<span id="Facilitationservice_manager" class="pIdsService" style="display: none; "></span>
	    <span class="needed" ></span>
	          <s:select 
                                      id="service_managerFacilitation"
                                      name="service_manager"
                                     list="#{'-1':'No Data'}"
                                      headerKey="-1"
                                      headerValue="Select Service Manager" 
                                      cssClass="select"
                                      cssStyle="width:75%;"
                                      theme="simple"
                                      >
                          </s:select>
	    </div>
	        </div>
	      <div class="newColumn">
                    <div class="leftColumn">Facilitation Remarks:</div>
                                <div class="rightColumn">
                                <span id="FacilitationRemark" class="pIdsService" style="display: none; "></span>
                                <span class="needed"></span>
                                <s:textfield name="service_remark" id="service_remarkFacilitation" maxlength="200"  onkeyup="textCounter(this.value,this.form.remLen,160,'service_remarkFacilitation')" placeholder="Enter Data" cssStyle="margin:0px 0px 3px 0px;  width: 73%;"  cssClass="textField"/> 
                   </div>
                  </div>
 	     </div>
	     
	     
 	     
 	     
 	     	     
	     <!-----------------------------------------------------------------------------Telemedicine FIELDS------------------------------------------------------------------------------------>
	     <div id="TelemedicineDiv" style="display:none">
	     
	     <div class="newColumn" >
	  	<div class="leftColumn">Telemedicine For:</div>
	           	<div class="rightColumn">
	           	<span id="TelemedicineMod" class="pIdsService" style="display: none; "></span>
	   			 <span class="needed"></span>
	          <s:select 
                                      id="telemedicine_mod"
                                      name="telemedicine_mod"
                                      list=	"#{'-1':'No Data'}"
                                      headerKey="%{keyExist}"
                                      headerValue="%{valueExist}" 
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                      >
                          </s:select>
	    </div>
	        </div>	  
	     
	     <div class="newColumn">
                    <div class="leftColumn">Booked Schedule:</div>
                                <div class="rightColumn">
                                <span id="TelemedicineBook" class="pIdsService" style="display: none; "></span>
                                <span class="needed"></span>
                	<sj:datepicker id="service_book_timeTelemedicine" name="service_book_time" onchange="accountManager(this.value,'service_managerTelemedicine');" timepickerOnly="false" minDate="0"  timepicker="true" timepickerAmPm="false" Class="button"  size="15" displayFormat="dd-mm-yy"  readonly="false"   showOn="focus"   Placeholder="Select Date and Time"/>  
                   </div>
                  </div>
	      <div class="newColumn" >
	  	<div class="leftColumn"">Service Manager:</div>
	           	<div class="rightColumn">
	           	<span id="Telemedicineservice_manager" class="pIdsService" style="display: none; "></span>
	    <span class="needed" ></span>
	          <s:select 
                                      id="service_managerTelemedicine"
                                      name="service_manager"
                                     list="#{'-1':'No Data'}"
                                      headerKey="-1"
                                      headerValue="Select Service Manager" 
                                      cssClass="select"
                                      cssStyle="width:75%;"
                                      theme="simple"
                                      >
                          </s:select>
	    </div>
	        </div>
	      <div class="newColumn">
                    <div class="leftColumn">Telemedicine Remarks:</div>
                                <div class="rightColumn">
                                <span id="TelemedicineRemark" class="pIdsService" style="display: none; "></span>
                                <span class="needed"></span>
                                <s:textfield name="service_remark" id="service_remarkTelemedicine" maxlength="200" onkeyup="textCounter(this.value,this.form.remLen,160,'service_remarkTelemedicine')"   placeholder="Enter Data" cssStyle="margin:0px 0px 3px 0px;  width: 73%;"  cssClass="textField"/> 
                   </div>
                  </div>
 	     </div>
 	     
<!-----------------------------------------------------------------------------IPD FIELDS------------------------------------------------------------------------------------>
	     
 	     <div id="IPDDiv" style="display:none">
	      <div class="newColumn" >
	  	<div class="leftColumn">Specialty:</div>
	           	<div class="rightColumn">
	           	<span id="IPDSpec" class="pIdsService" style="display: none; "></span>
	    <span class="needed"></span>
	          <s:select 
                                      id="IPD_specialtyA_service"
                                      name="ipd_spec"
                                      list=	"SpecMap"
                                      headerKey="%{keyExist}"
                                      headerValue="%{valueExist}" 
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                      onchange="docNameGet(this.value, 'IPD_doctor_nameA_service')"
                                      >
                          </s:select>
	    </div>
	        </div>
	        
	        <div class="newColumn" >
	  	<div class="leftColumn">Doctor Name:</div>
	           	<div class="rightColumn">
	           	<span id="IPDDoc" class="pIdsService" style="display: none; "></span>
	    <span class="needed"></span>
	          <s:select 
                                      id="IPD_doctor_nameA_service"
                                      name="ipd_doc"
                                      list=	"#{'-1':'No Data'}"
                                       headerKey="%{keyExistSec}"
                                      headerValue="%{valueExistSec}" 
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                      >
                          </s:select>
                          </div>
	        </div>	
	        
	        <div class="newColumn" >
	  	<div class="leftColumn">Bed Type:</div>
	           	<div class="rightColumn">
	           	<span id="IPDBed" class="pIdsService" style="display: none; "></span>
	    <span class="needed"></span>
	          <s:select 
                                      id="IPD_bed_type"
                                      name="ipd_bed"
                                      list=	"{}"
                                      headerKey="-1"
                                      headerValue="Select Bed Type" 
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                      >
                          </s:select>
	    </div>
	        </div>	
	        
	        <div class="newColumn" >
	  	<div class="leftColumn">Payment Type:</div>
	           	<div class="rightColumn">
	           	<span id="IPDPay" class="pIdsService" style="display: none; "></span>
	    <span class="needed"></span>
	          <s:select 
                                      id="IPD_payment_type"
                                      name="ipd_pat_type"
                                      list=	"paymenetType"
                                      headerKey="-1"
                                      headerValue="Select Payment Type" 
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                      >
                          </s:select>
                        
	    </div>
	        </div>
	        
	        <div class="newColumn">
                    <div class="leftColumn">Booked Schedule:</div>
                                <div class="rightColumn">
                                <span id="IPDBook" class="pIdsService" style="display: none; "></span>
                                <span class="needed"></span>
                	<sj:datepicker id="service_book_timeIPD" name="service_book_time" onchange="accountManager(this.value,'service_managerIPD');" timepickerOnly="false" minDate="0"  timepicker="true" timepickerAmPm="false" Class="button"  size="15" displayFormat="dd-mm-yy"  readonly="false"   showOn="focus"   Placeholder="Select Date and Time"/>  
                   </div>
                  </div>
	     <div class="newColumn" >
	  	<div class="leftColumn" >Service Manager:</div>
	           	<div class="rightColumn">
	           	<span id="IPDSer" class="pIdsService" style="display: none; "></span>
	    <span class="needed"></span>
	          <s:select 
                                      id="service_managerIPD"
                                      name="service_manager"
                                      list="#{'-1':'No Data'}"
                                      headerKey="-1"
                                      headerValue="Select Service Manager" 
                                      cssClass="select"
                                      cssStyle="width:75%;"
                                      theme="simple"
                                      >
                          </s:select>
	    </div>
	        </div>
	   
	   <div class="newColumn">
                    <div class="leftColumn">IPD Remarks:</div>
                                <div class="rightColumn">
                                <span id="IPDremark" class="pIdsService" style="display: none; "></span>
                                <span class="needed"></span>
                                <s:textfield name="service_remark" id="service_remarkIPD" maxlength="200" onkeyup="textCounter(this.value,this.form.remLen,160,'service_remarkIPD')"  placeholder="Enter Data" cssStyle="margin:0px 0px 3px 0px;  width: 73%;"  cssClass="textField"/>  
                   </div>
                  </div>
	   
	     </div>
	     
<!-----------------------------------------------------------------------------Day Care FIELDS------------------------------------------------------------------------------------>	     
	      
	     <div id="DayCareDiv" style="display:none">
	        <div class="newColumn" >
	  	<div class="leftColumn">Specialty:</div>
	           	<div class="rightColumn">
	           	<span id="DaySpec" class="pIdsService" style="display: none; "></span>
	    <span class="needed"></span>
	          <s:select 
                                      id="Day_care_specialtyA_service"
                                      name="daycare_spec"
                                      list=	"SpecMap"
                                      headerKey="%{keyExist}"
                                      headerValue="%{valueExist}" 
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                    onchange="docNameGet(this.value, 'Day_care_doctorA_service')"
                                      >
                          </s:select>
	    </div>
	        </div>
	 <div class="newColumn" >
	  	<div class="leftColumn">Doctor Name:</div>
	           	<div class="rightColumn">
	           	<span id="DayDoc" class="pIdsService" style="display: none; "></span>
	    <span class="needed"></span>
	          <s:select 
                                      id="Day_care_doctorA_service"
                                      name="daycare_doc"
                                      list=	"#{'-1':'No Data'}"
                                     headerKey="%{keyExistSec}"
                                      headerValue="%{valueExistSec}" 
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                      >
                          </s:select> 
                          </div>
	        </div>	     
	     <div class="newColumn">
                    <div class="leftColumn">Booked Schedule:</div>
                                <div class="rightColumn">
                                <span id="DayBook" class="pIdsService" style="display: none; "></span>
                                <span class="needed"></span>
                	 <sj:datepicker id="service_book_time_day" name="service_book_time" onchange="accountManager(this.value,'service_managerDay');" timepickerOnly="false" minDate="0"  timepicker="true" timepickerAmPm="false" Class="button"  size="15" displayFormat="dd-mm-yy"  readonly="false"   showOn="focus"   Placeholder="Select Date and Time"/>
                   </div>
                  </div>
	    
	    <div class="newColumn" >
	  	<div class="leftColumn" >Service Manager:</div>
	           	<div class="rightColumn">
	           	<span id="DaySer" class="pIdsService" style="display: none; "></span>
	    <span class="needed"></span>
	          <s:select 
                                     
                                      id="service_managerDay"
                                      name="service_manager"
                                      list=	"#{'-1':'No Data'}"
                                      headerKey="-1"
                                      headerValue="Select Service Manager" 
                                      cssClass="select"
                                      cssStyle="width:75%;"
                                      theme="simple"
                                      >
                          </s:select>
	    </div>
	        </div>
	        
	        <div class="newColumn">
                    <div class="leftColumn">Day Care Remarks:</div>
                                <div class="rightColumn">
                                <span id="DayRemark" class="pIdsService" style="display: none; "></span>
                                <span class="needed"></span>
                                <s:textfield name="service_remark" id="service_remark_day" maxlength="200"  onkeyup="textCounter(this.value,this.form.remLen,160,'service_remark_day')"  placeholder="Enter Data" cssStyle="margin:0px 0px 3px 0px;  width: 73%;"  cssClass="textField"/>                
                                 
                   </div>
                  </div>
                  
	     </div>
	     
	     
	     
	     
 <!-----------------------------------------------------------------------------Laboratry FIELDS------------------------------------------------------------------------------------>	     
 
 
	     <div id="LaboratoryDiv" style="display:none">
	    <!--  <div class="newColumn" >
	  	<div class="leftColumn">Modality:</div>
	           	<div class="rightColumn">
	           	<span id="LabMod" class="pIdsService" style="display: none; "></span>
	    <span class="needed"></span>
	          <s:select 
                                      id="laboratory_modality_action_lab"
                                      name="lab_mod"
                                      list=	"#{'-1':'No Data'}"
                                      headerKey="%{keyExist}"
                                      headerValue="%{valueExist}" 
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                      >
                          </s:select>
	    </div>
	        </div> -->	   
	     <div class="newColumn">
                    <div class="leftColumn">Booked Schedule:</div>
                                <div class="rightColumn">
                                <span id="LabBook" class="pIdsService" style="display: none; "></span>
                                <span class="needed"></span>
                	 <sj:datepicker id="service_book_time_lab" name="service_book_time" onchange="accountManager(this.value,'service_managerLab');" timepickerOnly="false" minDate="0"  timepicker="true" timepickerAmPm="false" Class="button"  size="15" displayFormat="dd-mm-yy"  readonly="false"   showOn="focus"   Placeholder="Select Date and Time"/>
                   </div>
                  </div>
	     
	     <!--  <div class="clear"></div> -->
	      <div class="newColumn" >
	  	<div class="leftColumn">Service Manager:</div>
	           	<div class="rightColumn">
	           	<span id="LabSer" class="pIdsService" style="display: none; "></span>
	    <span class="needed"></span>
	          <s:select 
                                      id="service_managerLab"
                                      name="service_manager"
                                      list=	"#{'-1':'No Data'}"
                                      headerKey="-1"
                                      headerValue="Select Service Manager" 
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                      >
                          </s:select>
	    </div>
	        </div>
	     
	      <div class="newColumn">
                    <div class="leftColumn">Laboratry Remarks:</div>
                                <div class="rightColumn">
                                <span id="LabRemark" class="pIdsService" style="display: none; "></span>
                                <span class="needed"></span>
                                <s:textfield name="service_remark" id="service_remark_lab" maxlength="200"  onkeyup="textCounter(this.value,this.form.remLen,160,'service_remark_lab')"   placeholder="Enter Data" cssStyle="margin:0px 0px 3px 0px;  width: 73%;"  cssClass="textField"/>
                   </div>
                  </div>
	     
	     </div>
	     
	     <!-----------------------------------------------------------------------------New Service Request FIELDS------------------------------------------------------------------------------------>	     
 
 
	     <div id="NewServiceDiv" style="display:none">
	     	     
	     <div class="newColumn">
                    <div class="leftColumn">Booked Schedule:</div>
                                <div class="rightColumn">
                                <span id="NewBook" class="pIdsService" style="display: none; "></span>
                                <span class="needed"></span>
                	  <sj:datepicker id="service_book_timeNS" name="service_book_time" onchange="accountManager(this.value,'service_managerNS');" timepickerOnly="false" minDate="0"  timepicker="true" timepickerAmPm="false" Class="button"  size="15" displayFormat="dd-mm-yy"  readonly="false"   showOn="focus"   Placeholder="Select Date and Time"/>
                   </div>
                  </div>
	     
	     
	      <div class="newColumn" >
	  	<div class="leftColumn">Service Manager:</div>
	           	<div class="rightColumn">
	           	<span id="NewSer" class="pIdsService" style="display: none; "></span>
	    <span class="needed"></span>
	          <s:select 
                                      id="service_managerNS"
                                      name="service_manager"
                                      list=	"#{'-1':'No Data'}"
                                      headerKey="-1"
                                      headerValue="Select Service Manager" 
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                      >
                          </s:select>
	    </div>
	        </div>
	    
	     <div class="clear"></div>
	     <div class="newColumn">
                    <div class="leftColumn">New Service Remarks:</div>
                                <div class="rightColumn">
                                <span id="NewRemarks" class="pIdsService" style="display: none; "></span>
                                <span class="needed"></span>
                                <s:textfield name="service_remark" id="service_remarkNS" maxlength="200"  onkeyup="textCounter(this.value,this.form.remLen,160,'service_remarkNS')" placeholder="Enter Data" cssStyle="margin:0px 0px 3px 0px;  width: 73%;"  cssClass="textField"/>
                   </div>
                  </div>
	     </div>
	     
	     <!-----------------------------------------------------------------------------Diagnostics FIELDS------------------------------------------------------------------------------------>	     
 
 
	     <div id="DiagnosticsDiv" style="display:none">
	     <div class="newColumn" >
	  	<div class="leftColumn">Diagnostics Test:</div>
	           	<div class="rightColumn">
	           	<span id="DiagT" class="pIdsService" style="display: none; "></span>
	    <span class="needed"></span>
	          <s:select 
                                      id="Diagnostics_testDT"
                                      name="diagnostics_test"
                                      list=	"#{'2d Echo':'2d Echo','Carotid Doppler':'Carotid Doppler','Stress Echo':'Stress Echo', 'TEE':'TEE', 'Ambulatory BP monitoring':'Ambulatory BP monitoring', 'Holter':'Holter', 'PFT':'PFT', 'Endoscopy':'Endoscopy', 'Uroflometry':'Uroflometry', 'EEG':'EEG', 'NCV':'NCV', 'Speech Therapy':'Speech Therapy'}"
                                      headerKey="%{keyExist}"
                                      headerValue="%{valueExist}" 
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                      >
                          </s:select>
	    </div>
	        </div>	     
	     <div class="newColumn">
                    <div class="leftColumn">Booked Schedule:</div>
                                <div class="rightColumn">
                                <span id="DiagBook" class="pIdsService" style="display: none; "></span>
                                <span class="needed"></span>
                	<sj:datepicker id="service_book_timeDT" name="service_book_time" onchange="accountManager(this.value,'service_managerDT');" timepickerOnly="false" minDate="0"  timepicker="true" timepickerAmPm="false" Class="button"  size="15" displayFormat="dd-mm-yy"  readonly="false"   showOn="focus"   Placeholder="Select Date and Time"/> 
                   </div>
                  </div>
                   <div class="newColumn" >
	  	<div class="leftColumn" ">Service Manager:</div>
	           	<div class="rightColumn">
	           	<span id="DiagSer" class="pIdsService" style="display: none; "></span>
	    <span class="needed"></span>
	          <s:select 
                                      id="service_managerDT"
                                      name="service_manager"
                                     list=	"#{'-1':'No Data'}"
                                      headerKey="-1"
                                      headerValue="Select Service Manager" 
                                      cssClass="select"
                                      cssStyle="width:75%" 
                                      theme="simple"
                                      >
                          </s:select>
	    </div>
	        </div>
	     <div class="newColumn">
                    <div class="leftColumn">Diagnostics Remarks:</div>
                                <div class="rightColumn">
                                <span id="DiagRemark" class="pIdsService" style="display: none; "></span>
                                <span class="needed"></span>
                                <s:textfield name="service_remark" id="service_remarkDT" maxlength="200"  onkeyup="textCounter(this.value,this.form.remLen,160,'service_remarkDT')" placeholder="Enter Data" cssStyle="margin:0px 0px 3px 0px;"  cssClass="textField"/>
                   </div>
                  </div>
	     </div>
	     
<!-----------------------------------------------------------------------------Emergency FIELDS------------------------------------------------------------------------------------>	     
 	     
 	     <div id="EmergencyDiv" style="display:none">
	      <div class="newColumn" >
	  	<div class="leftColumn">Specialty:</div>
	           	<div class="rightColumn">
	           	<span id="EmerSpec" class="pIdsService" style="display: none; "></span>
	    <span class="needed"></span>
	          <s:select 
                                      id="emergency_specialtyA_service"
                                      name="em_spec"
                                      list=	"SpecMap"
                                      headerKey="%{keyExist}"
                                      headerValue="%{valueExist}" 
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                      >
                          </s:select>
	    </div>
	        </div>
	     

	 <div class="newColumn" >
	  	<div class="leftColumn">Emergency:</div>
	           	<div class="rightColumn">
	           	<span id="EmerEmer" class="pIdsService" style="display: none; "></span>
	    <span class="needed"></span>
	          <s:select 
	          
                                      id="emergancy_assistanceEmer"
                                      name="em_spec_assis"
                                      list=	"#{'Ambulance':'Ambulance','Air-Ambulance':'Air-Ambulance'}"
                                     headerKey="%{keyExistSec}"
                                      headerValue="%{valueExistSec}" 
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                      >
                          </s:select>
	    </div>
	        </div>	     
	     <div class="newColumn">
                    <div class="leftColumn">Booked Schedule:</div>
                                <div class="rightColumn">
                                <span id="EmerBook" class="pIdsService" style="display: none; "></span>
                                <span class="needed"></span>
                	<sj:datepicker id="service_book_time_emer" name="service_book_time" onchange="accountManager(this.value,'service_manager_emer');" timepickerOnly="false" minDate="0"  timepicker="true" timepickerAmPm="false" Class="button"  size="15" displayFormat="dd-mm-yy"  readonly="false"   showOn="focus"   Placeholder="Select Date and Time"/> 
                   </div>
                  </div>
	     
	      <div class="newColumn" >
	  	<div class="leftColumn" >Service Manager:</div>
	           	<div class="rightColumn">
	           	<span id="EmerSer" class="pIdsService" style="display: none; "></span>
	    <span class="needed" ></span>
	          <s:select 
                                      id="service_manager_emer"
                                      name="service_manager"
                                     list=	"#{'-1':'No Data'}"
                                      headerKey="-1"
                                      headerValue="Select Service Manager" 
                                      cssClass="select"
                                      cssStyle="width:75%;"
                                      theme="simple"
                                      >
                          </s:select>
	    </div>
	        </div>
	      
	       	      <div class="newColumn">
                    <div class="leftColumn">Emergency Remarks:</div>
                                <div class="rightColumn">
                                <span id="EmerRemark" class="pIdsService" style="display: none; "></span>
                                <span class="needed"></span>
                           <s:textfield name="service_remark" id="service_remark_emer" maxlength="200"  onkeyup="textCounter(this.value,this.form.remLen,160,'service_remark_emer')" placeholder="Enter Data" cssStyle="margin:0px 0px 3px 0px;  width: 73%;"  cssClass="textField"/> 
                   </div>
                  </div>
	       
                  </div>
	       
</div>
</div>
</body>
</html>