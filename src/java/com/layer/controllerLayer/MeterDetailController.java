/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.layer.controllerLayer;

import com.Bean.tableClasses.meterDetail_Bean;
import com.connection.DBConnection.DBConnection;
import com.layer.modellLayer.MeterDetail_Model;
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
import org.json.simple.JSONObject;

/**
 *
 * @author Shubham
 */
public class MeterDetailController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        int lowerLimit = 0, noOfRowsTraversed = 0, noOfRowsToDisplay = 10, noOfRowsInTable;
        try {
            int itemNameId;
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            ServletContext ctx = getServletContext();
            meterDetail_Bean itemNameBean = new meterDetail_Bean();
            MeterDetail_Model meterDetailModel = new MeterDetail_Model();
//            meterDetailModel.setDriverClass(ctx.getInitParameter("driverClass"));
//            meterDetailModel.setConnectionString(ctx.getInitParameter("connectionString"));
//            meterDetailModel.setDb_username(ctx.getInitParameter("db_username"));
//            meterDetailModel.setDb_password(ctx.getInitParameter("db_password"));
            meterDetailModel.setConnection(DBConnection.getConnection(ctx));
            String searchMeterNo = request.getParameter("searchMeterNo");
            String searchMobileNo = request.getParameter("searchMobileNo");
            String active = request.getParameter("active");
            String JQuery = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQuery != null) {
                List list = null;
                if (JQuery.equals("getMeterNo")) {
                    list = meterDetailModel.getMeterNo(q);
                } else if (JQuery.equals("getMobileNo")) {
                    list = meterDetailModel.getMobileNo(q);
                } else if (JQuery.equals("getNodeName")) {
                    list = meterDetailModel.getNodeName(q);
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
            if (searchMeterNo == null) {
                searchMeterNo = "";
            }
            if (searchMobileNo == null) {
                searchMobileNo = "";
            }
            if (active == null) {
                active = "";
            }

            if (task.equals("showMapWindow")) {
                String longi = request.getParameter("logitude");
                String latti = request.getParameter("lattitude");
                request.setAttribute("longi", longi);
                request.setAttribute("latti", latti);
                System.out.println(latti + "," + longi);
                request.getRequestDispatcher("openMapWindowView").forward(request, response);
                return;
            }
            if (task.equals("checkMeter_no")) {
                String meter_no = request.getParameter("meter_no");
                List<String> list = meterDetailModel.checkMeterNo(meter_no);
                int size = list.size();
                out.print(size);
                return;
            } else if (task.equals("getlatlong"))
            {
                JSONObject json = meterDetailModel.getLatLong();
                out = response.getWriter();
                out.print(json);
                return;
            }
            if (task.equals("Delete")) {
                meterDetailModel.deleteRecord(request.getParameter("meter_detail_id"));
            }
            if (task.equals("Save") || task.equals("Save AS New") || task.equals("Update")) {
                String meter_detail_id = request.getParameter("meter_detail_id");
                if (meter_detail_id == null || meter_detail_id.isEmpty()) {
                    itemNameId = 0;
                } else {
                    itemNameId = Integer.parseInt(meter_detail_id);
                }
                if (task.equals("Save AS New")) {
                    itemNameId = 0;
                }
                itemNameBean.setMeter_detail_id(itemNameId);
                itemNameBean.setKey_person_name(request.getParameter("key_person_name"));
                itemNameBean.setMeter_no(request.getParameter("meter_no"));
                itemNameBean.setMeter_reading(request.getParameter("meter_reading"));
                itemNameBean.setDate_time(request.getParameter("date_time"));
                itemNameBean.setRemark(request.getParameter("remark"));
                itemNameBean.setWater_service_no(request.getParameter("water_service_no"));
                itemNameBean.setProperty_service_no(request.getParameter("property_service_no"));
                itemNameBean.setNode(request.getParameter("node"));
                String latitude = request.getParameter("latitude");
                String longitude = request.getParameter("longitude");
                if (latitude != null && !latitude.isEmpty()) {
                    itemNameBean.setLatitude(Double.parseDouble(latitude));
                }
                if (longitude != null && !longitude.isEmpty()) {
                    itemNameBean.setLongitude(Double.parseDouble(longitude));
                }
                if (itemNameId == 0) {
                    meterDetailModel.insertRecord(itemNameBean);
                } else if (task.equals("Update")) {
                    meterDetailModel.UpdateRecord(itemNameBean);
                }
            }

            if (task.equals("Show All Records")) {
                searchMeterNo = "";
                searchMobileNo = "";
                active = "";
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
            noOfRowsInTable = meterDetailModel.getNoOfRows(searchMeterNo, searchMobileNo, active);
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
            List<meterDetail_Bean> list = meterDetailModel.ShowData(lowerLimit, noOfRowsToDisplay, searchMeterNo, searchMobileNo, active);
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
            request.setAttribute("searchMeterNo", searchMeterNo);
            request.setAttribute("searchMobileNo", searchMobileNo);
            request.setAttribute("active", active);
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            RequestDispatcher rd = request.getRequestDispatcher("meter_detail_view");
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
            Logger.getLogger(MeterDetailController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(MeterDetailController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
