<%@ taglib prefix="s" uri="/struts-tags" %>
				<s:select 
                              id="domainIpName"
						      name="domainIpName" 
						      list="domainNamelist"
						      listKey="id"
						      listValue="domainname"
						      headerKey="-1"
						      headerValue="Select Ip/Domain Name" 
                              cssClass="form_menu_inputselect"
                              >
                 </s:select>
          