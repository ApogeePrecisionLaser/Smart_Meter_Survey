package com.node.controller;

import com.Bean.tableClasses.meterDetail_Bean;
import com.connection.DBConnection.DBConnection;
import com.node.model.NearestMapModel;
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
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.waterworks.tableClasses.OverHeadTank;
import com.waterworks.tableClasses.WaterTreatmentPlant;

public class NearestMapController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 3, noOfRowsInTable;
            System.out.println("this is OverHeadTankController ....");
            ServletContext ctx = getServletContext();
            NearestMapModel nearestMapController = new NearestMapModel();
            nearestMapController.setDriver(ctx.getInitParameter("driverClass"));
            nearestMapController.setUrl(ctx.getInitParameter("connectionString"));
            nearestMapController.setUser(ctx.getInitParameter("db_username"));
            nearestMapController.setPassword(ctx.getInitParameter("db_password"));
            try {
                nearestMapController.setConnection(DBConnection.getConnectionForUtf(ctx));
            } catch (SQLException ex)
            {
                Logger.getLogger(NearestMapController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Connection is - " + nearestMapController.getConnection());
            request.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Type", "text/plain; charset=UTF-8");
            String task = request.getParameter("task");
            int range = 0;
            String search = "";
            String search_range = request.getParameter("search_range");
            if (search_range == null || search_range.isEmpty()) {
            } else {
                range = Integer.parseInt(search_range);
            }
            search = request.getParameter("search");
            try {
                if (search == null) {
                    search = "";
                }
                if (search == null) {
                    search = "";
                }
            } catch (Exception e) {
            }

            if (task == null) {
                task = "";
            } else if (task.equals("Show All Records")) {
                range = 0;
                search = "";
            }
            double current_latitude = 0.0;
            double current_longtitude = 0.0;
            try {
                current_latitude = Double.parseDouble(request.getParameter("latitude"));
                current_longtitude = Double.parseDouble(request.getParameter("longitude"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (search.equals("OverheadTank")) {
                List<OverHeadTank> list = nearestMapController.getAllRecords(range, current_latitude, current_longtitude);
                request.setAttribute("overHeadTankList", list);
            } else if (search.equals("WaterPlant")) {
                List<WaterTreatmentPlant> list = nearestMapController.getAllRecords1(range, current_latitude, current_longtitude);
                request.setAttribute("list", list);
            } else if (search.equals("ConnectionDetail"))
            {
                List<meterDetail_Bean> list = nearestMapController.ShowData(range, current_latitude, current_longtitude);
                request.setAttribute("list", list);
            }
            request.setAttribute("longitude", current_longtitude);
            request.setAttribute("latitude", current_latitude);
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            request.setAttribute("search", search);
            request.setAttribute("search_range", range);
            request.setAttribute("message", nearestMapController.getMessage());
            request.setAttribute("msgBgColor", nearestMapController.getMessageBGColor());
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("nearestMap").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
