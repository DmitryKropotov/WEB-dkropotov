<%--
  Created by IntelliJ IDEA.
  User: dkropotov
  Date: 12/9/19
  Time: 14:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

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

<form:form commandName="userchecker">
    <form:errors path="*" cssClass="errorblock" element="div" />
</form:form>

<h1>You have two operations: register a new user and login to the system</h1>

<table>
    <tr>
        <h2>Register a new user</h2>
            <form:form commandName="userchecker">
            <table>
                <h2>${userchecker.successfulRegMessage}</h2>
                <!--tr-->
                   Email: <input type="email" name="email"/>
                   <!--form:errors path="email" cssClass="error"/>
                </tr-->
                </br>

                <!--tr-->
                   Password: <input type="password" name="password"/>
                   <!--form:errors path="password" cssClass="error"/>
                </tr-->
                </br>

                <!--tr-->
                   Repeat password: <input type="password" name="passwordRepeater"/>
                   <!--form:errors path="passwordRepeater" cssClass="error"/>
                </tr-->
                </br>

                <h2>${userchecker.passwordError}</h2>
                <h2>${userchecker.userAlreadyExists}</h2>
                <input type="submit" value="Submit user"/>
            </table>
            </form:form>
    </tr>

    <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
        <font color="red">
            Your login attempt was not successful due to <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
        </font>
    </c:if>
    <form name="loginForm" action="authenticateUser" method="post">
        User-name<input type="text" name="username" /><br />
        Password <input type="password" name="password" />
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <input type="submit" value="Submit">
    </form>
</table>
</body>
</html>
