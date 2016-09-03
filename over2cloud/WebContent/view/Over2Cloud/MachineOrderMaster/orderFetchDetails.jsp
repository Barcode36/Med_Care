<%@taglib uri="/struts-tags"  prefix="s" %>

<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<SCRIPT type="text/javascript">
	

	function fetchOrd66()
	{
		$.ajax({
		    type : "post",
		    url  : "view/Over2Cloud/CorporatePatientServices/Lodge_Feedback/orderFetchDR.action",
		    success : function(data) {
	  	$(data).each(function(index)
			{
			});
			
		},
		   error: function() {
	          alert("error");
	      }
		 });
	}

	function fetchOrd88()
	{
		$.ajax({
		    type : "post",
		    url  : "view/Over2Cloud/CorporatePatientServices/Lodge_Feedback/orderFetchHIS.action",
		    success : function(data) {
	  	$(data).each(function(index)
			{
			});
			
		},
		   error: function() {
	          alert("error");
	      }
		 });
	}
	</script>
<h4> <b> Date Time of last order fetch from HIS: </b><s:property value="%{lastOrderDateTime}"/></h4>

<s:if test="forceFetch==1">
<center>
	<div class="type-button">
	

	
	<sj:submit value="Force Fetch Order- 1" button="true"
	cssStyle="margin-left: -2px;margin-top: 81px;height:72px;width:14%"
	
	onclick="fetchOrd66();" />

	</div>
	
	<sj:submit value="Force Fetch Order- 2" button="true"
	cssStyle="margin-left: -2px;margin-top: 31px;height:72px;width:14%"
	
	onclick="fetchOrd88();" />

	</div>

	</center>

</s:if>