/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.waterworks.controller;

import com.connection.DBConnection.DBConnection;
import com.general.model.GeneralModel;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.DefaultFontMapper;
import com.itextpdf.text.pdf.FontMapper;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.Table;
import com.waterworks.model.SendMailModel;
import com.waterworks.model.SendPDFSchedularModel;
import com.waterworks.model.SendPDFSchedularModel1;
import com.waterworks.tableClasses.SendPDFSchedularBean;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Shobha
 */
public class SendPDFSchedularController1 extends HttpServlet {
   



    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
       // PrintWriter out = response.getWriter();

    //private static Connection connection;
    Date dt = new Date();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String cut_dt = df.format(dt);
    String path = "";


ServletContext ctx = getServletContext();



        SendPDFSchedularModel1 model = new SendPDFSchedularModel1();


        model.setDriver(ctx.getInitParameter("driverClass"));
        model.setUrl(ctx.getInitParameter("connectionString"));
        model.setUser(ctx.getInitParameter("db_username"));
        model.setPassword(ctx.getInitParameter("db_password"));
        try {
            model.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (SQLException ex) {
            Logger.getLogger(OverHeadTankController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            List list = model.get_AllOverheadTankId();

            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                int overhead_tank_id = (Integer) iterator.next();


               // model.generate_graph(request, response, overhead_tank_id,ctx);
              
               model.generateREport(overhead_tank_id, request, response, ctx);
            }
        } catch (Exception ex) {
            System.out.println(" run() Error: " + ex);
        }


       

    }


    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";

}

}
