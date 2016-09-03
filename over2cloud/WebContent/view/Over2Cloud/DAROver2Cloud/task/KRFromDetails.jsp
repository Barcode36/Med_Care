<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s"  uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<s:url value="/js/krLibrary/tagJs/krLibrary.js"/>"></script>
<title>Insert title here</title>
</head>
<body>

			 			<div class="newColumn">
							<div class="leftColumn1">Department:</div>
							<div class="rightColumn1">
								
						          <s:select 
			                              id="deptName"
			                              list="deptName"
			                              headerKey="-1"
			                              headerValue="Select Department" 
			                              cssClass="select"
			                              cssStyle="width:82%"
			                              onchange="fetchGroup(this.value,'groupNameNew')"
			                              
			                              >
			                  		</s:select>
							</div>
						</div>
              			
           				<div class="newColumn">
           					<div class="leftColumn1">Group:</div>
           					<div class="rightColumn1">
      								   <s:select 
		                              id="groupNameNew"
		                              list="{'No Data'}"
		                              headerKey="-1"
		                              headerValue="Select Group" 
		                              cssClass="select"
		                              cssStyle="width:82%"
		                              onchange="getSubGrp(this.value,'subGroupName');"
		                              
		                              >
		                  		</s:select>
      								 
			</div>
           				</div>
           		
           				<div class="newColumn">
            				<div class="leftColumn1">Sub Group:</div>
						<div class="rightColumn1"> 
					  <s:select 
                            id="subGroupName"
                            list="#{'-1':'Select'}"
                            headerKey="-1"
                            headerValue="Select Sub Group" 
                            cssClass="select"
                            cssStyle="width:82%"
                            onchange="getKrName(this.value,'abc');"
                            
                            >
                		</s:select>
				</div>
		         </div>
              	
              	
           			  <div class="newColumn">
            				<div class="leftColumn1">KR Name:</div>
						<div class="rightColumn1"> 
					  <s:select 
                            id="abc"
                            name="%{dataFor}"
                            list="#{'-1':'Select'}"
                            headerKey="-1"
                            headerValue="Select KR" 
                            cssClass="select"
                            cssStyle="width:82%"
                            
                            >
                		</s:select>
				         </div>
		               </div>
              	
			<div class="clear"></div>
</body>
</html>