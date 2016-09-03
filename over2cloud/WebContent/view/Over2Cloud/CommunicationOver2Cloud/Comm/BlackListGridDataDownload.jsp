<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="/js/offering.js"/>"></script>
<script src="<s:url value="/js/excel/excel_upload_download.js"/>"></script>
<SCRIPT type="text/javascript">
$.subscribe('beforeClientUpload',function(event,data)
		{
			setParameters("client_basic_data#client_offering_mapping#client_contact_data", "uploadExcel", "mapped_client_configuration", "client_basic_configuration#client_offering_configuration#client_contact_configuration","client","");
		}); 

function setParameters(one, two, three, four, five, six)
{
	$('#tableName').val(one);
	$('#downloadType').val(two);
	$('#mappedTableName').val(three);
	$('#masterTableName').val(four);
	$('#level').val(five);
	$('#productMappedId').val(six);
	
}
$.subscribe('resetAllSelect',function(event,data)
		{
			$('select').find('option:first').attr('selected', 'selected');
		});

$.subscribe('validate', function(event,data)
		{
		    var mystring = $(".pIds").text();
		    var fieldtype = mystring.split(",");
		    var pattern = /^\d{10}$/;
		    for(var i=0; i<fieldtype.length; i++)
		    {
		        
		        var fieldsvalues = fieldtype[i].split("#")[0];
		        var fieldsnames = fieldtype[i].split("#")[1];
		        var colType = fieldtype[i].split("#")[2]; 
		      //  alert(colType);  //fieldsType[i]=first_name#t
		        var validationType = fieldtype[i].split("#")[3];
		       // $("#"+fieldsvalues).css("background-color","");
		        errZone.innerHTML="";
		        //alert(fieldsvalues+">>>>fieldsvalues");   
		        if(fieldsvalues!="")
		        {
		           if(colType=="abc")
		           {
		            	
		                if($("#"+fieldsvalues).val()=="")
		                {
		                	errZone.innerHTML="<div class='user_form_inputError2'>Please Select Excel File For Upload  </div>";
			                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
			                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
			                $("#"+fieldsvalues).focus();
			                //$("#"+fieldsvalues).css("background-color","#ff701a");
			                event.originalEvent.options.submit = false;
			                break;   
		                }
		                if($("#"+fieldsvalues).val()!="")   
		                {
		                	$('#AssetBasicDialog').dialog('open');
		            } 
		            

		                }  
		         }
		        
		        
		     }   
		});


function getCurrentColumn(downloadType,mappedTableName,masterTableName,dialogId,dataDiv,excelName,level)
{
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/excel/currentColumn.action",
	    data : "downloadType="+downloadType+"&mappedTableName="+mappedTableName+"&masterTableName="+masterTableName+"&excelName="+excelName+"&level="+level,
	    success : function(data) {
		$("#"+dataDiv).html(data);
		$("#"+dialogId).dialog('open');
	},
	   error: function() {
	        alert("error");
	    }
	 });
}

</SCRIPT>
<STYLE type="text/css">
.user_form_text_new{width:200px; text-align:left; margin-right:10px; font-size:12px;  font-weight:600; float:left; line-height:40px;}
</STYLE>
</head>


<div class="clear"></div>
<div class="list-icon">
	<div class="head">Exclusion DownLoad</div>
</div>

<div class="container_block">
<div style=" float:left; padding:20px 1%; width:98%;">
<sj:accordion id="accordion" autoHeight="false">
<sj:accordionItem title="DownLoad File To Desktop" id="oneId">  
<div class="form_inner" id="form_reg">
<div class="page_form">
<!-- Offering level 1 --------------------------------------------------------------------------------------------------------------->
<s:form id="formone" name="formone" namespace="/view/Over2Cloud/CommunicationOver2Cloud/Comm" action="beforeBlackListGridDataDownload" theme="simple"  method="post"enctype="multipart/form-data" >
	
	<div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="errZone" style="float:left; margin-left: 7px">
             </div> 
             </div>
	<s:hidden id="tableName" name="tableName" value="client_basic_data#"></s:hidden>
	<s:hidden id="downloadType" name="downloadType"></s:hidden>
	<s:hidden id="mappedTableName" name="mappedTableName" value="mapped_client_configuration"></s:hidden>
	<s:hidden id="masterTableName" name="masterTableName" value="client_basic_configuration#"></s:hidden>
	<s:hidden id="level" name="level" value="client"></s:hidden>
	<s:hidden id="productMappedId" name="productMappedId"></s:hidden>
	
			<div class="form_menubox">
			<span id="form2MandatoryFields" class="pIds" style="display: none; "></span>
				<div class="inputmain">
					<div class="user_form_text_new">
					Download Excel file:
					</div>
					<!--<div class="user_form_input inputarea">
						<s:file name="excel" id="excelValidate"/>					
					</div> 
				--></div>
				<div class="inputmain">
					
					<div class="user_form_input inputarea">
						<a id='astUpload1' >
       						<font color="#194d65">Download </font>
       					</a>					
					</div> 
				</div>
			</div>
			
			<div class="form_menubox">
			<span id="form2MandatoryFields" class="pIds" style="display: none; "></span>
				<div class="inputmain">
					<div class="user_form_text_new">
					Download Text file:
					</div>
					<!--<div class="user_form_input inputarea">
						<s:file name="excel" id="excelValidate"/>					
					</div> 
				--></div>
				<div class="inputmain">
					
					<div class="user_form_input inputarea">
						<a id='astUpload1'>
       						<font color="#194d65">Download</font>
       					</a>					
					</div> 
				</div>
			</div>
			
			
</s:form>
</div>
</div>
</sj:accordionItem>
</sj:accordion>

 
 <sj:dialog
                  id="AssetBasicDialog"
                  autoOpen="false"
                  closeOnEscape="true"
                  modal="true"
                  title="Client Basic Detail Upload Via Excel"
                  width="600"
                  height="350"
                  showEffect="slide"
                  hideEffect="explode">
                  <sj:div id="foldeffectExcel" effect="fold">
                     <div id="saveExcel"></div>
                  </sj:div>
                  <div id="createAssetBasic"></div>
        	</sj:dialog>              
<sj:dialog id="uploadColumnAstDialog" modal="true" effect="slide" autoOpen="false" width="300" height="500" title="Column Name" loadingText="Please Wait" overlayColor="#fff" overlayOpacity="1.0" position="['left','top']" >
	<div id="uploadAstColumnDiv"></div>
</sj:dialog>
</html>