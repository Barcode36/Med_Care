<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript">

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

	
	alert("Id is as"+id1+"<><>name<<>><"+name1+"<><>value is as"+value1+"<Table Name<><>>"+tableName);
	
	alert("Table Name is as <<><><><>"+tableName);


	 $("#ExcelDialog").load(conP+"/view/Over2Cloud/reportsConfiguration/getColumnsNamesNdTableName.action?id="+id+"&tableName="+tableName);                                             
	   $("#ExcelDialog").dialog('open');
}

//five


function callMe4()
{
	//four
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

	
	alert("Id is as"+id1+"<><>name<<>><"+name1+"<><>value is as"+value1+"<Table Name<><>>"+tableName);
	
	alert("Table Name is as <<><><><>"+tableName);


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

	
	alert("Id is as"+id1+"<><>name<<>><"+name1+"<><>value is as"+value1+"<Table Name<><>>"+tableName);
	
	alert("Table Name is as <<><><><>"+tableName);


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

	
	alert("Id is as"+id1+"<><>name<<>><"+name1+"<><>value is as"+value1+"<Table Name<><>>"+tableName);
	
	alert("Table Name is as <<><><><>"+tableName);


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

	
	//alert("#####"+selected.length);
/*	for(var i=0; i<selected.length; i++)
	{
		alert(selected[i]);

		
	}	
	*/
		var connection=document.getElementById("conString").value;
		var tableName=document.getElementById("mappedTableName").value;

		//$.ajax( {
			//alert("vggvjgfjg");
			//type :"post",
			//url :connection+"/view/Over2Cloud/reportsConfiguration/getColumnsNamesNdTableName.action?id="+id+"&name="+name+"&value="+value+"&tableName="+tableName,
			//success : function(subdeptdata) {
			//alert("Div name is as <><><><><<><><><"+targetDivName);
			//$("#ExcelDialog").html(subdeptdata);
			//},
			//error : function() {
			//	alert("error");
			//}
		//});

		//alert("Returned");


    //alert("11111");

    var conP = "<%=request.getContextPath()%>";

		alert("Id is as"+id1+"<><>name<<>><"+name1+"<><>value is as"+value1+"<Table Name<><>>"+tableName);
		
   $("#ExcelDialog").load(conP+"/view/Over2Cloud/reportsConfiguration/getColumnsNamesNdTableName.action?id="+id+"&tableName="+tableName);                                             
   $("#ExcelDialog").dialog('open');
	 
}




$.subscribe('getSelectedFields',function(event, data)
		{

	
	
	var connection=document.getElementById("conString").value;

	var tableName=document.getElementById("mappedTableName").value;

	event.originalEvent.options.submit = false;

	});
		 
</script>


 <script type="text/javascript">
function OnlickEdite(valuepass){
	
	var tablemapped=valuepass.value;
	var columnid=valuepass.id;
	var levelname=valuepass.name;
	alert("Table Mapped"+tablemapped+" Cloumn Id is as "+columnid+" Level Name is as "+levelname);
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
<div class="page_title"><h1>Configuration >> Editor</h1></div>
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
<sj:accordionItem title="%{filed_name}>> %{level1Name}" id="oneId">  
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formone" name="formone" theme="simple"  method="post"enctype="multipart/form-data" >
		<input type="hidden" value="<%=request.getContextPath()%>" id="conString">
		<input type="hidden" value="${mappedtablelevel1}" id="mappedTableName">
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
                   <s:checkbox  fieldValue="%{mappedtablelevel1}" name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select" />
                  </s:if><s:else>
                
                  <s:div id="one" cssStyle="width:15%; padding-left: 21%; cursor:pointer;  " title="%{field_name}"  name="%{field_name}"  id="%{id}"  ><s:checkbox title="%{mappedtablelevel1}" name="%{field_name}" id="%{id}"   value="true"  disabled="true" /></s:div>
                  </s:else> 

                  </div>
                 
				  <%} else {%>
				 <div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
				  <s:if test="%{actives}">
                 <div class="user_form_input"><s:checkbox  fieldValue="%{mappedtablelevel1}" name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select" /></div>
				 </s:if><s:else>
				
                  <s:div id="one" cssStyle="width:15%; padding-left: 21%; cursor:pointer;  " title="%{field_name}" name="%{field_name}"  id="%{id}"   > <s:checkbox title="%{mappedtablelevel1}" name="%{field_name}" id="%{id}"   value="true"  disabled="true" /></s:div>
                  </s:else>
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
	                           onclick="callMe()"
	                           />
	                           
	                           
	               
			</s:form>
</div>
</div>
</sj:accordionItem>

<s:if test="globalLevel>1">
<sj:accordionItem title="%{filed_name} >> %{level2Name}" id="two">  
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formTwo" name="formTwo" cssClass="cssn_form"  theme="simple"  method="post"enctype="multipart/form-data" >
		<input type="hidden" value="<%=request.getContextPath()%>" id="conString">
		<input type="hidden" value="${mappedtablelevel2}" id="mappedTableName2">
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
                  <s:checkbox fieldValue="%{mappedtablelevel2}"  name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select" />
				  </s:if><s:else>
                  <s:div cssStyle="width:15%; padding-left: 21%; cursor:pointer;  "name="%{mappedtablelevel2}"  id="%{id}"  ><s:checkbox title="%{mappedtablelevel2}" name="%{field_name}" id="%{id}"   value="true"  disabled="true"/></s:div>
				  </s:else>
				  </div>
				  <%} else {%>
				  <div class="user_form_text1"><s:property value="%{field_name}"/>:</div>
				  <div class="user_form_input">
				  <s:if test="%{actives}">
                 <s:checkbox fieldValue="%{mappedtablelevel2}" name="%{field_name}" id="%{id}" cssClass="form_menu_inputtext sub_select" />
				 </s:if> <s:else>
                  <s:div  cssStyle="width:15%; padding-left: 21%; cursor:pointer;  " title="%{field_name}" id="%{id}" ><s:checkbox title="%{mappedtablelevel2}" name="%{field_name}" id="%{id}"   value="true"  disabled="true"/></s:div>
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
			</s:form>
</div>
</div>
</sj:accordionItem>
</s:if>
<s:if test="globalLevel>2">
<!-- Level3 -->
<sj:accordionItem title="%{filed_name} >> %{level3Name}" id="three">  
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formThree" name="formThree" cssClass="cssn_form"  theme="simple"  method="post"enctype="multipart/form-data" >
       <input type="hidden" value="${mappedtablelevel3}" id="mappedTableName3">
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
<s:if test="globalLevel>3">
<!-- Level4 -->
<sj:accordionItem title="%{filed_name} >> %{level4Name}" id="four">  
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formFour" name="formFour" cssClass="cssn_form"  theme="simple"  method="post"enctype="multipart/form-data" >
        <input type="hidden" value="${mappedtablelevel4}" id="mappedTableName4">
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
<s:if test="globalLevel>4">
<!-- Level5 -->
<sj:accordionItem title="%{filed_name} >> %{level5Name}" id="five">  
<div class="form_inner" id="form_reg">
<div class="page_form">
		<s:form id="formFive" name="formFive" cssClass="cssn_form"  theme="simple"  method="post"enctype="multipart/form-data" >
        <input type="hidden" value="${mappedtablelevel5}" id="mappedTableName5">
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