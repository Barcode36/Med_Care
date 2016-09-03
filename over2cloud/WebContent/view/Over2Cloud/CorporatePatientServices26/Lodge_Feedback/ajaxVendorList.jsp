<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
</head>
<body>
    <s:select 
          id="assetVendor" 
          name="assetVendor" 
          list="vendorList"
          headerKey="-1"
		  headerValue="Select Vendor" 
          cssClass="select"
          cssStyle="width:82%"
          />
    </body>
</html>