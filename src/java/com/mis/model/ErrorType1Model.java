/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mis.model;

import com.mis.tableClasses.ErrorType1;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author Navitus1
 */
public class ErrorType1Model {

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

        String query = " select count(*) from (select w.name as name1,o.name,error_name,error_value,status_name,d.created_date,el.remark,mor_on_time,"
                + "  mor_off_time, eve_on_time, eve_off_time from "
                + "  error_log el,type_of_error toe,overheadtank o,status s,watertreatmentplant w,distribution d,ohlevel ol"
                + "  where el.overheadtank_id=o.overheadtank_id and el.type_of_error_id=toe.type_of_error_id "
                + "  and el.status_id=s.status_id and w.watertreatmentplant_id=o.watertreatmentplant_id"
                + "  and d.ohlevel_id=ol.ohlevel_id and o.overheadtank_id=ol.overheadtank_id group by name,error_name,el.created_date ) as a";
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

    public ArrayList<ErrorType1> getAllRecords(int lowerLimit, int noOfRowsToDisplay) {
        ArrayList<ErrorType1> list = new ArrayList<ErrorType1>();
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }
        String query = " select error_log_id,w.name as name1,o.name,error_name,error_value,status_name,d.created_date,el.remark,mor_on_time,"
                + "  mor_off_time, eve_on_time, eve_off_time from "
                + "  error_log el,type_of_error toe,overheadtank o,status s,watertreatmentplant w,distribution d,ohlevel ol"
                + "  where el.overheadtank_id=o.overheadtank_id and el.type_of_error_id=toe.type_of_error_id "
                + "  and el.status_id=s.status_id and w.watertreatmentplant_id=o.watertreatmentplant_id"
                + "  and d.ohlevel_id=ol.ohlevel_id and o.overheadtank_id=ol.overheadtank_id and el.distribution_id=d.distribution_id order by error_log_id desc"
                + addQuery;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                ErrorType1 ohLevelBean = new ErrorType1();
                ohLevelBean.setWaterTreatmentPlantName(rset.getString("name1"));
                ohLevelBean.setOverHeadTankName(rset.getString("name"));
                ohLevelBean.setError_name(rset.getString("error_name"));
                ohLevelBean.setError_value(rset.getString("error_value"));
                ohLevelBean.setError_log_id(rset.getInt("error_log_id"));
                String created_date = rset.getString("created_date");
                ohLevelBean.setDate(created_date.split(" ")[0]);
                ohLevelBean.setActual_time(created_date.split(" ")[1]);
                String status_name = rset.getString("status_name");
                ohLevelBean.setStatus_name(status_name);
                String mor_on_time = rset.getString("mor_on_time");
                String mor_off_time = rset.getString("mor_off_time");
                String eve_on_time = rset.getString("eve_on_time");
                String eve_off_time = rset.getString("eve_off_time");
                String remark = rset.getString("remark");
                ohLevelBean.setRemark(rset.getString("remark"));
                if (remark.split(" ")[1].equals("Off") && remark.split(" ")[4].equals("Morning")) {
                    ohLevelBean.setSchedule_time(mor_off_time);
                } else if (remark.split(" ")[1].equals("On") && remark.split(" ")[4].equals("Morning")) {
                    ohLevelBean.setSchedule_time(mor_on_time);
                } else if (remark.split(" ")[1].equals("On") && remark.split(" ")[4].equals("Evening")) {
                    ohLevelBean.setSchedule_time(eve_on_time);
                } else if (remark.split(" ")[1].equals("Off") && remark.split(" ")[4].equals("Evening")) {
                    ohLevelBean.setSchedule_time(eve_off_time);
                }
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
