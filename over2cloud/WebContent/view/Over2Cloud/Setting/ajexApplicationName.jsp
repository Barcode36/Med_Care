<%@ taglib prefix="s" uri="/struts-tags" %>
				<s:select 
                              id="applicationName"
						      name="applicationName" 
						      list="apps"
						      listKey="appsCode"
						      listValue="applicationName"
						      headerKey="-1"
						      headerValue="Select Application Name" 
                              cssClass="form_menu_inputselect"
                              >
                 </s:select>
