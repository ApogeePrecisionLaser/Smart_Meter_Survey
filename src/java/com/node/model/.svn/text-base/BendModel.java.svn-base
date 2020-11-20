/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.node.model;

import com.node.tableClasses.Bend;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Navitus1
 */
public class BendModel {
   private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";

 public List<Bend> ShowData(int lowerLimit, int noOfRowsToDisplay, String searchBend)
    {
        List<Bend> list = new ArrayList<Bend>();
        String query = " select * from bend "
                + " where IF('" + searchBend + "'='',bend_name LIKE '%%', bend_name='" + searchBend + "') "
                + " ORDER BY bend_id LIMIT " + lowerLimit + "," + noOfRowsToDisplay;
        try
        {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next())
            {
                Bend Bend = new Bend();
                Bend.setBend_id(rs.getInt("bend_id"));
                Bend.setBend_name(rs.getString("bend_name"));
                Bend.setRemark(rs.getString("remark"));
                list.add(Bend);
            }
        } catch (Exception e)
        {
            System.out.println("ShowData ERROR inside Bend - " + e);
        }
        return list;
    }

    public int getNoOfRows(String searchBend)
    {
        int count = 0;
        String query = " select count(*) as count from bend "
                + " where IF('" + searchBend + "'='',bend_name LIKE '%%', bend_name='" + searchBend + "') ";
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

    public int insertRecord(Bend Bend) {
        int rowsAffected = 0;
        String query = "insert into bend (bend_name, remark) values (?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, Bend.getBend_name());
            ps.setString(2, Bend.getRemark());
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

  public int UpdateRecord(Bend Bend) {
        int rowsAffected = 0;
        String query = " UPDATE bend SET bend_name=?, remark=? "
                + " WHERE bend_id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, Bend.getBend_name());
            ps.setString(2, Bend.getRemark());
            ps.setInt(3, Bend.getBend_id());
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

    public int deleteRecord(String meterDetailId)
    {
        int rowsAffected = 0;
        String query = "delete from  bend  WHERE bend_id=" + meterDetailId;
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

    public List<String> getBend(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT bend_name FROM bend WHERE' "
                + " AND bend_name LIKE '" + q.trim() + "%' ORDER BY bend_id";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("bend_name"));
            }
        } catch (Exception ex) {
            System.out.println("getReason ERROR  getBend- " + ex);
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
