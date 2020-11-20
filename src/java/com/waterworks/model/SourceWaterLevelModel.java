package com.waterworks.model;

import com.waterworks.tableClasses.SourceWaterLevel;
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

public class SourceWaterLevelModel {

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

    public byte[] generateMapReport(String jrxmlFilePath, List<SourceWaterLevel> listAll) {
        byte[] reportInbytes = null;
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, beanColDataSource);
        } catch (Exception e) {
            System.out.println("Error: in SourceWaterLevelModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public List<SourceWaterLevel> showAllData(String sourceWaterName) {
        ArrayList<SourceWaterLevel> list = new ArrayList<SourceWaterLevel>();
        String query = "SELECT sw.name,le.level,le.date_time,le.remark, level_datetime "
                + "FROM sourcewaterlevel AS le "
                + "LEFT JOIN sourcewater AS sw ON le.sourcewater_id = sw.sourcewater_id "
                + "WHERE IF('" + sourceWaterName + "'='',sw.name LIKE '%%',sw.name=?) "
                + "ORDER BY sw.name ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, sourceWaterName);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                SourceWaterLevel sourceWaterLevelBean = new SourceWaterLevel();
                sourceWaterLevelBean.setSourceWaterName(rset.getString("name"));
                sourceWaterLevelBean.setLevel(rset.getFloat("level"));
                sourceWaterLevelBean.setDateTime(rset.getString("date_time"));
                sourceWaterLevelBean.setRemark(rset.getString("remark"));
                sourceWaterLevelBean.setLevel_datetime(rset.getString("level_datetime"));
//                sourceWaterLevelBean.setLatitude(rset.getString("latitude"));
//                sourceWaterLevelBean.setLongitude(rset.getString("longitude"));

                list.add(sourceWaterLevelBean);
            }
        } catch (Exception e) {
            System.out.println("Error in showAllRecrod -- SourceWaterLevelModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        return list;
    }

    public int getSourceWaterId(String sourceWaterName) {
        String query = "SELECT sourcewater_id FROM sourcewater WHERE name='" + sourceWaterName + "'";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            if (rset.next()) {
                return rset.getInt(1);
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Error in getSourceWaterId - SourceWaterLevelModel - " + e);
            message = "Something going wrong";
            messageBGColor = "red";
            return 0;
        }
    }

    public List<String> getSourceWater(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT name FROM sourcewater ORDER BY name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String sourceWater_type = rset.getString("name");
                if (sourceWater_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(sourceWater_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Source Water name exists.......");
            }
        } catch (Exception e) {
            System.out.println("Error in getSourceWater - SourceWaterLevelModel - " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        return list;
    }

    public void updateRecord(SourceWaterLevel sourceWaterLevelBean) {
        String query = "UPDATE sourcewaterlevel SET sourcewater_id=?,level=?,remark=?, level_datetime=? WHERE sourcewaterlevel_id=?";
        try {
            PreparedStatement presta = connection.prepareStatement(query);
            presta.setInt(1, sourceWaterLevelBean.getSourceWaterId());
            presta.setFloat(2, sourceWaterLevelBean.getLevel());
            presta.setString(3, sourceWaterLevelBean.getRemark());
            presta.setString(4, convertToSqlDate(sourceWaterLevelBean.getLevel_datetime()).toString() + " " + sourceWaterLevelBean.getHours() + ":" + sourceWaterLevelBean.getMinutes());
//        presta.setString(5, sourceWaterLevelBean.getLatitude());
//        presta.setString(6, sourceWaterLevelBean.getLongitude());
            presta.setInt(5, sourceWaterLevelBean.getSourceWaterLevelId());
            int i = presta.executeUpdate();
            if (i > 0) {
                message = "Record updated successfully";
                messageBGColor = "yellow";
            } else {
                message = "Record not updated successfully";
                messageBGColor = "red";
            }
        } catch (Exception e) {
            System.out.println("Error in updateRecord - SourceWaterLevelModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
    }

    public void deleteRecord(int sourceWaterLevelId) {
        PreparedStatement presta = null;
        try {
            presta = connection.prepareStatement("DELETE FROM sourcewaterlevel WHERE sourcewaterlevel_id=?");
            presta.setInt(1, sourceWaterLevelId);
            int i = presta.executeUpdate();
            if (i > 0) {
                message = "Record deleted successfully......";
                messageBGColor = "yellow";
            } else {
                message = "Record not deleted successfully......";
                messageBGColor = "red";
            }
        } catch (Exception e) {
            System.out.println("Error in deleteRecord ---- SourceWaterLevelModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
    }

    public ArrayList<SourceWaterLevel> getAllRecords(int lowerLimit, int noOfRowsToDisplay, String sourceWaterName) {
        ArrayList<SourceWaterLevel> list = new ArrayList<SourceWaterLevel>();
        String query = "SELECT le.sourcewaterlevel_id,sw.name,le.level,le.date_time,le.remark, date_format(level_datetime, '%d-%m-%Y %h:%i') AS level_datetime "
                + "FROM sourcewaterlevel AS le "
                + "LEFT JOIN sourcewater AS sw ON le.sourcewater_id = sw.sourcewater_id "
                + "WHERE IF('" + sourceWaterName + "'='',sw.name LIKE '%%',sw.name=?) "
                + "ORDER BY sw.name "
                + "LIMIT " + lowerLimit + "," + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, sourceWaterName);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                SourceWaterLevel sourceWaterLevelBean = new SourceWaterLevel();
                sourceWaterLevelBean.setSourceWaterLevelId(rset.getInt("sourcewaterlevel_id"));
                sourceWaterLevelBean.setSourceWaterName(rset.getString("name"));
                sourceWaterLevelBean.setLevel(rset.getFloat("level"));
                sourceWaterLevelBean.setDateTime(rset.getString("date_time"));
                sourceWaterLevelBean.setRemark(rset.getString("remark"));
                sourceWaterLevelBean.setLevel_datetime(rset.getString("level_datetime"));
//                sourceWaterLevelBean.setLatitude(rset.getString("latitude"));
//                sourceWaterLevelBean.setLongitude(rset.getString("longitude"));

                list.add(sourceWaterLevelBean);
            }
        } catch (Exception e) {
            System.out.println("Error in getAllRecrod -- SourceWaterLevelModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        return list;
    }

    public int getTotalRowsInTable(String sourceWaterName) {
        String query = " SELECT count(*) "
                + "FROM sourcewaterlevel as le "
                + "LEFT JOIN sourcewater AS sw ON le.sourcewater_id = sw.sourcewater_id "
                + "WHERE IF('" + sourceWaterName + "'='',sw.name LIKE '%%',sw.name=?) ";

        int noOfRows = 0;
        try {
            PreparedStatement presta = connection.prepareStatement(query);
            presta.setString(1, sourceWaterName);
            ResultSet rs = presta.executeQuery();
            rs.next();
            noOfRows = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows - SourceWaterLevelModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

    public void insertRecord(SourceWaterLevel sourceWaterLevelBean) {
        String query = "INSERT INTO sourcewaterlevel(sourcewater_id,level,remark, level_datetime) VALUES(?,?,?,?)";
        try {
            PreparedStatement presta = connection.prepareStatement(query);
            presta.setInt(1, sourceWaterLevelBean.getSourceWaterId());
            presta.setFloat(2, sourceWaterLevelBean.getLevel());
            presta.setString(3, sourceWaterLevelBean.getRemark());
            presta.setString(4, convertToSqlDate(sourceWaterLevelBean.getLevel_datetime()).toString() + " " + sourceWaterLevelBean.getHours() + ":" + sourceWaterLevelBean.getMinutes());
//            presta.setString(5, sourceWaterLevelBean.getLatitude());
//            presta.setString(6, sourceWaterLevelBean.getLongitude());
            int i = presta.executeUpdate();
            if (i >= 0) {
                message = "Record inserted succesfully";
                messageBGColor = "Yellow";
            } else {
                message = "Record not inserted succesfully";
                messageBGColor = "red";
            }
        } catch (Exception e) {
            System.out.println("Error in insertRecord - SourceWaterLevelModel : " + e);
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
            System.out.println("Error in closeConnection -- SourceWaterLevelModel : " + e);
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
