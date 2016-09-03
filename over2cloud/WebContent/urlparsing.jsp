
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.net.*"%>
<%
StringBuilder Result=new StringBuilder("");
String inputLine="";
try{
String url=request.getParameter("url").toString();
URL oracle = new URL(url);
BufferedReader in = new BufferedReader(
new InputStreamReader(oracle.openStream()));
while ((inputLine = in.readLine()) != null)
{
    //System.out.println(inputLine);
     Result.append(inputLine);
}
in.close();
}catch(Exception ex){}
%>
<%=Result.toString() %>
