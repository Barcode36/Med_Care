function getheadoffice(country,div){
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/Location/getHeadOfficeList.action?countryId="+country,
	    success : function(data) {
	    	$('#'+div+' option').remove();
	$('#'+div).append($("<option></option>").attr("value",-1).text('Select Head Office'));
	$(data).each(function(index)
	{
	   $('#'+div).append($("<option></option>").attr("value",this.ID).text(this.NAME));
	});
	},
	   error: function() {
	        alert("error");
	    }
	 });
}
function getbranchoffice(country,div)
{
	//alert(country);
	//alert(div);
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/Compliance/Location/getBranchOfficeList.action?countryId="+country,
	    success : function(data) {
	    	$('#'+div+' option').remove();
	$('#'+div).append($("<option></option>").attr("value",-1).text('Select Branch Office'));
	$(data).each(function(index)
	{
	   $('#'+div).append($("<option></option>").attr("value",this.ID).text(this.NAME));
	});
	},
	   error: function() {
	        alert("error");
	    }
	 });
}




//merged by kadir -----checkforvalidcopy() and checkforvalid  date 10-06-2016

function checkforvalid(deptName)
{
var mapId=$("#regLevel").val();
var headofficeId=$("#headOfficeId").val();
var groupId=$("#groupId").val(); 
var regLevel=$("#regLevel").val();
var mappedOrgnztnId=$("#mappedOrgnztnId").val();

 
alert("mapId"+mapId+"headofficeId  "+headofficeId+" regLevel  "+regLevel );

	  $.ajax
	({
	type : "post",
	url  : "view/Over2Cloud/Compliance/Location/getBranchValid.action?mappedOrgnztnId="+mappedOrgnztnId+"&headofficeId="+headofficeId+"&groupId="+groupId+"&deptName="+deptName+"&regLevel="+regLevel,
	async:false,
	success : function(dataList)
	{
		if(dataList.length>0){
		 	var total=0;
			var temp="";
			
			
			if(dataList.length>0){
				var total=0;
				var temp="";
				
				console.log("  field  "+dataList[0].YES);
				if(dataList[0].YES=='All Ready Exist')
				{
					 setTimeout(function(){ $("#userStatus1").fadeIn(); }, 10);
					 setTimeout(function(){ $("#userStatus1").fadeOut(); }, 4000);
					$("#check").hide();
				
				 
				}
				else
				{
					$("#userStatus1").hide();
					 setTimeout(function(){ $("#check").fadeIn(); }, 10);
					 setTimeout(function(){ $("#check").fadeOut(); }, 4000);
				}
			 
				
		}
		}
	},
	error : function()
	{
	alert("Error on data fetch");
	} 
	}); 
	
}



// checkforvalid for add extra div
function checkforvalidcopy(deptName)
{
 
var mapId=$("#regLevel").val();
var headofficeId=$("#headOfficeId").val();
var groupId=$("#groupId").val(); 
var regLevel=$("#regLevel").val();
var mappedOrgnztnId=$("#mappedOrgnztnId").val();


	  $.ajax
	({
	type : "post",
	url  : "view/Over2Cloud/Compliance/Location/getBranchValid.action?mappedOrgnztnId="+mappedOrgnztnId+"&headofficeId="+headofficeId+"&groupId="+groupId+"&deptName="+deptName+"&regLevel="+regLevel,
	async:false,
	success : function(dataList)
	{
		if(dataList.length>0){
			var total=0;
			var temp="";
			
			if(dataList.length>0){
				var total=0;
				var temp="";
				
				//console.log("  field  "+dataList[0].YES);
				if(dataList[0].YES=='All Ready Exist')
				{
					
//					$("#status1").show();
//					$("#status").hide();
					
					 setTimeout(function(){ $("#status1").fadeIn(); }, 10);
					 setTimeout(function(){ $("#status1").fadeOut(); }, 4000);
					$("#check").hide();
				
				}
				else
				{
//					$("#status1").hide();
//					$("#status").show();
//					
					
					 setTimeout(function(){ $("#status").fadeIn(); }, 10);
					 setTimeout(function(){ $("#status").fadeOut(); }, 4000);
				}
			 
				
		}
		}
	},
	error : function()
	{
	alert("Error on data fetch");
	} 
	}); 
	
}  