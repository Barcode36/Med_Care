<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>::: Over2Cloud :::</title>

<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<link type="text/css" href="<s:url value="/css/theme1.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="../js/ddaccordion.js"/>"></script>
<script type="text/javascript" src="<s:url value="../js/expandable_block.js"/>"></script>
<link href="http://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet" type="text/css" />
<link href="http://fonts.googleapis.com/css?family=Droid+Sans" rel="stylesheet" type="text/css" />
<link href="http://fonts.googleapis.com/css?family=Open+Sans:400,600" rel="stylesheet" type="text/css" />
<!-- Services menu starts heres -->
<link type="text/css" href="<s:url value="css/select_custom.css"/>" rel="stylesheet" />
<script src="http://www.digitss.com/jquery/custom-radio-checkbox/jquery.js" type="text/javascript"></script>
<script src="http://www.digitss.com/jquery/custom-radio-checkbox/jquery.custom_radio_checkbox.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript">
$(document).ready(function(){
	$(".radio").dgStyle();
	$(".checkbox").dgStyle();
});
</script>

<!-- Customised select box ends here-->

</head>
<body>
<div class="wrapper">
<div class="main_wrapper">
<!-- Left Side Bar Coming fix -->
<s:include value="/menubar.jsp"></s:include>
<!-- left sidebar ends -->
<div class="content">
<div class="content_block">
<!--  top content Fix  -->
<s:include value="/top.jsp"></s:include>
<!--  top content ends  -->
<div class="container">
<!-- Main Block  Changeable-->
<div class="page_title"><h1>Mail Configuration</h1></div>
<div class="container_block">
						<div class="form_inner">
							<div class="page_form">
								<form action="" name="" id="" method="post" class="cssn_form">
									<h2>Please fill up the following details</h2>
									<div class="fields">
										<div class="sub_block">
											<label>Username</label>
											<input type="text" name="" id="" value="" />
										</div>
										<div class="port">
											<label>Port</label>
											<input type="text" name="" id="" value="" class="slab" />
										</div>
									</div>
									<div class="fields">
										<div class="sub_block">
											<label>Password</label>
											<input type="password" name="" id="" value="" />
										</div>
									</div>
									<div class="fields">
										<ul>
											<li class="submit">
												<input type="submit" class="submit" name="" id="" value="Submit" /></li>
											<li class="cancel">
												<input type="submit" class="submit" name="" id="" value="Cancel" /></li>
										</ul>
									</div>
								</form>
							</div>
						</div>
					</div>
					
					
					

<!-- End Main Block -->	
<!-- Footer.jsp fixed -->
<s:include value="/footer.jsp"></s:include>
<!-- End Footer.jsp -->
</div>
</div>
</div>

</div>
</div>
</body>
</html>
