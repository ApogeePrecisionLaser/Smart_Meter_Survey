/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.layer.modellLayer;

import com.Bean.tableClasses.MultipleMeterDetail;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Shubham
 */
public class MultipleMeterDetailModel {

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

    public int insertRecord(MultipleMeterDetail bean) throws SQLException {
        connection.setAutoCommit(false);
        int rowsAffected = 0, key_person_id = 0;
        String query = "insert into key_person (key_person_name,org_office_id,mobile_no1,emp_code,designation_id,address_line1) values(?,?,?,?,?,?)";
        String insertMeterDetail = "insert into meter_detail(key_person_id,meter_no, meter_reading, date_time,water_service_no,property_service_no ) values(?,?,?,?,?,?)";
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            String[] key_person_name = bean.getKey_person_name();
            for (int i = 0; i < key_person_name.length; i++) {
                int meter_detail_id = Integer.parseInt(bean.getMeter_detail_id()[i]);
                if (meter_detail_id == 1) {
                    ps.setString(1, bean.getKey_person_name()[i]);
                    ps.setInt(2, 1);
                    ps.setString(3, bean.getMobile_no()[i]);
                    ps.setInt(4, getMaxEmpCode());
                    ps.setInt(5, 1);
                    ps.setString(6, bean.getRemark()[i]);
                    ps.executeUpdate();
                    ResultSet rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        key_person_id = rs.getInt(1);
                    }
                    if (key_person_id > 0) {
                        PreparedStatement ps1 = (PreparedStatement) connection.prepareStatement(insertMeterDetail);
                        ps1.setInt(1, key_person_id);
                        ps1.setString(2, bean.getMeter_no()[i]);
                        ps1.setDouble(3, Double.parseDouble(bean.getMeter_reading()[i]));
                        ps1.setString(4, bean.getDate_time()[i]);
                        ps1.setString(5, bean.getWater_service_no()[i]);
                        ps1.setString(6, bean.getProperty_service_no()[i]);
                        rowsAffected = ps1.executeUpdate();
                    }
                }
            }
        } catch (Exception e) {
            connection.rollback();
            message = "Record Not Inserted ......";
            msgBgColor = COLOR_OK;
            System.out.println(" Not Inserted");
            System.out.println("ERROR: in insertRecord in MultipleMeterDetailModel : " + e);
        }
        if (rowsAffected > 0) {
            connection.commit();
            connection.setAutoCommit(true);
            message = "Record Inserted successfully......";
            msgBgColor = COLOR_OK;
            System.out.println("Inserted");
        } else {
            connection.rollback();
            message = "Record not Inserted Some Error!";
            msgBgColor = COLOR_ERROR;
            System.out.println("not Inserted");
        }
        return rowsAffected;
    }

    public int getKeyPersonId(String person_name) {
        int id = 0;
        String query = "SELECT key_person_id FROM key_person WHERE key_person_name='" + person_name + "'";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                id = rs.getInt("key_person_id");
            }
        } catch (Exception ex) {
            System.out.println("ERROR : in getKeyPersonId inside MeterDetailModel : " + ex);
        }
        return id;
    }

    public int getMaxEmpCode() {
        int id = 0;
        String query = "SELECT max(emp_code) as emp_code from key_person";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                id = rs.getInt("emp_code");
                id = id + 1;
            }
        } catch (Exception ex) {
            System.out.println("ERROR : in getKeyPersonId inside MeterDetailModel : " + ex);
        }
        return id;
    }

    public int checkMob1Value(String mobile_no1) {
        int value = 1, count = 0;
        String query = "SELECT count(*) AS count FROM key_person k where mobile_no1 = '"+mobile_no1+"' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            if (rset.next()) {
                count = rset.getInt("count");
            }
        } catch (Exception e) {
            System.out.println("RegistrationModel checkMob1Value() Error: " + e);
        }

        if (count > 0) {
            value = 0;
        } else {
            value = 1;
        }

        return value;
    }

    public void setConnection() {
        try {
            Class.forName(driver);
            // connection = DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            connection = (Connection) DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("WattageTypeModel setConnection() Error: " + e);
        }
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
