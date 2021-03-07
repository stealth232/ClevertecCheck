<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Auth</title>
</head>
<body>
<c:if test="${user!=null}">
    <td>${"Hello again, lets enter your login and password!"}</td>
</c:if>
<tbody>
<form action="/auth" method="GET">
    <tr>
        <th>Enter Login</th><br/>
        <td><input type="text" name="login" placeholder="login"></td>
        <br/>
        <th>Enter Password</th><br/>
        <td><input type="text" name="password" placeholder="password"></td>
        <br/>
        <td><input type="submit" value="Enter"></td>
    </tr>
    ${pageContext.request.contextPath}
</form>
</tbody>
</body>
</html>
