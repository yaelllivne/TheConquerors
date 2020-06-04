<%--
    Document   : index
    Created on : Jan 24, 2012, 6:01:31 AM
    Author     : blecherl
    This is the login JSP for the online chat application
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@page import="utils.*" %>
    <%@ page import="constants.Constants" %>
    <head>
        <meta charset="UTF-8">
        <title>Login page</title>
        <link rel="stylesheet" type="text/css" href="css/LoginCss.css">
    </head>
    <body class="mainBody">
    <div class="mainDiv">
        <img src="image/headline.jpg">
        <br/>
        <% String usernameFromSession = SessionUtils.getUsername(request);%>
        <% String usernameFromParameter = request.getParameter(Constants.USERNAME) != null ? request.getParameter(Constants.USERNAME) : "";%>
        <% if (usernameFromSession == null) {%>
        <form method="GET" action="Login">
            <div>
                <span>Please insert your name:</span>
                <input name="username" type="text" class="UserNameInput"type="text" name=" value="<%=usernameFromParameter%>"/>
            </div>
            <br/>
            <input type="submit" class="buttonLogin" value="Login"/>
        </form>
        <% Object errorMessage = request.getAttribute(Constants.USER_NAME_ERROR);%>
        <% if (errorMessage != null) {%>
        <span class="bg-danger" style="color:red;"><%=errorMessage%></span>
        <% } }%>
    </div>
    </body>
</html>