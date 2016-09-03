<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<script type="text/javascript">

$.subscribe('completeData22',function(event,data)
		{
	setTimeout(function(){ $("#foldeffect10").fadeIn(); }, 10);
    setTimeout(function(){ $("#foldeffect10").fadeOut(); }, 4000);
			
		});


</script>
<SCRIPT type="text/javascript">


	$.subscribe('close', function(event,data)
	        {
       
		 document.getElementById("indicator2").style.display="none";
	        });
	//Code Starts
		$('#browse').bind('change', function()
	   {
	     var iSize = ($("#browse")[0].files[0].size / 1024);
	     
	     if (iSize / 1024 > 1)
	     {
	        if (((iSize / 1024) / 1024) > 1)
	        {
	            iSize = (Math.round(((iSize / 1024) / 1024) * 100) / 100);
	            $("#krbrowse").html("File Size Is "+ iSize + "Gb");
	        }
	        else
	        {
	            iSize = (Math.round((iSize / 1024) * 100) / 100)
	            $("#krbrowse").html("File Size Is "+ iSize + "Mb");
	        }
	     }
	     else
	     {
	        iSize = (Math.round(iSize * 100) / 100)
	        $("#krbrowse").html("File Size Is "+ iSize  + "kb");
	     }  
	     if(this.files[0].size<=21316300)
			{
			a=true;
			
			}
		else
			{
			a=false;
			}
	  });
	

	function viewDept()
	{

		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/>Loading...</center>");
		var conP = "<%=request.getContextPath()%>";
		$.ajax({
		    type : "post",
		    url : conP+"/view/Over2Cloud/KRLibraryOver2Cloud/beforeKrView.action?KRSharedbyWithMe=1&modifyFlag=0&deleteFlag=0&formater=1&header=1",
		    success : function(data) 
		    {
				$("#"+"data_part").html(data);
		    },
		    error: function() 
		    {
	            alert("error");
	        }
		 });
		}


	function reset(formId) {
		  $("#formId").trigger("reset"); 
		}

	
</script>
</head>
<div class="clear"></div>

<div class="container_block">
<div style="float: left; padding: 20px 5%; width: 90%; margin-top: 45px;">

			<div class="form_inner" id="form_reg">
			<div class="page_form">
			<s:form id="formone" name="formone" action="orgLogoUpload" theme="simple" method="post" enctype="multipart/form-data">

		      <center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone" style="float:center; margin-left: 7px"></div></div></center>

				<div class="clear"></div>
				<div class="newColumn">
				<div class="leftColumn"><b>Select&nbsp;Logo:</b></div>
				<div class="rightColumn" style="margin-left: 120px;">
				<s:file name="orgLogo"  id="orgLogo" style="margin-left: -3px;margin-top: -39px;"  />
				<div id="krbrowse"></div>
				</div>
				<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display: none" /></center>
				</div>
				<div class="clear"></div>
				<center>
				<b>Note:</b> Please use .jpg image for logo upload.
				</center>
				<div class="clear"></div>
				<div class="clear"></div>
				<br>
				<div class="fields">
				<div style="width: 100%; text-align: center; padding-bottom: 10px;">
				<sj:submit 
								     clearForm="true"
								     value="  Add  " 
								     effect="highlight"
								     effectOptions="{ color : '#222222'}"
								     effectDuration="5000"
								     button="true"
								     onCompleteTopics="completeData22"
								     cssClass="submit"
								     indicator="indicator2"
								     onBeforeTopics="validate"
							     />
				<sj:a 
							 button="true" href="#"
							 onclick="reset('formone');"
							 cssClass="submit"
						>
						
						Reset
					</sj:a>
					 <sj:div id="foldeffect10"  effect="fold">
                    <div id="Result7"></div>
               </sj:div>

				</div>
				</div>
				<div class="clear"></div>
				

	</s:form>
	


</div>
</div></div></div>
</html>