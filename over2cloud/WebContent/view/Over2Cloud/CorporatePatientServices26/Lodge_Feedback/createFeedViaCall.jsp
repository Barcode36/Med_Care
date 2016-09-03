<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/feedback.js"/>"></script>
<script type="text/javascript">
function getOnlinePage()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/beforeFeedViaOnline.action?feedStatus=online&dataFor=HDM",
	    success : function(feeddraft) {
       $("#"+"data_part").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}
function viewActivityBoard()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Activity_Board/viewActivityBoardHeader.action",
	    success : function(feeddraft) {
       $("#"+"data_part").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}
</script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Ticket Lodge</div><img alt="" src="images/forward.png" style="margin-top:10px; float: left;"><div class="head"> Call</div><sj:a button="true" cssClass="button" buttonIcon="ui-icon-plus"  style="margin-top:4px; float: right;"  onclick="getOnlinePage()">Online Mode</sj:a> 
	<sj:a cssStyle="margin-top:4px;float:right;" button="true" href="#" onclick="viewActivityBoard();">Activity Board</sj:a>
	  
</div>
<div class="clear"></div>
<div style="overflow:auto; height:450px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">

<s:form id="formone" name="formone" action="/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/confirmEscalation.jsp" theme="css_xhtml"  method="post" enctype="multipart/form-data" >
<s:hidden name="viaFrom" value="call"></s:hidden>
<s:hidden name="dataFor" value="HDM"></s:hidden>
<s:hidden name="pageFor"  value="SD"></s:hidden>
<s:hidden id="escalationTime"  name="escalationTime"></s:hidden>
<s:hidden id="bydept_subdept"  name="bydept_subdept" value="NA"></s:hidden>

<center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone" style="float:center; margin-left: 7px"></div></div></center>  
<!--  <div class="secHead"  >Search By  </div> -->
  <table align="center" border="1" width="70%" style="border-collapse: collapse;">
	   
				<tr bgcolor="#D8D8D8" style="height: 25px">
				  		 	<td width="10%" align="center"><b>Mobile No.:<b></td>
							<td width="10%" ><s:textfield name="mobile" maxlength="10" id="mobile" onblur="getDetail('mobile','mobile')"   cssClass="textField" placeholder="Enter Data" /></td>
					 	<td width="10%" align="center"><b>Unique ID:<b></td>
							<td width="10%"><s:textfield name="unique"  id="unique" onblur="getDetail('unique','uids')"  cssClass="textField" placeholder="Enter Data" /></td>
					 	<td width="10%" align="center"><b>Department:<b></td>
							<td width="12%"> <s:select 
				                              id="dept"
				                              name="bydept"
				                              list="fromDept"
				                              headerKey="-1"
				                              headerValue="Select Department" 
				                              cssClass="select"
					                          cssStyle="width:100%"
					                          onchange="getUserByDept(this.value,'user','Select User');"
					                          
				                   
				                              >
				                  </s:select></td>
					 	<td width="10%" align="center"><b>User:<b></td>
							<td width="10%" bgcolor="white"> <s:select 
				                              id="user"
				                              name="user"
				                              list="{'No Data'}"
				                              headerKey="-1"
				                              headerValue="Select User" 
				                              cssClass="select"
					                          cssStyle="width:100%"
				                              onchange="getDetail('user','ids')"	>
				                  </s:select></td>
					 	 </tr>
	</table>
	 
	<sj:a button="true"  style="margin-top:-30px; float: right;" href="#" onclick="resetForm('formone');">Reset</sj:a>
 
<div class="secHead">From</div>
	 
	<s:iterator value="pageFieldsColumns" begin="3" end="3" status="status">
	
	      <div class="newColumn">
                <div class="leftColumn">Name:&nbsp;</div>
				<div class="rightColumn">
			 	    <s:textfield name="%{key}"  id="%{key}" readonly="true" cssClass="textField" placeholder="Enter Data" />
				</div>
		 </div>
	 
	 </s:iterator>
	 
	 
	   <s:iterator value="pageFieldsColumns" begin="0" end="0" status="status">
       
     <div class="newColumn">
			 <div class="leftColumn">Department:&nbsp;</div>
           		<div class="rightColumn">
                      <s:textfield name="deptname"  id="deptname"  readonly="true" cssClass="textField" placeholder="Enter Data" />
                </div>
      </div>
    
     </s:iterator>
	 
	 <s:iterator value="pageFieldsColumns" begin="4" end="4" status="status">
	
	      <div class="newColumn">
		  <div class="leftColumn">Mobile No.:&nbsp;</div>
	            <div class="rightColumn">
	                		     <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                 <s:textfield name="%{key}" readonly="true"  id="%{key}" onblur="getDetail('%{key}')" maxlength="10" cssClass="textField" placeholder="Enter Data" />
	             </div>
      </div>
	 </s:iterator>
	 <s:iterator value="pageFieldsColumns" begin="5" end="5" status="status">
	
	      <div class="newColumn">
                <div class="leftColumn">Email ID:&nbsp;</div>
				<div class="rightColumn">
				<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				    <s:textfield name="%{key}"  id="%{key}" readonly="true" cssClass="textField" placeholder="Enter Data" />
				</div>
		 </div>
	 
	 </s:iterator>
	 
	  <s:iterator value="pageFieldsColumns" begin="2" end="2" status="status">
	
	      <div class="newColumn">
		  <div class="leftColumn">Unique ID:&nbsp;</div>
	            <div class="rightColumn">
	                		     <s:if test="%{mandatory}"><span class="needed"></span></s:if>
		                 <s:textfield name="uniqueid" id="uniqueid" readonly="true"  maxlength="10" cssClass="textField" placeholder="Enter Data" />
	             </div>
      </div>
	 </s:iterator>
	 
	 
	 <s:iterator value="pageFieldsColumns" begin="2" end="5" status="status">
	  <s:if test="%{mandatory}">
			     <span id="normalSubdept" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
			     <span id="ddSubdept" class="ddPids" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
      </s:if>
	 
	    
	 </s:iterator>
	 
  
	<div class="newColumn">
	     <div class="leftColumn">Self Acknowledge:</div>
	     	<div class="rightColumn" >
	     		<div style="width: auto; float: left; text-align: left; line-height:25px; margin-right: 10px;">SMS:</div><div style="width: auto; float: left; text-align: left;  line-height:25px; margin-right: 10px; vertical-align: middle; padding-top: 5px;"><s:checkbox name="recvSMS" id="recvSMS"></s:checkbox></div>
	     		<div style="width: auto; float: left; text-align: left; line-height:25px; margin-right: 10px; margin-left: 20px;">Email:</div><div style="width: auto; float: left; text-align: left;  line-height:25px; margin-right: 10px; vertical-align: middle; padding-top: 5px;"><s:checkbox name="recvEmail" id="recvEmail"></s:checkbox></div>
	     	</div>
    </div>

<div class="clear"></div>
<div class="secHead">To</div>

 <s:iterator value="pageFieldsColumns" begin="0" end="1" status="status">
	  <s:if test="%{mandatory}">
			     <span id="normalSubdept" class="pIds" style="display: none; "><s:property value="%{key}"/>3#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
			     <span id="ddSubdept" class="ddPids" style="display: none; "><s:property value="%{key}"/>3#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
      </s:if>
	         <s:if test="#status.odd">
                   <div class="newColumn">
        			   <div class="leftColumn" >To Department:&nbsp;</div>
					        <div class="rightColumn">
					             <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                  <s:select  id="%{key}3"
				                              name="todept"
				                              list="serviceDeptList"
				                              headerKey="-1"
				                              headerValue="Select Department" 
				                              cssClass="select"
					                          cssStyle="width:82%"
				                              onchange="getServiceDept('subdepartment','id','subdeptname','deptid',this.value,'moduleName','HDM','subdeptname','ASC','subdeptname3','Select Sub-Department');Reset('.pIds');"
				                              >
				                  </s:select>
                           </div>
                   </div>
	          </s:if>
              <s:else>
                     <div class="newColumn">
					      <div class="leftColumn"><s:property value="%{value}"/>:&nbsp;</div>
							<div class="rightColumn">
				             	  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                  <s:select 
				                              id="%{key}3"
                              				  name="tosubdept"
				                              list="{'No Data'}"
				                              headerKey="-1"
				                              headerValue="Select Sub-Department" 
				                              cssClass="select"
				                              cssStyle="width:82%"
				                              onchange="commonSelectAjaxCall2('feedback_type','id','fbType','dept_subdept',this.value,'moduleName','HDM','fbType','ASC','feedType','Select Feedback Type');Reset('.pIds');">
				                  </s:select>
				           </div>
	                  </div>
                 </s:else>
     </s:iterator>
     <s:hidden name="tosubdept_extra" id="tosdId"></s:hidden>
     
     <s:iterator value="pageFieldsColumns" begin="6" end="7" status="status">
	  <s:if test="%{mandatory}">
             	<s:if test="key=='feedType'">
             		<span id="normalSubdept" class="pIds" style="display: none; ">feedType#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             		<span id="ddSubdept" class="ddPids" style="display: none; ">feedType#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>	
             	</s:if>
             	<s:elseif test="key=='category'">
             		<span id="normalSubdept" class="pIds" style="display: none; ">category#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             		<span id="ddSubdept" class="ddPids" style="display: none; ">category#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             	</s:elseif>
             	<s:else>
             		<span id="normalSubdept" class="pIds" style="display: none; "><s:property value="%{key}"/>3#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             		<span id="ddSubdept" class="ddPids" style="display: none; "><s:property value="%{key}"/>3#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             	</s:else>
      </s:if>
	      <s:if test="#status.odd">
	      <div class="newColumn">
	    	<div class="leftColumn" ><s:property value="%{value}"/>:&nbsp;</div>
		        <div class="rightColumn">
		             <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                  <s:select  
	                  			  id="%{key}"
	                              name="feedbackType"
	                              list="{'No Data'}"
	                              headerKey="-1"
	                              headerValue="Select Feedback Type" 
	                              cssClass="select"
		                          cssStyle="width:82%"
	                              onchange="commonSelectAjaxCall2('feedback_category','id','categoryName','fbType',this.value,'hide_show','Active','categoryName','ASC','category','Select Category');Reset('.pIds');"
	                              >
	                  </s:select>
	             </div>
	         </div>
	       </s:if>
	       <s:else>
	          <div class="newColumn">
		      <div class="leftColumn"><s:property value="%{value}"/>:&nbsp;</div>
				<div class="rightColumn">
	             	  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
	                  <s:select 
	                              id="category"
	                              name="category" 
	                              list="{'No Data'}" 
	                              headerKey="-1"
	                              headerValue="Select Category" 
	                              cssClass="select"
	                              cssStyle="width:82%"
	                              onchange="commonSelectAjaxCall2('feedback_subcategory','id','subCategoryName','categoryName',this.value,'hide_show','Active','subCategoryName','ASC','subCatg','Select Sub-Category');Reset('.pIds');">
	                  </s:select>
	             </div>
	            </div>
	      </s:else>
     </s:iterator>
     <s:hidden name="feedType" id="feedId"></s:hidden>
     <s:hidden name="subCategory_extra" id="scatgid"></s:hidden>
     
     <s:iterator value="pageFieldsColumns" begin="8" end="9" status="status">
	  <s:if test="%{mandatory}">
	   		 <span id="normalSubdept" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
       		 <span id="ddSubdept" class="ddPids" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
       </s:if>
      <s:if test="#status.odd">
      <div class="newColumn">
    	<div class="leftColumn" ><s:property value="%{value}"/>:&nbsp;</div>
	        <div class="rightColumn">
	             <s:if test="%{mandatory}"><span class="needed"></span></s:if>
                  <s:select  
                  			  id="%{key}"
                              name="subCategory"
                              list="{'No Data'}"
                              headerKey="-1"
                              headerValue="Select Sub Category" 
                              cssClass="select"
	                          cssStyle="width:82%"
	                          onchange="getFeedBreifViaAjax(this.value);Reset('.pIds');"
                              >
                  </s:select>
             </div>
         </div>
       </s:if>
       <s:else>
          <div class="newColumn">
	      <div class="leftColumn"><s:property value="%{value}"/>:&nbsp;</div>
			<div class="rightColumn">
			 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
             	  <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" onchange="Reset('.pIds');"/>
             </div>
            </div>
      </s:else>
     </s:iterator>
     
      <s:iterator value="pageFieldsColumns" begin="11" end="11">
	  <s:if test="%{mandatory}">
       		 <span id="ddSubdept" class="ddPids" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
       </s:if>
      
        <!--    <div class="newColumn">
	      <div class="leftColumn"><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
			<div class="rightColumn">
             	  <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" />
             </div>
            </div>   -->
     </s:iterator>
     
     
     <s:div>
     <span id="normalSubdept" class="pIds" style="display: none; ">floorid#Floor#D#,</span>
     <span id="ddSubdept" class="ddPids" style="display: none; ">floorid#Floor#D#,</span>
	 <div class="newColumn">
         <div class="leftColumn">Floor:&nbsp;</div>
			<div class="rightColumn">
				<span class="needed"></span>
                  <s:select 
                              id="floorid"
                              name="floor"
                              list="floorInfo"
                              headerKey="-1"
                              headerValue="Select Floor"
                              cssClass="select"
                              cssStyle="width:82%"
                              onchange="commonSelectAjaxCall2('floor_room_detail','id','roomno','floorname',this.value,'','','roomno','ASC','subfloorid','Select Sub-Floor');">
                  </s:select>
                   <%-- list="#{'-1' : 'Select Floor','Basement' : 'Basemnet', 'Grd Flr' : 'Ground Floor', '1st Flr' : 'First Floor', '2nd Flr' : 'Second Floor', '3rd Flr' : 'Third Floor', '4th Flr' : 'Fourth Floor', '5th Flr' : 'Fifth Floor', '6th Flr' : 'Sixth Floor', '7th Flr' : 'Seventh Floor'}" --%>
			</div>
	 </div>
	 </s:div>
	 <s:if test="%{subFlrStatus}">
	 <span id="normalSubdept" class="pIds" style="display: none; ">subfloorid#Sub-Floor#D#,</span>
     <span id="ddSubdept" class="ddPids" style="display: none; ">subfloorid#Sub-Floor#D#,</span>
	 <div class="newColumn">
         <div class="leftColumn">Sub Floor:&nbsp;</div>
			<div class="rightColumn">
				<span class="needed"></span>
                  <s:select 
                              id="subfloorid"
                              name="roomNo"
                              list="{'No Data'}"
                              headerKey="-1"
                              headerValue="Select Sub-Floor"
                              cssClass="select"
                              cssStyle="width:82%"
                              onchange="getSubFloorDetail(this.value);"
                              >
                  </s:select>
			</div>
	 </div>
	 
	  <div class="newColumn">
	      <div class="leftColumn">Inter Com:&nbsp;</div>
			<div class="rightColumn">
             	  <s:textfield  id="interCom" disabled="true" cssClass="textField" placeholder="Enter Data" onchange="Reset('.pIds');"/>
             </div>
            </div>
	 </s:if>	 
     
     <s:iterator value="pageFieldsColumns" begin="10" end="10">
	  <s:if test="%{mandatory}">
       		 <span id="ddSubdept" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
       </s:if>
      
          <div class="newColumn">
	      <div class="leftColumn"><s:property value="%{value}"/>:&nbsp;</div>
			<div class="rightColumn">
			<s:if test="%{mandatory}"><span class="needed"></span></s:if>
             	  <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" onchange="Reset('.pIds');"/>
             </div>
            </div>
     </s:iterator>
     
     
	 <div class="clear"></div>
	 <br>
	 <span id="normalSubdept" class="pIds" style="display: none; ">recvSMS#recvSMS#C#CB,recvEmail#recvEmail#C#CB,</span>
     <span id="ddSubdept" class="ddPids" style="display: none; ">recvSMS#recvSMS#C#CB,recvEmail#recvEmail#C#CB,</span>
               
		 <div class="fields" align="center">
		 <ul>
			<li class="submit" style="background:none;">
			<div class="type-button">
			<div id="ButtonDiv">
	        <sj:submit 
                        targets="confirmingEscalation" 
                        clearForm="true"
                        value="Register" 
                        button="true"
                        onBeforeTopics="validateCall"
                        onCompleteTopics="beforeFirstAccordian"
                        cssStyle="margin-left:54px;margin-top:-23px;"
				        />
			 </div>
			            <sj:a cssStyle="margin-left: 231px;margin-top: -27px;" button="true" href="#" onclick="resetForm('formone');Reset('.pIds');">Reset</sj:a>
		       </div>
	        </li>
		 </ul>
		 </div>
	     
	     <div align="center">
			<sj:dialog id="printSelect" autoOpen="false"  closeOnEscape="true" modal="true" title="Confirm Feedback Via Call" width="755" height="450" showEffect="slide" hideEffect="explode" position="['center','top']">
                     <sj:div id="foldeffectExcel"  effect="fold"><div id="saveExcel"></div></sj:div>
                     <div id="confirmingEscalation"></div>
            </sj:dialog>
         </div>
</s:form>
</div>
</div>
</body>
</html>