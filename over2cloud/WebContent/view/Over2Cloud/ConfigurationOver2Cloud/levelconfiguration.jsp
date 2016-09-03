
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

<script type="text/javascript">
 function getSubFormValue(id,ref){
	// alert(id);
	
	var selectid = $('input[name='+id+']:checked').map(function() {
	        return $(this).attr("title");
	    }).get();

	    var maped = ref.title;
        var conP = '<%=request.getContextPath()%>';
       $.ajax({
			    type : "post",
			    url : conP +"/view/CloudApps/addconfigurationsliding.action?titles="+selectid+"&mapedtable="+maped,
			    success : function(dividdd) {
		        alert("Success!!!");
			},
			   error: function() {
			        alert("error");
			    }
			 });
	   
  };
</script>
<body> 
<s:set name="levelIdValue" value="levelId"/>
<div class="form_inner" id="form_reg">
<div class="page_form">
<div style="display: none;">
<span id="conftable"> <s:property value="%{#parameters.mapedtable}"/>	</span>
</div>
<s:if test="%{#levelIdValue=='string1'}">

  			     <div class="form_menubox">
		           <div class="user_form_text">Level1</div>
                   <div class="user_form_input"><s:textfield  name="leveL1" title="Level" id="leveL1" cssClass="form_menu_inputtext" maxlength="100" /></div>
		            <div class="user_form_text1">Level2</div>
                     <div class="user_form_input"><s:textfield  name="levelId1" id="levelId1" cssClass="form_menu_inputtext" maxlength="100" /></div>
		    		 </div>
</s:if>
		     
		   <s:if test="%{#levelIdValue=='string2'}">
	
	
			<div class="form_menubox">
		                     <div class="user_form_text">Level1</div>
                     <div class="user_form_input"><s:textfield  name="levelId1" id="levelId1" cssClass="form_menu_inputtext" maxlength="100" /></div>
		                     <div class="user_form_text1">Level2</div>
                     <div class="user_form_input"><s:textfield  name="levelId1" id="levelId1" cssClass="form_menu_inputtext" maxlength="100" /></div>
		        
		     </div>
		     <div class="form_menubox">
		            <div class="user_form_text">Level3</div>
                   <div class="user_form_input"><s:textfield  name="levelId1" id="levelId1" cssClass="form_menu_inputtext" maxlength="100" /></div>
		     
		     </div>
		     
		     </s:if>
		             
		   <s:if test="%{#levelIdValue=='string3'}">
		   
		   <div class="form_menubox">
		                     <div class="user_form_text">Level1</div>
                     <div class="user_form_input"><s:textfield  name="levelId1" id="levelId1" cssClass="form_menu_inputtext" maxlength="100" /></div>
		                     <div class="user_form_text1">Level2</div>
                     <div class="user_form_input"><s:textfield  name="levelId1" id="levelId1" cssClass="form_menu_inputtext" maxlength="100" /></div>
		        
		     </div>
		            <div class="form_menubox">
		            <div class="user_form_text">Level3</div>
                   <div class="user_form_input"><s:textfield  name="levelId1" id="levelId1" cssClass="form_menu_inputtext" maxlength="100" /></div>
		            <div class="user_form_text1">Level4</div>
                   <div class="user_form_input"><s:textfield  name="levelId1" id="levelId1" cssClass="form_menu_inputtext" maxlength="100" /></div>
		     
		     </div>
		     </s:if>
		     
		           <s:if test="%{#levelIdValue=='string4'}">
		           
		           <div class="form_menubox">
		                     <div class="user_form_text">Level1</div>
                     <div class="user_form_input"><s:textfield  name="levelId1" id="levelId1" cssClass="form_menu_inputtext" maxlength="100" /></div>
		                     <div class="user_form_text1">Level2</div>
                     <div class="user_form_input"><s:textfield  name="levelId1" id="levelId1" cssClass="form_menu_inputtext" maxlength="100" /></div>
			     	</div>
		            <div class="form_menubox">
		            <div class="user_form_text">Level3</div>
                   <div class="user_form_input"><s:textfield  name="levelId1" id="levelId1" cssClass="form_menu_inputtext" maxlength="100" /></div>
		            <div class="user_form_text1">Level4</div>
                   <div class="user_form_input"><s:textfield  name="levelId1" id="levelId1" cssClass="form_menu_inputtext" maxlength="100" /></div>
		     
		     </div>
		     <div class="form_menubox">
		            <div class="user_form_text">Level5</div>
                   <div class="user_form_input"><s:textfield  name="levelId1" id="levelId1" cssClass="form_menu_inputtext" maxlength="100" /></div>
		     
		     </div>
		     
		     </s:if>
		        <s:if test="%{#levelIdValue=='string5'}">
		           <div class="form_menubox">
		                     <div class="user_form_text">Field One</div>
                     <div class="user_form_input"><s:textfield  name="levelId1" id="levelId1" cssClass="form_menu_inputtext" maxlength="100" /></div>
		            <div class="user_form_text1">Field Two</div>
                   <div class="user_form_input"><s:textfield  name="levelId1" id="levelId1" cssClass="form_menu_inputtext" maxlength="100" /></div>
		     
		     </div>
		       <div class="form_menubox">
		            <div class="user_form_text">Field Three</div>
                   <div class="user_form_input"><s:textfield  name="levelId1" id="levelId1" cssClass="form_menu_inputtext" maxlength="100" /></div>
		            <div class="user_form_text1">Field Four</div>
                   <div class="user_form_input"><s:textfield  name="levelId1" id="levelId1" cssClass="form_menu_inputtext" maxlength="100" /></div>
		     
		     </div>
		         <div class="form_menubox">
		            <div class="user_form_text">Field Five</div>
                   <div class="user_form_input"><s:textfield  name="levelId1" id="levelId1" cssClass="form_menu_inputtext" maxlength="100" /></div>
		     
		     </div>
		     </s:if>
<div id="levelIdMsg"></div>		  
<br>
<br>   
<!-- Code here for generating the configuration dyanamic page based on the select level of hierarchy added by sandeep on 28-2-2013 -->
<s:iterator value="dataList" id="dataList" status="xyzzz">  
<s:form name="three">
<div class="page_title"><h1 style="text-indent:5px;"><s:property value="%{key.mappedConfigLevelView}"/></h1></div>
<div class="steps_content" style="padding:10px 2.5%; float:left; width:95%;">
				<div class="form_content" style="max-height:210px; padding-top:0; width:100%; margin:0px 0px 0px 0px;">
<ul>
<s:set name="conidValue" value="%{key.configTable}" />
<s:iterator value="%{value}" var="listData" status="ConfigurationUtilBean" >
<s:set name="conid" value="%{id}" />
<s:if test="%{mandatory}">
<li><s:checkbox name="titles11-%{conidValue}" title="%{id}-%{conidValue}-" id="titles11-%{conidValue}" disabled="true" value="true" fieldValue="%{conid}"  theme="simple"/>&nbsp;&nbsp;<s:property value="field_name"/>
</li>
</s:if>
<s:else>
<li><s:checkbox name="titles11-%{conidValue}" title="%{id}-%{conidValue}-" id="titles11-%{conidValue}" value="true" fieldValue="%{conid}"  theme="simple"/>&nbsp;&nbsp;<s:property value="field_name"/>
</li>
</s:else>

</s:iterator>
</ul>
</div>

<div class="fields" style="width:100%; padding:0;">
<ul>
<li class="submit">
<input type="button" value="New Add" id="" title="<s:property value="key.mappedConfigDataTable"/>" name="" class="btn createNewBtn" onclick="getSubFormValue('titles11-<s:property value="%{key.configTable}"/>',this);" />
</li>										
</ul>
<div id="abccc"></div>
</div>
</div>
</s:form>
<br>
</s:iterator>  

		     </div></div>
		     </body>