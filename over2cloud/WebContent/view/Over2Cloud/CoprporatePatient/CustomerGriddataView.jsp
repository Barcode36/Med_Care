<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
</script>
</head>
<body>
<sj:dialog 
        id="cpsServicesIdd" 
        title="Book Request" 
        autoOpen="false" 
        modal="true"
        width="1029"
        height="450"
        >
</sj:dialog>

<div class="clear"></div>
<div class="middle-content">
<div class="clear"></div>
 <div style="width:99%;    margin: 5px 0px 0px 6px; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; ">
 <div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
 <s:url id="veiwCustomerDetail" action="veiwCustomerDetail" escapeAmp="false">
<s:param name="healthPkgID" value="%{healthPkgID}"/>
<s:param name="billingGrpID" value="%{billingGrpID}"/>
<s:param name="accountManagerID" value="%{accountManagerID}"/>
<s:param name="customerIds" value="%{customerIds}"/>
<s:param name="billinggroup" value="%{billinggroup}"/>
<s:param name="packagegroup" value="%{packagegroup}"/>		
    </s:url>
    <s:url id="viewModifyPartNo" action="viewModifyPartNo" /> 
    <sjg:grid 
		  id="gridedittableeee"
          href="%{veiwCustomerDetail}"
          groupColumnShow="[false]"
	      groupCollapse="false"
	      groupOrder="['asc']"
          gridModel="customerGridData"
          navigator="false"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="false"
          editinline="false"
          rowList="100,250,500,1000"
          rowNum="15"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          rownumbers="true"
          pagerInput="false"   
          multiselect="true"
          navigatorSearchOptions="{sopt:['eq','cn']}"  
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:250,width:600}"
          editurl="%{viewModifyPartNo}"
          navigatorEditOptions="{closeAfterEdit:true,reloadAfterSubmit:true}"
          shrinkToFit="false"
          autowidth="true"
          loadonce="true"
         
          onEditInlineSuccessTopics="oneditsuccess"
          >
         
           
			
		<s:iterator value="masterViewPropCustomer" id="masterViewPropCustomer" >  
		  <sjg:gridColumn 
			name="%{colomnName}"
			index="%{colomnName}"
			title="%{headingName}"
		    width="%{width}"
			align="%{align}"
			editable="%{editable}"
			formatter="%{formatter}"
			search="%{search}"
			hidden="%{hideOrShow}"
			/>
		  </s:iterator>
	</sjg:grid>
    <!-------------------- End grid -------------------------> 


 
<!-- Code End for Header Strip -->

</div>
</div>
</div>
  <!------------------- Grid Part start -------------------------->                  
  


</body>
</html>
                   
                                    