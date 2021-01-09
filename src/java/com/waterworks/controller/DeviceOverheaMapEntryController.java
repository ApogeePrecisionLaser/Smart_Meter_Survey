package com.waterworks.controller;

import com.connection.DBConnection.DBConnection;
import com.util.UniqueIDGenerator;
import com.waterworks.model.DeviceOverheaMapEntryModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.waterworks.model.OHLevelModel;
import com.waterworks.tableClasses.OHLevel;
import com.waterworks.tableClasses.ohlevelmapbean;

/**
 *
 * @author Com7_2
 */
public class DeviceOverheaMapEntryController extends HttpServlet {
   //@Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NumberFormatException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable,device_junction_mapId;
        System.out.println("this is OHLevelController ....");
        ServletContext ctx = getServletContext();
        DeviceOverheaMapEntryModel ohLevelModel = new DeviceOverheaMapEntryModel();

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

        String searchDevice="";
        String searchOverHeadTank = "";
        String searchwaterTreatmentName = "";
        searchDevice = request.getParameter("searchDevice");
        searchOverHeadTank = request.getParameter("searchOverHeadTank");
        searchwaterTreatmentName = request.getParameter("searchwaterTreatmentName");
        
        try {
            if (searchDevice == null) {
                searchDevice = "";
            }
            if (searchOverHeadTank == null) {
                searchOverHeadTank = "";
            }
            if (searchwaterTreatmentName == null) {
                searchwaterTreatmentName = "";
            }
        } catch (Exception e) {
        }

        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getDevice")) {
                    list = ohLevelModel.getDevice(q);
                } else if (JQstring.equals("getOverHeadTank")) {
                    list = ohLevelModel.getOverHeadTank(q);
                }
                if (JQstring.equals("setCommand")) {
                    String ohlevel_id = request.getParameter("ohlevel_id");
                    String change_command_value = request.getParameter("change_command_value");
                    int junction_id= ohLevelModel.getOverHeadTankid(Integer.parseInt(ohlevel_id));
                    ctx.setAttribute((""+junction_id).trim(),change_command_value);
                    out.print("1");
                    return;
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                ohLevelModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --OHLevelController get JQuery Parameters Part-" + e);
        }


        if (task == null) {
            task = "";
        } else if (task.equals("Show All Records")) {
            searchDevice = "";
            searchOverHeadTank = "";
        } else if (task.equals("generateReport")) {
            searchOverHeadTank = request.getParameter("searchOverHeadTank");
            searchwaterTreatmentName = request.getParameter("searchwaterTreatmentName");
            List listAll = null;
            String jrxmlFilePath;
            response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            listAll = ohLevelModel.showAllData(searchDevice, searchOverHeadTank);
            jrxmlFilePath = ctx.getRealPath("/report/waterWorks/OverheadTankLevelReport.jrxml");
            byte[] reportInbytes = ohLevelModel.generateMapReport(jrxmlFilePath, listAll);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            return;
        }

        String device_junction_map_id = request.getParameter("device_junction_map_id");
                if (device_junction_map_id == null || device_junction_map_id.isEmpty()) {
                    device_junction_mapId = 0;
                } else {
                    device_junction_mapId = Integer.parseInt(device_junction_map_id);
                }
             
        if (task.equals("Delete")) {
            ohLevelModel.deleteRecord(device_junction_mapId);  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New") || task.equals("Update")) {
            
                
            int ohLevelId;
            try {
                ohLevelId = Integer.parseInt(request.getParameter("ohLevelId"));
                
            } catch (Exception e) {
                ohLevelId = 0;
            }
            if (task.equals("Save AS New")) {
                ohLevelId = 0;
            }
            ohlevelmapbean ohLevelBean = new ohlevelmapbean();
//            ohLevelBean.setOhLevelId(ohLevelId);
//            ohLevelBean.setOverHeadTankId(ohLevelModel.getOverHeadTankId(request.getParameter("overHeadTankName")));
//            String level_a = request.getParameter("level_a").trim();
//            String level_b = request.getParameter("level_b");
//            int b = (int) Integer.parseInt(level_b);
//            Byte byte1 = (byte) Integer.parseInt(level_a);
//            Byte byte2 = (byte) b;
//            ohLevelBean.setLevel_a(byte1);
//            ohLevelBean.setLevel_b(byte2);
//
//            String level_datetime = request.getParameter("level_datetime");
//            String level_time_hour = request.getParameter("level_time_hour");
//            String level_time_min = request.getParameter("level_time_min");
//
//            ohLevelBean.setLevel_datetime(level_datetime);
//            ohLevelBean.setHours(Integer.parseInt(level_time_hour));
//            ohLevelBean.setMinutes(Integer.parseInt(level_time_min));
//
//            request.setAttribute("level_datetime", level_datetime);
//            request.setAttribute("level_time_hour", level_time_hour);
//            request.setAttribute("level_time_min", level_time_min);
            ohLevelBean.setDevice_junction_map_id(device_junction_mapId);

            ohLevelBean.setDevice_id(request.getParameter("device_id"));
            ohLevelBean.setOverHeadTankName(request.getParameter("overheadtank_name"));
            ohLevelBean.setRemark(request.getParameter("remark"));
            //System.out.println("overheadtank_name--"+request.getParameter("overheadtank_name"));
            
            if (ohLevelId == 0) {
                System.out.println("Inserting values by OHLevelModel......");
                ohLevelModel.insertRecord(ohLevelBean);
            } else {
            
                System.out.println("Update values by OHLevelModel........");
                ohLevelModel.updateRecord(ohLevelBean);
            }
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

        noOfRowsInTable = ohLevelModel.getTotalRowsInTable(searchDevice, searchOverHeadTank);                  // get the number of records (rows) in the table.

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
        //System.out.println("searchDevice--"+searchDevice);
        //System.out.println("searchOverHeadTank--"+searchOverHeadTank);

        // Logic to show data in the table.
        List<ohlevelmapbean> ohLevelList = ohLevelModel.getDeviceOverheadMapAllRecords(lowerLimit, noOfRowsToDisplay, searchDevice, searchOverHeadTank,ctx);
        lowerLimit = lowerLimit + ohLevelList.size();
        noOfRowsTraversed = ohLevelList.size();
        
 //       System.out.println("list size in cont --" +ohLevelList.size());
 
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
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchOverHeadTank", searchOverHeadTank);
        request.setAttribute("searchDevice", searchDevice);
        request.setAttribute("message", ohLevelModel.getMessage());
        request.setAttribute("msgBgColor", ohLevelModel.getMessageBGColor());
        request.getRequestDispatcher("view/waterWorks/ohlevelmapEntry.jsp").forward(request, response);
        //response.sendRedirect("deviceOverHeadMap");
    }

//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        doGet(request, response);
//    }
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.err.println("do gettt");
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
        System.err.println("do postttt");
        processRequest(request, response);
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
}


