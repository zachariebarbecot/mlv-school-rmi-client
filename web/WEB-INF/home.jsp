<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
    </head>
    <body style="width: 800px; margin: 0 auto; text-align: center;">
        <h1>Home</h1>
        <c:if test="${not empty requestScope.message}">
            <p>
                ${requestScope.message}
            </p>
        </c:if>
        <nav>
            <ul style="display: inline-block; font-weight: bold;">
                <li style="display: inline-block"><a href="<c:url value="/" />">Home</a></li>
                    <c:choose>
                        <c:when test="${empty sessionScope.user_session}">
                        <li style="display: inline-block"><a href="<c:url value="/login" />">Log In</a></li>
                        </c:when>
                        <c:otherwise>
                        <li style="display: inline-block"><a href="<c:url value="/catalog" />">Catalogue</a></li>
                        <li style="display: inline-block"><a href="<c:url value="/reservation" />">Mes réservations</a></li>
                        <li style="display: inline-block"><a href="<c:url value="/logout" />">Log Out</a></li>
                        </c:otherwise>
                    </c:choose>
            </ul>
        </nav>
        <c:if test="${not empty sessionScope.user_session}">
            <section>
                <p>
                    Last name: <c:out value="${sessionScope.user_session.getLName()}" /><br />
                    First name: <c:out value="${sessionScope.user_session.getFName()}" /><br />
                    Status: <c:choose>
                        <c:when test="${sessionScope.user_session.getStatus()}">
                            <c:out value="Enseignant" />
                        </c:when>
                        <c:otherwise>
                            <c:out value="Etudiant" />
                        </c:otherwise>
                    </c:choose>
                </p>
            </section>     
        </c:if>

    </body>
</html>
