/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartMeterSurvey.general.controller;

import com.Webservice.Model.SmartMeterSurveyServiceModel;
import com.connection.DBConnection.DBConnection;
import com.general.model.spreadsheet;
import com.layer.modellLayer.MeterReadingModel;
import com.smartMeterSurvey.general.model.DownloadZipFilesModel;
//import com.meter.general.model.ViewBillImageModel;
import com.util.GetDate;
//import com.meter.util.RepositryPath;
import com.util.ZipUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.apache.jasper.tagplugins.jstl.core.Catch;

/**
 *
 * @author Administrator
 */
public class DownloadZipFilesController extends HttpServlet {

    private Connection connection;
    private static String repository_path;
    private File file = null;
    String tempDir = "C:\\zip";
    private String meter_service_number;
    private String payment_req_no;
    private String message;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext ctx = getServletContext();

        DownloadZipFilesModel downloadZipFilesModel = new DownloadZipFilesModel();
         SmartMeterSurveyServiceModel sm = new SmartMeterSurveyServiceModel();
         MeterReadingModel meterDetailModel = new MeterReadingModel();
        //ViewBillImageModel viewBillImageModel = new ViewBillImageModel();
//        HttpSession session = request.getSession(false);
//        if (session == null || (String) session.getAttribute("user_name") == null) {
//            response.sendRedirect("beforeLoginPage");
//            return;
//        }
        String task = "";
        task = request.getParameter("task");
        if (task == null) {
            task = "";
        }
        String searchMeterNo = request.getParameter("searchMeterNo");
            String searchMeterNoTo = request.getParameter("searchMeterNoTo");
            String searchDateFrom = request.getParameter("searchDateFrom");
            String searchDateTo = request.getParameter("searchDateTo");
        try {
            connection = DBConnection.getConnectionForUtf(ctx);
            repository_path = "";//RepositryPath.getRepositoryPath(connection);
            downloadZipFilesModel.setConnection(connection);
             sm.setConnection(connection);
             meterDetailModel.setConnection(connection);
            
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
            //viewBillImageModel.setConnection(connection);
        } catch (Exception e) {
            System.out.println("error in ViewBillImageController setConnection() calling try block" + e);
        }

        if (task.equals("generateReportZipFile")) {
            String jrxmlFilePath;
            List list = null;
            response.setContentType("application/vnd.ms-excel");
            //response.setHeader("Content-Disposition", "attachment; filename=meter_reading.xls");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            jrxmlFilePath = ctx.getRealPath("/report/meter_reading.jrxml");
            list = downloadZipFilesModel.ShowData1(-1, -1, searchMeterNo,searchMeterNoTo,searchDateFrom,searchDateTo);

            // code to generate spreadsheet on drive -- starts here

            String[] array = new String[]{"S.No.", "Meter Installation Date", "Meter No.", "Survey Meter No.", "Meter Start Reading", "Key Person Name", "Address", "Mobile No.", "No. of Occupants", "Date & Time", "Meter Reading", "Remark", "GPS Link", "Image"};
            List headerList = Arrays.asList(array);
            int dataListSize = list.size()+2;
            int headerListSize = headerList.size();
            String ExcelReportName = "Meter Reading";
            String sheetRange = "Sheet1!A1:N" + dataListSize;
            try {
                spreadsheet.uploadGoogleSheet(list, headerList, dataListSize, headerListSize, ExcelReportName, sheetRange, connection);
            } catch (Exception ex) {
                System.out.print("Exception in DownloadZipFilesController generateReportZipFile block - " + ex);
            }

            List listAll = meterDetailModel.ShowData(-1, -1, searchMeterNo,searchMeterNoTo,searchDateFrom,searchDateTo);
           String ExcelReportName1 = "Meter Reading All Data";
           int dataListSize1 = listAll.size()+2;
           String sheetRange1 = "Sheet1!A1:N" + dataListSize1;
            try {
                spreadsheet.uploadGoogleSheet(listAll, headerList, dataListSize1, headerListSize, ExcelReportName1, sheetRange1, connection);
            } catch (Exception ex) {
                System.out.print("Exception in DownloadZipFilesController generateReportZipFile block 2 - " + ex);
            }

            // code to generate spreadsheet on drive -- ends here

           boolean isCreated = true;
           File tempFolder1 = new File(tempDir);
            if (!tempFolder1.exists()) {
              isCreated = tempFolder1.mkdirs();
           }
           downloadZipFilesModel.generateExcelReport(jrxmlFilePath, list, tempDir + "/" + "MeterSurveyReading.xls");
          task = "downloadZip"; 
        }


        if (task.equals("downloadZip")) {

//            meter_service_number = request.getParameter("meter_service_number");
//            String billmonth = request.getParameter("bill_month");
//            String gid = request.getParameter("gid");
//            String[] bill_month = billmonth.split(",");
//            String[] general_image_id = gid.split(",");
            File destFile = null;
            File tempFolder1 = null;
            boolean isCreated = true;
            String source_dierectory = "";
            try {

                //for (int i = 0; i <= bill_month.length; i++) {
                    try {
                      //  String billMonth = bill_month[i];
                        // for (int j = 0; j <= general_image_id.length; j++) {

//                        String[] parts = billMonth.split(",");
//                        String part = parts[0];
//                        String[] part1 = part.split("-");
//                        String month = part1[0];
//                        String year = part1[1];
//                        String full_month = GetDate.getFullMonthName(month);
//                        String general_image_detail_id = general_image_id[i];
                       // if (general_image_detail_id != null) {
                            tempFolder1 = new File(tempDir);//+ File.separator + billMonth
//                            if (!tempFolder1.exists()) {
//                                isCreated = tempFolder1.mkdirs();
//                            }
                         //   String imagename = downloadZipFilesModel.getImage_name(general_image_detail_id);

                          //  if (imagename != null) {

//                                String destination_path = downloadZipFilesModel.getDestinationPath(imagename);

                               /// if (destination_path.endsWith("meter")) {
                                  //  String meter_name_auto = downloadZipFilesModel.getMeterNameAuto(meter_service_number);
                                    List<String> list = downloadZipFilesModel.getImagePathList();
                                    Iterator<String> itr = list.iterator();
                                    while (itr.hasNext()) {
                                        String img_path = itr.next();
                                        String[] image_name = img_path.split("/");
                                        String name = image_name[image_name.length - 1];
//                                        source_dierectory = destination_path + File.separator + year + File.separator + full_month + File.separator + img_name;
//                                        destFile = new File(tempFolder1 + "/" + img_name);
                                        file = new File(img_path);
                                        if (file.exists()) {
                                            FileUtils.copyFile(file, new File(tempDir + "/" + name));
                                        }
                                    }

                              //  }
//                                if (destination_path.endsWith("requisition")) {
//                                    payment_req_no = downloadZipFilesModel.getPaymentReqNo(general_image_detail_id);
//                                    source_dierectory = destination_path + File.separator + year + File.separator + full_month + File.separator + payment_req_no + File.separator + imagename;
//                                    destFile = new File(tempFolder1 + "/" + imagename);
//                                    file = new File(source_dierectory);
//                                    if (file.exists()) {
//                                        FileUtils.copyFile(file, destFile);
//                                    }
//                                }

                          //  }
                      //  }
                    } catch (Exception e) {
                        System.out.println("Exception :" + e);
                        message = "No Image is Found ";
                    }
                //}
                if (tempFolder1.exists()) {
                    String zip_path = tempDir + "/" + "WaterMeterSurvey" + "/" + "WaterMeterSurvey";//repository_path + "statement" + File.separator + meter_service_number;
                    File tempFolder = new File(zip_path);
                    if (!tempFolder.exists()) {
                        isCreated = tempFolder.mkdirs();
                    }
                    String zip_name = zip_path + ".zip";
                    downloadZipFilesModel.zipfolder(zip_name, tempDir);
                    File f = new File(zip_name);
                    FileInputStream fis = new FileInputStream(f);
                    byte[] buf = new byte[2048];
                    response.setContentType("application/zip");
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + "WaterMeterSurvey" + ".ZIP\"");
                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
                    response.setContentLength(fis.available());
                    ServletOutputStream os = response.getOutputStream();
                    BufferedOutputStream out = new BufferedOutputStream(os);
                    int ch = 0;
                    while ((ch = bis.read()) != -1) {
                        out.write(ch);
                    }

                    bis.close();
                    fis.close();
                    out.close();
                    os.close();
                    response.flushBuffer();
                    downloadZipFilesModel.deleteDirectory(tempFolder);
                }


                File deleteFile = new File(tempDir);
                downloadZipFilesModel.deleteDirectory(deleteFile);


            } catch (Exception e) {
                System.out.println("Exception :" + e);
            }
        }
        if (task.equals("downloadRequisition")) {
            try {

                File destFile = null;
                File tempFolder1 = null;
                boolean isCreated = true;
                //List<String> list = new ArrayList<String>();
                String image_name = "";
                String billmonth = request.getParameter("bill_month");
                int gid = Integer.parseInt(request.getParameter("gid"));
                String requisition_no = request.getParameter("payment_req_no");
                image_name = request.getParameter("image_name");
                if (image_name == null || image_name.isEmpty()) {
                    // image_name = viewBillImageModel.getImageName(gid);
                }
                //requisition_no = viewBillImageModel.getRequisitionNUM(image_name);
                String destination = "";//viewBillImageModel.getDestinationPath(image_name, requisition_no);
                File fi = new File(destination);
                if (fi.listFiles() != null) {
                    if (fi.listFiles().length > 0) {
                        File[] listOfFiles = fi.listFiles();

                        tempFolder1 = new File(tempDir + File.separator + billmonth);
                        if (!tempFolder1.exists()) {
                            isCreated = tempFolder1.mkdirs();
                        }

                        for (int i = 0; i < listOfFiles.length; i++) {
                            if (listOfFiles[i].isFile()) {
                                String destination_path = destination + listOfFiles[i].getName();
                                String source_dierectory = destination_path;
                                //list.add(destination + listOfFiles[i].getName());
                                destFile = new File(tempFolder1 + "/" + listOfFiles[i].getName());
                                file = new File(source_dierectory);
                                if (file.exists()) {
                                    FileUtils.copyFile(file, destFile);
                                }
                                System.out.println("File " + listOfFiles[i].getName());
                            }
                        }
                    }
                }

                if (tempFolder1.exists()) {
                    String zip_path = repository_path + "donloaded_Requisition" + File.separator + requisition_no;
                    File tempFolder = new File(zip_path);
                    if (!tempFolder.exists()) {
                        isCreated = tempFolder.mkdirs();
                    }
                    String zip_name = zip_path + ".zip";
                    File zf = new File(zip_name);
                    downloadZipFilesModel.zipfolder(zip_name, tempDir);
                    File f = new File(zip_name);
                    FileInputStream fis = new FileInputStream(f);
                    byte[] buf = new byte[2048];
                    response.setContentType("application/zip");
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + requisition_no + ".ZIP\"");
                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
                    response.setContentLength(fis.available());
                    ServletOutputStream os = response.getOutputStream();
                    BufferedOutputStream out = new BufferedOutputStream(os);
                    int ch = 0;
                    while ((ch = bis.read()) != -1) {
                        out.write(ch);
                    }

                    bis.close();
                    fis.close();
                    out.close();
                    os.close();
                    response.flushBuffer();
                    zf.delete();
                    downloadZipFilesModel.deleteDirectory(tempFolder);
                }
                File deleteFile = new File(tempDir);
                downloadZipFilesModel.deleteDirectory(deleteFile);
            } catch (Exception e) {
                System.out.println("Exception :" + e);
            }


        }

        //    request.setAttribute("message",message);
        request.getRequestDispatcher("/billStatement_view").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
