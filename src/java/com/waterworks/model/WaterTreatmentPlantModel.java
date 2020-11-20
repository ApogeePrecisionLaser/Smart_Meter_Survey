package com.waterworks.model;

import com.waterworks.tableClasses.WaterTreatmentPlant;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class WaterTreatmentPlantModel {

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
            Class.forName(driverClass);
            // connection = DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            connection = (Connection) DriverManager.getConnection(connectionString, db_username, db_password);
        } catch (Exception e) {
            System.out.println("WattageTypeModel setConnection() Error: " + e);
        }
    }

    public byte[] generateMapReport(String jrxmlFilePath, List<WaterTreatmentPlant> listAll) {
        byte[] reportInbytes = null;
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, beanColDataSource);
        } catch (Exception e) {
            System.out.println("Error: in WaterTreatmentPlantModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public List<WaterTreatmentPlant> showAllData(String waterTreatmentPlantName) {
        ArrayList<WaterTreatmentPlant> list = new ArrayList<WaterTreatmentPlant>();
        String query = "SELECT water.name,water.capacity_mld,ci.city_name,water.address1,water.address2,water.remark, water.date_time, water.latitude, water.longitude "
                + "FROM watertreatmentplant AS water "
                + "LEFT JOIN city AS ci ON water.city_id = ci.city_id "
                + "WHERE IF('" + waterTreatmentPlantName + "' = '',water.name LIKE '%%',water.name = ?) "
                + "ORDER BY water.name ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, waterTreatmentPlantName);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                WaterTreatmentPlant waterTreatmentPlantBean = new WaterTreatmentPlant();
                waterTreatmentPlantBean.setWaterTreatmentPlantName(rset.getString("name"));
                waterTreatmentPlantBean.setCapacityMLD(Integer.parseInt(rset.getString("capacity_mld")));
                waterTreatmentPlantBean.setCityName(rset.getString("city_name"));
                waterTreatmentPlantBean.setAddress1(rset.getString("address1"));
                waterTreatmentPlantBean.setAddress2(rset.getString("address2"));
                waterTreatmentPlantBean.setRemark(rset.getString("remark"));
                waterTreatmentPlantBean.setDateTime(rset.getString("date_time"));
                waterTreatmentPlantBean.setLatitude(rset.getString("latitude"));
                waterTreatmentPlantBean.setLongitude(rset.getString("longitude"));
                list.add(waterTreatmentPlantBean);
            }
        } catch (Exception e) {
            System.out.println("Error in showAllRecrod -- WaterTreatmentPlantModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        return list;
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
            System.out.println("Error in getWaterTreatmentPlant ERROR inside WaterTreatmentPlantModel - " + e);
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
            System.out.println("Error in getCityName - WaterTreatmentPlantModel : " + e);
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
            System.out.println("Error in getCityId - WaterTreatmentPlantModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        return 0;
    }

    public void updateRecord(WaterTreatmentPlant waterTreatmentPlantBean) {
        String query = "UPDATE watertreatmentplant SET name=?,capacity_mld=?,city_id=?,address1=?,address2=?,remark=?, latitude=?, Longitude=? WHERE watertreatmentplant_id=?";
        try {
            PreparedStatement presta = connection.prepareStatement(query);
            presta.setString(1, waterTreatmentPlantBean.getWaterTreatmentPlantName());
            presta.setInt(2, waterTreatmentPlantBean.getCapacityMLD());
            presta.setInt(3, waterTreatmentPlantBean.getCityId());
            presta.setString(4, waterTreatmentPlantBean.getAddress1());
            presta.setString(5, waterTreatmentPlantBean.getAddress2());
            presta.setString(6, waterTreatmentPlantBean.getRemark());
            presta.setString(7, waterTreatmentPlantBean.getLatitude());
            presta.setString(8, waterTreatmentPlantBean.getLongitude());
            presta.setInt(9, waterTreatmentPlantBean.getWaterTreatmentPlantId());
            int i = presta.executeUpdate();
            if (i > 0) {
                message = "Record updated successfully";
                messageBGColor = "yellow";
            } else {
                message = "Record not updated successfully";
                messageBGColor = "red";
            }
        } catch (Exception e) {
            System.out.println("Error in updateRecord - WaterTreatmentPlantModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
    }
public List<WaterTreatmentPlant> showDataBean() {
        List<WaterTreatmentPlant> list = new ArrayList<WaterTreatmentPlant>();


        String query = " select latitude,longitude from watertreatmentplant" ;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                WaterTreatmentPlant bean = new WaterTreatmentPlant();
                bean.setLatitude(rset.getString("latitude"));
                bean.setLongitude(rset.getString("longitude"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowDataBean() :ShiftLoginModel" + e);
        }
        return list;
    }
    public void deleteRecord(int waterTreatmentPlantId) {
        PreparedStatement presta = null;
        try {
            presta = connection.prepareStatement("DELETE FROM watertreatmentplant WHERE watertreatmentplant_id=?");
            presta.setInt(1, waterTreatmentPlantId);
            int i = presta.executeUpdate();
            if (i > 0) {
                message = "Record deleted successfully......";
                messageBGColor = "yellow";
            } else {
                message = "Record not deleted successfully......";
                messageBGColor = "red";
            }
        } catch (Exception e) {
            System.out.println("Error in deleteRecord ---- WaterTreatmentPlantModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
    }

    public ArrayList<WaterTreatmentPlant> getAllRecords(int lowerLimit, int noOfRowsToDisplay, String waterTreatmentPlantName) {
        ArrayList<WaterTreatmentPlant> list = new ArrayList<WaterTreatmentPlant>();
        String query = "SELECT water.watertreatmentplant_id,water.name,water.capacity_mld,ci.city_name,water.address1,water.address2,water.remark, water.date_time, water.latitude, water.longitude "
                + "FROM watertreatmentplant AS water "
                + "LEFT JOIN city AS ci ON water.city_id=ci.city_id "
                + "WHERE IF('" + waterTreatmentPlantName + "'='',water.name LIKE '%%',water.name=?) "
                + "ORDER BY water.name "
                + "LIMIT " + lowerLimit + "," + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, waterTreatmentPlantName);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                WaterTreatmentPlant waterTreatmentPlantBean = new WaterTreatmentPlant();
                waterTreatmentPlantBean.setWaterTreatmentPlantId(rset.getInt("watertreatmentplant_id"));
                waterTreatmentPlantBean.setWaterTreatmentPlantName(rset.getString("name"));
                waterTreatmentPlantBean.setCapacityMLD(Integer.parseInt(rset.getString("capacity_mld")));
                waterTreatmentPlantBean.setCityName(rset.getString("city_name"));
                waterTreatmentPlantBean.setAddress1(rset.getString("address1"));
                waterTreatmentPlantBean.setAddress2(rset.getString("address2"));
                waterTreatmentPlantBean.setRemark(rset.getString("remark"));
                waterTreatmentPlantBean.setDateTime(rset.getString("date_time"));
                waterTreatmentPlantBean.setLatitude(rset.getString("latitude"));
                waterTreatmentPlantBean.setLongitude(rset.getString("longitude"));
                list.add(waterTreatmentPlantBean);
            }
        } catch (Exception e) {
            System.out.println("Error in getAllRecrod -- WaterTreatmentPlantModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        return list;
    }

    public int getTotalRowsInTable(String waterTreatmentPlantName) {
        String query = " SELECT count(*) "
                + " FROM watertreatmentplant "
                + "WHERE IF('" + waterTreatmentPlantName + "'='',name LIKE '%%',name=?)";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, waterTreatmentPlantName);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows - WaterTreatmentPlantModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

    public void insertRecord(WaterTreatmentPlant waterTreatmentPlantBean) {
        String query = "INSERT INTO watertreatmentplant(name,capacity_mld,city_id,address1,address2,remark, latitude, longitude) VALUES(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement presta = connection.prepareStatement(query);
            presta.setString(1, waterTreatmentPlantBean.getWaterTreatmentPlantName());
            presta.setInt(2, waterTreatmentPlantBean.getCapacityMLD());
            presta.setInt(3, waterTreatmentPlantBean.getCityId());
            presta.setString(4, waterTreatmentPlantBean.getAddress1());
            presta.setString(5, waterTreatmentPlantBean.getAddress2());
            presta.setString(6, waterTreatmentPlantBean.getRemark());
            presta.setString(7, waterTreatmentPlantBean.getLatitude());
            presta.setString(8, waterTreatmentPlantBean.getLongitude());
            int i = presta.executeUpdate();
            if (i >= 0) {
                message = "Record inserted succesfully";
                messageBGColor = "Yellow";
            } else {
                message = "Record not inserted succesfully";
                messageBGColor = "red";
            }
        } catch (Exception e) {
            System.out.println("Error in insertRecord - WaterTreatmentPlantModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Error in closeConnection -- WaterTreatmentPlantModel : " + e);
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
