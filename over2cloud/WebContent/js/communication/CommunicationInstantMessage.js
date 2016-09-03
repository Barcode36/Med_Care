
function viewInstantmsg() {
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/>Loading...</center>");
	$.ajax({
	    type : "post",
	    url  : "view/Over2Cloud/CommunicationOver2Cloud/Comm/beforeinstantmessageView.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function putValues(ref)
{
	
	$("#DIV"+ref.name).html(" "+ref.value+" ");
	   $("#writeMessagesssss").val($("#xxxxxxxxx").text().replace(/^(\s*)|(\s*)$/g, '').replace(/\s+/g, ' '));
}

$.subscribe('validateExcel', function(event,data)
        {
       var status=false;
		if(document.xlsx.ASExcel.value!="")
        {
      	  temp=document.xlsx.ASExcel.value.split(".");
      	  if(temp[1]!="xls" && temp[1]!="xlsx")
      	  {
      	  excelError.innerHTML="<div class='user_form_inputError2'>Select only xls file</div>";
      	  document.xlsx.ASExcel.focus();
      	  status = false;
      	  }
      	  else
      	  {
      		  excelError.innerHTML="";
      		  status = true;
      	  }
        }
		
          if(document.xlsx.ASExcel.value=="")
          {
        	  excelError.innerHTML="<div class='user_form_inputError2'>Select An Excel</div>";
        	  document.xlsx.ASExcel.focus();
    	      event.originalEvent.options.submit = false;
          }if(document.getElementById("root").value=="-1"){
              excelError.innerHTML="<div class='user_form_inputError2'>Please Select Root</div>";
        	  document.xlsx.ASExcel.focus();
    	      event.originalEvent.options.submit = false;
          }
          else
          {
        	  if
        	  (status)
        	  {
        	  excelError.innerHTML="";
        	  $('#ASExcelDialog').dialog('open');
    	      event.originalEvent.options.submit = true;
        	  }
          }
          
          
        });  



$.subscribe('validateExcelmail', function(event,data)
        {
       var status=false;
		if(document.xlsx.ASExcel.value!="")
        {
      	  temp=document.xlsx.ASExcel.value.split(".");
      	 
      	  if(temp[1]!="xls" && temp[1]!="xlsx")
      	  {
      	  excelError.innerHTML="<div class='user_form_inputError2'>Select only xls file</div>";
      	  document.xlsx.ASExcel.focus();
      	  status = false;
      	  }
      	  else
      	  {
      		  excelError.innerHTML="";
      		  status = true;
      	    }
          }
		if(document.xlsx.ASExcel.value=="")
        {
      	  excelError.innerHTML="<div class='user_form_inputError2'>Select An Excel</div>";
      	  document.xlsx.ASExcel.focus();
  	      event.originalEvent.options.submit = false;
        }
  
        	  if(status)
        	  {
        		  excelError.innerHTML="";
        	  $('#ASExcelDialog').dialog('open');
        	  }
        });  


function getObj(id)
 {
   return document.getElementById(id);
 }

$.subscribe('complete', function(event,data)
    {
        setTimeout(function(){ $("#foldeffect").fadeIn(); }, 1000);
        setTimeout(function(){ $("#foldeffect").fadeOut(); }, 4000);
    });



$.subscribe('varifyFile', function(event,data)
{

 var fileName = upload_TS_excel.ASExcel.value;
 if(fileName.length >= 1)
 {

 parts = fileName.substring(fileName.lastIndexOf("."));
 if (parts!= ".txt")
  {
   alert ("Input File Must Be a Text File (.txt)");
   return false;
  }
  }
 if(document.upload_TS_excel.ASExcel.value=="")
 {
	 txtError.innerHTML="<div class='user_form_inputError2'>Select A Text File</div>";
	  document.upload_TS_excel.ASExcel.focus();
     event.originalEvent.options.submit = false;
 }
 if(document.getElementById("root").value=="-1"){
              txtError.innerHTML="<div class='user_form_inputError2'>Please Select Root</div>";
        	  document.upload_TS_excel.ASExcel.focus();
    	      event.originalEvent.options.submit = false;
          }
 else
 {
		  txtError.innerHTML="";
	
	  $('#ASTextDialog').dialog('open');
     event.originalEvent.options.submit = true;

 }
  return true;
});

$.subscribe('varifymailFile', function(event,data)
		{

		 var fileName = upload_TS_excel.ASExcel.value;
		 if(fileName.length >= 1)
		 {

		 parts = fileName.substring(fileName.lastIndexOf("."));
		 if (parts!= ".txt")
		  {
		   alert ("Input File Must Be a Text File (.txt)");
		   return false;
		  }
		  }
		 if(document.upload_TS_excel.ASExcel.value=="")
		 {
			 txtError.innerHTML="<div class='user_form_inputError2'>Select A Text File</div>";
			  document.upload_TS_excel.ASExcel.focus();
		     event.originalEvent.options.submit = false;
		 }
		 else
		 {
				  txtError.innerHTML="";
			
			  $('#ASTextDialog').dialog('open');
		     event.originalEvent.options.submit = true;

		 }
		  return true;
		});





function countChar()
{

if(xlsfiledata.msg_bodys.value.length > 160){

alert("Only 160 Character Is Allowed.");
return false;
}
xlsfiledata.countNum.value=xlsfiledata.msg_bodys.value.length;

var msgName = xlsfiledata.msg_bodys.value;
      var iChars = "#|^~`{}\\[]";

  for (var i = 0; i < msgName.length; i++) {
      if (iChars.indexOf(msgName.charAt(i)) != -1)
      {
      alert("Containts special characters. \n These are not allowed. Please remove them and try again.");
      var len =(xlsfiledata.msg_bodys.value.length)-1;
      xlsfiledata.msg_bodys.value=(xlsfiledata.msg_bodys.value).substring(0,len);
      xlsfiledata.msg_bodys.focus();
      return false;
          }
     }
}



