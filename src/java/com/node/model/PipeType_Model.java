/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.node.model;

import com.layer.modellLayer.*;
import com.node.tableClasses.PipeType_Bean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shubham
 */
public class PipeType_Model
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

    public List<PipeType_Bean> ShowData(int lowerLimit, int noOfRowsToDisplay, String searchPipe_type)
    {
        List<PipeType_Bean> list = new ArrayList<PipeType_Bean>();
        String query = " select * from pipe_type "
                + " where IF('" + searchPipe_type + "'='',pipe_type LIKE '%%', pipe_type='" + searchPipe_type + "') AND active='Y' "
                + " ORDER BY pipe_type LIMIT " + lowerLimit + "," + noOfRowsToDisplay;
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                PipeType_Bean pipeType_Bean = new PipeType_Bean();
                pipeType_Bean.setPipe_type_id(rs.getInt("pipe_type_id"));
                pipeType_Bean.setPipe_type(rs.getString("pipe_type"));
                pipeType_Bean.setRemark(rs.getString("remark"));
                list.add(pipeType_Bean);
            }
        } catch (Exception e) {
            System.out.println("ShowData ERROR inside PipeTypeModel - " + e);
        }
        return list;
    }

    public int getNoOfRows(String searchPipe_type)
    {
        int count = 0;
        String query = " select count(*) as count from pipe_type "
                + " where IF('" + searchPipe_type + "'='',pipe_type LIKE '%%', pipe_type='" + searchPipe_type + "') AND active='Y' ";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (Exception e) {
            System.out.println("getNoOfRows ERROR inside PipeTypeModel - " + e);
        }
        return count;
    }

    public int insertRecord(PipeType_Bean pipeType_Bean) {
        int rowsAffected = 0;
        String query = "insert into pipe_type (pipe_type, remark) values (?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, pipeType_Bean.getPipe_type());
            ps.setString(2, pipeType_Bean.getRemark());
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
            System.out.println("getFuseType ERROR inside PipeTypeModel - " + e);
        }
        return rowsAffected;
    }

    

    public int UpdateRecord(PipeType_Bean pipeType_Bean) {
        int rowsAffected = 0;
        String query = " UPDATE pipe_type SET pipe_type=?, remark=? "
                + " WHERE pipe_type_id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            
            ps.setString(1, pipeType_Bean.getPipe_type());
            ps.setString(2, pipeType_Bean.getRemark());
            ps.setInt(3, pipeType_Bean.getPipe_type_id());
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
            System.out.println("getFuseType ERROR inside PipeTypeModel - " + e);
        }
        return rowsAffected;
    }

    public int deleteRecord(String meterDetailId)
    {
        int rowsAffected = 0;
        String query = "UPDATE pipe_type SET active='N' WHERE pipe_type_id=" + meterDetailId;
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
            System.out.println("deleteRecord ERROR inside PipeTypeModel - " + e);
        }
        return rowsAffected;
    }

    public List<String> getPipeType(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT pipe_type FROM pipe_type WHERE active='Y' "
                + " AND pipe_type LIKE '" + q.trim() + "%' ORDER BY pipe_type";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("pipe_type"));
            }
        } catch (Exception ex) {
            System.out.println("getPipeType ERROR inside PipeTypeModel - " + ex);
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
            System.out.println("Error: in closeConnection() in PipeTypeModel " + e);
        }
    }
}
