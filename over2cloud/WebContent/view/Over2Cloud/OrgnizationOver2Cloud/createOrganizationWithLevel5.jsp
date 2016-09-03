<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<html>
<head>

<sj:head locale="en" jqueryui="true" jquerytheme="mytheme"
	customBasepath="%{contextz}" />
<script src="<s:url value="/js/organization.js"/>"></script>
<script src="<s:url value="/js/common/commonvalidation.js"/>"></script>
<SCRIPT type="text/javascript">
	$.subscribe('level1', function(event, data) {
		//document.getElementById("indicator1").style.display="none";
			setTimeout( function() {
				$("#orglevel1Div").fadeIn();
			}, 10);
			setTimeout( function() {
				$("#orglevel1Div").fadeOut();
			}, 4000);
		});
	$.subscribe('level2', function(event, data) {
		//document.getElementById("indicator1").style.display="none";
			setTimeout( function() {
				$("#orglevel2").fadeIn();
			}, 10);
			setTimeout( function() {
				$("#orglevel2").fadeOut();
			}, 4000);
		});
	$.subscribe('level3', function(event, data) {
		//document.getElementById("indicator1").style.display="none";
			setTimeout( function() {
				$("#orglevel3").fadeIn();
			}, 10);
			setTimeout( function() {
				$("#orglevel3").fadeOut();
			}, 4000);
		});
	$.subscribe('level4', function(event, data) {
		//document.getElementById("indicator1").style.display="none";
			setTimeout( function() {
				$("#orglevel4").fadeIn();
			}, 10);
			setTimeout( function() {
				$("#orglevel4").fadeOut();
			}, 4000);
		});
	$.subscribe('level5', function(event, data) {
		//document.getElementById("indicator1").style.display="none";
			setTimeout( function() {
				$("#orglevel5").fadeIn();
			}, 10);
			setTimeout( function() {
				$("#orglevel5").fadeOut();
			}, 4000);
		});



	function viewOrg()
	{
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			var conP = "<%=request.getContextPath()%>";
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/commonModules/beforeOrganizationView.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}


	function reset(formId) {
		  $("#formId").trigger("reset"); 
		}
</script>

</head>
<body>
<div class="clear"></div>

<div class="middle-content">

	<div class="list-icon"><div class="head">Organization</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div>
	</div><div class="clear"></div>	
	<div style="min-height: 10px; height: auto; width: 100%;"></div>
	
	<div class="border">
		<div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
  		<div id="errZone" style="float:left; margin-left: 7px"></div>        
        </div>
  
		<div>
				<br><br>
	<s:if test="globalLevel>1">
				<s:form id="formTwo" name="formTwo" namespace="/view/Over2Cloud/commonModules" action="createOrganizationLevel2" theme="simple"  method="post"enctype="multipart/form-data" >
				<div id="ajaxOrganizationDetails"></div>
			
           <span id="mandatoryFields" class="pIds" style="display: none; ">locCompany#<s:property value="%{organizationLocLevel2Name}"/>#D#a,</span>
				<table width="90%" cellspacing="0" cellpadding="0" border="0" class="secContent">
				<tbody width="100%">
				<tr>
					<td width="25%" class="label"><s:property value="%{organizationLocLevel2Name}"/>:</td>
					<td width="25%" class="element"><span class="needed"></span>
						<s:select 
		                              id="locCompany"
		                              name="locCompany" 
		                              list="countryList"
		                              headerKey="-1"
		                              headerValue="Select Location" 
		                              cssClass="select"
		                              cssStyle="width:82%"
		                              >
		                  </s:select>
		                  <div id="errorlocCompany" class="errordiv" style="color: red;"></div>
					</td>
					<s:if test="organizationRegComLevel2">
					<span id="mandatoryFields" class="pIds" style="display: none; ">locCompanyRegCountry#<s:property value="%{organizationRegComLevel2Name}"/>#D#a,</span>
						<td width="17%" class="label mandatory"><s:property value="%{organizationRegComLevel2Name}"/>:</td>
						<td width="25%" class="element"><span class="needed"></span>
							<s:select 
                              id="locCompanyRegCountry"
                              name="locCompanyRegCountry" 
                              list="countryList"
                              headerKey="-1"
                              headerValue="Select Country" 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                   
                  			</s:select>
						</td>
					</s:if>
				</tr>
			
			<s:iterator value="configurationForLevel2" status="counter">
			  <s:if test="%{mandatory}">
				   <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				 </s:if>
					<s:if test="#counter.odd">
					<tr>
							<s:if test="%{mandatory}">
							<td width="25%" class="label"><s:property value="%{value}"/>:</td>
		      				<td width="25%" class="element"><span class="needed"></span>
		      				<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data"/>
		      				</td>
		      				</s:if>
		      				<s:else>
		      				<td width="25%" class="label"><s:property value="%{value}"/>:</td>
		      				<td width="25%" class="element">
		      				<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data"/>
		      				</td>
						    </s:else>
					     </s:if>
					<s:else>
							<s:if test="%{mandatory}">
							<td width="25%" class="label"><s:property value="%{value}"/>:</td>
							<td width="25%" class="element"><span class="needed"></span>
		      					<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data"/>
		      				</td>
		      				</s:if>
		      				<s:else>
		      				<td width="25%" class="label"><s:property value="%{value}"/>:</td>
							<td width="25%" class="element">
		      					<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data"/>
		      				</td>
		      				</s:else>
					</s:else>
			</s:iterator>
	</tbody>
</table>
<div class="clear"></div><br><br>
<div style="width: 100%; text-align: center; padding-bottom: 70px;">
	<sj:submit 
	     targets="orglevel2Div" 
	     clearForm="true"
	     value="Save" 
	     effect="highlight"
	     effectOptions="{ color : '#222222'}"
	     effectDuration="5000"
	     button="true"
	     onCompleteTopics="level2"
	     cssClass="submit"
	     indicator="indicator3"
	     onBeforeTopics="validate"
     />
     
      <sj:a
      		 href="#"
			 value="reset" 
			 cssClass="submit" 
			 effectOptions="{ color : '#222222'}"  
			 button="true"  
			 title="Cancel"
			 onclick="reset('formTwo');"
			 >
			 Reset
			 </sj:a>
     
	 <sj:a
      		 href="#"
			 value="Cancel" 
			 cssClass="submit" 
			 effectOptions="{ color : '#222222'}"  
			 button="true"  
			 onclick="viewOrg()"
			 title="Cancel"
			 >
			 Back
			 </sj:a>
	
	<sj:div id="orglevel2"  effect="fold">
		<div id="orglevel2Div"></div>
	</sj:div></div>
</s:form>
</s:if>
	<s:if test="globalLevel>2">
				<s:form id="formThree" name="formThree" namespace="/view/Over2Cloud/commonModules" action="createOrganizationLevel3" theme="simple"  method="post"enctype="multipart/form-data" >
				  <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
  				<div id="errZone" style="float:left; margin-left: 7px"></div>        
           </div>
           <span id="mandatoryFields" class="pIds" style="display: none; ">locCompany#<s:property value="%{organizationLocLevel2Name}"/>#D#a,</span>
				<table width="90%" cellspacing="0" cellpadding="0" border="0" class="secContent">
				<tbody width="100%">
				<tr>
					<td width="25%" class="label"><s:property value="%{organizationLocLevel3Name}"/>:</td>
					<td width="25%" class="element">
					<div id="regsiteredLoc"><span class="needed"></span>
						<s:select 
		                              id="locationCompany"
		                              name="locationCompany" 
		                              list="countryList"
		                              headerKey="-1"
		                              headerValue="Select Location" 
		                              cssClass="select"
		                              cssStyle="width:82%"
		                              >
		                  </s:select>
		                  </div>
		                  <div id="errorlocCompany" class="errordiv" style="color: red;"></div>
					</td>
				
				</tr>
			
			<s:iterator value="configurationForLevel3" status="counter">
			  <s:if test="%{mandatory}">
				   <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				 </s:if>
					<s:if test="#counter.odd">
				
					<tr>
						 <s:if test="%{key == 'branchAddress' || key == 'branchLandmark' || key == 'branchCity'}">
							<td width="25%" class="label"><s:property value="%{value}"/>:</td>
		      				<td width="25%" class="element"><span class="needed"></span>
		      					
		      					<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data"/>
		      					<div id="error<s:property value="%{key}"/>" class="errordiv" style="color: red;"></div>
		      				</td>
						</s:if>
						<s:else>
							<td width="25%" class="label"><s:property value="%{value}"/>:</td>
		      				<td width="25%" class="element">
		      					<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data"/>
		      					<div id="error<s:property value="%{key}"/>" class="errordiv" style="color: red;"></div>
		      				</td>
						</s:else>
					</s:if>
					<s:else>
                            <s:if test="%{key == 'branchAddress' || key == 'branchLandmark' || key == 'branchCity'}">
							<td width="25%" class="label"><s:property value="%{value}"/>:</td>
							<td width="25%" class="element"><span class="needed"></span>
		      					<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data"/>
		      					<div id="error<s:property value="%{key}"/>" class="errordiv" style="color: red;"></div>
		      				</td>
						</s:if>
						<s:else>
							<td width="25%" class="label"><s:property value="%{value}"/>:</td>
							<td width="25%" class="element">
		      					<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data"/>
		      					<div id="error<s:property value="%{key}"/>" class="errordiv" style="color: red;"></div>
		      				</td>
						</s:else>
					
					</s:else>
			</s:iterator>
	</tbody>
</table>
<div class="clear"></div>
<div style="width: 100%; text-align: center; padding-bottom: 10px;">
	<sj:submit 
	      targets="orglevel3Div" 
          clearForm="true"
          value="  Register  " 
          effect="highlight"
          effectOptions="{ color : '#222222'}"
          effectDuration="5000"
          button="true"
          onCompleteTopics="level3"
          cssClass="submit"
          indicator="indicator4"
          onBeforeTopics="validate"
     />
	 <sj:a
      		href="#"
			 value="Cancel" 
			 cssClass="button" 
			 effectOptions="{ color : '#222222'}"  
			 button="true"  
			 onclick="viewOrg()"
			 title="Cancel"
			 >
			 Cancel
			 </sj:a>
	
		<sj:div id="orglevel3"  effect="fold">
                    <div id="orglevel3Div"></div>
               </sj:div>
</s:form>
</s:if>
	<s:if test="globalLevel>3">
				<s:form id="formfour" name="formfour" namespace="/view/Over2Cloud/commonModules" action="createOrganizationLevel4" theme="simple"  method="post"enctype="multipart/form-data" >
				  <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
  				<div id="errZone" style="float:left; margin-left: 7px"></div>        
           </div>
           <span id="mandatoryFields" class="pIds" style="display: none; ">locCompany#<s:property value="%{organizationLocLevel2Name}"/>#D#a,</span>
				<table width="90%" cellspacing="0" cellpadding="0" border="0" class="secContent">
				<tbody width="100%">
				<tr>
					<td width="25%" class="label"><s:property value="%{organizationLocLevel4Name}"/>:</td>
					<td width="25%" class="element"><span class="needed"></span>
					 <div id="regsiteredLevel3">
						<s:select 
		                              id="level3Registration"
		                              name="level3Registration" 
		                              list="countryList"
		                              headerKey="-1"
		                              headerValue="Select Location" 
		                              cssClass="select"
		                              cssStyle="width:82%"
		                              >
		                  </s:select>
		                  </div>
		                  <div id="errorlocCompany" class="errordiv" style="color: red;"></div>
					</td>
				
				</tr>
			
			<s:iterator value="configurationForLevel4" status="counter">
			  <s:if test="%{mandatory}">
				   <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				 </s:if>
					<s:if test="#counter.odd">
					<tr>
                            <s:if test="%{key == 'level4Address' || key == 'level4Landmark' || key == 'level4City'}">
							<td width="25%" class="label"><s:property value="%{value}"/>:</td>
		      				<td width="25%" class="element"><span class="needed"></span>
		      					
		      					<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data"/>
		      					<div id="error<s:property value="%{key}"/>" class="errordiv" style="color: red;"></div>
		      				</td>
						</s:if>
						<s:else>
							<td width="25%" class="label"><s:property value="%{value}"/>:</td>
		      				<td width="25%" class="element">
		      					<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data"/>
		      					<div id="error<s:property value="%{key}"/>" class="errordiv" style="color: red;"></div>
		      				</td>
						</s:else>
					</s:if>
					<s:else>
                            <s:if test="%{key == 'level4Address' || key == 'level4Landmark' || key == 'level4City'}">
							<td width="25%" class="label"><s:property value="%{value}"/><font color="red">*</font>:</td>
							<td width="25%" class="element">
		      					<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data"/>
		      					<div id="error<s:property value="%{key}"/>" class="errordiv" style="color: red;"></div>
		      				</td>
						</s:if>
						<s:else>
							<td width="25%" class="label"><s:property value="%{value}"/>:</td>
							<td width="25%" class="element">
		      					<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data"/>
		      					<div id="error<s:property value="%{key}"/>" class="errordiv" style="color: red;"></div>
		      				</td>
						</s:else>
					
					</s:else>
			</s:iterator>
	</tbody>
</table>
<div class="clear"></div>
<div style="width: 100%; text-align: center; padding-bottom: 10px;">
	<sj:submit 
	    targets="orglevel4Div" 
        clearForm="true"
        value="  Register  " 
        effect="highlight"
        effectOptions="{ color : '#222222'}"
        effectDuration="5000"
        button="true"
        onCompleteTopics="level4"
        cssClass="submit"
        indicator="indicator5"
        onBeforeTopics="validateorg4"
     />
	 <sj:a
      		href="#"
			 value="Cancel" 
			 cssClass="button" 
			 effectOptions="{ color : '#222222'}"  
			 button="true"  
			 onclick="viewOrg()"
			 title="Cancel"
			 >
			 Cancel
			 </sj:a>
	
		<sj:div id="orglevel4"  effect="fold">
                    <div id="orglevel4Div"></div>
               </sj:div>
</s:form>
</s:if>
<s:if test="globalLevel>4">
				<s:form id="formfour" name="formfour" namespace="/view/Over2Cloud/commonModules" action="createOrganizationLevel5" theme="simple"  method="post"enctype="multipart/form-data" >
				  <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
  				<div id="errZone" style="float:left; margin-left: 7px"></div>        
           </div>
           <span id="mandatoryFields" class="pIds" style="display: none; ">locCompany#<s:property value="%{organizationLocLevel2Name}"/>#D#a,</span>
				<table width="90%" cellspacing="0" cellpadding="0" border="0" class="secContent">
				<tbody width="100%">
				<tr>
					<td width="25%" class="label"><s:property value="%{organizationLocLevel5Name}"/>:</td>
					<td width="25%" class="element"><span class="needed"></span>
					 <div id="regsiteredLevel4">
						<s:select 
		                              id="level4Registration"
		                              name="level4Registration" 
		                              list="countryList"
		                              headerKey="-1"
		                              headerValue="Select Location" 
		                              cssClass="select"
		                              cssStyle="width:82%"
		                              >
		                  </s:select>
		                  </div>
		                 
					</td>
				
				</tr>
			
			<s:iterator value="configurationForLevel4" status="counter">
			  <s:if test="%{mandatory}">
				   <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				 </s:if>
					<s:if test="#counter.odd">
					<tr>
							<s:if test="%{key == 'level5Address' || key == 'level5Landmark' || key == 'level5City'}">
							<td width="25%" class="label"><s:property value="%{value}"/>:</td>
		      				<td width="25%" class="element">
		      					<span class="needed"></span>
		      					<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data"/>
		      					<div id="error<s:property value="%{key}"/>" class="errordiv" style="color: red;"></div>
		      				</td>
						</s:if>
						<s:else>
							<td width="25%" class="label"><s:property value="%{value}"/>:</td>
		      				<td width="25%" class="element">
		      					<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data"/>
		      					<div id="error<s:property value="%{key}"/>" class="errordiv" style="color: red;"></div>
		      				</td>
						</s:else>
					</s:if>
					<s:else>

							<s:if test="%{key == 'level5Address' || key == 'level5Landmark' || key == 'level5City'}">							<td width="25%" class="label"><s:property value="%{value}"/><font color="red">*</font>:</td>
							<td width="25%" class="element">
		      					<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data"/>
		      					<div id="error<s:property value="%{key}"/>" class="errordiv" style="color: red;"></div>
		      				</td>
						</s:if>
						<s:else>
							<td width="25%" class="label"><s:property value="%{value}"/>:</td>
							<td width="25%" class="element">
		      					<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data"/>
		      					<div id="error<s:property value="%{key}"/>" class="errordiv" style="color: red;"></div>
		      				</td>
						</s:else>
					
					</s:else>
			</s:iterator>
	</tbody>
</table>
<div class="clear"></div>
<div style="width: 100%; text-align: center; padding-bottom: 10px; margin-left: 70px;">
	<sj:submit 
	    targets="orglevel5Div" 
        clearForm="true"
        value="Save" 
        effect="highlight"
        effectOptions="{ color : '#222222'}"
        effectDuration="5000"
        button="true"
        onCompleteTopics="level5"
        cssClass="submit"
        indicator="indicator6"
        onBeforeTopics="validateorg5"
      />
      
      <sj:a
      		href="#"
			 value="Cancel" 
			 cssClass="button" 
			 effectOptions="{ color : '#222222'}"  
			 button="true"  
			 onclick="viewOrg()"
			 title="Cancel"
			 >
			 Cancel
			 </sj:a>
			 
		</div>
		<sj:div id="orglevel5"  effect="fold">
                    <div id="orglevel5Div"></div>
            </sj:div>
             
</s:form>
</s:if>
</div>
</div>
</div>
</body>
</html>