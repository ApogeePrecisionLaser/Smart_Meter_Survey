/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.node.controller;

import com.connection.DBConnection.DBConnection;
import com.node.model.ValveModel;
import com.node.tableClasses.Valve;
import com.util.UniqueIDGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
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
public class ValveController extends HttpServlet {
   
 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      response.setContentType("text/html;charset=UTF-8");
  int lowerLimit = 0, noOfRowsTraversed = 0, noOfRowsToDisplay = 10, noOfRowsInTable;
        try {
             int reasonType_id;
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            ServletContext ctx = getServletContext();
            ValveModel valveModel = new ValveModel();

            valveModel.setConnection(DBConnection.getConnection(ctx));
            String searchReason = request.getParameter("searchReason");
            String JQuery = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQuery != null) {
                List list = null;
                if (JQuery.equals("getBend")) {
                   list = valveModel.getValve(q);
                }
                if (list != null) {
                    Iterator<String> iter = list.iterator();
                    while (iter.hasNext()) {
                        String data = iter.next();
                        out.println(data);
                    }
                }
                out.close();
                valveModel.closeConnection();
                return;
            }
            String task = request.getParameter("task");
            if (task == null) {
                task = "";
            }
            if (searchReason == null) {
                searchReason = "";
            }

            if (task.equals("Delete"))
            {
                valveModel.deleteRecord(request.getParameter("valve_id"));
            }
            if (task.equals("Save") || task.equals("Save AS New") || task.equals("Update")) {
                int valve_id = 0;
                try {
                    valve_id = Integer.parseInt(request.getParameter("valve_id").trim());
                } catch (Exception ex) {
                    valve_id = 0;
                }
                if (task.equals("Save AS New")) {
                    valve_id = 0;
                }
                Valve valve=new Valve();
                valve.setValve_id(valve_id);
                valve.setValve_name(request.getParameter("valve_name"));
                  valve.setDiameter(Double.parseDouble(request.getParameter("diameter")));
                valve.setRemark(request.getParameter("remark"));
                if (valve_id == 0)
                {
                    valveModel.insertRecord(valve);
                } else if (task.equals("Update")) {
                    valveModel.UpdateRecord(valve);
                }
            }

            if (task.equals("Show All Records")) {
                searchReason = "";
            }
            String buttonAction = request.getParameter("buttonAction");
            if (buttonAction == null) {
                buttonAction = "none";
            }
            try
            {
                lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
                noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
            } catch (Exception e)
            {
                lowerLimit = noOfRowsTraversed = 0;
            }
            if (task.equals("Save") || task.equals("Save AS New") || task.equals("Update") || task.equals("Delete")) {
                lowerLimit = noOfRowsTraversed = 0;
            }
            noOfRowsInTable = valveModel.getNoOfRows(searchReason);
            if (buttonAction.equals("Next")); else if (buttonAction.equals("Previous"))
            {
                int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
                if (temp < 0) {
                    noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                    lowerLimit = 0;
                } else {
                    lowerLimit = temp;
                }
            } else if (buttonAction.equals("First"))
            {
                lowerLimit = 0;
            } else if (buttonAction.equals("Last"))
            {
                lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
                if (lowerLimit < 0) {
                    lowerLimit = 0;
                }
            }
            List<Valve> list = valveModel.ShowData(lowerLimit, noOfRowsToDisplay, searchReason);
            lowerLimit = lowerLimit + list.size();
            noOfRowsTraversed = list.size();
            if ((lowerLimit - noOfRowsTraversed) == 0)
            {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }
            request.setAttribute("list", list);
            request.setAttribute("message", valveModel.getMessage());
            request.setAttribute("msgBgColor", valveModel.getMsgBgColor());
            request.setAttribute("searchReason", searchReason);
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
           request.getRequestDispatcher("valve").forward(request, response);
        }catch(Exception e){

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
