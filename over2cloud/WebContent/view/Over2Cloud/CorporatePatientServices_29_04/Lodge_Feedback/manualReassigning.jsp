<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
</head>
<body>
   <table >
   <tr>
   <td> <s:radio list="#{'Case1':'All Skills '}" name="q1" id="q1"  theme="simple"  onclick="manualReassignPage(this.id)"/></td>
   </tr>
    <tr>
   <td> <s:radio list="#{'Case2':'All Skills Same Floor'}" name="q1" id="q2"  theme="simple"  onclick="manualReassignPage(this.id)"/></td>
   </tr>
    <tr>
   <td> <s:radio list="#{'Case3':'Same Skills All Floor'}" name="q1" id="q3"  theme="simple"  onclick="manualReassignPage(this.id)"/></td>
   </tr>
    <tr>
   <td> <s:radio list="#{'Case4':'Same Skills Same Floor'}" name="q1" id="q4"  theme="simple" value="Case4" onclick="manualReassignPage(this.id)"/></td>
   </tr>
   
   </table>
 			Allot To<sj:autocompleter
					 id="allottoMan"
					 name="allottoMan"
					 list="manualMap"
				 	 selectBox="true"
					 selectBoxIcon="true"
					 onSelectTopics="changeReassign"
				 	/>
				 	<br>
				 <sj:a id="abc" name="abc" onclick="setAllotTo()" cssStyle="margin-left: 111px;margin-top: 33px;" button="true">OK</sj:a>	
				 

    </body>
</html>