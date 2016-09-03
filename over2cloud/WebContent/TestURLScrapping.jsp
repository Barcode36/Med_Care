<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Facebook Extract Link Data</title>
 <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
    
 <script type="text/javascript">


 $(document).ready(function()
{
	$("#contentbox").keyup(function()
	{
		var content=$(this).val();
		var urlRegex = /(\b(https?|ftp|file):\/\/[-A-Z0-9+&@#\/%?=~_|!:,.;]*[-A-Z0-9+&@#\/%=~_|])/ig;
		var url= content.match(urlRegex);
		
			if(url.length>0)
			{
			
			$("#linkbox").slideDown('show');
			$("#linkbox").html("<img src=images/ajax-loaderNew.gif>");
			
				$.ajax({
				    url: "urlparsing.jsp?url="+url,
				    type: 'get',
				    dataType: 'html',
				    async: false,
				    success: function(response) {
				    	var tempLogo;
				        var title=(/<title>(.*?)<\/title>/m).exec(response);
				        var logo=(/<img src=(.*?)>/m).exec(response);
				        alert(logo);
				        if(logo==null)
				        {
				        	tempLogo="images/No_image.png";
				        	
				        }
				        else
				        {
				        	tempLogo="images/No_image.png";
				        }
				        alert(tempLogo);
				        if(title==null)
				        {
				        	title="No data available";
				        }

				        $("#imgPart").html("<img src='"+tempLogo+"' class='img' height='20%;'/>");
				        $("#linkbox").html("<h1>"+title[1]+"</h1><br/><p>"+url+"</p>");
				    } 
				 });
			}
	});

});
</script>
<style type="text/css">
	.video_block{
		float:left; width:90%; padding:5%; background:#f7f7f7; border:1px solid #dedede;
	}
	.video_main{
		float:left; width:auto; padding:0px 10px 0px 0px;
	}
	.video_content{
		float:left; width:auto;
	}
	.video_content h1{
		font-family:'Open Sans'; font-size:13px; font-weight:600;
	}
	.video_content p{
		text-align:justify; line-height:18px; padding:0px 0px 10px 0px;
	}
</style>
</head>

<body>
<textarea id="contentbox"></textarea>



<div class="video_block">
	<div class="video_main" id="imgPart"></div>
	<div class="video_content" id="linkbox">
		<h1></h1>
		<p></p>
	</div>
</div>
</body>
</html>
