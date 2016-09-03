
$(function(){ $(".overlay_shadow, .lightbox").hide();
$(".closepopup").click(function(){					 
$(".overlay_shadow, .lightbox").fadeOut();	
});
});


function viewFullgroupMembers(grpID)
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
	//calling ajax for selected group view
	$("#"+"ajaxDiv").html("");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/homePage/viewAllMembersOfGroup.action?grpID="+grpID,
	    success : function(subdeptdata) {
	    	$("#"+"ajaxDiv").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function deleteGroupMember(memberId,divId)
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/homePage/removeMemberFromGroup.action?grpMappedID="+memberId,
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

function searchGroup()
{
	var searchedData=searchedData=document.getElementById("searchString").value;
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/homePage/beforeAllGroups.action?searchedData="+searchedData,
	    success : function(subdeptdata) {
		$("#"+"homePageDivId").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function fillMembersData()
{
	$("#"+"userGrp").html("");
	$("#"+"mappedData").html("");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/homePage/userGroups.action",
	    success : function(subdeptdata) {
       $("#"+"userGrp").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function getUnMappedDataBased(grpId)
{
	 $("#"+"mappedData").html("");
	var mappingTypeSelected=document.getElementById("mappingTypeText").value;
	if(mappingTypeSelected!='-1' && mappingTypeSelected!='' && grpId!='-1' && grpId!='')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/homePage/getUnMappedData.action?mappingTypeSelected="+mappingTypeSelected+"&grpId="+grpId,
		    success : function(subdeptdata) {
	       $("#"+"mappedData").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
		
	}
}

function getUnMappedDataBasedMappe(mappingTypeSelected)
{
	 $("#"+"mappedData").html("");
	var grpId=document.getElementById("grpNameToBemapped").value;
	if(mappingTypeSelected!='-1' && mappingTypeSelected!='' && grpId!='-1' && grpId!='')
	{
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/homePage/getUnMappedData.action?mappingTypeSelected="+mappingTypeSelected+"&grpId="+grpId,
		    success : function(subdeptdata) {
	       $("#"+"mappedData").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
		
	}
}
function fillGroups()
{
	$("#"+"userGrpView").html("");
	$("#"+"userGrpViewData").html("");
	
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/homePage/fillGroups.action",
	    success : function(subdeptdata) {
       $("#"+"userGrpView").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function getMappedData(grpId,to,divId,drawDivId)
{
	if(divId!='')
	 {
		$("#"+divId).html("");
	 }
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/homePage/fillGroupsData.action?grpId="+grpId+"&to="+to,
	    success : function(subdeptdata) {
       $("#"+drawDivId).html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
	 if(divId!='')
	 {
		 document.getElementById(divId).style.display="none";
	 }
}