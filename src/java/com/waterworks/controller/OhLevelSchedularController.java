/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.waterworks.controller;

import com.connection.DBConnection.DBConnection;
import com.waterworks.model.OhLevelSchedularModel;
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

/**
 *
 * @author Shobha
 */
public class OhLevelSchedularController extends HttpServlet{
@Override
    public void init() throws ServletException {
        ServletContext ctx = getServletContext();
        Connection con = null;
        DBConnection dbCon = new DBConnection();
        System.out.println("TimeSchedulerController is accessed");

        try {
            con = dbCon.getConnection(ctx);
        } catch (SQLException ex) {
            Logger.getLogger(SchedulerController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (con != null) {
            OhLevelSchedularModel sm = new OhLevelSchedularModel();
            OhLevelSchedularModel.setConnection( (java.sql.Connection) con);
            sm.setCtx(ctx);

            ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
            scheduledThreadPool.scheduleAtFixedRate(sm, 0, 111, TimeUnit.MINUTES);

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
        Logger.getLogger(OhLevelSchedularController.class.getName()).log(Level.SEVERE, null, ex);
    }
        System.out.println("---------------SchedulerController is Running--------------");
    }
}
