package com.node.model;

import com.Bean.tableClasses.meterDetail_Bean;
import com.waterworks.model.*;
import com.waterworks.tableClasses.OverHeadTank;
import com.waterworks.tableClasses.WaterTreatmentPlant;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class NearestMapModel {

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

    public List<OverHeadTank> showAllData(String range, String overHeadTankName) {
        ArrayList<OverHeadTank> list = new ArrayList<OverHeadTank>();
        String query = "SELECT wtp.name,oht.name,oht.capacity_height,oht.capacity_ltr,oht.height,ci.city_name,oht.address1,"
                + " oht.address2,oht.date_time,oht.remark, oht.latitude, oht.longitude "
                + "FROM overheadtank AS oht "
                + "LEFT JOIN watertreatmentplant AS wtp ON oht.watertreatmentplant_id = wtp.watertreatmentplant_id "
                + "LEFT JOIN city AS ci ON oht.city_id = ci.city_id "
                + "WHERE IF('" + range + "'='',wtp.name LIKE '%%',wtp.name=?) "
                + "AND IF('" + overHeadTankName + "'='',oht.name LIKE '%%',oht.name=?) "
                + "ORDER BY oht.name ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, range);
            pstmt.setString(2, overHeadTankName);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                OverHeadTank overHeadTankBean = new OverHeadTank();
                overHeadTankBean.setWaterTreatmentPlantName(rset.getString(1));
                overHeadTankBean.setOverHeadTankName(rset.getString(2));
                overHeadTankBean.setCapacityHeight(rset.getInt("capacity_height"));
                overHeadTankBean.setCapacityLTR(rset.getInt("capacity_ltr"));
                overHeadTankBean.setHeight(rset.getInt("height"));
                overHeadTankBean.setCityName(rset.getString("city_name"));
                overHeadTankBean.setAddress1(rset.getString("address1"));
                overHeadTankBean.setAddress2(rset.getString("address2"));
                overHeadTankBean.setRemark(rset.getString("remark"));
                overHeadTankBean.setDateTime(rset.getString("date_time"));
                overHeadTankBean.setLatitude(rset.getString("latitude"));
                overHeadTankBean.setLongitude(rset.getString("longitude"));
                list.add(overHeadTankBean);
            }
        } catch (Exception e) {
            System.out.println("Error in showAllRecrod -- OverHeadTankModel : " + e);
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
            System.out.println("Error in getOverHeadTank ERROR inside OverHeadTankModel - " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        return list;
    }

    public int getWaterTreatmentPlantId(String range) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT watertreatmentplant_id FROM watertreatmentplant WHERE name='" + range + "'";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            if (rset.next()) {
                return rset.getInt("watertreatmentplant_id");
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Error in getOverHeadTankId - OverHeadTankModel : " + range);
            message = "Something going wrong";
            messageBGColor = "red";
            return 0;
        }
    }

    public List<String> getOverHeadTank(String q, String range) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT oht.name FROM overheadtank as oht "
                + "LEFT JOIN watertreatmentplant AS wtp ON oht.watertreatmentplant_id = wtp.watertreatmentplant_id "
                + "WHERE IF('" + range + "'='',wtp.name LIKE '%%',wtp.name='" + range + "') "
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
            System.out.println("Error in getCityName - OverHeadTankModel : " + e);
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
            System.out.println("Error in getCityId - OverHeadTankModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        return 0;
    }

    public void updateRecord(OverHeadTank overHeadTankBean) {
        String query = "UPDATE overheadtank SET watertreatmentplant_id=?,name=?,capacity_height=?,capacity_ltr=?,height=?,city_id=?,address1=?,address2=?,remark=?, latitude=?, longitude=? WHERE overheadtank_id=?";
        try {
            PreparedStatement presta = connection.prepareStatement(query);
            presta.setInt(1, overHeadTankBean.getWaterTreatmentPlantId());
            presta.setString(2, overHeadTankBean.getOverHeadTankName());
            presta.setFloat(3, overHeadTankBean.getCapacityHeight());
            presta.setFloat(4, overHeadTankBean.getCapacityLTR());
            presta.setFloat(5, overHeadTankBean.getHeight());
            presta.setInt(6, overHeadTankBean.getCityId());
            presta.setString(7, overHeadTankBean.getAddress1());
            presta.setString(8, overHeadTankBean.getAddress2());
            presta.setString(9, overHeadTankBean.getRemark());
            presta.setString(10, overHeadTankBean.getLatitude());
            presta.setString(11, overHeadTankBean.getLongitude());
            presta.setInt(12, overHeadTankBean.getOverHeadTankId());
            int i = presta.executeUpdate();
            if (i > 0) {
                message = "Record updated successfully";
                messageBGColor = "yellow";
            } else {
                message = "Record not updated successfully";
                messageBGColor = "red";
            }
        } catch (Exception e) {
            System.out.println("Error in updateRecord - OverHeadTankModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
    }

    public void deleteRecord(int overHeadTankId) {
        PreparedStatement presta = null;
        try {
            presta = connection.prepareStatement("DELETE FROM overheadtank WHERE overheadtank_id=?");
            presta.setInt(1, overHeadTankId);
            int i = presta.executeUpdate();
            if (i > 0) {
                message = "Record deleted successfully......";
                messageBGColor = "yellow";
            } else {
                message = "Record not deleted successfully......";
                messageBGColor = "red";
            }
        } catch (Exception e) {
            System.out.println("Error in deleteRecord ---- OverHeadTankModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
    }

    public List<meterDetail_Bean> ShowData(int range, double current_latitude, double current_longtitude) {
        List<meterDetail_Bean> list = new ArrayList<meterDetail_Bean>();
        String query = "SELECT n.node_name,meter_detail_id, kp.key_person_name, meter_no, meter_reading, date_time, m.latitude, m.longitude, m.remark,water_service_no,property_service_no "
                + " FROM meter_detail m,key_person as kp, node as n"
                + " WHERE kp.key_person_id=m.key_person_id AND n.node_id=m.node_id AND m.active='Y' and n.active='Y' "
                + " ORDER BY m.created_date";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                String latitude = rs.getString("latitude");
                String longitude = rs.getString("longitude");
                if ((!latitude.isEmpty() || !longitude.isEmpty()) && (!latitude.startsWith("0.00") && !longitude.startsWith("0.00"))) {
                    double d = distance(current_latitude, current_longtitude, Double.parseDouble(latitude), Double.parseDouble(longitude), "K");
                    if (range >= d) {
                        meterDetail_Bean meterDetail_Bean = new meterDetail_Bean();
                        meterDetail_Bean.setMeter_detail_id(rs.getInt("meter_detail_id"));
                        meterDetail_Bean.setKey_person_name(rs.getString("key_person_name"));
                        meterDetail_Bean.setMeter_no(rs.getString("meter_no"));
                        meterDetail_Bean.setMeter_reading(rs.getString("meter_reading"));
                        meterDetail_Bean.setDate_time(rs.getString("date_time"));
                        meterDetail_Bean.setLatitude(rs.getDouble("latitude"));
                        meterDetail_Bean.setLongitude(rs.getDouble("longitude"));
                        meterDetail_Bean.setRemark(rs.getString("remark"));
                        meterDetail_Bean.setWater_service_no(rs.getString("water_service_no"));
                        meterDetail_Bean.setProperty_service_no(rs.getString("property_service_no"));
                        meterDetail_Bean.setNode(rs.getString("node_name"));
                        list.add(meterDetail_Bean);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("ShowData ERROR inside MeterDetailModel - " + e);
        }
        return list;
    }

    public ArrayList<WaterTreatmentPlant> getAllRecords1(int range, double current_latitude, double current_longtitude) {
        ArrayList<WaterTreatmentPlant> list = new ArrayList<WaterTreatmentPlant>();
        String query = "SELECT water.watertreatmentplant_id,water.name,water.capacity_mld,ci.city_name,water.address1,water.address2,water.remark, water.date_time, water.latitude, water.longitude "
                + "FROM watertreatmentplant AS water "
                + "LEFT JOIN city AS ci ON water.city_id=ci.city_id "
                + "ORDER BY water.name ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next())
            {
                String latitude = rset.getString("latitude");
                String longitude = rset.getString("longitude");
                if ((!latitude.isEmpty() || !longitude.isEmpty()) && (!latitude.startsWith("0.00") && !longitude.startsWith("0.00")))
                {
                    double d = distance(current_latitude, current_longtitude, Double.parseDouble(latitude), Double.parseDouble(longitude), "K");
                    if (range >= d)
                    {
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
                }
            }
        } catch (Exception e) {
            System.out.println("Error in getAllRecrod -- WaterTreatmentPlantModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        return list;
    }

    public ArrayList<OverHeadTank> getAllRecords(int range, double current_latitude, double current_longtitude) {
        ArrayList<OverHeadTank> list = new ArrayList<OverHeadTank>();
        String query = "SELECT wtp.name,oht.overheadtank_id,oht.name,oht.capacity_height,oht.capacity_ltr,oht.height,"
                + " ci.city_name,oht.address1,oht.address2,oht.date_time,oht.remark, oht.latitude, oht.longitude "
                + "FROM overheadtank AS oht "
                + "LEFT JOIN watertreatmentplant AS wtp ON oht.watertreatmentplant_id = wtp.watertreatmentplant_id "
                + "LEFT JOIN city AS ci ON oht.city_id = ci.city_id "
                + "ORDER BY oht.name ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                String latitude = rset.getString("latitude");
                String longitude = rset.getString("longitude");
                if ((!latitude.isEmpty() || !longitude.isEmpty()) && (!latitude.startsWith("0.00") && !longitude.startsWith("0.00"))) {
                    double d = distance(current_latitude, current_longtitude, Double.parseDouble(latitude), Double.parseDouble(longitude), "K");
                    if (range >= d) {
                        OverHeadTank overHeadTankBean = new OverHeadTank();
                        overHeadTankBean.setWaterTreatmentPlantName(rset.getString(1));
                        overHeadTankBean.setOverHeadTankId(rset.getInt("overheadtank_id"));
                        overHeadTankBean.setOverHeadTankName(rset.getString(3));
                        overHeadTankBean.setCapacityHeight(rset.getFloat("capacity_height"));
                        overHeadTankBean.setCapacityLTR(rset.getInt("capacity_ltr"));
                        System.out.println("Height is --- " + rset.getDouble("height"));
                        overHeadTankBean.setHeight(rset.getFloat("height"));
                        overHeadTankBean.setCityName(rset.getString("city_name"));
                        overHeadTankBean.setAddress1(rset.getString("address1"));
                        overHeadTankBean.setAddress2(rset.getString("address2"));
                        overHeadTankBean.setRemark(rset.getString("remark"));
                        overHeadTankBean.setDateTime(rset.getString("date_time"));
                        overHeadTankBean.setLatitude(rset.getString("latitude"));
                        overHeadTankBean.setLongitude(rset.getString("longitude"));
                        list.add(overHeadTankBean);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error in getAllRecrod -- OverHeadTankModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        return list;
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "N") {
            dist = dist * 0.8684;
        }

        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::	This function converts decimal degrees to radians						 :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::	This function converts radians to decimal degrees						 :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    public int getTotalRowsInTable(int range, String overHeadTankName, double current_latitude, double current_longtitude) {
        String query = " SELECT count(*) "
                + "FROM overheadtank AS oht "
                + "LEFT JOIN watertreatmentplant AS wtp ON oht.watertreatmentplant_id = wtp.watertreatmentplant_id "
                + "LEFT JOIN city AS ci ON oht.city_id = ci.city_id "
                + "ORDER BY oht.name ";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(2, overHeadTankName);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows - OverHeadTankModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

    public void insertRecord(OverHeadTank overHeadTankBean) {
        String query = "INSERT INTO overheadtank(watertreatmentplant_id,name,capacity_height,capacity_ltr,height,city_id,address1,address2,remark, latitude, longitude) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement presta = connection.prepareStatement(query);
            presta.setInt(1, overHeadTankBean.getWaterTreatmentPlantId());
            presta.setString(2, overHeadTankBean.getOverHeadTankName());
            presta.setFloat(3, overHeadTankBean.getCapacityHeight());
            presta.setFloat(4, overHeadTankBean.getCapacityLTR());
            presta.setFloat(5, overHeadTankBean.getHeight());
            presta.setInt(6, overHeadTankBean.getCityId());
            presta.setString(7, overHeadTankBean.getAddress1());
            presta.setString(8, overHeadTankBean.getAddress2());
            presta.setString(9, overHeadTankBean.getRemark());
            presta.setString(10, overHeadTankBean.getLatitude());
            presta.setString(11, overHeadTankBean.getLongitude());
            int i = presta.executeUpdate();
            if (i >= 0) {
                message = "Record inserted succesfully";
                messageBGColor = "Yellow";
            } else {
                message = "Record not inserted succesfully";
                messageBGColor = "red";
            }
        } catch (Exception e) {
            System.out.println("Error in insertRecord - OverHeadTankModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Error in closeConnection -- OverHeadTankModel : " + e);
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
