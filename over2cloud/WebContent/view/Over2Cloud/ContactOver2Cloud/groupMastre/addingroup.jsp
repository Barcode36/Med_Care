<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s"  uri="/struts-tags"%>
    <%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
	<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="/js/common/commonvalidation.js"/>"></script>
  <script type="text/javascript">
$.subscribe('result', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#addgroup").fadeIn(); }, 10);
	         setTimeout(function(){ $("#addgroup").fadeOut(); }, 4000);
	       });
	       
function resetForm(formId)
{
	$('#'+formId).trigger("reset");
}


	       </script>
	       
	   <script>

	 function validationField(){
		$("#createnew_group").prop('readonly', 'true');
     }
      function validatdrowdown(){
         if($("#createnew_group").val()!="" ){
		$("#groupName").prop('disabled', 'true');
		}
		
     }
</script>


	
<script type="text/javascript">
	//Conatct masters works STARTS from here
	    function addingroup() {
	    var myArray = $('#check_list').val();
	    alert("myArray"+myArray);
    	 if(myArray!=null && myArray!=""){
    	 var  select_param = $("#select_param").val();
    	 var  createnew_group = $("#createnew_group").val();
    	 alert("createnew_group"+createnew_group);
    	 var  group_Name = $("#groupName").val();
    	 alert("group_Name"+group_Name);
    	 var  notes = $("#notes").val();
    	 alert("notes"+notes);
    	 var conP = "<%=request.getContextPath()%>";
		$.ajax({
		     type : "post",
		     url : conP+"/view/Over2Cloud/CommunicationOver2Cloud/Comm/addingroupdata?id="+myArray+"&createnew_group="+createnew_group+"&group_name="+group_Name+"&notes="+notes,
		    success : function(subdeptdata) {
	       $("#"+"responceMessage").html(subdeptdata);
	         setTimeout(function(){ $("#responceMessage").fadeIn(); }, 10);
	         setTimeout(function(){ $("#responceMessage").fadeOut(); }, 4000);
	      
		},
		   error: function() {
	            alert("error");
	        }
		 });
      }else{
    	  alert("Please Select Row");
      }
	}
</script>

	  <script type="text/javascript">
         function groupbackView(){
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
					$.ajax({
					    type : "post",
					    url : "view/Over2Cloud/CommunicationOver2Cloud/Comm/groupdetailsview?",
					    success : function(subdeptdata) {
				       $("#"+"data_part").html(subdeptdata);
					},
					   error: function() {
				            alert("error");
				        }
					 });
				}

</script> 
<script type="text/javascript">
function changeGroupname(divId,param){
	
	if(param!='all_contactType'){
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CommunicationOver2Cloud/Comm/getViaAjaxcontactsubType.action?select_param="+param,
	    success : function(Data){
			$('#'+divId+' option').remove();
			$('#'+divId).append($("<option></option>").attr("value",-1).text("Select Sub Contact Type"));
	    	$(Data).each(function(index)
			{
			   $('#'+divId).append($("<option></option>").attr("value",this.id).text(this.name));
			});
		    },
	   error: function() {
	        alert("error");
	    }
	 });
	}
}

function changsubcontactType(divId,param){
	if(param!='all_contactSubType'){
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CommunicationOver2Cloud/Comm/getViaAjaxcontactType.action?select_param="+param,
	    success : function(saveData){
	    	$("#clienttddddd").html(saveData);
		    },
	   error: function() {
	        alert("error");
	    }
	 });
	}
}

</script>
     
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<s:hidden id="app_id" value="%{#parameters.app_id}"></s:hidden>
<div class="clear"></div>
<div class="middle-content">
	<div class="list-icon">
		<div class="head">Group</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div>
	</div>
	<div class="clear"></div>
	<div style="min-height: 10px; height: auto; width: 100%;"></div>
	<div class="border">
		<div style="width: 100%; text-align: center; padding-top: 10px;">
							<div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
  <div id="errZone" style="float:left; margin-left: 7px"></div>  
        </div>
        <div class="clear"></div>
        <br>
<s:form id="form" name="form"  namespace="/view/Over2Cloud/CommunicationOver2Cloud/Comm" action="addingroupdata" theme="simple"  method="post"enctype="multipart/form-data" >
              <span id="mandatoryFields" class="pIds" style="display: none; ">contacttype#Contact Type#D#a,</span>
                <div class="newColumn">
								<div class="leftColumn1">Contact Type: </div>
								<div class="rightColumn">
								<span class="needed"></span>
										 <s:select 
				                              id="contacttype"
				                              name="contacttype"
				                              list='groupListData'
				                              headerKey="-1"
				                              headerValue="-Select Contact Type-" 
				                              cssClass="select"
				                              onchange="changeGroupname('subgroupName',this.value);"
				                             cssStyle="width:75%"
											>
						                 </s:select>
						           
								</div>
							</div>
							 <span id="mandatoryFields" class="pIds" style="display: none; ">subgroupName#Contact Sub Type#D#a,</span>
							<div class="newColumn">
								<div class="leftColumn1">Contact Sub Type: </div>
								<div class="rightColumn">
								<span class="needed"></span>
										 <s:select 
				                              id="subgroupName"
				                              name="subgroupName"
				                              list="#{'-1':'-Select Contact Sub-Type-'}"
				                              headerKey="-1"
				                              cssClass="select"
				                              onchange="changsubcontactType('cotactid',this.value);"
				                             cssStyle="width:75%"
											>
						                 </s:select>
						           
			                  		<div id="errorlevel2org" class="errordiv"></div>
								</div>
							</div>
								
							<div id="clienttddddd">
				<div class="newColumn">
								<div class="leftColumn1">Contact Person:</div>
								<div class="rightColumn">
										 <s:select 
				                              id="cotactid"
				                              name="cotactid"
				                              list="#{'-1':'-Select Contact Person-'}"
				                              headerKey="-1"
				                              cssClass="select"
				                              cssStyle="width:74%"
											>
						                 </s:select>
						           
			                  		<div id="errorlevel2org" class="errordiv"></div>
															</div>
							</div>
							</div>
															<span id="mandatoryFields" class="pIds" style="display: none; ">group_name#Select Group#D#a,</span>
							
							<div class="newColumn">
								<div class="leftColumn1">Select Group: </div>
								<div class="rightColumn">
								<span class="needed"></span>
										 <s:select 
				                              id="group_name"
				                              name="group_name"
				                              list='groupNameListData'
				                              headerKey="-1"
				                              headerValue="-Select Group Name-" 
				                              cssClass="select"
				                              cssStyle="width:72%"
				                              onclick="validationField();"
											>
						                 </s:select>
						           
								</div>
							</div>  
								<div class="newColumn">
						<div class="leftColumn1">Comment :</div>
						<div class="rightColumn">
					     <span class="needed"></span>
					    <s:textarea name="notes" id="notes"  rows="5" cols="80" cssStyle="margin:0px 0px 10px 0px"  />
						</div>
					     </div>
				
                <div class="clear"></div>
		<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
			<div class="type-button" style="margin-left: -200px;">
			<center>

			<sj:submit 
          			   targets="groupadd" 
                       clearForm="true"
                       value="Add" 
                       effect="highlight"
                       effectOptions="{ color : '#222222'}"
                       effectDuration="5000"
                       button="true"
                       onCompleteTopics="result"
                       cssClass="submit"
                       cssStyle="margin-right: 2px;margin-bottom: 29px;"
                       indicator="indicator2"
                       onBeforeTopics="validate"
                       openDialog="takeActionGrid"
         />
         <sj:a 
				     	href="#" 
		               	button="true" 
		               	onclick="resetForm('form');"
						cssStyle="margin-top: -28px;"						>
					  	Reset
		   </sj:a>
		   
		   <sj:a 
				     	href="#" 
		               	button="true" 
		               	onclick="groupbackView();"
						cssStyle="margin-right: -137px;margin-top: -28px;"
						>
					  	Back
		   </sj:a>
           <sj:div id="addgroup"  effect="fold">
                    <div id="groupadd"></div>
               </sj:div>
		  </center> 
      
    </s:form>

</div>

</div>
<center>
<div id="responceMessage" >
<div  id="ajaxdivid">
</div>
</div>
</center>
</div>
</body>
</html>