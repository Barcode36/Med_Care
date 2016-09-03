<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

<html>
<head>
<STYLE type="text/css">
.form_menu_inputtext1{
border: 4px solid #F0F0F0;
    border-radius: 3px 3px 3px 3px;
    box-shadow: 0 0 0 1px #DDDDDD inset;
    float: left;
    font-family: 'Open Sans',sans-serif;
    font-size: 12px;
    /*margin: 5px 0 0 15px;*/
    padding: 3px;
    transition: all 0.3s ease-in-out 0s;
    width: 125px;
}
.form_menu_inputselect1{

border: 4px solid #F0F0F0;
    box-shadow: 0 0 0 1px #DDDDDD inset;
    float: left;
    font-family: 'Open Sans',sans-serif;
    font-size: 12px;
    /*margin: 5px 0 0 15px;*/
    padding: 3px;
    transition: all 0.3s ease-in-out 0s;
    width: 130px;}
    .user_form_input1{width:14px; text-align:left; margin-right:10px; font-family:Arial, Helvetica, sans-serif; font-size:12px; color:#194d65;  font-weight:bold; float:left; line-height:20px;}
    .user_form_text1{width:95px; text-align:left; margin-right:10px; font-size:12px;  font-weight:600; float:left; line-height:30px;}
</STYLE>

<script type="text/javascript">
$.subscribe('complete', function(event,data)
        {
          setTimeout(function(){ $("#foldeffect1").fadeIn(); }, 50);
          setTimeout(function(){ $("#foldeffect1").fadeOut(); }, 4000);
        });
</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
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
			<div class="secHead"><s:property value="orgnization"/> Dashboard</div>
		<s:form name="RequestInformation"  id="AddRequest_URL" action="allrequestpennel"  theme="css_xhtml" theme="simple"  method="post">
				
				<s:hidden name="orgnization" value="orgnization"></s:hidden>
				
				<div class="form_menubox">
				<div class="inputmain">
                  <div class="user_form_text">Account Id</div>
                  <div class="user_form_input inputarea"><s:textfield name="accountid1" id="accountid1" maxlength="150" placeholder="Enter Application Name" readonly="true"  cssClass="form_menu_inputtext"/></div>
             	</div>
             	
             	<div class="inputmain">
                  <div class="user_form_text1">Country</div>
                  <div class="user_form_input inputarea"><s:textfield name="country" id="country" maxlength="150" placeholder="Enter Application Name" readonly="true"  cssClass="form_menu_inputtext"/></div>
             	</div>
             	</div>
             	
             	
             	<div class="form_menubox">
                  <div class="user_form_text">Type Of Request</div>
                  <div class="user_form_input">
                  <s:select 
                              id="typeOfRequest"
                              name="typeOfRequest" 
                              list="{'Request Type','Feedback Type','Complain Type'}"
                              headerKey="-1"
                              headerValue="Select Type" 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                  </s:select>
                  </div>
             	</div>
             	<div class="form_menubox">
               <div class="inputmain" >
                  <div class="user_form_text">Subject</div>
                  <div class="user_form_input"><s:textfield name="subject" id="subject" maxlength="150" placeholder="Enter Subject record" cssClass="form_menu_inputtext"/></div>
             	</div>
             	<div class="inputmain">
                  <div class="user_form_text1">Description</div>
                  <div class="user_form_input inputarea"><s:textarea name="description" id="description"  placeholder="Enter Description  in Brife" cssClass="form_menu_inputtext"/></div>
             	</div>
             	</div>
				<!-- Buttons -->
				<div class="fields" align="center">
				<ul style="align:center;">
				<li class="submit" style="background: none;">
					
					<div class="type-button" align="center">
	                <img id="indicator" src="<s:url value="/images/ajax-loader.gif"/>" alt="Loading..."   style="display:none"/>
	                <sj:div id="foldeffect"  effect="fold">
                     <div id="Result"></div>
          			</sj:div>
	                
	                <sj:submit
	                    targets="Result" 
					    clearForm="true"
						id="submit"
						value="Register" 
						href="%{AddRequest_URL}"
						effect="foldeffect"
						effectOptions="{float:'right'}"
						effectDuration="5000"
						cssClass="Submit"
						button="true"
						onCompleteTopics="complete;"
						indicator="indicator"
		             />
		             
	               </div>
	             </li>
				</ul>
				</div>
			</s:form>
</div>
</div>
</div>
</body>
</html>