<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 
								<sj:autocompleter id="pat_state" name="pat_state"  list="stateMap" selectBox="true" selectBoxIcon="true" 
					       			cssClass="textField" theme="simple" onSelectTopics="getSelectState" />
					       			 -->
					       			
					<sj:autocompleter
					 id="pat_city"
					 name="pat_city"
					 list="commondata"
				 	 selectBox="true"
					 selectBoxIcon="true"
					 cssClass="textField"
					 theme="simple"
					/>
</body>
</html>