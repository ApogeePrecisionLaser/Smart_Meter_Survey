/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.layer.modellLayer;

import com.Bean.tableClasses.meterDetail_Bean;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Shubham
 */
public class MeterDetail_Model {

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";

    public List<meterDetail_Bean> ShowData(int lowerLimit, int noOfRowsToDisplay, String searchMeterNo, String searchMobileNo, String active) {
        String addActive = "";
        if (!active.isEmpty()) {
            if (active.equals("Y")) {
                addActive = " AND m.active='Y' ";
            } else {
                addActive = " AND m.active='N' ";
            }
        }
        List<meterDetail_Bean> list = new ArrayList<meterDetail_Bean>();
        String query = "SELECT kp.mobile_no1,n.node_name,meter_detail_id, kp.key_person_name, meter_no, meter_reading, date_time, m.latitude, m.longitude, m.remark,water_service_no,property_service_no "
                + " FROM meter_detail m,key_person as kp, node as n"
                + " WHERE kp.key_person_id=m.key_person_id AND n.node_id=m.node_id " + addActive + " and n.active='Y' "
                + " AND IF('" + searchMeterNo + "'='', m.meter_no LIKE '%%', m.meter_no='" + searchMeterNo + "') "
                + " AND IF('" + searchMobileNo + "'='', kp.mobile_no1 LIKE '%%', kp.mobile_no1='" + searchMobileNo + "') "
                + " ORDER BY water_service_no LIMIT " + lowerLimit + "," + noOfRowsToDisplay;
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
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
        } catch (Exception e) {
            System.out.println("ShowData ERROR inside MeterDetailModel - " + e);
        }
        return list;
    }

    public int getNoOfRows(String searchMeterNo, String searchMobileNo, String active) {
        int count = 0;
        String addActive = "";
        if (!active.isEmpty()) {
            if (active.equals("Y")) {
                addActive = " AND m.active='Y' ";
            } else {
                addActive = " AND m.active='N' ";
            }
        }
        String query = "SELECT count(meter_detail_id) as count FROM meter_detail m,key_person as kp, node as n "
                + " WHERE kp.key_person_id=m.key_person_id AND n.node_id=m.node_id " + addActive + " and n.active='Y' "
                + " AND IF('" + searchMeterNo + "'='', m.meter_no LIKE '%%', m.meter_no='" + searchMeterNo + "') "
                + " AND IF('" + searchMobileNo + "'='', kp.mobile_no1 LIKE '%%', kp.mobile_no1='" + searchMobileNo + "') ";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (Exception e) {
            System.out.println("getNoOfRows ERROR inside MeterDetailModel - " + e);
        }
        return count;
    }

    public int getNodeId(String node_name) {
        int node_id = 0;
        String query = "SELECT node_id FROM node WHERE node_name ='" + node_name + "' ";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                node_id = rs.getInt("node_id");
            }
        } catch (Exception ex) {
        }
        return node_id;
    }

    public int insertRecord(meterDetail_Bean meterDetail_Bean) {
        int rowsAffected = 0;
        String query = "INSERT INTO meter_detail (key_person_id, meter_no, meter_reading, date_time, latitude, longitude, remark,water_service_no,property_service_no,node_id) "
                + " VALUES (?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, getKeyPersonId(meterDetail_Bean.getKey_person_name()));
            ps.setString(2, meterDetail_Bean.getMeter_no());
            ps.setString(3, meterDetail_Bean.getMeter_reading());
            ps.setString(4, meterDetail_Bean.getDate_time());
            ps.setDouble(5, meterDetail_Bean.getLatitude());
            ps.setDouble(6, meterDetail_Bean.getLongitude());
            ps.setString(7, meterDetail_Bean.getRemark());
            ps.setString(8, meterDetail_Bean.getWater_service_no());
            ps.setString(9, meterDetail_Bean.getProperty_service_no());
            ps.setInt(10, getNodeId(meterDetail_Bean.getNode()));
            rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                message = "Record saved successfully.";
                msgBgColor = COLOR_OK;
            } else {
                message = "Cannot save the record, some error.";
                msgBgColor = COLOR_ERROR;
            }
        } catch (Exception e) {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
            System.out.println("getFuseType ERROR inside MeterDetailModel - " + e);
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

    public int UpdateRecord(meterDetail_Bean meterDetail_Bean) {
        int rowsAffected = 0;
        String query = " UPDATE meter_detail SET key_person_id=?, meter_no=?, meter_reading=?, date_time=?, latitude=?, longitude=?, remark=?,water_service_no=?,property_service_no=?,node_id=? "
                + " WHERE meter_detail_id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, getKeyPersonId(meterDetail_Bean.getKey_person_name()));
            ps.setString(2, meterDetail_Bean.getMeter_no());
            ps.setString(3, meterDetail_Bean.getMeter_reading());
            ps.setString(4, meterDetail_Bean.getDate_time());
            ps.setDouble(5, meterDetail_Bean.getLatitude());
            ps.setDouble(6, meterDetail_Bean.getLongitude());
            ps.setString(7, meterDetail_Bean.getRemark());
            ps.setString(8, meterDetail_Bean.getWater_service_no());
            ps.setString(9, meterDetail_Bean.getProperty_service_no());
            ps.setInt(10, getNodeId(meterDetail_Bean.getNode()));
            ps.setInt(11, meterDetail_Bean.getMeter_detail_id());
            rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                message = "Record Update successfully.";
                msgBgColor = COLOR_OK;
            } else {
                message = "Cannot update the record, some error.";
                msgBgColor = COLOR_ERROR;
            }
        } catch (Exception e) {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
            System.out.println("getFuseType ERROR inside MeterDetailModel - " + e);
        }
        return rowsAffected;
    }

    public int deleteRecord(String meterDetailId) {
        int rowsAffected = 0;
        String query = "UPDATE meter_detail SET active='N' WHERE meter_detail_id=" + meterDetailId;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
            if (rowsAffected > 0) {
                message = "Record deleted successfully.";
                msgBgColor = COLOR_OK;
            } else {
                message = "Cannot delete the record, some error.";
                msgBgColor = COLOR_ERROR;
            }

        } catch (Exception e) {
            message = "Cannot delete the record, some error.";
            msgBgColor = COLOR_ERROR;
            System.out.println("getFuseType ERROR inside MeterDetailModel - " + e);
        }
        return rowsAffected;
    }

    public List<String> getMeterNo(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT meter_no FROM meter_detail WHERE active='Y' "
                + " AND meter_no LIKE '" + q.trim() + "%' ORDER BY meter_no";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("meter_no"));
            }
        } catch (Exception ex) {
            System.out.println("getMeterNo ERROR inside MeterDetailModel - " + ex);
        }
        return list;
    }

    public List<String> checkMeterNo(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT meter_no FROM meter_detail WHERE meter_no = '" + q.trim() + "'";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("meter_no"));
            }
        } catch (Exception ex) {
            System.out.println("getMeterNo ERROR inside MeterDetailModel - " + ex);
        }
        return list;
    }

    public JSONObject getLatLong()
    {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        String query = " Select latitude,longitude from meter_detail";
        try
        {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next())
            {
                JSONObject json1 = new JSONObject();
                json1.put("latitude", rs.getDouble("latitude"));
                json1.put("longitude", rs.getDouble("longitude"));
                jsonArray.add(json1);
            }
        } catch (Exception e) {
            System.out.println("ShowData ERROR inside PipeTypeModel - " + e);
        }
        json.put("data", jsonArray);
        return json;
    }

    public List<String> getMobileNo(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT mobile_no1 FROM key_person WHERE  "
                + " mobile_no1 LIKE '" + q.trim() + "%' and mobile_no1 REGEXP '[0-9]'";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("mobile_no1"));
            }
        } catch (Exception ex) {
            System.out.println("getMeterNo ERROR inside MeterDetailModel - " + ex);
        }
        return list;
    }

    public List<String> getNodeName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select node_name from node";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String node_name = rset.getString("node_name");
                if (node_name.startsWith(q)) {
                    list.add(node_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Person of such Office Code Exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--getDesignationList()-- " + e);
        }
        return list;
    }

    public String getMessage() {
        return message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
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

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Error: in closeConnection() in MeterDetailModel " + e);
        }
    }
}
