/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.node.controller;

import com.connection.DBConnection.DBConnection;
import com.node.model.NodeConnectionDetailModel;
import com.node.tableClasses.NodeConnectionDetail;
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
 * @author Navitus1
 */
public class NodeConnectiondetailController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 3, noOfRowsInTable;
        System.out.println("this is WaterTreatmentPlantController ....");
        ServletContext ctx = getServletContext();
        NodeConnectionDetailModel nodeovertank = new NodeConnectionDetailModel();


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
        String search_node_name = "", search_meter_no = "";
        search_node_name = request.getParameter("search_node_name");
        if (search_node_name == null) {
            search_node_name = "";
        }
        search_meter_no = request.getParameter("search_meter_no");
        if (search_meter_no == null) {
            search_meter_no = "";
        }
        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getTankName")) {
                    list = nodeovertank.getTankName(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                nodeovertank.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --OverHeadTankController get JQuery Parameters Part-" + e);
        }
 
        try {

            if (task.equals("Delete")) {
                nodeovertank.deleteRecord(Integer.parseInt(request.getParameter("node_overheadtank_mapping_id")));  // Pretty sure that organisation_type_id will be available.
            } else if (task.equals("Save") || task.equals("Save AS New") || task.equals("Update")) {
                int node_overheadtank_mapping_id;
                try {
                    node_overheadtank_mapping_id = Integer.parseInt(request.getParameter("node_overheadtank_mapping_id"));
                } catch (Exception e) {
                    node_overheadtank_mapping_id = 0;
                }
                if (task.equals("Save AS New")) {
                    node_overheadtank_mapping_id = 0;
                }
                String node_name = request.getParameter("node_name");
                String meter_no = request.getParameter("meter_no");
                String remark = request.getParameter("remark");
                NodeConnectionDetail nh = new NodeConnectionDetail();
                nh.setNode_overheadtank_mapping_id(node_overheadtank_mapping_id);
                nh.setNode_name(node_name);
                nh.setMeter_no(meter_no);
                nh.setRemark(remark);
                if (node_overheadtank_mapping_id == 0) {
                    System.out.println("Inserting values by ......");
                    nodeovertank.insertRecord(nh);
                } else {
                    System.out.println("Update values by ........");
                    nodeovertank.updateRecord(nh);
                }

            }
            if (task.equals("Show All Records")) {
                search_node_name = "";
                search_meter_no = "";
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

            noOfRowsInTable = nodeovertank.getTotalRowsInTable(search_node_name, search_meter_no);                  // get the number of records (rows) in the table.

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
            // Logic to show data in the table.
            List<NodeConnectionDetail> list = nodeovertank.showAllData(lowerLimit, noOfRowsToDisplay, search_node_name, search_meter_no);
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
            request.setAttribute("search_node_name", search_node_name);
            request.setAttribute("search_meter_no", search_meter_no);
            request.getRequestDispatcher("node_connection").forward(request, response);
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
