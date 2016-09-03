<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
function resetForm(formone)
{
	$('#'+formone).trigger("reset");
}

	var radioValue = 'kpi';
	function getObj(id) {
		return document.getElementById(id);
	}

	jQuery.struts2_jquery.require( [
			"js/base/jquery.ui.widget" + jQuery.struts2_jquery.minSuffix
					+ ".js",
			"js/base/ jquery.ui.datepicker" + jQuery.struts2_jquery.minSuffix
					+ ".js" ]);
	if (jQuery.struts2_jquery.local != "en") {
		jQuery.struts2_jquery.require("i18n/jquery.ui.datepicker-"
				+ jQuery.struts2_jquery.local + ".min.js");
	}
	var datePick = function(elem) {
		$(elem).datepicker( {
			dateFormat :'mm-yy'
		});
		jQuery(elem).datepicker();
		jQuery('#ui-datepicker-div').css("z-index", 2000);
	};

	$.subscribe('complete', function(event, data) {
		setTimeout( function() {
			$("#foldeffect").fadeIn();
		}, 10);
		setTimeout( function() {
			$("#foldeffect").fadeOut();
		}, 4000);

		$('input:radio[name=targetOn][value='+radioValue+']').attr('checked', true);
		$('radio').find('option:first').attr('selected', 'selected');
	});

	
	var totalTarget = 0;
	var remVal=0;
	//var remTarget = 0;
	function setTotalTarget(total)
	{
		//alert(total);
		totalTarget = total;
		//remVal = totalTarget;
		//$("#targetVal").text("Remaining Value : "+ totalTarget);
		//remTarget = 0;
	}
	
	var valPer=100;
	function setOfferingTargetValue(target, id, textId)
	{
		//alert("target : "+ target);
		if(target.toString() == "")
		{
			$('#'+id).val("");
			return;
		}
		var remTarget = 0;
		var v = $(".abc");
		var i;

		if(/^[a-zA-Z0-9- ]*$/.test(target) == false) {
		    alert('Your search string contains illegal characters.');
		    return;
		}
		if(totalTarget != 0)
		{
			if(target != null && target != "")
			{
				$('#'+id).val((totalTarget*target)/100);

				for(i=0; i<v.length; i++)
				{
					if(v[i].value != "" && v[i].value != null)
					{
						remTarget += parseInt(v[i].value);
					}
				}
				if(remTarget >100)
				{
					//alert(" Target overflow ");
					var val = $("#"+textId).val();
					target = val.substring(0, (val.length)-1);
					$("#"+textId).val(target);
					$('#'+id).val((totalTarget*target)/100);
					
					return false;
				}

				remVal = (remVal + parseInt($('#'+id).val()));
				
				if(valPer > 0)
				{
					$("#targetPre").text("Remaining Target : "+ parseInt(valPer-remTarget)  +"%");
					//$("#targetVal").text("Remaining Value : "+ remVal);
				}
			}
		}
		else
		{
			alert("Please enter total target");
			$(".abc").val("");
		}
		
	}

	$("#selectall").ready(function () {
    	$('.select').attr('checked',true);
	});
 function selectAllchk(val){
   if(val.checked == true)
	   {
	   $('[name=weightage]').show(); 
	   $('[name=deptID]').attr('checked',true);
	   }
   
   else{
	   $('[name=weightage]').hide();
	   $('[name=deptID]').attr('checked',false);
       }
	} 
	
	function viewClient()
	{
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/target/beforeTargetView.action",
	    success : function(subdeptdata) {
	   $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
	        alert("error");
	    }
	 });
	}


$.subscribe('validate', function(event,data)
{
	if($("#empName").val() == null || $("#empName").val() == "" ||  $("#empName").val() == "-1")
	{
		 $("#errZone").html("<div class='user_form_inputError2'>Please Select One Employee !</div>");	
         setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
         setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		 event.originalEvent.options.submit = false;
		 return;
	}

	var selVal = $("input[name='targetOn']:checked").val();
	if(selVal == null || selVal == "")
	{
		 $("#errZone").html("<div class='user_form_inputError2'>Please Select Target On !</div>");	
         setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
         setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		 event.originalEvent.options.submit = false;
		 return;
	}
	if($("#targetMonth").val() == null || $("#targetMonth").val() == "")
	{
		 $("#errZone").html("<div class='user_form_inputError2'>Please Select Month !</div>");	
         setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
         setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		 event.originalEvent.options.submit = false;
		 return;
	}

});
	
</script>
<SCRIPT type="text/javascript">
function selectMe(me,one,two)
{
	if(me.checked)
	{
		$("#"+one+two).show();
		$("#"+two).show();
		
	}
	else
	{
		$("#"+one+two).hide();
		$("#"+two).hide();
	}
}

</SCRIPT>
<STYLE type="text/css">
	/*new Table CSS for all table view added by sandeep */
 
	*{
		padding:0px; margin:0px;
	}
	td.tabular_head{
		color:#ffffff; text-indent:25px; font-size:13px; line-height:30px; font-weight:400; border-radius:5px 5px 0px 0px; -webkit-border-radius:5px 5px 0px 0px; -moz-border-radius:5px 5px 0px 0px;
		border-bottom:1px solid #e7e9e9;
	}
	td.footer_block{
		color:#ffffff; text-indent:25px; font-size:13px; line-height:30px; font-weight:400; border-radius:0px 0px 5px 5px; -webkit-border-radius:0px 0px 5px 5px; -moz-border-radius:0px 0px 5px 5px;
	}
	td.footer_block td{
		font-size:13px; line-height:30px; font-weight:400; border-right:1px solid #e7e9e9; padding:0px 0.5% 0px 1%;
	}
	td.tabular_cont td{
		 font-size:13px; line-height:30px; font-weight:400; border-right:1px solid #e7e9e9; padding:0px 0 0px 1%;
	}
</STYLE>
</head>
<br><body>
	
	
			<div class="clear"></div>
				
				<div class="clear"></div>
				<table width="100%" cellspacing="0" cellpadding="3">
				<!-- <tr>
					<td bgcolor="#e7e9e9" class="tabular_head" valign="middle"
						colspan="7" style="color: black">Assign Weightage</td>
				</tr> -->
				<tr>
					<td bgcolor="#e7e9e9"
						style="border-bottom: 1px solid #e7e9e9; color: #000000;"
						valign="top" class="tabular_cont">
					<table cellspacing="0" cellpadding="0" width="100%">
						<tbody>
							<tr>
								<td width="7.5%" align="center">S. No.</td>
								<td valign="middle" width="2.5%">
								 <s:checkbox name="selectAll" 
								    theme="simple"
									id="selectall"
									cssClass="select"
									fieldValue="%{key}" 
									name="empName"
									onclick="selectAllchk(this);"
									 /> 
								 </td>
								<td valign="middle" width="45%">Email Id</td>
								<td valign="middle" width="45%">Name</td>
							</tr>
						</tbody>
					</table>
					</td>
				</tr>
				<%-- mapOfferingList:<s:property value="mapOfferingList.size()"/> --%>
				<s:iterator value="locationMasterMap" id="locationMasterMap" status="counter">
					<s:if test="#counter.count%2 != 0">
						<tr>
							<td bgcolor="#ffffff"
								style="border-bottom: 1px solid #e7e9e9; color: #181818;"
								valign="top" class="tabular_cont">
								
<table cellspacing="0" cellpadding="0" width="100%">
	<tbody>
		<tr>
		
		
		     <td valign="middle" width="7.5%" bgcolor="#ffffff"
									style="color: #000000; text-align: center;" >
									<s:property value="#counter.count"/>
			 </td>
		
			<td width="2.5%"><s:checkbox cssClass="select"
				fieldValue="%{key}" name="emailIdOne" theme="simple" 
				onclick="selectMe(this,'targetInPr','%{#counter.count}');" />
			</td>
			
			<td valign="middle" width="45%"><s:property
				value="%{key}" />
		    </td>
			
			<td valign="middle" width="45%"><s:textfield
				id="%{#counter.count}" name="empName" value="%{value}"  
				placeholder="Enter Name" cssClass="textField" readonly="false"
				theme="simple"></s:textfield>
			</td>
		</tr>
	</tbody>
</table>
							</td>
						</tr>
					</s:if>
					<s:else>
					<tr>
							<td bgcolor="#e2e4e4"
								style="border-bottom: 1px solid #e7e9e9; color: #181818;"
								valign="top" class="tabular_cont">
								
<table cellspacing="0" cellpadding="0" width="100%">
	<tbody>
		<tr>
		
		
		<td valign="middle" width="7.5%" bgcolor="#e2e4e4"
									style="color: #000000; text-align: center;" ><s:property
						      value="#counter.count"/>
			 </td>
			 
			<td width="2.5%"><s:checkbox cssClass="select"
				fieldValue="%{key}" name="emailIdOne" theme="simple" 
				onclick="selectMe(this,'targetInPr','%{#counter.count}');" />
			</td>
			
			<td valign="middle" width="45%"><s:property
				value="%{key}" />
		    </td>
			
			<td valign="middle" width="45%"><s:textfield
				id="%{#counter.count}" name="empName" value="%{value}" 
				 cssClass="textField" readonly="false"
				theme="simple"></s:textfield>
			</td>
		</tr>
	</tbody>
</table>
							</td>
						</tr>
			
				</s:else>
				</s:iterator>
				
			</table><br/>
			<div class="clear"></div>
			<!-- Buttons -->
			<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				
			<center><sj:div id="foldeffect" effect="fold">
				<div id="Result1"></div>
			</sj:div></center>
	
</body>
</html>