<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>

<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/communication/CommunicationInstantMessage.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/communication/CommunicationScheduleMessage.js"/>"></script>
<script src="<s:url value="/js/common/instantacommanvalidation.js"/>"></script>
<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>



<script>
$(document).ready(function(){
$("#groupName").multiselect();
});
</script>
<SCRIPT type="text/javascript">
$.subscribe('level1', function(event,data)
	       {
	         setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
	       });

function getSubDept(deptId,destAjaxDiv,deptLevel,subdeptType) {
	var dept=$("#"+deptId).val();
	if (deptLevel=='2') {
    if (subdeptType=='1') {
	$.ajax({
		type :"post",
		url :"view/Over2Cloud/CommunicationOver2Cloud/Comm/subDeptViaAjax.action?destination="+ destAjaxDiv+"&dept="+deptId,
		success : function(subDeptData){
		$("#" + destAjaxDiv).html(subDeptData);
	    },
	    error : function () {
			alert("Somthing is wrong to get Sub Department");
		}
	});
    }
   
	}
}

function changecontact()
{
	
	if(formtwo.mappedid.value != -1)
	{
		if(formtwo.mappedid.value == -2)
		{
			alert('This is a Category, Instead Please Select Group Name.');
			formtwo.mappedid.selectedIndex = 0;
		}
		else
		{
			formtwo.groupName.disabled = true;
		}	
	}
	else
	{
		formtwo.groupName.disabled = false;
	}
}

//disable Contact name.
function changeGroup11()
{
	//alert('changeEmp:frm.grp_name.value'+frm.grp_name.value);
	if( formtwo.groupName.value != -1){
		if(formtwo.groupName.value == -2){
			alert('This is a Category, Instead Please Select Group Name.');
			formtwo.groupName.selectedIndex = 0;
		}else{
			formtwo.mappedid.disabled = true;
		}	
	}else{
		formtwo.mappedid.disabled = false;
	}
}

function getMessageText(message)
{
	  var rootvalue= $("#root").val();
	  if(rootvalue=='Trans'){
		  $.ajax({
			    type : "post",
			    url : "view/Over2Cloud/CommunicationOver2Cloud/template/templateSubmissionaction.action?id="+message,
			    success : function(subdeptdata) {
			    $("#divid").hide();
			    $("#destAjaxDiv").show();
		       $("#"+"destAjaxDiv").html(subdeptdata);
		      
			},
			   error: function() {
		            alert("error");
		        }
			 });
    	  }
	  else{
    	$.getJSON("view/Over2Cloud/CommunicationOver2Cloud/Comm/checkMsg.action?message="+message,
      function(data){
    		  $("#divid").show();
    		  $("#destAjaxDiv").hide();
            $("#writeMessage").val(data);
            var i=document.getElementById("writeMessage").value.length;
            var msgvalue=document.getElementById("writeMessage").value;
     	   $("#writeMessagess").val(msgvalue);
            $("#remLen").val(i);
    	  });
	  }
}

function checkNumber(mobileNum) 
{ 
	var IndNum = /^\d{10}$/;
	var regex = new RegExp(IndNum); 
	
	if (mobileNum.match(regex)) 
		return true; 
	else 
		return false; 
} 
function checkMobNo()
{
	var csvNum = document.formtwo.mobileNo.value;
	var newtext = csvNum.split(","); 
	for(var i=0 ;i < newtext.length; i++) 
	{ 
		var mobileNum = checkNumber(newtext[i]);
		if (!mobileNum) 
		{ 
			errZone.innerHTML="<div class='user_form_inputError2'>Please enter comma seperated mobile Number.</div>";
			document.formtwo.mobileNo.value="";
			//document.formtwo.mobileNo.focus();
			return false;
		}
	} 
	errZone.innerHTML="";
	return true;
}

//validation for text counter.
function textCounter(field, countfield, maxlimit) {    //162    160
	
	if (document.formtwo.writeMessage.value.length > 0) {
		var a = document.formtwo.writeMessage.value;
		var iChars = "!@$%^&*()+=-[]';,{}|\":?";
		for ( var i = 0; i < a.length; i++) {
			if (iChars.indexOf(a.charAt(i)) != -1) {
				errZone.innerHTML = "<div class='user_form_inputError2'>Only alphabets are allowed. </div>";
				document.formtwo.writeMessage.value = "";
			} else 
			{
				errZone.innerHTML="";
		    	}
		  }
	   }
	
	if(field.value.length <= maxlimit)
	{
		countfield.value = field.value.length;
	}
	else
	{
		var a = Math.floor(field.value.length / maxlimit);
		var b = maxlimit * a;
		var c = field.value.length - b;
		countfield.value = c;
		countfield.value += "/";
		countfield.value += Math.floor(field.value.length / maxlimit);
	}
}
</script>
<SCRIPT type="text/javascript">
function okSendsmsButton(){
	var conP = '<%=request.getContextPath()%>';
	var validsel_id;
	var duplicatesel_id;
	validsel_id = $("#gridedittable").jqGrid('getGridParam','selarrrow');
	duplicatesel_id = $("#duplicategridedittable").jqGrid('getGridParam','selarrrow');
	
	if(validsel_id!=null && validsel_id!="" && duplicatesel_id!=null && duplicatesel_id!=""){
	alert("validsel_id"+validsel_id);
	alert("duplicatesel_id"+duplicatesel_id);
	urls ='view/Over2Cloud/CommunicationOver2Cloud/Comm/getViaAjaxinstantSendMsg?select_id='+validsel_id+"&duplicatesel_id="+duplicatesel_id;
	 $.ajax({
	    type : 'post',
	    url :urls ,
	    success : function(saveData) {
	 $("#excelValidUploadResult").html(saveData);
	 $("#excelValidUploadResult").fadeIn(3000);
	 $("#excelValidUploadResult").fadeOut(3000);
	},
	   error: function() {
	        alert("error");
	    }
	 });
	}
	else if(validsel_id!=null && validsel_id!=""){
	alert("validsel_id"+validsel_id);
	urls ='view/Over2Cloud/CommunicationOver2Cloud/Comm/getViaAjaxinstantSendMsg?select_id='+validsel_id;
	 $.ajax({
	    type : 'post',
	    url :urls ,
	    success : function(saveData) {
	 $("#excelValidUploadResult").html(saveData);
	 $("#excelValidUploadResult").fadeIn(3000);
	 $("#excelValidUploadResult").fadeOut(3000);
	},
	   error: function() {
	        alert("error");
	    }
	 });
	}
	else if(duplicatesel_id!=null && duplicatesel_id!=""){
	alert("duplicatesel_id"+duplicatesel_id);
	urls ='view/Over2Cloud/CommunicationOver2Cloud/Comm/getViaAjaxinstantSendMsg?duplicatesel_id='+duplicatesel_id;
	 $.ajax({
	    type : 'post',
	    url :urls ,
	    success : function(saveData) {
	 $("#excelDuplicateUploadResult").html(saveData);
	 $("#excelDuplicateUploadResult").fadeIn(3000);
	 $("#excelDuplicateUploadResult").fadeOut(3000);
	},
	   error: function() {
	        alert("error");
	    }
	 });
	}
	
   
};
function cancelButtonsendsms(){
$('#ASExcelDialog').dialog('close');
};
function cancelButtonsendsms1(){
	$('#ASTextDialog').dialog('close');
	};
</SCRIPT>
<sj:dialog id="mybuttondialog"
	buttons="{ 
    		'Send SMS':function() { okInstantClientMsgButton(); },
    		'Cancel':function() { cancelButton(); } 
    		}"
	autoOpen="false" modal="true" width="800" height="350"
	title="Confirm Instant SMS">

</sj:dialog>
<script type="text/javascript">
function okButton(){
		var sel_id;
		var conP = '<%=request.getContextPath()%>';
		select_id = $("#gridedittable").jqGrid('getGridParam','selarrrow');
		var msg_body = document.getElementById("msg_body").value;
		var titles = document.getElementById("titles").value;
		 $.ajax({
			    type : "post",
			    url : "view/Over2Cloud/CommunicationOver2Cloud/Comm/getViaAjaxSendMsg.action?select_id="+sel_id+"&msg_body"+msg_body,
			    success : function(clienttype) {
			    alert(clienttype);
				$("#clienttype").html(clienttype);
			},
			   error: function() {
			        alert("error");
			    }
			 });
	   
  };
  function cancelButton(){
   $('#mybuttondialog').dialog('close');
  };
  function cancelSMSSendInstantsms(){
	   $('#InstantSsaveConfirmid').dialog('close');
	  };
	  function instantcancelButton(){
		   $('#settemplateGriddata').dialog('close');
     };
     function instantcancelButtonsendsms(){
		   $('#takeActionGrid').dialog('close');
   };
     
	  


</script>
<script type="text/javascript">
function setdyanmaictemplatemsg(val,mobileNo,frequencys,divId){

	 $('#rootname').val($('#root').val());
var groupName= $('#groupName').val();
if(val=="Transssss" && $("#ASExcel").val()=="" && $("#ASExcels").val()==""){
if(frequencys.value!=null && frequencys.value!=""){
 var frequencys=frequencys.value;
 var hours = $("#hours").val();
 var date = $("#date").val();
 var dates = $("#dates").val();
 var day = $("#day").val();
 $("#settemplateGriddata").load("<%=request.getContextPath()%>/view/Over2Cloud/CommunicationOver2Cloud/template/viewtemplatedata.action?frequencys="+frequencys+"&hours="+
 hours+"&date="+date+"&dates="+dates+"&day="+day+"&mobileNo="+mobileNo.value+"&root="+val.split(" ").join("%20"));
 $("#settemplateGriddata").dialog('open');
 }else{
 $("#settemplateGriddata").load("<%=request.getContextPath()%>/view/Over2Cloud/CommunicationOver2Cloud/template/viewtemplatedata.action?mobileNo="+mobileNo.value+"&root="+val.split(" ").join("%20"));
	$("#settemplateGriddata").dialog('open');
  }
 }
else{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/CommunicationOver2Cloud/Comm/getViaAjaxtemplate.action?template_type="+val,
	    success : function(Data){
			$('#'+divId+' option').remove();
			$('#'+divId).append($("<option></option>").attr("value",-1).text("--Select Template--"));
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
</script>
<script type="text/javascript">
function okdownload(){
  $("#download").submit();
}
function okdownloadexcel(){
	  $("#downloadexcel").submit();
	}

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
<script type="text/javascript">
function abc()
{
	  $("#dialog").dialog('open');
}
</script>
<script type="text/javascript">
function mobileValidate1() {
	if (document.formtwo.mobileNo.value.length > 0) {
		var a = document.formtwo.mobileNo.value;
		var iChars = "0123456789,";
		for ( var i = 0; i < a.length; i++) {
			if (iChars.indexOf(a.charAt(i)) == -1) {
				errZone.innerHTML = "<div class='user_form_inputError2'>Enter Numeric Values Only</div>";
				var len = (document.formtwo.mobileNo.value.length) - 1;
				document.formtwo.mobileNo.value = (document.formtwo.mobileNo.value).substring(0, len);
				document.formtwo.mobileNo.focus();
			} else {
				if (a.charAt(0) == '9' || a.charAt(0) == '8'|| a.charAt(0) == '7') {
					errMobile1.innerHTML = "";
					document.formtwo.mobileNo.focus();
				} else {
					errZone.innerHTML = "<div class='user_form_inputError2'>Starts with 7,8,9</div>";
					var length = (document.formtwo.mobileNo.value.length) - 1;
					document.formtwo.mobileNo.value = (document.formtwo.mobileNo.value).substring(0, length);
					document.formtwo.mobileNo.focus();
				}

			}
		}
	}
	
	else
		{
		errZone.innerHTML = "<div class='user_form_inputError2'></div>";
		}
}
</script>
<script type="text/javascript">
function resetForm(formId)
{
	$('#'+formId).trigger("reset");
}

</script>

</head>
<body>
<sj:dialog
          id="settemplateGriddata"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Confirmation Page"
          modal="true"
          openTopics="openEffectDialog"
          closeTopics="closeEffectDialog"
          width="900"
   	      height="430"
          draggable="true"
    	  resizable="true"
    	  	buttons="{ 
				'Close':function() { instantcancelButton(); } 
				}">
          
          </sj:dialog>
<sj:dialog
          id="takeActionGrid"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Confirmation Page"
          modal="true"
          openTopics="openEffectDialog"
          closeTopics="closeEffectDialog"
          width="900"
   	      height="430"
          draggable="true"
    	  resizable="true"
    	  	buttons="{ 
				'Close':function() { instantcancelButtonsendsms(); } 
				}"
          >
       <div id="orglevel1" align="left" ></div> 
</sj:dialog>
<div class="list-icon">
	<div class="head">Push SMS </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Send</div>

</div>
	<div class="clear"></div>
<div style=" float:left; padding:10px 0%; width:100%;">
<div class="clear"></div>
<div class="border">

<div class="container_block">
<div style=" float:left; padding:20px 1%; width:98%;">

<div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
  <div id="errZone" style="float:left; margin-left: 7px"></div>  
   <div id="excelError"></div>   
    <div id="txtError"></div>   
        </div>
        <s:hidden name="upload4" value="instantmsgList" />
        <s:hidden name="deptHierarchy" id="deptHierarchy" value="%{deptHierarchy}"></s:hidden>
		<s:form action="dowloadformate" id="download" theme="css_xhtml" enctype="multipart/form-data" name="forms">
		</s:form>
		<s:form action="dowloadexcelformate" id="downloadexcel" theme="css_xhtml" enctype="multipart/form-data" name="forms">
		</s:form>

         
	  <s:form id="upload_AS_excel"  action="uploadASExcel"  theme="simple" method="post" enctype="multipart/form-data" name="xlsx" >
	  
          		 <s:hidden name="transmessagebody" id="writeMessagesssss"/>
           <s:hidden name="messagebody" id="writeMessagess"/>
         <s:hidden name="rootnames" id="rootname"/>
         
       <div class="newColumn">
       <div class="leftColumn1">Select Excel File:</div>
       <div class="rightColumn">
	   <s:if test="%{mandatory}"><span class="needed"></span></s:if>
       <s:file name="ASExcel" id="ASExcel"  placeholder="Enter Data"  cssClass="textField" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px; width:73%" />
   
    <sj:a id="searchButton" title="Search" title="Download" buttonIcon="ui-icon-arrowstop-1-s"  cssStyle="height:25px; width:32px;margin-top: -43px;margin-left: 208px;" cssClass="button" button="true" onclick="okdownloadexcel();"></sj:a>
 </div>
 	<sj:submit 
				targets="uploadASExcel"
				clearForm="true" 
				value="  Upload  " 
				button="true"
				onBeforeTopics="validateExcel"
				cssStyle="margin-left: -42px;margin-top: 2px;"
				 /> 
       </div>
       
       
					<sj:dialog
				id="ASExcelDialog" 
				autoOpen="false" 
				closeOnEscape="true"
				modal="true" 
				title="Confirm Instant SMS Page" 
				width="920"
				height="400" 
				showEffect="slide" 
				hideEffect="explode"
				buttons="{ 
				'Send':function() { okSendsmsButton(); },
				'Close':function() { cancelButtonsendsms(); } 
				}">
				<sj:div id="foldeffectExcel" effect="fold">
				<div id="saveExcel"></div>
				</sj:div>
				<div id="uploadASExcel"></div>
				</sj:dialog>
       </s:form>
       <s:form id="upload_TS_excel" theme="css_xhtml" action="uploadTextFile" method="post" enctype="multipart/form-data" name="upload_TS_excel">
       <div class="newColumn">
       <div class="leftColumn1">Select Text File:</div>
       <div class="rightColumn">
	   <s:if test="%{mandatory}"><span class="needed"></span></s:if>
       <s:file name="ASExcel" id="ASExcels"   placeholder="Enter Data"  cssClass="textField" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px; width:73%" />
      <sj:a id="searchButtonss" title="Search" title="Download" buttonIcon="ui-icon-arrowstop-1-s"  cssStyle="height:25px; width:32px;margin-top: -43px;margin-left: 208px;" cssClass="button" button="true"  onclick="okdownload();"></sj:a>
       </div>
       
      <sj:submit 
				targets="uploadTextFile"
				clearForm="true" 
				value="  Upload  " 
				button="true"
				onBeforeTopics="varifyFile" 
				cssStyle="margin-left: -42px;margin-top: 2px;"
				/> 
       </div>
     
				<sj:dialog
				id="ASTextDialog" 
				autoOpen="false" 
				closeOnEscape="true" 
				modal="true"
				title="Confirm Instant SMS Page" 
				width="920"
				height="400" 
				showEffect="slide" 
				hideEffect="explode"
				buttons="{ 
				'Send':function() { okSendsmsButton(); },
				'Close':function() { cancelButtonsendsms1(); } 
				}">
				<sj:div id="foldeffectExcel" effect="fold">
				<div id="saveExcel"></div>
				</sj:div>
				<div id="uploadTextFile"></div>
				</sj:dialog>
       </s:form>
       <s:form id="formtwo" name="formtwo" namespace="/view/Over2Cloud/CommunicationOver2Cloud/Comm" action="insertDatainstantmsg" theme="simple"  method="post" >
		       <div class="clear"></div>	
		  
			  <div class="clear"></div>	
			  <div class="newColumn">
								<div class="leftColumn1">Contact Type:</div>
								<div class="rightColumn">
										 <s:select 
				                              id="contacttype"
				                              name="contacttype"
				                              list='groupcontactNameListData'
				                              headerKey="-1"
				                              headerValue="-Select Contact Type-" 
				                              cssClass="select"
				                              onchange="changeGroupname('subcontacttype',this.value);"
				                             cssStyle="width:75%"
											>
						                 </s:select>
						           
								</div>
							</div>
							<div class="newColumn">
								<div class="leftColumn1">Contact Sub Type:</div>
								<div class="rightColumn">
										 <s:select 
				                              id="subcontacttype"
				                              name="subcontacttype"
				                              list="#{'-1':'-Select Contact Sub-Type-'}"
				                              headerKey="-1"
				                              cssClass="select"
				                              onchange="changsubcontactType('check_list',this.value);"
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
				                              id="contactid"
				                              name="contactid"
				                              list="#{'-1':'-Select Contact Person-'}"
				                              headerKey="-1"
				                              cssClass="-Select Group Name-"
				                              cssStyle="width:74%"
											>
						                 </s:select>
						           
			                  		<div id="errorlevel2org" class="errordiv"></div>
															</div>
							</div>
							</div>
							
							<div class="newColumn">
								<div class="leftColumn1">Select Group:</div>
								<div class="rightColumn">
										 <s:select 
				                              id="groupName"
				                              name="groupName"
				                              list='groupNameListData'
				                            
				                              multiple="true">
						                 </s:select>
								</div>
							</div>
							
							
				
				<div class="clear"></div>	
			<s:iterator value="mobileTextBox" status="counter">
          
             <span id="mandatoryFields" class="pIds" style="display: none; ">mobileNo#Mobile No#T#m,</span>
				 <s:if test="#counter.count%2 != 0">
				 <s:if test="%{mandatory}">
	                   <div class="rightColumn">
						<div class="leftColumn1"><s:property value="%{value}"/>:</div>
						<div class="rightColumn">
					     <span class="needed"></span><s:textfield name="mobileNo"  id="mobileNo" onkeyup="mobileValidate1()"  cssClass="textField" placeholder="Enter Data in CSV Format" cssStyle="margin: 0px 0px 10px; width: 400px; height: 30px;" onblur="return checkMobNo();"/>
					     </div></div>
						  </s:if>
					     
			  </s:if>
		</s:iterator>
		
				   <div class="newColumn">
				    <span id="mandatoryFields" class="pIds" style="display: none; ">root#Message Template Route#D#a,</span>
						<div class="leftColumn1" style="margin-left: 30px">Message Template Route:</div>
						<div class="rightColumn"> <span class="needed"></span>
						<s:select 
                              id="root"
                              name="root" 
                              list='messagerootList' 
                              headerKey="-1"
                              headerValue="-Select Route Type-" 
                              cssClass="select"
                              cssStyle="width:74%"
                              onchange="setdyanmaictemplatemsg(this.value,mobileNo,frequencys,'templateidss');"
                              >
                   
                  </s:select>
				 </div>
				 
				 </div>
					   
		<s:iterator value="messageNameDropdown" status="counter">
	                  <div class="newColumn">
						<div class="leftColumn1" style="margin-left: 30px">Template Name:</div>
						<div class="rightColumn">
						<s:select 
                               name="templateid" 
                               id="templateidss"
                               list="#{'-1':'-Select Template Name-'}" 
                              headerKey="-1-"
                              cssClass="select"
                              cssStyle="width:74%"
                              onchange="getMessageText(this.value)"
                              >
                  </s:select>
				 </div>
				 </div>
				
		</s:iterator>
		<s:iterator value="frequency" begin="0" end="0">

<div class="clear"></div>
		<div style="width:100%; line-height:40px;">
       <div style="text-align:right; padding:6px; line-height:25px;	float:left;	width:12%;">
       <s:property value="%{value}"/>:</div>
       <div style="width: 100%">
       <s:if test="%{mandatory}"><span class="needed"></span></s:if>
      		 <s:radio list="#{'One-Time':'One-Time','Daily':'Daily','Weekly':'Weekly','Monthly':'Monthly','Yearly':'Yearly','None':'none'}" name="%{key}" id="%{key}" onclick="changeFrequency(this.value,'dateDiv','dayDiv','timeDiv','dateDiv1')"/>
      
      </div>
       </div>
					<div class="clear"></div>
							<div id=dateDiv style="display: none">
							<div class="newColumn">
							<div class="leftColumn1">Date</div>
							<div class="rightColumn">
								  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
       								<sj:datepicker id="date" name="date" readonly="true" placeholder="Enter Date" showOn="focus" displayFormat="yy-mm-dd"  cssClass="textField" />
      						 </div>
				                  
	                  			<div id="errorlevel2org" class="errordiv"></div>
							</div>
							</div>
							
							
							<div id=dateDiv1 style="display: none">
							<div class="newColumn">
							<div class="leftColumn1">Date</div>
							<div class="rightColumn">
								  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
      							 <sj:datepicker id="dates" name="date" readonly="true" placeholder="Enter Date" showOn="focus" displayFormat="yy-mm-dd" cssClass="textField" />
     						  </div>
	                  			<div id="errorlevel2org" class="errordiv"></div>
							</div>
							</div>
							
						<div id=timeDiv style="display: none">	
					     <div class="newColumn">
							<div class="leftColumn1">Time:</div>
							<div class="rightColumn">
								  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
      					 <sj:datepicker id="hours" name="hours" readonly="true" placeholder="Enter Time" showOn="focus" timepicker="true" timepickerOnly="true"  timepickerFormat="hh:mm:ss" cssClass="textField"/>
      						 </div>
	                  			<div id="errorlevel2org" class="errordiv"></div>
							</div></div>
							
							<div id=dayDiv style="display: none">
							<div class="newColumn">
							<div class="leftColumn1">Day:</div>
							<div class="rightColumn">
								  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
								  <s:select 
                              id="day"
                              name="day" 
                              list="#{'Monday':'Monday','Tuesday':'Tuesday','Wednesday':'Wednesday','Thursday':'Thursday','Friday':'Friday','Saturday':'Saturday'}"
                              headerKey="Sunday"
                              headerValue="Sunday" 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                   </s:select>
      					 </div>
	                  			<div id="errorlevel2org" class="errordiv"></div>
							</div></div>
						

</s:iterator>
<div class="clear"></div>	
           <div id="destAjaxDiv">
           </div>
           <div id="divid">
            <s:iterator value="writemessageTextBox" >
                
				 	 <s:if test="%{key == 'writeMessage'}">
	                   <div class="rightColumn">
						<div class="leftColumn1"><s:property value="%{value}"/>:</div>
						<div class="rightColumn">
					     <span class="needed"></span>
					    <s:textarea name="%{key}" id="%{key}" cols="95" rows="20" class="form_menu_inputtext" onkeydown="textCounter(this.form.writeMessage,this.form.remLen,160)" style="margin: 0px 0px 10px; width: 600px; height: 50px;"/>
						</div>
					     </div>
						  </s:if>
					   </s:iterator>
					   <div class="newColumn">
								<div class="leftColumn1" style="margin-left: 153PX;margin-top: 14PX">Number Of Characters</div>
								<div class="rightColumn">
								<input readonly type="text" id="remLen" name="remLen" size=3 maxlength="3" value="0" style="border:1px solid #e2e9ef;border-top:1px solid #cbd3e2;outline:medium none;padding:2px;margin-top: 16PX;"">
						           
			                  		<div id="errorlevel2org" class="errordiv"></div>
								</div>
							</div>
					   </div>
					   	   
<div class="form_menubox">
			<b>Note :</b> For Transactional Template
			Dynamic Fields should be placed inside <> and Length should be placed inside() 
			<a id="hlOpenMe" href="#" onclick="abc();">Example</a></div>
				<div class="clear"></div>	
				
                
		<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
			<div class="type-button" style="margin-left: -200px;">
			<center>

			<sj:submit 
          			   targets="orglevel1" 
                       clearForm="false"
                       value="Send" 
                       effect="highlight"
                       effectOptions="{ color : '#222222'}"
                       effectDuration="5000"
                       button="true"
                       onCompleteTopics="level1"
                       cssClass="submit"
                       cssStyle="margin-right: 2px;margin-bottom: 29px;"
                       indicator="indicator2"
                       onBeforeTopics="validate"
                       
         />
         
         <sj:a 
				     	href="#" 
		               	button="true" 
		               	onclick="resetForm('formtwo');"
						cssStyle="margin-top: -28px;"						>
					  	Reset
		   </sj:a>
		   
	
        
		  </center> 
   </div>

</s:form>  

</div>
</div>
</div>
</div>
</body>
</html>

<sj:dialog
   		id="dialog"
   		 title="Example For Template"
   		 autoOpen="false"
   		 modal="true"
   		  openTopics="openEffectDialog"
          closeTopics="closeEffectDialog"
          width="600"
   	      height="180"
          draggable="true"
    	  resizable="true"
   		>
   		 
   		<font face="Verdana" size="0.5" style="font-size: medium;font-family: serif;">
			Hi, &lt;Name(60)&gt; ,thanks for your information. We have updated the same. Wish you all the best. Regards, 
			</font>
   		
   		<br>
   		<table style="width:300px">
<tr>
  <td width="100px;">Template Id</td>
  <td  width="500px;">Template </td>		
  
  </tr>
  <tr>
  <td>12345</td>
  <td>Dear Xyx, thanks for your information. We have updated the same. Wish you all the best. Regards. </td>		
  
  </tr>

</table>   
   		</sj:dialog>	   

