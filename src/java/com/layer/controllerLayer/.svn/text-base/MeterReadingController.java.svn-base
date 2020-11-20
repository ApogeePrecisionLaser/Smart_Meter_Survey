/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.layer.controllerLayer;

import com.Bean.tableClasses.meterDetail_Bean;
import com.connection.DBConnection.DBConnection;
import com.layer.modellLayer.MeterReadingModel;
import com.util.UniqueIDGenerator;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
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

/**
 *
 * @author Shubham
 */
public class MeterReadingController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        int lowerLimit = 0, noOfRowsTraversed = 0, noOfRowsToDisplay = 10, noOfRowsInTable;
        try {
            int itemNameId;
            response.setContentType("text/html;charset=UTF-8");
            ServletContext ctx = getServletContext();
            meterDetail_Bean itemNameBean = new meterDetail_Bean();
            MeterReadingModel meterDetailModel = new MeterReadingModel();
//            meterDetailModel.setDriverClass(ctx.getInitParameter("driverClass"));
//            meterDetailModel.setConnectionString(ctx.getInitParameter("connectionString"));
//            meterDetailModel.setDb_username(ctx.getInitParameter("db_username"));
//            meterDetailModel.setDb_password(ctx.getInitParameter("db_password"));
            meterDetailModel.setConnection(DBConnection.getConnection(ctx));

            String searchMeterNo = request.getParameter("searchMeterNo");
            String searchMeterNoTo = request.getParameter("searchMeterNoTo");
            String searchDateFrom = request.getParameter("searchDateFrom");
            String searchDateTo = request.getParameter("searchDateTo");

            String JQuery = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQuery != null) {
                PrintWriter out = response.getWriter();
                List list = null;
                if (JQuery.equals("getMeterNo")) {
                    list = meterDetailModel.getMeterNo(q);
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
            if (searchMeterNoTo == null) {
                searchMeterNoTo = "";
            }
            if (searchDateFrom == null) {
                searchDateFrom = "";
            }
            if (searchDateTo == null) {
                searchDateTo = "";
            }
            if (task.equals("generateReport")) {
                List listAll = null;
                String jrxmlFilePath;
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                listAll = meterDetailModel.ShowData1(-1, -1, searchMeterNo,searchMeterNoTo,searchDateFrom,searchDateTo);
                jrxmlFilePath = ctx.getRealPath("/report/meter_reading.jrxml");
                byte[] reportInbytes = meterDetailModel.generateMapReport(jrxmlFilePath, listAll);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
            }
            if (task.equals("displayCompleteList")) {
                List listAll = null;
                String jrxmlFilePath;
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                listAll = meterDetailModel.ShowData1(-1, -1, searchMeterNo,searchMeterNoTo,searchDateFrom,searchDateTo);
                jrxmlFilePath = ctx.getRealPath("/report/meter_reading.jrxml");
                byte[] reportInbytes = meterDetailModel.generateMapReport(jrxmlFilePath, listAll);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
            } else if (task.equals("generateReport1")) {
                String jrxmlFilePath;
                List list = null;
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment; filename=meter_reading.xls");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                jrxmlFilePath = ctx.getRealPath("/report/meter_reading.jrxml");
                list = meterDetailModel.ShowData1(-1, -1, searchMeterNo,searchMeterNoTo,searchDateFrom,searchDateTo);
                ByteArrayOutputStream reportInbytes = meterDetailModel.generateExcelList(jrxmlFilePath, list);
                response.setContentLength(reportInbytes.toByteArray().length);
                servletOutputStream.write(reportInbytes.toByteArray(), 0, reportInbytes.toByteArray().length);
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
            } else if (task.equals("generateReportZipFile")) {
                String jrxmlFilePath;
                List list = null;
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment; filename=meter_reading.xls");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                jrxmlFilePath = ctx.getRealPath("/report/meter_reading.jrxml");
                list = meterDetailModel.ShowData1(-1, -1, searchMeterNo,searchMeterNoTo,searchDateFrom,searchDateTo);
                ByteArrayOutputStream reportInbytes = meterDetailModel.generateExcelList(jrxmlFilePath, list);
                response.setContentLength(reportInbytes.toByteArray().length);
                servletOutputStream.write(reportInbytes.toByteArray(), 0, reportInbytes.toByteArray().length);
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
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

            if (task.equals("showMapWindow")) {
                String longi = request.getParameter("logitude");
                String latti = request.getParameter("lattitude");
                request.setAttribute("longi", longi);
                request.setAttribute("latti", latti);
                System.out.println(latti + "," + longi);
                request.getRequestDispatcher("openMapWindowView").forward(request, response);
                return;
            }

            if (task.equals("viewImage")) {
                String meter_reading_id = request.getParameter("meter_reading_id");
                String path = meterDetailModel.getDestination_Path("MeterReading") + meter_reading_id;
                String image = meterDetailModel.getImageName(meter_reading_id);
                path = path + "/" + image;
                response.setContentType("image/jpg");
                ServletOutputStream os = response.getOutputStream();
                FileInputStream is = new FileInputStream(new File(path));
                byte[] buf = new byte[1024 * 1024];
                int nRead = 0;
                while ((nRead = is.read(buf)) != -1) {
                    os.write(buf, 0, nRead);
                }
                os.flush();
                os.close();
                is.close();
                return;
            }
            /*if (task.equals("getImage") || task.equals("getImageThumb")) {
            String path = "C:/ssadvt_repository/CodeRed/";
            if (image_destination == null || image_destination.isEmpty()) {
            image_destination = path + "general/no_image.png";
            String ext = image_destination.split("\\.")[1];
            response.setContentType("image/" + ext);
            } else {
            File f = new File(image_destination);
            if (f.exists()) {
            String ext = image_destination.split("\\.")[1];
            response.setContentType("image/" + ext);
            } else {
            image_destination = path + "general/no_image.png";
            }
            }
            ServletOutputStream os = response.getOutputStream();
            FileInputStream is = new FileInputStream(new File(image_destination));
            byte[] buf = new byte[1024 * 1024];
            int nRead = 0;
            while ((nRead = is.read(buf)) != -1) {
            os.write(buf, 0, nRead);
            }
            os.flush();
            os.close();
            is.close();
            return;
            }*/

            if (task.equals("Delete")) {
                meterDetailModel.deleteRecord(request.getParameter("meter_reading_id"));
            }
            if (task.equals("Save") || task.equals("Save AS New") || task.equals("Update")) {
                String meter_detail_id = request.getParameter("meter_reading_id");
                if (meter_detail_id == null || meter_detail_id.isEmpty()) {
                    itemNameId = 0;
                } else {
                    itemNameId = Integer.parseInt(meter_detail_id);
                }
                if (task.equals("Save AS New")) {
                    itemNameId = 0;
                }
                itemNameBean.setMeter_reading_id(itemNameId);
                itemNameBean.setKey_person_name(request.getParameter("key_person_name"));
                itemNameBean.setMeter_no(request.getParameter("meter_no"));
                itemNameBean.setMeter_reading(request.getParameter("meter_reading"));
                itemNameBean.setDate_time(request.getParameter("date_time") + " " + request.getParameter("time_h") + ":" + request.getParameter("time_m"));
                String no_of_occupants = request.getParameter("no_of_occupants");
                if (no_of_occupants != null && !no_of_occupants.isEmpty()) {
                    itemNameBean.setNumber_of_occupants(Integer.parseInt(no_of_occupants));
                }
                itemNameBean.setRemark(request.getParameter("remark"));
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
                searchMeterNoTo="";searchDateFrom="";searchDateTo="";
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
            noOfRowsInTable = meterDetailModel.getNoOfRows(searchMeterNo,searchMeterNoTo,searchDateFrom,searchDateTo);
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
            List<meterDetail_Bean> list = meterDetailModel.ShowData(lowerLimit, noOfRowsToDisplay, searchMeterNo,searchMeterNoTo,searchDateFrom,searchDateTo);
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
            request.setAttribute("searchMeterNoTo", searchMeterNoTo);
            request.setAttribute("searchDateFrom", searchDateFrom);
            request.setAttribute("searchDateTo", searchDateTo);
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            request.getRequestDispatcher("meterReadingView").forward(request, response);
        } catch (Exception e) {
            System.out.println("MeterReadingController " + e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(MeterReadingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(MeterReadingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
