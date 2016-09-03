<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
 <script type="text/javascript">
function OnlickEdite(valuepass,mandatoryFalg){
	var tablemapped=valuepass.value;
	var columnid=valuepass.id;
	var levelname=valuepass.name;
	var mandatoryFalgTemp=mandatoryFalg;
    var conP = "<%=request.getContextPath()%>";
        $("#mybuttondialog").load(conP+"/view/CloudApps/operationwithfieldAction.action?mandatoryFalg="+mandatoryFalgTemp+"&mapedtable="+tablemapped.split(" ").join("%20")+"&id="+columnid.split(" ").join("%20")+"&levelname="+levelname.split(" ").join("%20"));
	   $("#mybuttondialog").dialog('open');
	}   
</script>
<SCRIPT type="text/javascript">
function OnlickEditee(valuepass,tablmapped,mandatoryFalg){
	var levelname=valuepass.title;
	var columnid=valuepass.id;
    var tablemapped =tablmapped;
    var mandatoryFalgTemp=mandatoryFalg;
	var conP = "<%=request.getContextPath()%>";
    $("#mybuttondialog").load(conP+"/view/CloudApps/operationwithfieldAction.action?mandatoryFalg="+mandatoryFalgTemp+"&mapedtable="+tablemapped.split(" ").join("%20")+"&id="+columnid.split(" ").join("%20")+"&levelname="+levelname.split(" ").join("%20"));
   $("#mybuttondialog").dialog('open');
}
 function SaveEditefielddetails(){
	 var active="",inactive="",mandatory="",field_value="",field_name="",field_type="",mandatorynot="";
	 try{
	    active = document.getElementById("chk1").checked;}
	    catch(err){}
		try{
		inactive = document.getElementById("chk2").checked;
		}catch(err){}
		try{
		mandatory = document.getElementById("mandatory").checked;
		}catch(err){}
		try{
			mandatorynot = document.getElementById("mandatorynot").checked;
			}catch(err){}
		try{
		field_value = document.getElementById("field_value").value;
		}catch(err){}
		try{
			field_name = document.getElementById("field_name").value;
			field_name=field_name.replace(/[`~!@#$%^&*()_|+\-=?;:'",.<>\{\}\[\]\\\/]/gi, '');
		}catch(err){}
		try{
		field_type = document.getElementById("field_type").value;
		}catch(err){}
		try{
		validation_type = document.getElementById("validation_type").value;
		}catch(err){}
		var id = $("#id").text();
		var mapedtable = $("#mapedtable").text();
		var comonmaped= $("#maped").text();
	    var conP = "<%=request.getContextPath()%>";

		$.ajax({
		    type : "post",
		    url : conP+"/view/CloudApps/editefieldoperationwithAll.action?active="+active+"&inactive="+inactive+"&mandatory="+mandatory+"&field_value="+field_name+"&id="+id+"&level_name="+field_value.split(" ").join("%20")+"&mapedtable="+mapedtable+"&field_type="+field_type
		    +"&comonmaped="+comonmaped+"&mandatorynot="+mandatorynot+"&validation_type="+validation_type,
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
	 <div style="display: none">
					 <span id="maped"><s:property value="%{commonMappedtablele}"/>	</span>
					</div>
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<sj:accordion id="accordion" autoHeight="false">
<s:if test= "level1Name!=null">
<sj:accordionItem title="%{filed_name}>> %{level1Name}" id="oneId">  
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formone" name="formone" namespace="/view/Over2Cloud/hr" action="createEmpBasic" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
			<div class="form_inner" id="form_reg">
	
		        <div style="display: none">
					 <span id="maped"><s:property value="%{mappedtablele}"/>	</span>
					</div>
		      	<%
				int temp=0;
				 %>
				 <s:iterator value="listconfiguration1">
				 <%if(temp%2==0){ %>
				 <div class="form_menubox">
				   <s:if test="%{mandatory}">
                  <div class="user_form_text"><s:property value="%{field_name}"/> *</div>
                  </s:if><s:else>
                   <div class="user_form_text"><s:property value="%{field_name}"/>:</div>
                  </s:else>
                   <div class="user_form_input">
                   <s:if test="%{actives}">
                  <s:checkbox fieldValue="%{mappedtablelevel1}" value="true" name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select" onclick="OnlickEdite(this,'%{mandatory}');"/>
				  </s:if><s:else>
                  <s:div cssStyle="width:15%; padding-left: 21%; cursor:pointer;  " title="%{field_name}" name="%{field_name}"  id="%{id}"  onclick="OnlickEditee(this,'%{mappedtablelevel1}','%{mandatory}');"><s:checkbox  name="%{field_name}" id="%{id}"   value="true"  disabled="true"/></s:div>
				  </s:else>
				  </div>
				  <%} else {%>
				     <s:if test="%{mandatory}">
				  <div class="user_form_text1"><s:property value="%{field_name}"/> * </div>
				  </s:if>
				  <s:else>
				  <div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
				  </s:else>
				  <div class="user_form_input">
				  <s:if test="%{actives}">
                 <s:checkbox fieldValue="%{mappedtablelevel1}" value="true" name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select" onclick="OnlickEdite(this,'%{mandatory}');"/>
				 </s:if> <s:else>
                  <s:div  cssStyle="width:15%; padding-left: 21%; cursor:pointer;  " title="%{field_name}" name="%{field_name}" id="%{id}" onclick="OnlickEditee(this,'%{mappedtablelevel1}','%{mandatory}');"><s:checkbox  name="%{field_name}" id="%{id}"   value="true"  disabled="true"/></s:div>
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
<center><b><font color="black" size="2">*Note: All special char will be removed from the field name.</font></b></center>
</sj:accordionItem>
</s:if>
<s:if test= "level2Name!=null">
<sj:accordionItem title="%{filed_name} >> %{level2Name}" id="two">  
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formTwo" name="formTwo" cssClass="cssn_form"  theme="simple"  method="post"enctype="multipart/form-data" >
                  <%
				 int temp=0;
				 %>
				 <s:iterator value="listconfiguration2">
				 <%if(temp%2==0){ %>
				 <div class="form_menubox">
                   <s:if test="%{mandatory}">
                  <div class="user_form_text"><s:property value="%{field_name}"/> *</div>
                  </s:if><s:else>
                   <div class="user_form_text"><s:property value="%{field_name}"/>:</div>
                  </s:else>
                   <div class="user_form_input">
                   <s:if test="%{actives}">
                  <s:checkbox fieldValue="%{mappedtablelevel2}"  name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select" onclick="OnlickEdite(this,'%{mandatory}');"/>
				  </s:if><s:else>
                  <s:div cssStyle="width:15%; padding-left: 21%; cursor:pointer;  "name="%{mappedtablelevel2}"  id="%{id}"  onclick="OnlickEditee(this,'%{mappedtablelevel2}','%{mandatory}');"><s:checkbox  name="%{field_name}" id="%{id}"   value="true"  disabled="true"/></s:div>
				  </s:else>
				  </div>
				  <%} else {%>
				  <s:if test="%{mandatory}">
                  <div class="user_form_text1"><s:property value="%{field_name}"/> *</div>
                  </s:if><s:else>
                   <div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
                  </s:else>
				  <div class="user_form_input">
				  <s:if test="%{actives}">
                 <s:checkbox fieldValue="%{mappedtablelevel2}" name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select" onclick="OnlickEdite(this,'%{mandatory}');"/>
				 </s:if> <s:else>
                  <s:div  cssStyle="width:15%; padding-left: 21%; cursor:pointer;  " title="%{field_name}" id="%{id}" onclick="OnlickEditee(this,'%{mappedtablelevel2}','%{mandatory}');"><s:checkbox  name="%{field_name}" id="%{id}"   value="true"  disabled="true"/></s:div>
				 </s:else>
		          </div>
				 </div>
				  <%} 
				  temp++;
				  %>
				 </s:iterator>
			</s:form>
</div>
</div>
<center><b><font color="black" size="2">*Note: All special char will be removed from the field name.</font></b></center>
</sj:accordionItem>
</s:if>
<s:if test= "level3Name!=null">
<sj:accordionItem title="%{filed_name} >> %{level3Name}" id="three">  
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formThree" name="formThree" cssClass="cssn_form"  theme="simple"  method="post"enctype="multipart/form-data" >
                 <%
				 int temp=0;
				 %>
				 <s:iterator value="listconfiguration3">
				 <%if(temp%2==0){ %>
				 <div class="form_menubox">
                    <s:if test="%{mandatory}">
                  <div class="user_form_text"><s:property value="%{field_name}"/> *</div>
                  </s:if><s:else>
                   <div class="user_form_text"><s:property value="%{field_name}"/>:</div>
                  </s:else>
                  <div class="user_form_input">
                    <s:if test="%{actives}">
                  <s:checkbox fieldValue="%{mappedtablelevel3}" name="%{field_name}"  id="%{id}"  cssClass="form_menu_inputtext sub_select" onclick="OnlickEdite(this,'%{mandatory}');"/>
                  </s:if>
                  <s:else>
                   <s:div  cssStyle="width:15%; padding-left: 21%; cursor:pointer;  " title="%{field_name}" id="%{id}" onclick="OnlickEditee(this,'%{mappedtablelevel3}','%{mandatory}');"><s:checkbox  name="%{field_name}" id="%{id}"   value="true"  disabled="true"/></s:div>
                  </s:else>
                  </div>
				  <%} else {%>
				    <s:if test="%{mandatory}">
                  <div class="user_form_text1"><s:property value="%{field_name}"/> *</div>
                  </s:if><s:else>
                   <div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
                  </s:else>
                  <div class="user_form_input">
                    <s:if test="%{actives}">
                  <s:checkbox fieldValue="%{mappedtablelevel3}" name="%{field_name}" id="%{id}"  cssClass="form_menu_inputtext sub_select" onclick="OnlickEdite(this,'%{mandatory}');"/>
                  </s:if>
                  <s:else>
                   <s:div  cssStyle="width:15%; padding-left: 21%; cursor:pointer;  " title="%{field_name}" id="%{id}" onclick="OnlickEditee(this,'%{mappedtablelevel3}','%{mandatory}');"><s:checkbox  name="%{field_name}" id="%{id}"   value="true"  disabled="true"/></s:div>
                  </s:else>
                  
                  </div>
				
				 </div>
				  <%} 
				  temp++;
				  %>
				 </s:iterator>
                  
              
			</s:form>
</div>
</div>
<center><b><font color="black" size="2">*Note: All special char will be removed from the field name.</font></b></center>
</sj:accordionItem>
</s:if>

<!-- Level4 -->
<s:if test= "level4Name!=null">
<sj:accordionItem title="%{filed_name} >> %{level4Name}" id="four">  
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formFour" name="formFour" cssClass="cssn_form"  theme="simple"  method="post"enctype="multipart/form-data" >
                  	  
 				<%
				 int temp=0;
				 %>
				 <s:iterator value="listconfiguration4">
				 <%if(temp%2==0){ %>
				 <div class="form_menubox">
                    <s:if test="%{mandatory}">
                  <div class="user_form_text"><s:property value="%{field_name}"/> *</div>
                  </s:if><s:else>
                   <div class="user_form_text"><s:property value="%{field_name}"/>:</div>
                  </s:else>
                  <div class="user_form_input">
                  <s:if test="%{actives}">
                  <s:checkbox fieldValue="%{mappedtablelevel4}" name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select" onclick="OnlickEdite(this,'%{mandatory}');"/>
                  	  </s:if>
                  	  <s:else>
                  	  <s:div  cssStyle="width:15%; padding-left: 21%; cursor:pointer;  " title="%{field_name}" id="%{id}" onclick="OnlickEditee(this,'%{mappedtablelevel4}','%{mandatory}');"><s:checkbox  name="%{field_name}" id="%{id}"   value="true"  disabled="true"/></s:div>
                  	  </s:else>
                  </div>
			
				  <%} else {%>
				   <s:if test="%{mandatory}">
                  <div class="user_form_text1"><s:property value="%{field_name}"/> *</div>
                  </s:if><s:else>
                   <div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
                  </s:else>
				  
                  <div class="user_form_input">
                  <s:if test="%{actives}">
                  <s:checkbox fieldValue="%{mappedtablelevel4}" name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select" onclick="OnlickEdite(this,'%{mandatory}');"/>
				 </s:if>
				 <s:else>
				 <s:div  cssStyle="width:15%; padding-left: 21%; cursor:pointer;  " title="%{field_name}" id="%{id}" onclick="OnlickEditee(this,'%{mappedtablelevel4}','%{mandatory}');"><s:checkbox  name="%{field_name}" id="%{id}"   value="true"  disabled="true"/></s:div>
				 </s:else>
				 </div>
				 </div>
				  <%} 
				  temp++;
				  %>
				 </s:iterator>
			</s:form>
</div>
</div>
<center><b><font color="black" size="2">*Note: All special char will be removed from the field name.</font></b></center>
</sj:accordionItem>

</s:if>
<!-- Level5 -->
<s:if test= "level5Name!=null">
<sj:accordionItem title="%{filed_name} >> %{level5Name}" id="five">  
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formFive" name="formFive" cssClass="cssn_form"  theme="simple"  method="post"enctype="multipart/form-data" >
                <%
				 int temp=0;
				 %>
				 <s:iterator value="listconfiguration5">
				 <%if(temp%2==0){ %>
				 <div class="form_menubox">
                  <div class="user_form_text"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input">
                  <s:if test="%{actives}">
                  <s:checkbox fieldValue="%{mappedtablelevel5}" name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select" onclick="OnlickEdite(this,'%{mandatory}');"/>
                  </s:if>
                  <s:else>
                  <s:div  cssStyle="width:15%; padding-left: 21%; cursor:pointer;  " title="%{field_name}" id="%{id}" onclick="OnlickEditee(this,'%{mappedtablelevel5}','%{mandatory}');"><s:checkbox  name="%{field_name}" id="%{id}"   value="true"  disabled="true"/></s:div>
                  </s:else>
                  </div>
				  <%} else {%>
				  <div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input">
                  <s:if test="%{actives}">
                  <s:checkbox fieldValue="%{mappedtablelevel5}" name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select" onclick="OnlickEdite(this,'%{mandatory}');"/>
                  </s:if>
                  <s:else>
                  <s:div  cssStyle="width:15%; padding-left: 21%; cursor:pointer;  " title="%{field_name}" id="%{id}" onclick="OnlickEditee(this,'%{mappedtablelevel5}','%{mandatory}');"><s:checkbox  name="%{field_name}" id="%{id}"   value="true"  disabled="true"/></s:div>
                  </s:else>
                  </div>
				 </div>
				  <%} 
				  temp++;
				  %>
				 </s:iterator>
			</s:form>
</div>
</div>
<center><b><font color="black" size="2">*Note: All special char will be removed from the field name.</font></b></center>
</sj:accordionItem>
</s:if>
</sj:accordion>
</div>
</div>
</html>