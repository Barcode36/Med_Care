<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<SCRIPT type="text/javascript">
function sendsinglemail(xxxx){
	var conP = '<%=request.getContextPath()%>';
    var conP = '<%=request.getContextPath()%>';
    var emailidtwo= "pankajk@dreamsol.biz";
	var customer_name = jQuery("#gridedittable").jqGrid('getCell',xxxx, 'first_name');
	var emailid = jQuery("#gridedittable").jqGrid('getCell',xxxx, 'emailid');
	var subject= "Invoice "+xxxx+"  from "+customer_name+" is attached with PDF";
	$("#sendmail").load(conP+"/view/invoiceOver2Cloud/mailinvoiceattchedpdf.action?id="+xxxx+"&subject="+subject.split(" ").join("%20")+"&emailidone="+emailid+"&emailidtwo="+emailidtwo);
}
</script>

<script type="text/javascript">
	//Conatct masters works STARTS from here
	    function getselectedids() {
	    var s;
	    var myArray = [];
	    var mymobileArray = [];
    	 s = $("#gridedittable").jqGrid('getGridParam','selarrrow');
    	  for(var i=0; i<s.length; i++){
	         myArray.push(jQuery("#gridedittable").jqGrid('getCell',s[i], 'emailid'));
	         mymobileArray.push(jQuery("#gridedittable").jqGrid('getCell',s[i], 'mobilenumber'));
	        }
    	  var emailidtwo= "pankajk@dreamsol.biz";
	      var first_name= "Dear";
          var conP = "<%=request.getContextPath()%>";
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		    type : "post",
		    url : conP+"/view/contactOver2Cloud/sendmail.action?id="+s+"&first_name="+first_name.split(" ").join("%20")+"&emailidone="+myArray+"&emailidtwo="+emailidtwo+"&mobileno="+mymobileArray,
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
</script>
<script type="text/javascript">
	//Conatct masters works STARTS from here
	    function addingroup() {
	    var myArray;
	    
    	 myArray = $("#gridedittable").jqGrid('getGridParam','selarrrow');
          var conP = "<%=request.getContextPath()%>";
		$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
		     type : "post",
		     url : conP+"/view/groupOver2Cloud/addingroupdata.action",
		     data: "myArray="+myArray,
		   
		    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
		},
		   error: function() {
	            alert("error");
	        }
		 });
	}
</script>

</head>
<body>
<div class="container_block"><div style=" float:left; padding:20px 5%; width:90%;">
<div class="tabs" align="left">
                                    <s:url id="filedownloadContactreportzzz" namespace="/view/contactOver2Cloud" action="download" ></s:url>
								    <ul>
									<li style="margin-left: -14px;"><a href="javascript:void();"  onclick="contactdetailsAdd();"> Create New Contact</a></li>
									<li><a href="javascript:void();" id="grid_multi_getselectedbutton" onClick="getselectedids();">Send Mail and SMS</a></li>
									<li><a href="javascript:void();" id="grid_multigetselectedbutton" onClick="addingroup();">Add in Group</a></li>
									<li><s:a href="javascript:void();" onclick="pageisNotAvibale();">Download Contact</s:a></li>
									<li><a href="javascript:void();" onclick="pageisNotAvibale();">Upload Contact</a></li>
									<li><a href="javascript:void();" onclick="pageisNotAvibale();">Setting Contact</a></li>
								</ul>
							</div>
 <div style="display: none">
  <span id="mappedtablele"><s:property value="%{mappedtablele}"/></span>
   <span id="basicdetails_table"><s:property value="%{basicdetails_table}"/></span>
   <span id="addressdetails_table"><s:property value="%{addressdetails_table}"/></span>
   <span id="customdetails_table"><s:property value="%{customdetails_table}"/></span>
   <span id="descriptive_table"><s:property value="%{descriptive_table}"/></span>
   <span id="createtbasicdetails_table"><s:property value="%{createtbasicdetails_table}"/></span>
   <span id="createaddressdetails_table"><s:property value="%{createaddressdetails_table}"/></span>
 </div>
<center>
<div class="form_inner" id="form_reg" style="margin-left: -51px;">
<div class="page_form">
<s:url id="viewcontactData" action="viewcontactData" escapeAmp="false">
<s:param name="mappedtablele" value="%{mappedtablele}"></s:param>
<s:param name="basicdetails_table" value="%{basicdetails_table}"></s:param>
<s:param name="addressdetails_table" value="%{addressdetails_table}"></s:param>
<s:param name="customdetails_table" value="%{customdetails_table}"></s:param>
<s:param name="descriptive_table" value="%{descriptive_table}"></s:param>
<s:param name="createtbasicdetails_table" value="%{createtbasicdetails_table}"></s:param>
<s:param name="createaddressdetails_table" value="%{createaddressdetails_table}"></s:param>
</s:url>
<s:url id="editContactDataGrid" action="editContactDataGrid" escapeAmp="false">
<s:param name="createtbasicdetails_table" value="%{createtbasicdetails_table}"></s:param>
</s:url>
<center>
<sjg:grid 
		  id="gridedittable"
          caption="%{headerName}"
          href="%{viewcontactData}"
          gridModel="contactDetail"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="%{editDeptData}"
          navigatorSearch="true"
          rowList="5,10,15,20,25,50,75,100,150,200,300,400,500,600,700,800,900,1000"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          rowNum="5"
          width="1000"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          editurl="%{editContactDataGrid}"
          shrinkToFit="false"
          >
             
		<s:iterator value="contactGridColomns" id="contactGridColomns" >  
		<sjg:gridColumn 
		name="%{colomnName}"
		index="%{colomnName}"
		title="%{headingName}"
		width="%{width}"
		align="%{align}"
		editable="%{editable}"
		search="%{search}"
		hidden="%{hideOrShow}"
		/>
		</s:iterator>  
</sjg:grid>
</center>
</div>
</div>
</center>
</div>
</div>
      <div id="sendmail">
<center><ul>   
  <sj:a id="downloadPDF" 
        
                button="true" 
                onclick="setConatctDownloadType('downloadPDF')"
                buttonIcon="ui-icon-arrowthickstop-1-s"
        >
                PDF Report
        </sj:a>
          <sj:a id="downloadExcel" 
             onclick="setConatctDownloadType('downloadExcel')" 
             buttonIcon="ui-icon-arrowthickstop-1-s"
             button="true"
        >
                Excel Report
        </sj:a>
          <sj:a id="downloadCSV" 
                onclick="setDownloadTypestillnot('downloadCSV')"
                button="true" 
                buttonIcon="ui-icon-arrowthickstop-1-s"
        >
                CSV Report
        </sj:a>
        
          <sj:a id="downloadHTML" 
                button="true" 
                onclick="setDownloadTypestillnot('downloadHTML')"
                buttonIcon="ui-icon-arrowthickstop-1-s"
        >
                HTML Report
        </sj:a>
        </ul>
        </center>
       </div>
        <center>
      

  </center>
</body>
</html>