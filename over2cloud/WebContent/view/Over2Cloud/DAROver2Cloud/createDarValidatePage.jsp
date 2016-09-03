<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/task_js/task.js"/>"></script>
<script type="text/javascript">
$.subscribe('completeData', function(event,data)
{
			 setTimeout(function(){ $("#darlevel22").fadeIn(); }, 10);
	         setTimeout(function(){ $("#darlevel22").fadeOut(); }, 4000);
	         $('select').find('option:first').attr('selected','selected');
});

</script>
</head>
<body>
<div class="list-icon">
	 <div class="head">
	Project</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div>
	 <div class="head">Review</div> 
</div>
<div style=" float:left; padding:5px 0%; width:100%;">
<div class="border">
<s:form id="formvalidation" name="formvalidation" action="addDarFormValidation" theme="css_xhtml"  method="post" enctype="multipart/form-data" >
		    <div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
	             <div id="errZone" style="float:left; margin-left: 7px">
	             </div> 
            </div>
		       <div class="newColumn">
								<div class="leftColumn1">Review Type:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:select 
	                              id="%{key}"
	                              name="%{key}"
	                              list="{'Technical','Functional'}"
	                              headerKey="-1"
	                              headerValue="--Select Review Type--" 
	                              cssClass="select"
	                              cssStyle="width:82%"
	                              onchange="test();"
	                              >
                              </s:select>
				</div>
				</div>

				<div class="newColumn">
								<div class="leftColumn1">Alloted To:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:select 
                              id="%{key}"
                              name="%{key}"
                              list="listTaskNameFovalidation"
                              headerKey="-1"
                              headerValue="--Select Alloted To--" 
                              cssClass="select"
                              cssStyle="width:82%"
                              onchange="getProjectNames(this.value,'taskname')"
                              >
                             </s:select>
				</div>
				</div>

		     <s:iterator value="darDropMap" begin="0" end="0">
		     <s:if test="%{mandatory}">
               <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>

	                  <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:select  
                               id="%{key}"
                               name="%{key}"
                               list="{'No Data'}"
                               headerKey="-1"
                               headerValue="--Select Project Name--" 
                               cssClass="select"
                               cssStyle="width:82%"
                               onchange="getClientData(this.value,'1','0')"
                              >
                              </s:select>
				</div>
				</div>
			</s:iterator>
<s:hidden  id="attachment" /> 

<div id="downloacLoc"></div>
	 		<s:iterator value="darDropMap" status="counter" begin="1">
		      <s:if test="%{mandatory}">
               <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>

               <s:if test="#counter.count%2 != 0">
               <s:if test="%{key=='attachment'}">
                <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:a   onclick="beforeDownload('attachment');"><B><img src="images/attachPic2.png"></B></s:a></div>
               </div>
               </s:if>
               <s:else>
                <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:textfield readonly="true"  id="%{key}" maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
               </div>
               </s:else>
               </s:if>
               <s:else>
                <s:if test="%{key=='attachment'}">
                <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <sj:a onclick="beforeDownload('attachment');"><B><img src="images/attachPic2.png"></B></sj:a></div>
               </div>
               </s:if>
               <s:else>
                <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:textfield readonly="true"  id="%{key}" maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
               </div>
               </s:else>
               </s:else>

            </s:iterator>
            
          <s:hidden id="dateVal" value="%{currentDate}" />
           <div style="width: 100%;height: 15px;float: left;background-color: #e9e9e9;border-bottom: 1px solid;border-color: #D6D6D6;">
             <div style="margin-left: 40%; float: left;">
				<div style="float: left;">
					<img alt="Previous" onclick="previousNextDayData('taskname','1','backward');" style="cursor: pointer;" src="images/backward.png" title="Previous">
					&nbsp;&nbsp;&nbsp;
				</div>
				<div style="float: left;">	
					 <b>Activity Status As On</b> 
					  <b>
					 <div id="dateDiv" style="margin-left: 125px;margin-top: -14px;">
					 		<b><s:property value="%{currentDate}"/></b>
					 </div>
					 </b>
				</div>
				<div id="activityDate" style="color: white; float: left;"></div>
				<div style="float: left;">
					&nbsp;&nbsp;&nbsp;<img alt="Next" onclick="previousNextDayData('taskname','1','forward');" style="cursor: pointer;" src="images/forward.png" title="Next">
				</div>
			</div>
		  </div>
     <div class="clear"></div>           

			<s:iterator value="darColumnMap" status="counter">
			 <s:if test="%{mandatory}">
               <span id="mandatoryFields" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             </s:if>
                   <s:if test="#counter.count%2 != 0">
                   <s:if test="%{key=='wStatus'}">
                    <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:select 
                              id="%{key}"
                              name="%{key}"
                              list="{'Pending','Partially Pending','Done'}"
                              headerKey="-1"
                              headerValue="--Select Work Status--" 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                             </s:select>
				    </div>
				    </div>
                   </s:if>

                   <s:elseif test="%{key=='rating'}">
                    <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:select 
                              id="%{key}"
                              name="%{key}"
                              list="{'1','2','3','4','5'}"
                              headerKey="-1"
                              headerValue="--Select Rating--" 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                             </s:select>
				    </div>
				    </div>
                   </s:elseif>

                   <s:elseif test="%{key=='reviewDoc'}">
                    <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:file name="%{key}" id="%{key}" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"  /></div>
		            </div>
                   </s:elseif>
                    <s:elseif test="%{key=='reviewComments'}">
                    <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:textfield name="%{key}" id="%{key}" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" placeholder="Enter Data" /></div>
		            </div>
                   </s:elseif>

                   <s:else>
                    <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:textfield readonly="true"  id="%{key}" maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
                    </div>
                   </s:else>
               </s:if>
               <s:else>

                 <s:if test="%{key=='wStatus'}">
                    <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:select 
                              id="%{key}"
                              name="%{key}"
                              list="{'Pending','Partially Pending','Done'}"
                              headerKey="-1"
                              headerValue="--Select Work Status--" 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                             </s:select>
				    </div>
				    </div>
                   </s:if>

                   <s:elseif test="%{key=='rating'}">
                    <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:select 
                              id="%{key}"
                              name="%{key}"
                              list="{'1','2','3','4','5'}"
                              headerKey="-1"
                              headerValue="--Select Rating--" 
                              cssClass="select"
                              cssStyle="width:82%"
                              >
                             </s:select>
				    </div>
				    </div>
                   </s:elseif>

                   <s:elseif test="%{key=='reviewDoc'}">
                    <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:file name="%{key}" id="%{key}" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"  /></div>
		            </div>
                   </s:elseif>
                   
					<s:elseif test="%{key=='reviewComments'}">
                    <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                                <s:textfield name="%{key}" id="%{key}" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" placeholder="Enter Data" /></div>
		            </div>
                   </s:elseif>
                   <s:else>
                    <div class="newColumn">
								<div class="leftColumn1"><s:property value="%{value}"/>:</div>
								<div class="rightColumn1">
	                            <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                            <s:textfield readonly="true"  id="%{key}" maxlength="50" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField"/></div>
                    </div>
                   </s:else>
               </s:else>
            </s:iterator>
		   <div class="clear"></div>

		  <center><img id="darvalidate" src="<s:url value="/images/indicator.gif"/>" alt="Loading..."  style="display: none;"/></center>
		  <center>
		   <div class="fields" align="center">
		    <ul>
			 <li class="submit" style="background:none;">
			 <div class="type-button">
			 <center>
	         <sj:submit 
	                        targets="darlevel22Div" 
	                        id="buttonadd"
	                        clearForm="true"
	                        value="Save" 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        cssClass="submit"
	                        onCompleteTopics="completeData"
	                        indicator="indicatorRuc"
	                        cssStyle="margin-left: -21px;"
	                        />
			        <sj:a cssStyle="margin-left: 183px;margin-top: -45px;" button="true" href="#" onclick="resetForm('formtask');">Reset</sj:a>
				            <sj:a cssStyle="margin-left: -1px;margin-top: -45px;" button="true" href="#" onclick="viewDar();">Back</sj:a>
	           </center>                 
	        </div>
	        </li>
		  </ul>
		  <sj:div id="darlevel22"  effect="fold">
                    <div id="darlevel22Div"></div>
          </sj:div>
	      </div>
	      </center>
	 </s:form>
</div>
</div>
</body>
</html>
