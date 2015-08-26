<%-- 
    Document   : TopicJSP
    Created on : 2015.08.26., 21:47:43
    Author     : TIMI
--%>

<%@page import="forummain.Topic"%>
<%@page import="forummain.Forum"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel='stylesheet' type='text/css' href='../style.css' />
        <%Forum forum = (Forum)request.getAttribute("forum");%>
        <%Topic topic = (Topic)request.getAttribute("topic");%>
        <%Integer id = (Integer)request.getAttribute("id");%>
        <title><%= topic.getSubject() %></title>
    </head>
    <body>
        <h1 align='center'><%= topic.getSubject() %> topik</h1>
        <table>
        <tr>
        <th>Sorszám</th>
        <th>Szerző</th>		
        <th>Hozzászólás</th>
        <th>Dátum</th>
        </tr>
        <% for(int i=topic.getPosts().size()-1; i>=0; i--) { %>
            <tr>
            <td><%= i %>.</td>
            <td><%= topic.getPosts().get(i).getAuthor() %></td>
            <td><%= topic.getPosts().get(i).getMessage() %></td>
            <td><%= topic.getPosts().get(i).getTime() %></td>
            </tr>
        <% } %>
        </table><p>
        <form method='post' action='<%= id %>' >
        Szerző: <input type='text' name='Author'>&nbsp&nbsp
        Hozzászólás: <input id='message' type='text' name='Message'><br>
        <p><input style='float:left;' type='submit' value='Elküld'>
        </form>

        <a class=btn style='float:right;' href='../ForumServlet'><button type='button'>Vissza a topikokhoz</button></a>
    </body>
</html>
