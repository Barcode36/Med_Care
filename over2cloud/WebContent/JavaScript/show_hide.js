function toggle(){
	var	tabular1				=	document.getElementById("login_click");
	var tabular2				=	document.getElementById("register_click");
	tabular1.style.display		=	"none";
	tabular2.style.display		=	"block";	
	var	sub_content1			=	document.getElementById("login");
	var	sub_content2			=	document.getElementById("register");
	sub_content1.style.display	=	"block";
	sub_content2.style.display	=	"none";
}
function toggle1(){
	var	tabular1				=	document.getElementById("login_click");
	var tabular2				=	document.getElementById("register_click");
	tabular1.style.display		=	"block";
	tabular2.style.display		=	"none";	
	var	sub_content1			=	document.getElementById("login");
	var	sub_content2			=	document.getElementById("register");
	sub_content1.style.display	=	"none";
	sub_content2.style.display	=	"block";
}