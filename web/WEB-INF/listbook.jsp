<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Liste des livres</title>
    </head>
    <body>
        <table>
            <thead>
                <tr>
                    <th>ISBN</th>
                    <th>Title</th>
                    <th>Author</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="book" items="${requestScope.list_book}">
                    <tr>
                        <td><c:out value="${book.getIsbn()}" /></td>
                        <td><a href="<c:url value="/book?isbn=${book.getIsbn()}" />"><c:out value="${book.getTitle()}" /></a></td>
                        <td><c:out value="${book.getAuthor()}" /></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>
