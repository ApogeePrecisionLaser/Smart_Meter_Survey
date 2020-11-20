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
import com.lowagie.text.Table;
import com.waterworks.tableClasses.SendPDFSchedularBean;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;
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
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.apache.pdfbox.util.PDFMergerUtility;

/**
 *
 * @author Shobha
 */
public class SendPDFSchedularModel extends TimerTask {
    List list1 = new ArrayList();
    private ServletContext ctx;
    private static Connection connection;
    Date dt = new Date();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String cut_dt = df.format(dt);
    String path = "";
//ServletContext ctx = getServletContext();
    public void setCtx(ServletContext ctx) {
        this.ctx = ctx;
    }
    HttpServletRequest request;
    HttpServletResponse response;
 //String email_id = "navitus.viveksingh@gmail.com";
    String emailId = "navitus.viveksingh@gmail.com";
    public void run() {
        try {
            List list = get_AllOverheadTankId();

            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                int overhead_tank_id = (Integer) iterator.next();
                generate_graph(request, response, overhead_tank_id);
               
              //  mailContent(path, email_id);

                //generateREport(overhead_tank_id,ctx);

            }
        } catch (Exception ex) {
            System.out.println(" run() Error: " + ex);
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

    public boolean generate_graph(HttpServletRequest request, HttpServletResponse response, int overheadtank_id) {

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

            list1.add(path);

            boolean writeChartToPDF = writeChartToPDF(generateBarChart(overheadtank_id), response, 600, 700, path);
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
                    //return;
                    //mailContent(path,email_id);//------------------------------------
                } catch (Exception e) {
                    System.out.println(e);
                }
                
            }
          generateREport(overheadtank_id,ctx);
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

   public void generateREport(int overheadTank_id,ServletContext ctx) throws IOException {

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

        //response.setContentType("application/pdf");
       // response.setCharacterEncoding("UTF-8");
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

            list1.add(path);

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, path);
            exporter.exportReport();

            //merg two pdf
            doMerge(list1);

        } catch (Exception e) {
            System.out.println("GeneralModel generateExcelList() JRException: " + e);
        }
        //return reportInbytes;
    }

public String doMerge(List list)  {
        String image_name = "";
         try{

//                 String[] time=date.split(" ");
//                 String s_date=time[0];
//                 String[] curr_array  = s_date.split("-");
//                 String  d = curr_array[2] + "-" + curr_array[1] + "-" +  curr_array[0];
//                 GeneralModel generalModel = new GeneralModel();
                 String img = "C:/ssadvt_repository/prepaid/RideReport/";
//           
                 File file1 = new File("C:/ssadvt_repository/prepaid/RideReport/");
        if (!file1.exists()) {
            file1.mkdirs();
        }
                 // path = path + "/" +reportName;
//           // String img="C:/ssadvt_repository/prepaid/RideReport/"+d;
            //image_name = d+"_"+id+".pdf";
                 image_name = "abc"+".pdf";
           String destination = img + "/"+ image_name;
           Iterator itr = list.iterator();
           /* File destinationDir = new File(destination_path + "temp_single_bill");  //C:/ssadvt_repository/MPEB/temp_service_connection
            if (!destinationDir.isDirectory()) {
                destinationDir.mkdirs();
            }*/

           File file=null;
            PDFMergerUtility ut = new PDFMergerUtility();
            while (itr.hasNext()) {
                String bill_image = itr.next().toString();
            String ext = bill_image.substring(bill_image.lastIndexOf(".") + 1);
                File f = null;
                if (ext.equals("pdf")) {
                    f = new File(bill_image);
                } else {
                    //bill_image = imageToPdfConvert(bill_image);
                   // f = new File(bill_image);
                }

                if (f.exists()) {
                    file = new File(bill_image);
                }
                if (!f.exists()) {
                    //file = new File(destination_path + "general\\bill_not_found.pdf");
                }
                ut.addSource(file);

            }
            ut.setDestinationFileName(destination);
            try {
                ut.mergeDocuments();
                //insertImageRecord(image_name,s_date,id);

                    //this code is for delete pdf

//                      File file2 = new File("C:/ssadvt_repository/prepaid1/temp_pdf");
//                      File[]  myFiles = file2.listFiles();
//                      for (int i=0; i<myFiles.length; i++) {
//                              myFiles[i].delete();
//                 }

                 mailContent(destination,emailId);

            } catch (Exception e) {
                System.out.println("Merge Exception :" + e);
            }
        }catch(Exception e){

        }
        return image_name;
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
        SendPDFSchedularModel.connection = connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println(" closeConnection() Error: " + e);
        }
    }
}
