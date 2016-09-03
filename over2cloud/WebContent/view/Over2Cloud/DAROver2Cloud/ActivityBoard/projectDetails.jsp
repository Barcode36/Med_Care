<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<s:if test="taskDetails.size()>0">
<center><b>Registration Details</b></center>
 <table width="100%" border="1" style="border-collapse: collapse;">
        
        <tr bgcolor="#D8D8D8" style="height: 25px">
          <s:iterator value="%{taskDetails}" status="status" >
            <s:if test="%{key=='Specific Task' || key=='Task Priority'}">
                    <td align="left" width="15%" ><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="left" width="30%"> <s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
        </tr>
        <tr style="height: 25px">
         <s:iterator value="%{taskDetails}" status="status" >
            <s:if test="%{key=='Task Type' || key=='For'}">
                    <td align="left" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                   <td align="left" width="30%" > <s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
       </tr>
       <tr bgcolor="#D8D8D8" style="height: 25px">
         <s:iterator value="%{taskDetails}" status="status" >
           <s:if test="%{key=='Name' || key=='Offering'}">
               
                   <td align="left" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                   <td align="left" width="30%" > <s:property value="%{value}"/></td>
               
           </s:if>
       </s:iterator>
       </tr>
        <tr style="height: 25px">
         <s:iterator value="%{taskDetails}" status="status" >
            <s:if test="%{key=='Registered By' || key=='Registered On'}">
                    <td align="left" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="left" width="30%" > <s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
    </tr>
          
</table> 
</s:if>
<s:if test="taskDetails.size()>0">
<center><b>Allotment Details</b></center>
 <table width="100%" border="1" style="border-collapse: collapse;">
        
        <tr bgcolor="#D8D8D8" style="height: 25px">
          <s:iterator value="%{taskDetails}" status="status" >
            <s:if test="%{key=='Alloted To' || key=='Co-owner'}">
                    <td align="left" width="15%" ><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="left" width="30%"> <s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
        </tr>
        <tr style="height: 25px">
         <s:iterator value="%{taskDetails}" status="status" >
            <s:if test="%{key=='Initiation Date' || key=='Due Date'}">
                    <td align="left" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                   <td align="left" width="30%" > <s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
       </tr>
       <tr bgcolor="#D8D8D8" style="height: 25px">
         <s:iterator value="%{taskDetails}" status="status" >
           <s:if test="%{key=='Technical Review By' || key=='On'}">
                   <td align="left" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                   <td align="left" width="30%" > <s:property value="%{value}"/></td>
           </s:if>
       </s:iterator>
       </tr>
        <tr style="height: 25px">
         <s:iterator value="%{taskDetails}" status="status" >
            <s:if test="%{key=='Functional Review By' || key=='On'}">
                    <td align="left" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="left" width="30%" > <s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
    </tr>
          
</table> 
</s:if>

<s:if test="%{mode=='activity'}">
<s:if test="dailyDetails.size()>0">
<center><b>Daily Records</b></center>

      <s:iterator value="%{dailyDetails}" id="totalCompl">   
      <table width="100%" border="1" style="border-collapse: collapse;">
        <tr bgcolor="#D8D8D8" style="height: 25px">
            <td align="left" width="15%" ><strong>Current Activity:</strong></td>
            <td align="left" width="30%"> <s:property value="#totalCompl.today"/></td>
            <td align="left" width="15%"><strong>Next Activity:</strong></td>
            <td align="left" width="30%" > <s:property value="#totalCompl.tommorow"/></td>
        </tr>
        <tr  style="height: 25px">
            <td align="left" width="15%"><strong>Man Hours:</strong></td>
            <td align="left" width="30%" > <s:property value="#totalCompl.manHour"/></td>
            <td align="left" width="15%"><strong>Cost (if any):</strong></td>
            <td align="left" width="30%" > <s:property value="#totalCompl.cost"/></td>
        </tr>
        <tr bgcolor="#D8D8D8" style="height: 25px">
            <td align="left" width="15%" ><strong>Status:</strong></td>
            <td align="left" width="30%"> <s:property value="#totalCompl.compercentage"/></td>
            <td align="left" width="15%"><strong>Unscheduled Activity:</strong></td>
            <td align="left" width="30%" > <s:property value="#totalCompl.otherworkk"/></td>
        </tr>
         <tr  style="height: 25px">
            <td align="left" width="15%"><strong>Challenges Faced (If Any):</strong></td>
            <td align="left" width="30%" > <s:property value="#totalCompl.challenges"/></td>
            <td align="left" width="15%"><strong>Submitted On:</strong></td>
            <td align="left" width="30%" > <s:property value="#totalCompl.lastdone"/></td>
            
        </tr>
    </table> 
   </s:iterator>       

<%-- <table width="100%" border="0" style="border-collapse: collapse;">
 <s:iterator value="%{dailyDetails}" id="totalCompl">
        <tr>
            <td>
                <table width="100%"  align="center" border="1" style="border-collapse: collapse;">
                    <tr bgcolor="#D8D8D8" style="height: 25px;">
                        <td align="left" width="12%"><strong>Current Activity:</strong></td>
                        <td  align="center"><s:property value="#totalCompl.today" /></td>
                    </tr>
                    <tr style="height: 25px;">
                        <td align="left" width="12%"><strong>Next Activity:</strong></td>
                        <td  align="center"><s:property value="#totalCompl.tommorow" /></td>
                    </tr>
                    <tr bgcolor="#D8D8D8" style="height: 25px;">
                        <td align="left" width="12%"><strong>Man Hours:</strong></td>
                        <td  align="center"><s:property value="#totalCompl.manHour" /></td>
                    </tr>
                    <tr style="height: 25px;">
                        <td align="left" width="12%"><strong>Cost (if any):</strong></td>
                        <td  align="center"><s:property value="#totalCompl.cost" /></td>
                    </tr>
                     <tr bgcolor="#D8D8D8" style="height: 25px;">
                        <td align="left" width="12%"><strong>Unscheduled Activity:</strong></td>
                        <td  align="center"><s:property value="#totalCompl.otherworkk" /></td>
                    </tr>
                    <tr style="height: 25px;">
                        <td align="left" width="12%"><strong>Challenges Faced (If Any):</strong></td>
                        <td  align="center"><s:property value="#totalCompl.challenges" /></td>
                    </tr>
                       <tr bgcolor="#D8D8D8" style="height: 25px;">
                        <td align="left" width="12%"><strong>Completion(%):</strong></td>
                        <td  align="center"><s:property value="#totalCompl.Compercentage" /></td>
                    </tr>
                    
                </table>
            </td>
        </tr>
</s:iterator>
</table> --%>
</s:if>
<s:if test="techDetails.size()>0">
<center><b>Technical Review</b></center>
 <table width="100%" border="1" style="border-collapse: collapse;">
        
        <tr bgcolor="#D8D8D8" style="height: 25px">
          <s:iterator value="%{techDetails}" status="status" >
            <s:if test="%{key=='Due On' || key=='Reviewed On'}">
                    <td align="left" width="15%" ><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="left" width="30%"> <s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
        </tr>
        <tr style="height: 25px">
         <s:iterator value="%{techDetails}" status="status" >
            <s:if test="%{key=='Work status' || key=='Rating'}">
                    <td align="left" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                   <td align="left" width="30%" > <s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
       </tr>
       <tr bgcolor="#D8D8D8" style="height: 25px">
         <s:iterator value="%{techDetails}" status="status" >
           <s:if test="%{key=='Comments' || key=='Review Documents'}">
               
                   <td align="left" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                   <td align="left" width="30%" > <s:property value="%{value}"/></td>
               
           </s:if>
       </s:iterator>
          
</table> 
</s:if>
<s:if test="functDetails.size()>0">
<center><b>Functional Review</b></center>
 <table width="100%" border="1" style="border-collapse: collapse;">
        
        <tr bgcolor="#D8D8D8" style="height: 25px">
          <s:iterator value="%{functDetails}" status="status" >
            <s:if test="%{key=='Due On' || key=='Reviewed On'}">
                    <td align="left" width="15%" ><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="left" width="30%"> <s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
        </tr>
        <tr style="height: 25px">
         <s:iterator value="%{functDetails}" status="status" >
            <s:if test="%{key=='Work status' || key=='Rating'}">
                    <td align="left" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                   <td align="left" width="30%" > <s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
       </tr>
       <tr bgcolor="#D8D8D8" style="height: 25px">
         <s:iterator value="%{functDetails}" status="status" >
           <s:if test="%{key=='Comments' || key=='Review Documents'}">
               
                   <td align="left" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                   <td align="left" width="30%" > <s:property value="%{value}"/></td>
               
           </s:if>
       </s:iterator>
          
</table> 
</s:if>
</s:if>
</body>
</html>