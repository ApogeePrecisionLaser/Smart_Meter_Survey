package com.waterworks.model;

import com.waterworks.tableClasses.RawWaterLevel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.text.DateFormat;
import java.text.ParseException;

public class RawWaterLevelModel {

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

    public void setConnection() {
        try {
            Class.forName(driver);
            // connection = DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            connection = (Connection) DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("WattageTypeModel setConnection() Error: " + e);
        }
    }

    public byte[] generateMapReport(String jrxmlFilePath, List<RawWaterLevel> listAll) {
        byte[] reportInbytes = null;
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, beanColDataSource);
        } catch (Exception e) {
            System.out.println("Error: in RawWaterLevelModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public List<RawWaterLevel> showAllData(String rawWaterName) {
        ArrayList<RawWaterLevel> list = new ArrayList<RawWaterLevel>();
        String query = "SELECT raw.name,le.level,le.date_time,le.remark, level_datetime "
                + "FROM rawwaterlevel AS le "
                + "LEFT JOIN rawwater AS raw ON le.rawwater_id = raw.rawwater_id "
                + "WHERE IF('" + rawWaterName + "'='',raw.name LIKE '%%',raw.name=?) "
                + "ORDER BY raw.name ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, rawWaterName);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                RawWaterLevel rawWaterLevelBean = new RawWaterLevel();
                rawWaterLevelBean.setRawWaterName(rset.getString("name"));
                rawWaterLevelBean.setLevel(rset.getFloat("level"));
                rawWaterLevelBean.setDateTime(rset.getString("date_time"));
                rawWaterLevelBean.setRemark(rset.getString("remark"));
                rawWaterLevelBean.setLevel_datetime(rset.getString("level_datetime"));
//                rawWaterLevelBean.setLatitude(rset.getString("latitude"));
//                rawWaterLevelBean.setLongitude(rset.getString("longitude"));

                list.add(rawWaterLevelBean);
            }
        } catch (Exception e) {
            System.out.println("Error in showAllRecrod -- RawWaterLevelModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        return list;
    }

    public int getRawWaterId(String rawWaterName) {
        String query = "SELECT rawwater_id FROM rawwater WHERE name='" + rawWaterName + "'";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            if (rset.next()) {
                return rset.getInt(1);
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Error in getRawWaterId - RawWaterLevelModel - " + e);
            message = "Something going wrong";
            messageBGColor = "red";
            return 0;
        }
    }

    public List<String> getRawWater(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT name FROM rawwater ORDER BY name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String rawWater_type = rset.getString("name");
                if (rawWater_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(rawWater_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Raw Water name exists.......");
            }
        } catch (Exception e) {
            System.out.println("Error in getRawWater - RawWaterLevelModel - " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        return list;
    }

    public void updateRecord(RawWaterLevel rawWaterLevelBean) {
        String query = "UPDATE rawwaterlevel SET rawwater_id=?,level=?,remark=?, level_datetime=? WHERE rawwaterlevel_id=?";
        try {
            PreparedStatement presta = connection.prepareStatement(query);
            presta.setInt(1, rawWaterLevelBean.getRawWaterId());
            presta.setFloat(2, rawWaterLevelBean.getLevel());
            presta.setString(3, rawWaterLevelBean.getRemark());
            presta.setString(4, convertToSqlDate(rawWaterLevelBean.getLevel_datetime()).toString() + " " + rawWaterLevelBean.getHours() + ":" + rawWaterLevelBean.getMinutes());
//        presta.setString(5, rawWaterLevelBean.getLatitude());
//        presta.setString(6, rawWaterLevelBean.getLongitude());
            presta.setInt(5, rawWaterLevelBean.getRawWaterLevelId());
            int i = presta.executeUpdate();
            if (i > 0) {
                message = "Record updated successfully";
                messageBGColor = "yellow";
            } else {
                message = "Record not updated successfully";
                messageBGColor = "red";
            }
        } catch (Exception e) {
            System.out.println("Error in updateRecord - RawWaterLevelModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
    }

    public void deleteRecord(int rawWaterLevelId) {
        PreparedStatement presta = null;
        try {
            presta = connection.prepareStatement("DELETE FROM rawwaterlevel WHERE rawwaterlevel_id=?");
            presta.setInt(1, rawWaterLevelId);
            int i = presta.executeUpdate();
            if (i > 0) {
                message = "Record deleted successfully......";
                messageBGColor = "yellow";
            } else {
                message = "Record not deleted successfully......";
                messageBGColor = "red";
            }
        } catch (Exception e) {
            System.out.println("Error in deleteRecord ---- RawWaterLevelModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
    }

    public ArrayList<RawWaterLevel> getAllRecords(int lowerLimit, int noOfRowsToDisplay, String rawWaterName) {
        ArrayList<RawWaterLevel> list = new ArrayList<RawWaterLevel>();
        String query = "SELECT le.rawwaterlevel_id,raw.name,le.level,le.date_time,le.remark, date_format(level_datetime, '%d-%m-%Y %h:%i') AS level_datetime "
                + "FROM rawwaterlevel AS le "
                + "LEFT JOIN rawwater AS raw ON le.rawwater_id = raw.rawwater_id "
                + "WHERE IF('" + rawWaterName + "'='',raw.name LIKE '%%',raw.name=?) "
                + "ORDER BY raw.name "
                + "LIMIT " + lowerLimit + "," + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, rawWaterName);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                RawWaterLevel rawWaterLevelBean = new RawWaterLevel();
                rawWaterLevelBean.setRawWaterLevelId(rset.getInt("rawwaterlevel_id"));
                rawWaterLevelBean.setRawWaterName(rset.getString("name"));
                rawWaterLevelBean.setLevel(rset.getFloat("level"));
                rawWaterLevelBean.setDateTime(rset.getString("date_time"));
                rawWaterLevelBean.setRemark(rset.getString("remark"));
                rawWaterLevelBean.setLevel_datetime(rset.getString("level_datetime"));
//                rawWaterLevelBean.setLatitude(rset.getString("latitude"));
//                rawWaterLevelBean.setLongitude(rset.getString("longitude"));

                list.add(rawWaterLevelBean);
            }
        } catch (Exception e) {
            System.out.println("Error in getAllRecrod -- RawWaterLevelModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        return list;
    }

    public int getTotalRowsInTable(String rawWaterName) {
        String query = " SELECT count(*) "
                + "FROM rawwaterlevel as le "
                + "LEFT JOIN rawwater AS raw ON le.rawwater_id = raw.rawwater_id "
                + "WHERE IF('" + rawWaterName + "'='',raw.name LIKE '%%',raw.name=?) ";

        int noOfRows = 0;
        try {
            PreparedStatement presta = connection.prepareStatement(query);
            presta.setString(1, rawWaterName);
            ResultSet rs = presta.executeQuery();
            rs.next();
            noOfRows = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows - RawWaterLevelModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

    public void insertRecord(RawWaterLevel rawWaterLevelBean) {
        String query = "INSERT INTO rawwaterlevel(rawwater_id,level,remark, level_datetime) VALUES(?,?,?,?)";
        try {
            PreparedStatement presta = connection.prepareStatement(query);
            presta.setInt(1, rawWaterLevelBean.getRawWaterId());
            presta.setFloat(2, rawWaterLevelBean.getLevel());
            presta.setString(3, rawWaterLevelBean.getRemark());
            presta.setString(4, convertToSqlDate(rawWaterLevelBean.getLevel_datetime()).toString() + " " + rawWaterLevelBean.getHours() + ":" + rawWaterLevelBean.getMinutes());
//            presta.setString(5, rawWaterLevelBean.getLatitude());
//            presta.setString(6, rawWaterLevelBean.getLongitude());
            int i = presta.executeUpdate();
            if (i >= 0) {
                message = "Record inserted succesfully";
                messageBGColor = "Yellow";
            } else {
                message = "Record not inserted succesfully";
                messageBGColor = "red";
            }
        } catch (Exception e) {
            System.out.println("Error in insertRecord - RawWaterLevelModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
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
            System.out.println("Error in closeConnection -- RawWaterLevelModel : " + e);
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
