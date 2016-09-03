<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
function toTitleCase(str,id)
{
    document.getElementById(id).value=str.replace(/\w\S*/g, function(txt){return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();});
}
</script>
	<script type="text/javascript">
		function showHideDiv()
		{
			var vendorName = $("#vendorname option:selected").text();
			if(vendorName=="New Vendor")
			{
				document.getElementById("hideDiv").style.display="none";
				document.getElementById("showDiv").style.display="block";
			}
		}
	</script>
</head>
<body>
<div >
<div class="clear"></div>


				 <s:iterator value="contactTextBox" status="counter">

 				<s:if test="%{mandatory}">
                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                  </s:if>				  <s:if test="#counter.count%2 !=0">
		         <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}">
				  	<span class="needed">
				  	</span>
				  </s:if>
				  <s:if test="%{key=='mobOne'}">
				  	<s:textfield name="%{key}" id="%{key}" maxlength="200" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" onchange="mobExists(this.value);"/>
				  	<img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/>
				  	<div id="empStatus" style="color: red;font-size: small;"></div>
				  </s:if>
				  <s:elseif test="%{key=='mobileNo'}">
				  	<s:textfield name="%{key}" id="%{key}" maxlength="200" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" />
				  </s:elseif>
				  <s:else>
				  	<s:textfield name="%{key}" id="%{key}" maxlength="200" onblur="toTitleCase(this.value,this.id)" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" />
				  </s:else>				  
					</div>
			      </div>
			      </s:if>
			      <s:else>
		          <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}">
				  	<span class="needed">
				  	</span>
				  </s:if>
				  	<s:if test="%{key=='mobOne'}">
				  	<s:textfield name="%{key}" id="%{key}" maxlength="10" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" onchange="mobExists(this.value);"/>
				  	<img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/>
				  	<s:hidden id="status" name="status"></s:hidden>
				  	<div id="empStatus" style="color: red;font-size: small;"></div>
				  </s:if>
				  <s:elseif test="%{key=='mobileNo'}">
				  	<s:textfield name="%{key}" id="%{key}" maxlength="200" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" />
				  </s:elseif>
				  <s:else>
				  	<s:textfield name="%{key}" id="%{key}" maxlength="200" placeholder="Enter Data" onblur="toTitleCase(this.value,this.id)" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" />
				  </s:else>					  
				  </div>
			      </div>
				 </s:else>
				 </s:iterator>
				 <s:iterator value="contactDateTimeBox" status="status">
				  <s:if test="%{mandatory}">
                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>
				  <s:if test="#status.even">
		         <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}">
				  	<span class="needed">
				  	</span>
				  </s:if>
				  	<sj:datepicker id="%{key}"  name="%{key}" yearRange="1900:2020" readonly="true" cssClass="textField" size="20"   changeMonth="true" changeYear="true"  showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Date"/>
					</div>
			      </div>
			      </s:if>
			      <s:elseif test="#status.odd">
		          <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}">
				  	<span class="needed">
				  	</span>
				  </s:if>
				  	<sj:datepicker id="%{key}"  name="%{key}" yearRange="1900:2020"  readonly="true" cssClass="textField" size="20"   changeMonth="true" changeYear="true"  showOn="focus" displayFormat="dd-mm-yy" Placeholder="Select Date"/>
				  </div>
			      </div>
				 </s:elseif>
				 </s:iterator>
				 <s:iterator value="contactFileBox" status="counter1">
				  <s:if test="%{mandatory}">
                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>
				  <s:if test="#counter1.count%2 !=0">
		         <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}">
				  	<span class="needed">
				  	</span>
				  </s:if>
				  	<s:file name="%{key}" id="%{key}" placeholder="Enter Data"  cssClass="textField"/>
					</div>
			      </div>
			      </s:if>
			      <s:else>
		          <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}">
				  	<span class="needed">
				  	</span>
				  </s:if>
				  	<s:file name="%{key}" id="%{key}" placeholder="Enter Data"  cssClass="textField"/>
				  </div>
			      </div>
				 </s:else>
				 </s:iterator>
				 <s:iterator value="contactFormDDBox" status="counter1">
				  <s:if test="%{mandatory}">
                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>
				  <s:if test="#counter1.count%2 !=0">
		         <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}">
				  	<span class="needed">
				  	</span>
				  </s:if>
				  <div id="hideDiv">
				  	 <s:if test="%{key=='vendorname'}">
				  			<s:select 
		                              id="vendorname"
		                              name="vendornameDrop" 
		                              list="vendornameMap"
		                              headerKey="-1"
		                              headerValue="Select Vendor" 
		                              cssClass="textField"
		                              onclick="showHideDiv();"
		                              >
		                  </s:select>
				  </s:if>
				  </div>
				 <div id="showDiv"  style="display: none">
				 	 <s:textfield name="vendorname" id="vendornameId" maxlength="200" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" />
				 </div>
				  	<s:if test="%{key=='industry'}">
				  	<s:select 
		                              id="industry"
		                              name="industry" 
		                              list="industryMap"
		                              headerKey="-1"
		                              headerValue="Select Industry"  
		                              cssClass="textField"
		                              onchange="getsubIndustry('subIndustry',this.value);"
		                              >
		                  </s:select>
				  </s:if>
				  <s:elseif test="%{key=='subIndustry'}">
				  	<s:select 
		                              id="subIndustry"
		                              name="subIndustry" 
		                              list="{'No Data'}"
		                              headerKey="-1"
		                              headerValue="Select Sub-Industry" 
		                              cssClass="textField"
		                              >
		                  </s:select>
				  </s:elseif>
				  <s:elseif test="%{key=='department'}">
				  	<s:select 
		                              id="department"
		                              name="department" 
		                              list="deptMap"
		                              headerKey="-1"
		                              headerValue="Select Department" 
		                              cssClass="textField"
		                              >
		                  </s:select>
				  </s:elseif>
				  
				    <s:elseif test="%{key=='star'}">
				  	<s:select 
		                              id="star"
		                              name="star" 
		                              list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
		                              headerKey="-1"
		                              headerValue="Select Star" 
		                              cssClass="textField"
		                              >
		                  </s:select>
				  </s:elseif>
				  
				      <s:elseif test="%{key=='degreeOfInfluence'}">
				  	<s:select 
		                              id="degreeOfInfluence"
		                              name="degreeOfInfluence" 
		                              list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
		                              headerKey="-1"
		                              headerValue="Select Influence" 
		                              cssClass="textField"
		                              >
		                  </s:select>
				  </s:elseif>
				     <s:elseif test="%{key=='source'}">
				  	<s:select 
		                              id="source"
		                              name="source" 
		                              list="sourceMap"
		                              headerKey="-1"
		                              headerValue="Select Source" 
		                              cssClass="textField"
		                              >
		                  </s:select>
				  </s:elseif>
					</div>
			      </div>
			     
			       <s:if test="%{key=='vendorname'}">
			        <div class="newColumn">
								<div class="leftColumn1">Vendor Type:</div>
								<div class="rightColumn1">
				  			<s:select 
		                              id="vendortypeid"
		                              name="vendorfor" 
		                              list="vendortypeMap"
		                              headerKey="-1"
		                              headerValue="Select Vendor Type" 
		                              cssClass="textField"
		                              >
		                  </s:select>
		                   </div>
		                   </div>
				  </s:if>
			      </s:if>
			      <s:else>
		          <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
								<s:if test="%{mandatory}">
				  	<span class="needed">
				  	</span>
				  </s:if>
				   <s:if test="%{key=='vendorname'}">
				  			<s:select 
		                              id="vendorname"
		                              name="vendorname" 
		                              list="vendornameMap"
		                              headerKey="-1"
		                              headerValue="Select Vendor" 
		                              cssClass="textField"
		                              >
		                  </s:select>
				  </s:if>
				  	<s:if test="%{key=='industry'}">
				  	<s:select 
		                              id="industry"
		                              name="industry" 
		                              list="industryMap"
		                              headerKey="-1"
		                              headerValue="Select Sub-Industry"  
		                              cssClass="textField"
		                              onchange="getsubIndustry('subIndustry',this.value);"
		                              >
		                  </s:select>
				  </s:if>
				  <s:elseif test="%{key=='subIndustry'}">
				  	<s:select 
		                              id="subIndustry"
		                              name="subIndustry" 
		                              list="{'No Data'}"
		                              headerKey="-1"
		                              headerValue="Select Sub-Industry" 
		                              cssClass="textField"
		                              >
		                  </s:select>
				  </s:elseif>
				  <s:elseif test="%{key=='department'}">
				  	<s:select 
		                              id="department"
		                              name="department" 
		                              list="deptMap"
		                              headerKey="-1"
		                              headerValue="Select Department" 
		                              cssClass="textField"
		                              >
		                  </s:select>
				  </s:elseif>
				  
				     <s:elseif test="%{key=='star'}">
				  	<s:select 
		                              id="star"
		                              name="star" 
		                              list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
		                              headerKey="-1"
		                              headerValue="Select Star" 
		                              cssClass="textField"
		                              >
		                  </s:select>
				  </s:elseif>
				  
				     <s:elseif test="%{key=='degreeOfInfluence'}">
				  	<s:select 
		                              id="degreeOfInfluence"
		                              name="degreeOfInfluence" 
		                              list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'}"
		                              headerKey="-1"
		                              headerValue="Select Influence" 
		                              cssClass="textField"
		                              >
		                  </s:select>
				  </s:elseif>
				    <s:elseif test="%{key=='source'}">
				  	<s:select 
		                              id="source"
		                              name="source" 
		                              list="sourceMap"
		                              headerKey="-1"
		                              headerValue="Select Source" 
		                              cssClass="textField"
		                              >
		                  </s:select>
				  </s:elseif>
				  
				  </div>
			      </div>
				 </s:else>
				 </s:iterator>
</div>
</body>
</html>