
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<html>
<head>
<script type="text/javascript" src="<s:url value="/js/cpservice/cpServiceValidation.js"/>"></script>
<script type="text/javascript">
$.subscribe('level123', function(event,data)
		  {
	setTimeout(function(){ $("#orglevelEdit").fadeIn(); }, 10);
    setTimeout(function(){ $("#orglevelEdit").fadeOut(); }, 1000);
    sussessMessage();
	        });
	function sussessMessage()
	{
		 delay(function()
		{
			 closeAdd();
		    }, 1000 );
	}
	var delay = (function(){
		  var timer = 0;
		  return function(callback, ms){
		    clearTimeout (timer);
		    timer = setTimeout(callback, ms);
		  };
		})();
function closeAdd() {
	$("#editDialog").dialog('close');
	corporatePatientView('load');
}
function showAgepatient1(date, div)
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/ageView.action?date="+date,
	    async:false,
	    success : function(data) {
		$('#'+div).empty();
		var age = data[0].age;
		$(data).each(function(index)
				{
		    		  if(this.ageY=='0'){
		    			      $('#'+div).val(age.years+" Years , "+age.months+" Months , "+age.days+" Days ");
		    				}
		    				else  if(this.ageY=='1') {
		    					 $('#'+div).append(age);
		    				}
		    				else  if(this.ageY=='2') {
		    					 $('#'+div).append('<font color="Red"><b>'+age+'</b></font>');
		    				}
				});
	},
	   error: function() {
          alert("error");
      }
	 });
	//$("#age").hide();
	//$("#ageText").show();
	
}
function fetchState(value, divId)
{
   // alert(value);
     $.ajax({
		    type : "post",
		    url : "view/Over2Cloud/CorporatePatientServices/cpservices/fetchState.action?stateId="+value,
		    success : function(data){
   			console.log(data)
				$('#'+divId+' option').remove();
				$('#'+divId).append($("<option></option>").attr("value",-1).text("Select State"));
		    	$(data).each(function(index)
				{
				   $('#'+divId).append($("<option></option>").attr("value",this.ID).text(this.NAME));
				});
			    },
		   error: function() {
		        alert("error");
		    }
		 });
}

function getCity22(sourceId,divId)
{
    $.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/fetchCity.action?stateId="+sourceId,
	    success : function(data){
			console.log(data)
			$('#'+divId+' option').remove();
			$('#'+divId).append($("<option></option>").attr("value",-1).text("Select City"));
	    	$(data).each(function(index)
			{
			   $('#'+divId).append($("<option></option>").attr("value",this.NAME).text(this.NAME));
			});
		    },
	   error: function() {
	        alert("error");
	    }
	 });
}
function fetchCorName1(sourceId, divId)
{  
	//var value=$("#"+sourceId+" option:selected").val();
	//alert("value "+value);
	$.ajax({
	    type : "post",
	    async:false,
	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/fetchCorName.action?id = "+sourceId,
	    success : function(data) {
	    console.log(data);
	    data = data.jsonArray;
	    if(data != null){
		    $('#'+divId+' option').remove();
		   $('#'+divId).append($("<option></option>").attr("value",-1).text('Select Corporate Name'));
		   $(data).each(function(index)
		   {
			    
		        $('#'+divId).append( $("<option></option>").attr("value",this.ID).text(this.NAME) );
		      
		   
		  });
	}
		
	},
	   error: function() {
            alert("error");
        }
	 });
}
//getLocation

function fetchLocation(sourceId, divId)
{ 
	//var value=$("#"+sourceId+" option:selected").val();
	//alert(sourceId);
	$.ajax({
	    type : "post",
	    async:false,
	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/fetchServiceLocation.action?service_id = "+sourceId,
	    success : function(data) {
	   data = data.servicelocation;
	  
	    if(data != null){
		    $('#'+divId+' option').remove();
		   $('#'+divId).append($("<option></option>").attr("value",-1).text('Select Location'));
		   $(data).each(function(index)
		   {
		   $('#'+divId).append($("<option></option>").attr("value",this.NAME).text(this.NAME));
		  });
	}
		
	},
	   error: function() {
            alert("error");
        }
	 });
}
function fetchPatientName1(sourceId, divId)
{ 
	//var value=$("#"+sourceId+" option:selected").val();
	
	$.ajax({
	    type : "post",
	    async:false,
	    url : "view/Over2Cloud/CorporatePatientServices/cpservices/fetchPatientName.action?id = "+sourceId,
	    success : function(data) {
	   data = data.jsonArray;
	  
	    if(data != null){
		    $('#'+divId+' option').remove();
		   $('#'+divId).append($("<option></option>").attr("value",-1).text('Select Patient'));
		   $(data).each(function(index)
		   {
			   
		   $('#'+divId).append(  $("<option></option>").attr("value",this.ID).text(this.NAME));
		  });
	}
		
	},
	   error: function() {
            alert("error");
        }
	 });
}
function ageText1(){
	
	var date=$("#currDOB").val();
	var curYear = date.substring(6, 10);
	var changeDOB = parseInt(curYear) - 30;
	var DOB = date.substring(0, 6) + changeDOB;
	$("#pat_dob_cel").val(DOB);
	$('#ageText1').hide();
	$('#age1').show();
	$('#dobCal1').show();
	$('#dobtxt1').hide();
	$('#msg_div').empty();

}
function ageCal1(){
	$('#ageText1').show();
	$('#age1').hide();
	$('#dobCal1').hide();
	$('#dobtxt1').show();
	$('#msg_div').empty();
	document.getElementById("pat_dob_txt1").value = "";
	
}
function checkuhidEdit(value)
{
	if(value=='NA')
	{
		//$('#'+'servicename').html("");
	}
	else if(value.trim()!= "" )
  {//alert(value.length);
		if(value.length == 10)
		{
  		}
		else
		{
			 errZone1.innerHTML="<div class='user_form_inputError2'>Enter 10 digit UHID ! </div>";
		       $("#uh_id").css("background-color","#ff701a");  // 255;165;79
		       $("#uh_id").focus();
		       setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
		       setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
		       event.originalEvent.options.submit = false;
		}
     
  }
}
function checkMobile1(value)
{
	//alert(value);
	if(value.trim()!= "" )
  {//alert(value.length);
		if(value.length == 10)
		{
  	}
		else
		{
			 errZone1.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
		       $("#mobile").css("background-color","#ff701a");  // 255;165;79
		       $("#mobile").focus();
		       setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
		       setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
		       event.originalEvent.options.submit = false;
		}
     
  }
}
function checkFormat1(value)
{
	if(value=='NA')
	{
		//$('#'+'servicename').html("");
	}
	else if(value.trim()!=""){ 
      if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(value.trim())){
      }else{
     errZone1.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
     $("#email").css("background-color","#ff701a");  // 255;165;79
     $("#email").focus();
     setTimeout(function(){ $("#errZone1").fadeIn(); }, 10);
     setTimeout(function(){ $("#errZone1").fadeOut(); }, 2000);
     event.originalEvent.options.submit = false;
        } 
      }
}
function getCalander1()
{
	$('#textId1').hide();
	$('#dateId1').show();
	$('#openId1').show();
	$('#calId1').hide();
	document.getElementById("preferred_timeID").value = "";
}
function getOpen1()
{
	$('#textId1').show();
	$('#dateId1').hide();
	$('#openId1').hide();
	$('#calId1').show();
	document.getElementById("preferred").value = "";
}
function getToday1(date) 
{
	//alert(date);
	$('#textId').show();
	$('#dateId').hide();
	  $.ajax
	({
	type : "post",
	url : "view/Over2Cloud/CorporatePatientServices/cpservices/fetchDatepreferedTime.action?fetchDate="+date,
	async:false,
	success : function(data)
	{
		if(data.length>0){
			$("#preferred_timeId").empty();
			var temp="";
			if(data[0].Today)
			{
			temp=data[0].Today;
			}
			if(data[0].Tomorrow)
			{
			temp=data[0].Tomorrow;
			}
			document.getElementById("preferred_timeId").value = temp;
		}
	
	},
	error : function()
	{
	alert("Error on data fetch");
	} 
	}); 
	
} 
</script>
</head>
<div>
 
	<s:form id="modifyForm" name="modifyForm" namespace="/view/Over2Cloud/CorporatePatientServices/cpservices" action="modifyCorporatePatience" theme="simple" method="post" enctype="multipart/form-data" focusElement="callerId">
	<s:hidden name="id" value="%{id}"/>
	<s:hidden id="currDOB" value="%{DOB}"></s:hidden>
		<center>
			<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%;height:100%; font-size: small; border: 0px solid red; border-radius: 6px;">
	             <div id="errZone1" style="float:center; margin-left: 7px" align="center"></div>        
	        </div>
        </center>
        <s:property value="p_name"/>
		<table  rules="rows" width="100%" style="margin-bottom: 10px;margin-left: 0px;margin-top: 5px;" >
		
		<tr align="center">
			<td>
		  	  	<label for="docNameTo" style="font-size: 15px">Account Manager</label>
				</td>
		 	 <td title="Account Manager Name">
		 	 <span class="pIds4" style="display: none; ">managerId#Account Manager#T#sc,</span>
					 <div class="newColumn" style="width: 99%">
						<div class="rightColumn" style="width: 99%">
						<span class="needed"></span>
								<s:select 
								id="managerId"
	                              name="ac_manager" 
								  list="acManager"  
								  headerKey="%{managerId}"
								  headerValue="%{manager}"
	                               cssClass="textField" 
	                               cssStyle="width:79%"
	                               theme="simple" 
	                               placeholder="Enter Data"
	                               onchange="fetchCorName1(this.value,'c_name');"
	                           />
						</div>
					 </div>
				 </td>
				 
				 <td>
		  	  	<label for="docSpecTo" style="font-size: 15px">Corporate Name</label>
				</td>
				<td title="Corporate Name">
				 <span class="pIds4" style="display: none; ">c_name#Corporate Name#T#sc,</span>
				  <div class="newColumn" style="width: 99%">
						<div class="rightColumn" style="width: 99%" >
						<span class="needed"></span>
					    	<s:select 
					    			id="c_name"
	                              name="corp_name" 
								  list="{}"  
	                               headerKey="%{corp_nameId}"
	                               headerValue="%{corp_name}"
	                               cssClass="textField" 
	                               cssStyle="width:77%"
	                               theme="simple" 
	                               placeholder="Enter Data"
	                           />
						</div>
					 </div>
				 </td>
				 
			   <td>
		  	  	<label for="corName" style="font-size: 15px">Ac Mobile</label>
			</td>
			<td title="Account Manager Name">
					 <div class="newColumn" style="width: 99%">
						<div class="rightColumn" style="width: 99%" >
							<div id="feed_status_name" style="width: 99%">
							<span class="needed"></span>
								<s:textfield id="acc_mob" name="acc_mob"  cssClass="textField" theme="simple" placeHolder="Enter Mobile" cssStyle="width: 175px;"  maxlength="10" />
							</div>
						</div>
					 </div>
				 </td>
				 
			 	 </tr>
		
		 <tr style="height:5px"></tr>
			<tr align="center">
			 <td>
		  	  	<label for="docNameTo" style="font-size: 15px">Patient Type</label>
				</td>
		 	 <td title="Patient Type">
		 	  <span class="pIds4" style="display: none; ">patient_type#Patient Type#T#sc,</span>
					 <div class="newColumn" style="width: 99%">
						<div class="rightColumn" style="width: 99%">
								<span class="needed"></span>
							<s:select 
					    		id="patient_type"
	                              name="feed_status" 
								  list="#{'Standard':'Standard','VVIP':'VVIP','Priority':'Priority','Others':'Others'}"
	                               headerKey="%{patient_type}"
	                               headerValue="%{patient_type}"
	                               cssClass="textField" 
	                               cssStyle="width:79%"
	                               theme="simple" 
	                               placeholder="Enter Data"
	                           />
						</div>
					 </div>
				 </td>
				 
				<td>
		  	  	<label for="docIdTo" style="font-size: 15px">Patient Name</label>
				</td>
				<td title="Patient Name">
				<span id="pname" class="pIds1" style="display: none; ">p_name#Patient Name#T#sc,</span>
				<div class="newColumn" style="width: 99%">
					<div class="rightColumn" style="width: 93%">
							  <s:textfield id="patient_name" name="patient_name" cssClass="textField" theme="simple" placeHolder="Enter patient NamP"   />
					</div>
				 </div>
				 </td>
				<td>
		  	  	<label for="docNameTo" style="font-size: 15px">Gender</label>
				</td>
		 	 <td title="Gender">
		 	 <span id="spanId" class="pIds1" style="display: none; ">pgender#Gender#T#sc,</span>
					 <div class="newColumn" style="width: 99%">
						<div class="rightColumn" style="width: 99%" >
						<span class="needed"></span>

							<s:select 
	                               
	                               id="pgender"
	                               name="pat_gender"  
	                               list="#{'Male':'Male','Female':'Female'}"  
	                               headerKey="%{pgenders}"
	                               headerValue="%{pgenders}"
	                               cssClass="textField" 
	                               cssStyle="width:78%"
	                               theme="simple" 
	                               placeholder="Enter Data"
	                           />
						</div>
					 </div>
				 </td>
				
				 </tr>
				 
				<tr align="center">
				
				 <td>
		  	  	<label for="docSpecTo" style="font-size: 15px">UHID</label>
				</td>
				<td title="UHID">
				 <div class="newColumn" style="width: 99%">
					<div class="rightColumn" style="width: 93%">
							 
							  <s:textfield id="uh_id" name="uhid"  cssClass="textField" theme="simple" placeHolder="Enter UHID" cssStyle="width: 178px;" onblur="checkuhidEdit(this.value);" maxlength="10" />
					</div>
				 </div>
				 </td>
				
				<td>
		  	  	<label for="docNameTo" style="font-size: 15px">Mobile</label>
				</td>
		 	 <td title="Patient Mobile No">
		 	  <span class="pIds1" style="display: none; ">mobile#Mobile#T#m,</span>
					 <div class="newColumn" style="width: 99%">
						<div class="rightColumn" style="width: 93%">
							<span class="needed"></span>
							<s:textfield id="mobile" name="pat_mobile" maxlength="10" cssClass="textField" theme="simple" placeHolder="Enter Mobile No" cssStyle="width: 199px;" onblur="checkMobile1(this.value);"  />
							
					    	
						</div>
					 </div>
				 </td>
		 	
				 <td>
		  	  		<label for="docIdTo" style="font-size: 15px">Patient DOB</label>
				</td>
		 	 <td title="Patient DOB">
				 <div class="newColumn" style="width: 99%">
					<div class="rightColumn" style="width: 99%">
					       <div id="age1" style="display: none;">
                               <sj:datepicker   cssClass="button"  name="pat_dob1"  id="pat_dob_cel" size="20"  readonly="true"  changeMonth="true" changeYear="true"  yearRange="1980" showOn="focus" displayFormat="dd-mm-yy" Placeholder="Enter DOB"  cssStyle="width: 148px;text-align: center;font-size: medium;"/>
							</div>
							<div id="ageText1" style="display: block;">
								<s:textfield   id="pat_dob_txt1" name="pat_dob"   cssClass="textField" placeholder="Enter Age" onchange="showAgeText(this.value,'pat_dob_txt1')" cssStyle="width: 175px;" maxlength="3"></s:textfield>
							</div>
					</div>
				 </div>
				 </td>
				 <td>
					 <div  style="width: 175%">
				 		<div style="width: 99%;margin-left: -253px;margin-top: -8px;">
						       <div id = "dobtxt1" style="display: block;">
       								<img alt="" src="images/calendar_blk.png" style="margin-top:14px;margin-left:94px; height: 22px; float: left;" onclick="ageText1()">
      				 			</div>
      				 			<div id = "dobCal1" style="display: none;">
       								<img alt="" src="images/textWrite.png" style="margin-top:14px;margin-left:94px; height: 22px; float: left;" onclick="ageCal1()">
       							</div>
						</div>
					 </div>
			</tr>
			
			<tr align="center">
			<td>
		  	  	<label for="docIdTo" style="font-size: 15px">Email</label>
				</td>
				<td title="Patient EmailID">
			 	 <div class="newColumn" style="width: 99%">
					<div class="rightColumn" style="width: 93%">
							<s:textfield id="email" name="pat_email" cssClass="textField" theme="simple" placeHolder="Enter Email" cssStyle="width: 178px;" onblur="checkFormat1(this.value);"  />
					</div>
				 </div>
				 </td>
				 <td>
				<label for="docIdTo" style="font-size: 15px">Country</label>
				</td>
				<td title="Country">
				  <div class="newColumn" style="width: 99%">
						<div class="rightColumn" style="width: 99%" id="pat_country">
					    	<s:select 
	                               id="country" 
	                               name="pat_country"
	                               list="stateMap"
	                               headerKey="%{countryid}"
	                               headerValue="%{country_name}"
	                               cssClass="textField" 
	                               cssStyle="width:77%"
	                               theme="simple" 
	                               placeholder="Enter Data"
	                               onchange="fetchState(this.value,'state_id1');"
	                           />
						</div>
					 </div>
				 </td>
				  <td>
				<label for="docIdTo" style="font-size: 15px">State</label>
				</td>
				<td title="Country">
				   <div class="newColumn" style="width: 99%">
						<div class="rightColumn" style="width: 99%">
							<s:select 
	                               id="state_id1" 
	                               name="pat_state"
	                               list="#{'-1':'No Data'}"   
	                               headerKey="%{stateid}"
	                               headerValue="%{stateid}"
	                               cssClass="textField" 
	                               cssStyle="width:78%"
	                               theme="simple" 
	                               placeholder="Enter Data"
	                               onchange="getCity22(this.value,'cityid');"
	                           />
						</div>
					 </div>
				 </td>
			 	 </tr>
			 	 
 			<tr align="center">
 			 <td>
		  	  	<label for="docNameTo" style="font-size: 15px">City</label>
				</td>
		 	 <td title="City">
					 <div class="newColumn" style="width: 99%">
						<div class="rightColumn" style="width: 99%" >
					    		<s:select 
	                               id="cityid" 
	                               name="pat_city"
	                               list="#{'-1':'No Data'}"   
	                               headerKey="%{cityid}"
	                               headerValue="%{cityid}"
	                               cssClass="textField" 
	                               cssStyle="width:79%"
	                               theme="simple" 
	                               placeholder="Enter Data"
	                           />
						</div>
					 </div>
				 </td>
				<td>
		  	  	<label for="docIdTo" style="font-size: 15px">Preferred Schedule:</label>
				</td>
				<td title="Preferred Schedule">
			 	 <div class="newColumn" style="width: 99%">
					<div class="rightColumn" >
							<div id="textId1" style="display: block" >
							<s:textfield id="preferred_timeId"  name="preferred_time"  cssClass="textField" theme="simple" placeholder="DD-MM-YYYY"  cssStyle="width: 199px;"  />
							</div>
							<div id="dateId1" style="display: none" >
								 <sj:datepicker name="preferred_timecal" id="preferred_timecal"  cssStyle="width: 172px;height: 16px;text-align: center;"  timepickerOnly="false" minDate="0"  timepicker="false" timepickerAmPm="false" Class="button"  size="15" displayFormat="dd-mm-yy"  readonly="false"   showOn="focus"   Placeholder="Select Preferred Schedule"/>
								 <br>H:<s:select id="hour"name="hour"  list="#{'01':'01','02':'02','03':'03','04':'04','05':'05','06':'06','07':'07','08':'08','09':'09','10':'10','11':'11','12':'12'}" cssClass="select" cssStyle="width:25%;height: 29px;" theme="simple"/>
								 M:<s:select id="minuts"name="minuts"  list="#{'01':'01','02':'02','03':'03','04':'04','05':'05','06':'06','07':'07','08':'08','09':'09','10':'10','11':'11','12':'12','13':'13','14':'14','15':'15','16':'16','17':'17','18':'18','19':'19','20':'20','21':'21','22':'22','23':'23','24':'24','25':'25','26':'26','27':'27','28':'28','29':'29','30':'30','31':'31','32':'32','33':'33','34':'34','35':'35','36':'36','37':'37','38':'38','39':'39','40':'40','41':'41','42':'42','43':'43','44':'44','45':'45','46':'46','47':'47','48':'48','49':'49','50':'50','51':'51','52':'52','53':'53','54':'54','55':'55','56':'56','57':'57','58':'58','59':'59','60':'60'}" cssClass="select" cssStyle="width:25%;height: 29px;" theme="simple"/>
								 <s:select id="ampm"name="ampm"  list="#{'AM':'AM','PM':'PM'}" cssClass="select" cssStyle="width:25%;height: 29px;" theme="simple"/>
							</div>
						</div>
				 </div>
				 <div class="cleear"></div>
				<div style="margin-left: 151px;">
				<div id="calId1" style="display: block;">
				<img alt="" src="images/calendar_blk.png" title="Calander" style="margin-top:63px;margin-left: -346px; height: 22px; float: left;" onclick="getCalander1()">
				</div>
				<div id="openId1" style="display: none;">
				<img alt="" src="images/textWrite.png" title="Open" style="margin-top:63px;margin-left: -346px; height: 22px; float: left;" onclick="getOpen1()">
				</div>
				<img alt="" src="images/today.png" title="Today" style="margin-top:63px; margin-left: -320px; height: 22px; float: left;" onclick="getToday1('today')">
				<img alt="" src="images/tomorrow.png" title="Tomorrow" style="margin-top:63px; margin-left: -290px; height: 22px; float: left;" onclick="getToday1('tomorrow')">
				</div>
				</td>
				
				 <td>
		  	  	<label for="docIdTo" style="font-size: 15px">Remarks</label>
				</td>
				<td title="Remarks">
			 	 <div class="newColumn" style="width: 99%">
					<div class="rightColumn" style="width: 93%">
								<s:textarea id="remarksId" name="remarks"  cssClass="textField" theme="simple" placeHolder="Enter remarks"  />
					</div>
				 </div>
				 </td>
				 
		 	 	</tr>
		 	 	
		<!-- </tr> -->
	</table>	 
	<br></br>
	<div class="newColumn">
	                   <div class="leftColumn">Service Manager:</div>
                       <div class="rightColumn"> 
                               <s:select 
	                                  name="service_manager"  
	                                  id="service_manager"
				                      list="serviceManager" 
				                      headerKey="%{emp_id}"
	                               	  headerValue="%{empName}"
				                      cssClass="textField" 
	                                  theme="simple"
				                      cssStyle="width:82%;"
				                      multiple="false"
	                           />
                       </div>
                       </div>
	<div class="clear"></div>
		<div class="clear" style="margin-top: 1px;margin-bottom:2px;" ></div>
		<center>
			<div class="type-button">
				<sj:submit targets="resultEdit" clearForm="false" value="Modify Patient"
					effect="highlight" effectOptions="{ color : '#FFFFFF'}"
					effectDuration="100" button="true" onCompleteTopics="level123" cssStyle="height:26px;"
					  cssClass="submit" />

				&nbsp;&nbsp;
				<sj:submit value="Back" button="true"  
					cssStyle="margin-left: -11px;margin-top: 0px;height:26px;"/>

			</div>
			<sj:div id="orglevelEdit"  effect="fold">
                   <sj:div  id="resultEdit"></sj:div></sj:div>    
		</center>
		 
 		     
	</s:form>
</div>
</html>
