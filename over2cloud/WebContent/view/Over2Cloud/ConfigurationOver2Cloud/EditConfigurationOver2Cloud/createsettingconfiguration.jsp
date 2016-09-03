<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
 <script type="text/javascript">
function OnlickEdite(valuepass,mandatoryFalg){
	var tablemapped=$("#maped").text();
	var columnid=valuepass.id;
	var levelname=valuepass.name;
	var mandatoryFalgTemp=mandatoryFalg;
    var conP = "<%=request.getContextPath()%>";
        $("#mybuttondialog").load(conP+"/view/CloudApps/operationwithfieldAction.action?mandatoryFalg="+mandatoryFalgTemp+"&mapedtable="+tablemapped.split(" ").join("%20")+"&id="+columnid.split(" ").join("%20")+"&levelname="+levelname.split(" ").join("%20"));
	   $("#mybuttondialog").dialog('open');
	}   
</script>
<script type="text/javascript">
function OnlickEditee(valuepass,mandatoryFalg){
	var tablemapped=$("#maped").text();
	var columnid=valuepass.id;
	var levelname=valuepass.name;
    var conP = "<%=request.getContextPath()%>";
        $("#mybuttondialog").load(conP+"/view/CloudApps/operationwithfieldAction.action?mandatoryFalg="+mandatoryFalg+"&mapedtable="+tablemapped.split(" ").join("%20")+"&id="+columnid.split(" ").join("%20")+"&levelname="+levelname.split(" ").join("%20"));
	   $("#mybuttondialog").dialog('open');
	}   
</script>
<SCRIPT type="text/javascript">
 function SaveEditefielddetails(){
	 var active="",inactive="",mandatory="",field_value="",field_name="",field_type="",mandatorynot="",validation_type="";
	    try{
	    active = document.getElementById("chk1").checked;}
	    catch(err){}
		try{
			inactive = document.getElementById("chk2").checked;}
		catch(err){}
		try{
			mandatory = document.getElementById("mandatory").checked;}
		catch(err){}
		try{
			mandatorynot = document.getElementById("mandatorynot").checked;
			}catch(err){}
		try{
			field_value = document.getElementById("field_value").value;}
		catch(err){}
		try{
			field_name = document.getElementById("field_name").value;
			field_name=field_name.replace(/[`~!@#$%^&*()_|+\-=?;:'",.<>\{\}\[\]\\\/]/gi, '');
        }catch(err){}
		try{
			field_type = document.getElementById("field_type").value;}
		catch(err){}
		try{
		validation_type = document.getElementById("validation_type").value;
		}catch(err){}
		
		var id = $("#id").text();
		var mapedtable = $("#mapedtable").text();
	    var conP = "<%=request.getContextPath()%>";

		$.ajax({
		    type : "post",
		    url : conP+"/view/CloudApps/editesettingfieldOperation.action?active="+active+"&inactive="+inactive+"&mandatory="+mandatory+"&field_value="+field_name+"&id="+id+"&level_name="+field_value.split(" ").join("%20")+"&mapedtable="+mapedtable+"&field_type="+field_type
		    +"&mandatorynot="+mandatorynot+"&validation_type="+validation_type,
		    success : function(saveData) {
			 $("#savedata").html(saveData);
			 $("#savedata").fadeIn(3000);
			 $("#savedata").fadeOut(3000);
		},
		   error: function() {
	            alert("error");
	        }
		 }
	);
} 
 function cancelButton()
 {
	 $("#mybuttondialog").dialog('close');
 }
</SCRIPT>


</head>
 <sj:dialog 
    	id="mybuttondialog" 
    	buttons="{ 
    		'Save':function() { SaveEditefielddetails(); },
    		'Cancel':function() { cancelButton(); } 
    		}" 
    	autoOpen="false" 
    	modal="true" 
    	width="280"
		height="310"
    	title=" Edit Box"
    	resizable="false"
    >
</sj:dialog>
<div class="page_title">
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<sj:accordion id="accordion" autoHeight="false">
<sj:accordionItem title="%{header_name}>>" id="oneId">  
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formone" name="formone" namespace="/view/Over2Cloud/setting" action="createEmpBasic" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
			<div class="form_inner" id="form_reg">
	
		        <div style="display: none" >
					 <span id="maped"><s:property value="%{mappedtablele}"/>	</span>
					</div>
		      	<%
				int temp=0;
				 %>
				  <s:iterator value="listsettingPerdropdownconfiguration">
				 <%if(temp%2==0){ %>
				 <div class="form_menubox">
                  <div class="user_form_text"><s:property value="%{field_name}"/>:</div>
                   <div class="user_form_input">
                   <s:if test="%{actives}">
                  <s:checkbox   name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select" onclick="OnlickEdite(this,'%{mandatory}');"/>
				  </s:if><s:else>
                  <s:div cssStyle="width:15%; padding-left: 21%; cursor:pointer;  " title="%{field_name}" name="%{field_name}"  id="%{id}"  onclick="OnlickEditee(this,'%{mandatory}');"><s:checkbox  name="%{field_name}" id="%{id}"   value="true"  disabled="true"/></s:div>
				  </s:else>
				  </div>
				  <%} else {%>
				  <div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
				  <div class="user_form_input">
				  <s:if test="%{actives}">
                 <s:checkbox  name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select" onclick="OnlickEdite(this,'%{mandatory}');"/>
				 </s:if> <s:else>
                  <s:div  cssStyle="width:15%; padding-left: 21%; cursor:pointer;  " title="%{field_name}" name="%{field_name}" id="%{id}" onclick="OnlickEditee(this,'%{mandatory}');"><s:checkbox  name="%{field_name}" id="%{id}"   value="true"  disabled="true"/></s:div>
				 </s:else>
		          </div>
				 </div>
				  <%} 
				  temp++;
				  %>
				 </s:iterator>
				  <s:iterator value="listsettingtextconfiguration">
				 <%if(temp%2==0){ %>
				 <div class="form_menubox">
                  <div class="user_form_text"><s:property value="%{field_name}"/>:</div>
                   <div class="user_form_input">
                   <s:if test="%{actives}">
                  <s:checkbox   name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select" onclick="OnlickEdite(this,'%{mandatory}');"/>
				  </s:if><s:else>
                  <s:div cssStyle="width:15%; padding-left: 21%; cursor:pointer;  " title="%{field_name}" name="%{field_name}"  id="%{id}"  onclick="OnlickEditee(this,'%{mandatory}');"><s:checkbox  name="%{field_name}" id="%{id}"   value="true"  disabled="true"/></s:div>
				  </s:else>
				  </div>
				  <%} else {%>
				  <div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
				  <div class="user_form_input">
				  <s:if test="%{actives}">
                 <s:checkbox  name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select" onclick="OnlickEdite(this,'%{mandatory}');"/>
				 </s:if> <s:else>
                  <s:div  cssStyle="width:15%; padding-left: 21%; cursor:pointer;  " title="%{field_name}" name="%{field_name}" id="%{id}" onclick="OnlickEditee(this,'%{mandatory}');"><s:checkbox  name="%{field_name}" id="%{id}"   value="true"  disabled="true"/></s:div>
				 </s:else>
		          </div>
				 </div>
				  <%} 
				  temp++;
				  %>
				 </s:iterator>
				 <s:iterator value="listsettingCalendrTimeconfiguration">
				 <%if(temp%2==0){ %>
				 <div class="form_menubox">
                  <div class="user_form_text"><s:property value="%{field_name}"/>:</div>
                   <div class="user_form_input">
                   <s:if test="%{actives}">
                  <s:checkbox   name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select" onclick="OnlickEdite(this,'%{mandatory}');"/>
				  </s:if><s:else>
                  <s:div cssStyle="width:15%; padding-left: 21%; cursor:pointer;  " title="%{field_name}" name="%{field_name}"  id="%{id}"  onclick="OnlickEditee(this,'%{mandatory}');"><s:checkbox  name="%{field_name}" id="%{id}"   value="true"  disabled="true"/></s:div>
				  </s:else>
				  </div>
				  <%} else {%>
				  <div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
				  <div class="user_form_input">
				  <s:if test="%{actives}">
                 <s:checkbox  name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select" onclick="OnlickEdite(this,'%{mandatory}');"/>
				 </s:if> <s:else>
                  <s:div  cssStyle="width:15%; padding-left: 21%; cursor:pointer;  " title="%{field_name}" name="%{field_name}" id="%{id}" onclick="OnlickEditee(this,'%{mandatory}');"><s:checkbox  name="%{field_name}" id="%{id}"   value="true"  disabled="true"/></s:div>
				 </s:else>
		          </div>
				 </div>
				  <%} 
				  temp++;
				  %>
				 </s:iterator>
		       <s:iterator value="listsettingPerCalendrconfiguration">
				 <%if(temp%2==0){ %>
				 <div class="form_menubox">
                  <div class="user_form_text"><s:property value="%{field_name}"/>:</div>
                   <div class="user_form_input">
                   <s:if test="%{actives}">
                  <s:checkbox   name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select" onclick="OnlickEdite(this,'%{mandatory}');"/>
				  </s:if><s:else>
                  <s:div cssStyle="width:15%; padding-left: 21%; cursor:pointer;  " title="%{field_name}" name="%{field_name}"  id="%{id}"  onclick="OnlickEditee(this,'%{mandatory}');"><s:checkbox  name="%{field_name}" id="%{id}"   value="true"  disabled="true"/></s:div>
				  </s:else>
				  </div>
				  <%} else {%>
				  <div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
				  <div class="user_form_input">
				  <s:if test="%{actives}">
                 <s:checkbox  name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select" onclick="OnlickEdite(this,'%{mandatory}');"/>
				 </s:if> <s:else>
                  <s:div  cssStyle="width:15%; padding-left: 21%; cursor:pointer;  " title="%{field_name}" name="%{field_name}" id="%{id}" onclick="OnlickEditee(this,'%{mandatory}');"><s:checkbox  name="%{field_name}" id="%{id}"   value="true"  disabled="true"/></s:div>
				 </s:else>
		          </div>
				 </div>
				  <%} 
				  temp++;
				  %>
				 </s:iterator>
				 <s:iterator value="listsettingtextAreaconfiguration">
				 <%if(temp%2==0){ %>
				 <div class="form_menubox">
                  <div class="user_form_text"><s:property value="%{field_name}"/>:</div>
                   <div class="user_form_input">
                   <s:if test="%{actives}">
                  <s:checkbox   name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select" onclick="OnlickEdite(this,'%{mandatory}');"/>
				  </s:if><s:else>
                  <s:div cssStyle="width:15%; padding-left: 21%; cursor:pointer;  " title="%{field_name}" name="%{field_name}"  id="%{id}"  onclick="OnlickEditee(this,'%{mandatory}');"><s:checkbox  name="%{field_name}" id="%{id}"   value="true"  disabled="true"/></s:div>
				  </s:else>
				  </div>
				  <%} else {%>
				  <div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
				  <div class="user_form_input">
				  <s:if test="%{actives}">
                 <s:checkbox  name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select" onclick="OnlickEdite(this,'%{mandatory}');"/>
				 </s:if> <s:else>
                  <s:div  cssStyle="width:15%; padding-left: 21%; cursor:pointer;  " title="%{field_name}" name="%{field_name}" id="%{id}" onclick="OnlickEditee(this,'%{mandatory}');"><s:checkbox  name="%{field_name}" id="%{id}"   value="true"  disabled="true"/></s:div>
				 </s:else>
		          </div>
				 </div>
				  <%} 
				  temp++;
				  %>
				 </s:iterator>
               </div>
			</s:form>
</div>
</div>
</sj:accordionItem>
</sj:accordion>
</div>
</div>
</html>