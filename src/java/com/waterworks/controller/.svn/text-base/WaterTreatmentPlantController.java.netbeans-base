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

import com.waterworks.model.WaterTreatmentPlantModel;
import com.waterworks.tableClasses.WaterTreatmentPlant;


public class WaterTreatmentPlantController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 3, noOfRowsInTable;
        System.out.println("this is WaterTreatmentPlantController ....");
        ServletContext ctx = getServletContext();
        WaterTreatmentPlantModel waterTreatmentPlantModel = new WaterTreatmentPlantModel();


        waterTreatmentPlantModel.setDriverClass(ctx.getInitParameter("driverClass"));
        waterTreatmentPlantModel.setConnectionString(ctx.getInitParameter("connectionString"));
        waterTreatmentPlantModel.setDb_username(ctx.getInitParameter("db_username"));
        waterTreatmentPlantModel.setDb_password(ctx.getInitParameter("db_password"));
        try {
            waterTreatmentPlantModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (SQLException ex) {
            Logger.getLogger(WaterTreatmentPlantController.class.getName()).log(Level.SEVERE, null, ex);
        }
  /*      waterTreatmentPlantModel.setDriver(ctx.getInitParameter("driverClass"));
        waterTreatmentPlantModel.setUrl(ctx.getInitParameter("connectionString"));
        waterTreatmentPlantModel.setUser(ctx.getInitParameter("db_userName"));
        waterTreatmentPlantModel.setPassword(ctx.getInitParameter("db_userPasswrod"));
        waterTreatmentPlantModel.setConnection(); */
        System.out.println("Connection is - " + waterTreatmentPlantModel.getConnection());
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");

        String task = request.getParameter("task");
        String searchWaterTreatmentPlant = "";
        searchWaterTreatmentPlant = request.getParameter("searchWaterTreatmentPlant");

        try {
            if (searchWaterTreatmentPlant == null) {
                searchWaterTreatmentPlant = "";
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
                if (JQstring.equals("getWaterTreatmentPlant")) {
                    list = waterTreatmentPlantModel.getWaterTreatmentPlant(q);
                } else if (JQstring.equals("getCityName")) {
                    list = waterTreatmentPlantModel.getCityName(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                waterTreatmentPlantModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --WaterTreatmentPlantController get JQuery Parameters Part-" + e);
        }

        if (task == null) {
            task = "";
        } else if (task.equals("Show All Records")) {
            searchWaterTreatmentPlant = "";
        } else if (task.equals("generateReport")) {
            searchWaterTreatmentPlant = request.getParameter("searchWaterTreatmentPlant");
            List listAll = null;
            String jrxmlFilePath;
            response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            listAll = waterTreatmentPlantModel.showAllData(searchWaterTreatmentPlant);
            jrxmlFilePath = ctx.getRealPath("/report/waterWorks/WaterTreatmentPlantReport.jrxml");
            byte[] reportInbytes = waterTreatmentPlantModel.generateMapReport(jrxmlFilePath, listAll);
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
        if (task.equals("showAllOverheadTank")) {
            List<WaterTreatmentPlant> List = waterTreatmentPlantModel.showDataBean();
            request.setAttribute("CoordinatesList", List);
            request.getRequestDispatcher("/view/MapView/allWaterTreatmentPlant.jsp").forward(request, response);
            return;
        }
        if (task.equals("Delete")) {
            waterTreatmentPlantModel.deleteRecord(Integer.parseInt(request.getParameter("waterTreatmentPlantId")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New") || task.equals("Update")) {
            int waterTreatmentPlantId;
            try {
                waterTreatmentPlantId = Integer.parseInt(request.getParameter("waterTreatmentPlantId"));
            } catch (Exception e) {
                waterTreatmentPlantId = 0;
            }
            if (task.equals("Save AS New")) {
                waterTreatmentPlantId = 0;
            }
            WaterTreatmentPlant waterTreatmentPlantBean = new WaterTreatmentPlant();
            waterTreatmentPlantBean.setWaterTreatmentPlantId(waterTreatmentPlantId);
            waterTreatmentPlantBean.setWaterTreatmentPlantName(request.getParameter("waterTreatmentPlantName"));
            waterTreatmentPlantBean.setCapacityMLD(Integer.parseInt(request.getParameter("capacityMLD")));
            waterTreatmentPlantBean.setCityId(waterTreatmentPlantModel.getCityId(request.getParameter("cityName")));
            waterTreatmentPlantBean.setAddress1(request.getParameter("address1"));
            waterTreatmentPlantBean.setAddress2(request.getParameter("address2"));
            waterTreatmentPlantBean.setLatitude(request.getParameter("latitude"));
            waterTreatmentPlantBean.setLongitude(request.getParameter("longitude"));
            waterTreatmentPlantBean.setRemark(request.getParameter("remark"));
            if (waterTreatmentPlantId == 0) {
                System.out.println("Inserting values by WaterTreatmentPlantModel......");
                waterTreatmentPlantModel.insertRecord(waterTreatmentPlantBean);
            } else {
                System.out.println("Update values by WaterTreatmentPlantModel........");
                waterTreatmentPlantModel.updateRecord(waterTreatmentPlantBean);
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
            System.out.println("searching.......... " + searchWaterTreatmentPlant);
            noOfRowsInTable = waterTreatmentPlantModel.getTotalRowsInTable(searchWaterTreatmentPlant);                  // get the number of records (rows) in the table.

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
            List<WaterTreatmentPlant> waterTreatmentPlantList = waterTreatmentPlantModel.getAllRecords(lowerLimit, noOfRowsToDisplay, searchWaterTreatmentPlant);
            lowerLimit = lowerLimit + waterTreatmentPlantList.size();
            noOfRowsTraversed = waterTreatmentPlantList.size();

            // Now set request scoped attributes, and then forward the request to view.
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("waterTreatmentPlantList", waterTreatmentPlantList);

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
        request.setAttribute("searchWaterTreatmentPlant", searchWaterTreatmentPlant);
        request.setAttribute("message", waterTreatmentPlantModel.getMessage());
        request.setAttribute("msgBgColor", waterTreatmentPlantModel.getMessageBGColor());
        request.getRequestDispatcher("waterTreatmentPlantView").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
