<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%String userRights = session.getAttribute("userRights") == null ? "" : session.getAttribute("userRights").toString();%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="/js/feedback/feedback.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/js.js"/>"></script>

<script type="text/javascript">
function pageisNotAvibale(){
$("#facilityisnotavilable").dialog('open');
}
</script>

<SCRIPT type="text/javascript">
var row=0;
$.subscribe('rowselect', function(event, data) {
    row = event.originalEvent.id;
});    
function editRow()
{
    if(row==0)
    {
        alert("Please select atleast one row.");
    }
    jQuery("#gridedittable").jqGrid('editGridRow', row ,{height:400, width:425,reloadAfterSubmit:true,closeAfterEdit:true,
        top:0,left:350,modal:true});
}

function deleteRow()
{
    if(row==0)
    {
        alert("Please select atleast one row.");
    }
    //Changes by abhay
    $("#gridedittable").jqGrid('delGridRow',row, {height:120,reloadAfterSubmit:true,msg: 'Inactive selected record(s)?',bSubmit: 'Inactive'});
}
function searchRow()
{
     $("#gridedittable").jqGrid('searchGrid', {sopt:['eq','cn']} );
}
function reloadGrid()
{
    $.ajax({
        type : "post",
        url : "/over2cloud/view/Over2Cloud/hr/employeeViewHeader.action",
        success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
}

function addNewSubGroup() {

    var conP = "<%=request.getContextPath()%>";
    $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : conP+"/view/Over2Cloud/hr/beforeContactCreate.action",
        success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
}

function uploadContact() {

    var conP = "<%=request.getContextPath()%>";
    $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : conP+"/view/Over2Cloud/hr/beforeUploadContact.action",
        success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
}

function viewContactDoc(cellvalue, options, row)
{
    var context_path = '<%=request.getContextPath()%>';
    return "<a href='#' title='Download' onClick='contactDocDownload("+row.id+")'><img src='"+ context_path +"/images/docDownlaod.jpg' alt='Download'></a>";
}


function contactDocDownload(contactId) 
{
    $.ajax({
        type : "post",
        url : "view/Over2Cloud/hr/contactDocDownload.action?contId="+contactId,
        success : function(data) 
        {
            $("#data_part").html(data);
        },
        error: function()
        {
            alert("error");
        }
     });
}

function viewEmpImage(cellvalue, options, row) {

    return "<a href='#' onClick='javascript:openDialog10("+row.id+ ")'>" + cellvalue + "</a>";
}


function openDialog10(rowId)
{
    id=rowId;
    $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    $.ajax({
        type : "post",
        url : "view/Over2Cloud/hr/contactImageView.action?id="+ rowId,
        success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
}

function employee(){
    $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
    var conP = "<%=request.getContextPath()%>";
    $.ajax({
        type : "post",
        url : conP+"/view/Over2Cloud/hr/beforeEmployee.action?empModuleFalgForDeptSubDept=1",
        success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
    },
       error: function() {
            alert("error");
        }
     });
}
function getOnChangePrimaryData(searchFields,SearchValue)
{
    var statusCheck=$("#statusCheck").val();
    $.ajax({
        type : "post",
        url : "/over2cloud/view/Over2Cloud/hr/employeeView.action",
        data:"searchFields="+searchFields+"&SearchValue="+SearchValue+"&statusCheck="+statusCheck,
       success : function(data) {
       $("#datapart").html(data);
    },
       error: function() {
            alert("error");
        }
     });
}

function getSearchData()
{
    var statusCheck=$("#statusCheck").val();
    $.ajax({
         type :"post",
           url : "/over2cloud/view/Over2Cloud/hr/employeeView.action",
        data:"statusCheck="+statusCheck,
         success : function(data) 
        {
            $("#datapart").html(data);
        },
        error: function()
        {
            alert("error");
        }
     });
}

function seachMobData(fieldser,fieldval)
{
    //var searchMob=$("#searchMob").val();
    var statusCheck=$("#statusCheck").val();

    $.ajax({
        type : "post",
        url : "/over2cloud/view/Over2Cloud/hr/employeeView.action",
       data:"fieldser="+fieldser+"&fieldval="+fieldval+"&statusCheck="+statusCheck,
        success : function(data) 
        {
         $("#datapart").html(data);
        },
       error: function() {
            alert("error");
        }
     });
}
function seachEmpIdData(fieldser,fieldval)
{
    //var empId=$("#empId").val();
    var statusCheck=$("#statusCheck").val();
    $.ajax({
        type : "post",
        url : "/over2cloud/view/Over2Cloud/hr/employeeView.action",
       data:"fieldser="+fieldser+"&fieldval="+fieldval+"&statusCheck="+statusCheck,
        success : function(data) 
        {
         $("#datapart").html(data);
        },
       error: function() {
            alert("error");
        }
     });
}

function loadGrid()
{
    
    $.ajax({
        type : "post",
        url : "/over2cloud/view/Over2Cloud/hr/employeeView.action",
        success : function(feeddraft) {
       $("#datapart").html(feeddraft);
    },
       error: function() {
            alert("error");
        }
     });
}

loadGrid();

 $(document).ready(function(){
      var droplist = $('#statusCheck');
      droplist.change(function(e){
        if (droplist.val() == '1') {
          $('#delButton').hide();
        }
        else {
          $('#delButton').show();
        }
      });
    }); 


function employeeFormatter(cellValue,options,rowObject)
{
    return '<a href="#"> <img title="Full View" src="images/WFPM/fullView.jpg" onClick="employeeDetailsFormatter('+rowObject.id+',\''+rowObject.mobOne+'\',\''+rowObject.empName+'\')"></a>';

}

function employeeDetailsFormatter(valuepassed,mobile,empNameValue) 
{
     
         $("#takeActionGridss").dialog({title: empNameValue});
        $("#takeActionGridss").load("view/Over2Cloud/hr/empFullView.action?id="+valuepassed+"&mobTwo="+mobile);
        $("#takeActionGridss").dialog('open');
}

var id;
getSearchData('statusCheck','0');

</SCRIPT>
</head>
<body>
<div class="list-icon">
     <div class="head">View Employee</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head"></div><div class="head">Active: <s:property value="%{activecount}"/>&nbsp;Inactive:  <s:property value="%{inActivecount}"/>&nbsp;Total Records: <s:property value="%{toatlcount}"/>
</div>
<div class="clear"></div>
<div style="align:center; padding-top: 10px;padding-left: 10px;padding-right: 10px;">
<div class="listviewBorder">
<!-- Code For Header Strip -->
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
    <table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
        <tbody>
            <tr>
                  <!-- Block for insert Left Hand Side Button -->
                  <td>
                      <table class="floatL" border="0" cellpadding="0" cellspacing="0">
                        <tbody><tr><td class="pL10">
                            <sj:a id="editButton" title="Edit" buttonIcon="ui-icon-pencil" cssStyle="height:25px; width:32px"  cssClass="button" button="true" onclick="editRow()"></sj:a>
                            
                             <sj:a id="delButton" title="Inactive" buttonIcon="ui-icon-trash"   cssClass="button" cssStyle="height:25px; width:32px" button="true"  onclick="deleteRow()"></sj:a>
                            
                            <sj:a id="searchButton" title="Search" buttonIcon="ui-icon-search" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="searchRow();"></sj:a>
                            <sj:a id="refButton" title="Refresh" buttonIcon="ui-icon-refresh" cssStyle="height:25px; width:32px" cssClass="button" button="true" onclick="reloadGrid();"></sj:a>  
                            
                             
                              <s:select  
                                        id                    =        "contactSubType"
                                        name                =        "contactSubType"
                                        list                =        "deptMap"
                                          headerKey           =       "-1"
                                        headerValue         =       "Select Contact Sub-Type"
                                        cssClass            =      "button"
                                        cssStyle            =      "margin-top: -29px;margin-left:3px"
                                        theme               =       "simple"
                                           onchange            =        "getOnChangePrimaryData('deptname',this.value) "
                                       
                                   
                                   >
                                   </s:select>
                             
                              <s:select  
                                    id                    =        "statusCheck"
                                    name                =        "statusCheck"
                                    list                =        "#{'0':'Active','1':'Inactive','-1':'All'}"
                                   cssClass             =      "button"
                                   cssStyle             =      "margin-top: -29px;margin-left:3px"
                                  theme                 =       "simple"
                                  onchange                =        "getSearchData();totalExp();"
                              
                                   >
                                   </s:select>
                                   
                               Mobile No.:<s:textfield name="searchMob" id="searchMob" onkeyup="seachMobData('mobOne',this.value);" cssStyle="margin-top: -27px"  placeholder="Enter Mobile No."  cssClass="button" theme="simple"/>    
                                Emp ID:<s:textfield name="searchEmpId" id="searchEmpId" onkeyup="seachEmpIdData('empId',this.value);" cssStyle="margin-top: -27px; margin-left: 2px"  placeholder="Enter Emp ID"  cssClass="button" theme="simple"/>
                             </td></tr></tbody>
                      </table>
                  </td>
                  
                  <!-- Block for insert Right Hand Side Button -->
                  <td class="alignright printTitle">
                  <sj:a button="true" cssClass="button" cssStyle="height:25px;" title="Add"  buttonIcon="ui-icon-plus" onclick="employee();">Add</sj:a> 
                  </td>
            </tr>
        </tbody>
    </table>
</div>
</div>
<s:url id="viewCommonContact" action="employeeViewInGrid"/>
<s:url id="editEmployeeContact" action="editEmployee"/>

    <div id="datapart"></div>
    
        </div></div>
        <sj:dialog
          id="takeActionGridss"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Employee Details"
          modal="true"
          closeTopics="closeEffectDialog"
          width="1000"
          height="1100"
          draggable="true"
          resizable="true"
         
          >
</sj:dialog>
<sj:dialog
    id="leadFullViewDialog"
    title="Full View"
    modal="true"
    autoOpen="false"
    width="900"
    height="400"    
    ></sj:dialog>



</body>
</html>