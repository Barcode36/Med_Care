<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>

<script type="text/javascript">

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
	}
}

</script>
<style type="text/css">
.sub_field_main select{
	background: #f5f9fc;
	width: 98%;
	border: 1px solid #64a4d2;
	border-radius: 5px;
	-moz-border-rdius: 5px;
	-moz-border-radius: 5px;
	padding: 3px;
	font-size: 13px;
	color: #222222;
	font-family: 'Open Sans', sans-serif;
}
.sub_field_main input{
	background: none repeat scroll 0 0 #F5F9FC;
	border: 1px solid #64A4D2;
	border-radius: 5px 5px 5px 5px;
	color: #222222;
	font-family: 'Open Sans',sans-serif;
	font-size: 13px;
	padding: 2px 4px;
}
.sub_text_box_add{
	width:100px!important;
}

</style>
</head>
<body>
<s:form name="productregistation"  id="AddProductReg_Url" action="doProductRegistation"  theme="css_xhtml" theme="simple"  method="post">
<s:hidden id="um" name="um" value="%{um}"> </s:hidden>
<s:hidden id="kcf" name="kcf" value="%{kcf}"> </s:hidden>
<s:hidden id="id" name="id" value="%{id}"> </s:hidden>
<s:hidden id="uid" name="uid" value="%{uid}"> </s:hidden>
<s:hidden id="contryid" name="contryid" value="%{contryid}"> </s:hidden>
<s:hidden id="pageFalg" name="pageFalg" value="1"> </s:hidden>



					<s:iterator value="appslist" var="BeanApps">
						 <s:set var="applicationCode" value="app_code" />
                         <s:set var="applicationName" value="app_name" />
						
						<div class="sub_field_main" style=" width:100%; float:left; padding:0px 0px 10px 0px;">
							<div style=" float:left; width:20px; text-align:center; padding-top:9px;">
								<s:checkbox  labelposition="right"  name="appcheckname"  fieldValue="%{app_code}"/>
							</div>
							<div style=" float:left; width:25%; text-indent:5px; font-size:12px; padding-top:8px; line-height:16px;"><s:property value="applicationName" /></div>
							<div class="sub_field_main" style=" width:24%; float:left;">
							<s:select 
                              id="noOfUser"
                              name="noOfUser" 
                              list="packageValue"
                              headerKey="-1"
                              headerValue="--Select Package--" 
                              cssClass="field"
                              onchange="calcluatePeriod(%{app_code},this.value);"
                              />
							</div>
							<!-- noOfUser -->
							<div id="${BeanApps.app_code}" style="display:none">
							<div style="float:left;padding:5px 10px 0px 10px;">
							<b>Validity</b>
							</div>
							<div class="sub_field_main sub_text_box_add" style=" float:left; width:12%;">
							<s:textfield name="valid_from" id="valid_from%{app_code}" readonly="true" cssStyle="width:85px;" cssClass="validity1"></s:textfield>
          				      </div>
							<div class="sub_field_main sub_text_box_add" style=" float:left; width:12%;">
							<s:textfield name="valid_to" id="valid_to%{app_code}" readonly="true" cssStyle="width:85px;" cssClass="validity1"></s:textfield>
							</div>
							<div class="sub_field_main sub_text_box_add" style=" float:left; width:12%;">
							<s:textfield name="totalCost" id="totalCost%{app_code}" readonly="true" cssStyle="width:85px;" cssClass="validity1"></s:textfield>
							</div>
							</div>
						</div>
					</s:iterator>
					<div class="pop_up_reg1"><input type="submit" name="" value="Submit" /></div>
</s:form>
</body>
</html>