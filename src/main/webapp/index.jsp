<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: dkropotov
  Date: 12/5/19
  Time: 18:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Final Project</title>
</head>
<body>
    <h4>Please choose an operation</h4>


       <tr>
           <form:form commandName="register">
               <td colspan="2">
                   <input type="submit" value="register a new user"/>
               </td>
           </form:form>
          <form:form commandName="login">
             <td colspan="2">
                <input type="submit" value="login to the system"/>
             </td>
          </form:form>
        </tr>
</body>
</html>
