<%--
  Created by IntelliJ IDEA.
  User: dkropotov
  Date: 12/10/19
  Time: 13:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>Final Project</title>


    <script type="text/javascript" src="jquery-1.8.3.js"></script>

    <script type="text/javascript">
        $(document).ready(
            function() {
                $.getJSON('<spring:url value="activities.json"/>', {<!--spring:url value="activities.json/>"-->
                    ajax : 'true'
                }, function(data){
                    var html = '<option value="">--Please select product--</option>';
                    var len = data.length;
                    debugger;
                    for (var i = 0; i < len; i++) {
                        html += '<option value="' + data[i] + '">'
                            + data[i] + '</option>';
                    }
                    html += '</option>';
                    $('#products').html(html);
                });
            });
    </script>

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

<!--error block for version with a better validation-->
<form:form commandName="productrequest">
    <form:errors path="*" cssClass="errorblock" element="div" />
</form:form>

<h1>You have a set of available operations for work with your cart</h1>

<table>
    <tr>
    <tr>
        <h2>Please choose goods from the store</h2>
        <h4>Available products are ${productrequest.availableProducts}</h4>
        <form:form>
            <form:form>
                <table>
                   Good: <input type="text" name="productRequest.title"/></td><br>
                   Amount: <input type="number" name="productRequest.amount" cssErrorClass="error"/></td><br>
                   <!--error message for version with a better validation-->
                   <td><form:errors path="number" cssClass="error" /></td><br>
                   <h2>${productrequest.answerForGoodRespond}</h2>
                   <!--input type="submit" value="Submit choice"/-->
                   <a href="<c:url value="/addItem" />">Submit choice</a>
                </table>
            </form:form>
            <form:form>
                <table>
                    <input type="hidden" name="productRequest.displayContent" value="true"/></td>
                   <h2>${productrequest.cartContent}</h2>
                   <!--input type="submit" value="Show cart content"/-->
                   <a href="<c:url value="/displayCartContent" />">Show cart content</a>
                </table>
            </form:form>



            <form:form>
               <table>
                  <input type="text" name="productRequest.itemToRemove"/></td><br>
                  <h2>${productrequest.removedSuccessfully}</h2>
                  <!--input type="submit" value="Remove item from cart"/-->
                   <a href="<c:url value="/removeItem" />">Remove item from cart</a>
               </table>
            </form:form>
            <form:form>
                <table>
                   <input type="text" name="productRequest.itemToModify"/></td><br>
                   <input type="number" name="productRequest.newAmount" cssErrorClass="error"/></td><br>
                   <!--error message for version with a better validation-->
                   <td><form:errors path="number" cssClass="error" /></td><br>
                   <h2>${productrequest.modificationResult}</h2>
                   <!--input type="submit" value="Modify cart item"/-->
                   <a href="<c:url value="/modifyItem" />">Modify cart item</a>
                </table>
            </form:form>
            <form:form>
                <table>
                   <input type="hidden" name="productRequest.checkoutBooking" value="true"/></td>
                   <h2>${productrequest.checkOutResult}</h2>
                   <!--input type="submit" value="Checkout booking"/-->
                   <a href="<c:url value="/checkoutBooking" />">Checkout booking</a>
                </table>
            </form:form>
            <form:form>
                <input type="hidden" name="productRequest.logOut" value="true">
                <!--input type="submit" value="Log out"/-->
                <!--input type="submit" href="<!-c:url value="/logout" />"/-->
                <a href="<c:url value="/logout" />">Logout</a>
            </form:form>
         </form:form>
    </tr>
</table>
</body>
</html>
