<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="/js/organization.js"/>"></script>
<script src="<s:url value="/js/common/commonvalidation.js"/>"></script>
<script src="<s:url value="/js/employeeOver2Cloud.js"/>"></script>
<SCRIPT type="text/javascript" src="js/common/commonContact.js"></SCRIPT>
<script type="text/javascript" src="js/hr/commonHr.js"></script>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<SCRIPT type="text/javascript">
function getUserAvailabilty(userName)
{
    var conP = "<%=request.getContextPath()%>";
      if(userName!="")
    {
         document.getElementById("indicator2").style.display="block";
         $.getJSON(conP+"/view/Over2Cloud/hr/checkUserName.action?userName="+userName,
            function(checkEmp){
        	 document.getElementById("indicator2").style.display="none";
        	 if(checkEmp.msg=='You can create User')
        		 {
        		 $("#userStatus1").html(checkEmp.msg);
        		 $("#userStatus").html("");
        		 }
        	 else
        		 {
                 $("#userStatus").html(checkEmp.msg);
                 $("#userStatus1").html("");
                 $("#userID").val("");
         		}
                
        });
    }
}
function reset(formId) 
{
	 $("#userStatus1").html("");
	 $("#userStatus").html("");
	 $("#userPass").html("");
      $("#"+formId).trigger("reset"); 
}
function checkPassword() 
{
	var pass= $("#password").val();
	var repass= $("#repassword").val();
	 document.getElementById("indicator3").style.display="none";
     
 	if(pass==repass)
	 $("#userPass").html("");
	else
		{
		$("#userPass").html("Password Mismatch");
		$("#repassword").val("");
		}
} 
function clearRePassword() 
{
 	  $("#repassword").val("");
 	 $("#userPass").html("");
	 
} 
$.subscribe('checkMe', function(event,data)
{
    var ctrl = $('select').attr("selectedIndex").text();
    alert(ctrl.length);   
    alert(ctrl);   
    event.originalEvent.options.submit = false;        
      
});
$.subscribe('level11', function(event,data)
        {
          setTimeout(function(){ $("#orglevel12").fadeIn(); }, 10);
          setTimeout(function(){ $("#orglevel12").fadeOut(); }, 4000);
          $('select').find('option:first').attr('selected', 'selected');
         
        });
var test;
        function getModuleName(name)
        {
            //alert("in get module");
              test=$(name).attr("id");
            //alert(test);
        }
$('input[name="ADD"]').on('change', function () 
{
    
     
     if(this.checked) { // check select status
         $('.case_ADD').each(function() { //loop through each checkbox
             this.checked = true;  //select all checkboxes with class "checkbox1"               
         });
     }else{
         $('.case_ADD').each(function() { //loop through each checkbox
             this.checked = false; //deselect all checkboxes with class "checkbox1"                       
         });         
     } 
   // $('input[name="reon_ADD"],input[name="surt_ADD"]')
        
       // .prop('checked' ,this.checked);
  /*   var sel = $('input[type=checkbox]:checked').map(function (_ADD, el) {
        return $(el).val();
    }).get();

    alert(sel); */
    //$('input[type="checkbox"]').not(this).prop("checked", this.checked);
 //   $('.orgn_ADD').attr('checked', this.checked);
 //on click 
           /*  if(this.checked) { // check select status
                $('.case_ADD').each(function() { //loop through each checkbox
                    this.checked = true;  //select all checkboxes with class "checkbox1"               
                });
            }else{
                $('.case_ADD').each(function() { //loop through each checkbox
                    this.checked = false; //deselect all checkboxes with class "checkbox1"                       
                });         
            } */
    
    
});
$(".chkbox").change(function() {
    var val = $(this).val();
  if( $(this).is(":checked") ) {
    
    $(":checkbox[value='"+val+"']").attr("checked", true);
  }
    else {
        $(":checkbox[value='"+val+"']").attr("checked", false);
    }
});





 
        function demo() {
            //alert("?????");
            
        //clicking the parent checkbox should check or uncheck all child checkboxes
        $("."+test+"_ADD").click(
                
        function () {
            alert("dgfvdbfg");
            $(this).parents('fieldset:eq(0)').find('.'+test+'_ADDCHILD').prop('checked', this.checked);
        });
        //clicking the last unchecked or checked checkbox should check or uncheck the parent checkbox
        $('.'+test+'_ADDCHILD').click( 
        function () {
            if ($(this).parents('fieldset:eq(0)').find('.'+test+'_ADD').prop('checked') == true && this.checked == false) $(this).parents('fieldset:eq(0)').find('.'+test+'_ADD').prop('checked', false);
            if (this.checked == true) {
                var flag = true;
                $(this).parents('fieldset:eq(0)').find('.'+test+'_ADDCHILD').each( 
                function () {
                    if (this.checked == false) flag = false;
                });
                $(this).parents('fieldset:eq(0)').find('.'+test+'_ADD').prop('checked', flag);
            }
        });
    }
function onload()
{
     $("#rpass_VIEW").attr("checked","checked");
}
onload();
</script>
</head>
<body>
<div class="clear"></div>

<div class="middle-content">

    <div class="list-icon">
    <div class="head">User</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div>
    </div>
    <div class="clear"></div>
    <div style="min-height: 10px; height: auto; width: 100%;"></div>
    <div class="border">
        <div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
          <div id="errZone" style="float:left; margin-left: 7px"></div>        
        </div>
          <div class="clear"></div>
  <div>
    <s:form id="formone" name="formone" namespace="/view/Over2Cloud/hr" action="createUser" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
						 <s:if test="%{mgtStatus}">
						  <div class="newColumn">
               				 <div class="leftColumn">Country Office:</div>
               		 			<div class="rightColumn">
                           <s:select 
		                              id="office"
		                              list="officeMap"
		                              headerKey="-1"
		                              headerValue="Select Country Office" 
		                              cssClass="select"
                       				  cssStyle="width:82%"
		                              theme="simple"
		                              onchange="getheadoffice(this.value,'headOfficeId');"
		                              >
		                  </s:select>
		                  </div>
		                  </div>
		                    <div class="newColumn">
               				 <div class="leftColumn">Head Office:</div>
               		 			<div class="rightColumn">
									<s:select 
					                              id="headOfficeId"
					                              list="{'No Data'}"
					                              headerKey="-1"
					                              headerValue="Select Head Office" 
					                              cssClass="select"
			                      				 cssStyle="width:82%"
					                              theme="simple"
					                              onchange="getbranchoffice(this.value,'regLevel')"
					                              >
					                  </s:select>
		                  </div>
		                  </div>
		                  </s:if>
		                  <s:if test="%{hodStatus}">
		                    <div class="newColumn">
               				 <div class="leftColumn">Branch Office:</div>
               		 			<div class="rightColumn">
		                  		<s:select 
		                              id="regLevel"
		                              name="regLevel"
		                              list="officeMap"
		                              headerKey="-1"
		                              headerValue="Select Branch Office" 
		                              cssClass="select"
                      				  cssStyle="width:82%"
		                              onchange="getGroupNamesForMappedOffice('groupId',this.value);"
		                              >
		                  </s:select>
                 </div>
                 </div>
                 </s:if>
                 <div class="newColumn">
                <div class="leftColumn">Contact Type:</div>
                <div class="rightColumn">
                <s:select 
                       id="groupId"
                       name="groupId" 
                       list="officeMap"
                       headerKey="-1"
                       headerValue="Select Contact Type" 
                       cssClass="select"
                       cssStyle="width:82%"
                       onchange="getsubGroupByGroup('subGroupId',this.value);"
                       >
                </s:select>
                </div>
                 </div>
                 
                 <div class="newColumn">
                <div class="leftColumn">Contact Sub-Type:</div>
                <div class="rightColumn">
                <s:select 
                       id="subGroupId"
                       name="subGroupId" 
                       list="{'No Data'}"
                       headerKey="-1"
                       headerValue="Select Contact Sub-Type" 
                       cssClass="select"
                       cssStyle="width:82%"
                       onchange="getContactNamesForUser(this.value,'contactName');"
                       >
                </s:select>
                </div>
                 </div>
                 
                 <div class="newColumn">
                <div class="leftColumn">Name:</div>
                <div class="rightColumn">
                <s:select 
                       id="contactName"
                       name="contactName" 
                       list="{'No Data'}"
                       headerKey="-1"
                       headerValue="Select Contact" 
                       cssClass="select"
                       cssStyle="width:82%"
                       onchange="getEmpDetails(this.value)"
                       >
                </s:select>
                </div>
                 </div>
                 
                    <s:hidden name="mobileNo" id="mobileNo" cssClass="textField" maxlength="10" placeholder="Auto Mobile No" readonly="true"/>
                    <s:hidden name="name" id="name" cssClass="textField" maxlength="10" placeholder="Auto Mobile No" readonly="true"/>
                
                <div class="newColumn">
                <div class="leftColumn">User Type:</div>
                <div class="rightColumn">
                <s:select 
                    id="userType" 
                    name="userType"
                   list="#{'N':'Normal','H':'HOD','M':'Management'}"
                    headerKey="N" 
                    headerValue="Select User Type"
                    cssClass="select"
                    cssStyle="width:82%"
                    >
                </s:select>
                </div>
                 </div>
     
                 <div class="newColumn">
                <div class="leftColumn">Login ID:</div><span id="mandatoryFields" class="pIds" style="display: none; ">userID#userID#T#a,</span>
                <div class="rightColumn"><SPAN class="needed"></SPAN>
                    <s:textfield name="userID" id="userID"  cssClass="textField" maxlength="100" placeholder="Enter User Name" onchange="getUserAvailabilty(this.value);"/>
                       <img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none;"/>
                      <div style=" color: red; " id="userStatus" class="user_form_text"></div>
                      <div  id="userStatus1" class="user_form_text"></div>
                      
                </div>
                 </div>
                 
              <div class="newColumn">
              <div class="leftColumn">Password:</div> <span id="mandatoryFields" class="pIds" style="display: none; ">password#Password#T#ans,</span>
               <div class="rightColumn"><SPAN class="needed"></SPAN>
                     <s:password name="password" id="password" cssClass="textField" maxlength="100" onchange="clearRePassword();" placeholder="Enter Password"/>
               </div>
               </div>
               
               <div class="newColumn">
              <div class="leftColumn">Retype Password:</div><span id="mandatoryFields" class="pIds" style="display: none; ">repassword#Retype Password#T#ans,</span>
               <div class="rightColumn"><SPAN class="needed"></SPAN>
                     <s:password name="repassword" id="repassword" cssClass="textField" maxlength="100" onchange="checkPassword();" placeholder="Enter Retype Password"/></div>
                  <img id="indicator3" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none;"/>
                      <div style=" color: red;  margin-left: 376px; " id="userPass"></div>
                  
               </div>
              <div class="newColumn">
              <div class="leftColumn" style="margin-top: -8px;">SMS:</div>
             <div class="rightColumn">
                    <s:checkbox id="smsNotification" name="smsNotification"></s:checkbox>
             </div>
             </div> 
             
             <div class="newColumn">
             <div class="leftColumn" style="margin-top: -8px;">Email:</div>
             <div class="rightColumn">
                    <s:checkbox id="emailNotification" name="emailNotification"></s:checkbox>
             </div>
             </div>
              
     <br>
      <div class="clear"></div>
<div class="secHead">User Rights</div>
    <s:include value="./userRights.jsp"></s:include>
    <div class="fields" align="center">
                    <sj:submit 
                            targets="level123" 
                            clearForm="true"
                            value="Save" 
                            effect="highlight"
                            effectOptions="{ color : '#222222'}"
                            effectDuration="5000"
                            button="true"
                            onCompleteTopics="level11"
                            onBeforeTopics="validate"
                            cssClass="submit"
                            indicator="indicator22"
                          
                            />
                            
                                   <div id=reset style="margin-top: -28px; margin-left: 209px;"> 
                          <sj:a 
                             button="true" href="#"
                             onclick="reset('formone');"
                             cssClass="submit"
                        >
                        
                        Reset
                    </sj:a>
                          <sj:a 
                            button="true" href="#"
                            onclick="cancelusermaster();"
                            cssClass="submit"
                        >
                        
                        Back
                    </sj:a>
                    </div>
                   </div>
                   
                   <center><img id="indicator22" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
                   
                   <sj:div id="orglevel12"  effect="fold">
                   <sj:div align="center" id="level123"></sj:div></sj:div>    
</s:form>
</div>
</div>
</div></body>
</html>