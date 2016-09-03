<!--<html>
<head>
	<title>Capcha Validation</title>
<style>
    a:link{ color:red; text-decoration:underline; }
    a:visited{ color:red; text-decoration:underline; }
    a:hover{ color:green; text-decoration:none; }
    a:active{ color:red; text-decoration:underline; }
</style>
</head>
<body>
	<table>
		<tr><td><a href="captcha.jsp">Captcha Application</a></td></tr>
	</table>
</body>
</html>
-->
<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>

    <html>
      <body>
        <form action="ASD" method="post">
        <%
          ReCaptcha c = ReCaptchaFactory.newReCaptcha("6LcOQNsSAAAAAGYjfmKSo5xke7A-n9kXnfXXZVZG", "6LcOQNsSAAAAAA2Q7wy_7byyIAaMjqHmGyTD8I2q", false);
          out.print(c.createRecaptchaHtml(null, null));
        %>
        <input type="submit" value="submit" />
        </form>
      </body>
    </html>


