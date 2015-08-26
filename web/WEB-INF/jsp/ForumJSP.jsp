<%-- 
    Document   : ForumJSP
    Created on : 2015.08.26., 20:52:09
    Author     : TIMI
--%>

<%@page import="forummain.Topic"%>
<%@page import="forummain.Forum"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel='stylesheet' type='text/css' href='style.css' />
        <title>Forum</title>
    </head>
    <body>
        <%Forum f = (Forum)request.getAttribute("forum");%>
        <h1 align='center'><%= f.getName()%></h1>
        
        <% if (f.getTopics().isEmpty()) {%>
            <p>Nincs megjelenítendő topik.</p>
        <% } else { %>
            <table>
            <tr>
            <th>Tárgy</th>
            <th>Szerző</th>		
            <th>Válaszok száma</th>
            <th>Utolsó hozzászólás dátuma</th>
            </tr>
            <% for(Topic t : f.getTopics()) { %>
                <tr>
                <td><a href='./TopicServlet/<%= t.getId() %>'> <%= t.getSubject()%> </a></td>
                <td> <%= t.getAuthor() %> </td>
                <td> <%= t.getReplies() %> </td>
                <td> <%= t.getLastPost() %> </td>
                </tr> 
            <% } %>
            </table>
        <% } %>
        <p><a href='./CreateTopic'><input type="button" name="btn1" value="Új topik létrehozása"></a>
    </body>
</html>
