<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Over2cloud</title>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<style type="text/css"></style>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/style2.css" rel="stylesheet" type="text/css"/>
<link href="css/dcmegamenu.css" rel="stylesheet" type="text/css" />
<link href="css/skins/black.css" rel="stylesheet" type="text/css" />
<link href="css/notification.css" rel="stylesheet" type="text/css" />
<link href="css/colorbox.css" rel="stylesheet" type="text/css" media="all" />
<script type='text/javascript' src='js/jquery.min.js'></script>
<script type='text/javascript' src='js/jquery.hoverIntent.minified.js'></script>
<script type='text/javascript' src='js/jquery.dcmegamenu.1.3.3.js'></script>
<script type="text/javascript" src="js/menucontrl.js"></script>
<script type="text/javascript" src="js/WFPM/menucontrl_WFPM.js"></script>
<script type="text/javascript" src="js/communication/communication.js"></script>
<script type="text/javascript">


$(document).ready(function($){
		$('#mega-menu').dcMegaMenu({
		      classParent: 'dc-mega',    // Allows you to change the class given to the parent li tags
		        classParent: 'dc-mega',
		    classContainer: 'sub-container',    // Allows you to change the class given to the mega sub-menu container
		    classSubLink: 'mega-hdr', // Set class given to mega menu parent menu items
		    rowItems: 3, // Number of sub-menus in each row
		    speed: 'fast',  // Speed of drop down animation
		        effect: 'slide',  // Type of drop down animation - 'slide' or 'fade'
		        event: 'hover', // Use either 'hover' or 'click'
		        fullWidth: false
	});
});

function bugReporting()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "reportABug.action",
		    success : function(data) {
	       		$("#data_part").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	
	}
</script>

<!-- Customised select box ends here-->
<script type="text/javascript">
$(document).ready(function(){
//******************************************** MyAccount Information *****************************************************

 $("#accountMgmtView").click(function(){
   var conP = '<%=request.getContextPath()%>';
	 $("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/AccountMgmt/AccountInfo.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});


 $("#accountMgmtResponce").click(function(){
 var conP = '<%=request.getContextPath()%>';
	 $("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/AccountMgmt/ClientServerResponce.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});





//******************************************** End MyAccount Information *****************************************************

//******************************************** Pack Information *****************************************************

 $("#addpackConfiguration").click(function(){
 var conP = '<%=request.getContextPath()%>';
	 $("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/Setting/packAdd.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});

 $("#deletepackRegistration").click(function(){
 var conP = '<%=request.getContextPath()%>';
	 $("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/Setting/packView.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});


//******************************************** End pack Information *****************************************************









//******************************************** Industry Information *****************************************************

 $("#addIndustry").click(function(){
 var conP = '<%=request.getContextPath()%>';
	 $("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/Setting/industryAdd.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
 $("#deleteIndustry").click(function(){
 var conP = '<%=request.getContextPath()%>';
	 $("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/Setting/deleteIndustry.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});

 




//******************************************** End Industry Information *****************************************************




//******************************************** Job Information *****************************************************

 $("#addjob").click(function(){
 var conP = '<%=request.getContextPath()%>';
	 $("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/Setting/JobAdd.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
 $("#deleteJob").click(function(){
 var conP = '<%=request.getContextPath()%>';
	 $("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/Setting/jobdelete.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});

 




//******************************************** Job Industry Information *****************************************************

















//******************************************** MyCloud Information Attach For All Client*****************************************************
  
  
  
  $("#onlineRegistationDetilsView").click(function(){
    var conP = '<%=request.getContextPath()%>';
	  alert("conP"+conP);
	  $("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/MycloudReport/onlineRegistationDetailsView.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});


  $("#onlineRegistationDetilsBlock").click(function(){
  var conP = '<%=request.getContextPath()%>';
	  $("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/MycloudReport/onlineRegistationDetailsBlock.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});


  $("#onlineAssociateDetilsView").click(function(){
  var conP = '<%=request.getContextPath()%>';
	  $("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/MycloudReport/onlineAssociateDetilsView.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});

  $("#onlineAssociateDetilsBlock").click(function(){
  var conP = '<%=request.getContextPath()%>';
	  $("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/MycloudReport/onlineAssocaiateDetailsBlock.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});

  $("#onlineOrgnizationalDetilsView").click(function(){
  var conP = '<%=request.getContextPath()%>';
	  $("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/MycloudReport/onlineOrgnizationalDetilsView.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});

  $("#onlineOrgnizationalDetilsBlock").click(function(){
  var conP = '<%=request.getContextPath()%>';
	  $("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/MycloudReport/onlineOrgnizationalDetailsBlock.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});





	

//*******************************************************************************************************************************************

// ******************************************Registation Part Start ***************************************************************************************************************************************



	$("#registation_patner").click(function(){
	var conP = '<%=request.getContextPath()%>';
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/Registation/registationPatner.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	$("#productConfirmation").click(function(){
	var conP = '<%=request.getContextPath()%>';
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/Registation/productConfirmation.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});

	$("#ourclient").click(function(){
	var conP = '<%=request.getContextPath()%>';
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/Registation/ourClientAction.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
   

	$("#blockClientAssociate").click(function(){
	var conP = '<%=request.getContextPath()%>';
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/Registation/blockClientasso.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});

// *******************************************************************************************************************************************************************************************************

	$("#MailConfiguration").click(function(){
	var conP = '<%=request.getContextPath()%>';
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/Setting/MailConfig.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	
	$("#RegistrationSpaceConfiguration").click(function(){
	var conP = '<%=request.getContextPath()%>';
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/Setting/serverconfigSpace.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
	

 $("#chunkDbAdd").click(function(){
 var conP = '<%=request.getContextPath()%>';
	 $("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url :  conP+"/view/Over2Cloud/Setting/chunkdbAction.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});

 $("#blockChunkSpaceConfig").click(function(){
 var conP = '<%=request.getContextPath()%>';
	 $("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url :  conP+"/view/Over2Cloud/Setting/blockChunkSpaceConfig.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});
 
	
$("#clientdbAdd").click(function(){
var conP = '<%=request.getContextPath()%>';
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/Setting/clientdbInfo.action",
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	});

$("#clientdbView").click(function(){
var conP = '<%=request.getContextPath()%>';
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : conP+"/view/Over2Cloud/Setting/clientdbViewBefore.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});


$("#deleteClientdb").click(function(){
var conP = '<%=request.getContextPath()%>';
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : conP+"/view/Over2Cloud/Setting/DeleteClientdbRecord.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});


$("#clientsingleSpacereg").click(function(){
var conP = '<%=request.getContextPath()%>';
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : conP+"/view/Over2Cloud/Setting/clientsingleSpacereg.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});

$("#viewApplicationRegistration").click(function(){
var conP = '<%=request.getContextPath()%>';
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : conP+"/view/Over2Cloud/Setting/viewApplicationRegistration.action",
	    data : "editUrl=false&deleteUrl=false",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});

$("#modifyApplicationRegistration").click(function(){
var conP = '<%=request.getContextPath()%>';
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : conP+"/view/Over2Cloud/Setting/viewApplicationRegistration.action",
	    data : "editUrl=true&deleteUrl=false",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});

$("#deleteApplicationRegistration").click(function(){
var conP = '<%=request.getContextPath()%>';
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : conP+"/view/Over2Cloud/Setting/viewApplicationRegistration.action",
	    data : "editUrl=false&deleteUrl=true",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});

$("#viewCSC").click(function(){
var conP = '<%=request.getContextPath()%>';
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : conP+"/view/Over2Cloud/Setting/viewCSC.action",
	    data : "editUrl=false&deleteUrl=false",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});

$("#modifyCSC").click(function(){
var conP = '<%=request.getContextPath()%>';
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : conP+"/view/Over2Cloud/Setting/viewCSC.action",
	    data : "editUrl=true&deleteUrl=false",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});

$("#deleteCFC").click(function(){
var conP = '<%=request.getContextPath()%>';
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : conP+"/view/Over2Cloud/Setting/viewCSC.action",
	    data : "editUrl=false&deleteUrl=true",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});



$("#blockSingleSpace").click(function(){
var conP = '<%=request.getContextPath()%>';
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : conP+"/view/Over2Cloud/Setting/blockSinglespace.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});


	

$("#addAplicationConfiguration").click(function(){
var conP = '<%=request.getContextPath()%>';
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : conP+"/view/Over2Cloud/Setting/addApplicationcontact.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
});

//demo account client DB configuration
//view/Over2Cloud/Setting/clientsingleSpacereg.action
$("#configureDemoSpace").click(function(){
var conP = '<%=request.getContextPath()%>';
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : conP+"/view/Over2Cloud/Setting/serverconfigSpaceDemo.action",
	    success : function(subdeptdata) {
     $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
          alert("error");
      }
	 });
});
$("#myaccountpages").click(function(){
    var conP = '<%=request.getContextPath()%>';
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	var url = conP+"/view/common_pages/profilepage.action";
	$.ajax({
	    type : "post",
	    url :url ,
	    success : function(subdeptdata) {
     $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
          alert("error");
      }
	 });
});
$("#settingpage").click(function(){
    var conP = '<%=request.getContextPath()%>';
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
	var url = conP+"/view/common_pages/settingpage.action";
	$.ajax({
	    type : "post",
	    url :url ,
	    success : function(subdeptdata) {
     $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
          alert("error");
      }
	 });
});

});
</script>
<script type="text/javascript">
	function showlayer(layer){
		var myLayer = document.getElementById(layer).style.display;
		if(myLayer=="none"){
			document.getElementById(layer).style.display="block";
		} else {
			document.getElementById(layer).style.display="none";
		}
	}

	function viwCentreDiv()
	{
			$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/newsAlertsConfig/showNewsHomePage.action",
			    success : function(subdeptdata) {
		       $("#"+"data_part").html(subdeptdata);
			},
			   error: function() {
		            alert("error");
		        }
			 });
	}
</script>
<style type="text/css">

ul.sub_drop_down{
	display:none;
}
ul.sub_drop_down li{
	color:#a13812; text-decoration:none; background:url(images/main_menu_bg_hover.png) repeat-x; border-left:1px solid #e6e6e5;
	padding-left:0px;
}
ul.sub_drop_down li a{
	text-indent:30px;
}
ul.sub_drop_down li b{
	background: url(images/main_menu_list.png) no-repeat scroll left 5px transparent;
    font-weight: normal;
    padding-left: 20px;
}

.testForDiv
{
	font-size: 9px;
	font-family: Tahoma,Arail,Times New Roman;
	color: #000000;
	font-size: 1.0em;
}
</style>
</head>
<body>
<div class="maincontainer">
<s:include value="/topClient.jsp"></s:include>
<div class="clear"></div>
<div class="middle-content" id="data_part"> 
	
	<br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
	<div class="contentdiv-small" style="align:center;overflow: hidden;width:50%;height:100%;background-color:#E0ECF8;margin-top:10px;margin-left:250px">
		<div class="testForDiv">
		<br /><br />
	<b>Dear <s:property value="#session['empName']" />,</b>
<br /><br />
Welcome to the Enterprise Productivity Management Application offered over Public and Private Cloud !
<br /><br />
You may start using the application based on the access rights alloted to you by the available tabs on the header.
<br /><br />
Please note you may give us your valuable <a href="reportABug.action"  class="reportBug">feedback</a> or <a href="seekAssistence.action"  class="booking">seek assistance</a> at any point of time by clicking on the links available in the footer.
	<br /><br /><br />
	
	<!--<img src="images/paperFeed.jpg" alt="Hello" align="top" />
	Hi
		--></div>
	</div>
</div>
<div class="clear"></div>
<div class="container_block">

   <div class="footer"  style="height: 15px;">
  
		<div class="copyrights">
		 <div class="logo1" style="float: left;"><a href="http://www.over2cloud.com/" target="new"><img src="images/logo.png" style="height: 20px;"/></a></div>
			<div class="logo1" style="float: right;"><a href="http://www.dreamsol.biz/" target="new"><!--<img src="images/dreamsol.png" style="height: 20px;"/> --></a></div>
				<ul>
					<li><a href="reportABug.action"  class="reportBug">Report a Bug</a></li>
					<li><a href="viewNewsandalerts.action"  class="newsAlerts">News And Alerts</a></li>
					<li><a href="seekAssistence.action"  class="booking1111">Seek Assistance</a></li>
				</ul>
			</div>
		</div>
	</div>
</div>

</body>
<script type='text/javascript' src='js/jquery4.js'></script>
<script type='text/javascript' src='js/jquery.colorbox.js'></script>
<script>
			$(document).ready(function(){
				//Examples of how to assign the ColorBox event to elements
				$(".reportBug").colorbox({rel:'booking', transition:"elastic",width:"50%",height:"60%"});
								
			});

			$(document).ready(function(){
				//Examples of how to assign the ColorBox event to elements
				$(".newsAlerts").colorbox({rel:'booking', transition:"elastic",width:"80%",height:"70%"});
								
			});

			$(document).ready(function(){
				//Examples of how to assign the ColorBox event to elements
				$(".booking1111").colorbox({rel:'booking', transition:"elastic",width:"50%",height:"60%"});
								
			});
</script>
</html>