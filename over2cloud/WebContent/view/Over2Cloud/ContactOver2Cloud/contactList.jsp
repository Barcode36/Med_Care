<%@ taglib prefix="s" uri="/struts-tags" %>
                  <s:select 
                              id="mappedid"
                              name="mappedid"
                              list="contactListData"
                              headerKey="-1"
                              headerValue="--Select Contact Name--" 
                              cssClass="form_menu_inputselect"
                              onchange="getAllContAdressingdetails(this.value);"
                             
                              >
                  </s:select>