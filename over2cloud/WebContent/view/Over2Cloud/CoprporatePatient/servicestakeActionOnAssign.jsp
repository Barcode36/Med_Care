<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<script type="text/javascript" src="<s:url value="/js/cpservice/actionValidation.js"/>"></script>
<SCRIPT type="text/javascript">
$.subscribe('reloadData', function(event, data)
{
	setTimeout(function(){ $("#orglevel12").fadeIn(); }, 10);
    setTimeout(function(){ $("#orglevel12").fadeOut(); }, 1000);
    sussessMessage();
	  });
function sussessMessage()
{
 delay(function()
{
	 $("#takeActionGrid").dialog('close');
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
	 $("#takeActionGrid").dialog('close');
	 newEntryCPS();
}
function backGrid()
{
	 $("#takeActionGrid").dialog('close');
	 newEntryCPS();
	 corporatePatientView('load');
}
function accountManagerRes(bdate, divId)
{ 
	var date=bdate.substring(0,2);
	$.ajax({
	    type : "post",
	    async:false,
	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/fetchAccountManager.action?id = "+$("#location").val()+"&date="+date+"&services="+$("#service").val(),
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
</head>
<body >
		<s:hidden  id="subCat" value="%{subCatg}"></s:hidden>
		<s:form id="formone1" name="formone" action="actionOnFinal"  theme="css_xhtml"  method="post" enctype="multipart/form-data">
		
			<s:hidden name="cps_id" value="%{id}"></s:hidden>
			<s:hidden id="corp_name_ID" name="corp_name_ID"  value="%{servicesID}"></s:hidden>
			<s:hidden id="service_name" name="service_name" value="%{serviceName}"></s:hidden>
			<s:hidden id="med_location" name="med_location" value="%{med_location}"></s:hidden>
	  <table border="1" width="100%" style="border-collapse: collapse;">
	   
				<tr bgcolor="#D8D8D8" style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='Ticket No'  || key=='Corporate Name' || key=='Patient Name'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr  style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='Package Type' || key=='Patient Mobile' || key=='Service' ">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr bgcolor="#D8D8D8" style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='Patient Email' || key=='Age' || key=='Location'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				<tr  style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='Preferred Schedule' || key=='Service Manager' || key=='Mobile'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
					
				</tr>
				<tr bgcolor="#D8D8D8" style="height: 25px">
					<s:iterator value="dataMap">
						<s:if test="key=='Escalation Level' || key=='UHID' || key=='Remarks'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
						</s:if>
					</s:iterator>
				</tr>
				
				
				<s:if test="serviceName=='IPD' || serviceName=='OPD' || serviceName=='DayCare'">
				<tr style="height: 25px">
					<s:iterator value="dataMap">
				 		<s:if test="key=='Doctor Name' || key=='Speciality'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
		 				</s:if>
			 		</s:iterator>
			 		<td width="10%"></td>
					<td width="10%"></td>
				</tr>
				</s:if>
				
				
				<s:if test="serviceName=='EHC'">
				<tr style="height: 25px">
					<s:iterator value="dataMap">
				 		<s:if test="key=='EHC Packages' || key=='Packages'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
		 				</s:if>
			 		</s:iterator>
			 		<td width="10%"></td>
					<td width="10%"></td>
				</tr>
				</s:if>
				
				
				<s:if test="serviceName=='Emergency'">
				<tr style="height: 25px">
					<s:iterator value="dataMap">
				 		<s:if test="key=='Emergency Speciality' || key=='Assistance'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
		 				</s:if>
			 		</s:iterator>
			 		<td width="10%"></td>
					<td width="10%"></td>
				</tr>
				</s:if>
				
				
				<s:if test="serviceName=='Radiology'">
				<tr style="height: 25px">
					<s:iterator value="dataMap">
				 		<s:if test="key=='Modality'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
		 				</s:if>
			 		</s:iterator>
			 		<td width="10%"></td>
					<td width="10%"></td>
					<td width="10%"></td>
					<td width="10%"></td>
				</tr>
				</s:if>
				
				
				<s:if test="serviceName=='Laboratory'">
				<tr style="height: 25px">
					<s:iterator value="dataMap">
				 		<s:if test="key=='Laboratory Test'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
		 				</s:if>
			 		</s:iterator>
			 		<td width="10%"></td>
					<td width="10%"></td>
					<td width="10%"></td>
					<td width="10%"></td>
				</tr>
				</s:if>
				
				
				
				<s:if test="serviceName=='Diagnostics'">
				<tr style="height: 25px">
					<s:iterator value="dataMap">
				 		<s:if test="key=='Test Name'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
		 				</s:if>
			 		</s:iterator>
			 		<td width="10%"></td>
					<td width="10%"></td>
					<td width="10%"></td>
					<td width="10%"></td>
				</tr>
				</s:if>
				
				
				
				<s:if test="serviceName=='Facilitation'">
				<tr style="height: 25px">
					<s:iterator value="dataMap">
				 		<s:if test="key=='Facilitation For'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
		 				</s:if>
			 		</s:iterator>
			 		<td width="10%"></td>
					<td width="10%"></td>
					<td width="10%"></td>
					<td width="10%"></td>
				</tr>
		 			</s:if>
				
				
				
				<s:if test="serviceName=='Telemedicine'">
				<tr style="height: 25px">
					<s:iterator value="dataMap">
				 		<s:if test="key=='Telemedicine For'">
							<td width="10%"><b><s:property value="%{key}"/>: <b></td>
							<td width="10%"><s:property value="%{value}"/></td>
		 				</s:if>
			 		</s:iterator>
			 		<td width="10%"></td>
					<td width="10%"></td>
					<td width="10%"></td>
					<td width="10%"></td>
				</tr>
				</s:if>
				
				
				
				
		</table>
		          <center>
			          <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
			          <div id="errZone1" style="float:center; margin-left: 7px">
			          </div>
			          </div>
		          </center>
		          
		           <div class="newColumn"> 
			       <div class="leftColumn">UHID:</div>
			            <div class="rightColumn">
			             <span id="uhid1" class="pidsFinal" style="display: none; ">uhidCheck#UHID#T#sc,</span>
				                  <s:textfield name="uhid" id="uhidCheck"  placeholder="Enter Data" cssClass="textField" value="%{uhid}" onblur="checkuhidOfAction(this.value);" maxlength="10"/>
			            </div>
	            </div>
	
	
             <div class="newColumn">
		       <div class="leftColumn">Status:</div>
		            <div class="rightColumn">
		            <span id="status1" class="pidsFinal" style="display: none; ">statusService#Status#D#D,</span>
		               <span class="needed"></span>
		                <s:select 
		                             id="statusService"
		                             name="status" 
		                           	 list="feedList"
		                             headerKey="-1"
		                             headerValue="Select Status"
		                             onchange="changeDiv(this.value)"
		                             cssClass="select"
		                             cssStyle="width:82%"
		                              >
		                              
                  		</s:select>    
		              </div>
		       </div>  
	
		   
		            
	        
		         <div id="resolve" style="display: none;">
		         
		           <div class="newColumn"> 
			       <div class="leftColumn">Resolve By:</div>
			       
			            <div class="rightColumn">
			            <span class="needed"></span>
			            <span id="resolveBy1" class="pidsFinal" style="display: none; "></span>
	                           <s:select 
		                              id="resolved_by"
	                                  name="resolved_by"  
				                      list="serviceMng" 
		                              headerKey="%{service_id}"
	                                  headerValue="%{ac_manager}"
		                             cssClass="select"
		                             cssStyle="width:82%"
		                              >
		                              
                  		</s:select>    
			            </div>
	            </div>
	            <div id="newRequest"style="display: none;">
	            <div class="newColumn"> 
			       <div class="leftColumn">New Request:</div>
			       
			            <div class="rightColumn">
			            <span class="needed"></span>
			           
                  		  <s:radio list="#{'1':'Yes','2':'No' }" name="chkNew" id="chkNew" value="2"  theme="simple"  onclick="chk(this.value, 'service')"/>
			            </div>
	            </div>
	            </div>
	            
	            </div>
	            
	             <div id="snooze" style="display: none;">
	             <span id="parkedTill1" class="pidsFinal" style="display: none; "></span>
	            <div class="newColumn"> 
			       <div class="leftColumn">Parked Till:</div>
			       
			            <div class="rightColumn">
			            <span class="needed"></span> 
			           <sj:datepicker cssStyle="margin:0px 0px 0px 0px"  cssClass="textField" timepickerOnly="true" timepicker="true" timepickerAmPm="true" id="parkedTill" name="parkedTill" size="20"   readonly="false"   showOn="focus" placeholder="Select Time"/>
			            </div>
	            </div>
	            
	            </div>
	            
	            <div id="nerReqst" style="display: none;"> 
	             <div class="newColumn">
	                   <div class="leftColumn">Service:</div>
                       <div class="rightColumn"><span class="needed" ></span> 
                       <span id="serviceSpan" class="pidsFinal" style="display: none;"></span>
                               <s:select 
	                                  name="service"  
	                                  id="serviceF"
				                      list="serviceManagerMap" 
				                      id="service"
				                      headerKey="-1"
	                                  headerValue="Select Services"
				                      cssClass="textField" 
	                                  theme="simple"
				                      cssStyle="width:82%;"
				                      placeholder="Enter Data"
				                      multiple="false"
				                      onchange="selectServices(this.value, 'location')"
				                   
	                           />
                       </div>
	                   </div>
	                   
	            <div class="newColumn">
	                   <div class="leftColumn">Location:</div>  
                       <div class="rightColumn"><span class="needed"></span> 
                        <span id="locationSpan" class="pidsFinal" style="display: none; "></span>
                               <s:select 
	                               name="location"  
	                               id="location" 
	                               list="#{'-1':'No Data'}"   
	                               headerKey="-1"
	                               headerValue="Select Location"
	                               cssClass="textField" 
	                               cssStyle="width:82%"
	                               theme="simple" 
	                               placeholder="Enter Data"
	                               onchange="serviceFieldsss('service','location')"
	                            
	                           />
				
                       </div>
	                   </div>
	            
	                   
	            </div>
	            
	           <div id="packIDH" style="display: none;">
            <div id="addFieldL1"></div>
            </div>

	             <div class="newColumn"> 									
			       <div class="leftColumn">Remarks:</div>
			            <div class="rightColumn">
			             <span class="needed"></span> 
			               	<span id="remark1" class="pidsFinal" style="display: none; ">remarkId#Remarks#T#sc,</span>
				                 <s:textfield name="remark" id="remarkId" onkeyup="textCounter(this.value,this.form.remLen,160,'remarkId')" placeholder="Enter Data" cssClass="textField" />
			            </div>
	            </div>
	             <div id="nerReqstP" style="display: none;">
	             <div class="newColumn">
	             <div id="close" style="display: none;">
	             <div class="leftColumn">Preferred Schedule:</div>
	             </div>
	             <div id="closeR" style="display: none;">
	             <div class="leftColumn">Booked Schedule:</div>
	             </div>
			               
		                   <div class="rightColumn"><span class="needed"></span>
							<span id="PreSebb" class="pidsFinal" style="display: none; "></span>
							<div id="textId11" style="display: block" >
							<s:textfield id="preferred_time_date" name="preferred_time" cssClass="textField" theme="simple" Placeholder="Date and Time For Schedule" cssStyle="width: 104px;height: 30px;" />
							</div>
							<div id="dateId11" style="display: none" >
								 <sj:datepicker name="preferred_time_cal" id="preferred_time_datecal" cssStyle="width: 65px;height: 15px;" onchange="accountManagerRes(this.value,'service_managerB');" timepickerOnly="false" minDate="0"  timepicker="false" timepickerAmPm="false" Class="button"  size="15" displayFormat="dd-mm-yy"  readonly="true"   showOn="focus"   Placeholder="Date and Time For Schedule"/>
								 H:<s:select id="houra"name="houra"  list="#{'01':'01','02':'02','03':'03','04':'04','05':'05','06':'06','07':'07','08':'08','09':'09','10':'10','11':'11','12':'12'}"   cssClass="select" cssStyle="width:20%;height: 29px;" theme="simple"/>
								 M:<s:select id="minutsa"name="minutsa"  list="#{'01':'01','02':'02','03':'03','04':'04','05':'05','06':'06','07':'07','08':'08','09':'09','10':'10','11':'11','12':'12','13':'13','14':'14','15':'15','16':'16','17':'17','18':'18','19':'19','20':'20','21':'21','22':'22','23':'23','24':'24','25':'25','26':'26','27':'27','28':'28','29':'29','30':'30','31':'31','32':'32','33':'33','34':'34','35':'35','36':'36','37':'37','38':'38','39':'39','40':'40','41':'41','42':'42','43':'43','44':'44','45':'45','46':'46','47':'47','48':'48','49':'49','50':'50','51':'51','52':'52','53':'53','54':'54','55':'55','56':'56','57':'57','58':'58','59':'59','60':'60'}"  cssClass="select" cssStyle="width:20%;height: 29px;" theme="simple"/>
								 <s:select id="ampma"name="ampma"  list="#{'AM':'AM','PM':'PM'}" cssClass="select" cssStyle="width:23%;height: 29px;margin-top: -28px;margin-left: 122px;" theme="simple"/>
							</div>
								 
			        	 </div>
			        	 <div id="calId11" style="display: block;">
 					<img alt="" src="images/calendar_blk.png" title="Calander" style="margin-top:-3px;margin-right: 255px; height: 22px; float: right;" onclick="getCalander11()">
 				   </div>
	             <div id="openId11" style="display: none;">
 					<img alt="" src="images/textWrite.png" title="Open" style="margin-top:-3px; margin-right: 255px; height: 22px; float: right;" onclick="getOpen11()">
 				 </div>
 				 <img alt="" src="images/today.png" title="Today" style="margin-top:-3px; margin-right: -50px; height: 22px; float: right;" onclick="getToday11('today')">
 				<img alt="" src="images/tomorrow.png" title="Tomorrow" style="margin-top:-3px; margin-right: -80px; height: 22px; float: right;" onclick="getToday11('tomorrow')">
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
	            <div id="seerviceManager" style="display: none;">
	             <div class="newColumn" >
			  		<div class="leftColumn">Service Manager:</div>
	           		<div class="rightColumn">
				    <span class="needed"></span>
				          <s:select 
                                      id="service_managerB"
                                      name="service_managerB"
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
	             
	             </div>
	             
	             
   <!-- Buttons -->
   <div class="clear"></div>
   <div class="clear"></div>
   <div class="fields" align="center">
	<ul>
	<li class="submit" style="background: none;">
	<div class="type-button">
	<div id="ButtonDiv">
	<sj:submit 
   	id="finalService"
	targets="target11" 
	clearForm="true"
	value="Save" 
 	button="true"
	effectOptions="{ color : '#222222'}"
	effectDuration="400"
	onCompleteTopics="reloadData"
	cssStyle="margin-left: 53px;"
	onBeforeTopics="checkValidation"/>
	<sj:a 
	button="true" href="#"
	cssStyle="margin-top: -25px;margin-left: 182px;"
	onclick="backGrid();" >Back</sj:a>
	</div>
	<sj:div id="orglevel12"  effect="fold">
                   <sj:div  id="target11"></sj:div></sj:div>
	<br>
	</div>
	</li>
	</ul>
	</div>
	<b>Note: Enter remarks atleast 50 character: &nbsp;&nbsp;&nbsp;&nbsp;<input readonly type="text" id="remLen" size=3 maxlength="3" value="0" style="border:1px solid #e2e9ef;border-top:1px solid #cbd3e2;outline:medium none;padding:2px;margin-top: 16PX;"></b>
</s:form>
<!-- ----------------------------------------------------EHC----------------------------------------------- -->
	<div id="EHCDivL1" style="display:none">
			      
			       <div class="newColumn" >
			  		<div class="leftColumn">EHC Packages:</div>
	           		<div class="rightColumn">
				    <span class="needed"></span>
				  	<span id="EHCp" class="pidsFinal" style="display: none; "></span>
                               <span class="needed"></span>
				          <s:select 
                                      id="EHC_package_typeF"
                                      name="ehc_pack_type"
                                      list=	"#{'Standard':'Standard','Custom':'Custom'}"
                                      headerKey="-1"
                                      headerValue="Packages Type" 
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                      onchange="packageGetAction(this.value);"
                                      >
                          </s:select>
				    </div>
			        </div>
			     

					 <div class="newColumn">
                    <div class="leftColumn">Packages:</div>
                                <div class="rightColumn"  >
                                <span class="needed"></span>
                                <span id="EHCp1" class="pidsFinal" style="display: none; "></span>
                              <s:select 
                                      id="ehc_pack_serviceF"
                                      name="ehc_pack"
                                      list=	"#{'-1':'No Data'}"
                                      headerKey="-1"
                                      headerValue="Packages" 
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                    
                                      >
                          </s:select>
                   </div>
                  </div>
			     </div>
			     
			    <!-- -----------------------------------------IPD--------------------------------------------------- -->
			     
			     <div id="IPDDivL1" style="display:none">
			      <div class="newColumn" >
			  		<div class="leftColumn">Speciality:</div>
	           		<div class="rightColumn">
				    <span class="needed"></span>
				  <span id="IPDFS" class="pidsFinal" style="display: none; "></span>
				          <s:select 
                                      id="IPD_specialtyIPD"
                                      name="ipd_spec"
                                      list=	"#{'-1':'No Data'}"
                                      headerKey="-1"
                                      headerValue="Select Specialty" 
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                      onchange="docNameGet(this.value, 'IPD_doctor')"
                                     
                                      >
                          </s:select>
				    </div>
			        </div>
			     <!--  onchange="docNameGet(this.value, 'IPD_doctor_name' )" --> 
			        <div class="newColumn" >
			  		<div class="leftColumn">Doctor Name:</div>
	           		<div class="rightColumn">
				    <span class="needed"></span>
				  	<span id="IPDFD" class="pidsFinal" style="display: none; "></span>
				         <s:select 
                                      id="IPD_doctor"
                                      name="ipd_doc"
                                      list=	"#{'-1':'No Data'}"
                                      headerKey="-1"
                                      headerValue="Select Doctor Name" 
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                      >
                          </s:select>
  						<!--<s:textfield name="ipd_doc" id="IPD_doctor_name" maxlength="200"   placeholder="Enter Data" cssStyle="margin:0px 0px 3px 0px;  width: 73%;"  cssClass="textField"/>
				    --></div>
			        </div>	
			        <div class="newColumn" >
			  		<div class="leftColumn">Bed Type:</div>
	           		<div class="rightColumn">
				    <span class="needed"></span>
				  	<span id="IPDFB" class="pidsFinal" style="display: none; "></span>
				          <s:select 
                                      id="IPD_bed_typeF"
                                      name="ipd_bad"
                                      list=	"bedType"
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
				    <span class="needed"></span>
				  	<span id="IPDPF" class="pidsFinal" style="display: none; "></span>
				          <s:select 
                                      id="IPD_payment_typeF"
                                      name="ipd_pat_type"
                                      list=	"PaymenetType"
                                      headerKey="-1"
                                      headerValue="Select Payment Type" 
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                      >
                          </s:select>
                        
				    </div>
			        </div>
			     </div>
			     
			     <!-- Radiology -->
			     
			      <div id="RadiologyDivL1" style="display:none">
			      <div class="newColumn" >
			  		<div class="leftColumn">Modality:</div>
	           		<div class="rightColumn">
				    <span class="needed"></span>
				  	<span id="RadioF" class="pidsFinal" style="display: none; "></span>
				           <s:select 
                                      id="radiology_modalityF"
                                      name="radio_mod"
                                      list=	"#{'-1':'No Data'}"
                                      headerKey="-1"
                                      headerValue="Select Modality" 
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                      >
                          </s:select>
				    </div>
			        </div>			     
			     </div>
			     
			     
			        			<!-- Facilitation -->
			     
			      <div id="FacilitationDivL1" style="display:none">
			      <div class="newColumn" >
			  		<div class="leftColumn">Facilitation For:</div>
	           		<div class="rightColumn">
				    <span class="needed"></span>
				  	<span id="FacilitationF" class="pidsFinal" style="display: none; "></span>
				           <s:select 
                                      id="facilitationF"
                                      name="facilitation_mod"
                                      list=	"#{'-1':'No Data'}"
                                      headerKey="-1"
                                      headerValue="Select Facilitation" 
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                      >
                          </s:select>
				    </div>
			        </div>			     
			     </div>
			     
			     
			     
			     <!-- Telemedicine -->
			     
			      <div id="TelemedicineDivL1" style="display:none">
			      <div class="newColumn" >
			  		<div class="leftColumn">Telemedicine For:</div>
	           		<div class="rightColumn">
				    <span class="needed"></span>
				  	<span id="TelemedicineF" class="pidsFinal" style="display: none; "></span>
				           <s:select 
                                      id="telemedicineF"
                                      name="telemedicine_mod"
                                      list=	"#{'-1':'No Data'}"
                                      headerKey="-1"
                                      headerValue="Select Telemedicine" 
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                      >
                          </s:select>
				    </div>
			        </div>			     
			     </div>
			     			
			     			     
 <!-----------------------------------------------------------------------------Laboratry FIELDS------------------------------------------------------------------------------------>			     
 
 
			     <div id="LaboratoryDivL1" style="display:none">
			     <div class="newColumn" >
			  		<div class="leftColumn">Modality:</div>
	           		<div class="rightColumn">
				  	<span id="LaboratoryFM" class="pidsFinal" style="display: none; "></span>
				  	 <span class="needed"></span>
				          <s:select 
                                      id="laboratory_modality"
                                      name="lab_mod"
                                      list=	"#{'a':'P','b':'Q','c':'R'}"
                                      headerKey="-1"
                                      headerValue="Select Modality" 
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                      >
                          </s:select>
				    </div>
			        </div>			     
			     </div>
			     
			    <!-- -----------------------------------------------emergency--------------------------------------------- -->
 			     
 			     <div id="EmergencyDivL1" style="display:none">
			      <div class="newColumn" >
			  		<div class="leftColumn">Speciality:</div>
	           		<div class="rightColumn">
				  	<span id="EmergencyFS" class="pidsFinal" style="display: none; "></span>
				  	<span class="needed"></span>
				          <s:select 
                                      id="emergency"
                                      name="em_spec"
                                      list=	"#{'-1':'No Data'}"
                                      headerKey="-1"
                                      headerValue="Select Specialty" 
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
				    <span class="needed"></span>
				  	<span id="EmergencyFE" class="pidsFinal" style="display: none; "></span>
				          <s:select 
				          
                                      id="assistanceF"
                                      name="em_spec_assis"
                                      list=	"#{'-1':'No Data'}"
                                      headerKey="-1"
                                      headerValue="Select Emergency Assistance" 
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                      >
                          </s:select>
				    </div>
			        </div>			     
			  
			     
			      
			     
			    
			       
                  </div>
			 <!--  ------------------------------------OPD------------------------------------------------------ -->
			     
			     <div id="OPDDivL1" style="display:none">
			       <div class="newColumn" >
			  		<div class="leftColumn">Speciality:</div>
	           		<div class="rightColumn">
				    <span class="needed"></span>
				  	<span id="OPDF" class="pidsFinal" style="display: none; "></span>
				          <s:select 
                                      id="OPD_specialtyCC"
                                      name="opd_spec"
                                      list=	"#{'-1':'No Data'}"
                                      headerKey="-1"
                                      headerValue="Select Specialty" 
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                      onchange="docNameGet(this.value, 'OPD_doctor')"
                                    
                                      >
                          </s:select>
				    </div>
			        </div>
			     
 
				 <div class="newColumn" >
			  		<div class="leftColumn">Doctor Name:</div>
	           		<div class="rightColumn">
				    <span class="needed"></span>
				  	<span id="OPDF1" class="pidsFinal" style="display: none; "></span>
				  	   <s:select 
                                      id="OPD_doctor"
                                      name="opd_doc"
                                      list=	"#{'-1':'No Data'}"
                                      headerKey="-1"
                                      headerValue="Select Doctor Name" 
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                      >
                          </s:select>
				    </div>
			        </div>			     
			     </div>   
  
			   <!-- -----------------------------------------------Diagnostics---------------------------------------------------------- -->
 
 
			     <div id="DiagnosticsDivL1" style="display:none">
			     <div class="newColumn" >
			  		<div class="leftColumn">Diagnostics Test:</div>
	           		<div class="rightColumn">
				    <span class="needed"></span>
				  	<span id="DiagnosticsFD" class="pidsFinal" style="display: none; "></span>
				          <s:select 
                                      id="Diagnostics_testID"
                                      name="diagnostics_test"
                                     list=	"#{'-1':'No Data'}"
                                      headerKey="-1"
                                      headerValue="Select Test" 
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                      >
                          </s:select>
				    </div>
			        </div>			     
			     </div>
			    <!-- ----------------------------------------------------------day care--------------------------------------------------- -->
			     
			      <div id="DayCareDivL1" style="display:none">
			        <div class="newColumn" >
			  		<div class="leftColumn">Speciality:</div>
	           		<div class="rightColumn">
				    <span class="needed"></span>
				  	<span id="DayCareFS" class="pidsFinal" style="display: none; "></span>
				          <s:select 
                                      id="Day_care_specialtyDAY"
                                      name="daycare_spec"
                                      list=	"#{'-1':'No Data'}"
                                      headerKey="-1"
                                      headerValue="Select Specialty" 
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                       onchange="docNameGet(this.value, 'Day_care_doctorD')"
                                    
                                      >
                          </s:select>
				    </div>
			        </div>   
			   

				 <div class="newColumn" >
			  		<div class="leftColumn">Doctor Name:</div>
	           		<div class="rightColumn">
				    <span class="needed"></span>
				  	<span id="DayCareFD" class="pidsFinal" style="display: none; "></span>
				         <s:select 
                                      id="Day_care_doctorD"
                                      name="daycare_doc"
                                      list=	"#{'-1':'No Data'}"
                                      headerKey="-1"
                                      headerValue="Select Doctor Name" 
                                      cssClass="select"
                                      cssStyle="width:75%"
                                      theme="simple"
                                      >
                          </s:select><!-- 
                           <s:textfield name="Day_care_doctor" id="daycare_doc" maxlength="200"   placeholder="Enter Doctor Name" cssStyle="margin:0px 0px 3px 0px;  width: 73%;"  cssClass="textField"/>
				    --></div>
			        </div>			     
			   
			     </div>
			     
			      <div id="NewServiceDiv12" style="display:none">
			      </div>

</body>
</html>