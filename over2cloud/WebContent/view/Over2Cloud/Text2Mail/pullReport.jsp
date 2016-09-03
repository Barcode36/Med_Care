<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="/js/common/commonvalidation.js"/>"></script>
<script src="<s:url value="/js/organization.js"/>"></script>
<script src="<s:url value="/js/employeeOver2Cloud.js"/>"></script>
<script src="<s:url value="/js/lead/LeadCommon.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<script type="text/javascript">

$.subscribe('completeData22',function(event,data)
		{
	setTimeout(function(){ $("#foldeffect10").fadeIn(); }, 10);
    setTimeout(function(){ $("#foldeffect10").fadeOut(); }, 4000);
    
			
		});


</script>
<SCRIPT type="text/javascript">
function rahul()
{
	startDate = $('#startDate').val();
	endDate = $('#endDate').val();
	type=$('#type option:selected').text();
	keyword=$('#keyword option:selected').text();
	
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$("#data_part").load("view/Over2Cloud/Text2Mail/viewPullReoprt.action?startDate="+startDate+"&endDate="+endDate+"&type="+type+"&keyword="+keyword);
	
	
	}

	
	
	

	
</script>
</head>
<div class="middle-content">
<div class="list-icon">
<div class="head">Pull Report</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">search</div>
</div>
	<div class="clear"></div>
	<div style="min-height: 10px; height: auto; width: 100%;"></div>
		<div class="border">
			<div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
  		<div id="errZone" style="float:left; margin-left: 7px"></div>        
        </div>
			<div><br>
<div style="float: left; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
								<div id="errZone" style="float: left; margin-left: 7px"></div>
							</div>
	<div class="newColumn">
						<div class="leftColumn1">From :</div>
						<div class="rightColumn1"><span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}" />#<s:property value="%{value}" />#T#a#,</span>
							<span class="needed"></span>
						<sj:datepicker id="startDate" name="startDate"  value="today" displayFormat="dd-mm-yy"  showOn="focus" changeMonth="true" changeYear="true" cssClass="textField" maxlength="50"/></div>
						</div>
						
						<div class="newColumn">
						<div class="leftColumn1">To:</div>
						<div class="rightColumn1"><span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}" />#<s:property value="%{value}" />#T#a#,</span>
							<span class="needed"></span>
						  <sj:datepicker id="endDate" name="endDate"  value="today" displayFormat="dd-mm-yy"  showOn="focus" changeMonth="true" changeYear="true" cssClass="textField" maxlength="50"/></div>
						</div>
						<div class="clear"></div>

         	<div class="newColumn">
				<div class="leftColumn1"> <span id="mandatoryFields" class="pIds" style="display: none; ">type#type#D#,</span>Msg Type:</div>
				<div class="rightColumn1">
					<s:if test="%{groupexist}"><span class="needed"></span></s:if>
	                  <s:select id="type"
						name="type" list="#{'Incoming':'Incoming','Outgoing':'Outgoing'}" headerKey="-1"
						headerValue="Select Group" cssClass="textField"
					          cssStyle="width:82%">

					</s:select>
				</div>
			</div>
			
         	<%-- <div class="newColumn">
	<div class="leftColumn1"> <span id="mandatoryFields" class="pIds" style="display: none; ">subGroup#<s:property value="%{subGroup}" />#D#,</span>Town:</div>
				<div class="rightColumn1">
					<s:if test="%{groupexist}"><span class="needed"></span></s:if>
	                 <s:select
						id="subGroup" name="subGroup" list="#{'-1':'Select'}"
						headerKey="-1" headerValue="Select Sub-Group"
						cssClass="textField"
					          cssStyle="width:82%"></s:select>
				</div>
			</div>
				<div class="clear"></div>

         	<div class="newColumn">
				<div class="leftColumn1"> <span id="mandatoryFields" class="pIds" style="display: none; ">group#Group#D#,</span>Executive:</div>
				<div class="rightColumn1">
					<s:if test="%{groupexist}"><span class="needed"></span></s:if>
	                  <s:select id="group"
						name="group" list="#{'-1':'Select'}" headerKey="-1"
						headerValue="Select Group" cssClass="textField"
					          cssStyle="width:82%"
						onchange="getSubGrp(this.value,'subGroup');">

					</s:select>
				</div>
			</div> --%>
			
         	<div class="newColumn">
	<div class="leftColumn1"> <span id="mandatoryFields" class="pIds" style="display: none; ">subGroup#<s:property value="%{subGroup}" />#D#,</span>Keyword:</div>
				<div class="rightColumn1">
					<s:if test="%{groupexist}"><span class="needed"></span></s:if>
	                 <s:select
						id="keyword" name="keyword" list="uploadKRMap"
						headerKey="-1" headerValue="Select Keyword"
						cssClass="textField"
					    cssStyle="width:82%"></s:select>
				</div>
			</div>



				
				<div class="clear"></div>
				<div class="fields">
				<div style="width: 100%; text-align: center; padding-bottom: 10px;">
				<sj:submit 
								     targets="Result7" 
								     clearForm="true"
								     value="  Search  " 
								     effect="highlight"
								     effectOptions="{ color : '#222222'}"
								     effectDuration="5000"
								     button="true"
								     onCompleteTopics="completeData22"
								     cssClass="button"
								     indicator="indicator2"
								     onBeforeTopics="validate"
								     onclick="rahul();"
							     />
							     
					 <sj:div id="foldeffect10"  effect="fold">
                    <div id="Result7"></div>
               </sj:div>

				</div>
				</div>
				<div class="clear"></div>
				

			</div></div>


</div>
</html>