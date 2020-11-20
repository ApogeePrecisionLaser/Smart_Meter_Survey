/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mis.model;

import com.mis.tableClasses.OnOffError;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author Navitus1
 */
public class OnOffErrorModel {

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

    public int getTotalRowsInTable() {

        String query = "select count(*) from (select count(error_log_id) from error_log el,"
                + " type_of_error toe,overheadtank o,status s,watertreatmentplant w"
                + " where el.overheadtank_id=o.overheadtank_id and el.type_of_error_id=toe.type_of_error_id"
                + " and el.status_id=s.status_id and w.watertreatmentplant_id=o.watertreatmentplant_id group by o.name,error_name) as a";
        int noOfRows = 0;
        try {
            PreparedStatement presta = connection.prepareStatement(query);

            ResultSet rs = presta.executeQuery();
            rs.next();
            noOfRows = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows - OHLevelModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

    public ArrayList<OnOffError> getAllRecords(int lowerLimit, int noOfRowsToDisplay) {
        ArrayList<OnOffError> list = new ArrayList<OnOffError>();
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }
        String query = " select w.name as name1,o.name,error_name,error_log_id from "
                + " error_log el,type_of_error toe,overheadtank o,status s,watertreatmentplant w"
                + " where el.overheadtank_id=o.overheadtank_id and el.type_of_error_id=toe.type_of_error_id"
                + " and el.status_id=s.status_id and w.watertreatmentplant_id=o.watertreatmentplant_id  group by o.name,error_name"
                + addQuery;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                OnOffError ohLevelBean = new OnOffError();
                ohLevelBean.setWaterTreatmentPlantName(rset.getString("name1"));
                ohLevelBean.setOverHeadTankName(rset.getString("name"));
                ohLevelBean.setError_name(rset.getString("error_name"));
                ohLevelBean.setError_log_id(rset.getInt("error_log_id"));
                list.add(ohLevelBean);
            }
        } catch (Exception e) {
            System.out.println("Error in getAllRecrod -- OHLevelModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        return list;
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
