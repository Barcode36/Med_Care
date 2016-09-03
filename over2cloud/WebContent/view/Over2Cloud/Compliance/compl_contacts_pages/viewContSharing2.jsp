<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<s:url id="levelMappedEmp_URL" action="levelMappedEmp">
<s:param name="moduleName" value="%{moduleName}"/>
</s:url>
<s:url id="contShareEmp_URL" action="contShareEmp">
<s:param name="moduleName" value="%{moduleName}"/>
</s:url>
<s:url id="editContLevel_URL" action="editContactLevel"></s:url>
<sjg:grid 
		            id="levelMappedEmpGid"
          			href="%{levelMappedEmp_URL}"
         			gridModel="masterViewList"
          			navigator="true"
          			navigatorAdd="false"
          			navigatorView="false"
          			navigatorDelete="false"
          			navigatorEdit="false"
          			navigatorSearch="false"
          			rowList="100,200,500"
          			rownumbers="-1"
          			viewrecords="true"       
          			pager="true"
          			pagerButtons="true"
          			pagerInput="true"   
          			multiselect="false"  
          			loadonce="%{loadonce}"
          			loadingText="Requesting Data..."  
          			rowNum="100"
          			autowidth="true"
          			editurl="%{editLevelMappedEmp_URL}"
          			navigatorEditOptions="{height:250,width:450}"
          			navigatorSearchOptions="{sopt:['eq','cn']}"
          		    navigatorEditOptions="{reloadAfterSubmit:true}"
          		    shrinkToFit="false"
                    >
          
          			<sjg:grid 
		            			id="contShareEmpGid"
          						href="%{contShareEmp_URL}"
         						gridModel="masterViewList1"
          						navigator="true"
          						navigatorAdd="false"
          						navigatorView="false"
          						navigatorDelete="false"
          						navigatorSearch="false"
          						navigatorEdit="false"
          					    rowList="10,25,50"
          						rownumbers="-1"
          						viewrecords="true"       
          						pager="true"
          						pagerButtons="true"
          						pagerInput="true"   
          						multiselect="false"  
          						loadonce="%{loadonce}"
          						loadingText="Requesting Data..."  
          						rowNum="10"
          						autowidth="true"
          						editurl="%{editContShareEmp_URL}"
          						navigatorEditOptions="{height:230,width:400}"
          						navigatorSearchOptions="{sopt:['eq','cn']}"
          		    			navigatorEditOptions="{reloadAfterSubmit:true}"
          		    			shrinkToFit="false"
                    			>
        
        						
        
					   			<sjg:gridColumn 
								name="shareWithEmpName"
								index="shareWithEmpName"
								title="Share With"
							    width="433"
								align="center"
								editable="false"
								search="true"
								hidden="false"
								/> 
								<sjg:gridColumn 
								name="shareEmpLevel"
								index="shareEmpLevel"
								title="Level"
							    width="200"
								align="center"
								editable="false"
								search="true"
								hidden="false"
								/> 
								
								<sjg:gridColumn 
								name="shareGroupName"
								index="shareGroupName"
								title="Contact Type"
							    width="348"
								align="center"
								editable="false"
    							search="true"
    							hidden="false"
								/>
								
								<sjg:gridColumn 
								name="shareWithDesignation"
								index="shareWithDesignation"
								title="Designation"
							    width="300"
								align="center"
								editable="false"
    							search="true"
    							hidden="false"
								/>
								
								<sjg:gridColumn 
								name="shareWithEmpMob"
								index="shareWithEmpMob"
								title="Mobile No"
							    width="200"
								align="center"
								editable="false"
    							search="true"
    							hidden="false"
								/>
								
								<sjg:gridColumn 
								name="shareWithEmpEmail"
								index="shareWithEmpEmail"
								title="Email Id"
							    width="300"
								align="center"
								editable="false"
    							search="true"
    							hidden="false"
								/>
								
 							</sjg:grid>  
          
          					<sjg:gridColumn 
								name="id"
								index="id"
								title="id"
							    width="100"
								align="center"
								editable="false"
    							search="true"
    							hidden="true"
								/>
          
			 					<sjg:gridColumn 
								name="mappedEmpName"
								index="mappedEmpName"
								title="Name"
							    width="350"
								align="center"
								editable="false"
    							search="true"
    							hidden="false"
								/>
								
								<sjg:gridColumn 
								name="level"
								index="level"
								title="Level"
							    width="150"
								align="center"
								editable="false"
    							search="true"
    							hidden="false"
								/>
								
								<sjg:gridColumn 
								name="mappedGroupName"
								index="mappedGroupName"
								title="Contact Type"
							    width="348"
								align="center"
								editable="false"
    							search="true"
    							hidden="false"
								/>
								
								<sjg:gridColumn 
								name="mappedDesignation"
								index="mappedDesignation"
								title="Designation"
							    width="348"
								align="center"
								editable="false"
    							search="true"
    							hidden="false"
								/>
								
								<sjg:gridColumn 
								name="mappedEmpMob"
								index="mappedEmpMob"
								title="Mobile No"
							    width="350"
								align="center"
								editable="false"
    							search="true"
    							hidden="false"
								/>
								
								<sjg:gridColumn 
								name="mappedEmpEmail"
								index="mappedEmpEmail"
								title="Email Id"
							    width="348"
								align="center"
								editable="false"
    							search="true"
    							hidden="false"
								/>
								
								
 					</sjg:grid> 

</body>
</html>