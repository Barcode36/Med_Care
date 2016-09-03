<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<script type="text/javascript" src="<s:url value="/js/order/feedback.js"/>"></script>
<SCRIPT type="text/javascript">



$.subscribe('removeCart', function(event, data) {
    restore();
    ORDERActivityRefresh();
    getPrintData();
    
});

	function getPrintData()
 	{
		$("#takeActionGrid").dialog('close');
		var ids = $("#complaintid").val();
		$("#printSelect").dialog('open');
		$("#printSelect").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$("#printSelect").load("/over2cloud/view/Over2Cloud/CorporatePatientServices/Lodge_Feedback/printCartPage.action?complaintid="+ids );
 	}


</SCRIPT>

</head>

<body>

		<sj:dialog id="printSelect" 
					title="Print Cart" 
					autoOpen="false"  
					modal="true" 
					height="870" 
					width="500" 
					showEffect="drop">
		</sj:dialog>
<div class="clear"></div>
<div class="middle-content">
<%-- <div class="list-icon">
	<div class="head">Take Action</div> <img alt="" src="images/forward.png" style="margin-top:10px; float: left;"> <div class="head">Ticket ID: <s:property value="%{ticket_no}"/>, Opened On: <s:property value="%{open_date}"/> & <s:property value="%{open_time}"/></div>
</div> --%>
<div class="clear"></div>
<div style="overflow:auto; height:220px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">

    
<center></center>
		<s:form id="formone1" name="formone" action="assignbulkorderCart"  theme="css_xhtml"  method="post" enctype="multipart/form-data">
			<s:hidden id="complaintid" name="complaintid" value="%{complaintid}"></s:hidden>
		    
		    
		    <center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone" style="float:center; margin-left: 7px"></div></div></center>
	     
	
	
		    <div class="clear"></div>
		       <div class="clear"></div>
		        <span  class="pIds" style="display: none; ">assignToMan#Assigned Technician#D#,</span>
   <div class="clear"></div>
             <div class="newColumn">
		       <div class="leftColumn">Select Technician:</div>
		            <div class="rightColumn">
		               <span class="needed"></span>
		               <div style="margin-top: 3px;">
		                <sj:autocompleter 
		                            id="assignToMan"
		                            name="assignToMan" 
		                          	list=	"assignTechnician4Cart"
		                            cssClass="select"
		                            cssStyle="width:82%"
		                            selectBox	=	"true"
									selectBoxIcon	=	"true"
									
		                              >
		                              
		                              
                  		 </sj:autocompleter> 
                  		 </div> 
		              </div>
		       </div>       
		         <span  class="pIds" style="display: none; ">tatCart1#Due#Date#,</span>
		          <div class="newColumn">
		       <div class="leftColumn">Due Date:</div>
		            <div class="rightColumn">
		               <span class="needed"></span>
		              <sj:datepicker cssStyle="height: 30px; width: 133px;" timepickerOnly="false" minDate="0" timepicker="false" timepickerAmPm="false" cssClass="button" id="tatCart1" name="tatCart1" size="20"   readonly="true"  displayFormat="dd-mm-yy" showOn="focus"   Placeholder="Select Date"/>    
		              </div>
		       </div>    
		        <span  class="pIds" style="display: none; ">tatCart#Due#Time#,</span>
		         <div class="newColumn">
		       <div class="leftColumn">Due Time:</div>
		            <div class="rightColumn">
		               <span class="needed"></span>
		              <sj:datepicker cssStyle="height: 30px; width: 133px;" timepickerOnly="true" timepicker="true" timepickerAmPm="false" cssClass="button" id="tatCart" name="tatCart" size="20"   readonly="true"  displayFormat="dd-mm-yy" showOn="focus"   Placeholder="Select Time"/>    
		              </div>
		       </div>    
		         
		       
	     
   <!-- Buttons -->
   <div class="clear"></div>
   <div class="clear"></div>
      <div class="clear"></div>
   <div class="clear"></div>
   <div class="fields" align="center">
   <center><img id="indicator1" src="<s:url value="/images/ajax-loader_small.gif"/>" alt="Loading..." style="display:none"/></center>
   <ul><li class="submit">
   <div class="type-button">
   <div id="bt" style="display: block;">
   <sj:submit 
   				id ="actionId"
	           targets="target1" 
	           clearForm="true"
	           value="  Allot  " 
	           effect="highlight"
	           effectOptions="{ color : '#222222'}"
	           effectDuration="5000"
	           button="true"
	           onBeforeTopics="validateCompletionTip"
	           onCompleteTopics="removeCart"
	                    
	             
			   />
	           
	            
   </div>
   </div>
   </li>
   </ul>
   </div>
   <div id="target1"></div>
</s:form>
</div>
</div>
</body>
</html>