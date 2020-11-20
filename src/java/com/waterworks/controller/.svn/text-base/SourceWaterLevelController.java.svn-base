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

import com.waterworks.model.SourceWaterLevelModel;
import com.waterworks.tableClasses.SourceWaterLevel;


public class SourceWaterLevelController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 3, noOfRowsInTable;
        System.out.println("this is OHLevelController ....");
        ServletContext ctx = getServletContext();
        SourceWaterLevelModel sourceWaterLevelModel = new SourceWaterLevelModel();
//          HttpSession session = request.getSession(false);
//       if (session == null || (String) session.getAttribute("user_name")==null ) {
//            response.sendRedirect("beforeLoginPage");             return;
//        }
//        try {
//            sourceWaterLevelModel.setConnection(DBConnection.getConnection(ctx, session));
//        } catch (Exception e) {
//            System.out.println("error in PaymentStatusController setConnection() calling try block" + e);
//        }

        sourceWaterLevelModel.setDriver(ctx.getInitParameter("driverClass"));
        sourceWaterLevelModel.setUrl(ctx.getInitParameter("connectionString"));
        sourceWaterLevelModel.setUser(ctx.getInitParameter("db_username"));
        sourceWaterLevelModel.setPassword(ctx.getInitParameter("db_password"));
        try {
            sourceWaterLevelModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (SQLException ex) {
            Logger.getLogger(SourceWaterLevelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Connection is - " + sourceWaterLevelModel.getConnection());
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");

        String task = request.getParameter("task");

        String searchSourceWater = "";
        searchSourceWater = request.getParameter("searchSourceWater");

        try {
            if (searchSourceWater == null) {
                searchSourceWater = "";
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
                if (JQstring.equals("getSourceWater")) {
                    list = sourceWaterLevelModel.getSourceWater(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                sourceWaterLevelModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error -- SourceWaterLevelController get JQuery Parameters Part-" + e);
        }
        if (task == null) {
            task = "";
        } else if (task.equals("Show All Records")) {
            searchSourceWater = "";
        } else if (task.equals("generateReport")) {
            searchSourceWater = request.getParameter("searchSourceWater");
            List listAll = null;
            String jrxmlFilePath;
            response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            listAll = sourceWaterLevelModel.showAllData(searchSourceWater);
            jrxmlFilePath = ctx.getRealPath("/report/waterWorks/SourceWaterLevelReport.jrxml");
            byte[] reportInbytes = sourceWaterLevelModel.generateMapReport(jrxmlFilePath, listAll);
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
            sourceWaterLevelModel.deleteRecord(Integer.parseInt(request.getParameter("sourceWaterLevelId")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New") || task.equals("Update")) {
            int sourceWaterLevelId;
            try {
                sourceWaterLevelId = Integer.parseInt(request.getParameter("sourceWaterLevelId"));
            } catch (Exception e) {
                sourceWaterLevelId = 0;
            }
            if (task.equals("Save AS New")) {
                sourceWaterLevelId = 0;
            }
            SourceWaterLevel swLevelBean = new SourceWaterLevel();
            swLevelBean.setSourceWaterLevelId(sourceWaterLevelId);
            swLevelBean.setSourceWaterId(sourceWaterLevelModel.getSourceWaterId(request.getParameter("sourceWaterName")));
            swLevelBean.setLevel(Float.parseFloat(request.getParameter("level")));

            String level_datetime = request.getParameter("level_datetime");
            String level_time_hour = request.getParameter("level_time_hour");
            String level_time_min = request.getParameter("level_time_min");

            swLevelBean.setLevel_datetime(level_datetime);
            swLevelBean.setHours(Integer.parseInt(level_time_hour));
            swLevelBean.setMinutes(Integer.parseInt(level_time_min));

            request.setAttribute("level_datetime", level_datetime);
            request.setAttribute("level_time_hour", level_time_hour);
            request.setAttribute("level_time_min", level_time_min);
//            swLevelBean.setLatitude(request.getParameter("latitude"));
//            swLevelBean.setLongitude(request.getParameter("longitude"));
            swLevelBean.setRemark(request.getParameter("remark"));
            if (sourceWaterLevelId == 0) {
                System.out.println("Inserting values by SourceWaterLevelModel......");
                sourceWaterLevelModel.insertRecord(swLevelBean);
            } else {
                System.out.println("Update values by SourceWaterLevelModel........");
                sourceWaterLevelModel.updateRecord(swLevelBean);
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

            noOfRowsInTable = sourceWaterLevelModel.getTotalRowsInTable(searchSourceWater);                  // get the number of records (rows) in the table.

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
            List<SourceWaterLevel> sourceWaterLevelList = sourceWaterLevelModel.getAllRecords(lowerLimit, noOfRowsToDisplay, searchSourceWater);
            lowerLimit = lowerLimit + sourceWaterLevelList.size();
            noOfRowsTraversed = sourceWaterLevelList.size();

            // Now set request scoped attributes, and then forward the request to view.
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("sourceWaterLevelList", sourceWaterLevelList);

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
        request.setAttribute("searchSourceWater", searchSourceWater);
        request.setAttribute("message", sourceWaterLevelModel.getMessage());
        request.setAttribute("msgBgColor", sourceWaterLevelModel.getMessageBGColor());
        request.getRequestDispatcher("sourceWaterLevelView").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
