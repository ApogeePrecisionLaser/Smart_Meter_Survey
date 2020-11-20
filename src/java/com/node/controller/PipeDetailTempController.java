/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.node.controller;

import com.connection.DBConnection.DBConnection;
import com.node.model.PipeDetailTempModel;
import com.node.tableClasses.PipeDetail;
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
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Shubham
 */
public class PipeDetailTempController extends HttpServlet
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException
    {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 3, noOfRowsInTable;
        System.out.println("this is WaterTreatmentPlantController ....");
        ServletContext ctx = getServletContext();
        PipeDetailTempModel pipeDetailModel = new PipeDetailTempModel();
        pipeDetailModel.setDriverClass(ctx.getInitParameter("driverClass"));
        pipeDetailModel.setConnectionString(ctx.getInitParameter("connectionString"));
        pipeDetailModel.setDb_username(ctx.getInitParameter("db_username"));
        pipeDetailModel.setDb_password(ctx.getInitParameter("db_password"));
        try
        {
            pipeDetailModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        System.out.println("Connection is - " + pipeDetailModel.getConnection());
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        String task = request.getParameter("task");
        if (task == null) {
            task = "";
        }
        String search_node_name="";
        String search_pipe_type="";
        search_node_name=request.getParameter("search_node_name");
        if(search_node_name==null){
            search_node_name="";
        }
        search_pipe_type=request.getParameter("search_pipe_type");
        if(search_pipe_type==null){
            search_pipe_type="";
        }
         try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getNodeName"))
                {
                    list = pipeDetailModel.getNodeName(q);
                }
                else if(JQstring.equals("getPipeType"))
                {
                    list = pipeDetailModel.getPipeType(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                pipeDetailModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --OverHeadTankController get JQuery Parameters Part-" + e);
        }
        if (task.equals("generateReport"))
        {
            List listAll = null;
            String jrxmlFilePath;
            response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            listAll = pipeDetailModel.showAllData(-1, -1,search_node_name,search_pipe_type);
            jrxmlFilePath = ctx.getRealPath("/report/pipe_detail.jrxml");
            byte[] reportInbytes = pipeDetailModel.generateMapReport(jrxmlFilePath, listAll);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            return;
        }
        try {
            if (task.equals("Delete")) {
                pipeDetailModel.deleteRecord(Integer.parseInt(request.getParameter("pipe_detail_id")));  // Pretty sure that organisation_type_id will be available.
            } else if (task.equals("Save") || task.equals("Save AS New") || task.equals("Update")) {
                int pipe_detail_id;
                try {
                    pipe_detail_id = Integer.parseInt(request.getParameter("pipe_detail_id"));
                } catch (Exception e) {
                    pipe_detail_id = 0;
                }
                if (task.equals("Save AS New"))
                {
                    pipe_detail_id = 0;
                }
                String node_name = request.getParameter("node_name");
                String head_latitude = request.getParameter("head_latitude");
                String head_longitude = request.getParameter("head_longitude");
                String tail_latitude = request.getParameter("tail_latitude");
                String tail_longitude = request.getParameter("tail_longitude");
                String diameter = request.getParameter("diameter");
                String diameter_unit = request.getParameter("diameter_unit");
                String length = request.getParameter("length");
                String length_unit = request.getParameter("length_unit");
                String remark = request.getParameter("remark");
                String pipe_type = request.getParameter("pipe_type");
                String pipe_name = request.getParameter("pipe_name");
             
                PipeDetail pd = new PipeDetail();
                pd.setPipe_detail_id(pipe_detail_id);
                pd.setNode_name(node_name);
                pd.setHead_latitude(Double.parseDouble(head_latitude));
                pd.setHead_longitude(Double.parseDouble(head_longitude));
                pd.setTail_latitude(Double.parseDouble(tail_latitude));
                pd.setTail_longitude(Double.parseDouble(tail_longitude));
                pd.setDiameter(Double.parseDouble(diameter));
                pd.setDiameter_unit(diameter_unit);
                pd.setLength(Double.parseDouble(length));
                pd.setLength_unit(length_unit);
                pd.setRemark(remark);
                pd.setPipe_type(pipe_type);
                pd.setPipe_name(pipe_name);
                
                if (pipe_detail_id == 0)
                {
                    System.out.println("Inserting values by ......");
                    pipeDetailModel.insertRecord(pd);
                } else {
                    System.out.println("Update values by WaterTreatmentPlantModel........");
                    pipeDetailModel.updateRecord(pd);
                }
            }
            if(task.equals("Show All Records")){
                search_node_name="";
                search_pipe_type="";
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

            noOfRowsInTable = pipeDetailModel.getTotalRowsInTable(search_node_name,search_pipe_type);

            if (buttonAction.equals("Next")); // lowerLimit already has value such that it shows forward records, so do nothing here.
            else if (buttonAction.equals("Previous")) {
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

            if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New") || task.equals("Update")) {
                lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
            }
            
            List<PipeDetail> list = pipeDetailModel.showAllData(lowerLimit, noOfRowsToDisplay,search_node_name,search_pipe_type);
            lowerLimit = lowerLimit + list.size();
            noOfRowsTraversed = list.size();

            // Now set request scoped attributes, and then forward the request to view.
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("list", list);

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
              request.setAttribute("search_node_name", search_node_name);
              request.setAttribute("search_pipe_type", search_pipe_type);
            //   request.setAttribute("message", sourceWaterLevelModel.getMessage());
            request.getRequestDispatcher("pipe_detail_temp").forward(request, response);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(PipeDetailTempController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(PipeDetailTempController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
