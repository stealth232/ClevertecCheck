
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Product DB editor</title>
</head>
<body>
<tbody>
<form action="/admin_product" method="GET">
    <tr>
        <th>Update stock by ID</th><br/>
        <td><input type="number" min="0"value="0" name="id" placeholder="id"></td>
        <br/>
        <td><input type="text" value="true" name="stock" placeholder="stock"></td>
        <br/>
        <td><input type="submit" value="Update stock"></td>
    </tr>
    ${pageContext.request.contextPath}
</form>
<form action="/admin_delproduct" method="GET">
    <tr>
        <th>Delete by ID</th><br/>
        <td><input type="number" min="0" value="1" name="id" placeholder="id"></td>
        <br/>
        <td><input type="submit" value="Delete product"></td>
    </tr>
    ${pageContext.request.contextPath}
</form>
<form action="/admin_addproduct" method="GET">
    <tr>
        <th>Add new product</th><br/>
        <td><input type="text"  name="name" placeholder="name"></td>
        <br/>
        <td><input type="number" step="0.01" min="0" value="1" name="cost" placeholder="cost"></td>
        <br/>
        <td><input type="text" value="true" name="stock" placeholder="stock"></td>
        <br/>
        <td><input type="submit" value="Add product"></td>
    </tr>
    ${pageContext.request.contextPath}
</form>
<form action="/admin_addproduct" method="GET">
    <tr>
        <th>Get all products</th><br/>
        <td><input type="submit" value="Show"></td>
    </tr>
    ${pageContext.request.contextPath}
</form>
</tbody>
</body>
</html>
