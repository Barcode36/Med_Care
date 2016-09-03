<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>

<script type="text/javascript">
$.subscribe('getescalationdata', function(event,data){
		var module=$("#moduleName").val();
		

//alert(module+":::"+data.value);
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/EventReminders/getStandardescalationdata.action?module="+module+"&dept="+data.value,
	    success : function(data) 
	    {
	    	console.log(data);
	    	$('#level1').empty();
	    	$('#level2').empty();
	    	$('#level3').empty();
	    	$('#level4').empty();
	    	$('#level5').empty();
	    	$(data).each(function(index){
				
	    		if(typeof this.level1 != 'undefined'){
	    			$('#level1').append('<div style="border:1px solid rgba(200, 200, 200, 0.93);float:left;margin-right:5px;border-radius:3px;">'+this.level1+'</div>');
	    		}
	    		if(typeof this.level2 != 'undefined'){
	    			$('#level2').append('<div style="border:1px solid rgba(200, 200, 200, 0.93);float:left;margin-right:5px;border-radius:3px;">'+this.level2+'</div>');
	    		}
	    		if(typeof this.level3 != 'undefined'){
	    			$('#level3').append('<div style="border:1px solid rgba(200, 200, 200, 0.93);float:left;margin-right:5px;border-radius:3px;">'+this.level3+'</div>');
	    		}
	    		if(typeof this.level4 != 'undefined'){
	    			$('#level4').append('<div style="border:1px solid rgba(200, 200, 200, 0.93);float:left;margin-right:5px;border-radius:3px;">'+this.level4+'</div>');
	    		}
	    		if(typeof this.level5 != 'undefined'){
	    			$('#level5').append('<div style="border:1px solid rgba(200, 200, 200, 0.93);float:left;margin-right:5px;border-radius:3px;">'+this.level5+'</div>');
	    		}
	    		
	    		
	    		
	    		
	    		
	    	});
	    },
	    error: function() 
	    {
	        alert("error");
	    }
	 });
});

function getContactMappingDragDrop(){
	
	if( $("#subType").val()==""){
		alert("Please Select Application Name and Department");
	}else{
		
			$.ajax({
			    type : "post",
			    url : "view/Over2Cloud/EventWiseAction/dragDropEscMapping.jsp",
			    success : function(data) {
		       		$("#dataDiv").html(data);
		       		$("#dataDialog").dialog({title:'Define Escalation Matrix for '+$('#moduleName').text()+' and Department '+$('#subType').text()});
		       		$("#dataDialog").dialog('open');
				},
			    error: function() {
		            alert("error");
		        }
			 });
		
	}
}
</script>
</head>
<body>
<sj:dialog
          id="dataDialog"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          modal="true"
          closeTopics="closeEffectDialog"
          width="1000"
          height="450"
          >
          <div id="dataDiv"></div>
</sj:dialog>
<%-- <s:hidden id="dataFor" value="%{moduleName}"/> --%>

<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	 <div class="head">
	 Standard Escalation</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">View For <s:property value="%{mainHeaderName}"/> Department</div> 
	 <div id="dragdrop" ></div>
	<div id="test"  class="alignright printTitle">
	<!-- <a href="#"><img alt="DragDrop-icon" src="images/1-512.png" width="25px" height="25px" style="margin-top: 7px;position: absolute;right: 177px" title="Drag Drop Mapping" onclick="getContactMappingDragDrop();" ></a> -->
       
    </div> 
	 
</div>
<div class="clear"></div>
<div style="display: none;" id="hiddenModule"></div>
<div style="display: none;" id="hiddenDept"></div>

<div class="listviewBorder"  style="margin-top: 10px;width: 97%;margin-left: 20px;" align="center">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr><td class="pL10">   
						
				    <sj:autocompleter
				       id="moduleName"
				       name="moduleName"
				       list="moduleName"
				     selectBox="true"
						selectBoxIcon="true"
						selectable="true"
					   theme="simple"
					 
					   
					 />
						
				
					 <sj:autocompleter 
                      id="subType" 
			          name="subType" 
			          list="deptNameMap"
			         selectBox="true"
						selectBoxIcon="true"
						selectable="true"
			          onSelectTopics="getescalationdata"
         			 />
         			 
         			 <!--<sj:a  button="true" cssClass="button" cssStyle="height: 27px;margin-left: 4px;" onclick="getSearchData();">OK</sj:a>
					      -->
					      <a href="#"><img alt="DragDrop-icon" src="images/1-512.png" width="25px" height="25px" style="margin-top: -23px;position: absolute;right: 33px" title="Customise Escalation" onclick="getContactMappingDragDrop();" ></a>
					      </td></tr></tbody>
					  </table>
				  </td>
				 <td class="alignright printTitle">
			 		
	       		</td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<div style="overflow: scroll; height: 430px;">
<div id="contactDiv">
<table id="data_table"  align="center" border="1"  width="90%" style="margin-top: 5%">
	<tr style="background: rgba(239, 238, 238, 0.66);">
	 <td width="" align="center">Level: 1</td> <td width="" align="center">To</td>
	 <td id="level1" width=""></td>
	 <td width="" align="center">L1 Escalation:  </td>
	 <td id="l1esc" width=""></td>
	</tr>
	<tr >
	<td width=""></td> 
	 <td width="" align="center">CC</td> 
	 <td id="level1cc" width=""></td>
	 
	</tr>
		<tr style="background: rgba(239, 238, 238, 0.66);" >
	 <td width="" align="center">Level: 2</td> <td width="" align="center">To</td>
	 <td id="level2" width=""></td>
	  <td align="center">L2 Escalation:  </td>
	 <td id="l2esc" width=""></td>
	</tr>
		<tr >
	<td width=""></td> 
	 <td width="" align="center">CC</td> 
	 <td id="level1cc" width=""></td>
	 
	</tr>
		<tr style="background: rgba(239, 238, 238, 0.66);">
	 <td width="" align="center">Level: 3</td> <td width="" align="center">To</td>
	 <td id="level3" width=""></td>
	  <td align="center">L3 Escalation:  </td>
	 <td id="l3esc" width=""></td>
	</tr>
		<tr >
	<td width=""></td> 
	 <td width="" align="center">CC</td> 
	 <td id="level1cc" width=""></td>
	 
	</tr>
		<tr style="background: rgba(239, 238, 238, 0.66);">
	 <td width="" align="center">Level: 4</td> <td width="" align="center">To</td>
	 <td id="level4" width=""></td>
	  <td align="center">L4 Escalation:  </td>
	 <td id="l4esc" width=""></td>
	</tr>
		<tr >
	<td width=""></td> 
	 <td width="" align="center">CC</td> 
	 <td id="level1cc" width=""></td>
	 
	</tr>
		<tr style="background: rgba(239, 238, 238, 0.66);">
	 <td width="" align="center">Level: 5</td> <td width="" align="center">To</td>
	 <td id="level5" width=""></td>
	  <td align="center">L5 Escalation:  </td>
	 <td id="l5esc" width=""></td>
	</tr>
		<tr >
	<td width=""></td> 
	 <td width="" align="center">CC</td> 
	 <td id="level1cc" width=""></td>
	 
	</tr>
	<tr>
	<td colspan="4">Note: Time mentioned in To L-1 is extra time given after due date & time of the event.</td>
	</tr>
	<tr>
	<td colspan="4">Escalation Matrix is subject to present employees hence absent employees are excluded & respective task are passed on next level with info to absentee.</td>
	</tr>
	</table>

</div>
</div>
</div>
</div>
</body>
</html>