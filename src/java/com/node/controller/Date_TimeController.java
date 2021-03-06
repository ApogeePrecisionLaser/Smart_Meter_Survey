/*
ShiftWorkBench-2
 */
package com.node.controller;

import com.connection.DBConnection.DBConnection;
import java.sql.Connection;
import com.node.model.Date_TimeModel;
import com.node.tableClasses.Date_TimeBean;
import com.util.UniqueIDGenerator;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Shubham Swarnkar
 */
public class Date_TimeController extends HttpServlet
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        ServletContext ctx = getServletContext();
        Date_TimeModel sm = new Date_TimeModel();
        String task = request.getParameter("task");
        int lowerLimit = 0, noOfRowsTraversed = 0, noOfRowsToDisplay = 10, noOfRowsInTable = 0;
        try {
            sm.setConnection((Connection) DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.print(e);
        }
        if (task == null || task.isEmpty()) {
            task = "";
        }
        try
        {
            if (task.equals("Delete")) {
                sm.deleteRecord(request.getParameter("level_date_id"));
            } else if (task.equals("save") || task.equals("Save AS New")) {
                int level_date_id = 0;
                try {
                    level_date_id = Integer.parseInt(request.getParameter("level_date_id").trim());
                } catch (Exception ex) {
                    level_date_id = 0;
                }

                if (task.equals("Save AS New")) {
                    level_date_id = 0;
                }
                String level_date = request.getParameter("level_date");
                String starting_time_hour = request.getParameter("starting_time_hour").trim();
                String starting_time_min = request.getParameter("starting_time_min").trim();
                Date_TimeBean sb = new Date_TimeBean();
                sb.setlevel_date_id(level_date_id);
                sb.setlevel_date(level_date);
                String starting_time = starting_time_hour + ":" + starting_time_min;
                if (starting_time.equals(":")) {
                    starting_time = "";
                }
                sb.setStarting_time(starting_time);
                sm.insertRecord(sb);
            }
            String buttonAction = request.getParameter("buttonAction");
            if (buttonAction == null)
            {
                buttonAction = "none";
            }
            try
            {
                lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
                noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
            } catch (Exception e) {
                lowerLimit = noOfRowsTraversed = 0;
            }

            noOfRowsInTable = Date_TimeModel.getNoOfRows();

            if (buttonAction.equals("Next"));
            else if (buttonAction.equals("Previous"))
            {
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
            if (task.equals("save") || task.equals("Save AS New") || task.equals("Delete"))
            {
                lowerLimit = lowerLimit - noOfRowsTraversed;
            }
            List<Date_TimeBean> list = Date_TimeModel.showData(lowerLimit, noOfRowsToDisplay);
            lowerLimit = lowerLimit + list.size();
            noOfRowsTraversed = list.size();
            if ((lowerLimit - noOfRowsTraversed) == 0) {
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }
            request.setAttribute("list", list);
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            request.setAttribute("message", sm.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("date_timeController");
            rd.forward(request, response);
        } catch (Exception e) {
            System.out.print(e);
        }
    }
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
