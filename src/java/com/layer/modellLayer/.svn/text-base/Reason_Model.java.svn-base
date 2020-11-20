/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.layer.modellLayer;

import com.Bean.tableClasses.Reason_Bean;
import com.node.model.*;
import com.layer.modellLayer.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shubham
 */
public class Reason_Model
{
    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";

    public List<Reason_Bean> ShowData(int lowerLimit, int noOfRowsToDisplay, String searchPipe_type)
    {
        List<Reason_Bean> list = new ArrayList<Reason_Bean>();
        String query = " select * from reason "
                + " where IF('" + searchPipe_type + "'='',reason LIKE '%%', reason='" + searchPipe_type + "') AND active='Y' "
                + " ORDER BY reason LIMIT " + lowerLimit + "," + noOfRowsToDisplay;
        try
        {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next())
            {
                Reason_Bean reason_Bean = new Reason_Bean();
                reason_Bean.setReason_id(rs.getInt("reason_id"));
                reason_Bean.setReason(rs.getString("reason"));
                reason_Bean.setRemark(rs.getString("remark"));
                list.add(reason_Bean);
            }
        } catch (Exception e)
        {
            System.out.println("ShowData ERROR inside Reason_Model - " + e);
        }
        return list;
    }

    public int getNoOfRows(String searchPipe_type)
    {
        int count = 0;
        String query = " select count(*) as count from reason "
                + " where IF('" + searchPipe_type + "'='',reason LIKE '%%', reason='" + searchPipe_type + "') AND active='Y' ";
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

    public int insertRecord(Reason_Bean reason_Bean) {
        int rowsAffected = 0;
        String query = "insert into reason (reason, remark) values (?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, reason_Bean.getReason());
            ps.setString(2, reason_Bean.getRemark());
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

    

    public int UpdateRecord(Reason_Bean reason_Bean) {
        int rowsAffected = 0;
        String query = " UPDATE reason SET reason=?, remark=? "
                + " WHERE reason_id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            
            ps.setString(1, reason_Bean.getReason());
            ps.setString(2, reason_Bean.getRemark());
            ps.setInt(3, reason_Bean.getReason_id());
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
        String query = "UPDATE reason SET active='N' WHERE reason_id=" + meterDetailId;
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

    public List<String> getReason(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT reason FROM reason WHERE active='Y' "
                + " AND reason LIKE '" + q.trim() + "%' ORDER BY reason";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("reason"));
            }
        } catch (Exception ex) {
            System.out.println("getReason ERROR inside Reason_Model - " + ex);
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
