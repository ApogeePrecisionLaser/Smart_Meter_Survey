/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.node.controller;

import com.layer.controllerLayer.*;
import com.connection.DBConnection.DBConnection;
import com.node.model.PipeType_Model;
import com.node.tableClasses.PipeType_Bean;
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
public class PipeTypeController extends HttpServlet
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        int lowerLimit = 0, noOfRowsTraversed = 0, noOfRowsToDisplay = 10, noOfRowsInTable;
        try
        {
            int pipeType_id;
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            ServletContext ctx = getServletContext();
            PipeType_Bean itemNameBean = new PipeType_Bean();
            PipeType_Model meterDetailModel = new PipeType_Model();
            meterDetailModel.setConnection(DBConnection.getConnection(ctx));
            String searchPipe_type = request.getParameter("searchPipe_type");
            String JQuery = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQuery != null) {
                List list = null;
                if (JQuery.equals("getPipeType")) {
                    list = meterDetailModel.getPipeType(q);
                }
                if (list != null) {
                    Iterator<String> iter = list.iterator();
                    while (iter.hasNext()) {
                        String data = iter.next();
                        out.println(data);
                    }
                }
                out.close();
                meterDetailModel.closeConnection();
                return;
            }
            String task = request.getParameter("task");
            if (task == null) {
                task = "";
            }
            if (searchPipe_type == null) {
                searchPipe_type = "";
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

            if (task.equals("Delete")) {
                meterDetailModel.deleteRecord(request.getParameter("pipe_type_id"));
            }
            if (task.equals("Save") || task.equals("Save AS New") || task.equals("Update"))
            {
                String pipe_type_id = request.getParameter("pipe_type_id");
                if (pipe_type_id == null || pipe_type_id.isEmpty()) {
                    pipeType_id = 0;
                } else {
                    pipeType_id = Integer.parseInt(pipe_type_id);
                }
                if (task.equals("Save AS New"))
                {
                    pipeType_id = 0;
                }
                itemNameBean.setPipe_type_id(pipeType_id);
                itemNameBean.setPipe_type(request.getParameter("pipe_type"));
                itemNameBean.setRemark(request.getParameter("remark"));
                if (pipeType_id == 0)
                {
                    meterDetailModel.insertRecord(itemNameBean);
                } else if (task.equals("Update")) {
                    meterDetailModel.UpdateRecord(itemNameBean);
                }
            }

            if (task.equals("Show All Records")) {
                searchPipe_type = "";
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
            if (task.equals("Save") || task.equals("Save AS New") || task.equals("Update") || task.equals("Delete")) {
                lowerLimit = noOfRowsTraversed = 0;
            }
            noOfRowsInTable = meterDetailModel.getNoOfRows(searchPipe_type);
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
            List<PipeType_Bean> list = meterDetailModel.ShowData(lowerLimit, noOfRowsToDisplay, searchPipe_type);
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
            request.setAttribute("message", meterDetailModel.getMessage());
            request.setAttribute("msgBgColor", meterDetailModel.getMsgBgColor());
            request.setAttribute("searchPipe_type", searchPipe_type);
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            RequestDispatcher rd = request.getRequestDispatcher("pipe_type");
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
            Logger.getLogger(PipeTypeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(PipeTypeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
