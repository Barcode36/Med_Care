<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s"  uri="/struts-tags"%>
    <%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
	<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<script type="text/javascript" src="<s:url value="/js/krLibrary/tagJs/krLibrary.js"/>"></script>
  <script src="<s:url value="/js/krLibrary/demo/js/jquery-ui.1.8.20.min.js"/>"></script>
  <script src="<s:url value="/js/krLibrary/tagJs/tagit.js"/>"></script>
  <link rel="stylesheet" type="text/css" href="<s:url value="/js/krLibrary/css1/tagit-stylish-yellow.css"/>">
  <script type="text/javascript">
$.subscribe('result', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#sendmail").fadeIn(); }, 8);
	         setTimeout(function(){ $("#sendmail").fadeOut(); }, 4000);
	       });

  </script>
<SCRIPT type="text/javascript">
	$(function () {
	  var availableTags = [
		"ActionScrip"
	  ];
	  $('#demo3').tagit({tagSource:availableTags, triggerKeys:['enter', 'comma', 'tab']});
	  $('#demo3GetTags').click(function () {
		showTags($('#demo3').tagit('tags'))
	  });
	});
	
	function showTags(tags) {
		var string="";
		for (var i in tags)
		  string += tags[i].label+ ",";
		formsendmail.tags.value=string;
		alert("string"+string);
	  }
	function removeall(tags)
	{
		for (var i in tags)
			tags[i].label= "";
	}
	

  </SCRIPT>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<div class="container_block">
						<div class="main_block" style="margin-top: 20px;">
							<div class="text_heading">
								<span>Send Email and SMS</span>
								<span class="image"><a href="javascript:void();" title="Update this">
									<img src="images/edit.png" width="7" height="7" alt="Update this" title="Update this" /></a>
								</span>
							</div>
						
							<div class="tabs" >
<center>
</center>
<s:form id="formsendmail" name="formsendmail" namespace="/view/contactOver2Cloud" action="sendmailiandsms"   theme="simple"  method="post"enctype="multipart/form-data">
<center>
               <div class="form_menuboxkr" >
            <div class="user_form_text_kr" style="width: 96px">Email One *:</div>
             <ul id="demo3" style="width: 600px;margin-left: -34px;font-size: 10px;">
             </ul>
             <s:hidden id="tagsssssss" name="tags" value="%{#parameters.emailidone}"></s:hidden>
            
            </div>
       
 <div class="form_menubox">
    
				  <div class="user_form_text">Email Two:</div>
                  <div class="user_form_input"><s:textfield name="emailtwo" id="emailtwo" value="%{#parameters.emailidtwo}" cssStyle="margin:0px 0px 10px 0px"  cssClass="form_menu_inputtext"/></div>
			       <div class="user_form_text1">SMS:</div>
                  <div class="user_form_input"><s:checkbox name="sms"  value="true" cssStyle="margin:0px 0px 10px 0px" /></div>
 </div>
 <div class="form_menubox">
    
				  <div class="user_form_text">Subject:</div>
                  <div class="user_form_input"><s:textarea name="subject" id="subject"  value="%{#parameters.subject}"  rows="2" cols="70" cssStyle="margin:0px 0px 10px 0px" /></div>
			 
      </div>
   
	        <div class="type-text">
			<sjr:ckeditor 
					id="mailbody" 
					name="mailbody" 
					rows="5" 
					cols="80" 
					width="830"
				    height="100"
			       value="%{#parameters.first_name}"
					onFocusTopics="focusRichtext"
					onBlurTopics="blurRichtext"
					onChangeTopics="highlightRichtext"
				
				/>
	        </div>
	        <br>
	       </center>
	       <center>
	<div class="fields" style="margin-left: -50px">
<ul>
				<li class="submit" style="background: none;" >
				<div class="type-button">
				<sj:submit
					 value="Cancel" 
					 effect="highlight"
	                 effectOptions="{ color : '#222222'}"
	                 effectDuration="5000"
	                 button="true"
	                 onCompleteTopics="result"
	                 cssClass="submit"
					button="true"
					onclick="contactcancelView();"
				/>
                
                </div>
				</ul>
					<ul>
				<li class="submit" style="background: none;">
					<div class="type-button">
				<sj:submit
				   targets="sendmail" 
	                clearForm="true"
					value="Send Mail" 
					effect="highlight"
	                 effectOptions="{ color : '#222222'}"
	                 effectDuration="5000"
	                 button="true"
	                 onCompleteTopics="result"
	                 cssClass="submit"
	                  indicator="indicator"
	                 onBeforeTopics="validate"
				/>
	           <sj:div id="sendmail"  effect="fold">
                    <div id="sendmail">
                    </div>
               </sj:div>
	           	<img id="indicator" 
					src="images/indicator.gif" 
					alt="Loading..." 
					style="display:none"/>
	           </div>
	        
	        </li>
	        </ul>
	        </div>
	        <br>
        </center>
    </s:form>

</div></div></div></div></div>
</body>
</html>