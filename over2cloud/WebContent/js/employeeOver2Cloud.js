//USER RIGHTS CHECKBOX ALL
 function checkFields(rigType,module){
        var rights=module.split("ID");
        var str=$(".rig").text();
        var rightsName=str.split("#");//contains are used in Mozila FireFox
        for(var i=0;i<rightsName.length;i++){
            if(rightsName[i].includes(rights[0])){
                var rId=rightsName[i].split(",");
                console.log(rId);
                if(!(document.getElementById(rId[1]+"_"+rigType).disabled)){
                    if(!($("#"+rId[1]+"_"+rigType).attr('checked'))){
                        $("#"+rId[1]+"_"+rigType).attr("checked", true);
                    }
                    else{
                        $("#"+rId[1]+"_"+rigType).attr("checked", false);
                }
                }    
            }
        }
    }

function checkMobnoExistViaAjax(mobno)
{
	 var mobile=document.getElementById(mobno).value;
	 document.getElementById("indicator2").style.display="block";
	 $.getJSON("/over2cloud/view/Over2Cloud/hr/checkMobile.action?mobOne="+mobile,
     //$.getJSON("/over2cloud/view/hradmin_pages/employee_admin/checkMobile.action?mobone="+mobile,
		function(empdata){
    	     $("#empStatus").html(empdata.msg);
    	     document.getElementById("indicator2").style.display="none";
        	 document.getElementById("status").value=empdata.status;
    });
}

function getAllEmpList(AjaxDivpTemp)
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/hr/getEmplDataList.action",
	    success : function(companyData) {
		$("#"+AjaxDivpTemp).html(companyData);
	},
	   error: function() {
	        alert("error");
	    }
	 });
}

function fillMob(valueTemp)
{
	document.formTwo.mobNoPerTemp.value=valueTemp;
}

var flag="0";
function checkStatus()
{
	if(flag=="0")
	{
		flag="1";
		document.getElementById('workExprncDiv').style.display='block';
		
	}
	else
	{
		flag="0";
		document.getElementById('workExprncDiv').style.display='none';
	}
	
}
function getCurrentColumn(downloadType,moduleName,dialogId,dataDiv)
{
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/hr/currentColumn.action?downloadType="+downloadType+"&download4="+moduleName,
	    success : function(data) {
		$("#"+dataDiv).html(data);
		$("#"+dialogId).dialog('open');
	},
	   error: function() {
	        alert("error");
	    }
	 });
}
