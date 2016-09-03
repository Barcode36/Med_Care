<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
123
		<s:url id="viewFeedConfig" action="viewFeedbackConfig" namespace="/view/Over2Cloud/feedback">
		<s:param name="searchType" value="%{#parameters.searchType}"></s:param>
	</s:url>
	<sjg:grid  
		  id="viewFeedbackConfg1"
          href="%{viewFeedConfig}"
          gridModel="feedbackDetail"
          navigator="true"
          navigatorAdd="false"
          navigatorView="false"
          navigatorDelete="false"
          navigatorEdit="false"
          navigatorSearch="false"
          navigatorRefresh="false"
          rowList="10"
          rownumbers="true"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          multiselect="false"
          loadingText="Requesting Data..."  
          rowNum="10"
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','ne','cn','bw','ne','ew']}"
          autowidth="true"
          shrinkToFit="true"
          
          >
          <sjg:gridColumn name="after" index="after" title="After" editable="false" align="center"></sjg:gridColumn>
          <sjg:gridColumn name="time" index="time" title="Time" editable="false" align="center"></sjg:gridColumn>
          <sjg:gridColumn name="keyword" index="keyword" title="Keyword" editable="false" align="center"></sjg:gridColumn>
          <sjg:gridColumn name="length" index="length" title="Length" editable="false" align="center"></sjg:gridColumn>
          <sjg:gridColumn name="type" index="type" title="Type" editable="false" align="center"></sjg:gridColumn>
   	 </sjg:grid>