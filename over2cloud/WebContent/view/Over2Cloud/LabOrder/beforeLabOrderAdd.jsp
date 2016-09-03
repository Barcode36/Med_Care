<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme"
	customBasepath="%{contextz}" />
<SCRIPT type="text/javascript" src="js/helpdesk/common.js"></SCRIPT>

<SCRIPT type="text/javascript">
	$.subscribe('level1', function(event, data) {
		$("#addDialog").dialog('open');
		setTimeout(function() {
			closeAdd() ;
		}, 4000);
		reset("formone");
	});
</script>

<script type="text/javascript">
	function closeAdd() {
		$("#addDialog").dialog('close');
		 backToView();
	}
</script>
<script type="text/javascript">
	function backToView() {
		$("#data_part")
				.html(
						"<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
		$.ajax({
					type : "post",
					url : "view/Over2Cloud/labOrder/viewLabOrderHeader.action",
					success : function(subdeptdata) {
						$("#" + "data_part").html(subdeptdata);
					},
					error : function() {
						alert("error");
					}
				});
	}

	function reset(formId) {
		$("#" + formId).trigger("reset");
	}
</script>
</head>
<body>
	<div class="clear"></div>
	
 <sj:dialog 
    	id="mybuttondialog" 
    	buttons="{ 
    		'Cancel':function() { cancelButton(); } 
    		}" 
    	autoOpen="false" 
    	modal="true" 
    	onCompleteTopics="close"
    	width="310"
		height="500"
    	title=" Edit Box"
    	resizable="false"
    >
</sj:dialog>
	<div class="middle-content">

		<div class="list-icon">
			<div class="head">Lab Order</div>
			<div class="head">
				<img alt="" src="images/forward.png"
					style="margin-top: 50%; float: left;">
			</div>
			<div class="head">Add</div>
		</div>
		<div class="clear"></div>
		<div class="border">
			<div class="container_block">
				<div style="float: left; padding: 20px 1%; width: 98%;">
								
					<s:form id="labOrder" name="labOrder"
						namespace="/view/Over2Cloud/labOrder"
						action="addLabOrderDetails" theme="css_xhtml" method="post"
						enctype="multipart/form-data">
						<center>
							<div
								style="float: center; border-color: black; background-color: rgb(255, 99, 71); color: white; width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
								<div id="errZone" style="float: center; margin-left: 7px"></div>
							</div>
						</center>

				<s:iterator value="pageFieldsColumns" status="status">
						  <s:if test="%{mandatory}">
		     	<span id="complSpan" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
			       	<div class="newColumn" >
			  		<div class="leftColumn1"><s:property value="%{value}"/>:</div>
	           		<div class="rightColumn1">
				    <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				       
				    
				             <s:if test="%{key == 'emp_band'}">
				         		<s:select 
                                      id="%{key}"
                                      name="%{key}" 
                                      list="bandList"
                                      headerKey="-1"
                                      headerValue="Select Band"
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      onchange="showbandDetail(this.value,'band_name','eq')"
                                      >
                          </s:select>
				         </s:if>
				         <s:elseif test="%{key == 'emp_department'}">
				         		<s:select 
                                      id="%{key}"
                                      name="%{key}" 
                                      list="deptList"
                                      headerKey="-1"
                                      headerValue="Select Contact Sub Type"
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      onchange="fetchEmployeeVal(this.value,'empDiv')"
                                      >
                          </s:select>
				         </s:elseif>
				          <s:elseif test="%{key == 'employee'}">
				          <div id="empDiv">
				         		<s:select 
                                      id="%{key}"
                                      name="%{key}" 
                                      list="#{'No Data'}"
                                      headerKey="-1"
                                      headerValue="Select Employee"
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      >
                          </s:select>
                          </div>
				         </s:elseif>
				  
				    </div>
			        </div>
			        
						</s:iterator>
							
									<!-- Buttons -->
						<center>
							<img id="indicator2" src="<s:url value="/images/indicator.gif"/>"
								alt="Loading..." style="display: none" />
						</center>

						<div class="clear">
							<div style="padding-bottom: 10px; margin-left: -76px"
								align="center">
								<sj:submit targets="level123" clearForm="true" value="Save"
									effect="highlight" effectOptions="{ color : '#FFFFFF'}"
									effectDuration="5000" button="true" onBeforeTopics="validate"
									onCompleteTopics="level1" />
								&nbsp;&nbsp;
								<sj:submit value="Reset" button="true"
									cssStyle="margin-left: 139px;margin-top: -41px;"
									onclick="reset('labOrder');" />
								&nbsp;&nbsp;
								<sj:a cssStyle="margin-left: 276px;margin-top: -58px;"
									button="true" href="#" value="Back" onclick="backToView()"
									cssStyle="margin-left: 266px;margin-top: -68px;">Back
					</sj:a>
							</div>
						</div>
					</s:form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>