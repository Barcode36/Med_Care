function allConversasionView(to,divID,hideDiv,searchStatus)
{
	var searchedData=null;
	if(searchStatus!='')
	{
		searchedData=searchedData=document.getElementById("searchString").value;
	}
	//alert(searchedData);
	$.ajax({
	    type : "post",
	    url : "/cloudapp/view/Over2Cloud/homePage/beforeViewMsg.action?to="+to+"&searchedData="+searchedData,
	    success : function(subdeptdata) {
       $("#"+divID).html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
	 if(hideDiv!='')
	 {
		 document.getElementById(hideDiv).style.display="none";
	 }
}

function removeFullConversasion(userName,empId,divIdHide)
{
	$.ajax({
	    type : "post",
	    url : "/cloudapp/view/Over2Cloud/homePage/deleteFullConversasion.action?userNameMsg="+userName+"&empIdForDelete="+empId,
	    success : function(subdeptdata) {
	    	if(divIdHide!='')
		   	 {
		   		 document.getElementById(divIdHide).style.display="none";
		   	 }
	},
	   error: function() {
            alert("error");
        }
	 });
	
}
$(function(){ $(".overlay_shadow, .lightbox").hide();
$(".closepopup").click(function(){					 
$(".overlay_shadow, .lightbox").fadeOut();	
});
});


function viewFullConversasion(userName,empId)
{
	
	$(".lightbox").fadeOut();
	$(".overlay_shadow").fadeIn();
	var i = $(".lhsclick").index(this);		
	$(".lightbox").eq(i).fadeIn();
	var lhtbox = $(".lightbox");
	var cenlightbox = $(".lightbox");		
	var height = $(window).height();
	var width = $(window).width();
	var left= width/2 - (cenlightbox.width()/2);
	var top = height/2 - (cenlightbox.height()/2);
	
	cenlightbox.css({'left' : left , 'top' : top});	
	
	
	//calling ajax for selecteduserview at time
	//alert("userName"+userName+"empId"+empId);
	$("#"+"ajaxDiv").html("");
	$.ajax({
	    type : "post",
	    url : "/cloudapp/view/Over2Cloud/homePage/viewFullConvOfSingle.action?userNameMsg="+userName+"&empIdForDelete="+empId,
	    success : function(subdeptdata) {
	    	$("#"+"ajaxDiv").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function removeMsg(msgID,divId)
{
	$.ajax({
	    type : "post",
	    url : "/cloudapp/view/Over2Cloud/homePage/deleteMsg.action?msgID="+msgID,
	    success : function(subdeptdata) {
	    	if(divId!='')
		   	 {
		   		 document.getElementById(divId).style.display="none";
		   	 }
	},
	   error: function() {
            alert("error");
        }
	 });
}