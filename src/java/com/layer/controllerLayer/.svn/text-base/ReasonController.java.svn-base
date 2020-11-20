/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.layer.controllerLayer;

import com.Bean.tableClasses.Reason_Bean;
import com.node.controller.*;
import com.layer.controllerLayer.*;
import com.connection.DBConnection.DBConnection;
import com.layer.modellLayer.Reason_Model;
import com.util.UniqueIDGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Shubham
 */
public class ReasonController extends HttpServlet
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        int lowerLimit = 0, noOfRowsTraversed = 0, noOfRowsToDisplay = 10, noOfRowsInTable;
        try
        {
            int reasonType_id;
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            ServletContext ctx = getServletContext();
            Reason_Model reason_model = new Reason_Model();
            Reason_Bean reason_Bean = new Reason_Bean();
            reason_model.setConnection(DBConnection.getConnection(ctx));
            String searchReason = request.getParameter("searchReason");
            String JQuery = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQuery != null) {
                List list = null;
                if (JQuery.equals("getReason")) {
                    list = reason_model.getReason(q);
                }
                if (list != null) {
                    Iterator<String> iter = list.iterator();
                    while (iter.hasNext()) {
                        String data = iter.next();
                        out.println(data);
                    }
                }
                out.close();
                reason_model.closeConnection();
                return;
            }
            String task = request.getParameter("task");
            if (task == null) {
                task = "";
            }
            if (searchReason == null) {
                searchReason = "";
            }
            if (task.equals("showMapWindow"))
            {
                String longi = request.getParameter("logitude");
                String latti = request.getParameter("lattitude");
                request.setAttribute("longi", longi);
                request.setAttribute("latti", latti);
                System.out.println(latti + "," + longi);
                request.getRequestDispatcher("openMapWindowView").forward(request, response);
                return;
            }
            if (task.equals("Delete"))
            {
                reason_model.deleteRecord(request.getParameter("reason_id"));
            }
            if (task.equals("Save") || task.equals("Save AS New") || task.equals("Update")) {
                String reason_id = request.getParameter("reason_id");
                if (reason_id == null || reason_id.isEmpty())
                {
                    reasonType_id = 0;
                } else
                {
                    reasonType_id = Integer.parseInt(reason_id);
                }
                if (task.equals("Save AS New"))
                {
                    reasonType_id = 0;
                }
                reason_Bean.setReason_id(reasonType_id);
                reason_Bean.setReason(request.getParameter("reason"));
                reason_Bean.setRemark(request.getParameter("remark"));
                if (reasonType_id == 0)
                {
                    reason_model.insertRecord(reason_Bean);
                } else if (task.equals("Update")) {
                    reason_model.UpdateRecord(reason_Bean);
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
            noOfRowsInTable = reason_model.getNoOfRows(searchReason);
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
            List<Reason_Bean> list = reason_model.ShowData(lowerLimit, noOfRowsToDisplay, searchReason);
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
            request.setAttribute("message", reason_model.getMessage());
            request.setAttribute("msgBgColor", reason_model.getMsgBgColor());
            request.setAttribute("searchReason", searchReason);
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            RequestDispatcher rd = request.getRequestDispatcher("reason");
            rd.forward(request, response);
        } catch (Exception e) {
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ReasonController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ReasonController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
