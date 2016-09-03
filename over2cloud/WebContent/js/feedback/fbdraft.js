var feedTypeFlag;
var feedCatgFlag;
var feedSCatgFlag;
var from;


function getFedbckCategory(deptId,destAjaxDiv)
{
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/Feedback_Over2Cloud/Feedback_Draft/catgryViaAjax.action?destination="+ destAjaxDiv+"&dept="+deptId,
		success : function(subDeptData){
		$("#" + destAjaxDiv).html(subDeptData);
	    },
	    error : function () {
			alert("Somthing is wrong to get Sub Department");
		}
	});
    }


function getSubDept(deptId,destAjaxDiv,deptLevel,subdeptType,ftf,fcf,fscf) {
	feedTypeFlag=ftf;
	feedCatgFlag=fcf;
	feedSCatgFlag=fscf;
	var dept=$("#"+deptId).val();
	if (deptLevel=='2') {
    if (subdeptType=='1') {
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/Feedback_Over2Cloud/Feedback_Draft/subDeptViaAjax.action?destination="+ destAjaxDiv+"&dept="+deptId,
		success : function(subDeptData){
		$("#" + destAjaxDiv).html(subDeptData);
	    },
	    error : function () {
			alert("Somthing is wrong to get Sub Department");
		}
	});
    }
	
    else if (subdeptType=='2') {
    	$.ajax({
    		type :"post",
    		url :"/over2cloud/view/Over2Cloud/Feedback_Over2Cloud/Feedback_Draft/BySubDeptViaAjax.action?destination="+ destAjaxDiv+"&dept="+deptId,
    		success : function(subDeptData){
    		$("#" + destAjaxDiv).html(subDeptData);
    	    },
    	    error : function () {
    			alert("Somthing is wrong to get Sub Department");
    		}
    	});
	}
    
    else if (subdeptType=='3') {
		$.ajax({
			type :"post",
			url :"/over2cloud/view/Over2Cloud/Feedback_Over2Cloud/Feedback_Draft/ToSubDeptViaAjax.action?destination="+ destAjaxDiv+"&dept="+deptId,
			success : function(subDeptData){
			$("#" + destAjaxDiv).html(subDeptData);
		    },
		    error : function () {
				alert("Somthing is wrong to get Sub Department");
			}
		});
    }
   }
  }

	
function getFeedType(subDeptId,destAjaxDiv) {
	 $("#tosdId").val(subDeptId);
	if (feedTypeFlag=='1') {
	var subdept=$("#"+subDeptId).val();
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/Feedback_Over2Cloud/Feedback_Draft/FBTypeAjax.action?destination="+ destAjaxDiv+"&deptOrSubDeptId="+subDeptId,
		success : function(feedTypeData){
		$("#" + destAjaxDiv).html(feedTypeData);
	    },
	    error : function () {
			alert("Somthing is wrong to get Feed Type");
		}
	});
	}
}


function getFeedType4Download(subDeptId,destAjaxDiv) {
	var subdept=$("#"+subDeptId).val();
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/Feedback_Over2Cloud/Feedback_Draft/FBType4Download.action?destination="+ destAjaxDiv+"&deptOrSubDeptId="+subDeptId,
		success : function(feedTypeData){
		$("#" + destAjaxDiv).html(feedTypeData);
	    },
	    error : function () {
			alert("Somthing is wrong to get Feed Type");
		}
	});
}

function getFeedCategory(fbTypeId,destAjaxDiv) {
	if (feedCatgFlag=='1') {
	var feedType=$("#"+fbTypeId).val();
	$("#feedId").val(fbTypeId);
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/Feedback_Over2Cloud/Feedback_Draft/FBCategoryAjax.action?destination="+ destAjaxDiv+"&feedTypeId="+fbTypeId,
		success : function(categoryData){
		$("#" + destAjaxDiv).html(categoryData);
	    },
	    error : function () {
			alert("Somthing is wrong to get Category");
		}
	});
	}
}


function FBCategory4Download(fbTypeId,destAjaxDiv) {
	var feedType=$("#"+fbTypeId).val();
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/Feedback_Over2Cloud/Feedback_Draft/FBCategoryAjax.action?destination="+ destAjaxDiv+"&feedTypeId="+fbTypeId,
		success : function(categoryData){
		$("#" + destAjaxDiv).html(categoryData);
	    },
	    error : function () {
			alert("Somthing is wrong to get Category");
		}
	});
}

function getFeedSubCategory(categoryId,destAjaxDiv) {
	if (feedSCatgFlag=='1') {
	var catgId=$("#"+categoryId).val();
	$.ajax({
		type :"post",
		url :"/over2cloud/view/Over2Cloud/Feedback_Over2Cloud/Feedback_Draft/FBSubCategoryAjax.action?destination="+ destAjaxDiv+"&catg="+categoryId,
		success : function(categoryData){
		$("#" + destAjaxDiv).html(categoryData);
	    },
	    error : function () {
			alert("Somthing is wrong to get Category");
		}
	});
	}
}


function getFeedBreifViaAjax(subCatg)
{
     // var subCatg = document.getElementById(subCatgId).value;
      $("#scatgid").val(subCatg);
      $("#test").val(subCatg);
       $.getJSON("/over2cloud/view/Over2Cloud/Feedback_Over2Cloud/Feedback_Draft/SubCatgDetail.action?subcatg="+subCatg,
    	 function(data){
        	   $("#subCatgId").val(data.id);
    	   
               if(data.feed_msg == null){
            	   $("#feed_brief").val("NA");
               }
               else {
            	   $("#feed_brief").val(data.feed_msg);
			   }
               
               if(data.addressing_time == null){
            	   $("#resolutionTime").val("00:30");
               }
               else {
            	   $("#resolutionTime").val(data.resolution_time);
			   }
               
               if(data.escalation_time == null){
            	   $("#escalationTime").val("02:00");
               }
               else {
            	   $("#escalationTime").val(data.resolution_time);
			   }
       });
}

function addNewFeedback()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Feedback_Over2Cloud/Feedback_Draft/beforeFeedDraft.action?empModuleFalgForDeptSubDept=1&feedbackDarft=1",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function uploadFeedback()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Feedback_Over2Cloud/Feedback_Draft/beforeFeedUpload.action?feedbackDarft=2",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function downloadFeedback()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/Feedback_Over2Cloud/Feedback_Draft/beforeFeedDownload.action?feedDarftDownload=1",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}