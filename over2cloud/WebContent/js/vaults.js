
$(function(){ $(".overlay_shadow, .lightbox").hide();
$(".closepopup").click(function(){					 
$(".overlay_shadow, .lightbox").fadeOut();	
});
});


function sharePost(postId)
{
	var moduleFlag=document.getElementById("moduleFlag").value;
	//calling ajax for selected group view
	if(moduleFlag=='2')
	{
		alert("Can't perform share operation here");
		return false;
	}
	else
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
		$.ajax({
		    type : "post",
		    url : "/cloudapp/view/Over2Cloud/homePage/getSharingData.action?postId="+postId+"&moduleFlag="+moduleFlag,
		    success : function(subdeptdata) {
			$("#"+"ajaxDiv").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	
}
function deleteFile(postId,postDivId)
{
	var moduleFlag=document.getElementById("moduleFlag").value;
	//alert("moduleFlag"+moduleFlag+"postId"+postId);
	if(moduleFlag=='2')
	{
		alert("Can't perform delete operation here");
		return false;
	}
	else if(moduleFlag=='1')//delete from uploaded files
	{
		document.getElementById(postDivId).style.display="none";
		$.ajax({
		    type : "post",
		    url : "/cloudapp/view/Over2Cloud/homePage/deleteUserFile.action?postId="+postId,
		    success : function(subdeptdata) {
		    	document.getElementById(postDivId).style.display="none";
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
	else if(moduleFlag=='3')//delete from shared files, called up from the homePageActioncntrl.java
	{
		document.getElementById(postDivId).style.display="none";
		$.ajax({
		    type : "post",
		    url : "/cloudapp/view/Over2Cloud/homePage/deletePost.action?postId="+postId+"&tableName=submitpost&moduleFlag="+moduleFlag,
		    success : function(subdeptdata) {
		    	document.getElementById(postDivId).style.display="none";
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
}
function drawshareFiles(searchFlag)
{
	var searchedData=null;
	if(searchFlag!='')
	{
		searchedData=document.getElementById("searchString").value;
	}
	var moduleFlag=document.getElementById("moduleFlag").value;
	$.ajax({
	    type : "post",
	    url : "/cloudapp/view/Over2Cloud/homePage/ajaxVaultsDesign.action?moduleFlag="+moduleFlag+"&tempFalg=1&searchedData="+searchedData,
	    success : function(subdeptdata) {
		$("#"+"autoAjaxDraw").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}