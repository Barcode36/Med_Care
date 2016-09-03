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
	 var active="",inactive="",mandatory="",field_value="",field_name="",field_type="";
	 try
	 {
	    active = document.getElementById("chk1").checked;
	 }catch(err)
		{}
		try
		{
			inactive = document.getElementById("chk2").checked;
		}catch(err)
		{}
		try
		{
			mandatory = document.getElementById("mandatory").checked;
		}catch(err)
		{}
		try
		{
			field_value = document.getElementById("field_value").value;
		}catch(err)
		{}
		try
		{
			field_name = document.getElementById("field_name").value;
			field_name=field_name.replace(/[`~!@#$%^&*()_|+\-=?;:'",.<>\{\}\[\]\\\/]/gi, '');
		}catch(err)
		{}
		try
		{
			field_type = document.getElementById("field_type").value;
		}catch(err)
		{}
		var id = $("#id").text();
		var mapedtable = $("#mapedtable").text();
	    var conP = "<%=request.getContextPath()%>";

		$.ajax({
		    type : "post",
		    url : conP+"/view/CloudApps/editefieldoperationwithAll.action?active="+active+"&inactive="+inactive+"&mandatory="+mandatory+"&field_value="+field_name+"&id="+id+"&level_name="+field_value.split(" ").join("%20")+"&mapedtable="+mapedtable+"&field_type="+field_type,
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
<script src="<s:url value="/js/organization.js"/>"></script>

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
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<sj:accordion id="accordion" autoHeight="false">
<sj:accordionItem title="%{filed_name}>> %{level1Name}" id="oneId">  
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formone" name="formone" theme="simple"  method="post"enctype="multipart/form-data" >
		             <div style="display: none">
					 <span id="maped"><s:property value="%{mappedtablelevel1}"/>	</span>
					</div>
		
				<%
				 int temp=0;
				 %>
				 <s:iterator value="listconfiguration">
				 <%if(temp%2==0){ %>
				 <div class="form_menubox">
                  <div class="user_form_text"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input">
                  <s:if test="%{actives}">
                   <s:checkbox  fieldValue="%{mappedtablelevel1}" name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select" onclick="OnlickEdite(this,'%{mandatory}');"/>
                  </s:if><s:else>
                
                  <s:div id="one" cssStyle="width:15%; padding-left: 21%; cursor:pointer;  " title="%{field_name}"  name="%{field_name}"  id="%{id}"    onclick="OnlickEditee(this,'%{mappedtablelevel1}','%{mandatory}');"><s:checkbox title="%{mappedtablelevel1}" name="%{field_name}" id="%{id}"   value="true"  disabled="true" /></s:div>
                  </s:else> 

                  </div>
                 
				  <%} else {%>
				 <div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
				  <div class="user_form_input">
				  <s:if test="%{actives}">
                 <s:checkbox  fieldValue="%{mappedtablelevel1}" name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select" onclick="OnlickEdite(this,'%{mandatory}');"/>
				 </s:if><s:else>
				
                  <s:div id="one" cssStyle="width:15%; padding-left: 21%; cursor:pointer;  " title="%{field_name}" name="%{field_name}"  id="%{id}"   onclick="OnlickEditee(this,'%{mappedtablelevel1,'%{mandatory}'}');"> <s:checkbox title="%{mappedtablelevel1}" name="%{field_name}" id="%{id}"   value="true"  disabled="true" /></s:div>
                  </s:else>
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

<s:if test="globalLevel>1">
<sj:accordionItem title="%{filed_name} >> %{level2Name}" id="two">  
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formTwo" name="formTwo" cssClass="cssn_form"  theme="simple"  method="post"enctype="multipart/form-data" >
				 	     <div style="display: none">
					 <span id="maped"><s:property value="%{mappedtablelevel2}"/>	</span>
					</div>
		
				
                  <%
				 int temp=0;
				 %>
				 <s:iterator value="listconfiguration2">
				 <%if(temp%2==0){ %>
				 <div class="form_menubox">
                  <div class="user_form_text"><s:property value="%{field_name}"/>:</div>
                   <div class="user_form_input">
                   <s:if test="%{actives}">
                  <s:checkbox fieldValue="%{mappedtablelevel2}"  name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select" onclick="OnlickEdite(this,'%{mandatory}');"/>
				  </s:if><s:else>
                  <s:div cssStyle="width:15%; padding-left: 21%; cursor:pointer;  "name="%{mappedtablelevel2}"  id="%{id}"  onclick="OnlickEditee(this,'%{mappedtablelevel2}','%{mandatory}');"><s:checkbox  name="%{field_name}" id="%{id}"   value="true"  disabled="true"/></s:div>
				  </s:else>
				  </div>
				  <%} else {%>
				  <div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
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
<s:if test="globalLevel>2">
<!-- Level3 -->
<sj:accordionItem title="%{filed_name} >> %{level3Name}" id="three">  
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formThree" name="formThree" cssClass="cssn_form"  theme="simple"  method="post"enctype="multipart/form-data" >
                  	 <div style="display: none">
					 <span id="maped"><s:property value="%{mappedtablelevel3}"/>	</span>
					</div>
             
                
                 <%
				 int temp=0;
				 %>
				 <s:iterator value="listconfiguration3">
				 <%if(temp%2==0){ %>
				 <div class="form_menubox">
                  <div class="user_form_text"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input">
                    <s:if test="%{actives}">
                  <s:checkbox fieldValue="%{mappedtablelevel3}" name="%{field_name}" id="%{id}"  cssClass="form_menu_inputtext sub_select" onclick="OnlickEdite(this,'%{mandatory}');"/>
                  </s:if>
                  <s:else>
                   <s:div  cssStyle="width:15%; padding-left: 21%; cursor:pointer;  " title="%{field_name}" id="%{id}" onclick="OnlickEditee(this,'%{mappedtablelevel3}','%{mandatory}');"><s:checkbox  name="%{field_name}" id="%{id}"   value="true"  disabled="true"/></s:div>
                  </s:else>
                  </div>
				  <%} else {%>
				  <div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
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
<s:if test="globalLevel>3">
<!-- Level4 -->
<sj:accordionItem title="%{filed_name} >> %{level4Name}" id="four">  
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formFour" name="formFour" cssClass="cssn_form"  theme="simple"  method="post"enctype="multipart/form-data" >
                  	  	 <div style="display: none">
					 <span id="maped"><s:property value="%{mappedtablelevel4}"/>	</span>
					</div>
           


 				<%
				 int temp=0;
				 %>
				 <s:iterator value="listconfiguration4">
				 <%if(temp%2==0){ %>
				 <div class="form_menubox">
                  <div class="user_form_text"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input">
                  <s:if test="%{actives}">
                  <s:checkbox fieldValue="%{mappedtablelevel4}" name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select" onclick="OnlickEdite(this,'%{mandatory}');"/>
                  	  </s:if>
                  	  <s:else>
                  	  <s:div  cssStyle="width:15%; padding-left: 21%; cursor:pointer;  " title="%{field_name}" id="%{id}" onclick="OnlickEditee(this,'%{mappedtablelevel4}','%{mandatory}');"><s:checkbox  name="%{field_name}" id="%{id}"   value="true"  disabled="true"/></s:div>
                  	  </s:else>
                  </div>
			
				  <%} else {%>
				  <div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
				  
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
<s:if test="globalLevel>4">
<!-- Level5 -->
<sj:accordionItem title="%{filed_name} >> %{level5Name}" id="five">  
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formFive" name="formFive" cssClass="cssn_form"  theme="simple"  method="post"enctype="multipart/form-data" >
                  		 <div style="display: none">
					 <span id="maped"><s:property value="%{mappedtablelevel5}"/>	</span>
					</div>
               
                
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