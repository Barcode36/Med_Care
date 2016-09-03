<%@ taglib prefix="s" uri="/struts-tags" %>
                  <s:select 
                              id="empName"
                              name="empName" 
                              list="empListData"
                              headerKey="-1"
                              headerValue="--Select Employee Name--" 
                              cssClass="form_menu_inputselect"
                              onchange="fillMob(this.value);"
                              >
                  </s:select>