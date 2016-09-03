$.subscribe('beforeFirstAccordian', function(event,data)
        {
          setTimeout(function(){ $("#foldeffect1").fadeIn(); }, 10);
          setTimeout(function(){ $("#foldeffect1").fadeOut(); }, 4000);
          $('select').find('option:first').attr('selected', 'selected');
          $("#ButtonDiv").show();
          resetForm('formone');
          onloadData();
        });

$.subscribe('beforeSecondAccordian', function(event,data)
        {
          setTimeout(function(){ $("#foldeffect2").fadeIn(); }, 10);
          setTimeout(function(){ $("#foldeffect2").fadeOut(); }, 4000);
          $('select').find('option:first').attr('selected', 'selected');
        });

$.subscribe('beforeThirdAccordian', function(event,data)
        {
          setTimeout(function(){ $("#foldeffect3").fadeIn(); }, 10);
          setTimeout(function(){ $("#foldeffect3").fadeOut(); }, 4000);
          $('select').find('option:first').attr('selected', 'selected');
        });

//function getFeedTypeEmployee111(subDeptId,destAjaxDiv1,destAjaxDiv2) {
function commonSelectAjaxCall(table,selectVar_One,selectVar_Two,conditionVar_Key,conditionVar_Value,conditionVar_Key2,conditionVar_Value2,order_Key,order_Value,targetid,frontVal) {
	var module=$("#"+conditionVar_Value2).val();
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/commonModules/commonSelectMethod.action?table="+ table+"&selectVar_One="+selectVar_One+"&selectVar_Two="+selectVar_Two+"&conditionVar_Key="+conditionVar_Key+"&conditionVar_Value="+conditionVar_Value+"&conditionVar_Key2="+conditionVar_Key2+"&conditionVar_Value2="+module+"&order_Key="+order_Key+"&order_Value="+order_Value,
		success : function(empData){
		$('#'+targetid+' option').remove();
		$('#'+targetid).append($("<option></option>").attr("value",-1).text(frontVal));
    	$(empData).each(function(index)
		{
		   $('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.name));
		});
	    },
	    error : function () {
			alert("Somthing is wrong to get Data");
		}
	});
  }

//function getFeedTypeEmployee111(subDeptId,destAjaxDiv1,destAjaxDiv2) {
function getServiceDept(table,selectVar_One,selectVar_Two,conditionVar_Key,conditionVar_Value,conditionVar_Key2,conditionVar_Value2,order_Key,order_Value,targetid,frontVal) {
	//alert("module  "+conditionVar_Value2);
	$.ajax
	 (
	   {
		type :"post",
		url :"/over2cloud/view/Over2Cloud/commonModules/getServiceDept.action?table="+ table+"&selectVar_One="+selectVar_One+"&selectVar_Two="+selectVar_Two+"&conditionVar_Key="+conditionVar_Key+"&conditionVar_Value="+conditionVar_Value+"&conditionVar_Key2="+conditionVar_Key2+"&conditionVar_Value2="+conditionVar_Value2+"&order_Key="+order_Key+"&order_Value="+order_Value,
		success : function(empData){
		$('#'+targetid+' option').remove();
		$('#'+targetid).append($("<option></option>").attr("value",-1).text(frontVal));
    	$(empData).each(function(index)
		{
		   $('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.name));
		});
	    },
	    error : function () {
			alert("Somthing is wrong to get Data");
		}
	   }
	 );
  }

//function getFeedTypeEmployee111(subDeptId,destAjaxDiv1,destAjaxDiv2) {
function commonSelectAjaxCall2(table,selectVar_One,selectVar_Two,conditionVar_Key,conditionVar_Value,conditionVar_Key2,conditionVar_Value2,order_Key,order_Value,targetid,frontVal) {
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/commonModules/commonSelectMethod.action?table="+ table+"&selectVar_One="+selectVar_One+"&selectVar_Two="+selectVar_Two+"&conditionVar_Key="+conditionVar_Key+"&conditionVar_Value="+conditionVar_Value+"&conditionVar_Key2="+conditionVar_Key2+"&conditionVar_Value2="+conditionVar_Value2+"&order_Key="+order_Key+"&order_Value="+order_Value,
		success : function(empData){
		$('#'+targetid+' option').remove();
		$('#'+targetid).append($("<option></option>").attr("value",-1).text(frontVal));
    	$(empData).each(function(index)
		{
		   $('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.name));
		});
	    },
	    error : function () {
			alert("Somthing is wrong to get Data");
		}
	});
  }

function resetForm(formId)
{
	$('#'+formId).trigger("reset");
}