/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.waterworks.controller;

import com.connection.DBConnection.DBConnection;
import com.waterworks.model.ErrorMsgSchedulerModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Com7_2
 */
public class ErrorMsgSchedulerController extends HttpServlet {
   
    
   
    @Override
    public void init() throws ServletException {
        ServletContext ctx = getServletContext();
        Connection con = null;
        DBConnection dbCon = new DBConnection();
        System.out.println("TimeSchedulerController is accessed");

        try {
            con = dbCon.getConnection(ctx);
        } catch (SQLException ex) {
            Logger.getLogger(ErrorMsgSchedulerController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (con != null) {
            ErrorMsgSchedulerModel sm = new ErrorMsgSchedulerModel();
            ErrorMsgSchedulerModel.setConnection( (java.sql.Connection) con);
            sm.setCtx(ctx);

            ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
            scheduledThreadPool.scheduleAtFixedRate(sm, 0, 30, TimeUnit.MINUTES);

//
//
//             ScheduleModel1 sm1 = new ScheduleModel1();
//            ScheduleModel.setConnection( (com.mysql.jdbc.Connection) con);
//            sm1.setCtx(ctx);
//
//            ScheduledExecutorService scheduledThreadPool1 = Executors.newScheduledThreadPool(5);
//            scheduledThreadPool1.scheduleAtFixedRate(sm, 0, 1, TimeUnit.HOURS);
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ErrorMsgSchedulerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("---------------SchedulerController is Running--------------");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
     

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
