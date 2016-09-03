<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<script type="text/javascript">
$.subscribe('makeEffect', function(event,data)
		  {
			 setTimeout(function(){ $("#resultTarget").fadeIn(); }, 10);
			 setTimeout(function(){ $("#resultTarget").fadeOut(); }, 400);
			 pharmacyAlert();
		  });


$(document).ready(function()
		{
		$("#employee").multiselect({
			   show: ["", 200],
	 		   hide: ["explode", 1000]
			});
		});
	 
function pharmacyAlert()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CorporatePatientServices/Pharmacy/viewPharmacyAlert.jsp",
	    success : function(feeddraft) {
       $("#"+"data_part").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function getSubDepartment(deptId){
	  $.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CorporatePatientServices/Pharmacy/fetchSubdepartmentList.action?deptId="+deptId,
	    success : function(data) {
	    	console.log(data);
       		$('#escsubDept option').remove();
      		$('#escsubDept').append($("<option></option>").attr("value","All").text("Select Sub Department"));
          	$(data).each(function(index)
      		{
          	//alert(this.id);	
      		   $('#escsubDept').append($("<option></option>").attr("value",this.id).text(this.name));
      		});
       		
       		},
	    error: function() {
            alert("error");
        }
	 });
}

function fetchEmpList(){
	var deptId = $("#escDept").val();
	var subDeptId = $("#escsubDept").val();
	  $.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CorporatePatientServices/Pharmacy/fetchEmpList.action?deptId="+deptId+"&subDeptId="+subDeptId,
	    success : function(data) {
		  $("#"+"employeeDiv").html(data);
     		
     		},
	    error: function() {
          alert("error");
      }
	 });
}

</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Pharmacy Alert</div><img alt="" src="images/forward.png" style="margin-top:10px; float: left;"><div class="head"> Add</div> 
	  
</div>
<div class="clear"></div>
<div style="overflow:auto; height:450px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">

<s:form id="formone" name="formone" action="addEmpForPharmacyAlert" theme="simple"  method="post" enctype="multipart/form-data" >

<center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone" style="float:center; margin-left: 7px"></div></div></center>  

				
				<div class="newColumn">
        			   <div class="leftColumn" >Department:&nbsp;</div>
					        <div class="rightColumn">
				                  <s:select  id="escDept"
				                              name="escDept"
				                              list="serviceDeptMap"
				                              headerKey="-1"
				                              headerValue="Select Department" 
				                              cssClass="select"
					                          cssStyle="margin-left: 3px;width: 243px;height: 30px;"
					                          onchange="getSubDepartment(this.value)"
					                         
				                              >
				                  </s:select>
                           </div>
                   </div>
                   	<div class="newColumn">
        			   <div class="leftColumn" >Sub Department:&nbsp;</div>
					        <div class="rightColumn">
				                  <s:select  id="escsubDept"
				                              name="escsubDept"
				                              list="{'No Data'}"
				                              headerKey="All"
				                              headerValue="Select Sub Department" 
				                              cssClass="select"
					                          cssStyle="margin-left: 3px;width: 243px;height: 30px;"
					                          onchange="fetchEmpList()"
				                              >
				                  </s:select>
                           </div>
                   </div> 
                   
                   
                      	<div class="newColumn">
        			   <div class="leftColumn" >Employee:&nbsp;</div>
					        <div class="rightColumn">
					        <div id="employeeDiv">
				                   <s:select 
				                   id="employee"
				                   name="employee" 
				                   list="{'No Data'}" 
				                   cssClass="select"
								   cssStyle="width:245px;height:40%" 
								   multiple="true" 
							 
									>
									</s:select>
                           </div>
                           </div>
                   </div>
    		 
    			  
          <div class="clear"> </div>   
		 <div class="fields" align="center">
		 <ul>
			<li class="submit" style="background: none;">
				<div class="type-button">
	        	<sj:submit 
         				targets			=	"resultTarget" 
         				clearForm		=	"true"
         				value			=	" Save " 
         				button			=	"true"
         				cssClass		=	"submit"
                 		indicator		=	"indicator2"
                 		effect			=	"highlight"
	                    effectOptions	=	"{ color : '#222222'}"
                 		effectDuration	=	"5000"
                 		onCompleteTopics=	"makeEffect"
                        indicator		=	"indicator2"
              
                        
     		  	  />
     		  	  <sj:a 
						button="true" href="#"
						onclick="resetForm('formone');"
						>
						Reset
				  </sj:a>
     		  	  <sj:a 
						button="true" href="#"
						onclick="pharmacyAlert();"
						>
						Back
					</sj:a>
	        </div>
	        </li>
		 </ul>
		 <sj:div id="resultTarget"  effect="fold">
   	     </sj:div>
		 </div>
</s:form>
</div>
</div>
</body>
</html>