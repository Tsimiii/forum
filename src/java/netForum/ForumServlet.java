package netForum;

import forummain.Topic;
import forummain.Forum;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.* ;


@WebServlet(name = "ForumServlet", urlPatterns = {"/ForumServlet"})
public class ForumServlet extends HttpServlet {

    private Forum forum;
    public Connection conn = null;
    public Statement stmt = null;
    private boolean first;
    
    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
         forum = Forum.getInstance();
         first = true;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            forum.setName("Fórum");
            
            request.setAttribute("forum", forum);
            request.getRequestDispatcher("/WEB-INF/jsp/ForumJSP.jsp").forward(request, response);
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
        if(first) {
            loadFromDatabase();
            first = false;
        }
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
        if(request.getParameterMap().containsKey("subject") && request.getParameterMap().containsKey("author")) {
            String subject = request.getParameter("subject");
            String author = request.getParameter("author");
            forum.addNewTopic(subject, author);
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost/forumdb", "root", "");
                stmt = conn.createStatement();
                
                PreparedStatement pstmt = conn.prepareStatement("insert into topics (subject, author) values(?,?)");
                pstmt.setString(1, subject);
                pstmt.setString(2, author);
                pstmt.executeUpdate();
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

    private void loadFromDatabase() {
        try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost/forumdb", "root", "");
                stmt = conn.createStatement();
                
                ResultSet result = stmt.executeQuery("select * from topics");
               
         	while (result.next() ) {
                    forum.addNewTopic(result.getString("subject"), result.getString("author"));
                } 
                
                result = stmt.executeQuery("select * from posts");
                while (result.next() ) {
                    Topic t = getTopicById(result.getInt("topic_id"));
                    t.writePost(result.getString("author"), result.getString("message"));
                }
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
    
    private Topic getTopicById(int id) {
        for(Topic t : forum.getTopics()) {
            if(t.getId() == id) {
                return t;
            }
        }
        return null;
    }

}