package com.waterworks.controller;


import com.connection.DBConnection.DBConnection;
import com.util.UniqueIDGenerator;
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

import com.waterworks.model.RawWaterLevelModel;

import com.waterworks.tableClasses.RawWaterLevel;

public class RawWaterLevelController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 3, noOfRowsInTable;
        System.out.println("this is OHLevelController ....");
        ServletContext ctx = getServletContext();
        RawWaterLevelModel rawWaterLevelModel = new RawWaterLevelModel();

//          HttpSession session = request.getSession(false);
//       if (session == null || (String) session.getAttribute("user_name")==null ) {
//            response.sendRedirect("beforeLoginPage");             return;
//        }
//        try {
//            rawWaterLevelModel.setConnection(DBConnection.getConnection(ctx, session));
//        } catch (Exception e) {
//            System.out.println("error in PaymentStatusController setConnection() calling try block" + e);
//        }
        rawWaterLevelModel.setDriver(ctx.getInitParameter("driverClass"));
        rawWaterLevelModel.setUrl(ctx.getInitParameter("connectionString"));
        rawWaterLevelModel.setUser(ctx.getInitParameter("db_username"));
        rawWaterLevelModel.setPassword(ctx.getInitParameter("db_password"));
        try {
            rawWaterLevelModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (SQLException ex) {
            Logger.getLogger(RawWaterLevelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Connection is - " + rawWaterLevelModel.getConnection());
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");

        String task = request.getParameter("task");

        String searchRawWater = "";
        searchRawWater = request.getParameter("searchRawWater");

        try {
            if (searchRawWater == null) {
                searchRawWater = "";
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
                if (JQstring.equals("getRawWater")) {
                    list = rawWaterLevelModel.getRawWater(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                rawWaterLevelModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --OHLevelController get JQuery Parameters Part-" + e);
        }
        if (task == null) {
            task = "";
        } else if (task.equals("Show All Records")) {
            searchRawWater = "";
        } else if (task.equals("generateReport")) {
            searchRawWater = request.getParameter("searchRawWater");
            List listAll = null;
            String jrxmlFilePath;
            response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            listAll = rawWaterLevelModel.showAllData(searchRawWater);
            jrxmlFilePath = ctx.getRealPath("/report/waterWorks/RawWaterLevelReport.jrxml");
            byte[] reportInbytes = rawWaterLevelModel.generateMapReport(jrxmlFilePath, listAll);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            return;
        }

        if (task.equals("showMapWindow")) {
            String longi = request.getParameter("logitude");
            String latti = request.getParameter("lattitude");
            request.setAttribute("longi", longi);
            request.setAttribute("latti", latti);
            System.out.println(latti + "," + longi);
            request.getRequestDispatcher("openMapWindowView").forward(request, response);
            return;
        }

        if (task.equals("Delete")) {
            rawWaterLevelModel.deleteRecord(Integer.parseInt(request.getParameter("rawWaterLevelId")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New") || task.equals("Update")) {
            int rawWaterLevelId;
            try {
                rawWaterLevelId = Integer.parseInt(request.getParameter("rawWaterLevelId"));
            } catch (Exception e) {
                rawWaterLevelId = 0;
            }
            if (task.equals("Save AS New")) {
                rawWaterLevelId = 0;
            }
            RawWaterLevel ohLevelBean = new RawWaterLevel();
            ohLevelBean.setRawWaterLevelId(rawWaterLevelId);
            ohLevelBean.setRawWaterId(rawWaterLevelModel.getRawWaterId(request.getParameter("rawWaterName")));
            ohLevelBean.setLevel(Float.parseFloat(request.getParameter("level")));

            String level_datetime = request.getParameter("level_datetime");
            String level_time_hour = request.getParameter("level_time_hour");
            String level_time_min = request.getParameter("level_time_min");

            ohLevelBean.setLevel_datetime(level_datetime);
            ohLevelBean.setHours(Integer.parseInt(level_time_hour));
            ohLevelBean.setMinutes(Integer.parseInt(level_time_min));

            request.setAttribute("level_datetime", level_datetime);
            request.setAttribute("level_time_hour", level_time_hour);
            request.setAttribute("level_time_min", level_time_min);
//            ohLevelBean.setLatitude(request.getParameter("latitude"));
//            ohLevelBean.setLongitude(request.getParameter("longitude"));
            ohLevelBean.setRemark(request.getParameter("remark"));
            if (rawWaterLevelId == 0) {
                System.out.println("Inserting values by RawWaterLevelModel......");
                rawWaterLevelModel.insertRecord(ohLevelBean);
            } else {
                System.out.println("Update values by RawWaterLevelModel........");
                rawWaterLevelModel.updateRecord(ohLevelBean);
            }
        }

       // if (request.getAttribute("isSelectPriv").equals("Y")) {
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

            noOfRowsInTable = rawWaterLevelModel.getTotalRowsInTable(searchRawWater);                  // get the number of records (rows) in the table.

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
            List<RawWaterLevel> rawWaterLevelList = rawWaterLevelModel.getAllRecords(lowerLimit, noOfRowsToDisplay, searchRawWater);
            lowerLimit = lowerLimit + rawWaterLevelList.size();
            noOfRowsTraversed = rawWaterLevelList.size();

            // Now set request scoped attributes, and then forward the request to view.
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("rawWaterLevelList", rawWaterLevelList);

            if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }
       // }
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchRawWater", searchRawWater);
        request.setAttribute("message", rawWaterLevelModel.getMessage());
        request.setAttribute("msgBgColor", rawWaterLevelModel.getMessageBGColor());
        request.getRequestDispatcher("rawWaterLevelView").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
