<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>
<style type="text/css">

.user_form_input{
    margin-bottom:10px;
}

</style>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/associate/associate.js"/>"></script>

<SCRIPT type="text/javascript">

function getMessageText(message)
{
    	$.getJSON("view/Over2Cloud/CommunicationOver2Cloud/Comm/checkMsgText1.action?message="+message,
      function(data){
            $("#writeMessage").val(data);
    	  });
 
}
function getSubDept(deptId,destAjaxDiv,deptLevel,subdeptType) {
	
	var dept=$("#"+deptId).val();
	if (deptLevel=='2') {
    if (subdeptType=='1') {
	$.ajax({
		type :"post",
		url :"view/Over2Cloud/CommunicationOver2Cloud/Comm/subDeptViaAjax1.action?destination="+ destAjaxDiv+"&dept="+deptId,
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


//disable Group Name




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


function getConfirmationPage()
{

	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url  : "view/Over2Cloud/CommunicationOver2Cloud/Comm/insertHindiMessageStates.action",
	    success : function(subdeptdata) 
	    {
		$("#orglevel1").html(subdeptdata);
		$("#takeActionGrid").dialog('open');
	},
	   error: function() {
            alert("error");
        }
	 });

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
	<div class="head"><h3>Instant Hindi Message >> Add</h3></div>
	</div>
	<div class="clear"></div>
<div style=" float:left; padding:10px 0%; width:100%;">
<div class="clear"></div>
<div class="border">

<div class="container_block">
<div style=" float:left; padding:20px 1%; width:98%;">


<s:form id="formtwo" name="formtwo" namespace="/view/Over2Cloud/CommunicationOver2Cloud/Comm" action="insertDataHindiInstantMsg" theme="css_xhtml"  method="post" >
<div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
  <div id="errZone" style="float:left; margin-left: 7px"></div>        
        </div>
        
         <s:hidden name="upload4" value="blacklistList" />
			<div class="form_menubox">
       		<div class="user_form_text">Select Excel</div>
       		<div class="user_form_input"><s:file name="excel"/><div id="errAssetExcel" style="display: inline; width: 20px;"></div></div>
       		<div class="user_form_text1">Excel Format</div>
       		<div class="user_form_input">
       		 <a href="<%=request.getContextPath()%>/view/Over2Cloud/CommunicationOver2Cloud/Comm/ExcelFileHindi_Msg.xls" ><font color="#194d65">Download </font></a>
       		</div>
    		</div>
        

<div class="clear"></div>
	<s:hidden name="deptHierarchy" id="deptHierarchy" value="%{deptHierarchy}"></s:hidden>  	
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
				                              headerValue="Select Contact" 
				                              cssClass="select"
				                              cssStyle="width:70%"
				                              onchange="changecontact();"
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
				                              list='groupListName'
				                              headerKey="-1"
				                              headerValue="Select Group" 
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
					     <span class="needed"></span><s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px; width:74%"/>
					     </div></div>
						  </s:if>
					     <s:else>
					     <div class="rightColumn">
						<div class="leftColumn1"><s:property value="%{value}"/>:</div>
						<div class="rightColumn"><s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px; width:74%"/>
					     </div></div>
					     </s:else>
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
                              list='messageNameList1' 
                              headerKey="-1"
                              headerValue="Select Message" 
                             cssClass="select"
                             cssStyle="width:74%"
                             onchange="getMessageText(this.value)"
                              >
                   
                  </s:select>
					    
					     </div></div></s:if>
						  
					     

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
					    <s:textarea name="%{key}" id="%{key}" cols="65" rows="30" class="form_menu_inputtext" style="margin: 0px 0px 10px; width: 380px; height: 60px;"/>
</div>
					     </div>
						  </s:if>
					     
					 
					   </s:iterator>
				<div class="clear"></div>	
				<br>
					<br>
                
<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
<div class="buttonStyle">
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
                       indicator="indicator2"
                       onBeforeTopics="validate"
                       openDialog="takeActionGrid"
         />
   </div>

  
</s:form>  

</div>
</div>
</div>
</div>
</body>
</html>
