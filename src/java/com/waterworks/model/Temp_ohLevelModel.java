/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.waterworks.model;

import com.waterworks.tableClasses.Temp_ohLevel;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author Shobha
 */
public class Temp_ohLevelModel {
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

    public ArrayList<Temp_ohLevel> getAllRecords(int lowerLimit, int noOfRowsToDisplay, String waterTreatmentPlantName, String overHeadTankName, String oh_level, String searchDateFrom, String searchDateTo,String from_hr,String from_min,String to_hr,String to_min) {
        ArrayList<Temp_ohLevel> list = new ArrayList<Temp_ohLevel>();
        if (searchDateFrom != null && !searchDateFrom.isEmpty()) {
            String[] sdate_array = searchDateFrom.split("-");
            searchDateFrom = sdate_array[2] + "-" + sdate_array[1] + "-" + sdate_array[0]+" "+from_hr+":"+from_min;
        }
        if (searchDateTo != null && !searchDateTo.isEmpty()) {
            String[] sdate_array = searchDateTo.split("-");
            searchDateTo = sdate_array[2] + "-" + sdate_array[1] + "-" + sdate_array[0]+" "+to_hr+":"+to_min;
        }
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }
        byte[] twoByteData = new byte[2];
        String addOverheadtank_id = "";
        if (!oh_level.equals("")) {
            int overheadtank_id = getOverHeadTankid(Integer.parseInt(oh_level));
            addOverheadtank_id = " le.overheadtank_id='" + overheadtank_id + "' and ";
        }
        String query = " SELECT wtp.name as name1,oht.name,le.ohlevel_id,le.level_a,le.level_b,step,level1,level2,level3,level4,"
                + " le.date_time,le.remark, date_format(level_datetime, '%d-%m-%Y %h:%i') AS level_datetime,oht.capacity_height "
                + " FROM temp_ohlevel AS le "
                + " LEFT JOIN overheadtank AS oht ON le.overheadtank_id = oht.overheadtank_id "
                + " LEFT JOIN watertreatmentplant AS wtp ON oht.watertreatmentplant_id = wtp.watertreatmentplant_id "
                + " where " + addOverheadtank_id
                + "  IF('" + searchDateFrom + "'='', le.date_time LIKE '%%',le.date_time >='" + searchDateFrom + "') "
                + " And IF('" + searchDateTo + "'='', le.date_time LIKE '%%',date_format(le.date_time, '%Y-%m-%d') <='" + searchDateTo + "') "
                + " AND IF('" + overHeadTankName + "'='',oht.name LIKE '%%',oht.name=?) "
                + " AND IF('" + waterTreatmentPlantName + "'='',wtp.name LIKE '%%',wtp.name=?) "
                + "  ORDER BY ohlevel_id desc "
                + addQuery;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, overHeadTankName);
            pstmt.setString(2, waterTreatmentPlantName);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                Temp_ohLevel ohLevelBean = new Temp_ohLevel();
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
                list.add(ohLevelBean);
            }
        } catch (Exception e) {
            System.out.println("Error in getAllRecrod -- OHLevelModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        return list;
    }

    public int getTotalRowsInTable(String waterTreatmentName, String overHeadTankName, String oh_level, String searchDateFrom, String searchDateTo,String from_hr,String from_min,String to_hr,String to_min) {
        String addOverheadtank_id = "";
        if (!oh_level.equals("")) {
            int overheadtank_id = getOverHeadTankid(Integer.parseInt(oh_level));
            addOverheadtank_id = " le.overheadtank_id='" + overheadtank_id + "' and ";
        }
        if (searchDateFrom != null && !searchDateFrom.isEmpty()) {
            String[] sdate_array = searchDateFrom.split("-");
            searchDateFrom = sdate_array[2] + "-" + sdate_array[1] + "-" + sdate_array[0]+" "+from_hr+":"+from_min;
        }
        if (searchDateTo != null && !searchDateTo.isEmpty()) {
            String[] sdate_array = searchDateTo.split("-");
            searchDateTo = sdate_array[2] + "-" + sdate_array[1] + "-" + sdate_array[0]+" "+to_hr+":"+to_min;
        }
        String query = " SELECT count(ohlevel_id) "
                + " FROM temp_ohlevel AS le "
                + "LEFT JOIN overheadtank AS oht ON le.overheadtank_id = oht.overheadtank_id "
                + "LEFT JOIN watertreatmentplant AS wtp ON oht.watertreatmentplant_id = wtp.watertreatmentplant_id "
                + " WHERE  " + addOverheadtank_id
                + "  IF('" + searchDateFrom + "'='', le.date_time LIKE '%%',le.date_time >='" + searchDateFrom + "') "
                + " And IF('" + searchDateTo + "'='', le.date_time LIKE '%%',date_format(le.date_time, '%Y-%m-%d') <='" + searchDateTo + "') "
                + " AND IF('" + overHeadTankName + "'='',oht.name LIKE '%%',oht.name=?) "
                + "AND IF('" + waterTreatmentName + "'='',wtp.name LIKE '%%',wtp.name=?) ";
        int noOfRows = 0;
        try {
            PreparedStatement presta = connection.prepareStatement(query);
            presta.setString(1, overHeadTankName);
            presta.setString(2, waterTreatmentName);
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
