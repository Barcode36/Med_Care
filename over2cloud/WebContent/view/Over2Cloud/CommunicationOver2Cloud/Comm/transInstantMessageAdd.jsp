<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>

<html>
<head>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/communication/CommunicationInstantMessage.js"/>"></script>
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
    	$.getJSON("view/Over2Cloud/CommunicationOver2Cloud/Comm/checkMsg.action?message="+message,
      function(data){
            $("#writeMessage").val(data);
            var i=document.getElementById("writeMessage").value.length;
            $("#remLen").val(i);

           
    	  });
 
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
			errnumber.innerHTML="<div class='user_form_inputError2'>Please enter comma seperated mobile num.</div>";
			document.formtwo.mobileNo.value="";
			//document.formone.csvNumber.focus();
			return false;
		}
	} 
	errnumber.innerHTML="";
	return true;
}

//validation for text counter.
function textCounter(field, countfield, maxlimit) {
	if (field.value.length < maxlimit) 
	field.value = field.value.substring(0, maxlimit);
	
	else 
	countfield.value = maxlimit + field.value.length;
	}

</script>
</head>
<body>

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
          >
       <div id="orglevel1" align="left" ></div> 
</sj:dialog>
<div class="list-icon">
	<div class="head">Instant Message</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div>

</div>
	<div class="clear"></div>
<div style=" float:left; padding:10px 0%; width:100%;">
<div class="clear"></div>
<div class="border">

<div class="container_block">
<div style=" float:left; padding:20px 1%; width:98%;">


<s:form id="formtwo" name="formtwo" namespace="/view/Over2Cloud/CommunicationOver2Cloud/Comm" action="beforeTransInstantMessageAdd" theme="simple"  method="post" >
<div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
  <div id="errZone" style="float:left; margin-left: 7px"></div>        
        </div>
        <s:hidden name="upload4" value="instantmsgList" />
        <s:hidden name="deptHierarchy" id="deptHierarchy" value="%{deptHierarchy}"></s:hidden>
			<div class="form_menubox">
       		<div class="user_form_text">Select Excel</div>
       		<div class="user_form_input"><s:file name="excel"/><div id="errAssetExcel" style="display: inline; width: 20px;"></div></div>
       		<div class="user_form_text1">Excel Format</div>
       		<div class="user_form_input">
       		 <a href="<%=request.getContextPath()%>/view/Over2Cloud/CommunicationOver2Cloud/Comm/ExcelFileInstant.xls" ><font color="black">Download </font></a>
       		</div>
    		</div>
		    <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
            <div id="errZone" style="float:left; margin-left: 7px"></div>        
            </div>
		     
		  <s:iterator value="subDept_DeptLevelName" status="status">
                <s:if test="%{mandatory}">
                    <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                </s:if>
              <s:if test="#status.odd">
              <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                  <s:select 
                              id="%{key}"
                              name="deptname"
                              list="deptList"
                              headerKey="-1"
                              headerValue="Select Department" 
                              cssClass="select"
                              cssStyle="width:47%"
                              onchange="getSubDept(this.value,'subDeptDiv1',%{deptHierarchy},'1','0','0','0');"
                              >
                  </s:select>
              </div>
              </div>
              </s:if>
             <s:else>
             <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <div id="subDeptDiv1">
                    <s:select 
                              id="%{key}"
                              name="subdept"
                              list="{'No Data'}"
                              headerKey="-1"
                              headerValue="Select Sub-Department" 
                              cssClass="select"
                              cssStyle="width:47%"
                              >
                   </s:select>
              </div>
              </div>
              </div>
              </s:else>
              </s:iterator>
			  <div class="clear"></div>	
				<div class="newColumn">
								<div class="leftColumn1">Contact:</div>
								<div class="rightColumn">
								
										 <s:select 
				                              id="mappedid"
				                              name="mappedid"
				                              list='contactListData'
				                              headerKey="-1"
				                              headerValue="Select Contact Name" 
				                              onchange="changecontact();"
				                              cssClass="select"
				                              cssStyle="width:70%"
											>
						                 </s:select>
						           
			                  		<div id="errorlevel2org" class="errordiv"></div>
															</div>
							</div>
				<div class="newColumn">
								<div class="leftColumn1">Select Group:</div>
								<div class="rightColumn">
										 <s:select 
				                              id="groupName"
				                              name="groupName"
				                              list='groupNameListData'
				                              headerKey="-1"
				                              headerValue="-Select Group Name-" 
				                              cssClass="select"
				                              cssStyle="width:70%"
				                              onchange="changeGroup11();"
											>
						                 </s:select>
						           
			                  		<div id="errorlevel2org" class="errordiv"></div>
								</div>
							</div>
				<div class="clear"></div>	
			<s:iterator value="mobileTextBox" status="counter">
                   <s:if test="%{mandatory}">
                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>  
           
				 <s:if test="#counter.count%2 != 0">
				 <s:if test="%{mandatory}">
	                   <div class="rightColumn">
						<div class="leftColumn1"><s:property value="%{value}"/>:</div>
						<div class="rightColumn">
					     <span class="needed"></span><s:textfield name="mobileNo"  id="mobileNo" onchange="validateCsv()"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px; width:74%" onblur="return checkMobNo();"/>
					     </div></div>
						  </s:if>
					     
			  </s:if>
		</s:iterator>
					   
		<s:iterator value="messageNameDropdown" status="counter">
			<s:if test="%{mandatory}">
			<span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
			</s:if>
				<s:if test="%{key == 'messageName'}">
	                   <div class="rightColumn">
						<div class="leftColumn1" style="margin-left: 19px"><s:property value="%{value}"/>:</div>
						<div class="rightColumn"> <span class="needed"></span>
						<s:select 
                              id="%{key}"
                              name="%{key}" 
                              list='messageNameList' 
                              headerKey="-1"
                              headerValue="Select Message" 
                              cssClass="select"
                              cssStyle="width:74%"
                              onchange="getMessageText(this.value)"
                              >
                   
                  </s:select>
				 </div>
				 </div>
				 </s:if>
		</s:iterator>

<div class="clear"></div>	
            <s:iterator value="writemessageTextBox" >
                   <s:if test="%{mandatory}">
                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>  
				 	 <s:if test="%{key == 'writeMessage'}">
	                   <div class="rightColumn">
						<div class="leftColumn1"><s:property value="%{value}"/>:</div>
						<div class="rightColumn">
					     <span class="needed"></span>
					    <s:textarea name="%{key}" id="%{key}" cols="65" rows="30" class="form_menu_inputtext" onkeydown="textCounter(this.form.writeMessage,this.form.remLen,0)" style="margin: 0px 0px 10px; width: 380px; height: 60px;"/>
						</div>
					     </div>
						  </s:if>
					   </s:iterator>
					   
					   <div class="clear"></div>	
					   <div class="newColumn">
								<div class="leftColumn1">Number Of Characters</div>
								<div class="rightColumn">
								<input readonly type="text" id="remLen" name="remLen" size=3 maxlength="3" value="0">
						           
			                  		<div id="errorlevel2org" class="errordiv"></div>
								</div>
							</div>
					   
					   	   

				<div class="clear"></div>	
				
                
		<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
			<div class="type-button" style="margin-left: -200px;">
			<center>

			<sj:submit 
          			   targets="orglevel1" 
                       clearForm="true"
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
                       openDialog="takeActionGrid"
         />
         
         <sj:a 
				     	href="#" 
		               	button="true" 
		               	onclick="viewMessageDraft1();"
						cssStyle="margin-top: -28px;"						>
					  	Reset
		   </sj:a>
		   
		   <sj:a 
				     	href="#" 
		               	button="true" 
		               	onclick="viewInstantmsg();"
						cssStyle="margin-right: -137px;margin-top: -28px;"
						>
					  	Back
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
