package com.node.controller;

import com.connection.DBConnection.DBConnection;
import com.node.model.TypeOfValveModel;
import com.node.tableClasses.TypeOfBend;
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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

public class TypeOfValveController extends HttpServlet {

    static int pipe_detail_id = 0;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        try {
            ServletContext ctx = getServletContext();
            TypeOfValveModel typeOfValveModel = new TypeOfValveModel();
            typeOfValveModel.setConnection(DBConnection.getConnection(ctx));
            int lowerLimit = 0, noOfRowsTraversed = 0, noOfRowsToDisplay = 10, noOfRowsInTable;
            try {
                String JQstring = request.getParameter("action1");
                String q = request.getParameter("q");   // field own input
                if (JQstring != null) {
                    PrintWriter out = response.getWriter();
                    List<String> list = null;
                    if (JQstring.equals("getTypeofvalve")) {
                        list = typeOfValveModel.getTypeofValve(q);
                    } 
                    Iterator<String> iter = list.iterator();
                    while (iter.hasNext()) {
                        String data = iter.next();
                        out.println(data);
                    }
                    typeOfValveModel.closeConnection();
                    return;
                }
            } catch (Exception e) {
                System.out.println("\n Error --OverHeadTankController get JQuery Parameters Part-" + e);
            }

            try {
            } catch (Exception e) {
                System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
            }
            String task = request.getParameter("task");
            if (task == null) {
                task = "";
            }
            if (task.equals("getPipeDetailId")) {
                pipe_detail_id = Integer.parseInt(request.getParameter("pipe_detail_id"));
            } else if (task.equals("GetPipeBend")) {
                JSONObject json = typeOfValveModel.getPipeDetail(pipe_detail_id);
                PrintWriter out = response.getWriter();
                out.print(json);
                return;
            }
            if (task.equals("Delete")) {
                typeOfValveModel.deleteRecord(Integer.parseInt(request.getParameter("type_of_bendId")));
            } else if (task.equals("Save") || task.equals("Save AS New") || task.equals("Add All Records") || task.equals("Update")) {
                int type_of_bend_id;
                try {
                    type_of_bend_id = Integer.parseInt(request.getParameter("type_of_bendId"));
                } catch (Exception e) {
                    type_of_bend_id = 0;
                }
                TypeOfBend typeOfBend = new TypeOfBend();
                if (task.equals("Save AS New")) {
                    type_of_bend_id = 0;
                    String[] id = new String[1];
                    String hs_id = "1";
                    id[0] = hs_id;
                    typeOfBend.setType_of_bend_id(id);
                } else {
                    typeOfBend.setType_of_bend_id(request.getParameterValues("type_of_bendId"));
                }
                if (task.equals("Add All Records")) {
                    typeOfBend.setType_of_bend_id(request.getParameterValues("type_of_bendId"));
                }
                if (type_of_bend_id == 0 || task.equals("Add All Records")) {
                    typeOfBend.setBend_order(request.getParameterValues("bendOrder"));
                    typeOfBend.setType_of_bend(request.getParameterValues("type_ofBend"));
                    typeOfBend.setRemark(request.getParameterValues("remark1"));
                    typeOfBend.setLatitude1(request.getParameterValues("latitude"));
                    typeOfBend.setLongitude1(request.getParameterValues("longitude"));
                    typeOfValveModel.insertMultipleRecord(typeOfBend, pipe_detail_id);
                } else if (task.equals("Update")) {
                    typeOfBend.setBendOrder(Integer.parseInt(request.getParameter("bendOrder")));
                    typeOfBend.setType_ofBend(request.getParameter("type_ofBend"));
                    typeOfBend.setRemark1(request.getParameter("remark1"));
                    typeOfBend.setType_of_bendId(Integer.parseInt(request.getParameter("type_of_bendId")));
                    typeOfBend.setLatitude(Double.parseDouble(request.getParameter("latitude")));
                    typeOfBend.setLongitude(Double.parseDouble(request.getParameter("longitude")));
                    typeOfValveModel.updateRecord(typeOfBend, pipe_detail_id);
                }
            }
            try {
            } catch (Exception e) {
                System.out.print("errrooorr" + e);
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
            noOfRowsInTable = typeOfValveModel.getNoOfRows(pipe_detail_id);
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
            if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New") || task.equals("Update")) {
                lowerLimit = lowerLimit - noOfRowsTraversed;
            }
            List<TypeOfBend> list = typeOfValveModel.ShowData(lowerLimit, noOfRowsToDisplay, pipe_detail_id);
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
            request.setAttribute("message", typeOfValveModel.getMessage());
            request.setAttribute("msgBgColor", typeOfValveModel.getMsgBgColor());
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            request.getRequestDispatcher("typeofvalve").forward(request, response);
        } catch (Exception e) {
            System.out.print("error in citystatuscontroller" + e);
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
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
