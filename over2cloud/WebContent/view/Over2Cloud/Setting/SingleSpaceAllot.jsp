<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/js.js"/>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/master.js"></script>

<script type="text/javascript">
$.subscribe('complete', function(event,data)
    {
        setTimeout(function(){ $("#foldeffect").fadeIn(); }, 10);
        setTimeout(function(){ $("#foldeffect").fadeOut(); }, 4000);
    });


function ChecktheDomainName(DomainAddressDiv,isocountrycode)
{
	var conP = "<%=request.getContextPath()%>";
	var countrycode=$("#"+isocountrycode).val();
	 $.ajax({
				    type : "post",
				    url : conP+"/view/Over2Cloud/Setting/ajexforDomainSingleip.action?countryid="+countrycode+"",
				    success : function(domainIdData) {
			        $("#"+DomainAddressDiv).html(domainIdData);
				},
				   error: function() {
			            alert("error");
			        }
				 });
}






</script>
</head>
<body>
<s:form  id="AddAllSingleSpace_Url" action="addAllSingleSpace"  theme="css_xhtml">
	 <center>
	 <table bordercolor="red" border="0.5" align="center" width="600px">
	 	<tr>
			<td>
			<label style="font-weight: bolder;">
			<font color="black" size="2">Country:</font>
			</label>
		   </td>
		    <td align="center">
		    <s:select 
                              id="country"
						      name="country" 
						      list="countrylist"
						      listKey="iso_code"
						      listValue="contryName"
						      headerKey="-1"
						      headerValue="Country" 
                              cssClass="form_menu_inputselect"
                              onchange="ChecktheDomainName('DomainAddressDiv','country');"
                             ></s:select>
		</td>
		<td>
			<label style="font-weight: bolder;">
			<font color="black" size="2">Single Space Server:</font>
			</label>
		   </td>
		    <td align="center">
		    <div id="DomainAddressDiv">
			<s:select 
                   			  id="domainIpName"
                              name="domainIpName" 
                              list="#{'no List':'No List'}" 
                              headerKey="-1"
                              headerValue="Select Ip/Domain Name" 
                              cssClass="form_menu_inputselect"
		          />
		   </div>
		</td>
	    </tr>
	 </table>
	 </center>
	 <br>
	 
	 
	 <!-- New Table CSS modified by sandeep on 24-04-2013 -->
	 <center>
     <table width="100%" cellspacing="0" cellpadding="3">
     
     <tr><td bgcolor="#0e0e0e" class="tabular_head" valign="middle" colspan="7">Allot Space</td></tr>

	<tr><td bgcolor="#252525" style=" border-bottom:1px solid #e7e9e9; color:#ffffff;" valign="top" class="tabular_cont">
			<table cellspacing="0" cellpadding="0" width="100%"><tbody>
				<tr>
					<td valign="middle" width="2.5%">&nbsp;</td>
					<td valign="middle" width="2.5%"><s:checkbox  name="selectall" id="selectall" theme="simple"/></td>
					<td valign="middle" width="20%">Space Name</td>
					<td valign="middle" width="20%">Account Id</td>
					
				</tr>
			</tbody></table>
		</td>
	</tr>
				
<s:iterator value="gridmodel" id="ChunkAccountId" status="counter">  
<s:set name="id" value="%{uRL}" />
<s:if test="#counter.count%2 != 0">
	<tr><td bgcolor="#ffffff" style=" border-bottom:1px solid #e7e9e9; color:#181818;" valign="top" class="tabular_cont">
		<table cellspacing="0" cellpadding="0" width="100%"><tbody>
			<tr>
				<td valign="middle" width="2.5%" bgcolor="#252525" style=" color:#ffffff; text-align:center;"><s:property value="#counter.count" /></td>
				<td valign="middle" width="2.5%"><s:set name="id" value="%{combinekey}" /><input type="checkbox" checked="checked" style="width:20px;"  value="${id}" id="spaceAddress" name="spaceAddress"></td>
				<td valign="middle" width="20%"><s:property value="chunkspace" /></td>
				<td valign="middle" width="20%"><s:property value="accountid" /></td>
			</tr></tbody>
		</table>
	</td></tr>
</s:if><s:else>
<tr><td bgcolor="#e2e4e4" style=" border-bottom:1px solid #e7e9e9; color:#181818;" valign="top" class="tabular_cont">
		<table cellspacing="0" cellpadding="0" width="100%"><tbody>
			<tr>
				<td valign="middle" width="2.5%" bgcolor="#252525" style=" color:#ffffff; text-align:center;"><s:property value="#counter.count" /></td>
				<td valign="middle" width="2.5%"><s:set name="id" value="%{combinekey}" /><input type="checkbox" checked="checked" style="width:20px;"  value="${id}" id="spaceAddress" name="spaceAddress"></td>
				<td valign="middle" width="20%"><s:property value="chunkspace" /></td>
				<td valign="middle" width="20%"><s:property value="accountid" /></td>
			</tr></tbody>
		</table>
	</td></tr>
</s:else>
</s:iterator> 				
      </table>
      </center>
      <!-- Ends Here -->
      <center> 
      <img id="indicator" src="<s:url value="/images/ajax-loader.gif"/>" alt="Loading..."   style="display:none"/>
             <div class="type-button">
             <sj:submit 
                        targets="Result" 
                        clearForm="true"
                        value="Add All" 
                        href="%{AddAllSingleSpace_Url}"
                        effect="highlight"
                        effectOptions="{ color :'#222222'}"
                        effectDuration="5000"
                        button="true"
                        onCompleteTopics="complete"
                        indicator="indicator"
                        />
                       <sj:div id="foldeffect"  effect="fold">
                     <div id="Result"></div>
          			</sj:div>
              </div>         
           </center>
     </s:form>
</body>
</html>