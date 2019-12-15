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



    <script type="text/javascript">
	$(document).ready(
		function() {
		$.getJSON('<spring:url value="http://localhost:8080/WEB_dkropotov2_war_exploded/goods.html"/>', {
    ajax : 'true'
}, function(data){
    var html = '<option value="">--Please select one--</option>';
    var len = data.length;
    for (var i = 0; i < len; i++) {
        html += '<option value="' + data[i].desc + '">'
                + data[i].desc + '</option>';
    }
    html += '</option>';

    $('#activities').html(html);
});

});

</script>



</head>
<body>

<form:form commandName="productrequest">
</form:form>


<h4>Please fill in your cart</h4>

<table>
    <tr>
    <tr>
        <h5>Please choose goods from the store</h5>
        <h2>Available products are ${productrequest.availableProducts}</h2>
        <form:form>
            <form:form>
                <table>
                   <!-- default values for variables for logic
                   <input type="hidden" name="displayContent" value="false"/></td>
                   <input type="hidden" name="itemToRemove" value="null"/></td>
                   <input type="hidden" name="itemToModify" value="null"/></td>
                   <input type="hidden" name="newAmount" value="null"/></td>
                   <input type="hidden" name="checkoutBooking" value="false"/></td>
                   <input type="hidden" name="logOut" value="false"/></td>-->

                   Good: <input type="text" name="title"/></td><br>
                   Amount: <input type="number" name="amount"/></td><br>
                   <h5>${productrequest.answerForGoodRespond}</h5>
                   <input type="submit" value="Submit choice"/>
                </table>
            </form:form>
            <form:form>
                <table>
                   <!-- default values for variables for logic
                   <input type="hidden" name="title"/></td>
                   <input type="hidden" name="amount"/></td>
                   <input type="hidden" name="itemToRemove" value="null"/></td>
                   <input type="hidden" name="itemToModify" value="null"/></td>
                   <input type="hidden" name="newAmount" value="null"/></td>
                   <input type="hidden" name="checkoutBooking" value="false"/></td>
                   <input type="hidden" name="logOut" value="false"/></td>-->

                    <input type="hidden" name="displayContent" value="true"/></td>
                   <h1>${productrequest.cartContent}</h1>
                   <input type="submit" value="Show cart content"/>
                </table>
            </form:form>



            <form:form>
               <table>
                  <!-- default values for variables for logic
                  <input type="hidden" name="title" value="null"/></td>
                  <input type="hidden" name="amount" value="null"/></td>
                  <input type="hidden" name="displayContent" value="false"/></td>
                  <input type="hidden" name="itemToModify" value="null"/></td>
                  <input type="hidden" name="newAmount" value="false"/></td>
                  <input type="hidden" name="checkoutBooking" value="false"/></td>
                  <input type="hidden" name="logOut" value="false"/></td>-->

                  <input type="text" name="itemToRemove"/></td><br>
                  <h1>${productrequest.removedSuccessfully}</h1>
                  <input type="submit" value="Remove item from cart"/>
               </table>
            </form:form>
            <form:form>
                <table>
                   <!-- default values for variables for logic
                   <input type="hidden" name="title" value="null"/></td>
                   <input type="hidden" name="amount" value="null"/></td>
                   <input type="hidden" name="displayContent" value="false"/></td>
                   <input type="hidden" name="itemToRemove" value="null"/></td>
                   <input type="hidden" name="newAmount" value="null"/></td>
                   <input type="hidden" name="checkoutBooking" value="false"/></td>
                   <input type="hidden" name="logOut" value="false"/></td>-->

                   <input type="text" name="itemToModify"/></td><br>
                   <input type="number" name="newAmount"/></td><br>
                   <h1>${productrequest.modificationResult}</h1>
                   <input type="submit" value="Modify cart item"/>
                </table>
            </form:form>
            <form:form>
                <table>
                    <!-- default values for variables for logic
                   <input type="hidden" name="title" value="null"/></td>
                   <input type="hidden" name="amount" value="null"/></td>
                   <input type="hidden" name="displayContent" value="false"/></td>
                   <input type="hidden" name="itemToRemove" value="null"/></td>
                   <input type="hidden" name="itemToModify" value="null"/></td>
                   <input type="hidden" name="newAmount" value="null"/></td>
                   <input type="hidden" name="logOut" value="false"/></td>-->

                   <input type="hidden" name="checkoutBooking" value="true"/></td>
                   <h1>${productrequest.checkOutResult}</h1>
                   <input type="submit" value="Checkout booking"/>
                </table>
            </form:form>

            <form:form>
                <!-- default values for variables for logic
                <input type="hidden" name="title" value="null"/></td>
                <input type="hidden" name="amount" value="null"/></td>
                <input type="hidden" name="displayContent" value="false"/></td>
                <input type="hidden" name="itemToRemove" value="null"/></td>
                <input type="hidden" name="itemToModify" value="null"/></td>
                <input type="hidden" name="newAmount" value="null"/></td>
                <input type="hidden" name="checkoutBooking" value="false"/></td>-->

                <input type="hidden" name="logOut" value="true">
                <input type="submit" value="Log out"/>
            </form:form>
         </form:form>
    </tr>
</table>
</body>
</html>
