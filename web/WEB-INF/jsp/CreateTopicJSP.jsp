<%-- 
    Document   : CreateTopicJSP
    Created on : 2015.08.26., 21:40:25
    Author     : TIMI
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Topik létrehozása<</title>
    </head>
    <body>
        <h2>Add meg a topik adatait!</h2>
        <form method='post' action ='ForumServlet' >
        <table border='0'>
        <tr><td valign='top'>A topik témája: </td>
        <td valign='top'><input type='text' name='subject' size='20'></td></tr>
        <tr><td valign='top'>A topik szerzője: </td>
        <td valign='top'><input type='text' name='author' size='20'></td></tr>
        <tr><td valign='top'><input type='submit' value='Topik létrehozása'></td></tr>
        </table></form>
    </body>
</html>
