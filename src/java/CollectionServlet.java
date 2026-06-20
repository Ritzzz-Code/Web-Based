/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author adamr
 */
@WebServlet(urlPatterns = {"/CollectionServlet"})
public class CollectionServlet extends HttpServlet {

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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CollectionServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CollectionServlet at " + request.getContextPath() + "</h1>");
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
        
        // TODO: Query Database (Model) for user's collection requests
        // TODO: request.setAttribute("collections", collectionList);
        
        // Forward to the View
        request.getRequestDispatcher("collection.jsp").forward(request, response);;
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
        
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "create":
                    String date = request.getParameter("pickupDate");
                    String time = request.getParameter("timeSlot");
                    String load = request.getParameter("loadSize");
                    
                    // TODO: Insert into Database (Status defaults to 'Pending')
                    System.out.println("Collection Scheduled: " + date + " | " + time);
                    break;
                    
                case "update":
                    String reqIdUpdate = request.getParameter("requestId");
                    String newDate = request.getParameter("pickupDate");
                    
                    // TODO: Update Database record where ID = reqIdUpdate
                    System.out.println("Collection Rescheduled: ID " + reqIdUpdate);
                    break;
                    
                case "delete":
                    String reqIdDelete = request.getParameter("requestId");
                    
                    // TODO: Delete from Database where ID = reqIdDelete
                    System.out.println("Collection Canceled: ID " + reqIdDelete);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // In a real app, set an error message here to display on the JSP
        }

        // Redirect back to doGet to refresh the UI with new data
        response.sendRedirect("CollectionServlet");
    }

}
