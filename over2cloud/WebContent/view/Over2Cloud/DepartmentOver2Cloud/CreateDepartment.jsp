 <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="/js/organization.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/showhide.js"/>"></script>
<SCRIPT type="text/javascript" src="js/feedback/feedbackForm.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/department/department.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/common/commonContact.js"></SCRIPT>
<script type="text/javascript" src="js/hr/commonHr.js"></script>
<SCRIPT type="text/javascript" src="js/commanValidation/validation.js"></SCRIPT>
<SCRIPT type="text/javascript">
$.subscribe('level1', function(event,data)
           {
             setTimeout(function(){ $("#orglevel1Div").fadeIn(); }, 10);
             setTimeout(function(){ $("#orglevel1Div").fadeOut(); }, 4000);
           });

    function viewDept()
    {
        $("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
        $.ajax({
            type : "post",
            url : "/over2cloud/view/Over2Cloud/commonModules/beforeDepartmentViewHeader.action?deptFlag=1",
            success : function(subdeptdata) {
           $("#"+"data_part").html(subdeptdata);
        },
           error: function() {
                alert("error");
            }
         });
    }

    function resetForm(formone) {
        
        $('#'+formone).trigger("reset");
        $("#extraDiv").hide();
        }

    function removCssText()
    {
        document.getElementById("msg_div").style.display="none";
    }
    function toTitleCase(str,id)
    {
        document.getElementById(id).value=str.replace(/\w\S*/g, function(txt){return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();});
    }
</script>
</head>
<body>
 
    <div class="list-icon">
     <div class="head">Contact Sub-Type</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Add</div>
</div>
<div class="clear"></div>
     <div style="width: 100%; center; padding-top: 10px;">
       <div class="border" style="height: 50%">
   <s:form id="formone" name="formone" namespace="/view/Over2Cloud/commonModules" action="createDepartment" theme="css_xhtml"  method="post"enctype="multipart/form-data" >
                           <center>
                             <div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
                             <div id="errZone" style="float:center; margin-left: 7px"></div>        
                             </div>
                           </center>
                 <s:iterator value="departmentDropDown" status="counter">
                  <s:if test="%{mandatory}">
                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>
                 
                  <s:if test="%{key=='mappedOrgnztnId'}">
                  <s:if test="%{mgtStatus}">
                    <div class="newColumn">
                                <div class="leftColumn1">Country Office:</div>
                                <div class="rightColumn1">
                                <s:if test="%{mandatory}"><span class="needed"></span> </s:if>
                      	<s:select 
                                      id="mappedOrgnztnId"
                                      name="mappedOrgnztnId"
                                      list="officeMap"
                                      headerKey="-1"
                                      headerValue="Select Country Office" 
                                      cssClass="select"
                                      cssStyle="width:82%"
                                       onchange="getheadoffice(this.value,'headOfficeId')"
                                     
                                      >
                          </s:select>
                              </div>
                  </div>
                                   <s:if test="%{mandatory}">
                      <span class="pIds" style="display: none; ">headOfficeId#<s:property value="%{headOfficeId}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if> 
                   	<div class="newColumn" >
			  		<div class="leftColumn1">Head Office:</div>
	           		<div class="rightColumn1">
				     <s:if test="%{mandatory}"><span class="needed"></span> </s:if>
				  
				    <s:select 
                                      id="headOfficeId"
                                      name="headofficeId"
                                      list="#{'-1':'Select Head Office'}"
                                      cssClass="select"
                                      cssStyle="width:82%"
                                      theme="simple"
                                      onchange="getbranchoffice(this.value,'regLevel')"
                                      >
                          </s:select>
				    </div>
			        </div>
			        </s:if>
			        <s:if test="%{hodStatus}">
			                    <s:if test="%{mandatory}">
                      <span class="pIds" style="display: none; ">regLevel#<s:property value="%{regLevel}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if> 
			        <div class="newColumn" >
			  		<div class="leftColumn1">Branch Office:</div>
	           		<div class="rightColumn1">
				   <span class="needed"></span>
				  
				   		 <s:select 
                                      id="regLevel"
                                      name="regLevel"
                                      list="officeMap"
                                      cssClass="select"
                                      headerKey="-1"
                                      headerValue="Select Branch Office" 
                                      cssStyle="width:82%"
                                      theme="simple"
                                      onchange="getGroupNamesForMappedOffice('groupId',this.value);"  > 
                                      </s:select>
				    </div>
			        </div>
			        </s:if>
                  </s:if>
                    <s:elseif test="%{key=='groupId'}">
                       <div class="newColumn">
                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
                                <div class="rightColumn1">
                                <s:if test="%{mandatory}">
                                      <span class="needed"></span>
                              </s:if>
                            <s:select 
                                      id="groupId"
                                      name="groupId" 
                                      list="officeMap"
                                      headerKey="-1"
                                      headerValue="Select Group" 
                                      cssClass="select"
                                      cssStyle="width:82%"   
                                        theme="simple"
                                    
                                      >   
                                     
                          </s:select>
                              </div>
                        </div>
                  </s:elseif>
                 </s:iterator>
                 
                 <div class="clear"></div>
                 <s:iterator value="departmentTextBox" status="counter">
                  <s:if test="%{mandatory}">
                      <span class="pIds" style="display: none; "><s:property value="%{key}"/>#<s:property value="%{value}"/>#<s:property value="%{colType}"/>#<s:property value="%{validationType}"/>,</span>
                 </s:if>
                  <s:if test="#counter.count%2 !=0">
                 <div class="newColumn">
                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
                                <div class="rightColumn1">
                                <s:if test="%{mandatory}">
                      <span class="needed">
                      </span>
                  </s:if>
                  <s:if test="%{key=='deptName'}">
                      <s:textfield name="%{key}" id="%{key}" maxlength="200" placeholder="Enter Data" cssStyle="margin: 1px 0px 11px 0px;width: 82%;" onblur="toTitleCase1(this.value,this.id)"  cssClass="textField" onchange="checkforvalid(this.value);"/>
                 <img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none;"/> 
                  <!--  <div style=" color: red; " id="userStatus" class="user_form_text"></div>-->
                  <!--    <div  id="userStatus1" class="user_form_text"></div>-->
              	   <div   id="check" class="user_form_text" style=" margin-top: -5%;display:none"> <img src="images/valid.png" style="height: 31px;width: 40px;margin-left: 271%;margin-top: -16% " > </div>
                   <div  id="userStatus1" class="user_form_text" style=" margin-top: -5%;display:none"><img src="images/invalid.png" style="height: 31px;width: 40px;margin-left: 271%;margin-top: -16% " > </div> 
                 
                  </s:if>
                  <s:else>
                      <s:textfield name="%{key}" id="%{key}" maxlength="200" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" />
                  </s:else>
                   <div id="msg_div" ></div>
                    </div>
                    <sj:a value="+" onclick="adddiv('100')" button="true" cssClass="submit" cssStyle="margin-left: 561px;margin-top: -46px;">+</sj:a>
                  </div>
                  </s:if>
                  <s:else>
                  <div class="newColumn">
                                <div class="leftColumn1"><s:property value="%{value}"/>:</div>
                                <div class="rightColumn1">
                                <s:if test="%{mandatory}">
                      <span class="needed">
                      </span>
                  </s:if>
                      <s:if test="%{key=='deptName'}"> 
                      <s:textfield name="%{key}" id="%{key}" maxlength="200" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" onchange="checkDeptAvailability('deptName','msg_div');"/>
                  </s:if>
                  <s:else>
                      <s:textfield name="%{key}" id="%{key}" maxlength="200" placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px"  cssClass="textField" />
                  </s:else>
                  </div>
                  </div>
                 </s:else>
                 </s:iterator>
                
                
                <div id="extraDiv"> 
                 <s:iterator begin="100" end="120" var="deptIndx">
                        <div class="clear"></div>
                         <div id="<s:property value="%{#deptIndx}"/>" style="display: none">
                             <s:iterator value="departmentTextBox" status="counter">
                            <div class="newColumn">
                                <div class="leftColumn1"><s:property value="%{value}"/><font color="red"></font>:</div>
                                <span class="needed" style="margin-left: -5px; margin-top: 8px; height: 23px;"></span>
                                <div class="rightColumn1"> 
                                    <s:textfield name="deptName"  id="deptName%{#deptIndx}"  cssClass="textField"  placeholder="Enter Data"  onchange="checkforvalidcopy(this.value);" style="width: 82%;margin-left: -1%;"/>
                                   <img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none;"/> 
                   
                     <div  id="status" class="user_form_text" style=" margin-top: -5%;display:none"><img src="images/valid.png" style="height: 26px;width: 40px;margin-left: 271%;margin-top: -8% " > </div> 
                     <div  id="status1" class="user_form_text" style=" margin-top: -5%;display:none"><img src="images/invalid.png" style="height: 26px;width: 40px;margin-left: 271%;margin-top: -8% " > </div> 
                                    </div>
                                    
                            <div style="margin-top: -37px;">
                            <s:if test="#deptIndx==113">
                                <div class="user_form_button2" ><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true"> - </sj:a></div>
                            </s:if>
                               <s:else>
                                   <div class="user_form_button2"style=" margin-left: 93%;"><sj:a value="+"  onclick="adddiv('%{#deptIndx+1}')" button="true"> + </sj:a></div>
                                  <div class="user_form_button3" style="padding-left: 10px;margin-left: 98%;margin-top: -5%;"><sj:a value="-" onclick="deletediv('%{#deptIndx}')" button="true"> - </sj:a></div>
                               </s:else>
                               </div>
                            </div>
                            </s:iterator>
                        </div>
                    </s:iterator>
                    </div>
                <center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
                <div class="clear" >
                <div style="padding-bottom: 43px;margin-left: -97px;margin-top: 2%;" align="center">
                        <sj:submit 
                             targets="level123" 
                             clearForm="true"
                             value="Save" 
                             effect="highlight"
                             effectOptions="{ color : '#222222'}"
                             effectDuration="5000"
                             button="true"
                             onBeforeTopics="validate"
                             onclick="removCssText();"
                             onCompleteTopics="level1"
                        />
                        &nbsp;&nbsp;
                        
                      
                        <sj:submit 
                             value="Reset" 
                             button="true"
                             cssStyle="margin-left: 139px;margin-top: -43px;"
                            onclick="resetForm('formone');resetColor('.pIds');" 
                            
                        />&nbsp;&nbsp;
                        <sj:a
                            cssStyle="margin-left: 276px;margin-top: -58px;"
                        button="true" href="#" value="View" onclick="viewDept();" cssStyle="margin-left: 266px;margin-top: -74px;" 
                        >Back
                    </sj:a>
                    </div>
               </div>
               <sj:div id="orglevel1Div"  effect="fold">
                   <sj:div id="level123">
                   </sj:div>
               </sj:div>
   </s:form>          
</div>
</div>
</body>
</html>
