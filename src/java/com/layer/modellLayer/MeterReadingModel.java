/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.layer.modellLayer;

import com.Bean.tableClasses.meterDetail_Bean;
import com.waterworks.tableClasses.OverHeadTank;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;

/**
 *
 * @author Shubham
 */
public class MeterReadingModel {

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";

    public List<meterDetail_Bean> ShowData(int lowerLimit, int noOfRowsToDisplay, String searchMeterNo, String searchMeterNoTo, String searchDateFrom, String searchDateTo) {
        List<meterDetail_Bean> list = new ArrayList<meterDetail_Bean>();
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }
//        String query = " SELECT * FROM ( SELECT md.meter_detail_id, mr.meter_reading_id, mr.meter_no as meter_no1,md.meter_no as meter_no2, mr.meter_reading, mr.date_time, mr.latitude, "
//                + " mr.longitude, mr.number_of_occupants, mr.remark "
//                + " FROM meter_reading mr Left JOIN meter_detail md ON md.meter_detail_id=mr.meter_detail_id AND md.active='Y'"
//                + " WHERE mr.active='Y' "
//                + " AND IF('" + searchMeterNo + "'='', mr.meter_no LIKE '%%' OR mr.meter_no IS NULL, mr.meter_no>='" + searchMeterNo + "') "
//                + " AND IF('" + searchMeterNoTo + "'='', mr.meter_no LIKE '%%' OR mr.meter_no IS NULL, mr.meter_no<='" + searchMeterNoTo + "') "
//                + " AND IF('" + searchDateFrom + "'='', mr.date_time LIKE '%%' OR mr.date_time IS NULL, mr.date_time>='" + searchDateFrom + "') "
//                + " AND IF('" + searchDateTo + "'='', mr.date_time LIKE '%%' OR mr.date_time IS NULL, mr.date_time<='" + searchDateTo + " 59:59:00') "
//                + " ORDER BY mr.date_time DESC) as data group by meter_no1 ORDER BY date_time DESC"+addQuery;
        String query = " SELECT md.date_time as date_time1,md.meter_detail_id, md.meter_no, mr.meter_reading_id, mr.meter_no, mr.meter_reading, mr.date_time, mr.latitude, kp.key_person_name,kp.address_line1,kp.mobile_no1, "
                + " mr.longitude, mr.number_of_occupants, mr.remark,gid.image_name "
                + " FROM meter_reading mr Left JOIN (meter_detail md, key_person as kp) ON md.meter_detail_id=mr.meter_detail_id AND kp.key_person_id=md.key_person_id AND md.active='Y'"
                + "   LEFT JOIN (general_image_details gid, image_destination id) ON id.image_destination_id=gid.image_destination_id AND gid.key_person_id=mr.meter_reading_id  AND gid.image_destination_id=3 "
                + " WHERE mr.active='Y' "
                + " AND IF('" + searchMeterNo + "'='', mr.meter_no LIKE '%%' OR mr.meter_no IS NULL, mr.meter_no>='" + searchMeterNo + "') "
                + " AND IF('" + searchMeterNoTo + "'='', mr.meter_no LIKE '%%' OR mr.meter_no IS NULL, mr.meter_no<='" + searchMeterNoTo + "') "
                + " AND IF('" + searchDateFrom + "'='', mr.date_time LIKE '%%' OR mr.date_time IS NULL, mr.date_time>='" + searchDateFrom + "') "
                + " AND IF('" + searchDateTo + "'='', mr.date_time LIKE '%%' OR mr.date_time IS NULL, mr.date_time<='" + searchDateTo + " 59:59:00') "
                + addQuery;
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                meterDetail_Bean meterDetail_Bean = new meterDetail_Bean();
                meterDetail_Bean.setMeter_detail_id(rs.getInt("meter_detail_id"));
                meterDetail_Bean.setMeter_reading_id(rs.getInt("meter_reading_id"));
                meterDetail_Bean.setMeter_no(rs.getString("md.meter_no"));
                meterDetail_Bean.setSurvey_meter_no(rs.getString("mr.meter_no"));
                meterDetail_Bean.setMeter_reading(rs.getString("meter_reading"));
                meterDetail_Bean.setDate_time(rs.getString("date_time"));
                meterDetail_Bean.setLatitude(rs.getDouble("latitude"));
                meterDetail_Bean.setLongitude(rs.getDouble("longitude"));
                meterDetail_Bean.setRemark(rs.getString("remark"));
                meterDetail_Bean.setNumber_of_occupants(rs.getInt("number_of_occupants"));
                String image_name = rs.getString("image_name");
                meterDetail_Bean.setImage_name(image_name);
                meterDetail_Bean.setKey_person_name(rs.getString("key_person_name"));
                meterDetail_Bean.setAddress(rs.getString("address_line1"));
                meterDetail_Bean.setMobile_no(rs.getString("mobile_no1"));
                   meterDetail_Bean.setDate_time1(rs.getString("date_time1"));
                list.add(meterDetail_Bean);
            }
        } catch (Exception e) {
            System.out.println("ShowData ERROR inside MeterReadingModel - " + e);
        }
        return list;
    }

    // For Row Apend on spreadsheet
    public List<meterDetail_Bean> ShowData2(int meter_reading_id) {
        List<meterDetail_Bean> list = new ArrayList<meterDetail_Bean>();
       
//        String query = " SELECT * FROM ( SELECT md.meter_detail_id, mr.meter_reading_id, mr.meter_no as meter_no1,md.meter_no as meter_no2, mr.meter_reading, mr.date_time, mr.latitude, "
//                + " mr.longitude, mr.number_of_occupants, mr.remark "
//                + " FROM meter_reading mr Left JOIN meter_detail md ON md.meter_detail_id=mr.meter_detail_id AND md.active='Y'"
//                + " WHERE mr.active='Y' "
//                + " AND IF('" + searchMeterNo + "'='', mr.meter_no LIKE '%%' OR mr.meter_no IS NULL, mr.meter_no>='" + searchMeterNo + "') "
//                + " AND IF('" + searchMeterNoTo + "'='', mr.meter_no LIKE '%%' OR mr.meter_no IS NULL, mr.meter_no<='" + searchMeterNoTo + "') "
//                + " AND IF('" + searchDateFrom + "'='', mr.date_time LIKE '%%' OR mr.date_time IS NULL, mr.date_time>='" + searchDateFrom + "') "
//                + " AND IF('" + searchDateTo + "'='', mr.date_time LIKE '%%' OR mr.date_time IS NULL, mr.date_time<='" + searchDateTo + " 59:59:00') "
//                + " ORDER BY mr.date_time DESC) as data group by meter_no1 ORDER BY date_time DESC"+addQuery;
        String query = " SELECT md.date_time as date_time1,md.meter_detail_id, md.meter_no, mr.meter_reading_id, mr.meter_no, mr.meter_reading, mr.date_time, mr.latitude, kp.key_person_name,kp.address_line1,kp.mobile_no1, "
                + " mr.longitude, mr.number_of_occupants, mr.remark,gid.image_name "
                + " FROM meter_reading mr Left JOIN (meter_detail md, key_person as kp) ON md.meter_detail_id=mr.meter_detail_id AND kp.key_person_id=md.key_person_id AND md.active='Y'"
                + "   LEFT JOIN (general_image_details gid, image_destination id) ON id.image_destination_id=gid.image_destination_id AND gid.key_person_id=mr.meter_reading_id  AND gid.image_destination_id=3 "
                + " WHERE mr.active='Y' and mr.meter_reading_id= "+meter_reading_id;
              
               
               // + addQuery;
        try {
            System.out.println(connection);
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                meterDetail_Bean meterDetail_Bean = new meterDetail_Bean();
                meterDetail_Bean.setMeter_detail_id(rs.getInt("meter_detail_id"));
                meterDetail_Bean.setMeter_reading_id(rs.getInt("meter_reading_id"));
                meterDetail_Bean.setMeter_no(rs.getString("md.meter_no"));
                meterDetail_Bean.setSurvey_meter_no(rs.getString("mr.meter_no"));
                meterDetail_Bean.setMeter_reading(rs.getString("meter_reading"));
                meterDetail_Bean.setDate_time(rs.getString("date_time"));
                meterDetail_Bean.setLatitude(rs.getDouble("latitude"));
                meterDetail_Bean.setLongitude(rs.getDouble("longitude"));
                meterDetail_Bean.setRemark(rs.getString("remark"));
                meterDetail_Bean.setNumber_of_occupants(rs.getInt("number_of_occupants"));
                String image_name = rs.getString("image_name");
                meterDetail_Bean.setImage_name(image_name);
                meterDetail_Bean.setKey_person_name(rs.getString("key_person_name"));
                meterDetail_Bean.setAddress(rs.getString("address_line1"));
                meterDetail_Bean.setMobile_no(rs.getString("mobile_no1"));
                   meterDetail_Bean.setDate_time1(rs.getString("date_time1"));
                list.add(meterDetail_Bean);
            }
        } catch (Exception e) {
            System.out.println("ShowData ERROR inside MeterReadingModel - " + e);
        }
        return list;
    }

    public List<meterDetail_Bean> ShowData1(int lowerLimit, int noOfRowsToDisplay, String searchMeterNo, String searchMeterNoTo, String searchDateFrom, String searchDateTo) {
        List<meterDetail_Bean> list = new ArrayList<meterDetail_Bean>();
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }
        String query = " SELECT md.meter_detail_id, md.date_time as date_time1,md.meter_no, md.meter_reading as meter_reading1, kp.key_person_name,kp.address_line1,kp.mobile_no1,"
                + "   mr.number_of_occupants,mr.date_time, mr.meter_reading,mr.remark,mr.latitude,mr.longitude, mr.meter_no, gid.image_name "
                + "   FROM meter_reading mr LEFT JOIN (meter_detail md, key_person as kp) ON md.meter_detail_id=mr.meter_detail_id"
                + "   AND kp.key_person_id=md.key_person_id"
                + "   LEFT JOIN general_image_details gid ON gid.key_person_id=mr.meter_reading_id"
                + "   AND md.active='Y' WHERE mr.active='Y' "
                + " AND IF('" + searchMeterNo + "'='', mr.meter_no LIKE '%%' OR mr.meter_no IS NULL, mr.meter_no>='" + searchMeterNo + "') "
                + " AND IF('" + searchMeterNoTo + "'='', mr.meter_no LIKE '%%' OR mr.meter_no IS NULL, mr.meter_no<='" + searchMeterNoTo + "') "
                + " AND IF('" + searchDateFrom + "'='', mr.date_time LIKE '%%' OR mr.date_time IS NULL, mr.date_time>='" + searchDateFrom + "') "
                + " AND IF('" + searchDateTo + "'='', mr.date_time LIKE '%%' OR mr.date_time IS NULL, mr.date_time<='" + searchDateTo + " 59:59:00') "
                + addQuery;
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                meterDetail_Bean meterDetail_Bean = new meterDetail_Bean();
                meterDetail_Bean.setMeter_detail_id(rs.getInt("meter_detail_id"));
                meterDetail_Bean.setDate_time1(rs.getString("date_time1"));
                meterDetail_Bean.setMeter_no(rs.getString("meter_no"));
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
                meterDetail_Bean.setSurvey_meter_no(rs.getString("mr.meter_no"));
                meterDetail_Bean.setImage_name(rs.getString("image_name"));
                list.add(meterDetail_Bean);
            }
        } catch (Exception e) {
            System.out.println("ShowData ERROR inside MeterReadingModel - " + e);
        }
        return list;
    }

    public int getNoOfRows(String searchMeterNo, String searchMeterNoTo, String searchDateFrom, String searchDateTo) {
        int count = 0;
        String query = "SELECT count(mr.meter_reading_id) as count "
                + " FROM meter_reading mr LEFT JOIN meter_detail md ON md.meter_detail_id=mr.meter_detail_id AND md.active='Y'"
                + " WHERE mr.active='Y' "
                + " AND IF('" + searchMeterNo + "'='', mr.meter_no LIKE '%%' OR mr.meter_no IS NULL, mr.meter_no>='" + searchMeterNo + "') "
                + " AND IF('" + searchMeterNoTo + "'='', mr.meter_no LIKE '%%' OR mr.meter_no IS NULL, mr.meter_no<='" + searchMeterNoTo + "') "
                + " AND IF('" + searchDateFrom + "'='', mr.date_time LIKE '%%' OR mr.date_time IS NULL, mr.date_time>='" + searchDateFrom + "') "
                + " AND IF('" + searchDateTo + "'='', mr.date_time LIKE '%%' OR mr.date_time IS NULL, mr.date_time<='" + searchDateTo + " 59:59:00') ";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (Exception e) {
            System.out.println("getNoOfRows ERROR inside MeterReadingModel - " + e);
        }
        return count;
    }

    public int insertRecord(meterDetail_Bean meterDetail_Bean) {
        int rowsAffected = 0;
        String query = "INSERT INTO meter_reading (meter_detail_id, meter_no, meter_reading, date_time, latitude, longitude, remark, number_of_occupants) "
                + " VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, getMeterDetailId(meterDetail_Bean.getMeter_no()));
            ps.setString(2, meterDetail_Bean.getMeter_no());
            ps.setString(3, meterDetail_Bean.getMeter_reading());
            ps.setString(4, meterDetail_Bean.getDate_time());
            ps.setDouble(5, meterDetail_Bean.getLatitude());
            ps.setDouble(6, meterDetail_Bean.getLongitude());
            ps.setString(7, meterDetail_Bean.getRemark());
            ps.setInt(8, meterDetail_Bean.getNumber_of_occupants());
            rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                message = "Record saved successfully.";
                msgBgColor = COLOR_OK;
            } else {
                message = "Cannot save the record, some error.";
                msgBgColor = COLOR_ERROR;
            }
        } catch (Exception e) {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
            System.out.println("insertRecord ERROR inside MeterReadingModel - " + e);
        }
        return rowsAffected;
    }

    public int getMeterDetailId(String meter_no) {
        int id = 0;
        String query = "SELECT meter_detail_id FROM meter_detail WHERE meter_no='" + meter_no + "'";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                id = rs.getInt("meter_detail_id");
            }
        } catch (Exception ex) {
            System.out.println("ERROR : in getKeyPersonId inside MeterReadingModel : " + ex);
        }
        return id;
    }

    public int UpdateRecord(meterDetail_Bean meterDetail_Bean) {
        int rowsAffected = 0;
        String query = " UPDATE meter_reading SET meter_detail_id=?, meter_no=?, meter_reading=?, date_time=?, "
                + " latitude=?, longitude=?, remark=?, number_of_occupants=? "
                + " WHERE meter_reading_id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, getMeterDetailId(meterDetail_Bean.getMeter_no()));
            ps.setString(2, meterDetail_Bean.getMeter_no());
            ps.setString(3, meterDetail_Bean.getMeter_reading());
            ps.setString(4, meterDetail_Bean.getDate_time());
            ps.setDouble(5, meterDetail_Bean.getLatitude());
            ps.setDouble(6, meterDetail_Bean.getLongitude());
            ps.setString(7, meterDetail_Bean.getRemark());
            ps.setInt(8, meterDetail_Bean.getNumber_of_occupants());
            ps.setInt(9, meterDetail_Bean.getMeter_reading_id());
            rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                message = "Record Update successfully.";
                msgBgColor = COLOR_OK;
            } else {
                message = "Cannot update the record, some error.";
                msgBgColor = COLOR_ERROR;
            }
        } catch (Exception e) {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
            System.out.println("UpdateRecord ERROR inside MeterReadingModel - " + e);
        }
        return rowsAffected;
    }

    public int deleteRecord(String meterReadingId) {
        int rowsAffected = 0;
        String query = "UPDATE meter_reading SET active='N' WHERE meter_reading_id=" + meterReadingId;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
            if (rowsAffected > 0) {
                message = "Record deleted successfully.";
                msgBgColor = COLOR_OK;
            } else {
                message = "Cannot delete the record, some error.";
                msgBgColor = COLOR_ERROR;
            }
        } catch (Exception e) {
            message = "Cannot delete the record, some error.";
            msgBgColor = COLOR_ERROR;
            System.out.println("deleteRecord ERROR inside MeterReadingModel - " + e);
        }
        return rowsAffected;
    }

    public List<String> getMeterNo(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT meter_no FROM meter_detail WHERE active='Y' "
                + " AND meter_no LIKE '" + q.trim() + "%' ORDER BY meter_no";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("meter_no"));
            }
        } catch (Exception ex) {
            System.out.println("getMeterNo ERROR inside MeterReadingModel - " + ex);
        }
        return list;
    }

    public String getDestination_Path(String image_uploaded_for) {
        String destination_path = "";
        String query = " SELECT destination_path FROM image_destination id, image_uploaded_for  iuf "
                + " WHERE id.image_uploaded_for_id = iuf.image_uploaded_for_id "
                + " AND iuf.image_uploaded_for = '" + image_uploaded_for + "' ";//traffic_police
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                destination_path = rs.getString("destination_path");
            }
        } catch (Exception ex) {
            System.out.println("ERROR: in getTrafficPoliceId in TraffiPoliceSearchModel : " + ex);
        }
        return destination_path;
    }

    public List<String> getImageNameList(String id) {
        List<String> list = new ArrayList<String>();
        int count = 0;
        String query = "SELECT g.image_name FROM image_destination id,image_uploaded_for  iuf,general_image_details g "
                + "  WHERE id.image_uploaded_for_id = iuf.image_uploaded_for_id AND id.image_destination_id = g.image_destination_id "
                + "  AND iuf.image_uploaded_for = 'Complaint_image' and key_person_id=" + id + " ";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                String image_name = rs.getString("image_name");
                list.add(image_name);
                count++;

            }
        } catch (Exception ex) {
        }
        return list;
    }

    public String getImageName(String id) {
        String imageName = "";
        int count = 0;
        String query = "SELECT g.image_name FROM image_destination id,image_uploaded_for  iuf,general_image_details g "
                + "  WHERE id.image_uploaded_for_id = iuf.image_uploaded_for_id AND id.image_destination_id = g.image_destination_id "
                + "  AND iuf.image_uploaded_for = 'MeterReading' and key_person_id=" + id + " ";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                imageName = rs.getString("image_name");
            }
        } catch (Exception ex) {
        }
        return imageName;
    }

    public byte[] generateMapReport(String jrxmlFilePath, List<OverHeadTank> listAll) {
        byte[] reportInbytes = null;
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, beanColDataSource);
        } catch (Exception e) {
            System.out.println("Error: in OverHeadTankModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public ByteArrayOutputStream generateExcelList(String jrxmlFilePath, List list) {
        ByteArrayOutputStream reportInbytes = new ByteArrayOutputStream();
        HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, jrBean);
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, reportInbytes);
            exporter.exportReport();
            //print(jasperPrint);
        } catch (Exception e) {
            System.out.println("GeneralModel generateExcelList() JRException: " + e);
        }
        return reportInbytes;
    }

    public String getMessage() {
        return message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getDb_password() {
        return db_password;
    }

    public void setDb_password(String db_password) {
        this.db_password = db_password;
    }

    public String getDb_username() {
        return db_username;
    }

    public void setDb_username(String db_username) {
        this.db_username = db_username;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Error: in closeConnection() in MeterReadingModel " + e);
        }
    }
}
