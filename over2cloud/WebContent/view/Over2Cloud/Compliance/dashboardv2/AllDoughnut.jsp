<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>


   
</head>
<body>
 <sj:div events="mousedown" effect="shake" effectDuration="300"   cssClass="result ui-widget-content ui-corner-all" cssStyle="padding:2px 2px 2px 2px">
       
   
<sjc:chart
        id="%{graphId}"
        cssStyle="width: 390px; height: 200px;"
        pie="true"
        pieLabel="true"
        pieInnerRadius="0.3"
    	pieLabelRadius="0.6"
    	pieLabelBackgroundColor="#FFFFFF"
    	pieLabelBackgroundOpacity="0.8"
        onClickTopics="getChatInfo"
        onHoverTopics="hover"
        legendShow="true"
       
        
    >
    <s:if test="divName=='1stDonut'">
    <s:iterator value="%{totalComplStatusMap}">
	    <s:if test="key=='Pending'">
		    <sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="#FF0033"
		    		/>	
		    		
		 </s:if>
		 <s:elseif test="key=='Done'">
		 	<sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="#009933"/>
		 </s:elseif>
		 <s:elseif test="key=='Missed'">
		 	<sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="#C87900"/>
		 </s:elseif>
		 <s:elseif test="key=='Snooze'">
		 	<sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="#A0A0A0"/>
		 </s:elseif>
    	</s:iterator>
    	</s:if>
    	
    	
    	  <s:elseif test="divName=='2ndDonut'">
    	<s:iterator value="%{mgmtCompDetail}">
	    <s:if test="key=='Yearly Total'">
		    <sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="green"
		    		/>	
		 </s:if>
		 <s:elseif test="key=='Yearly Missed'">
		 	<sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="red"/>
		 </s:elseif>
		 <s:elseif test="key=='Monthly Total'">
		 	<sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="#FF8000"/>
		 </s:elseif>
		 <s:elseif test="key=='Monthly Missed'">
		 	<sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="#084B8A"/>
		 </s:elseif>
		 <s:elseif test="key=='Weekly Total'">
		 	<sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="#F08085"/>
		 </s:elseif>
		 <s:elseif test="key=='Weekly Missed'">
		 	<sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="#8A0886"/>
		 </s:elseif>
    	</s:iterator>
    </s:elseif>
  
    <s:elseif test="divName=='3rdDonut'">
    	<s:iterator value="%{ageingCompDetail}">
	    <s:if test="key=='This Year'">
		    <sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="orange"
		    		/>	
		 </s:if>
		 <s:elseif test="key=='This Month'">
		 	<sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="#9FF781"/>
		 </s:elseif>
		 <s:elseif test="key=='This Week'">
		 	<sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="brown"/>
		 </s:elseif>
    	</s:iterator>
    </s:elseif>
      
    <s:elseif test="divName=='4thDonut'">
    	<s:iterator value="%{previousMonthUserCompDetail}">
	    <s:if test="key=='Pending'">
		    <sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="#FF0033"
		    		/>	
		    		
		 </s:if>
		 <s:elseif test="key=='Done'">
		 	<sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="#009933"/>
		 </s:elseif>
    	</s:iterator>
    </s:elseif>
      
    <s:elseif test="divName=='7thDoughnut'">
    	<s:iterator value="%{escLevelCompDetail}">
	    <s:if test="key=='Level 1'">
		    <sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="orange"
		    		/>	
		    		
		 </s:if>
		 <s:elseif test="key=='Level 2'">
		 	<sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="#9FF781"/>
		 </s:elseif>
		 <s:elseif test="key=='Level 3'">
		 	<sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="brown"/>
		 </s:elseif>
		 <s:elseif test="key=='Level 4'">
		 	<sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="brown"/>
		 </s:elseif>
		 <s:elseif test="key=='Level 5'">
		 	<sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		color="brown"/>
		 </s:elseif>
    	</s:iterator>
    </s:elseif>
    
     <s:elseif test="divName=='7thDoughnut'">
	     <s:iterator value="%{todayCompDetail}">
		    <sjc:chartData
		    		label="%{key}"
		    		data="%{value}"
		    		/>	
			    		
	    </s:iterator>
    </s:elseif>
    	
    </sjc:chart>
     </sj:div>
     </body>
</html>