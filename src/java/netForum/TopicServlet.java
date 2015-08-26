/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netForum;

import forummain.Forum;
import forummain.Post;
import forummain.Topic;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "TopicServlet", urlPatterns = {"/TopicServlet/*"})
public class TopicServlet extends HttpServlet {
    
    private Forum forum;
    private int id;
    private Topic topic = null;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
                forum=Forum.getInstance();
                topic = getTopicById(id);
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<link rel='stylesheet' type='text/css' href='../style.css' />");
            out.println("<title>" + topic.getSubject() + " topik</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1 align='center'>" + topic.getSubject() + " topik</h1>");  
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Sorszám</th>");
            out.println("<th>Szerző</th>");		
            out.println("<th>Hozzászólás</th>");
            out.println("<th>Dátum</th>");
            out.println("</tr>");
            for(int i=topic.getPosts().size()-1; i>=0; i--) {
                out.println("<tr>");
                out.println("<td>" + i + ".</td>");
                out.println("<td>" + topic.getPosts().get(i).getAuthor() + "</td>");
                out.println("<td>" + topic.getPosts().get(i).getMessage() + "</td>");
                out.println("<td>" + topic.getPosts().get(i).getTime() + "</td>");
                out.println("</tr>");
            }
            out.println("</table><p>");
            out.println("<form method='post' action='" + id + "'>");
            out.print("Szerző: <input type='text' name='Author'>&nbsp&nbsp");
            out.print("Hozzászólás: <input id='message' type='text' name='Message'><br>");
            out.print("<p><input style='float:left;' type='submit' value='Elküld'>");
            out.print("</form>");
            
            out.println("<a class=btn style='float:right;' href='../ForumServlet'><button type='button'>Vissza a topikokhoz</button></a>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                String[] parts = request.getRequestURL().toString().split("/");
                id = Integer.parseInt(parts[parts.length-1]);
            processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if(request.getParameterMap().containsKey("Author") && request.getParameterMap().containsKey("Message")) {
            String author = request.getParameter("Author");
            String message = request.getParameter("Message");
            topic.writePost(author, message);
        }
        processRequest(request, response);
        //response.sendRedirect("/ForumWebApp/TopicServlet");
        /*ServletContext context= getServletContext();
        RequestDispatcher rd= context.getRequestDispatcher("/ForumWebApp/TopicServlet");
        rd.forward(request, response);*/
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private Topic getTopicById(int id) {
        for(Topic t : forum.getTopics()) {
            if(t.getId() == id) {
                return t;
            }
        }
        return null;
    }

}
