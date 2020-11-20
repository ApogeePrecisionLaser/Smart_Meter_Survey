/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waterworks.controller;

import com.connection.DBConnection.DBConnection;
import com.general.model.GeneralModel;
import java.sql.Connection;
import com.util.UniqueIDGenerator;
import com.waterworks.model.OnOffModel;
import com.waterworks.tableClasses.OnOff;
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
 * @author Administrator
 */
public class OnOffController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 15, noOfRowsInTable;
        System.out.println("this is OHLevelController ....");
        ServletContext ctx = getServletContext();
        OnOffModel ohLevelModel = new OnOffModel();
List listpdf = null;
        ohLevelModel.setDriver(ctx.getInitParameter("driverClass"));
        ohLevelModel.setUrl(ctx.getInitParameter("connectionString"));
        ohLevelModel.setUser(ctx.getInitParameter("db_username"));
        ohLevelModel.setPassword(ctx.getInitParameter("db_password"));
        try {
            ohLevelModel.setConnection((Connection) DBConnection.getConnectionForUtf(ctx));
        } catch (SQLException ex) {
            Logger.getLogger(OHLevelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setCharacterEncoding("UTF-8");
        String task = request.getParameter("task");
        if (task == null) {
            task = "";
        }
        try {
            String searchDateFrom = "", searchDateTo = "",searchOverheadtankName="";
            searchDateFrom = request.getParameter("searchDateFrom");
            searchDateTo = request.getParameter("searchDateTo");
            searchOverheadtankName = request.getParameter("oht_name");
            String typeName = request.getParameter("type_name");
            //String searchOverheadTankName=request.getParameter("search_overheadtank_name");

            if(searchOverheadtankName == null){
                  searchOverheadtankName="";

              }

 if(typeName == null){
                  typeName="";

              }

            if (searchDateFrom == null) {
                searchDateFrom = "";
            }
            if (searchDateTo == null) {
                searchDateTo = "";
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
                        list = ohLevelModel.getOverheadTankName(q);
                    }
                    
                      if (JQString.equals("getTypeName")) {
                        String action2 = request.getParameter("action2");
                        if (action2 == null) {
                            action2 = "";
                        }
                        //call getOverheadTankName() method
                        list = ohLevelModel.getTypeName(q);
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
                jrxmlFilePath = ctx.getRealPath("/report/waterWorks/OnOffAllLevel.jrxml");
                String type = request.getParameter("type");
                String q="";
               List listlength = ohLevelModel.getTypeName(q);
                if(type.equals("All"))
                {
                  for(int i=1; i<=listlength.size()-1;i++)
                  {
                  type= OnOffModel.getTypeNameId(i);
                  
                  
                listpdf = ohLevelModel.ShowAllPDF(-1, -1, searchDateFrom, searchDateTo,searchOverheadtankName,type);
                  }
                byte[] reportInbytes = GeneralModel.generateRecordList(jrxmlFilePath, listpdf);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                                 }
                
                
                else
                {
                
//                response.setContentType("application/pdf");
//                response.setCharacterEncoding("UTF-8");
//                ServletOutputStream servletOutputStream = response.getOutputStream();
                jrxmlFilePath = ctx.getRealPath("/report/waterWorks/OnOffLevel.jrxml");
                list = ohLevelModel.ShowPDF(-1, -1, searchDateFrom, searchDateTo,searchOverheadtankName,type);
                byte[] reportInbytes = GeneralModel.generateRecordList(jrxmlFilePath, list);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
              
                }
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

            noOfRowsInTable = ohLevelModel.getTotalRowsInTable(searchDateFrom, searchDateTo,searchOverheadtankName,typeName);                  // get the number of records (rows) in the table.

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
                searchDateTo = "";
                searchOverheadtankName="";
                typeName="";
            }

            List<OnOff> ohLevelList = ohLevelModel.getAllRecords(lowerLimit, noOfRowsToDisplay, searchDateFrom, searchDateTo,searchOverheadtankName,typeName);
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
            request.setAttribute("message", ohLevelModel.getMessage());
            request.setAttribute("msgBgColor", ohLevelModel.getMessageBGColor());
            request.setAttribute("searchDateFrom", searchDateFrom);
             request.setAttribute("typeName", typeName);
            request.setAttribute("searchDateTo", searchDateTo);
            request.setAttribute("searchOverheadtankName", searchOverheadtankName);
            request.getRequestDispatcher("onoff").forward(request, response);
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(OnOffController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(OnOffController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
