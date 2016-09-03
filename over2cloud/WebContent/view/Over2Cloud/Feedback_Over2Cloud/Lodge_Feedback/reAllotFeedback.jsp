<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
    <head>
    <script type="text/javascript" src="<s:url value="/js/feedback.js"/>"></script>
    <script type="text/javascript" src="<s:url value="/js/helpdesk/common.js"/>"></script>
    <script type="text/javascript" src="<s:url value="/js/helpdesk/fbdraft.js"/>"></script>
    </head>
    <body>
    <div class="secHead">Re-Assign To </div>
		     <div class="newColumn">
		       <div class="leftColumn">Department:</div>
		            <div class="rightColumn">
		          <span class="needed"></span>
					<s:select 
                              id="deptname3"
                              name="todept"
                              list="serviceDeptList"
                              headerKey="-1"
                              headerValue="Select Department" 
                              cssClass="select"
          					  cssStyle="width:82%"
                              onchange="getSubDept(this.value,'subDeptDiv3',%{deptHierarchy},'3','1','1','1');"
                              >
                  </s:select>
		            </div>
            </div>
		    <div class="newColumn">
		       <div class="leftColumn">Sub-Department:</div>
		            <div class="rightColumn">
		            <span class="needed"></span>
		            <div id="subDeptDiv3">
	                  <s:select 
	                              id="subdept3"
	                              name="tosubdept"
	                              list="{'No Data'}"
	                              headerKey="-1"
	                              headerValue="Select Sub-Department" 
	                              cssClass="select"
          					  cssStyle="width:82%"
	                              >
	                  </s:select>
                  </div>
		            
		            </div>
            </div>
		    <div class="clear"></div>
		    
		    <div class="newColumn">
		       <div class="leftColumn">Feedback Type:</div>
		            <div class="rightColumn">
		            <span class="needed"></span>
					<div id="feedTypeDiv3">
                    <s:select 
                              id="feedbackType"
                              name="feedbackType"
                              list="{'No Data'}"
                              headerKey="-1"
                              headerValue="Select Feedback Type" 
                              cssClass="select"
          					  cssStyle="width:82%"
                              >
                    </s:select>
                  </div>
		            </div>
            </div>
		    <div class="newColumn">
		       <div class="leftColumn">Reason/ RCA:</div>
		            <div class="rightColumn">
		             <span class="needed"></span>
		                   <s:textfield name="reAssignRemark" id="reAssignRemark" placeholder="Enter Re-Assign Reason"  cssClass="textField" />
		            </div>
            </div>
            </body>
</html>