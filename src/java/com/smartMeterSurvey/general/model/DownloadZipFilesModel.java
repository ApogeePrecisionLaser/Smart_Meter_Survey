/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartMeterSurvey.general.model;

import com.Bean.tableClasses.meterDetail_Bean;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;

/**
 *
 * @author Administrator
 */
public class DownloadZipFilesModel {

    private Connection connection;
    private String OUTPUT_ZIP_FILE;
    private List<String> fileList = new ArrayList<String>();
    private List<String> imagePathList = new ArrayList<String>();

    public void setConnection(Connection con) {
        try {
            connection = con;
        } catch (Exception e) {
            System.out.println("DownloadZipFileModel setConnection() Error: " + e);
        }
    }

    public List<meterDetail_Bean> ShowData1(int lowerLimit, int noOfRowsToDisplay, String searchMeterNo, String searchMeterNoTo, String searchDateFrom, String searchDateTo) {
        List<meterDetail_Bean> list = new ArrayList<meterDetail_Bean>();
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }
//        String query = " SELECT mr.meter_reading_id, md.meter_detail_id, md.date_time as date_time1,md.meter_no, md.meter_reading as meter_reading1, kp.key_person_name,kp.address_line1,kp.mobile_no1, "
//                + "   mr.number_of_occupants,mr.date_time, mr.meter_reading,mr.remark,mr.latitude,mr.longitude, mr.meter_no, gid.image_name, id.destination_path "
//                + "   FROM meter_reading mr LEFT JOIN (meter_detail md, key_person as kp) ON md.meter_detail_id=mr.meter_detail_id "
//                + "   AND kp.key_person_id=md.key_person_id AND md.active='Y' "
//                + "   LEFT JOIN (general_image_details gid, image_destination id) ON id.image_destination_id=gid.image_destination_id AND gid.key_person_id=mr.meter_reading_id  AND gid.image_destination_id=3 "
//                + "   WHERE mr.active='Y' "
//                + " AND IF('" + searchMeterNo + "'='', mr.meter_no LIKE '%%' OR mr.meter_no IS NULL, mr.meter_no>='" + searchMeterNo + "') "
//                + " AND IF('" + searchMeterNoTo + "'='', mr.meter_no LIKE '%%' OR mr.meter_no IS NULL, mr.meter_no<='" + searchMeterNoTo + "') "
//                + " AND IF('" + searchDateFrom + "'='', mr.date_time LIKE '%%' OR mr.date_time IS NULL, mr.date_time>='" + searchDateFrom + "') "
//                + " AND IF('" + searchDateTo + "'='', mr.date_time LIKE '%%' OR mr.date_time IS NULL, mr.date_time<='" + searchDateTo + " 59:59:00') "
//                + addQuery;
        String query = " SELECT * FROM ( SELECT mr.meter_reading_id, md.meter_detail_id, md.date_time as date_time1,md.meter_no as meter_no1, md.meter_reading as meter_reading1, kp.key_person_name,kp.address_line1,kp.mobile_no1, "
                + "   mr.number_of_occupants,mr.date_time, mr.meter_reading,mr.remark,mr.latitude,mr.longitude, mr.meter_no, gid.image_name, id.destination_path "
                + "   FROM meter_reading mr LEFT JOIN (meter_detail md, key_person as kp) ON md.meter_detail_id=mr.meter_detail_id "
                + "   AND kp.key_person_id=md.key_person_id AND md.active='Y' "
                + "   LEFT JOIN (general_image_details gid, image_destination id) ON id.image_destination_id=gid.image_destination_id AND gid.key_person_id=mr.meter_reading_id  AND gid.image_destination_id=3 "
                + "   WHERE mr.active='Y' "
                + " AND IF('" + searchMeterNo + "'='', mr.meter_no LIKE '%%' OR mr.meter_no IS NULL, mr.meter_no>='" + searchMeterNo + "') "
                + " AND IF('" + searchMeterNoTo + "'='', mr.meter_no LIKE '%%' OR mr.meter_no IS NULL, mr.meter_no<='" + searchMeterNoTo + "') "
                + " AND IF('" + searchDateFrom + "'='', mr.date_time LIKE '%%' OR mr.date_time IS NULL, mr.date_time>='" + searchDateFrom + "') "
                + " AND IF('" + searchDateTo + "'='', mr.date_time LIKE '%%' OR mr.date_time IS NULL, mr.date_time<='" + searchDateTo + " 59:59:00') "
                + " ORDER BY mr.date_time DESC) as data group by meter_no1 ORDER BY date_time DESC" + addQuery;
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                meterDetail_Bean meterDetail_Bean = new meterDetail_Bean();
                meterDetail_Bean.setMeter_detail_id(rs.getInt("meter_detail_id"));
                meterDetail_Bean.setDate_time1(rs.getString("date_time1"));
                meterDetail_Bean.setMeter_no(rs.getString("meter_no1"));
                meterDetail_Bean.setMeter_reading1(rs.getString("meter_reading1"));
                meterDetail_Bean.setKey_person_name(rs.getString("key_person_name"));
                meterDetail_Bean.setAddress(rs.getString("address_line1"));
                meterDetail_Bean.setMobile_no(rs.getString("mobile_no1"));
                meterDetail_Bean.setNumber_of_occupants(rs.getInt("number_of_occupants"));
                meterDetail_Bean.setDate_time(rs.getString("date_time"));
                meterDetail_Bean.setMeter_reading(rs.getString("meter_reading"));
                meterDetail_Bean.setRemark(rs.getString("remark"));
                meterDetail_Bean.setLatitude(rs.getDouble("latitude"));
                meterDetail_Bean.setLongitude(rs.getDouble("longitude"));
                meterDetail_Bean.setSurvey_meter_no(rs.getString("meter_no"));
                String image_name = rs.getString("image_name");
                meterDetail_Bean.setImage_name(image_name);
                if (image_name != null && !image_name.isEmpty()) {
                    imagePathList.add(rs.getString("destination_path") + rs.getString("meter_reading_id") + "/" + image_name);
                }
                list.add(meterDetail_Bean);
            }
        } catch (Exception e) {
            System.out.println("ShowData ERROR inside MeterReadingModel - " + e);
        }
        return list;
    }

    public void generateExcelReport(String jrxmlFilePath, List list, String path) {
        ByteArrayOutputStream reportInbytes = new ByteArrayOutputStream();
        HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, jrBean);
            //JRXlsExporter exporter = new JRXlsExporter();
            //String path = createAppropriateDirectories1("C:/ssadvt_repository/prepaid/temp_pdf");
            //path = path + "/" +reportName;
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, path);
            exporter.exportReport();
        } catch (Exception e) {
            System.out.println("GeneralModel generateExcelList() JRException: " + e);
        }
        //return reportInbytes;
    }

    public String getImage_name(String gid) {
        String image_name = null;
        try {
            String query = "select image_name from general_image_details where general_image_details_id='" + gid + "'";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            if (rst.next()) {
                image_name = rst.getString("image_name");
            }

        } catch (Exception e) {
            System.out.println("Error in get image name" + e);
        }
        return image_name;
    }

    public String getDestinationPath(String image_name) {
        String destination_path = null;
        try {
            String query = "SELECT id.destination_path , gid.date_time, image_uploaded_for_name "
                    + " FROM image_uploaded_for AS iuf, image_destination AS id, general_image_details As gid "
                    + " WHERE iuf.image_uploaded_for_id=id.image_uploaded_for_id "
                    + " AND id.image_destination_id=gid.image_destination_id "
                    + " AND gid.image_name= '" + image_name + "' ";
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            if (rst.next()) {
                destination_path = rst.getString("destination_path");
            }

        } catch (Exception e) {
            System.out.println("Error in get image name" + e);
        }
        return destination_path;
    }
//       public void zipFolderContents(String sourceDirectory, String zipFileName) {
//        try {
//            FileFilter fileFilter = new FileFilter() {
//
//                public boolean accept(File pathname) {
//                    String fileName = pathname.getName();
//                    int index = fileName.indexOf(".");
//                    String fileExtendsion = fileName.substring(index + 1);
////                    System.out.println(fileExtendsion);
//                    boolean result;
//                    if (fileExtendsion.equals("zip")) {
//                        result = false;
//                    } else {
//                        result = true;
//                    }
//                    return result;
//                }
//            };
//            File file = new File(sourceDirectory);
//            File[] fileList = file.listFiles(fileFilter);
//            ZipEntry[] zipEnteries = new ZipEntry[fileList.length];
//            for (int i = 0; i < fileList.length; i++) {
//                if(fileList[i].isDirectory()){
//                zipEnteries[i] = new ZipEntry(fileList[i].getName());
//                }
//            }
//            FileOutputStream fos = new FileOutputStream(zipFileName);
//            ZipOutputStream zos = new ZipOutputStream(fos);
//            for (int i = 0; i < zipEnteries.length; i++) {
//                zos.putNextEntry(zipEnteries[i]);
//                FileInputStream fis = new FileInputStream(fileList[i]);
//                byte[] data = new byte[1000];
//                int result = fis.read(data);
//                while (result != -1) {
//                    zos.write(data, 0, result);
//                    result = fis.read(data);
//                }
//            }
//            zos.finish();
//            zos.flush();
//            zos.close();
//            fos.flush();
//            fos.close();
//        } catch (Exception ex) {
//            System.out.println("TempQuotationModel zipFolderContents() Error:" + ex);
//        }
//    }
//        public  void zipDir(String zipFileName, String dir) throws Exception {
//    File dirObj = new File(dir);
//    ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
//    System.out.println("Creating : " + zipFileName);
//    addDir(dirObj, out);
//    out.close();
//  }
//       public  void addDir(File dirObj, ZipOutputStream out) throws IOException {
//    File[] files = dirObj.listFiles();
//    byte[] tmpBuf = new byte[1024];
//
//    for (int i = 0; i < files.length; i++) {
//      if (files[i].isDirectory()) {
//        addDir(files[i], out);
//        continue;
//      }
//      //String  source =  .substring(SOURCE_FOLDER.lastIndexOf("\\")+1, SOURCE_FOLDER.length())
//      FileInputStream in = new FileInputStream(files[i].getAbsolutePath());
//      System.out.println(" Adding: " + files[i].getAbsolutePath());
//      out.putNextEntry(new ZipEntry(files[i].getPare));
//      int len;
//      while ((len = in.read(tmpBuf)) > 0) {
//        out.write(tmpBuf, 0, len);
//      }
//      out.closeEntry();
//      in.close();
//    }
//  }
//
//

    public void zipfolder(String file_name, String SOURCE_FOLDER) {

        OUTPUT_ZIP_FILE = file_name;
        generateFileList(new File(SOURCE_FOLDER), SOURCE_FOLDER);
        //   String sourecfolder = "C:/ssadvt_repository/MPEB/Files";

        zipIt(OUTPUT_ZIP_FILE, SOURCE_FOLDER);

    }

    public void zipIt(String zipFile, String SOURCE_FOLDER) {
        byte[] buffer = new byte[2048];
        String source = "";
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        //  List<String>  fileList = new ArrayList<String>();
        try {
            try {

                source = SOURCE_FOLDER.substring(SOURCE_FOLDER.lastIndexOf("\\") + 1, SOURCE_FOLDER.length());
            } catch (Exception e) {

                source = SOURCE_FOLDER;
            }
            System.out.println("source name" + source);
            fos = new FileOutputStream(zipFile);
            zos = new ZipOutputStream(fos);

            System.out.println("Output to Zip : " + zipFile);
            FileInputStream in = null;

            for (String file : fileList) {

                System.out.println("File Added : " + file);
                ZipEntry ze = new ZipEntry(source + File.separator + file);
                zos.putNextEntry(ze);

                try {
                    in = new FileInputStream(SOURCE_FOLDER + File.separator + file);
                    int len;
                    while ((len = in.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                } catch (Exception e) {
                    System.out.println("File Added : " + file);
                } finally {
                    in.close();
                }
            }

            zos.closeEntry();
            System.out.println("Folder successfully compressed");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                zos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void generateFileList(File node, String SOURCE_FOLDER) {
        try {
            // add file only


            if (node.isFile()) {
                fileList.add(generateZipEntry(node.toString(), SOURCE_FOLDER));

            }

            if (node.isDirectory()) {
                String[] subNote = node.list();
                for (String filename : subNote) {
                    generateFileList(new File(node, filename), SOURCE_FOLDER);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPaymentReqNo(String gid) {
        String payment_req_no = null;
        try {
            String query = "SELECT payment_req_no FROM payment_requisition "
                    + "where general_image_details_id='" + gid + "' and active='Y' group by payment_req_no";

            ResultSet rst = connection.prepareStatement(query).executeQuery();
            if (rst.next()) {
                payment_req_no = rst.getString("payment_req_no");
            }

        } catch (Exception e) {
            System.out.println("Error in get image name" + e);
        }
        return payment_req_no;
    }

    public List<String> getImageName(String meter_name_auto, String bill_month) {
        List<String> list = new ArrayList<String>();
        String img_name = "";
        img_name = bill_month + "_" + meter_name_auto;
        try {
            String query = "select g.image_name  from general_image_details as g  "
                    + "Where SUBSTRING_INDEX(g.image_name,'_',2) = '" + img_name + "' "
                    + "or SUBSTRING_INDEX(g.image_name,'.',1) = '" + img_name + "' ";

            ResultSet rst = connection.prepareStatement(query).executeQuery();
            while (rst.next()) {
                list.add(rst.getString("image_name"));
            }

        } catch (Exception e) {
            System.out.println("Error in get image name" + e);
        }
        return list;
    }

    public String getMeterNameAuto(String msn) {
        String meter_name_auto = null;
        try {
            String query = "select meter_name_auto from meters "
                    + "where meter_service_number='" + msn + "' and final_revision='VALID';";

            ResultSet rst = connection.prepareStatement(query).executeQuery();
            if (rst.next()) {
                meter_name_auto = rst.getString("meter_name_auto");
            }

        } catch (Exception e) {
            System.out.println("Error in get image name" + e);
        }
        return meter_name_auto;
    }

    private String generateZipEntry(String file, String SOURCE_FOLDER) {

        return file.substring(SOURCE_FOLDER.length() + 1, file.length());
    }

    public boolean deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDirectory(children[i]);
                if (!success) {
                    return false;
                }
            }
        }

        // either file or an empty directory
        System.out.println("removing file or directory : " + dir.getName());
        return dir.delete();
    }

    public List<String> getImagePathList() {
        return imagePathList;
    }

    public List<String> getClientData() {
        List list = new ArrayList();
        String query = "SELECT client_email from client_details c ";

        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("client_email"));
            }
        } catch (Exception e) {
            System.out.println("Error DownloadZipFilesModel getClientData() : " + e);
        }

        return list;
    }
}
