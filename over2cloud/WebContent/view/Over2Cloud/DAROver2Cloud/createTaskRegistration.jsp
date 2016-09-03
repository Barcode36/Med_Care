<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/task_js/task.js"/>"></script>
<script type="text/javascript">
$.subscribe('getDateCompletion', function(event,data)
{
	var fromDate = $("#intiation").val();
	var toDate = $("#completion").val();
     if (Date.parse(fromDate) > Date.parse(toDate))
     {
      	alert("Invalid Date Range!\nStart Date cannot be after End Date!");
      	return false;
	 }
});
$.subscribe('completeData', function(event,data)
{
	  setTimeout(function(){ $("#foldeffect").fadeIn(); }, 10);
      setTimeout(function(){ $("#foldeffect").fadeOut(); }, 4000);
      $('select').find('option:first').attr('selected', 'selected');
      $('#attachment').val('');
});
function resetForm(formId) 
{
	 $('#'+formId).trigger("reset");
}
function addInKR()
{
	
	$('#openKRDialog').dialog('open');
	$('#openKRDialog').dialog({height: '450',width:'1250',position:'center'});
	$("#krview").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/beforeKRUpload.action?dataFrom=Other",
	    success : function(data) 
	    {
			$("#"+"krview").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}
function fromKRAdd()
{
	$("#result").load("<%=request.getContextPath()%>/view/Over2Cloud/DAROver2Cloud/beforeFromKR.action?dataFor=attachment1");
}
</script>
</head>
<body>
<sj:dialog
          id="openKRDialog"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title="KR Upload"
          modal="true"
          width="1200"
          height="450"
          draggable="true"
    	  resizable="true"
          >
          <div id="krview"></div>
</sj:dialog>
<div class="list-icon">
	 <div class="head">Project</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Registration</div> 
</div>
 <div style=" float:left; padding:5px 0%; width:100%;">
	<div class="border" style="overflow-x:hidden;">
        <s:form id="formtask" name="formtask" action="addTaskRegis" theme="css_xhtml"  method="post" enctype="multipart/form-data" >
		   <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="errZone" style="float:left; margin-left: 7px">
             </div> 
          </div>
		     <s:iterator value="taskColumnMap" status="status">
		     <s:if test="%{mandatory}">
              <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
               </s:if>
               
		      	  <s:if test="#status.odd">
	                  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:textfield name="%{key}" id="%{key}" maxlength="500" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
				</div>
				</s:if>
			   
			   <s:else>
			  <div class="newColumn">
						<div class="leftColumn1"><s:property value="%{value}"/>:</div>
						<div class="rightColumn1">
                           <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                            <s:textfield name="%{key}" id="%{key}" maxlength="500" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
			  </div>
			  </s:else>
		     </s:iterator>
		      <s:iterator value="taskDropMap">
		      <s:if test="%{mandatory}">
                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
              </s:if> 
			    <s:if test="%{key == 'tasktype'}">
               <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:select 
                              id="%{key}"
                              name="%{key}"
                              list="listtask"
                              headerKey="-1"
                              headerValue="Select Task Type" 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                               </s:select>
               </div> 
               </div>               
               </s:if>
     
              <s:if test="%{key == 'allotedby'}">
             <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                  				<s:select 
                              id="%{key}"
                              name="%{key}"
                              list="listAllotedemployee"
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                  				</s:select>
                  </div>
                  </div>
                </s:if>
               
               <s:if test="%{key == 'clientfor'}">
               <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                  				<s:select 
                              id="%{key}"
                              name="%{key}"
                              list="#{'PC':'Prospect Client','EC':'Existing Client','PA':'Prospect Associate','EA':'Existing Associate','IN':'Internal','N':'Other'}"
                              headerKey="-1"
                              headerValue="Select Client" 
                              cssClass="select"
                              cssStyle="width:82%"
                              onchange="getClientValues(this.value,'cName')"
                              >
                 				 </s:select>
                </div>
                </div> 				 
                </s:if>
               
                 <s:if test="%{key == 'cName'}">
               <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                  				<s:select 
                              id="%{key}"
                              name="%{key}"
                              list="{'No Data'}"
                              headerKey="-1"
                              headerValue="Select Name" 
                              cssClass="select"
                              cssStyle="width:82%"
                              onchange="getOfferingDetails(this.value,'clientfor','offering');"
                              >
                 				 </s:select>
                </div>
                </div> 				 
                </s:if>
                
                 <s:if test="%{key == 'offering'}">
               <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                  				<s:select 
                              id="%{key}"
                              name="%{key}"
                              list="{'No Data'}"
                              headerKey="-1"
                              headerValue="Select Offering" 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                 				 </s:select>
                </div>
                </div> 				 
                </s:if>
                
              <s:if test="%{key == 'priority'}">
              <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                			    <s:select 
                              id="%{key}"
                              name="%{key}"
                              list="{'Low','High','Medium'}"
                              headerKey="-1"
                              headerValue="Select Priority" 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                  			   </s:select>
                 </div>
                 </div> 			   
                </s:if>
              
                </s:iterator>
                
               
		  <div class="clear"></div> 
		    <s:iterator value="taskFileMap" status="counter" begin="0" end="0">
				 <s:if test="%{mandatory}">
                 <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>
		        <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:file name="%{key}" id="%{key}" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"  /></div>
		       </div>
		    </s:iterator>
		    <sj:a cssStyle="margin-left: 83px;margin-top: 9px;" button="true" href="#" onclick="fromKRAdd();">From KR</sj:a>
		    <sj:a cssStyle="margin-left: 0px;margin-top: 9px;" button="true" href="#" onclick="addInKR();">To KR</sj:a>
		    	  <div class="clear"></div>   
		    <div id="result"></div>
		     <div class="clear"></div> 
             <center><img id="indicator222" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
		     <center>
			 <div class="type-button" style="text-align: center;">
	         <sj:submit 
	                        targets="target1" 
	                        clearForm="true"
	                        id="taskADD"
	                        value=" Register " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="completeData"
	                        cssClass="submit"
	                        indicator="indicator222"
	                        cssStyle="margin-left: -51px;"
	                        />
	   
			       
		              <sj:a cssStyle="margin-left: 181px;margin-top: -45px;" button="true" href="#" onclick="resetForm('formtask');">Reset</sj:a>
		            <s:if test="%{dataFor=='activtyBoard'}">
		              <sj:a cssStyle="margin-left: 0px;margin-top: -45px;" button="true" href="#" onclick="viewactivtyBoard();">Back</sj:a> 
		            </s:if>
		            <s:else>
		              <sj:a cssStyle="margin-left: 0px;margin-top: -45px;" button="true" href="#" onclick="viewTask();">Back</sj:a>
		            </s:else>
	        </div>
	        </center>
		  <sj:div id="foldeffect" effect="fold">
		  <div id="target1"></div>
		  </sj:div>
	 
	 </s:form>
	  </div>
	  </div>
</body>
</html>
