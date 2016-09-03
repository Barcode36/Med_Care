<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>


<script type="text/javascript">
function selectcheck1(){ 
	$("#chk2").prop('checked', false);
}
function selectcheck2(){ 
	$("#chk1").prop('checked', false);
}
var v=true;
function editField(){
	if(v){
		$("#textfield").css('display', 'block');
        $("#xxx").css('display', 'none');
		 v=false;}
	else{
		$("#textfield").css('display', 'none');
		v=true;
		$("#xxx").css('display', 'block');
		}
}
</script>
<div class="container_block"><div style=" float:left; padding:20px 0%; width:95%;">
<div class="form_inner" id="form_reg" style="width:100%; padding:0;">
<div id="savedata">
<div style="float:left; width:100%;">

		<s:form id="formoness" name="formoness" cssClass="cssn_form" action="" theme="simple"  method="post"enctype="multipart/form-data" >
         <div class="form_menubox11" style="display: none">
          <span id="id"><s:property value="%{#parameters.id}"/></span>
            <span id="mapedtable"><s:property value="%{#parameters.mapedtable}"/></span>
            </div>
         <div class="form_menubox11">
		          <div class="user_form_text11">Active</div>
		           <div class="user_form_input_chk"><s:checkbox name="titles" id="chk1"  onclick="selectcheck1();"/>
		           </div>
		     </div>
		         <s:if test="%{mandatoryFalg}">
		         <div class="form_menubox11">
		          <div class="user_form_text11">Inactive</div>
		         <div class="user_form_input_chk"><s:checkbox name="titless" id="chk2"   onclick="selectcheck2();"/>
		         </div>
				 </div>
				 </s:if>
				  <s:if test="%{mandatoryFalg}">
				  <div class="form_menubox11">
		          <div class="user_form_text11">Mandatory</div>
		         <div class="user_form_input_chk"><s:checkbox name="titles" id="mandatory" /></div>
				</div>
					 </s:if>
					 <s:else>
					   <div class="form_menubox11">
		          <div class="user_form_text11">UnMandatory</div>
		         <div class="user_form_input_chk"><s:checkbox name="titles" id="mandatorynot" /></div>
					 </s:else>
				     <div class="form_menubox11">
		          <div class="user_form_text11">New Field</div>
		         <div class="user_form_input_chk"><s:checkbox name="levelcheked" id="levelcheked" onclick="editField();"/></div>
				</div>
				<div id="textfield" style="display: none;">
				 <div class="form_menubox11">
		          <div class="user_form_text11">Field Name</div>
		         <div class="user_form_input_chk"><s:textfield name="field_name" id="field_name"/></div>
		         </div>
		          <div class="form_menubox11">
		          <div class="user_form_text11">Field Type</div>
		         <div class="user_form_input_chk">
		         <s:select list="#{'T':'Text Box','F':'File','Time':'Calender'}" name="field_type" id="field_type" cssStyle="width:95%;"></s:select>
		         </div>
				</div>
				
				 <div class="form_menubox11">
		         <div class="user_form_text11">Validation</div>
		         <div class="user_form_input_chk">
		         <s:select 
		         id="validation_type"
		         name="validation_type"  
		         list="#{'a':'Alphabate','an':'Alpha Numeric','n':'Numeric Validation','m':'Mobile Validation','e':'Email Id','w':'Website'}"
		         cssStyle="width:95%;"
		          >
		         </s:select>
		         </div>
				 </div>
			
				</div>
				<div id="xxx">
                <div class="form_menubox11" >
		          <div class="user_form_text11">Field Value</div>
		           <div class="user_form_input_chk">
		            <s:textfield name="field_value" id="field_value"  value="%{#parameters.levelname}"/></div>
		         </div>
		         </div>
			</s:form>
			</div>
			</div>
			</div>
			</div>
			</div>