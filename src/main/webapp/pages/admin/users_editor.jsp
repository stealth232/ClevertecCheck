<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Users DB editor</title>
</head>
<body>
<tbody>
<form action="/admin_user" method="GET">
    <tr>
        <th>Delete user by login</th><br/>
        <td><input type="text" name="login" placeholder="login"></td>
        <br/>
        <td><input type="submit" value="Delete"></td>
    </tr>
    ${pageContext.request.contextPath}
</form>
<form action="/admin_user" method="GET">
    <tr>
        <th>Change role by login</th><br/>
        <td><input type="text" name="logintochange" placeholder="login"></td>
        <br/>
        <td><input type="text" name="role" placeholder="admin"></td>
        <br/>
        <td><input type="submit" value="New Role"></td>
    </tr>
    ${pageContext.request.contextPath}
</form>
<form action="/admin_user" method="GET">
    <tr>
        <th>Get all users</th><br/>
        <td><input type="submit" value="Show"></td>
    </tr>
    ${pageContext.request.contextPath}
</form>
</tbody>
</body>
</html>
