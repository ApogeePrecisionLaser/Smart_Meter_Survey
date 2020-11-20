/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.layer.controllerLayer;

import com.Bean.tableClasses.MultipleMeterDetail;
import com.connection.DBConnection.DBConnection;
import com.layer.modellLayer.MultipleMeterDetailModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Navitus1
 */
public class MultipleMeterDetailController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        ServletContext ctx = getServletContext();
        MultipleMeterDetailModel nodeovertank = new MultipleMeterDetailModel();


        nodeovertank.setDriverClass(ctx.getInitParameter("driverClass"));
        nodeovertank.setConnectionString(ctx.getInitParameter("connectionString"));
        nodeovertank.setDb_username(ctx.getInitParameter("db_username"));
        nodeovertank.setDb_password(ctx.getInitParameter("db_password"));
        try {
            nodeovertank.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (SQLException ex) {
        }
        System.out.println("Connection is - " + nodeovertank.getConnection());
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        String task = request.getParameter("task");
        if (task == null || task.isEmpty()) {
            task = "";
        }
        try {
            int value = 0;
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("checkmobile_no1")) {
                    String mobile_no1 = request.getParameter("mobile_no1").trim();
                    if (mobile_no1 != null && !mobile_no1.isEmpty()) {
                        value = nodeovertank.checkMob1Value(mobile_no1);
                    }
                    out.print(value);
                    out.close();
                    nodeovertank.closeConnection();
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String current_date = dateFormat.format(date);
            if (task.equals("Add All Records")) {
                String[] meter_detail_id = request.getParameterValues("meter_detail_id");
                String[] key_person_name = request.getParameterValues("key_person_name");
                String[] mobile_no = request.getParameterValues("mobile_no");
                String[] meter_no = request.getParameterValues("meter_no");
                String[] meter_reading = request.getParameterValues("meter_reading");
                String[] remark = request.getParameterValues("remark");
                String[] date_time = request.getParameterValues("date_time");
                String[] water_service_no = request.getParameterValues("water_service_no");
                String[] property_service_no = request.getParameterValues("property_service_no");

                MultipleMeterDetail md = new MultipleMeterDetail();
                md.setMeter_detail_id(meter_detail_id);
                md.setKey_person_name(key_person_name);
                md.setMobile_no(mobile_no);
                md.setMeter_no(meter_no);
                md.setMeter_reading(meter_reading);
                md.setDate_time(date_time);
                md.setRemark(remark);
                md.setWater_service_no(water_service_no);
                md.setProperty_service_no(property_service_no);
                nodeovertank.insertRecord(md);
            }

            request.setAttribute("current_date", current_date);
            request.setAttribute("message", nodeovertank.getMessage());
            request.setAttribute("msgBgColor", nodeovertank.getMsgBgColor());
            request.getRequestDispatcher("multiplemeter").forward(request, response);
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
            Logger.getLogger(MultipleMeterDetailController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MultipleMeterDetailController.class.getName()).log(Level.SEVERE, null, ex);
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
