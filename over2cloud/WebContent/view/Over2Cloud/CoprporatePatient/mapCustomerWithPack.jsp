<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">

function onloadPackage(value){

	if(value=='1')
	{
		//$("#Wsearch1").hide();
	//$("#Wsearch").show();
	 $("#package").show();
	 $("#actionButton").show();
		var targetid="packageData";
		$.ajax({
			type :"post",
			url  : "view/Over2Cloud/CorporatePatientServices/cpservices/packageData.action",
			success : function(empData){
			console.log(empData);
			$('#'+targetid+' option').remove();
			$('#'+targetid).append($("<option></option>").attr("value",-1).text("Select Package For Map"));
	    	$(empData).each(function(index)
			{
			   $('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.Name));
			});
		    },
		    error : function () {
				alert("Somthing is wrong to get Data");
			}
		});
		$('#billingGrp option').remove();
		$('#accountManager option').remove();
		$("#billGrp").hide();
		$("#accMGR").hide();
	}
	else if(value=='2')

	{

		$("#billGrp").show();
		$("#actionButton").show();
		//$("#Wsearch1").show();
		//$("#Wsearch").hide();
		var targetid="billingGrp";
		$.ajax({
			type :"post",
			url  : "view/Over2Cloud/CorporatePatientServices/cpservices/billingGrpData.action",
			success : function(empData){
			console.log(empData);
			$('#'+targetid+' option').remove();
			$('#'+targetid).append($("<option></option>").attr("value",-1).text("Select Billing Group For Map"));
	    	$(empData).each(function(index)
			{
			   $('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.Name));
			});
		    },
		    
		    error : function () {
				alert("Somthing is wrong to get Data");
			}
		});
		$('#packageData option').remove();
		$('#accountManager option').remove();
		$("#package").hide();
		$("#accMGR").hide();
	}
	else if(value=='3')

	{

		$("#accMGR").show();
		$("#actionButton").show();
		
		var targetid="accountManager";
		$.ajax({
			type :"post",
			url  : "view/Over2Cloud/CorporatePatientServices/cpservices/accountManagerData.action",
			success : function(empData){
			console.log(empData);
			$('#'+targetid+' option').remove();
			$('#'+targetid).append($("<option></option>").attr("value",-1).text("Select Account Manager For Map"));
	    	$(empData).each(function(index)
			{
			   $('#'+targetid).append($("<option></option>").attr("value",this.id).text(this.Name));
			});
		    },
		    
		    error : function () {
				alert("Somthing is wrong to get Data");
			}
		});
		$('#packageData option').remove();
		$('#billingGrp option').remove();
		$("#package").hide();
		$("#billGrp").hide();
		
	}
	else{

		$("#billGrp").hide();
		$("#actionButton").hide();
		 $("#package").hide();
		 $("#accMGR").show();

	}
}

function actionOnMap(){
//alert("kkk");
	var ids = jQuery("#gridedittableeee").jqGrid('getGridParam', 'selarrrow');
	var healthPkgID = $("#packageData").val();
	var billingGrpID = $("#billingGrp").val();
	var accountManagerID = $("#accountManager").val();
	//alert(ids);
	//alert("healthPkgID "+healthPkgID+" billingGrpID "+ billingGrpID +" accountManagerID "+accountManagerID);
	if(ids.length==0)	
	{
		alert("Please Select Atleast One Row.");//|| accountManagerID==null && billingGrpID=='-1' || healthPkgID=='-1' || accountManagerID=='-1'
	}
	else if(healthPkgID==null && healthPkgID=='null' && accountManagerID=='null' )
	{
		alert("Please Select Data from Drop Down.");
	}
	else if(billingGrpID==null && accountManagerID=='null' && healthPkgID=='null')
	{
		alert("Please Select Data from Drop Down.");
	}
	else if(accountManagerID==null && healthPkgID=='null' && healthPkgID =='null')// || billingGrpID == '-1' || accountManagerID=='-1' &&   healthPkgID == null || billingGrpID=='-1' || healthPkgID=='-1' || accountManagerID=='-1' && accountManagerID==null && billingGrpID=='-1' || healthPkgID=='-1' || accountManagerID=='-1')
	{
		alert("Please Select Data from Drop Down.");
	}
	else
	{
		$.ajax({
			  type : "post",
			  url  : "view/Over2Cloud/CorporatePatientServices/cpservices/mapCustomer.action?customerIds="+ids+"&healthPkgID="+healthPkgID+"&billingGrpID="+billingGrpID+"&accountManagerID="+accountManagerID,
			    success : function(data) {
			$("#resultDiv").html(data);
			mapAvilabelData(healthPkgID,billingGrpID,accountManagerID)
            
			       	},
			    error: function() {
			            alert("error");
			        }
			 });
		
	}

	
}
function cancelButton(ids)
{
	 $('#completionResult').dialog('close');
}

function onloadData(){

	var accountManagerID = $("#accountManager").val();
	var healthPkgID = $("#packageData").val();
	var billingGrpID = $("#billingGrp").val();
	var billinggroup=$("#billinggroup").val();
	var packagegroup=$("#packagegroup").val();
	//alert(packagegroup);
 	$.ajax({
    type : "post",
    async:false,
    url : "view/Over2Cloud/CorporatePatientServices/cpservices/mapCustomerwithPackData.action?accountManagerID="+accountManagerID+"&healthPkgID="+healthPkgID+"&billingGrpID="+billingGrpID+"&packagegroup="+packagegroup+"&billinggroup="+billinggroup,
    success : function(feeddraft) {
        $("#"+"viewDataDiv").html(feeddraft);
	},
   error: function() {
        alert("error");
    }
 });
}
function mapAvilabelData(healthPkgID,billingGrpID,accountManagerID)
{

 	$.ajax({
    type : "post",
    async:false,
    url : "view/Over2Cloud/CorporatePatientServices/cpservices/mapCustomerwithPackData.action?accountManagerID="+accountManagerID+"&healthPkgID="+healthPkgID+"&billingGrpID="+billingGrpID,
    success : function(feeddraft) {
        $("#"+"viewDataDiv").html(feeddraft);
        setTimeout(function(){ $("#resultDiv").fadeIn(); }, 10);
        setTimeout(function(){ $("#resultDiv").fadeOut(); }, 900);
	},
   error: function() {
        alert("error");
    }
 });
}
function mapAction()
{
	 $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/CorporatePatientServices/cpservices/ViewCustomerMap.action",
		    success : function(data) 
		    {
				$("#"+"data_part").html(data);
		    },
		    error: function() 
		    {
	            alert("hhh");
	        }
		 });
}
onloadData();
function wildSearch()
{
	 delay(function()
	{
	  onloadData();
	    }, 3000 );
}
var delay = (function(){
	  var timer = 0;
	  return function(callback, ms){
	    clearTimeout (timer);
	    timer = setTimeout(callback, ms);
	  };
	})();
function billgr()
{
	$("#Wsearch").hide();
	$("#Wsearch1").show();
}
function helgr()
{
	$("#Wsearch1").hide();
	$("#Wsearch").show();
}
</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Corporate Patient </div>
	<div class="head">
	<img alt="" src="images/forward.png" style="margin-top:10px;margin-right:3px; float: left;">Activity 
	</div>
	
</div>	
<div class="clear"></div>
<center><div id="resultDiv"></div></center>
 <div style="width:99%;    margin: 5px 0px 0px 6px; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; ">
 <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
 <table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
	 	    <tbody>
    <tr>
		    <td >
		       	       <s:select  
					    	id	=	"servicese"
					    	name	=	"services"
					    	list	=	"#{'1':'Health Checkup Package', '2':'Billing Group'}"
					      	theme	=	"simple"
					      	cssClass	=	"button"
					      	headerKey="-1"
					      	headerValue="Services"
					      	cssStyle	=	"height: 28px;margin-top: 3px;margin-left: -2px;width: 179px;"
					      	onchange	=	"onloadPackage(this.value);"	
	      					>
	      			</s:select>
	      			
	      			
		    </td>
		    
		    
	    </tr>
	    <tr>
	    <td>
	    <div id="package" style="display: none;"><!--
				    //<sj:a button="true" cssClass="button" cssStyle="height:25px;"  title="Add"   onclick="actionOnCart();">Action</sj:a> 
					--><s:select  
					    	id	=	"packageData"
					    	name	=	"packageData"
					    	list	=	"#{'-1':'No Data'}"
					      	theme	=	"simple"
					      	cssClass	=	"button"
					      	headerKey="-1"
					      	headerValue="Services"
					      	cssStyle	=	"height: 28px;margin-top:-29px;margin-left: 178px;width: 223px;"
					      	onchange	=	"onloadData();helgr();"	
	      					>
	      			</s:select>
				
				</div>
		     	 
	    <div id="Wsearch" style="display: none;">
	    <s:textfield  id="packagegroup" name="packagegroup" onkeyup="wildSearch();" cssClass="textField" cssStyle="width: 150px;height: 23px;margin-top: -28px ;margin-left: 429px;" Placeholder="Search Customer Name" theme="simple"/>
	    </div>
	    <div id="Wsearch1" style="display: none;">
	    <s:textfield  id="billinggroup" name="billinggroup" onkeyup="wildSearch();" cssClass="textField" cssStyle="width: 150px;height: 23px;margin-top: -28px ;margin-left: 429px;" Placeholder="Search Customer Name" theme="simple"/>
	    </div>
	    </td>
	    </tr>
	    <tr>
	    <td>
	    <div id="billGrp" style="display: none;"><!--
				    //<sj:a button="true" cssClass="button" cssStyle="height:25px;"  title="Add"   onclick="actionOnCart();">Action</sj:a> 
					--><s:select  
					    	id	=	"billingGrp"
					    	name	=	"billingGrp"
					    	list	=	"#{'-1':'No Data'}"
					      	theme	=	"simple"
					      	cssClass	=	"button"
					      	headerKey="-1"
					      	headerValue="Billing Group"
					      	cssStyle	=	"height: 28px;margin-top:-29px;margin-left: 183px;width: 223px;"
					      	onchange	=	"onloadData();billgr();"
	      					>
	      			</s:select>
				
				</div>
				
				<div id="accMGR" style="display: none;">
				<s:select  
					    	id	=	"accountManager"
					    	name	=	"accountManager"
					    	list	=	"#{'-1':'No Data'}"
					      	theme	=	"simple"
					      	cssClass	=	"button"
					      	headerKey="-1"
					      	headerValue="Account Manager"
					      	cssStyle	=	"height: 28px;margin-top:-29px;margin-left: 183px;width: 223px;"
					      	onchange	=	"onloadData();"
	      					>
	      			</s:select>
				
				</div>
				<sj:a button="true" cssClass="button" cssStyle="height: 20px; margin-top: -34px; float: right;margin-right: 79px;"  title="Back"   onclick="mapAction();">Back</sj:a>
				<div id="actionButton" style="display: none;">
				
				    <sj:a button="true" cssClass="button" cssStyle="height: 20px; margin-top: -34px; float: right;"  title="Map"   onclick="actionOnMap();">Map</sj:a> 
					
				
				</div>
		     	 
	    
	    </td>
	    </tr>
	        </tbody>
	 </table>
	 
	
</div>
 
<div id="viewDataDiv" style="display: block" >

 
    <!-------------------- End grid -------------------------> 

</div>
 
<!-- Code End for Header Strip -->

</div>
</div>
</div>
  <!------------------- Grid Part start -------------------------->                  
  
<sj:dialog
          id="completionResult"
          showEffect="slide" 
    	  hideEffect="explode" 
    	  openTopics="openEffectDialog"
    	  closeTopics="closeEffectDialog"
          autoOpen="false"
          title="Confirmation Message"
          cssStyle="overflow:hidden;text-align:center;margin-top:10px;"
          modal="true"
          width="400"
          height="150"
          draggable="true"
    	  resizable="true"
    	   buttons="{ 
    		'Close':function() { cancelButton(); } 
    		}" 
          >
          <div id="result"></div>
</sj:dialog>

</body>
</html>
                   
                                    