<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<style>
table,th,td
{
border:1px solid black;
border-collapse:collapse;
}
</style>
<script src="<s:url value="/js/common/commonvalidation.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/communication/CommuincationBlackList.js"/>"></script>

<script type="text/javascript">
$.subscribe('result', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#resultaddtemplate").fadeIn(); }, 1000);
	         setTimeout(function(){ $("#resultaddtemplate").fadeOut(); }, 4000);
	       });
</script>

<SCRIPT type="text/javascript">
function viewTemplate ()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/>Loading...</center>");
$.ajax({
			    type : "post",
			    url  : "view/Over2Cloud/CommunicationOver2Cloud/template/beforeTemplateView.action",
			    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });


}

function abc()
{
	  $("#dialog").dialog('open');
}



//validation for text counter.
function textCounter(field, countfield, maxlimit) {    //162    160
	if(field.value.length <= maxlimit)
	{
		countfield.value = field.value.length;
	}
	else
	{
		var a = Math.floor(field.value.length / maxlimit);
		var b = maxlimit * a;
		alert(b);
		var c = field.value.length - b;
		alert(c);
		countfield.value = c;
		countfield.value += "/";
		countfield.value += Math.floor(field.value.length / maxlimit);
	}
}
</script>
</head>
<body>
<div class="list-icon">
	<div class="head">Template</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div>
</div>
<div class="border">
<div class="container_block">
<div style=" float:left; padding:20px 1%; width:98%;">
<s:form id="formtwo" name="formtwo" namespace="/view/Over2Cloud/CommunicationOver2Cloud/template" action="insertDataForTemplate" theme="css_xhtml"  method="post" >
<div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
  <div id="errZone" style="float:left; margin-left: 7px"></div>        
        </div>
        <br>
   	<!-- Text box -->
               <s:iterator value="templateID" status="counter">
                   <s:if test="%{mandatory}">
                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>  
                 <s:if test="%{key == 'template_id'}">
	                   <div class="rightColumn">
						<div class="leftColumn1"><s:property value="%{value}"/></div>
						<div class="rightColumn"> <s:if test="%{mandatory}">
					     <span class="needed"></span></s:if>
					     <s:textfield name="%{key}"  id="%{key}" value="%{Templateserise}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
					     </div></div>
					     </s:if>
			</s:iterator>
			
			<div class="newColumn">
								<span id="mandatoryFields" class="pIds" style="display: none; ">template_type#Message Template Type#D#a,</span>
								<div class="leftColumn1">Message Template Type: </div>
								<div class="rightColumn">
								<span class="needed"></span>
										 <s:select 
										       name="template_type"
				                              id="template_type"
				                              list="#{'Trans':'Transactional Template','Promo ':'Promo  Template','Open':'Open  Template'}"
				                               headerKey="-1"
				                               headerValue="-Select Template Type-"
				                               cssClass="select"
				                               cssStyle="width:75%"
											>
						                 </s:select>
								</div>
							</div>
					<div class="clear"></div>
					 <div class="rightColumn">
						<div class="leftColumn1">Template Name:</div>
						<div class="rightColumn">
						 <span id="mandatoryFields" class="pIds" style="display: none; ">template_name#Template Name:#T#an,</span>
					     <span class="needed"></span><s:textfield name="template_name"  id="template_name"  cssClass="textField" placeholder="Enter Template Name " cssStyle="margin: 0px 0px 10px; width: 400px; height: 30px;" onblur="return checkMobNo();"/>
					     </div></div>
					     	<div class="clear"></div>
               <s:iterator value="templateName" status="counter">
                   <s:if test="%{mandatory}">
                      <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>  
                 <s:if test="%{key == 'template'}">
	                   <div class="rightColumn">
						<div class="leftColumn1"><s:property value="%{value}"/></div>
						<div class="rightColumn"> <s:if test="%{mandatory}">
					     <span class="needed"></span>  </s:if>
					   <s:textarea name="%{key}" id="%{key}" cols="95" rows="30" class="form_menu_inputtext" onkeydown="textCounter(this.form.template,this.form.remLen,160)" style="margin: 0px 0px 10px; width: 600px; height: 70px;"/>
					     </div></div>
					     </s:if>
		 </s:iterator>
		 
		 <div class="clear"></div>	
					   <div class="newColumn">
								<div class="leftColumn1">Number Of Characters</div>
								<div class="rightColumn">
								<input readonly type="text" id="remLen" name="remLen" size=3 maxlength="3" value="0" style="border:1px solid #e2e9ef;border-top:1px solid #cbd3e2;outline:medium none;padding:2px;" >
						           
			                  		<div id="errorlevel2org" class="errordiv"></div>
								</div>
							</div>
					  <div class="clear"></div> 
		 
		
<div class="form_menubox">
			<b>Note :</b> For Transactional Template
			Dynamic Fields should be placed inside <> and Length should be placed inside() 
			<a id="hlOpenMe" href="#" onclick="abc();">Example</a></div>
				<div class="clear"></div>	

<!-- Buttons -->
<div class="clear"></div>
 <center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
	<div class="type-button" style="margin-left: -200px;">
<center> 
       <sj:submit 
           targets="templateresult" 
                       clearForm="true"
                       value="Save" 
                       effect="highlight"
                       effectOptions="{ color : '#222222'}"
                       effectDuration="5000"
                       button="true"
                       onCompleteTopics="result"
                       cssClass="submit"
                       cssStyle="margin-right: 65px;margin-bottom: -9px;"
                       indicator="indicator2"
                       onBeforeTopics="validate"
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
		               	onclick="viewTemplate();"
						cssStyle="margin-right: -137px;margin-top: -28px;"
						>
					  	Back
		   </sj:a>
		   
         <sj:div id="resultaddtemplate"  effect="fold">
                    <div id="templateresult"></div>
               </sj:div>
      </center>   
   </div>
</s:form>  
</div>
</div>
</div>


<sj:dialog
   		id="dialog"
   		 title="Example For Template"
   		 autoOpen="false"
   		 modal="true"
   		  openTopics="openEffectDialog"
          closeTopics="closeEffectDialog"
          width="400"
   	      height="150"
          draggable="true"
    	  resizable="true"
   		>
   		 
   		<font face="Verdana" size="0.5" style="font-size: medium;font-family: serif;">
			Dear &lt;Name(60)&gt; ,This is Test Template. 
			</font>
		
   		
   		<br>
   		<table style="width:300px">
<tr>
  <td>Template Id</td>
  <td>Template </td>		
  
  </tr>
  <tr>
  <td>12345</td>
  <td>Dear Sumiti,This is Test Template. </td>		
  
  </tr>

</table>   
   		</sj:dialog>	   

</body>
</html>
