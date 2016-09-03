<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
</head>
<body>
    <s:select 
          id="subCategory" 
          name="subCategory" 
          list="feedSubCategorylist"
          headerKey="-1"
          headerValue="Select Sub Category"
          cssClass="form_menu_inputselect"
          onchange="getFeedBreifViaAjax('subCategory');"
          />
    </body>
</html>