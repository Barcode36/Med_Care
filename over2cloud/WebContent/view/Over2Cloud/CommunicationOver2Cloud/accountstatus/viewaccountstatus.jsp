<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@taglib  prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<style type="text/css"></style>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/style1.css" rel="stylesheet" type="text/css" />
<link href="css/style2.css" rel="stylesheet" type="text/css" />

<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
</head>
<body>
 
<div class="clear"></div>
<div class="list-icon">
<div class="head">Account Status </div>
</div>
<div id="datapart">
</br>
</br>
<div class="clear"></div>
</br>
</br>
 <sj:accordion id="accordion" autoHeight="false">
             <sj:accordionItem title="SMS Account >> For (Transactional Route)" id="oneId">  
<div class="clear"></div>
   <s:iterator value="transoorderList">
    <table width="100%" cellspacing="0" cellpadding="0"><tbody>
    <tr>
    <td class="cell"><strong>Sender Id :</strong></td> 
    <td class="cell"><strong><s:property value="clientid"></s:property></strong></td>
    </tr><tr><td class="cell"><strong>Total Credits:</strong></td> <td class="cell">
    <strong><s:property value="totalcredits"></s:property></strong></td>
    </tr><tr><td class="cell"><strong>Used Credits:</strong></td> 
    <td class="cell">
    <strong><s:property value="usedcredits"></s:property></strong></td>
    <td class="cell"><strong>Balance Credits:</strong></td>
    <td class="cell">
    <strong><s:property value="balancecredit"></s:property></strong></td>
    </tr><tr><td class="cell"><strong>Valid From</strong></td>
    <td class="cell">
    <strong><s:property value="validfrom"></s:property></strong></td> 
    <td class="cell"><strong>Valid To</strong>
    </td>
    <td class="cell">
    <strong><s:property value="validto"></s:property></strong></td> 
    </tr>
</tbody>
</table>
</s:iterator>
</br></sj:accordionItem>
<sj:accordionItem title="SMS Account >> For (Promo Route)" id="twoId">
<s:iterator value="promoorderList">
    <table width="100%" cellspacing="0" cellpadding="0"><tbody>
    <tr>
    <td class="cell"><strong>Sender Id :</strong></td> 
    <td class="cell"><strong><s:property value="clientid"></s:property></strong></td>
    </tr><tr><td class="cell"><strong>Total Credits:</strong></td> <td class="cell">
    <strong><s:property value="totalcredits"></s:property></strong></td>
    </tr><tr><td class="cell"><strong>Used Credits:</strong></td> 
    <td class="cell">
    <strong><s:property value="usedcredits"></s:property></strong></td>
    <td class="cell"><strong>Balance Credits:</strong></td>
    <td class="cell">
    <strong><s:property value="balancecredit"></s:property></strong></td>
    </tr><tr><td class="cell"><strong>Valid From</strong></td>
    <td class="cell">
    <strong><s:property value="validfrom"></s:property></strong></td> 
    <td class="cell"><strong>Valid To</strong>
    </td>
    <td class="cell">
    <strong><s:property value="validto"></s:property></strong></td> 
    </tr>
</tbody>
</table>
</s:iterator>
</sj:accordionItem>
<sj:accordionItem title="SMS Account >> For (Open  Route)" id="fiveId">
<s:iterator value="openoorderList">
    <table width="100%" cellspacing="0" cellpadding="0"><tbody>
    <tr>
    <td class="cell"><strong>Sender Id :</strong></td> 
    <td class="cell"><strong><s:property value="clientid"></s:property></strong></td>
    </tr><tr><td class="cell"><strong>Total Credits:</strong></td> <td class="cell">
    <strong><s:property value="totalcredits"></s:property></strong></td>
    </tr><tr><td class="cell"><strong>Used Credits:</strong></td> 
    <td class="cell">
    <strong><s:property value="usedcredits"></s:property></strong></td>
    <td class="cell"><strong>Balance Credits:</strong></td>
    <td class="cell">
    <strong><s:property value="balancecredit"></s:property></strong></td>
    </tr><tr><td class="cell"><strong>Valid From</strong></td>
    <td class="cell">
    <strong><s:property value="validfrom"></s:property></strong></td> 
    <td class="cell"><strong>Valid To</strong>
    </td>
    <td class="cell">
    <strong><s:property value="validto"></s:property></strong></td> 
    </tr>
</tbody>
</table>
</s:iterator>
</sj:accordionItem>
</sj:accordion>
 </div>

</body>
</html>
