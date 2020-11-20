/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.node.model;

import com.node.tableClasses.OverHead_WaterPlant;
import com.node.tableClasses.PurposeHeaderNode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Navitus1
 */
public class OverHead_WaterPlantModel {

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

    public int getTotalRowsInTable(String search_watertreatmentplant, String search_overheadtank_name) {
        String query = " select count(watertreatmentplant_overheadtank_id) "
                + " from watertreatmentplant_overheadtank as wtom,overheadtank as oh, watertreatmentplant as wtp where wtom.watertreatmentplant_id=wtp.watertreatmentplant_id and oh.overheadtank_id=wtom.overheadtank_id "
                + " AND if('" + search_watertreatmentplant + "'='', wtp.name LIKE '%%', wtp.name LIKE '%" + search_watertreatmentplant + "%')"
                + " AND if('" + search_overheadtank_name + "'='', oh.name LIKE '%%', oh.name LIKE '%" + search_overheadtank_name + "%')";
        int noOfRows = 0;
        try {
            PreparedStatement presta = connection.prepareStatement(query);
            ResultSet rs = presta.executeQuery();
            rs.next();
            noOfRows = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows  : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

    public List<OverHead_WaterPlant> showAllData(int lowerLimit, int noOfRowsToDisplay, String search_watertreatmentplant, String search_overheadtank_name) {
        ArrayList<OverHead_WaterPlant> list = new ArrayList<OverHead_WaterPlant>();
        String query = " select  watertreatmentplant_overheadtank_id,oh.name as name1,wtom.remark,wtp.name as name2 "
                 + " from watertreatmentplant_overheadtank as wtom,overheadtank as oh, watertreatmentplant as wtp where wtom.watertreatmentplant_id=wtp.watertreatmentplant_id and oh.overheadtank_id=wtom.overheadtank_id "
                + " AND if('" + search_watertreatmentplant + "'='', wtp.name LIKE '%%', wtp.name LIKE '%" + search_watertreatmentplant + "%')"
                + " AND if('" + search_overheadtank_name + "'='', oh.name LIKE '%%', oh.name LIKE '%" + search_overheadtank_name + "%')";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                OverHead_WaterPlant pd = new OverHead_WaterPlant();
                pd.setOverHead_WaterPlant_id(rset.getInt("watertreatmentplant_overheadtank_id"));
                pd.setWatertreatmentplant(rset.getString("name2"));
                pd.setOverheadtank_name(rset.getString("name1"));
                pd.setRemark(rset.getString("remark"));
                list.add(pd);
            }
        } catch (Exception e) {
            System.out.println("Error in showAllRecrod -- : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        return list;
    }

    public void insertRecord(OverHead_WaterPlant pd) {
        String query = "insert into watertreatmentplant_overheadtank (watertreatmentplant_id, overheadtank_id, remark) values (?,?,?) ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            int node_id = getWaterPlant(pd.getWatertreatmentplant());
            ps.setInt(1, node_id);
            int overhead_id = getOverHeadId(pd.getOverheadtank_name());
            ps.setInt(2, overhead_id);

            ps.setString(3, pd.getRemark());
            int i = ps.executeUpdate();
            if (i >= 0) {
                message = "Record inserted succesfully";
                messageBGColor = "Yellow";
            } else {
                message = "Record not inserted succesfully";
                messageBGColor = "red";
            }
        } catch (Exception e) {
            System.out.println("Error in insertRecord - OHLevelModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
    }

    public int getWaterPlant(String name) {
        int node_id = 0;
        String query = "SELECT watertreatmentplant_id FROM watertreatmentplant where name ='" + name + "' ";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                node_id = rs.getInt("watertreatmentplant_id");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return node_id;
    }

    public int getOverHeadId(String node_name) {
        int overheadtank_id = 0;
        String query = "SELECT overheadtank_id FROM overheadtank WHERE name ='" + node_name + "' ";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                overheadtank_id = rs.getInt("overheadtank_id");
            }
        } catch (Exception ex) {
        }
        return overheadtank_id;
    }
    public void updateRecord(OverHead_WaterPlant pd) {
        String query = "UPDATE watertreatmentplant_overheadtank SET watertreatmentplant_id=?, overheadtank_id=?, remark=? where watertreatmentplant_overheadtank_id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            int node_id = getWaterPlant(pd.getWatertreatmentplant());
            ps.setInt(1, node_id);
            int overhead_id = getOverHeadId(pd.getOverheadtank_name());
            ps.setInt(2, overhead_id);
            ps.setString(3, pd.getRemark());
            ps.setInt(4, pd.getOverHead_WaterPlant_id());
            int i = ps.executeUpdate();
            if (i > 0) {
                message = "Record updated successfully";
                messageBGColor = "yellow";
            } else {
                message = "Record not updated successfully";
                messageBGColor = "red";
            }
        } catch (Exception e) {
            System.out.println("Error in updateRecord  : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
    }

    public void deleteRecord(int id)
    {
        PreparedStatement presta = null;
        try
        {
            presta = connection.prepareStatement("DELETE FROM watertreatmentplant_overheadtank WHERE watertreatmentplant_overheadtank_id=?");
            presta.setInt(1, id);
            int i = presta.executeUpdate();
            if (i > 0)
            {
                message = "Record deleted successfully......";
                messageBGColor = "yellow";
            } else {
                message = "Record not deleted successfully......";
                messageBGColor = "red";
            }
        } catch (Exception e) {
            System.out.println("Error in deleteRecord ---- OHLevelModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
    }

    public List<String> getTankName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT name FROM overheadtank";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String node_name = rset.getString("name");
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

    public List<PurposeHeaderNode> showData1(int lowerLimit, int noOfRowsToDisplay, String headername, String headerindex) {
        List<PurposeHeaderNode> list = new ArrayList<PurposeHeaderNode>();
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }

        String query = "SELECT t.index_no,t.node_id,n.node_name, pn.node_name AS parent_node_name,t.active,t.remark "
                + "FROM node as n,tree as t, node as pn "
                + "where n.node_id=t.node_id AND t.node_parent_id=pn.node_id "
                + "And n.active='Y' And t.active='Y' and pn.active='Y' AND t.index_no NOT LIKE '0%' and n.generation_no=1 " //index_no not like '%0%'
                + " AND if('" + headername + "'='', n.node_name LIKE '%%', n.node_name LIKE '%" + headername + "%')"
                + " AND if('" + headerindex + "'='', t.index_no LIKE '%%',t.index_no LIKE '" + headerindex + "%') " // ORDER BY t.index_no"
                + " ORDER BY CAST(SUBSTRING_INDEX(t.index_no, '.', 1) AS UNSIGNED),CAST(SUBSTRING_INDEX(t.index_no, '.', -1) AS UNSIGNED),t.index_no "
                + addQuery;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                PurposeHeaderNode headerNode = new PurposeHeaderNode();
                headerNode.setIndex_no(rset.getString("index_no"));
                headerNode.setNode_id(rset.getInt("node_id"));
                headerNode.setTree_node_name(rset.getString("node_name"));
                headerNode.setNode_parent_name(rset.getString("parent_node_name"));
                headerNode.setTree_active(rset.getString("active"));
                headerNode.setTree_remark(rset.getString("remark"));
                list.add(headerNode);
            }
        } catch (Exception e) {
            System.out.println("PurposeHeaderModel showData1() Error: " + e);
        }
        return list;
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
