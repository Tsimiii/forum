package netForum;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import forummain.*;

@WebServlet(name = "ForumServlet", urlPatterns = {"/ForumServlet"})
public class ForumServlet extends HttpServlet {

    private Forum forum;
    
    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
         forum = Forum.getInstance();
    }
    
    

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            
            forum.setName("Fórum");
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<link rel='stylesheet' type='text/css' href='style.css' />");
            out.println("<title>Servlet ForumServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1 align='center'>" + forum.getName() + "</h1>");
            
            if(forum.getTopics().isEmpty()) {
                out.println("<p>Nincs megjelenítendő topik.</p>");
            } else {
                out.println("<table>");
                out.println("<tr>");
                out.println("<th>Tárgy</th>");
                out.println("<th>Szerző</th>");		
                out.println("<th>Válaszok száma</th>");
                out.println("<th>Utolsó hozzászólás dátuma</th>");
                out.println("</tr>");
                for(Topic t : forum.getTopics()) {
                    out.println("<tr>");
                    //out.println("<td><a href='javascript:void(0)' onclick='goToTopic(\"" + t.getSubject() + "\")'>" + t.getSubject() + "</a></td>");
                    out.println("<td><a href='/ForumWebApp/TopicServlet/" + t.getId() + "'>" + t.getSubject() + "</a></td>");
                    out.println("<td>" + t.getAuthor() + "</td>");
                    out.println("<td>" + t.getReplies() + "</td>");
                    out.println("<td>" + t.getLastPost() + "</td>");
                    out.println("</tr>");                   
                }
                out.println("</table>");
            }
            out.println("<p><form method='post' action='CreateTopic'>");   
            out.println("<input type='submit' id='btn1' name='btn1' value='Új topik létrehozása'/>");
            out.println("</form>");
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
        if(request.getParameterMap().containsKey("subject") && request.getParameterMap().containsKey("author")
                && request.getParameterMap().containsKey("message")) {
            String subject = request.getParameter("subject");
            String author = request.getParameter("author");
            String message = request.getParameter("message");
            forum.addNewTopic(subject, author, message);
        }
        processRequest(request, response);
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

}