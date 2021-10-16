<%--
  Created by IntelliJ IDEA.
  User: dinar
  Date: 14.10.2021
  Time: 11:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sign in</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/sign-in" method="post">
    <label>
        <input name="login" type="text"  placeholder="NAME"/>
    </label>
    <label>
        <input name="password" type="password" placeholder="PASSWORD"/>
    </label>
    <input type="submit">
</form>
</body>
</html>
