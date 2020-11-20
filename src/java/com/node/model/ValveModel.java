/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.node.model;

import com.node.tableClasses.Valve;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Navitus1
 */
public class ValveModel {

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";

    public List<Valve> ShowData(int lowerLimit, int noOfRowsToDisplay, String searchValve) {
        List<Valve> list = new ArrayList<Valve>();
        String query = " select * from valve "
                + " where IF('" + searchValve + "'='',valve_name LIKE '%%', valve_name='" + searchValve + "') "
                + " ORDER BY Valve_id LIMIT " + lowerLimit + "," + noOfRowsToDisplay;
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                Valve Valve = new Valve();
                Valve.setValve_id(rs.getInt("Valve_id"));
                Valve.setValve_name(rs.getString("valve_name"));
                  Valve.setDiameter(rs.getDouble("diameter"));
                Valve.setRemark(rs.getString("remark"));
                list.add(Valve);
            }
        } catch (Exception e) {
            System.out.println("ShowData ERROR inside Valve - " + e);
        }
        return list;
    }

    public int getNoOfRows(String searchValve) {
        int count = 0;
        String query = " select count(*) as count from valve "
                + " where IF('" + searchValve + "'='',Valve_name LIKE '%%', Valve_name='" + searchValve + "') ";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (Exception e) {
            System.out.println("getNoOfRows ERROR inside Reason_Model - " + e);
        }
        return count;
    }

    public int insertRecord(Valve Valve) {
        int rowsAffected = 0;
        String query = "insert into valve (valve_name,diameter, remark) values (?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, Valve.getValve_name());
            ps.setDouble(2, Valve.getDiameter());
            ps.setString(3, Valve.getRemark());
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
            System.out.println("insertRecord ERROR inside Reason_Model - " + e);
        }
        return rowsAffected;
    }

    public int UpdateRecord(Valve Valve) {
        int rowsAffected = 0;
        String query = " UPDATE valve SET valve_name=?,diameter=?, remark=? "
                + " WHERE Valve_id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, Valve.getValve_name());
            ps.setDouble(2, Valve.getDiameter());
            ps.setString(3, Valve.getRemark());
            ps.setInt(4, Valve.getValve_id());
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
            System.out.println("getFuseType ERROR inside Reason_Model - " + e);
        }
        return rowsAffected;
    }

    public int deleteRecord(String meterDetailId) {
        int rowsAffected = 0;
        String query = "delete from  valve  WHERE valve_id=" + meterDetailId;
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
            System.out.println("deleteRecord ERROR inside Reason_Model - " + e);
        }
        return rowsAffected;
    }

    public List<String> getValve(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT valve_name FROM valve WHERE' "
                + " AND Valve_name LIKE '" + q.trim() + "%' ORDER BY Valve_id";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("Valve_name"));
            }
        } catch (Exception ex) {
            System.out.println("getReason ERROR  getValve- " + ex);
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
            System.out.println("Error: in closeConnection() in Reason_Model " + e);
        }
    }
}
