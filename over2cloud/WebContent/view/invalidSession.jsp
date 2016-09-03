<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head >
<title>Over 2 Cloud</title>
<link href="<s:url value="/css/style.css"/>" rel="stylesheet" type="text/css" />
<script type="text/javascript">
 function validate()
	        {
	 document.getElementById("indicator2").style.display="block";
	 document.getElementById("bt").style.display="none";
	 login.submit;
	         }
 </script>
 <style>
 	.heading
 	{
		border:solid 2px #FF0000; 
		background:#EFEFEF;
		color:#FF0000;
		padding:4px;
		text-align:center;.
		
	}
 .redText {
	font-family: Arial, Tahoma;
	font-size: 11px;
	color: #FF0000
}
 </style>
 
</head>
<body >
<br><br><br><br><br><br><br><br><br><br><br>
	<center>
		<div class="dream_loginpanel">
			<div id="adminlogo">
                <center><a href="http://www.over2cloud.com" target="_blank"><img src="<s:url value="/images/logo.png"/>" alt="logo" border="0" title="logo" style="width:330px;" /></a></center>
        	</div>
           <div id="dream_loginbox" align="center">
		   <div id="dream_userpart" align="center">
		   <center>
		   <br></br>
		   <font >Oops!!!&nbsp;Your&nbsp;Session&nbsp;has&nbsp;been&nbsp;expired</font>,&nbsp;<br/>or<br/>&nbsp;Your&nbsp;Account&nbsp;must&nbsp;have&nbsp;expired. </center>
              <br></br>
             Never mind just call us at: <font class="redText">+91-120-4316414/ 422/ 9250400311</font><br/>or<br/>
             SMS: "<font class="redText">LogEr</font>" to <font class="redText">92-666-05050</font> from your mobile.
             <br/>or<br/>
             Email at: support@dreamsol.biz
             <br/>or<br/>
             <a href="<s:url value="signuppage.action"/>"><font color="blue">Click Here</font></a> to Login again.
            </div>
            <br></br>
  		  	<div id="dream_footerlogin">All&nbsp;Rights&nbsp;Reserved&nbsp;by&nbsp;DreamSol&nbsp;TeleSolutions&nbsp;Pvt.&nbsp;Ltd.<br><a href="http://www.dreamsol.biz" target="_blank">www.dreamsol.biz</a><br></br> Copyrights 2014</div>
	</div>
	</center>
</body>

</html>
