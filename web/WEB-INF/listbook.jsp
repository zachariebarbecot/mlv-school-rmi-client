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
                    <th>Reserver</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="book" items="${requestScope.list_book}">
                    <tr>
                        <td><c:out value="${book.getIsbn()}" /></td>
                        <td><a href="<c:url value="/book?isbn=${book.getIsbn()}" />"><c:out value="${book.getTitle()}" /></a></td>
                        <td><c:out value="${book.getAuthor()}" /></td>
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
