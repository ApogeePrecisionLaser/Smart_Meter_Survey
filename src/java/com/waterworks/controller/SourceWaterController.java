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

import com.waterworks.model.SourceWaterModel;

import com.waterworks.tableClasses.SourceWater;

public class SourceWaterController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 3, noOfRowsInTable;
        System.out.println("this is SourceWaterController ....");
        ServletContext ctx = getServletContext();

        SourceWaterModel sourceWaterModel = new SourceWaterModel();
//
//          HttpSession session = request.getSession(false);
//       if (session == null || (String) session.getAttribute("user_name")==null ) {
//            response.sendRedirect("beforeLoginPage");             return;
//        }
//        try {
//            sourceWaterModel.setConnection(DBConnection.getConnection(ctx, session));
//        } catch (Exception e) {
//            System.out.println("error in PaymentStatusController setConnection() calling try block" + e);
//        }
       sourceWaterModel.setDriver(ctx.getInitParameter("driverClass"));
        sourceWaterModel.setUrl(ctx.getInitParameter("connectionString"));
        sourceWaterModel.setUser(ctx.getInitParameter("db_username"));
        sourceWaterModel.setPassword(ctx.getInitParameter("db_password"));
        try {
            sourceWaterModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (SQLException ex) {
            Logger.getLogger(SourceWaterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Connection is - " + sourceWaterModel.getConnection());
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
                    list = sourceWaterModel.getSourceWater(q);
                } else if (JQstring.equals("getCityName")) {
                    list = sourceWaterModel.getCityName(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                sourceWaterModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --SourceWaterController get JQuery Parameters Part-" + e);
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
            listAll = sourceWaterModel.showAllData(searchSourceWater);
            jrxmlFilePath = ctx.getRealPath("/report/waterWorks/SourceWaterReport.jrxml");
            byte[] reportInbytes = sourceWaterModel.generateMapReport(jrxmlFilePath, listAll);
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
            sourceWaterModel.deleteRecord(Integer.parseInt(request.getParameter("sourceWaterId")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New") || task.equals("Update")) {
            int sourceWaterId;
            try {
                sourceWaterId = Integer.parseInt(request.getParameter("sourceWaterId"));
            } catch (Exception e) {
                sourceWaterId = 0;
            }
            if (task.equals("Save AS New")) {
                sourceWaterId = 0;
            }
            SourceWater sourceWaterBean = new SourceWater();
            try {
                sourceWaterBean.setSourceWaterId(sourceWaterId);
                sourceWaterBean.setName(request.getParameter("sourceWaterName"));
                sourceWaterBean.setAvgHeight(Float.parseFloat(request.getParameter("avgHeight")));
                sourceWaterBean.setMaxHeight(Float.parseFloat(request.getParameter("maxHeight")));
                sourceWaterBean.setMinHeight(Float.parseFloat(request.getParameter("minHeight")));
                sourceWaterBean.setCityId(sourceWaterModel.getCityId(request.getParameter("cityName")));
                sourceWaterBean.setAddress1(request.getParameter("address1"));
                sourceWaterBean.setAddress2(request.getParameter("address2"));
                sourceWaterBean.setLatitude(request.getParameter("latitude"));
                sourceWaterBean.setLongitude(request.getParameter("longitude"));
                sourceWaterBean.setRemark(request.getParameter("remark"));
            } catch (Exception e) {
                System.out.println("Error in SourceWaterController : " + e);
            }
            if (sourceWaterId == 0) {
                System.out.println("Inserting values by SourceWaterModel......");
                sourceWaterModel.insertRecord(sourceWaterBean);
            } else {
                System.out.println("Update values by SourceWaterModel........");
                sourceWaterModel.updateRecord(sourceWaterBean);
            }
        }

        //if (request.getAttribute("isSelectPriv").equals("Y")) {
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
            System.out.println("searching.......... " + searchSourceWater);
            noOfRowsInTable = sourceWaterModel.getTotalRowsInTable(searchSourceWater);                  // get the number of records (rows) in the table.

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
            List<SourceWater> sourceWaterList = sourceWaterModel.getAllRecords(lowerLimit, noOfRowsToDisplay, searchSourceWater);
            lowerLimit = lowerLimit + sourceWaterList.size();
            noOfRowsTraversed = sourceWaterList.size();

            // Now set request scoped attributes, and then forward the request to view.
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("sourceWaterList", sourceWaterList);

            if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }
        //}
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchSourceWater", searchSourceWater);
        request.setAttribute("message", sourceWaterModel.getMessage());
        request.setAttribute("msgBgColor", sourceWaterModel.getMessageBGColor());
        request.getRequestDispatcher("sourceWaterView").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
