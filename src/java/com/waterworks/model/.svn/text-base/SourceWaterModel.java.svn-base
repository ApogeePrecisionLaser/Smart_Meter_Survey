package com.waterworks.model;

import com.waterworks.tableClasses.SourceWater;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class SourceWaterModel {

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

    public byte[] generateMapReport(String jrxmlFilePath, List<SourceWater> listAll) {
        byte[] reportInbytes = null;
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, beanColDataSource);
        } catch (Exception e) {
            System.out.println("Error: in SourceWaterModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public List<SourceWater> showAllData(String sourceWaterName) {
        ArrayList<SourceWater> list = new ArrayList<SourceWater>();
        String query = "SELECT sr.sourcewater_id,sr.name,sr.avg_height,sr.max_height,sr.min_height,ci.city_name,sr.address1,sr.address2,sr.remark,sr.date_time, sr.latitude, sr.longitude "
                + "FROM sourcewater AS sr "
                + "LEFT JOIN city AS ci ON sr.city_id = ci.city_id "
                + "WHERE IF('" + sourceWaterName + "'='',sr.name LIKE '%%',sr.name=?) "
                + "ORDER BY sr.name ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, sourceWaterName);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                SourceWater sourceWaterBean = new SourceWater();
                sourceWaterBean.setName(rset.getString("name"));
                sourceWaterBean.setAvgHeight(rset.getFloat("avg_height"));
                sourceWaterBean.setMaxHeight(rset.getInt("max_height"));
                sourceWaterBean.setMinHeight(rset.getFloat("min_height"));
                sourceWaterBean.setCityName(rset.getString("city_name"));
                sourceWaterBean.setAddress1(rset.getString("address1"));
                sourceWaterBean.setAddress2(rset.getString("address2"));
                sourceWaterBean.setRemark(rset.getString("remark"));
                sourceWaterBean.setDateTime(rset.getString("date_time"));
                sourceWaterBean.setLatitude(rset.getString("latitude"));
                sourceWaterBean.setLongitude(rset.getString("Longitude"));
                list.add(sourceWaterBean);
            }
        } catch (Exception e) {
            System.out.println("Error in showAllRecrod -- SourceWaterModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        return list;
    }

    public List<String> getSourceWater(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT name FROM sourcewater ORDER BY name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String sourcewater_type = rset.getString("name");
                if (sourcewater_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(sourcewater_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Source Water Name exists.......");
            }
        } catch (Exception e) {
            System.out.println("Error in getSourceWater - SourceWaterModel - " + e);
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
            System.out.println("Error in getCityName - SourceWaterModel : " + e);
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
            System.out.println("Error in getCityId - SourceWaterModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        return 0;
    }

    public void updateRecord(SourceWater sourceWaterBean) {
        String query = "UPDATE sourcewater SET name=?,avg_height=?,max_height=?,min_height=?,city_id=?,address1=?,address2=?,remark=?, latitude=?, longitude=? WHERE sourcewater_id=?";
        try {
            PreparedStatement presta = connection.prepareStatement(query);
            presta.setString(1, sourceWaterBean.getName());
            presta.setFloat(2, sourceWaterBean.getAvgHeight());
            presta.setFloat(3, sourceWaterBean.getMaxHeight());
            presta.setFloat(4, sourceWaterBean.getMinHeight());
            presta.setInt(5, sourceWaterBean.getCityId());
            presta.setString(6, sourceWaterBean.getAddress1());
            presta.setString(7, sourceWaterBean.getAddress2());
            presta.setString(8, sourceWaterBean.getRemark());
            presta.setString(9, sourceWaterBean.getLatitude());
            presta.setString(10, sourceWaterBean.getLongitude());
            presta.setInt(11, sourceWaterBean.getSourceWaterId());
            int i = presta.executeUpdate();
            if (i > 0) {
                message = "Record updated successfully";
                messageBGColor = "yellow";
            } else {
                message = "Record not updated successfully";
                messageBGColor = "red";
            }
        } catch (Exception e) {
            System.out.println("Error in updateRecord - SourceWaterModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
    }

    public void deleteRecord(int sourcewaterId) {
        PreparedStatement presta = null;
        try {
            presta = connection.prepareStatement("DELETE FROM sourcewater WHERE sourcewater_id=?");
            presta.setInt(1, sourcewaterId);
            int i = presta.executeUpdate();
            if (i > 0) {
                message = "Record deleted successfully......";
                messageBGColor = "yellow";
            } else {
                message = "Record not deleted successfully......";
                messageBGColor = "red";
            }
        } catch (Exception e) {
            System.out.println("Error in deleteRecord ---- SourceWaterModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
    }

    public ArrayList<SourceWater> getAllRecords(int lowerLimit, int noOfRowsToDisplay, String sourceWaterName) {
        ArrayList<SourceWater> list = new ArrayList<SourceWater>();
        String query = "SELECT sr.sourcewater_id,sr.name,sr.avg_height,sr.max_height,sr.min_height,ci.city_name,sr.address1,sr.address2,sr.remark,sr.date_time, latitude, longitude "
                + "FROM sourcewater AS sr "
                + "LEFT JOIN city AS ci ON sr.city_id = ci.city_id "
                + "WHERE IF('" + sourceWaterName + "'='',sr.name LIKE '%%',sr.name=?) "
                + "ORDER BY sr.name "
                + "LIMIT " + lowerLimit + "," + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, sourceWaterName);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                SourceWater sourceWaterBean = new SourceWater();
                sourceWaterBean.setSourceWaterId(rset.getInt("sourcewater_id"));
                sourceWaterBean.setName(rset.getString("name"));
                sourceWaterBean.setAvgHeight(rset.getFloat("avg_height"));
                sourceWaterBean.setMaxHeight(rset.getInt("max_height"));
                sourceWaterBean.setMinHeight(rset.getFloat("min_height"));
                sourceWaterBean.setCityName(rset.getString("city_name"));
                sourceWaterBean.setAddress1(rset.getString("address1"));
                sourceWaterBean.setAddress2(rset.getString("address2"));
                sourceWaterBean.setRemark(rset.getString("remark"));
                sourceWaterBean.setDateTime(rset.getString("date_time"));
                sourceWaterBean.setLatitude(rset.getString("latitude"));
                sourceWaterBean.setLongitude(rset.getString("longitude"));
                list.add(sourceWaterBean);
            }
        } catch (Exception e) {
            System.out.println("Error in getAllRecrod -- SourceWaterModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        return list;
    }

    public int getTotalRowsInTable(String sourceWaterName) {
        String query = "SELECT count(*) "
                + " FROM sourcewater AS source "
                + "WHERE IF('" + sourceWaterName + "'='',source.name LIKE '%%',source.name=?) ";

        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, sourceWaterName);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows - SourceWaterModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

    public void insertRecord(SourceWater sourceWaterBean) {
        String query = "INSERT INTO sourcewater(name,avg_height,max_height,min_height,city_id,address1,address2,remark, latitude, longitude) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement presta = connection.prepareStatement(query);
            presta.setString(1, sourceWaterBean.getName());
            presta.setFloat(2, sourceWaterBean.getAvgHeight());
            presta.setFloat(3, sourceWaterBean.getMaxHeight());
            presta.setFloat(4, sourceWaterBean.getMinHeight());
            presta.setInt(5, sourceWaterBean.getCityId());
            presta.setString(6, sourceWaterBean.getAddress1());
            presta.setString(7, sourceWaterBean.getAddress2());
            presta.setString(8, sourceWaterBean.getRemark());
            presta.setString(9, sourceWaterBean.getLatitude());
            presta.setString(10, sourceWaterBean.getLongitude());
            int i = presta.executeUpdate();
            if (i >= 0) {
                message = "Record inserted succesfully";
                messageBGColor = "Yellow";
            } else {
                message = "Record not inserted succesfully";
                messageBGColor = "red";
            }
        } catch (Exception e) {
            System.out.println("Error in insertRecord - SourceWaterModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Error in closeConnection -- SourceWaterModel : " + e);
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
