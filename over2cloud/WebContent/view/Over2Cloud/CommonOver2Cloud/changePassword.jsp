<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:if test="%{changeType=='header'}">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
</s:if>
<SCRIPT type="text/javascript">

$.subscribe('validate', function(event,data)
{
	//alert("hh");
    var mystring = $(".pIds").text();
    var fieldtype = mystring.split(",");
    var pattern = /^\d{10}$/;
    for(var i=0; i<fieldtype.length; i++)
    {
        
        var fieldsvalues = fieldtype[i].split("#")[0];
        var fieldsnames = fieldtype[i].split("#")[1];
        var colType = fieldtype[i].split("#")[2];   //fieldsType[i]=first_name#t
        var validationType = fieldtype[i].split("#")[3];
          
        $("#"+fieldsvalues).css("background-color","");
        errZone.innerHTML="";
        if(fieldsvalues!= "" )
        {
            if(colType=="D")
            {
           
            if($("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
            {
            errZone.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;    
              }
            }
            else if(colType =="T")
            {
            if(validationType=="n"){
            var numeric= /^[0-9]+$/;
            if(!(numeric.test($("#"+fieldsvalues).val()))){
            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Numeric Values of "+fieldsnames+"</div>";
            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;
              }    
             }
            else if(validationType=="an"){
             var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
            if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;
              }
            }
            else if(validationType=="a"){
            if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val()))){
            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;     
              }
             }
            else if(validationType =="sc")
            {
          	  if($("#"+fieldsvalues).val().length < 1)
		             {
		                errZone.innerHTML="<div class='user_form_inputError2'>Please Enter "+fieldsnames+" !!! </div>";
		                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		                $("#"+fieldsvalues).focus();
		                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		                event.originalEvent.options.submit = false;
		                break;
		              }
             }
            else if(validationType=="aNewPwd"){
                if(!(/^[a-zA-Z !@#$%^&*/\()0123456789]+$/.test($("#"+fieldsvalues).val()))){
                errZone.innerHTML="<div class='user_form_inputError2'>Please Enter  "+fieldsnames+"</div>";
                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
                $("#"+fieldsvalues).focus();
                $("#"+fieldsvalues).css("background-color","#ff701a");
                event.originalEvent.options.submit = false;
                break;     
                  }
                 }
            else if(validationType=="aOldPwd"){
                if(!(/^[a-zA-Z !@#$%^&*()0123456789]+$/.test($("#"+fieldsvalues).val()))){
                errZone.innerHTML="<div class='user_form_inputError2'>Please Enter  "+fieldsnames+"</div>";
                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
                $("#"+fieldsvalues).focus();
                $("#"+fieldsvalues).css("background-color","#ff701a");
                event.originalEvent.options.submit = false;
                break;     
                  }
                 }
            
            else if(validationType=="m"){
           if($("#"+fieldsvalues).val().length > 10 || $("#"+fieldsvalues).val().length < 10)
            {
                errZone.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                $("#"+fieldsvalues).focus();
                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
                event.originalEvent.options.submit = false;
                break;
            }
            else if (!pattern.test($("#"+fieldsvalues).val())) {
                errZone.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                $("#"+fieldsvalues).focus();
                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
                event.originalEvent.options.submit = false;
                break;
             }
                else if(($("#"+fieldsvalues).val().charAt(0)!="9") && ($("#"+fieldsvalues).val().charAt(0)!="8") && ($("#"+fieldsvalues).val().charAt(0)!="7") && ($("#"+fieldsvalues).val().charAt(0)!="6"))
             {
                errZone.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
                $("#"+fieldsvalues).focus();
                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
                event.originalEvent.options.submit = false;
                break;
               }
             } 
             else if(validationType =="e"){
             if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val())){
             }else{
            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
            $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
            $("#"+fieldsvalues).focus();
            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
            event.originalEvent.options.submit = false;
            break;
               } 
             }
             else if(validationType =="w"){
             
             
             
             }
           }
           
            else if(colType=="TextArea"){
            if(validationType=="an"){
            var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
            if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;
               }
             }
            else if(validationType=="mg"){
             
             
             }    
            }
            else if(colType=="Time"){
            if($("#"+fieldsvalues).val()=="")
            {
            errZone.innerHTML="<div class='user_form_inputError2'>Please Select Time "+fieldsnames+" Value </div>";
            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;    
              }    
            }
            else if(colType=="Date"){
            if($("#"+fieldsvalues).val()=="")
            {
            errZone.innerHTML="<div class='user_form_inputError2'>Please Select Date "+fieldsnames+" Value  </div>";
            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
            $("#"+fieldsvalues).focus();
            $("#"+fieldsvalues).css("background-color","#ff701a");
            event.originalEvent.options.submit = false;
            break;    
              }
             }  
        }
    }        
});


$.subscribe('level1', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none"; tabConfig
	         setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
	         $('select').find('option:first').attr('selected', 'selected');
	       });


function getContactNamesForUser(subGroupId,divId)
{
			$.ajax({
				type :"post",
				url :"view/Over2Cloud/hr/getContBySubGroupIdForReset.action",
				data:"subGroupId="+subGroupId,
				success : function(empData){
				$('#'+divId+' option').remove();
				$('#'+divId).append($("<option></option>").attr("value",-1).text("Select Contact"));
		    	$(empData).each(function(index)
				{
				   $('#'+divId).append($("<option></option>").attr("value",this.id).text(this.name));
				});
			    },
			    error : function () {
					alert("Somthing is wrong to get Data");
				}
			});
}

function reset(formId) 
{
	  $("#"+formId).trigger("reset"); 
	}

</script>
</head>
<s:if test="%{changeType=='header'}">
	<div class="list-icon">
	<div class="head">User</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Reset Password</div>
	</div>
</s:if>
<div class="clear"></div>
	<div style="min-height: 10px; height: auto; width: 100%;"></div>
		<div class="border">
          <s:form id="formone111111" name="formone" namespace="/view/Over2Cloud/commonModules" action="changePwd" theme="css_xhtml"  method="post"enctype="multipart/form-data" >

            <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px; margin-bottom: 23px;">
                  <div id="errZone" style="float:left; margin-left: 7px"></div>        
           </div>
           <s:hidden name="changeType" value="%{changeType}"></s:hidden>
                   <s:if test="%{hod}">
                     <div class="newColumn">
	             	     <div class="leftColumn1">Name:</div>
						 <div class="rightColumn1">
						  <span id="normalSubdept" class="pIds" style="display: none; ">empName#empName#D#,</span>
		                  <span class="needed"></span>
		                 
		                  <s:select 
		                              cssClass="select"
	                                  cssStyle="width:82%"
		                              id="empName"
		                              name="empName" 
		                              list="getAllDept"
		                              headerKey="-1"
		                              headerValue="Select Employee Name" 
		                               cssClass="textField"
						               cssStyle="width:82%"
		                              > 
		                  </s:select>
		                  </div>
	                  </div>
	                  
	                  <div class="newColumn">
	                     <div class="leftColumn1">New Password:</div>
					     <div class="rightColumn1">
					      <span id="normalSubdept" class="pIds" style="display: none; ">newPwd#newPwd#T#sc#,</span>
					     <span class="needed"></span>
					     <s:password name="newPwd"  id="newPwd"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
				      </div> 
					  </div>
					  
					  </s:if>
					  
					  
					  <s:if test="%{mgmt}">
					    <s:hidden name="userType" value="false"></s:hidden>
					   <div class="newColumn">
             	 <div class="leftColumn1">Contact Sub-Type:</div>
				<div class="rightColumn1">
                  <span id="normalSubdept" class="pIds" style="display: none; ">dept#Department#D#,</span>
                  <span class="needed"></span>
             	 <div id="subDeptId">
                  <s:select 
                              id="dept"
                              name="dept"
                              list="getAllDept"
                              headerKey="-1"
                              headerValue="Select Department" 
                              cssClass="textField"
                              cssStyle="width:82%"
                              onchange="getContactNamesForUser(this.value,'empName');"
                              >
                  </s:select>
                  </div>
                  </div>
                  </div>
                   <div class="newColumn">
             	  <div class="leftColumn1">Name:</div>
					 <div class="rightColumn1">
					  <span id="normalSubdept" class="pIds" style="display: none; ">empName#empName#D#,</span>
	                  <span class="needed"></span>
	                  <div id="AjaxDivp">
	               <s:select id="empName"
						name="empName" list="#{'-1':'Select'}" headerKey="-1"
						headerValue="Select Employee" cssClass="textField">

					</s:select>
	                  </div>
	                  </div></div>
	                  
	                  
	                  <div class="newColumn">
	                     <div class="leftColumn1">New Password:</div>
					     <div class="rightColumn1">
					    <span id="normalSubdept" class="pIds" style="display: none; ">newPwd#New Password#T#sc#,</span>
					     <span class="needed"></span>
					     <s:password name="newPwd"  id="newPwd"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"/>
				      </div> 
					  </div>
					  
					  </s:if>
					  <s:if test="%{normalUser}">
					   <s:hidden name="empName" value="%{empName}"></s:hidden>
					  <div class="newColumn">
	                     <div class="leftColumn1">Old Password</div>
					     <div class="rightColumn1">
					      <span id="normalSubdept" class="pIds" style="display: none; ">oldPwd#Old Password#T#sc#,</span>
					      <span class="needed"></span>
					     <s:password name="oldPwd"  id="oldPwd"  cssClass="textField" placeholder="Old Password" cssStyle="margin:0px 0px 10px 0px"/>
				      </div> 
					  </div>
					  
					   <div class="newColumn">
	                     <div class="leftColumn1">New Password:</div>
					     <div class="rightColumn1">
					      <span id="normalSubdept" class="pIds" style="display: none; ">newPwd#New Password#T#sc#,</span>
					     <span class="needed"></span>
					     <s:password name="newPwd"  id="newPwd"  cssClass="textField" placeholder="New Password" cssStyle="margin:0px 0px 10px 0px"/>
				      </div> 
					  </div>
					  </s:if>
					  <div class="clear"></div>
					   <div class="newColumn">
 			 <div class="leftColumn" style="margin-top: -8px;">SMS:</div>
		     <div class="rightColumn">
		     	   <s:checkbox id="smsNotification" name="smsNotification" checked="checked"></s:checkbox>
		     </div>
		     </div> 
		     
		     <div class="newColumn">
		     <div class="leftColumn" style="margin-top: -8px;">Email:</div>
		     <div class="rightColumn">
		     	   <s:checkbox id="emailNotification" name="emailNotification" checked="checked"></s:checkbox>
		     </div>
		     </div>
					  
             	 <div class="clear">
			    <center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
					<div class="fields" align="center">
	               <center> <sj:submit 
	                        targets="orglevel1" 
	                        clearForm="true"
	                        value="  Save " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="level1"
	                        cssClass="submit"
	                        indicator="indicator2"
	                        onBeforeTopics="validate"
	                        cssStyle="margin-right: 40px;"
	                        />
	                        <div id=reset11 style="margin-top: -27px; margin-left: 127px;">
	                        	<sj:a 
							 button="true" href="#"
							 onclick="reset('formone111111');"
							 cssClass="submit"
						>
						
						Reset
					</sj:a>
	                        </div>
	                    
					</center>
	               </div>
				<sj:div id="orglevel1Div"  effect="fold">
                    <div id="orglevel1"></div>
               </sj:div>
             

</div>	
</s:form>
</div>
</html>