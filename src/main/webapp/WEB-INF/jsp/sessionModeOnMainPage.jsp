<%--
  Created by IntelliJ IDEA.
  User: dkropotov
  Date: 12/10/19
  Time: 13:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Final Project</title>
</head>
<body>

<form:form commandName="sessionModeOn">
</form:form>


<h4>Please fill in your cart</h4>

<table>
    <tr>
    <tr>
        <h5>Please choose goods from the store</h5>
        <form:form>
            <table>
                Good: <input type="text" name="title"/></td><br>
                Amount: <input type="number" name="amount"/></td><br>
                <input type="submit" value="Submit choice"/>
            </table>
        </form:form>
    </tr>
</table>
</body>
</html>
