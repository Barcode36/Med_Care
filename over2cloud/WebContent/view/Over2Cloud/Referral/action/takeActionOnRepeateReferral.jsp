<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript">

$.subscribe('level2', function(event,data)
        {
	//$("#completionResult1").dialog({title: 'Confirmation Message..',width: 400,height: 100});
	//$("#completionResult1").dialog('open');
	setTimeout(function() {$("#result1").fadeIn();}, 10);
	setTimeout(function() {$("#result1").fadeOut();}, 5000);
	notSave();
        });

function notSave(){

	$("#completionResult").dialog('close');
}

</script>
</head>
<body>
<sj:dialog id="completionResult1" showEffect="slide"
		hideEffect="explode"
		autoOpen="false"
		cssStyle="overflow:hidden;text-align:center;margin-top:10px;"
		modal="true" draggable="true"
		resizable="true"
		>
		
	</sj:dialog>
<s:form id="formone1" name="formone1" namespace="/view/Over2Cloud/Referral" action="addReferralOnrepeate" theme="simple" method="post" enctype="multipart/form-data"  >
		
		
	
	<s:hidden name="ifrepete" value="%{ifrepete}"></s:hidden>
	
	<s:hidden name="adm_doc" value="%{adm_doc}"></s:hidden>
	
	<s:hidden name="adm_spec" value="%{adm_spec}"></s:hidden>
	<s:hidden name="bed" value="%{bed}"></s:hidden>
	<s:hidden name="caller_emp_id" value="%{caller_emp_id}"></s:hidden>
	<s:hidden name="caller_emp_name" value="%{caller_emp_name}"></s:hidden>
	
	<s:hidden name="nursing_unit" value="%{nursing_unit}"></s:hidden>
	<s:hidden name="patient_name" value="%{patient_name}"></s:hidden>
	<s:hidden name="priority" value="%{priority}"></s:hidden>
	<s:hidden name="reason" value="%{reason}"></s:hidden>
	
	<s:hidden name="ref_by" value="%{ref_by}"></s:hidden>
	<s:hidden name="ref_by_widget" value="%{ref_by_widget}"></s:hidden>
	
	<s:hidden name="ref_to" value="%{ref_to}"></s:hidden>
	<s:hidden name="ref_to_emp_id" value="%{ref_to_emp_id}"></s:hidden>
	<s:hidden name="ref_to_emp_id_widget" value="%{ref_to_emp_id_widget}"></s:hidden>
	
	<s:hidden name="ref_to_sub_spec" value="%{ref_to_sub_spec}"></s:hidden>
	<s:hidden name="ref_to_widget" value="%{ref_to_widget}"></s:hidden>
	
	<s:hidden name="uhid" value="%{uhid}"></s:hidden>
	
	This Referral is already Exist
	Do you still want to add !!
	 	<div class="clear" style="margin-top: 1px;margin-bottom:2px;" ></div>
		<center>
			<div class="type-button">
				<sj:submit  
				             value="Yes" 
                             effect="highlight"
                             effectOptions="{ color : '#222222'}"
                            effectDuration="5000"
                             button="true"
                             onBeforeTopics="level2"
                            targets="result1"
                            
					  />
					  <sj:a
                            
                        button="true" href="#" value="View" onclick="notSave();" 
                        >No
                    </sj:a>

				

			</div>
			
		</center>
 <div id="result1"></div>
	</s:form>
</body>
</html>