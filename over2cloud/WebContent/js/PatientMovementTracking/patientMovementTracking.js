getGridView();
getStatusCounter();
function getGridView()
{
	var total=0;
	 var temp="0";
	$.ajax({
 		type :"post",
 		url :"view/Over2Cloud/PatientMovementTracking/viewActivityBoardDetail.action",
		data: "fromDate="+$('#fromDate').val()+"&toDate="+$('#toDate').val()+"&location="+$('#location_search').val()+"&nursing="+$('#nursing_search').val(),
 		success : function(data) 
	    {
			$("#middleDiv").html(data);
			 
			 
		},
	    error: function()
	    {
	        alert("error viewLinsence");
	    }
 	}); 	
}
function UpdateDropFilters ()
{

$.ajax({
    type : "post",
    url : "view/Over2Cloud/PatientMovementTracking/fetchFilterNurshingUnit.action?fromDate="+$('#fromDate').val()+"&toDate="+$('#toDate').val(),
    async:false,
    success : function(feeddraft) {
	if($("#nursing_search").val()=='-1')
	{
$("#nursing_search option").remove();
$('#nursing_search').append($("<option></option>").attr("value",-1).text("Select Nursing Unit"));
 $(feeddraft).each(function(index)
{
   $("#nursing_search").append($("<option></option>").attr("value",this.id).text(this.name));
});
}  
},
   error: function() {
            alert("error");
        }
 });

$.ajax({
    type : "post",
    url : "view/Over2Cloud/PatientMovementTracking/fetchFilterLocation.action?fromDate="+$('#fromDate').val()+"&toDate="+$('#toDate').val(),
    success : function(feeddraft) {
	if($("#location_search").val()=='-1')
	{
    	$('#location_search option').remove();
    	$('#location_search').append($("<option></option>").attr("value",-1).text("Select Location"));
    	$(feeddraft).each(function(index)
{
   $('#location_search').append($("<option></option>").attr("value",this.id).text(this.name));
});
}
},
   error: function() {
            alert("error");
        }
 });

}

 
function getStatusCounter()
{
	$.ajax
	({
	type : "post",
	url : "view/Over2Cloud/PatientMovementTracking/fetchCounterStatus.action?fromDate="+$('#fromDate').val()+"&toDate="+$('#toDate').val(),
	async:false,
	success : function(data)
	{
	if(data.length>0){
	var total=0;
	$("#statusCounter").empty();
	var temp="";
	if(data[0].Total>0)
	{
		temp+='<div class="TitleBorder"><h1>Total Patient</h1><div class="circle">'+data[0].Total+'</div></div>';
	}
	 
	$("#statusCounter").append(temp);
	}
	
	},
	error : function()
	{
	alert("Error on data fetch");
	} 
	}); 
	
}