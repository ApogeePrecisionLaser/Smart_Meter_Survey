/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.node.controller;

import com.connection.DBConnection.DBConnection;
import com.node.model.NodeOverHeadTankModel;
import com.node.model.OverHead_WaterPlantModel;
import com.node.tableClasses.OverHead_WaterPlant;
import com.node.tableClasses.PurposeHeaderNode;
import com.util.UniqueIDGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Shubham
 */
public class OverHead_WaterPlantController extends HttpServlet
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 3, noOfRowsInTable;
        System.out.println("this is WaterTreatmentPlantController ....");
        ServletContext ctx = getServletContext();
        OverHead_WaterPlantModel overtankPipe = new OverHead_WaterPlantModel();
        overtankPipe.setDriverClass(ctx.getInitParameter("driverClass"));
        overtankPipe.setConnectionString(ctx.getInitParameter("connectionString"));
        overtankPipe.setDb_username(ctx.getInitParameter("db_username"));
        overtankPipe.setDb_password(ctx.getInitParameter("db_password"));
        try
        {
            overtankPipe.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        System.out.println("Connection is - " + overtankPipe.getConnection());
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        String task = request.getParameter("task");
        if (task == null) {
            task = "";
        }
        String search_pipe_detail = "", search_overheadtank_name = "";
        search_pipe_detail = request.getParameter("search_pipe_detail");
        if (search_pipe_detail == null)
        {
            search_pipe_detail = "";
        }
        
        search_overheadtank_name = request.getParameter("search_overheadtank_name");
        if (search_overheadtank_name == null)
        {
            search_overheadtank_name = "";
        }
        try
        {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getTankName")) {
                    list = overtankPipe.getTankName(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                overtankPipe.closeConnection();
                return;
            }
        } catch (Exception e)
        {
            System.out.println("\n Error --OverHeadTankController get JQuery Parameters Part-" + e);
        }
        try {
            if (task.equals("Delete")) {
                overtankPipe.deleteRecord(Integer.parseInt(request.getParameter("overHead_WaterPlant_id")));  // Pretty sure that organisation_type_id will be available.
            } else if (task.equals("Save") || task.equals("Save AS New") || task.equals("Update")) {
                int overHead_WaterPlant_id;
                try {
                    overHead_WaterPlant_id = Integer.parseInt(request.getParameter("overHead_WaterPlant_id"));
                } catch (Exception e) {
                    overHead_WaterPlant_id = 0;
                }
                if (task.equals("Save AS New")) {
                    overHead_WaterPlant_id = 0;
                }
                String watertreatmentplant = request.getParameter("watertreatmentplant");
                String overheadtank_name = request.getParameter("overheadtank_name");
                String remark = request.getParameter("remark");
                OverHead_WaterPlant nh = new OverHead_WaterPlant();
                nh.setOverHead_WaterPlant_id(overHead_WaterPlant_id);
                nh.setWatertreatmentplant(watertreatmentplant);
                nh.setOverheadtank_name(overheadtank_name);
                nh.setRemark(remark);
                if (overHead_WaterPlant_id == 0) {
                    System.out.println("Inserting values by ......");
                    overtankPipe.insertRecord(nh);
                } else {
                    System.out.println("Update values by ........");
                    overtankPipe.updateRecord(nh);
                }

            }
            if (task.equals("Show All Records")) {
                search_pipe_detail = "";
                search_overheadtank_name = "";
            }
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
            noOfRowsInTable = overtankPipe.getTotalRowsInTable(search_pipe_detail, search_overheadtank_name);                  // get the number of records (rows) in the table.

            if (buttonAction.equals("Next")); // lowerLimit already has value such that it shows forward records, so do nothing here.
            else if (buttonAction.equals("Previous"))
            {
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
            // Logic to show data in the table.
            List<OverHead_WaterPlant> list = overtankPipe.showAllData(lowerLimit, noOfRowsToDisplay, search_pipe_detail, search_overheadtank_name);
            lowerLimit = lowerLimit + list.size();
            noOfRowsTraversed = list.size();

            // Now set request scoped attributes, and then forward the request to view.
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("list", list);

            if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }
            //  }
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            request.setAttribute("search_pipe_detail", search_pipe_detail);
            request.setAttribute("search_overheadtank_name", search_overheadtank_name);
            request.getRequestDispatcher("pipe").forward(request, response);
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
