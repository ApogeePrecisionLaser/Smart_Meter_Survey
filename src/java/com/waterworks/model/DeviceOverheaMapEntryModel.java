package com.waterworks.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import com.waterworks.tableClasses.OHLevel;
import com.waterworks.tableClasses.ohlevelmapbean;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletContext;

public class DeviceOverheaMapEntryModel {

    private Connection connection;
    private String driver, url, user, password;
    private String message, messageBGColor;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
    DecimalFormat df = new DecimalFormat("0.00");

    public void setConnection() {
        try {
            Class.forName(driver);
            // connection = DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            connection = (Connection) DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("WattageTypeModel setConnection() Error: " + e);
        }
    }

    public byte[] generateMapReport(String jrxmlFilePath, List<OHLevel> listAll) {
        byte[] reportInbytes = null;
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, beanColDataSource);
        } catch (Exception e) {
            System.out.println("Error: in OHLevelModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public List<OHLevel> showAllData(String waterTreatmentPlantName, String overHeadTankName) {
        ArrayList<OHLevel> list = new ArrayList<OHLevel>();
        String query = "SELECT wtp.name,oht.name,le.level_a,le.level_b,le.date_time,le.remark, level_datetime "
                + "FROM ohlevel AS le "
                + "LEFT JOIN overheadtank AS oht ON le.overheadtank_id = oht.overheadtank_id "
                + "LEFT JOIN watertreatmentplant AS wtp ON oht.watertreatmentplant_id = wtp.watertreatmentplant_id "
                + "WHERE IF('" + overHeadTankName + "'='',oht.name LIKE '%%',oht.name=?) "
                + "AND IF('" + waterTreatmentPlantName + "'='',wtp.name LIKE '%%',wtp.name=?) "
                + "ORDER BY oht.name ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, overHeadTankName);
            pstmt.setString(2, waterTreatmentPlantName);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                OHLevel ohLevelBean = new OHLevel();
                ohLevelBean.setWaterTreatmentPlantName(rset.getString(1));
                ohLevelBean.setOverHeadTankName(rset.getString(2));
                ohLevelBean.setLevel_a(rset.getByte("level_a"));
                ohLevelBean.setLevel_b(rset.getByte("level_b"));
                ohLevelBean.setDateTime(rset.getString("date_time"));
                ohLevelBean.setRemark(rset.getString("remark"));
                ohLevelBean.setLevel_datetime(rset.getString("level_datetime"));

                list.add(ohLevelBean);
            }
        } catch (Exception e) {
            System.out.println("Error in showAllRecrod -- OHLevelModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        return list;
    }

    public List<String> getOverHeadTank(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT oht.name FROM overheadtank AS oht "
                + "ORDER BY oht.name";

        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/smart_meter_survey","root","root");
//            Statement stmt = con.createStatement();
            ResultSet rset = connection.prepareStatement(query).executeQuery();

            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String overHeadTank_name = rset.getString("name");
                if (overHeadTank_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(overHeadTank_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Overhead Tank exists.......");
            }
            //con.close();

        } catch (Exception e) {
            System.out.println("Error in getOverHeadTank - OHLevelModel - " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        return list;
    }

    public int getOverHeadTankId(String overHeanTankName) {
        String query = "SELECT overheadtank_id FROM overheadtank WHERE name='" + overHeanTankName + "'";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            if (rset.next()) {
                return rset.getInt(1);
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Error in getOverHeadTank - OHLevelModel - " + e);
            message = "Something going wrong";
            messageBGColor = "red";
            return 0;
        }
    }

    public List<String> getDevice(String q) {

        List<String> list = new ArrayList<String>();
        String query = "SELECT device_id FROM device_status group by device_id ORDER BY device_id";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mqtt_server", "root", "root");
            Statement stmt = con.createStatement();
            ResultSet rset = stmt.executeQuery(query);

            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String device = rset.getString("device_id");
                if (device.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(device);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Device exists.......");
            }
            con.close();

        } catch (Exception e) {
            System.out.println("Error in getDevice - OHLevelModel - " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }

        return list;
    }

    public void updateRecord(ohlevelmapbean ohLevelBean) {
            boolean status=false;
            int overheadtank_id=0;
            overheadtank_id = getOverHeadTankId(ohLevelBean.getOverHeadTankName());

            int rowsAffected=0;

        String query1 = "SELECT max(revision_no) FROM device_junction_map djm "
                + " WHERE device_junction_map_id = "+ohLevelBean.getDevice_junction_map_id()+" && active='Y' "
                + " ORDER BY revision_no DESC ";

        String query2 = "UPDATE device_junction_map SET active=?, overheadttank_id=?,device_id=?,remark=? "
                + " WHERE device_junction_map_id=?";
        
        String query3 = "INSERT INTO device_junction_map(device_id,overheadttank_id,"
                + " remark,revision_no, active) VALUES (?,?,?,?,?) ";

      // System.out.println("query111--" + query1);
       
       
       
       int updateRowsAffected = 0;
      try {
           PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query1);
           ResultSet rs = ps.executeQuery();
           if(rs.next()){
               System.out.println("max rev no ="+rs.getString(1));
           PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
           pst.setString(1,  "N");
           pst.setInt(2, overheadtank_id);
           pst.setString(3, ohLevelBean.getDevice_id());
           pst.setString(4,ohLevelBean.getRemark());
           //pst.setInt(5, rs.getInt("revision_no"));
           pst.setInt(5, ohLevelBean.getDevice_junction_map_id());
          // System.out.println("query222--" + query2);
           updateRowsAffected = pst.executeUpdate();
             if(updateRowsAffected >= 1){
             int rev = rs.getInt(1)+1;
             PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
             psmt.setString(1,ohLevelBean.getDevice_id());
             psmt.setInt(2,overheadtank_id);
             psmt.setString(3,ohLevelBean.getRemark());
             psmt.setInt(4,rev);
             psmt.setString(5, "Y");
             //psmt.setString(6, "Y");
            // System.out.println("query333--" + query3);
             int a = psmt.executeUpdate();
              if(a > 0)
              status=true;
             //message = "Something going wrong";

             message="Record Updated Successfully...";
         msgBgColor = COLOR_OK;
             }else{
               message ="Record not updated...";
         msgBgColor = COLOR_ERROR;
             }
           }
          } catch (Exception e)
             {
              System.out.println(" ohlevelmodel updateRecord() Error: " + e);
             }
      if (updateRowsAffected > 0) {
             message = "Record Updated successfully......";
            //msgBgColor = COLOR_OK;
            System.out.println("Inserted");
        } else {
             message = "Record Not Updated Some Error!";
           // msgBgColor = COLOR_ERROR;
            System.out.println("not Inserted");
        }

       //return message;

//        try {
//            int overheadtank_id = 0;
//            overheadtank_id = getOverHeadTankId(ohLevelBean.getOverHeadTankName());
//            // System.out.println("overheadtank_id--"+overheadtank_id);
//            PreparedStatement presta = connection.prepareStatement(query);
//            // System.out.println("ohlevelbean--"+ohLevelBean.getRemark());
//            presta.setInt(1, overheadtank_id);
//            presta.setString(2, ohLevelBean.getDevice_id());
//            presta.setString(3, ohLevelBean.getRemark());
//            presta.setInt(4, ohLevelBean.getDevice_junction_map_id());
//
//            int i = presta.executeUpdate();
//            if (i > 0) {
//                message = "Record updated successfully";
//                messageBGColor = "yellow";
//            } else {
//                message = "Record not updated successfully";
//                messageBGColor = "red";
//            }
//        } catch (Exception e) {
//            System.out.println("Error in updateRecord - OHLevelModel : " + e);
//            message = "Something going wrong";
//            messageBGColor = "red";
//        }

    }

    public void deleteRecord(int device_junction_map_id) {
        PreparedStatement presta = null;
        try {
            presta = connection.prepareStatement("DELETE FROM device_junction_map WHERE device_junction_map_id=?");
            presta.setInt(1, device_junction_map_id);
            int i = presta.executeUpdate();
            if (i > 0) {
                message = "Record deleted successfully......";
                messageBGColor = "yellow";
            } else {
                message = "Record not deleted successfully......";
                messageBGColor = "red";
            }
        } catch (Exception e) {
            System.out.println("Error in deleteRecord ---- OHLevelModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
    }

    public ArrayList<OHLevel> getAllRecords(int lowerLimit, int noOfRowsToDisplay, String waterTreatmentPlantName, String overHeadTankName, ServletContext ctx) {
        ArrayList<OHLevel> list = new ArrayList<OHLevel>();
        byte[] twoByteData = new byte[2];
        String query = " SELECT wtp.name as name1,oht.name,le.ohlevel_id,le.overheadtank_id, \n"
                + "le.level_a,le.level_b,le.date_time,le.remark, date_format(level_datetime, '%d-%m-%Y %h:%i') \n"
                + "AS level_datetime,step,level1,level2,level3,level4,oht.capacity_height FROM ohlevel AS le\n"
                + " LEFT JOIN overheadtank AS oht ON le.overheadtank_id = oht.overheadtank_id \n"
                + " LEFT JOIN watertreatmentplant AS wtp ON oht.watertreatmentplant_id = wtp.watertreatmentplant_id \n"
                + "WHERE IF(''='',oht.name LIKE '%%',oht.name=?) AND IF(''='',wtp.name LIKE '%%',wtp.name=?) \n"
                + " and le.ohlevel_id in(select max(ohlevel_id) from ohlevel group by overheadtank_id)group by overheadtank_id "
                + " LIMIT " + lowerLimit + "," + noOfRowsToDisplay;
//        String query = " select * from (SELECT wtp.name as name1,oht.name,le.ohlevel_id,le.overheadtank_id,"
//                + " le.level_a,le.level_b,le.date_time,le.remark,"
//                + " date_format(level_datetime, '%d-%m-%Y %h:%i') AS level_datetime,step,level1,level2,level3,level4,oht.capacity_height "
//                + "FROM ohlevel AS le "
//                + "LEFT JOIN overheadtank AS oht ON le.overheadtank_id = oht.overheadtank_id "
//                + "LEFT JOIN watertreatmentplant AS wtp ON oht.watertreatmentplant_id = wtp.watertreatmentplant_id "
//                + "WHERE IF('" + overHeadTankName + "'='',oht.name LIKE '%%',oht.name=?) "
//                + "AND IF('" + waterTreatmentPlantName + "'='',wtp.name LIKE '%%',wtp.name=?) "
//                + "  ORDER BY ohlevel_id desc )  as a group by a.name order by name1,date_time desc"
//                + " LIMIT " + lowerLimit + "," + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, overHeadTankName);
            pstmt.setString(2, waterTreatmentPlantName);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                OHLevel ohLevelBean = new OHLevel();
                ohLevelBean.setWaterTreatmentPlantName(rset.getString(1));
                ohLevelBean.setOverHeadTankName(rset.getString(2));
                ohLevelBean.setOhLevelId(rset.getInt("ohlevel_id"));
                ohLevelBean.setLevel_a(rset.getByte("level_a"));
                ohLevelBean.setLevel_b(rset.getByte("level_b"));
                ohLevelBean.setValue_of_ab(rset.getString("remark"));
                ohLevelBean.setDateTime(rset.getString("date_time"));
                ohLevelBean.setRemark("");
                ohLevelBean.setLevel_datetime(rset.getString("date_time"));
                ohLevelBean.setStep(rset.getByte("step"));
                ohLevelBean.setLevel1(rset.getByte("level1"));
                ohLevelBean.setLevel2(rset.getByte("level2"));
                ohLevelBean.setLevel3(rset.getByte("level3"));
                ohLevelBean.setLevel4(rset.getByte("level4"));
                twoByteData[0] = rset.getByte("level3");
                twoByteData[1] = rset.getByte("level4");
                long voltage1 = (new BigInteger(twoByteData).longValue());
                ohLevelBean.setValue_of_34(("" + voltage1).trim());
                ohLevelBean.setLevel_of_34("" + df.format((voltage1 / (rset.getDouble("capacity_height") * 1000)) * 100));
                String command = rset.getString("overheadtank_id");
                String command_value = "", feedback = "";
                try {
                    command_value = (String) ctx.getAttribute(command);
                    feedback = (String) ctx.getAttribute("feedback_" + command);
                    if (feedback == null) {
                        feedback = ("" + 0).trim();
                    }
                    if (command_value == null) {
                        command_value = ("" + 0).trim();
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
                ohLevelBean.setCommand(command_value);
                ohLevelBean.setFeedback(feedback);
                list.add(ohLevelBean);
            }
        } catch (Exception e) {
            System.out.println("Error in getAllRecrod -- OHLevelModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        return list;
    }

    public ArrayList<ohlevelmapbean> getDeviceOverheadMapAllRecords(int lowerLimit, int noOfRowsToDisplay, String deviceName, String overHeadTankName, ServletContext ctx) {
        ArrayList<ohlevelmapbean> list = new ArrayList<ohlevelmapbean>();
        byte[] twoByteData = new byte[2];
        int overheadtank_id = 0;
        //OHLevel ohLevelBean = new OHLevel();
        //System.out.println("device id --" + deviceName + "  overhead name --" + overHeadTankName);

        if (!overHeadTankName.equals("")) {//System.err.println("yahan aa gaya ");
            overheadtank_id = getOverHeadTankId(overHeadTankName);
        }
//            String query = " select djm.device_id,djm.overheadttank_id,oht.name,oht.capacity_height,oht.capacity_ltr,djm.remark,djm.device_junction_map_id "
//                + "from device_junction_map as djm ,overheadtank as oht where djm.overheadttank_id=oht.overheadtank_id and djm.active='y'  "
//                + " LIMIT " + lowerLimit + "," + noOfRowsToDisplay;

        String query = " select djm.device_id,djm.overheadttank_id,oht.name,oht.capacity_height,oht.capacity_ltr,djm.remark,djm.device_junction_map_id "
                + "from device_junction_map as djm ,overheadtank as oht where djm.overheadttank_id=oht.overheadtank_id and djm.active='y'  ";

        if (!deviceName.equals("")) {
            query += " and djm.device_id='" + deviceName + "' ";
        }

        if (!overHeadTankName.equals("")) {
            query += " and djm.overheadttank_id='" + overheadtank_id + "' ";
        }
        
        query+= " LIMIT " + lowerLimit + "," + noOfRowsToDisplay;

        System.out.println("query--" + query);
//        String query = " select * from (SELECT wtp.name as name1,oht.name,le.ohlevel_id,le.overheadtank_id,"
//                + " le.level_a,le.level_b,le.date_time,le.remark,"
//                + " date_format(level_datetime, '%d-%m-%Y %h:%i') AS level_datetime,step,level1,level2,level3,level4,oht.capacity_height "
//                + "FROM ohlevel AS le "
//                + "LEFT JOIN overheadtank AS oht ON le.overheadtank_id = oht.overheadtank_id "
//                + "LEFT JOIN watertreatmentplant AS wtp ON oht.watertreatmentplant_id = wtp.watertreatmentplant_id "
//                + "WHERE IF('" + overHeadTankName + "'='',oht.name LIKE '%%',oht.name=?) "
//                + "AND IF('" + waterTreatmentPlantName + "'='',wtp.name LIKE '%%',wtp.name=?) "
//                + "  ORDER BY ohlevel_id desc )  as a group by a.name order by name1,date_time desc"
//                + " LIMIT " + lowerLimit + "," + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //  pstmt.setString(1, overHeadTankName);
            //   pstmt.setString(2, waterTreatmentPlantName);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                ohlevelmapbean ohLevelBean = new ohlevelmapbean();
                ohLevelBean.setWaterTreatmentPlantName(rset.getString(1));
                ohLevelBean.setOverHeadTankName(rset.getString(3));
                ohLevelBean.setOhLevelId(rset.getInt(2));
                ohLevelBean.setRemark(rset.getString(6));
                ohLevelBean.setDevice_junction_map_id(rset.getInt(7));

                list.add(ohLevelBean);
            }
        } catch (Exception e) {
            System.out.println("Error in getAllRecrod -- OHLevelModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        System.out.println("list--" + list.size());
        return list;
    }

    public int getTotalRowsInTable(String deviceName, String overHeadTankName) {
        int overheadtank_id = 0;
        ohlevelmapbean ohLevelBean = new ohlevelmapbean();

        if (!overHeadTankName.equals("")) {//System.err.println("yahan aa gaya ");
            overheadtank_id = getOverHeadTankId(overHeadTankName);
        }
//            String query = " select djm.device_id,djm.overheadttank_id,oht.name,oht.capacity_height,oht.capacity_ltr,djm.remark,djm.device_junction_map_id "
//                + "from device_junction_map as djm ,overheadtank as oht where djm.overheadttank_id=oht.overheadtank_id and djm.active='y'  "
//                + " LIMIT " + lowerLimit + "," + noOfRowsToDisplay;

        String query = " select djm.device_id,djm.overheadttank_id,oht.name,oht.capacity_height,oht.capacity_ltr,djm.remark,djm.device_junction_map_id "
                + "from device_junction_map as djm ,overheadtank as oht where djm.overheadttank_id=oht.overheadtank_id and djm.active='y'  ";

        if (!deviceName.equals("")) {
            query += " and djm.device_id='" + deviceName + "' ";
        }

        if (!overHeadTankName.equals("")) {
            query += " and djm.overheadttank_id='" + overheadtank_id + "' ";
        }
        
        //query+= " LIMIT " + lowerLimit + "," + noOfRowsToDisplay;

        int noOfRows = 0;
        try {
            PreparedStatement presta = connection.prepareStatement(query);
            ResultSet rs = presta.executeQuery();
            while (rs.next()) {
                noOfRows++;
            }
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows - OHLevelModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

    public void insertRecord(ohlevelmapbean ohLevelBean) {
//        
//          java.util.Date dt = new java.util.Date();
//    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//    SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    String cut_dt = df.format(dt);
        int overheadtank_id = 0;
        overheadtank_id = getOverHeadTankId(ohLevelBean.getOverHeadTankName());
        // System.out.println("model overheadtank_id ---"+overheadtank_id);

        String query = "INSERT INTO device_junction_map(device_id,overheadttank_id,remark,active) VALUES(?,?,?,?)";
        try {
            PreparedStatement presta = connection.prepareStatement(query);
            presta.setString(1, ohLevelBean.getDevice_id());
            presta.setInt(2, overheadtank_id);
            presta.setString(3, ohLevelBean.getRemark());
            presta.setString(4, "Y");

            int i = presta.executeUpdate();

            if (i >= 0) {

                message = "Record inserted succesfully";
                messageBGColor = "Yellow";
            } else {
                message = "Record not inserted succesfully";
                messageBGColor = "red";
            }
        } catch (Exception e) {
            System.out.println("Error in insertRecord - OHLevelModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
    }

    public int getOverHeadTankid(int ohlevel_id) {
        int overheadtank_id = 0;
        String query = " select overheadtank_id from ohlevel where ohlevel_id=" + ohlevel_id;
        try {
            PreparedStatement presta = connection.prepareStatement(query);
            ResultSet rs = presta.executeQuery();
            rs.next();
            overheadtank_id = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error in getOverTankId " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }

        return overheadtank_id;
    }

    public List<String> getWaterTreatmentPlant(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT name FROM watertreatmentplant order by name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String waterTreatmentPlant_type = rset.getString("name");
                if (waterTreatmentPlant_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(waterTreatmentPlant_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Water Treatment Plant exists.......");
            }
        } catch (Exception e) {
            System.out.println("Error in getOverHeadTank ERROR inside OverHeadTankModel - " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        return list;
    }

    public List<String> getOverHeadTank(String q, String waterTreatmentPlantName) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT oht.name FROM overheadtank as oht "
                + "LEFT JOIN watertreatmentplant AS wtp ON oht.watertreatmentplant_id = wtp.watertreatmentplant_id "
                + "WHERE IF('" + waterTreatmentPlantName + "'='',wtp.name LIKE '%%',wtp.name='" + waterTreatmentPlantName + "') "
                + "ORDER BY oht.name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String overHeadTank_type = rset.getString("name");
                if (overHeadTank_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(overHeadTank_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Overhead Tank exists.......");
            }
        } catch (Exception e) {
            System.out.println("Error in getOverHeadTank - OverHeadTankModel - " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        return list;
    }

    public java.sql.Date convertToSqlDate(String date) throws ParseException {
        java.sql.Date finalDate = null;
        String strD = date;
        String[] str1 = strD.split("-");
        strD = str1[1] + "/" + str1[0] + "/" + str1[2]; // Format: mm/dd/yy
        finalDate = new java.sql.Date(DateFormat.getDateInstance(DateFormat.SHORT, new Locale("en", "US")).parse(strD).getTime());
        return finalDate;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Error in closeConnection -- OHLevelModel : " + e);
        }
    }

    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("PaymentStatusModel setConnection() Error: " + e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

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

    public String getMessage() {
        return message;
    }

    public String getMessageBGColor() {
        return messageBGColor;
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

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public void setMsgBgColor(String msgBgColor) {
        this.msgBgColor = msgBgColor;
    }
}
