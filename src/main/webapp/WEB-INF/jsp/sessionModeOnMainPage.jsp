<%--
  Created by IntelliJ IDEA.
  User: dkropotov
  Date: 12/10/19
  Time: 13:02
  To change this template use File | Settings | File Templates.
--%>


<!--%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%-->
<!--%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %-->
<!--%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %-->

<!--DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"-->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Add Minutes Page</title>

    <script type="text/javascript" src="jquery-1.8.3.js"></script>

    <script type="text/javascript">
        $(document).ready(
            function() {
                $.getJSON('http://localhost:8080/WEB_dkropotov2_war_exploded/activities.json', {<!--spring:url value="activities.json/>"--> <!--activities.json-->
                    ajax : 'true'
                }, function(data){
                    var html = '<option value="">--Please select one--</option>';
                    var len = data.length;
                    for (var i = 0; i < len; i++) {
                        html += '<option value="' + data[i].desc + '">'
                            + data[i].desc + '</option>';
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

<!--base version how it works-->
<form:form commandName="exercise">
<table>
    <tr>
        <td><form:input path="minutes"/></td>
        <td>
            <form:select id="products" path="activity"></form:select>
        </td>
    </tr>
    <tr>
        <td colspan="3">
            <input type="submit" value="Enter Exercise"/>
        </td>
    </tr>
</table>
</form:form>


<form:form commandName="productrequest">
   <form:errors path="*" cssClass="errorblock" element="div" />
</form:form>

<h4>Please fill in your cart</h4>


<!--form:form commandName="productrequest"-->
    <table>
     <tr>
     <tr>
        <h5>Please choose goods from the store</h5>
        <h2>Available products are ${productrequest.availableProducts}</h2>
        <tr>
        <form:form>
            <form:form commandName="productRequest">
            <table>
            <!--But here it doesn't work I don't understand why and everything breaks when clicking a button or creating new session-->
            <!--td>
                <!-form:select id="products" path="title"><!-/form:select>
            </td-->
            Good: <input type="text" name="productRequest.title"/></td><br>
            Amount: <input type="number" name="productRequest.amount" cssErrorClass="error"/></td><br>
            <!--<td><!-form:errors path="number" cssClass="error"/></td><br>-->
            <h5>${productrequest.answerForGoodRespond}</h5>
            <input type="submit" value="Submit choice"/>
            </table>
            </form:form>
        </form:form>
        </tr>
    </tr>
    </table>
<!--/form:form-->



<!--%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Final Project</title>


    <script type="text/javascript" src="jquery-1.8.3.js"></script>

    <script type="text/javascript">
        $(document).ready(
            function() {
                $.getJSON('<!-spring:url value="activities.json"/>', { spring:url value="activities.json/>"
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

<!-form:form commandName="productrequest">
    <!-form:errors path="*" cssClass="errorblock" element="div" />
<!-/form:form>

<h4>Please fill in your cart</h4>

<table>
    <tr>
    <tr>
        <h5>Please choose goods from the store</h5>
        <h2>Available products are $<{productrequest.availableProducts}</h2>
        <!-form:form>
            <!-form:form>
                <!-table>

                    <!-form:select type="text" id="products" path="productRequest.title"><!-/form:select><br>

                   <!-Good: <input type="text" name="productRequest.title"/></td><br>
                   Amount: <input type="number" name="productRequest.amount" cssErrorClass="error"/></td><br>
               <td><!-form:errors path="number" cssClass="error" /></td><br>
                   <!-h5>$<{productrequest.answerForGoodRespond}</h5>
                   <input type="submit" value="Submit choice"/>
                </table>
            <!-/form:form>
            <!-form:form>
                <table>
                    <input type="hidden" name="productRequest.displayContent" value="true"/></td>
                   <h1>$<{productrequest.cartContent}</h1>
                   <input type="submit" value="Show cart content"/>
                </table>
            <!-/form:form>



            <!-form:form>
               <table>
                  <input type="text" name="productRequest.itemToRemove"/></td><br>
                  <h1>$<{productrequest.removedSuccessfully}</h1>
                  <input type="submit" value="Remove item from cart"/>
               </table>
            <!-/form:form>
            <!-form:form>
                <table>
                   <input type="text" name="productRequest.itemToModify"/></td><br>
                   <input type="number" name="productRequest.newAmount" cssErrorClass="error"/></td><br>
                   <td><!-form:errors path="number" cssClass="error" /></td><br>
                   <h1>$<!-{productrequest.modificationResult}</h1>
                   <input type="submit" value="Modify cart item"/>
                </table>
            <!-/form:form>
            <!-form:form>
                <table>
                   <input type="hidden" name="productRequest.checkoutBooking" value="true"/></td>
                   <h1>$<!{productrequest.checkOutResult}</h1>
                   <input type="submit" value="Checkout booking"/>
                </table>
            <!-/form:form>
            <!-form:form>
                <input type="hidden" name="productRequest.logOut" value="true">
                <input type="submit" value="Log out"/>
            <!-/form:form>
         <!-/form:form>
    </tr>
</table>
</body>
</html>-->
