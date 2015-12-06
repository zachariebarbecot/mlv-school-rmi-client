<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
    </head>
    <body>
        <h1>Home</h1>
        <nav>
            <c:if test="${not empty requestScope.message}">
                <p>
                    ${requestScope.message}
                </p>
            </c:if>
            <a href="<c:url value="/listbook" />">Liste des livres</a> 
            <c:choose>
                <c:when test="${empty sessionScope.user_session}">
                    <a href="<c:url value="/login" />">Log In</a> 
                </c:when>
                <c:otherwise>
                    <a href="<c:url value="/reservation" />">Reservation</a> 
                    <a href="<c:url value="/logout" />">Log Out</a> 
                </c:otherwise>
            </c:choose>
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
