<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
</head>
<body>
    <s:select 
          id="categoryId%{destination}" 
          name="categoryId" 
          list="feedCategorylist"
          headerKey="-1"
          headerValue="Select Category"
          cssClass="form_menu_inputselect"
          onchange="getFeedSubCategory(this.value,'subCategoryDiv%{destination}');"
          />
    </body>
</html>