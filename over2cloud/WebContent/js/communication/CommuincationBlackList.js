function addBlackList() {

	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
                        $.ajax({
                            type : "post",
                            url : "view/Over2Cloud/CommunicationOver2Cloud/Comm/beforeBlackListAdd.action",
                            success : function(subdeptdata) {
                       $("#"+"data_part").html(subdeptdata);
                        },
                           error: function() {
                            alert("error");
                        }
                         });
}

function excelUpload()
{

	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
         $("#data_part").load("view/Over2Cloud/CommunicationOver2Cloud/blacklist/BlackListExcelUpload.jsp");
}

function downloadGridData()
{
        alert("bkgh");
        $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");

        $.ajax({
            type : "post",
            url  : "view/Over2Cloud/CommunicationOver2Cloud/Comm/beforeBlackListGridDataDownload.action",
            success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
        },
           error: function() {
            alert("error");
        }
         });
}
function viewBlackList() {
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");

        $.ajax({
            type : "post",
            url : "view/Over2Cloud/CommunicationOver2Cloud/Comm/beforeBlackListView.action",
            success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
        },
           error: function() {
            alert("error");
        }
         });

}

function showGrid() {

        $("#BlacklistBasicDialog").dialog('open');
        $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");

        $.ajax({
            type : "post",
            url : "view/Over2Cloud/CommunicationOver2Cloud/Comm/showExcel.action",
            success : function(subdeptdata) {

       $("#BlacklistBasicDialog").html(subdeptdata);
        },
           error: function() {
            alert("error");
        }
         });

}

function getContactNamesForUser(subGroupId,divId)
{
                        $.ajax({
                                type :"post",
                                url :"view/Over2Cloud/CommunicationOver2Cloud/Comm/getContforBlack.action",
                                data:"subGroupId="+subGroupId,
                                success : function(empData){
                                $('#'+divId+' option').remove();
                                $('#'+divId).append($("<option></option>").attr("value",-1).text("Select Contact Name "));
                        $(empData).each(function(index)
                                {
                                   $('#'+divId).append($("<option></option>").attr("value",this.id).text(this.name));
                                });
                            },
                            error : function () {
                                        alert("Somthing is wrong to get Data");
                                }
                        });
}
function getEmpDetails(empId)
{
        $.ajax({
                type : "post",
                url : "view/Over2Cloud/hr/getEmpDetails.action",
                data : "empId="+empId,
                success : function(data){
                        $('#mobileno').val(data.mobileNo);
                        $('#email').val(data.email);
                },
                error : function(){
                        alert("Error");
                }
                });
}

$.subscribe('varifyFile', function(event,data)
                {
                 var fileName = upload_BL_text.text.value;
                 if(fileName.length >= 1)
                 {
                 parts = fileName.substring(fileName.lastIndexOf("."));
                 if (parts!= ".txt")
                  {
                   alert ("Input File Must Be a Text File (.txt)");
                   return false;
                  }
                  }
                 if(upload_BL_text.text.value=="")
                 {
                         txtError.innerHTML="<div class='user_form_inputError2'>Select A Text File</div>";
                          document.upload_BL_text.text.focus();
                     event.originalEvent.options.submit = false;
                 }
                 else
                 {
                      txtError.innerHTML="";
                          $('#bLTextDialog').dialog('open');
                     event.originalEvent.options.submit = true;

                 }
                  return true;
                });


$.subscribe('validateExcel', function(event,data)
        {
       var status=false;
                if(document.upload_BL_excel.excel.value!="")
        {
          temp=document.upload_BL_excel.excel.value.split(".");
          if(temp[1]!="xls" && temp[1]!="xlsx")
          {
          excelError.innerHTML="<div class='user_form_inputError2'>Select only xls file</div>";
          document.upload_BL_excel.excel.focus();
          status = false;
          }
          else
          {
                  excelError.innerHTML="";
                  status = true;
          }
        }
          if(document.upload_BL_excel.excel.value=="")
          {
                  excelError.innerHTML="<div class='user_form_inputError2'>Select An Excel</div>";
                  document.upload_BL_excel.excel.focus();
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