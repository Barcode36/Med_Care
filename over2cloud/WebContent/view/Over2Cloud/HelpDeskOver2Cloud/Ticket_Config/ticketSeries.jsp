<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/helpdesk/ticketSeries.js"/>"></script>
</head>
<body>
<div class="clear"></div>
<div class="middle-content">
<!-- Header Strip Div Start -->
<div class="list-icon">
	 <div class="head">Ticket Series</div><img alt="" src="images/forward.png" style="margin-top:10px; float: left;"><div class="head">Add</div> 
</div>
<div class="clear"></div>
<div style="height: 180px; width: 96%; margin: 1%; border: 1px solid #e4e4e5; -webkit-border-radius: 6px; -moz-border-radius: 6px; border-radius: 6px; -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, .10); box-shadow: 0 1px 4px rgba(0, 0, 0, .10); padding: 1%;">
<s:hidden  id="dataFor" value="%{dataFor}"></s:hidden>
	<s:form id="formone" name="formone" action="addTicketSeries" theme="css_xhtml"  method="post" enctype="multipart/form-data" >
	<s:hidden id="dataFor" name="dataFor" value="%{dataFor}"/>
    <center><div style="float:center; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;"><div id="errZone" style="float:center; margin-left: 7px"></div></div></center>          
       
       				<div class="newColumn">
			              <div class="leftColumn">Ticket Type:</div>
							  <div class="rightColumn">
				                   <s:select 
				                              id="ticket_type"
				                              name="ticket_type"
				                              list="ticketSeriesType"
				                              headerKey="-1"
				                              headerValue="Select Ticket Type" 
				                              cssClass="select"
				                              cssStyle="width:82%"
				                              onchange="getChangeDiv(this.value)"
				                              >
				                   </s:select>
				            </div>
			          </div>
			          
			          <div class="newColumn">
		       			 <div class="leftColumn">Abb:</div>
		       			 <div class="rightColumn">
		       			   <s:if test="%{mandatory}"><span class="needed"></span></s:if>
			                   <s:textfield name="abb"  id="abb"  cssClass="textField" maxlength="3" placeholder="Enter Data"/>
						</div>
					 </div>
					 
					  <s:if test="dataFor=='COMPL'">
           			 <div class="newColumn">
		       			 <div class="leftColumn">Frequency:</div>
		       			 <div class="rightColumn">
		       			   		<s:select 
			                              id="frequency"
			                              name="frequency"
			                              list="#{'Yes':'Yes','No':'No'}"
			                              cssClass="select"
			                              cssStyle="width:82%"
			                              >
			                  </s:select>
		       			   </div>
					 </div>
                     </s:if>
					 
					<div id="normal" style="display: none"> 
                     <div class="newColumn">
		       			 <div class="leftColumn">Series:</div>
		       			 <div class="rightColumn">
		       			   <s:if test="%{mandatory}"><span class="needed"></span></s:if>
			                   <s:textfield name="n_series"  id="n_series"  cssClass="textField" maxlength="5" placeholder="Enter Data" onchange="setDemoSeries();"/>
						</div>
					</div>
       				</div>
       				<div id="deptWise" style="display: none">
       					<div class="newColumn">
			              <div class="leftColumn">Department:</div>
							  <div class="rightColumn">
								<s:if test="%{mandatory}"><span class="needed"></span></s:if>
				                   <s:select 
				                              id="deptName"
				                              name="deptName"
				                              list="serviceDeptList"
				                              headerKey="-1"
				                              headerValue="Select Department" 
				                              cssClass="select"
				                              cssStyle="width:82%"
				                              onchange="getPrefix(this.value,'dataFor');"
				                              >
				                   </s:select>
				            </div>
			          </div>
			          
       				</div>
       				<div id="allDeptWise" style="display: none">
       				<div class="newColumn">
		       			 <div class="leftColumn">Dept Fit:</div>
		       			 <div class="rightColumn">
		       			   		<s:select 
			                              id="deptfit"
			                              name="deptfit"
			                              list="#{'1':'1 Char','2':'2 Chars','3':'3 Chars','4':'4Chars'}"
			                              cssClass="select"
			                              cssStyle="width:82%"
			                              >
			                  </s:select>
		       			   </div>
					</div>
       				
       				<div class="newColumn">
		       			 <div class="leftColumn">Series:</div>
		       			 <div class="rightColumn">
			                   <s:textfield name="d_series"  id="d_series"  cssClass="textField" maxlength="5" placeholder="Enter Data" onchange="setDemoSeries();"/>
						</div>
					</div>
       				</div>
       				
       				<div class="newColumn">
		       			 <div class="leftColumn">Demo Series:</div>
		       			 <div class="rightColumn">
			                   <s:textfield  id="demoseries" name="prefix" cssClass="textField" readonly="true"  placeholder="Enter Data"/>
						</div>
					</div>
       				
       				<div class="newColumn">
			                <div class="leftColumn">Floor Wise: </div>
							<div class="rightColumn">
							    <s:select 
		                              id="floor_status"
		                              name="floor_status"
		                              list="#{'N' : 'No Need','Y' : 'Conider Floor','N' : 'Do Not Consider Floor'}"
		                              cssClass="select"
	                                  cssStyle="width:82%">
		                        </s:select>
							</div>
						 </div>
            
            <div class="clear"></div>
            <div class="clear"></div>
		    <div class="fields" align="center">
			<ul>
				<li class="submit" style="background:none;">
					<div class="type-button">
	                <sj:submit 
	                        targets="target1" 
	                        clearForm="true"
	                        value=" Save " 
	                        button="true"
	                        cssClass="submit"
	                        onCompleteTopics="beforeFirstAccordian"
	                        onBeforeTopics="validateTicketSeries"
	                        cssStyle="margin-left: -40px;"
	                        />
	           <sj:a cssStyle="margin-left: 175px;margin-top: -45px;" button="true" href="#" onclick="resetForm('formone');resetColor('.ddPids');resetColor('.normalpIds');">Reset</sj:a>
	            <sj:a cssStyle="margin-left: 2px;margin-top: -45px;" button="true" href="#" onclick="backForm('dataFor');">Back</sj:a>
	                        
	               </div>
	               </li>
		     </ul>
		       <sj:div id="foldeffect1" effect="fold"><div id="target1"></div></sj:div>
			</div>
     </s:form>
</div>
</div>
</body>
</html>
