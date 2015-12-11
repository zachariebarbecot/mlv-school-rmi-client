﻿<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book</title>
    </head>
    <body style="width: 800px; margin: 0 auto; text-align: center;">
        <h1>Book</h1>
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
        <h1><c:out value="${requestScope.book.getTitle()}" /></h1>
        <h2><c:out value="${requestScope.book.getAuthor()}" /></h2>
        <h2><c:out value="${requestScope.book.getIsbn()}" /></h2>
        <p><c:out value="${requestScope.book.getCreated()}" /></p>
        <form method="post">
            <textarea name="comment"></textarea><br />
            <input type="hidden" name="isbn" value="${requestScope.book.getIsbn()}" />
            <input type="submit" value="Submit" />
        </form>
        <c:if test="${not empty requestScope.commentlist}">
            <p>
                Comments:<br />
                <c:forEach var="comment" items="${requestScope.commentlist}">
                <p>
                    <c:out value="${comment.getComment()}" />
                    <c:out value="${comment.getCreated()}" />
                </p>
            </c:forEach>
        </p>
    </c:if>
</body>
</html>
