function addHindiInstantmsg()
{
	
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/>Loading...</center>");
	$.ajax({
	    type : "post",
	    url  : "view/Over2Cloud/CommunicationOver2Cloud/Comm/beforeHindiInstantMessageAdd.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });

	}

function hindiInstantReport(){
$.ajax({
    type : "post",
    url :"view/Over2Cloud/CommunicationOver2Cloud/Comm/instantHindiSearchReport.action",
    success : function(subdeptdata) {
   $("#"+"data_part").html(subdeptdata);
},
   error: function() {
        alert("error");
    }
 });
}