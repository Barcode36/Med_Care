<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>

<s:url id="gridMappedEmp_URL5" action="gridMappedEmp5">
<s:param name="moduleName" value="%{moduleName}"/>
</s:url>
<s:url id="gridMappedEmp_URL4" action="gridMappedEmp4"/>
<s:url id="gridMappedEmp_URL3" action="gridMappedEmp3"/>
<s:url id="gridMappedEmp_URL2" action="gridMappedEmp2"/>
<s:url id="gridMappedEmp_URL1" action="gridMappedEmp1"/>
<sjg:grid 
		            id="mappedEmpGrid5"
          			href="%{gridMappedEmp_URL5}"
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
          			navigatorEditOptions="{height:250,width:450}"
          			navigatorSearchOptions="{sopt:['eq','cn']}"
          		    navigatorEditOptions="{reloadAfterSubmit:true}"
          		    shrinkToFit="false"
                    >
          
          			<sjg:grid 
		            			id="mappedEmpGrid4"
          						href="%{gridMappedEmp_URL4}"
         						gridModel="masterViewList4"
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
          						navigatorEditOptions="{height:230,width:400}"
          						navigatorSearchOptions="{sopt:['eq','cn']}"
          		    			navigatorEditOptions="{reloadAfterSubmit:true}"
          		    			shrinkToFit="false"
                    			>
        						<sjg:grid 
		            			id="mappedEmpGrid3"
          						href="%{gridMappedEmp_URL3}"
         						gridModel="masterViewList3"
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
          						navigatorEditOptions="{height:230,width:400}"
          						navigatorSearchOptions="{sopt:['eq','cn']}"
          		    			navigatorEditOptions="{reloadAfterSubmit:true}"
          		    			shrinkToFit="false"
                    			>
        
        
        						<sjg:grid 
		            			id="mappedEmpGrid2"
          						href="%{gridMappedEmp_URL2}"
         						gridModel="masterViewList2"
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
          						navigatorEditOptions="{height:230,width:400}"
          						navigatorSearchOptions="{sopt:['eq','cn']}"
          		    			navigatorEditOptions="{reloadAfterSubmit:true}"
          		    			shrinkToFit="false"
                    			>
        
        						<sjg:grid 
		            			id="mappedEmpGrid1"
          						href="%{gridMappedEmp_URL1}"
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
          						navigatorEditOptions="{height:230,width:400}"
          						navigatorSearchOptions="{sopt:['eq','cn']}"
          		    			navigatorEditOptions="{reloadAfterSubmit:true}"
          		    			shrinkToFit="false"
                    			>
        
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
										name="gridEmpName1"
										index="gridEmpName1"
										title="Mapped Person"
									    width="290"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
									/>
          
		          					<sjg:gridColumn 
										name="gridLevel1"
										index="gridLevel1"
										title="Level"
									    width="150"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
										/>
										
									<sjg:gridColumn 
										name="gridgroupName1"
										index="gridgroupName1"
										title="Contact Type"
									    width="150"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
										/>
										
									<sjg:gridColumn 
										name="gridEmpDesignation1"
										index="gridEmpDesignation1"
										title="Designation"
									    width="320"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
									/>
								
									<sjg:gridColumn 
										name="gridEmpMob1"
										index="gridEmpMob1"
										title="Mobile No"
									    width="220"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
									/>
									
									<sjg:gridColumn 
										name="gridEmpEmail1"
										index="gridEmpEmail1"
										title="Email Id"
									    width="320"
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
										name="gridEmpName2"
										index="gridEmpName2"
										title="Mapped Person"
									    width="290"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
									/>
								
								<sjg:gridColumn 
										name="gridLevel2"
										index="gridLevel2"
										title="Level"
									    width="150"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
										/>
								
								<sjg:gridColumn 
										name="gridgroupName2"
										index="gridgroupName2"
										title="Contact Type"
									    width="150"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
										/>
								
										
								<sjg:gridColumn 
										name="gridEmpDesignation2"
										index="gridEmpDesignation2"
										title="Designation"
									    width="320"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
									/>	
									<sjg:gridColumn 
										name="gridEmpMob2"
										index="gridEmpMob2"
										title="Mobile No"
									    width="220"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
									/>
									
									<sjg:gridColumn 
										name="gridEmpEmail2"
										index="gridEmpEmail2"
										title="Email Id"
									    width="320"
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
										name="gridEmpName3"
										index="gridEmpName3"
										title="Mapped Person"
									    width="290"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
									/>
									
		          					<sjg:gridColumn 
										name="gridLevel3"
										index="gridLevel3"
										title="Level"
									    width="150"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
										/>
								
									<sjg:gridColumn 
										name="gridgroupName3"
										index="gridgroupName3"
										title="Contact Type"
									    width="150"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
										/>
								
								    <sjg:gridColumn 
										name="gridEmpDesignation3"
										index="gridEmpDesignation3"
										title="Designation"
									    width="320"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
									/>
									
									<sjg:gridColumn 
										name="gridEmpMob3"
										index="gridEmpMob3"
										title="Mobile No"
									    width="220"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
									/>
									
									<sjg:gridColumn 
										name="gridEmpEmail3"
										index="gridEmpEmail3"
										title="Email Id"
									    width="350"
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
										name="gridEmpName4"
										index="gridEmpName4"
										title="Mapped Person"
									    width="300"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
									/>
									
		          					<sjg:gridColumn 
										name="gridLevel4"
										index="gridLevel4"
										title="Level"
									    width="150"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
										/>
								
									<sjg:gridColumn 
										name="gridgroupName4"
										index="gridgroupName4"
										title="Contact Type"
									    width="150"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
										/>
								
									<sjg:gridColumn 
										name="gridEmpDesignation4"
										index="gridEmpDesignation4"
										title="Designation"
									    width="320"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
									/>
								
									<sjg:gridColumn 
										name="gridEmpMob4"
										index="gridEmpMob4"
										title="Mobile No"
									    width="254"
										align="center"
										editable="false"
		    							search="true"
		    							hidden="false"
									/>
									
									<sjg:gridColumn 
										name="gridEmpEmail4"
										index="gridEmpEmail4"
										title="Email Id"
									    width="400"
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
								name="gridEmpName5"
								index="gridEmpName5"
								title="Mapped Person"
							    width="330"
								align="center"
								editable="false"
    							search="true"
    							hidden="false"
								/>
								
          					<sjg:gridColumn 
								name="gridLevel5"
								index="gridLevel5"
								title="Level"
							    width="150"
								align="center"
								editable="false"
    							search="true"
    							hidden="false"
								/>
								
							<sjg:gridColumn 
								name="gridgroupName5"
								index="gridgroupName5"
								title="Contact Type"
							    width="150"
								align="center"
								editable="false"
    							search="true"
    							hidden="false"
								/>
								
								<sjg:gridColumn 
									name="gridEmpDesignation5"
									index="gridEmpDesignation5"
									title="Designation"
								    width="320"
									align="center"
									editable="false"
	    							search="true"
	    							hidden="false"
								/>
								
								<sjg:gridColumn 
								name="gridEmpMob5"
								index="gridEmpMob5"
								title="Mobile No"
							    width="358"
								align="center"
								editable="false"
    							search="true"
    							hidden="false"
								/>
								
								<sjg:gridColumn 
								name="gridEmpEmail5"
								index="gridEmpEmail5"
								title="Email Id"
							    width="360"
								align="center"
								editable="false"
    							search="true"
    							hidden="false"
								/>
								
 					</sjg:grid> 
</body>
</html>