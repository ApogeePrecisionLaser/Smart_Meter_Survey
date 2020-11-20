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

import com.waterworks.model.RawWaterModel;
import com.waterworks.tableClasses.RawWater;


public class RawWaterController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 3, noOfRowsInTable;
        System.out.println("this is RawWaterController ....");
        ServletContext ctx = getServletContext();

        RawWaterModel rawWaterModel = new RawWaterModel();

       rawWaterModel.setDriver(ctx.getInitParameter("driverClass"));
        rawWaterModel.setUrl(ctx.getInitParameter("connectionString"));
        rawWaterModel.setUser(ctx.getInitParameter("db_username"));
        rawWaterModel.setPassword(ctx.getInitParameter("db_password"));
        try {
            rawWaterModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (SQLException ex) {
            Logger.getLogger(RawWaterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Connection is - " + rawWaterModel.getConnection());
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
                    list = rawWaterModel.getRawWater(q);
                } else if (JQstring.equals("getCityName")) {
                    list = rawWaterModel.getCityName(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                rawWaterModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --RawWaterController get JQuery Parameters Part-" + e);
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
            listAll = rawWaterModel.showAllData(searchRawWater);
            jrxmlFilePath = ctx.getRealPath("/report/waterWorks/RawWaterReport.jrxml");
            byte[] reportInbytes = rawWaterModel.generateMapReport(jrxmlFilePath, listAll);
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
            rawWaterModel.deleteRecord(Integer.parseInt(request.getParameter("rawWaterId")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New") || task.equals("Update")) {
            int rawWaterId;
            try {
                rawWaterId = Integer.parseInt(request.getParameter("rawWaterId"));
            } catch (Exception e) {
                rawWaterId = 0;
            }
            if (task.equals("Save AS New")) {
                rawWaterId = 0;
            }
            RawWater rawWaterBean = new RawWater();
            try {
                rawWaterBean.setRawWaterId(rawWaterId);
                rawWaterBean.setName(request.getParameter("rawWaterName"));
                rawWaterBean.setAvgHeight(Float.parseFloat(request.getParameter("avgHeight")));
                rawWaterBean.setMaxHeight(Float.parseFloat(request.getParameter("maxHeight")));
                rawWaterBean.setMinHeight(Float.parseFloat(request.getParameter("minHeight")));
                rawWaterBean.setCityId(rawWaterModel.getCityId(request.getParameter("cityName")));
                rawWaterBean.setAddress1(request.getParameter("address1"));
                rawWaterBean.setAddress2(request.getParameter("address2"));
                rawWaterBean.setLatitude(request.getParameter("latitude"));
                rawWaterBean.setLongitude(request.getParameter("longitude"));
                rawWaterBean.setRemark(request.getParameter("remark"));
            } catch (Exception e) {
                System.out.println("Error in RawWaterController : " + e);
            }
            if (rawWaterId == 0) {
                System.out.println("Inserting values by RawWaterModel......");
                rawWaterModel.insertRecord(rawWaterBean);
            } else {
                System.out.println("Update values by RawWaterModel........");
                rawWaterModel.updateRecord(rawWaterBean);
            }
        }

      //  if (request.getAttribute("isSelectPriv").equals("Y")) {
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
            System.out.println("searching.......... " + searchRawWater);
            noOfRowsInTable = rawWaterModel.getTotalRowsInTable(searchRawWater);                  // get the number of records (rows) in the table.

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
            List<RawWater> rawWaterList = rawWaterModel.getAllRecords(lowerLimit, noOfRowsToDisplay, searchRawWater);
            lowerLimit = lowerLimit + rawWaterList.size();
            noOfRowsTraversed = rawWaterList.size();

            // Now set request scoped attributes, and then forward the request to view.
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("rawWaterList", rawWaterList);

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
        request.setAttribute("message", rawWaterModel.getMessage());
        request.setAttribute("msgBgColor", rawWaterModel.getMessageBGColor());
        request.getRequestDispatcher("rawWaterView").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
