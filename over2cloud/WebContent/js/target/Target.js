function AjexonCategoryTargetDetils(AveragetargetdivDiv,Categoryid)
{
	var OutletidList=new Array();
	var j=0;
	for(var i=0 ; i < document.formone.outletid.length;i++){
		if(document.formone.outletid[i].checked)
		{
			OutletidList[j]=document.formone.outletid[i].value; 
			j++;
		}
		}
	$.ajax
	  (
	    {
	        type: "post",
	        url : "/sfa/DreamSol/TargetMgmt/CategoryBulkAction.action?categoryid="+Categoryid+"&outletList="+OutletidList,
	        success : function(TargetDetails)
	        {
	    	$("#"+AveragetargetdivDiv).html(TargetDetails);},
	        error: function()
	        {alert("error");}
	    }
	  );
}

function AjexonProductTargetDetils(AveragetargetProductdivDiv,produstsizeid)
{
	var OutletidList=new Array();
	var j=0;
	for(var i=0 ; i < document.formone.outletid.length;i++){
		if(document.formone.outletid[i].checked)
		{
			OutletidList[j]=document.formone.outletid[i].value; 
			j++;
		}
		}
	$.ajax
	  (
	    {
	        type: "post",
	        url : "/sfa/DreamSol/TargetMgmt/ProductBulkAction.action?productid="+produstsizeid+"&outletList="+OutletidList,
	        success : function(TargetDetails)
	        {
	    	$("#"+AveragetargetProductdivDiv).html(TargetDetails);},
	        error: function()
	        {alert("error");}
	    }
	  );
}










function AjexonTargetDetils(targetDiv,outletId)
{
	$.ajax
	  (
	    {
	        type: "post",
	        url : "/sfa/DreamSol/TargetMgmt/TargetDetils.action?outletId="+outletId,
	        success : function(TargetDetails) 
	        {
	    	$("#"+targetDiv).html(TargetDetails);},
	        error: function()
	        {alert("error");}
	    }
	  );
}

function AjexonProposalTarget(proposaltargetDiv,FillTxtValue)
{	
	
	if(FillTxtValue!=null || FillTxtValue!="")
	{
		var Average=document.getElementById("averageTarget").value;
		$.ajax
		  (
		    {
		        type: "post",
		        url : "/sfa/DreamSol/TargetMgmt/ProposalTarget.action?proposalTarget="+FillTxtValue+"&average="+Average,
		        success : function(Grothratevalue) 
		        {
		    	$("#"+proposaltargetDiv).html(Grothratevalue);},
		        error: function()
		        {alert("error");}
		    }
		  );
		
	}
	

}

//Dated 08-11-2012
function category()
{
	var n;
	for(n=0; n<document.Totaltargetid.targetvalue.length; n++)
	{	
		if(document.Totaltargetid.targetvalue[n].value.length>0)
		{
			
			var a =document.Totaltargetid.targetvalue[n].value;
			var iChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_`@#~!$%^&*()+=-[]';,{}|\":<>?";
			for(var i=0;i<a.length;i++)
			{
				//alert("alert called."+"value of a is:"+a);
				if (iChars.indexOf(a.charAt(i)) != -1)
				{
					alert("Please Enter Numeric Values.");
					var len = (document.Totaltargetid.targetvalue[n].value.length) - 1;
					document.Totaltargetid.targetvalue[n].value = (document.Totaltargetid.targetvalue[n].value).substring(0, len);
					document.Totaltargetid.targetvalue[n].focus();
					event.originalEvent.Totaltargetid.submit = false;
				}
				else
					{
					}
		    }
	   }
    }
}


function targetCategory()
{
	var n;
	for(n=0; n<document.AddCategoryDetails_Url.targetvalue.length; n++)
	{	
		if(document.AddCategoryDetails_Url.targetvalue[n].value.length>0)
		{
			
			var a =document.AddCategoryDetails_Url.targetvalue[n].value;
			var iChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_`@#~!$%^&*()+=-[]';,{}|\":<>?";
			for(var i=0;i<a.length;i++)
			{
				//alert("alert called."+"value of a is:"+a);
				if (iChars.indexOf(a.charAt(i)) != -1)
				{
					alert("Please Enter Numeric Values.");
					var len = (document.AddCategoryDetails_Url.targetvalue[n].value.length) - 1;
					document.AddCategoryDetails_Url.targetvalue[n].value = (document.AddCategoryDetails_Url.targetvalue[n].value).substring(0, len);
					document.AddCategoryDetails_Url.targetvalue[n].focus();
					event.originalEvent.Totaltargetid.submit = false;
				}
				else
					{
					}
		    }
	   }
    }
}

//For Monthly target.
function validateMonthlyProposedTarget()
{
	var y;
	for(y=0; y<document.formone.targetvalue.length; y++)
	{	
		if(document.formone.targetvalue[y].value.length>0)
		{
			var b =document.formone.targetvalue[y].value;
			var iCharss = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_`@#~!$%^&*()+=-[]';,{}|\":<>?";
			for(var j=0;j<b.length;j++)
			{
				if (iCharss.indexOf(b.charAt(j)) != -1)
				{
					errEmptyMonthlyTarget.innerHTML="<div class='user_form_inputError2'>Only Numeric Values.</div>";
					var lent = (document.formone.targetvalue[y].value.length) - 1;
					document.formone.targetvalue[y].value = (document.formone.targetvalue[y].value).substring(0, lent);
					document.formone.targetvalue[y].focus();
					event.originalEvent.formone.submit = false;
				}
				else
					{
						errEmptyMonthlyTarget.innerHTML="";
					}
			}
		}
  }
}
function validateMonthlySingleTarget()
{
		if(document.formone.targetvalue.value=="")
		{
			errEmptyMonthlyTarget.innerHTML="<div class='user_form_inputError2'>Enter Numeric Values.</div>";
		}
		if(document.formone.targetvalue.value.length>0)
		{
			var a =document.formone.targetvalue.value;
			var iChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_`@#~!$%^&*()+=-[]';,{}|\":<>?";
			for(var i=0;i<a.length;i++)
			{
				if (iChars.indexOf(a.charAt(i)) != -1)
				{
					errEmptyMonthlyTarget.innerHTML="<div class='user_form_inputError2'>Only Numeric Values.</div>";
					var len = (document.formone.targetvalue.value.length) - 1;
					document.formone.targetvalue.value = (document.formone.targetvalue.value).substring(0, len);
					document.formone.targetvalue.focus();
					event.originalEvent.formone.submit = false;
				}
				else
				{
					errEmptyMonthlyTarget.innerHTML="";
				}
		   }
	    }
}














