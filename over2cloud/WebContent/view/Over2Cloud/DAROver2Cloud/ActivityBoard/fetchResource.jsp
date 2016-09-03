<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<s:url value="/js/task_js/task.js"/>"></script>
<title>Insert title here</title>
</head>
<script type="text/javascript">


function fetchData(id,monthCount)
{
	
	var currentMonth="0";
    if (id=='1') 
    {
	   currentMonth=$("#dateVal").val();
    }
     $.ajax
     ({
    	type:"post",
    	url:"view/Over2Cloud/DAROver2Cloud/fetchOtherResource.action?id="+id+"&monthCounter="+monthCount+"&fromDate="+currentMonth,
    	success:function(data)
    			{
		    		$.ajax
		    	     ({
		    	    	type:"post",
		    	    	url:"view/Over2Cloud/DAROver2Cloud/calculateDate.action?id="+id+"&monthCounter="+monthCount+"&fromDate="+currentMonth,
		    	    	success:function(data)
    	    			{
		    	    		$("#dateVal").val(data.currentDate); 
    	    				$("#dateDiv").html(data.currentDate); 
    	    			},
		    	    	error:function()
    	    			{
    	    				alert("Error");
    	    			}
		    	    			
		    	     });
    				$("#result").html(data); 
    			},
    	error:function()
    			{
    				alert("Error");
    			}
    			
     });
    		
 
}
fetchData('1','0');
</script>
<body>

<s:hidden id="dateVal" value="%{fromDate}" />
<div style="height:auto; margin-bottom:10px;"  align="center" >
             <div style="margin-left: 40%; float: left;">
				<div style="float: left;">
					<img alt="Previous" onclick="previousNextDayData4Resource('1','backward');" style="cursor: pointer;" src="images/backward.png" title="Previous">
					&nbsp;&nbsp;&nbsp;
				</div>
				<div style="float: left;margin-left: -68px;">	
					 <b>Resource Status As On</b> 
					  <b>
					 <div id="dateDiv" style="margin-left: 201px;margin-top: -13px;">
					 		 <b><s:property value="%{fromDate}"/></b> 
					 </div>
					 </b>
				</div>
				<div id="activityDate" style="color: white; float: left;"></div>
				<div style="float: right;">
					&nbsp;&nbsp;&nbsp;<img alt="Next" onclick="previousNextDayData4Resource('1','forward');" style="cursor: pointer;" src="images/forward.png" title="Next">
				</div>
			</div>
</div>
<br>
<div id="result">

</div>
</body>
</html>