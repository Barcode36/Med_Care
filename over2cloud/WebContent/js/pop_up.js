$(function(){ $(".overlay_shadow, .lightbox").hide();
 	$(".closepopup").click(function(){					 
		$(".overlay_shadow, .lightbox").fadeOut();	
	});
});

function fadePopIn()
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
		
		
		//calling ajax for products registartion at time of demo
			$.ajax({
			    type : "post",
			    url : "demoToRegular.action",
			    success : function(subdeptdata) {
		       $("#"+"ajaxDiv").html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
}