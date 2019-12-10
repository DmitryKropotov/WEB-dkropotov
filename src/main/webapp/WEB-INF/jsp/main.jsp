<%--
  Created by IntelliJ IDEA.
  User: dkropotov
  Date: 12/9/19
  Time: 14:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Final Project</title>

    <style>
    .error {
    color: #ff0000;
    }

    .errorblock {
    color: #000;
    background-color: #ffEEEE;
    border: 3px solid #ff0000;
    padding: 8px;
    margin: 16px;
    }
    </style>

</head>
<body>

<form:form commandName="mainPage">
</form:form>

<h4>Please choose an operation</h4>

<table>
    <tr>
    <tr>
    <form:errors path="*" cssClass="errorblock" element="div" />
        <h5>Register a new user</h5>
            <form:form>
            <table>
                Email: <input type="text" name="email"/></td><br>
                Password: <input type="password" name="password"/></td><br>
                Repeat password: <input type="password" name="passwordRepeater"/></td><br>
                <input type="submit" value="Submit user"/>
            </table>
            </form:form>
    </tr>
    <tr>
        <h5>Login to the system</h5>
         <form:form>
         <table>
             Email: <input type="text" name="email"/></td><br>
             Password: <input type="password" name="password"/></td><br>
             <input type="submit" value="Login user"/>
         </table>
         </form:form>
    </tr>
</table>
</body>
</html>
