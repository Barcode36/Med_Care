
 <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<html lang="en">
<head>


   <meta charset="utf-8">
  <title>Draggable Employee Mapping </title>
     <link rel="stylesheet" href="template/theme/mytheme/jquery-ui.css">
  <script src="js/jquery.ui.draggable.js"></script>
   <script src="js/jquery.ui.droppable.js"></script>
     <script type="text/javascript">
    
     /*
      * 
      *	written Kamlesh	
      *	Copyright (c) 2015 Kamlesh Kumar Yadav
      *	Built for jQuery library
      *
      */

     
(function($) {
		  
	$.fn.easyPaginate = function(options){

		var defaults = {				
			step: 4,
			delay: 100,
			numeric: true,
			nextprev: true,
			auto:false,
			loop:false,
			pause:4000,
			clickstop:true,
			controls: 'pagination',
			current: 'current',
			randomstart: false
		}; 
		
		var options = $.extend(defaults, options); 
		var step = options.step;
		var lower, upper;
		var children = $(this).children();
		var count = children.length;
		var obj, next, prev;		
		var pages = Math.floor(count/step);
		var page = (options.randomstart) ? Math.floor(Math.random()*pages)+1 : 1;
		var timeout;
		var clicked = false;
		
		function show(){
			clearTimeout(timeout);
			lower = ((page-1) * step);
			upper = lower+step;
			$(children).each(function(i){
				var child = $(this);
				child.hide();
				if(i>=lower && i<upper){ setTimeout(function(){ child.fadeIn('fast') }, ( i-( Math.floor(i/step) * step) )*options.delay ); }
				if(options.nextprev){
					if(upper >= count) { next.fadeOut('fast'); } else { next.fadeIn('fast'); };
					if(lower >= 1) { prev.fadeIn('fast'); } else { prev.fadeOut('fast'); };
				};
			});	
			$('li','#'+ options.controls).removeClass(options.current);
			$('li[data-index="'+page+'"]','#'+ options.controls).addClass(options.current);
			
			if(options.auto){
				if(options.clickstop && clicked){}else{ timeout = setTimeout(auto,options.pause); };
			};
		};
		
		function auto(){
			if(options.loop) if(upper >= count){ page=0; show(); }
			if(upper < count){ page++; show(); }				
		};
		
		this.each(function(){ 
			
			obj = this;
			
			if(count>step){
								
				if((count/step) > pages) pages++;
				
				var ol = $('<ol id="'+ options.controls +'"></ol>').insertAfter(obj);
				
				if(options.nextprev){
					prev = $('<li class="prev"><<</li>')
						.hide()
						.appendTo(ol)
						.click(function(){
							clicked = true;
							page--;
							show();
						});
				};
				
				if(options.numeric){
					for(var i=1;i<=pages;i++){
					$('<li data-index="'+ i +'">'+ i +'</li>')
						.appendTo(ol)
						.click(function(){	
							clicked = true;
							page = $(this).attr('data-index');
							show();
						});					
					};				
				};
				
				if(options.nextprev){
					next = $('<li class="">>></li>')
						.hide()
						.appendTo(ol)
						.click(function(){
							clicked = true;			
							page++;
							show();
						});
				};
			
				show();
			};
		});	
		
	};	

})(jQuery);
     </script>
  <script type="text/javascript" >
  var count=0,idvalidate=[];
  
  
  $(document).ready(function () {
  //getting free employee for select department from compliance contact
  
  
 
	  
  var module = $("#moduleName").val();
  var dept = $("#subType").val();
    $.ajax({
    	    type : "post",
    	    url : "view/Over2Cloud/EventReminders/getfreeEmp.action?module="+module+"&dept="+dept,
    	    success : function(data) {
        //   	console.log(data);
        //alert("Loding...");
              	$(data).each(function(index)
          		{
              		
              	$("#free ul").append('<li id="'+this.id+'lis" onmouseover="showclosebutton('+this.id+')" onmouseout="hideclosebutton('+this.id+')" ondblclick="getempDet('+this.id+');">'+this.name+'<a href="#"> <div id="'+this.id+'cls" class="closediv" onclick="removeemp('+this.id+',false)">x</div></a></li>');
              	
          		});
              	dragdrop();
                makefloordroppable('l1to');
                makefloordroppable('l2to');
                makefloordroppable('l3to');
                makefloordroppable('l4to');
                makefloordroppable('l5to');
                makefloordroppable('l1cc');
                makefloordroppable('l2cc');
                makefloordroppable('l3cc');
                makefloordroppable('l4cc');
                makefloordroppable('l5cc');
                makefloordroppable('free');
               // $('ul#feeEmpLists').easyPaginate();
                
                
                
          	  
          	  $('ul#feeEmpLists').easyPaginate({
          	  step:12
          	});
           		},
    	    error: function() {
                alert("error");
            }
    	 });
  //to get floor or wings div for selected department
  
  //getting ids of employee
	 
    
	 });
	//making li droppable
	 function dragdrop(){
		/*  $('#'+dropid1+' ul li').each(function(i)
					{
    	  idvalidate.push( $(this).attr('id')); 
					});	
      */
		 
			
		 $("li").draggable({
		    	revert:false,
		    	 opacity:0.9,	
		    	containment:'#escmax',
		        cursor: "crosshair",
	          grid: [ 50, 20 ],
	          start:function (){
	        	  contents= $(this).text();
	        	  id=$(this).val();
	        	
	       
	          },
	          stop:function (){
	        	  $(this).removeAttr("style");
	       
	          }
	          
		    	
		    });
	 }
	function dragdrop1(id){
		 $("#"+id).draggable({
		    	revert:false,
		    	 opacity:0.9,	
		    	containment:'#escmax',
		        cursor: "move",
	          grid: [ 50, 20 ],
	          start:function (){
	        	  contents= $(this).text();
	        	  id=$(this).val();
	        	
	       
	          },
	          stop:function (){
	        	  $(this).removeAttr("style");
	       
	          }
	          
		    	
		    });
	}
  function removeemp(id,addFlag){
	  if(addFlag){
		//  alert("true");
		  
		  $("#"+id+"lis").fadeOut('slow').remove();
		  
	  }else{
		 //alert("false");
	  $("#"+id+"lis").fadeOut('slow').remove();
	  }
	 
  }
  function showclosebutton(id){
	 // alert("called "+id);
	  $("#"+id+"cls").css('display','inline')
	  .css('float','right')
	  .css('margin','2px 1px 0px 0px')
	  .css('border-radius','3px')
	  .css('background','rgb(194, 194, 194)')
	  .css('padding','0px 1px 0px 2px')
	  .css('font-size','smaller');
	  
	
	 
	                
  }
  
  function hideclosebutton(id){
		 // alert("called "+id);
		  $("#"+id+"cls").css('display','none');
		                
	  }
  
function maptomultiplewing(emp){
	$('#hiddenemp').text(emp.id);
	var temp=$('#'+emp.id).text();
	var str  = temp.split(' ');
	var deptlevel=str[str.length-1];//level
	$('#hiddenlevel').text(deptlevel);
	$("#maxmizeViewDialog").dialog({title:'Map '+$('#'+emp.id).text()+' to Wings',height:400,width:240,dialogClass:'transparent'});
	$("#maxmizeViewDialog").dialog('open');
	 $.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Escalation_Conf/mappingwing.jsp",
	    success : function(data) 
	    {
	    	
			$("#maximizeDataDiv").html(data);
	 },
	   error: function() {
	        alert("error");
	    }
	 }); 
}
    function democall(selval){
  //	  alert(selval);
  	  
  	  $.ajax({
      	    type : "post",
      	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Escalation_Conf/BeforeConfigEscContact1.action?deptId="+selval,
      	    success : function(data) {
      	    	//alert(JSON.stringify(data));
                     		$('#catalog ul').empty();
                     		var temp="";
                        	$(data).each(function(index)
                    		{
                        	temp +='<li id="'+this.id+'" ondblclick="maptomultiplewing(this);">'+this.name+'</li>';
                    		});
                    		//alert(temp);
                    	    $("#catalog ul").append(temp);
                    	    $("#catalog ul").sortable({
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
  	  definecontent(selval);
  	  
    }
    var dynamiccount;
    var addFlag=true;
    var addFlag1=true;
    //to define wings and floor according to mapping of deparment 
    function definecontent(selval){
  	  $.ajax({
    	    type : "post",
    	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Escalation_Conf/getfloorwingsdetail.action?deptId="+selval,
    	    success : function(data) {
    	    	$("#wings").empty();
    	    	dynamiccount=data.length;
    	    //	console.log(data);
    	    	if(data[0].flag=='wing'){
    	    		$('#identify').text('wing');
    	    		var ct=0;
    	    		var oldhead;
        	    	$(data).each(function(index)
                    		{
        	    		
             	    	
        	    		
        	    			
        	    			if(ct==0){
                        		$("#wings").append('<div style="width:98%; background:-webkit-gradient(linear, 0 0, 0 100%, from(#f8f8f8), to(#eaeaea));">'+this.floorname+'</div>');
                        		ct++;
                        		
                        	}else {
                        		//alert(index);
                        		if(data[(index-1)].floorname===data[index].floorname){
                        			
                        		}else{
                        			$("#wings").append('</br><div style="width:98%; background:-webkit-gradient(linear, 0 0, 0 100%, from(#f8f8f8), to(#eaeaea));">'+this.floorname+'</div>');
                        		
                        		}
                        	}
                        	
                        	$("#wings").append('<div id="'+this.wingid+'wing" style=""><ul><p id="'+this.floorid+'"></p><b>'+this.wingname+'</b></ul></div>');
        	    	
        	    					appendsavedemp(this.wingid);
  	                          	adddivobj(this.wingid+'wing');
  	                          	dragdrop(this.wingid+'wing');
                        	
                    		});
    	    	}else{
    	    		$(data).each(function(index)
                    		{
    	    			$('#identify').text('floor');
  	                          	
  	                          	$("#wings").append('<div id="'+this.floorid+'floor"><ul><p id="'+this.floorid+'"></p><b>'+this.floorname+'</b></ul></div>');
  	                          	appendsavedempforfloor(this.floorid);
  	                          
  	                          	makefloordroppable(this.floorid+'floor');
                        
                    		});
    	    	}
    	    	//$("#wings").append('</div>');
    	    	/* $('#wings').accordion({
    	    	   
               });
    	    	*/
    	    //	alert("trace");
    	    	
           		},
    	    error: function() {
                alert("error");
            }
    	 });
  	  
  	  
    }
    
    //composing header for floor Name
    function headercompose(floorname){
  	  var temp1=floorname;
  	  var temp2=temp1;
  	  $("#wings").append('<h3>'+floorname+'</h3>');
    }
    
    
    //appending saved emp for floor
    function appendsavedempforfloor(floorid1){
  	  $.ajax({
    		type:"post",
    		url: "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Escalation_Conf/getmappedemployee.action?floorName="+floorid1+'&deptId='+$('#department').val(),
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
    
    function appendsavedemp(wingid1){
  		//alert(wingid1);
  		//getting employee saved in data base for respective floor and wings
      	$.ajax({
      		type:"post",
      		url: "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Escalation_Conf/getmappedemployee.action?wingName="+wingid1+'&deptId='+$('#department').val(),
      		success: function(data){
      			$(data).each(function(index)
                  		{
      				//alert(wingid1);
      				$('#'+wingid1+'wing ul').append('<li id="'+this.id+'">'+this.name+'</li>');
                  		});
      			
      		
      		},
      		error:function(){
      		   alert("error");
      		}
      				
      	});
    }
     
  
  	 
  	  
 
  	  
  	  //floor droppable
  	function makefloordroppable(divid){
  		  $('#'+divid).droppable({
  			  hoverClass:'border',
  			  accept:'li',
  			  drop:function(event,ui){
  				
  			 	 //getting id of all li inside div
  				  var liIds = $('#'+$(this).attr('id')+' li').map(function(i,n) {
						 return $(n).attr('id');
					}).get().join(',');
  				  //alert("lids"+liIds);
  				 //appending li to dropped div and validating too
  				
			      		//	 alert("Mapping");
			      			
			      			var dropid2=$(ui.draggable).attr('id');
			      			var realId= dropid2.substring(0,dropid2.indexOf("l"));
			      		//	alert("drop2 "+dropid2);
			      			var dropname2=$(ui.draggable).text();
			      		//	alert("dropName2 "+dropname2);
			      		  $("#"+dropid2).fadeOut('slow').remove();
			      		  $("#"+divid+' ul').append('<li id="'+dropid2+'" onmouseover="showclosebutton('+realId+')" onmouseout="hideclosebutton('+realId+')">'+dropname2.substring(0,dropname2.lastIndexOf("x"))+'<a href="#"> <div id="'+realId+'cls" class="closediv" onclick="removeemp('+realId+',true)">x</div></a></li>');
			      		dragdrop1(dropid2);
			      		  
			      		
			      			
			      		
  			/* var res=$(this).attr('id');
  				
  				var finalres=res.substring(0,res.indexOf("f"));
  				console.log('Floor DopdId: '+finalres);
  				console.log('empi Id: '+$(ui.draggable).attr('id'));
  				var fulname=$(ui.draggable).text();
  				console.log('empi Id: '+fulname[fulname.length-1]);
  				if(addFlag1){
  					$.ajax({
         				 type:"post",
         				 url:"/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Escalation_Conf/addEscContactConfig.action",
         				 data :  { escDept: $('#department').val(), empName: $(ui.draggable).attr('id'), floorname:finalres, level: fulname[fulname.length-1],escLevel:'floor' },
         				 success:function(data){
         					 $('#message').empty();
				      			$("#message").fadeIn('fast');
         					$('#message').append("Employee mapped successfully!!!").css('color','black');
         					 $("#message").fadeOut(2000);      				 },
         				 error:function(){
         					  alert("error");
         				 }
         			}); 
  				}
  			 */
  				
  			  }
  		  });
  		  
  	  }
  	  
  	  
  	  
 //to make div droppable 	  
 function adddivobj(divid){
	  // alert("droppable: "+divid);
	   $('#'+divid).droppable({
		 hoverClass:'border',
		 accept:'#catalog li',
		 drop:function(event,ui){
			 var liIds = $('#'+$(this).attr('id')+' li').map(function(i,n) {
					 return $(n).attr('id');
				}).get().join(',');
			 console.log("list: "+liIds);
			
			
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
				 
				
			
			 console.log("array : "+array);
			 console.log("current ele: "+$(ui.draggable).attr('id'));
			 console.log("ret val : "+checkflag);
					      		if(checkflag==true){
					      		   $('#message').empty();
					      			$("#message").fadeIn('fast');
					      			$('#message').append("Contact already mapped to this floor!!!!").css('color','red');
					      			 $("#message").fadeOut(2000);
					      			addFlag=false;
					      			
					      			
					      		}else if(checkflag==false){
									//for adding employee to database 
									alert("saving");
									//		 console.log('Emp ID: ' + $(ui.draggable).attr("id"));
									//		console.log('Name: ' + $(ui.draggable).text());
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
										//	console.log('Wing ID: '+res);
										//	console.log('Floor ID: '+$('#'+pid+' p').attr('id'));
										//	console.log('Dept ID: '+$('#department').val());
										
										if(deptlevel!='' && typeof deptlevel != 'undefined' && empId!='' && typeof empId != 'undefined'
													&& res!='' && typeof res != 'undefined' && floorId!='' && typeof floorId != 'undefined' 
													&& floorId!='' && typeof floorId != 'undefined' && deptId!='' && typeof deptId != 'undefined'
											){//saving data 
												//alert("You can save");
												
											  $.ajax({
							              	    type : "post",
							              	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Escalation_Conf/addEscContactConfig.action",
							              	    data :  { escDept: deptId, empName: empId, floorname:floorId, wingsname:res, level: deptlevel,escLevel:'wings' },
							              	    success : function(data) {
							              	    	console.log(data[0].newid);
							              	    	var newid=data[0].newid;
							              	    addFlag=true;
								      			var dropid2=$(ui.draggable).attr('id');
								      			var dropname2=$(ui.draggable).text();
								      			$("#"+divid+' ul').append('<li id="'+dropid2+'-'+newid+'">'+dropname2+'</li>');
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
							              	 });
											}
											
											}
						
		 }//drop end
	   						
	   
		 
				 });
	 
	   
			 }//adddiv end 
  
 function removeEmp(){
  //	alert("check");
  	 dragdrop('catalog');
  	$('#wings li').draggable({
  		revert:true,
  		containment: $('#shop'),
  		opacity:0.5,
  		start:function(i,e){
  			$('#trashdiv img').css('border','2px dashed blue');
  			},
  	    stop: function( event, ui ) {
  	    	//alert("$(ui.draggable).text()"+$(ui.draggable).text());
  			 $('#trashdiv img').css('border','');
  			 
  		 }
  		
  	});
		
  	$('#wings li').css('cursor','move');
  	$('#trashdiv img').fadeIn();
  	  $('#trashdiv img').droppable({
 			 hoverClass:'zoom1',
 			 accept:'#wings li',
 			 drop:function(event,ui){
 				console.log('id dropped: '+$(ui.draggable).attr('id'));
 			 
 			var trashempid=	$(ui.draggable).attr('id');
 			var realid=trashempid.split('-')[1];
 			
 			if(realid!='' && typeof realid != 'undefined'){
 				
 			 $.ajax({
     		  
     		  type : "post",
           	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Escalation_Conf/deleteEmpByTrash.action",
           	    data :  { trashEmpId: realid },
           	    success : function(data) {
           	    	//console.log(data);
           	    	  $('ul li:nth-child('+realid+')').fadeOut(100);
       ///    	  $('#'+realid).remove();
           		 $('#message').empty();
            	    	$('#message').append('Data Removed Successfully!!!').css('color','black').css('display','block');
            	    $("#message").fadeIn('fast');
            	  $("#message").fadeOut(2000);
            	    
            	  
                  		},
           	    error: function() {
                       alert("error");
                   }
           	 });
 			}
  	 
  	
 			 }
  	  });
  }
function refresh(){
	var deptval=$('#department').val();
	if(deptval!=-1){
		democall(deptval);
	}
	$('#trashdiv img').fadeOut();
}

//funtion for timer dialog

function showtimerdialog(lvl){
	
	$("#timerDialog").dialog({title:'Define Escalation Time For '+lvl});
//	$("#timerDialog").dialog('open');
 $("#levelid").html(lvl);

	  $.ajax({
		  
		  type : "post",
		  url : "view/Over2Cloud/EventWiseAction/timertime.jsp",
      	   success : function(data) {
      		 $("#timerDiv").html(data);
      		$("#timerDialog").dialog('open');
       	  
             		},
      	    error: function() {
                  alert("error");
              }
      	 });

	
	
}

function getempDet(id){
	alert(id);
	  $.ajax({
		  type : "post",
		  url : "view/Over2Cloud/EventReminders/getempdetail.action?dept="+id,
      	   success : function(data) {
      		   console.log(data);
      		   var temp="";
      		   temp+='<table>';
      		 temp+='<tr><td><b>Name:</b> '+data[0].empName+'</td></tr>';
      		 if(data[0].emplogo=="NA"){
      			temp+='<div id="imgtd" style="float:right;border: 3px solid rgb(191, 177, 177);padding: 0%;border-radius: 5px;box-shadow: -1px 3px 9px 6px #263431;"><img alt="Employee Image" src="images/noImage.JPG" width="95px" height="95px"/></div>';
      		 }else{
      			 
      			temp+='<div id="imgtd" style="float:right;border: 3px solid rgb(191, 177, 177);padding: 0%;border-radius: 5px;box-shadow: -1px 3px 9px 6px #263431;"><img alt="Employee Image" src="'+data[0].emplogo+'" width="95px" height="95px"/></div>';
      		 }
      		 
      		 
      		temp+='<tr><td><b>Mobile:</b> '+data[0].mobile+'</td></tr>';
      		temp+='<tr><td><b>From Department:</b> '+data[0].fromdept+'</td></tr>';
      		temp+='<tr><td><b>Mapped For Department:</b> '+data[0].fordept+'</td></tr>';
      		temp+='<tr><td><b>Mapped For Application:</b> '+data[0].module+'</td></tr>';
      		temp+='<tr><td><b>Mapped Department Level:</b> '+data[0].level+'</td></tr>';
      	   temp+='</table>';
      		
      		   
      		 $("#timerDiv").html(temp);
      		$("#timerDialog").dialog({title:'Employee Detail for '+data[0].empName+' ',height:170});
      		$("#timerDialog").dialog('open'); 
       	  
             		},
      	    error: function() {
                  alert("error");
              }
      	 });

}

  </script>
    <style type="text/css">
     
     
     .closediv{
     display:none;
     
     }
     
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
            width: 28%;
            border: 1px solid #bbb;
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
            font-family: Verdana;
            text-align: center;
            padding: 7px;
            margin: 5px;
            font-weight: bold;
            border: 1px solid #aaa;
            background: -webkit-gradient(linear, 0 0, 0 100%, from(#f8f8f8), to(#eaeaea));
        }
        .draggable-demo-cart-wrapper
        {
            float: right;
            border: 1px solid #aaa;
            height: auto;
            width: 900px;
        }
        #wings div{
            float:left;
            border: 1px solid #aaa;
            height: auto;
            width: 200px;
            margin: 5px 5px 5px 5px;
            border-radius:5px;
            box-shadow:0 2px 10px 0 rgba(4, 0, 0, 0.61);
        }
        
         #floor ul b{
            background:-webkit-gradient(linear, 0 0, 0 100%, from(#f8f8f8), to(#eaeaea));
        }
        h3{
        background:-webkit-gradient(linear, 0 0, 0 100%, from(#f8f8f8), to(#eaeaea));
        }
          #escmax td.border{
            border: 5px dotted rgb(212, 212, 212);
            width: 20%;
            
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
        
         border: 1px solid #aaa;
         height: 10px;
         padding: 1%;
         color: black;
         font-size: 11px;
         margin-bottom: 1px;
         font-family: Verdana;
       cursor: move;
       border-radius:3px;
       margin: 2px 2px 2px 2px;
        }
        
         #droplis ul li{
        
         border: 1px solid #aaa;
         height: 13px;
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
         height: 15px;
         padding: 1%;
      
        }
        #catalog ul li:HOVER{

        background: -webkit-gradient(linear, 0 0, 0 100%, from(#f8f8f8), to(#eaeaea));
         border: 1px solid #aaa;
         height: 13px;
         padding: 1%;
         
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
       
		#escmax td{
		 border: 1px solid #aaa;
		 
		}
		
		#escmax th{
		 border: 1px solid #aaa;
		 
		}
		
		#free ul li {
		border:1px solid rgba(200, 200, 200, 0.93);
		border-radius:3px;
		width: 146px;
		padding: 0%;
		}
		
		#free ol li {
		
		float: left;
list-style: none;
cursor: pointer;
margin: 0 0 0 .5em;
border:1px solid rgb(194, 194, 194);
border-radius: 3px;
padding: 0px 5px 0px 5px;
}

		ol#pagination {
overflow: hidden;
}

#free ol{
padding: 0;
line-height: inherit;
margin: 0 0 1.5em 0;
}
		
		#hiddentr td {
			border:1px solid #FFF;
		}
		
		#escmax td li {
		
		margin:2%;
		}
		
		.current{
		background:rgb(194, 194, 194) ;
		}
		.ui-droppable ul li{
		border: 1px solid rgb(194, 194, 194);
		padding: 0%;
         color: black;
         font-size: 12px;
         font-family: Verdana;
           border-radius:3px;
     
		
		}
		#imgtd:HOVER {
	box-shadow: 0px 0px 0px 0px #FFFFFF;
}
    </style>
    <script type="text/javascript">
  
          
    </script>
</head>
<body class='default' id="containmenttable">
<sj:dialog
          id="timerDialog"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          modal="true"
          closeTopics="closeEffectDialog"
          width="400"
          height="100"
          
          >
          <div id="timerDiv" >
           </div>
           
           <div id="levelid" style="display: none;" >
           </div>
           
</sj:dialog>
<div style="display: none;" id="identify"></div>
<div style="display: none;" id="hiddenemp"></div>
<div style="display: none;" id="hiddenlevel"></div>
    <div id="shop" class="draggable-demo-shop jqx-rc-all" align="center" >
     <div id="message"></div>
     
<table id="escmax" width="100%" border="1px solid black;" >
 <tbody>
	<tr border="1" height="10px" style="background: rgb(212, 212, 212);" >
		<th >Free
		</th>
		<th>L1 To
		</th>
		<th>L2 To
		</th>
		<th>L3 To
		</th>
		<th>L4 To
		</th>
		<th>L5 To
		</th>
	</tr>
	<span id="droplis">
			<tr height="100px" >
				<td id="free" width="20%" rowspan="4" align="center"><ul id="feeEmpLists" ></ul></td>
				<td  id="l1to" align="center" width="15%">
			<font  size="1">Level 1	Escalation Time:</font> <div id="seltimedisp1" ></div>	<a href="#" onclick="showtimerdialog('Level 1');">	<img alt="clock" src="images/clock-icon.png" width="20px" height="20px" style="margin: 1px 4px -3px 126px;"></a>
					<ul></ul>
				
				</td>
				
				<td id="l2to" align="center" width="15%">
				<font  size="1">Level 2	Escalation Time:</font> <div id="seltimedisp2"></div>	<a href="#" onclick="showtimerdialog('Level 2');">	<img alt="clock" src="images/clock-icon.png" width="20px" height="20px" style="margin: 1px 4px -3px 126px;"></a>
					<ul></ul>
				</td>
				
				<td id="l3to" align="center" width="15%">
				<font  size="1">Level 3	Escalation Time:</font> <div id="seltimedisp3"></div>	<a href="#" onclick="showtimerdialog('Level 3');">	<img alt="clock" src="images/clock-icon.png" width="20px" height="20px" style="margin: 1px 4px -3px 126px;"></a>
					<ul></ul>
				</td>
				
				<td id="l4to" align="center" width="15%">
				<font  size="1">Level 4	Escalation Time:</font> <div id="seltimedisp4"></div>	<a href="#" onclick="showtimerdialog('Level 4');">	<img alt="clock" src="images/clock-icon.png" width="20px" height="20px" style="margin: 1px 4px -3px 126px;"></a>
					<ul></ul>
					
				</td>
				
				<td id="l5to" align="center" width="15%">
				<font  size="1">Level 5	Escalation Time:</font> <div id="seltimedisp5"></div>	<a href="#" onclick="showtimerdialog('Level 5');">	<img alt="clock" src="images/clock-icon.png" width="20px" height="20px" style="margin: 1px 4px -3px 126px;"></a>
					<ul></ul>
				</td>
			</tr>
			<tr height="10px" style="background: rgb(212, 212, 212);">
			
				<th>L1 CC
				</th>
				<th>L2 CC
				</th>
				<th>L3 CC
				</th>
				<th>L4 CC
				</th>
				<th>L5 CC
				</th>
			</tr>
				<tr height="100px">
			
				<td id="l1cc" align="center"><ul></ul>
				</td>
				<td id="l2cc" align="center"><ul></ul>
				</td>
				<td id="l3cc" align="center"><ul></ul>
				</td>
				<td id="l4cc" align="center"><ul></ul>
				</td>
				<td id="l5cc" align="center"><ul></ul>
				</td>
				<td style="border:1px solid #FFF "></td>
			</tr>
	</span>
	<tr height="90%" id="hiddentr">
	<td ></td>
		<td >
		</td>
		<td >
		</td>
		<td >
		</td>
		<td >
		</td>
		<td >
		</td>
	</tr>
 </tbody>
</table>

       
        </div>
      <%--   <div class="draggable-demo-cart-wrapper jqx-rc-all">
            <div class="draggable-demo-title jqx-rc-t" onclick="">Escalation Matrix
             <sj:div cssClass="button"  cssStyle="float:left" href="#" onclick=" dragdrop('catalog');" title="Add Employee" bindOn="button">ADD</sj:div>
             <sj:div cssClass="button"  cssStyle="float:left" href="#" onclick="removeEmp()" title="Delete Employee"><img alt="Delete" src="images/Delete_Icon.png" width="15px" height="15px"></img></sj:div>
             <button  href="#" onclick=" dragdrop('catalog');" title="Add Employee" style="float: left;margin-right:3px; "><img alt="Add_image" src="images/add.png" width="13px" height="13px"/></button>
             <button  href="#" onclick="removeEmp()" title="Remove Employee" style="float: left;margin-right:3px;"><img alt="Delete_image" src="images/Delete_Icon.png" width="13px" height="13px"/></button>
             <button  href="#" onclick="refresh()" title="Refresh" style="float: left;"><img alt="Refresh_image" src="images/refresh_cloud.png" width="13px" height="13px"/></button>
            </div>
           
                            <div id="wings">
                            
                             
                            </div>
                            <div id="trashdiv"><img alt="Trash" src="images/Delete_Icon.png" width="100px" height="100px" style="display: none;float: right;position: relative;">
                            
                            </div>
                           
                            
            </div> --%>
        <div style="clear: both;"></div>

   
   
   
</body>
</html> 