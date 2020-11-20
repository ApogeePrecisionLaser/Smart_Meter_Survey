/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.waterworks.controller;

import com.connection.DBConnection.DBConnection;
import com.general.model.GeneralModel;
import java.sql.Connection;
import com.util.UniqueIDGenerator;
import com.waterworks.model.LevelRangeModel;
import com.waterworks.tableClasses.LevelRangeBean;
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

/**
 *
 * @author Shobha
 */
public class LevelRangeController extends HttpServlet {
   
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
        LevelRangeModel levelRangeModel = new LevelRangeModel();

        levelRangeModel.setDriver(ctx.getInitParameter("driverClass"));
        levelRangeModel.setUrl(ctx.getInitParameter("connectionString"));
        levelRangeModel.setUser(ctx.getInitParameter("db_username"));
        levelRangeModel.setPassword(ctx.getInitParameter("db_password"));
        try {
            levelRangeModel.setConnection((Connection) DBConnection.getConnectionForUtf(ctx));
        } catch (Exception ex) {
            Logger.getLogger(OHLevelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setCharacterEncoding("UTF-8");
        String task = request.getParameter("task");
        if (task == null) {
            task = "";
        }
        try {
            String searchDateFrom = "",searchTimeFrom="", searchDateTo = "",searchTimeTo="",searchOverheadtankName="";
            String waterTreatmentPlantName="";
            searchDateFrom = request.getParameter("searchDateFrom");
            searchTimeFrom=request.getParameter("searchTimeFrom");
            searchDateTo = request.getParameter("searchDateTo");
            searchTimeTo=request.getParameter("searchTimeTo");
            searchOverheadtankName = request.getParameter("oht_name");

            //String searchOverheadTankName=request.getParameter("search_overheadtank_name");

            if(searchOverheadtankName == null || searchOverheadtankName.isEmpty()){
                  searchOverheadtankName="";

              }else{
                searchOverheadtankName = request.getParameter("oht_name");

                waterTreatmentPlantName=levelRangeModel.getWaterTreatmentPlantName(searchOverheadtankName);
                if(!waterTreatmentPlantName.equals("")){
                    
                }

              }



            if (searchDateFrom == null || searchDateFrom.isEmpty()) {
                searchDateFrom = "";
                
            }
            if(searchTimeFrom == null || searchTimeFrom.isEmpty()){
                searchTimeFrom="";
            }
            if (searchDateTo == null || searchDateTo.isEmpty()) {
                searchDateTo = "";
               
            }
            if(searchTimeTo == null || searchTimeTo.isEmpty()){
                 searchTimeTo="";
            }

            if(!searchDateFrom.isEmpty()  && searchTimeFrom.isEmpty()){

                searchTimeFrom= "00:00:00";
            }
            if(!searchDateTo.isEmpty() && searchTimeTo.isEmpty()){

                searchTimeTo= "23:59:59";
            }


            try {
                //PrintWriter pw = response.getWriter();
                String JQString = request.getParameter("action1");
                String q = request.getParameter("q");

                if (JQString != null) {
                    PrintWriter pw = response.getWriter();
                    List<String> list = null;

                    if (JQString.equals("getOverHeadTankName")) {
                        String action2 = request.getParameter("action2");
                        if (action2 == null) {
                            action2 = "";
                        }
                        //call getOverheadTankName() method
                        list = levelRangeModel.getOverheadTankName(q);
                    }

                    Iterator<String> itr = list.iterator();
                    while (itr.hasNext()) {
                        String data = itr.next();
                        pw.println(data);
                    }
                    return;
                }
            } catch (Exception e) {
                System.out.println(e);
            }



            if (task.equals("viewPdf")) {
                String jrxmlFilePath;
                List list = null;
                response.setContentType("application/pdf");
                response.setCharacterEncoding("UTF-8");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                jrxmlFilePath = ctx.getRealPath("/report/waterWorks/level_range.jrxml");
                list = levelRangeModel.getAllRecords(-1, -1, searchDateFrom,searchTimeFrom,searchDateTo,searchTimeTo,searchOverheadtankName);
                byte[] reportInbytes = GeneralModel.generateRecordList(jrxmlFilePath, list);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
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

            noOfRowsInTable = levelRangeModel.getTotalRowsInTable(searchDateFrom,searchTimeFrom,searchDateTo,searchTimeTo,searchOverheadtankName);                  // get the number of records (rows) in the table.

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
            if (task.equals("Show All Records")) {
                searchDateFrom = "";
                searchTimeFrom="";
                searchDateTo = "";
                searchTimeTo="";
                searchOverheadtankName="";

                waterTreatmentPlantName="";
            }

            List<LevelRangeBean> ohLevelList = levelRangeModel.getAllRecords(lowerLimit, noOfRowsToDisplay, searchDateFrom,searchTimeFrom,searchDateTo,searchTimeTo,searchOverheadtankName);
            lowerLimit = lowerLimit + ohLevelList.size();
            noOfRowsTraversed = ohLevelList.size();

            levelRangeModel.getAllRecordsLevelRangeCount(lowerLimit, noOfRowsToDisplay, searchDateFrom,searchTimeFrom,searchDateTo,searchTimeTo,searchOverheadtankName);

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
            request.setAttribute("message", levelRangeModel.getMessage());
            request.setAttribute("msgBgColor", levelRangeModel.getMessageBGColor());

            request.setAttribute("totalLeakage", levelRangeModel.totalLeakage);
            request.setAttribute("totalStable", levelRangeModel.totalStable);
            request.setAttribute("totalSupply", levelRangeModel.totalSupply);
            request.setAttribute("totalDistribution", levelRangeModel.totalDistribution);

             request.setAttribute("totalLeakageTime", levelRangeModel.totalLeakageTime);
              request.setAttribute("totalStableTime", levelRangeModel.totalStableTime);
               request.setAttribute("totalSupplyTime", levelRangeModel.totalSupplyTime);
                request.setAttribute("totalDistributionTime", levelRangeModel.totalDistributionTime);

            request.setAttribute("searchDateFrom", searchDateFrom);
            request.setAttribute("searchTimeFrom", searchTimeFrom);
            request.setAttribute("searchDateTo", searchDateTo);
            request.setAttribute("searchTimeTo", searchTimeTo);
            request.setAttribute("searchOverheadtankName", searchOverheadtankName);
            request.setAttribute("waterTreatmentPlantName", waterTreatmentPlantName);
            request.getRequestDispatcher("level_range").forward(request, response);
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
