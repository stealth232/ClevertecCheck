<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Create Order</title>
</head>
<body>
<td>${String.format('%s %s %s %s',"Hello, ", user.getFirstName(), "! Your role is ", user.getUserType())}</td>
    <tbody>
    <form action="/shop" method="GET">
        <tr>
            <th>Snickers</th><br/>
            <td><input type="number" min="0"  value="1" name="itemId" placeholder="Snickers"></td>
            <td><input type="number" min="0" value="1" name="quantity" placeholder="quantity"></td>
            <br/>
            <th>Twix</th><br/>
            <td><input type="number" min="0"  value="2" name="itemId" placeholder="Twix"></td>
            <td><input type="number" min="0" value="1" name="quantity" placeholder="quantity"></td>
            <br/>
            <th>Mars</th><br/>
            <td><input type="number" min="0" value="3" name="itemId" placeholder="Mars"></td>
            <td><input type="number" min="0" value="1"  name="quantity" placeholder="quantity"></td>
            <br/>
            <th>KitKat</th><br/>
            <td><input type="number" min="0" value="4" name="itemId" placeholder="KitKat"></td>
            <td><input type="number" min="0" value="1" name="quantity" placeholder="quantity"></td>
            <br/>
            <th>Alonka</th><br/>
            <td><input type="number" min="0" value="5" name="itemId" placeholder="Alonka"></td>
            <td><input type="number" min="0" value="1" name="quantity" placeholder="quantity"></td>
            <br/>
            <th>Nuts</th><br/>
            <td><input type="number" min="0" value="6" name="itemId" placeholder="Nuts"></td>
            <td><input type="number" min="0" value="1" name="quantity" placeholder="quantity"></td>
            <br/>
            <th>Any product</th><br/>
            <td><input type="number" min="0" value="0" name="itemId" placeholder="Any"></td>
            <td><input type="number" min="0" value="0" name="quantity" placeholder="quantity"></td>
            <br/>
            <th>CARD NUMBER</th><br/>
            <td><input type="number" value="3578" name="card" placeholder="card"></td>
            <td><input type="submit" value="Create"></td>
        </tr>
    </form>
    </tbody>
    ${pageContext.request.contextPath}
</body>
</html>

