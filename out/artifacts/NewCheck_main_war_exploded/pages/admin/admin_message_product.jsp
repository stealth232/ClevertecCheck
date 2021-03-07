<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Message</title>
</head>
<body>
${message}
<form method="LINK" action="/pages/admin/product_editor.jsp">
    <input type="submit" value="Continue">
</form>
<table>
    <thead>
    <tr>
        <th>id</th>
        <th>product</th>
        <th>cost</th>
        <th>stock</th>

    </tr>
    </thead>
    <tbody>
    <c:forEach var="product" items="${products}">
        <tr>
            <td>${product.itemId}</td>
            <td>${product.name}</td>
            <td>${product.cost}</td>
            <td>${product.stock}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
