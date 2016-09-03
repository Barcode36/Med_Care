<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/compliance/compliance.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
$(document).ready(function()
	{
	$("#krname").multiselect({
		   show: ["", 200],
		   hide: ["explode", 1000]
		});
	});


$.subscribe('makeEffect', function(event,data)
		  {
			 setTimeout(function(){ $("#complTarget").fadeIn(); }, 10);
			 setTimeout(function(){ $("#complTarget").fadeOut(); }, 1000);
			 $.ajax({
				    type : "post",
				    url : "view/Over2Cloud/Compliance/compl_task_page/ViewTaskName4AddKR.action",
				    success : function(data) 
				    {
					$("#data_part").html(data);
					},
				    error: function()
				    {
				        alert("error");
				    }
				 });
		  });

</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Un-Mapped KR </div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Add</div> 
</div>
<div class="clear"></div>
<div style="width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
<div class="form_inner" id="form_reg" style="width:100%; padding:0px;">
<div class="page_form">
<s:form id="complKRComplTipTask" name="complKRComplTipTask" action="addKRComplTipAction" namespace="/view/Over2Cloud/Compliance/compl_task_page" theme="simple"  method="post"enctype="multipart/form-data" >
<s:hidden name="taskId" value="%{taskId}" />
	<div class="newColumn">
          	<div class="leftColumn">KR Group:</div>
           	<div class="rightColumn">
           	<s:if test="%{mandatory}"><span class="needed"></span></s:if>
           	<s:select  
                        id		    ="kr_group"
                        list		="krGroupList"
                        headerKey	="-1"
                        headerValue="Select KR Group" 
                        cssClass="select"
		 				cssStyle="width:95%"
		 				onchange="commonSelectAjaxCall('kr_sub_group_data','id','subGroupName','groupName',this.value,'','','subGroupName','ASC','kr_subgroup','Select Sub-Group Name');"
                        >
            </s:select>
           	</div>
          	</div>
          	
          	<div class="newColumn">
          	<div class="leftColumn">Sub-Group Name:</div>
           	<div class="rightColumn">
           	<s:if test="%{mandatory}"><span class="needed"></span></s:if>
           	<s:select  
                        id		    ="kr_subgroup"
                        list		="{'No Data'}"
                        headerKey	="-1"
                        headerValue="Select Sub-Group Name" 
                        cssClass="select"
		 				cssStyle="width:95%"
		 				onchange="getKRName(this.value);"
                        >
            </s:select>
           	</div>
          	</div>
          	
          	<div class="newColumn">
          	<div class="leftColumn">KR Name:</div>
           	<div class="rightColumn">
           	<s:if test="%{mandatory}"><span class="needed"></span></s:if>
           	<div id="krNameDiv">
           	<s:select  
                        id		    ="krname"
                        name		="krname"
                        list		="{'No Data'}"
                        headerKey	="-1"
                        headerValue="Select KR Name" 
                        cssClass="textField"
		 				cssStyle="width:5%"
		 				multiple="true"
                        >
            </s:select>
            </div>
           	</div>
          	</div>
          	
          	<s:iterator value="complTaskTxtBox" status="status">
			    <div class="newColumn">
         		<div class="leftColumn"><s:property value="%{field_name}"/>:</div>
          		<div class="rightColumn">
          		<s:textfield cssStyle="width: 92%" name="%{field_value}"  id="%{field_value}"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px; width:80%"/>
          		<div id="newDiv" style="float: right;width: 10%;margin-top: -46px;"><sj:a value="+" onclick="adddiv('100')" button="true" cssStyle="margin-left: -14px;margin-top: 47px;">+</sj:a></div>
          		</div>
         	    </div>
		  </s:iterator>
		  
		  <s:iterator begin="100" end="108" var="compTipIndx">
		  <div class="clear"></div>
	      <div id="<s:property value="%{#compTipIndx}"/>" style="display: none">
	      		<s:iterator value="complTaskTxtBox" status="status">
	      		<div class="newColumn">
         		<div class="leftColumn"><s:property value="%{field_name}"/>:</div>
          		<div class="rightColumn">
          			<sj:textfield name="%{field_value}" id="%{field_value}" placeholder="Enter Data" cssClass="textField" />
          			<div style="float: left;margin-left: 85%;margin-top: -29px;width: 44%">	
					<s:if test="#compTipIndx==108">
	                    <div class="user_form_button2"><sj:a value="-" onclick="deletediv('%{#compTipIndx}')" button="true">-</sj:a></div>
	                </s:if>
					<s:else>
	  		  			<div class="user_form_button2"><sj:a value="+" onclick="adddiv('%{#compTipIndx+1}')" button="true" cssStyle="margin-left: -12px;">+</sj:a></div>
	          			<div class="user_form_button3" style="margin-left: -4px;"><sj:a value="-" onclick="deletediv('%{#compTipIndx}')"  button="true">-</sj:a></div>
	       			</s:else>
       			</div>
          		</div>
         	    </div>
          		
	      		</s:iterator>
	      </div>
	      </s:iterator>
	      
	        
		<div class="clear"></div>
        <div class="clear"></div>
        <br>
		<div class="fields">
		<center>
		 <ul>
			<li class="submit" style="background:none;">
			<div class="type-button">
	        <sj:submit 
         				targets			=	"complTarget" 
         				clearForm		=	"true"
         				value			=	" Save " 
         				button			=	"true"
         				cssClass		=	"submit"
                 		indicator		=	"indicator2"
                 		effect			=	"highlight"
                 		effectOptions	=	"{ color : '#222222'}"
                 		effectDuration	=	"5000"
                 		onCompleteTopics=	"makeEffect"
     		  	  />
     		  	    <sj:a 
                        button="true" href="#"
                        onclick="resetTaskName('complKRComplTipTask'); resetColor('.pIds');"
                        >
                        Reset
                    </sj:a>
     		  	  <sj:a 
						button="true" href="#"
						onclick="viewTaskNameMaster();"
						>
						Back
					</sj:a>
	        </div>
	        </li>
		 </ul>
		  <center><sj:div id="complTarget"  effect="fold"> </sj:div></center>
		 </center>
</div>
</s:form>
</div>
</div>
</div>
</div>
</body>
</html>