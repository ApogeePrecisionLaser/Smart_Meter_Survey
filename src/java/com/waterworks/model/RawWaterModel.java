package com.waterworks.model;

import com.waterworks.tableClasses.RawWater;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class RawWaterModel {

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

    public byte[] generateMapReport(String jrxmlFilePath, List<RawWater> listAll) {
        byte[] reportInbytes = null;
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, beanColDataSource);
        } catch (Exception e) {
            System.out.println("Error: in RawWaterModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public List<RawWater> showAllData(String rawWaterName) {
        ArrayList<RawWater> list = new ArrayList<RawWater>();
        String query = "SELECT r.name,r.avg_height,r.max_height,r.min_height,ci.city_name,r.address1,r.address2,r.remark,r.date_time, r.latitude, r.longitude "
                + "FROM rawwater AS r "
                + "LEFT JOIN city AS ci ON r.city_id = ci.city_id "
                + "WHERE IF('" + rawWaterName + "'='',r.name LIKE '%%',r.name=?) "
                + "ORDER BY r.name ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, rawWaterName);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                RawWater rawWaterBean = new RawWater();
                rawWaterBean.setName(rset.getString("name"));
                rawWaterBean.setAvgHeight(rset.getFloat("avg_height"));
                rawWaterBean.setMaxHeight(rset.getFloat("max_height"));
                rawWaterBean.setMinHeight(rset.getFloat("min_height"));
                rawWaterBean.setCityName(rset.getString("city_name"));
                rawWaterBean.setAddress1(rset.getString("address1"));
                rawWaterBean.setAddress2(rset.getString("address2"));
                rawWaterBean.setRemark(rset.getString("remark"));
                rawWaterBean.setDateTime(rset.getString("date_time"));
                rawWaterBean.setLatitude(rset.getString("latitude"));
                rawWaterBean.setLongitude(rset.getString("longitude"));
                list.add(rawWaterBean);
            }
        } catch (Exception e) {
            System.out.println("Error in getAllRecrod -- RawWaterModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        return list;
    }

    public List<String> getRawWater(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT name FROM rawwater ORDER BY name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String rawwater_type = rset.getString("name");
                if (rawwater_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(rawwater_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Row Water Name exists.......");
            }
        } catch (Exception e) {
            System.out.println("Error in getRawWater - RawWaterModel - " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        return list;
    }

    public List<String> getCityName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT city_name FROM city order by city_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String city_type = rset.getString("city_name");
                if (city_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(city_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such City exists.......");
            }
        } catch (Exception e) {
            System.out.println("Error in getCityName - RawWaterModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        return list;
    }

    public int getCityId(String cityName) {

        String query = "SELECT city_id FROM city WHERE city_name=?";
        try {
            PreparedStatement presta = connection.prepareStatement(query);
            presta.setString(1, cityName);
            ResultSet rset = presta.executeQuery();
            rset.next();
            return rset.getInt("city_id");
        } catch (Exception e) {
            System.out.println("Error in getCityId - RawWaterModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        return 0;
    }

    public void updateRecord(RawWater rawWaterBean) {
        String query = "UPDATE rawwater SET name=?,avg_height=?,max_height=?,min_height=?,city_id=?,address1=?,address2=?,remark=?, latitude=?, longitude=? WHERE rawwater_id=?";
        try {
            PreparedStatement presta = connection.prepareStatement(query);
            presta.setString(1, rawWaterBean.getName());
            presta.setFloat(2, rawWaterBean.getAvgHeight());
            presta.setFloat(3, rawWaterBean.getMaxHeight());
            presta.setFloat(4, rawWaterBean.getMinHeight());
            presta.setInt(5, rawWaterBean.getCityId());
            presta.setString(6, rawWaterBean.getAddress1());
            presta.setString(7, rawWaterBean.getAddress2());
            presta.setString(8, rawWaterBean.getRemark());
            presta.setString(9, rawWaterBean.getLatitude());
            presta.setString(10, rawWaterBean.getLongitude());
            presta.setInt(11, rawWaterBean.getRawWaterId());
            int i = presta.executeUpdate();
            if (i > 0) {
                message = "Record updated successfully";
                messageBGColor = "yellow";
            } else {
                message = "Record not updated successfully";
                messageBGColor = "red";
            }
        } catch (Exception e) {
            System.out.println("Error in updateRecord - RawWaterModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
    }

    public void deleteRecord(int rawWaterId) {
        PreparedStatement presta = null;
        try {
            presta = connection.prepareStatement("DELETE FROM rawwater WHERE rawwater_id=?");
            presta.setInt(1, rawWaterId);
            int i = presta.executeUpdate();
            if (i > 0) {
                message = "Record deleted successfully......";
                messageBGColor = "yellow";
            } else {
                message = "Record not deleted successfully......";
                messageBGColor = "red";
            }
        } catch (Exception e) {
            System.out.println("Error in deleteRecord ---- RawWaterModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
    }

    public ArrayList<RawWater> getAllRecords(int lowerLimit, int noOfRowsToDisplay, String rawWaterName) {
        ArrayList<RawWater> list = new ArrayList<RawWater>();
        String query = "SELECT r.rawwater_id,r.name,r.avg_height,r.max_height,r.min_height,ci.city_name,r.address1,r.address2,r.remark,r.date_time, r.latitude, r.longitude "
                + "FROM rawwater AS r "
                + "LEFT JOIN city AS ci ON r.city_id = ci.city_id "
                + "WHERE IF('" + rawWaterName + "'='',r.name LIKE '%%',r.name=?) "
                + "ORDER BY r.name "
                + "LIMIT " + lowerLimit + "," + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, rawWaterName);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                RawWater rawWaterBean = new RawWater();
                rawWaterBean.setRawWaterId(rset.getInt("rawwater_id"));
                rawWaterBean.setName(rset.getString("name"));
                rawWaterBean.setAvgHeight(rset.getFloat("avg_height"));
                rawWaterBean.setMaxHeight(rset.getFloat("max_height"));
                rawWaterBean.setMinHeight(rset.getFloat("min_height"));
                rawWaterBean.setCityName(rset.getString("city_name"));
                rawWaterBean.setAddress1(rset.getString("address1"));
                rawWaterBean.setAddress2(rset.getString("address2"));
                rawWaterBean.setRemark(rset.getString("remark"));
                rawWaterBean.setDateTime(rset.getString("date_time"));
                rawWaterBean.setLatitude(rset.getString("latitude"));
                rawWaterBean.setLongitude(rset.getString("Longitude"));
                list.add(rawWaterBean);
            }
        } catch (Exception e) {
            System.out.println("Error in getAllRecrod -- RawWaterModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        return list;
    }

    public int getTotalRowsInTable(String rawWaterName) {
        String query = "SELECT count(*) "
                + " FROM rawwater AS raw "
                + "WHERE IF('" + rawWaterName + "'='',raw.name LIKE '%%',raw.name=?) ";

        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, rawWaterName);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows - RawWaterModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

    public void insertRecord(RawWater rawWaterBean) {
        String query = "INSERT INTO rawwater(name,avg_height,max_height,min_height,city_id,address1,address2,remark, latitude, longitude) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement presta = connection.prepareStatement(query);
            presta.setString(1, rawWaterBean.getName());
            presta.setFloat(2, rawWaterBean.getAvgHeight());
            presta.setFloat(3, rawWaterBean.getMaxHeight());
            presta.setFloat(4, rawWaterBean.getMinHeight());
            presta.setInt(5, rawWaterBean.getCityId());
            presta.setString(6, rawWaterBean.getAddress1());
            presta.setString(7, rawWaterBean.getAddress2());
            presta.setString(8, rawWaterBean.getRemark());
            presta.setString(9, rawWaterBean.getLatitude());
            presta.setString(10, rawWaterBean.getLongitude());
            int i = presta.executeUpdate();
            if (i >= 0) {
                message = "Record inserted succesfully";
                messageBGColor = "Yellow";
            } else {
                message = "Record not inserted succesfully";
                messageBGColor = "red";
            }
        } catch (Exception e) {
            System.out.println("Error in insertRecord - RawWaterModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Error in closeConnection -- RawWaterModel : " + e);
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
