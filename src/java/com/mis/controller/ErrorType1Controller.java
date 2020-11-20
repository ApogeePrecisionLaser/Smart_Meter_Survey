/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mis.controller;

import com.connection.DBConnection.DBConnection;
import com.mis.model.ErrorType1Model;
import com.mis.tableClasses.ErrorType1;
import com.util.UniqueIDGenerator;
import java.io.IOException;
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
public class ErrorType1Controller extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int lowerLimit = 0, noOfRowsTraversed, noOfRowsToDisplay = 15, noOfRowsInTable;
        System.out.println("this is ErrorType1Controller ....");
        ServletContext ctx = getServletContext();
        ErrorType1Model errorType1Model = new ErrorType1Model();

        errorType1Model.setDriver(ctx.getInitParameter("driverClass"));
        errorType1Model.setUrl(ctx.getInitParameter("connectionString"));
        errorType1Model.setUser(ctx.getInitParameter("db_username"));
        errorType1Model.setPassword(ctx.getInitParameter("db_password"));
        try {
            errorType1Model.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
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
        try {
            noOfRowsInTable = errorType1Model.getTotalRowsInTable();                  // get the number of records (rows) in the table.

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

            List<ErrorType1> ohLevelList = errorType1Model.getAllRecords(lowerLimit, noOfRowsToDisplay);
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
            request.setAttribute("message", errorType1Model.getMessage());
            request.setAttribute("msgBgColor", errorType1Model.getMessageBGColor());
            request.getRequestDispatcher("errortype1").forward(request, response);
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
