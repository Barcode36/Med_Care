function getDeptViaAjax(deptAjaxDiv)
	{
		$.ajax({
		    type : "post",
		    url : "/sfa/view/admin_pages/common_admin/DeptAjax.action?",
		    success : function(deptdata) {
	        $("#"+deptAjaxDiv).html(deptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
		
	}



function getSubDeptViaAjax(sourceAjaxDiv,destAjaxDiv,deptId)
{
	
	//alert(document.getElementById(deptId).value+"dept");
	
	var dept=document.getElementById(deptId).value;
	$.ajax({
	    type : "post",
	    url : "/sfa/view/admin_pages/common_admin/SubDeptAjax.action?dept="+dept+"&destination="+destAjaxDiv+"&source="+sourceAjaxDiv,
	    success : function(subdeptdata) {
        $("#"+sourceAjaxDiv).html(subdeptdata);
       
	},
	   error: function() {
            alert("error");
        }
	 });
	
}


function getAvailableViaAjax(fieldId,destAjaxDiv,destStatus)
{
	var fieldValue=document.getElementById(fieldId).value;
	
	document.getElementById("indicator2").style.display="block";
     $.getJSON("/sfa/view/admin_pages/common_admin/checkAvailabilty.action?searchField="+fieldValue,
		function(checkdata){
    	     $("#"+destAjaxDiv).html(checkdata.msg);
    	     document.getElementById("indicator2").style.display="none";
        	document.getElementById(destStatus).value=checkdata.status;
        
    });
    
}




function getLocationViaAjax(destLocationAjaxDiv,destBranchAjaxDiv,officeTypeId,comId)
{
	//var officeyType=$("#"+officeTypeId).val();
	
	var officeyType= $("input[name='"+officeTypeId+"']:checked").val();
    var comVal=$("#"+comId).val();
	 if(officeyType=='1' || officeyType=='2')
	 {
		 if($("#"+destLocationAjaxDiv).css('display') == 'none')
		 {
			 $("#"+destLocationAjaxDiv).show('slow');
		 }
		
		 
		 if(officeyType=='1')
		 {
			 if($("#"+destBranchAjaxDiv).css('display') == 'none')
			 {
				 $("#"+destBranchAjaxDiv).show('slow');
			 }
		 }
		 
		
		 if(officeyType=='2')
		 {
			 if($("#"+destBranchAjaxDiv).css('display') != 'none')
			 {
				 $("#"+destBranchAjaxDiv).hide('slow');
			 }
		 }
		
		 
		 
		 if($("#"+destLocationAjaxDiv).css('display') != 'none' ||$("#"+destBranchAjaxDiv).css('display') != 'none')
		 {
			 $.ajax({
				    type : "post",
				    url : "/sfa/view/admin_pages/common_admin/officeLocationAjax.action?companyId="+comVal+"&officeType="+officeyType+"&branchAjaxDiv="+destBranchAjaxDiv+"&destinationResult=radio",
				    success : function(officeLocationData) {
			        $("#"+destLocationAjaxDiv).html(officeLocationData);
				},
				   error: function() {
			            alert("error");
			        }
				 });
			 
		 }
		 
		 
	 }
	 
	  else if(officeyType=='3')
	 {
		
		 if($("#"+destLocationAjaxDiv).css('display') != 'none')
		 {
			 $("#"+destLocationAjaxDiv).hide('slow');
		 }
		 if($("#"+destBranchAjaxDiv).css('display') != 'none')
		 {
			 $("#"+destBranchAjaxDiv).hide('slow');
		 }
		  
		
	 }
	  else if(officeyType=='-1')
	 {
		
		 if($("#"+destLocationAjaxDiv).css('display') != 'none')
		 {
			 $("#"+destLocationAjaxDiv).hide('slow');
		 }
		
		 if($("#"+destBranchAjaxDiv).css('display') != 'none')
			 {
				 $("#"+destBranchAjaxDiv).hide('slow');
			 }
		 }
	
	 
}

	
	 function getBranchViaAjax(destBranchAjaxDiv,locationId,officeyTypeId)
	 {
		 if(officeyTypeId=='1')
		{
			
			 var location= $("input[name='"+locationId+"']:checked").val();
			$.ajax({
					    type : "post",
					    url : "/sfa/view/admin_pages/common_admin/officeBranchAjax.action?locationId="+location+"&officeType="+officeyTypeId+"&destBranchAjaxDiv="+destBranchAjaxDiv+"&destinationResult=radio",
					    success : function(officeBranchData) {
				        $("#"+destBranchAjaxDiv).html(officeBranchData);
					},
					   error: function() {
				            alert("error");
				        }
					 });

		}		 
				 
			 }
			 	 
	

	 function getDeptAtHeadViaAjax(destDeptAjaxDiv,officeTypeId,comId,lodgeFlag,lodgeDestDeptAjaxDiv)
	 {
	 	var officeyType=$("#"+officeTypeId).val();
	 	 var comVal=$("#"+comId).val();
	 	if(officeyType=='3')
	 	 {
	 		   $.ajax({
	 				    type : "post",
	 				    url : "/sfa/view/admin_pages/common_admin/deptAjax.action?companyId="+comVal+"&officeType="+officeyType+"&deptAjaxDiv="+destDeptAjaxDiv+"&destinationResult=radio",
	 				    success : function(officeDeptData) {
	 			        $("#"+destDeptAjaxDiv).html(officeDeptData);
	 			        
	 	
	 			  		
	 		   },
	 				   error: function() {
	 			            alert("error");
	 			        }
	 				 });
	 		 
		        if(lodgeFlag)
		        {
	 		  $.ajax({
				    type : "post",
				    url : "/sfa/view/admin_pages/common_admin/deptAjax.action?companyId="+comVal+"&officeType="+officeyType+"&deptAjaxDiv="+lodgeDestDeptAjaxDiv+"&destinationResult=radio",
				    success : function(officeDeptData) {
	 		      $("#"+lodgeDestDeptAjaxDiv).html(officeDeptData);
	 		 				
		   },
				   error: function() {
			            alert("error");
			        }
				 });
		 
		        }
	 	    }
	 }


	 function getDesgnationViaAjax(destDesgAjaxDiv,officeTypeId,comId)
	 {
	 	var officeType=$("#"+officeTypeId).val();
	 		   $.ajax({
	 				    type : "post",
	 				    url : "/sfa/view/admin_pages/common_admin/desgnationViaAjax.action?office="+officeType+"&destinationResult=radio",
	 				    success : function(desgnationData) {
	 			        $("#"+destDesgAjaxDiv).html(desgnationData);
	 			        
	 				},
	 				   error: function() {
	 			            alert("error");
	 			        }
	 				 });
	 }





	 function getDeptAtLocationViaAjax(destDeptAjaxDiv,locationId,officeTypeId,lodgeFlag,lodgeDestDeptAjaxDiv)
	 {
		if(officeTypeId=='2')
		{
			var locationType= $("input[name='"+locationId+"']:checked").val();
	 		  
			$.ajax({
	 				    type : "post",
	 				    url : "/sfa/view/admin_pages/common_admin/deptAjax.action?locationId="+locationType+"&officeType="+officeTypeId+"&deptAjaxDiv="+destDeptAjaxDiv+"&destinationResult=radio",
	 				    success : function(locationDeptData) {
	 			        $("#"+destDeptAjaxDiv).html(locationDeptData);
	 			 			
	 		   },
	 				   error: function() {
	 			            alert("error");
	 			        }
	 				 });
			
			 if(lodgeFlag)
			 {
				 $.ajax({
					    type : "post",
					    url : "/sfa/view/admin_pages/common_admin/deptAjax.action?locationId="+locationType+"&officeType="+officeTypeId+"&deptAjaxDiv="+lodgeDestDeptAjaxDiv+"&destinationResult=radio",
					    success : function(locationDeptData) {
				        $("#"+lodgeDestDeptAjaxDiv).html(locationDeptData); 			
			   },
					   error: function() {
				            alert("error");
				        }
					 });
				 
				 
			 }
			
			
			
		}    
	 }


	 function getDeptViaAjax(destDeptAjaxDiv,branchId,officeTypeId,lodgeFlag,lodgeDestDeptAjaxDiv)
	 {
	 	if(officeTypeId=='1')
	 	  {
	 		var branch=$("input[name='"+branchId+"']:checked").val();
	 		$.ajax({
	 				 type : "post",
	 				 url : "/sfa/view/admin_pages/common_admin/deptAjax.action?branchId="+branch+"&officeType="+officeTypeId+"&deptAjaxDiv="+destDeptAjaxDiv+"&destinationResult=radio&dept2AjaxDiv="+lodgeDestDeptAjaxDiv,
	 				 success : function(officeDeptData) {
	 				 $("#"+destDeptAjaxDiv).html(officeDeptData);
	 				  
	 			 				
	 		
	 		},
	 				error: function() {
	 				            alert("error");
	 				}
	 				});
	 		
	 		 if(lodgeFlag)
	 		 {
	 			$.ajax({
					 type : "post",
					 url : "/sfa/view/admin_pages/common_admin/deptAjax.action?branchId="+branch+"&officeType="+officeTypeId+"&deptAjaxDiv="+lodgeDestDeptAjaxDiv+"&destinationResult=radio",
					 success : function(officeDeptData) {
		 			$("#"+lodgeDestDeptAjaxDiv).html(officeDeptData);			
			
			},
					error: function() {
					            alert("error");
					}
					});
		 		 
	 			 
	 		 } 
	 			 
	 		
	 			
	 		
	 		}		 
	 	}


	 function getSubDeptViaAjax(destSubDeptAjaxDiv,deptId)
	 {
		var deptKeyId=$("input[name='"+deptId+"']:checked").val();
	 	$.ajax({
	 			 type : "post",
	 			 url : "/sfa/view/admin_pages/common_admin/subDeptAjax.action?dept="+deptKeyId+"&subDeptAjaxDiv="+destSubDeptAjaxDiv+"&destinationResult=radio",
	 			 success : function(officeSubDeptData) {
	 				 $("#"+destSubDeptAjaxDiv).html(officeSubDeptData);
	 				},
	 				error: function() {
	 				            alert("error");
	 				}
	 				});
	 				 
	 	}

	 
	 function showMore(hideLinkId,showLinkId,divId)
	 {

	 $("#"+hideLinkId).show('slow');
	 $("#"+divId).show('slow');
	 $("#"+showLinkId).hide('slow');	
	 }

	 function hideMore(hideLinkId,showLinkId,divId)
	 {

	 $("#"+hideLinkId).show('slow');
	 $("#"+divId).hide('slow');
	 $("#"+showLinkId).hide('slow');	
	 }

	 
	
	  
	 
