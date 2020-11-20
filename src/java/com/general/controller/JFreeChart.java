/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.general.controller;

import com.connection.DBConnection.DBConnection;
import com.general.model.JFreeChartModel;
import com.itextpdf.text.Document;

import com.itextpdf.text.pdf.DefaultFontMapper;
import com.itextpdf.text.pdf.FontMapper;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.Table;


import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.design.JasperDesign;

/**
 *
 * @author jpss
 */
public class JFreeChart extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext ctx = getServletContext();
        JFreeChartModel jFreeChartModel = new JFreeChartModel();

        try {
            jFreeChartModel.setConnection(DBConnection.getConnection(ctx));
        } catch (Exception e) {
            System.out.println("error in JFreeChart setConnection() calling try block" + e);
        }
        try {
            String requestprinrt = request.getParameter("requestprinrt");
            if (requestprinrt.equals("VIEW_GRAPH")) {
                String ohlevel_id = request.getParameter("ohlevel_id");
                int overheadtank_id = jFreeChartModel.getOverHeadTankid(Integer.parseInt(ohlevel_id));
                //  jFreeChartModel.setMeterRepositoryPath(RepositryPath.getRepPath(jFreeChartModel.getConnection(), jFreeChartModel.getCompanyCityCredentials(meter_id)));
                String current_date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
                String folder_path = "C:\\ssadvt_repository\\SmartMeterSurvey\\chart\\";
                File file = new File(folder_path);
                if (!file.exists()) {
                    file.mkdirs();
                }
                String path = folder_path + current_date + "_" + overheadtank_id + ".pdf";
                boolean writeChartToPDF = writeChartToPDF(jFreeChartModel.generateBarChart(overheadtank_id), response, 600, 700, path);
                if (writeChartToPDF == true) {
                    System.out.println("path " + path + " received.");
                    try {
                        OutputStream os = response.getOutputStream();
                        File f = new File(path);
                        InputStream is = new FileInputStream(f);
                        byte[] buf = new byte[32 * 1024];
                        int nRead = 0;
                        while ((nRead = is.read(buf)) != -1) {
                            os.write(buf, 0, nRead);
                        }
                        os.flush();
                        os.close();
                        return;
                    } catch (Exception e) {
                    }

                }
            }

           if (requestprinrt.equals("VIEW_GRAPH1")) {
                String ohlevel_id = request.getParameter("ohlevel_id");
                int overheadtank_id = jFreeChartModel.getOverHeadTankid(Integer.parseInt(ohlevel_id));
                String searchDateFrom = request.getParameter("searchDateFrom");
                String searchDateTo = request.getParameter("searchDateTo");
                String from_hr = request.getParameter("from_hr");
                String from_min = request.getParameter("from_min");
                String to_hr = request.getParameter("to_hr");
                String to_min = request.getParameter("to_min");
                if (searchDateFrom != null && !searchDateFrom.isEmpty()) {
                    String[] sdate_array = searchDateFrom.split("-");
                    searchDateFrom = sdate_array[2] + "-" + sdate_array[1] + "-" + sdate_array[0] + " " + from_hr + ":" + from_min;
                }
                if (searchDateTo != null && !searchDateTo.isEmpty()) {
                    String[] sdate_array = searchDateTo.split("-");
                    searchDateTo = sdate_array[2] + "-" + sdate_array[1] + "-" + sdate_array[0] + " " + to_hr + ":" + to_min;
                }
                //  jFreeChartModel.setMeterRepositoryPath(RepositryPath.getRepPath(jFreeChartModel.getConnection(), jFreeChartModel.getCompanyCityCredentials(meter_id)));
                String current_date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
                String folder_path = "C:\\ssadvt_repository\\SmartMeterSurvey\\chart1\\";
                File file = new File(folder_path);
                if (!file.exists()) {
                    file.mkdirs();
                }
                String path = folder_path + current_date + "_" + overheadtank_id + ".pdf";
                boolean writeChartToPDF = writeChartToPDF(jFreeChartModel.generateBarChart1(overheadtank_id,searchDateFrom,searchDateTo), response, 600, 700, path);
                if (writeChartToPDF == true) {
                    System.out.println("path " + path + " received.");
                    try {
                        OutputStream os = response.getOutputStream();
                        File f = new File(path);
                        InputStream is = new FileInputStream(f);
                        byte[] buf = new byte[32 * 1024];
                        int nRead = 0;
                        while ((nRead = is.read(buf)) != -1) {
                            os.write(buf, 0, nRead);
                        }
                        os.flush();
                        os.close();
                        return;
                    } catch (Exception e) {
                    }

                }
            }
        } catch (Exception e) {
            System.out.println("JFreeChart main thread " + e);
        }
    }

    public static boolean writeChartToPDF(org.jfree.chart.JFreeChart chart, HttpServletResponse response, int width, int height, String fileName) {
        boolean isChartGenerated = false;
        PdfWriter writer = null;

        Document document = new Document();

        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();
            PdfContentByte contentByte = writer.getDirectContent();
            PdfTemplate template = contentByte.createTemplate(width, height);
            Graphics2D graphics2d = template.createGraphics(width, height, (FontMapper) new DefaultFontMapper());
            Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, width,
                    height);

            chart.draw(graphics2d, rectangle2d);

            graphics2d.dispose();
            contentByte.addTemplate(template, 0, 0);

            Table t = new Table(10, 20);
            com.lowagie.text.pdf.PdfPTable pt = t.createPdfPTable();
            JasperDesign jp = new JasperDesign();
            // JRBand jr= new J
            //jp.setTitle();
        } catch (Exception e) {
            System.out.println("JFreeeChart writeChartToPDF - " + e);
        }
        document.close();
        isChartGenerated = true;
        return isChartGenerated;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
