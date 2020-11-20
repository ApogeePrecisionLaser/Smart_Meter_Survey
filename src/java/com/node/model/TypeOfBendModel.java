/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.node.model;

import com.node.tableClasses.PipeType_Bean;
import com.node.tableClasses.TypeOfBend;
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
public class TypeOfBendModel {

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";

    public List<TypeOfBend> ShowData(int lowerLimit, int noOfRowsToDisplay, int pipe_detail_id) {
        List<TypeOfBend> list = new ArrayList<TypeOfBend>();
        String query = " select tob.type_of_bend_id, bend_name, bend_order,tob.remark,latitude,longitude from type_of_bend as tob "
                + " left join pipe_bend as pb ON tob.type_of_bend_id=pb.type_of_bend_id "
                + " left join bend as b ON b.bend_id=tob.bend_id where pb.pipe_detail_id=" + pipe_detail_id;
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                TypeOfBend typeOfBend = new TypeOfBend();
                typeOfBend.setType_of_bendId(rs.getInt("type_of_bend_id"));
                typeOfBend.setBendOrder(rs.getInt("bend_order"));
                typeOfBend.setType_ofBend(rs.getString("bend_name"));
                typeOfBend.setRemark1(rs.getString("remark"));
                typeOfBend.setLatitude(rs.getDouble("latitude"));
                typeOfBend.setLongitude(rs.getDouble("longitude"));
                list.add(typeOfBend);
            }
        } catch (Exception e) {
            System.out.println("ShowData ERROR inside PipeTypeModel - " + e);
        }
        return list;
    }

    public JSONObject getPipeDetail(int pipe_detail_id) {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        String query = " select latitude,longitude from type_of_bend as tob "
                + " left join pipe_bend as pb ON tob.type_of_bend_id=pb.type_of_bend_id where pb.pipe_detail_id=" + pipe_detail_id + " order by bend_order";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
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

    public int getNoOfRows(int pipe_detail_id) {
        int count = 0;
        String query = " select count(*) as count from type_of_bend as tob "
                + " left join pipe_bend as pb ON tob.type_of_bend_id=pb.type_of_bend_id "
                + " left join bend as b ON b.bend_id=tob.bend_id where pb.pipe_detail_id=" + pipe_detail_id;
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

    public boolean insertMultipleRecord(TypeOfBend typeOfBend, int pipe_detail_id) {
        int a = 0;
        String query = "";
        PreparedStatement pstmt = null;
        boolean status = false;
        try {
            String[] type_Of_Bend = typeOfBend.getType_of_bend();
            for (int i = 0; i < type_Of_Bend.length; i++) {
                query = "Insert into type_of_bend (bend_id, bend_order, remark,pipe_detail_id) values (?,?,?,?) ";
                if (typeOfBend.getType_of_bend_id()[i].equals("1")) {
                    pstmt = connection.prepareStatement(query);
                    pstmt.setInt(1,getBendId(typeOfBend.getType_of_bend()[i]));
                    pstmt.setString(2, typeOfBend.getBend_order()[i]);
                    pstmt.setString(3, typeOfBend.getRemark()[i]);
                    pstmt.setInt(4, pipe_detail_id);
                    a = pstmt.executeUpdate();
                    if (a > 0) {
                        ResultSet rs = pstmt.getGeneratedKeys();
                        int id = 0;
                        if (rs != null && rs.next()) {
                            id = rs.getInt(1);
                        }
                        a = 0;
                        query = "insert into pipe_bend (latitude, longitude, remark, type_of_bend_id, pipe_detail_id) values (?,?,?,?,?) ";
                        pstmt = connection.prepareStatement(query);
                        pstmt.setString(1, typeOfBend.getLatitude1()[i]);
                        pstmt.setString(2, typeOfBend.getLongitude1()[i]);
                        pstmt.setString(3, typeOfBend.getRemark()[i]);
                        pstmt.setInt(4, id);
                        pstmt.setInt(5, pipe_detail_id);
                        a = pstmt.executeUpdate();
                    }
                }
            }
            if (a > 0) {
                status = true;
            }
        } catch (Exception e) {
            System.out.println("CityStatusModel insertMultipleRecord() Error: " + e);
        }
        return status;
    }

    public boolean updateRecord(TypeOfBend typeOfBend, int pipe_detail_id) {
        String query = " UPDATE type_of_bend SET bend_id=?,bend_order=?, remark=? "
                + " WHERE type_of_bend_id=?";
        int a = 0;
        boolean status = false;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            pstmt.setInt(1,getBendId(typeOfBend.getType_ofBend()));
            pstmt.setInt(2, typeOfBend.getBendOrder());
            pstmt.setString(3, typeOfBend.getRemark1());
            pstmt.setInt(4, typeOfBend.getType_of_bendId());
            a = pstmt.executeUpdate();
            if (a > 0) {
                query = " UPDATE pipe_bend SET latitude=?,longitude=? "
                        + " WHERE type_of_bend_id=? AND pipe_detail_id=?";
                pstmt = (PreparedStatement) connection.prepareStatement(query);
                pstmt.setDouble(1, typeOfBend.getLatitude());
                pstmt.setDouble(2, typeOfBend.getLongitude());
                pstmt.setInt(3, typeOfBend.getType_of_bendId());
                pstmt.setInt(4, pipe_detail_id);
                a = pstmt.executeUpdate();
                status = true;
            }
        } catch (Exception e) {
            System.out.println("cityStatusModel updateRecord() Error: " + e);
        }

        return status;
    }

    public int deleteRecord(int id) {
        int rowsAffected = 0;
        String query = "DELETE from pipe_bend where type_of_bend_id=" + id;
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
public int getBendId(String type_of_bend){
    int bend_id=0;
    try {
        String query="select bend_id from bend where bend_name='"+type_of_bend+"'";
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if(rs.next()) {
                bend_id = rs.getInt("bend_id");
            }
        } catch (Exception ex) {
            System.out.println("ERROR inside getBendId - " + ex);
        }
    return bend_id;
}
    public List<String> getTypeofbend(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT bend_name FROM bend group by bend_name";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                String bend_name = rs.getString("bend_name");
            //   if (bend_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(bend_name);
            //    }
            }
        } catch (Exception ex) {
            System.out.println("ERROR inside getTypeofbend - " + ex);
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
