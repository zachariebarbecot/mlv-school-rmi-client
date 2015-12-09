<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Catalogue</title>
    </head>
    <body style="width: 800px; margin: 0 auto; text-align: center;">
        <h1>Catalogue</h1>
        <c:if test="${not empty requestScope.message}">
            <p>
                ${requestScope.message}
            </p>
        </c:if>
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
        <table>
            <thead>
                <tr>
                    <th>ISBN</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Created</th>
                    <th>Reserver</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="book" items="${requestScope.catalog}">
                    <tr>
                        <td><c:out value="${book.getIsbn()}" /></td>
                        <td><a href="<c:url value="/book?isbn=${book.getIsbn()}" />"><c:out value="${book.getTitle()}" /></a></td>
                        <td><c:out value="${book.getAuthor()}" /></td>
                        <td><c:out value="${book.getCreated()}" /></td>
                        <td>
                            <c:if test="${not empty sessionScope.user_session}">
                                <form method="post" action="reserver">
                                    <input type="hidden" name="isbn" value="${book.getIsbn()}" />
                                    <input type="submit" value="Reserver" />
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <h2>Ajouter livre</h2>
        <form method="post" action="addbook">
            <fieldset>
                <label for="isbn">ISBN: </label>
                <input id="isbn" type="number" name="isbn" />
                <label for="title">Title: </label>
                <input id="title" type="text" name="title" />
                <label for="author">Author: </label>
                <input id="author" type="text" name="author" />
                <input type="submit" value="Ajouter" />
            </fieldset>
        </form>
    </body>
</html>
