<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reservation</title>
    </head>
    <body style="width: 800px; margin: 0 auto; text-align: center;">
        <h1>Reservation de <c:out value="${sessionScope.user_session.getLName()}" />  <c:out value="${sessionScope.user_session.getFName()}" /></h1>
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
        <c:choose>
            <c:when test="${not empty requestScope.booklist}">
                <table>
                    <thead>
                        <tr>
                            <th>ISBN</th>
                            <th>Title</th>
                            <th>Author</th>
                            <th>Created</th>
                            <th>Rendre</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="book" items="${requestScope.booklist}">
                            <tr>
                                <td><c:out value="${book.getIsbn()}" /></td>
                                <td><c:out value="${book.getTitle()}" /></td>
                                <td><c:out value="${book.getAuthor()}" /></td>
                                <td><c:out value="${book.getCreated()}" /></td>
                                <td>
                                    <form method="post">
                                        <input type="hidden" name="isbn" value="${book.getIsbn()}" />
                                        <input type="submit" value="Rendre" />
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table> 
            </c:when>
            <c:otherwise>
                <p>
                    Aucune réservation
                </p>
            </c:otherwise>
        </c:choose>
    </body>
</html>
