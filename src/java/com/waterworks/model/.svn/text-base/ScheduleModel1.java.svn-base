/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.waterworks.model;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;
import javax.servlet.ServletContext;

/**
 *
 * @author Navitus1
 */
public class ScheduleModel1 extends TimerTask {

    private ServletContext ctx;
    private static Connection connection;
    Date dt = new Date();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String cut_dt = df.format(dt);

    public void setCtx(ServletContext ctx) {
        this.ctx = ctx;
    }



        public void run() {
        try {
            System.out.println("run method is running");
       
        } catch (Exception ex) {
            System.out.println(" run() Error: " + ex);
        }
    }

        public void checkErrorLog(){
            
        }


}
