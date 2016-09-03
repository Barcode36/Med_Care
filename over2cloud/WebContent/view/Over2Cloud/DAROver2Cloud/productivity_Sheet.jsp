<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<script type="text/javascript">
function getProductivityData(id)
{
	var idto=$("#from").val();
	var idfrom=$("#to").val();
	$("#dashboard_status").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$("#dashboard_status").load("view/Over2Cloud/DAROver2Cloud/checkDashDetail.action?idtodate="+idfrom+"&idto="+idto+"&idproduct="+id+"");
}
</script>
</head>

<body>
<div class="list-icon">
	<div class="head">Productivity Management Sheet</div>
  </div>
   <div style=" float:left; padding:5px 0%; width:100%;">
    <div class="border">
                  <div class="newColumn">
								<div class="leftColumn1">From:</div>
								<div class="rightColumn1">
	                            <span class="needed"></span>
                                <sj:datepicker name="from" id="from" value="today" changeMonth="true" cssStyle="margin:0px 0px 10px 0px" changeYear="true" yearRange="1890:2020" showOn="focus" readonly="true" displayFormat="dd-mm-yy" cssClass="textField" placeholder="Select Date"/></div>
				  </div>
				  <div class="newColumn">
								<div class="leftColumn1">To:</div>
								<div class="rightColumn1">
	                            <span class="needed"></span>
                                <sj:datepicker name="to" id="to" value="today" changeMonth="true" cssStyle="margin:0px 0px 10px 0px"  changeYear="true" yearRange="1890:2020" showOn="focus"  readonly="true" displayFormat="dd-mm-yy" cssClass="textField" placeholder="Select Date" /></div>
	     		  </div>
                  <div class="newColumn">
								<div class="leftColumn1">Employee Name:</div>
								<div class="rightColumn1">
                    			<s:select 
                              id="name"
                              list='listemployee'
                              headerKey="-1"
                              headerValue="Select Employee Name" 
                              cssClass="select"
                              cssStyle="width:82%"
                              onchange="getProductivityData(this.value)"
                              
                              >
                  				 </s:select>
				  </div>
				  </div>
</div>
</div>
</body>
<div id="dashboard_status"></div>
				
</html>

