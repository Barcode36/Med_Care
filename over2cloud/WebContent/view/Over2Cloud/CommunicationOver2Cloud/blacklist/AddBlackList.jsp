<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>

<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme"
customBasepath="%{contextz}"/>

<script src="<s:url value="/js/common/commonvalidation.js"/>"></script>
<script type="text/javascript" src="<s:url
value="/js/communication/CommuincationBlackList.js"/>"></script>
<style type="text/css">
.user_form_input{
    margin-bottom:10px;
}
</style>
<SCRIPT type="text/javascript">
function showtime(val,div){
var p=document.getElementById(div);
if(val=="Time"){
   p.style.display='block';

  }else{
   p.style.display='none';
  }
}
$.subscribe('result', function(event,data)
               {
                        //document.getElementById("indicator1").style.display="none";
                 setTimeout(function(){ $("#resultblacklist").fadeIn(); }, 10);
                 setTimeout(function(){ $("#resultblacklist").fadeOut(); }, 4000);
               });

function resetForm(formId)
{
        $('#'+formId).trigger("reset");
}

</script>
</head>
<body>
<div class="list-icon">
        <div class="head">Exclusion</div><div class="head"><img alt=""
src="images/forward.png" style="margin-top:50%; float:
left;"></div><div class="head">Add</div>

</div>
        <div class="clear"></div>
<div style=" float:left; padding:10px 0%; width:100%;">
<div class="clear"></div>
<div class="border">

<div class="container_block">
<div style=" float:left; padding:20px 1%; width:98%;">


<s:form id="formtwo" name="formtwo"
namespace="/view/Over2Cloud/CommunicationOver2Cloud/Comm"
action="insertDataBlackList" theme="css_xhtml"  method="post" >
<div style="float:right; border-color: black; background-color:
rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px
solid red; border-radius: 8px;">
  <div id="errZone" style="float:left; margin-left: 7px"></div>
        </div>

        <s:hidden name="mobileNumbers" value="%{mobileNumbers}" />
                                                <div class="clear"></div>
        <!-- Text box -->
         <s:iterator value="blackListFor" status="counter">
                   <s:if test="%{mandatory}">
                      <span id="mandatoryFields" class="pIds"
style="display: none; "><s:property value="%{key}"/>#<s:property
value="%{value}"/>#<s:property value="%{colType}"/>#<s:property
value="%{validationType}"/>,</span>
                 </s:if>

                                         <%-- <s:if test="#counter.count%2 != 0"> --%>

                                 <s:if test="%{mandatory}">
                           <div class="rightColumn">
                                                <div class="leftColumn1"><s:property value="%{value}"/>: </div>
                                                <div class="rightColumn">
                                            <s:if test="%{key=='blistfor'}">
                       <s:select  cssStyle="width:218px"
                                        list="#{'Mobile Number':' Mobile','Email
ID':' Email','Both':' Both'}"
                                        value="%{m}"
                                        name="%{key}"
                                        id="blacklist" />

                  </s:if> </div></div>
                                                  </s:if>
                                             <s:else>
                                             <div class="rightColumn">
                                                <div class="leftColumn1"><s:property value="%{value}"/>: </div>
                                                <div class="rightColumn">
                                                <s:if test="%{key=='blistfor'}">
                                                 <s:select  cssStyle="width:218px"
                                        list="#{'Mobile Number':' Mobile','Email
ID':' Email','Mobile No & Email':' Both'}"
                                        value="%{Mobile Number}"
                                        name="%{key}"
                                        id="blacklist" />
                                        </s:if>
                                                </div></div>
                                             </s:else>

                                           </s:iterator>
        <div class="rightColumn">
        <span id="mandatoryFields" class="pIds" style="display: none;
">module#Module Name#D,<s:property value="%{value}"/>#D#<s:property
value="%{validationType}"/>,</span>

                                                <div class="leftColumn1">Module Name:</div>
                                        <div class="rightColumn"><span class="needed"></span>
                                          <s:select
                      id="module"
                                  name="module"
                                  list="appDetails"
                                  headerKey="-1"
                                  headerValue="Select Module Name"
                                  cssClass="select"
                                 cssStyle="width:218px"
                                 />

                  </div></div>
  
                                           <div class="rightColumn">
                                           <span id="mandatoryFields" class="pIds" style="display: none;
">department#Department#D,<s:property value="%{value}"/>#D#<s:property
value="%{validationType}"/>,</span>

                                                <div class="leftColumn1">Department:</div>
                                                <div class="rightColumn"><span class="needed"></span>
                                          <s:select
                     id="department"
                       name="department"
                        list="deptMap"
                       headerKey="-1"
                       headerValue="Select Department"
                       cssClass="select"
                       cssStyle="width:218px"
                       onchange="getContactNamesForUser(this.value,'name');"
                       />

                  </div></div>

                   <div class="rightColumn">
                   <span id="mandatoryFields" class="pIds"
style="display: none; ">name#Contact Name#D,<s:property
value="%{value}"/>#D#<s:property value="%{validationType}"/>,</span>

                                                <div class="leftColumn1">Contact Name:</div>
                                         <div class="rightColumn"><span class="needed"></span>
                                          <s:select
                           id="name"
                       name="name"
                       list="{'No Data'}"
                       headerKey="-1"
                       headerValue="Select Contact Name"
                       cssClass="select"
                       cssStyle="width:218px"
                       onchange="getEmpDetails(this.value)"
                       />

                  </div></div>



                                           <s:iterator value="mobileNOTextBox" status="counter">
                              <s:if test="#counter.count%2 != 0">

                                 <s:if test="%{mandatory}">
                           <div class="rightColumn">
                                                <div class="leftColumn1"><s:property value="%{value}"/>: </div>
                                                <div class="rightColumn">
                                             <span class="needed"></span><s:textfield name="%{key}"
id="%{key}"  cssClass="textField" placeholder="Enter Data"
cssStyle="margin:0px 0px 10px 0px;"/>
                                             </div></div>
                                                  </s:if>
                                            
                                          </s:if>
                                           </s:iterator>
                                     
                                     
                                     
                                    <div class="rightColumn">
                                                <div class="leftColumn1">Email Id: </div>
                                                <div class="rightColumn">
                                             <span class="needed"></span><s:textfield name="email"
id="email"  cssClass="textField" placeholder="Enter Data"
cssStyle="margin:0px 0px 10px 0px;"/>
                                             </div></div>
                                        
                                     
           <s:iterator value="reasonForBlacklist" status="counter">
                   
                                         <s:if test="#counter.count%2 != 0">

                                 <s:if test="%{mandatory}">
                           <div class="rightColumn">
                                                <div class="leftColumn1"><s:property value="%{value}"/>: </div>
                                                <div class="rightColumn">
                                             <span class="needed"></span><s:textarea name="%{key}"
id="%{key}"      placeholder="Enter Data"
style="margin: 0px 0px 5px; width: 220px; height: 30px;"/>
                                             </div></div>
                                                  </s:if>
                                              
                                          </s:if>
                                           </s:iterator>
                                           
                                        <div class="clear"></div>
                                        
                                        
                                        
                                 
                <div style="width:100%; line-height:40px;">
       <div style="text-align:right; padding:6px;
line-height:25px;       float:left;     width:12%;">
       Select Period: </div>
       <div style="width: 100%">
                 <s:select cssStyle="width:218px"
                           list="#{'Forever':'Forever','Time':'Period'}"
                           name="duration"
                           id="duration"
                           onchange="showtime(this.value,'dateDiv');"/>


      </div>

       </div>
      <div class="clear"></div>
       <div id=dateDiv style="display: none ;" >
       <div class="newColumn">
                                                                <div class="leftColumn1" style="margin-left: -5px;">From Date:</div>
                                                                <div class="rightColumn">
                                                                 <sj:datepicker id="fromdate" name="fromdate" readonly="true"
placeholder="Enter Date" showOn="focus" displayFormat="dd-mm-yy"
cssClass="textField"   cssStyle="margin:0px 0px 10px 0px;"/>
                                                                </div>
                                                        </div>
                                                        <div class="newColumn">
                                                                <div class="leftColumn1" style="margin-left: -40px;">To Date:</div>
                                                                <div class="rightColumn">
                                                                                  <sj:datepicker id="todate" name="todate" readonly="true"
placeholder="Enter Date"   showOn="focus"
displayFormat="dd-mm-yy"  cssClass="textField"  cssStyle="margin:0px
0px 10px 0px;"/>
                                                                </div>
                                                        </div>
                                                        </div>

             <div class="newColumn">
              <div class="leftColumn" style="margin-top:
-8px;margin-left: -100px">SMS:</div>
             <div class="rightColumn">
                    <s:checkbox id="smsNotification"
name="smsNotification" value="true" ></s:checkbox>
             </div>
             </div>

             <div class="newColumn">
             <div class="leftColumn" style="margin-top:
-8px;margin-left: -164px" >Email:</div>
             <div class="rightColumn">
                    <s:checkbox id="emailNotification"
name="emailNotification"></s:checkbox>
             </div>
             </div>

<!-- Buttons -->
<div class="clear"></div>
 <center><img id="indicator3" src="<s:url
value="/images/indicator.gif"/>" alt="Loading..."
style="display:none"/></center>
        <div class="type-button" style="margin-left: -200px;">
<center>
       <sj:submit
           targets="blacklistresult"
                       clearForm="true"
                       value="Save"
                       effect="highlight"
                       effectOptions="{ color : '#222222'}"
                       effectDuration="5000"
                       button="true"
                       onCompleteTopics="result"
                       cssClass="submit"
                       cssStyle="margin-right: 65px;margin-bottom: -9px;"
                       indicator="indicator2"
                       onBeforeTopics="validate"
         />



         <sj:a
                                        href="#"
                                button="true"
                                onclick="resetForm('formtwo');"
                                                cssStyle="margin-top: -28px;"                                           >
                                                Reset
                   </sj:a>

                   <sj:a
                                        href="#"
                                button="true"
                                onclick="viewBlackList();"
                                                cssStyle="margin-right: -137px;margin-top: -28px;"
                                                >
                                                Back
                   </sj:a>

         <sj:div id="resultblacklist"  effect="fold">
                    <div id="blacklistresult"></div>
               </sj:div>

      </center>

   </div>

</s:form>
</div>
</div>
</div></div>

</body>
</html>