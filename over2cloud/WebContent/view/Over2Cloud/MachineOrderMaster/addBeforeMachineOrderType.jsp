<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<style type="text/css">
.user_form_input {
	margin-bottom: 10px;
}
</style>
<html>
<head>
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme"
	customBasepath="%{contextz}" />
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>	
	
<script type="text/javascript">
$.subscribe('validate', function(event,data)
		{
			var mystring = $(".pIds").text();
		    var fieldtype = mystring.split(",");
		    var pattern = /^\d{10}$/;
			for(var i=0; i<fieldtype.length; i++)
			{
				
				var fieldsvalues = fieldtype[i].split("#")[0];
				var fieldsnames = fieldtype[i].split("#")[1];
				var colType = fieldtype[i].split("#")[2];   //fieldsType[i]=first_name#t
				var validationType = fieldtype[i].split("#")[3];
			      
				$("#"+fieldsvalues).css("background-color","");
				addresserrZone.innerHTML="";
				if(fieldsvalues!= "" )
				{
				    if(colType=="D"){
				    if($("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
				    {
				    addresserrZone.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
			        setTimeout(function(){ $("#addresserrZone").fadeIn(); }, 10);
				    setTimeout(function(){ $("#addresserrZone").fadeOut(); }, 2000);
			        $("#"+fieldsvalues).focus();
			        $("#"+fieldsvalues).css("background-color","#ff701a");
			        event.originalEvent.options.submit = false;
					break;	
					  }
					}
					else if(colType =="T"){
					if(validationType=="n"){
					var numeric= /^[0-9]+$/;
					if(!(numeric.test($("#"+fieldsvalues).val().trim()))){
					addresserrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Numeric Values of "+fieldsnames+"</div>";
			        setTimeout(function(){ $("#addresserrZone").fadeIn(); }, 10);
				    setTimeout(function(){ $("#addresserrZone").fadeOut(); }, 2000);
			        $("#"+fieldsvalues).focus();
			        $("#"+fieldsvalues).css("background-color","#ff701a");
			        event.originalEvent.options.submit = false;
			        break;
			          }	
				     }
					else if(validationType=="an"){
				     var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
					if(!(allphanumeric.test($("#"+fieldsvalues).val().trim()))){
					addresserrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
			        setTimeout(function(){ $("#addresserrZone").fadeIn(); }, 10);
				    setTimeout(function(){ $("#addresserrZone").fadeOut(); }, 2000);
			        $("#"+fieldsvalues).focus();
			        $("#"+fieldsvalues).css("background-color","#ff701a");
			        event.originalEvent.options.submit = false;
			        break;
			          }
					}
					else if(validationType=="a"){
					if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val().trim()))){
				    addresserrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
			        setTimeout(function(){ $("#addresserrZone").fadeIn(); }, 10);
				    setTimeout(function(){ $("#addresserrZone").fadeOut(); }, 2000);
			        $("#"+fieldsvalues).focus();
			        $("#"+fieldsvalues).css("background-color","#ff701a");
			        event.originalEvent.options.submit = false;
			        break;     
			          }
					 }
					else if(validationType=="m"){
				   if($("#"+fieldsvalues).val().trim().length > 10 || $("#"+fieldsvalues).val().trim().length < 10)
					{
						addresserrZone.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
						$("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
						$("#"+fieldsvalues).focus();
						setTimeout(function(){ $("#addresserrZone").fadeIn(); }, 10);
				        setTimeout(function(){ $("#addresserrZone").fadeOut(); }, 2000);
				        event.originalEvent.options.submit = false;
						break;
					}
				    else if (!pattern.test($("#"+fieldsvalues).val().trim())) {
					    addresserrZone.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
						$("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
						$("#"+fieldsvalues).focus();
						setTimeout(function(){ $("#addresserrZone").fadeIn(); }, 10);
				        setTimeout(function(){ $("#addresserrZone").fadeOut(); }, 2000);
				        event.originalEvent.options.submit = false;
						break;
					 }
						else if(($("#"+fieldsvalues).val().trim().charAt(0)!="9") && ($("#"+fieldsvalues).val().trim().charAt(0)!="8") && ($("#"+fieldsvalues).val().trim().charAt(0)!="7") && ($("#"+fieldsvalues).val().trim().charAt(0)!="6"))
					 {
						addresserrZone.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
						$("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
						$("#"+fieldsvalues).focus();
						setTimeout(function(){ $("#addresserrZone").fadeIn(); }, 10);
				        setTimeout(function(){ $("#addresserrZone").fadeOut(); }, 2000);
				        event.originalEvent.options.submit = false;
						break;
					   }
				     } 
					 else if(validationType =="e"){
				     if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val().trim())){
				     }else{
				    addresserrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
				    $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
					$("#"+fieldsvalues).focus();
					setTimeout(function(){ $("#addresserrZone").fadeIn(); }, 10);
				    setTimeout(function(){ $("#addresserrZone").fadeOut(); }, 2000);
				    event.originalEvent.options.submit = false;
					break;
					   } 
				     }
				     else if(validationType =="w"){
				     
				     
				     
				     }
				   }
				   
					else if(colType=="TextArea"){
					if(validationType=="an"){
				    var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
					if(!(allphanumeric.test($("#"+fieldsvalues).val().trim()))){
					addresserrZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
			        setTimeout(function(){ $("#addresserrZone").fadeIn(); }, 10);
				    setTimeout(function(){ $("#addresserrZone").fadeOut(); }, 2000);
			        $("#"+fieldsvalues).focus();
			        $("#"+fieldsvalues).css("background-color","#ff701a");
			        event.originalEvent.options.submit = false;
			        break;
			           }
					 }
					else if(validationType=="mg"){
					 
					 
					 }	
					}
					else if(colType=="Time"){
					if($("#"+fieldsvalues).val().trim()=="")
				    {
				    addresserrZone.innerHTML="<div class='user_form_inputError2'>Please Select Time "+fieldsnames+" Value </div>";
			        setTimeout(function(){ $("#addresserrZone").fadeIn(); }, 10);
				    setTimeout(function(){ $("#addresserrZone").fadeOut(); }, 2000);
			        $("#"+fieldsvalues).focus();
			        $("#"+fieldsvalues).css("background-color","#ff701a");
			        event.originalEvent.options.submit = false;
					break;	
					  }	
					}
					else if(colType=="Date"){
					if($("#"+fieldsvalues).val().trim()=="")
				    {
				    addresserrZone.innerHTML="<div class='user_form_inputError2'>Please Select Date "+fieldsnames+" Value  </div>";
			        setTimeout(function(){ $("#addresserrZone").fadeIn(); }, 10);
				    setTimeout(function(){ $("#addresserrZone").fadeOut(); }, 2000);
			        $("#"+fieldsvalues).focus();
			        $("#"+fieldsvalues).css("background-color","#ff701a");
			        event.originalEvent.options.submit = false;
					break;	
					  }
					 }  
				}
			}		
		});



</script>
<SCRIPT type="text/javascript">
$.subscribe('level', function(event,data)
		{
			 setTimeout(function(){ $("#resultDiv1").fadeIn(); }, 10);
			 setTimeout(function(){ $("#resultDiv1").fadeOut();
			// cancelButton();
			 },4000);
			 reset('formone');
			 getAllOrderNames('orderId');
		});
		
		function cancelButton()
		{
			$('#completionResult').dialog('close');
			viewmainkeyword();
		}
		
	$.subscribe('level1', function(event, data) {
		setTimeout( function() {
			$("#resultDiv2").fadeIn();
			$('#completionResult').dialog('open');
		}, 10);
		setTimeout( function() {
			$("#resultDiv2").fadeOut();
			$('#completionResult').dialog('close');
			viewmainkeyword();
		}, 4000);
		 reset('formtwo');
	});

	function viewmainkeyword()
	{
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/MachineOrder/machineOrderTypeHeader.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}

function getAllOrderNames(targetid){
	$.ajax({
		type :"post",
		url :"view/Over2Cloud/MachineOrder/getAllOrderNames.action",
		success : function(empData){
		$('#'+targetid+' option').remove();
		$('#'+targetid).append($("<option></option>").attr("value",-1).text("--select Name--"));
    	$(empData).each(function(index)
		{
		   $('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.ordername));
		});
	    },
	    error : function () {
			alert("Somthing is wrong to get Data");
		}
	});
}
getAllOrderNames('orderId');

	function reset(formId) {
		  $("#"+formId).trigger("reset"); 
		}
	
</script>

</head>
<sj:dialog
          id="completionResult"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title="Confirmation Message"
          cssStyle="overflow:hidden;text-align:center;margin-top:10px;"
          modal="true"
          width="400"
          height="150"
          draggable="true"
    	  resizable="true"
    	   buttons="{ 
    		'Close':function() { cancelButton(); } 
    		}" 
          >
          <div id="result"></div>
</sj:dialog>
<body>
	<div class="list-icon">
<div class="head">Machine Order Type</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div>
</div>
	
	<div class="container_block">
		<div style="float: left; padding: 20px 1%; width: 98%;">
			<div class="border">
			<sj:accordion id="accordion" autoHeight="false" >
<div style="float: left; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
								<div id="addresserrZone" style="float: left; margin-left: 7px"></div>
							</div>
							<br/>
							
			<sj:accordionItem title="Add Order Item" id="acor_item1" >
					<div class="form_inner" id="form_reg" style="margin-top: 10px;">
						<s:form id="formone" name="formone" namespace="/view/Over2Cloud/MachineOrder" action="addMachineOrder" theme="simple" method="post" enctype="multipart/form-data">
							<s:iterator value="configKeyTextBox4Machine" status="counter">
								<s:if test="%{mandatory}">
	                      			<span id="mandatoryFields" class="pIds" style="display: none;"><s:property value="%{key}" />#<s:property value="%{value}" />#<s:property value="%{colType}" />#<s:property value="%{validationType}" />,</span>
	                 			</s:if>
	                 			
	                 			<s:if test="#counter.odd">
	                 				<div class="newColumn">
	                 					<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	                 					<div class="rightColumn1">
	                 						<s:if test="%{mandatory}"><span class="needed"></span>
		                 					</s:if>
	   										<s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" />
                      						
										</div>
	                 				</div>
	                 			</s:if>
	                 			<s:else>
	                 				<div class="newColumn">
		                 				<div class="leftColumn1"><s:property value="%{value}" />:</div>
										<div class="rightColumn1">
										<s:if test="%{mandatory}">
	                 						<span class="needed"></span>
	                 					</s:if>
											<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" />
										</div>
									</div>
	                 			</s:else>
	                 			
							</s:iterator>
							
							<div class="clear"></div>
   							<div class="fields">
							<div style="width: 100%; text-align: center; padding-bottom: 10px;">
								<sj:submit 
								     targets="result1" 
								     clearForm="true"
								     value="  Save  " 
								     effect="highlight"
								     effectOptions="{ color : '#222222'}"
								     effectDuration="100"
								     button="true"
								    
								    onCompleteTopics="level"
								     cssClass="submit"
							     />
							   	<sj:a 
							 button="true" href="#"
							 onclick="reset('formone');"
							 cssClass="submit"
						>
						
						Reset
					</sj:a>
					<sj:a 
							button="true" href="#"
							onclick="viewmainkeyword();"
							cssClass="submit"
						>
						
						Back
					</sj:a>
							</div>
						</div>
						<div class="clear"></div>
						<sj:div id="resultDiv1"  effect="fold">
		                    <div id="result1"></div>
		                </sj:div>
						</s:form>
					</div>
					
					</sj:accordionItem>
					
					<sj:accordionItem title="Map Order Item" id="acor_item2" >
					<div class="form_inner" id="form_reg" style="margin-top: 10px;">
						<s:form id="formtwo" name="formtwo" namespace="/view/Over2Cloud/MachineOrder" action="addMachineOrderType" theme="simple" method="post" enctype="multipart/form-data">
							<div style="float: left; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
								<div id="errZone" style="float: left; margin-left: 7px"></div>
							</div>
							<br/>
							
							<s:iterator value="configKeyTextBox" status="counter">
								<s:if test="%{mandatory}">
	                      			<span id="mandatoryFields" class="pIds" style="display: none;"><s:property value="%{key}" />#<s:property value="%{value}" />#<s:property value="%{colType}" />#<s:property value="%{validationType}" />,</span>
	                 			</s:if>
	                 			
	                 			<s:if test="#counter.odd">
	                 				<div class="newColumn">
	                 					<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	                 					<div class="rightColumn1">
	                 						<s:if test="%{mandatory}"><span class="needed"></span>
		                 					</s:if><s:if test="%{key == 'orderId'}">
											<s:select name="%{key}" id="%{key}"  list="{'No data'}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" />
										</s:if>
										<s:else>
											<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" />
										</s:else>
										</div>
	                 				</div>
	                 			</s:if>
	                 			<s:else>
	                 				<div class="newColumn">
		                 				<div class="leftColumn1"><s:property value="%{value}" />:</div>
										<div class="rightColumn1">
										<s:if test="%{mandatory}">
	                 						<span class="needed"></span>
	                 					</s:if>
										<s:if test="%{key == 'orderId'}">
											<s:select name="%{key}" id="%{key}"  list="{'No data'}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" />
										</s:if>
										<s:else>
											<s:textfield name="%{key}" id="%{key}" cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;" />
											<sj:a value="+" onclick="adddiv('100')" button="true" cssClass="submit" cssStyle="  margin-left: 258px; margin-top: -39px">+</sj:a>
										</s:else>
										</div>
									</div>
	                 			</s:else>
							</s:iterator>
							
							
							<div id="extraDiv">
                 <s:iterator begin="100" end="120" var="typeIndx">
                        <div class="clear"></div>
                         <div id="<s:property value="%{#typeIndx}"/>" style="display: none">
                             <s:iterator value="configKeyTextBox" status="counter" begin="1">
                            <div class="newColumn">
                                <div class="leftColumn1"><s:property value="%{value}"/><font color="red"></font>:</div>
                                <span class="needed" style="margin-left: -5px; margin-top: 8px; height: 23px;"></span>
                                <div class="rightColumn1">
                                    <s:textfield name="order_type"  id="order_type%{#typeIndx}"  cssClass="textField" cssStyle="margin:0px 0px 10px 0px;" placeholder="Enter Data" />
                                    </div>
                                    
                            <div style="margin-top: -37px;">
                            <s:if test="#typeIndx==113">
                                <div class="user_form_button2"><sj:a value="-" onclick="deletediv('%{#typeIndx}')" button="true"> - </sj:a></div>
                            </s:if>
                               <s:else>
                                   <div class="user_form_button2"><sj:a value="+"  onclick="adddiv('%{#typeIndx+1}')" button="true"> + </sj:a></div>
                                  <div class="user_form_button3" style="padding-left: 10px;"><sj:a value="-" onclick="deletediv('%{#typeIndx}')" button="true"> - </sj:a></div>
                               </s:else>
                               </div>
                            </div>
                            </s:iterator>
                        </div>
                    </s:iterator>
                    </div>

							<div class="clear"></div>
   							<div class="fields">
							<div style="width: 100%; text-align: center; padding-bottom: 10px;">
								<sj:submit 
								     targets="result" 
								     clearForm="true"
								     value="  Save  " 
								     effect="highlight"
								     effectOptions="{ color : '#222222'}"
								     effectDuration="100"
								     button="true"
								     cssClass="submit"
									onBeforeTopics="validate"	
									onCompleteTopics="level1"
							     />
							   	<sj:a 
							 button="true" href="#"
							 onclick="reset('formone');"
							 cssClass="submit"
						>
						
						Reset
					</sj:a>
					<sj:a 
							button="true" href="#"
							onclick="viewmainkeyword();"
							cssClass="submit"
						>
						
						Back
					</sj:a>
							</div>
						</div>
						<div class="clear"></div>
						<sj:div id="resultDiv2"  effect="fold">
		                    <div id="result2"></div>
		                </sj:div>
						</s:form>
					</div>
					
					</sj:accordionItem>
					</sj:accordion>
					
					
			</div>
		</div>
	</div>
</body>
</html>
