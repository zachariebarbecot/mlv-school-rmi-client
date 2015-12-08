<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Log In</title>
    </head>
    <body style="width: 800px; margin: 0 auto; text-align: center;">
        <nav>
            <ul>
                <li><a href="<c:url value="/" />">Home</a></li>
                <c:choose>
                    <c:when test="${empty sessionScope.user_session}">
                        <li><a href="<c:url value="/login" />">Log In</a></li>
                        </c:when>
                        <c:otherwise>
                        <li><a href="<c:url value="/catalog" />">Catalogue</a></li>
                        <li><a href="<c:url value="/reservation" />">Mes réservations</a></li>
                        <li><a href="<c:url value="/logout" />">Log Out</a></li>
                        </c:otherwise>
                    </c:choose>
            </ul>
        </nav>
        <section>
            <c:if test="${not empty requestScope.message}">
                <p>
                    ${requestScope.message}
                </p>
            </c:if>
            <c:if test="${empty sessionScope.user_session}">
                <form method="post">
                    <fieldset>
                        <legend>LogIn</legend>
                        <label  for="lname">Last Name</label><br />
                        <input id="lname" type="text" name="lname" /><br />
                        <label  for="fname">First Name</label><br />
                        <input id="fname" type="text" name="fname" /><br />
                        <input name="action" type="submit" value="Log In" />
                    </fieldset>
                </form>
            </c:if>
        </section>
    </body>
</html>
