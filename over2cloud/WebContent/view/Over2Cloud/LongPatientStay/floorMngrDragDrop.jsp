<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<html>
<head>
<meta  http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script  id="dragdrop2" type='text/javascript' src='scripts/jquery-1.10.2.min.js'></script>
<script  src="js/jquery-ui.js"></script>
  <script type="text/javascript" >
  var count=0,idvalidate=[];
  var checkedWingIds=[];
  var modifiedFlag=false;
  
  function modifier(){
	  modifiedFlag=true;
	  $("#modifier").css('display','inline-block');
  }
  function unmodifier(){
	  modifiedFlag=false;
	  $("#modifier").css('display','none');
  }
  
  $(document).ready(function () {
  //to get list of all department
    $.ajax({
    	    type : "post",

    	    url : "view/Over2Cloud/LongPatientStay/depatmentsforlist.action?deptFlag=dept",
    	   // url : "/over2cloud/view/Over2Cloud/LongPatientStay/depatmentsforlist.action?deptFlag=dept",
    	    success : function(data) {
           	$('#department option').remove();
          	$('#department').append($("<option></option>").attr("value",-1).text("Select Department"));
              	$(data).each(function(index)
          	{
              	//alert(this.id);	
          	   $('#department').append($("<option></option>").attr("value",this.id).text(this.dept));
          	});
           	
           	},
    	    error: function() {
                alert("error");
            }
    	 });
  //to get floor or wings div for selected department
  
  //getting ids of employee
    
	  
       	
	 });
  
  
function maptomultiplewing(emp){
	 checkedWingIds=[];
	$('#hiddenemp').text(emp.id);
	
	var temp=$('#'+emp.id).text();
	var str  = temp.split('(');
	var deptlevel=str[str.length-1];//level
	$('#hiddenempname').text(temp);
	$('#hiddenlevel').text(deptlevel);
	$("#maxmizeViewDialog1").dialog({title:'Map '+$('#'+emp.id).text()+' to Wings',height:400,width:240,dialogClass:'transparent'});
	
	$("#maximizeDataDiv1").html("<br><br><br><br><br><center><img src=images/lightbox-ico-loading.gif></center>");
	 $.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/LongPatientStay/mappingwing.jsp",
	    success : function(data) 
	    {
	    	
	$("#maximizeDataDiv1").html(data);
	$("#maxmizeViewDialog1").dialog('open');
	    },
	   error: function() {
	        alert("error");
	    }
	 }); 
}

var delay = (function(){
	  var timer = 0;
	  return function(callback, ms){
	    clearTimeout (timer);
	    timer = setTimeout(callback, ms);
	  };
	})();
	
	function searchdelay(search){
	delay(function(){
	getEmployee(search);
	    }, 10 );
	}

	function getEmployee(search){
	if($("#department").val()!='-1'){
	$.ajax({
	      	    type : "post",
	      	    url : "/over2cloud/view/Over2Cloud/LongPatientStay/BeforeConfigEscContact1.action?searchString="+search+"&deptId="+$("#department").val(),
	      	    success : function(data) {
	      	    	//alert(JSON.stringify(data));
	      	    	//console.log(data);
	                     	$('#catalog ul').empty();
                     	var temp="";
                        	$(data).each(function(index)
                    	{
                        	temp +='<li id="'+this.id+'" ondblclick="maptomultiplewing(this);">'+this.name+'<a href="#"><span id="'+this.id+'kk'+this.logo+'" style="float:left;  margin: -11px 1px 0px 0px;   box-shadow: 3px 2px 4px 0px rgb(164, 171, 176); border-radius: 17px;" onclick="appendSkillDetails(this.id)"><img class="maxPic" style="border-radius: 17px;" src="'+this.logo+'" width="34px" height="33px" /></span></a></li>';
                    	});
                    	//alert(temp);
                    	    $("#catalog ul").append(temp);
                    	    $("#catalog li").draggable({
                    	    	revert:true,
                    	    	containment:'#shop',
                    	    	
                    	    });
	             	//alert(temp);
	             	},
	      	    error: function() {
	                  alert("error");
	              }
	      	 });
	}  
	
	}
	
	function appendSkillDetails(emp){
	$.ajax({
	      	    type : "post",
	      	    url : "/over2cloud/view/Over2Cloud/LongPatientStay/getSkillDetails.action?trashEmpId="+emp.split("kk")[0],
	      	    success : function(data) {
	      	    	
	      	    	$("#maxmizeViewDialog1").dialog({title:'Skillset Details ',height:250,width:400,dialogClass:'transparent'});
	      	  	$("#maxmizeViewDialog1").dialog('open');
	      	  	$("#maximizeDataDiv1").html("<br><br><br><br><br><center><img src=images/lightbox-ico-loading.gif></center>");
	      	  	var temp='';
	      	  temp+='<table width="100%" id="skillInfo"><tr><td rowspan="10"><img src="'+emp.split("kk")[1]+'" width="120px" height="120px"/></td><td>Skills</td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<td>Level</td><tr>';
	      	 
	      	  
	      	  //console.log(data);
	      	  $(data).each(function(index){
	            	
	                      temp+='<tr><td>'+this.skill+'</td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<td>'+this.level+'</td><tr>';
	                      
	      	  });
	      	  	temp+='</table>';
	      	  	$("#maximizeDataDiv1").html(temp);
	      	  	
	             	},
	      	    error: function() {
	                  alert("error");
	              }
	      	 });
	}
    function democall(selval,editFlag){
  //	  alert(selval);
  	 $("#shiftViewDiv").hide();
  	$("#dpetEmpDiv").show();
  	  $.ajax({
      	    type : "post",
      	    url : "/over2cloud/view/Over2Cloud/LongPatientStay/BeforeConfigEscContact1.action?deptId="+selval,
      	    success : function(data) {
      	    	//alert(JSON.stringify(data));
      	    //	console.log(data);
                     	$('#catalog ul').empty();
                     	var temp="";
                        	$(data).each(function(index)
                    	{
                        	temp +='<li id="'+this.id+'" ondblclick="maptomultiplewing(this);">'+this.name+'<a href="#"><span id="'+this.id+'kk'+this.logo+'" style="float:left;  margin: -11px 1px 0px 0px;   box-shadow: 3px 2px 4px 0px rgb(164, 171, 176); border-radius: 17px;" onclick="appendSkillDetails(this.id)"><img class="maxPic" style="border-radius: 17px;" src="'+this.logo+'" width="34px" height="33px" /></span></a></li>';
                    	});
                    	//alert(temp);
                    	    $("#catalog ul").append(temp);
                    	    $("#catalog li").draggable({
                    	    	revert:true,
                    	    	containment:'#shop',
                    	    	
                    	    });
             	//alert(temp);
             	},
      	    error: function() {
                  alert("error");
              }
      	 });
  	  count=0;
  	  if(editFlag=='T'){
  	 definecontent(selval);
  	  }
  	 
  	  
    }
    var dynamiccount;
    var addFlag=true;
    var addFlag1=true;
    var floorNA="NA";
    var wingIds='';
    var idWingsContainer=[];
    //to define wings and floor according to mapping of deparment 
    function definecontent(selval){
  	  $.ajax({
    	    type : "post",
    	    url : "/over2cloud/view/Over2Cloud/LongPatientStay/getfloorwingsdetail.action",
    	    success : function(data) {
    	    	$("#wings").empty();
    	    	dynamiccount=data.length;
    	    	var countFloorItem=[];
    	 //  	console.log(data);
    	   	$.each(data, function() 
    	   	{
    	   	
	    	   	  $.each(this, function(k, v)
	    	   	  {
	    	   	//	console.log(k);
	    	   	$("#wings").append('<div style="width:98%; background: #e6e6e6  ;box-shadow: 2px 1px 4px -1px rgb(180, 186, 189);font-weight: bold;" align="center"><span style="float:left;margin:3px 0px 0px 3px;"><img id="'+k.split(" ")[0]+'kkkshow1" title="Expand" style="cursor:pointer;" class="ui-icon tree-wrap-ltr ui-icon-circlesmall-plus" onclick="showHideLoc1(this.id)" /><img id="'+k.split(" ")[0]+'kkkhide1" title="Collapse" style="cursor:pointer;display:none;" class="ui-icon ui-icon-circlesmall-minus tree-wrap-ltr"  onclick="showHideLoc1(this.id)" /></span>'+k+'<span id="'+k.split(" ")[0]+'countSpan"></span></div>');
	    	$("#wings")
	.append(
	'<div id="'+k.split(" ")[0]+'divid1" style="display:none;width:auto;border-radius:6px;"></div>');
	    	idWingsContainer.push("#"+k.split(" ")[0]+"divid1");
	    	$.each(this, function(k1, v1)
	    	   	 {
	     	   	//	console.log(v);
	     	  	 	$("#"+k.split(" ")[0]+"divid1").append('<div id="'+this.wingid+'wing" style="" align="center"><ul><p id="'+floorNA+'"></p><b>'+this.wingname+'</b></ul></div>');
	     	  	wingIds+=this.wingid+',';
	     	  	countFloorItem.push("#"+k.split(" ")[0]+"divid1");
	                 	adddivobj(this.wingid+'wing');
	                 	dragdrop(this.wingid+'wing');
	                 	
	     	   	  	});
	    	   	 	 });
    	   	});
    	
    	   	appendsavedemp('',wingIds,'');
    	    	//$("#wings").append('</div>');
    	    	/* $('#wings').accordion({
    	    	   
               });
    	    	*/
    	    //	alert("trace");
    	    countUpdate(countFloorItem);
           	},
    	    error: function() {
                alert("error");
            }
    	 });
  	  
  	 
    }
    function expandAllFloors(){
    	
    }
    function countUpdate(varArray){
    	
    	for (var i=0;i<varArray.length;i++)
    	{
    	/* alert(varArray[i]);
    	var liIds='';  
    	liIds = $(varArray[i]+' li').map(function(i,n) {
    	 return $(n).attr('id');
    	}).get().join(',');
    	console.log(liIds);	  
	  var precount = liIds.split(",");
	  console.log(precount);
	  var count=precount.length;
	 var countId= varArray[i].split("divid")[0]+"countSpan";
	 alert(count);
	 $(countId).text(count); */
	
    	}
    }
    function showHideLoc1(kid){
	var flrdiv=kid.split("kkk")[0]+"divid1";
	var showhide=kid.split("kkk")[1];
	if(showhide=='show1'){
	$("#"+flrdiv).show();
	$("#"+kid.split("kkk")[0]+"kkkhide1").show();
	$("#"+kid.split("kkk")[0]+"kkkshow1").hide();
	}else{
	$("#"+flrdiv).hide();
	$("#"+kid.split("kkk")[0]+"kkkshow1").show();
	$("#"+kid.split("kkk")[0]+"kkkhide1").hide();
	}
	}
    //composing header for floor Name
    function headercompose(floorname){
  	  var temp1=floorname;
  	  var temp2=temp1;
  	  $("#wings").append('<h3>'+floorname+'</h3>');
    }
    
    
  /*   //appending saved emp for floor
    function appendsavedempforfloor(floorid1){
  	  $.ajax({
    	type:"post",
    	url: "/over2cloud/view/Over2Cloud/LongPatientStay/getmappedemployee.action?floorName="+floorid1+'&deptId='+$('#department').val(),
    	success: function(data){
    	$(data).each(function(index)
                	{
    	//alert(wingid1);
    	$('#'+floorid1+'floor ul').append('<li id="'+this.id+'">'+this.name+'</li>');
                	});
    	
    	
    	},
    	error:function(){
    	   alert("error");
    	}
    	
    	});
    }
     */
     function clearShiftInfo(){
    	 $("#shiftId1L").val("");
	$("#shiftName1L").html("");
       	$("#shiftFrom1L").html("");
       	$("#shiftTo1L").html("");
    	$(".shiftTagL").css('display', 'none');
    	 $("#shiftId1").val("");
    	$("#shiftName1").html("");
    	       	$("#shiftFrom1").html("");
    	       	$("#shiftTo1").html("");
    	    	$(".shiftTag").css('display', 'none');
    	
     }
    function appendsavedemp(shiftId,wing,viewFlag){
  	//alert(wingid1);
  	//getting employee saved in data base for respective floor and wings
  	//alert("floor");
  	$("#shiftFrom1").show();
	$("#shiftTO1").show();
  	$("#shiftFrom2").hide();
	$("#shiftTO2").hide();
  	 shift_employeeIdsForSaveAs=[];
      	$.ajax({
      	type:"post",
      	url: "/over2cloud/view/Over2Cloud/LongPatientStay/getmappedemployee.action?deptId="+$("#department").val()+"&shiftId="+shiftId+"&wingIds="+wing+"&viewFlag="+viewFlag,
      	async:false,
      	success: function(data){
      	//console.log(data);
      	if(data.length!=0 && viewFlag!="T")
      	{
	      	if( data[0].shiftFrom!="NA" && data[0].shiftName!="NA" && data[0].shiftTo!="NA" )
	      	{
	      	$(".shiftTag").css('display', 'inline-block');
	      	
	      	$("#shiftId1").val(data[0].shiftid);
	          	$("#shiftName1").html(data[0].shiftName).css('color','green');
	                      	$("#shiftFrom1").html(data[0].shiftFrom);
	                      	$("#shiftTo1").html(data[0].shiftTo);
	                      	$(".shiftTagL").css('display', 'inline-block');
	                      	
	                      	$("#shiftId1L").val(data[0].shiftid);
	                        $("#shiftName1L").html(data[0].shiftName).css('color','black');
	                        $("#shiftFrom1L").html(data[0].shiftFrom);
	                        $("#shiftTo1L").html(data[0].shiftTo);
	         }
      	}
      	else if(viewFlag=="T")
      	{
      	$(".shiftTagL").css('display', 'inline-block');
          	
          	$("#shiftId1L").val(data[0].shiftid);
              	$("#shiftName1L").html(data[0].shiftName).css('color','black');
                          	$("#shiftFrom1L").html(data[0].shiftFrom);
                          	$("#shiftTo1L").html(data[0].shiftTo);
      	}else{
      	 $("#shiftId1L").val("");
      	$("#shiftName1L").html("No loaded shift");
      	       	$("#shiftFrom1L").html("");
      	       	$("#shiftTo1L").html("");
      	    	$(".shiftTagL").css('display', 'none');
      	    	 $("#shiftId1").val("");
      	    	$("#shiftName1").html("No active shift").css('color','green');
      	    	       	$("#shiftFrom1").html("");
      	    	       	$("#shiftTo1").html("");
      	    	    	$(".shiftTag").css('display', 'none');
      	}
      	
      	
      	
      if(data.length>0){	
    	 
    	  $(data).each(function(index){
          	//alert(wingid1);
          	//$('#'+wingid1+'wing ul').append('<li id="'+this.id+'">'+this.name+'</li>');
          	$('#'+this.wingid+'wing ul').append('<li id="'+this.id+'-'+this.wingid+'">'+this.name+'<a href="#"><span class="closeSpan" id="'+this.id+'kk'+this.wingid+'" onclick="removeEmp(this.id)"><img alt="closeimage" src="images/hide_feed.png" title="Remove" /></span></a></li>');
          	shift_employeeIdsForSaveAs.push(this.id+"#"+this.wingid);
                    
    	  });
      }
      	
      	
      	
      	},
      	error:function(){
      	   alert("error");
      	}
      	
      	});
    }
     
  	
  	 function dragdrop(dropid){
  	/*  $('#'+dropid1+' ul li').each(function(i)
	{
       	  idvalidate.push( $(this).attr('id')); 
	});	
         */
  	 
  	// alert("graggable: "+dropid);
         $("#"+dropid+" li" ).draggable({ 
          opacity:0.5,	 
         	  revert: true, // when not dropped, the item will revert back to its initial position
         	  containment: $('#shop'),
               cursor: "crosshair",
               grid: [ 50, 20 ],
               start:function (){
             	 //alert("hi");
             	  contents= $(this).text();
             	//  ids=$(this).val();
             	 
            
               }
               
           });  
  	 count++;
  	 }
  	  
  	  
  	  
  	  
  	/*   //floor droppable
  	function makefloordroppable(divid){
  	 
  	  $('#'+divid).droppable({
  	  hoverClass:'border',
  	  accept:'#catalog li',
  	  drop:function(event,ui){
  	  
  	 //getting id of all li inside div
  	  var liIds = $('#'+$(this).attr('id')+' li').map(function(i,n) {
	 return $(n).attr('id');
	}).get().join(',');
  	  
  	 //appending li to dropped div and validating too
  	  if(liIds.indexOf($(ui.draggable).attr('id'))!='-1'){
  	   $('#message').empty();
  	  $("#message").fadeIn('fast');
	      	$('#message').append("Contact already mapped to this floor!!!!").css('color','red');
	      	 $("#message").fadeOut(2000);
	      	addFlag1=false;
	      	
	      	
	      	}else{
	      	addFlag1=true;
	      	var dropid2=$(ui.draggable).attr('id');
	      	var dropname2=$(ui.draggable).text();
	      	$("#"+divid+' ul').append('<li id="'+dropid2+'">'+dropname2+'</li>');
	      	
	      	
	      	
	      	}
  	var res=$(this).attr('id');
  	
  	var finalres=res.substring(0,res.indexOf("f"));
  	//console.log('Floor DopdId: '+finalres);
  	//console.log('empi Id: '+$(ui.draggable).attr('id'));
  	var fulname=$(ui.draggable).text();
  	//console.log('empi Id: '+fulname[fulname.length-1]);
  	if(addFlag1){
  	$.ajax({
         	 type:"post",
         	 url:"/over2cloud/view/Over2Cloud/LongPatientStay/addEscContactConfig.action",
         	 data :  { escDept: $('#department').val(),escSubDept: $('#subdepartment').val(), empName: $(ui.draggable).attr('id'), floorname:finalres, level: fulname[fulname.length-1],escLevel:'floor' },
         	 success:function(data){
         	 $('#message').empty();
	      	$("#message").fadeIn('fast');
         	$('#message').append("Employee mapped successfully!!!").css('color','black');
         	 $("#message").fadeOut(2000);      	 },
         	 error:function(){
         	  alert("error");
         	 }
         	});
  	}
  	
  	
  	  }
  	  });
  	  
  	  } */
  	  
  	var shift_employeemapped_wing  =[];
  	  var refreshFlag=false;
 //to make div droppable 	  
 function adddivobj(divid){
	
	  // alert("droppable: "+divid);
	   $('#'+divid).droppable({
	 hoverClass:'border',
	 accept:'#catalog li',
	 drop:function(event,ui){
	 modifier();
	 refreshFlag=true;
	 if($("#shiftId1L").val()!=''){
	 $("#save").attr('onclick','save()').css('opacity','1');
	 $("#saveAs").attr('onclick','saveAs()').css('opacity','1');
	 }
	
	 var liIds = $('#'+$(this).attr('id')+' li').map(function(i,n) {
	 return $(n).attr('id');
	}).get().join(',');
	// console.log("list: "+liIds);
	
	
	//var temp11='';
	var array = liIds.split(',');
	//	alert(array.length);
	var temp11;
	var checkflag=false;
	for(var i=0;i<array.length;i++){
	//alert(array[i]);
	
	if(array[i].split("-")[0]==$(ui.draggable).attr('id')){
	checkflag=true;
	}
	}
	 
	
	
	
	      	if(checkflag==true){
	      	   $('#message').empty();
	      	$("#message").fadeIn('fast');
	      	$('#message').append("Contact already mapped to this floor!!!!").css('color','red');
	      	 $("#message").fadeOut(4000);
	      	addFlag=false;
	      	
	      	
	      	}else if(checkflag==false){
	
	var name = $(ui.draggable).text();
	var str  = name.split(' ');
	var deptlevel=str[str.length-1];//level
	var empId=$(ui.draggable).attr("id");//empId
	var pid=$(this).attr('id');
	var res = pid.substring(0, 1);//wing ID
	var floorId=$('#'+pid+' p').attr('id');//floorID
	var deptId=$('#department').val();//dept ID
	//	console.log('level: '+str[str.length-1]);
	var pid=$(this).attr('id');
	var res = pid.substring(0, pid.indexOf("w"));
	shift_employeemapped_wing.push(empId+"#"+res);
	shift_employeeIdsForSaveAs.push(empId+"#"+res);
	
	if(deptlevel!='' && typeof deptlevel != 'undefined' && empId!='' && typeof empId != 'undefined'
	&& res!='' && typeof res != 'undefined' && floorId!='' && typeof floorId != 'undefined' 
	&& floorId!='' && typeof floorId != 'undefined' && deptId!='' && typeof deptId != 'undefined'
	){//saving data 
	//alert("You can save");
	addFlag=true;
	      	var dropid2=$(ui.draggable).attr('id');
	      	var dropname2=$(ui.draggable).text();
	      	//$("#"+divid+' ul').append('<li id="'+dropid2+'-'+newid+'">'+dropname2+'</li>');
	      	$("#"+divid+' ul').append('<li id="'+dropid2+"-"+res+'">'+dropname2+'<a href="#"><span class="closeSpan" id="'+dropid2+"kk"+res+'" onclick="removeEmp(this.id)"><img alt="closeimage" src="images/hide_feed.png" title="Remove" /></span></a></li>');
	
	      	/*   $.ajax({
	              	    type : "post",
	              	    url : "/over2cloud/view/Over2Cloud/LongPatientStay/addEscContactConfig.action",
	              	    data :  { escDept: deptId,escSubDept: $('#subdepartment').val(), empName: empId, floorname:floorId, wingsname:res, level: deptlevel,escLevel:'wings' },
	              	    success : function(data) {
	              	    	//console.log(data[0].newid);
	              	    	var newid=data[0].newid;
	              	     
	              	    $(function() {
	              	    	
	              	    $('#message').empty();
	              	    	$('#message').append("Employee mapped successfully!!!").css('color','black').css('display','block');;
	              	    $("#message").fadeIn('fast');
	              	  $("#message").fadeOut(2000);
	              	    });
	                     	},
	              	    error: function() {
	                          alert("error");
	                      }
	              	 }); */
	}
	
	}
	
	 }//drop end
	   	
	   
	 
	 });
	 
	   
	 }//adddiv end 
	 function myFunction(empName,remId,wingid) {
	 $("#maxmizeViewDialog").dialog({title:'Remove '+empName+'',height:106,width:340,dialogClass:'transparent'});
	
	var temp='';
	temp+='<button  onclick="beforupdateConfirm(this.id)" id="yes" title="Single Location" style="margin-top: 15px;margin-left:20px;  border-radius: 5px;"> <font size="2">Single</font></button>';
	temp+='<button  onclick="beforupdateConfirm(this.id)" id="no" title="All Location" style="margin-top: 15px;margin-left:120px;  border-radius: 5px;"> <font size="2">All</font></button>';
	$("#remId").val(remId);
	$("#wingid").val(wingid);
	$("#trashMulDiv").append(remId+"#"+wingid+",");
	$("#maximizeDataDiv").html(temp);
	$("#maxmizeViewDialog").dialog('open');
	}
	 function beforupdateConfirm(con){
	 modifier();
	 var remId=$("#remId").val();
	 var wingid=$("#wingid").val();
	 $("#save").attr('onclick',' updateConfirm("'+con+'")').css('opacity','1');
	 $("#maxmizeViewDialog").dialog('close');
	 $('#'+remId+"-"+wingid).fadeOut(200);
	   	 $('#'+remId+"-"+wingid).remove();
	   	 $("#saveAs").attr('onclick',' saveAs()').css('opacity','1');
	   	//console.log("before SaveAs Arr"+shift_employeeIdsForSaveAs);
	 	//console.log("before Save Arr"+shift_employeemapped_wing);
	   	if(con=='yes'){
	   	
	   	 var index = shift_employeemapped_wing.indexOf(remId+'#'+wingid);
	 	if (index > -1) {
	 	shift_employeemapped_wing.splice(index, 1);
	 	}
	
	 	 var index = shift_employeeIdsForSaveAs.indexOf(remId+'#'+wingid);
	 	if (index > -1) {
	 	shift_employeeIdsForSaveAs.splice(index, 1);
	 	}
	   	}else if(con=='no'){
	   	removeFromSave=[];
	   	removeFromSaveAs=[];
	   	for(var i=0; i<shift_employeemapped_wing.length;i++){
	   	 
	   	 if(remId==shift_employeemapped_wing[i].split("#")[0])
	   	 {
	   	removeFromSave.push(remId+'#'+shift_employeemapped_wing[i].split("#")[1]);
	   	 $('#'+remId+"-"+shift_employeemapped_wing[i].split("#")[1]).fadeOut(200);
	   	   	 $('#'+remId+"-"+shift_employeemapped_wing[i].split("#")[1]).remove();
	   	 
	   	 /* var index = shift_employeemapped_wing.indexOf(remId+'#'+shift_employeemapped_wing[i].split("#")[1]);
	 	if (index > -1) {
	 	shift_employeemapped_wing.splice(index, 1);
	 	} */
	   	 }
	   	}
	   	//shift_employeemapped_wing=shift_employeemapped_wing.diff(removeFromSave);
	   	shift_employeemapped_wing = $(shift_employeemapped_wing).not(removeFromSave).get();	
	   	//taking array difference
	
	   	
	   	for(var j=0; j<shift_employeeIdsForSaveAs.length;j++)
	   	{
	   	removeFromSaveAs.push(remId+'#'+shift_employeeIdsForSaveAs[j].split("#")[1]);
	   	 if(remId==shift_employeeIdsForSaveAs[j].split("#")[0])
	   	 {
	   	 $('#'+remId+"-"+shift_employeeIdsForSaveAs[j].split("#")[1]).fadeOut(200);
	   	   	 $('#'+remId+"-"+shift_employeeIdsForSaveAs[j].split("#")[1]).remove();
	   	/*  var index = shift_employeeIdsForSaveAs.indexOf(remId+'#'+shift_employeeIdsForSaveAs[j-1].split("#")[1]);
	 	if (index > -1) {
	 	shift_employeeIdsForSaveAs.splice(index, 1);
	 	} */
	   	 }
	   	}
	   	
	   	//shift_employeeIdsForSaveAs=shift_employeeIdsForSaveAs.diff(removeFromSaveAs);
	   	shift_employeeIdsForSaveAs = $(shift_employeeIdsForSaveAs).not(removeFromSaveAs).get();	
	   	}
	 	
	  	//console.log("After SaveAs Arr"+shift_employeeIdsForSaveAs);
	 	//console.log("After Save Arr"+shift_employeemapped_wing);
	 
	 }
	 function updateConfirm(con){
	 //res=con;
	 //alert(res);
	 var shiftid=$("#shiftId1L").val();
	 var remId=$("#remId").val();
	 var wingid=$("#wingid").val();
	 if(con=='yes')
	 {
	 $.ajax({
	  type : "post",
	   	    url : "/over2cloud/view/Over2Cloud/LongPatientStay/deleteEmpByTrash.action",
	   	    data :  { trashEmpId: $("#trashMulDiv").text(),wingIds:wingid,shiftId:shiftid },
	   	    async:false,
	   	    success : function(data) {
	   	    	$("#trashMulDiv").empty();
	   			$('#message').empty();
	    	    $('#message').append('Data Removed Successfully!!!').css('color','black').css('display','block');
	    	    $("#message").fadeIn('fast');
	    	  $("#message").fadeOut(4000);
	    	    
	          	},
	   	    error: function() {
	               alert("error");
	           }
	   	 });
	 }
	 else if(con=='no')
	 
	 {
	 $.ajax({
	  type : "post",
	   	    url : "/over2cloud/view/Over2Cloud/LongPatientStay/deleteEmpByTrash.action?viewFlag=ALL",
	   	    data :  { trashEmpId: remId ,shiftId:shiftid },
	   	    success : function(data) {
	   	     $('#'+remId+"-"+wingid).fadeOut(200);
	   	   	$('#'+remId+"-"+wingid).remove();
	   	 $('#message').empty();
	    	    	$('#message').append('Data Removed Successfully!!!').css('color','black').css('display','block');
	    	    $("#message").fadeIn('fast');
	    	  $("#message").fadeOut(4000);
	    	    
	          	},
	   	    error: function() {
	               alert("error");
	           }
	   	 });
	 
	 }
	
	 unmodifier();
	 $("#save").attr('onclick','').css('opacity','.3');
	 getshiftmappingdata($("#shiftId1L").val());
	 }
	 
	 function removeEmp(realid)
	 {
	 //removing if mapped remove before saving

	// alert(realid);
	 
	 var remId=realid.split("kk")[0];
	 var wingid=realid.split("kk")[1];
	 myFunction($("#"+remId+"-"+wingid).text(),remId,wingid);
	 
	}
	 
	 function deleteshift1(){
	 con=confirmReset();
	 if(con=='yes'){
	 deleteShift('all');
	 }
	 
	 }
	 
	 function deleteShift(checkdel){
	 var shiftid=$("#shiftId1L").val();
	 var url1='';
	 if(checkdel=='shift')
	 {
	 url1="/over2cloud/view/Over2Cloud/LongPatientStay/deleteShift.action?viewFlag=empOnly";
	 }else if(checkdel=='all')
	 {
	 url1="/over2cloud/view/Over2Cloud/LongPatientStay/deleteShift.action?"; 
	 }
	
	 $.ajax({
	type : "post",
	   	    url : url1,
	   	    data :  { shiftId: shiftid,deptId:$("#department").val()},
	   	    success : function(data) {
	   	 $('#message').empty();
	    	    	$('#message').append(data).css('color','black').css('display','block');
	    	    $("#message").fadeIn('fast');
	    	  $("#message").fadeOut(4000);
	    	    
	          	},
	   	    error: function() {
	               alert("error");
	           }
	   	 });
	 //call view shift
	 ViewShif();
	 refresh();
	 }
	 function confirmReset(){
	 var r = confirm("Are you sure to take this action?");
	 if (r == true) {
	     return 'yes';
	 } else {
	     return 'no';
	 }
	 
	 }
	 
	 function resetShift(){
	con = confirmReset();
	 if(con=='yes'){
	 deleteShift('shift');
	 }
	 }
	 
function refresh()
{
	 var deptId= $("#department").val();
 	  $.ajax({
   	    type : "post",
   	    url : "/over2cloud/view/Over2Cloud/LongPatientStay/getfloorwingsdetail.action",
   	    async:false,
   	    success : function(data) {
   	    	$("#wings").empty();
   	    	dynamiccount=data.length;
   	 //  	//console.log(data);
   	   	$.each(data, function() 
   	   	{
   	   	
	    	   	  $.each(this, function(k, v)
	    	   	  {
	    	   	//	//console.log(k);
	    	  	$("#wings").append('<div style="width:98%; background: #e6e6e6  ;box-shadow: 2px 1px 4px -1px rgb(180, 186, 189);font-weight: bold;" align="center"><span style="float:left;margin:3px 0px 0px 3px;"><img id="'+k.split(" ")[0]+'kkkshow1" title="Expand" style="cursor:pointer;" class="ui-icon tree-wrap-ltr ui-icon-circlesmall-plus" onclick="showHideLoc1(this.id)" /><img id="'+k.split(" ")[0]+'kkkhide1" title="Collapse" style="cursor:pointer;display:none;" class="ui-icon ui-icon-circlesmall-minus tree-wrap-ltr"  onclick="showHideLoc1(this.id)" /></span>'+k+'<span id="'+k.split(" ")[0]+'countSpan"></span></div>');
   	    	$("#wings")
   	.append(
   	'<div id="'+k.split(" ")[0]+'divid1" style="display:none;width:auto;border-radius:6px;"></div>');
	    	   	
	    	   	 $.each(this, function(k1, v1)
	    	   	 {
	     	   	//	//console.log(v);
	     	  	 	$("#"+k.split(" ")[0]+"divid1").append('<div id="'+this.wingid+'wing" style="" align="center"><ul><p id="'+floorNA+'"></p><b>'+this.wingname+'</b></ul></div>');
	     	  	// appendsavedemp(this.wingid);
	                 	adddivobj(this.wingid+'wing');
	                 	dragdrop(this.wingid+'wing');
	     	   	  	});
	    	   	 	 });
   	   	});
   	      	    	
          	},
   	    error: function() {
               alert("error");
           }
   	 });
 	  
}

function addShift()
{
	//$("#shiftaddDiv").show();
	var deptId=$("#department").val();
	if(deptId!='-1'){
	if(refreshFlag == false){
	refresh();
	}
	
	clearShiftInfo();
	$("#shiftViewDiv").hide();
	$("#maxmizeViewDialog").dialog({title:'Add Shift',height:256,width:440,dialogClass:'transparent'});
	
	//alert("ID to save"+shift_employeemapped_wing);
	$("#maximizeDataDiv").html("<br><br><br><br><br><center><img src=images/lightbox-ico-loading.gif></center>");
	
	 $.ajax({
	  type : "post",
	  	    url : "/over2cloud/view/Over2Cloud/LongPatientStay/saveEmpWing.action",
	  	    data:{shiftemployeemappedwing:shift_employeemapped_wing.toString(),deptId:deptId},
	  	    	success : function(data) {
	  	    	//alert(data);
	  	    	$("#maximizeDataDiv").empty();
	  	 	    	  	$("#maximizeDataDiv").html(data);
	  	    	  	
	  	 	    	 $("#maxmizeViewDialog").dialog('open');
	         	},
	  	    error: function() {
	              alert("error");
	          }
	  	 });
	}else{
	$('#message').empty();
	    	$('#message').append("Please Select Department").css('color','red').css('display','block');
	   	 	$("#message").fadeIn('fast');
	 	 	$("#message").fadeOut(4000);
	}
	
	
}

function saveAs(){
	//$("#shiftaddDiv").show();
	$("#shiftViewDiv").hide();
	$("#maxmizeViewDialog").dialog({title:'Add Shift',height:256,width:440,dialogClass:'transparent'});
	$("#maxmizeViewDialog").dialog('open');
	//alert("ID to save"+shift_employeemapped_wing);
	$("#maximizeDataDiv").html("<br><br><br><br><br><center><img src=images/lightbox-ico-loading.gif></center>");
	//console.log(shift_employeeIdsForSaveAs);
	 $.ajax({
	  type : "post",
  	    url : "/over2cloud/view/Over2Cloud/LongPatientStay/saveEmpWing.action",
  	    data:{shiftemployeemappedwing:shift_employeeIdsForSaveAs.toString(),deptId:$("#department").val()},
  	    	success : function(data) {
  	    	//alert(data);
  	    	  	$("#maximizeDataDiv").empty();
  	    	  	$("#maximizeDataDiv").html(data);
   	    
         	},
  	    error: function() {
              alert("error");
          }
  	 });
	 shift_employeeIdsForSaveAs=[];
	 
	 $("#saveAs").attr('onclick','').css('opacity','.3');
	 $("#save").attr('onclick','').css('opacity','.3');
	
}

function fetchLiveShift(){
	$.ajax({
	  type : "post",
 	    url : "/over2cloud/view/Over2Cloud/LongPatientStay/viewShift.action?deptId="+$("#department").val()+"&viewFlag=live",
 	    	success : function(data) {
 	    	if(data.length!=0){
 	    	$(".shiftTagL").css('display', 'inline-block');
 	    	 	$("#shiftId1L").val(data[0].id);
 	          	$("#shiftName1L").html(data[0].shiftName).css('color','black');
 	                      	$("#shiftFrom1L").html(data[0].from);
 	                      	$("#shiftTo1L").html(data[0].to);
 	    	}
        	},
 	    error: function() {
             alert("error");
         }
 	 });
}


 function saveShift(){
	unmodifier();
	$.ajax({
	  type : "post",
	    url : "/over2cloud/view/Over2Cloud/LongPatientStay/addShiftWithWingMap.action",
	    data:{shiftemployeemappedwing:$("#shiftemployeemappedwing").val(),deptId:$("#deptId").val(),shift_name:$("#shift_name1").val(),fromShift:$("#fromShift").val().trim(),toShift:$("#toShift").val().trim()},
	    async:false,
	    success : function(data) {
	    	//alert(data);
	    	 $('#message').empty();
	   	    	$('#message').append(data).css('color','black').css('display','block');
	   	   	 	$("#message").fadeIn('fast');
	   	 	 	$("#message").fadeOut(4000);
	    	  	
	   	 	fetchLiveShift();
	       	},
	    error: function() {
	            alert("error");
	        }
	 });
	$("#maxmizeViewDialog").dialog('close');
} 

 function ViewShif(){
	//$("#maxmizeViewDialog").dialog({title:'Shifts',height:256,width:240,dialogClass:'transparent'});
	//$("#maxmizeViewDialog").dialog('open');
	//alert("ID to save"+shift_employeemapped_wing);
	$("#dpetEmpDiv").hide();
	//$("#shiftaddDiv").hide();
	$("#shiftViewDiv").show();
	$("#shiftViewDiv").html("<br><br><br><br><br><center><img src=images/lightbox-ico-loading.gif></center>");
	$.ajax({
	  type : "post",
 	    url : "/over2cloud/view/Over2Cloud/LongPatientStay/viewShift.action?deptId="+$("#department").val(),
 	    	success : function(data) {
 	    	//console.log(data);
 	    	  	//$("#catalog ul").empty();
 	    	  	var temp='';
 	    	  	$(data).each(function(index)
	    	   	 {
	     	   	//	console.log(v);
	     	  	 	temp+='<li class="viewShift" id="'+this.id+'" onclick="getshiftmappingdata('+this.id+')"><a href="#">'+this.shiftName+'&nbsp;&nbsp;&nbsp;('+this.from+' to '+this.to+')<a href="#"><span class="editShift" style="float:right" id="'+this.id+'" onclick="editShift(this.id)"><img alt="edit_image" src="images/profileEditIcon.png"/></span></a></li><hr/>';
	     	   	  	});
 	    	//  	temp+=' <center><button  href="#" onclick="save()" title="Save" style="">Save</button></center>';
 	    	   $("#shiftViewDiv").html(temp);
  	    
        	},
 	    error: function() {
             alert("error");
         }
 	 });
	unmodifier();
}

function editShift(shiftId){
//	alert(shiftId);
	$("#shiftId1L").val(shiftId);
	$("#dpetEmpDiv").show();
	//$("#catalog ul").empty();
	democall($("#department").val(),'F');
	
	$("#maxmizeViewDialog2").dialog({title:'Edit Shift',height:256,width:440,dialogClass:'transparent'});
	
	//alert("ID to save"+shift_employeemapped_wing);
	$("#maximizeDataDiv2").html("<br><br><br><br><br><center><img src=images/lightbox-ico-loading.gif></center>");
	//console.log(shift_employeeIdsForSaveAs);
	 $.ajax({
	  type : "post",
  	    url : "/over2cloud/view/Over2Cloud/LongPatientStay/saveEmpWing.action",
  	    data:{viewFlag:"F"},
  	    	success : function(data) {
  	    	//alert(data);
  	    	  	$("#maximizeDataDiv2").empty();
  	    	  	$("#maximizeDataDiv2").html(data);
  	    	  $("#maxmizeViewDialog2").dialog('open');
         	},
  	    error: function() {
              alert("error");
          }
  	 });
	
	
}

function editShiftTime(){
	$("#shiftViewDiv").hide();
	 $.ajax({
	  type : "post",
	  	    url : "/over2cloud/view/Over2Cloud/LongPatientStay/editShiftTime.action",
	  	    data:{shiftId:$("#shiftId1L").val(),fromTime:$("#fromShift").val().trim(),toTime:$("#toShift").val().trim()},
	  	    async:false,
	  	    	success : function(data) {
	  	    	//alert(data);
	  	    	$('#message').empty();
	   	    	$('#message').append(data).css('color','black').css('display','block');
	   	   	 	$("#message").fadeIn('fast');
	   	 	 	$("#message").fadeOut(4000);
	   	    
	         	},
	  	    error: function() {
	              alert("error");
	          }
	  	 });
	 $("#maxmizeViewDialog2").dialog('close');
	 democall($("#department").val(),'T');
	 ViewShif();
}
function getshiftmappingdata(shiftId){
	refresh();
	appendsavedemp(shiftId,wingIds,"T");
	$("#shiftId1L").val(shiftId);
	$("#deleteShift").attr('onclick','deleteshift1()').css('opacity','1');
}


function save(){
	//console.log(shift_employeemapped_wing);
	unmodifier();
	$("#shiftViewDiv").hide();
	//alert(shift_employeemapped_wing);
	 $.ajax({
	  type : "post",
  	    url : "/over2cloud/view/Over2Cloud/LongPatientStay/addShiftWithWingMapSave.action",
  	    data :  { shiftID: $("#shiftId1L").val(),shiftemployeemappedwing:shift_employeemapped_wing.toString() },
  	    success : function(data) {
  	 $('#message').empty();
   	    	$('#message').append(data).css('color','black').css('display','block');
   	    $("#message").fadeIn('fast');
   	  $("#message").fadeOut(4000);
   	    
         	},
  	    error: function() {
              alert("error");
          }
  	 });
	 shift_employeemapped_wing=[];
	 $("#save").attr('onclick','').css('opacity','.3');
}

function viewMappingCurrnet(){
	$("#dpetEmpDiv").show();
	//$("#shiftaddDiv").hide();
	$("#shiftViewDiv").hide();
}

  </script>
    <style type="text/css">
     
        .draggable-demo-cart
        {
            border: 2px dashed #aaa;
            padding: 5px;
            width: 232px;
            margin: auto;
        }
        .draggable-demo-shop 
        {
          float:left;
	overflow:auto;
	width:96%;
	padding:1%;
	margin:1%;
	border:1px solid #e4e4e5;
	-webkit-border-radius: 6px;
	-moz-border-radius: 6px;
	border-radius: 6px;
	-webkit-box-shadow: 0 1px 4px rgba(0,0,0,.10);
	-moz-box-shadow: 0 1px 4px rgba(0,0,0,.10);
	box-shadow: 0 1px 4px rgba(0,0,0,.10);
        }
        .draggable-demo-catalog
        {
            position: relative;
            border: 1px solid #bbb;
            width: 100%;
            height: auto;
            float: left;
        }
        .draggable-demo-product-image
        {
            width: 150px;
        }
        
	  .draggable-demo-product
	   {
	padding: 5px;
	border: 1px solid #888;
	width: 50px;
	height: 50px;
	background-color: #fff;
	 float: left;
	margin: 5px;
	}
	
        .draggable-demo-product img
         {
	width: 48px;
	height: 30px;
	border: 1px solid #aaa;
	border-top-width: 0px;
	outline-width: 15px;
	}
        .draggable-demo-product-header
        {
            border: 1px solid #888;
            height: 21px;
            border-bottom-width: 0px;
            font-size: 13px;
            position: relative;
            text-align: center;
        }
        .draggable-demo-product-header-label
        {
            margin-top: 3px;
        }
        .draggable-demo-product-price
        {
            position: absolute;
            top: 124px;
            left: 6px;
            width: 113px;
            text-align: center;
            font-family: Verdana;
            font-size: 11px;
            display: none;
            height: 16px;
            border-top: 1px solid #888;
            border-bottom: 1px solid #fff;
        }
        .draggable-demo-title
        {
            font-size: 12px;
            text-align: center;
            padding: 7px;
            margin: 5px;
            font-weight: bold;
            border: 1px solid #aaa;
            background: -webkit-gradient(linear, 0 0, 0 100%, from(#f8f8f8), to(#eaeaea));
        }
        .draggable-demo-cart-wrapper
        {
             margin-left: 5px;
            border: 1px solid #aaa;
            height: auto;
            width: 99%;
        }
        #wings div{
            float:left;
            border: 1px solid #aaa;
            height: auto;
            width: 195px;
            margin: 5px 5px 5px 5px;
            box-shadow:0 2px 10px 0 rgba(4, 0, 0, 0.61);
        }
        
         #floor ul b{
            background:-webkit-gradient(linear, 0 0, 0 100%, from(#f8f8f8), to(#eaeaea));
        }
        h3{
        background:-webkit-gradient(linear, 0 0, 0 100%, from(#f8f8f8), to(#eaeaea));
        }
          #wings div.border{
            border-width: 1px;
            border: dotted;
            
        }
        
        
        .draggable-demo-total
        {
            font-size: 17px;
            font-family: Verdana;
            margin: 6px;
            margin-top: 7px;            
            padding: 7px;
        }
        #catalog ul li{
        
	        
	       height: 10px;
	  padding: 2%;
	  color: black;
	  font-size: 11px;
	  margin-bottom: 1px;
	  font-family: Verdana;
	  cursor: move;
	  border-radius: 3px;
	  margin: 20px 2px 2px 2px;
	  width:150px;
	  float: left;
        }
        
         #wings ul li{
        
         border: 1px solid #aaa;
         height: auto;
         padding: 1%;
         color: black;
         font-size: 12px;
         margin-bottom: 1px;
         font-family: Verdana;
           border-radius:3px;
       margin: 2px 2px 2px 2px;
         
      
        }
         #wings ul li:HOVER{
        
        background: -webkit-gradient(linear, 0 0, 0 100%, from(#f8f8f8), to(#eaeaea));
         border: 1px solid #aaa;
         height: auto;
         padding: 1%;
      
        }
        #catalog ul li:HOVER{
       
         
        }
        
        #shop {
        overflow: auto;
        }
        .closebutton{
         border: 1px solid #aaa;
         width: 5px;
         height: 5px;
         display: none;
        }
        
        #trashdiv img.zoom1{
        height:120px;
        width: 120px;
        opacity:0.5;
        }
        .deco, .next , .prev{
        
          float: left;
	  border: 1px solid rgb(233, 233, 233);
	  padding: 6px 9px 3px 8px;
	  border-radius: 5px;
	  margin-left: 7px;
	        
	        }
       #pagination a{
       color:black;
       }
 .closeSpan
     {
     width: 9px;
  height: 9px;
  float: right;
    margin: 6px 0px 0px 0px;
  
  }
  .shiftTag{
  display: none;
  }
  .shiftTagL{
  display: none;
  }
  #modifier{
  display: none;
  }
  .maxPic:HOVER {
	width: 40px;
	height: 40px;
}
 #skillInfo tr:nth-child(even) {background: #CCC}
#skillInfo tr:nth-child(odd) {background: #FFF}

    </style>
</head>
<body>
<sj:dialog
          id="maxmizeViewDialog"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          modal="true"
          >
          <div id="maximizeDataDiv"></div>
</sj:dialog>
<sj:dialog
          id="maxmizeViewDialog1"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          modal="true"
          >
          <div id="maximizeDataDiv1"></div>
</sj:dialog>
<sj:dialog
          id="maxmizeViewDialog2"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          modal="true"
          >
          <div id="maximizeDataDiv2"></div>
</sj:dialog>
<div class="list-icon">
	 <div class="head">Location</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Map</div> 
</div>

<div style="display: none;" id="identify"></div>
<div style="display: none;" id="hiddenemp"></div>
<div style="display: none;" id="hiddenlevel"></div>
<div style="display: none;" id="hiddenempname"></div>
<div style="display: none;" id="remId"></div>
<div style="display: none;" id="wingid"></div>
<div style="display: none;" id="trashMulDiv"></div>


    <div id="shop" class="draggable-demo-shop jqx-rc-all" align="center" >
    <table align="right" >
    	<tbody>
    	 	<tr>
    	 	
    	 	<td align="left" style="  background: rgb(236, 246, 253);margin-right: 4px;box-shadow: 2px 1px 4px -1px rgb(180, 186, 189);">
	    	 	<s:hidden id="shiftId1" theme="simple" ></s:hidden>
	    	 	<i class="shiftTag" id="ShiftType"><b style="color:green;">Active Shift: </b></i>&nbsp;&nbsp;&nbsp;&nbsp;
	    	 	<span id="shiftName1" style="display: inline-block;"></span>&nbsp;&nbsp;
	    	 	<span class="shiftTag"><<</span>&nbsp;<span id="shiftFrom1" style="display: inline-block;"></span>
	    	 	&nbsp;<span class="shiftTag">>></span>&nbsp;&nbsp;<span class="shiftTag"><<</span>&nbsp;
	    	 	<span id="shiftTo1" style="display: inline-block;"></span>&nbsp;<span class="shiftTag">>></span>
    	 	</td>
    	 	
    	 	
    	 	
    	 	</tr>
    	 	<tr>
    	 	<td align="left" style="  background: rgb(236, 246, 253);margin-right: 4px;box-shadow: 2px 1px 4px -1px rgb(180, 186, 189);">
	    	 	<s:hidden id="shiftId1L" theme="simple" ></s:hidden><i class="shiftTagL" id="ShiftTypeL">Loaded Shift: </i>&nbsp;&nbsp;&nbsp;&nbsp;
	    	 	<span id="shiftName1L" style="display: inline-block;"></span>&nbsp;&nbsp;<span class="shiftTagL"><<</span>&nbsp;
	    	 	<span id="shiftFrom1L" style="display: inline-block;"></span>&nbsp;<span class="shiftTagL">>></span>&nbsp;&nbsp;
	    	 	<span class="shiftTagL"><<</span>&nbsp;<span id="shiftTo1L" style="display: inline-block;"></span>&nbsp;<span class="shiftTagL">>></span>
	    	 	<span id="modifier" >(Shift has been modified.) *</span>
    	 	</td>
    	 	</tr>
    	</tbody>
    	 	
    </table>
       
    <table width="100%">
    	<tbody>
    	 	<tr>
    	 	<td width="30%" valign="top">
	    	 <div id="message"></div>
	        <div id="catalog" class="draggable-demo-catalog jqx-rc-all">
	        <div class="draggable-demo-title jqx-rc-t" >Employees
	         <button  href="#" onclick="viewMappingCurrnet()"  title="View Mapping For Selected Department" style="float: right;"><img alt="Emp_image" src="images/client_activity.png" width="15px" height="15px"/></button>
	        </div>
	        <div style="margin-left: 10px" > Select Department:  <s:select id="department" cssStyle="width:69%;;margin-bottom:10px;margin-left:1px;" list="{'No Data'}" onchange="democall(this.value,'T');" headerKey="-1" headerValue="Select Department" theme="simple"></s:select></div>
	 	
	 <s:textfield name="shift_name"  id="shift_name" theme="simple" cssClass="button" cssStyle="width:89%;margin-left:5px;  margin-bottom: 9px;" placeholder="Enter Data" onkeyup="searchdelay(this.value);"/>
	<!-- <div id="shiftaddDiv"></div> -->
	<div id="dpetEmpDiv"  style="float:right;width: 100%; height: 448px;  margin-bottom: 20px;" >
	<ul>
	</ul>
	</div>
	<div id="shiftViewDiv" style="margin: 53px 21px 16px 21px;overflow: auto;height: 400px;"></div>
	
	
	
	        </div>
    	 	</td>
    	 	
    	 	<td valign="top">
    	 	     
	    	 	<%-- <s:textfield id="shiftFrom2"  theme="simple" cssStyle="color:blue;float:left;  width: 8%;" />
	    	 	<s:textfield id="shiftTo2"  theme="simple" cssStyle="color:blue;float:left;  width: 8%;"/> --%>
	    	 	<div class="draggable-demo-cart-wrapper jqx-rc-all"><s:hidden id="shiftId1" theme="simple" ></s:hidden>
	            <div class="draggable-demo-title jqx-rc-t" onclick="">Locations 
	         <%--     <sj:div cssClass="button"  cssStyle="float:left" href="#" onclick=" dragdrop('catalog');" title="Add Employee" bindOn="button">ADD</sj:div>
	             <sj:div cssClass="button"  cssStyle="float:left" href="#" onclick="removeEmp()" title="Delete Employee"><img alt="Delete" src="images/Delete_Icon.png" width="15px" height="15px"></img></sj:div> --%>
	             <!-- <button  href="#" onclick="removeEmp()" title="Remove Employee" style="float: left;margin-right:3px;"><img alt="Delete_image" src="images/Delete_Icon.png" width="13px" height="13px"/></button> -->
	            <!--<button id="ddShift"  onclick="addShift()"   style="float: left;"><img alt="Refresh_image" src="images/add.png" width="15px" height="15px"/></button>
	             <button id="viewShift" onclick="ViewShif()" title="View Shifts" style="float: left;"><img alt="View_shift_Image" src="images/Company.png" width="15px" height="15px"/></button>
	           
	            --><button  id="save" onclick="" title="Save" style="float: left;opacity:.3;"><img alt="Save_image" src="images/save-ico.ico" width="15px" height="15px"/></button>
	            <button  id="saveAs" onclick="" title="Save As" style="float: left;opacity:.3;"><img alt="Save_image" src="images/save-As-icon.ico" width="15px" height="15px"/></button>
	             <button  id="deleteShift" onclick="" title="Delete Shift" style="float: left;opacity:.3;"><img alt="Save_image" src="images/remove.png" width="15px" height="15px"/></button>
	             <button id="reset" onclick="resetShift()" title="Reset" style="float: left;"><img alt="Refresh_image" src="images/refresh_cloud.png" width="15px" height="15px"/></button>
	             
	     
	            </div>
	          
	               <div id="wings" style="height: 543px;overflow: auto;" >
	              
	               </div>
	               <div id="trashdiv"><img alt="Trash" src="images/Delete_Icon.png" width="100px" height="100px" style="display: none;float: right;position: relative;">
	               </div>
	        </div>
    	 	</td>
    	 	
    	 	</tr>
    	</tbody>
    </table>
     
        
        <div style="clear: both;"></div>
    </div>
   
</body>
</html>