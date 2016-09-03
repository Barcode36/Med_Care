<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>


</head>

<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<SCRIPT type="text/javascript">
 $.subscribe('makeEffect', function(event,data)
  {
	 setTimeout(function(){ $("#complTarget").fadeIn(); }, 10);
	 setTimeout(function(){ $("#complTarget").fadeOut(); }, 400);
  });
 
 
function selectOG(og1)
	{
				
		$("#allotedDialog").dialog('open');
		$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : "/over2cloud/view/Over2Cloud/OperationalAnalysis/readExcel.action?og1="+og1,
		    success : function(data) {
		    	$("#allotedDialog").html(data);
			},
		    error: function() {
	            alert("error");
	        }
		 });
	}

	
	
	
	
function reload()
{
	
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
    $.ajax({
			type: "post",
			url : "/over2cloud/view/Over2Cloud/OperationalAnalysis/openDialog.action",
			success: function (data)
			{
				$("#data_part").html(data);
				
			},
			error: function ()
			{
				alert("error");
				
			}
	  	 });
};
	
	
	



 
function getCloseData()
{
	
	$("#allotedDialog").dialog('close');
}
</SCRIPT>

<script type="text/javascript">
function printDiv(divName) {
    var printContents = document.getElementById(divName).innerHTML;
    $("#printSelect").dialog('close');
    var myWindow = window.open("","myWindow","width=900,height=600"); 
    myWindow.document.write(printContents);
    myWindow.moveTo(300,200); 
    myWindow.print();
    myWindow.close();
}


</script>

<script type="text/javascript">
    $(document).ready(function() {
		$.subscribe('dialogopentopic', function(event,ui) {
	        
		});
		$.subscribe('dialogclosetopic', function(event,ui) {
	        
	        reload();
		});
		$.subscribe('dialogbeforeclosetopic', function(event,ui) {
	       
		});
    });
    </script>  



<style type="text/css">
	.divstyle {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 94px; height: 32px; background-color: yellowgreen; border: 1px solid #ff6633; border-radius: 11px; margin:6px;
}

.divstyle:hover
{
	background: #ff6633;
	-webkit-transform: scale(1.2);
   -moz-transform: scale(1.2);
   -ms-transform: scale(1.2);
   -o-transform: scale(1.2);
   transform: scale(1.2);  
}

</style>

<sj:dialog
          id="allotedDialog"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Orgnisation Level"
          modal="true"
          onCloseTopics="dialogclosetopic" 
          onBeforeCloseTopics="dialogbeforeclosetopic" 
          width="1000"
          height="400"
          >
          
</sj:dialog>
<body>


<div class="type-button">
            
                    <sj:a button="true"style="height: 17px;margin-top:-5px;margin-right: 10px;width:30px;float: right;"cssClass="button" buttonIcon="ui-icon-print" title="Print"onclick="printDiv('printDiv')"></sj:a>
              </div>
<div id='printDiv'>
	

	<s:iterator value="pUHID" var="listVal" status="counter">
	<div class="contentdiv-small" style="overflow: hidden;  height:132px; width:98%;  margin-left:1px; margin-right:1px;   border-color: black" >
    <div class="clear"></div>
           <div style="height:14px; margin-bottom:2px;" id='1stBlock'></div>
                  
                       <table  align="center" border="0" width="100%" height="10%" style="border-style:dotted solid;">
                               <tbody>
                                    <tr bgcolor="black" bordercolor="black" >
                                     <td align="center" width="8%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" ><b>Ticket No</b></font></td>
	                                       <td align="center" width="20%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" ><b>Patient Name</b></font></td>
	                                       <td align="center" width="20%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" ><b>Corporate Name</b></font></td>
	                                       <td align="center" width="9%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" ><b>Status</b></font></td>
	                                       <td align="center" width="20%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" ><b>Location</b></font></td>
	                                       <td align="center" width="30%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" ><b>Book Schedule</b></font></td>
	                                       <td align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" ><b>Services</b></font></td>
	                                        <td align="center" width="10%" height="70%"><font face="Arial, Helvetica, sans-serif" color="#FFFFFF" ><b>Package</b></font></td>
	                                       
	                                </tr>
	                                <s:iterator value="pDataCart" var="listVal1" status="counter1">
	                                
	                                <s:if test='%{#listVal[0]==#listVal1[0]}'>
 									<tr>	
 											<td align="center" width="12%"><s:property value="%{#listVal1[0]}" /></td>
 											<td align="center" width="12%"><s:property value="%{#listVal1[1]}" /></td>
                                           	<td align="center" width="5%"><s:property value="%{#listVal1[2]}" /></td>
                                           	<td align="center" width="5%"><s:property value="%{#listVal1[3]}" /></td>
                                           	<td align="center" width="12%"><s:property value="%{#listVal1[4]}" /></td>
 											<td align="center" width="40%"><s:property value="%{#listVal1[5]}" /></td>
                                           	<td align="center" width="5%"><s:property value="%{#listVal1[6]}" /></td>
                                           	<td align="center" width="5%"><s:property value="%{#listVal1[7]}" /></td>
                                           
                                           
                                    </tr>
                                    </s:if>
                                   
                                    
                                    
                                    </s:iterator>
                                    
                                    
                               </tbody>
                       </table>
                 </div>
	
	
		
		</s:iterator>
	</div>
	
	
	
	
</body>
</html>