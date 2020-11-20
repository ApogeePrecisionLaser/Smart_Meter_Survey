/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.node.controller;

import com.connection.DBConnection.DBConnection;
import com.node.model.NodeOverHeadTankModel;
import com.node.tableClasses.PurposeHeaderNode;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Com7_2
 */
public class GetPipeNameController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 3, noOfRowsInTable;
        System.out.println("this is WaterTreatmentPlantController ....");
        ServletContext ctx = getServletContext();
        NodeOverHeadTankModel nodeovertank = new NodeOverHeadTankModel();


        nodeovertank.setDriverClass(ctx.getInitParameter("driverClass"));
        nodeovertank.setConnectionString(ctx.getInitParameter("connectionString"));
        nodeovertank.setDb_username(ctx.getInitParameter("db_username"));
        nodeovertank.setDb_password(ctx.getInitParameter("db_password"));
        try {
            nodeovertank.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (SQLException ex) {
        }
        System.out.println("Connection is - " + nodeovertank.getConnection());
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        String task = request.getParameter("task");
        if (task == null) {
            task = "";
        }
        try {
            String headername = request.getParameter("searchheader_name");
            String headerindex = request.getParameter("searchheader_index");
            if (headerindex == null || headerindex.isEmpty()) {
                headerindex = "";
            }
            if (headername == null || headername.isEmpty()) {
                headername = "";
            }
            List<PurposeHeaderNode> PurposeHeaderList = null;
            if (task.equals("header")) {

                PurposeHeaderList = nodeovertank.showData1(-1, -1, headername, headerindex);
            }
                       request.setAttribute("task", task);   
            request.setAttribute("headername", headername);
            request.setAttribute("headerindex", headerindex);
            request.setAttribute("PurposeHeaderList", PurposeHeaderList);
            request.getRequestDispatcher("getheader").forward(request, response);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(GetPipeNameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(GetPipeNameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
