<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
 <script type="text/javascript">


	function saveSelected1()
	{
		var selected = [];
	 	var id=[];
	 	var id1;
	 	
	 	var name=[];
	 	var name1;
	 	
	 	

	 	var value=[];
	 	var value1;
	 	
	 	$('#oneId input:checked').each(function() 
	 	{
	 	    selected.push(this.id+"#"+this.name+"#"+this.value);

	 		id.push(this.id);
	 		id1=id1+","+this.id;
	 		
	 		name.push(this.name);
	 		name1=name1+","+this.name;

	 		value.push(this.value);
	 		value1=value1+","+this.value;
	 	});
	 		var connection=document.getElementById("conString").value;
	 		var tableName=document.getElementById("mappedTableName").value;
	 		var actionType="saveSelected";
	        var conP = "<%=request.getContextPath()%>";
	 		
		    $("#ExcelDialog").load(conP+"/view/Over2Cloud/reportsConfiguration/getColumnsNamesNdTableNameForGraph.action?id="+id+"&tableName="+tableName+"&save="+actionType);                                             
		    $("#ExcelDialog").dialog('open');
	}

	function saveSelected2()
	{
		var selected = [];
	 	var id=[];
	 	var id1;
	 	
	 	var name=[];
	 	var name1;
	 	
	 	

	 	var value=[];
	 	var value1;
	 	
	 	$('#two input:checked').each(function() 
	 	{
	 	    selected.push(this.id+"#"+this.name+"#"+this.value);

	 		id.push(this.id);
	 		id1=id1+","+this.id;
	 		
	 		name.push(this.name);
	 		name1=name1+","+this.name;

	 		value.push(this.value);
	 		value1=value1+","+this.value;
	 	});
	 		var connection=document.getElementById("conString").value;
	 		var tableName=document.getElementById("mappedTableName").value;
	 		var actionType="saveSelected";
	        var conP = "<%=request.getContextPath()%>";
		    $("#ExcelDialog").load(conP+"/view/Over2Cloud/reportsConfiguration/getColumnsNamesNdTableName.action?id="+id+"&tableName="+tableName+"&save="+actionType);                                             
		    $("#ExcelDialog").dialog('open');
	}

	function callMe5()
	 {
	 	var selected = [];
	 	var id=[];
	 	var id1;
	 	var name=[];
	 	var name1;
	 	var value=[];
	 	var value1;
	 	
	 	$('#five input:checked').each(function() 
	 	{
	 	    selected.push(this.id+"#"+this.name+"#"+this.value);

	 		id.push(this.id);
	 		id1=id1+","+this.id;
	 		name.push(this.name);
	 		name1=name1+","+this.name;
	 		value.push(this.value);
	 		value1=value1+","+this.value;
	 	});

	 	var tableName=document.getElementById("mappedTableName5").value;
	 	var conP = "<%=request.getContextPath()%>";

	 	 $("#ExcelDialog").load(conP+"/view/Over2Cloud/reportsConfiguration/getColumnsNamesNdTableName.action?id="+id+"&tableName="+tableName);                                             
	 	   $("#ExcelDialog").dialog('open');
	 	
	 }
	 
 function callMe4()
 {
 	var selected = [];
 	var id=[];
 	var id1;
 	var name=[];
 	var name1;
 	var value=[];
 	var value1;
 	
 	$('#four input:checked').each(function() 
 	{
 	    selected.push(this.id+"#"+this.name+"#"+this.value);

 		id.push(this.id);
 		id1=id1+","+this.id;
 		
 		name.push(this.name);
 		name1=name1+","+this.name;

 		value.push(this.value);
 		value1=value1+","+this.value;

 	    
 	});

 	var tableName=document.getElementById("mappedTableName4").value;
 	var conP = "<%=request.getContextPath()%>";

 	 $("#ExcelDialog").load(conP+"/view/Over2Cloud/reportsConfiguration/getColumnsNamesNdTableName.action?id="+id+"&tableName="+tableName);                                             
 	   $("#ExcelDialog").dialog('open');
 	
 }

 
 function callMe()
 {
 	var selected = [];
 	var id=[];
 	var id1;
 	
 	var name=[];
 	var name1;
 	
 	

 	var value=[];
 	var value1;
 	
 	$('#oneId input:checked').each(function() 
 	{
 	    selected.push(this.id+"#"+this.name+"#"+this.value);

 		id.push(this.id);
 		id1=id1+","+this.id;
 		
 		name.push(this.name);
 		name1=name1+","+this.name;

 		value.push(this.value);
 		value1=value1+","+this.value;
 	});
 		var connection=document.getElementById("conString").value;
 		var tableName=document.getElementById("mappedTableName").value;
        var conP = "<%=request.getContextPath()%>";
    $("#ExcelDialog").load(conP+"/view/Over2Cloud/reportsConfiguration/getColumnsNamesNdTableName.action?id="+id+"&tableName="+tableName);                                             
    $("#ExcelDialog").dialog('open');
 	 
 }

 function callMe2()
 {
 	var selected = [];
 	var id=[];
 	var id1;
 	var name=[];
 	var name1;
 	var value=[];
 	var value1;
 	
 	$('#two input:checked').each(function() 
 	{
 	    selected.push(this.id+"#"+this.name+"#"+this.value);

 		id.push(this.id);
 		id1=id1+","+this.id;
 		
 		name.push(this.name);
 		name1=name1+","+this.name;

 		value.push(this.value);
 		value1=value1+","+this.value;

 	    
 	});

 	var tableName=document.getElementById("mappedTableName2").value;
 	var conP = "<%=request.getContextPath()%>";
 	 $("#ExcelDialog").load(conP+"/view/Over2Cloud/reportsConfiguration/getColumnsNamesNdTableName.action?id="+id+"&tableName="+tableName);                                             
 	   $("#ExcelDialog").dialog('open');
 	
 }

 function callMe3()
 {
 	var selected = [];
 	var id=[];
 	var id1;
 	var name=[];
 	var name1;
 	var value=[];
 	var value1;
 	
 	$('#three input:checked').each(function() 
 	{
 	    selected.push(this.id+"#"+this.name+"#"+this.value);

 		id.push(this.id);
 		id1=id1+","+this.id;
 		
 		name.push(this.name);
 		name1=name1+","+this.name;

 		value.push(this.value);
 		value1=value1+","+this.value;

 	    
 	});

 	var tableName=document.getElementById("mappedTableName3").value;
 	var conP = "<%=request.getContextPath()%>";
 	 $("#ExcelDialog").load(conP+"/view/Over2Cloud/reportsConfiguration/getColumnsNamesNdTableName.action?id="+id+"&tableName="+tableName);                                             
 	   $("#ExcelDialog").dialog('open');
 }


 
function OnlickEdite(valuepass){
	var tablemapped=valuepass.value;
	var columnid=valuepass.id;
	var levelname=valuepass.name;
    var conP = "<%=request.getContextPath()%>";
        $("#mybuttondialog").load(conP+"/view/CloudApps/operationwithfieldAction.action?mapedtable="+tablemapped.split(" ").join("%20")+"&id="+columnid.split(" ").join("%20")+"&levelname="+levelname.split(" ").join("%20"));
	   $("#mybuttondialog").dialog('open');
	}   
</script>
<SCRIPT type="text/javascript">
function OnlickEditee(valuepass,tablmapped){
	
	var levelname=valuepass.title;
	var columnid=valuepass.id;
    var tablemapped =tablmapped;
	var conP = "<%=request.getContextPath()%>";
    $("#mybuttondialog").load(conP+"/view/CloudApps/operationwithfieldAction.action?mapedtable="+tablemapped.split(" ").join("%20")+"&id="+columnid.split(" ").join("%20")+"&levelname="+levelname.split(" ").join("%20"));
   $("#mybuttondialog").dialog('open');
}
 function SaveEditefielddetails(){
	    var active = document.getElementById("chk1").checked;
		var inactive = document.getElementById("chk2").checked;
		var mandatory = document.getElementById("mandatory").checked;
		var field_value = document.getElementById("field_value").value;
		var field_name = document.getElementById("field_name").value;
		var field_type = document.getElementById("field_type").value;
		var id = $("#id").text();
		var mapedtable = $("#mapedtable").text();
		var comonmaped= $("#maped").text();
	    var conP = "<%=request.getContextPath()%>";

		$.ajax({
		    type : "post",
		    url : conP+"/view/CloudApps/editefieldoperationwithAll.action?active="+active+"&inactive="+inactive+"&mandatory="+mandatory+"&field_value="+field_name+"&id="+id+"&level_name="+field_value.split(" ").join("%20")+"&mapedtable="+mapedtable+"&field_type="+field_type
		    +"&comonmaped="+comonmaped,
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
<SCRIPT type="text/javascript">
$.subscribe('level1', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
	       });
$.subscribe('level2', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#orglevel2Div").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel2Div").fadeOut(); }, 4000);
	       });
$.subscribe('level3', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#orglevel3Div").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel3Div").fadeOut(); }, 4000);
	       });
$.subscribe('level4', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#orglevel4Div").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel4Div").fadeOut(); }, 4000);
	       });
$.subscribe('level5', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#orglevel5Div").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel5Div").fadeOut(); }, 4000);
	       });
</script>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
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
<div class="page_title"><h1>Organization Registration >> Editor</h1></div>
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<sj:dialog 
                                id="ExcelDialog" 
                                autoOpen="false"
                    			closeOnEscape="true" 
                    			modal="true" 
                    			title="Reports >> Selected Column Values" 
                    		    width="950" 
                    			height="350" 
                    			showEffect="slide" 
                    			hideEffect="explode">
                    
                    
                    <sj:div id="foldeffect"  effect="fold"><div id="saveExcel"></div></sj:div>
                    <div id="Result11" ></div>
                    </sj:dialog> 
<sj:accordion id="accordion" autoHeight="false">
<s:if test= "level1Name!=null">
<sj:accordionItem title="%{filed_name}>> %{level1Name}" id="oneId">  
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formone" name="formone" namespace="/view/Over2Cloud/hr" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
			<input type="hidden" value="<%=request.getContextPath()%>" id="conString">
		<input type="hidden" value="${mappedtablelevel1}" id="mappedTableName">
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
                  <div class="user_form_text"><s:property value="%{field_name}"/>:</div>
                   <div class="user_form_input">
                   <s:if test="%{actives}">
                  <s:checkbox fieldValue="%{mappedtablelevel1}"  name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select" />
				  </s:if><s:else>
                  <s:div cssStyle="width:15%; padding-left: 21%; cursor:pointer;  " title="%{field_name}" name="%{field_name}"  id="%{id}" ><s:checkbox  name="%{field_name}" id="%{id}"   value="true"  disabled="false"/></s:div>
				  </s:else>
				  </div>
				  <%} else {%>
				  <div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
				  <div class="user_form_input">
				  <s:if test="%{actives}">
                 <s:checkbox fieldValue="%{mappedtablelevel1}" name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select" />
				 </s:if> <s:else>
                  <s:div  cssStyle="width:15%; padding-left: 21%; cursor:pointer;  " title="%{field_name}" name="%{field_name}" id="%{id}" ><s:checkbox  name="%{field_name}" id="%{id}"   value="true"  disabled="false"/></s:div>
				 </s:else>
		          </div>
				 </div>
				  <%} 
				  temp++;
				  %>
				 </s:iterator>
		      <sj:submit 
	                           value="  Get Selected Data" 
	                           targets="Result" 
	                           clearForm="true"
	                           button="true"
	                           onclick="callMe()"
	                           />
	          <sj:submit 
	                           value=" Get Selected Graph" 
	                           targets="Result" 
	                           clearForm="true"
	                           button="true"
	                           onclick="saveSelected1()"
	                           />
               </div>
			</s:form>
</div>
</div>
</sj:accordionItem>
</s:if>
<s:if test= "level2Name!=null">
<sj:accordionItem title="%{filed_name} >> %{level2Name}" id="two">  
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formTwo" name="formTwo" cssClass="cssn_form"  theme="simple"  method="post"enctype="multipart/form-data" >
             <input type="hidden" value="${mappedtablelevel2}" id="mappedTableName2">
                  <%
				 int temp=0;
				 %>
				 <s:iterator value="listconfiguration2">
				 <%if(temp%2==0){ %>
				 <div class="form_menubox">
                  <div class="user_form_text"><s:property value="%{field_name}"/>:</div>
                   <div class="user_form_input">
                   <s:if test="%{actives}">
                  <s:checkbox fieldValue="%{mappedtablelevel2}"  name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select" />
				  </s:if><s:else>
                  <s:div cssStyle="width:15%; padding-left: 21%; cursor:pointer;  "name="%{mappedtablelevel2}"  id="%{id}" ><s:checkbox  name="%{field_name}" id="%{id}"   value="true"  disabled="true"/></s:div>
				  </s:else>
				  </div>
				  <%} else {%>
				  <div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
				  <div class="user_form_input">
				  <s:if test="%{actives}">
                 <s:checkbox fieldValue="%{mappedtablelevel2}" name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select" />
				 </s:if> <s:else>
                  <s:div  cssStyle="width:15%; padding-left: 21%; cursor:pointer;  " title="%{field_name}" id="%{id}" ><s:checkbox  name="%{field_name}" id="%{id}"   value="true"  disabled="true"/></s:div>
				 </s:else>
		          </div>
				 </div>
				  <%} 
				  temp++;
				  %>
				 </s:iterator>
				  <sj:submit 
	                           value="  Get Selected" 
	                           targets="Result" 
	                           clearForm="true"
	                           button="true"
	                           onclick="callMe2()"
	                           />
	                           
	                           <sj:submit 
	                           value=" Save Selected" 
	                           targets="Result" 
	                           clearForm="true"
	                           button="true"
	                           onclick="saveSelected2()"
	                           />
			</s:form>
</div>
</div>
</sj:accordionItem>
</s:if>
<s:if test= "level3Name!=null">
<sj:accordionItem title="%{filed_name} >> %{level3Name}" id="three">  
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formThree" name="formThree" cssClass="cssn_form"  theme="simple"  method="post"enctype="multipart/form-data" >
                    <input type="hidden" value="${mappedtablelevel3}" id="mappedTableName3"> 
              <s:property value="%{mappedtablelevel3}"/>
                 <%
				 int temp=0;
				 %>
				 <s:iterator value="listconfiguration3">
				 <%if(temp%2==0){ %>
				 <div class="form_menubox">
                  <div class="user_form_text"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input">
                    <s:if test="%{actives}">
                  <s:checkbox fieldValue="%{mappedtablelevel3}" name="%{field_name}" id="%{id}"  cssClass="form_menu_inputtext sub_select" />
                  </s:if>
                  <s:else>
                   <s:div  cssStyle="width:15%; padding-left: 21%; cursor:pointer;  " title="%{field_name}" id="%{id}" ><s:checkbox  name="%{field_name}" id="%{id}"   value="true"  disabled="true"/></s:div>
                  </s:else>
                  </div>
				  <%} else {%>
				  <div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input">
                    <s:if test="%{actives}">
                  <s:checkbox fieldValue="%{mappedtablelevel3}" name="%{field_name}" id="%{id}"  cssClass="form_menu_inputtext sub_select" />
                  </s:if>
                  <s:else>
                   <s:div  cssStyle="width:15%; padding-left: 21%; cursor:pointer;  " title="%{field_name}" id="%{id}" ><s:checkbox  name="%{field_name}" id="%{id}"   value="true"  disabled="true"/></s:div>
                  </s:else>
                  
                  </div>
				
				 </div>
				  <%} 
				  temp++;
				  %>
				 </s:iterator>
                  
                <sj:submit 
	                           value="  Get Selected" 
	                           targets="Result" 
	                           clearForm="true"
	                           button="true"
	                           onclick="callMe3()"
	                           />
			</s:form>
</div>
</div>
</sj:accordionItem>
</s:if>

<!-- Level4 -->
<s:if test= "level4Name!=null">
<sj:accordionItem title="%{filed_name} >> %{level4Name}" id="four">  
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formFour" name="formFour" cssClass="cssn_form"  theme="simple"  method="post"enctype="multipart/form-data" >
                   <input type="hidden" value="${mappedtablelevel4}" id="mappedTableName4"> 
              <s:property value="%{mappedtablelevel3}"/>  	  
 				<%
				 int temp=0;
				 %>
				 <s:iterator value="listconfiguration4">
				 <%if(temp%2==0){ %>
				 <div class="form_menubox">
                  <div class="user_form_text"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input">
                  <s:if test="%{actives}">
                  <s:checkbox fieldValue="%{mappedtablelevel4}" name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select" />
                  	  </s:if>
                  	  <s:else>
                  	  <s:div  cssStyle="width:15%; padding-left: 21%; cursor:pointer;  " title="%{field_name}" id="%{id}" ><s:checkbox  name="%{field_name}" id="%{id}"   value="true"  disabled="true"/></s:div>
                  	  </s:else>
                  </div>
			
				  <%} else {%>
				  <div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
				  
                  <div class="user_form_input">
                  <s:if test="%{actives}">
                  <s:checkbox fieldValue="%{mappedtablelevel4}" name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select" />
				 </s:if>
				 <s:else>
				 <s:div  cssStyle="width:15%; padding-left: 21%; cursor:pointer;  " title="%{field_name}" id="%{id}" ><s:checkbox  name="%{field_name}" id="%{id}"   value="true"  disabled="true"/></s:div>
				 </s:else>
				 </div>
				 </div>
				  <%} 
				  temp++;
				  %>
				 </s:iterator>
				  <sj:submit 
	                           value="  Get Selected" 
	                           targets="Result" 
	                           clearForm="true"
	                           button="true"
	                           onclick="callMe4()"
	                           />
               
			</s:form>
</div>
</div>
</sj:accordionItem>

</s:if>
<!-- Level5 -->
<s:if test= "level5Name!=null">
<sj:accordionItem title="%{filed_name} >> %{level5Name}" id="five">  
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formFive" name="formFive" cssClass="cssn_form"  theme="simple"  method="post"enctype="multipart/form-data" >
                  	<input type="hidden" value="${mappedtablelevel5}" id="mappedTableName5">
                 
                <%
				 int temp=0;
				 %>
				 <s:iterator value="listconfiguration5">
				 <%if(temp%2==0){ %>
				 <div class="form_menubox">
                  <div class="user_form_text"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input">
                  <s:if test="%{actives}">
                  <s:checkbox fieldValue="%{mappedtablelevel5}" name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select" />
                  </s:if>
                  <s:else>
                  <s:div  cssStyle="width:15%; padding-left: 21%; cursor:pointer;  " title="%{field_name}" id="%{id}" ><s:checkbox  name="%{field_name}" id="%{id}"   value="true"  disabled="true"/></s:div>
                  </s:else>
                  </div>
				  <%} else {%>
				  <div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
                  <div class="user_form_input">
                  <s:if test="%{actives}">
                  <s:checkbox fieldValue="%{mappedtablelevel5}" name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select" />
                  </s:if>
                  <s:else>
                  <s:div  cssStyle="width:15%; padding-left: 21%; cursor:pointer;  " title="%{field_name}" id="%{id}" ><s:checkbox  name="%{field_name}" id="%{id}"   value="true"  disabled="true"/></s:div>
                  </s:else>
                  </div>
				 </div>
				  <%} 
				  temp++;
				  %>
				 </s:iterator>
               
               <sj:submit 
	                           value="  Get Selected" 
	                           targets="Result" 
	                           clearForm="true"
	                           button="true"
	                           onclick="callMe5()"
	                           />
			</s:form>
</div>
</div>
</sj:accordionItem>
</s:if>
</sj:accordion>
</div>
</div>
</html>