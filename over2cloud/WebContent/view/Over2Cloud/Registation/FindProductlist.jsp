<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/js.js"/>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/master.js"></script>

<script type="text/javascript">
$.subscribe('complete', function(event,data)
    {
        setTimeout(function(){ $("#foldeffect").fadeIn(); }, 10);
        setTimeout(function(){ $("#foldeffect").fadeOut(); }, 4000);
    });
</script>
</head>
<body>
      
	 <center>
     <table  bordercolor="#181818" border="0" align="center" cellspacing="7" width="100%">
      
		     	<tr>
		     			<td style="width:50px; text-align:center; font-weight:bold; padding:0px 0px 5px 0px;">
							<label style="font-weight:bold; text-align:center; font-size:13px;"><b>S.No:</b></label>
						</td>
						<td style="width:170px; text-align:center; padding:0px 0px 5px 0px;">
							<label style="font-weight:bold; text-align:center; font-size:13px;"><b>Application:</b></label>
						</td>
						<td style="width:80px; text-align:center; padding:0px 0px 5px 0px;">
							<label style="font-weight:bold; text-align:center; font-size:13px;"><b>User Count:</b></label>
						</td>
					</tr>
		   
		  
		   	<% int tempVab = 0; %>
		   	<s:iterator    value="applicationUser">
		   	 <% tempVab++; %>
		   	 <tr>
		     	<td width="50px" style="text-align:left;"><%=tempVab%></td> 
				<td style="text-align:center; width:170px;"><label style=" text-align:center; font-size:12px;" class="dislabel"><s:property value="applicationName" /></label></td>
				<td style="text-align:center; width:80px;"><label style=" text-align:center; font-size:12px; float:none;" class="dislabel"><s:property value="applicationuser" /></label></td>
		   	</tr>
		   	</s:iterator>
		   
		   
		
		   
      </table>
      </center>
</body>

</html>