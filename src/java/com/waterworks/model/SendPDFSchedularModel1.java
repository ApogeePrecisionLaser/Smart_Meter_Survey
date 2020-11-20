/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.waterworks.model;

import com.general.model.GeneralModel;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.DefaultFontMapper;
import com.itextpdf.text.pdf.FontMapper;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64.InputStream;
import com.itextpdf.text.pdf.codec.Base64.OutputStream;
import com.lowagie.text.Table;
import com.waterworks.tableClasses.SendPDFSchedularBean;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Shobha
 */
public class SendPDFSchedularModel1 {

    //private ServletContext ctx;
    private static Connection connection;
    Date dt = new Date();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String cut_dt = df.format(dt);
    String path = "";
private String driver, url, user, password;
//    public void setCtx(ServletContext ctx) {
//        this.ctx = ctx;
//    }

public void setConnection() {
        try {
            Class.forName(driver);
            // connection = DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            connection = (Connection) DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("WattageTypeModel setConnection() Error: " + e);
        }
    }
     public List get_AllOverheadTankId() {
        List list = new ArrayList();
        String query = "select overheadtank_id from ohlevel o group by o.overheadtank_id ";
        try {
            PreparedStatement ps1 = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps1.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("overheadtank_id");
                list.add(id);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public boolean generate_graph(HttpServletRequest request, HttpServletResponse response, int overheadtank_id,ServletContext ctx) {

        try {
            //String requestprinrt = request.getParameter("requestprinrt");
            // if (requestprinrt.equals("VIEW_GRAPH")) {
            //String ohlevel_id = request.getParameter("ohlevel_id");
            //int overheadtank_id = jFreeChartModel.getOverHeadTankid(Integer.parseInt(ohlevel_id));
            //  jFreeChartModel.setMeterRepositoryPath(RepositryPath.getRepPath(jFreeChartModel.getConnection(), jFreeChartModel.getCompanyCityCredentials(meter_id)));
            String current_date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
            String folder_path = "C:\\ssadvt_repository\\SmartMeterSurvey\\chart\\";
            File file = new File(folder_path);
            if (!file.exists()) {
                file.mkdirs();
            }
            path = folder_path + current_date + "_" + overheadtank_id + ".pdf";
            boolean writeChartToPDF = writeChartToPDF(generateBarChart(overheadtank_id), response, 600, 700, path);
            if (writeChartToPDF == true) {
                System.out.println("path " + path + " received.");
                try {
                    ServletOutputStream os = response.getOutputStream();
                    File f = new File(path);
                    FileInputStream is = new FileInputStream(f);
                    byte[] buf = new byte[32 * 1024];
                    int nRead = 0;
                    while ((nRead = is.read(buf)) != -1) {
                        os.write(buf, 0, nRead);
                    }
                    os.flush();
                    os.close();
                    //return;
                    //mailContent(path,email_id);//------------------------------------
                } catch (Exception e) {
                    System.out.println(e);
                }

            }
          generateREport(overheadtank_id, request, response,ctx);
        } catch (Exception e) {
            System.out.println("JFreeChart main thread " + e);
        }

        return true;
    }

    public org.jfree.chart.JFreeChart generateBarChart(int overheadtank_id) {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        org.jfree.chart.JFreeChart chart = null;
        String time = "";
        String current_date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        try {
            for (int i = 0; i < 24; i++) {
                for (int j = 0; j < 60; j = j + 5) {
                    if (i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 7 || i == 8 || i == 9 || i == 0) {
                        time = "0" + i + ":" + j;
                    } else {
                        time = "" + i + ":" + j;
                    }
                    String query = "SELECT * FROM ohlevel where overheadtank_id='" + overheadtank_id + "' "
                            + " and IF('" + current_date + "' = '', date_time LIKE '%%', date_time LIKE'" + current_date + " " + time + "%')";

                    PreparedStatement pstmt = connection.prepareStatement(query);

                    ResultSet rset = pstmt.executeQuery();
                    if (rset.next()) {
                        dataSet.setValue(rset.getInt("remark"), "Level", time);
                    }
                }
            }
            chart = ChartFactory.createLineChart(
                    "OverHeadTank Level Representation ", "Time", "Level",
                    dataSet, PlotOrientation.HORIZONTAL, false, true, false);
        } catch (Exception e) {
            System.out.println("JFreeChartModel generateBarChart() Error: " + e);
        }
        return chart;
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

    public String mailContent(String FileNamePath, String emailId) {
        /* Assumptions:
         * 1) "toEmailList" contains at least one entry (email address).
         * "toEmailList" is the list of all primary recipients e.g. "jpss1277@gmail.com".
         * 2) "bccEmailList" is the list of all Blind Carbon Copy(bcc) recipients (here it is sold_by person).
         */
        String user_name = "";
        String user_password = "";
        try {
            // Code to retrieve the email id, and password from which email will be send.
            String query = "SELECT m.sender_user_name, m.sender_password FROM mail AS m ";
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                user_name = rset.getString("sender_user_name");
                user_password = rset.getString("sender_password");
            } else {
                return "No mails were sent. Error: No records were found in Mail Info table.";
            }

            List sent_tolist = new ArrayList();
            sent_tolist.add(emailId);
            String host = "smtp.gmail.com";
            String port = "465";
            String subject = "Pre Registration Form Report";
            String bodyText = "Dear Sir/Madam,\n"
                    + "Please find attached PDF Report of your Pre Registration Form \n";

            SendMailModel sendMailModel = new SendMailModel(connection);
            sendMailModel.setHost(host);
            sendMailModel.setPort(port);
            sendMailModel.setUser_name(user_name);
            sendMailModel.setUser_password(user_password);
            sendMailModel.setTo(sent_tolist);
            sendMailModel.setSubject(subject);
            sendMailModel.setBodyText(bodyText);
            sendMailModel.setFileName(FileNamePath);
            return sendMailModel.sendMail();
            // System.out.println("mailed Content successfully");
        } catch (Exception e) {
            System.out.println("PreRegistrationModel - mailContent Error: " + e);
            return e.toString();
        }

    }

    public void generateREport(int overheadTank_id, HttpServletRequest request, HttpServletResponse response,ServletContext ctx) throws IOException {
        
        String jrxmlFilePath;
        List list = null;
        list = getAllRecords(overheadTank_id);

        if(list.size()>0){
            try{
         String folder_path = "C:\\ssadvt_repository\\SmartMeterSurvey\\chart3\\";
        File file = new File(folder_path);
        if (!file.exists()) {
            file.mkdirs();
        }
        
        response.setContentType("application/pdf");
        response.setCharacterEncoding("UTF-8");
        //ServletOutputStream servletOutputStream = response.getOutputStream();
        jrxmlFilePath = ctx.getRealPath("/report/waterWorks/SendPDFSchedular.jrxml");
        //list = getAllRecords(overheadTank_id);

       
        //byte[] reportInbytes = GeneralModel.generateRecordList(jrxmlFilePath, list);
        //response.setContentLength(reportInbytes.length);
        //servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
        //servletOutputStream.flush();
        //servletOutputStream.close();

       // path = folder_path + cut_dt + "__" + overheadTank_id + ".pdf";
        path = folder_path;

        SavePdf(jrxmlFilePath,list,cut_dt + "__" + overheadTank_id + ".pdf",path);
}
            catch(Exception e){
                System.out.println(e);
            }
        }
    }

     public void SavePdf(String jrxmlFilePath, List list, String reportName,String path) {
        ByteArrayOutputStream reportInbytes = new ByteArrayOutputStream();
        HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, jrBean);
            //JRXlsExporter exporter = new JRXlsExporter();
            //String path = createAppropriateDirectories1("C:/ssadvt_repository/prepaid/temp_pdf");
            path = path +reportName;
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, path);
            exporter.exportReport();
        } catch (Exception e) {
            System.out.println("GeneralModel generateExcelList() JRException: " + e);
        }
        //return reportInbytes;
    }

    

    public List<SendPDFSchedularBean> getAllRecords(int overheadtank_id) {
        List<SendPDFSchedularBean> list = new ArrayList<SendPDFSchedularBean>();
        int m_lekage_value;
        int e_lekage_value;
        String query = " select d.type,ol.date_time,ol.remark from watertreatmentplant AS wtp,overheadtank AS oht, "
                + " ohlevel as ol,distribution as d "
                + " where oht.watertreatmentplant_id = wtp.watertreatmentplant_id "
                + "  and ol.overheadtank_id = oht.overheadtank_id and ol.ohlevel_id=d.ohlevel_id  "
                + " and ol.overheadtank_id=  "+overheadtank_id
                + "  and ol.date_time between '" + cut_dt + " 00:01:00' and '" + cut_dt + " 11:59:59'";


        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                SendPDFSchedularBean bean = new SendPDFSchedularBean();
                bean.setM_type(rset.getString("type"));
                bean.setM_date_time(rset.getString("date_time"));
                String m_level = rset.getString("remark");
                bean.setM_level(rset.getString("remark"));


                String query2 = " select d.type,ol.date_time,ol.remark from watertreatmentplant AS wtp,overheadtank AS oht, "
                        + " ohlevel as ol,distribution as d "
                        + " where oht.watertreatmentplant_id = wtp.watertreatmentplant_id "
                        + "  and ol.overheadtank_id = oht.overheadtank_id and ol.ohlevel_id=d.ohlevel_id  "
                        + "  and ol.date_time between '" + cut_dt + " 13:01:00' and '" + cut_dt + " 23:59:59'";

                try {
                    ResultSet rs2 = connection.prepareStatement(query2).executeQuery();
                    if (rs2.next()) {

                        bean.setE_type(rs2.getString("type"));
                        bean.setE_date_time(rs2.getString("date_time"));
                        String e_level = rset.getString("remark");
                        bean.setE_level(rset.getString("remark"));

                    }
                } catch (Exception e) {
                    System.out.println(e);
                }

                String query4 = "select max(ol.remark) as m_max,min(ol.remark) as m_min  from distribution d,ohlevel ol where d.ohlevel_id=ol.ohlevel_id "
                        + " and ol.overheadtank_id='" + overheadtank_id + "' and "
                        + " ol.date_time between '" + cut_dt + " 00:01:00' and '" + cut_dt + " 11:59:59'";
                try {
                    ResultSet rs4 = connection.prepareStatement(query4).executeQuery();
                    if (rs4.next()) {
                        bean.setM_max(rs4.getString("m_max"));
                        bean.setM_min(rs4.getString("m_min"));
                        m_lekage_value = Integer.parseInt(rs4.getString("m_max")) - Integer.parseInt(m_level);
                        bean.setM_lekage_value(m_lekage_value);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
                String query5 = "select max(ol.remark) as e_max,min(ol.remark) as e_min  from distribution d,ohlevel ol where d.ohlevel_id=ol.ohlevel_id "
                        + " and ol.overheadtank_id='" + overheadtank_id + "' and "
                        + " ol.date_time between '" + cut_dt + " 12:01:00' and '" + cut_dt + " 23:59:59'";
                try {
                    ResultSet rs5 = connection.prepareStatement(query5).executeQuery();
                    if (rs5.next()) {
                        bean.setE_max(rs5.getString("e_max"));
                        bean.setE_min(rs5.getString("e_min"));
                        e_lekage_value = Integer.parseInt(rs5.getString("e_max")) - Integer.parseInt(m_level);
                        bean.setE_lekage_value(e_lekage_value);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }

                list.add(bean);
            }



        } catch (Exception e) {
            System.out.println(e);
        }

        return list;
    }


  public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        SendPDFSchedularModel1.connection = connection;
    }
//    public void setConnection(Connection con) {
//        try {
//
//            connection = con;
//        } catch (Exception e) {
//            System.out.println("PaymentStatusModel setConnection() Error: " + e);
//        }
//    }



    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

}
