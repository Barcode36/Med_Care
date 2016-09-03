<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>

<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link   type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<link   type="text/css" href="<s:url value="/css/validation.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/js.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<script src="js/jquery.js"></script>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

<link rel="stylesheet" type="text/css" href="css/stylesheet.css" />
<link href="http://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="JavaScript/show_hide.js"></script>

<style type="text/css">
.pop_up_content_inner_sub h1{
	font-size:14px; font-weight:bold; text-align:center; padding:0px 0px 20px 0px;
}
.pop_up_submit_here{
	background: url("images/submit_here.png") no-repeat scroll 0 0 transparent;
    float: left;
    height: 38px;
    transition: background 0.15s ease-in-out 0s;
    width: 106px;
}
.pop_up_submit_here:hover{
	background: url("images/submit_here.png") no-repeat scroll 0 -38px transparent;
}
.pop_up_submit_here input{
	width:106px; height:38px; cursor:pointer; background:none; border:none;
}
.popoupdiv{
	height:auto!important;
}

#pop_up_form{
	height:185px; overflow:auto; padding:0px 0px 20px 0px;
}
.sub_field_main input.field{
	padding:2px 4px;
}
.sub_field_main input.validity{
	background: none repeat scroll 0 0 #F5F9FC;
    border: 1px solid #64A4D2;
    border-radius: 5px 5px 5px 5px;
    color: #222222;
    font-family: 'Open Sans',sans-serif;
    font-size: 13px;
    padding:2px 4px; width:100px;
}
.sub_field_main input.validity1{
	background: none repeat scroll 0 0 #F5F9FC;
    border: 1px solid #64A4D2;
    border-radius: 5px 5px 5px 5px;
    color: #222222;
    font-family: 'Open Sans',sans-serif;
    font-size: 13px;
    padding:2px 4px; width:90px;
}
</style>

<script type="text/javascript"><!--
$.subscribe('complete', function(event,data)
        {
         
        });

function toggle_me(){
	// on Submit Java Script Done For Client Side.....
	var sub_disable1	=	document.getElementById("disableAccountid");
	sub_disable.style.display	=	"none";
}

function calcluatePeriod(divId,PID)
{
	if(PID!=-2 && PID!=-1)
	{
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Setting/getpackageInfo.action?PID="+PID,
		    success : function(data) {
		    //alert(data);
			 var itemsData=data.split(',');
			 document.getElementById("valid_from"+divId.id).value=itemsData[0];
			 document.getElementById("valid_to"+divId.id).value=itemsData[1];
			 document.getElementById("totalCost"+divId.id).value=itemsData[2];
			 
		},
		   error: function() {
	            alert("error");
	        }
		 });
		divId.style.display="block";
	}
	else
	{
		alert("We Will Contact You Soon, Thank You!!!");
		//var um=document.getElementById("um").value;
		//var kcf=document.getElementById("kcf").value;
		//var id=document.getElementById("id").value;
		//var uid=document.getElementById("uid").value;
		//var contryid=document.getElementById("contryid").value;
		//alert("um"+um+"kcf"+kcf+"id"+id+"uid"+uid+"contryid"+contryid);
		//$("#packageDlg").load("<%=request.getContextPath()%>/view/Over2Cloud/Setting/newPackageRequest.action?um="+um+"&kcf="+kcf+"&id="+id+"&uid="+uid+"&contryid="+contryid);
		//$("#packageDlg").dialog('open');*/
	}
}

function productdisplay(me,val){
if(me.checked)
$("#"+val).css("display","block");
else
$("#"+val).css("display","none");

}

</script>

<script language="javascript" src="JavaScript/selectBox.js"></script>
<script type="text/javascript" language="javascript" src="JavaScript/pop_up.js"></script>
<link rel="stylesheet" type="text/css" href="css/pop_up.css" />
</head>
<body onload="fadePopIn();" >
<sj:dialog 
	id="packageDlg" 
	title="Request For New User Package" 
	autoOpen="false" 
	modal="true" 
	width="400" 
	showEffect="slide" 
	hideEffect="explode" 
	height="300"
>
</sj:dialog>
<div class="overlay_shadow" id="overlay_shadow"></div>
<div id="Result">
<s:form name="productregistation"  id="AddProductReg_Url" action="doProductRegistation"  theme="css_xhtml" theme="simple"  method="post">
<s:hidden id="um" name="um" value="%{um}"> </s:hidden>
<s:hidden id="kcf" name="kcf" value="%{kcf}"> </s:hidden>
<s:hidden id="id" name="id" value="%{id}"> </s:hidden>
<s:hidden id="uid" name="uid" value="%{uid}"> </s:hidden>
<s:hidden id="contryid" name="contryid" value="%{contryid}"> </s:hidden>
<div class="lightbox">
	<div class="popoupdiv" style=" width:1055px;">
		<div class="pop_content">
			<div class="pop_content_block"><img src="images/pop_up_top.png" width="1044" height="20" alt="" title="" /></div>
			<div class="pop_up_content_inner">
				<div class="pop_up_content_inner_sub" style="width:1020px">
				
					<h1>Choose your Products</h1>
					<div class="pop_text" style="width:920px"><p>Please select the applications you are interested in and 
					select the package of user license and validity (These application package are with current offer).</p></div>
					<div class="pop_up_form" id="pop_up_form" style="width:1020px">
						
					<s:iterator value="appslist" var="BeanApps">
						 <s:set var="applicationCode" value="app_code" />
                         <s:set var="applicationName" value="app_name" />
						
						<div class="sub_field_main" style=" width:100%;">
							<div style=" float:left; width:20px; text-align:center; padding-top:9px;">
								<s:checkbox  labelposition="right"  name="appcheckname"  fieldValue="%{app_code}" onclick="productdisplay(this,'one%{app_code}');"/>
							</div>
							<div style=" float:left; width:19%; text-indent:5px; font-size:12px; padding-top:8px; line-height:16px;"><s:property value="applicationName" /></div>
							<div class="sub_field_main" id="one${BeanApps.app_code}" style="display:none; width:25%; float:left;">
							<s:select 
                              id="noOfUser"
                              name="noOfUser" 
                              list="packageValue"
                              headerKey="-1"
                              headerValue="--Select Package--" 
                              cssClass="field"
                              onchange="calcluatePeriod(%{app_code},this.value);"
                              theme="simple"
                              cssClass="select"
                              cssStyle="width:70%"
                              />
							</div>
							<!-- noOfUser -->
							<div id="${BeanApps.app_code}" style="display:none; float: left;">
							<div style="float:left;padding-top:5px;">
							<b>Validity</b>
							</div>
							<div class="sub_field_main sub_text_box_add" style=" width:12%;">
							<s:textfield name="valid_from" id="valid_from%{app_code}" readonly="true" cssClass="validity1"></s:textfield>
          				      </div>
							<div class="sub_field_main sub_text_box_add" style=" width:12%;">
							<s:textfield name="valid_to" id="valid_to%{app_code}" readonly="true" cssClass="validity1"></s:textfield>
							</div>
							<div class="sub_field_main sub_text_box_add" style=" width:12%;">
							<s:textfield name="totalCost" id="totalCost%{app_code}" readonly="true" cssClass="validity1"></s:textfield>
							</div>
							</div>
							
						</div>
												
					</s:iterator>
						
					</div>
					
					<div style="width:90%; margin:0 auto; text-align:center;"><div class="pop_up_forgot"><a href="javascript:void();">please contact our <span>support team</span>, for problems of any sort</a></div>
					</div>
					<div class="sub_field_main" style="text-align:center;"><img id="indicator1" src="<s:url value="/images/ajax-loader.gif"/>" alt="Loading..."  width="70%"  style="display:none"/></div>
					
					<div class="pop_up_submit" align="center">
						<!--  <div class="pop_up_submit_here closepopup"><input type="submit" name="" value="" /></div>
						-->
						<div class="pop_up_reg" align="center">
						 <sj:submit 
                              targets="Result" 
                              clearForm="true"
                              id="form_submit"
                              value="" 
                              href="%{AddProductReg_Url}"
                              effect="highlight"
                              cssClass="form_submit"
                              effectOptions="{float:'right'}"
                              effectDuration="5000"
                              button="true"
                              onCompleteTopics="complete;"
                              onBeforeTopics="validate"
                              onclick="javascript:toggle_me();"
                              indicator="indicator1"
                            />
						</div>
					</div>
				</div>
			</div>
			<div class="pop_content_block"><img src="images/pop_up_bottom.png" width="1044" height="18" alt="" title="" /></div>
		</div>
	</div>
</div>
</s:form>
 </div>
</body>
</html>