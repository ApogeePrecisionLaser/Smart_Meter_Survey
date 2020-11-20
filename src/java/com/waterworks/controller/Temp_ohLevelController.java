/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.waterworks.controller;

import com.connection.DBConnection.DBConnection;
import com.general.model.GeneralModel;
import com.util.UniqueIDGenerator;
import com.waterworks.model.Temp_ohLevelModel;
import com.waterworks.tableClasses.Temp_ohLevel;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Shobha
 */
public class Temp_ohLevelController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 15, noOfRowsInTable;
        System.out.println("this is OHLevelController ....");
        ServletContext ctx = getServletContext();
        Temp_ohLevelModel ohLevelModel = new Temp_ohLevelModel();

        ohLevelModel.setDriver(ctx.getInitParameter("driverClass"));
        ohLevelModel.setUrl(ctx.getInitParameter("connectionString"));
        ohLevelModel.setUser(ctx.getInitParameter("db_username"));
        ohLevelModel.setPassword(ctx.getInitParameter("db_password"));
        try {
            ohLevelModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (SQLException ex) {
            Logger.getLogger(OHLevelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("Connection is - " + ohLevelModel.getConnection());
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");

        String task = request.getParameter("task");

        String searchOverHeadTank = "";
        String searchWaterTreatmentPlant = "";
        String overheadtank_id = "";
        String from_hr = "";
        String from_min = "";
        String to_hr = "";
        String to_min = "";
        searchOverHeadTank = request.getParameter("searchOverHeadTank");
        searchWaterTreatmentPlant = request.getParameter("searchWaterTreatmentPlant");
        overheadtank_id = request.getParameter("overheadtank_id");
        String searchDateFrom = "", searchDateTo = "";
        searchDateFrom = request.getParameter("searchDateFrom");
        from_hr = request.getParameter("from_hr");
        from_min = request.getParameter("from_min");
        searchDateTo = request.getParameter("searchDateTo");
        to_hr = request.getParameter("to_hr");
        to_min = request.getParameter("to_min");

        try {
            if (searchOverHeadTank == null) {
                searchOverHeadTank = "";
            }
            if (searchWaterTreatmentPlant == null) {
                searchWaterTreatmentPlant = "";
            }
            if (overheadtank_id == null) {
                overheadtank_id = "";
            }
            if (searchDateFrom == null) {
                searchDateFrom = "";
            }
            if (searchDateTo == null) {
                searchDateTo = "";
            }
            if (from_hr == null) {
                from_hr = "";
            }
            if (from_min == null) {
                from_min = "";
            }
            if (to_hr == null) {
                to_hr = "";
            }
            if (to_min == null) {
                to_min = "";
            }
        } catch (Exception e) {
        }

        if (task == null) {
            task = "";
        }
        if (task.equals("generateReport")) {
            String jrxmlFilePath;
            List list = null;
            response.setContentType("application/pdf");
            response.setCharacterEncoding("UTF-8");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            jrxmlFilePath = ctx.getRealPath("/report/waterWorks/TankLevelHistory.jrxml");
            list = ohLevelModel.getAllRecords(-1, -1, searchWaterTreatmentPlant, searchOverHeadTank, overheadtank_id, searchDateFrom, searchDateTo,from_hr,from_min,to_hr,to_min);
            byte[] reportInbytes = GeneralModel.generateRecordList(jrxmlFilePath, list);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            return;
        }




        //    if (request.getAttribute("isSelectPriv").equals("Y")) {
        try {
            lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
            noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
        } catch (Exception e) {
            lowerLimit = noOfRowsTraversed = 0;
        }
        String buttonAction = request.getParameter("buttonAction"); // Holds the name of any of the four buttons: First, Previous, Next, Delete.
        if (buttonAction == null) {
            buttonAction = "none";
        }

        noOfRowsInTable = ohLevelModel.getTotalRowsInTable(searchWaterTreatmentPlant, searchOverHeadTank, overheadtank_id, searchDateFrom, searchDateTo,from_hr,from_min,to_hr,to_min);                  // get the number of records (rows) in the table.

        if (buttonAction.equals("Next")); // lowerLimit already has value such that it shows forward records, so do nothing here.
        else if (buttonAction.equals("Previous")) {
            int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
            if (temp < 0) {
                noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                lowerLimit = 0;
            } else {
                lowerLimit = temp;
            }
        } else if (buttonAction.equals("First")) {
            lowerLimit = 0;
        } else if (buttonAction.equals("Last")) {
            lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
            if (lowerLimit < 0) {
                lowerLimit = 0;
            }
        }

        if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New") || task.equals("Update")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        }
        if (task.equals("Show All Records")) {
            searchDateFrom = "";
            searchDateTo = "";
            searchWaterTreatmentPlant = "";
            searchOverHeadTank = "";
        }
        // Logic to show data in the table.
        List<Temp_ohLevel> ohLevelList = ohLevelModel.getAllRecords(lowerLimit, noOfRowsToDisplay, searchWaterTreatmentPlant, searchOverHeadTank, overheadtank_id, searchDateFrom, searchDateTo,from_hr,from_min,to_hr,to_min);
        lowerLimit = lowerLimit + ohLevelList.size();
        noOfRowsTraversed = ohLevelList.size();

        // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("ohLevelList", ohLevelList);

        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
        //   }
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchOverHeadTank", searchOverHeadTank);
        request.setAttribute("searchWaterTreatmentPlant", searchWaterTreatmentPlant);
        request.setAttribute("searchDateFrom", searchDateFrom);
        request.setAttribute("searchDateTo", searchDateTo);
        request.setAttribute("from_hr", from_hr);
        request.setAttribute("from_min", from_min);
        request.setAttribute("to_hr", to_hr);
        request.setAttribute("to_min", to_min);
        request.setAttribute("overheadtank_id", overheadtank_id);
        request.setAttribute("message", ohLevelModel.getMessage());
        request.setAttribute("msgBgColor", ohLevelModel.getMessageBGColor());
        request.getRequestDispatcher("temp_ohLevelHistoryView").forward(request, response);

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
        processRequest(request, response);
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
        processRequest(request, response);
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
