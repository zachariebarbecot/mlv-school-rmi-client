<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book</title>
    </head>
    <body>
        <h1><c:out value="${requestScope.book.getTitle()}" /></h1>
        <h2><c:out value="${requestScope.book.getAuthor()}" /></h2>
        <p><c:out value="${requestScope.book.getCreated()}" /></p>
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
