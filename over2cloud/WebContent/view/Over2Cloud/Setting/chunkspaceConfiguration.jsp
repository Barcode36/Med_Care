<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="/js/common/commonvalidation.js"/>"></script>
<script type="text/javascript">
$.subscribe('complete', function(event,data)
        {
          setTimeout(function(){ $("#foldeffect").fadeIn(); }, 50);
          setTimeout(function(){ $("#foldeffect").fadeOut(); }, 4000);
        });

function ChecktheDomainName(DomainAddressDiv,isocountrycode)
{
	var conP = "<%=request.getContextPath()%>";
	var countrycode=$("#"+isocountrycode).val();
	 $.ajax({
				    type : "post",
				    url : conP+"/view/Over2Cloud/Setting/ajexforDomainip.action?countryid="+countrycode+"",
				    success : function(domainIdData) {
			        $("#"+DomainAddressDiv).html(domainIdData);
				},
				   error: function() {
			            alert("error");
			        }
				 });
}
</script>
<div class="page_title"><h1></h1>

<div class="clear"></div>
<div class="middle-content">
	<div class="list-icon">
	<div class="head">Chunk Registration Space</div>
	</div>
	<div class="clear"></div>
	<div style="min-height: 10px; height: auto; width: 100%;"></div>
	<div class="border">
		<div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
  		<div id="errZone" style="float:left; margin-left: 7px"></div>        
        </div>
          <div class="clear"></div>
		<div style="width: 100%; text-align: center; padding-top: 10px;">
	
		</div>
		<div>
			<div class="secHead">Chunk Registration Space Add</div>
		<s:form name="ServerConfiguration"  id="ServerConfiguration_url" action="ServerSpaceConfiguration"  theme="css_xhtml" theme="simple"  method="post">
				<div class="form_menubox">
				  <div class="inputmain">
				  <span id="mandatoryFields" class="pIds" style="display: none; ">slabfromAccount#Slab from#T#n,</span>
	                  <div class="user_form_text">Slab from<font color="red"> *</font>:</div>
	                  <div class="user_form_input inputarea"><s:textfield name="slabfromAccount" id="slabfromAccount" cssClass="form_menu_inputtext" maxlength="20" placeholder="From Account Id"/>
	                  </div>
                  </div>
                  <div class="inputmain">
                     <span id="mandatoryFields" class="pIds" style="display: none; ">slabtoAccount#Slab to#T#n,</span>
	                  <div class="user_form_text1">Slab to<font color="red"> *</font>:</div>
	                  <div class="user_form_input inputarea"><s:textfield name="slabtoAccount" id="slabtoAccount" cssClass="form_menu_inputtext" maxlength="20" placeholder="To Account Id"/>
	                  </div>
             	  </div>
             	</div>
             	<div class="form_menubox">
             	   <div class="inputmain">
             	    <span id="mandatoryFields" class="pIds" style="display: none; ">country#Country#D#a,</span>
	                  <div class="user_form_text">Country:</div>
	                  <div class="user_form_input inputarea">
	                  <s:select 
	                              id="country"
							      name="country" 
							      list="countryList"
							      listKey="iso_code"
							      listValue="contryName"
							      headerKey="-1"
							      headerValue="Country" 
	                              cssClass="select"
                                  cssStyle="width:82%"
	                              onchange="ChecktheDomainName('DomainAddressDiv','country');"
	                              >
	                  </s:select>
	                  </div>
                  </div>
                  <div class="inputmain">
                  <span id="mandatoryFields" class="pIds" style="display: none; ">domainIpName#Domain Address/IP#D#a,</span>
	                  <div class="user_form_text1">Domain Address/IP:</div>
	                  <div class="user_form_input inputarea">
	                  <div id="DomainAddressDiv">
	                  <s:select 
	                              id="domainIpName"
	                              name="domainIpName" 
	                              list="#{'no List':'No List'}" 
	                              headerKey="-1"
	                              headerValue="Select Ip/Domain Name" 
	                              cssClass="select"
                                  cssStyle="width:82%"
	                              />
	                              </div>
	                  </div>
	                  </div>
                  </div>
               
				<!-- Buttons -->
				 <div class="clear"></div>
				<div style="width: 100%; text-align: center; padding-bottom: 10px;">
	                <img id="indicator" src="<s:url value="/images/ajax-loader.gif"/>" alt="Loading..."   style="display:none"/>
	               
	                
	                <sj:submit 
							targets="Result" 
							clearForm="true"
							id="submit"
							value="Register" 
							href="%{ServerConfiguration_url}"
							effect="foldeffect"
							cssClass="submit"
							effectOptions="{float:'right'}"
							effectDuration="5000"
							button="true"
							onCompleteTopics="complete"
							indicator="indicator"
							onBeforeTopics="validate"
		             />					                             			                              
	              <sj:submit 
							 value="Cancel" 
							 cssClass="submit" 
							 effect="highlight"
							 effectOptions="{ color : '#222222'}"  
							 button="true"  
							 onclick="designationView();"
							 />
				 <sj:div id="foldeffect"  effect="fold">
                     <div id="Result"></div>
          			</sj:div>
				</div>
			
			</s:form>
</div>
</div>
</div>
