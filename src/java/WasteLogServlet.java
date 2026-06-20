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
@WebServlet(urlPatterns = {"/WasteLogServlet"})
public class WasteLogServlet extends HttpServlet {

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
            out.println("<title>Servlet WasteLogServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet WasteLogServlet at " + request.getContextPath() + "</h1>");
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

        // TODO: 1. Connect to Database
        // TODO: 2. Fetch list of Waste Logs using a Java Bean (Model)
        // TODO: 3. request.setAttribute("wasteLogs", list);
        // Forward the data to the View (JSP)
        request.getRequestDispatcher("waste-log.jsp").forward(request, response);
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

        switch (action) {
            case "create":
                String material = request.getParameter("materialType");
                double weight = Double.parseDouble(request.getParameter("weight"));
                // TODO: Save to Database
                System.out.println("Created: " + material + " - " + weight + "kg");
                break;

            case "update":
                // TODO: Update logic
                break;

            case "delete":
                String logId = request.getParameter("logId");
                // TODO: Delete from Database
                System.out.println("Deleted log ID: " + logId);
                break;
        }

        // After completing the action, redirect back to doGet to refresh the table
        response.sendRedirect("WasteLogServlet");

    }
}
