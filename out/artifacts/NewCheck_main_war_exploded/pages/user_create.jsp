<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Users</title>
</head>
<body>
    <tbody>
    <form action="/create" method="GET">
        <tr>
            <th>First Name</th><br/>
            <td><input type="text" name="firstName" placeholder="firstName"></td>
            <br/>
            <th>Second Name</th><br/>
            <td><input type="text" name="secondName" placeholder="secondName"></td>
            <br/>
            <th>Age</th><br/>
            <td><input type="number" name="age" placeholder="age"></td>
            <br/>
            <th>Login</th><br/>
            <td><input type="text" name="login" placeholder="login"></td>
            <br/>
            <th>Password</th><br/>
            <td><input type="text" name="password" placeholder="password"></td>
            <br/>
            <td><input type="submit" value="Create"></td>
        </tr>
        ${pageContext.request.contextPath}
    </form>
    </tbody>
</body>
</html>
