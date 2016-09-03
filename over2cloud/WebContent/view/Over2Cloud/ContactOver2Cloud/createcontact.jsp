<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>

	<script src="js/common/commonvalidation.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
function getAllContAdressingdetails(values)
{ 
 var conP = '<%=request.getContextPath()%>';
	$.ajax({
	    type : "post",
	    url : conP+"/view/contactOver2Cloud/getAdressContactDataListsss.action?mappedid="+values,
	    success : function(contactDataaaa) {
		 $("#AjaxDivpTempsss").html(contactDataaaa);
	},
	   error: function() {
	        alert("error");
	    }
	 });
}
</script>
<script type="text/javascript">
function downloadformat(){
      var actionname = "downloadContactformate";
    $("#downloaddailcontactformate").load("view/contactOver2Cloud/progressbar.action?actionurl="+actionname)
    $("#downloaddailcontactformate").dialog('open');
}
</script>
<script type="text/javascript">
function downloadformate(){
var myArray = [];
    $(":checkbox:checked").each(function() {
    if(this.value!= "true"){
     myArray.push(this.value);
    }
    });
       var conP = '<%=request.getContextPath()%>';
       $.ajax({
			    type : "post",
			    url : conP +"/view/contactOver2Cloud/downloadContactformate.action?titles="+myArray,
			   success : function(dividdd) {
			 $("#divid").html(dividdd);
			 $("#divid").fadeIn(3000);
			 $("#divid").fadeOut(3000);
			  },
			   error: function() {
			        alert("error");
			    }
			 });

}
</script>
<script type="text/javascript">
$.subscribe('validateExcel', function(event,data){
                     
               var status=false;
               if(document.xlsx.CAExcel.value!=""){
               temp=document.xlsx.CAExcel.value.split(".");
               if(temp[1]!="xls" && temp[1]!="xlsx")
               {
               excelError.innerHTML="<div class='user_form_inputError2'>Select only xls file</div>";
               document.xlsx.CAExcel.focus();
               status = false;
               }
               else
               {
                       excelError.innerHTML="";
                       status = true;
                       }}
               
         if(document.xlsx.CAExcel.value==""){
                 excelError.innerHTML="<div class='user_form_inputError2'>Select An Excel</div>";
                 document.xlsx.CAExcel.focus();
                 event.originalEvent.options.submit = false;
         }
         else {
                 if
                 (status){
                 excelError.innerHTML="";
                 $('#CAExcelDialog').dialog('open');
                 event.originalEvent.options.submit = true;
                 } }
              });  
</script>
<script type="text/javascript">
$.subscribe('result', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#bscconatct").fadeIn(); }, 10);
	         setTimeout(function(){ $("#bscconatct").fadeOut(); }, 4000);
	       });
$.subscribe('result1', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#contactaddrs").fadeIn(); }, 10);
	         setTimeout(function(){ $("#contactaddrs").fadeOut(); }, 4000);
	       });

function getAllContactList(AjaxDivpTemp)
{
     var conP = '<%=request.getContextPath()%>';
	$.ajax({
	    type : "post",
	    url : conP+"/view/contactOver2Cloud/getContactDataList.action",
	    success : function(contactData) {

		$("#"+AjaxDivpTemp).html(contactData);
	},
	   error: function() {
	        alert("error");
	    }
	 });
}

</script>


</head>

<div class="container_block">
<div style=" float:left; padding:0px 5%; width:90%;">
<s:if test= "basicfieldsheader!=null">
<div class="sub_container_block_sub">
						<div class="main_block">
							<div class="text_heading">
								<span><s:property value="%{basicfieldsheader}"/> </span>
								<span class="image"><a href="javascript:void();" title="Update this">
									<img src="images/edit.png" width="7" height="7" alt="Update this" title="Update this" /></a>
								</span>
							</div>
							<div class="tabs">
                            <s:form id="upload_CA_excel" theme="css_xhtml" action="uploadCAExcel" method="post" enctype="multipart/form-data" name="xlsx">
           <div class="form_menubox">
          <div class="user_form_text">Excel Upload:</div>
         <div class="user_form_input"><s:file name="CAExcel" id="CAExcel"  cssClass="form_menu_inputtext"/></div>
                   <div id="excelError"></div>
                   <div class="user_form_text1">Excel Format </div>
            <div class="user_form_input">
            <a href="javascript:downloadformat()";>Download </a>
            </div>
            
             <sj:submit 
                       targets="uploadCAExcel" 
                       clearForm="true"
                       value="  Upload  " 
                       button="true"
                       onBeforeTopics="validateExcel"
                       />
                      </div>
                        <sj:dialog id="CAExcelDialog" autoOpen="false" closeOnEscape="true" modal="true" title="Confirm Contact Details" width="900" height="370" showEffect="slide" hideEffect="explode" >
                       <sj:div id="foldeffectExcel"  effect="fold">
                   <div id="saveExcel"></div>
                   </sj:div>
                       <div id="uploadCAExcel"></div>
                       </sj:dialog>
              </s:form>
	
		
       
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formone" name="formone" namespace="/view/contactOver2Cloud" action="addcontactbasicDetails" theme="simple"  method="post"enctype="multipart/form-data" >
	             		        <s:hidden name="mappedtablele" key="mappedtablele" value="%{mappedtablele}"  />   
				             <s:hidden name="basicdetails_table" key="basicdetails_table" value="%{basicdetails_table}"  />
				             <s:hidden name="createtbasicdetails_table" key="createtbasicdetails_table" value="%{createtbasicdetails_table}"  />
		   <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
  				<div id="errZone" style="float:left; margin-left: 7px"></div>        
           </div>
				<div class="form_menubox">
				<s:if test= "addressfieldsheader!=null">
			   <div class="user_form_text">Sir Name:</div>
			   <div class="user_form_input">
				<s:select 
				            cssClass="form_menu_inputselect"
                      
                              id="sirname"
                              name="sirname" 
                              list="{'Mr.','Ms.','Mrs.','Miss.','Dr.','Prof.','Rev','Other'}" 
                              headerKey="-1"
                              headerValue="- Sir Name - " 
                              > 
                  </s:select>
                  </div>
                  </s:if>
                  </div>
              	   <div class="form_menubox">
				 <s:iterator value="listbasicsconfiguration" status="counter">
				  <s:if test="%{mandatory}">
				   <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				 </s:if>
				 <s:if test="#counter.count%2 != 0">
				 <s:if test="%{mandatory}">
                  <div class="inputmain">
                  <div class="user_form_text"><s:property value="%{field_name}"/>:
                  </div>
                  <div class="user_form_input">
                  <span class="needed"></span><s:textfield name="%{field_value}" id="%{field_value}"  cssStyle="margin:0px 0px 10px 0px"  cssClass="form_menu_inputtext" placeholder="Enter Data"/></div>
				 <div id="error<s:property value="%{field_value}"/>" class="errordiv">
				 </div>
				 </div>
				  </s:if>
				  <s:else>
				   <div class="inputmain"><div class="user_form_text"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input"><s:textfield name="%{field_value}" id="%{field_value}"  cssStyle="margin:0px 0px 10px 0px"  cssClass="form_menu_inputtext" placeholder="Enter Data"/></div>
				 <div id="error<s:property value="%{field_value}"/>" class="errordiv"></div></div>
				  </s:else>
				  
				  </s:if><s:else>
				   <s:if test="%{mandatory}">
				  <div class="inputmain"><div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input"><span class="needed"></span><s:textfield name="%{field_value}" id="%{field_value}" cssStyle="margin:0px 0px 10px 0px"  cssClass="form_menu_inputtext" placeholder="Enter Data"/></div>
				   <div id="error<s:property value="%{field_value}"/>" class="errordiv"></div></div>
				  </s:if>
				  <s:else>
				  <div class="inputmain"><div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input"><s:textfield name="%{field_value}" id="%{field_value}"  cssStyle="margin:0px 0px 10px 0px"  cssClass="form_menu_inputtext" placeholder="Enter Data"/></div>
				   <div id="error<s:property value="%{field_value}"/>" class="errordiv"></div></div>
				  </s:else>
				
				</s:else>
				 </s:iterator>
				 </div>
             	   <div class="form_menubox">
				 <s:iterator value="listbasicPerDateCalendr" status="counter">
				  <s:if test="%{mandatory}">
				   <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{field_value}"/>#<s:property value="%{field_name}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
				 </s:if>
				 <s:if test="#counter.count%2 != 0">
				 <s:if test="%{mandatory}">
                  <div class="inputmain">
                  <div class="user_form_text"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input">
                  <span class="needed"></span><sj:datepicker name="%{field_value}" id="%{field_value}" changeMonth="true" changeYear="true" yearRange="1890:2020" cssStyle="margin:0px 0px 10px 0px" showOn="focus" displayFormat="dd-mm-yy" cssClass="form_menu_inputtext" placeholder="Select Date" readonly="true"/></div>
				 </div>
				  </s:if>
				  <s:else>
				   <div class="inputmain"><div class="user_form_text"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input"><sj:datepicker name="%{field_value}" id="%{field_value}" changeMonth="true" changeYear="true" yearRange="1890:2020" cssStyle="margin:0px 0px 10px 0px" showOn="focus" displayFormat="dd-mm-yy" cssClass="form_menu_inputtext" placeholder="Select Date" readonly="true"/></div>
				 </div>
				  </s:else>
				  </s:if>
				  <s:else>
				  <s:if test="%{mandatory}">
				  <div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input"><span class="needed"></span><sj:datepicker name="%{field_value}" id="%{field_value}" changeMonth="true" changeYear="true" yearRange="1890:2020" cssStyle="margin:0px 0px 10px 0px" showOn="focus" displayFormat="dd-mm-yy" cssClass="form_menu_inputtext" placeholder="Select Date" readonly="true"/></div>
				  </s:if>
				  <s:else>
				  <div class="inputmain"><div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input"><sj:datepicker name="%{field_value}" id="%{field_value}" changeMonth="true" changeYear="true" yearRange="1890:2020" cssStyle="margin:0px 0px 10px 0px" showOn="focus" displayFormat="dd-mm-yy" cssClass="form_menu_inputtext" placeholder="Select Date" readonly="true"/></div>
				   <div id="error<s:property value="%{field_value}"/>" class="errordiv"></div></div>
				  </s:else>
				
				</s:else>
				 </s:iterator>
				 </div>
				   <div class="form_menubox">
				 <s:iterator value="listbasicPerdropdown" status="counter">
				 <s:if test="#counter.count%2 != 0">
                  <div class="user_form_text"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input"><s:textfield name="%{field_value}" id="%{field_value}" cssStyle="margin:0px 0px 10px 0px" maxlength="50" placeholder="Enter Data" cssClass="form_menu_inputtext"/></div>
				  </s:if><s:else>
				  <div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input"><s:textfield name="%{field_value}" id="%{field_value}" cssStyle="margin:0px 0px 10px 0px" maxlength="50" placeholder="Enter Data" cssClass="form_menu_inputtext"/></div>
				</s:else>
				 </s:iterator>
				 </div>
				<center>
				<img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/>
				</center>
				<div class="fields">
					<ul>
				<li class="submit" style="background: none;">
				<div class="type-button">
				  <sj:submit 
	                        value="Cancel" 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        cssClass="submit"
	                        onclick="contactcancelView();"
	                        />
                </div>
				</ul>
				<ul>
				<li class="submit" style="background: none;">
					<div class="type-button">
	                <sj:submit 
	                        targets="conatbsic" 
	                        clearForm="true"
	                        value="  Register  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="result"
	                        cssClass="submit"
	                        onBeforeTopics="validate"
	                        indicator="indicator2"
	                        />
	          </div>
				<sj:div id="bscconatct"  effect="fold">
                    <div id="conatbsic"></div>
               </sj:div>
               </li>
               </ul>
               </div>
			</s:form>
		
</div>
</div>
</div>
</div>
</div>
</s:if>
</div>
</div>
<div class="container_block">
<div style=" float:left; padding:0px 5%; width:90%;">
<s:if test= "addressfieldsheader!=null">
<div class="sub_container_block_sub">
						<div class="main_block">
							<div class="text_heading">
								<span><s:property value="%{addressfieldsheader}"/> </span>
								<span class="image"><a href="javascript:void();" title="Update this">
									<img src="images/edit.png" width="7" height="7" alt="Update this" title="Update this" /></a>
								</span>
							</div>
					
							<div class="tabs">
<div class="form_inner" id="form_reg">
		   <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
  				<div id="addresserrZone" style="float:left; margin-left: 7px"></div>        
           </div>
<div class="page_form">

	<s:form id="formTwo" name="formTwo" cssClass="cssn_form" namespace="/view/contactOver2Cloud" action="addcontactAddressingDetails" theme="simple"  method="post"enctype="multipart/form-data" >
				        <s:hidden name="mappedtablele" key="mappedtablele" value="%{mappedtablele}"  />   
				             <s:hidden name="addressdetails_table" key="addressdetails_table" value="%{addressdetails_table}"  />                  
                              <s:hidden name="createaddressdetails_table" key="createaddressdetails_table" value="%{createaddressdetails_table}"  />
					
					 <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
  				<div id="addresserrZone" style="float:left; margin-left: 7px"></div>        
           </div>
					<div class="form_menubox">
				   <span  class="pIdsaddrss" style="display: none; ">mappedid#Contact Name#D#a,</span>
                  <div class="user_form_text">Contact Name:*</div>
                  <div class="user_form_input"><span class="needed"></span>
                  <div id="AjaxDivp">
                  <s:select 
                              cssClass="form_menu_inputselect"
                              id="mappedid"
                              name="mappedid" 
                              list="{'No Data'}" 
                              headerKey="-1"
                              onclick="getAllContactList('AjaxDivp');"
                              headerValue="-Select Name-" 
                              > 
                  </s:select>
                  </div>
                  </div>
                  </div>
                 <div id="AjaxDivpTempsss">
				 <div class="form_menubox">
				 <s:iterator value="listadressingconfiguration" status="counter">
				 <s:if test="#counter.count%2 != 0">
				 <s:if test="%{mandatory}">
                  <div class="user_form_text"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input"><span class="needed"></span><s:textfield name="%{field_value}" id="%{field_value}" cssStyle="margin:0px 0px 10px 0px" maxlength="50"  cssClass="form_menu_inputtext" placeholder="Enter Data"/></div>
				 </s:if>
				 <s:else>
				 <div class="user_form_text"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input"><s:textfield name="%{field_value}" id="%{field_value}" cssStyle="margin:0px 0px 10px 0px" maxlength="50"  cssClass="form_menu_inputtext" placeholder="Enter Data"/></div>
				 </s:else>
				 </s:if>
				 <s:else>
				 <s:if test="%{mandatory}">
                  <div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input"><span class="needed"></span><s:textfield name="%{field_value}" id="%{field_value}" cssStyle="margin:0px 0px 10px 0px" maxlength="50"  cssClass="form_menu_inputtext" placeholder="Enter Data"/></div>
				 </s:if>
				 <s:else>
				  <div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input"><s:textfield name="%{field_value}" id="%{field_value}" cssStyle="margin:0px 0px 10px 0px" maxlength="50"  cssClass="form_menu_inputtext" placeholder="Enter Data"/></div>
				 </s:else>
				 </s:else>
				 </s:iterator>
                  </div>
                  <div class="form_menubox">
				 <s:iterator value="listadressingTextArea" status="counter">
				 <s:if test="#counter.count%2 != 0">
                 <s:if test="%{mandatory}">
                  <div class="user_form_text"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input"><s:textarea name="%{field_value}" id="%{field_value}" cssStyle="margin:0px 0px 10px 0px" maxlength="50"  cssClass="form_menu_inputtext" placeholder="Enter Data"/></div>
				 </s:if>
				 <s:else>
				 <div class="user_form_text"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input"><s:textarea name="%{field_value}" id="%{field_value}" cssStyle="margin:0px 0px 10px 0px" maxlength="50"  cssClass="form_menu_inputtext" placeholder="Enter Data"/></div>
				 </s:else>
				 </s:if>
				 <s:else>
				     <s:if test="%{mandatory}">
				    <div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input"><s:textarea name="%{field_value}" id="%{field_value}" cssStyle="margin:0px 0px 10px 0px" maxlength="50"  cssClass="form_menu_inputtext" placeholder="Enter Data"/></div>
				     </s:if>
				     <s:else>
				     <div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input"><s:textarea name="%{field_value}" id="%{field_value}" cssStyle="margin:0px 0px 10px 0px" maxlength="50"  cssClass="form_menu_inputtext" placeholder="Enter Data"/></div>
				     </s:else>
				 </s:else>
				 </s:iterator>
				 
                  </div>
                  <div class="form_menubox">
				 <s:iterator value="listadressingPerDateCalendr" status="counter">
				 <s:if test="#counter.count%2 != 0">
				 <s:if test="%{mandatory}">
                  <div class="inputmain">
                  <div class="user_form_text"><s:property value="%{field_name}"/>:
                  </div>
                  <div class="user_form_input">
                  <span class="needed"></span><sj:datepicker name="%{field_value}" id="%{field_value}" changeMonth="true" changeYear="true" yearRange="1890:2020" cssStyle="margin:0px 0px 10px 0px" showOn="focus" displayFormat="dd-mm-yy" cssClass="form_menu_inputtext" placeholder="Select Date"/></div>
				 <div id="error<s:property value="%{field_value}"/>" class="errordiv">
				 </div>
				 </div>
				  </s:if>
				  <s:else>
				   <div class="inputmain"><div class="user_form_text"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input"><sj:datepicker name="%{field_value}" id="%{field_value}" changeMonth="true" changeYear="true" yearRange="1890:2020" cssStyle="margin:0px 0px 10px 0px" showOn="focus" displayFormat="dd-mm-yy" cssClass="form_menu_inputtext" placeholder="Select Date"/></div>
				 <div id="error<s:property value="%{field_value}"/>" class="errordiv"></div></div>
				  </s:else>
				  
				  </s:if><s:else>
				   <s:if test="%{mandatory}">
				  <div class="inputmain"><div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input"><span class="needed"></span><sj:datepicker name="%{field_value}" id="%{field_value}" changeMonth="true" changeYear="true" yearRange="1890:2020" cssStyle="margin:0px 0px 10px 0px" showOn="focus" displayFormat="dd-mm-yy" cssClass="form_menu_inputtext" placeholder="Select Date"/></div>
				   <div id="error<s:property value="%{field_value}"/>" class="errordiv"></div></div>
				  </s:if>
				  <s:else>
				  <div class="inputmain"><div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input"><sj:datepicker name="%{field_value}" id="%{field_value}" changeMonth="true" changeYear="true" yearRange="1890:2020" cssStyle="margin:0px 0px 10px 0px" showOn="focus" displayFormat="dd-mm-yy" cssClass="form_menu_inputtext" placeholder="Select Date"/></div>
				   <div id="error<s:property value="%{field_value}"/>" class="errordiv"></div></div>
				  </s:else>
				
				</s:else>
				 </s:iterator>
				 
                  </div>
                  </div>
               
                  
				<!-- Buttons -->
				<center><img id="indicator3" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				<div class="fields">
					<ul>
				<li class="submit" style="background: none;">
				<div class="type-button">
				  <sj:submit 
	                        value="Cancel" 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        cssClass="submit"
	                        onclick="contactcancelView();"
	                        />
                </div>
				</ul>
				<ul>
				<li class="submit" style="background: none;">
					<div class="type-button">
	                <sj:submit 
	                        targets="conatadress" 
	                        clearForm="true"
	                        value="Register  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="result1"
	                        cssClass="submit"
	                        indicator="indicator3"
	                        onBeforeTopics="validateadressing"
	                        />
	               </div>
				</ul>
				<sj:div id="contactaddrs"  effect="fold">
                    <div id="conatadress">
                    </div>
               </sj:div>
               </div>
	</s:form>
</div>
</div>
</div>
</div>
</div>
</s:if>
</div>
</div>
 <center>
         <sj:dialog 
      	id="downloaddailcontactformate" 
      	 	 	  		buttons="{ 
    		'Ok Download':function() {okdownload();},
    		'Cancel':function() {},
    		}"  
    	showEffect="slide" 
    	hideEffect="explode" 
    	autoOpen="false" 
    	modal="true" 
    	title="Select Fields for Uploding"
    	openTopics="openEffectDialog"
    	closeTopics="closeEffectDialog"
    	modal="true" 
    	width="390"
		height="300"
    />
  </center>