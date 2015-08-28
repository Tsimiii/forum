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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "TopicServlet", urlPatterns = {"/TopicServlet/*"})
public class TopicServlet extends HttpServlet {
    
    private Forum forum;
    public int id;
    private Topic topic = null;
    public Connection conn = null;
    public Statement stmt = null;

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
            /* TODO output your page here. You may use following sample code. */
                forum=Forum.getInstance();
                topic = getTopicById(id);
        
        request.setAttribute("forum", forum);
        request.setAttribute("topic", topic);
        request.setAttribute("id", id);
        request.getRequestDispatcher("/WEB-INF/jsp/TopicJSP.jsp").forward(request, response);
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
            Post post = topic.writePost(author, message);
            
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost/forumdb", "root", "");
                stmt = conn.createStatement();
                
                PreparedStatement pstmt = conn.prepareStatement("insert into posts (author, topic_id, message, curr_time) values(?,?,?,?)");
                pstmt.setString(1, author);
                pstmt.setInt(2, id);
                pstmt.setString(3, message);
                pstmt.setLong(4, post.getTime());
                pstmt.executeUpdate();
                //conn.commit();
            }catch(ClassNotFoundException ex) {
                System.out.println("Error: unable to load driver class!");
                System.exit(1);
            }catch(SQLException se){
                se.printStackTrace();
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                try{
                    if(stmt!=null)
                    stmt.close();
                }catch(SQLException se2){}
                try{
                    if(conn!=null)
                        conn.close();
                }catch(SQLException se){
                       se.printStackTrace();
                }
            }
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
