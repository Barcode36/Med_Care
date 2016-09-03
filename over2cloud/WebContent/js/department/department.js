function checkDeptAvailability(dept,div)
{
	
	var mapId=$("#mappedOrgnztnId").val();
	var deptName=$("#"+dept).val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/commonModules/beforeDepartmentExistsSearch.action?selectedorgId="+mapId+"&deptName="+deptName,
	    success : function(subdeptdata) {
       $("#"+div).html(subdeptdata);
       setTimeout(function(){ $("#msg_div").fadeOut(); }, 8000);
       
	},
	   error: function() {
            alert("error");
        }
	 });
}
function getDeptBySubGroup(dropDownId,value)
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/hr/getDeptBySubGroupData.action",
	    data : "selectedId="+value,
	    success : function(data) 
	    {
			$('#'+dropDownId+' option').remove();
			$('#'+dropDownId).append($("<option></option>").attr('value',-1).text('Select Department'));
			$(data).each(function(index)
			{
			   $('#'+dropDownId).append($("<option></option>").attr("value",this.id).text(this.name));
			});
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}