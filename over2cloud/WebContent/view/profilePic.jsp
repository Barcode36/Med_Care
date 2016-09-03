<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
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
	
	function reset(formId) {
		  $("#formId").trigger("reset"); 
		}

	
</script>
</head>
<body>
<div style="overflow-x:hidden; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
<s:form id="formone" name="formone"  action="uploadPic"  theme="simple" method="post" enctype="multipart/form-data">
	<table width="100%" align="center" height="100%">
		<tr>
			<td width="25%" valign="top">
				
					<s:file name="browse"  id="browse"  />
			</td>
		</tr>
		<tr>
			<td>
				<sj:submit 
								     clearForm="true"
								     value="  Add  " 
								     effect="highlight"
								     effectOptions="{ color : '#222222'}"
								     effectDuration="5000"
								     button="true"
								     cssClass="submit"
								     indicator="indicator2"
								     onBeforeTopics="completeData22"
								     cssStyle="margin-top: -25px;margin-left: 206px;"
								     targets="resultDiv"
								     
							     />
			</td>
		</tr>
	</table>
	<br>
	<div id="resultDiv"></div>
</s:form>
</div>
</body>
</html>