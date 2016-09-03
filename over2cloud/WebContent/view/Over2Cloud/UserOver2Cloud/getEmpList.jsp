 <%@ taglib prefix="s" uri="/struts-tags" %>
 <s:select 
                              id="empName"
                              name="empName" 
                              list="empListData"
                              headerKey="-1"
                              headerValue="Select Emp Name" 
                              cssClass="textField"
                             cssStyle="width:143%;" 
                              onchange="getMobileNumber(this.value);"
                              >
                  </s:select>