<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%--
  Created by IntelliJ IDEA.
  User: dkropotov
  Date: 12/5/19
  Time: 18:24
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>Final Project</title>
</head>
<body>
    <h4>Please choose an operation</h4>

    <form action="/action_page.php">
        First name: <input type="text" name="FirstName" value="Mickey"><br>
        Last name: <input type="text" name="LastName" value="Mouse"><br>
        <input type="submit" value="Submit">
    </form>

    <table>
       <tr>
        <tr>
            <h5>Register a new user</h5>
            <form action="/registerUser.html">
                 Email: <input type="text" name="email"/></td><br>
                 Password: <input type="password" name="password"/></td><br>
                 Repeat password: <input type="password"/></td><br>
                <input type="submit" value="Submit user"/>
            </form>
        </tr>
        <tr>
            <h5>Login to the system</h5>
            <form commandName="user2">
                 Email: <input type="text" name="email"/></td><br>
                 Password: <input type="password" name="password"/></td><br>
                <input type="submit" value="Login user"/>
            </form>
        </tr>
    </table>
</body>
</html>
