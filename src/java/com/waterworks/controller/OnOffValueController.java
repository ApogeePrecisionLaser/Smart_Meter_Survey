/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.waterworks.controller;

import com.connection.DBConnection.DBConnection;
import com.util.UniqueIDGenerator;
import com.waterworks.model.OnOffValueModel;
import com.waterworks.tableClasses.OnOffValue;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jpss
 */
public class OnOffValueController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
         response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            response.setContentType("text/html;charset=UTF-8");
            int lowerLimit = 0, noOfRowsTraversed = 0, noOfRowsToDisplay = 6, noOfRowsInTable;

            ServletContext ctx = getServletContext();
            OnOffValue onOffValue = new OnOffValue();
            OnOffValueModel onOffValueModel = new OnOffValueModel();
            onOffValueModel.setDriverClass(ctx.getInitParameter("driverClass"));
            onOffValueModel.setConnectionString(ctx.getInitParameter("connectionString"));
            onOffValueModel.setDb_username(ctx.getInitParameter("db_username"));
            onOffValueModel.setDb_password(ctx.getInitParameter("db_password"));
            try{
            onOffValueModel.setConnection((Connection) DBConnection.getConnection(ctx));

            }catch(Exception e){
                System.out.println(e);
            }
            String task = request.getParameter("task");
            String searchOnOff_Name = request.getParameter("searchOnOff_Name");
            String JQuery = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQuery != null) {
                List list = null;
                if (JQuery.equals("getOnOffName")) {
                    list = onOffValueModel.getOnOffName(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                return;
            }
            if (task == null) {
                task = "";
            }
            if (searchOnOff_Name == null) {
                searchOnOff_Name = "";
            }
            String onoff_value_id = request.getParameter("onoff_value_id");
            if (onoff_value_id == null || onoff_value_id.isEmpty()) {
                onoff_value_id = "0";


            }
            String onoff_name = request.getParameter("onoff_name");
             String onoff_value = request.getParameter("onoff_value");
             String time= request.getParameter("time_m");
            String remark = request.getParameter("remark");
            onOffValue.setOnOffName(onoff_name);
            onOffValue.setOnOffValue(onoff_value);
            onOffValue.setTime(time);
            onOffValue.setRemark(remark);
            onOffValue.setOnOff_id(Integer.parseInt(onoff_value_id));
            if (task.equals("Save")) {
                onOffValueModel.insertRecord(onOffValue);
            }
            if (task.equals("Save AS New")) {
                if (onOffValueModel.insertRecord(onOffValue)) {
                    System.out.println("record saved successfully");
                } else {
                    System.out.print("record not saved");
                }
            }
            if (task.equals("Delete")) {
                if (onOffValueModel.deleteRecord(onOffValue)) {
                    System.out.print("record deleted");
                } else {
                    System.out.print("record not deleted");
                }
            }
            if (task.equals("Update")) {
                if (onOffValueModel.UpdateRecord(onOffValue)) {
                    System.out.print("record deleted");
                } else {
                    System.out.print("record not deleted");
                }
            }
            if (task.equals("Show All Records")) {
                searchOnOff_Name = "";
            }
            String buttonAction = request.getParameter("buttonAction");
            if (buttonAction == null) {
                buttonAction = "none";
            }
            try {
                lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
                noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
            } catch (Exception e) {
                lowerLimit = noOfRowsTraversed = 0;
            }
            noOfRowsInTable = onOffValueModel.getNoOfRows(searchOnOff_Name);
            if (buttonAction.equals("Next")); else if (buttonAction.equals("Previous")) {
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
            if (task.equals("Save") || task.equals("Save AS New") || task.equals("Delete") || task.equals("Update")) {
                lowerLimit = lowerLimit - noOfRowsTraversed;
            }
            List list = onOffValueModel.ShowData(lowerLimit, noOfRowsToDisplay, searchOnOff_Name);
            lowerLimit = lowerLimit + list.size();
            noOfRowsTraversed = list.size();
            if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }
            request.setAttribute("list", list);
            request.setAttribute("message", onOffValueModel.getMessage());
            request.setAttribute("msgBgColor", onOffValueModel.getMsgBgColor());
            request.setAttribute("searchOnOff_Name", searchOnOff_Name);

            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            // request.setAttribute("searchcity", searchcity);
            RequestDispatcher rd = request.getRequestDispatcher("onOffValue");
            rd.forward(request, response);
        } finally {
            out.close();
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
