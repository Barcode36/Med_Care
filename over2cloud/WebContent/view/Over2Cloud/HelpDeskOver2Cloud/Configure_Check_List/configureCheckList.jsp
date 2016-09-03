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
<script type="text/javascript" src="<s:url value="/js/compliance/compliance.js"/>"></script>
<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 
<link href="js/multiselect/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="js/multiselect/jquery.multiselect.css" rel="stylesheet" type="text/css" />
 <script type="text/javascript" src="<s:url value="/js/multiselect/jquery-ui.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/multiselect/jquery.multiselect.js"/>"></script>
 


<script type="text/javascript">
function viewConfigureCheckList()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Configure_Check_List/viewConfigureCheckList.action",
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
          alert("error");
      }
	 });
}
function getCallPage()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/HelpDeskOver2Cloud/Lodge_Feedback/beforeFeedViaCall.action?feedStatus=call&dataFor=HDM",
	    success : function(feeddraft) {
       $("#"+"data_part").html(feeddraft);
	},
	   error: function() {
            alert("error");
        }
	 });
}

function getKRName(subGroupId)
{
	 $.ajax({
		    type : "post",
		    url : "view/Over2Cloud/Compliance/compl_task_page/getKRNameAction.action?subGroupId="+subGroupId,
		    success : function(data) 
		    {
				$("#krNameDiv").html(data);
		    },
		    error: function() 
		    {
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


function addInKR()
{
	 $('#openKRDialog').dialog('open');
	 $('#openKRDialog').html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "/over2cloud/view/Over2Cloud/KRLibraryOver2Cloud/beforeKRUpload.action?dataFrom=Other",
	    success : function(data) 
	    {
			 	$("#openKRDialog").html(data);
 
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
}

function fromKRAdd()
{
 
	if($("#fromkrview").css('display') == 'none')
		$("#fromkrview").show('slow');
		
}

 



</script>
<script type="text/javascript">
$(document).ready(function()
	{
	$("#krname").multiselect({
		   show: ["", 200],
		   hide: ["explode", 1000]
		});
	});
</script>



</head>
<body>
<sj:dialog
          id="openKRDialog"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Add KR Library"
          modal="true"
          closeTopics="closeEffectDialog"
          width="1250"
          height="500"
          >
</sj:dialog>
<div class="clear"></div>
<div class="middle-content">
<div class="list-icon">
	<div class="head">Add</div><img alt="" src="images/forward.png" style="margin-top:10px; float: left;"><div class="head">Completion Tip</div>  
		 
</div>
<div class="clear"></div>
<div style="overflow:auto; height:320px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
<s:form id="formone" name="formone" action="saveConfigureCheckList"  theme="css_xhtml"  method="post" enctype="multipart/form-data">
<s:hidden name="viaFrom" value="online"></s:hidden>
<s:hidden name="pageFor"  value="SD"></s:hidden>
<s:hidden name="subCategory_extra" id="test"></s:hidden>
		<center>
			<div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 6px;">
	             <div id="errZone" style="float:center; margin-left: 7px"></div>        
	        </div>
        </center>    
        
        <s:iterator value="pageFieldsColumns" begin="0" end="1" status="status">
			  <s:if test="%{mandatory}">
					     <span id="normalSubdept" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
	 	      </s:if>
		        <s:if test="#status.odd">
                   <div class="newColumn">
        			   <div class="leftColumn" >To Department&nbsp;:&nbsp;</div>
					        <div class="rightColumn">
					             <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                  <s:select  id="%{key}"
				                              name="todept"
				                              list="serviceDeptList"
				                              headerKey="-1"
				                              headerValue="Select Department" 
				                              cssClass="select"
					                          cssStyle="width:82%"
				                              onchange="getServiceDept('subdepartment','id','subdeptname','deptid',this.value,'moduleName','HDM','subdeptname','ASC','subdeptname','Select Sub-Department');commonSelectAjaxCall('kr_group_data','id','groupName','dept',this.value,'','','groupName','ASC','kr_group','Select KR Group');"
				                              >
				                  </s:select>
                           </div>
                     </div>
                  </s:if>
                  <s:else>
                     <div class="newColumn">
					      <div class="leftColumn"><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
							<div class="rightColumn">
				             	  <s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                  <s:select 
				                              id="%{key}"
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
     
      <s:iterator value="pageFieldsColumns" begin="6" end="7" status="status">
	  <s:if test="%{mandatory}">
             	<s:if test="key=='feedType'">
             		<span id="normalSubdept" class="pIds" style="display: none; ">feedType#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
             	 	</s:if>
             	<s:elseif test="key=='category'">
             		<span id="normalSubdept" class="pIds" style="display: none; ">category#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
              	</s:elseif>
      </s:if>
	      <s:if test="#status.odd">
	      <div class="newColumn">
	    	<div class="leftColumn" ><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
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
		      <div class="leftColumn"><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
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
     
     <s:iterator value="pageFieldsColumns" begin="8" end="8" status="status">
	  <s:if test="%{mandatory}">
	   		 <span id="normalSubdept" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
         </s:if>
      <s:if test="#status.odd">
      <div class="newColumn">
    	<div class="leftColumn" ><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
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
     <!--  <s:else>
          <div class="newColumn">
	      <div class="leftColumn"><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
			<div class="rightColumn">
			 <s:if test="%{mandatory}"><span class="needed"></span></s:if>
             	  <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" onchange="Reset('.pIds');"/>
             </div>
            </div>
      </s:else>  -->
     </s:iterator>
      
     
   <!--   
     <s:div>
     <span id="normalSubdept" class="pIds" style="display: none; ">floorid#Floor#D#,</span>
     <span id="ddSubdept" class="ddPids" style="display: none; ">floorid#Floor#D#,</span>
	 <div class="newColumn">
         <div class="leftColumn">Floor&nbsp;:&nbsp;</div>
			<div class="rightColumn">
				<span class="needed"></span>
                  <s:select 
                              id="floorid"
                              name="floor"
                              list="#{'-1' : 'Select Floor','Basement' : 'Basemnet', 'Grd Flr' : 'Ground Floor', '1st Flr' : 'First Floor', '2nd Flr' : 'Second Floor', '3rd Flr' : 'Third Floor', '4th Flr' : 'Fourth Floor', '5th Flr' : 'Fifth Floor', '6th Flr' : 'Sixth Floor', '7th Flr' : 'Seventh Floor'}"
                              cssClass="select"
                                 cssStyle="width:82%"
                                 onchange="Reset('.pIds');">
                  </s:select>
			</div>
	 </div>
	 </s:div>
     
     
     <s:iterator value="pageFieldsColumns" begin="10" end="10">
	  <s:if test="%{mandatory}">
       		 <span id="ddSubdept" class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
       </s:if>
      
          <div class="newColumn">
	      <div class="leftColumn"><s:property value="%{value}"/>&nbsp;:&nbsp;</div>
			<div class="rightColumn">
			<s:if test="%{mandatory}"><span class="needed"></span></s:if>
             	  <s:textfield name="%{key}"  id="%{key}"  cssClass="textField" placeholder="Enter Data" onchange="Reset('.pIds');"/>
             </div>
            </div>
     </s:iterator>
     
     
      
       <div class="newColumn">
	     <div class="leftColumn">Self Acknowledge&nbsp;:&nbsp;</div>
	     	<div class="rightColumn" >
	     		<div style="width: auto; float: left; text-align: left; line-height:25px; margin-right: 10px;">SMS:</div><div style="width: auto; float: left; text-align: left;  line-height:25px; margin-right: 10px; vertical-align: middle; padding-top: 5px;"><s:checkbox name="recvSMS" id="recvSMS"></s:checkbox></div>
	     		<div style="width: auto; float: left; text-align: left; line-height:25px; margin-right: 10px; margin-left: 20px;">Email:</div><div style="width: auto; float: left; text-align: left;  line-height:25px; margin-right: 10px; vertical-align: middle; padding-top: 5px;"><s:checkbox name="recvEmail" id="recvEmail"></s:checkbox></div>
	     	</div>
    </div>    --> 
      
         
         
         
          <sj:a cssStyle="margin-left: 330px;margin-top: 9px;" button="true" href="#" onclick="fromKRAdd();">From KR</sj:a>
		    <sj:a cssStyle="margin-left: 0px;margin-top: 9px;" button="true" href="#" onclick="addInKR();">To KR</sj:a>
         
           <div class="clear"></div>
      <div id="fromkrview" style="display:none">
         
         <div class="newColumn">
          	<div class="leftColumn">KR Group:</div>
           	<div class="rightColumn">
             	<s:select  
                        id		    ="kr_group"
                        list		="{'No Data'}"
                        headerKey	="-1"
                        headerValue="Select KR Group" 
                        cssClass="select"
		 				cssStyle="width:82%"
		 				onchange="commonSelectAjaxCall('kr_sub_group_data','id','subGroupName','groupName',this.value,'','','subGroupName','ASC','kr_subgroup','Select Sub-Group Name');"
                        >
            </s:select>
           	</div>
          	</div>
          	
          	<div class="newColumn">
          	<div class="leftColumn">Sub-Group Name:</div>
           	<div class="rightColumn">
            	<s:select  
                        id		    ="kr_subgroup"
                        list		="{'No Data'}"
                        headerKey	="-1"
                        headerValue="Select Sub-Group Name" 
                        cssClass="select"
		 				cssStyle="width:82%"
		 				onchange="getKRName(this.value);"
                        >
            </s:select>
           	</div>
          	</div>
          	
          	<div class="newColumn">
          	<div class="leftColumn">KR Name:</div>
           	<div class="rightColumn">
             	<div id="krNameDiv">
           	<s:select  
                        id		    ="krname"
                        name		="krname"
                        list		="{'No Data'}"
                        headerKey	="-1"
                        headerValue="Select KR Name" 
                        cssClass="select"
		 				cssStyle="width:5%"
		 				multiple="true"
                        >
            </s:select>
            </div>
           	</div>
          	</div>
          	
          	 </div>
          	<div class="clear"></div>
    <div class="newColumn">
         		<div class="leftColumn">Completion Tip:</div>
          		<div class="rightColumn">
          		<s:textfield cssStyle="width: 92%" name="completion"  id="completion"  cssClass="textField" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px; width:80%"/>
          		<div id="newDiv" style="float: right;width: 10%;margin-top: -46px;"><sj:a value="+" onclick="adddiv('100')" button="true" cssStyle="margin-left: -14px;margin-top: 7px;">+</sj:a></div>
          		</div>
         	    </div>
    <s:iterator begin="100" end="108" var="compTipIndx">
		  <div class="clear"></div>
	      <div id="<s:property value="%{#compTipIndx}"/>" style="display: none">
	      	 	<div class="newColumn">
         		<div class="leftColumn">Completion Tip:</div>
          		<div class="rightColumn">
          			<sj:textfield name="completion" id="completion" placeholder="Enter Data" cssClass="textField" />
          			<div style="float: left;margin-left: 85%;margin-top: -30px;width: 44%">	
					<s:if test="#compTipIndx==108">
	                    <div class="user_form_button2"><sj:a value="-" onclick="deletediv('%{#compTipIndx}')" button="true">-</sj:a></div>
	                </s:if>
					<s:else>
	  		  			<div class="user_form_button2"><sj:a value="+" onclick="adddiv('%{#compTipIndx+1}')" button="true" cssStyle="margin-left: -12px;">+</sj:a></div>
	          			<div class="user_form_button3" style="margin-left: -4px;"><sj:a value="-" onclick="deletediv('%{#compTipIndx}')"  button="true">-</sj:a></div>
	       			</s:else>
       			</div>
          		</div>
         	    </div>
          		 
	      </div>
	      </s:iterator>
	      
	      
	      
     
     <div class="clear"></div>
	 <div class="fields" align="center">
				<ul>
				<li class="submit"  style="background:none"; >
					<div class="type-button">
					<div id="ButtonDiv">
	                <sj:submit 
	                        id="onlineSubmitId"
	                        targets="target1" 
	                        clearForm="true"
	                        value="Save" 
	                        button="true"
	                  
	                          onBeforeTopics="validateCompletionTip"
	                        cssStyle="margin-left: -40px;"
	                        effect			=	"highlight"
                 			effectOptions	=	"{ color : '#222222'}"
                 			effectDuration	=	"5000"
	                          indicator="indicator1"
	                      
	                        onCompleteTopics="beforeFirstAccordian"
	                     
				            />
				    </div>
				            <sj:a cssStyle="margin-left: 185px;margin-top: -43px;" button="true" href="#" onclick="resetForm('formone');Reset('.pIds'); ">Reset</sj:a>
				         <sj:a cssStyle="margin-left: 9px;margin-top: -43px;" button="true" href="#" onclick="viewConfigureCheckList();">Back</sj:a>  
	               </div>
	               </li>
				</ul>
				</div>
				
	<center><img id="indicator1" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				  <sj:div id="foldeffect1"    effect="fold"><div  id="target1" style="margin-top: 20px;"></div></sj:div>
	   
</s:form>
</div>
</div>
</body>
</html>