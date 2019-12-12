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
            <form:form commandName="productrequest">
                <form:select id="goods" path="products"></form:select>
            </form:form>
            <h1>Available products are ${productrequest.products}</h1>
            <form:form>
                <input type="hidden" name="logOut" value="true">
                <input type="submit" value="Log out"/>
            </form:form>
         </form:form>
    </tr>
</table>
</body>
</html>
