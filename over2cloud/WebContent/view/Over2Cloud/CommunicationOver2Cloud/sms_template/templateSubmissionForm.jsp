<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

</head>
<script type="text/javascript">

$.subscribe('result', function(event,data)
	       {
	         setTimeout(function(){ $("#templatemsg").fadeIn(); }, 1000);
	         setTimeout(function(){ $("#templatemsg").fadeOut(); }, 4000);
	       });



$.subscribe('validate', function(event,data)
{
    var mystring = $(".pIds").text();
    alert (mystring);
    var fieldtype = mystring.split(",");
    var pattern = /^\d{10}$/;
    for(var i=0; i<fieldtype.length; i++)
    {
        
        var fieldsvalues = fieldtype[i].split("#")[0];
        var fieldsnames = fieldtype[i].split("#")[1];
        var colType = fieldtype[i].split("#")[2]; 
        //fieldsType[i]=first_name#t
        $("#"+fieldsvalues).css("background-color","");
        errZone.innerHTML="";
        if(fieldsvalues!= "" )
        {
            if(colType =="T")
            {
	            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Values </div>";
	            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
	            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
	            $("#"+fieldsvalues).focus();
	            $("#"+fieldsvalues).css("background-color","#ff701a");
	            event.originalEvent.options.submit = false;
	            break;
            }
          }
       }
    } 
);



</script>
<body>
<s:form id="formtwovbvbv"  action="insertemplatemsgurl" theme="css_xhtml"  method="post" >
  <s:hidden id="date" name="date" value="%{#parameters.date}"></s:hidden>
  <s:hidden id="hours" name="hours" value="%{#parameters.hours}"></s:hidden>
  <s:hidden id="day" name="day" value="%{#parameters.day}"></s:hidden>
  <s:hidden id="root" name="root" value="%{#parameters.root}"></s:hidden>
 <center>
	 <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
	            <div id="errZone" style="float:left; margin-left: 7px"></div>        
	 </div>
 </center>
 <div style="width:500px; height: 300px;">
 <s:hidden id="tempId" name="tempId" value="%{id}"></s:hidden>
 <s:hidden id="templateData" name="templateData" value="%{StoredTemp}"></s:hidden>
  <table width="90%" cellspacing="0" cellpadding="0" border="0" class="secContent">
				        <tbody width="100%">
		        		<tr>
							<td>Mobile No<font color="red">*</font>:
		      				
		      				<s:textfield name="mobileNo" id="mobileNo"  value="%{#parameters.mobileNo}" cssClass="textField" placeholder="Enter Data" cssStyle="margin: 0px 0px 10px; width: 300px; height: 30px;"/>
		      				</td>
							
		      		   </tr>
		      		   </tbody>
		      		   </table>
 
 <s:textfield id="lengthrange" name="lengthrange" value="%{lengthOfTextfield}"></s:textfield>
                       
<s:iterator value="tempList" status="status" var="index">

 <s:if test="%{key == 'textfield'}">
 <span id="complSpan" class="pIds" style="display: none; ">textfield<s:property value="%{#status.count}"/>#Test#T#,</span>
 <s:textfield name="textfield%{#status.count}"  id="%{key}" onchange="validateCsv()"  maxlength="%{default_value}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin: 0px 0px 10px; width: 300px; height: 30px;" />
 </s:if>
 <s:else>
 <s:property value="%{key}"/>
 </s:else>
</s:iterator>


<br>
<br>
<div class="clear"></div>
             	
				<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				 <div class="clear"></div> 
				<center>
					<div class="type-button">
	                <sj:submit 
	                        targets="templatetarget" 
	                        clearForm="true"
	                        value=" Send SMS " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="result"
	                        cssClass="submit"
	                        indicator="indicator2"
	                        onBeforeTopics=""
	                        />
	                  
	               </div>
			</center>
				<sj:div id="templatetarget"  effect="fold">
                    <div id="templatemsg"></div>
               </sj:div>

</div>
</s:form>
</body>
</html>